package courses.hibernate.vo;

import java.util.Date;

/**
 * Domain object that represents an EBill
 */
public class EBill implements Comparable<EBill> {
	private long ebillId;
	private double minimumAmountDue;
	private double balance;
	private double amountPaid;
	private Date datePaid;
	private Date receivedDate;
	private Date dueDate;
	private AccountTransaction accountTransaction;
	private EBiller ebiller;
	private Account account;

	/**
	 * Public no argument constructor required by Hibernate
	 */
	public EBill() {

	}

	/**
	 * Constructor to be used by application
	 * 
	 * @param receivedDate
	 * @param dueDate
	 * @param ebiller
	 * @param minimumAmountDue
	 * @param balance
	 */
	public EBill(Date receivedDate, Date dueDate, EBiller ebiller,
			Account account, double minimumAmountDue, double balance) {
		setReceivedDate(receivedDate);
		setDueDate(dueDate);
		setEbiller(ebiller);
		setMinimumAmountDue(minimumAmountDue);
		setBalance(balance);
		setAccount(account);
	}

	/**
	 * Get minimumAmountDue
	 * 
	 * @return minimumAmountDue
	 */
	public double getMinimumAmountDue() {
		return minimumAmountDue;
	}

	/**
	 * Set minimumAmountDue
	 * 
	 * @param minimumAmountDue
	 */
	protected void setMinimumAmountDue(double minimumAmountDue) {
		this.minimumAmountDue = minimumAmountDue;
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
	protected void setBalance(double balance) {
		this.balance = balance;
	}

	/**
	 * Get amountPaid
	 * 
	 * @return amountPaid
	 */
	public double getAmountPaid() {
		return amountPaid;
	}

	/**
	 * Set amountPaid
	 * 
	 * @param amountPaid
	 */
	private void setAmountPaid(double amountPaid) {
		this.amountPaid = amountPaid;
	}

	/**
	 * Get datePaid
	 * 
	 * @return
	 */
	public Date getDatePaid() {
		return datePaid;
	}

	/**
	 * Set datePaid
	 * 
	 * @param datePaid
	 */
	private void setDatePaid(Date datePaid) {
		this.datePaid = datePaid;
	}

	/**
	 * Get dueDate
	 * 
	 * @return
	 */
	public Date getDueDate() {
		return dueDate;
	}

	/**
	 * Set dueDate
	 * 
	 * @param dueDate
	 */
	private void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	/**
	 * Pay Ebill. Should not be used by hibernate to set account transaction on
	 * object load. Set access=field on attribute.
	 * 
	 * @param accountTransaction
	 *            account transaction used to pay ebill
	 */
	public void setAccountTransaction(AccountTransaction accountTransaction) {
		this.accountTransaction = accountTransaction;
		this.setAmountPaid(accountTransaction.getAmount());
		this.setDatePaid(new Date());
		this.setBalance(this.getBalance() - this.getAmountPaid());

		if (accountTransaction != null
				&& (accountTransaction.getEbill() == null || !accountTransaction
						.getEbill().equals(this))) {
			accountTransaction.setEbill(this);
		}
	}

	/**
	 * Get accountTransaction
	 * 
	 * @return accountTransaction
	 */
	public AccountTransaction getAccountTransaction() {
		return accountTransaction;
	}

	/**
	 * Get ebiller
	 * 
	 * @return ebiller
	 */
	public EBiller getEbiller() {
		return ebiller;
	}

	/**
	 * Set ebiller. Maintain both sides of bidirectional relationship.
	 * 
	 * @param ebiller
	 */
	protected void setEbiller(EBiller ebiller) {
		this.ebiller = ebiller;
		if (ebiller != null && !ebiller.getEbills().contains(this)) {
			ebiller.addEbill(this);
		}
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
	protected void setAccount(Account account) {
		this.account = account;
		if (account != null && !account.getEbills().contains(this)) {
			account.addEbill(this);
		}
	}

	/**
	 * Get receivedDate
	 * 
	 * @return receivedDate
	 */
	public Date getReceivedDate() {
		return receivedDate;
	}

	/**
	 * Set receivedDate
	 * 
	 * @param receivedDate
	 */
	protected void setReceivedDate(Date receivedDate) {
		this.receivedDate = receivedDate;
	}

	/**
	 * @return the ebillId
	 */
	public long getEbillId() {
		return ebillId;
	}

	/**
	 * @param ebillId
	 *            the ebillId to set
	 */
	protected void setEbillId(long ebillId) {
		this.ebillId = ebillId;
	}

	/**
	 * @see java.util.Comparator#compare(Object)
	 */
	public int compareTo(EBill ebillToCompare) {
		return (this.dueDate.compareTo(ebillToCompare.getDueDate()));
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer(512);
		sb.append("\n----EBILL----\n");
		sb.append("ebillId=" + ebillId + "\n");
		sb.append("minimumAmountDue=" + minimumAmountDue + "\n");
		sb.append("balance=" + balance + "\n");
		sb.append("amountPaid=" + amountPaid + "\n");
		sb.append("datePaid=" + datePaid + "\n");
		sb.append("receivedDate=" + receivedDate + "\n");
		sb.append("eBiller="
				+ ((ebiller == null) ? "null" : ebiller.getEbillerId()) + "\n");
		sb.append("accountTransaction="
				+ ((accountTransaction == null) ? "null" : accountTransaction
						.getAccountTransactionId()) + "\n");
		sb.append("----EBILL----\n");
		return sb.toString();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(amountPaid);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(balance);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result
				+ ((datePaid == null) ? 0 : datePaid.hashCode());
		result = prime * result + (int) (ebillId ^ (ebillId >>> 32));		
		temp = Double.doubleToLongBits(minimumAmountDue);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result
				+ ((receivedDate == null) ? 0 : receivedDate.hashCode());
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
		if (!(obj instanceof EBill))
			return false;
		EBill other = (EBill) obj;
		if (Double.doubleToLongBits(amountPaid) != Double
				.doubleToLongBits(other.amountPaid))
			return false;
		if (Double.doubleToLongBits(balance) != Double
				.doubleToLongBits(other.balance))
			return false;
		if (datePaid == null) {
			if (other.datePaid != null)
				return false;
		} else if (!datePaid.equals(other.datePaid))
			return false;
		if (ebillId != other.ebillId) {
				return false;
		} 
		if (Double.doubleToLongBits(minimumAmountDue) != Double
				.doubleToLongBits(other.minimumAmountDue))
			return false;
		if (receivedDate == null) {
			if (other.receivedDate != null)
				return false;
		} else if (!receivedDate.equals(other.receivedDate))
			return false;
		return true;
	}
}
