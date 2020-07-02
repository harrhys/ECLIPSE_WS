package com.farbig.cart.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

/**
 * Entity implementation class for Entity: Account
 *
 */
@Entity(name = "account")
public class Account implements Serializable {

	private static final long serialVersionUID = 133L;

	public Account() {
		super();
	}

	@Id
	@Column(name = "ACCOUNT_ID")
	@SequenceGenerator(name = "acc_id", sequenceName = "acc_id", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "acc_id")
	private int id;

	@Column(name = "BALANCE")
	private double balance;

	@Column(name = "STATUS")
	private String status;

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "USER_ID")
	private User user;

	@OneToMany(mappedBy = "srcAcc", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Transaction> srcTxns;

	@OneToMany(mappedBy = "tgtAcc", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Transaction> tgtTxns;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Transaction> getSrcTxns() {
		return srcTxns;
	}

	public void setSrcTxns(List<Transaction> srcTxns) {
		this.srcTxns = srcTxns;
	}

	public List<Transaction> getTgtTxns() {
		return tgtTxns;
	}

	public void setTgtTxns(List<Transaction> tgtTxns) {
		this.tgtTxns = tgtTxns;
	}

}
