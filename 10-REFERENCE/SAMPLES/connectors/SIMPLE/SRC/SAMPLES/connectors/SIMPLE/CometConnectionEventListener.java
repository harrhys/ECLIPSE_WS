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
import java.util.*;

/**
 * The connector architecture provides an event callback mechanism that
 * enables an application server to receive notifications from a
 * ManagedConnection instance. The App server implements this class in order to
 * listen to event notifications from ManagedConnection instance.
 */
public class CometConnectionEventListener {

    private Vector listeners;
    private ManagedConnection mcon;

    /**
     *Constructor
     *@param  mcon The managed connection that created this instance.
     */
    public CometConnectionEventListener(ManagedConnection mcon) {
	System.out.println(" 5. In CometConnectionEventListener ctor");
        listeners = new Vector();
        this.mcon = mcon;
    }

    /**
     *send connection event to the application server
     */
    public void sendEvent(int eventType, Exception ex,
                          Object connectionHandle) {
        Vector list = (Vector) listeners.clone();
        ConnectionEvent ce = null;
        if (ex == null) {
            ce = new ConnectionEvent(mcon, eventType);
        } else {
            ce = new ConnectionEvent(mcon, eventType, ex);
        }
        if (connectionHandle != null) {
            ce.setConnectionHandle(connectionHandle);
        }
         int size = list.size();
        for (int i=0; i<size; i++) {
            ConnectionEventListener l =
                (ConnectionEventListener) list.elementAt(i);
            switch (eventType) {
            case ConnectionEvent.CONNECTION_CLOSED:
                l.connectionClosed(ce);
                break;
            case ConnectionEvent.LOCAL_TRANSACTION_STARTED:
                l.localTransactionStarted(ce);
                break;
            case ConnectionEvent.LOCAL_TRANSACTION_COMMITTED:
                l.localTransactionCommitted(ce);
                break;
            case ConnectionEvent.LOCAL_TRANSACTION_ROLLEDBACK:
                l.localTransactionRolledback(ce);
                break;
            case ConnectionEvent.CONNECTION_ERROR_OCCURRED:
                l.connectionErrorOccurred(ce);
                break;
            default:
                throw new IllegalArgumentException(Messages.getMessage(Messages.ILLEGAL_EVENT_TYPE)+eventType);


            }
        }
    }

  /**
   *Adds a connection event listener to the ManagedConnection Listner instance.
   *The registered ConnectionEventListener instances are notified of connection close and
   *error events, also of local transaction related events on the Managed Connection.
   *@param   listener - a new ConnectionEventListener to be registered
   */
    public void addConnectorListener(ConnectionEventListener l) {
        listeners.addElement(l);
    }

   /**
    *Removes an already registered connection event listener .
    *@param  listener already registered connection event listener to be removed
    */
    public void removeConnectorListener(ConnectionEventListener l) {
        listeners.removeElement(l);
    }


}
