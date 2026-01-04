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

package com.sun.j2ee.blueprints.catalog.client;

import java.util.Locale;

//j2ee imports
import javax.naming.NamingException;
import javax.ejb.CreateException;
import javax.ejb.FinderException;
import javax.rmi.PortableRemoteObject;
import javax.naming.InitialContext;

// catalog component imports
import com.sun.j2ee.blueprints.catalog.ejb.CatalogLocalHome;
import com.sun.j2ee.blueprints.catalog.ejb.CatalogLocal;

import com.sun.j2ee.blueprints.catalog.dao.CatalogDAO;
import com.sun.j2ee.blueprints.catalog.dao.CatalogDAOFactory;
import com.sun.j2ee.blueprints.catalog.exceptions.CatalogDAOSysException;

import com.sun.j2ee.blueprints.catalog.model.Page;
import com.sun.j2ee.blueprints.catalog.model.Category;
import com.sun.j2ee.blueprints.catalog.model.Product;
import com.sun.j2ee.blueprints.catalog.model.Item;



/**
 * This helper class makes calls to the local Catalog EJB.
 */
public class CatalogClientHelper  {

    public static final String CATALOG_EJBHOME =
        "java:comp/env/ejb/local/Catalog";

    private CatalogDAO dao;

    private boolean useFastLane = false;

    public CatalogClientHelper(boolean useFastLane) {
        this.useFastLane = useFastLane;
    }

    public CatalogClientHelper() {
        useFastLane = true;
    }

    /**
     * Uses the CatalogEJB to search
     */
    public Page searchItems(String searchQuery, int start, int count, String localeString)
        throws CatalogClientException {
        Locale locale = getLocaleFromString(localeString);
        return useFastLane
            ? searchItemsFromDAO(searchQuery, start, count, locale)
            : searchItemsFromEJB(searchQuery, start, count, locale);
    }


    /**
     * Uses the CatalogEJB to search
     */
    public Page searchItems(String searchQuery, int start, int count)
        throws CatalogClientException {
        return useFastLane
            ? searchItemsFromDAO(searchQuery, start, count, Locale.getDefault())
            : searchItemsFromEJB(searchQuery, start, count, Locale.getDefault());
    }

    Page searchItemsFromEJB(String searchQuery, int start, int count, Locale locale)
        throws CatalogClientException {
        try {
            return getCatalogEJB().searchItems(searchQuery, start, count,
                                               locale);
        }
        catch (CreateException ce) {
            throw new CatalogClientException("CatalogClientHelper:: CreateException Error trying to lookup EJB" + ce);
        }
        catch (NamingException ne) {
            throw new CatalogClientException("CatalogClientHelper:: NamingException Error trying to lookup EJB" + ne);
        }
        catch (ClassCastException cce) {
            throw new CatalogClientException("CatalogClientHelper:: ClassCastException Error trying to lookup EJB" + cce);
        }
        catch (Exception e) {
            throw new CatalogClientException("CatalogClientHelper:: java.lang.Exception Error trying to lookup EJB" + e);
        }
    }

    Page searchItemsFromDAO(String searchQuery, int start, int count, Locale locale)
        throws CatalogClientException {
        try {
            if (dao == null)
                dao = CatalogDAOFactory.getDAO();
            return dao.searchItems(searchQuery, start, count,
                                   locale);
        }
        catch (CatalogDAOSysException se) {
            System.out.println("Exception reading data from dao " + se);
            throw new CatalogClientException(se.getMessage());
        }
    }

    public Page getCategories(int start, int count, String localeString)
        throws CatalogClientException {
        Locale locale = getLocaleFromString(localeString);
        return useFastLane
            ? getCategoriesFromDAO(start, count, locale)
            : getCategoriesFromEJB(start, count, locale);
    }

    public Page getCategories(int start, int count)
        throws CatalogClientException {
        return useFastLane
            ? getCategoriesFromDAO(start, count, Locale.getDefault())
            : getCategoriesFromEJB(start, count, Locale.getDefault());
    }

