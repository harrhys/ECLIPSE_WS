/**
 * Copyright © 2002 Sun Microsystems, Inc. All rights reserved.
 */
package samples.packaging.pkgingC.client;

import java.util.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.rmi.PortableRemoteObject;

import samples.packaging.pkging.ejb.ModuleLibrary;
import samples.packaging.pkging.ejb.ModuleLibraryHome;

/**
 * Stand-alone client to be run remotely. The client looks up an enterprise bean using
 * JNDI. Invokes a business method 'getPower' and prints the result at the terminal.
 *
 */
public class ModuleClient {
    /**
     * Main method of the client application. The client expects two arguments. First
     * being the remote host name, where the bean has been deployed. Second being the
     * RMI-IIOP port number. These information are needed while making the JNDI lookup.
     * After a successful lookup, the client makes a method call with the remote object
     * reference and prints the result in the terminal.
     * @param args arguments to main.
     */
    public static void main(String[] args) {
        try {
            // get the host and port from the command line
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

            String jndiname = "ejb/SunONE.pkging.pkgingCEJB.ModuleLibrary";
            System.out.println("Doing a JNDI lookup on " + jndiname );
            Object objref = initial.lookup( jndiname );

            ModuleLibraryHome home =
                (ModuleLibraryHome)PortableRemoteObject.narrow(objref,
                                            ModuleLibraryHome.class);
            ModuleLibrary moduleHandle = home.create();

            // Now use the handle for some simple calls
            System.out.println("2 raised to the power of 3 is "+moduleHandle.getPower(2,3));
        }
            catch (Exception e) {
            e.printStackTrace();
        }
    }
}
