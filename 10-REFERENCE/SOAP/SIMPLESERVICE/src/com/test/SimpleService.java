package com.test;

import javax.xml.ws.Endpoint;

public class SimpleService{

    protected SimpleService() throws Exception {
        System.out.println("Starting Server");
        Object implementor = new com.test.WSImpl();
        String address = "http://localhost:7777/MySimpleService/SimplePort";
        Endpoint.publish(address, implementor);
    }
    
    public static void main(String args[]) throws Exception { 
        new SimpleService();
        System.out.println("Server ready..."); 
    }
}
 
 