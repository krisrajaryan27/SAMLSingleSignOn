/**
 * 
 */
package com.talentPool.customReports.djhelper.impl;

import net.sf.jasperreports.engine.JRException;
import ar.com.fdvs.dj.core.DynamicJasperHelper;
import ar.com.fdvs.dj.core.layout.LayoutManager;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.builders.ColumnBuilderException;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.customReports.djhelper.manager.TPCustomLayoutManager;
import com.talentPool.customReports.exception.DynamicReportGenerationException;
import com.talentPool.customReports.jaxb.CustomReport;

/**
 * @author PraveenK
 * @since  Dec 8, 2011
 */
public class DJHelper extends AbstarctDJHelper {

	/**
	 * @param reportType
	 */
	public DJHelper(CustomReport customReport) {
		super(customReport);
	}
	
	
	/**
	 * Generates {@link DynamicReport} using the reportDetails
	 * <li>Adds the report Details</li>
	 * <li>Builds and add the report columns</li>
	 * <li>Builds and add the report groups if any available</li>
	 * @return {@link DynamicReport} built using reportDetails
	 * @throws DynamicReportGenerationException
	 */
	public DynamicReport generateDynamicReport() throws DynamicReportGenerationException {
		TPLogger.getLogger().debug("Generating DynamicReport for reportId"+customReport.getReportId());
		try {
			addReportStyles();
			addReportDetails();
			addReportColumns();
		} catch (ColumnBuilderException cbe) {
			throw new DynamicReportGenerationException("Error in building columns for Dynamic Report:"+customReport.getReportId());
		}
		return frb.build();
	}

	/* (non-Javadoc)
	 * @see com.talentPool.customReports.service.IDJHelper#generateJRXML(com.talentPool.customReports.jaxb.CustomReport, java.lang.String)
	 */
	@Override
	public void generateJRXML(CustomReport customReport, String filePath) throws DynamicReportGenerationException, JRException {
		generateJRXML(customReport, filePath, new TPCustomLayoutManager());
	}

	/* (non-Javadoc)
	 * @see com.talentPool.customReports.service.IDJHelper#generateJRXML(com.talentPool.customReports.jaxb.CustomReport, java.lang.String, ar.com.fdvs.dj.core.layout.LayoutManager)
	 */
	@Override
	public void generateJRXML(CustomReport customReport, String filePath,
		LayoutManager layout) throws DynamicReportGenerationException, JRException {
		DynamicReport dynamicReport = generateDynamicReport(); 
		DynamicJasperHelper.generateJRXML(dynamicReport, layout, null, "UTF-8", filePath);
	}

}
