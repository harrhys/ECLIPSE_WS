package com.test;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

@WebService

public interface WSInterface {
	
	public Emp getEmployee(String name);
	@SOAPBinding(style=Style.DOCUMENT.RPC)
	public String getFullName(String first, String last);
	

}
