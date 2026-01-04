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

package com.sun.j2ee.blueprints.asyncsender.util;


import javax.naming.NamingException;
import javax.ejb.CreateException;
import javax.naming.InitialContext;

import com.sun.j2ee.blueprints.asyncsender.ejb.AsyncSenderLocalHome;



import javax.jms.QueueConnectionFactory;
import javax.jms.Queue;

/**
 * This class is use to get the resources to send a JMS Message
 * Any change here should also be reflected in the deployment
 * descriptors since this class uses resources specified in the
 * deployment descriptors.
 */
public class JMSLocator {


    /**
     * @return the factory Home for the asyncsender session EJB
     */
    public static AsyncSenderLocalHome createAsyncEJB()
                                    throws NamingException, CreateException {
        InitialContext ic = new InitialContext();
          Object objref = ic.lookup(JNDINames.ASYNCSENDER_LOCAL_EJB_HOME);
          return (AsyncSenderLocalHome) objref;
    }


    /**
     * @return the factory for the factory to get queue connections from
     */
    public static QueueConnectionFactory getQueueConnectionFactory()
                                                 throws NamingException {
        InitialContext ic = new InitialContext();
        return (QueueConnectionFactory) ic.lookup(JNDINames.QUEUE_CONNECTION_FACTORY);
    }


    /**
     * @return the Queue Destination to send messages to
     */
    public static Queue getQueue() throws NamingException {
        InitialContext ic = new InitialContext();
        return (Queue)ic.lookup(JNDINames.ASYNC_SENDER_QUEUE);
    }





}

