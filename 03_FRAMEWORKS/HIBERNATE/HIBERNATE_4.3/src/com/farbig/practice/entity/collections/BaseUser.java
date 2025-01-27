package com.farbig.practice.entity.collections;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.MapKeyColumn;
import javax.persistence.MapKeyEnumerated;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.farbig.practice.entity.BaseEntity;
import com.farbig.practice.entity.embeddable.Address;

@Entity
@Table(name = "COLL_BASE_USER")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorValue(value = "BASEUSER")
@DiscriminatorColumn(name = "USER_TYPE", discriminatorType = DiscriminatorType.STRING)
public class BaseUser extends BaseEntity {

	public BaseUser() {

	}

	public BaseUser(BaseUser user) {
		this.id = user.getId();
		this.addressList = user.getAddressList();
		this.emails = user.getEmails();
		this.userName = user.getUserName();
		this.phonenumbers = user.getPhonenumbers();
	}

	@Id
	@Column(name = "USER_ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "u_seq")
	@SequenceGenerator(name = "u_seq", sequenceName = "user_seq")
	private int id;

	@Column(name = "USER_NAME")
	private String userName;

	@ElementCollection(targetClass = Address.class)
	@JoinTable(name = "COLL_USER_ADDRESS", joinColumns = @JoinColumn(name = "USER_ID"))
	private Collection<Address> addressList;

	@ElementCollection
	@CollectionTable(name = "COLL_USER_EMAIL", joinColumns = @JoinColumn(name = "USER_ID"))
	@Column(name = "EMAIL_ADDRESS")
	private Set<String> emails;

	@ElementCollection
	@CollectionTable(name = "COLL_USER_PHONE", joinColumns = @JoinColumn(name = "USER_ID"))
	@MapKeyEnumerated(EnumType.STRING)
	@MapKeyColumn(name = "PHONE_TYPE")
	@Column(name = "PHONE_NUMBER")
	private Map<PhoneType, String> phonenumbers;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Collection<Address> getAddressList() {
		return addressList;
	}

	public void setAddressList(Collection<Address> addressList) {
		this.addressList = addressList;
	}

	public Set<String> getEmails() {
		return emails;
	}

	public void setEmails(Set<String> emails) {
		this.emails = emails;
	}

	public void addEmail(String email) {
		if (this.emails == null)
			emails = new HashSet<String>();
		emails.add(email);
	}

	public Map<PhoneType, String> getPhonenumbers() {
		return phonenumbers;
	}

	public void setPhonenumbers(Map<PhoneType, String> phonenumbers) {
		this.phonenumbers = phonenumbers;
	}

	public static enum PhoneType {
		HOME, OFFICE, MOBILE
	}

}
