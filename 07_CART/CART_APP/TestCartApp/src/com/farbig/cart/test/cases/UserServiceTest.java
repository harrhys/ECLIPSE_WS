package com.farbig.cart.test.cases;

import java.util.Date;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NameClassPair;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;

import com.farbig.cart.entity.Account;
import com.farbig.cart.entity.Admin;
import com.farbig.cart.entity.Customer;
import com.farbig.cart.entity.Merchant;
import com.farbig.cart.persistence.PersistenceHandler;
import com.farbig.cart.persistence.PersistenceHandlerFactory;
import com.farbig.cart.persistence.TxnMgmtType;
import com.farbig.cart.services.UserServices;
import com.farbig.cart.services.gateway.util.ServiceHelper;

public class UserServiceTest {

	static UserServices userService = (UserServices) ServiceHelper.getServiceProxy(UserServices.class);

	public static void main(String args[]) throws Exception {

		ListAllJNDIEntries();
		
		testCustomerCreation();

		testAdminCreationJPAJDBCJ2SE();

		testAdminCreationJPAJDBC();

		testAdminCreationNonJTADSJ2SE();

		testAdminCreationNonJTADS();

		testAdminCreationJTADS();

		testAdminCreationHibernateJDBC();

		testAdminCreationHibernateJTA();

		testAdminCreationHibernateCMT();
		
		

	}

	public static void testAdminCreationJPAJDBCJ2SE() throws Exception {

		// This code completely runs in J2SE environment, No UserService is called
		Admin user = new Admin();
		user.setName("Hibernate JTA");
		user.setAdminType("ROOT");
		user.setStatus("ACTIVE");
		user.setUsername("cartuser2");
		user.setPassword("cart123");
		user.setCreatedBy(getClassName());
		user.setCreatedDate(new Date());

		PersistenceHandler handler = PersistenceHandlerFactory.getDataHandler(TxnMgmtType.JPA_JDBC);

		handler.openSession();
		user = (Admin) handler.save(user);
		handler.closeSession();
		handler.closeConnection();
		System.out.println(user.getId() + "-" + user.getId() + user.getName());
	}

	public static void testAdminCreationJPAJDBC() throws Exception {

		Admin user = new Admin();
		user.setName("Hibernate JTA");
		user.setAdminType("ROOT");
		user.setStatus("ACTIVE");
		user.setUsername("cartuser2");
		user.setPassword("cart123");
		user.setCreatedBy(getClassName());
		user.setCreatedDate(new Date());
		// user.setTxnMgmtType(TxnMgmtType.JPA_JDBC);
		user = userService.createAdminUser(user);
		System.out.println(user.getId() + "-" + user.getId() + user.getName());
	}

	public static void testAdminCreationNonJTADSJ2SE() throws Exception {

		// This code completely runs in J2SE environment, No UserService is called
		Admin user = new Admin();
		user.setName("Hibernate JTA");
		user.setAdminType("ROOT");
		user.setStatus("ACTIVE");
		user.setUsername("cartuser2");
		user.setPassword("cart123");
		user.setCreatedBy(getClassName());
		user.setCreatedDate(new Date());
		user.setTxnMgmtType(TxnMgmtType.JPA_NON_JTA_DS);

		PersistenceHandler handler = PersistenceHandlerFactory.getDataHandler(TxnMgmtType.JPA_NON_JTA_DS);

		handler.openSession();
		user = (Admin) handler.save(user);
		handler.closeSession();
		handler.closeConnection();
		System.out.println(user.getId() + "-" + user.getId() + user.getName());
	}

	public static void testAdminCreationNonJTADS() throws Exception {

		Admin user = new Admin();
		user.setName("Hibernate JTA");
		user.setAdminType("ROOT");
		user.setStatus("ACTIVE");
		user.setUsername("cartuser2");
		user.setPassword("cart123");
		user.setCreatedBy(getClassName());
		user.setCreatedDate(new Date());
		user.setTxnMgmtType(TxnMgmtType.JPA_JDBC);
		user = userService.createAdminUser(user);
		System.out.println(user.getId() + "-" + user.getId() + user.getName());
	}

	public static void testAdminCreationJTADS() throws Exception {

		Admin user = new Admin();
		user.setName("Hibernate JTA");
		user.setAdminType("ROOT");
		user.setStatus("ACTIVE");
		user.setUsername("cartuser2");
		user.setPassword("cart123");
		user.setCreatedBy(getClassName());
		user.setCreatedDate(new Date());
		user.setTxnMgmtType(TxnMgmtType.JPA_JTA_DS);
		user = userService.createAdminUser(user);
		System.out.println(user.getId() + "-" + user.getId() + user.getName());
	}

	public static void testAdminCreationHibernateJDBC() throws Exception {

		Admin user = new Admin();
		user.setName("Hibernate JTA");
		user.setAdminType("ROOT");
		user.setStatus("ACTIVE");
		user.setUsername("cartuser2");
		user.setPassword("cart123");
		user.setCreatedBy(getClassName());
		user.setCreatedDate(new Date());
		user.setTxnMgmtType(TxnMgmtType.HIBERNATE_JDBC);
		System.out.println(user);
		user = userService.createAdminUserBMT(user);
		System.out.println(user.getId() + "-" + user.getId() + user.getName());
	}

