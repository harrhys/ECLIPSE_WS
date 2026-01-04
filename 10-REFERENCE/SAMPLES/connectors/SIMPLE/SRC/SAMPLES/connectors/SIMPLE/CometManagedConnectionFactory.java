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
import javax.security.auth.Subject;
import java.io.PrintWriter;
import java.io.Serializable;
import javax.resource.spi.security.PasswordCredential;
import java.util.*;
import java.beans.*;
import java.io.*;
import java.text.MessageFormat;

// CustomCodeBegin globalScope

// CustomCodeEnd


/**
 *An object of this class is a factory of both ManagedConnection and
 *connection factory instances.
 *This class supports connection pooling by defining methods for
 *matching and creating connections.
 *This class is implemented as java bean.
 */

public class CometManagedConnectionFactory implements ManagedConnectionFactory,Serializable {
  private transient PrintWriter out;
  private transient PropertyChangeSupport changes = new PropertyChangeSupport(this);


  // CustomCodeBegin classScope

  /* example code:   */

  private String host=null;
  private String port=null;


  public String getHost() {
        return this.host;
  }


  public void setHost(String host) {
        String oldName = this.host;
        this.host = host;
        changes.firePropertyChange("host",oldName,host);
  }


  public String getPort() {
        return this.port;
  }


  public void setPort(String port) {
        String oldName = this.port;
        this.port = port;
        changes.firePropertyChange("port",oldName,port);
  }

  // CustomCodeEnd


  /**
   *Defualt constructor.
   */

  public CometManagedConnectionFactory() {
    System.out.println(" 1. In CometManagedConnectionFactory ctor");
  }


  /**
   * Creates a Connection Factory instance. The Connection Factory instance gets initialized with
   * the passed ConnectionManager. In the managed scenario, ConnectionManager is provided by the
   * application server.
   * @param cxManager ConnectionManager to be associated with created EIS connection factory instance.
   * @return Object - EIS-specific Connection Factory instance.
   */

  public Object createConnectionFactory(ConnectionManager cxManager) throws ResourceException{

    System.out.println("createConnectionFactory 1 this"+this);
  
    CometConnectionFactory cf=null;
    try{
      cf=new CometConnectionFactory(this, cxManager);
    }catch(Exception e){
      throw new ResourceException(e.getMessage());
    }
    return cf;
  }


  /**
   * Creates a Connection Factory instance. The Connection Factory instance gets initialized with
   * a default ConnectionManager.In the non managed scenario, ConnectionManager is provided by
   * the resource adapter.
   * @return Object - EIS-specific Connection Factory instance.
   */

  public Object createConnectionFactory() throws ResourceException{
      System.out.println("createConnectionFactory 2 ");
      return new CometConnectionFactory(this, null);
  }


  /**
   *ManagedConnectionFactory uses the security information (passed as Subject) and additional
   *ConnectionRequestInfo (which is specific to ResourceAdapter and opaque to application server)
   *to create this new connection.
   *@param Subject Caller's security information
   *@param cxRequestInfo Additional resource adapter specific connection request information
   *@return ManagedConnection - ManagedConnection instance.
   */

  public ManagedConnection createManagedConnection(Subject subject, ConnectionRequestInfo cxRequestInfo) throws ResourceException{
    System.out.println(" -- In CometManagedConnectionFactory:: createManagedConnection");
      String userName = null;
      CometManagedConnection gmc=null;
      PasswordCredential pc = Util.getPasswordCredential(this, subject, cxRequestInfo);

      if(pc!=null)
         userName = pc.getUserName();

      gmc=new CometManagedConnection(this,userName);
      if ( !gmc.connect(host,port) )
      {
        System.out.println(" -- Connect to backend FAIL !!!");
      
        Object[] args={host,port};         
        throw new ResourceException(MessageFormat.format(Messages.getMessage(Messages.CONNECTION_FAILED),args),
                                            Messages.CONNECTION_FAILED);

      }

      // ToDo: Add service specific code here
      // CustomCodeBegin createManagedConnection
      /* example code:
       String lang=cxRequestInfo.getLanguage();
       gmc.setLanguage(lang);
      */
      // CustomCodeEnd

      return gmc;
  }


