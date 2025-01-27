package com.peppercoin.mpp.gateway;

public class GatewayTarget {
	private String host;
	private int port;
	private String name;

	public GatewayTarget(String host, int port, String name) {
		this.host = host;
		this.port = port;
		this.name = name;
	}

	public String getHost() {
		return this.host;
	}

	public int getPort() {
		return this.port;
	}

	public String getName() {
		return this.name;
	}

	public String toString() {
		return this.name + " (" + this.host + ":" + this.port + ")";
	}
}