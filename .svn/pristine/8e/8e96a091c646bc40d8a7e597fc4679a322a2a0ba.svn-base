/**
 * Copyright 2009 - Nitman Software Pvt. Ltd. All Rights Reserved. 
 * This software is the proprietary information of Nitman Software Pvt. Ltd. 
 * Use is subject to license terms.
 */
package com.talentPool.customReports.djhelper.impl;

import java.util.List;

import ar.com.fdvs.dj.core.DJConstants;
import ar.com.fdvs.dj.domain.ColumnProperty;
import ar.com.fdvs.dj.domain.DJCalculation;
import ar.com.fdvs.dj.domain.DJCrosstab;
import ar.com.fdvs.dj.domain.DJCrosstabColumn;
import ar.com.fdvs.dj.domain.DJCrosstabMeasure;
import ar.com.fdvs.dj.domain.DJCrosstabRow;
import ar.com.fdvs.dj.domain.builders.CrosstabBuilder;
import ar.com.fdvs.dj.domain.builders.CrosstabRowBuilder;
import ar.com.fdvs.dj.domain.constants.Border;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.properties.TPLabels;
import com.talentPool.common.utils.Utils;
import com.talentPool.customReports.constants.CRColumnValueConstants;
import com.talentPool.customReports.djhelper.ICrossTabBuilder;
import com.talentPool.customReports.djhelper.djCustomImpl.totalProvider.BlankTotalProvider;
import com.talentPool.customReports.djhelper.djCustomImpl.valueFormatter.DefaultMeasureValueFormatter;
import com.talentPool.customReports.jaxb.CandNamesSpecifications;
import com.talentPool.customReports.jaxb.CustomReport;
import com.talentPool.customReports.jaxb.Field;
import com.talentPool.dynamicReports.utils.DJStyles;

/**
 * @author PraveenK
 * @since  Dec 9, 2011
 */
public class CrossTabBuilder implements ICrossTabBuilder {
	
	CrosstabBuilder ctb 			= null;
	CustomReport customReport 	= null;
	
	/**
	 * @param customReport
	 */
	public CrossTabBuilder(final CustomReport customReport){
		this.customReport=customReport;
		this.ctb = new CrosstabBuilder();
	}

	/* (non-Javadoc)
	 * @see com.talentPool.customReports.djhelper.ICrossTabBuilder#buildDJCrossTab()
	 */
	@Override
	public DJCrosstab buildDJCrossTab() {
		TPLogger.getLogger().debug("Building the cross tab for report "+customReport.getReportId());
		addCTDetailsAndStyles();
		addCTRows();
		addCTColumns();
		addCTMeasures();
		return ctb.build();
	}
	
	/**
	 * 
	 */
	protected void addCTDetailsAndStyles(){
		ctb.setHeight(200)
        .setDatasource("sr",DJConstants.DATA_SOURCE_ORIGIN_PARAMETER, DJConstants.DATA_SOURCE_TYPE_JRDATASOURCE)
        .setColorScheme(DJConstants.COLOR_SCHEMA_LIGHT_GREEN)
        .setAutomaticTitle(true)
        .setCellBorder(Border.THIN)
//        .setRowHeaderWidth(120)
        .setColumnHeaderHeight(35);
        setCellDimension();
	}
	
	/**
	 * Sets cell Dimension for crosstab
	 * Height of the cell is set to 15
	 * Width is set to 150 * no of cells when Show candidate names is selected
	 * Width is set to max(110, 50 * no of cells when Show candidate names is selected)
	 */
	private void setCellDimension(){
		CandNamesSpecifications cns = customReport.getCandNamesSpecifications();
		int size = customReport.getMeasures().size();
		if(cns!=null && cns.isShowNames()){
			ctb.setCellDimension(15, 150*size);
		}else{
			ctb.setCellDimension(15, Math.max(110, 50*size));
		}
	}
	
	
	/**
	 * 
	 */
	protected void addCTRows(){
		List<Field> rows = customReport.getRows();
		if(!Utils.isListEmptyOrNull(rows)){
			CandNamesSpecifications cns = customReport.getCandNamesSpecifications();
			for (int i = 0; i < rows.size(); i++) {
				Field row = rows.get(i);
				DJCrosstabRow djCrosstabRow = buildDJCrossTabRow(row); 
				if(cns==null || !cns.isShowNames()){
					if((i==0 && customReport.isShowRowGrandTotal()) || (i!=0 && rows.get(i-1).isShowSubTotal())){
						djCrosstabRow.setShowTotals(true);
						djCrosstabRow.setTotalLegend(TPLabels.getLabel("custom_report_column.header.total"));
					}
				} 
				ctb.addRow(djCrosstabRow);
			}
		}else {
			TPLogger.getLogger().debug("This Report does not have any rows to buld");
		}
	}
	
	/**
	 * 
	 */
	protected void addCTColumns(){
		List<Field> columns = customReport.getColumns();
		if(!Utils.isListEmptyOrNull(columns)){
			for(Field column : columns){
				ColumnProperty colProp = new ColumnProperty(column.getKey(),column.getDataType());
				DJCrosstabColumn dcc = new DJCrosstabColumn();
				dcc.setProperty(colProp);
				dcc.setTitle(column.getDisplayName());
				dcc.setShowTotals(false);
				ctb.addColumn(dcc);
			}
		}else {
			TPLogger.getLogger().debug("This Report does not have any rows to buld");
		}
	}
	
	/**
	 * 
	 */
	protected void addCTMeasures(){
		List<Field> measures = customReport.getMeasures();
		if(!Utils.isListEmptyOrNull(measures)){
			for(final Field measure : measures){
				ColumnProperty colMeasure =  new ColumnProperty();
				colMeasure.setProperty(measure.getKey());
				colMeasure.setValueClassName(measure.getDataType());
				DJCrosstabMeasure djctm = null;
				if(CRColumnValueConstants.COLUMN_VALUE_TYPE_SUM.equals(measure.getFieldValueType())){
					djctm =  new DJCrosstabMeasure(colMeasure, DJCalculation.SUM, measure.getDisplayName());					
				}else if(CRColumnValueConstants.COLUMN_VALUE_TYPE_FIRST.equals(measure.getFieldValueType())){
					djctm =  new DJCrosstabMeasure(colMeasure, DJCalculation.FIRST, measure.getDisplayName());
					djctm.setPrecalculatedTotalProvider(new BlankTotalProvider());
				}else {
					djctm =  new DJCrosstabMeasure(colMeasure, DJCalculation.NOTHING, measure.getDisplayName());
					djctm.setPrecalculatedTotalProvider(new BlankTotalProvider());
				}
				djctm.setValueFormatter(new DefaultMeasureValueFormatter());
				djctm.setStyle(DJStyles.getMeasureStyle());
				ctb.addMeasure(djctm);
			}
		}else{
			TPLogger.getLogger().debug("This Report does not have any measures to build");
		}
	}
	

	
	protected DJCrosstabRow buildDJCrossTabRow(Field row){
		DJCrosstabRow djCrosstabRow = new CrosstabRowBuilder()
		.setProperty(row.getKey(),row.getDataType())
		.setTitle(row.getDisplayName())
		.setHeaderWidth(row.getWitdh())
		.build();
		return djCrosstabRow;
	}
	
}
