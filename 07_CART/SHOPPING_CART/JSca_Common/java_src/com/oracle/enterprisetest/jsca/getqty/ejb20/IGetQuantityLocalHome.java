package com.oracle.enterprisetest.jsca.getqty.ejb20;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;

public interface IGetQuantityLocalHome extends EJBLocalHome {
  IGetQuantityLocal create() throws CreateException;
}

