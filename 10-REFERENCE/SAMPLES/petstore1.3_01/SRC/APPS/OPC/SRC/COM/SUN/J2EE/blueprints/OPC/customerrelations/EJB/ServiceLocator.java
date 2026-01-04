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

package com.sun.j2ee.blueprints.opc.customerrelations.ejb;

import javax.naming.NamingException;
import javax.ejb.CreateException;
import javax.naming.InitialContext;

import javax.jms.QueueConnectionFactory;
import javax.jms.Queue;

/**
 * This class is use to get the resources to send a JMS Message
 * Any change here should also be reflected in the deployment
 * descriptors since this class uses resources specified in the
 * deployment descriptors.
 */
public class ServiceLocator {

    private InitialContext ic = null;

    public ServiceLocator() throws NamingException {
        ic = new InitialContext();
    }

    /**
     * @return the factory for the factory to get queue connections from
     */
    public  QueueConnectionFactory getQueueConnectionFactory(String qConnFactory)
                                                 throws NamingException {
        ic = new InitialContext();
        return (QueueConnectionFactory) ic.lookup(qConnFactory);
    }

    /**
     * @return the Queue Destination to send messages to
     */
    public  Queue getQueue(String mailSenderQueue) throws NamingException {
        return (Queue)ic.lookup(mailSenderQueue);
    }

    /**
     * @return the boolean value corresponding
     * to the SEND_CONFIRMATION_MAIL property.
     * If property is not present in the deployment
     * descriptor, conservatively assume that we do not send
     * status mail for each order.
     */
    public boolean getSendOrderStatusMail(String envName) {
        boolean boolVal = false;
        try {
            Boolean bool = (Boolean)ic.lookup(envName);
            if (bool != null) {
                boolVal = bool.booleanValue();
            }
        } catch (NamingException ne) {
            // If property is not present in the deployment
            // descriptor, conservatively assume that we do not send
            // confirmation mail for each order.
            System.out.println(ne);
        }
        return boolVal;
    }
}

