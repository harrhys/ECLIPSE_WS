package courses.hibernate.vo;

/**
 * Domain object representing a Checking Account
 */
public class CheckingAccount extends Account {
	private String checkStyle;

	/**
	 * Set checkStyle
	 * 
	 * @param checkStyle
	 */
	public void setCheckStyle(String checkStyle) {
		this.checkStyle = checkStyle;
	}

	/**
	 * Get checkStyle
	 * 
	 * @return checkStyle
	 */
	public String getCheckStyle() {
		return checkStyle;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer(512);
		sb.append("\n----CHECKING ACCOUNT----\n");
		sb.append(super.toString());
		sb.append("checkStyle=" + checkStyle + "\n");
		sb.append("----CHECKING ACCOUNT----\n");
		return sb.toString();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((checkStyle == null) ? 0 : checkStyle.hashCode());
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
		CheckingAccount other = (CheckingAccount) obj;
		if (checkStyle == null) {
			if (other.checkStyle != null)
				return false;
		} else if (!checkStyle.equals(other.checkStyle))
			return false;
		return true;
	}
}
