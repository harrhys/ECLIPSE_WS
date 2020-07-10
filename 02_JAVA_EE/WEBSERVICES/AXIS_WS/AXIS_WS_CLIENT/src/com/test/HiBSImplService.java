/**
 * HiBSImplService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.test;

public interface HiBSImplService extends javax.xml.rpc.Service {
    public java.lang.String getHiBSImplAddress();

    public com.test.HiBSImpl getHiBSImpl() throws javax.xml.rpc.ServiceException;

    public com.test.HiBSImpl getHiBSImpl(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
