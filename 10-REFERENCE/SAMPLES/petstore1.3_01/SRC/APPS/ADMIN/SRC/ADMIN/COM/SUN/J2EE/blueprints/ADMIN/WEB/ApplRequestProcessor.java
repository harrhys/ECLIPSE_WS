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

package com.sun.j2ee.blueprints.admin.web;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;

import org.xml.sax.SAXException;
import org.xml.sax.InputSource;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import java.util.Collection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.HashMap;
import java.util.Set;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import javax.servlet.ServletOutputStream;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import javax.rmi.PortableRemoteObject;
import java.rmi.RemoteException;

import javax.ejb.CreateException;

import com.sun.j2ee.blueprints.opc.admin.ejb.OPCAdminFacadeHome;
import com.sun.j2ee.blueprints.opc.admin.ejb.OPCAdminFacade;
import com.sun.j2ee.blueprints.opc.admin.ejb.OrderDetails;
import com.sun.j2ee.blueprints.opc.admin.ejb.OPCAdminFacadeException;

import com.sun.j2ee.blueprints.asyncsender.util.AsyncHelper;

import com.sun.j2ee.blueprints.xmldocuments.OrderApproval;
import com.sun.j2ee.blueprints.xmldocuments.ChangedOrder;
import com.sun.j2ee.blueprints.xmldocuments.XMLDocumentException;

/**
 * This servlet serves requests from the rich client
 */
public class ApplRequestProcessor extends HttpServlet {

    private String OPC_ADMIN_NAME = "java:comp/env/ejb/remote/OPCAdminFacade";

    private OPCAdminFacade opcAdminEJB = null;
    String replyHeader = "<?xml version=\"1.0\" standalone=\"yes\"?>\n" +
                       "<Response>\n";

    //Helper Classes

    /**
     * This helper method processes the incoming XML document
     * @param xmlString The incoming XML doc in string format
     * @return <Code>Document</Code> the DOM representation of the doc
     * @throws <Code>ParserConfigurationException</Code>
     * @throws <Code>SAXException</Code>
     * @throws <Code>IOException</Code>
     */
    private Document parse(String xmlString) throws
                  ParserConfigurationException, SAXException, IOException {
        Document doc = null;

        ByteArrayInputStream xmlBa = new
                            ByteArrayInputStream(xmlString.getBytes());
        InputSource xmlInp = new InputSource(xmlBa);

        // create a DocumentBuilderFactory and configure it
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        // From the factory get a doc builder
        DocumentBuilder db = null;
        db = dbf.newDocumentBuilder();

        // parse the input file
        doc = db.parse(xmlInp);
        return(doc);
    }

    /**
     * This method gets the value of a give node
     * @param n <Code>Node</Code>
     * @return String that represents value of given Node
     */
    private String getValue(Node n) {
        Node child = n.getFirstChild();
        if(child == null)
            return(null);
        return(child.getNodeValue());
    }

    /**
     * This method returns the orders of given status
     * @param status  The requested status
     * @return String  An xml doc that has the orders of given status or
     *                  an xml document indicating error in case of failures
     */
    private String getOrdersByStatus(String status) {
        int count=0;
        StringBuffer buffer = new StringBuffer("");

        try {
            Collection orderColl = opcAdminEJB.getOrdersByStatus(status);
            if(orderColl == null)
                return(null);
            Iterator it = orderColl.iterator();
            while((it != null) && it.hasNext()) {
                OrderDetails anOrder = (OrderDetails)it.next();
                buffer.append("<Order>\n");
                buffer.append("<OrderId>" + anOrder.getOrderId() +
                              "</OrderId>\n");
                buffer.append("<UserId>" + anOrder.getUserId() +
                              "</UserId>\n");
                buffer.append("<OrderDate>" + anOrder.getOrderDate() +
                              "</OrderDate>\n");
                buffer.append("<OrderAmount>" + anOrder.getOrderValue() +
                              "</OrderAmount>\n");
                buffer.append("<OrderStatus>" + anOrder.getOrderStatus() +
                              "</OrderStatus>\n");
                buffer.append("</Order>\n");
                count++;
            }
        } catch(RemoteException ae) {
            System.out.println("RE in processXML : " + ae.getMessage());
            return(replyHeader +
                   "<Error>Exception while processing :  " + ae.getMessage() +
                   ". Please try again</Error>\n</Response>\n");
        } catch(OPCAdminFacadeException ae) {
            System.out.println("OPCEx in processXML : " + ae.getMessage());
            return(replyHeader +
                   "<Error>Exception while processing :  " + ae.getMessage() +
                   ". Please try again</Error>\n</Response>\n");
        }
        return(replyHeader + "<Type>GETORDERS</Type>\n" +
               "<Status>" + status + "</Status>\n" +
               "<TotalCount>" + count + "</TotalCount>\n" +
               buffer.toString() + "</Response>\n");
    }

