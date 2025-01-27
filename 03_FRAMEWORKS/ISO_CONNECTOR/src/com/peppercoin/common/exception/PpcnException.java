package com.peppercoin.common.exception;

import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public abstract class PpcnException extends Exception {
	private ExceptionKey key;
	
	public static class BundleID
	{

	    public String getName()
	    {
	        return name;
	    }

	    private String name;

	    public BundleID(String name)
	    {
	        this.name = name;
	    }
	}

	public ExceptionKey getKey() {
		return this.key;
	}

	protected PpcnException(BundleID bundle, ExceptionKey key, Object[] args) {
		super(format(bundle, key, args));
		this.key = key;
	}

	protected PpcnException(BundleID bundle, ExceptionKey key, String arg) {
		this(bundle, key, toArray(arg));
	}

	protected PpcnException(BundleID bundle, ExceptionKey key, String arg1, String arg2) {
		this(bundle, key, toArray(arg1, arg2));
	}

	protected PpcnException(BundleID bundle, ExceptionKey key, String arg1, String arg2, String arg3) {
		this(bundle, key, toArray(arg1, arg2, arg3));
	}

	protected PpcnException(BundleID bundle, ExceptionKey key) {
		super(format(bundle, key, new Object[0]));
		this.key = key;
	}

	private static String format(BundleID bundle, ExceptionKey key, Object[] args) {
		try {
			ResourceBundle res = ResourceBundle.getBundle(bundle.getName());
			return MessageFormat.format(res.getString(key.getName()), args);
		} catch (MissingResourceException var4) {
			return "can't find resource in bundle=" + bundle.getName() + ", key=" + key.getName();
		} catch (ClassCastException var5) {
			return "can't cast resource in bundle=" + bundle.getName() + ", key=" + key.getName();
		}
	}

	protected static Object[] toArray(Object o1, Object o2, Object o3) {
		Object[] o = new Object[]{o1, o2, o3};
		return o;
	}

	protected static Object[] toArray(Object o1, Object o2) {
		Object[] o = new Object[]{o1, o2};
		return o;
	}

	protected static Object[] toArray(Object o1) {
		Object[] o = new Object[]{o1};
		return o;
	}
}