package com.farbig.practice.nw;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLConnection;

public class URLUtil {

	public static void main(String are[]) {

		try {
			URL url = new URL("http://www.javatpoint.com/java-tutorial");
			URLConnection urlcon = url.openConnection();
			InputStream stream = urlcon.getInputStream();
			int i;
			while ((i = stream.read()) != -1) {
				System.out.print((char) i);
			}
		} catch (Exception e) {
			System.out.println(e);
		}

		try {
			URL url = new URL("http://www.javatpoint.com/java-tutorial");
			HttpURLConnection huc = (HttpURLConnection) url.openConnection();
			for (int i = 1; i <= 8; i++) {
				System.out.println(huc.getHeaderFieldKey(i) + " = "
						+ huc.getHeaderField(i));
			}

			System.out.println(huc.getHeaderFields());
			huc.disconnect();
		} catch (Exception e) {
			System.out.println(e);
		}

		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(
					System.in));

			String hostName = "";

			while (!hostName.equals("stop")) {

				if (!hostName.equals("")) {
					InetAddress ip = InetAddress.getByName(hostName);
					System.out.println("Host Name: " + ip.getHostName());
					System.out.println("IP Address: " + ip.getHostAddress());
				}
				hostName = br.readLine();

			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

}
