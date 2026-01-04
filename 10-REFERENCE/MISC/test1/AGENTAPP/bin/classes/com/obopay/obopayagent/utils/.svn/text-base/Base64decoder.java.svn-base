package com.obopay.obopayagent.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Base64 decoder.
 * @author 		<a href=mailto:roy@obopay.com>Roy Chen</a>
 */
public class Base64decoder {
    static final char[] charTab =
            "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/"
            .toCharArray();
    
    static int decode(char c) {
        
        if (Character.isUpperCase(c))
            return ((int) c) - 65;
        else if (Character.isLowerCase(c))
            return ((int) c) - 97 + 26;
        else if (Character.isDigit(c))
            return ((int) c) - 48 + 26 + 26;
        else
            switch (c) {
                case '+' :
                    return 62;
                case '/' :
                    return 63;
                case '=' :
                    return 0;
                default :
                    throw new RuntimeException(
                            "unexpected code: " + c);
            }
    }
    
    /** Decodes the given Base64 encoded String to a new byte array.
     * The byte array holding the decoded data is returned.
     * @param s						The base-64 string to be decoded.
     * @return 						The decoded byte array.
     */
    public static byte[] decode(String s) {
        
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            
            decode(s, bos);
            
        } catch (IOException e) {
            throw new RuntimeException();
        }
        return bos.toByteArray();
    }
    
    /** Decodes the given Base64 encoded String to a new byte array.
     * The byte array holding the decoded data is returned.
     * @param s						The base-64 string to be decoded.
     * @param os					The <code>OutputStream</code> to store the decoded value.
     */
    public static void decode(String s, OutputStream os)
    throws IOException {
        int i = 0;
        
        int len = s.length();
        
        while (true) {
            while (i < len && s.charAt(i) <= ' ')
                i++;
            
            if (i == len)
                break;
            
            int tri =
                    (decode(s.charAt(i)) << 18)
                    + (decode(s.charAt(i + 1)) << 12)
                    + (decode(s.charAt(i + 2)) << 6)
                    + (decode(s.charAt(i + 3)));
            
            
            os.write((tri >> 16) & 255);
            if (s.charAt(i + 2) == '=')
                break;
            os.write((tri >> 8) & 255);
            if (s.charAt(i + 3) == '=')
                break;
            os.write(tri & 255);
            
            i += 4;
        }
    }
}
