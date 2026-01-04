/*
 *
 * Copyright 2001 Sun Microsystems, Inc. All Rights Reserved.
 *
 * This software is the proprietary information of Sun Microsystems, Inc.
 * Use is subject to license terms.
 *
 */

package samples.connectors.simple;


import javax.resource.spi.*;
import java.lang.Object;
import javax.resource.ResourceException;
import javax.transaction.xa.XAResource;
import javax.security.auth.Subject;
import java.io.PrintWriter;
import java.util.*;
import javax.resource.spi.security.PasswordCredential;
import javax.resource.spi.SecurityException;
import javax.resource.spi.IllegalStateException;
import javax.resource.NotSupportedException;
import java.io.ObjectInputStream;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.InetAddress;
import java.net.UnknownHostException;


// CustomCodeBegin globalScope

// CustomCodeEnd


/**
 *ManagedConnection instance represents a physical connection to the underlying EIS.
 */

public class  CometManagedConnection implements ManagedConnection{

  private String user;
  private ManagedConnectionFactory mcf;
  private CometConnectionEventListener CometListener;
  private Set connectionSet;  // set of CometConnection
  private PrintWriter logWriter;
  private boolean destroyed;
  private static int testCounter = 0;
  // CustomCodeBegin classScope
  private Socket backendSocket = null;// = new Socket(InetAddress.getLocalHost(), 8020);
  private OutputStream outStream = null; // = soc.getOutputStream();
  private ObjectOutputStream objOutputStream = null; // = new ObjectOutputStream(o);
  private InputStream inStream = null;//= soc.getInputStream();
  private ObjectInputStream objInputStream = null;// = new ObjectInputStream(is);


  public boolean connect(String host, String port) {
    try {
      System.out.println(" -- In CometManagedConnection:: connect ("+testCounter+") "+host+":"+port);
        backendSocket = new Socket(InetAddress.getByName(host),  Integer.parseInt(port) );
        outStream = backendSocket.getOutputStream();
        objOutputStream = new ObjectOutputStream(outStream);
        inStream = backendSocket.getInputStream();
        objInputStream = new ObjectInputStream(inStream);
    } catch (Exception e) {
      return false;
    }
    return true;
  }


  public void sendData(Object obj)throws ResourceException {
    try {
        objOutputStream.writeObject(obj);
        objOutputStream.flush();
    } catch (Exception e) {
        System.out.println(" -- Send data FAILED !!!");
        throw new ResourceException(Messages.getMessage(Messages.SEND_DATA_FAILED),
                                            Messages.SEND_DATA_FAILED);
    }
  }


  public Object getData()throws ResourceException {
    Object obj = null;
    try {
        obj = objInputStream.readObject();
    } catch (Exception e) {
        System.out.println(" -- Get data FAILED !!!");
        throw new ResourceException(Messages.getMessage(Messages.GET_DATA_FAILED),
                                            Messages.GET_DATA_FAILED);
    }
    return obj;
  }


  /* example code:
  private String host=null;
  public void setHost(String host){
     this.host=host;
  }


  public String getHost(){
     return this.host;
  }


  private String port=null;

  public void setPort(String port){
     this.port=port;
  }

  public String getPort(){
     return this.port;
  }

  private String language=null;

  public void setLanguage(String language){
     this.language=language;
  }

  public String getLanguage(){
     return this.language;
  }
  */

  // CustomCodeEnd



  /**
   *Constructor
   *@param mcf  The ManagedConnectionFactory that created this instance
   *@param user name of the user associated with the ManagedConnection instance
   */

  CometManagedConnection(ManagedConnectionFactory mcf,String user ) {
        System.out.println(" 4. In CometManagedConnection ctor "+testCounter++);
        this.mcf = mcf;
        this.user = user;
        connectionSet = new HashSet();
        CometListener = new CometConnectionEventListener(this);
  }



  /**
   *Creates a new connection handle for the underlying physical connection represented by the
   * ManagedConnection instance. This connection handle is used by the application code to
   * refer to the underlying physical connection.
   * @param  subject security context as JAAS subject
   * @param  cxRequestInfo   ConnectionRequestInfo instance
   * @return Object - Connection instance representing the connection handle
   */

  public Object getConnection(Subject subject, ConnectionRequestInfo cxRequestInfo) throws ResourceException{
        PasswordCredential pc = Util.getPasswordCredential(mcf, subject, cxRequestInfo);
        if (pc == null) {
            if (user != null) {
                throw new SecurityException(Messages.getMessage(Messages.PRINCIPAL_DOES_NOT_MATCH),
                                            Messages.PRINCIPAL_DOES_NOT_MATCH);
            }
        } else {
            if (!pc.getUserName().equals(user)) {
                throw new SecurityException(Messages.getMessage(Messages.PRINCIPAL_DOES_NOT_MATCH),
                                            Messages.PRINCIPAL_DOES_NOT_MATCH);
            }
        }
        checkIfDestroyed();
        CometConnection CometCon = new CometConnection(this);
        addCometConnection(CometCon);
        return CometCon;
  }



  /**
   *Destroys the physical connection.
   */

  public void destroy() throws ResourceException{
        if (destroyed) return;
        destroyed = true;
        testCounter--;
        try {
           objInputStream.close();
           objOutputStream.close();
           backendSocket.close();
        } catch (Exception e) {
        }

        Iterator it = connectionSet.iterator();
        while (it.hasNext()) {
             CometConnection CometCon = (CometConnection) it.next();
             CometCon.invalidate();
        }
        connectionSet.clear();

        // ToDo: Add service specific code here
        // CustomCodeBegin destroy
        /*example:
        Close the connection with the EIS.
        */
        // CustomCodeEnd
  }



