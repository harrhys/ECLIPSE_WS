package com.oracle.enterprisetest.jsca.getprc.ejb20;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;

public interface IGetPriceLocalHome extends EJBLocalHome {
  IGetPriceLocal create() throws CreateException;
}

