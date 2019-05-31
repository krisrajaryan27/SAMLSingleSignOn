/**
 * 
 */
package com.talentPool.offerSheet.dataobject;

import com.talentPool.common.db.SimpleDataObject;
import com.talentPool.offerSheet.utils.OfferSheetUtils;

/**
 * @author pallavi
 *
 */
public class OfferSheetTemplateVariable extends SimpleDataObject {		
	/**
	 * @return the templateId
	 */
	public String getTemplateId() {
		return getString("templateId");
	}
	/**
	 * @param templateId the templateId to set
	 */
	public void setTemplateId(String templateId) {
		setAttribute("templateId", templateId);
	}
	/**
	 * @return the templateVariable
	 */
	public String getTemplateVariable() {
		return getString("templateVariable");
	}
	/**
	 * @param templateVariable the templateVariable to set
	 */
	public void setTemplateVariable(String templateVariable) {
		setAttribute("templateVariable", templateVariable);
	}
	/**
	 * @return the templateVariableVal
	 */
	public String getTemplateVariableVal() {
		return getString("templateVariableVal");
	}
	/**
	 * @param templateVariableVal the templateVariableVal to set
	 */
	public void setTemplateVariableVal(String templateVariableVal) {
		setAttribute("templateVariableVal", templateVariableVal);
	}
	/**
	 * @return the templateVariableAttribute
	 */
	public String getTemplateVariableAttribute() {
		return getString("templateVariableAttribute");
	}
	/**
	 * @param templateVariableAttribute the templateVariableAttribute to set
	 */
	public void setTemplateVariableAttribute(String templateVariableAttribute) {
		setAttribute("templateVariableAttribute", templateVariableAttribute);
	}
	/**
	 * @return the templateVariableDisplayName
	 */
	public String getTemplateVariableDisplayName() {
		String[] temp = getTemplateVariable().split(OfferSheetUtils.getUserDefinedVariableBoundary());
		if(temp.length > 1) {
			return temp[1];
		} else {
			return getTemplateVariable();
		}
	}
}
