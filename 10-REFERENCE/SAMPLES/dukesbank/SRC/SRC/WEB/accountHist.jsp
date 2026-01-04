<%--
 
  Copyright 2001 Sun Microsystems, Inc. All Rights Reserved.
  
  This software is the proprietary information of Sun Microsystems, Inc.  
  Use is subject to license terms.
  
--%>

<%@ taglib uri="/struts-logic" prefix="logic" %>
<%@ page import="com.sun.ebank.util.*,com.sun.ebank.ejb.account.AccountController,java.util.*,com.sun.ebank.ejb.exception.*" %>


<jsp:useBean id="beanManager" class="com.sun.ebank.web.BeanManager" scope="application"/>
<jsp:useBean id="accountHistoryBean" class="com.sun.ebank.web.AccountHistoryBean" scope="request"/>

<% 
  ArrayList accounts = null;
  try {
  	accounts = beanManager.getAccountController().getAccountsOfCustomer(request.getUserPrincipal().getName());
  } catch (InvalidParameterException e) {
  	// Not possible
  }
  ResourceBundle messages = (ResourceBundle)session.getAttribute("messages"); 
%>


<center>
<table border=0 cellpadding=2 cellspacing=5 width=500>
  <tr>
  <td valign=bottom>
    <%=messages.getString("AccountName")%>
  </td>
  <td valign=bottom>
    <%=messages.getString("ViewSelect")%>
  </td>
  <td valign=bottom>
    <%=messages.getString("SortSelect")%>  </td>
  <td>&nbsp;</td>
  </tr>

  <form name="accountHistory" method="post" action="<%=request.getContextPath()%>/accountHist" >
  <tr>
  <td valign=top>
  <select name=accountId>
    <logic:iterate collection="<%= accounts %>" id="ad" type="com.sun.ebank.util.AccountDetails">
      <logic:equal parameter="accountId" value="<%=ad.getAccountId()%>" >
       	<option value="<jsp:getProperty name="ad" property="accountId"/>" selected><jsp:getProperty name="ad" property="description"/></option>
      </logic:equal>
      <logic:notEqual parameter="accountId" value="<%=ad.getAccountId()%>">
          <option value="<jsp:getProperty name="ad" property="accountId"/>"><jsp:getProperty name="ad" property="description"/></option>
      </logic:notEqual>
    </logic:iterate>
  </select>
  </td>

  <td valign=top>
  <select name=activityView> 
    <option value=0<logic:equal parameter="activityView" value="0"> selected</logic:equal>><%=messages.getString("ViewOption0")%></option>  	   
    <option value=1<logic:equal parameter="activityView" value="1"> selected</logic:equal>><%=messages.getString("ViewOption1")%></option>	   
    <option value=2<logic:equal parameter="activityView" value="2"> selected</logic:equal>><%=messages.getString("ViewOption2")%></option>
  </select>
  </td>

  <td valign=top>
  <select name=sortOption>  
    <option value=0<logic:equal parameter="sortOption" value="0"> selected</logic:equal>><%=messages.getString("SortOption0")%></option>	   
    <option value=1<logic:equal parameter="sortOption" value="1"> selected</logic:equal>><%=messages.getString("SortOption1")%></option>	   
    <option value=2<logic:equal parameter="sortOption" value="2"> selected</logic:equal>><%=messages.getString("SortOption2")%></option>  
    <option value=3<logic:equal parameter="sortOption" value="3"> selected</logic:equal>><%=messages.getString("SortOption3")%></option>	   	
  </select>
  </td>
  
  
  <td valign="bottom" width=50> 
  <input type="submit" name="Submit" value="<%=messages.getString("Update")%>">   
  </td>
  <td>&nbsp;</td>
  </tr>

  <tr>
  <td valign=top>
  <table cellpadding=0 cellspacing=0 border=0>
  <tr>
  <td><input type="radio" name="date" value="0" <logic:equal parameter="date" value="0"> checked</logic:equal>><%=messages.getString("DateSince")%>
  </td>
  <td>
  <select width=10 name="sinceMonth">
    <option value="1"<logic:equal parameter="sinceMonth" value="1"> selected</logic:equal>><%=messages.getString("Month1")%>
    <option value="2"<logic:equal parameter="sinceMonth" value="2"> selected</logic:equal>><%=messages.getString("Month2")%>
    <option value="3"<logic:equal parameter="sinceMonth" value="3"> selected</logic:equal>><%=messages.getString("Month3")%>
    <option value="4"<logic:equal parameter="sinceMonth" value="4"> selected</logic:equal>><%=messages.getString("Month4")%>
    <option value="5"<logic:equal parameter="sinceMonth" value="5"> selected</logic:equal>><%=messages.getString("Month5")%>
    <option value="6"<logic:equal parameter="sinceMonth" value="6"> selected</logic:equal>><%=messages.getString("Month6")%>
    <option value="7"<logic:equal parameter="sinceMonth" value="7"> selected</logic:equal>><%=messages.getString("Month7")%>
    <option value="8"<logic:equal parameter="sinceMonth" value="8"> selected</logic:equal>><%=messages.getString("Month8")%>
    <option value="9"<logic:equal parameter="sinceMonth" value="9"> selected</logic:equal>><%=messages.getString("Month9")%>
    <option value="10"<logic:equal parameter="sinceMonth" value="10"> selected</logic:equal>><%=messages.getString("Month10")%>
    <option value="11"<logic:equal parameter="sinceMonth" value="11"> selected</logic:equal>><%=messages.getString("Month11")%>
    <option value="12"<logic:equal parameter="sinceMonth" value="12"> selected</logic:equal>><%=messages.getString("Month12")%>
  </select>

  <select width=3 name="sinceDay">
    <option value="1"<logic:equal parameter="sinceDay" value="1"> selected</logic:equal>>1
    <option value="2"<logic:equal parameter="sinceDay" value="2"> selected</logic:equal>>2
    <option value="3"<logic:equal parameter="sinceDay" value="3"> selected</logic:equal>>3
    <option value="4"<logic:equal parameter="sinceDay" value="4"> selected</logic:equal>>4
    <option value="5"<logic:equal parameter="sinceDay" value="5"> selected</logic:equal>>5
    <option value="6"<logic:equal parameter="sinceDay" value="6"> selected</logic:equal>>6
    <option value="7"<logic:equal parameter="sinceDay" value="7"> selected</logic:equal>>7
    <option value="8"<logic:equal parameter="sinceDay" value="8"> selected</logic:equal>>8
    <option value="9"<logic:equal parameter="sinceDay" value="9"> selected</logic:equal>>9
    <option value="10"<logic:equal parameter="sinceDay" value="10"> selected</logic:equal>>10
    <option value="11"<logic:equal parameter="sinceDay" value="11"> selected</logic:equal>>11
    <option value="12"<logic:equal parameter="sinceDay" value="12"> selected</logic:equal>>12
    <option value="13"<logic:equal parameter="sinceDay" value="13"> selected</logic:equal>>13
    <option value="14"<logic:equal parameter="sinceDay" value="14"> selected</logic:equal>>14
    <option value="15"<logic:equal parameter="sinceDay" value="15"> selected</logic:equal>>15
    <option value="16"<logic:equal parameter="sinceDay" value="16"> selected</logic:equal>>16
    <option value="17"<logic:equal parameter="sinceDay" value="17"> selected</logic:equal>>17
    <option value="18"<logic:equal parameter="sinceDay" value="18"> selected</logic:equal>>18
    <option value="19"<logic:equal parameter="sinceDay" value="19"> selected</logic:equal>>19
    <option value="20"<logic:equal parameter="sinceDay" value="20"> selected</logic:equal>>20
    <option value="21"<logic:equal parameter="sinceDay" value="21"> selected</logic:equal>>21
    <option value="22"<logic:equal parameter="sinceDay" value="22"> selected</logic:equal>>22
    <option value="23"<logic:equal parameter="sinceDay" value="23"> selected</logic:equal>>23
    <option value="24"<logic:equal parameter="sinceDay" value="24"> selected</logic:equal>>24
    <option value="25"<logic:equal parameter="sinceDay" value="25"> selected</logic:equal>>25
    <option value="26"<logic:equal parameter="sinceDay" value="26"> selected</logic:equal>>26
    <option value="27"<logic:equal parameter="sinceDay" value="27"> selected</logic:equal>>27
    <option value="28"<logic:equal parameter="sinceDay" value="28"> selected</logic:equal>>28
    <option value="29"<logic:equal parameter="sinceDay" value="29"> selected</logic:equal>>29
    <option value="30"<logic:equal parameter="sinceDay" value="30"> selected</logic:equal>>30
    <option value="31"<logic:equal parameter="sinceDay" value="31"> selected</logic:equal>>31
  </select>
  </center></td></tr>
  </table>
  </td>

  <td colspan=4  valign=top>
  <table cellpadding=0 cellspacing=3 border=0>
  <tr>
  <td><input type=radio name=date value=1 <logic:equal parameter="date" value="1"> checked</logic:equal>><%=messages.getString("DateRange")%></td>
  <td>
  <select width=10 name="beginMonth">
    <option value="1"<logic:equal parameter="beginMonth" value="1"> selected</logic:equal>><%=messages.getString("Month1")%>
    <option value="2"<logic:equal parameter="beginMonth" value="2"> selected</logic:equal>><%=messages.getString("Month2")%>
    <option value="3"<logic:equal parameter="beginMonth" value="3"> selected</logic:equal>><%=messages.getString("Month3")%>
    <option value="4"<logic:equal parameter="beginMonth" value="4"> selected</logic:equal>><%=messages.getString("Month4")%>
    <option value="5"<logic:equal parameter="beginMonth" value="5"> selected</logic:equal>><%=messages.getString("Month5")%>
    <option value="6"<logic:equal parameter="beginMonth" value="6"> selected</logic:equal>><%=messages.getString("Month6")%>
    <option value="7"<logic:equal parameter="beginMonth" value="7"> selected</logic:equal>><%=messages.getString("Month7")%>
    <option value="8"<logic:equal parameter="beginMonth" value="8"> selected</logic:equal>><%=messages.getString("Month8")%>
    <option value="9"<logic:equal parameter="beginMonth" value="9"> selected</logic:equal>><%=messages.getString("Month9")%>
    <option value="10"<logic:equal parameter="beginMonth" value="10"> selected</logic:equal>><%=messages.getString("Month10")%>
    <option value="11"<logic:equal parameter="beginMonth" value="11"> selected</logic:equal>><%=messages.getString("Month11")%>
    <option value="12"<logic:equal parameter="beginMonth" value="12"> selected</logic:equal>><%=messages.getString("Month12")%>
  </select>
  

  <select width=3 name="beginDay">
    <option value="1"<logic:equal parameter="beginDay" value="1"> selected</logic:equal>>1
    <option value="2"<logic:equal parameter="beginDay" value="2"> selected</logic:equal>>2
    <option value="3"<logic:equal parameter="beginDay" value="3"> selected</logic:equal>>3
    <option value="4"<logic:equal parameter="beginDay" value="4"> selected</logic:equal>>4
    <option value="5"<logic:equal parameter="beginDay" value="5"> selected</logic:equal>>5
    <option value="6"<logic:equal parameter="beginDay" value="6"> selected</logic:equal>>6
    <option value="7"<logic:equal parameter="beginDay" value="7"> selected</logic:equal>>7
    <option value="8"<logic:equal parameter="beginDay" value="8"> selected</logic:equal>>8
    <option value="9"<logic:equal parameter="beginDay" value="9"> selected</logic:equal>>9
    <option value="10"<logic:equal parameter="beginDay" value="10"> selected</logic:equal>>10
    <option value="11"<logic:equal parameter="beginDay" value="11"> selected</logic:equal>>11
    <option value="12"<logic:equal parameter="beginDay" value="12"> selected</logic:equal>>12
    <option value="13"<logic:equal parameter="beginDay" value="13"> selected</logic:equal>>13
    <option value="14"<logic:equal parameter="beginDay" value="14"> selected</logic:equal>>14
    <option value="15"<logic:equal parameter="beginDay" value="15"> selected</logic:equal>>15
    <option value="16"<logic:equal parameter="beginDay" value="16"> selected</logic:equal>>16
    <option value="17"<logic:equal parameter="beginDay" value="17"> selected</logic:equal>>17
    <option value="18"<logic:equal parameter="beginDay" value="18"> selected</logic:equal>>18
    <option value="19"<logic:equal parameter="beginDay" value="19"> selected</logic:equal>>19
    <option value="20"<logic:equal parameter="beginDay" value="20"> selected</logic:equal>>20
    <option value="21"<logic:equal parameter="beginDay" value="21"> selected</logic:equal>>21
    <option value="22"<logic:equal parameter="beginDay" value="22"> selected</logic:equal>>22
    <option value="23"<logic:equal parameter="beginDay" value="23"> selected</logic:equal>>23
    <option value="24"<logic:equal parameter="beginDay" value="24"> selected</logic:equal>>24
    <option value="25"<logic:equal parameter="beginDay" value="25"> selected</logic:equal>>25
    <option value="26"<logic:equal parameter="beginDay" value="26"> selected</logic:equal>>26
    <option value="27"<logic:equal parameter="beginDay" value="27"> selected</logic:equal>>27
    <option value="28"<logic:equal parameter="beginDay" value="28"> selected</logic:equal>>28
    <option value="29"<logic:equal parameter="beginDay" value="29"> selected</logic:equal>>29
    <option value="30"<logic:equal parameter="beginDay" value="30"> selected</logic:equal>>30
    <option value="31"<logic:equal parameter="beginDay" value="31"> selected</logic:equal>>31
  </select>
  </td>
  <td><%=messages.getString("DateThrough")%></td>
  <td>
  <select width=10 name="endMonth">
    <option value="1"<logic:equal parameter="endMonth" value="1"> selected</logic:equal>><%=messages.getString("Month1")%>
    <option value="2"<logic:equal parameter="endMonth" value="2"> selected</logic:equal>><%=messages.getString("Month2")%>
    <option value="3"<logic:equal parameter="endMonth" value="3"> selected</logic:equal>><%=messages.getString("Month3")%>
    <option value="4"<logic:equal parameter="endMonth" value="4"> selected</logic:equal>><%=messages.getString("Month4")%>
    <option value="5"<logic:equal parameter="endMonth" value="5"> selected</logic:equal>><%=messages.getString("Month5")%>
    <option value="6"<logic:equal parameter="endMonth" value="6"> selected</logic:equal>><%=messages.getString("Month6")%>
    <option value="7"<logic:equal parameter="endMonth" value="7"> selected</logic:equal>><%=messages.getString("Month7")%>
    <option value="8"<logic:equal parameter="endMonth" value="8"> selected</logic:equal>><%=messages.getString("Month8")%>
    <option value="9"<logic:equal parameter="endMonth" value="9"> selected</logic:equal>><%=messages.getString("Month9")%>
    <option value="10"<logic:equal parameter="endMonth" value="10"> selected</logic:equal>><%=messages.getString("Month10")%>
    <option value="11"<logic:equal parameter="endMonth" value="11"> selected</logic:equal>><%=messages.getString("Month11")%>
    <option value="12"<logic:equal parameter="endMonth" value="12"> selected</logic:equal>><%=messages.getString("Month12")%>
  </select>

  <select width=3 name="endDay">
    <option value="1"<logic:equal parameter="endDay" value="1"> selected</logic:equal>>1
    <option value="2"<logic:equal parameter="endDay" value="2"> selected</logic:equal>>2
    <option value="3"<logic:equal parameter="endDay" value="3"> selected</logic:equal>>3
    <option value="4"<logic:equal parameter="endDay" value="4"> selected</logic:equal>>4
    <option value="5"<logic:equal parameter="endDay" value="5"> selected</logic:equal>>5
    <option value="6"<logic:equal parameter="endDay" value="6"> selected</logic:equal>>6
    <option value="7"<logic:equal parameter="endDay" value="7"> selected</logic:equal>>7
    <option value="8"<logic:equal parameter="endDay" value="8"> selected</logic:equal>>8
    <option value="9"<logic:equal parameter="endDay" value="9"> selected</logic:equal>>9
    <option value="10"<logic:equal parameter="endDay" value="10"> selected</logic:equal>>10
    <option value="11"<logic:equal parameter="endDay" value="11"> selected</logic:equal>>11
    <option value="12"<logic:equal parameter="endDay" value="12"> selected</logic:equal>>12
    <option value="13"<logic:equal parameter="endDay" value="13"> selected</logic:equal>>13
    <option value="14"<logic:equal parameter="endDay" value="14"> selected</logic:equal>>14
    <option value="15"<logic:equal parameter="endDay" value="15"> selected</logic:equal>>15
    <option value="16"<logic:equal parameter="endDay" value="16"> selected</logic:equal>>16
    <option value="17"<logic:equal parameter="endDay" value="17"> selected</logic:equal>>17
    <option value="18"<logic:equal parameter="endDay" value="18"> selected</logic:equal>>18
    <option value="19"<logic:equal parameter="endDay" value="19"> selected</logic:equal>>19
    <option value="20"<logic:equal parameter="endDay" value="20"> selected</logic:equal>>20
    <option value="21"<logic:equal parameter="endDay" value="21"> selected</logic:equal>>21
    <option value="22"<logic:equal parameter="endDay" value="22"> selected</logic:equal>>22
    <option value="23"<logic:equal parameter="endDay" value="23"> selected</logic:equal>>23
    <option value="24"<logic:equal parameter="endDay" value="24"> selected</logic:equal>>24
    <option value="25"<logic:equal parameter="endDay" value="25"> selected</logic:equal>>25
    <option value="26"<logic:equal parameter="endDay" value="26"> selected</logic:equal>>26
    <option value="27"<logic:equal parameter="endDay" value="27"> selected</logic:equal>>27
    <option value="28"<logic:equal parameter="endDay" value="28"> selected</logic:equal>>28
    <option value="29"<logic:equal parameter="endDay" value="29"> selected</logic:equal>>29
    <option value="30"<logic:equal parameter="endDay" value="30"> selected</logic:equal>>30
    <option value="31"<logic:equal parameter="endDay" value="31"> selected</logic:equal>>31
  </select>
  </td></tr>
  </table>
