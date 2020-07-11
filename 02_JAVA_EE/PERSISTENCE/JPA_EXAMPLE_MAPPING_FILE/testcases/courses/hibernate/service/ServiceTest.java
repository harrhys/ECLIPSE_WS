package courses.hibernate.service;

import java.util.Date;

import javax.persistence.EntityManager;

import junit.framework.Assert;

import org.hibernate.HibernateException;

import courses.hibernate.util.JPAUtil;
import courses.hibernate.vo.Account;
import courses.hibernate.vo.AccountOwner;
import courses.hibernate.vo.AccountTransaction;
import courses.hibernate.vo.CdAccount;
import courses.hibernate.vo.CheckingAccount;
import courses.hibernate.vo.EBill;
import courses.hibernate.vo.EBiller;
import courses.hibernate.vo.SavingsAccount;

/**
 * Parent class for Service Tests that contains helper methods
 */
public abstract class ServiceTest {

	/**
	 * Create an ebiller
	 * 
	 * @return ebiller that was created
	 */
	protected EBiller createEBiller() {
		EBiller eBiller = new EBiller();
		eBiller.setName("DISCOVER CARD");
		eBiller.setBillingAddress("500 Madison Avenue NY, NY 10015");
		eBiller.setPhone("1-800-DISCOVER");
		EBillerService ebillerService = new EBillerService();
		ebillerService.saveOrUpdateEBiller(eBiller);
		return eBiller;
	}