  /**
   *Returns a matched connection from the candidate set of connections.
   *ManagedConnectionFactory uses the security info (as in Subject) and information provided
   *through ConnectionRequestInfo and additional Resource Adapter specific criteria to do
   *matching.
   *@param connectionSet  candidate connection set
   *@param Subject caller's security information
   *@param cxRequestInfo additional resource adapter specific connection request information
   *@return ManagedConnection if resource adapter finds an acceptable match otherwise null
   */

  public ManagedConnection matchManagedConnections(Set connectionSet, Subject subject, 
        ConnectionRequestInfo cxRequestInfo) throws ResourceException{

        PasswordCredential pc = Util.getPasswordCredential(this, subject, cxRequestInfo);
        String userName = null;
        if (pc != null)
            userName = pc.getUserName();

        Iterator it = connectionSet.iterator();
        while (it.hasNext()) {
            Object obj = it.next();
            if (obj instanceof CometManagedConnection) {
                CometManagedConnection mc = (CometManagedConnection) obj;
                ManagedConnectionFactory mcf = mc.getManagedConnectionFactory();

                if (Util.isEqual(mc.getUserName(), userName) &&
                    mcf.equals(this)) {
                    Debug.printDebug("In matchManagedConnections mc="+mc);
                    return mc;
                }
            }
        }
        return null;
  }


  /**
   *Set the log writer for this ManagedConnectionFactory instance.
   *The log writer is a character output stream to which all logging and tracing messages for
   *this ManagedConnectionfactory instance will be printed.
   *@param out  PrintWriter - an out stream for error logging and tracing
   */

  public void setLogWriter(PrintWriter out) throws ResourceException{

     this.out=out;

  }


  /**
   *Get the log writer for this ManagedConnectionFactory instance.
   *@return PrintWriter - an out stream for error logging and tracing
   */

  public PrintWriter getLogWriter() throws ResourceException{
      return this.out;
  }


  /**
   *Returns the hash code for the ManagedConnectionFactory
   *@return int - hash code for the ManagedConnectionFactory   
   */

  public int hashCode(){
    int hashcode=0;
    // ToDo: Add service specific code here

    // CustomCodeBegin hashCode
    if (host!= null && port!=null) {
            hashcode=host.hashCode()+port.hashCode();

    }
    // CustomCodeEnd
    return hashcode;
  }


  /**
   *Check if this ManagedConnectionFactory is equal to another ManagedConnectionFactory.
   *@param other another ManagedConnectionFactory
   *@return boolean - true if two instances are equal
   */

  public boolean equals(Object other){
        boolean equal=false;
        if (other != null) {
          if (other instanceof CometManagedConnectionFactory) {

            // ToDo: Add service specific code here

            // CustomCodeBegin equals

            String host1 = ((CometManagedConnectionFactory) other).host;
            String host2 = this.host;
            String port1=((CometManagedConnectionFactory) other).port;
            String port2=this.port;
            equal= Util.isEqual(host1,host2) && Util.isEqual(port1,port2);

            // CustomCodeEnd
          }
        }
        return equal;
  }


   /**
    *Associate PropertyChangeListener to the ManagedConnectionFactory, 
    *in order to notify about properties changes. <br>
    *java bean implementation.
    */

   public void addPropertyChangeListener(PropertyChangeListener l) {
        changes.addPropertyChangeListener(l);
   }


   /**
    *Delete association of PropertyChangeListener to the ManagedConnectionFactory.
    */

   public void removePropertyChangeListener(PropertyChangeListener l) {
        changes.removePropertyChangeListener(l);
   }

  private void readObject(ObjectInputStream in)throws IOException,ClassNotFoundException{
      System.out.println("Before readObject mcf");
  
      in.defaultReadObject();
      this.changes = new PropertyChangeSupport(this);
      this.out=null;
      System.out.println("after readObject mcf");
  }
}

