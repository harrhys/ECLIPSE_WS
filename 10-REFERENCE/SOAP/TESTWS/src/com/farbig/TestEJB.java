package com.farbig;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 * Session Bean implementation class TestEJB
 */
@Stateless(mappedName = "myejb")
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
