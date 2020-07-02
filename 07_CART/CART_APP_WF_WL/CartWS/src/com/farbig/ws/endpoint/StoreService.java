package com.farbig.ws.endpoint;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import javax.jws.WebParam.Mode;
import javax.xml.ws.Holder;

import com.farbig.ws.objects.Product;
import com.farbig.ws.objects.Store;

@WebService(targetNamespace = "http://localhost:7001/Services/StoreServiceEP")
@SOAPBinding(style = Style.DOCUMENT)
public interface StoreService extends ProductService {

	@WebMethod(action = "createProductAction", operationName = "createProduct")
	public @WebResult(header = false, name = "productOutput", partName = "productResult") Product CreateProductEntity(
			@WebParam(mode = Mode.INOUT, header = false, name = "partnerId", partName = "partnerIdParam", targetNamespace = "http://localhost:7001/Services/StoreServices/PartnerId") Holder<String> partnerId,
			@WebParam(mode = Mode.IN, header = false,  partName = "productParam", targetNamespace = "http://localhost:7001/Services/StoreServices/Product") Product product);

	@WebMethod(action = "createStoreAction", operationName = "createStore")
	public @WebResult(header = false, name = "storeOutput", partName = "storeResult") Store CreateStoreEntity(
			@WebParam(mode = Mode.INOUT, header = true, name = "partnerId", partName = "partnerIdParam", targetNamespace = "http://localhost:7001/Services/StoreServices/partnerId") Holder<String> partnerId,
			@WebParam(mode = Mode.IN, header = false,  partName = "storeParam", targetNamespace = "http://localhost:7001/Services/StoreServices/Store") Store store);

}
