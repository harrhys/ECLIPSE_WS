package courses.hibernate.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import junit.framework.Assert;

import org.hibernate.HibernateException;
import org.junit.AfterClass;
import org.junit.Test;

import courses.hibernate.util.JPAUtil;
import courses.hibernate.vo.Account;
import courses.hibernate.vo.AccountOwner;
import courses.hibernate.vo.AccountTransaction;
import courses.hibernate.vo.CdAccount;
import courses.hibernate.vo.CheckingAccount;
import courses.hibernate.vo.EBill;
import courses.hibernate.vo.SavingsAccount;

/**
 * Service layer tests for Account
 */
public class AccountServiceTest extends ServiceTest {


	/**
	 * Close EntityManagerFactory after all tests have been executed
	 */
	@AfterClass
	public static void closeEntityManagerFactory() {
		JPAUtil.getEntityManagerFactory().close();
	}

	/**
	 * Test account creation
	 */
	@Test
	public void testCreateAccount() {

		SavingsAccount account = new SavingsAccount();
		EntityManager entityManager = null;

		try {
			entityManager = JPAUtil.getEntityManager();
			entityManager.getTransaction().begin();

			account.setCreationDate(new Date());
			account.setBalance(1000L);
			account.setInterestRate(3.5);

			Assert.assertTrue(account.getAccountId() == 0);

			// save the account
			// ---- --- -------
			AccountService accountService = new AccountService();
			accountService.saveOrUpdateAccount(account);

			entityManager.getTransaction().commit();
			System.out.println("var account = " + account);

			// check that IDs were set after the hbm session
			// ----- ---- --- ---- --- ----- --- --- -------
			Assert.assertTrue(account.getAccountId() > 0);
		} catch (HibernateException e) {
			e.printStackTrace();
			entityManager.getTransaction().rollback();
			Assert.fail();
		} finally {
			entityManager.close();
		}

		// cleanup
		// -------
		deleteAccount(account);
	}

	/**
	 * Test retrieval of account
	 */
	@Test
	public void testGetAccount() {
		Account account = null;
		EntityManager entityManager = null;

		try {
			entityManager = JPAUtil.getEntityManager();
			entityManager.getTransaction().begin();
			account = createSavingsAccount();
			entityManager.getTransaction().commit();
		} catch (HibernateException e) {
			e.printStackTrace();
			entityManager.getTransaction().rollback();
			Assert.fail();
		} finally {
			entityManager.close();
		}

		try {
			entityManager = JPAUtil.getEntityManager();
			entityManager.getTransaction().begin();

			System.out.println("var account = " + account);
			AccountService accountService = new AccountService();
			Account anotherCopy = accountService.getAccount(account
					.getAccountId());

			System.out.println("var anotherCopy = " + anotherCopy);

			// make sure these are two separate instances
			// ---- ---- ----- --- --- -------- ---------
			Assert.assertTrue(account != anotherCopy);

			entityManager.getTransaction().commit();
		} catch (HibernateException e) {
			e.printStackTrace();
			entityManager.getTransaction().rollback();
			Assert.fail();
		} finally {
			entityManager.close();
		}

		// cleanup
		// -------
		deleteAccount(account);
	}

	/**
	 * Test updating of account balance
	 */
	@Test
	public void testUpdateAccountBalance() {

		Account account = null;
		EntityManager entityManager = null;
		AccountService accountService = new AccountService();

		try {
			entityManager = JPAUtil.getEntityManager();
			entityManager.getTransaction().begin();

			account = createCheckingAccount();
			System.out.println("var account = " + account);

			account.setBalance(2000);
			accountService.saveOrUpdateAccount(account);
			entityManager.getTransaction().commit();
		} catch (HibernateException e) {
			e.printStackTrace();
			entityManager.getTransaction().rollback();
			Assert.fail();
		} finally {
			entityManager.close();
		}

		try {
			entityManager = JPAUtil.getEntityManager();
			entityManager.getTransaction().begin();

			Account anotherCopy = accountService.getAccount(account
					.getAccountId());
			System.out.println("var anotherCopy = " + anotherCopy);

			// make sure the one we just pulled back
			// from the database has the updated balance
			// -----------------------------------------
			Assert.assertTrue(anotherCopy.getBalance() == 2000);

			entityManager.getTransaction().commit();
		} catch (HibernateException e) {
			e.printStackTrace();
			entityManager.getTransaction().rollback();
			Assert.fail();
		} finally {
			entityManager.close();
		}
		// cleanup
		// -------
		deleteAccount(account);
	}

