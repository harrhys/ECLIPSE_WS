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
package com.sun.j2ee.blueprints.po.card.ejb;

import java.lang.Object;

import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.CreateException;
import javax.ejb.RemoveException;

import java.util.Date;

/**
 * This is the main Entity Bean class for CardEJB
 */

public abstract class CardEJB implements EntityBean {

    private EntityContext context = null;

    // getters and setters for PO CMP fields

    public abstract String getCardNumber();
    public abstract void setCardNumber(String num);
    public abstract String getCardType();
    public abstract void setCardType(String typ);
    public abstract Date getExpiryDate();
    public abstract void setExpiryDate(Date exp);

    public Object ejbCreate(String number, String type, Date expDate)
                                              throws CreateException {
        setCardNumber(number);
        setCardType(type);
        setExpiryDate(expDate);
        return null;
    }

    public void ejbPostCreate(String number, String type, Date expDate)
        throws CreateException{}
    public void setEntityContext(EntityContext c){ context = c; }
    public void unsetEntityContext(){}
    public void ejbRemove() throws RemoveException {}
    public void ejbActivate() {}
    public void ejbPassivate() {}
    public void ejbStore() {}
    public void ejbLoad() {}
}

