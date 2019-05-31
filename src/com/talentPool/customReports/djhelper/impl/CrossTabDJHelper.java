package com.talentPool.customReports.djhelper.impl;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.JRException;
import ar.com.fdvs.dj.core.DynamicJasperHelper;
import ar.com.fdvs.dj.core.layout.LayoutManager;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.builders.ColumnBuilderException;
import ar.com.fdvs.dj.domain.constants.Page;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.utils.Utils;
import com.talentPool.customReports.djhelper.constants.DJHelperConstants;
import com.talentPool.customReports.djhelper.manager.CrossTabLayoutManager;
import com.talentPool.customReports.exception.DynamicReportGenerationException;
import com.talentPool.customReports.jaxb.CustomReport;
import com.talentPool.customReports.jaxb.Field;

/**
 * @author PraveenK
 * @since  Dec 8, 2011
 */
public class CrossTabDJHelper extends AbstarctDJHelper {

	/**
	 * @param reportType
	 */
	public CrossTabDJHelper(CustomReport customReport) {
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
			addCrossTab();
			setCrossTabParameters(parameters);
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
		generateJRXML(customReport, filePath, new CrossTabLayoutManager(parameters));
	}

	/* (non-Javadoc)
	 * @see com.talentPool.customReports.service.IDJHelper#generateJRXML(com.talentPool.customReports.jaxb.CustomReport, java.lang.String, ar.com.fdvs.dj.core.layout.LayoutManager)
	 */
	@Override
	public void generateJRXML(CustomReport customReport, String filePath,
								LayoutManager layout) throws DynamicReportGenerationException,
								JRException {
		DynamicReport dynamicReport = generateDynamicReport(); 
		DynamicJasperHelper.generateJRXML(dynamicReport, layout, parameters, "UTF-8", filePath);
	}
	
	protected void addReportDetails(){
		super.addReportDetails();
		frb.setPageSizeAndOrientation(new Page(3000, 4000, true))
		.setUseFullPageWidth(true)
	    .setPrintColumnNames(false);
	}
	
	protected void setCrossTabParameters(Map<String, String> parameters){
		List<Field> measures =  customReport.getMeasures();
		if(!Utils.isListEmptyOrNull(measures)){
			int i = 0;
			for ( Field col : measures) {
				parameters.put("measure_label_"+i, col.getDisplayName());
				frb.addParameter("measure_label_"+i, String.class.getName());
				i++;
			}	
		}
		List<Field> columns =  customReport.getColumns();
		if(!Utils.isListEmptyOrNull(columns)){
			for ( Field col : columns) {
				if(DJHelperConstants.DJ_COMPARATORS_MAP.containsKey(col.getDataType())){
					frb.addParameter(DJHelperConstants.DJ_COMPARATORS_MAP.get(col.getDataType()), Comparator.class.getName());
				}
			}	
		}
		
		List<Field> rows =  customReport.getRows();
		if(!Utils.isListEmptyOrNull(rows)){
			for ( Field col : rows) {
				if(DJHelperConstants.DJ_COMPARATORS_MAP.containsKey(col.getDataType())){
					frb.addParameter(DJHelperConstants.DJ_COMPARATORS_MAP.get(col.getDataType()), Comparator.class.getName());
				}
			}	
		}
	}
	
}