    Page getCategoriesFromDAO(int start, int count, Locale locale)
        throws CatalogClientException {
        try {
            if (dao == null)
                dao = CatalogDAOFactory.getDAO();
            return dao.getCategories(start, count, locale);
        }
        catch (CatalogDAOSysException se) {
            System.out.println("Exception reading data from dao " + se);
            throw new CatalogClientException(se.getMessage());
        }
    }

    /**
     * Uses the CatalogEJB to get a list of all the products in a category
     */
    Page getCategoriesFromEJB(int start, int count, Locale locale)
        throws CatalogClientException {
        try {
            return getCatalogEJB().getCategories(start, count,
                                                 locale);
        }
        catch (CreateException ce) {
            ce.printStackTrace();
            throw new CatalogClientException("CatalogClientHelper:: CreateException Error trying to lookup EJB " + ce);
        }
        catch (NamingException ne) {
            throw new CatalogClientException("CatalogClientHelper:: NamingException Error trying to lookup EJB" + ne);
        }
        catch (ClassCastException cce) {
            cce.printStackTrace();
            throw new CatalogClientException("CatalogClientHelper:: ClassCastException Error trying to lookup EJB" + cce);
        }
        catch (Exception e) {
            throw new CatalogClientException("CatalogClientHelper:: java.lang.Exception Error trying to lookup EJB" + e);
        }
    }

    public Page getProducts(String categoryID, int start, int count, String localeString)
        throws CatalogClientException {
         Locale locale = getLocaleFromString(localeString);
        return useFastLane
            ? getProductsFromDAO(categoryID, start, count, locale)
            : getProductsFromEJB(categoryID, start, count,locale);
    }

    public Page getProducts(String categoryID, int start, int count)
        throws CatalogClientException {
        return useFastLane
            ? getProductsFromDAO(categoryID, start, count, Locale.getDefault())
            : getProductsFromEJB(categoryID, start, count, Locale.getDefault());
    }

    Page getProductsFromEJB(String categoryID, int start, int count, Locale locale)
        throws CatalogClientException {
        try {
            return getCatalogEJB().getProducts(categoryID, start, count,
                                               locale);
        }
        catch (CreateException ce) {
            throw new CatalogClientException("CatalogClientHelper:: CreateException Error trying to lookup EJB" + ce);
        }
        catch (NamingException ne) {
            throw new CatalogClientException("CatalogClientHelper:: NamingException Error trying to lookup EJB" + ne);
        }
        catch (ClassCastException cce) {
            throw new CatalogClientException("CatalogClientHelper:: ClassCastException Error trying to lookup EJB" + cce);
        }
        catch (Exception e) {
            throw new CatalogClientException("CatalogClientHelper:: java.lang.Exception Error trying to lookup EJB" + e);
        }
    }

    Page getProductsFromDAO(String categoryID, int start, int count, Locale locale)
        throws CatalogClientException {
        try {
            if (dao == null)
                dao = CatalogDAOFactory.getDAO();
            return dao.getProducts(categoryID, start, count,
                                   locale);
        }
        catch (CatalogDAOSysException se) {
            System.out.println("Exception reading data from dao " + se);
            throw new CatalogClientException(se.getMessage());
        }
    }

    public Page getItems(String productID, int start, int count, String localeString)
        throws CatalogClientException {
        Locale locale = getLocaleFromString(localeString);
        return useFastLane
            ? getItemsFromDAO(productID, start, count, locale)
            : getItemsFromEJB(productID, start, count, locale);
    }


    public Page getItems(String productID, int start, int count)
        throws CatalogClientException {
        return useFastLane
            ? getItemsFromDAO(productID, start, count, Locale.getDefault())
            : getItemsFromEJB(productID, start, count, Locale.getDefault());
    }

    Page getItemsFromEJB(String productID, int start, int count, Locale locale)
        throws CatalogClientException {
        try {
            return getCatalogEJB().getItems(productID, start, count,
                                            locale);
        }
        catch (CreateException ce) {
            throw new CatalogClientException("CatalogClientHelper.getItems:: CreateException Error trying to lookup EJB" + ce);
        }
        catch (NamingException ne) {
            throw new CatalogClientException("CatalogClientHelper.getItems:: NamingException Error trying to lookup EJB" + ne);
        }
        catch (ClassCastException cce) {
            throw new CatalogClientException("CatalogClientHelper.getItems:: ClassCastException Error trying to lookup EJB" + cce);
        }
        catch (Exception e) {
            throw new CatalogClientException("CatalogClientHelper.getItems:: java.lang.Exception Error trying to lookup EJB" + e);
        }
    }

