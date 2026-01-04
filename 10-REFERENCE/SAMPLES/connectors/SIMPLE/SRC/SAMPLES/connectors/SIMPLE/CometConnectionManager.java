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
import java.io.Serializable;

/**
 *The default ConnectionManager implementation for the  non-managed scenario
 *This provieds a hook for a resource adapter to pass a connection
 *request to an application server.
 */
public class CometConnectionManager implements ConnectionManager ,Serializable {

  /**
   *Default Constructor
   */
  public CometConnectionManager() {
	System.out.println("In CometConnectionManager ctor");
  }

  /**
   *The method allocateConnection gets called by the resource adapter's connection factory
   *instance. This lets connection factory instance (provided by the resource adapter) pass
   *a connection request to the ConnectionManager instance.
   *The connectionRequestInfo parameter represents information specific to the resource adapter
   * for handling of the connection request.
   *@param mcf used by application server to delegate connection matching/creation
   *@param cxRequestInfo connection request Information
   */
  public Object allocateConnection(ManagedConnectionFactory mcf, ConnectionRequestInfo cxRequestInfo) throws ResourceException{
        ManagedConnection mc = mcf.createManagedConnection(null, cxRequestInfo);
        return mc.getConnection(null, cxRequestInfo);
  }
}
