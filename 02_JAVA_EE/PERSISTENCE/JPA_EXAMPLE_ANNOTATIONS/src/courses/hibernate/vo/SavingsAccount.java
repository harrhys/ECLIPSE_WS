package courses.hibernate.vo;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * Domain object representing a Savings Account
 */
@Entity
@DiscriminatorValue("SAVINGS")
public class SavingsAccount extends Account {

	@Basic(optional = false)
	@Column(name = "INTEREST_RATE")
	private double interestRate;

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
		sb.append("\n----SAVINGS ACCOUNT----\n");
		sb.append(super.toString());
		sb.append("interestRate=" + interestRate + "\n");
		sb.append("----SAVINGS ACCOUNT----\n");
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
		SavingsAccount other = (SavingsAccount) obj;
		if (Double.doubleToLongBits(interestRate) != Double
				.doubleToLongBits(other.interestRate))
			return false;
		return true;
	}
}
