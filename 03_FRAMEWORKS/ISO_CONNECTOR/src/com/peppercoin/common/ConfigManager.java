package com.peppercoin.common;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;

import org.jboss.system.ServiceMBeanSupport;

import com.peppercoin.common.PpcnLogger.Level;
import com.peppercoin.common.exception.PpcnError;

public class ConfigManager extends ServiceMBeanSupport implements ConfigManagerMBean {
	private static final PpcnLogger logger;
	
	private class EarFilenameFilter
    implements FilenameFilter
{

    public boolean accept(File file, String name)
    {
        return name.matches("(.)*\\.ear$");
    }

    private EarFilenameFilter()
    {
    }

    EarFilenameFilter(ConfigManager x1)
    {
        this();
    }
}

	public void refreshConfigCache() {
		try {
			logger.log("refreshCache", "Configuration parameter cache about to be reset", Level.WARN);
			Config.reset();
		} catch (Exception var2) {
			throw new PpcnError("Exception caught by MBean:", var2);
		}
	}

	public void setConfigParameter(String key, String value) {
		this.setConfigParameter(key, value, "No description specified", true);
	}

	public void setConfigParameter(String key, String value, String description, boolean withTransaction) {
		try {
			logger.log("setParameter", "Request to set parameter " + key + " = " + value, Level.WARN);
			if (withTransaction) {
				Config.setWithTransaction(key, value, description);
			} else {
				Config.set(key, value, description);
			}

		} catch (Exception var6) {
			throw new PpcnError("Exception caught by MBean:", var6);
		}
	}

	public String getConfigParameter(String key) {
		try {
			return Config.get(key);
		} catch (Exception var3) {
			throw new PpcnError("Exception caught by MBean:", var3);
		}
	}

	public String showAllConfigParameters() {
		try {
			Collection parameters = Config.getAllParameters();
			JMXHTMLTable table = new JMXHTMLTable(new String[]{"Key", "Value", "Host"});
			Iterator iter = parameters.iterator();

			while (iter.hasNext()) {
				ConfigParam nextParam = (ConfigParam) iter.next();
				table.addRow(new String[]{nextParam.getKey(), nextParam.getValue(), nextParam.getHost()});
			}

			return table.render();
		} catch (Exception var5) {
			throw new PpcnError("Exception caught by MBean:", var5);
		}
	}

	public String showAllSystemParameters() {
		JMXHTMLTable table = new JMXHTMLTable(new String[]{"Name", "Value"});
		Enumeration e = System.getProperties().propertyNames();

		while (e.hasMoreElements()) {
			String nextName = (String) e.nextElement();
			table.addRow(new String[]{nextName, System.getProperty(nextName)});
		}

		return table.render();
	}

	public String showCurrentTime() {
		PpcnTime now = new PpcnTime();
		return "Current time: " + now + "   Delta: " + PpcnTime.getTimeDelta();
	}

	public String showBuildInfo() {
		JMXHTMLTable table = new JMXHTMLTable(new String[]{"Property", "Value"});
		File earFile = this.getEarFile();
		if (earFile != null) {
			table.addRow(new String[]{"Ear file name", earFile.getName()});
			table.addRow(new String[]{"Last modified", (new Date(earFile.lastModified())).toString()});
			return table.render();
		} else {
			return "No ear files found!";
		}
	}

	private File getEarFile() {
      File deployDir = new File(System.getProperty("jboss.server.home.dir") + File.separator + "deploy");
      File[] files = deployDir.listFiles(new EarFilenameFilter(this));
      logger.log("getEarFile", "Found " + files.length + " ear files", Level.DEBUG);
      return files.length != 1 ? null : files[0];
   }

	static {
		logger = new PpcnLogger(com.peppercoin.common.ConfigManager.class.getName());
	}
}