package courses.hibernate.vo;

import java.util.Date;

/**
 * Domain object representing a Savings Account
 */
public class CdAccount extends Account {
	private double interestRate;
	private Date maturityDate;
	
	/**
	 * @return the maturityDate
	 */
	public Date getMaturityDate() {
		return maturityDate;
	}

	/**
	 * @param maturityDate the maturityDate to set
	 */
	public void setMaturityDate(Date maturityDate) {
		this.maturityDate = maturityDate;
	}

	/**
	 * Get interestRate
	 * 
	 * @return interestRate
	 */
	public double getInterestRate() {
		return interestRate;
	}

	/**
	 * Set interestRate
	 * 
	 * @param interestRate
	 */
	public void setInterestRate(double interestRate) {
		this.interestRate = interestRate;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer(512);
		sb.append("\n----CD ACCOUNT----\n");
		sb.append(super.toString());
		sb.append("interestRate=" + interestRate + "\n");
		sb.append("maturityDate=" + maturityDate + "\n");
		sb.append("----CD ACCOUNT----\n");
		return sb.toString();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		long temp;
		temp = Double.doubleToLongBits(interestRate);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result
				+ ((maturityDate == null) ? 0 : maturityDate.hashCode());
		return result;
	}

	/** 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		CdAccount other = (CdAccount) obj;
		if (Double.doubleToLongBits(interestRate) != Double
				.doubleToLongBits(other.interestRate))
			return false;
		if (maturityDate == null) {
			if (other.maturityDate != null)
				return false;
		} else if (!maturityDate.equals(other.maturityDate))
			return false;
		return true;
	}
}
