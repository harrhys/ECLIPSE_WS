package com.farbig.practice.cdi.container;

public class CDIContainer {

//	public static void main(String[] args) {
//		
//		new Weld().initialize();
//		
//		 try (CDI<Object> container = new Weld().initialize()) {
//	            Checkout checkout = container.select(Checkout.class).get();
//	            checkout.finishCheckout();
//	        }
//
//	}

}

class Checkout {
    public void finishCheckout() {
        System.out.println("Finishing Checkout");
    }
}
