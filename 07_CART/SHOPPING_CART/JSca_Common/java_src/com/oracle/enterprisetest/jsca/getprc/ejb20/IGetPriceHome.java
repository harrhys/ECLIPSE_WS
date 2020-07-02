package com.oracle.enterprisetest.jsca.getprc.ejb20;

import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBHome;

public interface IGetPriceHome extends EJBHome {
  IGetPriceRemote create() throws CreateException, RemoteException;
}


