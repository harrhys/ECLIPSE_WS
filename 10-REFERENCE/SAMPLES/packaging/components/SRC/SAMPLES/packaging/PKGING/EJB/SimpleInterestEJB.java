package samples.packaging.pkging.ejb;

import javax.ejb.*;
import java.util.*;
import javax.naming.*;

import samples.packaging.pkging.lib.Calculator;
import samples.packaging.pkging.ejb.*;

/**
 * A simple stateless bean which calculates the simple and compound interest for
 * a given set of inputs. The business method for this bean have been as declared in
 * the remote interface(SimpleInterest).
 */
public class SimpleInterestEJB implements javax.ejb.SessionBean {
	private SessionContext m_ctx;
	private static CompoundInterest interestRemote = null;

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
	public SimpleInterestEJB() {
	}

    /**
     * Returns the compound interest for a given set of inputs: ( P * T * R)/(100 * 12)
     * @param principal principal amount in double
     * @param time time in months
     * @param rate rate in percentage
     * @return returns the compound interest
     * @exception throws a RemoteException.
     */
	public double getCompoundInterest(double principal, double time, double rate) throws java.rmi.RemoteException {
	   double interest = 0;

       if (interestRemote == null) {
           interestRemote = this.getremoteEJB();
       }

       if (interestRemote == null) {
          System.out.println("Could not get remote interface to CompoundInterest");
          throw new java.rmi.RemoteException("Could not get interface to CompoundInterest");
       }

       interest = interestRemote.getCompoundInterest(principal,time,rate);
       return interest;
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

	    // if the env-entry library is set to "file"
		if (this.getLibraryMode() == FILE_MODE) {
		    Calculator calc = new Calculator();
	        try {
	            interest = calc.getProduct(principal,time);
	            interest = calc.getProduct(interest,rate);
	            interest = calc.getDivision(interest,1200);
	        }
	        catch (Exception e) {
	            System.out.println("Exception thrown:"+e.getMessage());
	            e.printStackTrace();
	            }
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
	            interest = moduleRemote.getProduct(principal,time);
	            interest = moduleRemote.getProduct(interest,rate);
	            interest = moduleRemote.getDivision(interest,1200);
	        }
	        catch (Exception e) {
	            System.out.println("Exception thrown:"+e.getMessage());
	            e.printStackTrace();
	        }
	    }


		return interest;
	}

    private CompoundInterest getremoteEJB() {

         CompoundInterestHome myInterestHome = null;
         CompoundInterest myInterestRemote = null;

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
          System.out.println("Looking up CompoundInterest bean home interface");
          String JNDIName = "java:comp/env/ejb/SunONE.pkging.pkgingEJB.CompoundInterest";
          System.out.println("Looking up: " + JNDIName);
          myInterestHome = (CompoundInterestHome)initContext.lookup(JNDIName);
        }
        catch(Exception e) {
           System.out.println("CompoundInterest bean home not found - " +  "Is bean registered with JNDI?: " + e.toString());
            e.printStackTrace();
        return null;
        }
        try {
          System.out.println("Creating the CompoundInterest bean");
          myInterestRemote = myInterestHome.create();
        }
        catch(CreateException e) {
          System.out.println("Could not create the CompoundInterest bean: "+
           e.toString());
          e.printStackTrace();
          return null;
        }
        catch(java.rmi.RemoteException e1) {
          System.out.println("Could not create the CompoundInterest bean: "+e1.toString());
          e1.printStackTrace();
          return null;
        }

        return myInterestRemote;

    }

	/**
	 * This private method returns the library mode being used for the bean.
	 * The mode should be either FILE_MODE or LIBRARY_MODE.
	 * FILE_MODE is returned when the env-entry library is set to "file".
	 * It means that the SimpleInterest bean should instantiate the class "Calculator".
	 *
	 * If env-entry is set to "module" then MODULE_MODE is returned.
	 * In this case the bean should make a JNDI call to the bean "ModuleLibrary".
	 */
	private int getLibraryMode() {

		String env_entry = null;
		int mode = 0;

		// get the entry java:comp/env/library to check if the value is "file" or "module"
		// if value is "file" then we instatiate the class samples.packaging.pkging.lib.Calculator
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
            javax.naming.NamingEnumeration nenum = initContext.listBindings( "ejb" );
            javax.naming.NameClassPair npair;
            while( nenum.hasMore() ) {
                npair = (javax.naming.NameClassPair) nenum.next();
                System.out.println( "Binding: " + npair.getName() + " => "
                        + npair.getClassName() );
            }

          System.out.println("Looking up ModuleLibrary bean home interface");
          //String JNDIName = "java:comp/env/ejb/SunONE.pkgingEJB.ModuleLibrary";
          //String JNDIName = "ejb/SunONE.pkgingEJB.ModuleLibrary";
          String JNDIName = "java:comp/env/ejb/SunONE.pkgingC2EJB.ModuleLibrary";
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
