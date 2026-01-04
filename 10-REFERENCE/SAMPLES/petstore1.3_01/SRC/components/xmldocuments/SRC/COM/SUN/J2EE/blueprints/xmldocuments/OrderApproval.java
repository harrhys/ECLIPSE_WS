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


public class OrderApproval {
  public static final String DTD_PUBLIC_ID = "-//Sun Microsystems, Inc. - J2EE Blueprints Group//DTD Order Approval 1.0//EN";
  public static final String DTD_SYSTEM_ID = "OrderApproval.dtd";
  public static final boolean VALIDATING = false;
  public static final String XML_ORDERAPPROVAL = "OrderApproval";
  //public static final String XML_TYPE = "Type";
  //public static final String XML_TOTALCOUNT = "TotalCount";
  //private String type;
  private ArrayList orderList = new ArrayList();


  // Constructor to be used when called with XML file name

  /**
   * @deprecated Use fromXML(File)
   */
  public OrderApproval(File file) {
    try {
      OrderApproval orderApproval = fromXML(file);
      this.orderList = orderApproval.orderList;
    } catch (XMLDocumentException exception) {
      System.err.println(exception.getRootCause());
    }
    return;
  }

  // Constructor to be used when creating from data

  public OrderApproval() {}

  public void addOrder(ChangedOrder order) {
    orderList.add(order);
    return;
  }

  public Collection getOrdersList() {
    return orderList;
  }

  // XML (de)serialization methods

  /**
   * @deprecated Use toXML()
   */

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

  public static OrderApproval fromXML(InputSource source) throws XMLDocumentException {
    /*
      Document document;
      try {
      DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
      DocumentBuilder builder = builderFactory.newDocumentBuilder();
      document = builder.parse(source);
      } catch (Exception exception) {
      throw new XMLDocumentException(exception);
      }
      if (XMLDocumentUtils.checkDocumentType(document, DTD_PUBLIC_ID)) {
      return fromDOM(document.getDocumentElement());
      }
      throw new XMLDocumentException("Document not of type: " + DTD_PUBLIC_ID);
      */
    return fromDOM(XMLDocumentUtils.fromXML(source, DTD_PUBLIC_ID, VALIDATING).getDocumentElement());
  }

  public static OrderApproval fromXML(File file) throws XMLDocumentException {
    try {
      return fromXML(new InputSource(new FileInputStream(file)));
    } catch (Exception exception) {
      throw new XMLDocumentException(exception);
    }
  }

  public static OrderApproval fromXML(String buffer) throws XMLDocumentException {
    try {
      return fromXML(new InputSource(new StringReader(buffer)));
    } catch (Exception exception) {
      throw new XMLDocumentException(exception);
    }
  }

  public Document toDOM() throws XMLDocumentException {
    /*
      try {
      DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
      builderFactory.setValidating(VALIDATING);
      DocumentBuilder builder = builderFactory.newDocumentBuilder();
      Document document = builder.newDocument();
      Element root = (Element) toDOM(document);
      document.appendChild(root);
      return document;
      } catch (Exception exception) {
      exception.printStackTrace(System.err);
      throw new XMLDocumentException(exception);
      }
      */
    Document document = XMLDocumentUtils.createDocument();
    Element root = (Element) toDOM(document);
    document.appendChild(root);
    return document;
  }

  public Node toDOM(Document document) {
    Element root = document.createElement(XML_ORDERAPPROVAL);
    //XMLDocumentUtils.appendChild(document, root, XML_TYPE, type);
    //XMLDocumentUtils.appendChild(document, root, XML_TOTALCOUNT, orderList.size());
    for (Iterator i = orderList.iterator(); i.hasNext();) {
      ChangedOrder changedOrder = (ChangedOrder) i.next();
      root.appendChild(changedOrder.toDOM(document));
    }
    return root;
  }

  public static OrderApproval fromDOM(Node node) throws XMLDocumentException {
    Element element;
    if (node.getNodeType() == Node.ELEMENT_NODE && (element = ((Element) node)).getTagName().equals(XML_ORDERAPPROVAL)) {
      Element child;
      OrderApproval orderApproval = new OrderApproval();
      //orderApproval.type = XMLDocumentUtils.getContentAsString(child = XMLDocumentUtils.getFirstChild(element, XML_TYPE, false), false);
      //XMLDocumentUtils.getContentAsInt(child = XMLDocumentUtils.getNextSibling(child, XML_TOTALCOUNT, false), false);
      orderApproval.orderList = new ArrayList();
      for (child = XMLDocumentUtils.getFirstChild(element, ChangedOrder.XML_ORDER, false);
           child != null;
           child = XMLDocumentUtils.getNextSibling(child, ChangedOrder.XML_ORDER, true)) {
        orderApproval.orderList.add(ChangedOrder.fromDOM(child));
      }
      return orderApproval;
    }
    throw new XMLDocumentException(XML_ORDERAPPROVAL + " element expected.");
  }

  public static void main(String[] args) {
    if (args.length <= 1) {
      String fileName = args.length > 0 ? args[0] : "OrderApproval.xml";
      try {
        OrderApproval orderApproval = OrderApproval.fromXML(new File(fileName));
        orderApproval.toXML(System.out);
        System.exit(0);
      } catch (XMLDocumentException exception) {
        System.err.println(exception.getRootCause());
        System.exit(2);
      }
    }
    System.err.println("Usage: " + OrderApproval.class.getName() + " [file-name]");
    System.exit(1);
  }
}
