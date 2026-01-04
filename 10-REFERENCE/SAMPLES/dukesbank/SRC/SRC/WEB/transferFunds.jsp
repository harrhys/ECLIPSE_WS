<%--
 
  Copyright 2001 Sun Microsystems, Inc. All Rights Reserved.
  
  This software is the proprietary information of Sun Microsystems, Inc.  
  Use is subject to license terms.
  
--%>

<%@ taglib uri="/struts-logic" prefix="logic" %>
<%@ page import="com.sun.ebank.util.*, com.sun.ebank.ejb.account.AccountController,java.util.*,com.sun.ebank.ejb.exception.InvalidParameterException" %>

<jsp:useBean id="beanManager" class="com.sun.ebank.web.BeanManager" scope="application"/>
<jsp:useBean id="transferBean" class="com.sun.ebank.web.TransferBean" scope="request"/>
<% 
  ArrayList accounts = null;
  try {
    accounts = beanManager.getAccountController().getAccountsOfCustomer(request.getUserPrincipal().getName()); 
  } catch (InvalidParameterException e) {  
  }
  ResourceBundle messages = (ResourceBundle)session.getAttribute("messages");%>
<center>
<table border=0 cellpadding=2 cellspacing=0 width=500>
   <tr>
      <td valign="top" colspan="4">   
      <form name="transfer" method="post" action="<%=request.getContextPath()%>/transferAck" >
      <table border=1 cellpadding=2 cellspacing=0>
      <tr>
        <td><b><%=messages.getString("AccountName")%></b></td>
        <td><b><%=messages.getString("AccountId")%></b></td>
        <td><b><%=messages.getString("AccountBalance")%></b></td>
        <td><b><%=messages.getString("AccountFrom")%></b></td>
        <td><b><%=messages.getString("AccountTo")%></b></td>
      </tr>
      <logic:iterate collection="<%= accounts %>" id="ad" type="com.sun.ebank.util.AccountDetails">
      <tr>
        <td><jsp:getProperty name="ad" property="description"/></td>
        <td><jsp:getProperty name="ad" property="accountId"/></td>
        <td align="right">$<jsp:getProperty name="ad" property="balance"/></td>
        <td><input type="radio" name="fromAccountId" value="<jsp:getProperty name="ad" property="accountId"/>"></td>
        <td><input type="radio" name="toAccountId" value="<jsp:getProperty name="ad" property="accountId"/>"></td>
      </tr>
      </logic:iterate>
      </table>
      </td>
    </tr>

    <tr>
        <td><b><%=messages.getString("TransferAmount")%></b></td>
        <td><input type="text" size="15" name="transferAmount"></td>
        <td><input type="submit" value="<%=messages.getString("Submit")%>"></td>
        <td><input type="reset" value="<%=messages.getString("Clear")%>"></td>
    </form>
    </tr>

</table>
</center>
