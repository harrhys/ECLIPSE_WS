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

package com.sun.j2ee.blueprints.supplierpo.order.ejb;

import java.util.Collection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Date;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.sun.j2ee.blueprints.xmldocuments.SupplierPO;
import com.sun.j2ee.blueprints.xmldocuments.LineItems;

import com.sun.j2ee.blueprints.supplierpo.order.ejb.SupplierOrderLocal;
import com.sun.j2ee.blueprints.supplierpo.order.ejb.SupplierOrderLocalHome;

import com.sun.j2ee.blueprints.contactinfo.ejb.ContactInfoLocal;
import com.sun.j2ee.blueprints.contactinfo.ejb.ContactInfoLocalHome;
import com.sun.j2ee.blueprints.address.ejb.AddressLocal;
import com.sun.j2ee.blueprints.address.ejb.AddressLocalHome;

import com.sun.j2ee.blueprints.lineitem.ejb.LineItemLocal;
import com.sun.j2ee.blueprints.lineitem.ejb.LineItemLocalHome;
import com.sun.j2ee.blueprints.lineitem.ejb.LineItem;

/**
 * This is a helper classes for the creating and parsing through the PO of
 * supplier
 */
public class SupplierOrderHelper {

    public final String PO_EJB = "java:comp/env/ejb/local/SupplierOrder";
    public final String CINFO_EJB = "java:comp/env/ejb/local/ContactInfo";
    public final String ADDR_EJB = "java:comp/env/ejb/local/Address";
    public final String LI_EJB = "java:comp/env/ejb/local/LineItem";

    private SupplierPO poObject;

    public SupplierOrderHelper() {}

    /**
     * Method persists the PO's CMP info
     * @return true if creation was ok; false if not
     */
    private boolean persistPoCMPInfo() {
        try {
            InitialContext initial = new InitialContext();
            Object objref = initial.lookup(PO_EJB);
            SupplierOrderLocalHome ref = (SupplierOrderLocalHome) objref;
            ref.create(poObject.getOrderId(),
                       poObject.getOrderDate().getTime(), "PENDING");
        } catch(CreateException ne) {
            System.out.println("Create Ex while persisting SuppPO CMP :" +
                    ne.getMessage());
            return(false);
        } catch(NamingException ne) {
            System.out.println("naming Ex while persisting SuppPO CMP :" +
                    ne.getMessage());
            return(false);
        }
        return(true);
    }

    /**
     * Method returns the local interface of given PO
     * @param String  the ID
     * @return <Code>SupplierOrderLocal</Code>
     */
    private SupplierOrderLocal findPO(String localOrderId) {
        SupplierOrderLocal po = null;
        try {
            InitialContext initial = new InitialContext();
            Object objref = initial.lookup(PO_EJB);
            SupplierOrderLocalHome poref = (SupplierOrderLocalHome) objref;
            po = poref.findByPrimaryKey(localOrderId);
        } catch(FinderException ne) {
            System.out.println("finder Ex while lloking up SuppPO :" +
                    ne.getMessage());
            return(null);
        } catch(NamingException ne) {
            System.out.println("naming Ex while persisting SuppPO CMR :" +
                    ne.getMessage());
            return(null);
        }
        return(po);
    }

    /**
     * Tis method persists PO's CMR info; Ideally this should goto the ejb's
     * post create method
     * @return true if creation was ok; false if not
     */
    private boolean persistPoCMRInfo() {
        try {
            InitialContext initial = new InitialContext();
            SupplierOrderLocal po = findPO(poObject.getOrderId());
            Object objref = initial.lookup(ADDR_EJB);
            AddressLocalHome addref = (AddressLocalHome) objref;
            AddressLocal addloc = (AddressLocal)
                addref.create(poObject.getShipStreet(), "",
                              poObject.getShipCity(), poObject.getShipState(),
                              poObject.getShipZip(),
                              poObject.getShipCountry());
            objref = initial.lookup(CINFO_EJB);
            ContactInfoLocalHome cinforef = (ContactInfoLocalHome)objref;
            ContactInfoLocal cinfoloc = (ContactInfoLocal)
                cinforef.create(poObject.getShipFirstName(),
                                poObject.getShipLastName(),
                                "NULL", "NULL", addloc);
            po.setContactInfo(cinfoloc);
            objref = initial.lookup(LI_EJB);
            LineItemLocalHome lineItemref = (LineItemLocalHome) objref;
            Collection litems = poObject.getLineItems();
            Iterator it = litems.iterator();
            while((it != null) && (it.hasNext())) {
                LineItems li = (LineItems) it.next();
                LineItemLocal lineItemloc = (LineItemLocal)
                    lineItemref.create(li.getCategoryId(), li.getProductId(),
                                       li.getItemId(), li.getLineNo(),
                                       li.getQuantity(), li.getUnitPrice(),
                                       "PENDING");
                po.addLineItem(lineItemloc);
            }
        } catch(CreateException ne) {
            System.out.println("Create Ex while persisting SuppPO CMR :" +
                    ne.getMessage());
            return(false);
        } catch(NamingException ne) {
            System.out.println("naming Ex while persisting SuppPO CMR :" +
                    ne.getMessage());
            return(false);
        }
        return(true);
    }

    /**
     * This method persists all info for a new po
     * @param <Code>SupplierPO</Code> the java object representation of PO
     * @return true or false for success / failure
     */
    public boolean persistPO(SupplierPO obj) {

        // Persist the information
        this.poObject = obj;
        if(persistPoCMPInfo() && persistPoCMRInfo())
            return(true);
        return(false);
    }

    /**
     * given a po id, this method find that po and sets its status to given
     * status
     * @param id
     * @param status
     * @return true / false
     */
    public boolean setPOStatus(String id, String status) {
        SupplierOrderLocal po = findPO(id);
        if(po == null)
            return(false);
        po.setPoStatus(status);
        return(true);
    }

    /**
     * Given a po id, ths returns the po's status
     * @param String po id
     * @return Stirng the status
     */
    public String getPOStatus(String id) {
        SupplierOrderLocal po = findPO(id);
        if(po == null)
            return(null);
        return(po.getPoStatus());
    }
}

