package courses.hibernate.service;

import javax.persistence.EntityManager;

import junit.framework.Assert;

import org.hibernate.HibernateException;
import org.junit.AfterClass;
import org.junit.Test;

import courses.hibernate.util.JPAUtil;
import courses.hibernate.vo.Account;
import courses.hibernate.vo.AccountTransaction;
import courses.hibernate.vo.EBill;
import courses.hibernate.vo.EBiller;

/**
 * Service layer tests for ebills
 */
public class EBillServiceTest extends ServiceTest {

	/**
	 * Close EntityManagerFactory after all tests have been executed
	 */
	@AfterClass
	public static void closeEntityManagerFactory() {
		JPAUtil.getEntityManagerFactory().close();
	}

	/**
	 * Test creating of ebill
	 */
	@Test
	public void testCreateEBill() {
		EntityManager entityManager = null;
		EBill ebill = null;
		EBiller ebiller = null;

		try {
			entityManager = JPAUtil.getEntityManager();
			entityManager.getTransaction().begin();

			// create the eBill
			// ------ --- -----
			ebiller = createEBiller();
			ebill = buildEBill(ebiller, createCheckingAccount());

			// save the eBill
			// ---- --- -----
			EBillService ebillService = new EBillService();
			ebillService.saveOrUpdateEBill(ebill);

			entityManager.getTransaction().commit();
		} catch (HibernateException e) {
			e.printStackTrace();
			entityManager.getTransaction().rollback();
			Assert.fail();
		} finally {
			entityManager.close();
		}
		System.out.println("var ebill = " + ebill);

		// check that IDs were set after the hbm session
		// ----- ---- --- ---- --- ----- --- --- -------
		Assert.assertNotNull(ebill.getEbillId());

		// cleanup
		// -------
		deleteAccount(ebill.getAccount());
		deleteEBiller(ebiller);
	}

	/**
	 * Test payment of ebill by setting a new account transaction on ebill
	 */
	@Test
	public void testPayEBill() {
		EntityManager entityManager = null;
		EBill ebill = null;
		Account account = null;
		EBillService ebillService = new EBillService();

		try {
			entityManager = JPAUtil.getEntityManager();
			entityManager.getTransaction().begin();

			account = createCheckingAccount();
			ebill = createEBill(account);
			AccountTransaction accountTransaction = createAccountTransaction(
					account, ebill.getBalance());

			// Pay ebill by setting transaction on ebill
			// --- ----- -- ------- ----------- -- -----
			ebill.setAccountTransaction(accountTransaction);
			ebillService.saveOrUpdateEBill(ebill);

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

			// Validate that transaction has been persisted on ebill
			// -------- ---- ----------- --- ---- --------- -- -----
			ebill = ebillService.getEBill(ebill.getEbillId());
			Assert.assertNotNull(ebill.getAccountTransaction());

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
	 * Test payment of ebill by setting a new account transaction and have the
	 * account transaction be created as part of the cascade.
	 */
	@Test
	public void testPayBillWithCascade() {

		EntityManager entityManager = null;
		EBill ebill = null;
		Account account = null;
		EBillService ebillService = new EBillService();

		try {
			// Create EBill and build an Account Transaction and set it on
			// eBill. Don't explicitly create Account Transaction -- it is
			// automatically created when EBill is updated
			// ------------------------------------------------------------
			entityManager = JPAUtil.getEntityManager();
			entityManager.getTransaction().begin();

			account = createCheckingAccount();
			ebill = createEBill(account);
			AccountTransaction accountTransaction = buildAccountTransaction(
					account, ebill.getBalance());

			ebill.setAccountTransaction(accountTransaction);
			ebillService.saveOrUpdateEBill(ebill);

			entityManager.getTransaction().commit();
		} catch (HibernateException e) {
			e.printStackTrace();
			entityManager.getTransaction().rollback();
			Assert.fail();
		} finally {
			entityManager.close();
		}

		try {
			// Validate that transaction has been persisted on ebill
			// -------- ---- ----------- --- ---- --------- -- -----
			entityManager = JPAUtil.getEntityManager();
			entityManager.getTransaction().begin();

			ebill = ebillService.getEBill(ebill.getEbillId());
			Assert.assertNotNull(ebill.getAccountTransaction());

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
}
