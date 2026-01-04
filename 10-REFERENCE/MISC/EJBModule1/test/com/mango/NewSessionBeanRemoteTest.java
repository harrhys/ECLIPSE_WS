/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mango;

import org.junit.Before;
import org.junit.Test;
import java.util.Properties;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import junit.framework.Assert;
/**
 *
 * @author somasundaram
 */
public class NewSessionBeanRemoteTest {
    private NewSessionBeanRemote remote = null;

    @Before
    public void setUp() throws NamingException {
        Properties props = new Properties();
        props.setProperty(Context.INITIAL_CONTEXT_FACTORY,
                    "com.sun.enterprise.naming.SerialInitContextFactory");
        props.setProperty(Context.URL_PKG_PREFIXES,
                    "com.sun.enterprise.naming");
        props.setProperty(Context.STATE_FACTORIES,
                    "com.sun.corba.ee.impl.presentation.rmi.JNDIStateFactoryImpl");
        // optional.  Defaults to localhost.  Only needed if web server is running
        // on a different host than the appserver
        props.setProperty("org.omg.CORBA.ORBInitialHost", "localhost");
        // optional.  Defaults to 3700.  Only needed if target orb port is not 3700.
        props.setProperty("org.omg.CORBA.ORBInitialPort", "3700");
        InitialContext ic = new InitialContext(props);
        remote = (NewSessionBeanRemote) ic
                                        .lookup("com.mango.NewSessionBeanRemote");
                                        //.lookup("java:global/MySessionBeanRemote");
                			//.lookup("MySessionBeanRemote");
                                        //.lookup("java:comp/env/ejb/MySessionBeanRemote");
    }

    @Test
    public void testSayHello() {
        System.out.println("sayHello");
        String expResult = "Hello World";
        String result = remote.sayHello();
        Assert.assertEquals(expResult, result);
    }
}