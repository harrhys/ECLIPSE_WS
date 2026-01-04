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
 % $Id: item.jsp,v 1.1.2.1 2002/05/03 17:32:54 deepakv Exp $
 % Copyright 2001 Sun Microsystems, Inc. All rights reserved.
 % Copyright 2001 Sun Microsystems, Inc. Tous droits réservés.
--%>

<%@ taglib uri="/WEB-INF/waftags.tld" prefix="waf" %>



<jsp:useBean
  id="catalog"
  class="com.sun.j2ee.blueprints.catalog.client.CatalogClientHelper"
  scope="session"
/>

<waf:extract id="catalog"
             scope="session"
             property="getItem"
             destinationScope="request"
             destinationId="extractedItem">
   <waf:extract_parameter parameter="item_id" />
   <waf:extract_parameter arg="en_US" type="java.lang.String"/>
</waf:extract>

<p class="petstore_title"><waf:getSmartProperty id="extractedItem" 
                      scope="request" 
                      property="attribute" />
<waf:getSmartProperty id="extractedItem" 
                      scope="request" 
                      property="productName"/></p>

<table cellpadding="2" cellspacing="0" border="0" width="100%">
<tr>
<td><img src="images/<waf:getSmartProperty id="extractedItem" scope="request" property="imageLocation"/>"></td>
<td class="petstore" width="100%"><b>List Price:</b>
<waf:getSmartProperty id="extractedItem" 
                      scope="request" 
                      property="listCost" 
                      formatText="currency" 
                      locale="en_US"/><br><br>
<waf:getSmartProperty id="extractedItem" 
                      scope="request" 
                      property="description"/><br><br>
<a href
="cart.do?action=purchase&itemId=<waf:parameter name="item_id"/>">Add to Cart</a></td>
</tr>
</table>
