/**
 * 
 */
package com.talentPool.masters.utils;

import java.io.StringWriter;
import java.util.ArrayList;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.utils.DateUtils;
import com.talentPool.common.utils.Utils;
import com.talentPool.common.xmlutils.XMLWriter;
import com.talentPool.masters.dataobject.FeedbackFormData;

/**
 * @author shivprasad
 * 
 */
public class FeedbackFormXmlGenerator {
	public String getXMLForFeedbackForms(ArrayList<FeedbackFormData> list) {
		StringWriter sWr = new StringWriter();
		XMLWriter wr = new XMLWriter(sWr);

		try {
			wr.startDocument();
			wr.startElement("rows");
			for (int i = 0; list != null && i < list.size(); i++) {
				FeedbackFormData data = list.get(i);

				AttributesImpl at = new AttributesImpl();
				at.addAttribute("", "id", "", "", data.getFeedbackFormId());
				wr.startElement("", "row", "", at);

				at = new AttributesImpl();
				at.addAttribute("", "name", "", "", "Col_I_Comment");
				wr.startElement("", "userdata", "", at);
				wr.characters("Delete");
				wr.endElement("userdata");

				at = new AttributesImpl();
				at.addAttribute("", "name", "", "", "formTitle");
				wr.startElement("", "userdata", "", at);
				wr.characters(data.getFeedbackFormTitle());
				wr.endElement("userdata");

				wr.startElement("cell");
				wr.characters("<img src=\"images/ico_delete.gif\" border=0>^javascript:deleteRecord(" + data.getFeedbackFormId() + ");^_self");
				wr.endElement("cell");

				wr.startElement("cell");
				wr.characters(wr.doubleEscape(data.getFeedbackFormTitle()) + "^javascript:editRecord(" + data.getFeedbackFormId() + ");^_self");
				wr.endElement("cell");

				wr.startElement("cell");
				wr.characters(wr.doubleEscape(data.getCreatedBy()));
				wr.endElement("cell");
				
				wr.startElement("cell");
				wr.characters(DateUtils.getSystemDateFormat(data.getLastModified()));
				wr.endElement("cell");

				wr.endElement("row");
			}
			wr.endElement("rows");
			wr.endDocument();
		} catch (SAXException e) {
			TPLogger.getLogger().error("Error while creating xml", e);
		}

		return sWr.getBuffer().toString();
	}
	

}
