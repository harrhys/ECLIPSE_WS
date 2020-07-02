package courses.hibernate.vo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;

/**
 * Domain object representing an Account
 */
@Entity
@Table(name = "ACCOUNT")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "ACCOUNT_TYPE", discriminatorType = DiscriminatorType.STRING)
public abstract class Account {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ACCOUNT_ID")
	private long accountId;

	@Basic(optional = false)
	@Column(name = "CREATION_DATE", updatable = false)
	private Date creationDate;

	@Basic(optional = false)
	@Column(name = "BALANCE")
	private double balance;

	@OneToMany(mappedBy = "account", cascade = CascadeType.REMOVE)
	@JoinColumn(name = "ACCOUNT_ID")
	private Collection<EBillerRegistration> ebillerRegistrations = new ArrayList<EBillerRegistration>();

	@OneToMany(mappedBy = "account", cascade = CascadeType.REMOVE)
	@JoinColumn(name = "ACCOUNT_ID")
	private Set<EBill> ebills = new HashSet<EBill>();

	@OneToMany(mappedBy = "account", cascade = CascadeType.REMOVE)
	@JoinColumn(name = "ACCOUNT_ID")
	private Set<AccountTransaction> accountTransactions = new HashSet<AccountTransaction>();

	@ManyToMany
	@JoinTable(name = "ACCOUNT_ACCOUNT_OWNER", joinColumns = { @JoinColumn(name = "ACCOUNT_ID") }, inverseJoinColumns = { @JoinColumn(name = "ACCOUNT_OWNER_ID") })
	private Collection<AccountOwner> accountOwners = new ArrayList<AccountOwner>();

	@Version
	@Column(name = "VERSION")
	private long version;

	/**
	 * Get accountId
	 * 
	 * @return accountId
	 */
	public long getAccountId() {
		return accountId;
	}

	/**
	 * Set accountId
	 * 
	 * @param accountId
	 */
	@SuppressWarnings("unused")
	private void setAccountId(long accountId) {
		this.accountId = accountId;
	}

	/**
	 * Get creationDate
	 * 
	 * @return creationDate
	 */
	public Date getCreationDate() {
		return creationDate;
	}

	/**
	 * Set creationDate
	 * 
	 * @param creationDate
	 */
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	/**
	 * Get balance
	 * 
	 * @return balance
	 */
	public double getBalance() {
		return balance;
	}

	/**
	 * Set balance
	 * 
	 * @param balance
	 */
	public void setBalance(double balance) {
		this.balance = balance;
	}

	/**
	 * Get ebillerRegistrations
	 * 
	 * @return ebillerRegistrations
	 */
	public Collection<EBillerRegistration> getEbillerRegistrations() {
		return ebillerRegistrations;
	}

	/**
	 * Set ebillerRegistrations
	 * 
	 * @param ebillerRegistrations
	 */
	protected void setEbillerRegistrations(
			Collection<EBillerRegistration> ebillerRegistrations) {
		this.ebillerRegistrations = ebillerRegistrations;
	}

	/**
	 * Add ebillerRegistration to ebillerRegistrations
	 * 
	 * @param ebillerRegistration
	 *            ebillerRegistration to add
	 */
	public void addEBillerRegistration(EBillerRegistration ebillerRegistration) {
		this.ebillerRegistrations.add(ebillerRegistration);
	}

	/**
	 * Remove ebillerRegistration from ebillerRegistrations
	 * 
	 * @param ebillerRegistration
	 *            ebillerRegistration to remove
	 */
	public void removeEBillerRegistration(
			EBillerRegistration ebillerRegistration) {
		this.ebillerRegistrations.remove(ebillerRegistration);
	}

	/**
	 * Get accountOwners
	 * 
	 * @return accountOwners
	 */
	public Collection<AccountOwner> getAccountOwners() {
		return accountOwners;
	}

	/**
	 * Set accountOwners
	 * 
	 * @param accountOwners
	 */
	protected void setAccountOwners(Collection<AccountOwner> accountOwners) {
		this.accountOwners = accountOwners;
	}

	/**
	 * Add accountOwner to accountOwners. Maintain both sides of bidirectional
	 * relationship.
	 * 
	 * @param accountOwner
	 *            accountOwner to be added
	 */
	public void addAccountOwner(AccountOwner accountOwner) {
		this.accountOwners.add(accountOwner);
		if (!accountOwner.getAccounts().contains(this)) {
			accountOwner.addAccount(this);
		}
	}

	/**
	 * Remove accountOwner from accountOwners. Maintain both sides of
	 * bidirectional relationship.
	 * 
	 * @param accountOwner
	 *            accountOwner to be removed
	 */
	public void removeAccountOwner(AccountOwner accountOwner) {
		this.accountOwners.remove(accountOwner);
		if (accountOwner.getAccounts().contains(this)) {
			accountOwner.removeAccount(this);
		}
	}

	/**
	 * Get ebills
	 * 
	 * @return ebills
	 */
	public Set<EBill> getEbills() {
		return ebills;
	}

