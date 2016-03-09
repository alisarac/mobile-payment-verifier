package com.alisrc.wp.payment;

import javax.xml.crypto.dsig.*;
import javax.xml.crypto.dsig.dom.DOMValidateContext;
import javax.xml.parsers.DocumentBuilderFactory;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import java.security.*;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class WindowsVerifier {
	
	public static boolean validateWindowsPhoneStore(String xmlData) throws Exception {
		xmlData= xmlData.replaceAll(">\\s+<","><");
		xmlData= xmlData.replaceAll(".*<Receipt","<Receipt");

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setNamespaceAware(true);
		Document doc;
		boolean valid = false;
		doc = dbf.newDocumentBuilder().parse(new InputSource(new StringReader(xmlData)));

		NodeList root = doc.getElementsByTagName("Receipt");

		NodeList nl = doc.getElementsByTagNameNS(XMLSignature.XMLNS, "Signature");

		if (nl.getLength() == 0) {
		    throw new Exception("Cannot find Signature element");
		}
		Element value = (Element) root.item(0);
		String certificateId = value.getAttribute("CertificateId");
        
		if(certificateId == null || certificateId.isEmpty()){
			return false;
		}
		
		PublicKey pk = getPublicKeyFromFile("src/main/resources/IapReceiptProduction.cer");

		// document containing the XMLSignature
		DOMValidateContext valContext = new DOMValidateContext(pk, nl.item(0));
		String providerName = System.getProperty("jsr105Provider", "org.jcp.xml.dsig.internal.dom.XMLDSigRI");
		XMLSignatureFactory fac = XMLSignatureFactory.getInstance("DOM", (Provider) Class.forName(providerName).newInstance());
		XMLSignature signature = fac.unmarshalXMLSignature(valContext);
		valid = signature.validate(valContext);

//		// Check core validation status
//		if (valid == false) {
//		    boolean sv = signature.getSignatureValue().validate(valContext);
//		    // check the validation status of each Reference
//		    @SuppressWarnings("rawtypes")
//		    Iterator i = signature.getSignedInfo().getReferences().iterator();
//		    for (int j=0; i.hasNext(); j++) {
//		        boolean refValid = ((Reference) i.next()).validate(valContext);
//		    }            
//		}
	
		return valid;
	}

	public static boolean validateWindowsStore(String xmlData) throws Exception {

		xmlData= xmlData.replaceAll(">\\s+<","><");
		xmlData= xmlData.replaceAll(".*<Receipt","<Receipt");

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setNamespaceAware(true);
		Document doc;
		boolean valid = false;

		doc = dbf.newDocumentBuilder().parse(new InputSource(new StringReader(xmlData)));

		NodeList root = doc.getElementsByTagName("Receipt");

		NodeList nl = doc.getElementsByTagNameNS(XMLSignature.XMLNS, "Signature");

		if (nl.getLength() == 0) {
		    throw new Exception("Cannot find Signature element");
		}
		Element value = (Element) root.item(0);
		String certificateId = value.getAttribute("CertificateId");
        
		if(certificateId == null || certificateId.isEmpty()){
			return false;
		}
		
		String base64CertString = getCertificate(certificateId);
		PublicKey pk = getPublicKeyFromCert(base64CertString);
		
		// document containing the XMLSignature
		DOMValidateContext valContext = new DOMValidateContext(pk, nl.item(0));
		String providerName = System.getProperty("jsr105Provider", "org.jcp.xml.dsig.internal.dom.XMLDSigRI");
		XMLSignatureFactory fac = XMLSignatureFactory.getInstance("DOM", (Provider) Class.forName(providerName).newInstance());
		XMLSignature signature = fac.unmarshalXMLSignature(valContext);
		valid = signature.validate(valContext);

//		// Check core validation status
//		
//		if (valid == false) {
//		    boolean sv = signature.getSignatureValue().validate(valContext);
//		    // check the validation status of each Reference
//		    @SuppressWarnings("rawtypes")
//		    Iterator i = signature.getSignedInfo().getReferences().iterator();
//		    for (int j=0; i.hasNext(); j++) {
//		        boolean refValid = ((Reference) i.next()).validate(valContext);
//		    }            
//		}

		return valid;
	}

	
	private static String getCertificate(String cid) {
		String url = "https://lic.apps.microsoft.com/licensing/certificateserver/?cid=" + cid;
		String certificate="";
		try {
			URL cert = new URL(url);
	        BufferedReader in;
			in = new BufferedReader(new InputStreamReader(cert.openStream()));
	        String inputLine;

	        while ((inputLine = in.readLine()) != null){
	        	if(!inputLine.startsWith("-")){
	        		certificate += inputLine;
	        	}
	        }
	        in.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
		return certificate;
	}
	
	public static String getProduct(String xmlData) {

		Element value = getProductInfo(xmlData);
		String productId = value.getAttribute("ProductId");

		return productId;
	}

	public static Element getProductInfo(String xmlData) {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setNamespaceAware(true);
		Document doc;
		try {

			doc = dbf.newDocumentBuilder().parse(new InputSource(new StringReader(xmlData)));
			NodeList product = doc.getElementsByTagName("ProductReceipt");
			Element value = (Element) product.item(0);
			return value;
			
		} catch (Exception e) {

		}
		return null;
	}
	
	/**
	 * 
	 * @param inStream, InputStream which contains public key string
	 * @return PublicKey object to use
	 * @throws Exception, CertificateException, CertificateExpiredException, CertificateNotYetValidException
	 */
	private static PublicKey getPK(InputStream inStream) throws Exception {
		PublicKey pk = null;
		
		CertificateFactory f = CertificateFactory.getInstance("X.509");
		X509Certificate certificate = (X509Certificate)f.generateCertificate(inStream);
		
		certificate.checkValidity();

		pk = certificate.getPublicKey();
		
		return pk;
	}


	/**
	 * 
	 * @param fileName which contains public key string
	 * @return PublicKey object to use
	 * @throws Exception, FileNotFoundException, CertificateException, CertificateExpiredException, CertificateNotYetValidException
	 */
	public static PublicKey getPublicKeyFromFile(String fileName) throws Exception {
		
		FileInputStream fin = new FileInputStream(fileName);
		
		return getPK(fin);
	}
	
	/**
	 * 
	 * @param string from certificate file
	 * @return PublicKey object to use
	 * @throws Exception, CertificateException, CertificateExpiredException, CertificateNotYetValidException
	 */
	public static PublicKey getPublicKeyFromCert(String cert) throws Exception {
		
		cert = "-----BEGIN CERTIFICATE-----\n"+ cert +"\n-----END CERTIFICATE-----";		
		return getPK(new ByteArrayInputStream(cert.getBytes()));		

	}
}