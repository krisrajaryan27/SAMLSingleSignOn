package com.talentPool.Skills.Skills;

import java.io.StringWriter;
import java.util.List;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.utils.Utils;
import com.talentPool.common.xmlutils.XMLWriter;
import com.talentPool.masters.dataobject.SkillCategoryData;
import com.talentPool.masters.dataobject.SkillData;

public class SkillsUtils {

	public static String getXmlForSkillsMaster(List<SkillData> skills) {		
		StringWriter sWr = new StringWriter();
		XMLWriter wr = new XMLWriter(sWr);		
		try {
			wr.startDocument();
			wr.startElement("rows");
			if (skills != null && skills.size() > 0) {
				for (int i = 0; i < skills.size(); i++) {
					SkillData skillsData = (SkillData) skills.get(i);					
					
					if(!Utils.isBlankOrNull(skillsData.getSkillId())){
						int skillId = Integer.parseInt(skillsData.getSkillId());
						String skillName = Utils.getBlankIfNull(skillsData.getSkillName());
						
						AttributesImpl atr = new AttributesImpl();
						atr.addAttribute("", "id", "", "", String.valueOf(skillId));
						wr.startElement("", "row", "", atr);
											
						atr = new AttributesImpl();
						atr.addAttribute("", "name", "", "", "skillName");
						wr.startElement("", "userdata", "", atr);
						wr.characters(skillName);
						wr.endElement("userdata");
						
						wr.startElement("cell");
						wr.characters(skillName);
						wr.endElement("cell");			
					
						wr.endElement("row");
					}
				}
			}
			wr.endElement("rows");
			wr.endDocument();

		}catch (SAXException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return sWr.getBuffer().toString();
	}
	
	
	
	public static String getXMLForSkillCategoryFilters(List skillCategories) {
		StringWriter sWr = new StringWriter();
		XMLWriter wr = new XMLWriter(sWr);
		try {
			wr.startDocument();
			wr.startElement("rows");
			for (int i = 0; i < skillCategories.size(); i++) {
				SkillCategoryData data = (SkillCategoryData) skillCategories.get(i);

				AttributesImpl at = new AttributesImpl();
				at.addAttribute("", "id", "", "", "" + data.getItemId());
				wr.startElement("", "row", "", at);				

				at = new AttributesImpl();
				at.addAttribute("", "name", "", "", "skillCategory");
				wr.startElement("", "userdata", "", at);
				wr.characters(data.getItemName());
				wr.endElement("userdata");
				
				wr.startElement("cell");
				wr.characters(data.getItemName());
				wr.endElement("cell");

				wr.endElement("row");
			}
			wr.endElement("rows");
			wr.endDocument();
		} catch (SAXException e) {
			TPLogger.getLogger().error("Error while creating xml file for Departments", e);
		}
		return sWr.getBuffer().toString();
	}
}
