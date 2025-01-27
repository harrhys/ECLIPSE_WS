package com.peppercoin.common;

import com.peppercoin.common.exception.PpcnError;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import javax.crypto.KeyGenerator;

public class Util {
	private static String myHost = null;
	public static final String KEY_TOKENS = "ABCDEFGHIJKLMNPQRSTUVXYZ0123456789";
	private static int keyCounter = 0;
	private static final Random randomKey = new Random(System.currentTimeMillis());
	private static final byte[] kids = "MattFangYiMollyJoannaUmeshSashaKerryGraceSruthiStefanKyrielJack!".getBytes();

	public static String genKey(String prefix) {
		return prefix + "-" + genKey();
	}

	public static synchronized String genKey() {
		StringBuffer key = new StringBuffer(15);
		String baseKey = genBaseKey();
		int k = 0;

		for (int j = 0; j < baseKey.length(); ++k) {
			if (k == 5) {
				key.append("-");
				k = 0;
			}

			key.append(baseKey.charAt(j));
			++j;
		}

		return key.toString();
	}

	private static String genBaseKey() {
		long t = System.currentTimeMillis();
		return keyCompress(t) + keyCompress(keyCounter++ % ("ABCDEFGHIJKLMNPQRSTUVXYZ0123456789".length() * 2));
	}

	public static synchronized String genRandomKey(int len) {
		String v = keyCompress(randomKey.nextLong());
		if (v.length() > len) {
			v = v.substring(v.length() - len);
		}

		while (v.length() < len) {
			v = v + "ABCDEFGHIJKLMNPQRSTUVXYZ0123456789"
					.charAt(randomKey.nextInt("ABCDEFGHIJKLMNPQRSTUVXYZ0123456789".length()));
		}

		return v;
	}

	private static String keyCompress(long t) {
		StringBuffer key = new StringBuffer(15);

		for (int len = "ABCDEFGHIJKLMNPQRSTUVXYZ0123456789".length(); t > 0L; t /= (long) len) {
			key.append("ABCDEFGHIJKLMNPQRSTUVXYZ0123456789".charAt((int) (t % (long) len)));
		}

		return key.reverse().toString();
	}

	public static String keyCompress(int t) {
		StringBuffer key = new StringBuffer(15);

		for (int len = "ABCDEFGHIJKLMNPQRSTUVXYZ0123456789".length(); t > 0; t /= len) {
			key.append("ABCDEFGHIJKLMNPQRSTUVXYZ0123456789".charAt(t % len));
		}

		return key.reverse().toString();
	}

	public static long keyUncompress(String key) {
		int t = 0;
		int len = "ABCDEFGHIJKLMNPQRSTUVXYZ0123456789".length();

		for (int j = 0; j < key.length(); ++j) {
			int i = "ABCDEFGHIJKLMNPQRSTUVXYZ0123456789".indexOf(key.charAt(j));
			if (i == -1) {
				throw new PpcnError("Invalid key - can't uncompress it: " + key);
			}

			t *= len;
			t += i;
		}

		return (long) t;
	}

	public static String getHost() {
		if (myHost != null) {
			return myHost;
		} else {
			try {
				myHost = InetAddress.getLocalHost().getHostName();
			} catch (UnknownHostException var1) {
				myHost = "";
			}

			return myHost;
		}
	}

	public static String getIPAddress() {
		try {
			return InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException var1) {
			return "";
		}
	}

	public static int toInt(String s, int defaultValue) {
		if (s == null) {
			return defaultValue;
		} else {
			try {
				return Integer.parseInt(s);
			} catch (NumberFormatException var3) {
				return defaultValue;
			}
		}
	}

	public static void sleep(long usecs) {
		try {
			Thread.sleep(usecs);
		} catch (InterruptedException var3) {
			System.out.println("Thread sleep interrupted: " + var3.toString());
		}

	}

	public static Object getInstance(String className) {
		try {
			return Class.forName(className).newInstance();
		} catch (ClassNotFoundException var2) {
			throw new PpcnError("getInstance: " + className + " can't be found", var2);
		} catch (InstantiationException var3) {
			throw new PpcnError("getInstance: " + className + " can't be instantiated", var3);
		} catch (IllegalAccessException var4) {
			throw new PpcnError("getInstance: " + className + " can't be instantiated", var4);
		} catch (RuntimeException var5) {
			throw new PpcnError("getInstance: " + className + " can't be launched", var5);
		}
	}

	public static String encode(String string) {
		if (string == null) {
			return string;
		} else {
			try {
				MessageDigest digester = MessageDigest.getInstance("SHA-1");
				byte[] hash = digester.digest(obfuscate(string));
				String v = "#" + Base64.encodeBytes(hash);
				return v;
			} catch (NoSuchAlgorithmException var4) {
				throw new PpcnError("Can't encode strings.", var4);
			}
		}
	}

	private static byte[] obfuscate(String s) {
		if (s != null && s.length() >= 1) {
			byte[] b = new byte[64];
			byte[] sb = s.getBytes();
			int j = 0;

			while (j < 64) {
				for (int k = 0; k < sb.length && j < 64; ++j) {
					b[j] = (byte) (sb[k] ^ kids[j]);
					++k;
				}
			}

			return b;
		} else {
			return "".getBytes();
		}
	}

	public static String toString(Object o) {
		return o == null ? "" : o.toString();
	}

	public static synchronized void generateMACKey() {
		String old = Config.get("charges-url-key");
		if (old != null) {
			Config.set("previous-charges-url-key", old, "Previous copy of the End-User MAC Key");
		}

		try {
			String newKey = Base64.encodeBytes(KeyGenerator.getInstance("HmacSHA1").generateKey().getEncoded());
			Config.set("charges-url-key", newKey, "End-User MAC Key");
		} catch (NoSuchAlgorithmException var2) {
			throw new PpcnError("Can't generateMACKey.", var2);
		}
	}

	public static String encodeForMonitor(String input) {
		String monitorString = "[MONITOR]";
		StringBuffer buf = new StringBuffer(monitorString);
		buf.append(input);
		buf.append(monitorString);
		return buf.toString();
	}
}