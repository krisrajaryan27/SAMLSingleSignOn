/**
 * 
 */
package com.talentPool.common.utils.Exception;

/**
 * @author Sachinm
 *
 */
public class PathTraversalException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Creates a new instance of <code>PathTraversalException</code> without detail message.
	 */
	public PathTraversalException() {
	}

	/**
	 * Constructs an instance of <code>PathTraversalException</code> with the specified detail message.
	 * 
	 * @param msg
	 *            the detail message.
	 */
	public PathTraversalException(String msg) {
		super(msg);
	}

}
