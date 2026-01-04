package samples.corba.ejbsample.ejb; 

/** The Home interface for the EJB CorbaClient.
 */
public interface CorbaClientHome extends javax.ejb.EJBHome { 
    /** Defines the method that allow a remote client to create EJB objects.
     * @throws RemoteException
     * @throws CreateException
     * @return Reference to EJB objects.
     */    
    public CorbaClient create() throws java.rmi.RemoteException, javax.ejb.CreateException; 
} 
