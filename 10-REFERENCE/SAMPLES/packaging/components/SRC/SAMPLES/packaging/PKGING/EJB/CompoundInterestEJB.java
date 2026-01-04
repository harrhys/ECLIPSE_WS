/**
 * Copyright © 2002 Sun Microsystems, Inc. All rights reserved.
 */

package samples.packaging.pkging.ejb;

import javax.ejb.*;
import java.util.*;
import javax.naming.*;

import samples.packaging.pkging.lib.Calculator;

/**
 * A simple stateless bean which calculates the simple and compound interest for
 * a given set of inputs. The business method for this bean have been as declared in
 * the remote interface(CompoundInterest).
 */
public class CompoundInterestEJB implements javax.ejb.SessionBean {
	// ---------------------------------------------------------------
	// private member data
    private  javax.ejb.SessionContext m_ctx = null;
	private static SimpleInterest interestRemote = null;
	private int FILE_MODE = 1;
	private int MODULE_MODE = 2;

	// the moduleRemote variable is only used for those packaging samples that
	// demonstrate module based registration
    private static ModuleLibrary moduleRemote = null;

    /**
     * Sets the session context. Required by EJB spec.
     * @param ctx A SessionContext object.
     */
    public void setSessionContext(javax.ejb.SessionContext ctx) {
        m_ctx = ctx;
    }

    /**
     * Creates a bean. Required by EJB spec.
     * @exception throws CreateException.
     */
    public void ejbCreate() throws java.rmi.RemoteException, javax.ejb.CreateException {
    }

    /**
     * Removes the bean. Required by EJB spec.
     */
    public void ejbRemove() {
    }

    /**
     * Loads the state of the bean from secondary storage. Required by EJB spec.
     */
    public void ejbActivate() {
    }

    /**
     * Serializes the state of the bean to secondary storage. Required by EJB spec.
     */
    public void ejbPassivate() {
    }

    /**
     * Required by EJB spec.
     */
	public CompoundInterestEJB() {
	}

    /**
     * Returns the simple interest for a given set of inputs.
     * @param principal principal amount in double
     * @param time time in months
     * @param rate rate in percentage
     * @return returns the simple interest
     * @exception throws a RemoteException.
     */
	public double getSimpleInterest(double principal, double time, double rate) throws java.rmi.RemoteException	{
	   double interest = 0;

       if (interestRemote == null) {
           interestRemote = this.getremoteEJB();
       }

       if (interestRemote == null) {
          System.out.println("Could not get remote interface to SimpleInterest");
          throw new java.rmi.RemoteException("Could not get interface to SimpleInterest");
       }

       interest = interestRemote.getSimpleInterest(principal,time,rate);
       return interest;
	}

    /**
     * Returns the compound interest for a given set of inputs: I = P[ (1 + r)^t -1 ]
     * @param principal principal amount in double
     * @param time time in months
     * @param rate rate in percentage
     * @return returns the compound interest
     * @exception throws a RemoteException.
     */
	public double getCompoundInterest(double principal, double time, double rate) throws java.rmi.RemoteException {
		double interest = 0;
		double amount = 0;


		// if the env-entry library is set to "file"
		if (this.getLibraryMode() == FILE_MODE) {
            Calculator calc = new Calculator();

		    amount = calc.getSum(1,rate/100);
		    amount = calc.getPower(amount,time/12);
		    amount = calc.getProduct(amount,principal);

		    interest = calc.getDifference(amount,principal);
		}

		// if the env-entry library is set to "module"
		if (this.getLibraryMode() == MODULE_MODE) {

		    // get handle to remote module library
		    if (moduleRemote == null) {
                moduleRemote = this.getremoteModuleEJB();
            }

            if (moduleRemote  == null) {
                System.out.println("Could not get remote interface to ModuleLibrary");
                throw new java.rmi.RemoteException("Could not get interface to ModuleLibrary");
            }


	        try {
        		amount = moduleRemote.getSum(1,rate/100);
		        amount = moduleRemote.getPower(amount,time/12);
		        amount = moduleRemote.getProduct(amount,principal);

		        interest = moduleRemote.getDifference(amount,principal);
	        }
	        catch (Exception e) {
	            System.out.println("Exception thrown:"+e.getMessage());
	            e.printStackTrace();
	        }
	    }

		return interest;
	}

