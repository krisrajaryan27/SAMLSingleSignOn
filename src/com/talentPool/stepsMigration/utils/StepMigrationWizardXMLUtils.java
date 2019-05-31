/**
 * 
 */
package com.talentPool.stepsMigration.utils;

import java.io.StringWriter;
import java.util.List;

import org.xml.sax.SAXException;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.constants.DHTMLXXMLWriterConstants;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.utils.Utils;
import com.talentPool.common.xmlutils.DHTMLXXMLWriter;
import com.talentPool.positions.dataobject.PositionData;
import com.talentPool.positions.utils.PositionUtils;

/**
 * @author PraveenK
 * @since  Jan 8, 2012
 */
public class StepMigrationWizardXMLUtils {

	/**
	 * @param columnsList
	 * @return
	 */
	public static String getExceptionPositionsXML(List<PositionData> posDataList) throws SAXException {
		StringWriter sWr = new StringWriter();
		DHTMLXXMLWriter wr = new DHTMLXXMLWriter(sWr);		
		try {
			wr.startDocument();
			wr.startElement(DHTMLXXMLWriterConstants.ROWS);
			if (!Utils.isListEmptyOrNull(posDataList)) {
				for (PositionData posData : posDataList) {
					String rowId 	 	 = posData.getPositionId();
					String posStatus 	 = PositionUtils.getPositionStatusValues(posData.getPositionStatus());
					String positionTitle = "<a href=\"#\" onclick=\"onClickExceptionPosition("+ posData.getPositionId() + ");\" >"+posData.getPositionTitle()+"</a>";
					String[] columns 	= {positionTitle, posStatus};
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
