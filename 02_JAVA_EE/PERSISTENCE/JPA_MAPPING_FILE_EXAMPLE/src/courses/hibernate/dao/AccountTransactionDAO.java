package courses.hibernate.dao;

import javax.persistence.EntityManager;

import courses.hibernate.util.JPAUtil;
import courses.hibernate.vo.AccountTransaction;

/**
 * Data Access Object for Account Transaction
 */
public class AccountTransactionDAO {

	/**
	 * Create a new account transaction or update an existing one
	 * 
	 * @param accountTransaction
	 *            account transaction to be persisted
	 */
	public void saveOrUpdateAccountTransaction(
			AccountTransaction accountTransaction) {
		EntityManager manager = JPAUtil.getEntityManager();
		manager.persist(accountTransaction);
	}

	/**
	 * Retrieve an account transaction from the data store
	 * 
	 * @param accountTransactionId
	 *            identifier of the account transaction to be retrieved
	 * @return accountTransaction represented by the identifier provided
	 */
	public AccountTransaction getAccountTransaction(long accountTransactionId) {
		EntityManager manager = JPAUtil.getEntityManager();
		return (AccountTransaction) manager.find(AccountTransaction.class,
				accountTransactionId);
	}
}
