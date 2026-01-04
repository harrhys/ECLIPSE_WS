/**
 * HiBSImplServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.test;

public class HiBSImplServiceLocator extends org.apache.axis.client.Service implements com.test.HiBSImplService {

    public HiBSImplServiceLocator() {
    }


    public HiBSImplServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public HiBSImplServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for HiBSImpl
    private java.lang.String HiBSImpl_address = "http://localhost:7001/WS/services/HiBSImpl";

    public java.lang.String getHiBSImplAddress() {
        return HiBSImpl_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String HiBSImplWSDDServiceName = "HiBSImpl";

    public java.lang.String getHiBSImplWSDDServiceName() {
        return HiBSImplWSDDServiceName;
    }

    public void setHiBSImplWSDDServiceName(java.lang.String name) {
        HiBSImplWSDDServiceName = name;
    }

    public com.test.HiBSImpl getHiBSImpl() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(HiBSImpl_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getHiBSImpl(endpoint);
    }

    public com.test.HiBSImpl getHiBSImpl(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.test.HiBSImplSoapBindingStub _stub = new com.test.HiBSImplSoapBindingStub(portAddress, this);
            _stub.setPortName(getHiBSImplWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setHiBSImplEndpointAddress(java.lang.String address) {
        HiBSImpl_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.test.HiBSImpl.class.isAssignableFrom(serviceEndpointInterface)) {
                com.test.HiBSImplSoapBindingStub _stub = new com.test.HiBSImplSoapBindingStub(new java.net.URL(HiBSImpl_address), this);
                _stub.setPortName(getHiBSImplWSDDServiceName());
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
        if ("HiBSImpl".equals(inputPortName)) {
            return getHiBSImpl();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://test.com", "HiBSImplService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://test.com", "HiBSImpl"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("HiBSImpl".equals(portName)) {
            setHiBSImplEndpointAddress(address);
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
