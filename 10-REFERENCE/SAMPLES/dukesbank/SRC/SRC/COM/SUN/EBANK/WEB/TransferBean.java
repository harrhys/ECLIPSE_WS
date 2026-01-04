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


public class TransferBean {
    private BeanManager beanManager;
    private BigDecimal transferAmount;
    private String fromAccountId, toAccountId;
    private ResourceBundle messages;

    public TransferBean () {
      beanManager = null;
      transferAmount = new BigDecimal("0.00");
      fromAccountId = "";
      toAccountId = ""; 
      messages = null;
   }

   public String populate() {
      TxController txCtl = beanManager.getTxController(); 
      String message = null;
      try {
         txCtl.transferFunds(transferAmount, "Transfer", fromAccountId, toAccountId);
      } catch (RemoteException e) {
        message = e.getMessage();
        Debug.print(message);
      } catch (InvalidParameterException e) {
        // Not possible
      } catch (AccountNotFoundException e) {
        // Not possible       
      } catch (InsufficientFundsException e) {
        message = messages.getString("InsufficientFundsError");
        Debug.print(message);
      } catch (InsufficientCreditException e) {
        message = messages.getString("InsufficientCreditError");
        Debug.print(message);
      }
      return message;
   }

   public BigDecimal getTransferAmount() {
      return transferAmount;
   }
   public String getFromAccountId() {
      return fromAccountId;
   }
   public String getToAccountId() {
      return toAccountId;
   }                   
   public void setBeanManager(BeanManager beanManager) {
      this.beanManager = beanManager;
   }
   public void setTransferAmount(BigDecimal transferAmount) {
      this.transferAmount = transferAmount;  
      Debug.print("Setting transfer amount to: " + transferAmount);
   }
   public void setFromAccountId(String fromAccountId) {
      this.fromAccountId = fromAccountId;
      Debug.print("Setting from account id to: " + fromAccountId);
   }  
   public void setToAccountId(String toAccountId) {
      this.toAccountId = toAccountId;
      Debug.print("Setting to account id to: " + toAccountId);           
   }  
  public void setMessages(ResourceBundle messages) {
    this.messages = messages;
  }
}

