package com.farbig.practice.nw;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class ClientServer {

	public static void main(String[] args) throws Exception {

		//startServer();
		startClient();
	}

	static void startServer() {
		Thread t = new Thread(new Runnable() {

			
			public void run() {

				ServerSocket ss = null;
				try {
					ss = new ServerSocket(777);
					Socket s = ss.accept();
					DataInputStream din = new DataInputStream(
							s.getInputStream());
					DataOutputStream dout = new DataOutputStream(
							s.getOutputStream());
					BufferedReader br = new BufferedReader(
							new InputStreamReader(System.in));
					String str = "", str2 = "";
					while (!str.equals("stop")) {
						str = din.readUTF();
						System.out.println("client says: " + str);
						str2 = br.readLine();
						dout.writeUTF(str2);
						dout.flush();
					}
					din.close();
					s.close();
					ss.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		});

		t.start();
	}

	static void startClient() throws Exception {

		Socket s = new Socket("localhost", 777);
		DataInputStream din = new DataInputStream(s.getInputStream());
		DataOutputStream dout = new DataOutputStream(s.getOutputStream());
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		String str = "", str2 = "";
		while (!str.equals("stop")) {
			str = br.readLine();
			dout.writeUTF(str);
			dout.flush();
			str2 = din.readUTF();
			System.out.println("Server says: " + str2);
		}

		dout.close();
		s.close();
	}

}
