package com.farbig.practice.nw;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class DatagramSockets {

	public static void main(String[] args) throws Exception {
		startReciever();
		startSender();
	}

	static void startReciever() {
		Thread t = new Thread(new Runnable() {
			public void run() {

				DatagramSocket ds;
				String str = "";
				try {
					ds = new DatagramSocket(3000);
					while (str != null && !str.equals("stop")) {
						byte[] buf = new byte[1024];
						DatagramPacket dp = new DatagramPacket(buf, 1024);
						ds.receive(dp);
						str = new String(dp.getData(), 0, dp.getLength());
						System.out.println("Data recieved: "+str);
					}
					ds.close();
				} catch (SocketException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		t.start();
	}

	static void startSender() throws Exception {
		DatagramSocket ds = new DatagramSocket();
		String str = "Welcome java";
		InetAddress ip = InetAddress.getByName("127.0.0.1");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		while (!str.equals("stop")) {
			System.out.println("Enter the data");
			str = br.readLine();
			DatagramPacket dp = new DatagramPacket(str.getBytes(),
					str.length(), ip, 3000);
			ds.send(dp);
			Thread.sleep(1000);
		}
		ds.close();
	}
}