  /**
   *initiates a cleanup of the any client-specific state as maintained by a ManagedConnection
   *instance. The cleanup should invalidate all connection handles that had been created
   *using this ManagedConnection instance
   */

  public void cleanup() throws ResourceException{
        checkIfDestroyed();
        Iterator it = connectionSet.iterator();
        while (it.hasNext()) {
                CometConnection CometCon = (CometConnection) it.next();
                CometCon.invalidate();
        }
        connectionSet.clear();


        // ToDo: Add service specific code here
        // CustomCodeBegin cleanup
        /*example:
        Initialization of data buffer.
        */
        // CustomCodeEnd

  }


  /**
   *Used by the container to change the association of an application-level connection handle
   *with a ManagedConneciton instance. The container should find the right ManagedConnection
   *instance and call the associateConnection method.
   *@param  connection Application-level connection handle
   */

  public void associateConnection(Object connection) throws ResourceException{
        checkIfDestroyed();
        if (connection instanceof CometConnection) {
            CometConnection CometCon = (CometConnection) connection;
            CometCon.associateConnection(this);
        } else {
            throw new IllegalStateException(Messages.getMessage(Messages.INVALID_CONNECTION),
                                            Messages.INVALID_CONNECTION);

        }
  }


  /**
   *Adds a connection event listener to the ManagedConnection instance. 
   *The registered ConnectionEventListener instances are notified of connection close and
   *error events, also of local transaction related events on the Managed Connection.
   *@param   listener - a new ConnectionEventListener to be registered
   */

  public void addConnectionEventListener(ConnectionEventListener listener){
           CometListener.addConnectorListener(listener);
  }


  /**
   *Removes an already registered connection event listener from the ManagedConnection instance.
   *@param  listener already registered connection event listener to be removed
   */

  public void removeConnectionEventListener(ConnectionEventListener listener){
           CometListener.removeConnectorListener(listener);
  }

  /**
   * Returns an javax.transaction.xa.XAresource instance. An application server enlists this
   * XAResource instance with the  Transaction Manager if the ManagedConnection instance is being
   * used in a JTA transaction that is being coordinated by the Transaction Manager.
   * @return    XAResource - XAResource instance
   */

  public XAResource getXAResource() throws ResourceException{
      throw new NotSupportedException(Messages.getMessage(Messages.NO_XATRANSACTION),
                                      Messages.NO_XATRANSACTION);

  }


  /**
   * Returns an javax.resource.spi.LocalTransaction instance. The LocalTransaction interface is
   * used by the container to manage local transactions for a RM instance.
   * @return LocalTransaction - LocalTransaction instance
   */

  public LocalTransaction getLocalTransaction() throws ResourceException{
      throw new NotSupportedException(Messages.getMessage(Messages.NO_TRANSACTION),
                                      Messages.NO_TRANSACTION);
  }


  /**
   * Gets the metadata information for this connection's underlying EIS resource manager instance.
   * The ManagedConnectionMetaData interface provides information about the underlying EIS
   * instance associated with the ManagedConenction instance.
   * @return ManagedConnectionMetaData - ManagedConnectionMetaData instance
   */

  public ManagedConnectionMetaData getMetaData() throws ResourceException{
      checkIfDestroyed();
      return new CometManagedConnectionMetaData(this);
  }


  /**
   * Sets the log writer for this ManagedConnection instance. 
   * The log writer is a character output stream to which all logging and tracing messages for
   * this ManagedConnection instance will be printed
   * @param out Character Output stream to be associated
   */

  public void setLogWriter(PrintWriter out) throws ResourceException{
      this.logWriter = out;
  }


  /**
   *Gets the log writer for this ManagedConnection instance.
   *@return  PrintWriter - Character ourput stream associated with this Managed- Connection
   *                       instance
   */   

  public PrintWriter getLogWriter() throws ResourceException{
     return logWriter;
  }


  /**
   *get user name of the user associated with the ManagedConnection instance
   */

  public String getUserName() {
       return user;
  }


  /**
   *Associate connection handle to the phisical connection
   *@param  CometCon  connection handle
   */

  public void addCometConnection(CometConnection CometCon) {
        connectionSet.add(CometCon);
  }


  /**
   *check validation of the phisical connection
   */

  private void checkIfDestroyed() throws ResourceException {
        if (destroyed) {
            throw new IllegalStateException(Messages.getMessage(Messages.DESTROYED_CONNECTION),
                                            Messages.DESTROYED_CONNECTION);
        }
  }


   /**
   *remove Associate connection handle from the connections set to the phisical connection
   *@param  CometCon  connection handle
   */

   public void removeCometConnection(CometConnection CometCon) {
        connectionSet.remove(CometCon);
   }


   /**
    *check validation of the phisical connection
    */

   boolean isDestroyed() {
        return destroyed;
   }


   /**
    *returns the ManagedConnectionFactory that created this instance of ManagedConnection
    */

   public ManagedConnectionFactory getManagedConnectionFactory() {
        return this.mcf;
   }


   /**
    *send connection event to the application server
    */

   public void sendEvent(int eventType, Exception ex) {
        CometListener.sendEvent(eventType, ex, null);
   }


   /**
    *send connection event to the application server
    */

   public void sendEvent(int eventType, Exception ex,
        Object connectionHandle) {
        CometListener.sendEvent(eventType, ex, connectionHandle);
   }
}