	/**
	 * Delete an ebiller
	 * 
	 * @param ebiller
	 *            ebiller to be deleted
	 */
	protected void deleteEBiller(EBiller ebiller) {
		EntityManager entityManager = null;
		try {
			entityManager = JPAUtil.getEntityManager();
			entityManager.getTransaction().begin();
			EBillerService ebillerService = new EBillerService();
			ebillerService.deleteEBiller(ebiller);
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
	 * Build an ebill object
	 * 
	 * @param ebiller
	 *            ebiller associated with ebill
	 * @return ebill
	 */
	protected EBill buildEBill(EBiller ebiller, Account account) {
		long dueDate = new Date().getTime() + 30 * 24 * 60 * 60 * 1000;
		EBill ebill = new EBill(new Date(), new Date(dueDate), ebiller,
				account, 25, 1000);
		return ebill;
	}

	/**
	 * Create an ebill object
	 * 
	 * @return ebill that was created
	 */
	protected EBill createEBill(Account account) {
		EBill ebill = buildEBill(createEBiller(), account);
		EBillService ebillService = new EBillService();
		ebillService.saveOrUpdateEBill(ebill);
		return ebill;
	}

	/**
	 * Delete an ebill
	 * 
	 * @param ebill
	 *            ebill to be deleted
	 */
	protected void deleteEBill(EBill ebill) {
		EntityManager entityManager = null;
		try {
			entityManager = JPAUtil.getEntityManager();
			entityManager.getTransaction().begin();
			EBillService ebillService = new EBillService();
			ebillService.deleteEBill(ebill);
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
	 * Build a CD account
	 * 
	 * @return cdAccount
	 */
	protected CdAccount buildCdAccount() {
		CdAccount account = new CdAccount();
		account.setCreationDate(new Date());
		account.setBalance(1000L);
		account.setInterestRate(3.5);
		account.setMaturityDate(new Date());
		return account;
	}

	/**
	 * Build a savings account
	 * 
	 * @return savingsAccount
	 */
	protected SavingsAccount buildSavingsAccount() {
		SavingsAccount account = new SavingsAccount();
		account.setCreationDate(new Date());
		account.setBalance(1000L);
		account.setInterestRate(3.5);
		return account;
	}

	/**
	 * Build a checking account
	 * 
	 * @return checkingAccount
	 */
	protected CheckingAccount buildCheckingAccount() {
		CheckingAccount account = new CheckingAccount();
		account.setCreationDate(new Date());
		account.setBalance(1000L);
		account.setCheckStyle("ELEPHANTS");
		return account;
	}

	/**
	 * Create a savings account
	 * 
	 * @return savings account that was created
	 */
	protected SavingsAccount createSavingsAccount() {
		return (SavingsAccount) createAccount(buildSavingsAccount());
	}

	/**
	 * Create a checking account
	 * 
	 * @return checking account that was created
	 */
	protected CheckingAccount createCheckingAccount() {
		return (CheckingAccount) createAccount(buildCheckingAccount());
	}

	/**
	 * Create a cd account
	 * 
	 * @return cd account that was created
	 */
	protected CdAccount createCdAccount() {
		return (CdAccount) createAccount(buildCdAccount());
	}

	/**
	 * Create an account
	 * 
	 * @return account that was created
	 */
	protected Account createAccount(Account account) {
		AccountService accountService = new AccountService();
		accountService.saveOrUpdateAccount(account);
		return account;
	}

	/**
	 * Delete an account
	 * 
	 * @param account
	 *            account to be deleted
	 */
	protected void deleteAccount(Account account) {
//		EntityManager entityManager = null;
//		try {
//			entityManager = JPAUtil.getEntityManager();
//			entityManager.getTransaction().begin();
//			AccountService accountService = new AccountService();
//			accountService.deleteAccount(account);
//			entityManager.getTransaction().commit();
//		} catch (HibernateException e) {
//			e.printStackTrace();
//			entityManager.getTransaction().rollback();
//			Assert.fail();
//		} finally {
//			entityManager.close();
//		}
	}

	/**
	 * Build an account transaction
	 * 
	 * @param amount
	 *            amount for transaction
	 * @return created account transaction
	 */
	protected AccountTransaction buildAccountTransaction(Account account,
			double amount) {
		AccountTransaction accountTransaction = new AccountTransaction();
		accountTransaction.setAmount(amount);
		accountTransaction.setTransactionDate(new Date());
		accountTransaction
				.setTransactionType(AccountTransaction.TRANSACTION_TYPE_DEBIT);
		accountTransaction.setAccount(account);
		return accountTransaction;
	}

	/**
	 * Create an electronic account transaction
	 * 
	 * @param amount
	 *            amount for transaction
	 * @return created account transaction
	 */
	protected AccountTransaction createAccountTransaction(Account account,
			double amount) {
		AccountTransaction accountTransaction = buildAccountTransaction(
				account, amount);

		AccountTransactionService accountTransactionService = new AccountTransactionService();
		accountTransactionService
				.saveOrUpdateAccountTransaction(accountTransaction);
		return accountTransaction;
	}

	/**
	 * Delete an account transaction
	 * 
	 * @param accountTransaction
	 *            accountTransaction to be deleted
	 */
	protected void deleteAccountTransaction(
			AccountTransaction accountTransaction) {
//		EntityManager entityManager = null;
//		try {
//			entityManager = JPAUtil.getEntityManager();
//			entityManager.getTransaction().begin();
//			entityManager.remove(accountTransaction);
//			entityManager.getTransaction().commit();
//		} catch (HibernateException e) {
//			e.printStackTrace();
//			entityManager.getTransaction().rollback();
//			Assert.fail();
//		} finally {
//			entityManager.close();
//		}
	}

	/**
	 * Build an account owner
	 * 
	 * @return account owner
	 */
	protected AccountOwner buildAccountOwner(String socialSecurityNumber) {
		AccountOwner accountOwner = new AccountOwner();
		accountOwner.setCellPhone("111-111-1111");
		accountOwner.setFirstName("Matthew");
		accountOwner.setLastName("Cherry");
		accountOwner.setHomePhone("222-222-2222");
		accountOwner.setSocialSecurityNumber(socialSecurityNumber);
		accountOwner.setStreetAddress("123 Fourth Street");
		accountOwner.setCity("Gaithersburg");
		accountOwner.setState("Maryland");
		accountOwner.setZipCode("11111");
		return accountOwner;
	}

	/**
	 * Create an account owner
	 * 
	 * @return account owner that was created
	 */
	protected AccountOwner createAccountOwner(String socialSecurityNumber) {
		AccountOwnerService accountOwnerService = new AccountOwnerService();
		AccountOwner accountOwner = buildAccountOwner(socialSecurityNumber);
		accountOwnerService.saveOrUpdateAccountOwner(accountOwner);
		return accountOwner;
	}

	/**
	 * Delete an account owner
	 * 
	 * @param accountOwner
	 *            accountOwner to be deleted
	 */
	protected void deleteAccountOwner(AccountOwner accountOwner) {
//		EntityManager entityManager = null;
//		try {
//			entityManager = JPAUtil.getEntityManager();
//			entityManager.getTransaction().begin();
//			AccountOwnerService accountOwnerService = new AccountOwnerService();
//			accountOwnerService.deleteAccountOwner(accountOwner);
//			entityManager.getTransaction().commit();
//		} catch (HibernateException e) {
//			e.printStackTrace();
//			entityManager.getTransaction().rollback();
//			Assert.fail();
//		} finally {
//			entityManager.close();
//		}
	}

	/**
	 * Sleep for some time
	 * 
	 * @param milliseconds
	 *            time in ms to sleep
	 */
	protected void sleep(int milliseconds) {
		try {
			Thread.sleep(milliseconds);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
