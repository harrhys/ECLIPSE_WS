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
import java.util.Locale;

import java.text.DateFormat;

import javax.xml.transform.stream.StreamResult;


public class PO { // FIXME! Change name to PurchaseOrder
  public static final String DTD_PUBLIC_ID = "-//Sun Microsystems, Inc. - J2EE Blueprints Group//DTD Purchase Order 1.0//EN";
  public static final String DTD_SYSTEM_ID = "PurchaseOrder.dtd";
  public static final boolean VALIDATING = true;
  public static final String XML_PURCHASEORDER = "PurchaseOrder";
  public static final String XML_LOCALE = "locale";
  public static final String XML_ORDERID = "OrderId";
  public static final String XML_USERID = "UserId";
  public static final String XML_EMAILID = "EmailId";
  public static final String XML_ORDERDATE = "OrderDate";
  public static final String XML_SHIPADDRESS = "ShipAddress";
  public static final String XML_BILLADDRESS = "BillAddress";
  public static final String XML_TOTALPRICE = "TotalPrice";
  public static final String XML_CREDITCARD = "CreditCard";
  public static final String XML_CARDNUMBER = "CardNumber";
  public static final String XML_EXPIRYDATE = "ExpiryDate";
  public static final String XML_CARDTYPE = "CardType";
  private Locale locale = Locale.US;
  private String orderId;
  private String userId;
  private String emailId;
  private Date orderDate;
  private Address shipAddress;
  private Address billAddress;
  private float totalPrice;
  private String creditCardNumber, creditCardExpDate, creditCardType; // FIXME! Create an inner class CreditCard
  private ArrayList lineItems = null;

  private static class Address {
    public static final String XML_FIRSTNAME = "FirstName";
    public static final String XML_LASTNAME = "LastName";
    public static final String XML_STREET = "Street";
    public static final String XML_CITY = "City";
    public static final String XML_STATE = "State";
    public static final String XML_COUNTRY = "Country";
    public static final String XML_ZIPCODE = "ZipCode";
    private String firstName;
    private String lastName;
    private String street;
    private String city;
    private String state;
    private String country;
    private String zipCode;

