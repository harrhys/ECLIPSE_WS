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
 % $Id: cart.jsp,v 1.1.2.1 2002/05/03 17:33:20 deepakv Exp $
 % Copyright 2001 Sun Microsystems, Inc. All rights reserved.
 % Copyright 2001 Sun Microsystems, Inc. Tous droits r駸erv駸.
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/waftags.tld" prefix="waf" %>

<jsp:useBean
  id="cart"
  class="com.sun.j2ee.blueprints.petstore.controller.web.ShoppingCartWebHelper"
  scope="session"/>

<waf:extract id="cart"
             scope="session"
             property="getItems"
             destinationScope="request"
             destinationId="extractedCollection">
        <waf:extract_parameter arg="ja_JP" type="java.util.Locale"/>
</waf:extract>

<waf:listIsEmpty id="extractedCollection" scope="request">
  <waf:true><p class="petstore_title">ショッピングカートには何も入ってません。</p></waf:true>

  <waf:false><p class="petstore_title">ショッピングカート</p>

<form action="cart.do">
<input type="hidden" name="action" value="update">

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

<waf:list collectionId="extractedCollection" scope="request">

<tr><td width="50%" class="petstore_listing">
<a href="item.screen?item_id=<waf:listItem
 property="itemId"/>"><waf:listItem property="attribute"/>
<waf:listItem property="name"/> </a></td>

<td class="petstore_listing"><a href
="cart.do?action=remove&itemId=<waf:listItem property="itemId"/>">削除</a></td>

<td class="petstore_listing" align="right"><waf:input type="text" size="4">
  <waf:name>itemQuantity_<waf:listItem property="itemId" /></waf:name>

  <waf:value><waf:listItem property="quantity"/></waf:value></waf:input></td>

<td class="petstore_listing" align="right"> @
<waf:listItem
property="unitCost" formatText="currency" locale="ja_JP" precision="0"/></td></tr></waf:list>

<tr><td class="petstore_listing" colspan="2"><input type="submit" value="カートを更新"></td>

<td class="petstore_listing" align="right"><b>総合計:</b></td>
<waf:extract id="cart"
             scope="session"
             property="getSubtotal"
             destinationScope="page"
             destinationId="subtotal">
        <waf:extract_parameter arg="ja_JP" type="java.util.Locale"/>
</waf:extract>

<td bgcolor="#CCCCFF" class="petstore_listing"
align="right"><waf:getSmartProperty id="subtotal" scope="page"  formatText="currency" locale="ja_JP" precision="0"/>
</td></tr>
</table>
</td>
</tr>
<tr>
<td align="right">
<p class="petstore"><a href="enter_order_information.screen">チェクアウトへ</a>
</p></td>
</tr>
</table>
</form>
</waf:false>
</waf:listIsEmpty>
