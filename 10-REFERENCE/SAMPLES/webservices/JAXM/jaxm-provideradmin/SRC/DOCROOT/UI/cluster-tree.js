/*
 * Copyright 2002 Sun Microsystems, Inc. All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

foldersTree = gFld("<span class='tree-node-text'>JAXM Provider</span>", "../welcome_blank.jsp", 
                'Profiles', "images/jmenu/folder_16_pad.gif", "top")
	
	aux1 = insFld(foldersTree, gFld("<span class='tree-node-text'>Profiles</span>", 
						"../jaxm_profile_default.jsp", 'profiles', 
                                                "images/jmenu/folder_16_pad.gif", "main"))

	     aux2 = insFld(aux1, gFld("<span class='tree-node-text'>ebXML</span>", 
				"../welcome_ebxml_blank.jsp", 'ebXML', "images/jmenu/jaxm_profile02.gif", "ebxml-profile"))

	     	    insDoc(aux2, gLnk(0, "<span class='tree-node-text'>HTTP</span>", 
				"../ebxml_http.jsp", 'http', "images/jmenu/jaxm_http.gif", "http"))
		    insDoc(aux2, gLnk(0, "<span class='tree-node-text'>HTTPS</span>", 
				"../ebxml_https.jsp", 'https', "images/jmenu/jaxm_https.gif", "https"))

    	     aux2 = insFld(aux1, gFld("<span class='tree-node-text'>SOAPRP</span>", 
				"../welcome_soaprp_blank.jsp", 'soapRP', "images/jmenu/jaxm_profile02.gif", "next-profile"))
				
			insDoc(aux2, gLnk(0, "<span class='tree-node-text'>HTTP</span>", 
				"../soaprp_http.jsp", 'http', "images/jmenu/jaxm_http.gif", "http"))
	     		insDoc(aux2, gLnk(0, "<span class='tree-node-text'>HTTPS</span>", 
				"../soaprp_https.jsp", 'https', "images/jmenu/jaxm_https.gif", "https"))
