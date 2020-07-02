/**
 * 
 */
package com.mango.misc;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.easymock.EasyMock;
/**
 * @author somasundaram
 *
 */
public class HelloTest {

	private Hello hello = null;
	private Recorder recorder = null;
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		hello = new HelloWorld();
		//recorder = new FileRecorder();
		recorder = EasyMock.createMock(Recorder.class);
		hello.setRecorder(recorder);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		hello = null;
	}

	/**
	 * Test method for {@link com.mango.misc.Hello#say(java.lang.String)}.
	 */
	@Test
	public void testSayString() {
		EasyMock.replay(recorder);
		String result = hello.say("Obopay");
		assertEquals("Hello Obopay", result);
	}

	/**
	 * Test method for {@link com.mango.misc.Hello#say()}.
	 */
	@Test
	public void testSay() {
		String result = hello.say();
		assertEquals("Hello World", result);
	}
}
