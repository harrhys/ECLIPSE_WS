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

import java.text.DateFormat;

import javax.xml.transform.stream.StreamResult;


public class SupplierPO {
  public static final String DTD_PUBLIC_ID = "-//Sun Microsystems, Inc. - J2EE Blueprints Group//DTD Supplier Order 1.0//EN";
  public static final String DTD_SYSTEM_ID = "SupplierOrder.dtd";
  public static final boolean VALIDATING = false;
  public static final String XML_SUPPLIERORDER = "SupplierOrder";
  public static final String XML_ORDERID = "orderid";
  public static final String XML_ORDERDATE = "orderdate";
  public static final String XML_SHIPADDRESS = "shipaddress";
  private String orderId;
  private Date orderDate;
  private Address shipAddress;
  private ArrayList lineItems = null;

  private static class Address {
    public static final String XML_FIRSTNAME = "firstname";
    public static final String XML_LASTNAME = "lastname";
    public static final String XML_STREET = "street";
    public static final String XML_CITY = "city";
    public static final String XML_STATE = "state";
    public static final String XML_COUNTRY = "country";
    public static final String XML_ZIP = "zip";
    private String firstName;
    private String lastName;
    private String street;
    private String city;
    private String state;
    private String country;
    private String zip;

    private Node toDOM(Document document, String name) {
      Element root = document.createElement(name);
      XMLDocumentUtils.appendChild(document, root, XML_FIRSTNAME, firstName);
      XMLDocumentUtils.appendChild(document, root, XML_LASTNAME, lastName);
      XMLDocumentUtils.appendChild(document, root, XML_STREET, street);
      XMLDocumentUtils.appendChild(document, root, XML_CITY, city);
      XMLDocumentUtils.appendChild(document, root, XML_STATE, state);
      XMLDocumentUtils.appendChild(document, root, XML_COUNTRY, country);
      XMLDocumentUtils.appendChild(document, root, XML_ZIP, zip);
      return root;
    }

    private static Address fromDOM(Node node, String name) throws XMLDocumentException {
      Element element;
      if (node.getNodeType() == Node.ELEMENT_NODE && (element = ((Element) node)).getTagName().equals(name)) {
        Element child;
        Address address = new Address();
        address.firstName = XMLDocumentUtils.getContentAsString(child = XMLDocumentUtils.getFirstChild(element, XML_FIRSTNAME, false), false);
        address.lastName = XMLDocumentUtils.getContentAsString(child = XMLDocumentUtils.getNextSibling(child, XML_LASTNAME, false), false);
        address.street = XMLDocumentUtils.getContentAsString(child = XMLDocumentUtils.getNextSibling(child, XML_STREET, false), false);
        address.city = XMLDocumentUtils.getContentAsString(child = XMLDocumentUtils.getNextSibling(child, XML_CITY, false), false);
        address.state = XMLDocumentUtils.getContentAsString(child = XMLDocumentUtils.getNextSibling(child, XML_STATE, false), false);
        address.country = XMLDocumentUtils.getContentAsString(child = XMLDocumentUtils.getNextSibling(child, XML_COUNTRY, false), false);
        address.zip = XMLDocumentUtils.getContentAsString(child = XMLDocumentUtils.getNextSibling(child, XML_ZIP, false), false);
        return address;
      }
      throw new XMLDocumentException(name + " element expected.");
    }

  }

  // Constructor to be used when creating SupplierPO from data

  public SupplierPO() {}

  // getter methods

  public String getOrderId() {
    return(orderId);
  }

  public Date getOrderDate() {
    return orderDate;
  }

  public String getShipFirstName() {
    return shipAddress.firstName;
  }

  public String getShipLastName() {
    return shipAddress.lastName;
  }

  public String getShipStreet() {
    return shipAddress.street;
  }

  public String getShipCity() {
    return shipAddress.city;
  }

  public String getShipState() {
    return shipAddress.state;
  }

  public String getShipCountry() {
    return shipAddress.country;
  }

  public String getShipZip() {
    return shipAddress.zip;
  }

  public Collection getLineItems() {
    return lineItems;
  }

