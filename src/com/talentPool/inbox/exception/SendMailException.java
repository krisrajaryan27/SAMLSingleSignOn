/**
 * 
 */
package com.talentPool.inbox.exception;

/**
 * @author shivprasad
 *
 */
public class SendMailException  extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Creates a new instance of <code>SendMailException</code> without detail message.
	 */
	public SendMailException() {
	}

	/**
	 * Constructs an instance of <code>SendMailException</code> with the specified detail message.
	 * 
	 * @param msg
	 *            the detail message.
	 */
	public SendMailException(String msg) {
		super(msg);
	}
}
