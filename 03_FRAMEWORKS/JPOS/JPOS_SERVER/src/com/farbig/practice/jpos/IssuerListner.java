package com.farbig.practice.jpos;

import java.io.IOException;

import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISORequestListener;
import org.jpos.iso.ISOSource;

public class IssuerListner implements ISORequestListener {
	
	public IssuerListner() {
		super();
	}
	public boolean process(ISOSource source, ISOMsg m) {

		try {
			System.out.println(m.getDirection() + m.getMTI());
			m.setResponseMTI();
			m.set(26, "30");
			m.set(39, "00");
			source.send(m);
		} catch (ISOException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}

}
