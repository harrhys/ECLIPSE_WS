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


<font size="+3">Extract Property Tag</font>
<br>
<br>
This page demonstrates the List Tag which will let you 
display a list of items.
<br>
<br>

<jsp:useBean
  id="testObject"
  class="com.sun.j2ee.blueprints.waf.test.web.ListTestBean"
  scope="session"
/>


<waf:extract id="testObject"
             scope="session"
             property="getSpecialItems"
             destinationScope="request"
             destinationId="extractedCollection"
>
 <waf:extract_parameter parameter="parameter_1" />
 <waf:extract_parameter arg="5" type="int"/>
</waf:extract>


<form>
  Name:<input type="text" name="parameter_1" value="<waf:parameter name="parameter_1"/>"/>
  <br><input type="submit" value="Post New Value">
</form>



 <table border="1">
  <tr bgcolor="gray">
   <td>Name</td>
   <td>Id</td>
   <td>Price</td>
  </tr>
  <waf:list collectionId="extractedCollection" scope="request" >
   <tr>
    <td><waf:listItem property="name"/></td>
    <td><waf:listItem property="id"/></td>
    <td><waf:listItem property="price" formatText="currency" locale="fr_FR"/></td>
   </tr>
  </waf:list>
 </table>


<br>
<br>
<font size="+1">Usage:</font>
<br>
<br>
&lt;waf:extract id="testObject"
             scope="session"
             property="getSpecialItems"
             destinationScope="request"
             destinationId="extractedCollection"
&gt;
 &lt;waf:extract_parameter parameter="parameter_1" /&gt;
 &lt;waf:extract_parameter arg="5" type="int"/&gt;
<br>
<br>
<strong>Extract Attributes</strong>
<ol>
 <li><strong>id</strong> is the id of the object your are requesting
 <li><strong>scope</strong> is the scope of the object your are requesting
 <li><strong>property</strong> is the method of the object you want to extract the object from
 <li><strong>destinationScope</strong> is the scope the return object will be placed in
 <li><strong>destinationId</strong> is the id the return object will be given in the specified scope
</ol>

<strong>Sub-Tag Parameter Attributes</strong>
These parameters are used to set the arguments to be given to the desitnation method.
any number of parameters may be specified and they must appear in the correct order.
<br>

<ol>
 <li><strong>arg</strong>Use this argument
 <li><strong>parameter</strong>Use a sting value matching the key from the HttpRequest parameter
 <li><strong>type</strong>The type of the arg or parameter. (Only primatives int, float, double, and String are supported)
</ol>


</html>
