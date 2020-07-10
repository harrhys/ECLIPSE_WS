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

public class ResponseHandlerStringExmpl {

	public static void main(String[] args) {
		// Creates a reference to CloseableHttpClient, which is thread safe
		CloseableHttpClient httpclient = HttpClients.createDefault();
		ResponseHandler<String> responseHandler = (ResponseHandler<String>) new MyStringResponseHandler();
		
		try {
			HttpGet httpget = new HttpGet("http://localhost/httpclient/letsstart.php");

			String responseBody = httpclient.execute(httpget, responseHandler);
			System.out.println(responseBody);
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
