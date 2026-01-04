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

import java.beans.Beans;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionListener;
import javax.servlet.http.HttpSessionEvent;
import javax.naming.InitialContext;
// j2ee imports
import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.FinderException;

// waf imports
import com.sun.j2ee.blueprints.waf.controller.web.util.WebKeys;
import com.sun.j2ee.blueprints.waf.util.EJBUtil;
import com.sun.j2ee.blueprints.waf.util.JNDINames;
import com.sun.j2ee.blueprints.waf.exceptions.GeneralFailureException;
import com.sun.j2ee.blueprints.waf.exceptions.AppException;
import com.sun.j2ee.blueprints.waf.controller.web.ServiceLocatorImpl;
import com.sun.j2ee.blueprints.waf.controller.web.WebClientController;
import com.sun.j2ee.blueprints.waf.controller.web.util.WebKeys;
import com.sun.j2ee.blueprints.util.tracer.Debug;

// petstore imports
import com.sun.j2ee.blueprints.petstore.controller.web.util.PetstoreKeys;

/**
 * This implmentation class of the ServiceLocator provides
 * access to services in the web tier and ejb tier.
 *
 */
public class ShoppingClientServiceLocatorImpl extends ServiceLocatorImpl {

    public void init(HttpSession session) {
        this.session = session;
        this.session.setAttribute(WebKeys.SERVICE_LOCATOR, this);
        getCustomer();
    }

    /**
     *
     * Create the WebClientController which in turn should create the
     * EJBClientController.
     *
     */
    public void sessionCreated(HttpSessionEvent se) {
        super.sessionCreated(se);
        // initialize the customer
        getCustomer();
    }

    // add  methods for web helpers

    public CustomerWebHelper  getCustomer() {
        CustomerWebHelper customer = (CustomerWebHelper)session.getAttribute(PetstoreKeys.CUSTOMER);
        if (customer == null) {
            if (session.getAttribute(PetstoreKeys.CUSTOMER) == null) {
                    customer = new CustomerWebHelper();
                    customer.init(session);
            } else {
                customer = (CustomerWebHelper)session.getAttribute(PetstoreKeys.CUSTOMER);
            }
        }
        return customer;
    }
}


