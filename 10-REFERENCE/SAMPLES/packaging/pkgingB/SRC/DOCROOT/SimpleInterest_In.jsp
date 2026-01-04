<!doctype html public "-//w3c//dtd html 4.0 transitional//en">
<%
double si_principal = 0;
double si_time = 0;
double si_rate = 0;
String value = null;
// get values from session if they exist
// this is done to show that session can be shared between WARs in an EAR
if (session != null) {
    try {    
        // get si_principal 
        value = (String)session.getAttribute("si_principal");
        if (value != null) 
            si_principal = (Double.valueOf(value)).doubleValue();
        
        // get si_time
        value = (String)session.getAttribute("si_time");
        if (value != null) 
            si_time = (Double.valueOf(value)).doubleValue();
        
        // get ci_rate
        value = (String)session.getAttribute("si_rate");
        if (value != null) 
            si_rate = (Double.valueOf(value)).doubleValue();
    }
    catch (NumberFormatException e) {
        e.printStackTrace();
    }
}

%><html>
<head>
   <title>SimpleInterest Input</title>
</head>
<body>

<hr WIDTH="100%">
<center><b>Simple Interest</b>
<hr WIDTH="100%"></center>
Enter the elements below to calculate the Simple Interest
<br>&nbsp;
<Form name=SI method=GET action="/pkgingB1/SimpleInterestServlet">
<p>Principal (in $) : <input type=text size=4 name=si_principal value=<%=si_principal%>>
<br>Time (in months) : <input type=text size=2 name=si_time value=<%=si_time%>>
<br>Rate (in percent) : <input type=text size=2 name=si_rate value=<%=si_rate%>>
<p>Click Submit to calculate the Simple Interest
<br>&nbsp;
<input type=submit value=submit>
</form>
</body>
</html>
