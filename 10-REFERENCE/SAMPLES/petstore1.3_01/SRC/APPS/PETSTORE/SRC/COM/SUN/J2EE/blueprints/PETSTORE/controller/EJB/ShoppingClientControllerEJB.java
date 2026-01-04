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
import com.sun.j2ee.blueprints.waf.controller.ejb.EJBClientControllerEJB;
import com.sun.j2ee.blueprints.waf.controller.ejb.StateMachine;
import com.sun.j2ee.blueprints.waf.exceptions.GeneralFailureException;

/**
 * Session Bean implementation for EJBClientController EJB.
 * See the StateMachine for more details.
 */
public class ShoppingClientControllerEJB extends EJBClientControllerEJB {
    private ShoppingClientFacadeLocal clientFacade = null;

    public void ejbCreate() {
        sm = new StateMachine(this, sc);
        sm.setAttribute("shoppingClientFacade", getShoppingClientFacade());
    }

    public ShoppingClientFacadeLocal getShoppingClientFacade() {
        if (clientFacade == null) {
            try {
                InitialContext ic = new InitialContext();
                Object o = ic.lookup("java:comp/env/ejb/local/ShoppingClientFacade");
                ShoppingClientFacadeLocalHome home =(ShoppingClientFacadeLocalHome)o;
                clientFacade = home.create();
            } catch (javax.ejb.CreateException cx) {
                throw new GeneralFailureException("ShoppingClientControllerEJB: Failed to Create ShoppingClientFacade: caught " + cx);
            } catch (javax.naming.NamingException nx) {
                throw new GeneralFailureException("ShoppingClientControllerEJB: Failed to Create ShoppingClientFacade: caught " + nx);
            }
        }
        return clientFacade;
    }

}


