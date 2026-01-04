package samples.corba.ejbsample.ejb;

import java.util.*;
import java.io.*;
import javax.naming.*;

import org.omg.CORBA.*;

import demos.simple.Simple.*;
//import com.iona.corba.util.SystemExceptionDisplayHelper;

import samples.corba.common.OrbSingleton;


public class CorbaClientEJB implements javax.ejb.SessionBean {

    private transient javax.ejb.SessionContext m_ctx = null;
    private ORB orb;
    private String iorFile1 = null;
    private String iorFile2 = null;
    private org.omg.CORBA.Object objref1 = null;
    private org.omg.CORBA.Object objref2 = null;
    private SimpleObject simple1 = null;
    private SimpleObject simple2 = null;

    public void setSessionContext(javax.ejb.SessionContext ctx) {
        m_ctx = ctx;
    }

    public void ejbCreate() throws java.rmi.RemoteException, javax.ejb.CreateException
    {
      System.out.println("CorbaClientEJB: ejbCreate() started on obj " + this);
      String orbDomainName = null;
      String orbCfgDir = null;

      try {
          InitialContext ic = new InitialContext();
          orbDomainName = (String)ic.lookup("java:comp/env/orbDomainName");
          orbCfgDir = (String)ic.lookup("java:comp/env/orbCfgDir");
          iorFile1 = (String)ic.lookup("java:comp/env/simpleObjectIor1");
          iorFile2 = (String)ic.lookup("java:comp/env/simpleObjectIor2");
      } catch (NamingException ex) {
        throw new javax.ejb.CreateException("Could not access ORB and/or IOR environment variables.");
      }

      System.out.println("CorbaClientEJB: Initializing ORB");
      System.out.println("CorbaClientEJB: orbDomainName=" + orbDomainName);
      System.out.println("CorbaClientEJB: orbCfgDir=" + orbCfgDir);
      System.out.println("CorbaClientEJB: iorFile1=" + iorFile1);
      System.out.println("CorbaClientEJB: iorFile1=" + iorFile1);

      try {
		OrbSingleton orbSingleton = OrbSingleton.getInstance (orbDomainName, orbCfgDir);
		orb = orbSingleton.getOrb();
      }
      catch (Exception ex) {
        throw new javax.ejb.CreateException("CorbaClientEJB: Failed to get ORB reference.");
      }

      try {
        initObjectRefs();
      }
      catch(Exception e)
      {
        orb.shutdown(true);
        throw new javax.ejb.CreateException("Error initializing object references.");
      }
      System.out.println("CorbaClientEJB: ejbCreate() finished on obj " + this);
    }

    public void ejbRemove() {
        System.out.println("CorbaClientEJB: ejbRemove() on obj " + this);
        orb.shutdown(true);
    }

    public void ejbPassivate() {
        System.out.println("CorbaClientEJB: ejbPassivate() on obj " + this);
    }

    public void ejbActivate() {
        System.out.println("CorbaClientEJB: ejbActivate() on obj " + this);
    }

    public void CorbaClient() {}

    /** Called by Servlet or RMI/IIOP client to call a method on CORBA object.
     * @throws RemoteException
     * @return Result of type String.
     */    
    public String doWork() throws java.rmi.RemoteException {
      System.out.println("CorbaClientEJB: doWork() started on obj " + this);
      System.out.println("CorbaClientEJB: Invoking method on first object.");

      String status = "success";

      try {
          simple1.call_me();
      }
      catch (org.omg.CORBA.COMM_FAILURE e) {
        System.out.println("Caught org.omg.CORBA.COMM_FAILURE exception." + e.toString());
        attemptInitObjectRefs();
      }
      catch (org.omg.CORBA.TRANSIENT e) {
        System.out.println("Caught org.omg.CORBA.TRANSIENT exception." + e.toString());
        attemptInitObjectRefs();
      }
      catch (SystemException e) {
     //   System.out.println("Caught exception: " + SystemExceptionDisplayHelper.toString(e));
        throw new java.rmi.RemoteException("Call to first CORBA method failed.");
      }
      catch (Exception e) {
        System.out.println("Caught unexpected exception." + e.toString());
        throw new java.rmi.RemoteException("Call to first CORBA method failed.");
      }

        // Invoke method on second object
        //
      System.out.println("CorbaClientEJB: Invoking method on second object.\n");

      try {
          simple2.call_me();
      }
      catch (org.omg.CORBA.COMM_FAILURE e) {
        System.out.println("Caught org.omg.CORBA.COMM_FAILURE exception." + e.toString());
        attemptInitObjectRefs();
        status = "failure";
      }
      catch (org.omg.CORBA.TRANSIENT e) {
        System.out.println("Caught org.omg.CORBA.TRANSIENT exception.\n" + e.toString());
        attemptInitObjectRefs();
        status = "failure";
      }
      catch (SystemException e) {
        //System.out.println("Caught exception: " + SystemExceptionDisplayHelper.toString(e));
        throw new java.rmi.RemoteException("Call to second CORBA method failed.");
      }
      catch (Exception e) {
        System.out.println("Caught unexpected exception." + e.toString());
        throw new java.rmi.RemoteException("Call to second CORBA method failed.");
      }
      System.out.println("CorbaClientEJB: doWork() finished on obj " + this);

      return status;
    }

    /** Called by Servlet or RMI/IIOP client to call a method on CORBA object.
     * @throws RemoteException
     * @return Result of type string (Success|Failure).
     */    
    private static org.omg.CORBA.Object import_object(ORB orb, String filename)
      throws FileNotFoundException, IOException
    {
      String ior = null;
      RandomAccessFile FileStream = null;

      System.out.println("CorbaClientEJB: Reading object reference from " + filename);

      FileStream = new RandomAccessFile(filename,"r");
      ior = FileStream.readLine();
      return orb.string_to_object(ior);
    }

    private void initObjectRefs()
      throws Exception
    {
      try {
        System.out.println("CorbaClientEJB: Invoking import_object() for first object");
        objref1 = import_object(orb, iorFile1);
        System.out.println("CorbaClientEJB: Invoking import_object() for second object");
        objref2 = import_object(orb, iorFile2);
      }
      catch(Exception e)
      {
        throw new Exception("Error accessing IOR file.");
      }

      System.out.println("CorbaClientEJB: Invoking narrow on first object.");
      simple1 = SimpleObjectHelper.narrow(objref1);

      System.out.println("CorbaClientEJB: Invoking narrow on second object.\n");
      simple2 = SimpleObjectHelper.narrow(objref2);
    }

    private void attemptInitObjectRefs()
    {
      try {
        System.out.println("CorbaClientEJB: Trying to reinitialize object refs.\n");
        initObjectRefs();
      }
      catch(Exception e)
      {
        System.out.println("Could not reinitialize object refs.\n");
      }
    }
}
