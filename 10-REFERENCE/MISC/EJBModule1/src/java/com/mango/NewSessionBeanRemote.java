/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mango;

import javax.ejb.Remote;

/**
 *
 * @author somasundaram
 */
@Remote
public interface NewSessionBeanRemote {

    String sayHello();
    
}
