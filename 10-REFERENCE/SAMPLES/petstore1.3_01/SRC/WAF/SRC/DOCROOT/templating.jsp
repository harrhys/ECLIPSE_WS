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

<br>
<br>
<font size="+3" color="black">Templating</font>
<br><br>
This templating mechanism contains:
<br>
<br>
<table border="1">
 <tr>
  <td width="25%" bgcolor="white">TemplateServlet.java</td>
  <td width="70%" bgcolor="#FFCCFF" wrap="true">This servlet is the core to the templating mechanism. It is important
    to note that this servlet can be used in combination with struts but its use is
    not required. It is better to thing of this servlet as providing a service.
    <br><br>
    The template servlet is called when a usrl is ending with &quot;.screen&quot; is
    called.
    <br><br>
    <bold>Parameters</bold>
    You may specify the language of the page to be displayed using the &quot;language&quot;
    parameter. This parameter will override the session locale of the user.
    <br><br>
    <bold>Configuration</bold>
    <br><br>
    You will need to provide the languages supported by the servlet in the web.xml file
    init parameters. This format of the languages is derived using th codes specified
    in ISO-649 separated by &quot;_&quot;s. The format is Language_Country or 
    Language_Country_Variant. Only the locales supported by the java.util.Locale
    of the corresponding Java Developer Kit you are using may be used.
    <br><br>
    &nbsp;&nbsp;&nbsp;&nbsp; &lt;init-param>
    <br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &lt;param-name>languages&lt;/param-name>
    <br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &lt;param-value>en_US,ja_JP&lt;/param-value>
    <br>&nbsp;&nbsp;&nbsp; &lt;/init-param>
    <br><br>
    Each language will need to provide a corresponding screen defintions formatted
    in the same method of Java Resource Bundles. This format is screendefintions followed 
    by the language specified in ISO-649 language codes separated by a &quot;_&quot;

    <br><br>
    You will also need to provide the default language used by the templating servlet
    to specify the default language by adding the following parameter to your servlet
    initialization.
    <br><br>
    &nbsp;&nbsp;&nbsp; &lt;init-param>
    <br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &lt;param-name>default_language&lt;/param-name>
    <br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &lt;param-value>en_US&lt;/param-value>
    <br>&nbsp;&nbsp;&nbsp; &lt;/init-param>

    
  </td>
 </tr>
 <tr>
  <td width="25%" bgcolor="white">screendefintions_en_US.xml</td>
  <td width="70%" bgcolor="#FFCCFF">One screen definitions for each language is recommended.
     This file contains all needed information needed to display the screens. This file must
     be located in the /WEB-INF/ root directory and must follow the following format.
     <br><br>
     screendefintions_Language_Country or screendefintions_Language_Country_Variant
     <br></br>
     For Example: United States English is screendefintions_en_US.xml
     <br>
     &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
     Japanese is screendefintions_ja_JP
     <br><br>
     <bold>template tag<bold>   
     <br><br>
     This tag is required in every screend defintions file. The location of the file
     must be preceeded by a /.
     <br><br>
     Example:
     <br><br>
     &lt;template>/template.jsp&lt;/template>
     <br><br>
     A screen defintion is defined in XML. The following example better expalins the makeup
     of a screen.
     <br><br>
     &lt;screen>
     <br>&nbsp;&nbsp;&nbsp; &lt;screen-name>main&lt;/screen-name>
     <br>&nbsp;&nbsp;&nbsp; &lt;parameter key="title" value="Welcome to theThe Macro Template Example" direct="true"/>
     <br>&nbsp;&nbsp;&nbsp; &lt;parameter key="banner" value="/banner.jsp" direct="false"/>
     <br>&nbsp;&nbsp;&nbsp; &lt;parameter key="sidebar" value="/sidebar.jsp" direct="false"/>
     <br>&nbsp;&nbsp;&nbsp; &lt;parameter key="body" value="/main.jsp" direct="false"/>
     <br>&nbsp;&nbsp;&nbsp; &lt;parameter key="footer" value="/footer.jsp" direct="false"/>
     <br>&lt;/screen>
  </td>
 </tr>
 <tr>
  <td width="25%" bgcolor="white">tempate.jsp</td>
  <td width="70%" bgcolor="#FFCCFF">This file is used as the template for every page
     in your application. There is one template file for every language. Note you should 
     specify the encoding for the language in the tempalte file using the contentType
     directive:
     <br><br>
     Example:
     <br><br>
     &lt;%@ page contentType="text/html;charset=SJIS" %>
     <br><br>
     The template uses a custom tag to insert the parameters defined in the screen defintions
     files into the template. More details on the IntertTag may be found below.
     <br><br>
     Following is an example of a template file.
    <br><br>
    &nbsp;&lt;%@ page contentType="text/html;charset=SJIS" %>
    <br>&lt;%@ taglib uri="/WEB-INF/macrotemplate.tld" prefix="template" %>
    <p>&lt;html>
    <br>&nbsp;&lt;head>&lt;title>&lt;template:insert parameter="title"/>&lt;/title>&lt;/head>
    <br>&nbsp;&lt;body>
    <br>&nbsp; &lt;template:insert parameter="banner"/>
    <br>&nbsp; &lt;table width="100%" height="100%" border="0" cellpadding="0"cellspacing="0">
    <br>&nbsp;&nbsp;&nbsp; &lt;tr>
    <br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &lt;td valign="top">&lt;template:insert parameter="body"/>&lt;/td>
    <br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &lt;td width="20%" valign="top" >
    <br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &lt;template:insert parameter="sidebar"/>
    <br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &lt;/td>
    <br>&nbsp;&nbsp;&nbsp; &lt;/tr>
    <br>&nbsp;&nbsp;&nbsp; &lt;tr> &lt;td valign="bottom">&lt;template:insert parameter="footer"/>&lt;/td> &lt;/tr>
    <br>&nbsp;&nbsp; &lt;/table>
    <br>&nbsp;&lt;/body>
    <br>&lt;/html>
  </td>
 </tr>
 <tr>
  <td width="25%" bgcolor="white">InsertTag.java</td>
  <td width="70%" bgcolor="#FFCCFF">This custom tag is used by the template servlet to insert
   values into the template.
   <br><br>
   <bold>Paramaters<bold>
   <br><br>
   parameter : the text or page to be included
   <br><br>
   direct : if the dircect attribute direct is &quot;true&quot; the text in the parameter attribute specified in the screen defintions will
   be included as is in the page. If not direct it is assumed that the parameter is a file
   which will be included using a runtime include (performed by the tag).
  </td>
 </tr>
 <tr>
  <td width="25%" bgcolor="white">ChangeLocaleAction.java</td>
  <td width="70%" bgcolor="#FFCCFF">This Action allows you to change the locale for a user session.
     Changing the locale will cause all subsequent pages to be in the locale.
     <br><br>
     The change-locale.do action requires one parameter which is locale.
     <br><br>
     A locale must be specified in the ISO-639 format.
     <br><br>
     The format is Language_Country or Language_Country_Variant
     <br></br>
     For Example: United States English is en_US
     <br>
     &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
     Japanese is ja_JP
  </td>
 </tr>
</table>

