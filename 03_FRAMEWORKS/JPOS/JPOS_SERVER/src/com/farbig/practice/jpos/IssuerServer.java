package com.farbig.practice.jpos;

import org.jpos.iso.ISOServer;
import org.jpos.iso.ServerChannel;
import org.jpos.iso.channel.XMLChannel;
import org.jpos.iso.packager.XMLPackager;
import org.jpos.util.LogSource;
import org.jpos.util.Logger;
import org.jpos.util.SimpleLogListener;

public class IssuerServer {
	
	public IssuerServer() {
		super();
	}

	public static void main(String[] args) throws Exception {

		Logger logger = new Logger();
		logger.addListener(new SimpleLogListener(System.out));
		ServerChannel channel = new XMLChannel(new XMLPackager());
		((LogSource) channel).setLogger(logger, "Issuer-Server-channel");
		ISOServer server = new ISOServer(7000, channel, null);
		server.setLogger(logger, "Issuer-server");
		server.addISORequestListener(new IssuerListner());
		new Thread(server).start();
	}
}
