/*
 *
 * Copyright 2001 Sun Microsystems, Inc. All Rights Reserved.
 *
 * This software is the proprietary information of Sun Microsystems, Inc.
 * Use is subject to license terms.
 *
 */

package samples.lifecycle.rmiserver;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
  * Remote interface for rmi server
  */
public interface SampleRemoteInterface extends java.rmi.Remote {
    void stop() throws RemoteException;
    void start() throws RemoteException;
}


