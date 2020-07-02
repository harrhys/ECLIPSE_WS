package com.farbig.practice.jpos;

import org.jpos.q2.Q2;

public class SwitchServer {
	
	public static void main(String ...strings) {
		
		Q2 server = new Q2();
		server.start();
		
	}

}
