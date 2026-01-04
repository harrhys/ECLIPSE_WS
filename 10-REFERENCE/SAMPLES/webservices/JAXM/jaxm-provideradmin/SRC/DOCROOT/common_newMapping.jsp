<!--
 Copyright 2002 Sun Microsystems, Inc. All rights reserved.
 SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
-->

<%@ page language="java" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>

<html:html locale="true">

<%@ include file="header.jsp" %>

<body background="UI/images/jmenu/PaperTexture.gif" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<br>

<html:errors/>

<html:form method="post" action="newMapping.do">

  <%-- Values needed for determining what action to take --%>
  <input type=hidden name=type value="Endpoint">
  <input type=hidden name=action value="add">
  <input type=hidden name=profile value="<%= profile %>" >
  <input type=hidden name=protocol value="<%= protocol %>" >

<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr class="table-main-row"> 
      <td>
        <div class="table-main-text">         
            <bean:message key="actions.endpoint.add"/>
        </div>
      </td>
    </tr>
  </table>
  <p>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr> 
    <td width="23%"> 
      <div class="table-normal-text"><b><bean:message key="label.uri"/>:</b></div>
    </td>
    <td rowspan="2"> 
     <html:text property="uri" size="35" value="" /> 
    </td>
  </tr>
  <tr> 
    <td width="23%">&nbsp;</td>
  </tr>
  <tr> 
    <td width="23%">&nbsp;</td>
    <td width="77%">&nbsp;</td>
  </tr>
  <tr> 
    <td width="23%"> 
      <div class="table-normal-text"><b><bean:message key="label.url"/>:</b></div>
    </td>
    <td rowspan="2"> 
     <html:textarea property="url" cols="35" rows="2" value="" />  
    </td>
  </tr>
  <tr> 
    <td width="23%">&nbsp;</td>
  </tr>
  <tr> 
    <td width="23%">&nbsp;</td>
    <td width="77%">&nbsp;</td>
  </tr>
 </table>
 
<%@ include file="buttons.jsp" %>

</html:form>

<p>&nbsp;</p>
<p>&nbsp;</p>
<p>&nbsp;</p>
</body>
</html:html>
