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
 % $Id: banner.jsp,v 1.1.2.1 2002/05/03 17:33:20 deepakv Exp $
 % Copyright 2001 Sun Microsystems, Inc. All rights reserved.
 % Copyright 2001 Sun Microsystems, Inc. Tous droits r駸erv駸.
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/waftags.tld" prefix="waf" %>

<form action="search.screen">
<table bgcolor="#FFFFFF" width="100%" cellpadding="0" 
cellspacing="0" border="0">

<tr><td align="left" valign="middle"><a href="main.screen"><img
src="images/banner_logo.gif" alt="Java ペット屋さんデモ ロゴマーク"
border="0"></td>

<td class="petstore" align="right" valign="middle"><input
class="petstore_listing" type="text" name="keywords" size="8"> <input
class="petstore_listing" type="submit" value="検索"><br><a
href="customer.screen">顧客情報</a> | <a href="cart.do">カート</a> |
<waf:bool id="j_signon" scope="session">
  <waf:true><a href="signoff.do">ログアウト</a></waf:true>
  <waf:false><a href="signon_welcome.screen">ログイン</a></waf:false>
</waf:bool>
<br><a href="changelocale.do?locale=en_US"><img src="images/us_flag.gif" border="0"/></a>
        &nbsp;<a href="changelocale.do?locale=ja_JP"><img src="images/ja_flag.gif" border="0"/></a>
</td>
</tr>
<tr>
<td colspan="2"><hr noshade="noshade" size="1"></td>
</tr>
</table>
</form>


