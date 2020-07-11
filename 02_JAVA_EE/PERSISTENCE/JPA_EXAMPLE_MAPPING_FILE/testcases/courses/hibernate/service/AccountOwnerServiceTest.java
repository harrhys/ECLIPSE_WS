package courses.hibernate.service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import org.hibernate.HibernateException;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;

import courses.hibernate.util.JPAUtil;
import courses.hibernate.vo.AccountOwner;

/**
 * Service layer tests for AccountOwner
 */
public class AccountOwnerServiceTest extends ServiceTest {

	/**
	 * Close EntityManagerFactory after all tests have been executed
	 */
	@AfterClass
	public static void closeEntityManagerFactory() {
		JPAUtil.getEntityManagerFactory().close();
	}
	
	/**
	 * Test adding of account owners to account using load method and proxies
	 */
	@Test
	public void testTransactionRollbackAgainstSSNUniqueKey() {
		AccountOwner accountOwner1 = null;
		AccountOwner accountOwner2 = null;

		EntityManager entityManager = null;
		AccountOwnerService accountOwnerService = new AccountOwnerService();

		long accountOwner1Id = 0;
		long accountOwner2Id = 0;
		try {
			// Create two account owners with the same ssn - should result
			// in database unique key violation, which will rollback the
			// transaction. So, neither account owner should be persisted
			// in the database.
			// -----------------------------------------------------------
			entityManager = JPAUtil.getEntityManager();
			entityManager.getTransaction().begin();

			accountOwner1 = createAccountOwner("111-11-1111");
			accountOwner1Id = accountOwner1.getAccountOwnerId();
			accountOwner2 = createAccountOwner("111-11-1111");
			accountOwner2Id = accountOwner2.getAccountOwnerId();

			entityManager.getTransaction().commit();
			Assert.fail();

		} catch (PersistenceException e) {
			e.printStackTrace();
			// this should result in exception
		} finally {
			entityManager.close();
		}
		
		try {
			// Validate that neither account owner was persisted in the
			// database.
			// --------------------------------------------------------
			entityManager = JPAUtil.getEntityManager();
			entityManager.getTransaction().begin();

			accountOwner1 = accountOwnerService
					.getAccountOwner(accountOwner1Id);
			Assert.assertNull(accountOwner1);
			accountOwner2 = accountOwnerService
					.getAccountOwner(accountOwner2Id);
			Assert.assertNull(accountOwner2);

			entityManager.getTransaction().commit();
		} catch (HibernateException e) {
			e.printStackTrace();
			entityManager.getTransaction().rollback();
			Assert.fail();
		} finally {
			entityManager.close();
		}
	}
}
