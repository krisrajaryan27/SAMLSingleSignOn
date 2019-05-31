/**
 * 
 */
package com.talentPool.positions.exception;

/**
 * @author pallavi
 *
 */
public class PositionParserException extends Exception {

	/**
	 * 
	 */
	public PositionParserException() {
			}

	/**
	 * @param arg0
	 */
	public PositionParserException(String arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 */
	public PositionParserException(Throwable arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public PositionParserException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

}
