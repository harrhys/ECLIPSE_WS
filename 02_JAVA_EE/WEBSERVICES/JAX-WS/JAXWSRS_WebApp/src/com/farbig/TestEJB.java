package com.farbig;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.embeddable.EJBContainer;

/**
 * Session Bean implementation class TestEJB
 */
@Stateless
@LocalBean
public class TestEJB {

    /**
     * Default constructor. 
     */
    public TestEJB() {
        // TODO Auto-generated constructor stub
    }
    
    public String sayHello(String name)
    {
    	return "Hello "+name;
    }

}
