/*
 *
 * Copyright 2001 Sun Microsystems, Inc. All Rights Reserved.
 *
 * This software is the proprietary information of Sun Microsystems, Inc.
 * Use is subject to license terms.
 *
 */

package samples.connectors.simple;

import javax.resource.cci.*;
import javax.resource.ResourceException;
import javax.resource.NotSupportedException;
import javax.resource.spi.ConnectionEvent;

/**
*This implementation class represents an application level connection
*handle that is used by a component to access an EIS instance.
*/
public class CometConnection implements javax.resource.cci.Connection{

  private CometManagedConnection mc; // if mc is null, means connection is invalid

  /**
   *Defualt constructor.
   *@param mc  a physical connection to an underlying EIS.
   */
  CometConnection(CometManagedConnection mc) {
        this.mc = mc;
	System.out.println(" 6. In CometConnection ctor mc="+mc);

  }

  /**
   *@return  CometManagedConnection - CometManagedConnection instance representing the physical connection to an underlying EIS.
   */
  CometManagedConnection getManagedConnection() {
	System.out.println(" -- In CometConnection:: getManagedConnection mc="+mc);
        return mc;
  }

  /**
   *Creates an Interaction associated with this Connection.
   *An Interaction enables an application to execute EIS functions.
   *@return  Interaction - Interaction instance
   */
  public Interaction createInteraction() throws ResourceException{
	System.out.println(" -- In CometConnection:: createInteraction mc="+mc);
       return new CometInteraction(this);
  }

  /**
   *Returns a LocalTransaction instance that enables a component to demarcate
   *resource manager local transactions on the Connection.
   *@return  LocalTransaction - LocalTransaction instance
   */
  public javax.resource.cci.LocalTransaction getLocalTransaction() throws ResourceException{
       throw new ResourceException(Messages.getMessage(Messages.NO_TRANSACTION),
                                   Messages.NO_TRANSACTION);
  }

  /**
   *Sets the AutoCommit mode for the Connection.
   *@param  autoCommit  true sets autoCommit mode on; false sets autoCommit mode to be off
   */
  public void setAutoCommit(boolean autoCommit) throws ResourceException{
       throw new ResourceException(Messages.getMessage(Messages.NO_TRANSACTION),
                                   Messages.NO_TRANSACTION);
  }

  /**
   *Gets the AutoCommit mode for the Connection.
   *@return boolean - true if autoCommit mode is on; false otherwise
   */
  public boolean getAutoCommit() throws ResourceException{
       throw new ResourceException(Messages.getMessage(Messages.NO_TRANSACTION),
                                   Messages.NO_TRANSACTION);
  }

  /**
   *Gets the information on the underlying EIS instance represented through an active connection.
   *@return ConnectionMetaData - ConnectionMetaData instance representing information about the EIS instance
   */
  public ConnectionMetaData getMetaData() throws ResourceException{
	System.out.println(" -- In CometConnection:: getMetaData mc="+mc);
      return new CometConnectionMetaData(mc);
  }

  /**
   *Gets the information on the ResultSet functionality supported.
   *@return ResultSetInfo - ResultSetInfoInfo instance
   */
  public ResultSetInfo getResultSetInfo() throws ResourceException{
       throw new NotSupportedException(Messages.getMessage(Messages.NO_RESULT_SET),
                                        Messages.NO_RESULT_SET);
  }

  /**
   * Initiates close of the connection handle at the application level.
   * A client should not use a closed connection to interact with an EIS.
   */
  public void close() throws ResourceException{
 	System.out.println(" -- In CometConnection:: close mc="+mc);
       if (mc == null) return;  // already be closed
        mc.removeCometConnection(this);
        mc.sendEvent(ConnectionEvent.CONNECTION_CLOSED, null, this);
        mc = null;
  }

  /**
   *Associate connection handle with new managed connection.
   *@param newMc  new managed connection
   */
  void associateConnection(CometManagedConnection newMc)
        throws ResourceException {

        try {
            checkIfValid();
        } catch (ResourceException ex) {
            throw new IllegalStateException(Messages.getMessage(Messages.INVALID_CONNECTION));
        }
        // dissociate handle with current managed connection
        mc.removeCometConnection(this);
        // associate handle with new managed connection
        newMc.addCometConnection(this);
        mc = newMc;
    }

    /**
     *Check the validation of the physical connection to the EIS.
     */
    void checkIfValid() throws ResourceException {
	System.out.println(" -- In CometConnection:: checkIfValid mc="+mc);
        if (mc == null) {
            throw new ResourceException(Messages.getMessage(Messages.INVALID_CONNECTION),
                                        Messages.INVALID_CONNECTION);
        }
    }

    /**
     *Sign the physical connection to the EIS as invalid.
     *The physical connection to the EIS can not be used any more.
     */
    void invalidate() {
	System.out.println(" -- In CometConnection:: invalidate mc="+mc);
       mc = null;
    }
}
