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
  // we can use either "reply" and explicit calls ( as in this example )
  // or specialized tags ( combined with standard jsp tags )

  Source src = message.getSOAPPart().getContent();
  SOAPPart sp = reply.getSOAPPart();
  sp.setContent(src);
%>

</jaxm:onMessage>
