package com.oracle.enterprisetest.jsca.cart;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.oracle.enterprisetest.jsca.cart package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _GetTotalQuantityResponse_QNAME = new QName("http://ws.svc.getqty.jsca.enterprisetest.oracle.com", "getTotalQuantityResponse");
    private final static QName _GetTotalQuantity_QNAME = new QName("http://ws.svc.getqty.jsca.enterprisetest.oracle.com", "getTotalQuantity");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.oracle.enterprisetest.jsca.cart
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetTotalQuantityResponse }
     * 
     */
    public GetTotalQuantityResponse createGetTotalQuantityResponse() {
        return new GetTotalQuantityResponse();
    }

    /**
     * Create an instance of {@link ShoppingCart }
     * 
     */
    public ShoppingCart createShoppingCart() {
        return new ShoppingCart();
    }

    /**
     * Create an instance of {@link ShoppingCartItem }
     * 
     */
    public ShoppingCartItem createShoppingCartItem() {
        return new ShoppingCartItem();
    }

    /**
     * Create an instance of {@link GetTotalQuantity }
     * 
     */
    public GetTotalQuantity createGetTotalQuantity() {
        return new GetTotalQuantity();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetTotalQuantityResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.svc.getqty.jsca.enterprisetest.oracle.com", name = "getTotalQuantityResponse")
    public JAXBElement<GetTotalQuantityResponse> createGetTotalQuantityResponse(GetTotalQuantityResponse value) {
        return new JAXBElement<GetTotalQuantityResponse>(_GetTotalQuantityResponse_QNAME, GetTotalQuantityResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetTotalQuantity }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.svc.getqty.jsca.enterprisetest.oracle.com", name = "getTotalQuantity")
    public JAXBElement<GetTotalQuantity> createGetTotalQuantity(GetTotalQuantity value) {
        return new JAXBElement<GetTotalQuantity>(_GetTotalQuantity_QNAME, GetTotalQuantity.class, null, value);
    }

}
