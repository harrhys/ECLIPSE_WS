package com.peppercoin.common;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.io.*;

public class Base64 {
	public static final int NO_OPTIONS = 0;
	public static final int ENCODE = 1;
	public static final int DECODE = 0;
	public static final int GZIP = 2;
	public static final int DONT_BREAK_LINES = 8;
	private static final int MAX_LINE_LENGTH = 76;
	private static final byte EQUALS_SIGN = 61;
	private static final byte NEW_LINE = 10;
	private static final String PREFERRED_ENCODING = "UTF-8";
	private static final byte[] ALPHABET;
	private static final byte[] _NATIVE_ALPHABET = new byte[]{65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78,
			79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108,
			109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56,
			57, 45, 47};
	private static final byte[] DECODABET;
	private static final byte BAD_ENCODING = -9;
	private static final byte WHITE_SPACE_ENC = -5;
	private static final byte EQUALS_SIGN_ENC = -1;
	
	public static class OutputStream extends FilterOutputStream
	{

	    public void write(int theByte)
	        throws IOException
	    {
	        if(suspendEncoding)
	        {
	            super.out.write(theByte);
	            return;
	        }
	        if(encode)
	        {
	            buffer[position++] = (byte)theByte;
	            if(position >= bufferLength)
	            {
	                out.write(Base64.encode3to4(b4, buffer, bufferLength));
	                lineLength += 4;
	                if(breakLines && lineLength >= 76)
	                {
	                    out.write(10);
	                    lineLength = 0;
	                }
	                position = 0;
	            }
	        }else
	            if(Base64.access$100()[theByte & 0x7f] > -5)
	            {
	                buffer[position++] = (byte)theByte;
	                if(position >= bufferLength)
	                {
	                    int len = Base64.access$200(buffer, 0, b4, 0);
	                    out.write(b4, 0, len);
	                    position = 0;
	                }
	            } else
	            if(Base64.access$100()[theByte & 0x7f] != -5)
	                throw new IOException("Invalid character in Base64 data.");
	        }

	    public void write(byte theBytes[], int off, int len)
	        throws IOException
	    {
	        if(suspendEncoding)
	        {
	            super.out.write(theBytes, off, len);
	            return;
	        }
	        for(int i = 0; i < len; i++)
	            write(theBytes[off + i]);

	    }

	    public void flushBase64()
	        throws IOException
	    {
	        if(position > 0)
	            if(encode)
	            {
	                out.write(Base64.encode3to4(b4, buffer, position));
	                position = 0;
	            } else
	            {
	                throw new IOException("Base64 input not properly padded.");
	            }
	    }

	    public void close()
	        throws IOException
	    {
	        flushBase64();
	        super.close();
	        buffer = null;
	        out = null;
	    }

	    public void suspendEncoding()
	        throws IOException
	    {
	        flushBase64();
	        suspendEncoding = true;
	    }

	    public void resumeEncoding()
	    {
	        suspendEncoding = false;
	    }

	    private int options;
	    private boolean encode;
	    private int position;
	    private byte buffer[];
	    private int bufferLength;
	    private int lineLength;
	    private boolean breakLines;
	    private byte b4[];
	    private boolean suspendEncoding;

	    public OutputStream(java.io.OutputStream out)
	    {
	        this(out, 1);
	    }

	    public OutputStream(java.io.OutputStream out, int options)
	    {
	        super(out);
	        this.options = options;
	        breakLines = (options & 8) != 8;
	        encode = (options & 1) == 1;
	        bufferLength = encode ? 3 : 4;
	        buffer = new byte[bufferLength];
	        position = 0;
	        lineLength = 0;
	        suspendEncoding = false;
	        b4 = new byte[4];
	    }
	}

	private static byte[] encode3to4(byte[] threeBytes) {
		return encode3to4(threeBytes, 3);
	}

