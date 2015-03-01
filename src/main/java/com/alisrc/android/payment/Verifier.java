package com.alisrc.android.payment;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.StringUtils;


public class Verifier {

	/**
	 * 
	 * @param signedData
	 * @param signature
	 * @param pubkey
	 * @return boolean, verified or not verified
	 * @throws Exception
	 */
	public static boolean verifySignature(String signedData, String signature, String pubkey) throws Exception{
		boolean verified = false;
		PublicKey mypub = getPublicKeyFromString(pubkey);
		verified = verifyWithKey(signedData.getBytes(), decode(signature), mypub);			

		return verified;
	}

	/**
	 * 
	 * @param signedData
	 * @param signature
	 * @param pubkey, PublicKey object
	 * @return
	 * @throws Exception
	 */
	public static boolean verifySignatureUsingPublicKey(String signedData, String signature, PublicKey pubkey) throws Exception{
		boolean verified = false;
		verified = verifyWithKey(signedData.getBytes(), decode(signature), pubkey);			

		return verified;
	}

	/**
	 * 
	 * @param input byte array
	 * @param sig signed byte array
	 * @param key PublicKey object
	 * @return boolean , verified or not verified
	 * @throws SignatureException
	 * @throws InvalidKeyException
	 * @throws NoSuchAlgorithmException
	 */
	private static boolean verifyWithKey(byte[] input, byte[] sig, PublicKey key) throws SignatureException, InvalidKeyException, NoSuchAlgorithmException {
		Signature signature = Signature.getInstance("SHA1withRSA");
		signature.initVerify(key);
		signature.update(input);
		return signature.verify(sig);
	}

	/**
	 * 
	 * @param key public key string
	 * @return PublicKey object to use
	 * @throws Exception,  InvalidKeySpecException, NoSuchAlgorithmException
	 */
	public static PublicKey getPublicKeyFromString(String key) throws Exception
	{
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(decode(key));
		PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);
		
		return publicKey;
	}

	/**
	 * 
	 * @param string from certificate file
	 * @return PublicKey object to use
	 * @throws Exception, CertificateException, CertificateExpiredException, CertificateNotYetValidException
	 */
	public static PublicKey getPublicKeyFromCert(String cert) throws Exception {
		byte data[];
		PublicKey pk = null;
		data = decode(cert);
		
		CertificateFactory f = CertificateFactory.getInstance("X.509");
		X509Certificate certificate = (X509Certificate) f
				.generateCertificate(new ByteArrayInputStream(data));
		
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
		PublicKey pk = null;
		
		FileInputStream fin = new FileInputStream(fileName);
		CertificateFactory f = CertificateFactory.getInstance("X.509");
		X509Certificate certificate = (X509Certificate)f.generateCertificate(fin);
		
		certificate.checkValidity();

		pk = certificate.getPublicKey();
		
		return pk;
	}
	
	/**
	 * 
	 * @param string to base 64 decode 
	 * @return base 64 decoded byte array of the string 
	 */
	private static byte[] decode(String s) {
	    return StringUtils.newStringUtf8(Base64.decodeBase64(s)).getBytes();
	}

}