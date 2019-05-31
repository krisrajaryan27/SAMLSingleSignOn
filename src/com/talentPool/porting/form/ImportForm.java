package com.talentPool.porting.form;

import java.util.List;

import org.apache.struts.upload.FormFile;

import com.talentPool.common.base.TPActionForm;
import com.talentPool.porting.dataobject.FailedStatusObject;

public class ImportForm extends TPActionForm{
	private String entityType;
	
	private FormFile attachedFile;
	
	private String option;
	
	private String filePath;
	
	private String sessionId;
	
	private String jsArrayMasterFields;
	
	private String mappings;
	
	private String isSessionComplete;
	
	private List<FailedStatusObject> failedObjects;
	
	private String failedStatusXML;

	public String getEntityType() {
		return entityType;
	}

	public void setEntityType(String entityType) {
		this.entityType = entityType;
	}

	public FormFile getAttachedFile() {
		return attachedFile;
	}

	public void setAttachedFile(FormFile attachedFile) {
		this.attachedFile = attachedFile;
	}

	public String getOption() {
		return option;
	}

	public void setOption(String option) {
		this.option = option;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getJsArrayMasterFields() {
		return jsArrayMasterFields;
	}

	public void setJsArrayMasterFields(String jsArrayMasterFields) {
		this.jsArrayMasterFields = jsArrayMasterFields;
	}

	public String getMappings() {
		return mappings;
	}

	public void setMappings(String mappings) {
		this.mappings = mappings;
	}

	public String getIsSessionComplete() {
		return isSessionComplete;
	}

	public void setIsSessionComplete(String isSessionComplete) {
		this.isSessionComplete = isSessionComplete;
	}

	public List<FailedStatusObject> getFailedObjects() {
		return failedObjects;
	}

	public void setFailedObjects(List<FailedStatusObject> failedObjects) {
		this.failedObjects = failedObjects;
	}

	public String getFailedStatusXML() {
		return failedStatusXML;
	}

	public void setFailedStatusXML(String failedStatusXML) {
		this.failedStatusXML = failedStatusXML;
	}	
	
	
	
}