	private static byte[] encode3to4(byte[] threeBytes, int numSigBytes) {
		byte[] dest = new byte[4];
		encode3to4(threeBytes, 0, numSigBytes, dest, 0);
		return dest;
	}

	private static byte[] encode3to4(byte[] b4, byte[] threeBytes, int numSigBytes) {
		encode3to4(threeBytes, 0, numSigBytes, b4, 0);
		return b4;
	}

	private static byte[] encode3to4(byte[] source, int srcOffset, int numSigBytes, byte[] destination,
			int destOffset) {
		int inBuff = (numSigBytes > 0 ? source[srcOffset] << 24 >>> 8 : 0)
				| (numSigBytes > 1 ? source[srcOffset + 1] << 24 >>> 16 : 0)
				| (numSigBytes > 2 ? source[srcOffset + 2] << 24 >>> 24 : 0);
		switch (numSigBytes) {
			case 1 :
				destination[destOffset] = ALPHABET[inBuff >>> 18];
				destination[destOffset + 1] = ALPHABET[inBuff >>> 12 & 63];
				destination[destOffset + 2] = 61;
				destination[destOffset + 3] = 61;
				return destination;
			case 2 :
				destination[destOffset] = ALPHABET[inBuff >>> 18];
				destination[destOffset + 1] = ALPHABET[inBuff >>> 12 & 63];
				destination[destOffset + 2] = ALPHABET[inBuff >>> 6 & 63];
				destination[destOffset + 3] = 61;
				return destination;
			case 3 :
				destination[destOffset] = ALPHABET[inBuff >>> 18];
				destination[destOffset + 1] = ALPHABET[inBuff >>> 12 & 63];
				destination[destOffset + 2] = ALPHABET[inBuff >>> 6 & 63];
				destination[destOffset + 3] = ALPHABET[inBuff & 63];
				return destination;
			default :
				return destination;
		}
	}

	public static String encodeObject(Serializable serializableObject) {
		return encodeObject(serializableObject, 0);
	}

	public static String encodeObject(Serializable serializableObject, int options) {
		
		ByteArrayOutputStream baos = null;
		OutputStream b64os = null;
		ObjectOutputStream oos = null;
		GZIPOutputStream gzos = null;
		int gzip = options & 2;
		int dontBreakLines = options & 8;

		label153 : {
			Object var9;
			try {
				baos = new ByteArrayOutputStream();
				b64os = new OutputStream(baos, 1 | dontBreakLines);
				if (gzip == 2) {
					gzos = new GZIPOutputStream(b64os);
					oos = new ObjectOutputStream(gzos);
				} else {
					oos = new ObjectOutputStream(b64os);
				}

				oos.writeObject(serializableObject);
				break label153;
			} catch (IOException var33) {
				var33.printStackTrace();
				var9 = null;
			} finally {
				try {
					oos.close();
				} catch (Exception var31) {
					;
				}

				try {
					gzos.close();
				} catch (Exception var30) {
					;
				}

				try {
					b64os.close();
				} catch (Exception var29) {
					;
				}

				try {
					baos.close();
				} catch (Exception var28) {
					;
				}

			}

			return (String) var9;
		}

		try {
			return new String(baos.toByteArray(), "UTF-8");
		} catch (UnsupportedEncodingException var32) {
			return new String(baos.toByteArray());
		}
	}

	public static String encodeBytes(byte[] source) {
		return encodeBytes(source, 0, source.length, 0);
	}

	public static String encodeBytes(byte[] source, int options) {
		return encodeBytes(source, 0, source.length, options);
	}

	public static String encodeBytes(byte[] source, int off, int len) {
		return encodeBytes(source, off, len, 0);
	}

