package com.farbig.cart.services.gateway.util;


public interface ReturnHandlerService {
	
	public void handleReturnService(GatewayMessage orignalMsg, Object tgtServiceResponse);

}
