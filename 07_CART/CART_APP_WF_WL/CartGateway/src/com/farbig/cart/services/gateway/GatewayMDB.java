package com.farbig.cart.services.gateway;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import com.farbig.cart.services.gateway.util.GatewayConstants;
import com.farbig.cart.services.gateway.util.GatewayMessage;
import com.farbig.cart.services.gateway.util.ReturnHandlerService;
import com.farbig.cart.services.gateway.util.ServiceHelper;
import com.farbig.cart.services.gateway.util.ServiceProxy;

/**
 * Message-Driven Bean implementation class for: GatewayMDB
 */
@MessageDriven(mappedName = "CARTJMSDQ", activationConfig = {
		@ActivationConfigProperty(propertyName = "destination", propertyValue = "CARTJMSDQ"),
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue") })
public class GatewayMDB implements MessageListener {

	/**
	 * Default constructor.
	 */
	public GatewayMDB() {
	}

	/**
	 * @see MessageListener#onMessage(Message)
	 */
	public void onMessage(Message message) {

		ObjectMessage msg = null;
		try {
			System.out.println("Message Recived..");

			msg = (ObjectMessage) message;

			GatewayMessage request = (GatewayMessage) msg.getObject();

			System.out.println(request
					.get(GatewayConstants.MESSAGE_PARAMETER_SERVICE_NAME));

			Object resp = ServiceProxy.callService(request);

			Class srcServiceClass = Class.forName(request.get(
					GatewayConstants.MESSAGE_PARAMETER_SOURCE_SERVICE_NAME)
					.toString());

			Object proxy = ServiceHelper.getServiceProxy(srcServiceClass);

			if (proxy instanceof ReturnHandlerService) {
				ReturnHandlerService srcService = (ReturnHandlerService) proxy;
				srcService.handleReturnService(request, resp);
			}
			
			//message.acknowledge();
		} catch (JMSException e) {
			e.printStackTrace();
		} catch (Throwable e) {
			e.printStackTrace();

		}

	}
}
