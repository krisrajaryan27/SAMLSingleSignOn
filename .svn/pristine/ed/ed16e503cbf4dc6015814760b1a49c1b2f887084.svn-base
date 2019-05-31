/**
 * 
 */
package com.talentPool.desktop.exceptions;

import java.util.ArrayList;

import com.talentPool.applicant.dataobject.ApplicantDuplicateSearchData;


/**
 * @author shivprasad
 * 
 */
public class DuplicateExistsException extends Exception {
	ArrayList<ApplicantDuplicateSearchData> duplicates;

	public DuplicateExistsException() {
	}

	public DuplicateExistsException(String msg) {
		super(msg);
	}

	public DuplicateExistsException(ArrayList<ApplicantDuplicateSearchData> duplicates) {
		this.duplicates = duplicates;
	}

	public ArrayList<ApplicantDuplicateSearchData> getDuplicates() {
		return duplicates;
	}
}
