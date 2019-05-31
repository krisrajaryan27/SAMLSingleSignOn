/**
 * Copyright 2009 - Nitman Software Pvt. Ltd. All Rights Reserved. 
 * This software is the proprietary information of Nitman Software Pvt. Ltd. 
 * Use is subject to license terms.
 */
package com.talentPool.offerSheet.factory;

import com.talentPool.common.utils.FileUtils.FileHandlerUtils;
import com.talentPool.offerSheet.OfferSheetConstants;
import com.talentPool.offerSheet.manager.ExcelOfferSheetGenerator;
import com.talentPool.offerSheet.manager.ExcelPDFOfferSheetGenerator;
import com.talentPool.offerSheet.manager.OfferSheetGenerator;
import com.talentPool.offerSheet.manager.WordOfferSheetGenerator;

/**
 * @author PraveenK
 * @since  Mar 27, 2012
 */
public class OfferSheetGeneratorFactory {
	
	/**
	 * Based Template format and output offer format OfferSheetGenerator will returned.
	 * @param templateFileName
	 * @param offerFormat
	 * @return SubClass of OfferSheetGenerator
	 * Null if mo criteria matches 
	 */
	public static OfferSheetGenerator getOfferSheetGenerator(String templateFileName, int offerFormat){
		if(FileHandlerUtils.isExcelDoc(templateFileName)){
			if(offerFormat==OfferSheetConstants.OFFER_FORMAT_PDF){
				return new ExcelPDFOfferSheetGenerator();
			}else{
				return new ExcelOfferSheetGenerator();
			}
		}else if(FileHandlerUtils.isWordDoc(templateFileName)){
			return new WordOfferSheetGenerator();
		}else{
			return null;
		}
	}
}
