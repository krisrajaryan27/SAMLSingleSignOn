/**
 * 
 */
package com.talentPool.dynamicReports.utils;

import java.util.List;
import java.util.Map;

import ar.com.fdvs.dj.domain.CustomExpression;
import ar.com.fdvs.dj.domain.DJCalculation;
import ar.com.fdvs.dj.domain.builders.ColumnBuilder;
import ar.com.fdvs.dj.domain.builders.ColumnBuilderException;
import ar.com.fdvs.dj.domain.builders.FastReportBuilder;
import ar.com.fdvs.dj.domain.builders.GroupBuilder;
import ar.com.fdvs.dj.domain.constants.GroupLayout;
import ar.com.fdvs.dj.domain.entities.DJGroup;
import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;
import ar.com.fdvs.dj.domain.entities.columns.PropertyColumn;

import com.talentPool.common.utils.Utils;
import com.talentPool.dynamicReports.constants.DJReportConstants;
import com.talentPool.reportDesign.utils.ColumnUtils;

/**
 * @author shantanu
 *
 */
public class DJUtils {
	public static AbstractColumn transformToDJColumn(String key) throws ColumnBuilderException, ClassNotFoundException{
		return transformToDJColumn(DynamicReportsColumnUtils.getColumnDBMapping(key), ColumnUtils.getColumnLabel(key));
	}
	
	public static AbstractColumn transformToDJColumn(String key,String label) throws ColumnBuilderException, ClassNotFoundException{
		return transformToDJColumn(key, label, String.class.getName());
	}
	
	public static AbstractColumn transformToDJColumn(String key,CustomExpression customExpression) throws ColumnBuilderException, ClassNotFoundException{
		return transformToDJColumn(DynamicReportsColumnUtils.getColumnDBMapping(key), ColumnUtils.getColumnLabel(key),customExpression);
	}
	
	public static AbstractColumn transformToDJColumn(String key,String label,CustomExpression customExpression) throws ColumnBuilderException, ClassNotFoundException{
		ColumnBuilder column = ColumnBuilder.getNew()
						.setColumnProperty(key, String.class.getName())
		 				.setCustomExpression(customExpression)
		 				.setTitle(label)
		 				.setWidth(DJReportConstants.COL_MAX_WIDTH);
		return column.build();
	}
	
	public static AbstractColumn transformToDJColumn(String key,String label,String claasName) throws ColumnBuilderException, ClassNotFoundException{
		ColumnBuilder column = ColumnBuilder.getNew()
		 				.setColumnProperty(key, claasName)
		 				.setTitle(label)
		 				.setWidth(DJReportConstants.COL_MAX_WIDTH);
		 				
		return column.build();
	}
	
	public static void addGroups(int startIndex, int firstNcolumns,FastReportBuilder drb,GroupLayout layout, List<AbstractColumn> footerCols){
		DJGroup group = null;
		for (int i = startIndex; i < firstNcolumns; i++) {
			group = transformToDJGroup((PropertyColumn)drb.getColumn(i), layout, footerCols);
			drb.addGroup(group);
		}
	}
	
	public static void addGroupFields(int firstNcolumns,FastReportBuilder drb,GroupLayout layout, List<AbstractColumn> footerCols){
		DJGroup group = null;
		for (int i = 0; i < firstNcolumns; i++) {
			group = transformToDJGroup((PropertyColumn)drb.getFields().get(i), layout, footerCols);
			drb.addGroup(group);
		}
	}
	
	public static void addGroups(List<String> groupByList,FastReportBuilder drb,GroupLayout layout){
		addGroups(groupByList, drb, layout,null);
	}
	
	public static void addGroups(List<String> groupByList,FastReportBuilder drb,GroupLayout layout,List<AbstractColumn> footerCols){
		addGroups(0, groupByList.size(), drb, layout, footerCols);
	}
	
	public static DJGroup transformToDJGroup(PropertyColumn column,GroupLayout layout,List<AbstractColumn> footerCols){
		GroupBuilder groupBuilder = new GroupBuilder();
		groupBuilder.setCriteriaColumn((PropertyColumn) column);
		groupBuilder.setGroupLayout(layout);
		if(footerCols!=null){
			addFooterVariable(footerCols, groupBuilder);	
		}
		return groupBuilder.build();
	}
	
	private static void addFooterVariable(List<AbstractColumn> footerCols,GroupBuilder groupBuilder){
		for (AbstractColumn col : footerCols) {
			groupBuilder.addFooterVariable(col, DJCalculation.SUM, DJStyles.getGroupByFooterStyle());
		}
	}
	
	public static void addGlobalFooter(List<AbstractColumn> footerCols,FastReportBuilder drb){
		for (AbstractColumn col : footerCols) {
			drb.addGlobalFooterVariable(col, DJCalculation.SUM, DJStyles.getGroupByFooterStyle());
			drb.setGlobalFooterVariableHeight(20);
			drb.setGrandTotalLegend("");
		}
	}
	
	@SuppressWarnings({ "unchecked", "serial" })
	public static CustomExpression evaluatePositionStepLevelLabel(final Map<String,String> stepLevelLabelMap,final String key) {
        return new CustomExpression() {
				public Object evaluate(Map fields, Map variables, Map parameters) {
                        String level = (String) fields.get(DynamicReportsColumnUtils.getColumnDBMapping(key));
                        return Utils.getBlankIfNull(stepLevelLabelMap.get(level));
                }

                public String getClassName() {
                        return String.class.getName();
                }
        };
	}
	
}
