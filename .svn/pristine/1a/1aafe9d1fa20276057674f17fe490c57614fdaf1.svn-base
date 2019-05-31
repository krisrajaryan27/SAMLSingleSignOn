/**
 * 
 */
package com.talentPool.offerSheet.utils;

import java.io.StringWriter;
import java.util.List;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.xmlutils.XMLWriter;
import com.talentPool.offerSheet.dataobject.OfferSheetTemplateData;

/**
 * @author pallavi
 *
 */
public class OfferSheetXmlGenerator {
	public String getXmlForOfferSheetTemplates(List<OfferSheetTemplateData> templates) {
		StringWriter sWr = new StringWriter();
		XMLWriter wr = new XMLWriter(sWr);
		try {
			wr.startDocument();
			wr.startElement("rows");
			if(templates != null && templates.size() > 0) {
				for(int i = 0; i < templates.size(); i++) {
					OfferSheetTemplateData data = templates.get(i);
					
					AttributesImpl atr = new AttributesImpl();
					atr.addAttribute("", "id", "", "", data.getTemplateId());
					wr.startElement("", "row", "", atr);
					
					atr = new AttributesImpl();
					atr.addAttribute("", "name", "", "", "templateName");
					wr.startElement("", "userdata", "", atr);
					wr.characters(data.getTemplateName());
					wr.endElement("userdata");
					
					wr.startElement("cell");
					wr.characters("<img src=\"images/ico_delete.gif\" border=0>^javascript:deleteRecord(" + data.getTemplateId() + ");^_self");
					wr.endElement("cell");

					wr.startElement("cell");
					wr.characters(wr.doubleEscape(data.getTemplateName()) + "^javascript:viewTemplate(\"" + data.getTemplateFilePath() + "\");^_self");
					wr.endElement("cell");

					wr.startElement("cell");
					wr.characters(wr.doubleEscape(data.getUserName()));
					wr.endElement("cell");

					wr.endElement("row");
				}
			}
			wr.endElement("rows");
			wr.endDocument();
		} catch (SAXException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return sWr.getBuffer().toString();
	}
}
