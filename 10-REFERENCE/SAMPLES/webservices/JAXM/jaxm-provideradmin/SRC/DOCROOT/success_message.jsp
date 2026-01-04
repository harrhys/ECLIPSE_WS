<!--
 Copyright 2002 Sun Microsystems, Inc. All rights reserved.
 SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
-->

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>

<html:html locale="true">

<%@ include file="header.jsp" %>

<body background="UI/images/jmenu/PaperTexture.gif">

<form name="welcome">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr bgcolor="7171A5"> 
      <td width="56%" > 
        <div class="page-title-text" align="left"> 
          <bean:message key="update.success"/>
        </div>
      </td>
    </tr>
  </table>

</form>

    <p> <br>
    <table-label-text> 

    <p> 
    <bean:message key="restart.provider"/>
    <P>
    <bean:message key="updates.provider"/>
     
    </table-label-text>

<p>&nbsp;</p>
</body>
</html:html>
