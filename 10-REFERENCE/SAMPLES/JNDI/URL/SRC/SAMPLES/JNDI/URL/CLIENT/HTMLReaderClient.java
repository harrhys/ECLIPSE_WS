/*
 *
 * Copyright 2001 Sun Microsystems, Inc. All Rights Reserved.
 * 
 * This software is the proprietary information of Sun Microsystems, Inc.  
 * Use is subject to license terms.
 * 
 */
package samples.jndi.url.client;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.rmi.PortableRemoteObject;
import samples.jndi.url.ejb.*;

public class HTMLReaderClient {

   public static void main(String[] args) {
       try {
           Context initial = new InitialContext();
           Object objref = initial.lookup("java:comp/env/ejb/SimpleHTMLReader");

           HTMLReaderHome home = 
               (HTMLReaderHome)PortableRemoteObject.narrow(objref, 
                                            HTMLReaderHome.class);

           HTMLReader htmlReader = home.create();
           StringBuffer contents = htmlReader.getContents();
	   System.out.println("The contents of the HTML page follows:\n");
           System.out.print(contents);

           System.exit(0);

       } catch (Exception ex) {
           System.err.println("Caught an unexpected exception!");
           ex.printStackTrace();
       }
   } 
} 
