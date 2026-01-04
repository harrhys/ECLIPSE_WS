<!--
 Copyright 2002 Sun Microsystems, Inc. All rights reserved.
 SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
-->

<%@ page session="false" %>
<%@ taglib uri="http://java.sun.com/jaxm" prefix="jaxm" %>

<jaxm:context jndiLookup="Sender"/>

<jaxm:call endpoint='<%= jaxmPageURI + "/hello.jsp" %>' resId="sres" msgId="sreq" >
  <jaxm:soapBody>
    <m:hello xmlns:m="http://hello.world">Bar </m:hello>
  </jaxm:soapBody>
</jaxm:call>

<h1>Request dump</h1>

<pre>
<jaxm:dump msgId="sreq" />
</pre>

<hr>

<h1>Response dump</h1>

<pre>
<jaxm:dump msgId="sres" />
</pre>

<hr>

<h1>Second call</h1>

<jaxm:call endpoint='<%= jaxmPageURI + "/hello.jsp" %>' resId="sres1" msgId="sreq1" >
  <jaxm:soapBody>
    <m:hello xmlns:m="http://hello.world">Foo </m:hello>
  </jaxm:soapBody>
</jaxm:call>

<h1>Request dump</h1>

<pre>
<jaxm:dump msgId="sreq1" />
</pre>

<hr>

<h1>Response dump</h1>

<pre>
<jaxm:dump msgId="sres1" />
</pre>

<hr>
