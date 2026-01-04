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
 % $Id: mylist.jsp,v 1.1.2.1 2002/05/03 17:32:55 deepakv Exp $
 % Copyright 2001 Sun Microsystems, Inc. All rights reserved.
 % Copyright 2001 Sun Microsystems, Inc. Tous droits r駸erv駸.
--%>

<%--
 %   Displays the pet favorites of the user. The favorites list
 %   can be included in any web page. Currently it is included in
 %   the cart screen, when the customer has the opportunity to
 %   add more items just before checkout.
--%>

<%@ taglib uri="/WEB-INF/waftags.tld" prefix="waf" %>


<jsp:useBean
  id="customer"
  class="com.sun.j2ee.blueprints.petstore.controller.web.CustomerWebHelper"
  scope="session"/>

<jsp:useBean
  id="catalog"
  class="com.sun.j2ee.blueprints.catalog.client.CatalogClientHelper"
  scope="session"
/>

<waf:extract id="customer"
             scope="session"
             property="getMyListPreference"
             destinationScope="page"
             destinationId="showFavorites"/>

<waf:extract id="customer"
             scope="session"
             property="getFavoriteCategory"
             destinationScope="page"
             destinationId="favoriteCategory"/>

<waf:extract id="catalog"
             scope="session"
             property="getProducts"
             destinationScope="page"
             destinationId="extractedPage">
   <waf:extract_parameter id="favoriteCategory" scope="page"/>
   <waf:extract_parameter arg="0" type="int"/>
   <waf:extract_parameter arg="5" type="int"/>
   <waf:extract_parameter arg="en_US" type="String"/>
</waf:extract>

<waf:extract id="extractedPage"
             scope="page"
             property="getList"
             destinationScope="request"
             destinationId="extractedCollection"/>

<waf:bool id="showFavorites" scope="page">
  <waf:true>

<table border="0"
       width="100%"
       cellpadding="1"
       cellspacing="0">
<tr>
<td bgcolor="#336666" class="petstore_title" align="center">
<font color="#FFFFFF">My List</font>
</td>
</tr>
<tr>
<td bgcolor="#336666">

<table border="0" 
       width="100%" 
       cellpadding="5" 
       cellspacing="1">
<tr>
<td bgcolor="#FFFFFF" class="petstore">
<waf:list collectionId="extractedCollection" scope="request"><a href="items.screen?product_id=<waf:listItem property="id" />"><waf:listItem property="name" /></a><br></waf:list>
</td>
</tr>
</table>
</td>
</tr>
</table>
</waf:true>
</waf:bool>
