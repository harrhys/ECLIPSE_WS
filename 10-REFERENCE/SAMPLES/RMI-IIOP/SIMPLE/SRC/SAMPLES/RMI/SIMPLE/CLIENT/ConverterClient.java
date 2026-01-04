/*
 * Copyright © 2002 Sun Microsystems, Inc. All rights reserved.
 */

package samples.rmi.simple.client;

import java.util.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.rmi.PortableRemoteObject;
import java.math.BigDecimal;
import samples.rmi.simple.ejb.*;

/**
 * A simple java client. This uses the services provided by 
 * the ConverterBean.
 */

public class ConverterClient {

   /*
    * The main method of the client. This invokes the converter bean to use its service.
    * It then asks the bean to convert 100 dollars to yen and 100 yen to euro.
    * The results are printed at the terminal where the client is run.
    * See appclient documentation in SunONE app server to run the clinet.
    *
    */
   public static void main(String[] args) {
       String url = null;
       String jndiname = null;
       boolean acc = true;


       try {

          if (args.length == 2 ) {
	      url = args[0];
	      jndiname = args[1];
	      acc = false;
	      System.out.println("url = "+url);
	  }

  	   Context initial;
	   Object objref;
   	   if (acc) {
	      initial = new InitialContext();
	      Context myEnv = (Context)initial.lookup("java:comp/env");
	      objref = myEnv.lookup("ejb/RMICConverter");
	   } else {
	      Properties env = new Properties();
	      env.put("java.naming.factory.initial",
		      "com.sun.jndi.cosnaming.CNCtxFactory");
	      env.put("java.naming.provider.url", url);
	      initial = new InitialContext(env);
	      objref = initial.lookup(jndiname);
	   }

           ConverterHome home =
               (ConverterHome)PortableRemoteObject.narrow(objref,
                                            ConverterHome.class);

           Converter currencyConverter = home.create();

           BigDecimal param = new BigDecimal ("100.00");
           BigDecimal amount = currencyConverter.dollarToYen(param);
           System.out.println(amount);
           amount = currencyConverter.yenToEuro(param);
           System.out.println(amount);

           System.exit(0);

       } catch (Exception ex) {
           System.err.println("Caught an unexpected exception!");
           ex.printStackTrace();
       }
   }
}
