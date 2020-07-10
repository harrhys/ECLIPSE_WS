package com.farbig.web.services.client;

import java.util.ArrayList;

import javax.xml.namespace.QName;
import javax.xml.rpc.Call;
import javax.xml.rpc.Service;
import javax.xml.rpc.ServiceFactory;
import javax.xml.rpc.encoding.XMLType;

public class DynamicInvocationInterface {

	static String wsdlURL = "http://localhost:80/services/product?WSDL";

	static String nameSpace = "http://services.web.farbig.com/";

	static String endPointURL = "http://localhost:80/services/product";

	public static void main(String[] args) {

		Call call = createRPCStyleCall();
		testRPCServiceMethods(call);
		call = createDocumentStyleCall();
		testDocumentServiceMethods(call);

	}

	private static Call createDocumentStyleCall() {

		Call call = null;

		try {
			// create the Service client object using the wsdl and service name
			QName serviceName = new QName(wsdlURL, "ProductServices");
			ServiceFactory factory = ServiceFactory.newInstance();
			Service service = factory.createService(serviceName);

			// Create Call object
			call = service.createCall();

			// set the target endpoint
			call.setTargetEndpointAddress(endPointURL);

			// specify the Document-style operation.
			call.setProperty(Call.SOAPACTION_URI_PROPERTY, endPointURL);
			//call.setProperty(Call.SOAPACTION_USE_PROPERTY, Boolean.TRUE);
			call.setProperty(Call.OPERATION_STYLE_PROPERTY, "document");
			// and the encoding style
			//call.setProperty(Call.ENCODINGSTYLE_URI_PROPERTY, null);

		} catch (Throwable th) {
			th.printStackTrace();
		}

		return call;
	}

	private static void testDocumentServiceMethods(Call call) {

		try {

			// operation name
			QName operationName = new QName(nameSpace, "getProducts");
			call.setOperationName(operationName);
			call.setReturnType(XMLType.XSD_STRING);

			// Invoke the method readLSpercent
			Object[] getProductsArgs = {};
			
			 call.invoke(getProductsArgs);

		//	System.out.println("Result : getProducts " + list);

			

		} catch (Throwable th) {
			th.printStackTrace();
		}

	}

	public static Call createRPCStyleCall() {

		Call call = null;

		try {
			// create the Service client object using the wsdl and service name
			QName serviceName = new QName(wsdlURL, "ProductServices");
			ServiceFactory factory = ServiceFactory.newInstance();
			Service service = factory.createService(serviceName);

			// Create Call object
			call = service.createCall();

			// set the target endpoint
			call.setTargetEndpointAddress(endPointURL);

			// specify the RPC-style operation.
			call.setProperty(Call.OPERATION_STYLE_PROPERTY, "rpc");
			// and the encoding style
			call.setProperty(Call.ENCODINGSTYLE_URI_PROPERTY, "http://schemas.xmlsoap.org/soap/encoding/");

		} catch (Throwable th) {
			th.printStackTrace();
		}

		return call;
	}

	public static void testRPCServiceMethods(Call call) {

		try {

			// operation name
			QName operationName = new QName(nameSpace, "getProducts");
			call.setOperationName(operationName);

			// Invoke the method readLSpercent
			Object[] getProductsArgs = {};
			Object getProductResult = call.invoke(getProductsArgs);

			System.out.println("Result : getProducts " + getProductResult.toString());

			/*
			 * call.removeAllParameters();
			 * 
			 * // operation name operationName = new QName(nameSpace, "addProduct");
			 * call.setOperationName(operationName);
			 * 
			 * // input parameter call.addParameter("arg0", // parameter name should be arg0
			 * XMLType.XSD_STRING, // parameter XML type QName String.class, // parameter
			 * Java type class ParameterMode.IN); // parameter mode
			 * 
			 * // setting return type call.setReturnType(XMLType.XSD_STRING);
			 * 
			 * // Invoke the method readLSpercent Object[] addProductArgs = { "DII Product"
			 * }; Object addProductResult = call.invoke(addProductArgs);
			 * 
			 * System.out.println("Result value: addProduct " +
			 * addProductResult.toString());
			 * 
			 * call.removeAllParameters();
			 * 
			 * // operation name operationName = new QName(nameSpace, "removeProduct");
			 * call.setOperationName(operationName);
			 * 
			 * // input parameter call.addParameter("arg0", // parameter name should be arg0
			 * XMLType.XSD_STRING, // parameter XML type QName String.class, // parameter
			 * Java type class ParameterMode.IN); // parameter mode
			 * 
			 * // setting return type call.setReturnType(XMLType.XSD_STRING);
			 * 
			 * // Invoke the method readLSpercent Object[] removeProductArgs = {
			 * "DP Product" }; Object removeProductResult = call.invoke(removeProductArgs);
			 * 
			 * System.out.println("Result value: removeProduct " +
			 * removeProductResult.toString());
			 * 
			 * call.removeAllParameters();
			 * 
			 * // operation name operationName = new QName(nameSpace, "removeOldProduct");
			 * call.setOperationName(operationName);
			 * 
			 * // Invoke the method readLSpercent Object[] removeOldProductArgs = {}; Object
			 * removeOldProductResult = call.invoke(removeOldProductArgs);
			 * 
			 * System.out.println("Result value: removeOldProduct " +
			 * removeOldProductResult.toString());
			 * 
			 * call.removeAllParameters();
			 */

		} catch (Throwable th) {
			th.printStackTrace();
		}

	}

}
