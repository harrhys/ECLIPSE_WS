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


<font size="+3">List Tag</font>
<br>
<br>
This page demonstrates the List Tag which will let you 
display a list of items.
<br>
<br>
<%
// This scriptlet is only to provide test data for the list tag
// In your application you should use the SmartBean tag to place
// the data in the proper scope

java.util.ArrayList testList = new java.util.ArrayList();
testList.add("Test Data 1");
testList.add("Test Data 2");
testList.add("Test Data 3");
testList.add("Monkeys rule!!");

// put the testList in the request scope
request.setAttribute("myCollection", testList);

%>

<waf:list collectionId="myCollection" scope="request" >
 <br>Item=<waf:listItem/>
</waf:list>

<br>
<br>
<font size="+1">Usage:</font>
<br>
<br>
&lt;waf:list collectionId="myCollection" scope="request" /&gt;
<br>&nbsp;&nbsp;&lt;waf:listItem/&gt;
<br>&lt;/waf:list/&gt;
<br>
<br>
Another example here is used with the <a href="extractpropertytag.screen">extractpropertytag</a> example.
This exampel will format numbers for the respective locales either as currency or numbers with
the specified precision.
<br>
<br>
<br> &lt;table border="1"&gt;
<br>  &nbsp;&lt;tr bgcolor="gray"&gt;
<br>   &nbsp;&nbsp;&lt;td&gt;Name&lt;/td&gt;
<br>   &nbsp;&nbsp;&lt;td&gt;Id&lt;/td&gt;
<br>   &nbsp;&nbsp;&lt;td&gt;Price&lt;/td&gt;
<br>  &nbsp;&lt;/tr&gt;
<br>  &nbsp;&lt;waf:list collectionId="extractedCollection" scope="request" &gt;
<br>   &nbsp;&nbsp;&lt;tr&gt;
<br>   &nbsp;&nbsp;&nbsp;&lt;td&gt;&lt;waf:listItem property="name"/&gt;&lt;/td&gt;
<br>   &nbsp;&nbsp;&nbsp;&lt;td&gt;&lt;waf:listItem property="id"/&gt;&lt;/td&gt;
<br>   &nbsp;&nbsp;&nbsp;&lt;td&gt;&lt;waf:listItem property="price" formatText="currency" locale="fr_FR"/&gt;&lt;/td&gt;
<br>   &nbsp;&nbsp;&lt;/tr&gt;
<br>  &nbsp;&lt;/waf:list&gt;
<br>&lt;/table&gt;
<br>
<br>

<strong>List Tag Attributes</strong>
<ol>
 <li><strong>collectionId</strong> is equal to the id of an Iterator object in.
 <li><strong>scope</strong> is the scope of the Iterator to be used.
</ol>
<br>
<br>
<strong>ListItem Tag Attributes</strong>
<br>
These Methods provide methods for specify what properties to extract from the items in 
the list. If no attibutes are displated the default "toString" results will be ouputed.
<br>
There are many utiltity methods here for formatting number results in different locales.
<ol>
 <li><strong>property</strong> (not required) The method to extract data from on the Iterator item. This will default to "toString" if not specified.
 <li><strong>formatText</strong> (not required)  Used to specify what format to apply to the numbers. Currently "number" and "currency" are supported.
 <li><strong>locale</strong> (not required) The locale to use when formatting text. This attribute is only used when formatting the number.
 <li><strong>precision</strong> (not required) The precision of decimal places to use when the formatText attribute is used. 
 <li><strong>numberFormatPattern</strong> (not required) The pattern of text used when the formatText attribute is used. Example "$####.00" 
</ol>

</html>
