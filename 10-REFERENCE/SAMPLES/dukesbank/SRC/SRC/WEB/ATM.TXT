<%--
 
  Copyright 2001 Sun Microsystems, Inc. All Rights Reserved.
  
  This software is the proprietary information of Sun Microsystems, Inc.  
  Use is subject to license terms.
  
--%>

<%@ taglib uri="/struts-logic" prefix="logic" %>
<%@ page import="com.sun.ebank.util.*,com.sun.ebank.ejb.account.AccountController,java.util.*,com.sun.ebank.ejb.exception.*" %>

<jsp:useBean id="beanManager" class="com.sun.ebank.web.BeanManager" scope="application"/>
<% 
  ArrayList accounts = null;
  try {
    accounts = beanManager.getAccountController().getAccountsOfCustomer(request.getUserPrincipal().getName()); 
  } catch (InvalidParameterException e) {
  }
  ResourceBundle messages = (ResourceBundle)session.getAttribute("messages");
%>
<%@ taglib uri="/struts-logic" prefix="logic" %>
<%@ page import="com.sun.ebank.util.*,com.sun.ebank.ejb.account.AccountController,java.util.ArrayList" %>
<center>
<table border=0 cellpadding=2 cellspacing=0 width=500> 
      <form name="atm" method="post" action="<%=request.getContextPath()%>/atmAck" >		  
      <tr>
      <td>
      <select name=operation> 
        <option value=0<logic:equal parameter="operation" value="0"> selected</logic:equal>><%=messages.getString("Withdraw")%></option>  	   
        <option value=1<logic:equal parameter="operation" value="1"> selected</logic:equal>><%=messages.getString("Deposit")%></option>   
      </select>
      </td>

      <td>
      <select name=accountId>
        <logic:iterate collection="<%= accounts %>" id="ad" type="com.sun.ebank.util.AccountDetails">
          <logic:equal parameter="accountId" value="<%=ad.getAccountId()%>" >
            <logic:notEqual name="ad" property="type" value="Credit">
              <option value="<jsp:getProperty name="ad" property="accountId"/>" selected><jsp:getProperty name="ad" property="description"/> ($<jsp:getProperty name="ad" property="balance"/>)</option>
            </logic:notEqual>
            <logic:equal name="ad" property="type" value="Credit">
              <option value="<jsp:getProperty name="ad" property="accountId"/>" selected><jsp:getProperty name="ad" property="description"/> ($<%= ad.getCreditLine().subtract(ad.getBalance()) %>)</option>
            </logic:equal>
          </logic:equal>
          <logic:notEqual parameter="accountId" value="<%=ad.getAccountId()%>">
            <logic:notEqual name="ad" property="type" value="Credit">
              <option value="<jsp:getProperty name="ad" property="accountId"/>"><jsp:getProperty name="ad" property="description"/> ($<jsp:getProperty name="ad" property="balance"/>)</option>
            </logic:notEqual>
            <logic:equal name="ad" property="type" value="Credit">
              <option value="<jsp:getProperty name="ad" property="accountId"/>"><jsp:getProperty name="ad" property="description"/> ($<%= ad.getCreditLine().subtract(ad.getBalance()) %>)</option>
            </logic:equal>
          </logic:notEqual>
        </logic:iterate>
      </select>
      </td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
      </tr>

      <tr>
      <td><b><%=messages.getString("ATMAmount")%></b></td>
      <td><input type="text" size="15" name="amount"></td>
      <td><input type="submit" value="<%=messages.getString("Submit")%>"></td>
      <td><input type="reset" value="<%=messages.getString("Clear")%>"></td>
      </tr>
      
    </form>
</table>
</center>
