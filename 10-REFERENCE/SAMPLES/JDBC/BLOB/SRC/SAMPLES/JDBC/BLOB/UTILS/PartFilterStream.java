/**
  Copyright © 2002 Sun Microsystems, Inc. All rights reserved.
*/

package samples.jdbc.blob.utils;

import java.io.*;


public class PartFilterStream extends FilterInputStream
{
	/**
	 * Stores the maximum number of bytes that can be read from this stream
	 */
	protected int streamSize;

	/**
	 * Tracks the total number of bytes read from the stream.
	 */
	protected int bytesRead;

	/**
	 * The <code>InputStream</code> being limited
	 */
	protected InputStream in;

	/**
	 * Initialises the filter.
	 *
	 * @param in The InputStream being limited
	 * @param streamSize The total number of bytes that will be read from the stream.
	 */
	public PartFilterStream(InputStream in, int streamSize)
	{
		super(in);
		this.in = in;
		this.streamSize = streamSize;
		this.bytesRead = 0;
	}

	/**
	 * Returns a single byte if there are bytes available and the stream has not reached the limit.
	 *
	 * @return a single int from the stream, or -1 if the stream end or limit has been reached.
	 */
	public int read() throws IOException
	{
		if (bytesRead < streamSize)
		{
			int b;
			if ( (b = in.read()) != -1 )
				bytesRead++;
			return b;
		}
		else
		{
			return -1;
		}
	}

    /**
     * Populates the byte array passed to it and returns the total number of bytes read.
     *
     * @param b the byte array used to return the information to the caller.
     * @return int the number of bytes read into the array, or -1 if the stream end or limit has been reached.
     */
	public int read(byte[] b) throws IOException
	{
		return this.read(b, 0, b.length);
	}

    /**
     * Populates the byte array passed to it and returns the total number of bytes read.
     *
     * @param b the byte array used to return the information to the caller.
     * @param off the point in the array to start placing the read information.
     * @param len the total number of bytes that can be placed in the array.
     * @return int the number of bytes read into the array, or -1 if the stream end or limit has been reached.
     */
	public int read(byte[] b, int off, int len) throws IOException
	{
		if (bytesRead < streamSize)
		{
			int res;
			if ( len > (streamSize - bytesRead) )
			{
				if ( (res = in.read(b, off, streamSize-bytesRead)) != -1)
					bytesRead += res;
				return res;
			}
			else
			{
				if ( (res = in.read(b, off, len)) != -1)
					bytesRead += res;
				return res;
			}
		}
		else
		{
			return -1;
		}
	}

    /**
     * Returns the number of bytes that can be read from the stream without blocking.
     *
     * @return int the number of bytes that can be read from the stream without blocking.  The file stream
     *             limit is used to control the number of available bytes if more bytes are available from the
     *             underlying stream.
     */
	public int available() throws IOException
	{
		int a = in.available();
		if (a > (streamSize - bytesRead))
			return (streamSize - bytesRead);
		else
			return a;
	}

}
