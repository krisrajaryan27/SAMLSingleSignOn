/**
 * 
 */
package com.talentPool.customReports.djhelper.utils;

import java.util.List;

import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;
import ar.com.fdvs.dj.domain.entities.columns.PropertyColumn;

import com.talentPool.customReports.djhelper.wrappers.DJComparatorWrapper;
import com.talentPool.customReports.jaxb.Field;


/**
 * @author PraveenK
 * @since  Dec 27, 2011
 */
public class DJUtils {

	/**
	 * For non cross tab reports where wrapper is not required then 
	 * for columns with Wrapper instances as data type default type i.e. String.class.getName() is returned  
	 * @param column
	 * @return
	 */
	public static String getDataType(final Field column){
		String dataType = null;
		try {
			@SuppressWarnings("rawtypes")
			Class clazz = Class.forName(column.getDataType());
			if(DJComparatorWrapper.class.isAssignableFrom(clazz)){
				dataType = String.class.getName();
			}else{
				dataType = column.getDataType();
			}
		} catch (ClassNotFoundException e) {
			dataType = String.class.getName();
			e.printStackTrace();
		}
		return dataType;
	}
	
	/**
	 * Iterates through the column list to retrieve {@link AbstractColumn} for give propertyKey 
	 * @param propertyKey
	 * @param columns
	 * @return null is there is no column with given propertKey else corresponding {@link AbstractColumn}
	 */
	public static AbstractColumn getColumn(String propertyKey, final List<AbstractColumn> columns){
		try {
			for (AbstractColumn abstractColumn : columns) {
				if(((PropertyColumn)abstractColumn).getColumnProperty().getProperty().equals(propertyKey)){
					return abstractColumn;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @param originalName
	 * @return modified display name to eliminate 'Custom Field()' text
	 */
	public static String getDisplayName(String originalName) {
		String displayName = originalName;
		
		if(originalName.contains("Custom Field")) {
			int start = originalName.indexOf("(");
			int end = originalName.indexOf(")");
			displayName = originalName.substring(start+1, end);
		}

		return displayName;
	}
	
}
