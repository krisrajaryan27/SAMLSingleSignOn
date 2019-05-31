/**
 * Copyright 2009 - Nitman Software Pvt. Ltd. All Rights Reserved. 
 * This software is the proprietary information of Nitman Software Pvt. Ltd. 
 * Use is subject to license terms.
 */
package com.talentPool.customReports.djhelper.impl;

import java.util.ArrayList;
import java.util.List;

import net.sf.jasperreports.engine.JRException;
import ar.com.fdvs.dj.core.DynamicJasperHelper;
import ar.com.fdvs.dj.core.layout.LayoutManager;
import ar.com.fdvs.dj.domain.DJCalculation;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.builders.ColumnBuilderException;
import ar.com.fdvs.dj.domain.entities.DJGroup;
import ar.com.fdvs.dj.domain.entities.DJGroupVariable;
import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;
import ar.com.fdvs.dj.domain.entities.columns.PropertyColumn;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.properties.TPLabels;
import com.talentPool.common.utils.Utils;
import com.talentPool.customReports.constants.CRColumnValueConstants;
import com.talentPool.customReports.djhelper.manager.TPCustomLayoutManager;
import com.talentPool.customReports.djhelper.utils.DJUtils;
import com.talentPool.customReports.exception.DynamicReportGenerationException;
import com.talentPool.customReports.jaxb.CustomReport;
import com.talentPool.customReports.jaxb.Field;
import com.talentPool.customReports.queryBuilder.utils.QueryBuilderUtils;

/**
 * @author PraveenK
 * @since  Dec 20, 2011
 */
public class RCDJHelper extends AbstarctDJHelper {

	/**
	 * @param customReport
	 */
	public RCDJHelper(CustomReport customReport) {
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
			List<Field> fields = new ArrayList<Field>();
			fields.addAll(customReport.getRows());
			fields.addAll(customReport.getColumns());
			addReportColumns(fields);
			addGrouping();
		} catch (ColumnBuilderException cbe) {
			throw new DynamicReportGenerationException("Error in building columns for Dynamic Report:"+customReport.getReportId());
		}
		return frb.build();
	}
	
	protected void addGrouping() throws ColumnBuilderException {
		List<Field> rows = customReport.getRows();
		List<Field> cols = customReport.getColumns();
		DJGroup djGroup = null;
		boolean haveGroupingFieldColumns = QueryBuilderUtils.haveGroupingFields(customReport.getColumns());
		int groupingCount = 0;
		if(!Utils.isListEmptyOrNull(rows)){
			if(haveGroupingFieldColumns){
				groupingCount = rows.size()-1; 
			}else {
				groupingCount = rows.size();
			}
			
			if(haveGroupingFieldColumns && customReport.isShowRowGrandTotal()){
				for (Field field : cols) {
					if(CRColumnValueConstants.COLUMN_VALUE_TYPE_SUM.equals(field.getFieldValueType())){
						AbstractColumn col =  DJUtils.getColumn(field.getKey(), frb.getColumns());
						if(col!=null){
							frb.addGlobalFooterVariable(col, DJCalculation.SUM);
							frb.setGrandTotalLegend(TPLabels.getLabel("custom_report_column.header.grand_total"));
						}
					}else if(CRColumnValueConstants.COLUMN_VALUE_TYPE_FIRST.equals(field.getFieldValueType())){
						AbstractColumn col =  DJUtils.getColumn(field.getKey(), frb.getColumns());
						if(col!=null){
							frb.addGlobalFooterVariable(new DJGroupVariable(col, DJCalculation.FIRST));
							frb.setGrandTotalLegend(TPLabels.getLabel("custom_report_column.header.grand_total"));
						}
					}
				}
			}
			
			
			for (int i = 0; i < groupingCount; i++) {
				djGroup = buildDJGroup((PropertyColumn)frb.getColumn(i));
				if(haveGroupingFieldColumns){
					if(rows.get(i).isShowSubTotal()){
						for (Field field : cols) {
							if(CRColumnValueConstants.COLUMN_VALUE_TYPE_SUM.equals(field.getFieldValueType())){
								AbstractColumn col =  DJUtils.getColumn(field.getKey(), frb.getColumns());
								if(col!=null){
									djGroup.addFooterVariable(new DJGroupVariable(col, DJCalculation.SUM));
								}
							}else if(CRColumnValueConstants.COLUMN_VALUE_TYPE_FIRST.equals(field.getFieldValueType())){
								AbstractColumn col =  DJUtils.getColumn(field.getKey(), frb.getColumns());
								if(col!=null){
									djGroup.addFooterVariable(new DJGroupVariable(col, DJCalculation.FIRST));
								}
							}
						}							
					}
				}
				frb.addGroup(djGroup);
			}
		}else{
			TPLogger.getLogger().debug("This Report does not have any columns to build");
		}
	}
	
	protected void addTotals(){
		
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