  // setter methods

  public void setOrderId(String id) {
    orderId = id;
  }

  public void setOrderDate(Date date) {
    orderDate = date;
  }

  /**
   * @deprecated
   */
  public void setOrderDate(String date) {
    try {
      setOrderDate(DateFormat.getInstance().parse(date));
    } catch (Exception exception) {
      System.err.println(exception.getMessage());
    }
  }

  public void setShipInfo(String fName, String lName, String street,
                          String city, String state, String country,
                          String zip) {
    shipAddress = new Address();
    shipAddress.firstName = fName;
    shipAddress.lastName = lName;
    shipAddress.street = street;
    shipAddress.city = city;
    shipAddress.state = state;
    shipAddress.country = country;
    shipAddress.zip = zip;
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

  public static SupplierPO fromXML(InputSource source) throws XMLDocumentException {
    return fromDOM(XMLDocumentUtils.fromXML(source, DTD_PUBLIC_ID, VALIDATING).getDocumentElement());
  }

  public static SupplierPO fromXML(File file) throws XMLDocumentException {
    try {
      return fromXML(new InputSource(new FileInputStream(file)));
    } catch (Exception exception) {
      throw new XMLDocumentException(exception);
    }
  }

  public static SupplierPO fromXML(String buffer) throws XMLDocumentException {
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
    Element root = document.createElement(XML_SUPPLIERORDER);
    XMLDocumentUtils.appendChild(document, root, XML_ORDERID, orderId);
    XMLDocumentUtils.appendChild(document, root, XML_ORDERDATE, DateFormat.getInstance().format(orderDate));
    root.appendChild(shipAddress.toDOM(document, XML_SHIPADDRESS));
    for (Iterator i = lineItems.iterator(); i.hasNext();) {
      LineItems lineItem = (LineItems) i.next();
      root.appendChild(lineItem.toDOM(document));
    }
    return root;
  }

  public static SupplierPO fromDOM(Node node) throws XMLDocumentException {
    Element element;
    if (node.getNodeType() == Node.ELEMENT_NODE && (element = ((Element) node)).getTagName().equals(XML_SUPPLIERORDER)) {
      Element child;
      SupplierPO purchaseOrder = new SupplierPO();
      purchaseOrder.setOrderId(XMLDocumentUtils.getContentAsString(child = XMLDocumentUtils.getFirstChild(element, XML_ORDERID, false), false));
      try {
        purchaseOrder.setOrderDate(DateFormat.getInstance().parse(XMLDocumentUtils.getContentAsString(child = XMLDocumentUtils.getNextSibling(child, XML_ORDERDATE, false), false)));
      } catch (Exception exception) {
        purchaseOrder.setOrderDate(new Date()); // FIX ME!
        System.err.println(XML_ORDERDATE + ": " + exception.getMessage() + " reset to current date.");
        //throw new XMLDocumentException(exception);
      }
      purchaseOrder.shipAddress = Address.fromDOM(child = XMLDocumentUtils.getNextSibling(child, XML_SHIPADDRESS, false), XML_SHIPADDRESS);
      for (child = XMLDocumentUtils.getNextSibling(child, LineItems.XML_LINEITEM, false);
           child != null;
           child = XMLDocumentUtils.getNextSibling(child, LineItems.XML_LINEITEM, true)) {
        purchaseOrder.addLineItem(LineItems.fromDOM(child));
      }
      return purchaseOrder;
    }
    throw new XMLDocumentException(XML_SUPPLIERORDER + " element expected.");
  }

  public static void main(String[] args) {
    if (args.length <= 1) {
      String fileName = args.length > 0 ? args[0] : "SupplierPO.xml";
      try {
        SupplierPO purchaseOrder = SupplierPO.fromXML(new File(fileName));
        purchaseOrder.toXML(System.out);
        System.exit(0);
      } catch (XMLDocumentException exception) {
        System.err.println(exception.getRootCause());
        System.exit(2);
      }
    }
    System.err.println("Usage: " + SupplierPO.class.getName() + " [file-name]");
    System.exit(1);
  }
}
