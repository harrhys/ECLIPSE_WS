package com.farbig.cart.ws;

import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/hello")
public class HelloWebSocket {
	
	public void greetTheClient(Session session)
	{
		try{
			session.getBasicRemote().sendText("Hello Client");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	

}
