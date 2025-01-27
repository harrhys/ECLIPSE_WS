package com.peppercoin.mpp.gateway.util;

import com.peppercoin.common.crypto.provider.CryptoUtilsFacade;
import com.peppercoin.common.exception.PpcnError;
import com.peppercoin.common.persistence.PpcnResultSet;
import com.peppercoin.common.ref.Merchant;
import com.peppercoin.common.xml.XMLDoc;
import com.peppercoin.mpp.transaction.IncomingRequest;
import java.util.HashMap;
import java.util.Map;

public abstract class MessageUtil {
	private static final byte[] EVEN_PARITY = new byte[128];

	public static byte evenParity(byte value) {
		if (value < 0) {
			throw new PpcnError("Cannot compute even parity for input byte < 0");
		} else {
			return EVEN_PARITY[value];
		}
	}

	public static byte[] undoEvenParity(byte[] bytes) {
		if (bytes == null) {
			return null;
		} else {
			for (int i = 0; i < bytes.length; ++i) {
				if (bytes[i] < 0) {
					bytes[i] = (byte) (bytes[i] & 127);
				}
			}

			return bytes;
		}
	}

	private static int bitCount(int b) {
		b -= b >>> 1 & 1431655765;
		b = (b & 858993459) + (b >>> 2 & 858993459);
		b = b + (b >>> 4) & 252645135;
		b += b >>> 8;
		b += b >>> 16;
		return b & 63;
	}

	public static String getBitRepresentation(byte b) {
		String bits = "";

		for (int i = 7; i >= 0; --i) {
			byte power = (byte) ((int) Math.pow(2.0D, (double) i));
			if ((b & power) == power) {
				bits = bits + "1";
			} else {
				bits = bits + "0";
			}
		}

		return bits;
	}

	public static boolean isPOS(IncomingRequest transaction) {
		return transaction.getTrack1() != null || transaction.getTrack2() != null;
	}

	public static boolean isVisa(IncomingRequest trans) {
		return trans.getCardType().equals("Visa");
	}

	public static boolean isMasterCard(IncomingRequest trans) {
		return trans.getCardType().equals("MasterCard");
	}

	public static boolean isAMEX(IncomingRequest trans) {
		return trans.getCardType().equals("American Express");
	}

	public static boolean isDiscover(IncomingRequest trans) {
		return trans.getCardType().equals("Discover");
	}

	public static boolean supportsAVS(IncomingRequest trans) {
		String type = trans.getCardType();
		return type.equals("Visa") || type.equals("MasterCard") || type.equals("American Express")
				|| type.equals("Discover") || type.equals("Diners Club") || type.equals("Diners Club for MC");
	}

	public static boolean cardSupportsCVV(IncomingRequest transaction) {
		return isVisa(transaction) || isMasterCard(transaction) || isDiscover(transaction) || isAMEX(transaction);
	}

	public static void addField(XMLDoc doc, String name, String value) {
		Map attributes = new HashMap();
		attributes.put("name", name);
		attributes.put("value", value);
		doc.add("FIELD", (String) null, attributes);
	}

	public static String getCardNumber(PpcnResultSet rs) {
		return getCardNumber(rs.getString("cc_number_encrypted"), rs.getString("encrypted_key_id"));
	}

	public static String getCardNumber(String encryptedNumber, String keyId) {
		return CryptoUtilsFacade.decrypt(encryptedNumber, keyId);
	}

	public static String getSoftDescriptor(Merchant merchant, String requestId) {
		String prefix = merchant.getSoftDescriptorPrefix();
		String suffix = merchant.getSoftDescriptorSuffix();
		if (prefix == null) {
			prefix = "";
		}

		if (suffix == null) {
			suffix = "";
		}

		int len = prefix.length();
		if (len > 12) {
			prefix = prefix.substring(0, 12);
		} else if (len < 12 && len > 7) {
			prefix = pad(prefix, 12);
		} else if (len > 3 && len < 7) {
			prefix = pad(prefix, 7);
		} else if (len < 3) {
			prefix = pad(prefix, 3);
		}

		String start = prefix + "*" + suffix;
		if (start.length() > 14) {
			start = start.substring(0, 14);
		} else {
			start = pad(start, 14);
		}

		return start + requestId;
	}

	private static String pad(String s, int len) {
		while (s.length() < len) {
			s = s + " ";
		}

		return s;
	}

	static {
		for (int i = 0; i < 128; ++i) {
			EVEN_PARITY[i] = (byte) (bitCount(i) % 2 == 0 ? i : i | 128);
		}

	}
}