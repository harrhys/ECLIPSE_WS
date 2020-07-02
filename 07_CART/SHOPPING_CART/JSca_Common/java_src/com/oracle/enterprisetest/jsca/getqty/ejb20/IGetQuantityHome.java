package com.oracle.enterprisetest.jsca.getqty.ejb20;

import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBHome;

public interface IGetQuantityHome extends EJBHome {
  IGetQuantityRemote create() throws CreateException, RemoteException;
}


