/**
 * 
 */
package com.talentPool.applicant.form;

import org.apache.struts.upload.FormFile;

import com.talentPool.common.base.TPActionForm;

/**
 * @author shivprasad
 *
 */
public class ApplicantRegistrationForm extends TPActionForm {
private FormFile attachedFile;
	
	
	private String filePath;


	public FormFile getAttachedFile() {
		return attachedFile;
	}


	public void setAttachedFile(FormFile attachedFile) {
		this.attachedFile = attachedFile;
	}


	public String getFilePath() {
		return filePath;
	}


	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
}
