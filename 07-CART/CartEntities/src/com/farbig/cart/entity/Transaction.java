package com.farbig.cart.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Entity implementation class for Entity: Transaction
 *
 */
@Entity(name = "transaction")
public class Transaction implements Serializable {

	private static final long serialVersionUID = 1L;

	public Transaction() {
		super();
	}

	@Id
	@Column(name = "TRANSACTION_ID")
	@SequenceGenerator(name = "txn_id", sequenceName = "txn_id", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "txn_id")
	private int id;

	@Column(name = "TXN_TYPE")
	private String txnType;

	@Column(name = "TXN_AMOUNT")
	private double txnAmount;

	@Column(name = "STATUS")
	private String status;

	@Column(name = "TXN_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date txnDate;

	@Column(name = "ORDER_ID")
	private int orderId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SRC_ACC_ID")
	private Account srcAcc;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TGT_ACC_ID")
	private Account tgtAcc;

	@Column(name = "SRC_ACC_BAL")
	private double srcAccBal;

	@Column(name = "TGT_ACC_BAL")
	private double tgtAccBal;

	public double getSrcAccBal() {
		return srcAccBal;
	}

	public void setSrcAccBal(double srcAccBal) {
		this.srcAccBal = srcAccBal;
	}

	public double getTgtAccBal() {
		return tgtAccBal;
	}

	public void setTgtAccBal(double tgtAccBal) {
		this.tgtAccBal = tgtAccBal;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTxnType() {
		return txnType;
	}

	public void setTxnType(String txnType) {
		this.txnType = txnType;
	}

	public double getTxnAmount() {
		return txnAmount;
	}

	public void setTxnAmount(double txnAmount) {
		this.txnAmount = txnAmount;
	}

	public Date getTxnDate() {
		return txnDate;
	}

	public void setTxnDate(Date txnDate) {
		this.txnDate = txnDate;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Account getSrcAcc() {
		return srcAcc;
	}

	public void setSrcAcc(Account srcAcc) {
		this.srcAcc = srcAcc;
	}

	public Account getTgtAcc() {
		return tgtAcc;
	}

	public void setTgtAcc(Account tgtAcc) {
		this.tgtAcc = tgtAcc;
	}
}
