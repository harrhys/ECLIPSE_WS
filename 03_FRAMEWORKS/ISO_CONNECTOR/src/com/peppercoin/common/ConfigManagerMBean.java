package com.peppercoin.common;

public interface ConfigManagerMBean {
	void refreshConfigCache();

	void setConfigParameter(String var1, String var2, String var3, boolean var4);

	void setConfigParameter(String var1, String var2);

	String getConfigParameter(String var1);

	String showAllConfigParameters();

	String showCurrentTime();

	String showAllSystemParameters();

	String showBuildInfo();
}