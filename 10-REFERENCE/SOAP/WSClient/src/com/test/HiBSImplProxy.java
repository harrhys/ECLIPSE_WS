package com.test;

public class HiBSImplProxy implements com.test.HiBSImpl {
  private String _endpoint = null;
  private com.test.HiBSImpl hiBSImpl = null;
  
  public HiBSImplProxy() {
    _initHiBSImplProxy();
  }
  
  public HiBSImplProxy(String endpoint) {
    _endpoint = endpoint;
    _initHiBSImplProxy();
  }
  
  private void _initHiBSImplProxy() {
    try {
      hiBSImpl = (new com.test.HiBSImplServiceLocator()).getHiBSImpl();
      if (hiBSImpl != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)hiBSImpl)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)hiBSImpl)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (hiBSImpl != null)
      ((javax.xml.rpc.Stub)hiBSImpl)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public com.test.HiBSImpl getHiBSImpl() {
    if (hiBSImpl == null)
      _initHiBSImplProxy();
    return hiBSImpl;
  }
  
  public java.lang.String sayHi(java.lang.String name) throws java.rmi.RemoteException{
    if (hiBSImpl == null)
      _initHiBSImplProxy();
    return hiBSImpl.sayHi(name);
  }
  
  
}