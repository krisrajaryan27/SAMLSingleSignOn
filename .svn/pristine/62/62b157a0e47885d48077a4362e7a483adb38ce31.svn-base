/**
 * Copyright 2009 - Nitman Software Pvt. Ltd. All Rights Reserved. 
 * This software is the proprietary information of Nitman Software Pvt. Ltd. 
 * Use is subject to license terms.
 */
package com.talentPool.offerSheet.manager;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.utils.FileUtils.FileHandlerUtils;
import com.talentPool.offerSheet.dataobject.OfferSheetTemplateData;
import com.talentPool.offerSheet.dataobject.OfferSheetTemplateVariable;
import com.talentPool.offerSheet.utils.ExcelToPDFConverterUtil;

/**
 * OfferSheet generator which will generate offer in PDF format for excel templates.
 * @author PraveenK
 * @since  Mar 28, 2012
 */
public class ExcelPDFOfferSheetGenerator extends ExcelOfferSheetGenerator {
	
	/* (non-Javadoc)
	 * @see com.talentPool.offerSheet.manager.ExcelOfferSheetGenerator#generateOfferSheet(com.talentPool.offerSheet.dataobject.OfferSheetTemplateData, java.util.List, java.lang.String)
	 */
	public void generateOfferSheet(OfferSheetTemplateData templateData, List<OfferSheetTemplateVariable> templateVariables, 
													String destinationPath) throws Exception {
		try {
		    File tempFile = File.createTempFile(templateData.getTemplateId()+System.currentTimeMillis(), FileHandlerUtils.getFileExtention(templateData.getTemplateFileName()));
		    tempFile.deleteOnExit(); // Delete temp file when program exits.
		    super.generateOfferSheet(templateData, templateVariables, tempFile.getAbsolutePath());
		    ExcelToPDFConverterUtil.convert(tempFile.getAbsolutePath(), destinationPath);
		}catch(IOException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
	}
}
