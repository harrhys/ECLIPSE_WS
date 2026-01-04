<%--
 
 * Copyright 2002 Sun Microsystems, Inc. All Rights Reserved.
 * 
 * Redistribution and use in source and binary forms, with or
 * without modification, are permitted provided that the following
 * conditions are met:
 * 
 * - Redistributions of source code must retain the above copyright
 *   notice, this list of conditions and the following disclaimer.
 * 
 * - Redistribution in binary form must reproduce the above
 *   copyright notice, this list of conditions and the following
 *   disclaimer in the documentation and/or other materials
 *   provided with the distribution.
 * 
 * Neither the name of Sun Microsystems, Inc. or the names of
 * contributors may be used to endorse or promote products derived
 * from this software without specific prior written permission.
 * 
 * This software is provided "AS IS," without a warranty of any
 * kind. ALL EXPRESS OR IMPLIED CONDITIONS, REPRESENTATIONS AND
 * WARRANTIES, INCLUDING ANY IMPLIED WARRANTY OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE OR NON-INFRINGEMENT, ARE HEREBY
 * EXCLUDED. SUN AND ITS LICENSORS SHALL NOT BE LIABLE FOR ANY
 * DAMAGES OR LIABILITIES SUFFERED BY LICENSEE AS A RESULT OF OR
 * RELATING TO USE, MODIFICATION OR DISTRIBUTION OF THIS SOFTWARE OR
 * ITS DERIVATIVES. IN NO EVENT WILL SUN OR ITS LICENSORS BE LIABLE
 * FOR ANY LOST REVENUE, PROFIT OR DATA, OR FOR DIRECT, INDIRECT,
 * SPECIAL, CONSEQUENTIAL, INCIDENTAL OR PUNITIVE DAMAGES, HOWEVER
 * CAUSED AND REGARDLESS OF THE THEORY OF LIABILITY, ARISING OUT OF
 * THE USE OF OR INABILITY TO USE THIS SOFTWARE, EVEN IF SUN HAS
 * BEEN ADVISED OF THE POSSIBILITY OF SUCH DAMAGES.
 * 
 * You acknowledge that this software is not designed, licensed or
 * intended for use in the design, construction, operation or
 * maintenance of any nuclear facility.
  
--%>

<%@ taglib uri="/jstl-c" prefix="c" %>
<%@ taglib uri="/jstl-fmt" prefix="fmt" %>
<%@ page import="java.util.*" %>
<%@ page errorPage="errorpage.jsp" %>


<%--<form action="<c:out value='${pageContext.request.contextPath}'/>/checkoutAck" method=post>--%>
<form action="/cbserver/checkoutAck" method=post>
<center>
<table cellpadding=4 cellspacing=2 border=0>
<tr>
<td colspan=2>
<p><fmt:message key="YourOrder"/> $<c:out value="${sessionScope.cart.total}" />. <fmt:message key="CheckoutInstructions"/></td>
</tr>
<tr>
<td colspan=2>&nbsp;</td>
</tr>

<tr bgcolor="#CC9999">
<td align="center" colspan=2><font size=5><b><fmt:message key="CheckoutForm"/><b></font></td>
</tr>

<tr bgcolor="#CC9999">
<td valign=top> 
<B><fmt:message key="FirstName"/></B> 
<br>
<input type="text" name="firstName" value='<c:out value="${requestScope.checkoutFormBean.firstName}"/>' size=15 maxlength=20>
<br><font size=2 color=black><c:out value="${requestScope.checkoutFormBean.errors.firstName}" /></font>
</td>
<td  valign=top>
<B><fmt:message key="LastName"/></B>
<br>
<input type="text" name="lastName" value='<c:out value="${requestScope.checkoutFormBean.lastName}"/>' size=15 maxlength=20>
<br><font size=2 color=black><c:out value="${requestScope.checkoutFormBean.errors.lastName}" /></font>
</td>
</tr>

<tr bgcolor="#CC9999">
<td valign=top>
<B><fmt:message key="EMail"/></B> 
<br>
<input type="text" name="email" value='<c:out value="${requestScope.checkoutFormBean.email}"/>' size=25  maxlength=125>
<br><font size=2 color=black><c:out value="${requestScope.checkoutFormBean.errors.email}" /></font>
</td>
<td  valign=top>
<B><fmt:message key="PhoneNumber"/></B>
<br> 
<input type="text" name="areaCode" value='<c:out value="${requestScope.checkoutFormBean.areaCode}"/>' size=3  maxlength=3>
<input type="text" name="phoneNumber" value='<c:out value="${requestScope.checkoutFormBean.phoneNumber}"/>' size=8  maxlength=8>
<br><font size=2 color=black><c:out value="${requestScope.checkoutFormBean.errors.phoneNumber}" /></font>
</td>
</tr>


<tr bgcolor="#CC9999">
<td valign=top>
<B><fmt:message key="Street"/></B> 
<br>
<input type="text" name="street" size=25 value='<c:out value="${requestScope.checkoutFormBean.street}"/>'  maxlength=25>
<br><font size=2 color=black><c:out value="${requestScope.checkoutFormBean.errors.street}" /></font>
</td>
<td  valign=top>
<B><fmt:message key="City"/></B>
<br>
<input type="text" name="city" size=25 value='<c:out value="${requestScope.checkoutFormBean.city}"/>'  maxlength=25>
<br><font size=2 color=black><c:out value="${requestScope.checkoutFormBean.errors.city}" /></font>
</td>
<br>
</tr>

<tr bgcolor="#CC9999">
<td valign=top>
<B><fmt:message key="State"/></B> 
<br>
<input type="text" name="state" size=2 value='<c:out value="${requestScope.checkoutFormBean.state}"/>'  maxlength=2>
<br><font size=2 color=black><c:out value="${requestScope.checkoutFormBean.errors.state}" /></font>
</td>
<td  valign=top>
<B><fmt:message key="Zip"/></B> 
<br>
<input type="text" name="zip" value='<c:out value="${requestScope.checkoutFormBean.zip}"/>' size=5  maxlength=5>
<br><font size=2 color=black><c:out value="${requestScope.checkoutFormBean.errors.zip}" /></font>
</td>
<br>
</tr>


<tr bgcolor="#CC9999">
<td valign=top>
<B><fmt:message key="CCOption"/></B> 
<br>
  <select name=CCOption> 
    <option value=0 <c:if test="${CCOption == 0}"> selected</c:if> >VISA</option>  	   
    <option value=1 <c:if test="${CCOption == 1}"> selected</c:if> >MasterCard</option>	   
    <option value=2 <c:if test="${CCOption == 2}"> selected</c:if> >American Express</option>
  </select>
</td>
<td  valign=top>
<B><fmt:message key="CCNumber"/></B> 
<br>
<input type="text" name="CCNumber" value='<c:out value="${requestScope.checkoutFormBean.CCNumber}"/>' size=16  maxlength=16>
<br><font size=2 color=black><c:out value="${requestScope.checkoutFormBean.errors.CCNumber}" /></font>
</td>
<br>
</tr>


<tr bgcolor="#CC9999">
<td colspan="2" align=center> 
<input type="submit" value="<fmt:message key='Submit'/>">&nbsp;&nbsp;&nbsp;&nbsp;<input type="reset" value="<fmt:message key='Reset'/>">
</td>
</tr>

</table>
</center>
</form>
