/**
 * 
 */
package com.talentPool.admin.dataobject;

import com.talentPool.common.db.SimpleDataObject;

/**
 * @author shivprasad
 * 
 */
public class DuplicateSettingsData extends SimpleDataObject {
	public DuplicateSettingsData(){
		
	}
	public DuplicateSettingsData(String fieldId, String fieldLabel, String fieldType, String checkFor) {
		setFieldId(fieldId);
		setFieldLabel(fieldLabel);
		setFieldType(fieldType);
		setCheckFor(checkFor);
	}
	public DuplicateSettingsData(String fieldId, String fieldLabel, String fieldType, String checkFor, String fieldCheckType) {
		setFieldId(fieldId);
		setFieldLabel(fieldLabel);
		setFieldType(fieldType);
		setCheckFor(checkFor);
		setFieldCheckType(fieldCheckType);
	}

	/**
	 * @return the fieldCheckType
	 */
	public String getFieldCheckType() {
		return getString("fieldCheckType");
	}

	/**
	 * @param fieldCheckType
	 *            the fieldCheckType to set
	 */
	public void setFieldCheckType(String fieldCheckType) {
		setAttribute("fieldCheckType", fieldCheckType);
	}

	/**
	 * @return the fieldId
	 */
	public String getFieldId() {
		return getString("fieldId");
	}

	/**
	 * @param fieldId
	 *            the fieldId to set
	 */
	public void setFieldId(String fieldId) {
		setAttribute("fieldId", fieldId);
	}

	/**
	 * @return the fieldLabel
	 */
	public String getFieldLabel() {
		return getString("fieldLabel");
	}

	/**
	 * @param fieldLabel
	 *            the fieldLabel to set
	 */
	public void setFieldLabel(String fieldLabel) {
		setAttribute("fieldLabel", fieldLabel);
	}

	/**
	 * @return the fieldType
	 */
	public String getFieldType() {
		return getString("fieldType");
	}

	/**
	 * @param fieldType
	 *            the fieldType to set
	 */
	public void setFieldType(String fieldType) {
		setAttribute("fieldType", fieldType);
	}

	/**
	 * @return
	 */
	public String getCheckFor() {
		return getString("checkFor");
	}

	/**
	 * @param checkFor
	 */
	public void setCheckFor(String checkFor) {
		setAttribute("checkFor", checkFor);
	}

}
