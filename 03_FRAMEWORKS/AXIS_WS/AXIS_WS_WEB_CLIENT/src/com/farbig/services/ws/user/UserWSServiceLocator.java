/**
 * UserWSServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.farbig.services.ws.user;

public class UserWSServiceLocator extends org.apache.axis.client.Service implements com.farbig.services.ws.user.UserWSService {

    public UserWSServiceLocator() {
    }


    public UserWSServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public UserWSServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for UserWS
    private java.lang.String UserWS_address = "http://localhost:8080/MYWS/services/UserWS";

    public java.lang.String getUserWSAddress() {
        return UserWS_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String UserWSWSDDServiceName = "UserWS";

    public java.lang.String getUserWSWSDDServiceName() {
        return UserWSWSDDServiceName;
    }

    public void setUserWSWSDDServiceName(java.lang.String name) {
        UserWSWSDDServiceName = name;
    }

    public com.farbig.services.ws.user.UserWS getUserWS() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(UserWS_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getUserWS(endpoint);
    }

    public com.farbig.services.ws.user.UserWS getUserWS(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.farbig.services.ws.user.UserWSSoapBindingStub _stub = new com.farbig.services.ws.user.UserWSSoapBindingStub(portAddress, this);
            _stub.setPortName(getUserWSWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setUserWSEndpointAddress(java.lang.String address) {
        UserWS_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.farbig.services.ws.user.UserWS.class.isAssignableFrom(serviceEndpointInterface)) {
                com.farbig.services.ws.user.UserWSSoapBindingStub _stub = new com.farbig.services.ws.user.UserWSSoapBindingStub(new java.net.URL(UserWS_address), this);
                _stub.setPortName(getUserWSWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("UserWS".equals(inputPortName)) {
            return getUserWS();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://services.web.farbig.com", "UserWSService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://services.web.farbig.com", "UserWS"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("UserWS".equals(portName)) {
            setUserWSEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
