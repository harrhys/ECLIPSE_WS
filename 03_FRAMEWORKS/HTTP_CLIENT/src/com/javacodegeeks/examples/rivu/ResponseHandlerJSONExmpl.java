package com.javacodegeeks.examples.rivu;

import java.io.*;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.*;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONObject;

public class ResponseHandlerJSONExmpl {

	public static void main(String[] args) {
		// Creates a reference to CloseableHttpClient, which is thread safe
		CloseableHttpClient httpclient = HttpClients.createDefault();
		ResponseHandler<JSONObject> responseHandler = (ResponseHandler<JSONObject>) new MyJSONResponseHandler();
		
		try {
			HttpGet httpget = new HttpGet("http://localhost/httpclient/simplejsonparse.php");

			JSONObject responseBody = (JSONObject) httpclient.execute(httpget, responseHandler);
			String statusCode = (String) responseBody.get("status_code");
			if(statusCode.equalsIgnoreCase("0")){
				JSONObject data = (JSONObject) responseBody.get("data");
				System.out.println(data);
			} else {
				System.out.println(responseBody);
			}
		} catch(IOException e) {
			e.printStackTrace();
		} finally {
			try {
				httpclient.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
	


}
