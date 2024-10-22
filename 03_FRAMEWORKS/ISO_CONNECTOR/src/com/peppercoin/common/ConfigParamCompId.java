package com.peppercoin.common;

import java.io.Serializable;

public class ConfigParamCompId implements Serializable {
	private String host;
	private String key;

	public ConfigParamCompId() {
	}

	public ConfigParamCompId(String host, String key) {
		this.host = host;
		this.key = key;
	}

	public String getKey() {
		return this.key;
	}

	void setKey(String v) {
		this.key = v;
	}

	public String getHost() {
		return this.host;
	}

	void setHost(String v) {
		this.host = v;
	}

	public boolean equals(Object obj) {
		ConfigParamCompId other = (ConfigParamCompId) obj;
		return this.getHost().equals(other.getHost()) && this.getKey().equals(other.getKey());
	}

	public int hashCode() {
		return (this.getHost() + "===" + this.getKey()).hashCode();
	}
}