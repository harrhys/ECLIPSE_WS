package com.farbig.cart.entity;

import java.io.Serializable;

import com.farbig.cart.persistence.TxnMgmtType;

public class CartEntity implements Serializable{
	
	TxnMgmtType txnMgmtType = TxnMgmtType.HIBERNATE_CMT;

	public TxnMgmtType getTxnMgmtType() {
		return txnMgmtType;
	}

	public void setTxnMgmtType(TxnMgmtType txnMgmtType) {
		this.txnMgmtType = txnMgmtType;
	}

}
