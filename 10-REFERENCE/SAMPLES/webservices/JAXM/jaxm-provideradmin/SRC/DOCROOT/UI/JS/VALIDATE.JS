/*
 * Copyright 2002 Sun Microsystems, Inc. All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

//<!-- 
/* ================================================================

 validate.js

JAVASCRIPT CODE TO Validate the values entered in JAXM Admin config
tool screens.

================================================================ */

 function MM_jumpMenu(targ,selObj,restore){ //v3.0
        eval(targ+".location='"+selObj.options[selObj.selectedIndex].value+"'");
        if (restore) selObj.selectedIndex=0;
 }

 function CheckForm( form ){

       // Get the integer representation
       var _maxretries = parseInt( form.maxretries.value );
       var _interval = parseInt( form.interval.value );
       var _records = parseInt( form.records.value );
       var _directory = form.directory.value;
  
       //check if numerical values are entered for the first three.
       if (isNaN(_maxretries) || isNaN(_interval) || isNaN(_records)) {
            alert("Expecting a number.\nPlease try again.");
            return false;
        }
        

        // check the boundaries
	if ((_maxretries <0) || ( _maxretries > 999)) {
	 alert("Maximum Retries is out of range.\nPlease try again.");
         form.maxretries.focus();
	 return false;
        } 

	if ((_interval <100) || ( _interval > 999999)) {
	 alert("Retry Interval is out of range.\nPlease try again.");
         form.interval.focus();
         return false;
	}

	if ((_records <0) || ( _records > 999)) {
	 alert("Records per file is out of range.\nPlease try again.");
         form.records.focus();
         return false;
	}

        //check that directory is not blank
        if (isWhitespace(_directory)) {
            alert("Directory cannot be blank.\nPlease try again");
            return false;
        } 

       }

  function CheckURL( form ){
       var url = form.url.value ;          
       if (isWhitespace(url)) {
        alert("URL cannot be blank.\nPlease try again.");
       }
  }

  // Check whether string s is empty.
  function isEmpty(s) { 
       return ((s == null) || (s.length == 0)); 
  }

  function isWhitespace (s) {
    var i;
    // Is s empty?
    if (isEmpty(s)) return true;

    // Search through string's characters one by one
    // until we find a non-whitespace character.
    // When we do, return false; if we don't, return true.

    for (i = 0; i < s.length; i++) {
    // Check that current character isn't whitespace.
    var c = s.charAt(i);

    if (whitespace.indexOf(c) == -1) return false;
    }

    // All characters are whitespace.
    return true;
  }

//-->