package com.farbig.cart.services.gateway;

import javax.ejb.Remote;

import com.farbig.cart.services.gateway.util.GatewayMessage;

@Remote
public interface GatewayCMTRemote extends Gateway{

	@Override
	GatewayMessage process(GatewayMessage request) throws Throwable;

}