    /**
     * This method gets chart details for the rich client
     * @param request  REVENUE or ORDER
     * @param start    start date in mm/dd/yyyy format
     * @param end      end date in mm/dd/yyyy format
     * @param category the requested category
     * @return String  An xml doc that has the chart details for given dates
     *                  an xml document indicating error in case of failures
     */
   private String getChartInfo(String request, String start, String end,
                                String category) {
        int totalQty = 0, qty = 0;
        float totalAmount = 0, amount = 0;
        String id, xmlElement, returnDoc;
        StringBuffer buffer = new StringBuffer("");

        if(category == null)
            xmlElement = "Category";
        else
            xmlElement = "Item";
        try {
            HashMap orderColl = opcAdminEJB.getChartInfo(request, start, end,
                                                       category);
            Set idKeys = orderColl.keySet();
            Iterator it = idKeys.iterator();
            while((it != null) && it.hasNext()) {
                id = (String)it.next();
                if(request.equals("REVENUE")) {
                    Float tmpAmount = (Float)orderColl.get(id);
                    amount = tmpAmount.floatValue();
                    totalAmount += amount;
                } else {
                    Integer tmpQty = (Integer)orderColl.get(id);
                    qty = tmpQty.intValue();
                    totalQty += qty;
                }
                buffer.append("<" + xmlElement + " name=\"" + id + "\">");
                if(request.equals("REVENUE"))
                    buffer.append(amount);
                else
                    buffer.append(qty);
                buffer.append("</" + xmlElement + ">\n");
            }
        } catch(RemoteException ae) {
            System.out.println("RE in processXML : " + ae.getMessage());
            return(replyHeader +
                   "<Error>Exception while processing :  " + ae.getMessage() +
                   ". Please try again</Error>\n</Response>\n");
        } catch(OPCAdminFacadeException ae) {
            System.out.println("OPCEx in processXML : " + ae.getMessage());
            return(replyHeader +
                   "<Error>Exception while processing :  " + ae.getMessage() +
                   ". Please try again</Error>\n</Response>\n");
        }
        returnDoc = replyHeader + "<Type>" + request + "</Type>\n" +
               "<Start>" + start + "</Start>\n" +
               "<End>" + end + "</End>\n" +
               ((category == null) ?
                ("<ReqCategory/>\n") :
                ("<ReqCategory>" + category + "</ReqCategory>\n")) +
               "<TotalSales>";
        if(request.equals("REVENUE"))
            returnDoc += totalAmount;
        else
            returnDoc += totalQty;
        returnDoc += "</TotalSales>\n" + buffer.toString() + "</Response>\n";
        return(returnDoc);
    }

