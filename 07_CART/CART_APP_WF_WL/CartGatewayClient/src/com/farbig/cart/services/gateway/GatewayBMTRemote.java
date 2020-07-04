package com.farbig.cart.services.gateway;

import javax.ejb.Remote;

import com.farbig.cart.services.gateway.util.GatewayMessage;

@Remote
public interface GatewayBMTRemote {

	GatewayMessage process(GatewayMessage request) throws Throwable;

}
