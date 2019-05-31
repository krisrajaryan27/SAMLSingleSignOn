/**
 * 
 */
package com.talentPool.offerSheet.manager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.utils.Utils;
import com.talentPool.documents.DocumentConstants;
import com.talentPool.offerSheet.dataobject.OfferSheetTemplateData;
import com.talentPool.offerSheet.dataobject.OfferSheetTemplateVariable;
import com.talentPool.offerSheet.utils.WordReplaceUtil;

/**
 * @author pallavi
 *
 */
public class WordOfferSheetGenerator extends OfferSheetGenerator {

	public void generateOfferSheet(OfferSheetTemplateData templateData,
			List<OfferSheetTemplateVariable> templateVariables,
			String destinationPath) throws IOException {
		String filePath = Utils.concatFilePath(DocumentConstants.documentsPath, templateData.getTemplateFilePath());
				
		Object[] findText = getArray(templateVariables, false);
		Object[] replaceText = getArray(templateVariables, true);
		
		WordReplaceUtil wordReplaceUtil = new WordReplaceUtil();
		wordReplaceUtil.replace(filePath, destinationPath, findText, replaceText);
	}

	private Object[] getArray(
			List<OfferSheetTemplateVariable> templateVariables, boolean getValue) {
		ArrayList<String> arrayList = new ArrayList<String>();
		if(templateVariables != null && templateVariables.size() > 0) {
			for(int i = 0; i < templateVariables.size(); i++) {
				if(!getValue) {
					arrayList.add(templateVariables.get(i).getTemplateVariable());
				} else {
//					arrayList.add(replaceComa(templateVariables.get(i).getTemplateVariableVal()));
					arrayList.add(templateVariables.get(i).getTemplateVariableVal());
				}				
			}
		}
		return arrayList.toArray();
	}
	
	private String replaceComa(String text){
		try {
			text = text.replaceAll(",", WordReplaceUtil.COMASEPARATOR);
		} catch (Exception e) {
			TPLogger.getLogger().error(e);
		}
		return text;
	}

}
