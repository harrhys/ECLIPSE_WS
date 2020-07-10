package com.farbig.practice.entity.collections;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.farbig.practice.entity.BaseEntity;
import com.farbig.practice.entity.embeddable.Address;

@Entity
@Table(name = "COLL_BASE_USER")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorValue(value = "BASEUSER")
@DiscriminatorColumn(name = "USER_TYPE", discriminatorType = DiscriminatorType.STRING)

public class BaseUser extends BaseEntity{

	@Id
	@Column(name = "USER_ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "u_seq")
	@SequenceGenerator(name = "u_seq", sequenceName = "user_seq",allocationSize=1)
	private int id;

	@Column(name = "USER_NAME")
	private String userName;
	
	@ElementCollection
	@JoinTable(name = "COLL_USER_ADDRESS", joinColumns = @JoinColumn(name = "USER_ID"))
	/*
	 * @GenericGenerator(name = "mygen", strategy = "hilo")
	 * 
	 * @CollectionId(columns = { @Column(name = "ADDRESS_ID") }, generator =
	 * "mygen", type = @Type(type = "long"))
	 */
	private Collection<Address> addressList;

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

	

}
