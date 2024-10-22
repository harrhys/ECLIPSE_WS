package com.peppercoin.common;

import com.peppercoin.common.persistence.PersistentObject;

public class ConfigParam extends PersistentObject {
	private ConfigParamCompId compId;
	private String value;
	private String description;

	ConfigParam() {
	}

	ConfigParam(String host, String key, String value, String desc) {
		this.compId = new ConfigParamCompId(host, key);
		this.value = value;
		this.description = desc;
		this.save();
	}

	ConfigParam(String key, String value, String desc) {
		this("", key, value, desc);
	}

	ConfigParam(boolean isLocal, String key, String value, String desc) {
		this.compId = new ConfigParamCompId("", key);
		this.value = value;
		this.description = desc;
	}

	public ConfigParamCompId getId() {
		return this.compId;
	}

	void setId(ConfigParamCompId v) {
		this.compId = v;
	}

	String getHost() {
		return this.compId.getHost();
	}

	String getParamKey() {
		return this.compId.getKey();
	}

	public String getValue() {
		return this.value;
	}

	void setValue(String v) {
		this.value = v;
	}

	public String getKey() {
		return this.getHost() != null && this.getHost().length() != 0
				? this.getHost() + "." + this.getParamKey()
				: "*." + this.getParamKey();
	}

	public String getDescription() {
		return this.description;
	}

	void setDescription(String v) {
		this.description = v;
	}
}