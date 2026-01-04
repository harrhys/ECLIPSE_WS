/*
 *
 * Copyright 2002 Sun Microsystems, Inc. All Rights Reserved.
 * 
 * Redistribution and use in source and binary forms, with or
 * without modification, are permitted provided that the following
 * conditions are met:
 * 
 * - Redistributions of source code must retain the above copyright
 *   notice, this list of conditions and the following disclaimer.
 * 
 * - Redistribution in binary form must reproduce the above
 *   copyright notice, this list of conditions and the following
 *   disclaimer in the documentation and/or other materials
 *   provided with the distribution.
 * 
 * Neither the name of Sun Microsystems, Inc. or the names of
 * contributors may be used to endorse or promote products derived
 * from this software without specific prior written permission.
 * 
 * This software is provided "AS IS," without a warranty of any
 * kind. ALL EXPRESS OR IMPLIED CONDITIONS, REPRESENTATIONS AND
 * WARRANTIES, INCLUDING ANY IMPLIED WARRANTY OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE OR NON-INFRINGEMENT, ARE HEREBY
 * EXCLUDED. SUN AND ITS LICENSORS SHALL NOT BE LIABLE FOR ANY
 * DAMAGES OR LIABILITIES SUFFERED BY LICENSEE AS A RESULT OF OR
 * RELATING TO USE, MODIFICATION OR DISTRIBUTION OF THIS SOFTWARE OR
 * ITS DERIVATIVES. IN NO EVENT WILL SUN OR ITS LICENSORS BE LIABLE
 * FOR ANY LOST REVENUE, PROFIT OR DATA, OR FOR DIRECT, INDIRECT,
 * SPECIAL, CONSEQUENTIAL, INCIDENTAL OR PUNITIVE DAMAGES, HOWEVER
 * CAUSED AND REGARDLESS OF THE THEORY OF LIABILITY, ARISING OUT OF
 * THE USE OF OR INABILITY TO USE THIS SOFTWARE, EVEN IF SUN HAS
 * BEEN ADVISED OF THE POSSIBILITY OF SUCH DAMAGES.
 * 
 * You acknowledge that this software is not designed, licensed or
 * intended for use in the design, construction, operation or
 * maintenance of any nuclear facility.
 * 
 */

package com.sun.cb;

import javax.xml.soap.*;
import javax.servlet.*;
import javax.servlet.http.*;

import javax.xml.transform.*;

import java.util.*;
import java.io.*;

public class ConfirmationServlet extends HttpServlet {
    static MessageFactory fac = null;
    
    static {
        try {
            fac = MessageFactory.newInstance();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    };

    MessageFactory msgFactory;

    public void init(ServletConfig servletConfig) 
               throws ServletException {
        super.init(servletConfig);
        try {
            // Initialize it to the default.
            msgFactory = MessageFactory.newInstance();
        } catch (SOAPException ex) {
            throw new ServletException(
             "Unable to create message factory"
                    + ex.getMessage());
        }
    }
    
    public void doPost( HttpServletRequest req, 
      HttpServletResponse resp)
                throws ServletException, IOException {
  try {
      // Get all the headers from the HTTP request.
        MimeHeaders headers = getHeaders(req);

      // Get the body of the HTTP request.
      InputStream is = req.getInputStream();

      // Now internalize the contents of a HTTP request and
      // create a SOAPMessage
      SOAPMessage msg = msgFactory.createMessage(headers, is);
      
      SOAPMessage reply = null;

      reply = onMessage(msg);

      if (reply != null) {
                
    // Need to call saveChanges because we're going to use the
    // MimeHeaders to set HTTP response information. These
    // MimeHeaders are generated as part of the save.

        if (reply.saveRequired()) {
            reply.saveChanges(); 
        }

        resp.setStatus(HttpServletResponse.SC_OK);

        putHeaders(reply.getMimeHeaders(), resp);
                    
        // Write out the message on the response stream.
        OutputStream os = resp.getOutputStream();
        reply.writeTo(os);
    
        os.flush();
                    
      } else 
        resp.setStatus(HttpServletResponse.SC_NO_CONTENT);

  } catch (Exception ex) {
      throw new ServletException("JAXM POST failed "+ex.getMessage());
  }
    }

    static MimeHeaders getHeaders(HttpServletRequest req) {

  Enumeration enum = req.getHeaderNames();
  MimeHeaders headers = new MimeHeaders();

  while (enum.hasMoreElements()) {
      String headerName = (String)enum.nextElement();
      String headerValue = req.getHeader(headerName);

      StringTokenizer values = new StringTokenizer(headerValue, ",");
      while (values.hasMoreTokens())
    headers.addHeader(headerName, values.nextToken().trim());
  }

  return headers;
    }

    static void putHeaders(MimeHeaders headers, 
               HttpServletResponse res) {

  Iterator it = headers.getAllHeaders();
  while (it.hasNext()) {
      MimeHeader header = (MimeHeader)it.next();

      String[] values = headers.getHeader(header.getName());
      if (values.length == 1)
    res.setHeader(header.getName(), header.getValue());
      else {
    StringBuffer concat = new StringBuffer();
    int i = 0;
    while (i < values.length) {
        if (i != 0)
      concat.append(',');
        concat.append(values[i++]);
    }

    res.setHeader(header.getName(), concat.toString());
        }
    }
    }

    // This is the application code for handling the message.

    public SOAPMessage onMessage(SOAPMessage message) {
    SOAPMessage confirmation = null;
        try {
            //retrieve orderID from message received
      SOAPBody sentSB = message.getSOAPPart().
                                getEnvelope().getBody();
      Iterator sentIt = sentSB.getChildElements();
            SOAPBodyElement sentSBE = (SOAPBodyElement)sentIt.next();
      Iterator sentIt2 = sentSBE.getChildElements();
      SOAPElement sentSE = (SOAPElement)sentIt2.next();

      //get the orderID to put in confirmation
      String sentID = sentSE.getValue();

        //create confirmation message
      //MessageFactory factory = MessageFactory.newInstance();
      confirmation = fac.createMessage();
      SOAPPart sp = confirmation.getSOAPPart();
      SOAPEnvelope env = sp.getEnvelope();
      SOAPBody sb = env.getBody();

        Name newBodyName = env.createName("confirmation", 
           "Confirm", "http://sonata.coffeebreak.com");
        SOAPBodyElement confirm = 
           sb.addBodyElement(newBodyName);
        //orderID
        Name newOrderIDName = env.createName("orderId");
        SOAPElement newOrderNo =
                 confirm.addChildElement(newOrderIDName);

        newOrderNo.addTextNode(sentID);

            //ship-date
        Name shipDateName = env.createName("ship-date");
        SOAPElement shipDate = 
        confirm.addChildElement(shipDateName);

            //create shipping date
            Date today = new Date();
          long msPerDay = 1000 * 60 * 60 * 24;
          long msTarget = today.getTime();
          long msSum = msTarget + (msPerDay * 2);
          Date result = new Date();
          result.setTime(msSum);
            String sd = result.toString();

        shipDate.addTextNode(sd);

      confirmation.saveChanges();

      } catch (Exception ex) {
        ex.printStackTrace();
      }
    return confirmation;
    }
}

