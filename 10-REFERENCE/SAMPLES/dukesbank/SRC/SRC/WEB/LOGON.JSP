<%--
 
  Copyright 2001 Sun Microsystems, Inc. All Rights Reserved.
  
  This software is the proprietary information of Sun Microsystems, Inc.  
  Use is subject to license terms.
  
--%>

<%@ page import="java.util.*" %>
<% ResourceBundle messages = (ResourceBundle)session.getAttribute("messages"); %>
 
<center>
<h3><p><%=messages.getString("Logon")%> <%=messages.getString("Submit")%>.</h3>
<br><br><form action="j_security_check" method=post>
<table>
<tr>
   <td align="center" >
   <table border="0">
   <tr>
   <td><b><%=messages.getString("CustomerId")%></b></td>
   <td>
      <input type="text" size="15" name="j_username"> 
   </td>
   </tr>
   <tr>
   <td><b><%=messages.getString("Password")%></b></td>
   <td> 
      <input type="password" size="15" name="j_password">
   </td>
   </tr>
   <tr>
   <td></td>
   <td align="right"> 
   <input type="submit" value="<%=messages.getString("Submit")%>">
   </td>
   </tr>
   <tr>
   <td><br></td>
   </tr>
</table>
</td>
</tr>
</table>
</form>
</center>
