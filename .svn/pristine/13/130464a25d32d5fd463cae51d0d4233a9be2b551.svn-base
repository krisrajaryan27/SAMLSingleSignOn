/**
 * 
 */
package com.talentPool.customReports.utils;

import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.talentPool.common.CommonConstants;
import com.talentPool.common.Logger.TPLogger;
import com.talentPool.customReports.dataobject.CustomReportColumn;
import com.talentPool.customReports.dataobject.CustomReportFilter;
import com.talentPool.customReports.jaxb.Filter;

/**
 * @author PraveenK
 * @since  Nov 25, 2011
 */
public class CRJSONUtils {
	
	
	/**
	 * Converts JSON string to <code>{@link List}<{@link CustomReportColumn}></code> Object. 
	 * @param columnsJSON
	 * @return <li><code>{@link List}<{@link CustomReportColumn}></code></li>
	 * <li>NULL when Gson attempts to read (or write) a malformed JSON element.</li>
	 */
	public static List<CustomReportColumn> converToColumnList(String columnsJSON){
		List<CustomReportColumn> columnsList = null;
		try {
			Gson gson = new Gson();
			Type columnsListType = new TypeToken<List<CustomReportColumn>>(){}.getType();
			columnsList = gson.fromJson(columnsJSON, columnsListType);				
		} catch (JsonSyntaxException jse) {
			TPLogger.getLogger().error("columnsJSON: "+columnsJSON, jse);
		} catch (JsonParseException jpe) {
			TPLogger.getLogger().error("columnsJSON: "+columnsJSON, jpe);
		}
		return columnsList;
	}
	
	/**
	 * Converts JSON string to <code>{@link List}<{@link Filter}></code> Object. 
	 * @param columnsJSON
	 * @return <li><code>{@link List}<{@link Filter}></code></li>
	 * <li>NULL when Gson attempts to read (or write) a malformed JSON element.</li>
	 */
	public static List<Filter> converToFilterList(String filtersJSON){
		List<Filter> filtersList = null;
		try {
			Gson gson = new Gson();
			Type filtersType = new TypeToken<List<Filter>>(){}.getType();
			filtersList = gson.fromJson(filtersJSON, filtersType);
		} catch (JsonSyntaxException jse) {
			TPLogger.getLogger().error("filtersJSON: "+filtersJSON, jse);
		} catch (JsonParseException jpe) {
			TPLogger.getLogger().error("filtersJSON: "+filtersJSON, jpe);
		}
		return filtersList;
	}
	
	
	/**
	 * Converts columnJSON to comma separated column Id's 
	 * @param columnsJSON
	 * @return <li>comma separated column Id's</li>
	 * <li>BLANK if columnsJSON is NULL or EMPTY or illegal JSON Format</li>
	 */
	public static String convertToCommaSepList(String columnsJSON){
		List<CustomReportColumn> columnLst =  converToColumnList(columnsJSON);
		if(columnLst!=null){
			StringBuffer selectedCols = new StringBuffer();
			for (CustomReportColumn customReportColumn : columnLst) {
				selectedCols.append(customReportColumn.getKey()).append(CommonConstants.DEFAULT_DELIMITER); 
			}
			return selectedCols.toString();
		}else{
			return "";
		}
	}
	
	/**
	 * Converts <li><code>{@link List}<{@link CustomReportColumn}></code></li> to filtersJSON. 
	 * @param filters
	 * @return filtersJSON
	 * <li>NULL when Gson attempts to read (or write) a malformed JSON element.</li>
	 */
	public static String converToFiltersJSON(List<Filter> filters){
		String filtersJSON = null;
		try {
			Gson gson = new Gson();
			Type filtersType = new TypeToken<List<CustomReportFilter>>(){}.getType();
			filtersJSON = gson.toJson(filters, filtersType);
		} catch (JsonSyntaxException jse) {
			TPLogger.getLogger().error("filtersJSON: "+filtersJSON, jse);
		} catch (JsonParseException jpe) {
			TPLogger.getLogger().error("filtersJSON: "+filtersJSON, jpe);
		}
		return filtersJSON;
	}
}