	/**
	 * Test deletion of account
	 */
	@Test
	public void testDeleteAccount() {

		Account account = null;
		EntityManager entityManager = null;
		AccountService accountService = new AccountService();

		try {
			entityManager = JPAUtil.getEntityManager();
			entityManager.getTransaction().begin();

			account = createCheckingAccount();
			System.out.println("var account = " + account);

			// delete the account
			// ------ --- -------
			accountService.deleteAccount(account);

			entityManager.getTransaction().commit();
		} catch (HibernateException e) {
			e.printStackTrace();
			entityManager.getTransaction().rollback();
			Assert.fail();
		} finally {
			entityManager.close();
		}
		try {
			// try to get the account again -- should be null
			// --- -- --- --- ------- ----- -- ------ -- ----
			entityManager = JPAUtil.getEntityManager();
			entityManager.getTransaction().begin();

			Account anotherCopy = accountService.getAccount(account
					.getAccountId());

			System.out.println("var anotherCopy = " + anotherCopy);

			Assert.assertNull(anotherCopy);

			entityManager.getTransaction().commit();
		} catch (HibernateException e) {
			e.printStackTrace();
			entityManager.getTransaction().rollback();
			Assert.fail();
		} finally {
			entityManager.close();
		}
	}

	/**
	 * Test many to many relationship of account-accountOwners. Create two
	 * accounts and accountOwners and build associations. Ensure that retrieval
	 * of the accountOwners and accounts have the appropriate relationships.
	 */
	@Test
	public void testManyToManyAccountAccountOwners() {
		AccountOwner accountOwner1 = null;
		AccountOwner accountOwner2 = null;
		Account account1 = null;
		Account account2 = null;
		EntityManager entityManager = null;
		AccountService accountService = new AccountService();

		try {
			entityManager = JPAUtil.getEntityManager();
			entityManager.getTransaction().begin();

			// Create AccountOwners and Accounts
			// ------ ------------- --- --------
			accountOwner1 = createAccountOwner("111-11-1111");
			accountOwner2 = createAccountOwner("222-22-2222");

			account1 = buildSavingsAccount();
			account1.addAccountOwner(accountOwner1);
			account2 = buildSavingsAccount();
			account2.addAccountOwner(accountOwner1);
			account2.addAccountOwner(accountOwner2);

			accountService.saveOrUpdateAccount(account1);
			accountService.saveOrUpdateAccount(account2);

			entityManager.getTransaction().commit();
		} catch (HibernateException e) {
			e.printStackTrace();
			entityManager.getTransaction().rollback();
			Assert.fail();
		} finally {
			entityManager.close();
		}

		try {
			// Load AccountOwner and ensure counts of accounts are correct
			// ---- ------------ --- ------ ------ -- -------- --- -------
			entityManager = JPAUtil.getEntityManager();
			entityManager.getTransaction().begin();

			AccountOwnerService accountOwnerService = new AccountOwnerService();
			accountOwner1 = accountOwnerService.getAccountOwner(accountOwner1
					.getAccountOwnerId());
			accountOwner2 = accountOwnerService.getAccountOwner(accountOwner2
					.getAccountOwnerId());
			Assert.assertEquals(2, accountOwner1.getAccounts().size());
			Assert.assertEquals(1, accountOwner2.getAccounts().size());

			// Load Accounts and ensure counts of AccountOwners are correct
			// ---- -------- --- ------ ------ -- ------------- --- -------
			account1 = accountService.getAccount(account1.getAccountId());
			account2 = accountService.getAccount(account2.getAccountId());
			Assert.assertEquals(1, account1.getAccountOwners().size());
			Assert.assertEquals(2, account2.getAccountOwners().size());

			System.out.println(accountOwner1);
			System.out.println(accountOwner2);
			System.out.println(account1);
			System.out.println(account2);
			entityManager.getTransaction().commit();
		} catch (HibernateException e) {
			e.printStackTrace();
			entityManager.getTransaction().rollback();
			Assert.fail();
		} finally {
			entityManager.close();
		}

		// cleanup
		// -------
		deleteAccount(account1);
		deleteAccount(account2);
		deleteAccountOwner(accountOwner1);
		deleteAccountOwner(accountOwner2);
	}

