
package test.ws.client;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the test.ws.client package. 
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

    private final static QName _GetTotalPriceResponse_QNAME = new QName("http://ws.svc.getprc.jsca.enterprisetest.oracle.com", "getTotalPriceResponse");
    private final static QName _GetTotalPrice_QNAME = new QName("http://ws.svc.getprc.jsca.enterprisetest.oracle.com", "getTotalPrice");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: test.ws.client
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetTotalPrice }
     * 
     */
    public GetTotalPrice createGetTotalPrice() {
        return new GetTotalPrice();
    }

    /**
     * Create an instance of {@link ShoppingCartItem }
     * 
     */
    public ShoppingCartItem createShoppingCartItem() {
        return new ShoppingCartItem();
    }

    /**
     * Create an instance of {@link GetTotalPriceResponse }
     * 
     */
    public GetTotalPriceResponse createGetTotalPriceResponse() {
        return new GetTotalPriceResponse();
    }

    /**
     * Create an instance of {@link ShoppingCart }
     * 
     */
    public ShoppingCart createShoppingCart() {
        return new ShoppingCart();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetTotalPriceResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.svc.getprc.jsca.enterprisetest.oracle.com", name = "getTotalPriceResponse")
    public JAXBElement<GetTotalPriceResponse> createGetTotalPriceResponse(GetTotalPriceResponse value) {
        return new JAXBElement<GetTotalPriceResponse>(_GetTotalPriceResponse_QNAME, GetTotalPriceResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetTotalPrice }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.svc.getprc.jsca.enterprisetest.oracle.com", name = "getTotalPrice")
    public JAXBElement<GetTotalPrice> createGetTotalPrice(GetTotalPrice value) {
        return new JAXBElement<GetTotalPrice>(_GetTotalPrice_QNAME, GetTotalPrice.class, null, value);
    }

}
