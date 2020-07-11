package courses.hibernate.vo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class EBiller {
	private long ebillerId;
	private String name;
	private String billingAddress;
	private String phone;
	private Collection<EBillerRegistration> ebillerRegistrations = new ArrayList<EBillerRegistration>();
	private Set<EBill> ebills = new HashSet<EBill>();

	/**
	 * Get ebillerId
	 * 
	 * @return ebillerId
	 */
	public long getEbillerId() {
		return ebillerId;
	}

	/**
	 * Set ebillerId
	 * 
	 * @param ebillerId
	 */
	public void setEbillerId(long ebillerId) {
		this.ebillerId = ebillerId;
	}

	/**
	 * Get name
	 * 
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set name
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Get billingAddress
	 * 
	 * @return billingAddress
	 */
	public String getBillingAddress() {
		return billingAddress;
	}

	/**
	 * Set billingAddress
	 * 
	 * @param billingAddress
	 */
	public void setBillingAddress(String billingAddress) {
		this.billingAddress = billingAddress;
	}

	/**
	 * Get phone
	 * 
	 * @return phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * Set phone
	 * 
	 * @param phone
	 */
	public void setPhone(String phone) {
		this.phone = phone;
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
	 *            ebillerRegistration to be added
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
		if (!ebill.getEbiller().equals(this)) {
			ebill.getEbiller().getEbills().remove(ebill);
			ebill.setEbiller(this);
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
		if (ebill.getEbiller().equals(this)) {
			ebill.setEbiller(null);
		}
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer(512);
		sb.append("\n----EBILLER----\n");
		sb.append("ebillerId=" + ebillerId + "\n");
		sb.append("name=" + name + "\n");
		sb.append("billingAddress=" + billingAddress + "\n");
		sb.append("phone=" + phone + "\n");
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
		if (ebills != null && ebills.isEmpty() == false) {
			sb.append("ebills=");
			for (EBill ebill : ebills) {
				sb.append((ebill == null) ? "null" : ebill.getEbillId());
				sb.append(",");
			}
			sb.deleteCharAt(sb.length() - 1);
			sb.append("\n");
		}
		sb.append("----EBILLER----\n");
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
				+ ((billingAddress == null) ? 0 : billingAddress.hashCode());
		result = prime * result + (int) (ebillerId ^ (ebillerId >>> 32));
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((phone == null) ? 0 : phone.hashCode());
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
		if (!(obj instanceof EBiller))
			return false;
		EBiller other = (EBiller) obj;
		if (billingAddress == null) {
			if (other.billingAddress != null)
				return false;
		} else if (!billingAddress.equals(other.billingAddress))
			return false;
		if (ebillerId != other.ebillerId)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (phone == null) {
			if (other.phone != null)
				return false;
		} else if (!phone.equals(other.phone))
			return false;
		return true;
	}
}
