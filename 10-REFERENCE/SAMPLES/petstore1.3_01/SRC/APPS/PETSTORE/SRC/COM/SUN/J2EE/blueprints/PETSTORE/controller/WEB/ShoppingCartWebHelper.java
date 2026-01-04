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
package com.sun.j2ee.blueprints.petstore.controller.web;

import java.util.*;

import javax.servlet.http.HttpSession;
import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.naming.NamingException;
import javax.ejb.RemoveException;

import com.sun.j2ee.blueprints.util.tracer.Debug;

// petstore imports
import com.sun.j2ee.blueprints.petstore.controller.web.util.PetstoreKeys;
import com.sun.j2ee.blueprints.petstore.controller.ejb.ShoppingClientControllerLocal;
import com.sun.j2ee.blueprints.petstore.controller.ejb.ShoppingClientFacadeLocal;

// cart component imports
import com.sun.j2ee.blueprints.cart.ejb.ShoppingCartLocal;
import com.sun.j2ee.blueprints.cart.ejb.ShoppingCartLocalHome;

import com.sun.j2ee.blueprints.cart.model.CartItem;

/**
 *
 */
public class ShoppingCartWebHelper implements java.io.Serializable {

    private ShoppingCartLocal cart;
    private HttpSession session;

    public ShoppingCartWebHelper() {
    }

    /**
     * constructor for an HTTP client.
     * @param the HTTP session object for a client
     */
    public void init(HttpSession session) {
        this.session = session;
        ShoppingClientControllerLocal sccEjb = null;
        sccEjb = (ShoppingClientControllerLocal)session.getAttribute(PetstoreKeys.EJB_CLIENT_CONTROLLER);
        if (sccEjb == null) {
        }
        if (sccEjb != null) {
            try {
                ShoppingClientFacadeLocal scf = sccEjb.getShoppingClientFacade();
                cart = scf.getShoppingCart();
                session.setAttribute(PetstoreKeys.CART, this);
            } catch (Exception e) {
                System.err.println("ShoppingCartWebHelper error: " + e);
            }
        }
    }

    /**
     * Return a collection of CartItems
     */
    public List getItems(Locale locale) {
        List ret = new ArrayList();

        if (cart == null)
            return ret;

        Collection c = cart.getItems(locale);
        for (Iterator it = c.iterator(); it.hasNext(); ) {
            ret.add(it.next());
        }

        return ret;
    }

    public List getItems(int start, int quantity, Locale locale) {
        List ret = new ArrayList();

        if (cart == null)
            return ret;

        Collection c = cart.getItems(locale);
        for (Iterator it = c.iterator(); it.hasNext(); ) {
            ret.add(it.next());
        }

        return ret;
    }

    public double getSubtotal(Locale locale) {
        double ret = 0.0d;

        for (Iterator it = getItems(locale).iterator(); it.hasNext(); ) {
            CartItem i = (CartItem) it.next();
            ret += (i.getUnitCost() * i.getQuantity()) ;
        }

        return ret;
    }
}

