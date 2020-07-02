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
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Domain object representing an Account Transaction
 */
@Entity
@Table(name = "ACCOUNT_TRANSACTION")
public class AccountTransaction implements Comparable<AccountTransaction> {
	public static final String TRANSACTION_TYPE_DEBIT = "DEBIT";
	public static final String TRANSACTION_TYPE_CREDIT = "CREDIT";

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ACCOUNT_TRANSACTION_ID")
	private long accountTransactionId;

	@Basic(optional = false)
	@Column(name = "TRANSACTION_TYPE")
	private String transactionType;

	@Basic(optional = false)
	@Column(name = "TXDATE")
	private Date transactionDate;

	@Basic(optional = false)
	@Column(name = "AMOUNT")
	private double amount;

	@ManyToOne
	@JoinColumn(name = "ACCOUNT_ID")
	private Account account;

	@OneToOne(mappedBy = "accountTransaction")
	@JoinColumn(name = "EBILL_ID")
	private EBill ebill;

	/**
	 * Get accountTransactionId
	 * 
	 * @return accountTransactionId
	 */
	public long getAccountTransactionId() {
		return accountTransactionId;
	}

	/**
	 * Set accountTransactionId
	 * 
	 * @param accountTransactionId
	 */
	public void setAccountTransactionId(long accountTransactionId) {
		this.accountTransactionId = accountTransactionId;
	}

	/**
	 * Get transactionType
	 * 
	 * @return transactionType
	 */
	public String getTransactionType() {
		return transactionType;
	}

	/**
	 * Set transactionType
	 * 
	 * @param transactionType
	 */
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	/**
	 * Get account
	 * 
	 * @return account
	 */
	public Account getAccount() {
		return account;
	}

	/**
	 * Set account. Maintain both sides of bidirectional relationship.
	 * 
	 * @param account
	 */
	public void setAccount(Account account) {
		this.account = account;
		if (account != null && !account.getAccountTransactions().contains(this)) {
			account.addAccountTransaction(this);
		}
	}

	/**
	 * Get transactionDate
	 * 
	 * @return transactionDate
	 */
	public Date getTransactionDate() {
		return transactionDate;
	}

	/**
	 * Set transactionDate
	 * 
	 * @param transactionDate
	 */
	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

	/**
	 * Get amount
	 * 
	 * @return amount
	 */
	public double getAmount() {
		return amount;
	}

	/**
	 * Set amount
	 * 
	 * @param amount
	 */
	public void setAmount(double amount) {
		this.amount = amount;
	}

	/**
	 * Get ebill
	 * 
	 * @return ebill
	 */
	public EBill getEbill() {
		return ebill;
	}

	/**
	 * Set ebill. Maintain both sides of bidirectional relationship.
	 * 
	 * @param ebill
	 */
	public void setEbill(EBill ebill) {
		this.ebill = ebill;
		if (ebill != null
				&& (ebill.getAccountTransaction() == null || !ebill
						.getAccountTransaction().equals(this))) {
			ebill.setAccountTransaction(this);
		}
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer(512);
		sb.append("\n----ACCOUNT TRANSACTION----\n");
		sb.append("accountTransactionId=" + accountTransactionId + "\n");
		sb.append("transactionType=" + transactionType + "\n");
		sb.append("transactionDate=" + transactionDate + "\n");
		sb.append("amount=" + amount + "\n");
		sb.append("account="
				+ ((account == null) ? "null" : account.getAccountId()) + "\n");
		sb.append((ebill == null) ? "null" : ebill.getEbillId());
		sb.append("----ACCOUNT TRANSACTION----\n");
		return sb.toString();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ (int) (accountTransactionId ^ (accountTransactionId >>> 32));
		long temp;
		temp = Double.doubleToLongBits(amount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result
				+ ((transactionDate == null) ? 0 : transactionDate.hashCode());
		result = prime * result
				+ ((transactionType == null) ? 0 : transactionType.hashCode());
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
		if (!(obj instanceof AccountTransaction))
			return false;
		AccountTransaction other = (AccountTransaction) obj;
		if (accountTransactionId != other.accountTransactionId)
			return false;
		if (Double.doubleToLongBits(amount) != Double
				.doubleToLongBits(other.amount))
			return false;
		if (transactionDate == null) {
			if (other.transactionDate != null)
				return false;
		} else if (!transactionDate.equals(other.transactionDate))
			return false;
		if (transactionType == null) {
			if (other.transactionType != null)
				return false;
		} else if (!transactionType.equals(other.transactionType))
			return false;
		return true;
	}

	/**
	 * @see java.util.Comparator#compare(Object)
	 */
	@Override
	public int compareTo(AccountTransaction accountTransactionToCompare) {
		return (this.getTransactionDate().compareTo(accountTransactionToCompare
				.getTransactionDate()));
	}
}
