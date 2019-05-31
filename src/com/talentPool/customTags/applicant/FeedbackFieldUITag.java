package com.talentPool.customTags.applicant;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.talentPool.applicant.dataobject.ApplicantData;
import com.talentPool.applicant.utils.ApplicantFieldUtils;
import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.utils.Utils;
import com.talentPool.user.manager.PermissionSet;

public class FeedbackFieldUITag extends TagSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String id = null;
	private String name = null;
	private String value = null;
	private String maxLength = null;
	private String fieldId = null;
	private Object data = null;
	private Object permission = null;
	private Map<String,String> attributes = null;

	public int doStartTag() throws JspException	{
		JspWriter out = null;
		ApplicantData aData = getApplicantData();
		String ui = null;
		try {
			out	= pageContext.getOut();
			if(!Utils.isBlankOrNull(getFieldId()) && aData!=null){
				setAttributes();
				ui = ApplicantFieldUtils.buildImportFieldUIForFeedbackForm(getFieldId(), aData, getAttributes(), getPermissionSet());
				out.println(ui);
			}
			release();
		} catch (Exception ioException) {
			TPLogger.getLogger().error(GlobalConstants.ERROR,ioException);
		}
		return SKIP_BODY;
	}
	
	 /**
     * Release all allocated resources.
     */
    public void release() {
        super.release();
        id = null;
    	value = null;
    	fieldId = null;
    	data = null;
    	permission = null;
    	attributes = null;
    }
    
    private void setAttributes(){
    	if(attributes==null){
    		attributes = new HashMap<String, String>();
    	}
    	attributes.put("id", getId());
    	if(getName()==null)
    		attributes.put("name", getId());
    	else 
    		attributes.put("name", getName());
    	attributes.put("value", getValue());
    	attributes.put("maxLength", getMaxLength());
    }

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * @return the fieldId
	 */
	public String getFieldId() {
		return fieldId;
	}

	/**
	 * @param fieldId the fieldId to set
	 */
	public void setFieldId(String fieldId) {
		this.fieldId = fieldId;
	}

	/**
	 * @return the data
	 */
	public Object getData() {
		return data;
	}
	
	/**
	 * @return the applicantData
	 */
	public ApplicantData getApplicantData() {
		Object aData = getData();
		if(aData instanceof ApplicantData)
			return (ApplicantData) aData;
		else
			return null;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(Object data) {
		this.data = data;
	}

	/**
	 * @return the permissionSet
	 */
	public PermissionSet getPermissionSet() {
		Object permissionSet = getPermission();
		if(permissionSet instanceof PermissionSet)
			return (PermissionSet) permissionSet;
		else
			return null;
	}

	/**
	 * @return the permission
	 */
	public Object getPermission() {
		return permission;
	}

	/**
	 * @param permission the permission to set
	 */
	public void setPermission(Object permission) {
		this.permission = permission;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the maxLength
	 */
	public String getMaxLength() {
		return maxLength;
	}

	/**
	 * @param maxLength the maxLength to set
	 */
	public void setMaxLength(String maxLength) {
		this.maxLength = maxLength;
	}

	/**
	 * @return the attributes
	 */
	public Map<String, String> getAttributes() {
		return attributes;
	}

	/**
	 * @param attributes the attributes to set
	 */
	public void setAttributes(Map<String, String> attributes) {
		this.attributes = attributes;
	}

}
