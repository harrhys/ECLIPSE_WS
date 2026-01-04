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

package com.sun.j2ee.blueprints.supplier.processpo.ejb;

import javax.naming.NamingException;
import javax.naming.InitialContext;
import javax.jms.TopicConnectionFactory;
import javax.jms.Topic;

/**
 * This class is used to get the resources to send a JMS Message
 * Any change here should also be reflected in the deployment
 * descriptors since this class uses resources specified in the
 * deployment descriptors.
 */
public class JMSLocator {

    private static final String TOPIC_CONNECTION_FACTORY =
                            "java:comp/env/jms/topic/TopicConnectionFactory";

    private static final String INVOICE_MDB_TOPIC =
                                  "java:comp/env/jms/topic/opc/InvoiceTopic";

    private InitialContext ic;

    public JMSLocator() throws NamingException {
        ic = new InitialContext();
    }

    /**
     * This method helps in obtaining the topic factory
     * @return the factory for the factory to get topic connections from
     */
    public  TopicConnectionFactory getTopicConnectionFactory()
                                                 throws NamingException {
        return (TopicConnectionFactory) ic.lookup(TOPIC_CONNECTION_FACTORY);
    }

    /**
     * This method obtains the topc itself for a caller
     * @return the Topic Destination to send messages to
     */
    public  Topic getTopic() throws NamingException {
        return (Topic)ic.lookup(INVOICE_MDB_TOPIC);
    }
}

