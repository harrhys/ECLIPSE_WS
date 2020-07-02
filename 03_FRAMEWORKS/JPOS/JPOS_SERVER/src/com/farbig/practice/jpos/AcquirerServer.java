package com.farbig.practice.jpos;

import org.jpos.iso.ISOServer;
import org.jpos.iso.ServerChannel;
import org.jpos.iso.channel.XMLChannel;
import org.jpos.iso.packager.XMLPackager;
import org.jpos.util.LogSource;
import org.jpos.util.Logger;
import org.jpos.util.SimpleLogListener;

public class AcquirerServer {
	
	public AcquirerServer() {
		super();
	}

	public static void main(String[] args) throws Exception {

		Logger logger = new Logger();
		logger.addListener(new SimpleLogListener(System.out));
		ServerChannel channel = new XMLChannel(new XMLPackager());
		((LogSource) channel).setLogger(logger, "Aquirer-Server-Channel");
		ISOServer server = new ISOServer(8000, channel, null);
		server.setLogger(logger, "Aquirer-server");
		server.addISORequestListener(new AcquirerListner());
		new Thread(server).start();
	}
}
