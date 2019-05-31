/**
 * 
 */
package com.talentPool.vendorservice.dataobject;

import java.util.ArrayList;

/**
 * @author shivprasad
 * 
 */
public class VerrorData {
	private ArrayList<String> errors;

	public VerrorData() {
		errors = new ArrayList<String>();
	}
	
	public void putError(String err){
		errors.add(err);
	}
	/**
	 * @return the errors
	 */
	public ArrayList<String> getErrors() {
		return errors;
	}

	/**
	 * @param errors
	 *            the errors to set
	 */
	public void setErrors(ArrayList<String> errors) {
		this.errors = errors;
	}

}
