package samples.corba.ejbsample.ejb; 

/** Remote interface for EJB CorbaClient.
 */
public interface CorbaClient extends javax.ejb.EJBObject { 
    /** Call inside the Bean implementation to call a method on a CORBA object.
     * @throws RemoteException Throws RemoteException if the request can't be process.
     * @return Result of type String.
     */    
    public String doWork() throws java.rmi.RemoteException; 
} 
