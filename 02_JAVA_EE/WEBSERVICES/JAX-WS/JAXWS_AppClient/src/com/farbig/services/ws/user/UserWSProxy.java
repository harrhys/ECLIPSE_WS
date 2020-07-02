package com.farbig.services.ws.user;

public class UserWSProxy implements com.farbig.services.ws.user.UserWS {
  private String _endpoint = null;
  private com.farbig.services.ws.user.UserWS userWS = null;
  
  public UserWSProxy() {
    _initUserWSProxy();
  }
  
  public UserWSProxy(String endpoint) {
    _endpoint = endpoint;
    _initUserWSProxy();
  }
  
  private void _initUserWSProxy() {
    try {
      userWS = (new com.farbig.services.ws.user.UserWSServiceLocator()).getUserWS();
      if (userWS != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)userWS)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)userWS)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (userWS != null)
      ((javax.xml.rpc.Stub)userWS)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public com.farbig.services.ws.user.UserWS getUserWS() {
    if (userWS == null)
      _initUserWSProxy();
    return userWS;
  }
  
  public java.lang.String createBaseUser() throws java.rmi.RemoteException{
    if (userWS == null)
      _initUserWSProxy();
    return userWS.createBaseUser();
  }
  
  
}