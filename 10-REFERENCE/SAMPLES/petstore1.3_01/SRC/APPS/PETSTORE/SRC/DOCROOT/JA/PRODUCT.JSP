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
 % $Id: product.jsp,v 1.1.2.1 2002/05/03 17:33:22 deepakv Exp $
 % Copyright 2001 Sun Microsystems, Inc. All rights reserved.
 % Copyright 2001 Sun Microsystems, Inc. Tous droits r駸erv駸.
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/waftags.tld" prefix="waf" %>

<jsp:useBean
  id="catalog"
  class="com.sun.j2ee.blueprints.catalog.client.CatalogClientHelper"
  scope="session"
/>

<p class="petstore_title">このカテゴリの商品</p>

<waf:extract id="catalog"
             scope="session"
             property="getProducts"
             destinationScope="request"
             destinationId="pageresults">
   <waf:extract_parameter parameter="category_id" />
   <waf:extract_parameter parameter="start" default="0" type="int"/>
   <waf:extract_parameter parameter="count" default="2" type="int"/>
   <waf:extract_parameter arg="ja_JP" type="String"/>   
</waf:extract>

<waf:extract id="pageresults" scope="request" property="getList"
destinationScope="request" destinationId="extractedCollection" />

<waf:extract id="pageresults" scope="request" property="hasNextPage"
destinationScope="request" destinationId="hasNext" />

<waf:extract id="pageresults" scope="request"
property="hasPreviousPage" destinationScope="request"
destinationId="hasPrevious" />

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
          scope="request"><tr><td class="petstore_listing"><a href="items.screen?product_id=<waf:listItem property="id" />"><waf:listItem property="name" /></a><br>
<waf:listItem property="description" /></td></tr></waf:list>
</table>
</td>
</tr>
</table>

<div align="right">
<p class="petstore_listing"><waf:bool id="hasPrevious" scope="request">
  <waf:true>
    <waf:link href="product.screen">
      <waf:queryParameter name="category_id"><waf:parameter name="category_id" /></waf:queryParameter>

      <waf:queryParameter name="start"><jsp:getProperty
name="pageresults" property="startOfPreviousPage" /></waf:queryParameter>

      <waf:queryParameter name="count"><jsp:getProperty
name="pageresults" property="size" /></waf:queryParameter>

      <waf:linkContent>前へ</waf:linkContent>
    </waf:link>
  </waf:true>
</waf:bool>

<waf:bool id="hasNext" scope="request">
  <waf:true>
    <waf:link href="product.screen">
      <waf:queryParameter name="category_id"><waf:parameter name="category_id" /></waf:queryParameter>

      <waf:queryParameter name="start"><jsp:getProperty
name="pageresults" property="startOfNextPage" /></waf:queryParameter>

      <waf:queryParameter name="count"><jsp:getProperty
name="pageresults" property="size" /></waf:queryParameter>

      <waf:linkContent>次へ</waf:linkContent>
    </waf:link>
  </waf:true>
</waf:bool></p>
</div>

