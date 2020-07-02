package courses.hibernate.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import junit.framework.Assert;

import org.hibernate.HibernateException;
import org.junit.AfterClass;
import org.junit.Test;

import courses.hibernate.util.JPAUtil;
import courses.hibernate.vo.Account;
import courses.hibernate.vo.AccountOwner;
import courses.hibernate.vo.EBiller;
import courses.hibernate.vo.EBill;
import courses.hibernate.vo.EBillerRegistration;

/**
 * Service layer tests for ebillers
 */
public class EBillerServiceTest extends ServiceTest {

	/**
	 * Close EntityManagerFactory after all tests have been executed
	 */
	@AfterClass
	public static void closeEntityManagerFactory() {
		JPAUtil.getEntityManagerFactory().close();
	}

	/**
	 * Test creating of ebiller
	 */
	@Test
	public void testCreateEBiller() {
		EntityManager entityManager = null;
		EBiller eBiller = new EBiller();
		try {
			entityManager = JPAUtil.getEntityManager();
			entityManager.getTransaction().begin();
			eBiller = createEBiller();
			System.out.println(eBiller);
			entityManager.getTransaction().commit();
		} catch (HibernateException e) {
			e.printStackTrace();
			entityManager.getTransaction().rollback();
			Assert.fail();
		} finally {
			entityManager.close();
		}
		
		deleteEBiller(eBiller);
	}

