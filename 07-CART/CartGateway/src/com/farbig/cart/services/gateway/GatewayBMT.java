package com.farbig.cart.services.gateway;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import com.farbig.cart.services.gateway.util.GatewayMessage;
import com.farbig.cart.services.gateway.util.ServiceHelper;

/**
 * Session Bean implementation class GatewayBMT
 */
@Stateless(mappedName = "gatewaybmt",name = "gatewaybmt")
@TransactionManagement(TransactionManagementType.BEAN)
public class GatewayBMT implements GatewayBMTRemote {

    /**
     * Default constructor. 
     */
    public GatewayBMT() {
    	
    }
    
	@Override
	public GatewayMessage process(GatewayMessage request) throws Throwable {

		ServiceHelper helper = new ServiceHelper();
		
		GatewayMessage response = null;
		
		System.out.println("Inside GatewayBMT");

		try {
			response = helper.process(request);

		} catch (Throwable e) {
			e.printStackTrace();
			throw e;
		}
		
		return response;
	}


}
