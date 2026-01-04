<!--
 Copyright 2002 Sun Microsystems, Inc. All rights reserved.
 SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
-->

<%@ page session="false" %>
<%@ taglib uri="http://java.sun.com/jaxm" prefix="jaxm" %>
<%@ page import="org.dom4j.*" %>

<jaxm:context jndiLookup="Sender"/>

<jaxm:onMessage msgId="received">
  <jaxm:soapBody>
    <m:hello xmlns:m="http://hello.world">Hello  <%=
((Node)receivedBody).getName()
%></m:hello>
  </jaxm:soapBody>
</jaxm:onMessage>
