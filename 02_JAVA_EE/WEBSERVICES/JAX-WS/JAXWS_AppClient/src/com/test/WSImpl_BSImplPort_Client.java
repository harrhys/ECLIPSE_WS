
package com.test;

/**
 * Please modify this class to meet your needs
 * This class is not complete
 */

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 2.7.3
 * 2013-03-23T02:01:37.804+05:30
 * Generated source version: 2.7.3
 * 
 */
public final class WSImpl_BSImplPort_Client {

    private static final QName SERVICE_NAME = new QName("http://test.com/", "BSImplService");

    private WSImpl_BSImplPort_Client() {
    }

    public static void main(String args[]) throws java.lang.Exception {
        URL wsdlURL = BSImplService.WSDL_LOCATION;
        if (args.length > 0 && args[0] != null && !"".equals(args[0])) { 
            File wsdlFile = new File(args[0]);
            try {
                if (wsdlFile.exists()) {
                    wsdlURL = wsdlFile.toURI().toURL();
                } else {
                    wsdlURL = new URL(args[0]);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
      
        BSImplService ss = new BSImplService(wsdlURL, SERVICE_NAME);
        WS port = ss.getBSImplPort();  
        
        {
        System.out.println("Invoking sayHello...");
        java.lang.String _sayHello_arg0 = "_sayHello_arg0-2092856463";
        java.lang.String _sayHello__return = port.sayHello(_sayHello_arg0);
        System.out.println("sayHello.result=" + _sayHello__return);


        }

        System.exit(0);
    }

}
