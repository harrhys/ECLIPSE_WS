//<!--

/* ================================================================

   browserVersion.js

JAVASCRIPT CODE TO DETECT BROWSER VENDOR, VERSION, AND OPERATING SYSTEM 

Here is the JavaScript code necessary to detect browser vendor,
version number, and operating system. This code creates a group of
variables which indicate the browser's vendor, version number,
JavaScript version, and operating system.

This code is believed to be compatible with all versions of all
JavaScript-capable browsers on all platforms. It has been tested on
the following operating systems and browser versions:

     Windows NT: Navigator 4, Navigator 3, and Navigator 2; Internet
     Explorer 5; Internet Explorer 3; Opera 3
     Windows 98: Netscape 6;Navigator 4.76; Internet Explorer 4;
     Internet Explorer 5; Internet Explorer 5.5; Opera 5; HotJava 3
     Macintosh: Navigator 4, Internet Explorer 3.01, Internet Explorer 4.02 
     RedHat Linux 6.2: Navigator 4.6; Netscape 6 
     SunOS5: Navigator 3 

Reference from http://www.mozilla.org/docs/web-developer/sniffer/browser_type.html

-January 2001

Modifications to the original code include:
	      * Making the code into a function
	      * pulling variable declarations outside the function
		body, making them globally available (read-only,
		please) - see bottom of this file

================================================================ */



