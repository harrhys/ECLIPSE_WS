package courses.hibernate.dao;

import javax.persistence.EntityManager;

import courses.hibernate.util.JPAUtil;
import courses.hibernate.vo.AccountOwner;

/**
 * Data Access Object for Account Owner
 */
public class AccountOwnerDAO {

	/**
	 * Create a new account owner or update an existing one
	 * 
	 * @param accountOwner
	 *            account owner to be persisted
	 */
	public void saveOrUpdateAccountOwner(AccountOwner accountOwner) {
		EntityManager manager = JPAUtil.getEntityManager();
		manager.persist(accountOwner);
	}

	/**
	 * Retrieve an account owner from the data store
	 * 
	 * @param accountOwnerId
	 *            identifier of the account owner to be retrieved
	 * @return accountOwner represented by the identifier provided
	 */
	public AccountOwner getAccountOwner(long accountOwnerId) {
		EntityManager manager = JPAUtil.getEntityManager();
		AccountOwner accountOwner = (AccountOwner) manager.find(
				AccountOwner.class, accountOwnerId);
		return accountOwner;
	}

	/**
	 * Delete account owner from data store
	 * 
	 * @param accountOwner
	 *            accountOwner to be deleted
	 */
	public void deleteAccountOwner(AccountOwner accountOwner) {
		EntityManager manager = JPAUtil.getEntityManager();
		accountOwner = manager.getReference(AccountOwner.class, accountOwner
				.getAccountOwnerId());
		manager.remove(accountOwner);
	}
}
