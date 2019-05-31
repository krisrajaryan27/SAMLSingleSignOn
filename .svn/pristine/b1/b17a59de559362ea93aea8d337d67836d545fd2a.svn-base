package com.talentPool.customReports.djhelper.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ar.com.fdvs.dj.domain.DJCrosstab;
import ar.com.fdvs.dj.domain.builders.ColumnBuilder;
import ar.com.fdvs.dj.domain.builders.ColumnBuilderException;
import ar.com.fdvs.dj.domain.builders.FastReportBuilder;
import ar.com.fdvs.dj.domain.builders.GroupBuilder;
import ar.com.fdvs.dj.domain.constants.GroupLayout;
import ar.com.fdvs.dj.domain.entities.DJGroup;
import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;
import ar.com.fdvs.dj.domain.entities.columns.PropertyColumn;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.properties.TPLabels;
import com.talentPool.common.utils.Utils;
import com.talentPool.customReports.djhelper.ICrossTabBuilder;
import com.talentPool.customReports.djhelper.IDJHelper;
import com.talentPool.customReports.djhelper.utils.DJUtils;
import com.talentPool.customReports.jaxb.CustomReport;
import com.talentPool.customReports.jaxb.Field;
import com.talentPool.dynamicReports.utils.DJStyles;

/**
 * @author PraveenK
 * @since  Nov 7, 2011
 */
public abstract class AbstarctDJHelper implements IDJHelper {
	protected CustomReport customReport	= null;
	protected FastReportBuilder frb	= null;
	Map<String, String> parameters = new HashMap<String, String>();
	
	public AbstarctDJHelper(CustomReport customReport){
		this.customReport=customReport;
		this.frb = new FastReportBuilder();
	}
	
	/**
	 * Adds Styling info for the report
	 * <li>Set Report Title Style</li>
	 * <li>Set Report Sub Title</li> ..
	 */
	protected void addReportStyles(){
	//	frb.setTitleStyle(DJStyles.getTopHeaderStyle());
		frb.setDefaultStyles(DJStyles.getTopHeaderStyle(), null, DJStyles.getDefaultColHeaderStyle(), DJStyles.getDefaultRowStyle());
		frb.setTitleStyle(DJStyles.getTopHeaderStyle());
	}
	
	/**
	 * Adds all report details
	 * <li>Set Report Title</li>
	 * <li>Set Report Sub Title</li> ..
	 */
	protected void addReportDetails(){
		frb.setTitle(customReport.getReportName());
		frb.setSubtitle("$P{subtitle}");
		frb.addParameter("subtitle", String.class.getName());
		frb.setHeaderHeight(20);
		frb.setWhenNoData(TPLabels.getLabel("report.error.no_data"), DJStyles.getNoDataStyle());
		frb.setIgnorePagination(true);	
	}
	
	/**
	 * Adds and builds all {@link AbstractColumn} Column columns using reportDetails
	 * @throws ColumnBuilderException
	 */
	protected void addReportColumns() throws ColumnBuilderException {
		List<Field> columns = customReport.getColumns();
		addReportColumns(columns);
	}
	
	protected void addReportColumns(List<Field> columns) throws ColumnBuilderException {
		AbstractColumn abstractColumn = null;
		if(!Utils.isListEmptyOrNull(columns)){
			for(Field column : columns){
				abstractColumn = buildAbstractColumn(column);
				frb.addColumn(abstractColumn);
			}
		}else{
			TPLogger.getLogger().debug("This Report does not have any columns to buld");
		}
	}
	
	/**
	 * Adds and builds all {@link AbstractColumn} Value/Measure Columns using reportDetails
	 * @throws ColumnBuilderException
	 */
	protected void addCrossTab() throws ColumnBuilderException {
		ICrossTabBuilder ctb = new CrossTabBuilder(customReport); // TODO Add a factory pattern to create instance
		DJCrosstab djCross = ctb.buildDJCrossTab();
		frb.addHeaderCrosstab(djCross);
	}
	
	
	/**
	 * @param column
	 * @return
	 * @throws ColumnBuilderException
	 */
	protected AbstractColumn buildAbstractColumn(Field column) throws ColumnBuilderException {
		try {
			ColumnBuilder colBuilder =  ColumnBuilder.getNew()
				 	.setColumnProperty(column.getKey(), DJUtils.getDataType(column))
				 	.setTitle(DJUtils.getDisplayName(column.getDisplayName()))
				 	.setWidth(column.getWitdh())
		  			.setHeaderStyle(DJStyles.getDefaultColHeaderStyle());
				return colBuilder.build();
		} catch (ColumnBuilderException e) {
			TPLogger.getLogger().error("Error in building Abstract Column for key:"+ column.getKey(), e);
			throw e;
		}
	}
	

	/**
	 * Builds a new DJGroup with the given {@link PropertyColumn} with Default Layout.
	 * @param groupByColumn
	 * @return
	 * @throws ColumnBuilderException
	 */
	protected DJGroup buildDJGroup(PropertyColumn groupByColumn) throws ColumnBuilderException {
		return buildDJGroup(groupByColumn, GroupLayout.DEFAULT);
	}
	
	/**
	 * Builds a new DJGroup with the given {@link PropertyColumn} and with given {@link GroupLayout}.
	 * @param groupByColumn
	 * @return
	 * @throws ColumnBuilderException
	 */
	protected DJGroup buildDJGroup(PropertyColumn groupByColumn,GroupLayout  layout) throws ColumnBuilderException {
		GroupBuilder groupBuilder =  new GroupBuilder();
		groupBuilder.setCriteriaColumn(groupByColumn);
		groupBuilder.setGroupLayout(layout);
		return groupBuilder.build();
	}
	
}
