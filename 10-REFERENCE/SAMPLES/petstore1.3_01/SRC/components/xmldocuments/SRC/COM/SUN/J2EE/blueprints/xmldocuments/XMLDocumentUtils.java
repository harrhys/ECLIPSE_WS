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

import java.io.InputStream;
import java.io.File;

import java.net.URL;

import java.util.Properties;
import java.util.Locale;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.w3c.dom.Text;
import org.w3c.dom.DOMException;
import org.w3c.dom.DocumentType;

import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;

import javax.xml.transform.TransformerFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.dom.DOMSource;


public final class XMLDocumentUtils {
  public static final String DEFAULT_ENCODING = "UTF-8";
  public static final String DTD_PUBLIC_ID_REGISTRY = "/dtds/DTDRegistry.properties";
  public static final String DTD_DIRECTORY_PATH = "/dtds/";

  private XMLDocumentUtils() {}

  public static Element getFirstChild(Element element, String name, boolean optional) throws XMLDocumentException {
    for (Node child = element.getFirstChild(); child != null; child = child.getNextSibling()) {
      if (child.getNodeType() == Node.ELEMENT_NODE) {
        if (((Element) child).getTagName().equals(name)) {
          return (Element) child;
        }
        break;
      }
    }
    if (!optional) {
      throw new XMLDocumentException(name + " element expected as first child of " + element.getTagName() + ".");
    }
    return null;
  }

  public static Element getChild(Element element, String name, boolean optional) throws XMLDocumentException {
    for (Node child = element.getFirstChild(); child != null; child = child.getNextSibling()) {
      if (child.getNodeType() == Node.ELEMENT_NODE) {
        if (((Element) child).getTagName().equals(name)) {
          return (Element) child;
        }
      }
    }
    if (!optional) {
      throw new XMLDocumentException(name + " element expected as child of " + element.getTagName() + ".");
    }
    return null;
  }

  public static Element getSibling(Element element, boolean optional) throws XMLDocumentException {
    return getSibling(element, element.getTagName(), optional);
  }

  public static Element getSibling(Element element, String name, boolean optional) throws XMLDocumentException {
    for (Node sibling = element.getNextSibling(); sibling != null; sibling = sibling.getNextSibling()) {
      if (sibling.getNodeType() == Node.ELEMENT_NODE) {
        if (((Element) sibling).getTagName().equals(name)) {
          return (Element) sibling;
        }
      }
    }
    if (!optional) {
      throw new XMLDocumentException(name + " element expected after " + element.getTagName() + ".");
    }
    return null;
  }

  public Element getNextSibling(Element element, boolean optional) throws XMLDocumentException {
    return getNextSibling(element, element.getTagName(), optional);
  }

  public static Element getNextSibling(Element element, String name, boolean optional) throws XMLDocumentException {
    for (Node sibling = element.getNextSibling(); sibling != null; sibling = sibling.getNextSibling()) {
      if (sibling.getNodeType() == Node.ELEMENT_NODE) {
        if (((Element) sibling).getTagName().equals(name)) {
          return (Element) sibling;
        }
        break;
      }
    }
    if (!optional) {
      throw new XMLDocumentException(name + " element expected after " + element.getTagName() + ".");
    }
    return null;
  }

  public static String getContent(Element element, boolean optional) throws XMLDocumentException {
    StringBuffer buffer = new StringBuffer();
    for (Node child = element.getFirstChild(); child != null; child = child.getNextSibling()) {
      if (child.getNodeType() == Node.TEXT_NODE || child.getNodeType() == Node.CDATA_SECTION_NODE) {
        try {
          buffer.append(((Text) child).getData());
        } catch (DOMException e) {}
      }
    }
    if (!optional && buffer.length() == 0) {
      throw new XMLDocumentException(element.getTagName() + " element: content expected.");
    }
    return buffer.toString();
  }

  public static String getContentAsString(Element element, boolean optional) throws XMLDocumentException {
    return getContent(element, optional);
  }

  public static int getContentAsInt(Element element, boolean optional) throws XMLDocumentException {
    try {
      return Integer.parseInt(getContent(element, optional));
    } catch (NumberFormatException exception) {
      throw new XMLDocumentException(element.getTagName() + " element: content format error.", exception);
    }
  }

  public static float getContentAsFloat(Element element, boolean optional) throws XMLDocumentException {
    try {
      return Float.parseFloat(getContent(element, optional));
    } catch (NumberFormatException exception) {
      throw new XMLDocumentException(element.getTagName() + " element: content format error.", exception);
    }
  }

  public static void appendChild(Document document, Node root, String name, String value) {
    Node node = document.createElement(name);
    node.appendChild(document.createTextNode(value));
    root.appendChild(node);
    return;
  }

  public static void appendChild(Document document, Node root, String name, long value) {
    appendChild(document, root, name, Long.toString(value));
    return;
  }

  public static void appendChild(Document document, Node root, String name, float value) {
    appendChild(document, root, name, Float.toString(value));
    return;
  }

  public static void appendChild(Node root, String name, String value) {
    appendChild(root.getOwnerDocument(), root, name, value);
    return;
  }

  public static void toXML(Document document, String dtdPublicId, String dtdSystemId, StreamResult result)
    throws XMLDocumentException {
      toXML(document, dtdPublicId, dtdSystemId, DEFAULT_ENCODING, result);
      return;
  }

