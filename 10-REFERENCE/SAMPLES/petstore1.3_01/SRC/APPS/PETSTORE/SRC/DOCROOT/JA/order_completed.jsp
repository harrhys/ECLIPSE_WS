<!--
 Copyright 2002 Sun Microsystems, Inc. All rights reserved.
 
 Redistribution and use in source and binary forms, with or without
 modification, are permitted provided that the following conditions
 are met:
 
 - Redistributions of source code must retain the above copyright
   notice, this list of conditions and the following disclaimer.
 
 - Redistribution in binary form must reproduce the above copyright
   notice, this list of conditions and the following disclaimer in
   the documentation and/or other materials provided with the
   distribution.
 
 Neither the name of Sun Microsystems, Inc. or the names of
 contributors may be used to endorse or promote products derived
 from this software without specific prior written permission.
 
 This software is provided "AS IS," without a warranty of any
 kind. ALL EXPRESS OR IMPLIED CONDITIONS, REPRESENTATIONS AND
 WARRANTIES, INCLUDING ANY IMPLIED WARRANTY OF MERCHANTABILITY,
 FITNESS FOR A PARTICULAR PURPOSE OR NON-INFRINGEMENT, ARE HEREBY
 EXCLUDED. SUN AND ITS LICENSORS SHALL NOT BE LIABLE FOR ANY DAMAGES
 SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR
 DISTRIBUTING THE SOFTWARE OR ITS DERIVATIVES. IN NO EVENT WILL SUN
 OR ITS LICENSORS BE LIABLE FOR ANY LOST REVENUE, PROFIT OR DATA, OR
 FOR DIRECT, INDIRECT, SPECIAL, CONSEQUENTIAL, INCIDENTAL OR
 PUNITIVE DAMAGES, HOWEVER CAUSED AND REGARDLESS OF THE THEORY OF
 LIABILITY, ARISING OUT OF THE USE OF OR INABILITY TO USE SOFTWARE,
 EVEN IF SUN HAS BEEN ADVISED OF THE POSSIBILITY OF SUCH DAMAGES.
 
 You acknowledge that Software is not designed, licensed or intended
 for use in the design, construction, operation or maintenance of
 any nuclear facility.
-->

<%--
 % $Id: order_completed.jsp,v 1.1.2.1 2002/05/03 17:33:22 deepakv Exp $
 % Copyright 2001 Sun Microsystems, Inc. All rights reserved.
 % Copyright 2001 Sun Microsystems, Inc. Tous droits r駸erv駸.
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/waftags.tld" prefix="waf" %>

<jsp:useBean
  id="orderresponse"
  class="com.sun.j2ee.blueprints.petstore.controller.events.OrderEventResponse"
  scope="request"/>

<waf:extract id="orderresponse"
             scope="request"
             property="getBillTo"
             destinationScope="request"
             destinationId="contactinfo"/>

<waf:extract id="contactinfo"
             scope="request"
             property="getAddress"
             destinationScope="request"
             destinationId="address"/>




<p class="petstore">注文が完了しました</p>
<br>
<hr>
<br>
あなたの注文 Id はYour order Id is <waf:getSmartProperty id="orderresponse" scope="request" property="orderId" /> です
<br>
<br>
すぐに <waf:getSmartProperty id="contactinfo" scope="request"
property="email" /> 宛に確認メールが届きます
<br>
<br>
Java ペット屋さんデモをご利用いただきましてありがとうございました。