	/**
	 * Test many to many relationship of account-ebillers. Create two ebillers
	 * and accounts and build associations. Ensure that retrieval of the
	 * ebillers and accounts have the appropriate relationships.
	 */
	@Test
	public void testManyToManyAccountEBillers() {
		EntityManager entityManager = null;
		AccountService accountService = new AccountService();
		AccountOwnerService accountOwnerService = new AccountOwnerService();
		EBillerService eBillerService = new EBillerService();
		EBiller ebiller1 = null;
		EBiller ebiller2 = null;
		Account account1 = null;
		Account account2 = null;
		EBillerRegistration ebillerRegistration1 = null;
		EBillerRegistration ebillerRegistration2 = null;
		EBillerRegistration ebillerRegistration3 = null;
		AccountOwner accountOwner = null;

		try {
			entityManager = JPAUtil.getEntityManager();
			entityManager.getTransaction().begin();

			// Create EBillers and Accounts
			// ------ -------- --- --------
			ebiller1 = createEBiller();
			ebiller2 = createEBiller();

			account1 = buildCheckingAccount();
			account2 = buildCheckingAccount();
			accountService.saveOrUpdateAccount(account1);
			accountService.saveOrUpdateAccount(account2);

			// Register ebiller with accounts and set registeredBy to
			// new account owner
			// ------------------------------------------------------
			accountOwner = buildAccountOwner("111-11-1111");
			accountOwnerService.saveOrUpdateAccountOwner(accountOwner);

			ebillerRegistration1 = accountService.registerEBiller(account1,
					ebiller1, accountOwner, "1111");
			ebillerRegistration2 = accountService.registerEBiller(account2,
					ebiller1, accountOwner, "1111");
			ebillerRegistration3 = accountService.registerEBiller(account2,
					ebiller2, accountOwner, "1111");

			entityManager.getTransaction().commit();
		} catch (HibernateException e) {
			e.printStackTrace();
			entityManager.getTransaction().rollback();
			Assert.fail();
		} finally {
			entityManager.close();
		}

		try {
			// Load EBiller and ensure counts of accounts are correct
			// ---- ------- --- ------ ------ -- -------- --- -------
			entityManager = JPAUtil.getEntityManager();
			entityManager.getTransaction().begin();

			ebiller1 = eBillerService.getEBiller(ebiller1.getEbillerId());
			ebiller2 = eBillerService.getEBiller(ebiller2.getEbillerId());
			Assert.assertEquals(2, ebiller1.getEbillerRegistrations().size());
			Assert.assertEquals(1, ebiller2.getEbillerRegistrations().size());

			// Load Accounts and ensure counts of ebillers are correct
			// ---- -------- --- ------ ------ -- -------- --- -------
			account1 = accountService.getAccount(account1.getAccountId());
			account2 = accountService.getAccount(account2.getAccountId());
			Assert.assertEquals(1, account1.getEbillerRegistrations().size());
			Assert.assertEquals(2, account2.getEbillerRegistrations().size());

			entityManager.getTransaction().commit();
		} catch (HibernateException e) {
			e.printStackTrace();
			entityManager.getTransaction().rollback();
			Assert.fail();
		} finally {
			entityManager.close();
		}

		try {
			// Unregister ebillers with accounts
			// ---------- -------- ---- --------
			entityManager = JPAUtil.getEntityManager();
			entityManager.getTransaction().begin();

			accountService.unregisterEBiller(ebillerRegistration1);
			accountService.unregisterEBiller(ebillerRegistration2);
			accountService.unregisterEBiller(ebillerRegistration3);

			entityManager.getTransaction().commit();
		} catch (HibernateException e) {
			e.printStackTrace();
			entityManager.getTransaction().rollback();
			Assert.fail();
		} finally {
			entityManager.close();
		}

		try {
			// Load EBiller and ensure counts of accounts are correct
			// ---- ------- --- ------ ------ -- -------- --- -------
			entityManager = JPAUtil.getEntityManager();
			entityManager.getTransaction().begin();

			ebiller1 = eBillerService.getEBiller(ebiller1.getEbillerId());
			ebiller2 = eBillerService.getEBiller(ebiller2.getEbillerId());
			Assert.assertEquals(0, ebiller1.getEbillerRegistrations().size());
			Assert.assertEquals(0, ebiller2.getEbillerRegistrations().size());

			// Load Accounts and ensure counts of ebillers are correct
			// ---- -------- --- ------ ------ -- -------- --- -------
			account1 = accountService.getAccount(account1.getAccountId());
			account2 = accountService.getAccount(account2.getAccountId());
			Assert.assertEquals(0, account1.getEbillerRegistrations().size());
			Assert.assertEquals(0, account2.getEbillerRegistrations().size());

			System.out.println(ebiller1);
			System.out.println(ebiller2);
			System.out.println(account1);
			System.out.println(account2);
			System.out.println(ebillerRegistration1);
			System.out.println(ebillerRegistration2);
			System.out.println(ebillerRegistration3);

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
		deleteAccountOwner(accountOwner);
		deleteAccount(account1);
		deleteAccount(account2);
		deleteEBiller(ebiller1);
		deleteEBiller(ebiller2);
	}

	/**
	 * Test creation of multiple ebills for an ebiller. Ensure that the ebill
	 * collection in ebiller has the correct number of ebills. Also, ensure that
	 * the list of balances loaded by the collection mapping is correct.
	 */
	@Test
	public void testCreateMultipleEBills() {

		EntityManager entityManager = null;
		EBillService ebillService = new EBillService();
		List<EBill> ebills = new ArrayList<EBill>();
		EBiller ebiller = null;
		long ebillerId = 0;

		try {
			entityManager = JPAUtil.getEntityManager();
			entityManager.getTransaction().begin();

			// Create an ebiller
			// ------ -- -------
			ebiller = createEBiller();

			// Create 10 ebills for ebiller. Because this is a set, need
			// some time between the ebills to set a different due date,
			// hence the sleep
			// ---------------------------------------------------------
			for (int i = 0; i < 10; i++) {
				EBill ebill = buildEBill(ebiller, createCheckingAccount());
				ebills.add(ebill);
				ebillService.saveOrUpdateEBill(ebill);
				sleep(100);
			}
			ebillerId = ebiller.getEbillerId();
			entityManager.getTransaction().commit();
		} catch (HibernateException e) {
			e.printStackTrace();
			entityManager.getTransaction().rollback();
			Assert.fail();
		} finally {
			entityManager.close();
		}

		try {
			// Retrieve the persisted ebiller and ensure that the
			// ebiller contains the 10 ebills
			// ---------------------------------------------------
			entityManager = JPAUtil.getEntityManager();
			entityManager.getTransaction().begin();

			EBillerService eBillerService = new EBillerService();

			ebiller = eBillerService.getEBiller(ebillerId);
			System.out.println(ebiller);
			Assert.assertEquals(10, ebiller.getEbills().size());

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
		for (EBill ebill : ebills) {
			// deleteEBill(ebill);
			deleteAccount(ebill.getAccount());
		}
		deleteEBiller(ebiller);
	}
}