package com.talentPool.masters.action;

import java.util.ArrayList;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.db.SimpleDataObject;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.utils.Utils;

/**
 * @author Ajeet
 */
public class ResumeTypeMasterAction extends ActionSupport {

	private static final long serialVersionUID = 1L;

	private String resumeType = null;
	private String resumeTypeId = null;
	private String updated = null;
	private ResumeTypeMasterManager manager = null;

	/**
	 * @return the resumeType
	 */
	public String getResumeType() {
		return resumeType;
	}

	/**
	 * @param resumeType
	 *            the resumeType to set
	 */
	public void setResumeType(String resumeType) {
		this.resumeType = resumeType;
	}

	/**
	 * @return the resumeTypeId
	 */
	public String getResumeTypeId() {
		return resumeTypeId;
	}

	/**
	 * @param resumeTypeId
	 *            the resumeTypeId to set
	 */
	public void setResumeTypeId(String resumeTypeId) {
		this.resumeTypeId = resumeTypeId;
	}

	/**
	 * @return the updated
	 */
	public String getUpdated() {
		return updated;
	}

	/**
	 * @param updated
	 *            the updated to set
	 */
	public void setUpdated(String updated) {
		this.updated = updated;
	}

	public String manageResumeTypes() throws Exception {
		return SUCCESS;
	}
	
	public String getResumeTypeXML() throws Exception {
		String xmlFile = "";
		try {
			manager = new ResumeTypeMasterManager();
			ArrayList<SimpleDataObject> resumeTypes = manager.getResumeTypes();
			xmlFile = manager.getXMLForResumeTypes(resumeTypes);
			ServletActionContext.getRequest().setAttribute("xmlFile", xmlFile);
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
//			xmlFile = Utils.getXMLForError(errors);
		}
		
		return SUCCESS;
	}
	
	public String addResumeType() throws Exception {
		return SUCCESS;
	}
	
	public String editResumeType() throws Exception {
		try {
			manager = new ResumeTypeMasterManager();
			if(!Utils.isBlankOrNull(getResumeTypeId())){
				SimpleDataObject sd = manager.getResumeType(getResumeTypeId());
				if(null!=sd){
					setResumeType((String)sd.getAttribute("resumeType"));
				}
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return SUCCESS;
	}

	public String saveResumeType() throws Exception {
		try {
			manager = new ResumeTypeMasterManager();
			if(!Utils.isBlankOrNull(getResumeTypeId())){
				//update
				manager.updateResumeType(getResumeTypeId(), getResumeType());
				setUpdated("1");
			}else if (!Utils.isBlankOrNull(getResumeType())) {
				//add
				manager.addResumeType(getResumeType());
				setUpdated("1");
			}	 
			
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return SUCCESS;
	}
	
	public String deleteResumeType() throws Exception {
		String xmlFile = "";
		try {
			manager = new ResumeTypeMasterManager();
			if(!Utils.isBlankOrNull(getResumeTypeId())){
				manager.deleteResumeType(getResumeTypeId());
				xmlFile = Utils.getXMLForIds(getResumeTypeId());
			}else{
				xmlFile = Utils.getXMLForError(null);
			}
			ServletActionContext.getRequest().setAttribute("xmlFile", xmlFile);
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
//			xmlFile = Utils.getXMLForError(errors);
		}
		
		return SUCCESS;
	}

}