    private Node toDOM(Document document, String name) {
      Element root = document.createElement(name);
      XMLDocumentUtils.appendChild(document, root, XML_FIRSTNAME, firstName);
      XMLDocumentUtils.appendChild(document, root, XML_LASTNAME, lastName);
      XMLDocumentUtils.appendChild(document, root, XML_STREET, street);
      XMLDocumentUtils.appendChild(document, root, XML_CITY, city);
      XMLDocumentUtils.appendChild(document, root, XML_STATE, state);
      XMLDocumentUtils.appendChild(document, root, XML_COUNTRY, country);
      XMLDocumentUtils.appendChild(document, root, XML_ZIPCODE, zipCode);
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
        address.zipCode = XMLDocumentUtils.getContentAsString(child = XMLDocumentUtils.getNextSibling(child, XML_ZIPCODE, false), false);
        return address;
      }
      throw new XMLDocumentException(name + " element expected.");
    }

  }

  // Constructor to be used when called with XML file name

  /**
   * @deprecated Use fromXML(File)
   */
  public PO(File file) {
    try {
      PO purchaseOrder = fromXML(file);
      this.orderId = purchaseOrder.orderId;
      this.userId = purchaseOrder.userId;
      this.emailId = purchaseOrder.emailId;
      this.orderDate = purchaseOrder.orderDate;
      this.shipAddress = purchaseOrder.shipAddress;
      this.billAddress = purchaseOrder.billAddress;
      this.totalPrice = purchaseOrder.totalPrice;
      this.creditCardNumber = purchaseOrder.creditCardNumber;
      this.creditCardExpDate = purchaseOrder.creditCardExpDate;
      this.creditCardType = purchaseOrder.creditCardType;
      this.lineItems = purchaseOrder.lineItems;
    } catch (XMLDocumentException exception) {
      System.err.println(exception.getRootCause());
    }
    return;
  }

  // Constructor to be used when creating PO from data

  public PO() {}

  // getter methods

  public Locale getLocale() {
    return locale;
  }

  public String getOrderId() {
    return orderId;
  }

  public String getUserId() {
    return userId;
  }

  public String getEmailId() {
    return emailId;
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

  public String getShipZipCode() {
    return shipAddress.zipCode;
  }

  public String getBillFirstName() {
    return billAddress.firstName;
  }

  public String getBillLastName() {
    return billAddress.lastName;
  }

  public String getBillStreet() {
    return billAddress.street;
  }

  public String getBillCity() {
    return billAddress.city;
  }

  public String getBillState() {
    return billAddress.state;
  }

  public String getBillCountry() {
    return billAddress.country;
  }

  public String getBillZipCode() {
    return billAddress.zipCode;
  }

  public float getTotalPrice() {
    return totalPrice;
  }

  public String getCreditCardNo() {
    return creditCardNumber;
  }

  public String getExpiryDate() {
    return creditCardExpDate;
  }

  public String getCardType() {
    return creditCardType;
  }

  public Collection getLineItems() {
    return lineItems;
  }

  // setter methods

  public void setLocale(Locale locale) {
    this.locale = locale;
  }

  public void setLocale(String locale) {
    this.locale = XMLDocumentUtils.getLocaleFromString(locale);
  }

  public void setOrderId(String orderId) {
    this.orderId = orderId;
  }

  /**
   * @deprecated
   public void setOrderId(int id) {
   orderId = Integer.toString(id);
   }
   */

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public void setEmailId(String emailId) {
    this.emailId = emailId;
  }

  public void setOrderDate(Date orderDate) {
    this.orderDate = orderDate;
  }

  /**
   * @deprecated
   public void setOrderDate(String date) {
   try {
   setOrderDate(DateFormat.getInstance().parse(date));
   } catch (Exception exception) {
   System.err.println(exception.getMessage());
   }
   }
   */

  public void setShipInfo(String fName, String lName, String street,
                          String city, String state, String country,
                          String zipCode) {
    shipAddress = new Address();
    shipAddress.firstName = fName;
    shipAddress.lastName = lName;
    shipAddress.street = street;
    shipAddress.city = city;
    shipAddress.state = state;
    shipAddress.country = country;
    shipAddress.zipCode = zipCode;
  }

  public void setBillInfo(String fName, String lName, String street,
                          String city, String state, String country,
                          String zipCode) {
    billAddress = new Address();
    billAddress.firstName = fName;
    billAddress.lastName = lName;
    billAddress.street = street;
    billAddress.city = city;
    billAddress.state = state;
    billAddress.country = country;
    billAddress.zipCode = zipCode;
  }

  public void setTotalPrice(float totalPrice) {
    this.totalPrice = totalPrice;
  }

  public void setCreditCardInfo(String creditCardNumber, String creditCardExpDate, String creditCardType) {
    this.creditCardNumber = creditCardNumber;
    this.creditCardExpDate = creditCardExpDate;
    this.creditCardType = creditCardType;
  }

  /**
   * @deprecated Use addLineItems(LineItems)
   public void addLineItem(String cat, String prod, String item, String line,
   int qty, float price) {
   addLineItem(new LineItems(cat, prod, item, line, qty, price));
   }
   */

  public void addLineItem(LineItems lineItem) {
    if(lineItems == null) {
      lineItems = new ArrayList();
    }
    lineItems.add(lineItem);
  }

  // XML (de)serialization methods

  /**
   * @deprecated Use toXML()
   public String getXml() {
   try {
   return toXML();
   } catch (XMLDocumentException exception) {
   System.err.println(exception.getRootCause());
   return null;
   }
   }
   */

  public void toXML(OutputStream stream) throws XMLDocumentException {
    XMLDocumentUtils.toXML(toDOM(), DTD_PUBLIC_ID, DTD_SYSTEM_ID, new StreamResult(stream));
    return;
  }

  public String toXML() throws XMLDocumentException {
    try {
      ByteArrayOutputStream stream = new ByteArrayOutputStream();
      XMLDocumentUtils.toXML(toDOM(), DTD_PUBLIC_ID, DTD_SYSTEM_ID, XMLDocumentUtils.DEFAULT_ENCODING, new StreamResult(stream));
      System.err.println("toXML: " + stream.toString(XMLDocumentUtils.DEFAULT_ENCODING));
      return stream.toString(XMLDocumentUtils.DEFAULT_ENCODING);
    } catch (Exception exception) {
      throw new XMLDocumentException(exception);
    }
  }

  public static PO fromXML(InputSource source) throws XMLDocumentException {
    return fromDOM(XMLDocumentUtils.fromXML(source, DTD_PUBLIC_ID, VALIDATING).getDocumentElement());
  }

  public static PO fromXML(File file) throws XMLDocumentException {
    try {
      return fromXML(new InputSource(new FileInputStream(file)));
    } catch (Exception exception) {
      System.err.println(exception.getMessage());
      throw new XMLDocumentException(exception);
    }
  }

  public static PO fromXML(String buffer) throws XMLDocumentException {
    System.err.println(buffer);
    try {
      return fromXML(new InputSource(new StringReader(buffer)));
    } catch (XMLDocumentException exception) {
      System.err.println(exception.getRootCause().getMessage());
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
    Element root = document.createElement(XML_PURCHASEORDER);
    root.setAttribute(XML_LOCALE, locale.toString());
    XMLDocumentUtils.appendChild(document, root, XML_ORDERID, orderId);
    XMLDocumentUtils.appendChild(document, root, XML_USERID, userId);
    XMLDocumentUtils.appendChild(document, root, XML_EMAILID, emailId);
    XMLDocumentUtils.appendChild(document, root, XML_ORDERDATE, DateFormat.getInstance().format(orderDate));
    root.appendChild(shipAddress.toDOM(document, XML_SHIPADDRESS));
    root.appendChild(billAddress.toDOM(document, XML_BILLADDRESS));
    XMLDocumentUtils.appendChild(document, root, XML_TOTALPRICE, totalPrice);
    Node node = document.createElement(XML_CREDITCARD);
    XMLDocumentUtils.appendChild(document, node, XML_CARDNUMBER, creditCardNumber);
    XMLDocumentUtils.appendChild(document, node, XML_EXPIRYDATE, creditCardExpDate);
    XMLDocumentUtils.appendChild(document, node, XML_CARDTYPE, creditCardType);
    root.appendChild(node);
    for (Iterator i = lineItems.iterator(); i.hasNext();) {
      LineItems lineItem = (LineItems) i.next();
      root.appendChild(lineItem.toDOM(document));
    }
    return root;
  }

  public static PO fromDOM(Node node) throws XMLDocumentException {
    Element element;
    if (node.getNodeType() == Node.ELEMENT_NODE && (element = ((Element) node)).getTagName().equals(XML_PURCHASEORDER)) {
      Element child;
      PO purchaseOrder = new PO();
      String value = element.getAttribute(XML_LOCALE);
      if (value != null) {
        purchaseOrder.setLocale(value);
      }
      child = XMLDocumentUtils.getFirstChild(element, XML_ORDERID, false);
      purchaseOrder.setOrderId(XMLDocumentUtils.getContentAsString(child, false));
      child = XMLDocumentUtils.getNextSibling(child, XML_USERID, false);
      purchaseOrder.setUserId(XMLDocumentUtils.getContentAsString(child, false));
      child = XMLDocumentUtils.getNextSibling(child, XML_EMAILID, false);
      purchaseOrder.setEmailId(XMLDocumentUtils.getContentAsString(child, false));
      try {
        child = XMLDocumentUtils.getNextSibling(child, XML_ORDERDATE, false);
        purchaseOrder.setOrderDate(DateFormat.getInstance().parse(XMLDocumentUtils.getContentAsString(child, false)));
      } catch (Exception exception) {
        purchaseOrder.setOrderDate(new Date()); // FIX ME!
        System.err.println(XML_ORDERDATE + ": " + exception.getMessage() + " reset to current date.");
      }
      child = XMLDocumentUtils.getNextSibling(child, XML_SHIPADDRESS, false);
      purchaseOrder.shipAddress = Address.fromDOM(child, XML_SHIPADDRESS);
      child = XMLDocumentUtils.getNextSibling(child, XML_BILLADDRESS, false);
      purchaseOrder.billAddress = Address.fromDOM(child, XML_BILLADDRESS);
      child = XMLDocumentUtils.getNextSibling(child, XML_TOTALPRICE, false);
      purchaseOrder.setTotalPrice(XMLDocumentUtils.getContentAsFloat(child, false));
      child = XMLDocumentUtils.getNextSibling(child, XML_CREDITCARD, false);
      Element grandChild = XMLDocumentUtils.getChild(child, XML_CARDNUMBER, false);
      purchaseOrder.creditCardNumber = XMLDocumentUtils.getContentAsString(grandChild, false);
      grandChild = XMLDocumentUtils.getChild(child, XML_EXPIRYDATE, false);
      purchaseOrder.creditCardExpDate = XMLDocumentUtils.getContentAsString(grandChild, false);
      grandChild = XMLDocumentUtils.getChild(child, XML_CARDTYPE, false);
      purchaseOrder.creditCardType = XMLDocumentUtils.getContentAsString(grandChild, false);
      for (child = XMLDocumentUtils.getNextSibling(child, LineItems.XML_LINEITEM, false);
           child != null;
           child = XMLDocumentUtils.getNextSibling(child, LineItems.XML_LINEITEM, true)) {
        purchaseOrder.addLineItem(LineItems.fromDOM(child));
      }
      return purchaseOrder;
    }
    throw new XMLDocumentException(XML_PURCHASEORDER + " element expected.");
  }

  public static void main(String[] args) {
    if (args.length <= 1) {
      String fileName = args.length > 0 ? args[0] : "PO.xml";
      try {
        PO purchaseOrder = PO.fromXML(new File(fileName));
        purchaseOrder.toXML(System.out);
        System.exit(0);
      } catch (XMLDocumentException exception) {
        System.err.println(exception.getRootCause());
        System.exit(2);
      }
    }
    System.err.println("Usage: " + PO.class.getName() + " [file-name]");
    System.exit(1);
  }
}
