package com.javacodegeeks.examples.rivu;

import java.io.*;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

public class DefaultHTTPClientExmpl {

	public static void main(String[] args) {
		HttpClient client = new DefaultHttpClient();
		HttpGet request = new HttpGet("http://localhost/httpclient/letsstart.php");
		HttpResponse response;
		try {
			response = client.execute(request);
		
			// Get the response
			BufferedReader br;
			
			br = new BufferedReader(new InputStreamReader(response
						.getEntity().getContent()));
			
			String line = "";
			while ((line = br.readLine()) != null) {
				System.out.println(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (UnsupportedOperationException e) {
			e.printStackTrace();
		}
	}

}
