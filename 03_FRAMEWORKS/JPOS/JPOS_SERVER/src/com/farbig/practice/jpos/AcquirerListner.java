package com.farbig.practice.jpos;

import java.io.IOException;

import org.jpos.iso.ISOChannel;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISORequestListener;
import org.jpos.iso.ISOSource;
import org.jpos.iso.channel.XMLChannel;
import org.jpos.iso.packager.XMLPackager;
import org.jpos.util.LogSource;
import org.jpos.util.Logger;
import org.jpos.util.SimpleLogListener;

public class AcquirerListner implements ISORequestListener {

	ISOChannel issuerChannel;

	public AcquirerListner() {
		super();
		connectToIssuer();
	}

	public boolean process(ISOSource source, ISOMsg m) {

		try {
			if (!issuerChannel.isConnected())
				issuerChannel.connect();
			m.set(26, "20");
			issuerChannel.send(m);
			ISOMsg r = issuerChannel.receive();
			System.out.println(r.getDirection() + r.getMTI());
			m.setResponseMTI();
			m.set(39, "00");
			m.set(26, "40");
			source.send(m);
		} catch (ISOException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}

	public void connectToIssuer() {
		try {
			Logger logger = new Logger();
			logger.addListener(new SimpleLogListener(System.out));
			issuerChannel = new XMLChannel("localhost", 7000, new XMLPackager());
			((LogSource) issuerChannel).setLogger(logger, "Issuer-channel");
			issuerChannel.connect();
		} catch (ISOException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void disconnectFromIssuer() {
		try {
			issuerChannel.disconnect();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
