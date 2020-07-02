package com.farbig.cart.persistence;

public enum TxnMgmtType {

	HIBERNATE_JDBC, HIBERNATE_CMT, HIBERNATE_JTA, JPA_JDBC, JPA_JTA_DS, JPA_NON_JTA_DS, DUMMY_DS;

}
