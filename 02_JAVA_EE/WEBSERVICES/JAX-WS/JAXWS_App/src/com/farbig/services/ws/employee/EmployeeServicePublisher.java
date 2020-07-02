package com.farbig.services.ws.employee;

import javax.xml.ws.Endpoint;

public class EmployeeServicePublisher{

    public static void main(String args[]) throws Exception { 
    	System.out.println("Starting Server");
        Object implementor = new com.farbig.services.ws.employee.EmployeeServiceImpl();
        String address = "http://localhost:7777/MySimpleService/SimplePort";
        Endpoint.publish(address, implementor);
        System.out.println("Server ready..."); 
    }
}
 
 