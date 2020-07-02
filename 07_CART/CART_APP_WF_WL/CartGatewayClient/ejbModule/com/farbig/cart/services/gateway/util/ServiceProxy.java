package com.farbig.cart.services.gateway.util;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.farbig.cart.services.gateway.GatewayBMTRemote;
import com.farbig.cart.services.gateway.GatewayCMTRemote;
import com.farbig.cart.services.gateway.util.BeanType.Type;

public class ServiceProxy implements InvocationHandler {

	private static Map<BeanType.Type, Object> ejbCache = new HashMap<BeanType.Type, Object>();

	private static Queue destinationQueue;

	private boolean isAsync;

	private String serviceName;

	private String srcServiceName;

	private QueueConnectionFactory qcf;

	public ServiceProxy(String serviceName)

	{
		this.serviceName = serviceName;
	}

	public ServiceProxy(String name, boolean b) {

		this.serviceName = name;
		this.isAsync = b;
	}

	public ServiceProxy(String srcServiceName, String tgtServiceName)

	{
		this.srcServiceName = srcServiceName;
		this.serviceName = tgtServiceName;
		this.isAsync = true;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

		Object retval;

		String n = this.serviceName;
		if (isAsync) {
			retval = invokeAsync(method, args);
		} else {
			retval = invokeSync(method, args);
		}
		return retval;
	}

	private Object invokeSync(Method method, Object[] args) throws Throwable {

		Object response = null;

		try {
			GatewayMessage request = getRequest(method, args);
			response = callService(request);
		} catch (Throwable e) {
			e.printStackTrace();
			throw e;
		}

		return response;

	}

	private Object invokeAsync(Method method, Object[] args) throws Throwable {

		try {
			processAsync(getRequest(method, args));
		} catch (Throwable e) {
			e.printStackTrace();
			throw e;
		}
		return null;
	}

	public GatewayMessage getRequest(Method method, Object[] args) {
		GatewayMessage request = new GatewayMessage();
		if (isAsync) {
			request.put(GatewayConstants.MESSAGE_PARAMETER_SOURCE_SERVICE_NAME, this.srcServiceName);

		}
		request.put(GatewayConstants.MESSAGE_PARAMETER_SERVICE_NAME, this.serviceName);
		request.put(GatewayConstants.MESSAGE_PARAMETER_METHOD_NAME, method.getName());

		request.put(GatewayConstants.BEAN_TYPE, getBeanType(method));

		Class pt = null;

		for (int i = 0; i < method.getParameterTypes().length; i++) {
			pt = method.getParameterTypes()[i];
			request.put(GatewayConstants.MESSAGE_PARAMETER_METHOD_PARAMETER_TYPE_PREFIX + i, pt.getName());
			request.put(GatewayConstants.MESSAGE_PARAMETER_METHOD_PARAMETER_VALUE_PREFIX + i, args[i]);

		}

		return request;
	}

	Type getBeanType(Method method) {
		return method.getAnnotation(BeanType.class) != null ? method.getAnnotation(BeanType.class).value() : null;
	}

	public static Object callService(GatewayMessage message) throws Throwable {
		Object response = null;
		GatewayMessage respMsg = null;

		String serviceName = (String) message.get(GatewayConstants.MESSAGE_PARAMETER_SERVICE_NAME);
		String methodName = (String) message.get(GatewayConstants.MESSAGE_PARAMETER_METHOD_NAME);

		try {
			respMsg = process(message);
			response = respMsg.get(GatewayConstants.MESSAGE_PARAMETER_METHOD_RETURN);
		} catch (Throwable e) {
			throw e;
		}

		return response;
	}

	private static GatewayMessage process(GatewayMessage request) throws Throwable {

		GatewayMessage response = null;

		Type type = ((Type) request.get(GatewayConstants.BEAN_TYPE));

		if (type != null && type.equals(Type.BMT)) {

			GatewayBMTRemote proxy = (GatewayBMTRemote) ejbCache.get(Type.BMT);

			if (proxy == null) {
				proxy = (GatewayBMTRemote) getContext()
						.lookup("java:app/CartGateway/GatewayBMT");
				ejbCache.put(Type.BMT, proxy);
			}

			response = proxy.process(request);

		} else {
			GatewayCMTRemote proxy = (GatewayCMTRemote) ejbCache.get(Type.CMT);

			if (proxy == null) {
				proxy = (GatewayCMTRemote) getContext()
						.lookup("java:app/CartGateway/GatewayCMT");
				ejbCache.put(Type.CMT, proxy);
			}
			response = proxy.process(request);
		}

		return response;
	}

	void processAsync(GatewayMessage request) throws Throwable {

		if (qcf == null)
			qcf = (QueueConnectionFactory) getContext().lookup("CARTJMSCF");

		QueueConnection qc = qcf.createQueueConnection();

		QueueSession qs = qc.createQueueSession(false, Session.SESSION_TRANSACTED);

		ObjectMessage msg = qs.createObjectMessage();
		msg.setObject(request);
		
		if (destinationQueue == null)
			destinationQueue = (Queue) getContext().lookup("CARTJMSDQ");

		qs.createSender(destinationQueue).send(msg);

		qs.close();
		qc.close();
	}

	private static Context getContext() {

		Properties p = new Properties();
		p.put(Context.PROVIDER_URL, "t3://localhost:7001");
		p.put(Context.INITIAL_CONTEXT_FACTORY, "weblogic.jndi.WLInitialContextFactory");

		Context context = null;
		try {
			context = new InitialContext();
		} catch (NamingException e) {
			e.printStackTrace();
		}

		return context;
	}

}
