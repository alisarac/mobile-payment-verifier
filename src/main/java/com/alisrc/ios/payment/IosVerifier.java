package com.alisrc.ios.payment;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import com.google.gson.Gson;

public class IosVerifier {
	
	private static IosVerifyResponse iosVerify(String orderId, boolean isSandbox) throws IOException {
		
		String verifyUrl = "https://buy.itunes.apple.com/verifyReceipt";
		
		if(isSandbox){
			verifyUrl = "https://sandbox.itunes.apple.com/verifyReceipt";
		}
		
		URL obj;
		StringBuffer response=new StringBuffer();
		IosVerifyResponse json =null;
		obj = new URL(verifyUrl);
	
		HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
		orderId = orderId.replaceAll("(\\r|\\n)", "");
		con.setRequestMethod("POST");
		con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded"); 
		String urlParameters = "{\"receipt-data\":\""+ orderId +"\"}";
		byte[] byteArray = urlParameters.getBytes("UTF-8");
		
		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.write(byteArray);
		wr.flush();
		wr.close();
 
		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
 
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		
		Gson gson = new Gson();  
		json = gson.fromJson(response.toString(), IosVerifyResponse.class);
		
		// if sandbox order is sent to live
		// we know it and we can verify it in sandbox mode
		if(json.getStatus() == 21007){
			return iosVerify(orderId, true);
		}
		return json;
	}
		
	public static boolean checkIosPayment(String orderId, boolean isSandbox) throws IOException {
		boolean isVerified = false;
		
		IosVerifyResponse verifiedData = iosVerify(orderId, isSandbox);
		
		if(verifiedData != null && verifiedData.getReceipt().getUnique_identifier() != null){
			isVerified = true;
		}
		return isVerified;
	}
	public static boolean checkIosPayment(String orderId) throws IOException {
		boolean isVerified = false;
		
		IosVerifyResponse verifiedData = iosVerify(orderId, false);
		
		if(verifiedData != null && verifiedData.getReceipt().getUnique_identifier() != null){
			isVerified = true;
		}
		return isVerified;
	}
}
