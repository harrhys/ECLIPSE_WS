package com.farbig.cart.services.gateway.util;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

public class ServiceHelper {

	public static Object getServiceProxy(Class serviceClass) {

		Object proxy = Proxy.newProxyInstance(serviceClass.getClassLoader(),
				new Class[] { serviceClass },
				new ServiceProxy(serviceClass.getName()));

		return proxy;
	}
	
	public static Object getAsyncServiceProxy(Class serviceClass) {

		Object proxy = Proxy.newProxyInstance(serviceClass.getClassLoader(),
				new Class[] { serviceClass },
				new ServiceProxy(serviceClass.getName(),true));

		return proxy;
	}

	public static Object getAsyncServiceProxy(Class srcServiceClass,
			Class tgtServiceClass) {

		Object proxy = Proxy.newProxyInstance(tgtServiceClass.getClassLoader(),
				new Class[] { tgtServiceClass }, new ServiceProxy(
						srcServiceClass.getName(), tgtServiceClass.getName()));

		return proxy;
	}

	private static Map serviceCache = new HashMap();

	public GatewayMessage process(GatewayMessage request) throws Throwable {

		GatewayMessage response = null;
		Object retval = null;
		String serviceName = null;
		String methodName = null;

		try {
			serviceName = (String) request
					.get(GatewayConstants.MESSAGE_PARAMETER_SERVICE_NAME);
			methodName = (String) request
					.get(GatewayConstants.MESSAGE_PARAMETER_METHOD_NAME);

			int numberOfParameters = (request.size() - 3) / 2;

			String[] parameterClassesNames = new String[numberOfParameters];
			Object[] parameterValues = new Object[numberOfParameters];
			for (int i = 0; i < numberOfParameters; i++) {
				String param_type = (String) request
						.get(GatewayConstants.MESSAGE_PARAMETER_METHOD_PARAMETER_TYPE_PREFIX
								+ i);

				parameterClassesNames[i] = param_type;
				parameterValues[i] = request
						.get(GatewayConstants.MESSAGE_PARAMETER_METHOD_PARAMETER_VALUE_PREFIX
								+ i);

			}
			retval = invokeServiceMethod(serviceName, methodName,
					parameterClassesNames, parameterValues);

			response = new GatewayMessage();
			response.put(GatewayConstants.MESSAGE_PARAMETER_METHOD_RETURN,
					retval);
		} catch (Throwable e) {

			e.printStackTrace();
			throw e;
		}

		return response;
	}

	private Object invokeServiceMethod(String serviceName, String methodName,
			String[] parameterClassesNames, Object[] parameterValues) throws Throwable
			 {
		
		final String METHOD = "invokeServiceMethod";
		Object retval = null;
		try{
		Class serviceImplementation = (Class) serviceCache.get(serviceName);

		if (serviceImplementation == null) {
			serviceImplementation = Class.forName(serviceName + "Impl");
			serviceCache.put(serviceName, serviceImplementation);
		}

		Class[] parameterClassTypes = new Class[parameterClassesNames.length];

		for (int i = 0; i < parameterClassesNames.length; i++) {
			parameterClassTypes[i] = Class.forName(parameterClassesNames[i]);
		}

		Method m = serviceImplementation.getMethod(methodName,
				parameterClassTypes);

		retval = m.invoke(serviceImplementation.newInstance(), parameterValues);
		}
		catch(Throwable e)
		{
			e.printStackTrace();
			throw e;
		}
		return retval;

	}

}
