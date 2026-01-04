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
import com.sun.ebank.ejb.account.AccountController;
import com.sun.ebank.ejb.tx.TxController;
import com.sun.ebank.ejb.exception.*;
import java.util.*;
import java.rmi.RemoteException;
import java.math.BigDecimal;


public class AccountHistoryBean {
   BigDecimal credits, debits, beginningBalance, endingBalance;
   String accountId;
   int date, activityView, sortOption, sinceMonth, sinceDay, beginMonth, beginDay, endMonth, endDay;
   ArrayList selectedTransactions;
   AccountDetails selectedAccount;
   BeanManager beanManager;

   public AccountHistoryBean () {
    credits = new BigDecimal("0.00");
    debits = new BigDecimal("0.00");
    beginningBalance = new BigDecimal("0.00");
    endingBalance = new BigDecimal("0.00");
   	selectedAccount = null;
    beanManager = null;
    selectedTransactions = null;
    date = 0;
    activityView = 0;
    sortOption = 0;
    sinceMonth = 1;
    sinceDay = 1;
    beginMonth = 1;
    beginDay = 1;
    endMonth = 1;
    endDay = 1;   
   }

   public void populate() {
     BigDecimal amount = new BigDecimal("0.00");
     TxDetails td = null;
     Date startDate = null;
     Date endDate = null;
      try {
         switch (date) {
            case 0:
              startDate = DateHelper.getDate(2001, sinceMonth, sinceDay);
              endDate = new Date();
              break;
            case 1:
              startDate = DateHelper.getDate(2001, beginMonth, beginDay);
              endDate = DateHelper.getDate(2001, endMonth, endDay);
              break;
         }

         ArrayList transactions = beanManager.getTxController().getTxsOfAccount(startDate, endDate, accountId);
         switch (sortOption) {
          	case 0:
               Collections.sort(transactions, new Comparator() {
                  public int compare(Object o1, Object o2) {
                     return ( ((TxDetails)o1).getTimeStamp().compareTo(((TxDetails)o2).getTimeStamp()) );
                  }
              	});			
               break;
            case 1:
               Collections.sort(transactions, new Comparator() {
                  public int compare(Object o1, Object o2) {
                     return ( ((TxDetails)o2).getTimeStamp().compareTo(((TxDetails)o1).getTimeStamp()) );
                  }
              	});			
               break;
            case 2:
               Collections.sort(transactions, new Comparator() {
                  public int compare(Object o1, Object o2) {
                     return ( ((TxDetails)o1).getDescription().compareTo( ((TxDetails)o2).getDescription() ));
                  }
              	});			
               break;
            case 3: 
               Collections.sort(transactions, new Comparator() {
                  public int compare(Object o1, Object o2) {
                     return ( ((TxDetails)o1).getAmount().compareTo(((TxDetails)o2).getAmount()) );
                  }
              	});			
               break;
         }
     		
         credits = new BigDecimal("0.00");
         debits = new BigDecimal("0.00"); 
         selectedAccount = beanManager.getAccountController().getDetails(accountId);
         beginningBalance = selectedAccount.getBalance();
         endingBalance = selectedAccount.getBalance();
         boolean isCreditAcct = false;
         if (selectedAccount.getType().equals("Credit"))
            isCreditAcct = true;

 	       Iterator i = transactions.iterator();
         if (i.hasNext()) {
            td = (TxDetails)i.next();
            beginningBalance = td.getBalance().subtract(td.getAmount());
         }
         i = transactions.iterator();
         while (i.hasNext()) {
            td = (TxDetails)i.next();
            amount = td.getAmount();
            if (isCreditAcct) {
               if (amount.floatValue( ) < 0)
                  credits.add(amount);
               else
                  debits.add(amount);
            }
            else {
               if (amount.floatValue() > 0)
                  credits.add(amount);
               else
                  debits.add(amount);
            }
         }
         if (td != null)
            endingBalance = td.getBalance();
         selectedTransactions = new ArrayList();
         i = transactions.iterator();
         if (i.hasNext()) {
            switch (activityView) {
             	case 0:
                  while (i.hasNext()) {
                     td = (TxDetails)i.next();
                     selectedTransactions.add(td);
                  }
                  break;
               case 1:
                  while (i.hasNext()) {
                     td = (TxDetails)i.next();
                     if (isCreditAcct) { 
                        if (td.getAmount().floatValue() < 0)
                           selectedTransactions.add(td);
                     }
                     else {
                        if (td.getAmount().floatValue() > 0)
                           selectedTransactions.add(td);
                     }
    
                  }
                  break;
               case 2:
                  while (i.hasNext()) {
                     td = (TxDetails)i.next();
                     if (isCreditAcct) {
                        if (td.getAmount().floatValue() > 0)
                           selectedTransactions.add(td);
                     }
                     else {
                        if (td.getAmount().floatValue() < 0)
                           selectedTransactions.add(td);
                     }
                  }
                  break;
             }
          } else { 
            Debug.print("No matching transactions.");
          }

      } catch (InvalidParameterException e) {
      } catch (RemoteException e) {
          Debug.print("Couldn't access enterprise bean: " + e.getMessage());
      } catch (AccountNotFoundException e) {
          Debug.print("Couldn't find account: " + e.getMessage());
      }
   }

   public BigDecimal getCredits() {
      return credits;
   } 
   public BigDecimal getDebits() {
      return debits;
   }
   public BigDecimal getBeginningBalance() {
      return beginningBalance;
   }
   public BigDecimal getEndingBalance() {
      return endingBalance;
   }
   public Collection getTransactions() {
      return selectedTransactions;
   }
   public AccountDetails getSelectedAccount() {
      return selectedAccount;
   }
   public void setBeanManager(BeanManager beanManager) {
      this.beanManager = beanManager;
   }
   public void setAccountId(String accountId) {
      this.accountId = accountId;
   }  
   public void setDate(int date) {
      this.date = date;
   }
   public void setBeginMonth(int beginMonth) {
      this.beginMonth = beginMonth;
   }
   public void setBeginDay(int beginDay) {
      this.beginDay = beginDay;
   }
   public void setEndMonth(int endMonth) {
      this.endMonth = endMonth;
   }
   public void setEndDay(int endDay) {
      this.endDay = endDay;
   }
   public void setSinceMonth(int sinceMonth) {
      this.sinceMonth = sinceMonth;
   }
   public void setSinceDay(int sinceDay) {
      this.sinceDay = sinceDay;
   }
   public void setActivityView(int activityView) {
      this.activityView = activityView;
   }
   public void setSortOption(int sortOption) {
      this.sortOption = sortOption;
   }
}