  public static void toXML(Document document, String dtdPublicId, String dtdSystemId, String encoding, StreamResult result)
    throws XMLDocumentException {
      try {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.METHOD, "xml");
        if (dtdSystemId != null) {
          transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, dtdSystemId);
        }
        transformer.setOutputProperty(OutputKeys.DOCTYPE_PUBLIC, dtdPublicId);
        transformer.setOutputProperty(OutputKeys.ENCODING, encoding);
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.transform(new DOMSource(document), result);
      } catch (Exception exception) {
        exception.printStackTrace(System.err);
        throw new XMLDocumentException(exception);
      }
      return;
  }

  public static Document fromXML(InputSource source, String dtdPublicId, boolean validating) throws XMLDocumentException {
    Document document;
    try {
      if (source.getSystemId() == null) { // Set the base URI to resolve relative URI
        source.setSystemId(DTD_DIRECTORY_PATH);
      }
      DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
      builderFactory.setValidating(validating);
      DocumentBuilder builder = builderFactory.newDocumentBuilder();
      builder.setEntityResolver(new EntityResolver() {
        public InputSource resolveEntity(String publicId, String systemId) {
          System.err.println("Resolving: " + publicId + " " + systemId);
          // Try first to access the resource using its system Id
          if (systemId != null) {
            try {
              new URL(systemId).openStream().close(); // Checks if the system Id URL can be open
              return null; // Let the default entity resolver take care of it
            } catch (Exception exception) {
              System.err.print("systemId: " + systemId + ": not a readable URL, is it a resource");
              try {
                InputStream stream = getClass().getResourceAsStream(systemId);
                InputSource src = new InputSource(stream);
                src.setSystemId(systemId);
                System.err.println("Yes");
                return src;
              } catch (Exception exception1) {
                System.err.println("No");
                System.err.println("systemId: " + systemId + ": not a resource, is the public Id locally mapped: " + publicId);
                System.err.println(exception1.getMessage());
                exception1.printStackTrace(System.err);
              }
            }
            // Then, try to map its public Id using the local registry
            if (publicId != null) {
              try {
                Properties mapping = new Properties();
                InputStream stream = getClass().getResourceAsStream(DTD_PUBLIC_ID_REGISTRY);
                if (stream != null) {
                  mapping.load(stream);
                  String location = mapping.getProperty(publicId);
                  if (location != null) {
                    URL url = null;
                    try { // Checks if the location is a readable URL
                      url = new URL(location);
                      url.openStream().close();
                      return new InputSource(url.toString());
                    } catch (Exception exception) { // If it's not a readable URL, let's assume it's resource path
                      System.err.print(location + ": not a readable URL, is it a resource? ");
                      try {
                        stream = getClass().getResourceAsStream(location);
                        InputSource src = new InputSource(stream);
                        src.setSystemId(location);
                        //src.setPublicId(publicId);
                        System.err.println("Yes");
                        return src;
                      } catch (Exception exception1) {
                        System.err.println("No");
                        System.err.println(exception1.getMessage());
                        exception1.printStackTrace(System.err);
                      }
                    }
                  }
                } else {
                  System.err.println("Can't access resource: " + DTD_PUBLIC_ID_REGISTRY);
                }
              } catch (Exception exception) {
                System.err.println("Cannot resolve " + publicId + " using: " + DTD_PUBLIC_ID_REGISTRY + " " + exception);
              }
            }
          }
          return null; // Let the default entity resolver take care of it
        }
      });
      document = builder.parse(source);
    } catch (Exception exception) {
      throw new XMLDocumentException(exception);
    }
    if (XMLDocumentUtils.checkDocumentType(document, dtdPublicId)) {
      return document;
    }
    throw new XMLDocumentException("Document not of type: " + dtdPublicId);
  }

  public static Document createDocument() throws XMLDocumentException {
    try {
      DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
      DocumentBuilder builder = builderFactory.newDocumentBuilder();
      Document document = builder.newDocument();
      return document;
    } catch (Exception exception) {
      exception.printStackTrace(System.err);
      throw new XMLDocumentException(exception);
    }
  }

  public static boolean checkDocumentType(Document document, String dtdPublicId) {
    DocumentType documentType = document.getDoctype();
    if (documentType != null) {
      String publicId = documentType.getPublicId();
      return publicId != null && publicId.equals(dtdPublicId);
    }
    return true; // Workaround until DTDs are published
    //return false;
  }

  /**
   * Convert a string based locale into a Locale Object
   * <br>
   * <br>Strings are formatted:
   * <br>
   * <br>language_contry_variant
   *
   **/
  public static Locale getLocaleFromString(String localeString) {
    if (localeString == null) return null;
    if (localeString.toLowerCase().equals("default")) return Locale.getDefault();
    int languageIndex = localeString.indexOf('_');
    if (languageIndex  == -1) return null;
    int countryIndex = localeString.indexOf('_', languageIndex +1);
    String country = null;
    if (countryIndex  == -1) {
      if (localeString.length() > languageIndex) {
        country = localeString.substring(languageIndex +1, localeString.length());
      } else {
        return null;
      }
    }
    int variantIndex = -1;
    if (countryIndex != -1) countryIndex = localeString.indexOf('_', countryIndex +1);
    String language = localeString.substring(0, languageIndex);
    String variant = null;
    if (variantIndex  != -1) {
      variant = localeString.substring(variantIndex +1, localeString.length());
    }
    if (variant != null) {
      return new Locale(language, country, variant);
    } else {
      return new Locale(language, country);
    }
  }
}
