/**
 * 
 */
package com.talentPool.user.exception;

/**
 * @author shivprasad
 * 
 */
public class InvalidLoginException extends Exception {
	/** Creates a new instance of InvalidLoginException */
	public InvalidLoginException() {
	}

	public InvalidLoginException(String msg) {
		super(msg);
	}

}