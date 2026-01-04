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

import java.util.HashMap;
import java.beans.Beans;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Locale;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

// signon component
import com.sun.j2ee.blueprints.signon.web.SignOnFilter;

// waf imports
import com.sun.j2ee.blueprints.waf.controller.web.WebClientController;
import com.sun.j2ee.blueprints.waf.controller.web.ServiceLocator;
import com.sun.j2ee.blueprints.waf.event.EventException;
import com.sun.j2ee.blueprints.waf.util.I18nUtil;

// customer imports
import com.sun.j2ee.blueprints.customer.profile.ejb.ProfileLocal;

// petstore imports
import com.sun.j2ee.blueprints.petstore.controller.web.util.PetstoreKeys;
import com.sun.j2ee.blueprints.petstore.controller.events.SignOnEvent;
import com.sun.j2ee.blueprints.petstore.controller.web.ShoppingClientServiceLocatorImpl;

/**
 * This class will bind with the current session and notify the Petstore
 * Back end when a SignOn has occured.
 *
 * This allows for a loose coupling of the SignOn component and the
 * Petstore Application.
 */
public class SignOnNotifier
   implements java.io.Serializable, HttpSessionAttributeListener {

    private HttpSession session;
    private WebClientController wcc;

    public SignOnNotifier() { }

    /**
     *
     * Create the WebClientController which in turn should create the
     * EJBClientController.
     *
     */
    public void attributeAdded(HttpSessionBindingEvent se) {
        this.session = se.getSession();
        String name = se.getName();

        /* check if the value matches the signon attribute
         * if a macth fire off an event to the ejb tier that the user
         * has signed on and load the account for the user
         */
        if (name.equals(SignOnFilter.SIGNED_ON_USER_SESSION_KEY)) {
            String value = (String)se.getValue();
            if (value.equals("true")) {
              String userName = (String)session.getAttribute(SignOnFilter.SESSION_USER_NAME);
              // look up the model manager and webclient controller
              ShoppingClientServiceLocatorImpl sl = (ShoppingClientServiceLocatorImpl)session.getAttribute(PetstoreKeys.SERVICE_LOCATOR);
              WebClientController wcc =  sl.getWebClientController();
              SignOnEvent soe = new SignOnEvent(userName);
              try {
                  wcc.handleEvent(soe);
              } catch (EventException e) {
                  System.out.println("SignOnNotifier Error handling event " + e);
              }
              // set the language to the preferred language
              ProfileLocal profile = sl.getCustomer().getProfile();
              Locale locale = I18nUtil.getLocaleFromString(profile.getPreferredLanguage());
              session.setAttribute(PetstoreKeys.LOCALE, locale);
            }
        }

    }


    // do nothing
    public void attributeRemoved(HttpSessionBindingEvent se) {}

    // do nothing
    public void attributeReplaced(HttpSessionBindingEvent se) {}
}