	public static String encodeBytes(byte[] source, int off, int len, int options) {
		int dontBreakLines = options & 8;
		int gzip = options & 2;
		if (gzip == 2) {
			ByteArrayOutputStream baos = null;
			GZIPOutputStream gzos = null;
			Base64.OutputStream b64os = null;

			label199 : {
				Object var37;
				try {
					baos = new ByteArrayOutputStream();
					b64os = new Base64.OutputStream(baos, 1 | dontBreakLines);
					gzos = new GZIPOutputStream(b64os);
					gzos.write(source, off, len);
					gzos.close();
					break label199;
				} catch (IOException var32) {
					var32.printStackTrace();
					var37 = null;
				} finally {
					try {
						gzos.close();
					} catch (Exception var29) {
						;
					}

					try {
						b64os.close();
					} catch (Exception var28) {
						;
					}

					try {
						baos.close();
					} catch (Exception var27) {
						;
					}

				}

				return (String) var37;
			}

			try {
				return new String(baos.toByteArray(), "UTF-8");
			} catch (UnsupportedEncodingException var30) {
				return new String(baos.toByteArray());
			}
		} else {
			boolean breakLines = dontBreakLines == 0;
			int len43 = len * 4 / 3;
			byte[] outBuff = new byte[len43 + (len % 3 > 0 ? 4 : 0) + (breakLines ? len43 / 76 : 0)];
			int d = 0;
			int e = 0;
			int len2 = len - 2;

			for (int lineLength = 0; d < len2; e += 4) {
				encode3to4(source, d + off, 3, outBuff, e);
				lineLength += 4;
				if (breakLines && lineLength == 76) {
					outBuff[e + 4] = 10;
					++e;
					lineLength = 0;
				}

				d += 3;
			}

			if (d < len) {
				encode3to4(source, d + off, len - d, outBuff, e);
				e += 4;
			}

			try {
				return new String(outBuff, 0, e, "UTF-8");
			} catch (UnsupportedEncodingException var31) {
				return new String(outBuff, 0, e);
			}
		}
	}

	private static byte[] decode4to3(byte[] fourBytes) {
		byte[] outBuff1 = new byte[3];
		int count = decode4to3(fourBytes, 0, outBuff1, 0);
		byte[] outBuff2 = new byte[count];

		for (int i = 0; i < count; ++i) {
			outBuff2[i] = outBuff1[i];
		}

		return outBuff2;
	}

	private static int decode4to3(byte[] source, int srcOffset, byte[] destination, int destOffset) {
		int outBuff;
		if (source[srcOffset + 2] == 61) {
			outBuff = (DECODABET[source[srcOffset]] & 255) << 18 | (DECODABET[source[srcOffset + 1]] & 255) << 12;
			destination[destOffset] = (byte) (outBuff >>> 16);
			return 1;
		} else if (source[srcOffset + 3] == 61) {
			outBuff = (DECODABET[source[srcOffset]] & 255) << 18 | (DECODABET[source[srcOffset + 1]] & 255) << 12
					| (DECODABET[source[srcOffset + 2]] & 255) << 6;
			destination[destOffset] = (byte) (outBuff >>> 16);
			destination[destOffset + 1] = (byte) (outBuff >>> 8);
			return 2;
		} else {
			try {
				outBuff = (DECODABET[source[srcOffset]] & 255) << 18 | (DECODABET[source[srcOffset + 1]] & 255) << 12
						| (DECODABET[source[srcOffset + 2]] & 255) << 6 | DECODABET[source[srcOffset + 3]] & 255;
				destination[destOffset] = (byte) (outBuff >> 16);
				destination[destOffset + 1] = (byte) (outBuff >> 8);
				destination[destOffset + 2] = (byte) outBuff;
				return 3;
			} catch (Exception var5) {
				System.out.println("" + source[srcOffset] + ": " + DECODABET[source[srcOffset]]);
				System.out.println("" + source[srcOffset + 1] + ": " + DECODABET[source[srcOffset + 1]]);
				System.out.println("" + source[srcOffset + 2] + ": " + DECODABET[source[srcOffset + 2]]);
				System.out.println("" + source[srcOffset + 3] + ": " + DECODABET[source[srcOffset + 3]]);
				return -1;
			}
		}
	}

