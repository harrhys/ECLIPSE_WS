package com.farbig.ejb;

import javax.ejb.Remote;

import com.farbig.collections.BaseUser;

@Remote
public interface MyBMTBeanRemote {

	public BaseUser createBaseUser() throws Exception;

	public BaseUser createBaseUserCMT() throws Exception;
	
	public BaseUser getUserCMT(Integer id) throws Exception;
	
	public BaseUser updateUserCMT() throws Exception;

}
