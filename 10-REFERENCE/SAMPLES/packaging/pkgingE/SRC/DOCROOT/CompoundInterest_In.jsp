<%--

  Copyright 2002 Sun Microsystems, Inc. All Rights Reserved.

  This software is the proprietary information of Sun Microsystems, Inc.
  Use is subject to license terms.

--%>
<!doctype html public "-//w3c//dtd html 4.0 transitional//en">
<html>
<head>
   <title>CompoundInterest Input</title>
</head>
<body>

<hr WIDTH="100%">
<center><b>Compound Interest</b>
<hr WIDTH="100%"></center>
Enter the elements below to calculate the Compound Interest
<br>&nbsp;
<Form name=SI method=GET action="/pkgingE/compound">
<p>Principal (in $) : <input type=text size=4 name=ci_principal>
<br>Time (in months) : <input type=text size=2 name=ci_time>
<br>Rate (in percent) : <input type=text size=2 name=ci_rate>
<p>Click Submit to calculate the Compound Interest
<br>&nbsp;
<input type=submit value=submit>
</form>
</body>
</html>
