<!--
 Copyright 2002 Sun Microsystems, Inc. All rights reserved.
 SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
-->

<%@ taglib uri="http://java.sun.com/jaxm" prefix="jaxm" %>
<%@ page import="javax.xml.soap.*" %>
<%@ page import="javax.xml.messaging.*" %>
<%@ page session="false" %>

<%@ page import="javax.xml.transform.*" %>
<%@ page import="javax.xml.transform.dom.*" %>
<%@ page import="org.w3c.dom.*" %>
<%@ page import="org.w3c.dom.Node" %>
<%@ page import="javax.xml.parsers.*" %>

<jaxm:context />

<jaxm:onMessage msgId="message" resId="reply" useBody="false">

      
<%  
   DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
   DocumentBuilder builder = factory.newDocumentBuilder();
   Document document = builder.newDocument();

   
   Node root = document.createElementNS("http://schemas.xmlsoap.org/soap/envelope/","SOAP-ENV:Envelope"); 
   Node body = document.createElementNS("http://schemas.xmlsoap.org/soap/envelope/","SOAP-ENV:Body"); 
   Node foo = document.createElementNS("http://foo.com/foo","foo:bar"); 

   document.appendChild(root);
   root.appendChild( body );
   body.appendChild( foo );

   DOMSource src=new DOMSource( document );
   SOAPPart sp = reply.getSOAPPart();
   sp.setContent(src);
%>

</jaxm:onMessage>

<b> This is a SOAP service. </b>