</td>
</tr>
</form>
</table>

<table border=1 cellpadding=2 cellspacing=0 width=500>
<tr>
  <td colspan=2 bgcolor="#CE9A00"><center>
  <b><%= ((AccountDetails)accountHistoryBean.getSelectedAccount()).getDescription() %></b></center></td>
</tr>

<tr>
  <td><center><b><%=messages.getString("AccountDescription")%></b></center></td>
  <td><center><b><%=messages.getString("AccountAmount")%></b></center></td>
</tr>

<tr>
  <td><%=messages.getString("AccountBeginningBalance")%></td>
  <td align="right">$<jsp:getProperty name="accountHistoryBean" property="beginningBalance"/></td>
</tr>

<tr>
  <td><%=messages.getString("AccountCredits")%></td>
  <td align="right">$<jsp:getProperty name="accountHistoryBean" property="credits"/></td>
</tr>

<tr>
  <td><%=messages.getString("AccountDebits")%></td>
  <td align="right">$<jsp:getProperty name="accountHistoryBean" property="debits"/></td>
</tr>
<tr>
  <td><%=messages.getString("AccountEndingBalance")%></td>
  <td align="right">$<jsp:getProperty name="accountHistoryBean" property="endingBalance"/></td>
</tr>
</table>

<p>
<table border=1 cellpadding=5 cellspacing=0 width=500>
<tr>
  <td><center><b><%=messages.getString("TxDate")%></b></center></td>
  <td><center><b><%=messages.getString("TxDescription")%></center></b></td>
  <td><center><b><%=messages.getString("TxAmount")%></b></center></td>
  <td><center><b><%=messages.getString("TxRunningBalance")%></b></center></td>
</tr>

<logic:iterate collection="<%= accountHistoryBean.getTransactions() %>" id="tx" type="com.sun.ebank.util.TxDetails">
<tr>
  <td><jsp:getProperty name="tx" property="timeStamp"/></td>
  <td><jsp:getProperty name="tx" property="description"/></td>
  <td align="right"><jsp:getProperty name="tx" property="amount"/></td>
  <td align="right">$<jsp:getProperty name="tx" property="balance"/></td>
</tr>
</logic:iterate>

</table>

</center>


