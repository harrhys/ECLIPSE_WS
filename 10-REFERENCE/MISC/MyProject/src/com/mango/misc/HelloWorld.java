/**
 * 
 */
package com.mango.misc;

/**
 * @author somasundaram
 *
 */
public class HelloWorld implements Hello {
	private final String address = new String("Hello ");
	private final String defaultPerson = new String("World");
	Recorder recorder;


	/* (non-Javadoc)
	 * @see com.mango.misc.Hello#say(java.lang.String)
	 */
	public String say(String to) {
		StringBuffer resultBuffer = new StringBuffer();
		resultBuffer.append(address);
		resultBuffer.append(to);
		recorder.record(resultBuffer.toString());
		return resultBuffer.toString();
	}

	/* (non-Javadoc)
	 * @see com.mango.misc.Hello#say()
	 */
	public String say() {
		StringBuffer resultBuffer = new StringBuffer();
		resultBuffer.append(address);
		resultBuffer.append(defaultPerson);
		recorder.record(resultBuffer.toString());
		return resultBuffer.toString();
	}
	public void setRecorder(Recorder recorder) {
		this.recorder = recorder;
	}	
}
