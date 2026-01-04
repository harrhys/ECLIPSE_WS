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
import org.w3c.dom.Element;


public class LineItems {
  public static final String XML_LINEITEM = "LineItem";
  public static final String XML_CATEGORYID = "CategoryId";
  public static final String XML_PRODUCTID = "ProductId";
  public static final String XML_ITEMID = "ItemId";
  public static final String XML_LINENUM = "LineNum";
  public static final String XML_QUANTITY = "Quantity";
  public static final String XML_UNITPRICE = "UnitPrice";
  private String categoryId;
  private String productId;
  private String itemId;
  private String lineNo;
  private int    quantity;
  private float  unitPrice;

  public LineItems(String categoryId, String productId, String itemId, String lineNo,
                   int quantity, float unitPrice) {
    this.categoryId = categoryId;
    this.productId = productId;
    this.itemId = itemId;
    this.lineNo = lineNo;
    this.quantity = quantity;
    this.unitPrice = unitPrice;
  }

  private LineItems() {} // Used by the fromDOM() factory method

  /**
   * @deprecated
  public String getCategory() {
    return this.categoryId;
  }
   */

  public String getCategoryId() {
    return this.categoryId;
  }

  public String getProductId() {
    return this.productId;
  }

  public String getItemId() {
    return this.itemId;
  }

  public String getLineNo() {
    return this.lineNo;
  }

  public int getQuantity() {
    return this.quantity;
  }

  public float getUnitPrice() {
    return this.unitPrice;
  }

  public Node toDOM(Document document) {
    Element root = document.createElement(XML_LINEITEM);
    XMLDocumentUtils.appendChild(document, root, XML_CATEGORYID, categoryId);
    XMLDocumentUtils.appendChild(document, root, XML_PRODUCTID, productId);
    XMLDocumentUtils.appendChild(document, root, XML_ITEMID, itemId);
    XMLDocumentUtils.appendChild(document, root, XML_LINENUM, lineNo);
    XMLDocumentUtils.appendChild(document, root, XML_QUANTITY, quantity);
    XMLDocumentUtils.appendChild(document, root, XML_UNITPRICE, unitPrice);
    return root;
  }

  public static LineItems fromDOM(Node node) throws XMLDocumentException {
    Element element;
    if (node.getNodeType() == Node.ELEMENT_NODE && (element = ((Element) node)).getTagName().equals(XML_LINEITEM)) {
      Element child;
      LineItems lineItems = new LineItems();
      lineItems.categoryId = XMLDocumentUtils.getContentAsString(child = XMLDocumentUtils.getFirstChild(element, XML_CATEGORYID, false), false);
      lineItems.productId = XMLDocumentUtils.getContentAsString(child = XMLDocumentUtils.getNextSibling(child, XML_PRODUCTID, false), false);
      lineItems.itemId = XMLDocumentUtils.getContentAsString(child = XMLDocumentUtils.getNextSibling(child, XML_ITEMID, false), false);
      lineItems.lineNo = XMLDocumentUtils.getContentAsString(child = XMLDocumentUtils.getNextSibling(child, XML_LINENUM, false), false);
      lineItems.quantity = XMLDocumentUtils.getContentAsInt(child = XMLDocumentUtils.getNextSibling(child, XML_QUANTITY, false), false);
      lineItems.unitPrice = XMLDocumentUtils.getContentAsFloat(child = XMLDocumentUtils.getNextSibling(child, XML_UNITPRICE, false), false);
      return lineItems;
    }
    throw new XMLDocumentException(XML_LINEITEM + " element expected.");
  }
}
