package com.javacodegeeks.examples.rivu;


import java.io.*;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.*;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONObject;

public class LoginExmpl {

	public static void main(String[] args) {
		
		//Take Input from User for Username and Password
		String uname = "";
		String pass = "";
		
		String token = "";
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		try {
			System.out.println("Enter Username");
			uname = br.readLine();
			System.out.println("Enter Password");
			pass = br.readLine();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		// Creates a reference to CloseableHttpClient, which is thread safe
		CloseableHttpClient httpclient = HttpClients.createDefault();
		ResponseHandler<JSONObject> responseHandler = (ResponseHandler<JSONObject>) new MyJSONResponseHandler();
		
		try {
			HttpPost httpost = new HttpPost("http://localhost/httpclient/loginexample.php");

			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("username", uname));
			params.add(new BasicNameValuePair("password", pass));
			
			httpost.setEntity(new UrlEncodedFormEntity(params, Consts.UTF_8));
			
			JSONObject responseBody = (JSONObject) httpclient.execute(httpost, responseHandler);
			String statusCode = (String) responseBody.get("status_code");
			if(statusCode.equalsIgnoreCase("0")){
				JSONObject data = (JSONObject) responseBody.get("data");
				token = (String) data.get("token"); // Keep Token for future refferences
			} 

			System.out.println(responseBody.get("error_message"));
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
