/**
 * 
 */
package com.talentPool.notifier.dataobject;

import java.sql.Date;

import com.talentPool.common.db.SimpleDataObject;
import com.talentPool.custom.utils.CustomFieldUtils;

/**
 * @author shivprasad
 * 
 */
public class TemplateData extends SimpleDataObject {
	/**
	 * @return the createdBy
	 */
	public String getUserId() {
		return getString("userId");
	}

	/**
	 * @param createdBy
	 *            the createdBy to set
	 */
	public void setUserId(String userId) {
		setAttribute("userId", userId);
	}

	/**
	 * @return the dateCreated
	 */
	public Date getDateCreated() {
		return getDate("dateCreated");
	}

	/**
	 * @param dateCreated
	 *            the dateCreated to set
	 */
	public void setDateCreated(Date dateCreated) {
		setAttribute("dateCreated", dateCreated);
	}

	/**
	 * @return the templateAuto
	 */
	public String getTemplateAuto() {
		return getString("templateAuto");
	}

	/**
	 * @param templateAuto
	 *            the templateAuto to set
	 */
	public void setTemplateAuto(String templateAuto) {
		setAttribute("templateAuto", templateAuto);
	}

	/**
	 * @return the templateCode
	 */
	public String getTemplateCode() {
		return getString("templateCode");
	}

	/**
	 * @param templateCode
	 *            the templateCode to set
	 */
	public void setTemplateCode(String templateCode) {
		setAttribute("templateCode", templateCode);
	}
	/**
	 * @return the templateContentFile
	 */
	public String getTemplateContentFile() {
		return getString("templateContentFile");
	}

	/**
	 * @param templateContentFile
	 *            the templateContentFile to set
	 */
	public void setTemplateContentFile(String templateContentFile) {
		setAttribute("templateContentFile", templateContentFile);
	}

	/**
	 * @return the templateId
	 */
	public String getTemplateId() {
		return getString("templateId");
	}

	/**
	 * @param templateId
	 *            the templateId to set
	 */
	public void setTemplateId(String templateId) {
		setAttribute("templateId", templateId);
	}

	/**
	 * @return the templateName
	 */
	public String getTemplateName() {
		return getString("templateName");
	}

	/**
	 * @param templateName
	 *            the templateName to set
	 */
	public void setTemplateName(String templateName) {
		setAttribute("templateName", templateName);
	}

	/**
	 * @return the templateSubjectFile
	 */
	public String getTemplateSubjectFile() {
		return getString("templateSubjectFile");
	}

	/**
	 * @param templateSubjectFile
	 *            the templateSubjectFile to set
	 */
	public void setTemplateSubjectFile(String templateSubjectFile) {
		setAttribute("templateSubjectFile", templateSubjectFile);
	}

	/**
	 * @return the templateVariables
	 */
	public String getTemplateVariables() {
		// return getString("templateVariables");
		return CustomFieldUtils.appendCustomFieldVariables(getTemplateVariableIds(),getString("templateVariables"));
	}

	/**
	 * @param templateVariables
	 *            the templateVariables to set
	 */
	public void setTemplateVariables(String templateVariables) {
		setAttribute("templateVariables", templateVariables);
	}

	public String getTemplateType() {
		return getString("templateType");
	}

	public void setTemplateType(String templateType) {
		setAttribute("templateType", templateType);
	}

	public String getTemplateFormat() {
		return getString("templateFormat");
	}

	public void setTemplateFormat(String templateFormat) {
		setAttribute("templateFormat", templateFormat);
	}

	public String getTemplatePrivate() {
		return getString("templatePrivate");
	}

	public void setTemplatePrivate(String templatePrivate) {
		setAttribute("templatePrivate", templatePrivate);
	}

	public void setTemplateSubjectText(String templateSubjectText) {
		setAttribute("templateSubjectText", templateSubjectText);
	}

	public String getTemplateSubjectText() {
		return getString("templateSubjectText");
	}

	public void setTemplateContentText(String templateContentText) {
		setAttribute("templateContentText", templateContentText);
	}

	public String getTemplateContentText() {
		return getString("templateContentText");
	}

	/**
	 * @return the templateTypeId
	 */
	public String getTemplateTypeId() {
		return getString("templateTypeId");
	}

	/**
	 * @param templateTypeId 
	 * 			the templateTypeId to set
	 */
	public void setTemplateTypeId(String templateTypeId) {
		setAttribute("templateTypeId", templateTypeId);
	}

	/**
	 * @return the templateVariableIds
	 */
	public String getTemplateVariableIds() {
		return getString("templateVariableIds");
	}

	/**
	 * @param templateVariableIds 
	 * 			the templateVariableIds to set
	 */
	public void setTemplateVariableIds(String templateVariableIds) {
		setAttribute("templateVariableIds", templateVariableIds);
	}

	/**
	 * @return the isTemplateDefault
	 */
	public String getIsTemplateDefault() {
		return getString("isTemplateDefault");
	}

	/**
	 * @param isTemplateDefault the isTemplateDefault to set
	 */
	public void setIsTemplateDefault(String isTemplateDefault) {
		setAttribute("isTemplateDefault", isTemplateDefault);
	}

	public String getIsTemplateSaveAsDraft() {
		return getString("isTemplateSaveAsDraft");
	}

	public void setIsTemplateSaveAsDraft(String isTemplateSaveAsDraft) {
		setAttribute("isTemplateSaveAsDraft", isTemplateSaveAsDraft);
	}

	public String getDoShowSaveAsDraftOption() {
		return getString("doShowSaveAsDraftOption");
	}

	public void setDoShowSaveAsDraftOption(String doShowSaveAsDraftOption) {
		setAttribute("doShowSaveAsDraftOption", doShowSaveAsDraftOption);
	}
}
