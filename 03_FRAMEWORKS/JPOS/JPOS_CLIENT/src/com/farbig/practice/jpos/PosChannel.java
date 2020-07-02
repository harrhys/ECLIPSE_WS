package com.farbig.practice.jpos;

import java.io.IOException;

import org.jpos.iso.ISOChannel;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.channel.XMLChannel;
import org.jpos.iso.packager.XMLPackager;
import org.jpos.util.LogSource;
import org.jpos.util.Logger;
import org.jpos.util.SimpleLogListener;

public class PosChannel {

	public static void main(String[] args) throws ISOException, IOException {

		for (int i = 0; i < 3; i++) {

			Thread pos = new Thread(new Runnable() {

				@Override
				public void run() {

					try {

						Logger logger = new Logger();
						logger.addListener(new SimpleLogListener(System.out));
						ISOChannel acquirerChannel = new XMLChannel("localhost", 8000, new XMLPackager());
						((LogSource) acquirerChannel).setLogger(logger, Thread.currentThread().getName());
						acquirerChannel.connect();
						ISOMsg m = new ISOMsg();
						m.setMTI("0800");
						m.set(3, "000000");
						m.set(26, "10");
						m.set(41, "00000001");
						m.set(70, "301");
						System.out.println(m.getDirection() + m.getMTI());
						acquirerChannel.send(m);
						acquirerChannel.receive();
						acquirerChannel.disconnect();

					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			},"Pos-Channel-"+i);
			
			pos.start();

		}

	}

}