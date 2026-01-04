package com.farbig.ejb;

import javax.ejb.Remote;

import com.farbig.collections.BaseUser;

@Remote
public interface MyCMTBeanRemote {
	
	 public BaseUser createBaseUser()  throws Exception;
	 
	 public BaseUser getBaseUser(Integer id)  throws Exception;
	 
	 public BaseUser updateBaseUser() throws Exception;
	 
	 public BaseUser createBaseUserBMT() throws Exception;
	 
	 public BaseUser testCMTBMTCMT()  throws Exception;

}
