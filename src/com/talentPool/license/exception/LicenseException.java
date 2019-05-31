/**
 * 
 */
package com.talentPool.license.exception;

/**
 * @author pallavi
 * @date Jan 19, 2007
 */
public class LicenseException extends Exception {
	/**
	 * 
	 */
	public LicenseException() {
		
	}

	/**
	 * @param arg0
	 */
	public LicenseException(String arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 */
	public LicenseException(Throwable arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public LicenseException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}
}
