/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mango;

import javax.ejb.Stateless;

/**
 *
 * @author somasundaram
 */
@Stateless
public class NewSessionBean implements NewSessionBeanRemote {

    public String sayHello() {
        return "Hello World";
    }
    
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
 
}
