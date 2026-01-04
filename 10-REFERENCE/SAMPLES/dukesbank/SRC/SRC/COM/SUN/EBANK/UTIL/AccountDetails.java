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
 * This class holds the details of a bank account entity.
 * It contains getters and setters for each variable.
 */

public class AccountDetails implements java.io.Serializable {

    private String accountId;
    private String type;
    private String description;
    private BigDecimal balance;
    private BigDecimal creditLine;
    private BigDecimal beginBalance;
    private Date beginBalanceTimeStamp;
    private ArrayList customerIds;

    public AccountDetails(String accountId, String type,
        String description, BigDecimal balance, BigDecimal creditLine,
        BigDecimal beginBalance, Date beginBalanceTimeStamp,
        ArrayList customerIds) {

        this.accountId = accountId;
        this.type = type;
        this.description = description;
        this.balance = balance;
        this.creditLine = creditLine;
        this.beginBalance = beginBalance;
        this.beginBalanceTimeStamp = beginBalanceTimeStamp;
        this.customerIds = customerIds;
    }

    // getters

    public String getAccountId() {
        return accountId;
    }

    public String getDescription() {
        return description;
    }

    public String getType() {
        return type;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public BigDecimal getCreditLine() {
        return creditLine;
    }

    public BigDecimal getBeginBalance() {
        return beginBalance;
    }

    public Date getBeginBalanceTimeStamp() {
        return beginBalanceTimeStamp;
    }

    public ArrayList getCustomerIds() {
        return customerIds;
    }

    // setters

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public void setCreditLine(BigDecimal creditLine) {
        this.creditLine = creditLine;
    }

    public void setBeginBalance(BigDecimal beginBalance) {
        this.beginBalance = beginBalance;
    }

    public void setBeginBalanceTimeStamp(Date beginBalanceTimeStamp) {
        this.beginBalanceTimeStamp = beginBalanceTimeStamp;
    }

    public void setCustomerIds(ArrayList customerIds) {
        this.customerIds= customerIds;
    }


} // AccountDetails