	/**
	 * Test creation of multiple ebills for an account.
	 */
	@Test
	public void testCreateMultipleEBills() {
		EntityManager entityManager = null;
		Account account = null;
		AccountService accountService = new AccountService();
		EBillService ebillService = new EBillService();
		long accountId = 0;
		try {
			entityManager = JPAUtil.getEntityManager();
			entityManager.getTransaction().begin();

			List<EBill> ebills = new ArrayList<EBill>();

			// Create an account
			// ------ -- -------
			account = createCheckingAccount();

			// Create 10 ebills for account. Because this is a set, need
			// some time between the ebills to set a different due date,
			// hence the sleep
			// ---------------------------------------------------------
			for (int i = 0; i < 10; i++) {
				EBill ebill = buildEBill(createEBiller(), account);
				ebills.add(ebill);
				ebillService.saveOrUpdateEBill(ebill);
				sleep(100);
			}
			accountId = account.getAccountId();
			entityManager.getTransaction().commit();
		} catch (HibernateException e) {
			e.printStackTrace();
			entityManager.getTransaction().rollback();
			Assert.fail();
		} finally {
			entityManager.close();
		}

		// Retrieve the persisted account and ensure that the account
		// contains the 10 ebills.
		// ----------------------------------------------------------
		try {
			entityManager = JPAUtil.getEntityManager();
			entityManager.getTransaction().begin();

			accountService = new AccountService();

			account = accountService.getAccount(accountId);
			System.out.println(account);
			Assert.assertEquals(10, account.getEbills().size());

			entityManager.getTransaction().commit();
		} catch (HibernateException e) {
			e.printStackTrace();
			entityManager.getTransaction().rollback();
			Assert.fail();
		} finally {
			entityManager.close();
		}

		// cleanup
		// -------
		deleteAccount(account);
	}

	/**
	 * Test creation of multiple transactions for an account.
	 */
	@Test
	public void testCreateMultipleAccountTransactions() {
		EntityManager entityManager = null;
		Account account = null;
		long accountId = 0;

		try {
			entityManager = JPAUtil.getEntityManager();
			entityManager.getTransaction().begin();

			AccountTransactionService accountTransactionService = new AccountTransactionService();
			List<AccountTransaction> accountTransactions = new ArrayList<AccountTransaction>();

			// Create an account
			// ------ -- -------
			account = createCheckingAccount();

			// Create 10 account transactions for account. Because this is a
			// set, need some time between the ebills to set a different due
			// date, hence the sleep
			// ---------------------------------------------------------
			for (int i = 0; i < 10; i++) {
				AccountTransaction accountTransaction = createAccountTransaction(
						account, 10);
				accountTransactions.add(accountTransaction);
				accountTransactionService
						.saveOrUpdateAccountTransaction(accountTransaction);
				sleep(100);
			}
			accountId = account.getAccountId();
			entityManager.getTransaction().commit();
		} catch (HibernateException e) {
			e.printStackTrace();
			entityManager.getTransaction().rollback();
			Assert.fail();
		} finally {
			entityManager.close();
		}

		try {
			// Retrieve the persisted account and ensure that the account
			// contains the 10 transactions.
			// ----------------------------------------------------------
			entityManager = JPAUtil.getEntityManager();
			entityManager.getTransaction().begin();

			AccountService accountService = new AccountService();

			account = accountService.getAccount(accountId);
			System.out.println(account);
			Assert.assertEquals(10, account.getAccountTransactions().size());
			Assert.assertEquals(new Double(900), account.getBalance());

			entityManager.getTransaction().commit();
		} catch (HibernateException e) {
			e.printStackTrace();
			entityManager.getTransaction().rollback();
			Assert.fail();
		} finally {
			entityManager.close();
		}
		// cleanup
		// -------
		deleteAccount(account);
	}

