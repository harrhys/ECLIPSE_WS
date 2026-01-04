import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.google.gson.JsonElement;


public class JSONProductClient {
	
	public static void main(String[] args) {
		
		//testPost();
		
		testGet();
	}
	
	public static void testGet()
	{

		 
		  try {
	 
			URL url = new URL("http://localhost:7001/RESTSERVICES/rest/product/get");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");
	 
			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ conn.getResponseCode());
			}
	 
			BufferedReader br = new BufferedReader(new InputStreamReader(
				(conn.getInputStream())));
	 
			String output;
			System.out.println("Output from Server .... \n");
			StringBuilder stringBuilder = new StringBuilder();
			while ((output = br.readLine()) != null) {
				System.out.println(output);
				stringBuilder.append(output);
			}
			/*
			JSONHelper helper = new JSONHelper();
			
			helper.stringToBean(stringBuilder.toString()).;
			*/
			com.google.gson.JsonParser parser = new com.google.gson.JsonParser();
			
			JsonElement ele = parser.parse(stringBuilder.toString());
			
			
			
			conn.disconnect();
	 
		  } catch (MalformedURLException e) {
	 
			e.printStackTrace();
	 
		  } catch (IOException e) {
	 
			e.printStackTrace();
	 
		  }
	 
		
	}
	 
	public static void testPost()
	{
		 try {
			 
				URL url = new URL("http://localhost:7001/RESTSERVICES/rest/product/post");
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setDoOutput(true);
				conn.setRequestMethod("POST");
				conn.setRequestProperty("Content-Type", "application/json");
		 
				String input = "{\"qty\":100,\"name\":\"iPad 4\"}";
		 
				OutputStream os = conn.getOutputStream();
				os.write(input.getBytes());
				os.flush();
		 
				if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
					throw new RuntimeException("Failed : HTTP error code : "
						+ conn.getResponseCode());
				}
		 
				BufferedReader br = new BufferedReader(new InputStreamReader(
						(conn.getInputStream())));
		 
				String output;
				System.out.println("Output from Server .... \n");
				while ((output = br.readLine()) != null) {
					System.out.println(output);
				}
		 
				conn.disconnect();
		 
			  } catch (MalformedURLException e) {
		 
				e.printStackTrace();
		 
			  } catch (IOException e) {
		 
				e.printStackTrace();
		 
			 }
		 
	}
}
