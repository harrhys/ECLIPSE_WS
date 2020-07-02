package com.farbig.services.ws.user;

import javax.jws.WebService;

@WebService
public class UserWS {
	
	public String createBaseUser()
	{
		System.out.println("test ws");
		
		return "my user webservice";
	}

}
