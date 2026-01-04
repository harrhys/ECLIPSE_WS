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
import javax.ejb.FinderException;
import javax.naming.NamingException;
import javax.ejb.RemoveException;

import com.sun.j2ee.blueprints.util.tracer.Debug;

// petstore imports
import com.sun.j2ee.blueprints.petstore.controller.web.util.PetstoreKeys;
import com.sun.j2ee.blueprints.petstore.controller.ejb.ShoppingClientControllerLocal;
import com.sun.j2ee.blueprints.petstore.controller.ejb.ShoppingClientFacadeLocal;

// customer component imports
import com.sun.j2ee.blueprints.customer.ejb.CustomerLocal;
import com.sun.j2ee.blueprints.customer.profile.ejb.ProfileLocal;

// contactinfo component imports
import com.sun.j2ee.blueprints.contactinfo.ejb.ContactInfoLocal;

// credit card component
import com.sun.j2ee.blueprints.creditcard.ejb.CreditCardLocal;

/**
 *
 */
public class CustomerWebHelper implements java.io.Serializable {

    private CustomerLocal customer;
    private HttpSession session;

    // these values are used for the profile when a user is not logged in
    // once logged in and the customer is loaded the users preferences
    // will be used.
    private String defaultPreferredLanguage = "";
    private String defaultFavoriteCategory = "";
    private  boolean defaultMyListPreference = false;
    private  boolean defaultBannerPreference = false;

    private ProfileLocal profile = null;

    public CustomerWebHelper() {
    }

    /**
     *  initialize  an HTTP client.
     * @param the HTTP session object for a client
     */
    public void init(HttpSession session) {
        this.session = session;
        session.setAttribute(PetstoreKeys.CUSTOMER, this);
    }

    // lazy load -- only load the ejb when needed
    private void getCustomerEJB() {
        ShoppingClientControllerLocal sccEjb = null;
        sccEjb = (ShoppingClientControllerLocal)session.getAttribute(PetstoreKeys.EJB_CLIENT_CONTROLLER);

        if (sccEjb != null) {
            try {
                ShoppingClientFacadeLocal scf = sccEjb.getShoppingClientFacade();
                customer = scf.getCustomer();
                profile = customer.getProfile();
            } catch (FinderException e) {
                System.err.println("CustomerWebHelper finder error: " + e);
            } catch (Exception e) {
                System.err.println("CustomerWebHelper error: " + e);
            }
        }
    }

    public ProfileLocal getProfile() {
        if (customer == null) getCustomerEJB();
        return profile;
    }

    /**
     * Return a reference to the Local Credit Card EJB
     */
    public CreditCardLocal getCreditCard() {
        if (customer == null) getCustomerEJB();
        return customer.getAccount().getCreditCard();
    }

    /**
     * Return a reference to the Local ContactInfo EJB
     */
    public ContactInfoLocal getContactInfo() {
        if (customer == null) getCustomerEJB();
        return customer.getAccount().getContactInfo();
    }


    public String getPreferredLanguage() {
        if (profile != null) return profile.getPreferredLanguage();
        else return defaultPreferredLanguage;
    }

    public String getFavoriteCategory() {
        if (profile != null) return profile.getFavoriteCategory();
        else return defaultFavoriteCategory;
    }

    public boolean getBannerPreference() {
        if (profile != null) return profile.getBannerPreference();
        else return defaultBannerPreference;
    }

    public boolean getMyListPreference() {
           if (profile != null) return  profile.getMyListPreference();
           else return defaultMyListPreference;
    }



}

