package com.farbig.ejb;

import javax.ejb.Remote;

@Remote
public interface MyEJBRemote {

	public String sayHello(String s) throws Exception;

}
