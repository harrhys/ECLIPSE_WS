<!--
 Copyright 2002 Sun Microsystems, Inc. All rights reserved.
 SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
-->

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>

<html:html locale="true">

<%@ include file="header.jsp" %>

<body background="UI/images/jmenu/PaperTexture.gif">

<form name="welcome" action="">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr bgcolor="7171A5"> 
      <td width="56%" > 
        <div class="page-title-text" align="left">
         <bean:message key="profile.soaprp"/>
        </div>
      </td>
    </tr>
  </table>

 </form>

 <p> <br>
    <table-label-text> <bean:message key="welcome.protocol.choose"/> </table-label-text>

<p>&nbsp;</p>
</body>
</html:html>
