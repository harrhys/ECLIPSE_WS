/*
 *
 * Copyright 2001 Sun Microsystems, Inc. All Rights Reserved.
 * 
 * This software is the proprietary information of Sun Microsystems, Inc.  
 * Use is subject to license terms.
 * 
 */

package com.sun.ebank.web;
import javax.servlet.*;
import javax.servlet.http.*;
import com.sun.ebank.util.Debug;
import java.util.*;
import java.math.BigDecimal;

public class Dispatcher extends HttpServlet {
  public void doGet(HttpServletRequest request, HttpServletResponse response) {   
      HttpSession session = request.getSession();
      ResourceBundle messages = (ResourceBundle)session.getAttribute("messages");
      if (messages == null) {
         Locale locale=request.getLocale();
         messages = ResourceBundle.getBundle("WebMessages", locale); 
         session.setAttribute("messages", messages);
      }


    String selectedScreen = request.getServletPath();
    request.setAttribute("selectedScreen", selectedScreen);
    BeanManager beanManager = (BeanManager)getServletContext().getAttribute("beanManager");

    if (selectedScreen.equals("/accountHist")) {
      AccountHistoryBean accountHistoryBean = new AccountHistoryBean();
      request.setAttribute("accountHistoryBean", accountHistoryBean);
      try {
        accountHistoryBean.setAccountId(request.getParameter("accountId"));
        accountHistoryBean.setSortOption(Integer.parseInt(request.getParameter("sortOption")));
        accountHistoryBean.setActivityView(Integer.parseInt(request.getParameter("activityView")));
        accountHistoryBean.setDate(Integer.parseInt(request.getParameter("date")));
        accountHistoryBean.setSinceDay(Integer.parseInt(request.getParameter("sinceDay")));
        accountHistoryBean.setSinceMonth(Integer.parseInt(request.getParameter("sinceMonth")));
        accountHistoryBean.setBeginDay(Integer.parseInt(request.getParameter("beginDay")));
        accountHistoryBean.setBeginMonth(Integer.parseInt(request.getParameter("beginMonth")));
        accountHistoryBean.setEndDay(Integer.parseInt(request.getParameter("endDay")));
        accountHistoryBean.setEndMonth(Integer.parseInt(request.getParameter("endMonth")));
      } catch (NumberFormatException e) {
      }
      accountHistoryBean.setBeanManager(beanManager);
      accountHistoryBean.populate();
    }
    try {
        request.getRequestDispatcher("/template.jsp").forward(request, response);
    } catch(Exception ex) {
      ex.printStackTrace();
    }
  }
  public void doPost(HttpServletRequest request, HttpServletResponse response) {
    HttpSession session = request.getSession();
    ResourceBundle messages = (ResourceBundle)session.getAttribute("messages");
    String selectedScreen = request.getServletPath();
    Debug.print(selectedScreen);  
    request.setAttribute("selectedScreen", selectedScreen);
    BeanManager beanManager = (BeanManager)getServletContext().getAttribute("beanManager");

    if (selectedScreen.equals("/accountHist")) {
      AccountHistoryBean accountHistoryBean = new AccountHistoryBean();
      request.setAttribute("accountHistoryBean", accountHistoryBean);
      try {
        accountHistoryBean.setAccountId(request.getParameter("accountId"));
        accountHistoryBean.setSortOption(Integer.parseInt(request.getParameter("sortOption")));
        accountHistoryBean.setActivityView(Integer.parseInt(request.getParameter("activityView")));
        accountHistoryBean.setDate(Integer.parseInt(request.getParameter("date")));
        accountHistoryBean.setSinceDay(Integer.parseInt(request.getParameter("sinceDay")));
        accountHistoryBean.setSinceMonth(Integer.parseInt(request.getParameter("sinceMonth")));
        accountHistoryBean.setBeginDay(Integer.parseInt(request.getParameter("beginDay")));
        accountHistoryBean.setBeginMonth(Integer.parseInt(request.getParameter("beginMonth")));
        accountHistoryBean.setEndDay(Integer.parseInt(request.getParameter("endDay")));
        accountHistoryBean.setEndMonth(Integer.parseInt(request.getParameter("endMonth")));
      } catch (NumberFormatException e) {
        // Not possible
      }
      accountHistoryBean.setBeanManager(beanManager);
      accountHistoryBean.populate();
    } else if (selectedScreen.equals("/transferAck")) {
      String fromAccountId = request.getParameter("fromAccountId");
      String toAccountId = request.getParameter("toAccountId");
      if ( (fromAccountId == null) || (toAccountId == null) ) {
        request.setAttribute("selectedScreen", "/error");
        request.setAttribute("errorMessage", messages.getString("AccountError"));
      } else { 
        TransferBean transferBean = new TransferBean();
        request.setAttribute("transferBean", transferBean);
        transferBean.setMessages(messages);
        transferBean.setFromAccountId(fromAccountId);       
        transferBean.setToAccountId(toAccountId);
        transferBean.setBeanManager(beanManager);
        try {
          transferBean.setTransferAmount(new BigDecimal(request.getParameter("transferAmount")));
          String errorMessage = transferBean.populate();
          if (errorMessage != null) {
          request.setAttribute("selectedScreen", "/error");
          request.setAttribute("errorMessage", errorMessage);
          }         
        } catch (NumberFormatException e) {
          request.setAttribute("selectedScreen", "/error");
          request.setAttribute("errorMessage", messages.getString("AmountError"));
        }
      }
    } else if (selectedScreen.equals("/atmAck")) {
      ATMBean atmBean = new ATMBean();
      request.setAttribute("atmBean", atmBean);
      atmBean.setMessages(messages);         
      atmBean.setAccountId(request.getParameter("accountId"));
      atmBean.setBeanManager(beanManager);
      try {
        atmBean.setAmount(new BigDecimal(request.getParameter("amount")));
        atmBean.setOperation(Integer.parseInt(request.getParameter("operation")));
        String errorMessage = atmBean.populate();
        if (errorMessage != null) {
          request.setAttribute("selectedScreen", "/error");
          request.setAttribute("errorMessage", errorMessage);
        }                 
      } catch (NumberFormatException e) {
        request.setAttribute("selectedScreen", "/error");
        request.setAttribute("errorMessage", messages.getString("AmountError"));
      }
    }       
    try {
        request.getRequestDispatcher("/template.jsp").forward(request, response);
    } catch(Exception e) {
    }
  }
}
