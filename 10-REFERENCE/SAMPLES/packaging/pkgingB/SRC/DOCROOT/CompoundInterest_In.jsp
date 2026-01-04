<!doctype html public "-//w3c//dtd html 4.0 transitional//en">
<%
double ci_principal = 0;
double ci_time = 0;
double ci_rate = 0;
String value = null;
// get values from session if they exist
if (session != null) {
    try {    
        // get ci_principal 
        value = (String)session.getAttribute("ci_principal");
        if (value != null) 
            ci_principal = (Double.valueOf(value)).doubleValue();
        
        // get ci_time
        value = (String)session.getAttribute("ci_time");
        if (value != null) 
            ci_time = (Double.valueOf(value)).doubleValue();
        
        // get ci_rate
        value = (String)session.getAttribute("ci_rate");
        if (value != null) 
            ci_rate = (Double.valueOf(value)).doubleValue();
    }
    catch (NumberFormatException e) {
        e.printStackTrace();
    }
}

%>
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
<Form name=SI method=GET action="/pkgingB2/CompoundInterestServlet">
<p>Principal (in $) : <input type=text size=4 name=ci_principal value=<%=ci_principal%>>
<br>Time (in months) : <input type=text size=2 name=ci_time value=<%=ci_time%>>
<br>Rate (in percent) : <input type=text size=2 name=ci_rate value=<%=ci_rate%>>
<p>Click Submit to calculate the Compound Interest
<br>&nbsp;
<input type=submit value=submit>
</form>
</body>
</html>
