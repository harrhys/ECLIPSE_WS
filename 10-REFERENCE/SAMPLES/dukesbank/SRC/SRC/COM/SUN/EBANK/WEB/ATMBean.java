/*
 *
 * Copyright 2001 Sun Microsystems, Inc. All Rights Reserved.
 * 
 * This software is the proprietary information of Sun Microsystems, Inc.  
 * Use is subject to license terms.
 * 
 */
 

package com.sun.ebank.web;

import com.sun.ebank.web.BeanManager;
import com.sun.ebank.util.*;
import com.sun.ebank.ejb.tx.TxController;
import com.sun.ebank.ejb.exception.*;
import java.util.*;
import java.rmi.RemoteException;
import java.math.BigDecimal;


public class ATMBean {
  private BeanManager beanManager;
  private BigDecimal amount;
  private int operation;
  private String accountId, operationString, prepString;
  private AccountDetails selectedAccount;
  private ResourceBundle messages;
  private Locale locale; 
  
  public ATMBean () {
    messages = null;
    beanManager = null;
    amount = new BigDecimal("0.00");
    operation = 0; 
    accountId = "";
    operationString = null;
    prepString = null;
    selectedAccount = null;
  }

  public String populate() {
    TxController txctl = beanManager.getTxController();
    String message = null;
    operationString = messages.getString("WithdrewString");
    prepString = messages.getString("FromString");

    try {
      selectedAccount = beanManager.getAccountController().getDetails(accountId);
    } catch (RemoteException e) {
    } catch (InvalidParameterException e) {
    } catch (AccountNotFoundException e) {
    }
    boolean isCreditAcct = false;
    if (selectedAccount.getType().equals("Credit"))
      isCreditAcct = true;

    if (isCreditAcct) {
      if (operation == 0) {  
        try {
          txctl.makeCharge(amount, "ATM Withdrawal", accountId);
        } catch (RemoteException e) {
          Debug.print(message);
          return e.getMessage();  
        } catch (InvalidParameterException e) {
          // Not possible
        } catch (AccountNotFoundException e) {
          // Not possible
        } catch (InsufficientCreditException e) {
          message = messages.getString("InsufficientCreditError");
          Debug.print(message);
        } catch (IllegalAccountTypeException e) {
          // Not possible
        }
      } else {
        operationString = messages.getString("DepositedString");
        prepString = messages.getString("ToString");
        try {
          txctl.makePayment(amount, "ATM Deposit", accountId);
        } catch (RemoteException e) {
          Debug.print(message);
          return e.getMessage();
        } catch (InvalidParameterException e) {
          // Not possible
        } catch (AccountNotFoundException e) {
          // Not possible
        } catch (IllegalAccountTypeException e) {
          // Not possible
        }
      }
    } else {  
      if (operation == 0) {  
        try {
          txctl.withdraw(amount, "ATM Withdrawal", accountId);
        } catch (RemoteException e) {
          Debug.print(message);
          return e.getMessage();
        } catch (InvalidParameterException e) {
          // Not possible
        } catch (AccountNotFoundException e) {
          // Not possible
        } catch (IllegalAccountTypeException e) {
          // Not possible
        } catch (InsufficientFundsException e) {
          message = messages.getString("InsufficientFundsError");
          Debug.print(message);
        }
      } else {  
        operationString = messages.getString("DepositedString");
        prepString = messages.getString("ToString");
        try {
          txctl.deposit(amount, "ATM Deposit", accountId);
        } catch (RemoteException e) {
          Debug.print(message);
          return e.getMessage();
        } catch (InvalidParameterException e) {
          // Not possible
        } catch (AccountNotFoundException e) {
          // Not possible
        } catch (IllegalAccountTypeException e) {
          // Not possible
        }
      }
    }
    try {
      selectedAccount = beanManager.getAccountController().getDetails(accountId);
    } catch (RemoteException e) {
      Debug.print(message);
      return e.getMessage();
    } catch (InvalidParameterException e) {
      // Not possible
    } catch (AccountNotFoundException e) {
      // Not possible
    }
    return message;
  }

  public BigDecimal getAmount() {
    return amount;
  }
  public String getAccountId() {
    return accountId;
  }
  public String getOperationString() {
    return operationString;
  }
  public String getPrepString() {
    return prepString;
  }
  public AccountDetails getSelectedAccount() {
    return selectedAccount;
  }

  public void setMessages(ResourceBundle messages) {
    this.messages = messages;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount; 
    Debug.print("Setting amount to: " + amount);
  }
  public void setOperation(int operation) {
    this.operation = operation;
    Debug.print("Setting operation to: " + operation);
  }  
  public void setAccountId(String accountId) {
    this.accountId = accountId;
    Debug.print("Setting account id to: " + accountId);    
  }  
  public void setBeanManager(BeanManager beanManager) {
    this.beanManager = beanManager;
  }
}

