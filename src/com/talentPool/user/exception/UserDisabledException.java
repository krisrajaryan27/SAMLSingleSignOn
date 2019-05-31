package com.talentPool.user.exception;

/**
 * @author SiddharthK
 *
 */
public class UserDisabledException extends Exception {
	/** Creates a new instance of InvalidLoginException */
	public UserDisabledException() {
	}

	public UserDisabledException(String msg) {
		super(msg);
	}

}