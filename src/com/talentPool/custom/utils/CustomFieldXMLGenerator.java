package com.talentPool.custom.utils;

import java.io.StringWriter;
import java.util.Iterator;
import java.util.List;

import org.xml.sax.helpers.AttributesImpl;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.db.SimpleDataObject;
import com.talentPool.common.utils.Utils;
import com.talentPool.common.xmlutils.XMLWriter;
import com.talentPool.custom.dataobject.CustomFieldData;

public class CustomFieldXMLGenerator {
	
	
	public String getXMLForCustomFields(List<CustomFieldData> fields, String fldEntityType) {
		StringWriter sWr = new StringWriter();
		XMLWriter wr = new XMLWriter(sWr);
		try {
			wr.startDocument();			
			if(fields != null && fields.size() > 0) {
				wr.startElement("rows");		
				
				Iterator<CustomFieldData> itr = fields.iterator();
				while(itr.hasNext()) {				
					CustomFieldData sdo = itr.next();
					
					AttributesImpl at = new AttributesImpl();
					at.addAttribute("", "id", "", "", sdo.getFieldId());
					wr.startElement("", "row", "", at);
					
					at = new AttributesImpl();
					at.addAttribute("", "name", "", "", "displayName");
					wr.startElement("", "userdata", "", at);
					wr.characters(sdo.getFieldDisplayName());
					wr.endElement("userdata");
					
					wr.startElement("cell");
					wr.characters("<img src=\"images/ico_delete.gif\" style=\"cursor:pointer;\" onclick=\"javascript: deleteField("+ sdo.getFieldId() +", "+ fldEntityType + ");\"/>");
					wr.endElement("cell");
					
					wr.startElement("cell");					
					wr.characters(wr.doubleEscape(sdo.getFieldDisplayName())+"^javascript:viewDetails(\"" + sdo.getFieldId() + "\",\"" + fldEntityType + "\");^_self");
					wr.endElement("cell");
					
					
					if(!Utils.isBlankOrNull(sdo.getFieldType())){
						wr.startElement("cell");
						wr.characters(sdo.getFieldType());
						wr.endElement("cell");
					}
					
					wr.endElement("row");
				}
			} else {
				AttributesImpl at = new AttributesImpl();
				at.addAttribute("", "total_rows", "", "", "0");
				wr.startElement("", "rows", "", at);
			}
			wr.endElement("rows");
			wr.endDocument();
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while getting XML for custom fields", e);
		}		
		return sWr.getBuffer().toString();
	}

}