function browserVersion() {

// Ultimate client-side JavaScript client sniff.
// (C) Netscape Communications 1999.  Permission granted to reuse and distribute.
// Revised 17 May 99 to add is_nav5up and is_ie5up (see below).
// Revised 20 Dec 00 to add is_gecko and change is_nav5up to is_nav6up
//                      also added support for IE5.5 Opera4&5 HotJava3 AOLTV

// Everything you always wanted to know about your JavaScript client
// but were afraid to ask. Creates "is_" variables indicating:
// (1) browser vendor:
//     is_nav, is_ie, is_opera, is_hotjava
// (2) browser version number:
//     is_major (integer indicating major version number: 2, 3, 4 ...)
//     is_minor (float   indicating full  version number: 2.02, 3.01, 4.04 ...)
// (3) browser vendor AND major version number
//     is_nav2, is_nav3, is_nav4, is_nav4up, is_nav6,is_nav6up,is_gecko, is_ie3,
           is_ie4, is_ie5, is_ie5up, is_ie4up, is_ie5_5, is_ie5_5up, is_hotjava3,is_hotjava3up,
           is_opera4, is_opera5, is_opera5up
// (4) JavaScript version number:
//     is_js (float indicating full JavaScript version number: 1, 1.1, 1.2 ...)
// (5) OS platform and version:
//     is_win, is_win16, is_win32, is_win31, is_win95, is_winnt, is_win98
//     is_os2
//     is_mac, is_mac68k, is_macppc
//     is_unix
//        is_sun, is_sun4, is_sun5, is_suni86
//        is_irix, is_irix5, is_irix6
//        is_hpux, is_hpux9, is_hpux10
//        is_aix, is_aix1, is_aix2, is_aix3, is_aix4
//        is_linux, is_sco, is_unixware, is_mpras, is_reliant
//        is_dec, is_sinix, is_freebsd, is_bsd
//     is_vms
//
// Note: you don't want your Nav4 or IE4 code to "turn off" or
// stop working when new versions of browsers are released, so
// in conditional code forks, use is_ie5up ("IE 5.0 or greater") 
// is_opera5up ("Opera 5.0 or greater") instead of is_ie5 or is_opera5
// to check version in code which you want to work on future
// versions.

    // convert all characters to lowercase to simplify testing
    agt=navigator.userAgent.toLowerCase();

    // *** BROWSER VERSION ***
    // Note: On IE5, these return 4, so use is_ie5up to detect IE5.
    is_major = parseInt(navigator.appVersion);
    is_minor = parseFloat(navigator.appVersion);

    // Note: Opera and WebTV spoof Navigator.  We do strict client detection.
    // If you want to allow spoofing, take out the tests for opera and webtv.
    is_nav  = ((agt.indexOf('mozilla')!=-1) && (agt.indexOf('spoofer')==-1)
                && (agt.indexOf('compatible') == -1) && (agt.indexOf('opera')==-1)
                && (agt.indexOf('webtv')==-1) && (agt.indexOf('hotjava')==-1));
    is_nav2 = (is_nav && (is_major == 2));
    is_nav3 = (is_nav && (is_major == 3));
    is_nav4 = (is_nav && (is_major == 4));
    is_nav4up = (is_nav && (is_major >= 4));
    is_navonly      = (is_nav && ((agt.indexOf(";nav") != -1) ||
                          (agt.indexOf("; nav") != -1)) );
    is_nav6 = (is_nav && (is_major == 5));
    is_nav6up = (is_nav && (is_major >= 5));
    is_gecko = (agt.indexOf('gecko') != -1);


    is_ie     = ((agt.indexOf("msie") != -1) && (agt.indexOf("opera") == -1));
    is_ie3    = (is_ie && (is_major < 4));
    is_ie4    = (is_ie && (is_major == 4) && (agt.indexOf("msie 5")==-1) );
    is_ie4up  = (is_ie  && (is_major >= 4));
    is_ie5    = (is_ie && (is_major == 4) && (agt.indexOf("msie 5.0")!=-1) );
    is_ie5_5  = (is_ie && (is_major == 4) && (agt.indexOf("msie 5.5") !=-1));
    is_ie5up  = (is_ie  && !is_ie3 && !is_ie4);
    is_ie5_5up =(is_ie && !is_ie3 && !is_ie4 && !is_ie5);

    // KNOWN BUG: On AOL4, returns false if IE3 is embedded browser
    // or if this is the first browser window opened.  Thus the
    // variables is_aol, is_aol3, and is_aol4 aren't 100% reliable.
    is_aol   = (agt.indexOf("aol") != -1);
    is_aol3  = (is_aol && is_ie3);
    is_aol4  = (is_aol && is_ie4);

    is_opera = (agt.indexOf("opera") != -1);
        is_opera4 = (agt.indexOf("opera 4") != -1);
        is_opera5 = (agt.indexOf("opera 5") != -1);
        is_opera5up = (is_opera && (is_major >=4));

    is_webtv = (agt.indexOf("webtv") != -1); 

        
    is_TVNavigator = ((agt.indexOf("navio") != -1) || (agt.indexOf("navio_aoltv") != -1)); 
        is_AOLTV = is_TVNavigator;

    is_hotjava = (agt.indexOf("hotjava") != -1);
    is_hotjava3 = (is_hotjava && (is_major == 3));
    is_hotjava3up = (is_hotjava && (is_major >= 3));

    // *** JAVASCRIPT VERSION CHECK ***
    is_js;
    if (is_nav2 || is_ie3) is_js = 1.0;
    else if (is_nav3 || is_opera) is_js = 1.1;
    else if ((is_nav4 && (is_minor <= 4.05)) || is_ie4) is_js = 1.2;
    else if ((is_nav4 && (is_minor > 4.05)) || is_ie5) is_js = 1.3;
        else if (is_hotjava3up) is_js = 1.4;
    else if (is_nav6 || is_gecko) is_js = 1.5;
    // NOTE: In the future, update this code when newer versions of JS
    // are released. For now, we try to provide some upward compatibility
    // so that future versions of Nav and IE will show they are at
    // *least* JS 1.x capable. Always check for JS version compatibility
    // with > or >=.
        else if (is_nav6up) is_js = 1.5;
    else if (is_ie && (is_major > 5)) is_js = 1.3

    // HACK: no idea for other browsers; always check for JS version with > or >=
    else is_js = 0.0;

    // *** PLATFORM ***
    is_win   = ( (agt.indexOf("win")!=-1) || (agt.indexOf("16bit")!=-1) );
    // NOTE: On Opera 3.0, the userAgent string includes "Windows 95/NT4" on all
    //        Win32, so you can't distinguish between Win95 and WinNT.
    is_win95 = ((agt.indexOf("win95")!=-1) || (agt.indexOf("windows 95")!=-1));

    // is this a 16 bit compiled version?
    is_win16 = ((agt.indexOf("win16")!=-1) || 
               (agt.indexOf("16bit")!=-1) || (agt.indexOf("windows 3.1")!=-1) || 
               (agt.indexOf("windows 16-bit")!=-1) );  

    is_win31 = ((agt.indexOf("windows 3.1")!=-1) || (agt.indexOf("win16")!=-1) ||
                    (agt.indexOf("windows 16-bit")!=-1));

    // NOTE: Reliable detection of Win98 may not be possible. It appears that:
    //       - On Nav 4.x and before you'll get plain "Windows" in userAgent.
    //       - On Mercury client, the 32-bit version will return "Win98", but
    //         the 16-bit version running on Win98 will still return "Win95".
    is_win98 = ((agt.indexOf("win98")!=-1) || (agt.indexOf("windows 98")!=-1));
    is_winnt = ((agt.indexOf("winnt")!=-1) || (agt.indexOf("windows nt")!=-1));
    is_win32 = (is_win95 || is_winnt || is_win98 || 
                    ((is_major >= 4) && (navigator.platform == "Win32")) ||
                    (agt.indexOf("win32")!=-1) || (agt.indexOf("32bit")!=-1));

    is_os2   = ((agt.indexOf("os/2")!=-1) || 
                    (navigator.appVersion.indexOf("OS/2")!=-1) ||   
                    (agt.indexOf("ibm-webexplorer")!=-1));

    is_mac    = (agt.indexOf("mac")!=-1);
    is_mac68k = (is_mac && ((agt.indexOf("68k")!=-1) || 
                               (agt.indexOf("68000")!=-1)));
    is_macppc = (is_mac && ((agt.indexOf("ppc")!=-1) || 
                                (agt.indexOf("powerpc")!=-1)));

    is_sun   = (agt.indexOf("sunos")!=-1);
    is_sun4  = (agt.indexOf("sunos 4")!=-1);
    is_sun5  = (agt.indexOf("sunos 5")!=-1);
    is_suni86= (is_sun && (agt.indexOf("i86")!=-1));
    is_irix  = (agt.indexOf("irix") !=-1);    // SGI
    is_irix5 = (agt.indexOf("irix 5") !=-1);
    is_irix6 = ((agt.indexOf("irix 6") !=-1) || (agt.indexOf("irix6") !=-1));
    is_hpux  = (agt.indexOf("hp-ux")!=-1);
    is_hpux9 = (is_hpux && (agt.indexOf("09.")!=-1));
    is_hpux10= (is_hpux && (agt.indexOf("10.")!=-1));
    is_aix   = (agt.indexOf("aix") !=-1);      // IBM
    is_aix1  = (agt.indexOf("aix 1") !=-1);    
    is_aix2  = (agt.indexOf("aix 2") !=-1);    
    is_aix3  = (agt.indexOf("aix 3") !=-1);    
    is_aix4  = (agt.indexOf("aix 4") !=-1);    
    is_linux = (agt.indexOf("inux")!=-1);
    is_sco   = (agt.indexOf("sco")!=-1) || (agt.indexOf("unix_sv")!=-1);
    is_unixware = (agt.indexOf("unix_system_v")!=-1); 
    is_mpras    = (agt.indexOf("ncr")!=-1); 
    is_reliant  = (agt.indexOf("reliantunix")!=-1);
    is_dec   = ((agt.indexOf("dec")!=-1) || (agt.indexOf("osf1")!=-1) || 
           (agt.indexOf("dec_alpha")!=-1) || (agt.indexOf("alphaserver")!=-1) || 
           (agt.indexOf("ultrix")!=-1) || (agt.indexOf("alphastation")!=-1)); 
    is_sinix = (agt.indexOf("sinix")!=-1);
    is_freebsd = (agt.indexOf("freebsd")!=-1);
    is_bsd = (agt.indexOf("bsd")!=-1);
    is_unix  = ((agt.indexOf("x11")!=-1) || is_sun || is_irix || is_hpux || 
                 is_sco ||is_unixware || is_mpras || is_reliant || 
                 is_dec || is_sinix || is_aix || is_linux || is_bsd || is_freebsd);

    is_vms   = ((agt.indexOf("vax")!=-1) || (agt.indexOf("openvms")!=-1));

    return;


} // browserVersion


    var agt		= 0;
    var is_major	= 0;
    var is_minor	= 0;
    var is_nav		= 0;
    var is_nav2		= 0;
    var is_nav3		= 0;
    var is_nav4		= 0;
    var is_nav4up	= 0;
    var is_navonly	= 0;
    var is_nav6		= 0;
    var is_nav6up	= 0;
    var is_gecko	= 0;
    var is_ie		= 0;
    var is_ie3		= 0;
    var is_ie4		= 0;
    var is_ie4up	= 0;
    var is_ie5		= 0;
    var is_ie5_5	= 0;
    var is_ie5up	= 0;
    var is_ie5_5up	= 0;
    var is_aol		= 0;
    var is_aol3		= 0;
    var is_aol4		= 0;
    var is_opera	= 0;
    var is_opera4	= 0;
    var is_opera5	= 0;
    var is_opera5up	= 0;
    var is_webtv	= 0;
    var is_TVNavigator	= 0;
    var is_AOLTV	= 0;
    var is_hotjava	= 0;
    var is_hotjava3	= 0;
    var is_hotjava3up	= 0;
    var is_js		= 0;
    var is_win		= 0;
    var is_win95	= 0;
    var is_win16	= 0;
    var is_win31	= 0;
    var is_win98	= 0;
    var is_winnt	= 0;
    var is_win32	= 0;
    var is_os2		= 0;
    var is_mac		= 0;
    var is_mac68k	= 0;
    var is_macppc	= 0;
    var is_sun		= 0;
    var is_sun4		= 0;
    var is_sun5		= 0;
    var is_suni86	= 0;
    var is_irix		= 0;
    var is_irix5	= 0;
    var is_irix6	= 0;
    var is_hpux		= 0;
    var is_hpux9	= 0;
    var is_hpux10	= 0;
    var is_aix		= 0;
    var is_aix1		= 0;
    var is_aix2		= 0;
    var is_aix3		= 0;
    var is_aix4		= 0;
    var is_linux	= 0;
    var is_sco		= 0;
    var is_unixware	= 0;
    var is_mpras	= 0;
    var is_reliant	= 0;
    var is_dec		= 0;
    var is_sinix	= 0;
    var is_freebsd	= 0;
    var is_bsd		= 0;
    var is_unix		= 0;
    var is_vms		= 0;

//--> end hide JavaScript
