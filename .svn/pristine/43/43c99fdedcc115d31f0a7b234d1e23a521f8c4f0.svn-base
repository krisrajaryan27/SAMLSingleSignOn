package com.talentPool.customReports.service.impl;

import net.sf.jasperreports.engine.JRException;

import com.talentPool.customReports.djhelper.IDJHelper;
import com.talentPool.customReports.djhelper.factory.DJFactory;
import com.talentPool.customReports.exception.DynamicReportGenerationException;
import com.talentPool.customReports.jaxb.CustomReport;
import com.talentPool.customReports.service.ICRTemplateService;

/**
 * 
 * @author PraveenK
 * @since  Nov 7, 2011
 */
public class CRJasperTemplateService implements ICRTemplateService {

	@Override
	public void saveReportTemplateFile(CustomReport customReport, String filePath) throws Exception {
		try {
			IDJHelper djHelpr = DJFactory.getDJHelper(customReport);
			if(djHelpr!=null)
				djHelpr.generateJRXML(customReport, filePath);
			else
				throw new DynamicReportGenerationException();
		}catch(DynamicReportGenerationException drg){
			throw drg;
		} catch (JRException e) {
			throw e;
		}	
	}

	@Override
	public void editReportTemplateFile(CustomReport reportType) {
		// TODO Auto-generated method stub
	}

	@Override
	public void deleteReportTemplateFile() {
		// TODO Auto-generated method stub
	}
	
}