    /**
     * This method processes the incoming XML doc and returns the response
     * @param String  the incoming XML request
     * @return Stirng  the response in XML format
     */
    private String processInputXml(String xmlDoc) {
        String returnDoc = null;
        Document doc = null;
        Element root = null;

        try {
            doc = parse(xmlDoc);
            root = doc.getDocumentElement();
        } catch (ParserConfigurationException pe) {
            return(replyHeader +
                   "<Error>Exception while processing :  " + pe.getMessage() +
                   ". Please try again</Error>\n</Response>\n");
        } catch (SAXException se) {
            return(replyHeader +
                   "<Error>Exception while processing :  " + se.getMessage() +
                   ". Please try again</Error>\n</Response>\n");
        } catch (IOException ie) {
            return(replyHeader +
                   "<Error>Exception while processing :  " + ie.getMessage() +
                   ". Please try again</Error>\n</Response>\n");
        }
        NodeList list = root.getElementsByTagName("Type");
        String reqType = getValue(list.item(0));
        if(reqType.equals("GETORDERS")) {
            list = root.getElementsByTagName("Status");
            String reqStat = getValue(list.item(0));
            returnDoc = getOrdersByStatus(reqStat);
        } else if(reqType.equals("UPDATESTATUS")) { // Should be using a style sheet to convert from one schema to the other
          Element element;
          OrderApproval orderApproval = new OrderApproval();
          list = root.getElementsByTagName("Order");
          for (int i = 0; i < list.getLength(); i++) {
            element = (Element) list.item(i);
            // Assumes values of OrderStatus are the same for both schemas
            Element orderIdElement = (Element) element.getElementsByTagName("OrderId").item(0);
            Element orderStatusElement = (Element) element.getElementsByTagName("OrderStatus").item(0);
            if (orderIdElement != null && orderStatusElement != null) {
              orderApproval.addOrder(new ChangedOrder(getValue(orderIdElement), getValue(orderStatusElement)));
            }
          }
          AsyncHelper sender = new AsyncHelper();
          try {
            sender.sendMessage(orderApproval.toXML());
            returnDoc = replyHeader + "<Type>UPDATEORDERS</Type>\n" +
              "<Status>SUCCESS</Status>\n" +
              "</Response>\n";
          } catch (XMLDocumentException exception) {
            returnDoc = replyHeader +
              "<Error>Exception while processing :  " + exception.getMessage() +
              ". Please try again</Error>\n</Response>\n";
          }
        } else if(reqType.equals("REVENUE") || reqType.equals("ORDERS")) {
            list = root.getElementsByTagName("Start");
            String start = getValue(list.item(0));
            list = root.getElementsByTagName("End");
            String end = getValue(list.item(0));
            list = root.getElementsByTagName("ReqCategory");
            String reqCat = getValue(list.item(0));
            returnDoc = getChartInfo(reqType, start, end, reqCat);
        } else {
            returnDoc = replyHeader +
                        "<Error>Unable to process request : " +
                        "Unknown request type - " + reqType +
                        "</Error>\n</Response>\n";
        }
        return(returnDoc);
    }

    // Servlet classes start here

    public void init() {
        try {
            InitialContext initial = new InitialContext();
System.out.println ("PG-> OPC_ADMIN_NAME = " + OPC_ADMIN_NAME);
            Object objref = initial.lookup(OPC_ADMIN_NAME);
System.out.println ("PG-> narrowObject = " + PortableRemoteObject.narrow(objref, OPCAdminFacadeHome.class));
System.out.println ("PG-> class = " + PortableRemoteObject.narrow(objref, OPCAdminFacadeHome.class).getClass().getName());
            OPCAdminFacadeHome opcHome = (OPCAdminFacadeHome)
                PortableRemoteObject.narrow(objref, OPCAdminFacadeHome.class);
            opcAdminEJB = opcHome.create();
        } catch (CreateException ce) {
            System.out.println("CREATEEX opcAdmin : " + ce.getMessage());
        } catch (NamingException ne) {
            System.out.println("NAMEEX ****************************");
            System.out.println("NAMEEX opcAdmin : " + ne.getMessage());
            System.out.println("NAMEEX opcAdmin : " + ne.toString());
            System.out.println("NAMEEX opcAdmin : " + ne.getExplanation());
            System.out.println("NAMEEX ****************************");
        } catch (RemoteException ne) {
            System.out.println("REMOTEEX opcAdmin : " + ne.getMessage());
        }
    }

    public void doGet(HttpServletRequest req, HttpServletResponse resp)
                throws java.io.IOException, javax.servlet.ServletException {
        doPost(req, resp);
    }

    public void doPost(HttpServletRequest req, HttpServletResponse resp)
                throws java.io.IOException, javax.servlet.ServletException {

        HttpSession session = req.getSession(false);
        ServletOutputStream out = resp.getOutputStream();

        // Check session; if null then there is no authentication; send error
        // If session is not null then the client has been authorised by
        // form based login mechanism
        if(session == null) {
            out.println(replyHeader +
                        "<Error>Session Timed Out; Please exit and " +
                        "login as admin from the login page</Error>\n" +
                        "</Response>\n");
        } else {
            String sId = session.getId();
            resp.setContentType("text/xml");
            BufferedReader inp = req.getReader();
            StringBuffer strbuf = new StringBuffer("");
            while(true) {
                String str = inp.readLine();
                if(str == null)
                    break;
                strbuf.append(str+"\n");
            }
            inp.close();
            out.println(processInputXml(strbuf.toString()));
        }
        out.flush();
        out.close();
    }
}