	public static void testAdminCreationHibernateJTA() throws Exception {

		Admin user = new Admin();
		user.setName("Hibernate JTA");
		user.setAdminType("ROOT");
		user.setStatus("ACTIVE");
		user.setUsername("cartuser2");
		user.setPassword("cart123");
		user.setCreatedBy(getClassName());
		user.setCreatedDate(new Date());
		user.setTxnMgmtType(TxnMgmtType.HIBERNATE_JTA);
		System.out.println(user);
		user = userService.createAdminUserBMT(user);
		System.out.println(user.getId() + "-" + user.getId() + user.getName());
	}

	public static void testAdminCreationHibernateCMT() throws Exception {

		Admin user = new Admin();
		user.setName("Hibernate JTA");
		user.setAdminType("ROOT");
		user.setStatus("ACTIVE");
		user.setUsername("cartuser2");
		user.setPassword("cart123");
		user.setCreatedBy(getClassName());
		user.setCreatedDate(new Date());
		user.setTxnMgmtType(TxnMgmtType.HIBERNATE_CMT);
		System.out.println(user);
		user = userService.createAdminUser(user);
		System.out.println(user.getId() + "-" + user.getId() + user.getName());
	}

	public static void testMerchantCreation() throws Exception {

		Merchant user = new Merchant();
		user.setName("CMT  NON JTA");
		user.setBusinessName("FARBIG MERCHANT");
		user.setStatus("ACTIVE");
		user.setUsername("merchant1");
		user.setPassword("cart123");
		user.setCreatedBy(getClassName());
		user.setCreatedDate(new Date());
		Account a = getNewAccount();
		user.setAccount(a);
		user = userService.createMerchant(user);
		System.out.println(user.getId() + "-" + user.getId() + user.getName());
	}

	public static void testAdminCreationBMT() throws Exception {

		Admin user = new Admin();
		user.setName("BMT JTA");
		user.setAdminType("ROOT");
		user.setStatus("ACTIVE");
		user.setUsername("cartuser2");
		user.setPassword("cart123");
		user.setCreatedBy(getClassName());
		user.setCreatedDate(new Date());
		user = userService.createAdminUserBMT(user);
		System.out.println(user.getId() + "-" + user.getId() + user.getName());
	}

	public static void testMerchantCreationBMT() throws Exception {

		Merchant user = new Merchant();
		user.setName("BMT NON JTA");
		user.setBusinessName("FARBIG MERCHANT");
		user.setStatus("ACTIVE");
		user.setUsername("merchant1");
		user.setPassword("cart123");
		user.setCreatedBy(getClassName());
		user.setCreatedDate(new Date());
		Account a = getNewAccount();
		user.setAccount(a);
		user = userService.createMerchantBMT(user);
		System.out.println(user.getId() + "-" + user.getId() + user.getName());

	}

	public static void testCustomerCreation() throws Exception {

		Customer user = new Customer();
		user.setName("CMT JDBC");
		user.setCustomerType("SILVER");
		user.setStatus("ACTIVE");
		user.setUsername("customer1");
		user.setPassword("cart123");
		user.setCreatedBy(getClassName());
		user.setCreatedDate(new Date());
		Account a = getNewAccount();
		user.setAccount(a);
		user = userService.createCustomer(user);
		System.out.println(user.getId() + "-" + user.getId() + user.getName());

	}

	public static void testCustomerCreationBMT() throws Exception {

		Customer user = new Customer();
		user.setName("BMT JDBC");
		user.setCustomerType("SILVER");
		user.setStatus("ACTIVE");
		user.setUsername("customer1");
		user.setPassword("cart123");
		user.setCreatedBy(getClassName());
		user.setCreatedDate(new Date());
		Account a = getNewAccount();
		user.setAccount(a);
		user = userService.createCustomerBMT(user);
		System.out.println(user.getId() + "-" + user.getId() + user.getName());

	}

	public static Account getNewAccount() {
		Account a = new Account();
		a.setBalance(0);
		a.setStatus("ACTIVE");
		return a;
	}

	public static String getClassName() {
		return UserServiceTest.class.getSimpleName();
	}

	private static Context getContext() {

		Properties p = new Properties();
		p.put(Context.PROVIDER_URL, "t3://localhost:7001");
		p.put(Context.INITIAL_CONTEXT_FACTORY, "weblogic.jndi.WLInitialContextFactory");

		Context context = null;
		try {
			context = new InitialContext(p);

		} catch (NamingException e) {
			e.printStackTrace();
		}

		return context;
	}

	private static void ListAllJNDIEntries() {
		try {
			Context context = getContext();

			// List all JNDI Registries in the environment
			NamingEnumeration<NameClassPair> list = context.list("/");

			while (list.hasMore()) {

				System.out.println(list.next().getName());

			}
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

}
