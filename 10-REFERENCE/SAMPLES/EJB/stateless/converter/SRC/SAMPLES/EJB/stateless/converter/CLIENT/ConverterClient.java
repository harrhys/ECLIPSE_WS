/**
 * Copyright © 2002 Sun Microsystems, Inc. All rights reserved.
 */

package samples.ejb.stateless.converter.client;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.rmi.PortableRemoteObject;
import java.math.BigDecimal;
import samples.ejb.stateless.converter.ejb.*;

/**
 * A simple java client. This uses the services provided by the <code>ConverterBean</code> and
 * converts 100 US dollars to Yen and 100 Yen to Euro. 
 * <p>In this regard, it does the following in order
 * <ul>
 * <li>Locates the home interface of the enterprise bean
 * <li>Gets a reference to the remote interface
 * <li>Invokes business methods
 * </ul>
 * <br>
 * <b>Locating the home interface:</b>
 * <blockquote><pre>
 *	Context initial = new InitialContext();
 *	Context myEnv = (Context)initial.lookup("java:comp/env");
 *	Object objref = myEnv.lookup("ejb/SimpleConverter");
 *  ConverterHome home = (ConverterHome)PortableRemoteObject.narrow(objref, ConverterHome.class);
 * </pre></blockquote>
 * <br>
 * <b>Creating the remote interface:</b>
 * <blockquote><pre>
 *	Converter currencyConverter = home.create();
 * </pre></blockquote>
 * <br>
 * <b>Invoking business methods:</b>
 * <blockquote><pre>
 *  BigDecimal param = new BigDecimal ("100.00");
 *	amount = currencyConverter.dollarToYen(param);
 *  amount = currencyConverter.yenToEuro(param);
 * </pre></blockquote>
 * <br>
 * <b>Output:</b>
 * <pre>
 * 12160.00
 * 0.77
 * </pre>
 *
 *
 */

public class ConverterClient {

   /**
    * The main method of the client. This invokes the <code>ConverterBean</code> to use
    * its services. It then asks the bean to convert 100 dollars to yen and
    * 100 yen to euro. The results are printed at the terminal where the client is run.
    * See <code>appclient</code> documentation in SunONE app server to run the clinet.
    *
    */
   public static void main(String[] args) {
       try {
           Context initial = new InitialContext();
           Context myEnv = (Context)initial.lookup("java:comp/env");
           Object objref = myEnv.lookup("ejb/SimpleConverter");

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
