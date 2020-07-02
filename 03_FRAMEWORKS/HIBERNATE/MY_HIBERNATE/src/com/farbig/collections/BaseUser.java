package com.farbig.collections;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.CollectionId;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "COLL_BASE_USER")
@Cacheable
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@Inheritance(strategy=InheritanceType.JOINED)
@DiscriminatorColumn(name = "USER_TYPE", length = 10, discriminatorType = DiscriminatorType.STRING)
public class BaseUser implements Serializable{


	private static final long serialVersionUID = 903569754541040222L;

	@Id
	@Column(name="USER_ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int userId;

	@Column(name="USER_NAME")
	private String userName;

/*	@ElementCollection
	@JoinTable(name="USER_ADDRESS",joinColumns=@JoinColumn(name="USER_ID"))
	@GenericGenerator(name = "mygen", strategy = "hilo")
	@CollectionId(columns = { @Column(name="ADDRESS_ID") }, generator = "mygen", type = @Type(type="long"))
	private Collection <Address> addressList;*/
	
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
	
/*	public Collection<Address> getAddressList() {
		return addressList;
	}

	public void setAddressList(Collection<Address> addressList) {
		this.addressList = addressList;
	}
*/
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
