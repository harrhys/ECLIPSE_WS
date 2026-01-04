/*
 *
 * Copyright 2001 Sun Microsystems, Inc. All Rights Reserved.
 * 
 * This software is the proprietary information of Sun Microsystems, Inc.  
 * Use is subject to license terms.
 * 
 */

package com.sun.ebank.util;

import java.math.BigDecimal;
import java.util.Date;
import java.util.ArrayList;

/**
 * This class holds the details of a bank transaction entity.
 */

public class TxDetails implements java.io.Serializable {

    private String txId;
    private String accountId;
    private Date timeStamp;
    private BigDecimal amount;
    private BigDecimal balance;
    private String description;

    public TxDetails(String txId, String accountId,
        Date timeStamp, BigDecimal amount, BigDecimal balance,
        String description) {

        this.txId = txId;
        this.accountId = accountId;
        this.timeStamp = timeStamp;
        this.amount = amount;
        this.balance = balance;
        this.description = description;
    }

    // getters

    public String getTxId() {
        return txId;
    }

    public String getAccountId() {
        return accountId;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public String getDescription() {
        return description;
    }


} // TxDetails
