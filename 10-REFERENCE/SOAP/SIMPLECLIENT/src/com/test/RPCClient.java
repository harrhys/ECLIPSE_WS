package com.test;

import javax.xml.namespace.QName;
import javax.xml.rpc.Call;
import javax.xml.rpc.ParameterMode;
import javax.xml.rpc.Service;
import javax.xml.rpc.ServiceFactory;
import javax.xml.rpc.encoding.XMLType;



public class RPCClient {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		try {
			QName serviceName = new QName(
					"http://localhost:7777/MySimpleService/SimplePort?WSDL",
					"WSImplService");
			ServiceFactory factory = ServiceFactory.newInstance();
			Service service = factory.createService(serviceName);
			String endpoint = "http://localhost:7777/MySimpleService/SimplePort";
			Call call = (Call) service.createCall();
			call.setTargetEndpointAddress(endpoint);
			QName operationName = new QName(
			        "http://test.com/", "getFullName");
			call.setOperationName(operationName);
			call.addParameter("first", XMLType.XSD_STRING,  
					ParameterMode.IN);
			call.addParameter("last", XMLType.XSD_STRING,  
					ParameterMode.IN);
			call.setReturnType(XMLType.XSD_STRING);
			 call.setProperty(Call.OPERATION_STYLE_PROPERTY,
				        "document");
				      // and the encoding style
				      call.setProperty(
					Call.ENCODINGSTYLE_URI_PROPERTY,
					"http://schemas.xmlsoap.org/soap/encoding/");
			String ret = (String) call
					.invoke(new Object[] { "Balaji", "Lord" });
			System.out.println("Result" + ret);
		} catch (Exception e) {
			System.err.println(e.toString());
		}

	}

}
