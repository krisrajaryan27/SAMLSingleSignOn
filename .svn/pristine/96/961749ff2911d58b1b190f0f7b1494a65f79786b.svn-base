/**
 * 
 */
package com.talentPool.customReports.utils;

import java.io.StringWriter;
import java.util.List;

import org.xml.sax.SAXException;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.constants.DHTMLXXMLWriterConstants;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.utils.Utils;
import com.talentPool.common.xmlutils.DHTMLXXMLWriter;
import com.talentPool.customReports.dataobject.CRColumn;

/**
 * @author PraveenK
 * @since  Nov 15, 2011
 */
public class CustomReportXMLUtils {
	
	/**
	 * @param columnsList
	 * @return
	 */
	public static String getXMLforAvailableColumns(List<CRColumn> columnsList) throws SAXException {
		StringWriter sWr = new StringWriter();
		DHTMLXXMLWriter wr = new DHTMLXXMLWriter(sWr);		
		try {
			wr.startDocument();
			wr.startElement(DHTMLXXMLWriterConstants.ROWS);
			if (!Utils.isListEmptyOrNull(columnsList)) {
				for (CRColumn crColumn : columnsList) {
					String rowId 	 	= crColumn.getColumnProperty();
					String[] columns 	= new String[2];
					
					columns[0] = crColumn.getColumnDisplayName();
					columns[1] = CustomReportUtils.getCategoryLabel(crColumn.getColumnCategory());
					
					wr.createDHTMLXRow(rowId, columns);
				}
			}
			wr.endElement(DHTMLXXMLWriterConstants.ROWS);
			wr.endDocument();

		}catch (SAXException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return sWr.getBuffer().toString();
	}
}
