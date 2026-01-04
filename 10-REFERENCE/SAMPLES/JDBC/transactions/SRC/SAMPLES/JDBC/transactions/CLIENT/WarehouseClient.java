/*
 *
 * Copyright 2002 Sun Microsystems, Inc. All Rights Reserved.
 * 
 * This software is the proprietary information of Sun Microsystems, Inc.  
 * Use is subject to license terms.
 * 
 */

package samples.jdbc.transactions.client;
import samples.jdbc.transactions.ejb.*;
import java.util.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.rmi.PortableRemoteObject;

/*
 * A simple java client. This uses the services provided by the WarehouseBean.
 *
 */

public class WarehouseClient {

   /* 
    * The main method of the client. This invokes the Warehouse bean to use its service.  
	* See appclient documentation in SunONE app server to run the clinet. 
	*/

   public static void main(String[] args) {


       try {
           Context initial = new InitialContext();
           Object objref = initial.lookup("java:comp/env/ejb/jdbc-transactions");

           WarehouseHome home = 
               (WarehouseHome)PortableRemoteObject.narrow(objref, 
                                            WarehouseHome.class);

           Warehouse duke = home.create();
           duke.ship("123", "456", 8);
           System.out.println("status = " + duke.getStatus("123", "456"));
           duke.remove();

           System.exit(0);

       } catch (Exception ex) {
           System.err.println("Caught an exception." );
           ex.printStackTrace();
       }

   } 
} 