	/**
	 * Test Account Inheritance
	 */
	@Test
	public void testAccountInheritance() {
		EntityManager entityManager = null;
		List<Account> accounts = null;

		try {
			entityManager = JPAUtil.getEntityManager();
			entityManager.getTransaction().begin();

			// Create two checking accounts and three savings accounts
			// ------ --- -------- -------- --- ----- ------- --------
			createCheckingAccount();
			createCheckingAccount();
			createSavingsAccount();
			createSavingsAccount();
			createSavingsAccount();
			createCdAccount();

			entityManager.getTransaction().commit();
		} catch (HibernateException e) {
			e.printStackTrace();
			entityManager.getTransaction().rollback();
			Assert.fail();
		} finally {
			entityManager.close();
		}

		try {
			// Retrieve the accounts and confirm that 5 accounts are retrieved,
			// 2 checking accounts, and 3 savings accounts
			// ----------------------------------------------------------------
			entityManager = JPAUtil.getEntityManager();
			entityManager.getTransaction().begin();

			AccountService accountService = new AccountService();
			accounts = accountService.getAccounts();
			List<CheckingAccount> checkingAccounts = accountService
					.getCheckingAccounts();
			List<SavingsAccount> savingsAccounts = accountService
					.getSavingsAccounts();
			List<CdAccount> cdAccounts = accountService.getCdAccounts();

			//Assert.assertEquals(6, accounts.size());
			/*
			 * Assert.assertEquals(2, checkingAccounts.size()); Assert.assertEquals(3,
			 * savingsAccounts.size()); Assert.assertEquals(1, cdAccounts.size());
			 */

			entityManager.getTransaction().commit();
		} catch (HibernateException e) {
			e.printStackTrace();
			entityManager.getTransaction().rollback();
			Assert.fail();
		} finally {
			entityManager.close();
		}

		// cleanup
		// -------
		for (Account account : accounts) {
			deleteAccount(account);
		}
	}

	/**
	 * Test cascade of an account deletion
	 */
	@Test
	public void testAccountDeleteCascade() {
		EntityManager entityManager = null;
		AccountService accountService = new AccountService();
		AccountOwnerService accountOwnerService = new AccountOwnerService();

		Account account = null;
		AccountOwner accountOwner1 = null;
		AccountOwner accountOwner2 = null;
		AccountTransaction accountTransaction = null;

		try {
			// Create Account, AccountOwner, and AccountTransaction and set
			// AccountOwner and AccountTransaction on Account
			// ------------------------------------------------------------
			entityManager = JPAUtil.getEntityManager();
			entityManager.getTransaction().begin();

			account = createCheckingAccount();
			accountOwner1 = createAccountOwner("111-11-1111");
			accountOwner2 = createAccountOwner("222-22-2222");

			accountTransaction = createAccountTransaction(account, 10);

			account.addAccountOwner(accountOwner1);
			account.addAccountOwner(accountOwner2);
			account.addAccountTransaction(accountTransaction);

			accountService.saveOrUpdateAccount(account);

			entityManager.getTransaction().commit();
		} catch (HibernateException e) {
			e.printStackTrace();
			entityManager.getTransaction().rollback();
			Assert.fail();
		} finally {
			entityManager.close();
		}

		try {
			// Delete the account
			// ------ --- -------
			entityManager = JPAUtil.getEntityManager();
			entityManager.getTransaction().begin();

			accountService.deleteAccount(account);

			entityManager.getTransaction().commit();
		} catch (HibernateException e) {
			e.printStackTrace();
			entityManager.getTransaction().rollback();
			Assert.fail();
		} finally {
			entityManager.close();
		}
		try {
			// Retrieve the account and confirm that the account and
			// accountTransaction have been deleted
			// -----------------------------------------------------
			entityManager = JPAUtil.getEntityManager();
			entityManager.getTransaction().begin();

			account = accountService.getAccount(account.getAccountId());
			Assert.assertNull(account);

			AccountTransactionService accountTransactionService = new AccountTransactionService();
			accountTransaction = accountTransactionService
					.getAccountTransaction(accountTransaction
							.getAccountTransactionId());
			Assert.assertNull(accountTransaction);

			// Retrieve the account owner and confirm account owners have
			// not been deleted
			// ----------------------------------------------------------
			accountOwner1 = accountOwnerService.getAccountOwner(accountOwner1
					.getAccountOwnerId());
			accountOwner2 = accountOwnerService.getAccountOwner(accountOwner2
					.getAccountOwnerId());

			Assert.assertNotNull(accountOwner1);
			Assert.assertNotNull(accountOwner2);
		} catch (HibernateException e) {
			e.printStackTrace();
			entityManager.getTransaction().rollback();
			Assert.fail();
		} finally {
			entityManager.close();
		}

		// Cleanup Account Owners
		// ------- ------- ------
		deleteAccountOwner(accountOwner1);
		deleteAccountOwner(accountOwner2);
	}
}
