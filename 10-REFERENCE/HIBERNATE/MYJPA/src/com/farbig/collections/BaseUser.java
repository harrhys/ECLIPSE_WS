package com.farbig.collections;

import static javax.persistence.AccessType.PROPERTY;
import static javax.persistence.DiscriminatorType.STRING;
import static javax.persistence.InheritanceType.JOINED;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import static javax.persistence.InheritanceType.SINGLE_TABLE;
import static javax.persistence.InheritanceType.TABLE_PER_CLASS;

@Entity
@Table(name = "BASE_USER")
@Cacheable(false)
@Inheritance(strategy = JOINED)
public class BaseUser implements Serializable{


	private static final long serialVersionUID = 903569754541040222L;

	@Id
	@Column(name="USER_ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int userId;

	@Column(name="USER_NAME")
	private String userName;

	@ElementCollection
	@JoinTable(name="USER_ADDRESS",joinColumns=@JoinColumn(name="USER_ID"))
	private Collection <Address> addressList;
	
	@Embedded
	private  Address address;

	@Column(name="CREATED_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createDt;


	public Date getCreateDt() {
		return createDt;
	}

	public void setCreateDt(Date createDt) {
		this.createDt = createDt;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
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

	public void setAddress(Address address) {
		this.address = address;
	}

	public Address getAddress() {
		return address;
	}
	
	@Override
	public String toString() {
		return userId+"-"+userName;
	}


}
