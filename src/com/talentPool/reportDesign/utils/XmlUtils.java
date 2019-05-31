/**
 * 
 */
package com.talentPool.reportDesign.utils;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.db.SimpleDataObject;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.utils.Utils;
import com.talentPool.common.xmlutils.XMLWriter;

/**
 * @author Ajeet
 *
 */
public class XmlUtils {

	public String getXMLColumns(String columns) {
		StringWriter sWr = new StringWriter();
		XMLWriter wr = new XMLWriter(sWr);		
		try {
			wr.startDocument();
			wr.startElement("rows");
			String[] colmnStr = columns.split(",");
			if (colmnStr != null && colmnStr.length > 0) {
				for (int i = 0; i < colmnStr.length; i++) {
					
					AttributesImpl atr = new AttributesImpl();					
					atr.addAttribute("", "id", "", "", String.valueOf(colmnStr[i]));
					wr.startElement("", "row", "", atr);
						
					String columnName = ColumnUtils.columnLabelMap.get(colmnStr[i]);
					atr = new AttributesImpl();
					atr.addAttribute("", "name", "", "", "columnName");
					wr.startElement("", "userdata", "", atr);
					wr.characters(columnName);
					wr.endElement("userdata");
										
					wr.startElement("cell");
					wr.characters(columnName);
					wr.endElement("cell");			
									
					wr.endElement("row");			
				}
			}
			wr.endElement("rows");
			wr.endDocument();

		}catch (SAXException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return sWr.getBuffer().toString();
	}
	
	public static String getXMLFromMasterDataList(ArrayList<?> masterDataList,String paramId, String paramName) {
		StringWriter sWr = new StringWriter();
		XMLWriter wr = new XMLWriter(sWr);
		try {
			wr.startDocument();
			wr.startElement("rows");
			for (Object object : masterDataList) {
				SimpleDataObject masterData = (SimpleDataObject)object;
				AttributesImpl atr = new AttributesImpl();
				atr.addAttribute("", "id", "", "", masterData.getString(paramId)+"");
				wr.startElement("", "row", "", atr);

				wr.startElement("cell");
				wr.characters(masterData.getString(paramName));
				wr.endElement("cell");

				wr.endElement("row");
			}	

			wr.endElement("rows");
			wr.endDocument();

		} catch (SAXException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return sWr.getBuffer().toString();
	}
	
	public static String getXMLFromDataMap(HashMap masterDataMap) {
		StringWriter sWr = new StringWriter();
		XMLWriter wr = new XMLWriter(sWr);
		try {
			wr.startDocument();
			wr.startElement("rows");
			for (Object key : masterDataMap.keySet()) {
				AttributesImpl atr = new AttributesImpl();
				atr.addAttribute("", "id", "", "", key+"");
				wr.startElement("", "row", "", atr);

				wr.startElement("cell");
				wr.characters(""+masterDataMap.get(key).toString());
				wr.endElement("cell");

				wr.endElement("row");
			}	

			wr.endElement("rows");
			wr.endDocument();

		} catch (SAXException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return sWr.getBuffer().toString();
	}
}