	public static byte[] decode(byte[] source, int off, int len) {
		int len34 = len * 3 / 4;
		byte[] outBuff = new byte[len34];
		int outBuffPosn = 0;
		byte[] b4 = new byte[4];
		int b4Posn = 0;

		for (int i = off; i < off + len; ++i) {
			byte sbiCrop = (byte) (source[i] & 127);
			byte sbiDecode = DECODABET[sbiCrop];
			if (sbiDecode < -5) {
				System.err.println("Bad Base64 input character at " + i + ": " + source[i] + "(decimal)");
				return null;
			}

			if (sbiDecode >= -1) {
				b4[b4Posn++] = sbiCrop;
				if (b4Posn > 3) {
					outBuffPosn += decode4to3(b4, 0, outBuff, outBuffPosn);
					b4Posn = 0;
					if (sbiCrop == 61) {
						break;
					}
				}
			}
		}

		byte[] out = new byte[outBuffPosn];
		System.arraycopy(outBuff, 0, out, 0, outBuffPosn);
		return out;
	}

	public static byte[] decode(String s) {
		byte[] bytes;
		try {
			bytes = s.getBytes("UTF-8");
		} catch (UnsupportedEncodingException var27) {
			bytes = s.getBytes();
		}

		bytes = decode(bytes, 0, bytes.length);
		if (bytes.length >= 2) {
			int head = bytes[0] & 255 | bytes[1] << 8 & '＀';
			if (bytes != null && bytes.length >= 4 && 35615 == head) {
				ByteArrayInputStream bais = null;
				GZIPInputStream gzis = null;
				ByteArrayOutputStream baos = null;
				byte[] buffer = new byte[2048];
				boolean var7 = false;

				try {
					baos = new ByteArrayOutputStream();
					bais = new ByteArrayInputStream(bytes);
					gzis = new GZIPInputStream(bais);

					int length;
					while ((length = gzis.read(buffer)) >= 0) {
						baos.write(buffer, 0, length);
					}

					bytes = baos.toByteArray();
				} catch (IOException var28) {
					;
				} finally {
					try {
						baos.close();
					} catch (Exception var26) {
						;
					}

					try {
						gzis.close();
					} catch (Exception var25) {
						;
					}

					try {
						bais.close();
					} catch (Exception var24) {
						;
					}

				}
			}
		}

		return bytes;
	}

	public static Object decodeToObject(String encodedObject) {
		byte[] objBytes = decode(encodedObject);
		ByteArrayInputStream bais = null;
		ObjectInputStream ois = null;
		Object obj = null;

		try {
			bais = new ByteArrayInputStream(objBytes);
			ois = new ObjectInputStream(bais);
			obj = ois.readObject();
		} catch (IOException var21) {
			var21.printStackTrace();
			obj = null;
		} catch (ClassNotFoundException var22) {
			var22.printStackTrace();
			obj = null;
		} finally {
			try {
				bais.close();
			} catch (Exception var20) {
				;
			}

			try {
				ois.close();
			} catch (Exception var19) {
				;
			}

		}

		return obj;
	}

	static {
		byte[] __bytes;
		try {
			__bytes = (new String("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-/"))
					.getBytes("UTF-8");
		} catch (UnsupportedEncodingException var2) {
			__bytes = _NATIVE_ALPHABET;
		}

		ALPHABET = __bytes;
		DECODABET = new byte[]{-9, -9, -9, -9, -9, -9, -9, -9, -9, -5, -5, -9, -9, -5, -9, -9, -9, -9, -9, -9, -9, -9,
				-9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -5, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, 62, -9, 63,
				52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -9, -9, -9, -1, -9, -9, -9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10,
				11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -9, -9, -9, -9, -9, -9, 26, 27, 28, 29, 30,
				31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -9, -9, -9, -9};
	}
}