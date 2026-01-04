<!--
 Copyright 2002 Sun Microsystems, Inc. All rights reserved.
 SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
-->

<%@ taglib uri="http://java.sun.com/jaxm" prefix="jaxm" %>
<%@ page import="javax.xml.soap.*" %>
<%@ page import="javax.xml.messaging.*" %>
<%@ page session="false" %>

<%@ page import="javax.xml.transform.*" %>
<%@ page import="javax.xml.transform.stream.*" %>

<jaxm:context />

<jaxm:onMessage msgId="message" resId="reply" useBody="false">

<% 
  pageContext.setAttribute( "reply", message );
%>

</jaxm:onMessage>
