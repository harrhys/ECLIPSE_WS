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
 % $Id: edit_customer.jsp,v 1.1.2.1 2002/05/03 17:33:21 deepakv Exp $
 % Copyright 2001 Sun Microsystems, Inc. All rights reserved.
 % Copyright 2001 Sun Microsystems, Inc. Tous droits r駸erv駸.
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/waftags.tld" prefix="waf" %>

<jsp:useBean
  id="customer"
  class="com.sun.j2ee.blueprints.petstore.controller.web.CustomerWebHelper"
  scope="session"/>

<waf:extract id="customer"
             scope="session"
             property="getContactInfo"
             destinationScope="request"
             destinationId="contactinfo"/>

<waf:extract id="contactinfo"
             scope="request"
             property="getAddress"
             destinationScope="request"
             destinationId="address"/>

<waf:extract id="customer"
             scope="session"
             property="getCreditCard"
             destinationScope="request"
             destinationId="creditCard"/>

<waf:extract id="customer"
             scope="session"
             property="getProfile"
             destinationScope="request"
             destinationId="profile"/>


<p class="petstore_title">顧客情報</p>

<waf:form method="POST" action="customer.do" name="customerform">
<input type="hidden" name="action" value="update"/>

<p class="petstore_title">連絡先</p>

<table cellpadding="5" cellspacing="0" width="100%" border="0">
<tr>
<td class="petstore_form" align="right"><b>姓</b></td> 

<td align="left" colspan="2"><waf:input cssClass="petstore_form"
type="text" name="family_name_a" size="30" maxlength="30">

<waf:value><waf:getSmartProperty id="contactinfo" scope="request"
property="familyName"/></waf:value>

</waf:input></td>
</tr>

<tr>
<td class="petstore_form" align="right"><b>名</b></td> 

<td align="left" colspan="2"><waf:input cssClass="petstore_form"
type="text" size="30" maxlength="30" validation="validation">

<waf:name>given_name_a</waf:name> 
<waf:value><waf:getSmartProperty id="contactinfo" 
                                 scope="request" 
                                 property="givenName" /></waf:value>

</waf:input></td>
</tr>

<tr>
<td class="petstore_form"><b>郵便番号</b> <input class="petstore_form" type="text"
name="postal_code_a" size="12" maxlength="12" value="<waf:getSmartProperty id="address" scope="request" property="zipCode"/>"></td>
</tr>

<tr>
<td class="petstore_form" align="right"><b>都道府県名</b></td>

<td class="petstore_form" align="left"><waf:select size="1"
name="state_or_province_a">
  <waf:selected><waf:getSmartProperty id="address" scope="request" property="state"/></waf:selected>
  <waf:option value="東京都" /> 
  <waf:option value="大阪府" />
  <waf:option value="長野県" /> 
</waf:select></td>
</tr>

<tr>
<td class="petstore_form" align="right"><b>市町村名</b></td>

<td align="left" colspan="2"><input class="petstore_form" type="text" name="city_a"
size="55" maxlength="70" value="<waf:getSmartProperty id="address" scope="request" property="city"/>"></td>
</tr>

<tr>
<td class="petstore_form" align="right"><b>町名番地</b></td>

<td align="left" colspan="2"><waf:input cssClass="petstore_form"
type="text" name="address_1_a" size="55" maxlength="70"
validation="validation">

<waf:value><waf:getSmartProperty id="address" scope="request"
property="streetName1" /></waf:value>

</waf:input></td>
</tr>

<tr>
<td>&nbsp;</td>

<td align="left" colspan="2"><input class="petstore_form" type="text" name="address_2_a"
size="55" maxlength="70" value="<waf:getSmartProperty id="address" scope="request" property="streetName2"/>"></td>
</tr>

<tr>
<td class="petstore_form" align="right"><b>国名</b></td>

<td class="petstore_form" align="left" colspan="2"><waf:select size="1"
name="country_a">
  <waf:selected><waf:getSmartProperty id="address" scope="request" property="country"/></waf:selected>
  <waf:option value="アメリカ">アメリカ</waf:option>
  <waf:option value="カナダ" />
  <waf:option value="日本" /> 
</waf:select></td>
</tr>

<tr>
<td class="petstore_form" align="right"><b>電話番号</b></td>

<td align="left" colspan="2"><input class="petstore_form" type="text"
name="telephone_number_a" size="12" maxlength="70"
value="<waf:getSmartProperty id="contactinfo" scope="request" property="telephone"/>"></td>
</tr>
<tr>
<td class="petstore_form" align="right"><b>E-Mail アドレス</b></td>

