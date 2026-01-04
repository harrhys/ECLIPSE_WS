/*
 * Copyright 2002 Sun Microsystems, Inc. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * - Redistributions of source code must retain the above copyright
 *   notice, this list of conditions and the following disclaimer.
 *
 * - Redistribution in binary form must reproduce the above copyright
 *   notice, this list of conditions and the following disclaimer in
 *   the documentation and/or other materials provided with the
 *   distribution.
 *
 * Neither the name of Sun Microsystems, Inc. or the names of
 * contributors may be used to endorse or promote products derived
 * from this software without specific prior written permission.
 *
 * This software is provided "AS IS," without a warranty of any
 * kind. ALL EXPRESS OR IMPLIED CONDITIONS, REPRESENTATIONS AND
 * WARRANTIES, INCLUDING ANY IMPLIED WARRANTY OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE OR NON-INFRINGEMENT, ARE HEREBY
 * EXCLUDED. SUN AND ITS LICENSORS SHALL NOT BE LIABLE FOR ANY DAMAGES
 * SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR
 * DISTRIBUTING THE SOFTWARE OR ITS DERIVATIVES. IN NO EVENT WILL SUN
 * OR ITS LICENSORS BE LIABLE FOR ANY LOST REVENUE, PROFIT OR DATA, OR
 * FOR DIRECT, INDIRECT, SPECIAL, CONSEQUENTIAL, INCIDENTAL OR
 * PUNITIVE DAMAGES, HOWEVER CAUSED AND REGARDLESS OF THE THEORY OF
 * LIABILITY, ARISING OUT OF THE USE OF OR INABILITY TO USE SOFTWARE,
 * EVEN IF SUN HAS BEEN ADVISED OF THE POSSIBILITY OF SUCH DAMAGES.
 *
 * You acknowledge that Software is not designed, licensed or intended
 * for use in the design, construction, operation or maintenance of
 * any nuclear facility.
 */

package com.sun.j2ee.blueprints.petstore.controller.ejb;

import java.util.Collection;
import java.util.HashMap;

// j2ee imports
import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.RemoveException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import javax.ejb.FinderException;
import javax.naming.InitialContext;

// waf imports
import com.sun.j2ee.blueprints.waf.exceptions.GeneralFailureException;

// shopping cart imports
import com.sun.j2ee.blueprints.cart.ejb.ShoppingCartLocal;
import com.sun.j2ee.blueprints.cart.ejb.ShoppingCartLocalHome;

// customer imports
import com.sun.j2ee.blueprints.customer.ejb.CustomerLocal;
import com.sun.j2ee.blueprints.customer.ejb.CustomerLocalHome;



/**
 * Session Bean implementation for ShoppingClientFacadeLocal EJB.
 *
 * Provide a facade to all of the ejbs related to a shopping client
 */
public class ShoppingClientFacadeLocalEJB implements SessionBean {

    private SessionContext sc = null;

    private ShoppingCartLocal cart = null;
    private CustomerLocal customer = null;
    private String userId = null;

    public ShoppingClientFacadeLocalEJB() {}


    public void ejbCreate() {
    }

    public void setSessionContext(SessionContext sc) {
        this.sc = sc;
    }

    public String getUserId() {
         return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    /*
     * Asume that the customer userId has been set
     */
    public CustomerLocal getCustomer() throws FinderException {
            if (userId == null) {
                throw new GeneralFailureException("ShoppingClientFacade: failed to look up name of customer: userId is not set" );
            }
            try {
                InitialContext ic = new InitialContext();
                Object o = ic.lookup("java:comp/env/ejb/local/Customer");
                CustomerLocalHome home =(CustomerLocalHome)o;
                customer = home.findByPrimaryKey(userId);
            } catch (javax.naming.NamingException nx) {
                throw new GeneralFailureException("ShoppingClientFacade: failed to look up name of customer: caught " + nx);
            }

        return customer;
    }

        public CustomerLocal createCustomer(String userId) {
            try {
                InitialContext ic = new InitialContext();
                Object o = ic.lookup("java:comp/env/ejb/local/Customer");
                CustomerLocalHome home =(CustomerLocalHome)o;
                customer = home.create(userId);
                this.userId = userId;
            } catch (javax.ejb.CreateException ce) {
                throw new GeneralFailureException("ShoppingClientFacade: failed to create customer: caught " + ce);
            } catch (javax.naming.NamingException nx) {
                throw new GeneralFailureException("ShoppingClientFacade: failed to look up name of customer: caught " + nx);
            }

        return customer;
    }

    public ShoppingCartLocal getShoppingCart() {
        if (cart == null) {
            try {
                InitialContext ic = new InitialContext();
                Object o = ic.lookup("java:comp/env/ejb/local/ShoppingCart");
                ShoppingCartLocalHome home =(ShoppingCartLocalHome)o;
                cart = home.create();
            } catch (javax.ejb.CreateException cx) {
                throw new GeneralFailureException("ShoppingClientFacade: failed to create cart: caught " + cx);
            } catch (javax.naming.NamingException nx) {
                throw new GeneralFailureException("ShoppingClientFacade: failed to look up name of cart: caught " + nx);
            }
        }
        return cart;
    }

    public void ejbRemove() {
    }

    public void ejbActivate() {}

    public void ejbPassivate() {}
}


