package com.javacodegeeks.examples.rivu;

import java.io.*;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;

public class CloseableHttpClientExmpl {

	public static void main(String[] args) {
		CloseableHttpClient client = HttpClients.createDefault();
		HttpGet request = new HttpGet("http://localhost/httpclient/letsstart.php");
		CloseableHttpResponse response = null;
		try {
			response = client.execute(request);
			int status = response.getStatusLine().getStatusCode();

			if (status >= 200 && status < 300) {
				BufferedReader br;
				
				br = new BufferedReader(new InputStreamReader(response
							.getEntity().getContent()));
				
				String line = "";
				while ((line = br.readLine()) != null) {
					System.out.println(line);
				}
			} else {
				System.out.println("Unexpected response status: " + status);
			}
		} catch (IOException | UnsupportedOperationException e) {
			e.printStackTrace();
		} finally {
		    if(null != response){
		    	try {
					response.close();
					client.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		    }
		}
	}

}