<td align="left" colspan="2"><input class="petstore_form" type="text"
name="email_a" size="12" maxlength="70"
value="<waf:getSmartProperty id="contactinfo" scope="request" property="email"/>"></td>
</tr>
</table>

<p class="petstore_title">クレジットカード情報</p>

<table cellpadding="5" cellspacing="0" width="100%" border="0">
<tr>
<td class="petstore_form" align="right"><b>クレジットカード種別</b></td> 
<td align="left" colspan="2">
<waf:select size="1"
name="credit_card_type">
  <waf:selected><waf:getSmartProperty id="creditCard" scope="request" property="cardType"/></waf:selected>
  <waf:option value="Java(TM) Card" />
  <waf:option value="Duke Express" />
  <waf:option value="Meow Card" />
</waf:select>
</tr>
<tr>
<td class="petstore_form" align="right"><b>カード番号</b></td> 
<td align="left" colspan="2"><waf:input cssClass="petstore_form"
type="text" size="30" maxlength="30" validation="validation">

<waf:name>credit_card_number</waf:name> 
<waf:value><waf:getSmartProperty id="creditCard" 
                                 scope="request" 
                                 property="cardNumber" /></waf:value>
</waf:input></td>
</tr>
<tr>
<td class="petstore_form" align="right"><b>有効期限</b></td> 

<td align="left" colspan="2"> 月: <waf:select size="1"
name="credit_card_expiry_month">
  <waf:selected><waf:getSmartProperty id="creditCard" scope="request" property="expiryMonth"/></waf:selected>
  <waf:option value="01" />
  <waf:option value="02" />
  <waf:option value="03" />
  <waf:option value="04" />
  <waf:option value="05" />
  <waf:option value="06" /> 
  <waf:option value="07" /> 
  <waf:option value="08" />
  <waf:option value="09" /> 
  <waf:option value="10" /> 
  <waf:option value="11" /> 
  <waf:option value="12" />
</waf:select>
年: 
<waf:select size="1"
name="credit_card_expiry_year">
  <waf:selected><waf:getSmartProperty id="creditCard" scope="request" property="expiryYear"/></waf:selected>
  <waf:option value="2001" />
  <waf:option value="2002" />
  <waf:option value="2003" />
  <waf:option value="2004" />
</waf:select>
</td>
</tr>
</table>

<p class="petstore_title">プロフィール情報</p>

<table border="0" cellpadding="5" width="100%" cellspacing="0">

<tr>
<td></td>
<td>
わたしの PetStore は次の言語を希望します。
<waf:select size="1" name="language">
  <waf:selected><waf:getSmartProperty id="profile" scope="request" property="preferredLanguage"/></waf:selected>
  <waf:option value="en_US">英語</waf:option>
  <waf:option value="ja_JP">日本語</waf:option>
</waf:select>
</td>
</tr>
<tr>
<td></td>
<td>
私の好きなカテゴリは、
<waf:select size="1" name="favorite_category">
  <waf:selected><waf:getSmartProperty id="profile" scope="request" property="favoriteCategory"/></waf:selected>
  <waf:option value="BIRDS">鳥</waf:option>
  <waf:option value="CATS">猫</waf:option>
  <waf:option value="DOGS">犬</waf:option>
  <waf:option value="FISH">魚</waf:option>
  <waf:option value="REPTILES">爬虫類</waf:option>
</waf:select>

</td>
</tr>

<tr>
<td>
&nbsp;
<waf:checkbox name="mylist_on">
 <waf:checked><waf:getSmartProperty id="profile" scope="request" property="myListPreference"/></waf:checked>
</waf:checkbox>
&nbsp;
</td>
<td>MyList 機能を有効にすることを希望します。  <i>MyList は、お買い物の時に
        目に付くように、お気に入りの商品やカテゴリを表示します。</i>
</tr>

<tr>
<td>
&nbsp;
<waf:checkbox name="banners_on">
 <waf:checked><waf:getSmartProperty id="profile" scope="request" property="bannerPreference"/></waf:checked>
</waf:checkbox>
&nbsp;
</td>
<td>ペットの情報バナー機能を有効にすることを希望します。  <i>Java ペット屋さんは、
        あなたのお気に入りの商品やカテゴリをもとに、お買い物の時に、ペットの情報を表示します。
</td>
</tr></td>
</tr>
</table>

<input class="petstore_form" type="submit" value="更新">
</waf:form>

