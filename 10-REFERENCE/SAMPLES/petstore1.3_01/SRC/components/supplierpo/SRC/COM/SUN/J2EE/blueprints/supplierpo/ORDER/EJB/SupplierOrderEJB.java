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

import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.CreateException;
import javax.ejb.RemoveException;

import java.util.Iterator;
import java.util.ArrayList;
import java.util.Collection;

import com.sun.j2ee.blueprints.contactinfo.ejb.ContactInfoLocal;
import com.sun.j2ee.blueprints.lineitem.ejb.LineItemLocal;
import com.sun.j2ee.blueprints.lineitem.ejb.LineItem;

/**
 * This is the main Entity Bean class for SupplierOrderEJB
 * It has a one-many relatioship with the LineItemEJB, and one-to-one
 * relationship between ContactInfoEJB
 */

public abstract class SupplierOrderEJB implements EntityBean {

    private EntityContext context = null;

    /**
     * Accessor method for purchase order ID
     * returns String  the PO id
     */
    public abstract String getPoId();

    /**
     * Setter method for PO id
     * @param String the PO ID
     */
    public abstract void setPoId(String id);

    /**
     * Accessor method for purchase order Date
     * returns long  the PO Date - time from epoch
     */
    public abstract long getPoDate();


    /**
     * Setter method for PO Date
     * @param long the PO Date - time from epoch
     */
    public abstract void setPoDate(long orderDate);

    /**
     * Accessor method for purchase order status
     * returns String  the PO status
     */
    public abstract String getPoStatus();


    /**
     * Setter method for PO status
     * @param String the PO status
     */
    public abstract void setPoStatus(String stat);

    /**
     * Accessor method for purchase order contact info
     * returns <Code>ContactInfoLocal</Code>  the PO contactinfo
     */
    public abstract ContactInfoLocal getContactInfo();

    /**
     * Setter method for PO contact info
     * @param <Code>ContactInfoLocal</Code> the PO contact info
     */
    public abstract void setContactInfo(ContactInfoLocal addr);

    /**
     * Accessor method for purchase order line items
     * returns <Code>Collection</Code>  the PO lientems
     */
    public abstract Collection getLineItems();

    /**
     * Setter method for PO line items
     * @param <Code>Collection</Code> the PO lineitems
     */
    public abstract void setLineItems(Collection litems);

    /**
     * Method adds a line item to existing lineitems
     * @param <Code>LineItemLocal</Code> the line item details
     */
    public void addLineItem(LineItemLocal lItem){
        getLineItems().add(lItem);
    }

    /**
     * the ejbCreate method
     * @param id  the PO id
     * @param date the date in time from epoch
     * @param poStat the staus of PO
     */
    public String ejbCreate(String id, long poDate, String poStat)
                                             throws CreateException {
        setPoId(id);
        setPoDate(poDate);
        setPoStatus(poStat);
        return null;
    }

    /**
     * Method gets a collection of all line items for this PO. This is required
     * because web tier cannot access CMR fields that return managed objects
     * without a transaction
     * @return <Code>Collection</Code> Colleciton of lineitems
     */
    public Collection getAllItems() {
        Collection liColl = getLineItems();
        if(liColl == null)
            return(null);
        ArrayList retVal = new ArrayList();
        Iterator it = liColl.iterator();
        while((it!=null) && (it.hasNext())) {
            LineItemLocal loc = (LineItemLocal) it.next();
            retVal.add(new LineItem(loc.getCategoryId(), loc.getProductId(),
                                    loc.getItemId(), loc.getLineNumber(),
                                    loc.getQuantity(), loc.getUnitPrice(),
                                    loc.getLineItemStatus()));
        }
        return(retVal);
    }

    // other ejb life cycle methods
    public void ejbPostCreate(String id, long poDate, String poStat)
        throws CreateException{}
    public void setEntityContext(EntityContext c){ context = c; }
    public void unsetEntityContext(){}
    public void ejbRemove() throws RemoveException {}
    public void ejbActivate() {}
    public void ejbPassivate() {}
    public void ejbStore() {}
    public void ejbLoad() {}
}

