package com.farbig.services.ws.calculator.client.dii;

import javax.xml.namespace.QName;
import javax.xml.rpc.Call;
import javax.xml.rpc.ParameterMode;
import javax.xml.rpc.Service;
import javax.xml.rpc.ServiceFactory;
import javax.xml.rpc.encoding.XMLType;

public class DynamicInvocationInterface {

	static String wsdlURL = "http://localhost:80/services/calculator?WSDL";

	static String nameSpace = "http://farbig.com";

	static String endPointURL = "http://localhost:80/services/calculator";

	public static void main(String[] args) {

		Call call = createRPCStyleCall();
		testRPCServiceMethods(call);
	}

	public static Call createRPCStyleCall() {

		Call call = null;

		try {
			// create the Service client object using the wsdl and service name
			QName serviceName = new QName(wsdlURL, "SimpleCalculatorService");
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
			QName operationName = new QName(nameSpace, "add");
			call.setOperationName(operationName);

			// input parameter
			call.addParameter("arg0", // parameter name should be arg0
					XMLType.XSD_INTEGER, // parameter XML type QName
					Integer.class, // parameter Java type class
					ParameterMode.IN); // parameter mode

			// input parameter
			call.addParameter("arg1", // parameter name should be arg0
					XMLType.XSD_INTEGER, // parameter XML type QName
					Integer.class, // parameter Java type class
					ParameterMode.IN); // parameter mode

			// setting return type
			call.setReturnType(XMLType.XSD_STRING);

			// Invoke the method add
			Object[] addArgs = { new Integer(7), new Integer(5) };
			Object addResult = call.invoke(addArgs);

			System.out.println("Result value: add " + addResult.toString());

			

		} catch (Throwable th) {
			th.printStackTrace();
		}

	}

}