    Page getItemsFromDAO(String productID, int start, int count, Locale locale)
        throws CatalogClientException {
        try {
            if (dao == null)
                dao = CatalogDAOFactory.getDAO();
            return dao.getItems(productID, start, count, locale);
        }
        catch (CatalogDAOSysException se) {
            System.out.println("Exception reading data from dao " + se);
            throw new CatalogClientException(se.getMessage());
        }
    }

        /**
     */
    public Item getItem(String itemID, Locale locale) throws CatalogClientException {
        return useFastLane ? getItemFromDAO(itemID,locale) : getItemFromEJB(itemID,locale);
    }

    /**
     */
    public Item getItem(String itemID, String localeString) throws CatalogClientException {
        Locale locale = getLocaleFromString(localeString);
        return useFastLane ? getItemFromDAO(itemID,locale) : getItemFromEJB(itemID,locale);
    }
    /**
     */
    public Item getItem(String itemID) throws CatalogClientException {
        return useFastLane ? getItemFromDAO(itemID,Locale.getDefault()) : getItemFromEJB(itemID,Locale.getDefault());
    }

    Item getItemFromDAO(String itemID, Locale locale) throws CatalogClientException {
        try {
            if (dao == null)
                dao = CatalogDAOFactory.getDAO();
            return dao.getItem(itemID, locale);
        }
        catch (CatalogDAOSysException se) {
            System.out.println("Exception reading data from dao " + se);
            throw new CatalogClientException(se.getMessage());
        }
    }

    Item getItemFromEJB(String itemID, Locale locale) throws CatalogClientException {
        try {
            return getCatalogEJB().getItem(itemID, locale);
        }
        catch (CreateException ce) {
            throw new CatalogClientException("CatalogClientHelper.getItem:: CreateException Error trying to lookup EJB" + ce);
        }
        catch (NamingException ne) {
            throw new CatalogClientException("CatalogClientHelper.getItem:: NamingException Error trying to lookup EJB" + ne);
        }
        catch (ClassCastException cce) {
            throw new CatalogClientException("CatalogClientHelper.getItem:: ClassCastException Error trying to lookup EJB" + cce);
        }
        catch (Exception e) {
            throw new CatalogClientException("CatalogClientHelper.getItem:: java.lang.Exception Error trying to lookup EJB" + e);
        }
    }

    // Helper methods

    CatalogLocal getCatalogEJB()
        throws NamingException, CreateException {
        InitialContext initial = new InitialContext();
        Object objref = initial.lookup(CATALOG_EJBHOME);
        CatalogLocalHome home = (CatalogLocalHome) objref;
        return home.create();
    }

    /**
     * Convert a string based locale into a Locale Object
     * <br>
     * <br>Strings are formatted:
     * <br>
     * <br>language_contry_variant
     *
     **/

    private Locale getLocaleFromString(String localeString) {
        if (localeString == null) return null;
        if (localeString.toLowerCase().equals("default")) return Locale.getDefault();
        int languageIndex = localeString.indexOf('_');
        if (languageIndex  == -1) return null;
        int countryIndex = localeString.indexOf('_', languageIndex +1);
        String country = null;
        if (countryIndex  == -1) {
            if (localeString.length() > languageIndex) {
                country = localeString.substring(languageIndex +1, localeString.length());
            } else {
                return null;
            }
        }
        int variantIndex = -1;
        if (countryIndex != -1) countryIndex = localeString.indexOf('_', countryIndex +1);
        String language = localeString.substring(0, languageIndex);
        String variant = null;
        if (variantIndex  != -1) {

            variant = localeString.substring(variantIndex +1, localeString.length());
        }
        if (variant != null) {
            return new Locale(language, country, variant);
        } else {
            return new Locale(language, country);
        }

    }
}

