<%--
 
  Copyright 2001 Sun Microsystems, Inc. All Rights Reserved.
  
  This software is the proprietary information of Sun Microsystems, Inc.  
  Use is subject to license terms.
  
--%>

<%@ page import="com.sun.ebank.util.*,com.sun.ebank.ejb.account.AccountController,java.util.*,com.sun.ebank.ejb.exception.*" %>
<jsp:useBean id="beanManager" class="com.sun.ebank.web.BeanManager" scope="application"/>
<%
  ArrayList accounts = null; 
  try {
    accounts = beanManager.getAccountController().getAccountsOfCustomer(request.getUserPrincipal().getName()); 
  } catch (InvalidParameterException e) {
  	// Not possible
  } catch (AccountNotFoundException e) {
  
  } 
  String accountId = ((AccountDetails)accounts.iterator().next()).getAccountId();
  ResourceBundle messages = (ResourceBundle)session.getAttribute("messages"); 
%>
<center> 
<table border=0 cellpadding=10 cellspacing=25 width=600>
<tr>      
  <td bgcolor="#CE9A00"><a href="<%=request.getContextPath()%>/accountList"><%=messages.getString("AccountList")%></a></td>
  <td bgcolor="#CE9A00"><a href="<%=request.getContextPath()%>/transferFunds"><%=messages.getString("TransferFunds")%></a></td>
  <td bgcolor="#CE9A00"><a href="<%=request.getContextPath()%>/atm?accountId=<%=accountId%>&operation=0"><%=messages.getString("ATM")%></a></td>
  <td bgcolor="#CE9A00"><a href="<%=request.getContextPath()%>/logoff"><%=messages.getString("Logoff")%></a></td>
</tr>
</table>
</center>
