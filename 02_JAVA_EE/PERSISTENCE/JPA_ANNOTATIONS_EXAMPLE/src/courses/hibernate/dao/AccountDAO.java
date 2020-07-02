package courses.hibernate.dao;

import java.util.List;

import javax.persistence.EntityManager;

import courses.hibernate.util.JPAUtil;
import courses.hibernate.vo.Account;
import courses.hibernate.vo.CdAccount;
import courses.hibernate.vo.CheckingAccount;
import courses.hibernate.vo.EBillerRegistration;
import courses.hibernate.vo.SavingsAccount;

/**
 * Data Access Object for Account
 */
public class AccountDAO {

	/**
	 * Create a new account or update an existing one
	 * 
	 * @param account
	 *            account to be persisted
	 */
	public void saveOrUpdateAccount(Account account) {
		EntityManager manager = JPAUtil.getEntityManager();
		manager.persist(account);
	}

	/**
	 * Retrieve an account from the data store
	 * 
	 * @param accountId
	 *            identifier of the account to be retrieved
	 * @return account represented by the identifier provided
	 */
	public Account getAccount(long accountId) {
		EntityManager manager = JPAUtil.getEntityManager();
		Account account = (Account) manager.find(Account.class, accountId);
		return account;
	}

	/**
	 * Get all accounts in system
	 */
	@SuppressWarnings("unchecked")
	public List<Account> getAccounts() {
		EntityManager manager = JPAUtil.getEntityManager();
		return (List<Account>) manager.createQuery("select a from Account a")
				.getResultList();
	}

	/**
	 * Get all checking accounts in system
	 */
	@SuppressWarnings("unchecked")
	public List<CheckingAccount> getCheckingAccounts() {
		EntityManager manager = JPAUtil.getEntityManager();
		return (List<CheckingAccount>) manager.createQuery("select a from CheckingAccount a")
				.getResultList();
	}

	/**
	 * Get all savings accounts in system
	 */
	@SuppressWarnings("unchecked")
	public List<SavingsAccount> getSavingsAccounts() {
		EntityManager manager = JPAUtil.getEntityManager();
		return (List<SavingsAccount>) manager.createQuery("select a from SavingsAccount a")
				.getResultList();
	}

	/**
	 * Get all CD accounts in system
	 */
	@SuppressWarnings("unchecked")
	public List<CdAccount> getCdAccounts() {
		EntityManager manager = JPAUtil.getEntityManager();
		return (List<CdAccount>) manager.createQuery("select a from CdAccount a")
				.getResultList();	}

	/**
	 * Delete account from data store
	 * 
	 * @param account
	 *            account to be deleted
	 */
	public void deleteAccount(Account account) {
		EntityManager manager = JPAUtil.getEntityManager();
		account = manager.getReference(Account.class, account.getAccountId());
		manager.remove(account);
	}

	/**
	 * Save an ebillerRegistraton (ebiller-account relationship)
	 * 
	 * @param ebillerRegistration
	 *            ebillerRegistration to be saved
	 */
	public void registerEBiller(EBillerRegistration ebillerRegistration) {
		EntityManager manager = JPAUtil.getEntityManager();
		manager.persist(ebillerRegistration);
	}

	/**
	 * Delete an ebillerRegistration (ebiller-account relationship)
	 * 
	 * @param ebillerRegistration
	 *            ebillerRegistration to be deleted
	 */
	public void unregisterEBiller(EBillerRegistration ebillerRegistration) {
		EntityManager manager = JPAUtil.getEntityManager();
		ebillerRegistration = manager.getReference(EBillerRegistration.class,
				ebillerRegistration.getEbillerRegistrationId());
		manager.remove(ebillerRegistration);
	}
}
