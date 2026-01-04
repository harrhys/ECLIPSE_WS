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

<%--<form action="<c:out value='${pageContext.request.contextPath}'/>/orderForm" method=post>--%>
<form action="/cbserver/orderForm" method=post>
<center>

<table cellpadding=4 cellspacing=2 border=0>

<tr>
<td colspan=4><fmt:message key="OrderInstructions"/></td>
</tr>

<tr>
<td colspan=4>
&nbsp;</td>
</tr>

<tr bgcolor="#CC9999">
<td align="center" colspan=4><font size=5><b><fmt:message key="OrderForm"/><b></font></td>
</tr>

<tr bgcolor="#CC9999">
<td align=center><B><fmt:message key="Coffee"/></B></td>
<td align=center><B><fmt:message key="Price"/></B></td>
<td align=center><B><fmt:message key="Quantity"/></B></td>
<td align=center><B><fmt:message key="Total"/></B></td>
</tr>

<c:forEach var="sci" items="${sessionScope.cart.items}" >
<tr bgcolor="#CC9999">
<td><c:out value="${sci.item.coffeeName}" /></td>
<td align=right>$<c:out value="${sci.item.retailPricePerPound}" /></td>
<td align=center><input type="text" name="<c:out value='${sci.item.coffeeName}' />_pounds" value="<c:out value='${sci.pounds}' />" size="3"  maxlength="3"></td> 
<td align=right>$<c:out value="${sci.price}" /></td>
</tr>
</c:forEach>

<tr>
<td>&nbsp;</td>
<td> 
<a href="/cbserver/checkoutForm?firstName=Coffee&lastName=Lover&email=jane@home&areaCode=123&phoneNumber=456-7890&street=99&city=Somewhere&state=CA&zip=95050&CCNumber=1234-2345-5678&CCOption=0"><fmt:message key='Checkout'/></a>
<%--<a href="<c:out value='${pageContext.request.contextPath}'/>/checkoutForm?firstName=Coffee&lastName=Lover&email=jane@home&areaCode=123&phoneNumber=456-7890&street=99&city=Somewhere&state=CA&zip=95050&CCNumber=1234-2345-5678&CCOption=0"><fmt:message key='Checkout'/></a>--%>
</td>
<td><input type="submit" value="<fmt:message key='Update'/>"></td>
<td align=right>$<c:out value="${sessionScope.cart.total}" /></td>
<td>&nbsp;</td>
</tr>

<tr>
<td colspan=5><c:out value="${requestScope.orderError}" /></td>
</tr>


</table>
</center>
</form>




