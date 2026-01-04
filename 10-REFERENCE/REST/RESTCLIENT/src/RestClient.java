import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class RestClient {

	public static void main(String[] args) {

		testPut();
	}

	public static void testGet() {

		// TODO Auto-generated method stub
		try {

			URL url = new URL(
					"http://localhost:7001/RESTSERVICES/rest/university");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "text/html");

			if (conn.getResponseCode() != 200) {
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
	
	public static void testPut() { try {
 
		URL url = new URL("http://localhost:7001/RESTSERVICES/rest/university/24");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setDoOutput(true);
		conn.setRequestMethod("PUT");
	//	conn.setRequestProperty("Accept", "text/plain");
 
		/*String input = "";
 
		OutputStream os = conn.getOutputStream();
		os.write(input.getBytes());
		os.flush();*/
 /*
		if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
			throw new RuntimeException("Failed : HTTP error code : "
				+ conn.getResponseCode());
		}*/
 
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
 
	 }}


}
