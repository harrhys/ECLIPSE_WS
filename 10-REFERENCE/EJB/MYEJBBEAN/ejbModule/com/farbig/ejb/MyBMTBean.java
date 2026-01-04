package com.farbig.ejb;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

import com.farbig.collections.BaseUser;

@Stateless(mappedName = "mybmtbean")
@TransactionManagement(TransactionManagementType.BEAN)
public class MyBMTBean implements
		MyBMTBeanRemote {
	
	

	public MyBMTBean() {
	}

	public BaseUser createBaseUser() throws NamingException, NotSupportedException, SystemException, SecurityException, IllegalStateException, RollbackException, HeuristicMixedException, HeuristicRollbackException {
		// This method will throw exception as it doesn't have the JTA Transaction
		
		Context context = BeanHelper.getEJBContext();
		
		UserTransaction txn = (UserTransaction) context.lookup("java:comp/UserTransaction");
		
		txn.begin();
		
		UserService service = new UserService();

		BaseUser user = service.createBaseUser();
		
		txn.commit();
		
		/*txn.begin();
		
		System.out.println(service.getBaseUser(user.getUserId()).getUserName());
		
		txn.commit();*/

		return user;
	}

	@Override
	public BaseUser createBaseUserCMT() throws Exception {
		
		MyCMTBeanRemote cmtproxy = BeanHelper.getCMTBeanProxy();
		BaseUser user = cmtproxy.createBaseUser();

		System.out.println(user.getUserId());

		return user;

	}

	@Override
	public BaseUser getUserCMT(Integer id) throws Exception {
		

		MyCMTBeanRemote cmtproxy = BeanHelper.getCMTBeanProxy();
		
		return cmtproxy.getBaseUser(id);
	}

	@Override
	public BaseUser updateUserCMT() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
