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
import java.math.BigDecimal;
import java.util.*;

public class CheckoutFormBean {
  private String firstName;
  private String lastName;
  private String email;
  private String areaCode;
  private String phoneNumber;
  private String street;
  private String city;
  private String state;
  private String zip;
  private int CCOption;
  private String CCNumber;
  private HashMap errors;
  private ShoppingCart cart;
  private RetailPriceList rpl;
  private OrderConfirmations ocs;
  private ResourceBundle messages;
  

  public boolean validate() {
    boolean allOk=true;
    if (firstName.equals("")) {
      errors.put("firstName",messages.getString("FirstNameError"));
      firstName="";
      allOk=false;
    } else {
      errors.put("firstName","");		
    }
    
    if (lastName.equals("")) {
      errors.put("lastName",messages.getString("LastNameError"));
      lastName="";
      allOk=false;
    } else {
      errors.put("lastName","");		
    }

    if (email.equals("") || (email.indexOf('@') == -1)) {
      errors.put("email",messages.getString("EMailError"));
      email="";
      allOk=false;
    } else {
      errors.put("email","");		
    }
    if (areaCode.equals("")) {
      errors.put("areaCode",messages.getString("AreaCodeError"));
      areaCode="";
      allOk=false;
    } else {
      errors.put("areaCode","");		
    }

    if (phoneNumber.equals("")) {
      errors.put("phoneNumber",messages.getString("PhoneNumberError"));
      phoneNumber="";
      allOk=false;
    } else {
      errors.put("phoneNumber","");		
    }
    if (street.equals("")) {
      errors.put("street",messages.getString("StreetError"));
      street="";
      allOk=false;
    } else {
      errors.put("street","");		
    }

    if (city.equals("")) {
      errors.put("city",messages.getString("CityError"));
      city="";
      allOk=false;
    } else {
      errors.put("city","");		
    }

    if (state.equals("")) {
      errors.put("state",messages.getString("StateError"));
      state="";
      allOk=false;
    } else {
      errors.put("state","");		
    }

    if (zip.equals("") || zip.length() !=5 ) {
      errors.put("zip",messages.getString("ZipError"));
      zip="";
      allOk=false;
    } else {
      try {
        int x = Integer.parseInt(zip);
      	errors.put("zip","");		
      } catch (NumberFormatException e) {
        errors.put("zip",messages.getString("ZipError"));
        zip="";
        allOk=false;
      }	
 		}

    if (CCNumber.equals("")) {
      errors.put("CCNumber",messages.getString("CCNumberError"));
      CCNumber="";
      allOk=false;
    } else {
      errors.put("CCNumber","");		
    }

    ocs.clear();
 		ConfirmationBean confirmation = null;
    if (allOk) {

      String orderId = CCNumber;
       
      AddressBean address = new AddressBean(street, city, state, zip);
      CustomerBean customer = new CustomerBean(firstName, lastName, "(" + areaCode+ ") " + phoneNumber, email);

      for(Iterator d = rpl.getDistributors().iterator(); d.hasNext(); ) {
        String distributor = (String)d.next();
        System.out.println(distributor);				
        ArrayList lis = new ArrayList();
        BigDecimal price = new BigDecimal("0.00");
        BigDecimal total = new BigDecimal("0.00");
        for(Iterator c = cart.getItems().iterator(); c.hasNext(); ) {
          ShoppingCartItem sci = (ShoppingCartItem) c.next();
          if ((sci.getItem().getDistributor()).equals(distributor) && sci.getPounds().floatValue() > 0) {
            price = sci.getItem().getWholesalePricePerPound().multiply(sci.getPounds());	
            total = total.add(price);
            LineItemBean li = new LineItemBean(sci.getItem().getCoffeeName(), sci.getPounds(), sci.getItem().getWholesalePricePerPound());
            lis.add(li);
          }
        }

        if (!lis.isEmpty()) {
          OrderBean order = new OrderBean(orderId, customer, lis, total, address);

        String jaxmSpropsName = "com.sun.cb.JAXMService";

        ResourceBundle jaxmSpropBundle =
           ResourceBundle.getBundle(jaxmSpropsName);

        String JAXMOrderURL = jaxmSpropBundle.getString("order.url");

          if (distributor.equals(JAXMOrderURL)) {
            OrderRequest or = new OrderRequest(JAXMOrderURL);
            confirmation = or.placeOrder(order);
          }
          else {
          	OrderCaller ocaller = new OrderCaller(distributor);
          	confirmation = ocaller.placeOrder(order);				
          }
          OrderConfirmation oc = new OrderConfirmation(order, confirmation);
          ocs.add(oc);
        }
      }
 		}

    return allOk;
  }

  public HashMap getErrors() {
    return errors;
  }

  public String getErrorMsg(String s) {
    String errorMsg =(String)errors.get(s.trim());
    return (errorMsg == null) ? "":errorMsg;
  }

  public CheckoutFormBean(ShoppingCart cart, RetailPriceList rpl, ResourceBundle messages) {
   firstName="";
   lastName="";
   email="";
   areaCode="";
   phoneNumber="";
   street="";
   city="";
   state="";
   zip="";
   CCOption=0;
   CCNumber="";
 	 errors = new HashMap();
   this.cart = cart;
   this.rpl = rpl;
   this.messages = messages;
   ocs = new OrderConfirmations();
  }
  
  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public String getEmail() {
    return email;
  }

  public String getZip() {
    return zip;
  }

  public String getAreaCode() {
    return areaCode;
  }  

  public String getPhoneNumber() {
    return phoneNumber;
  }   
  
  public String getStreet() {
    return street;
  }   
  
  public String getCity() {
    return city;
  }   
  
  public String getState() {
    return state;
  }   
  
  public int getCCOption() {
    return CCOption;
  } 
  
  public String getCCNumber() {
    return CCNumber;
  }   
  
  public OrderConfirmations getOrderConfirmations() {
    return ocs;
  }
  
  public void setMessages(ResourceBundle messages) {
    this.messages=messages;
  }

  public void setFirstName(String firstname) {
    this.firstName=firstname;
  }

  public void setLastName(String lastname) {
    this.lastName=lastname;
  }

  public void setEmail(String email) {
    this.email=email;
  }

  public void setZip(String zip) {
    this.zip=zip;
  }

  public void setAreaCode(String areaCode) {
    this.areaCode=areaCode ;
  }
  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber=phoneNumber ;
  }  
  public void setStreet(String street) {
    this.street=street ;
  }  
  public void setCity(String city) {
    this.city=city ;
  }  
  public void setState(String state) {
    this.state=state ;
  }  
  public void setCCOption(int CCOption) {
    this.CCOption=CCOption ;
  }
  public void setCCNumber(String CCNumber) {
    this.CCNumber=CCNumber ;
  }	
  public void setErrors(String key, String msg) {	
    errors.put(key,msg);
  }

}



