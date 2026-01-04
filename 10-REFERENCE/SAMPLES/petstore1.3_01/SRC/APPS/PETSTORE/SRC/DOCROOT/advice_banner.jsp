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
 % $Id: advice_banner.jsp,v 1.1.2.1 2002/05/03 17:32:52 deepakv Exp $
 % Copyright 2001 Sun Microsystems, Inc. All rights reserved.
 % Copyright 2001 Sun Microsystems, Inc. Tous droits réservés.
--%>

<%--
 %   Displays the pet favorites of the user. The favorites list
 %   can be included in any web page. Currently it is included in
 %   the cart screen, when the customer has the opportunity to
 %   add more items just before checkout.
--%>

<%@ taglib uri="/WEB-INF/waftags.tld" prefix="waf" %>



<jsp:useBean
  id="bannerhelper"
  class="com.sun.j2ee.blueprints.petstore.controller.web.ShoppingClientBannerHelper"
  scope="session"
/>



<waf:extract id="customer"
             scope="session"
             property="getBannerPreference"
             destinationScope="page"
             destinationId="showBanners"/>

<waf:extract id="customer"
             scope="session"
             property="getFavoriteCategory"
             destinationScope="page"
             destinationId="favoriteCategory"/>

<waf:extract id="bannerhelper"
             scope="session"
             property="getBanner"
             destinationScope="page"
             destinationId="bannername">
   <waf:extract_parameter id="favoriteCategory" scope="page"/>
</waf:extract>

<waf:bool id="showBanners" scope="page">
  <waf:true>

<table border="0"
       width="100%"
       bgcolor="#336666"
       cellpadding="1"
       cellspacing="0">
<tr>
<td align="center">
<image src="images/<waf:getSmartProperty id="bannername" scope="page"/>"/>
</td>
</tr>
</table>
</waf:true>
</waf:bool>
