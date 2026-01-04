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
 % $Id: search.jsp,v 1.1.2.1 2002/05/03 17:32:55 deepakv Exp $
 % Copyright 2001 Sun Microsystems, Inc. All rights reserved.
 % Copyright 2001 Sun Microsystems, Inc. Tous droits réservés.
--%>

<%@ taglib uri="/WEB-INF/waftags.tld" prefix="waf" %>

<jsp:useBean
  id="helper"
  class="com.sun.j2ee.blueprints.catalog.client.CatalogClientHelper"
  scope="session"
/>

<waf:extract id="helper"
             scope="session"
             property="searchItems"
             destinationScope="request"
             destinationId="pageresults">
   <waf:extract_parameter parameter="keywords" />
   <waf:extract_parameter parameter="start" default="0" type="int"/>
   <waf:extract_parameter parameter="count" default="4" type="int"/>
   <waf:extract_parameter arg="en_US" type="java.lang.String"/>
</waf:extract>

<waf:extract id="pageresults" scope="request" property="getList"
destinationScope="request" destinationId="extractedCollection" />

<waf:extract id="pageresults" scope="request" property="hasNextPage"
destinationScope="request" destinationId="hasNext" />

<waf:extract id="pageresults" scope="request"
property="hasPreviousPage" destinationScope="request"
destinationId="hasPrevious" />

<waf:listIsEmpty id="extractedCollection" scope="request">
  <waf:true><p class="petstore_title">No results were found for your
  search.</p></waf:true>

  <waf:false><p class="petstore_title">Search Results</p>

<p class="petstore">Items matching any of: <b><waf:parameter
name="keywords" /></b></p>

<table border="0"
       width="100%"
       cellpadding="1"
       cellspacing="0">
<tr>
<td bgcolor="#808080">
<table width="100%"
       cellspacing="0" 
       cellpadding="2" 
       border="0" 
       bgcolor="#FFFFFF">
<waf:list collectionId="extractedCollection" 
          scope="request"><tr><td class="petstore_listing"><a
href="item.screen?item_id=<waf:listItem
property="itemId"/>"><waf:listItem property="attribute"/>
<waf:listItem property="productName"/></a><br> <waf:listItem
property="description"/></td>

<td class="petstore_listing" align="right"><waf:listItem
property="listCost" formatText="currency" locale="en_US"/><br><a href
="cart.do?action=purchase&itemId=<waf:listItem
property="itemId"/>">Add to Cart</a></td></tr></waf:list>
</table>
</td>
</tr>
</table>

<div align="right">
<p class="petstore_listing"><waf:bool id="hasPrevious" scope="request">
  <waf:true>
    <waf:link href="search.screen">
      <waf:queryParameter name="keywords"><waf:parameter name="keywords" /></waf:queryParameter>

      <waf:queryParameter name="start"><jsp:getProperty
name="pageresults" property="startOfPreviousPage" /></waf:queryParameter>

      <waf:queryParameter name="count"><jsp:getProperty
name="pageresults" property="size" /></waf:queryParameter>

      <waf:linkContent>Previous</waf:linkContent>
    </waf:link>
  </waf:true>
</waf:bool>

<waf:bool id="hasNext" scope="request">
  <waf:true>
    <waf:link href="search.screen">
      <waf:queryParameter name="keywords"><waf:parameter name="keywords" /></waf:queryParameter>

      <waf:queryParameter name="start"><jsp:getProperty
name="pageresults" property="startOfNextPage" /></waf:queryParameter>

      <waf:queryParameter name="count"><jsp:getProperty
name="pageresults" property="size" /></waf:queryParameter>

      <waf:linkContent>Next</waf:linkContent>
    </waf:link>
  </waf:true>
</waf:bool></p>
</div>
</waf:false>
</waf:listIsEmpty>

