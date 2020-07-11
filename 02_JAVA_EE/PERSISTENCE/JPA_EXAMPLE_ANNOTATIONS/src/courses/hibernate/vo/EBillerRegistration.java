package courses.hibernate.vo;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Domain object representing an EBillerRegistration
 */
@Entity
@Table(name = "ACCOUNT_EBILLER")
public class EBillerRegistration {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ACCOUNT_EBILLER_ID")
	private long ebillerRegistrationId;

	@Basic(optional = false)
	@Column(name = "ACCOUNT_NUMBER", updatable = false)
	private String accountNumber;

	@Basic(optional = false)
	@Column(name = "DATE_ADDED", updatable = false)
	private Date dateAdded;

	@ManyToOne
	@JoinColumn(name = "ACCOUNT_ID", updatable = false)
	private Account account;

	@ManyToOne
	@JoinColumn(name = "EBILLER_ID", updatable = false)
	private EBiller ebiller;

	@ManyToOne
	@JoinColumn(name = "ACCOUNT_OWNER_ID", updatable = false)
	private AccountOwner registeredBy;

	public EBillerRegistration() {
	}

	public EBillerRegistration(Account account, EBiller ebiller,
			AccountOwner registeredBy, Date dateAdded, String accountNumber) {
		setAccount(account);
		setEbiller(ebiller);
		setRegisteredBy(registeredBy);
		setAccountNumber(accountNumber);
		setDateAdded(dateAdded);
	}

	public long getEbillerRegistrationId() {
		return ebillerRegistrationId;
	}

	@SuppressWarnings("unused")
	private void setEbillerRegistrationId(long ebillerRegistrationId) {
		this.ebillerRegistrationId = ebillerRegistrationId;
	}

	public Account getAccount() {
		return account;
	}

	private void setAccount(Account account) {
		this.account = account;
		if (!account.getEbillerRegistrations().contains(this)) {
			account.addEBillerRegistration(this);
		}
	}

	public EBiller getEbiller() {
		return ebiller;
	}

	private void setEbiller(EBiller ebiller) {
		this.ebiller = ebiller;
		if (!ebiller.getEbillerRegistrations().contains(this)) {
			ebiller.addEBillerRegistration(this);
		}
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	private void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public Date getDateAdded() {
		return dateAdded;
	}

	private void setDateAdded(Date dateAdded) {
		this.dateAdded = dateAdded;
	}

	private void setRegisteredBy(AccountOwner registeredBy) {
		this.registeredBy = registeredBy;
	}

	public AccountOwner getRegisteredBy() {
		return registeredBy;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer(512);
		sb.append("\n----EBILLER REGISTRATION----\n");
		sb.append("accountEBillerId=" + ebillerRegistrationId + "\n");
		sb.append("account="
				+ ((account == null) ? "null" : account.getAccountId()) + "\n");
		sb.append("eBiller="
				+ ((ebiller == null) ? "null" : ebiller.getEbillerId()) + "\n");
		sb.append("registeredBy="
				+ ((registeredBy == null) ? "null" : registeredBy
						.getAccountOwnerId()) + "\n");
		sb.append("accountNumber=" + accountNumber + "\n");
		sb.append("dateAdded=" + dateAdded + "\n");
		sb.append("----EBILLER REGISTRATION----\n");
		return sb.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ (int) (ebillerRegistrationId ^ (ebillerRegistrationId >>> 32));
		result = prime * result
				+ ((accountNumber == null) ? 0 : accountNumber.hashCode());
		result = prime * result
				+ ((dateAdded == null) ? 0 : dateAdded.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EBillerRegistration other = (EBillerRegistration) obj;
		if (ebillerRegistrationId != other.ebillerRegistrationId)
			return false;
		if (accountNumber == null) {
			if (other.accountNumber != null)
				return false;
		} else if (!accountNumber.equals(other.accountNumber))
			return false;
		if (dateAdded == null) {
			if (other.dateAdded != null)
				return false;
		} else if (!dateAdded.equals(other.dateAdded))
			return false;
		return true;
	}

}
