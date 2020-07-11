package courses.hibernate.service;

import courses.hibernate.dao.AccountOwnerDAO;
import courses.hibernate.vo.AccountOwner;

/**
 * Service layer for Account Owner
 */
public class AccountOwnerService {
	AccountOwnerDAO accountOwnerDAO = new AccountOwnerDAO();

	/**
	 * Create a new account owner or update an existing one
	 * 
	 * @param accountOwner
	 *            account owner to be persisted
	 */
	public void saveOrUpdateAccountOwner(AccountOwner accountOwner) {
		accountOwnerDAO.saveOrUpdateAccountOwner(accountOwner);
	}

	/**
	 * Retrieve an account owner
	 * 
	 * @param accountOwnerId
	 *            identifier of the account owner to be retrieved
	 * @return accountOwner represented by the identifier provided
	 */
	public AccountOwner getAccountOwner(long accountOwnerId) {
		return accountOwnerDAO.getAccountOwner(accountOwnerId);
	}

	/**
	 * Delete account owner
	 * 
	 * @param accountOwner
	 *            accountOwner to be deleted
	 */
	public void deleteAccountOwner(AccountOwner accountOwner) {
		accountOwnerDAO.deleteAccountOwner(accountOwner);
	}
}
