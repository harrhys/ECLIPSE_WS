package com.farbig.ejb;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import com.farbig.collections.BaseUser;

@Stateless(mappedName = "mycmtbean")
@TransactionAttribute(TransactionAttributeType.REQUIRED)

public class MyCMTBean implements MyCMTBeanRemote {

	public MyCMTBean() {
	}

	public BaseUser createBaseUser() {
		UserService service = new UserService();

		BaseUser user = service.createBaseUser();

		System.out.println("USER ID" + user.getUserId());

		return user;
	}
	
	@Override
	public BaseUser getBaseUser(Integer id) throws Exception {
		UserService service = new UserService();

		return service.getBaseUser(id);
	}

	@Override
	
	public BaseUser createBaseUserBMT() throws Exception {

		MyBMTBeanRemote bmtproxy = BeanHelper.getBMTBeanProxy();

		BaseUser user = bmtproxy.createBaseUser();

		System.out.println(user.getUserId());

		return user;
	}

	@Override
	public BaseUser testCMTBMTCMT() throws Exception {

		MyBMTBeanRemote bmtproxy = BeanHelper.getBMTBeanProxy();
		BaseUser user = bmtproxy.createBaseUserCMT();
		user = getBaseUser(user.getUserId());
		return user;
	}



	@Override
	public BaseUser updateBaseUser() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
