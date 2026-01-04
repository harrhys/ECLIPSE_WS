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


public class Mail {
  public static final String DTD_PUBLIC_ID = "-//Sun Microsystems, Inc. - J2EE Blueprints Group//DTD OPC Mail 1.0//EN";
  public static final String DTD_SYSTEM_ID = "Mail.dtd";
  public static final boolean VALIDATING = false;
  public static final String XML_MAIL = "Mail";
  public static final String XML_SUBJECT = "Subject";
  public static final String XML_EMAILADDRESS = "Address";
  public static final String XML_CONTENT = "Content";
  private String eMailAddress = null;
  private String subject  = null;
  private String mailContent = null;


  public Mail() {}

  public Mail(String eMailAddress, String subject, String mailContent) {
    this.eMailAddress = eMailAddress;
    this.subject = subject;
    this.mailContent = mailContent;
  }

  //getters
  public String getEmailAddress() {
    return eMailAddress;
  }

  public String getSubject() {
    return subject;
  }
  public String getMailContent() {
    return mailContent;
  }

  // XML (de)serialization methods

  public String toXML() throws XMLDocumentException {
    try {
      ByteArrayOutputStream stream = new ByteArrayOutputStream();
      XMLDocumentUtils.toXML(toDOM(), DTD_PUBLIC_ID, DTD_SYSTEM_ID, XMLDocumentUtils.DEFAULT_ENCODING, new StreamResult(stream));
      return stream.toString(XMLDocumentUtils.DEFAULT_ENCODING);
    } catch (Exception exception) {
      throw new XMLDocumentException(exception);
    }
  }

  private static Mail fromXML(InputSource source) throws XMLDocumentException {
    return fromDOM(XMLDocumentUtils.fromXML(source, DTD_PUBLIC_ID, VALIDATING).getDocumentElement());
  }

  public static Mail fromXML(File file) throws XMLDocumentException {
    try {
      return fromXML(new InputSource(new FileInputStream(file)));
    } catch (Exception exception) {
      throw new XMLDocumentException(exception);
    }
  }

  public static Mail fromXML(String buffer) throws XMLDocumentException {
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
    Element root = document.createElement(XML_MAIL);
    XMLDocumentUtils.appendChild(document, root, XML_EMAILADDRESS, eMailAddress);
    XMLDocumentUtils.appendChild(document, root, XML_SUBJECT, subject);
    XMLDocumentUtils.appendChild(document, root, XML_CONTENT, mailContent);
    return root;
  }

  private static Mail fromDOM(Node node) throws XMLDocumentException {
    Element element;
    if (node.getNodeType() == Node.ELEMENT_NODE && (element = ((Element) node)).getTagName().equals(XML_MAIL)) {
      Element child;
      Mail mail = new Mail();
      mail.eMailAddress = XMLDocumentUtils.getContentAsString(child = XMLDocumentUtils.getFirstChild(element, XML_EMAILADDRESS, false), false);
      mail.subject = XMLDocumentUtils.getContentAsString(child = XMLDocumentUtils.getNextSibling(child, XML_SUBJECT, false), false);
      mail.mailContent = XMLDocumentUtils.getContentAsString(child = XMLDocumentUtils.getNextSibling(child, XML_CONTENT, false), false);
      return mail;
    }
    throw new XMLDocumentException(XML_MAIL + " element expected.");
  }

  public static void main(String[] args) {
    if (args.length <= 1) {
      String fileName = args.length > 0 ? args[0] : "Mail.xml";
      try {
        Mail mail = Mail.fromXML(new File(fileName));
        System.out.println(Mail.fromXML(mail.toXML()).getMailContent());
        System.exit(0);
      } catch (XMLDocumentException exception) {
        System.err.println(exception.getRootCause());
        System.exit(2);
      }
    }
    System.err.println("Usage: " + Mail.class.getName() + " [file-name]");
    System.exit(1);
  }
}
