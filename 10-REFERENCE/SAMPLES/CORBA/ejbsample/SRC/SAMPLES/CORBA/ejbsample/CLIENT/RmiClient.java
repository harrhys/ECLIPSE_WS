package samples.corba.ejbsample.client; 

import java.util.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.rmi.PortableRemoteObject;

import samples.corba.ejbsample.ejb.CorbaClient; 
import samples.corba.ejbsample.ejb.CorbaClientHome; 

/** RMI/IIOP client for the EJB CorbaClient.
 */
public class RmiClient {

    /** Main method accepting the arguments such as iAS_HOST.DOMAIN.NAME PORT NAME.
     * @param args Command line arguments passed into main method.
     */    
   public static void main(String[] args) {
       try {

           if (args.length != 2) {
               System.out.println("Wrong number of arguments to client");
               System.exit(1);
           }
           String host = args[0];
           String port = args[1];
           Properties env = new Properties();
           env.put("java.naming.factory.initial",
                   "com.sun.jndi.cosnaming.CNCtxFactory");
           env.put("java.naming.provider.url", "iiop://" + host + ":"+port);

           Context initial = new InitialContext(env);
           Object objref = initial.lookup("ejb/TheCorbaClient");

           CorbaClientHome home = (CorbaClientHome)PortableRemoteObject.narrow(objref, CorbaClientHome.class);

           CorbaClient corbaClient = home.create();
           String status = corbaClient.doWork();
           System.out.println("Call of corbaClient.doWork() was a " + status);

       } catch (Exception ex) {
           System.err.println("Caught an unexpected exception!");
           ex.printStackTrace();
       }
       return;
   } 
} 
