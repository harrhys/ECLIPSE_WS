package com.farbig.cart.services.gateway;

import com.farbig.cart.services.gateway.util.GatewayMessage;

public interface Gateway {

	GatewayMessage process(GatewayMessage request) throws Exception, Throwable;

}
