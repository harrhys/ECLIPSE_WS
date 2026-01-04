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


package com.sun.j2ee.blueprints.xmldocuments;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;

import org.xml.sax.SAXException;
import org.xml.sax.InputSource;

import java.io.OutputStream;
import java.io.ByteArrayOutputStream;
import java.io.StringReader;
import java.io.FileInputStream;
import java.io.File;
import java.io.IOException;

import java.util.Collection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Date;

import javax.xml.transform.stream.StreamResult;


public class Invoice {
  public static final String DTD_PUBLIC_ID = "-//Sun Microsystems, Inc. - J2EE Blueprints Group//DTD Invoice 1.0//EN";
  public static final String DTD_SYSTEM_ID = "Invoice.dtd";
  public static final boolean VALIDATING = false;
  public static final String XML_INVOICE = "Invoice";
  public static final String XML_ORDERID = "orderid";
  public static final String XML_USERID = "userid";
  public static final String XML_ORDERDATE = "orderdate";
  public static final String XML_SHIPDATE = "shipdate";
  private String orderId;
  private String  userId;
  private String  orderDate;
  private String  shipDate;
  private ArrayList lineItems = null;

  // Constructor to be used when called with XML file name

  // Constructor to be used when creating PO from data

  public Invoice() {}

  // getter methods

  public String getOrderId() {
    return orderId;
  }

  public String getUserId() {
    return userId;
  }

  public String getOrderDate() {
    return orderDate;
  }

  public String getShipDate() {
    return shipDate;
  }

  public Collection getLineItems() {
    return lineItems;
  }

  // setter methods

  public void setOrderId(String orderId) {
    this.orderId = orderId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public void setOrderDate(String orderDate) {
    this.orderDate = orderDate;
  }

  public void setShipDate(String shipDate) {
    this.shipDate = shipDate;
  }

  public void addLineItem(String cat, String prod, String item, String line,
                          int qty, float price) {
    addLineItem(new LineItems(cat, prod, item, line, qty, price));
  }

  public void addLineItem(LineItems lineItem) {
    if(lineItems == null) {
      lineItems = new ArrayList();
    }
    lineItems.add(lineItem);
  }

  // XML (de)serialization methods

  public void toXML(OutputStream stream) throws XMLDocumentException {
    XMLDocumentUtils.toXML(toDOM(), DTD_PUBLIC_ID, DTD_SYSTEM_ID, new StreamResult(stream));
    return;
  }

  public String toXML() throws XMLDocumentException {
    try {
      ByteArrayOutputStream stream = new ByteArrayOutputStream();
      XMLDocumentUtils.toXML(toDOM(), DTD_PUBLIC_ID, DTD_SYSTEM_ID, XMLDocumentUtils.DEFAULT_ENCODING, new StreamResult(stream));
      return stream.toString(XMLDocumentUtils.DEFAULT_ENCODING);
    } catch (Exception exception) {
      throw new XMLDocumentException(exception);
    }
  }

  public static Invoice fromXML(InputSource source) throws XMLDocumentException {
    return fromDOM(XMLDocumentUtils.fromXML(source, DTD_PUBLIC_ID, VALIDATING).getDocumentElement());
  }

  public static Invoice fromXML(File file) throws XMLDocumentException {
    try {
      return fromXML(new InputSource(new FileInputStream(file)));
    } catch (Exception exception) {
      throw new XMLDocumentException(exception);
    }
  }

  public static Invoice fromXML(String buffer) throws XMLDocumentException {
    try {
      return fromXML(new InputSource(new StringReader(buffer)));
    } catch (Exception exception) {
      throw new XMLDocumentException(exception);
    }
  }

  public Document toDOM() throws XMLDocumentException {
    Document document = XMLDocumentUtils.createDocument();
    Element root = (Element) toDOM(document);
    document.appendChild(root);
    return document;
  }

  public Node toDOM(Document document) {
    Element root = document.createElement(XML_INVOICE);
    XMLDocumentUtils.appendChild(document, root, XML_ORDERID, orderId);
    XMLDocumentUtils.appendChild(document, root, XML_USERID, userId);
    XMLDocumentUtils.appendChild(document, root, XML_ORDERDATE, orderDate);
    XMLDocumentUtils.appendChild(document, root, XML_SHIPDATE, shipDate);
    for (Iterator i = lineItems.iterator(); i.hasNext();) {
      LineItems lineItem = (LineItems) i.next();
      root.appendChild(lineItem.toDOM(document));
    }
    return root;
  }

  public static Invoice fromDOM(Node node) throws XMLDocumentException {
    Element element;
    if (node.getNodeType() == Node.ELEMENT_NODE && (element = ((Element) node)).getTagName().equals(XML_INVOICE)) {
      Element child;
      Invoice invoice = new Invoice();
      child = XMLDocumentUtils.getFirstChild(element, XML_ORDERID, false);
      invoice.setOrderId(XMLDocumentUtils.getContentAsString(child, false));
      child = XMLDocumentUtils.getNextSibling(child, XML_USERID, false);
      invoice.setUserId(XMLDocumentUtils.getContentAsString(child, false));
      child = XMLDocumentUtils.getNextSibling(child, XML_ORDERDATE, false);
      invoice.setOrderDate(XMLDocumentUtils.getContentAsString(child, false));
      child = XMLDocumentUtils.getNextSibling(child, XML_SHIPDATE, false);
      invoice.setShipDate(XMLDocumentUtils.getContentAsString(child, false));
      for (child = XMLDocumentUtils.getNextSibling(child, LineItems.XML_LINEITEM, false);
           child != null;
           child = XMLDocumentUtils.getNextSibling(child, LineItems.XML_LINEITEM, true)) {
        invoice.addLineItem(LineItems.fromDOM(child));
      }
      return invoice;
    }
    throw new XMLDocumentException(XML_INVOICE + " element expected.");
  }

  public static void main(String[] args) {
    if (args.length <= 1) {
      String fileName = args.length > 0 ? args[0] : "Invoice.xml";
      try {
        Invoice invoice = Invoice.fromXML(new File(fileName));
        invoice.toXML(System.out);
        System.exit(0);
      } catch (XMLDocumentException exception) {
        System.err.println(exception.getRootCause());
        System.exit(2);
      }
    }
    System.err.println("Usage: " + Invoice.class.getName() + " [file-name]");
    System.exit(1);
  }
}
