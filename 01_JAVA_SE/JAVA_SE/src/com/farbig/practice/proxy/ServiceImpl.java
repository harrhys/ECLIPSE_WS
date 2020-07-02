package com.farbig.practice.proxy;

public class ServiceImpl implements ServiceInteface {
	
	public String getMsg(String s)
	{
		return s + " returned from ServiceImpl ";
	}

}
