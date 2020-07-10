package com.farbig.cart.services.gateway;

import javax.ejb.Stateless;

import com.farbig.cart.services.gateway.util.GatewayMessage;
import com.farbig.cart.services.gateway.util.ServiceHelper;

/**
 * Session Bean implementation class GatewayCMT
 */
@Stateless(name = "gatewaycmt")
public class GatewayCMT implements GatewayCMTRemote {

    /**
     * Default constructor. 
     */
    public GatewayCMT() {
    }
    
  	@Override
  	public GatewayMessage process(GatewayMessage request) throws Throwable {

  		ServiceHelper helper = new ServiceHelper();
  		
  		GatewayMessage response = null;
  		
  		System.out.println("Inside GatewayCMT");
  		
  		

  		try {
  			
  			response = helper.process(request);
  			
  		} catch (Throwable e) {
  			e.printStackTrace();
  			throw e;
  		}
  		return response;
  	}

}
