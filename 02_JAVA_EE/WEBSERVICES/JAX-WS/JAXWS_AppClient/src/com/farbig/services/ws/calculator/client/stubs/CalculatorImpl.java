
package com.farbig.services.ws.calculator.client.stubs;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.4-b01
 * Generated source version: 2.2
 * 
 */
@WebServiceClient(name = "CalculatorImpl", targetNamespace = "vishar.com", wsdlLocation = "http://localhost:7001/SOAP/CalculatorImpl?WSDL")
public class CalculatorImpl
    extends Service
{

    private final static URL CALCULATORIMPL_WSDL_LOCATION;
    private final static WebServiceException CALCULATORIMPL_EXCEPTION;
    private final static QName CALCULATORIMPL_QNAME = new QName("vishar.com", "CalculatorImpl");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("http://localhost:7001/SOAP/CalculatorImpl?WSDL");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        CALCULATORIMPL_WSDL_LOCATION = url;
        CALCULATORIMPL_EXCEPTION = e;
    }

    public CalculatorImpl() {
        super(__getWsdlLocation(), CALCULATORIMPL_QNAME);
    }

  /*  public CalculatorImpl(WebServiceFeature... features) {
        super(__getWsdlLocation(), CALCULATORIMPL_QNAME, features);
    }*/

    public CalculatorImpl(URL wsdlLocation) {
        super(wsdlLocation, CALCULATORIMPL_QNAME);
    }

   /* public CalculatorImpl(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, CALCULATORIMPL_QNAME, features);
    }*/

    public CalculatorImpl(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

  /*  public CalculatorImpl(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }*/

    /**
     * 
     * @return
     *     returns Calculator
     */
    @WebEndpoint(name = "CalculatorImplPort")
    public Calculator getCalculatorImplPort() {
        return super.getPort(new QName("vishar.com", "CalculatorImplPort"), Calculator.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns Calculator
     */
    @WebEndpoint(name = "CalculatorImplPort")
    public Calculator getCalculatorImplPort(WebServiceFeature... features) {
        return super.getPort(new QName("vishar.com", "CalculatorImplPort"), Calculator.class, features);
    }

    private static URL __getWsdlLocation() {
        if (CALCULATORIMPL_EXCEPTION!= null) {
            throw CALCULATORIMPL_EXCEPTION;
        }
        return CALCULATORIMPL_WSDL_LOCATION;
    }

}