	// ---------------------------------------------------------------
	// private methods
    private SimpleInterest getremoteEJB() {

         SimpleInterestHome myInterestHome = null;
         SimpleInterest myInterestRemote = null;

         InitialContext initContext = null;
         System.out.println("\nInside SimpleInterestEJB.getRemoteEJB...");

         System.out.println("Retrieving JNDI initial context");
         try    {
            initContext = new javax.naming.InitialContext();
         }
         catch (Exception e) {
            System.out.println("Exception creating InitialContext: " + e.toString());
            return null;
         }
         if (initContext == null) {
            System.out.println("Null Pointer - JNDI context");
            return null;
         }

        try {
          System.out.println("Looking up SimpleInterest bean home interface");
          String JNDIName = "java:comp/env/ejb/SunONE.pkging.pkgingEJB.SimpleInterest";
          System.out.println("Looking up: " + JNDIName);
          myInterestHome = (SimpleInterestHome)initContext.lookup(JNDIName);
        }
        catch(Exception e) {
           System.out.println("SimpleInterest bean home not found - " +  "Is bean registered with JNDI?: " + e.toString());
            e.printStackTrace();
        return null;
        }
        try {
          System.out.println("Creating the SimpleInterest bean");
          myInterestRemote = myInterestHome.create();
        }
        catch(CreateException e) {
          System.out.println("Could not create the SimpleInterest bean: "+
           e.toString());
          e.printStackTrace();
          return null;
        }
        catch(java.rmi.RemoteException e1) {
          System.out.println("Could not create the SimpleInterest bean: "+e1.toString());
          e1.printStackTrace();
          return null;
        }

        return myInterestRemote;

    }
	/**
	 * This private method returns the library mode being used for the bean.
	 * The mode should be either FILE_MODE or LIBRARY_MODE.
	 * FILE_MODE is returned when the env-entry library is set to "file".
	 * It means that the CompoundInterest bean should instantiate the class "Calculator".
	 *
	 * If env-entry is set to "module" then MODULE_MODE is returned.
	 * In this case the bean should make a JNDI call to the bean "ModuleLibrary".
	 */
	private int getLibraryMode() {

		String env_entry = null;
		int mode = 0;

		// get the entry java:comp/env/library to check if the value is "file" or "module"
		// if value is "file" then we instatiate the class samples.pkging.lib.Calculator
		// if value is "module" then we do a JNDI lookup for the bean "ModuleLibrary"
		// This is done to support the same code for all packaging samples

		try {

              Context initial = new InitialContext();
              Context environment = (Context)initial.lookup("java:comp/env");

              env_entry = (String)environment.lookup("library");
              if (env_entry != null) {
                System.out.println("library mode is set to "+env_entry);
              }
              else {
                System.out.println("Lookup failed");
              }

              if (env_entry.equals("file")) {
                mode = FILE_MODE;
              }

              if (env_entry.equals("module")) {
                mode = MODULE_MODE;
              }

           } catch (NamingException ex) {
                throw new EJBException("NamingException: " +
                   ex.getMessage());
           }
	    return mode;
	}


    /**
     * This method returns a remote interface to the ModuleLibrary bean. This is called
     * for the samples that access the library functions (in the classes Calculator and DateLibrary)
     * when they are wrapped in the ModuleLibrary bean.
     */
    private ModuleLibrary getremoteModuleEJB() {

         ModuleLibraryHome myModuleHome = null;
         ModuleLibrary myModuleRemote = null;

         InitialContext initContext = null;
         System.out.println("\nInside SimpleInterestEJB.getRemoteEJB...");

         System.out.println("Retrieving JNDI initial context");
         try    {
            initContext = new javax.naming.InitialContext();
         }
         catch (Exception e) {
            System.out.println("Exception creating InitialContext: " + e.toString());
            return null;
         }
         if (initContext == null) {
            System.out.println("Null Pointer - JNDI context");
            return null;
         }

        try {
          System.out.println("Looking up ModuleLibrary bean home interface");
          String JNDIName = "java:comp/env/ejb/SunONE.pkgingEJB.ModuleLibrary";
          System.out.println("Looking up: " + JNDIName);
          myModuleHome = (ModuleLibraryHome)initContext.lookup(JNDIName);
        }
        catch(Exception e) {
           System.out.println("ModuleLibrary bean home not found - " +  "Is bean registered with JNDI?: " + e.toString());
            e.printStackTrace();
        return null;
        }
        try {
          System.out.println("Creating the ModuleLibrary bean");
          myModuleRemote = myModuleHome.create();
        }
        catch(CreateException e) {
          System.out.println("Could not create the ModuleLibrary bean: "+
           e.toString());
          e.printStackTrace();
          return null;
        }
        catch(java.rmi.RemoteException e1) {
          System.out.println("Could not create the ModuleLibrary bean: "+e1.toString());
          e1.printStackTrace();
          return null;
        }

        return myModuleRemote;

    }

}
