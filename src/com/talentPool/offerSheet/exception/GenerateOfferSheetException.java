/**
 * 
 */
package com.talentPool.offerSheet.exception;

/**
 * Any failure in Offer sheet generation should throw this exception 
 * @author praveenk
 * @since  Mar 5, 2012
 */
public class GenerateOfferSheetException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public GenerateOfferSheetException() {
		super();
	}

	public GenerateOfferSheetException(String msg) {
		super(msg);
	}
}
