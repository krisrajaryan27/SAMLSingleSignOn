/**
 * 
 */
package com.talentPool.offerSheet.manager;

import java.util.List;

import com.talentPool.offerSheet.dataobject.OfferSheetTemplateData;
import com.talentPool.offerSheet.dataobject.OfferSheetTemplateVariable;

/**
 * @author pallavi
 *
 */
public abstract class OfferSheetGenerator {
	public abstract void generateOfferSheet(OfferSheetTemplateData templateData, List<OfferSheetTemplateVariable> templateVariables, 
			String destinationPath) throws Exception;

	protected String getTemplateVariableValue(String templateVariable, List<OfferSheetTemplateVariable> templateVariables) {
		String value = "0";
		if(templateVariables != null && templateVariables.size() > 0) {
			for(int i = 0; i < templateVariables.size(); i++) {
				OfferSheetTemplateVariable variable = templateVariables.get(i);
				if(variable.getTemplateVariable().equals(templateVariable)) {
					value = variable.getTemplateVariableVal();
					break;
				}
			}
		}
		return value;
	}
}
