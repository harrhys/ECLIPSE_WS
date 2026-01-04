/*
 *
 * Copyright 2001 Sun Microsystems, Inc. All Rights Reserved.
 *
 * This software is the proprietary information of Sun Microsystems, Inc.
 * Use is subject to license terms.
 *
 */

package samples.connectors.simple;


import java.io.*;
import javax.resource.Referenceable;
import javax.resource.cci.*;
import javax.resource.spi.*;
import javax.resource.ResourceException;
import javax.naming.Reference;

// CustomCodeBegin globalScope
// CustomCodeEnd

/**
 * This implementation class provides provides an inteface for getting
 * connection to an EIS instance.
 * Application code looks up a ConnectionFactory instance from JNDI namespace and
 * uses it to get EIS connections. 
 */
public class CometConnectionFactory implements
          ConnectionFactory, Serializable, Referenceable{

  private ManagedConnectionFactory mcf;
  private ConnectionManager cm;
  private  Reference reference;
  private  transient PrintWriter out;
  private  transient int milliseconds;


  // CustomCodeBegin classScope
  // CustomCodeEnd

  public CometConnectionFactory(){

  }

  /**
   *CometConnectionFactory constructor.
   *@param mcf  the ManagedConnectionFactory that created this CometConnectionFactory instance.
   *@param cm   the ConnectionManager
   */

  public CometConnectionFactory(ManagedConnectionFactory mcf,
                          ConnectionManager cm) {

	System.out.println(" 2. In CometConnectionFactory ctor, mcf="+mcf+" cm="+cm);
        this.mcf = mcf;
        if (cm == null) {
            this.cm = new CometConnectionManager();
        } else {
            this.cm = cm;
        }
  }


  /**
   *Gets a connection to an EIS instance. The component does not pass any security information.
   *@return Connection - Connection instance
   */

  public javax.resource.cci.Connection getConnection() throws ResourceException{

        javax.resource.cci.Connection con = null;
        System.out.println("In getConnection---before allocateConnection"); 
        
        con = (javax.resource.cci.Connection) cm.allocateConnection(mcf, null);
        System.out.println("In getConnection---after allocateConnection"); 
        return con;

  }


  /**
   *Gets a connection to an EIS instance. A component should use the getConnection variant
   *with javax.resource.cci.ConnectionSpec parameter, if it needs to pass any resource adapter
   *specific security information and connection parameters.
   *@param properties properties connection parameters and security information specified as ConnectionSpec instance
   *@return Connection - Connection instance
   */

  public javax.resource.cci.Connection getConnection(ConnectionSpec properties) throws ResourceException{

        javax.resource.cci.Connection con = null;
        ConnectionRequestInfo info =
            new CometConnectionRequestInfo(
		    ((CometConnectionSpec)properties).getUser(),
		    ((CometConnectionSpec)properties).getPassword());


        // ToDo: Add service specific code here
        // CustomCodeBegin execute

        /* example code:
          String lang=((CometConnectionSpec)properties).getLanguage();
          info.setLanguage(lang);
        */

        // CustomCodeEnd

        con = (javax.resource.cci.Connection)cm.allocateConnection(mcf,info);
        return con;

  }


  /**
   *Gets a RecordFactory instance. The RecordFactory is used for the creation of ims Record instances.
   *@return RecordFactory - RecordFactory instance
   */

  public RecordFactory getRecordFactory() throws ResourceException{
      return new CometRecordFactory();
  }


  /**
   *Gets metadata for the Resource Adapter. Note that the metadata information is about the
   *ResourceAdapter and not the EIS instance.
   *@return ResourceAdapterMetaData - ResourceAdapterMetaData instance
   */

  public ResourceAdapterMetaData getMetaData() throws ResourceException{
      return new CometResourceAdapterMetaData();
  }


  /**
   *Sets the log writer for the ConnectionFactory instance. 
   *The log writer is a character output stream to which all logging and tracing messages for
   *the Connectionfactory instance will be printed.
   *@param out Log writer associated with the ConnectionFactory
   */

  public void setLogWriter(PrintWriter out) throws ResourceException{
     this.out=out;
  }


  /**
   *Gets the log writer for the ConnectionFactory instance.
   *@return PrintWriter - log writer associated with the ConnectionFactory
   */

  public PrintWriter getLogWriter() throws ResourceException{
     return out;
  }


  /**
   *Sets the maximum time in milliseconds that this connection factory will wait while
   *attempting to connect to an EIS.A value of zero specifies that the timeout is the default
   *system timeout if there is one; otherwise it specifies that there is no timeout.
   *When a ConnectionFactory object is created the timeout is initially zero.
   *@param  milliseconds connection establishment timeout in milliseconds.
   */

  public void setTimeout(int milliseconds) throws ResourceException{
     this.milliseconds=milliseconds;
  }


  /**
   *Gets the maximum time in milliseconds that this connection factory can wait while
   *attempting to connect to an EIS
   *@return int - connection establishment timeout in milliseconds
   */

  public int getTimeout() throws ResourceException{
     return milliseconds;
  }


  /**
   *This method was eclared in javax.resource.Referenceable interface,and should be implemented
   *in order to support JNDI registration.
   */

   public void setReference(Reference reference) {
      System.out.println("In CometConnectionFactory::setReference");
      this.reference = reference;
  }


  /**
   *This method was eclared in javax.resource.Referenceable interface,and should be implemented
   *in order to support JNDI registration.
   */

  public Reference getReference() {
      System.out.println("In CometConnectionFactory::getReference");
      return reference;
  }
}
