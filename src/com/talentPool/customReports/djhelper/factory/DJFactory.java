/**
 * 
 */
package com.talentPool.customReports.djhelper.factory;

import com.talentPool.common.utils.Utils;
import com.talentPool.customReports.djhelper.IDJHelper;
import com.talentPool.customReports.djhelper.impl.CrossTabDJHelper;
import com.talentPool.customReports.djhelper.impl.DJHelper;
import com.talentPool.customReports.djhelper.impl.RCDJHelper;
import com.talentPool.customReports.djhelper.impl.RMDJHelper;
import com.talentPool.customReports.djhelper.impl.SummaryDJHelper;
import com.talentPool.customReports.jaxb.CustomReport;

/**
 * @author PraveenK
 * @since  Dec 8, 2011
 */
public class DJFactory {

	/**
	 * Factory method that returns {@link IDJHelper} implementation based {@link CustomReport} information.
	 * <li>If {@link CustomReport} is having Rows, Columns and Values defined then {@link CrossTabDJHelper} is returned</li>
	 * <li>If {@link CustomReport} is have only Columns and Rows defined then {@link SummaryDJHelper} instance is returned</li>
	 * <li>If {@link CustomReport} is have only Columns defined then {@link DJHelper} instance is returned</li>
	 * <li>If none of the above criteria satisfies then null is returned </li>
	 * @param customReport
	 * @return
	 */
	public static IDJHelper getDJHelper(final CustomReport customReport){
		boolean columns = !Utils.isListEmptyOrNull(customReport.getColumns());
		boolean rows = !Utils.isListEmptyOrNull(customReport.getRows());
		boolean measures = !Utils.isListEmptyOrNull(customReport.getMeasures());
		if(columns && rows && measures){
			return new CrossTabDJHelper(customReport);
		}else if(rows && columns){
			return new RCDJHelper(customReport);
		}else if(rows && measures){
			return new RMDJHelper(customReport);
		}else {
			return new DJHelper(customReport);
		} 
	}
}