	/**
	 * Set ebills
	 * 
	 * @param ebills
	 */
	protected void setEbills(Set<EBill> ebills) {
		this.ebills = ebills;
	}

	/**
	 * Add ebill to ebills. Maintain both sides of bidirectional relationship.
	 * 
	 * @param ebill
	 *            to be added
	 */
	public void addEbill(EBill ebill) {
		ebills.add(ebill);
		if (!ebill.getAccount().equals(this)) {
			ebill.getAccount().getEbills().remove(ebill);
			ebill.setAccount(this);
		}
	}

	/**
	 * Remove ebill from ebills. Maintain both sides of bidirectional
	 * relationship.
	 * 
	 * @param ebill
	 *            ebill to be removed
	 */
	public void removeEbill(EBill ebill) {
		ebills.remove(ebill);
		if (ebill.getAccount().equals(this)) {
			ebill.setAccount(null);
		}
	}

	/**
	 * Get accountTransactions
	 * 
	 * @return accountTransactions
	 */
	public Set<AccountTransaction> getAccountTransactions() {
		return accountTransactions;
	}

	/**
	 * Set accountTransactions
	 * 
	 * @param accountTransactions
	 */
	protected void setAccountTransactions(
			Set<AccountTransaction> accountTransactions) {
		this.accountTransactions = accountTransactions;
	}

	/**
	 * Add accountTransaction to accountTransactions and update account balance.
	 * Maintain both sides of bidirectional relationship.
	 * 
	 * @param accountTransaction
	 *            to be added
	 */
	public void addAccountTransaction(AccountTransaction accountTransaction) {
		accountTransactions.add(accountTransaction);
		if (accountTransaction.getTransactionType().equals(
				AccountTransaction.TRANSACTION_TYPE_CREDIT)) {
			this.setBalance(balance + accountTransaction.getAmount());
		} else if (accountTransaction.getTransactionType().equals(
				AccountTransaction.TRANSACTION_TYPE_DEBIT)) {
			this.setBalance(balance - accountTransaction.getAmount());
		}
		if (!accountTransaction.getAccount().equals(this)) {
			accountTransaction.getAccount().getAccountTransactions().remove(
					accountTransaction);
			accountTransaction.setAccount(this);
		}
	}

	/**
	 * Remove accountTransaction from accountTransactions. Maintain both sides
	 * of bidirectional relationship.
	 * 
	 * @param accountTransaction
	 *            accountTransaction to be removed
	 */
	public void removeAccountTransaction(AccountTransaction accountTransaction) {
		accountTransactions.remove(accountTransaction);
		if (accountTransaction.getAccount().equals(this)) {
			accountTransaction.setAccount(null);
		}
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer(512);
		sb.append("\n----ACCOUNT----\n");
		sb.append("accountId=" + accountId + "\n");
		sb.append("creationDate=" + creationDate + "\n");
		sb.append("balance=" + balance + "\n");
		sb.append("version=" + version + "\n");

		if (ebillerRegistrations != null
				&& ebillerRegistrations.isEmpty() == false) {
			sb.append("ebillerRegistrations=");
			for (EBillerRegistration ebillerRegistration : ebillerRegistrations) {
				sb.append((ebillerRegistration == null) ? "null"
						: ebillerRegistration.getEbillerRegistrationId());
				sb.append(",");
			}
			sb.deleteCharAt(sb.length() - 1);
			sb.append("\n");
		}

		if (accountOwners != null && accountOwners.isEmpty() == false) {
			sb.append("accountOwners=");
			for (AccountOwner accountOwner : accountOwners) {
				sb.append((accountOwner == null) ? "null" : accountOwner
						.getAccountOwnerId());
				sb.append(",");
			}
			sb.deleteCharAt(sb.length() - 1);
			sb.append("\n");
		}

		if (ebills != null && ebills.isEmpty() == false) {
			sb.append("ebills=");
			for (EBill ebill : ebills) {
				sb.append((ebill == null) ? "null" : ebill.getEbillId());
				sb.append(",");
			}
			sb.deleteCharAt(sb.length() - 1);
			sb.append("\n");
		}

		if (accountTransactions != null
				&& accountTransactions.isEmpty() == false) {
			sb.append("accountTransactions=");
			for (AccountTransaction accountTransaction : accountTransactions) {
				sb.append((accountTransactions == null) ? "null"
						: accountTransaction.getAccountTransactionId());
				sb.append(",");
			}
			sb.deleteCharAt(sb.length() - 1);
			sb.append("\n");
		}
		sb.append("----ACCOUNT----\n");
		return sb.toString();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (accountId ^ (accountId >>> 32));
		long temp;
		temp = Double.doubleToLongBits(balance);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result
				+ ((creationDate == null) ? 0 : creationDate.hashCode());
		return result;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Account))
			return false;
		Account other = (Account) obj;
		if (accountId != other.accountId)
			return false;
		if (Double.doubleToLongBits(balance) != Double
				.doubleToLongBits(other.balance))
			return false;
		if (creationDate == null) {
			if (other.creationDate != null)
				return false;
		} else if (!creationDate.equals(other.creationDate))
			return false;
		return true;
	}
}
