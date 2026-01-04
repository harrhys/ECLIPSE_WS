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

<%@ taglib uri="/WEB-INF/waftags.tld" prefix="waf" %>
<html>


<font size="+3">Smart Property Tag</font>
<br>
<br>
This page demonstrates the Smart Tag which will let you 
display a list of items.
<br>
<br>

<jsp:useBean
  id="testObject"
  class="com.sun.j2ee.blueprints.waf.test.web.ListTestBean"
  scope="session"
/>

The property with Currency in the French Locale is: 
  <waf:getSmartProperty id="testObject"
                                       scope="session"
                                       property="price"
                                       formatText="currency"
                                       locale="fr_FR"/>
<br>
<br>The property as a decimal in German Locale is: 
  <waf:getSmartProperty id="testObject"
                                       scope="session"
                                       property="price"
                                       formatText="number"
                                       locale="de_DE"/>

<br>
<br>The property as currency US English Locale is: 
  <waf:getSmartProperty id="testObject"
                                       scope="session"
                                       property="price"
                                       formatText="currency"
                                       locale="en_US"/>
<br>
<br>The property as a decimal in US English Locale is: 
  <waf:getSmartProperty id="testObject"
                                       scope="session"
                                       property="price"
                                       formatText="number"
                                       locale="en_US"/>

 </html>
