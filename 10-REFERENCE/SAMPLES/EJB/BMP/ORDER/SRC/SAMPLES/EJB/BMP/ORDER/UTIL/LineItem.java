package samples.ejb.bmp.order.util;
/**
 *
 * Copyright 2001 Sun Microsystems, Inc. All Rights Reserved.
 * 
 * This software is the proprietary information of Sun Microsystems, Inc.  
 * Use is subject to license terms.
 * 
 */


 /**
  * This java helper class represents a LineItem object that need not be represented by a entity bean.
  * If a database table doesn't represent a business entity, or if it
  * stores information that is contained in another entity, then the 
  * table should be represented with a helper class.
 */

public class LineItem implements java.io.Serializable {

   String productId;
   int quantity;
   double unitPrice;
   int itemNo;
   String orderId;
   
  /**
   *  Returns a LineItem object given productId and quantity.
   *  @param productId
   *  @param quantity
  */

   public LineItem(String productId, int quantity,
     double unitPrice, int itemNo, String orderId) {

      this.productId = productId;
      this.quantity = quantity;
      this.unitPrice = unitPrice;
      this.itemNo = itemNo;
      this.orderId = orderId;
   }

  /**
   *Returns the productId of this LineItem
   */

   public String getProductId() {
      return productId;
   }

  /**
   *Returns the quantity of this LineItem
   */

   public int getQuantity() {
      return quantity;
   }

  /**
   *Returns the productId of this LineItem
   */

   public double getUnitPrice() {
      return unitPrice;
   }

  /**
   *Returns the productId of this LineItem
   */
   public int getItemNo() {
      return itemNo;
   }

  /**
   *Returns the productId of this LineItem
   */
   public String getOrderId() {
      return orderId;
   }

}
