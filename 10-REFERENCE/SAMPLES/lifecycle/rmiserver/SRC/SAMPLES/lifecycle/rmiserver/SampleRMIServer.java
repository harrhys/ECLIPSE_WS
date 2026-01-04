/*
 *
 * Copyright 2001 Sun Microsystems, Inc. All Rights Reserved.
 *
 * This software is the proprietary information of Sun Microsystems, Inc.
 * Use is subject to license terms.
 *
 */


package samples.lifecycle.rmiserver;

import java.rmi.*;
import java.rmi.server.*;



public class SampleRMIServer extends UnicastRemoteObject
                           implements SampleRemoteInterface
{
    /** 
     * Creates the rmi server 
     */
    public SampleRMIServer() throws RemoteException {
        super();
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new RMISecurityManager());
        }
        String name = "//localhost/SampleRMIServer";
        try {
            Naming.rebind(name, this);
            System.out.println("SampleRMIServer bound");
        } catch (Exception e) {
            System.err.println("SampleRMIServer exception: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
      * Implementation of remote methods 
      */
  
    public void stop() {
	      System.out.println("In stop() of SampleRMIServer");
    }

    public void start() {
	      System.out.println("In start() of SampleRMIServer");
    }

    public static void main(String[] args) {
         try {
            SampleRemoteInterface engine = new SampleRMIServer();
         } catch (Exception e) {
            System.err.println("SampleRMIServer exception: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

