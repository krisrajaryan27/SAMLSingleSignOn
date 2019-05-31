/**
 * 
 */
package com.talentPool.desktop.manager;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import com.talentPool.applicant.ApplicantConstants;
import com.talentPool.applicant.dataobject.ApplicantData;
import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.db.DBPreparedQuery;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.utils.Utils;
import com.talentPool.common.xmlutils.XMLWriter;
import com.talentPool.masters.dataobject.SkillData;
import com.talentPool.user.manager.PermissionSet;


/**
 * @author shivprasad
 * 
 */
public class DesktopSearchManager {
	public ArrayList<ApplicantData> getApplicantToAttach(String name, String email, PermissionSet permissionSet) {
		DBPreparedQuery dq = null;
		ArrayList<ApplicantData> applicants = null;
		try {
			String[] dynParam = new String[1];
			ArrayList<String> dynamicContent = new ArrayList<String>();
			dynParam[0] = " ";
			if (!Utils.isBlankOrNull(name)) {
				if (Utils.isBlankOrNull(dynParam[0])) {
					dynParam[0] += " WHERE ";
				}
				dynParam[0] += " ta.applicant_name like ? ";
				dynamicContent.add(name + "%");
			}
			if (!Utils.isBlankOrNull(email)) {
				if (Utils.isBlankOrNull(dynParam[0])) {
					dynParam[0] += " WHERE ";
				} else {
					dynParam[0] += " AND ";
				}
				dynParam[0] += " (ta.applicant_email1 like ? OR ta.applicant_email2 like ? )";
				dynamicContent.add(email + "%");
				dynamicContent.add(email + "%");
			}

			if(permissionSet!=null){
				if (permissionSet.isDO_NOT_SHOW_CONFIDENTIAL_PROFILE()) {
					if (Utils.isBlankOrNull(dynParam[0])) {
						dynParam[0] += " WHERE ";
					} else {
						dynParam[0] += " AND ";
					}
					dynParam[0] += " ta.is_confidential = ? ";
					dynamicContent.add(ApplicantConstants.APPLICANT_NOT_CONFIDENTIAL);
				}
			}
			
			dynParam[0] += " ORDER BY ta.applicant_name ";

			dq = new DBPreparedQuery("dDesktopSearch_getApplicantsToAttach", dynParam);
			for (int i = 0; i < dynamicContent.size(); i++) {
				dq.setString(i + 1, dynamicContent.get(i));
			}
			applicants = (ArrayList<ApplicantData>) dq.getResult();
		} catch (Exception e) {
			TPLogger.getLogger().error("Error", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return applicants;
	}

	public String getXMLforAttachToSearch(ArrayList<ApplicantData> applicants) {
		StringWriter sWr = new StringWriter();
		XMLWriter wr = new XMLWriter(sWr);
		try {
			wr.startDocument();
			wr.startElement("rows");
			if (applicants != null && applicants.size() > 0) {
				for (int i = 0; i < applicants.size(); i++) {
					ApplicantData data = applicants.get(i);

					AttributesImpl atr = new AttributesImpl();
					atr.addAttribute("", "id", "", "", data.getApplicantId());
					wr.startElement("", "row", "", atr);

					wr.startElement("cell");
					wr.characters(wr.doubleEscape(data.getApplicantName()));
					wr.endElement("cell");
					wr.startElement("cell");
					wr.characters(wr.doubleEscape(data.getApplicantEmail1()));
					wr.endElement("cell");
					wr.startElement("cell");
					wr.characters(wr.doubleEscape(data.getApplicantEmail2()));
					wr.endElement("cell");

					wr.endElement("row");
				}
			}
			wr.endElement("rows");
			wr.endDocument();

		} catch (SAXException e) {
			TPLogger.getLogger().error("Error", e);
		}
		return sWr.getBuffer().toString();
	}
	
	
	public String getApplicantName(String applicantId) {
		DBPreparedQuery dq = null;
		String applicantName = "";
		try {
			dq = new DBPreparedQuery("dDesktopSearch_GetApplicantName");
			dq.setString(1, applicantId);
			applicantName = dq.getStringResult();
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while getting Applicant Name", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return applicantName;
	}
	
	public String getXMLSkills(List skills) {
		StringWriter sWr = new StringWriter();
		XMLWriter wr = new XMLWriter(sWr);		
		try {
			wr.startDocument();
			wr.startElement("rows");
			if (skills != null && skills.size() > 0) {
				for (int i = 0; i < skills.size(); i++) {
					
					SkillData skillData = (SkillData)skills.get(i);
					
					String skillId  = (""+skillData.getSkillId()==null?"":""+skillData.getSkillId());
					String skillName = (""+skillData.getSkillName()==null?"":""+skillData.getSkillName());
					
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
			wr.endElement("rows");
			wr.endDocument();

		}catch (SAXException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return sWr.getBuffer().toString();
	}

	public String getSourceId(String source) {
		DBPreparedQuery dq = null;
		String sourceId = "";
		source += "%"; 
		try {
			dq = new DBPreparedQuery("dDesktopSearch_GetSourceIdFromName");
			dq.setString(1, source);
			sourceId = dq.getIdResult();
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return sourceId;
	}
}
