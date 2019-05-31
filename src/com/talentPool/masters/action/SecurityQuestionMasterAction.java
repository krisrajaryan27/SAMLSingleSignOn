package com.talentPool.masters.action;

import java.util.ArrayList;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.db.SimpleDataObject;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.utils.Utils;
import com.talentPool.masters.manager.SecurityQuestionMasterManager;

/**
 * @author Sachinm
 *
 */
public class SecurityQuestionMasterAction extends ActionSupport {

	private static final long serialVersionUID = 1L;

	private String securityQuestion = null;
	private String securityQuestionId = null;
	private String updated = null;
	private SecurityQuestionMasterManager manager = null;

	/**
	 * @return the securityQuestion
	 */
	public String getSecurityQuestion() {
		return securityQuestion;
	}

	/**
	 * @param securityQuestion
	 *            the securityQuestion to set
	 */
	public void setSecurityQuestion(String securityQuestion) {
		this.securityQuestion = securityQuestion;
	}

	/**
	 * @return the securityQuestionId
	 */
	public String getSecurityQuestionId() {
		return securityQuestionId;
	}

	/**
	 * @param securityQuestionId
	 *            the securityQuestionId to set
	 */
	public void setSecurityQuestionId(String securityQuestionId) {
		this.securityQuestionId = securityQuestionId;
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

	/**
	 * @return "success"
	 * @throws Exception
	 */
	public String manageSecurityQuestions() throws Exception {
		return SUCCESS;
	}
	
	/**
	 * @return security question in XML format
	 * @throws Exception
	 */
	public String getSecurityQuestionXML() throws Exception {
		String xmlFile = "";
		try {
			manager = new SecurityQuestionMasterManager();
			ArrayList<SimpleDataObject> securityQuestions = manager.getSecurityQuestions();
			xmlFile = manager.getXMLForSecurityQuestions(securityQuestions);
			ServletActionContext.getRequest().setAttribute("xmlFile", xmlFile);
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}		
		return SUCCESS;
	}
	
	/**
	 * @return "success"
	 * @throws Exception
	 */
	public String addSecurityQuestion() throws Exception {
		return SUCCESS;
	}
	
	/**
	 * @return "success"
	 * @throws Exception
	 */
	public String editSecurityQuestion() throws Exception {
		try {
			manager = new SecurityQuestionMasterManager();
			if(!Utils.isBlankOrNull(getSecurityQuestionId())){
				SimpleDataObject sd = manager.getSecurityQuestion(getSecurityQuestionId());
				if(null!=sd){
					setSecurityQuestion((String)sd.getAttribute("securityQuestion"));
				}
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return SUCCESS;
	}

	/**
	 * @return "success"
	 * @throws Exception
	 */
	public String saveSecurityQuestion() throws Exception {
		try {
			manager = new SecurityQuestionMasterManager();
			if(!Utils.isBlankOrNull(getSecurityQuestionId())){
				//update
				manager.updateSecurityQuestion(getSecurityQuestionId(), getSecurityQuestion());
				setUpdated("1");
			}else if (!Utils.isBlankOrNull(getSecurityQuestion())) {
				//add
				manager.addSecurityQuestion(getSecurityQuestion());
				setUpdated("1");
			}			
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return SUCCESS;
	}
	
	/**
	 * @return "success"
	 * @throws Exception
	 */
	public String deleteSecurityQuestion() throws Exception {
		String xmlFile = "";
		try {
			manager = new SecurityQuestionMasterManager();
			if(!Utils.isBlankOrNull(getSecurityQuestionId())){
				manager.deleteSecurityQuestion(getSecurityQuestionId());
				xmlFile = Utils.getXMLForIds(getSecurityQuestionId());
			}else{
				xmlFile = Utils.getXMLForError(null);
			}
			ServletActionContext.getRequest().setAttribute("xmlFile", xmlFile);
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}		
		return SUCCESS;
	}

}
