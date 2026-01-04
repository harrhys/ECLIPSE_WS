<!--
 Copyright 2002 Sun Microsystems, Inc. All rights reserved.
 SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
-->

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>

<html:html locale="true">

<%@ include file="header.jsp" %>

<frameset cols="208,582*" frameborder="NO" border="0" framespacing="0" rows="*"> 
  <frame name="leftFrame" src="UI/cluster-tree.jsp" frameborder="YES">
  <frame name="mainFrame" src="welcome_blank.jsp" frameborder="YES">
</frameset>

<noframes>
<body bgcolor="#FFFFFF">
</body>
</noframes>

</html:html>
