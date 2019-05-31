package com.talentPool.positions.manager;

import java.io.StringWriter;
import java.util.List;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import com.talentPool.applicant.constants.ImportConfigurationConstants;
import com.talentPool.applicant.manager.ImportConfigurationManager;
import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.constants.DHTMLXXMLWriterConstants;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.utils.CommonUtils;
import com.talentPool.common.utils.Utils;
import com.talentPool.common.xmlutils.DHTMLXXMLWriter;
import com.talentPool.common.xmlutils.XMLWriter;
import com.talentPool.selectionProcess.SelectionProcessConstants;
import com.talentPool.selectionProcess.dataobject.ActionRequiredData;
import com.talentPool.selectionProcess.dataobject.RejectedCandidateData;
import com.talentPool.selectionProcess.dataobject.SelectionProcessData;
import com.talentPool.todo.utils.ToDoUtils;
import com.talentPool.user.manager.PermissionSet;

public class PositionSummaryXMLUtil {
	public String getXMLForInProcessApplicants(List applicants,String actionFilter, PermissionSet permissionSet) {
		StringWriter sWr = new StringWriter();
		XMLWriter wr = new XMLWriter(sWr);
		try {
			wr.startDocument();
			AttributesImpl atr = new AttributesImpl();
			wr.startElement("","rows","",atr);
			
			if (applicants != null && applicants.size() > 0) {
				for (int indx = 0; indx < applicants.size(); indx++) {
					SelectionProcessData data = (SelectionProcessData) applicants.get(indx);
					ActionRequiredData actionRequiredData = ToDoUtils.getActionRequired(data);
					String actionRequired = actionRequiredData.getActionRequired();
					int actionType = actionRequiredData.getActionType();
					if((!Utils.isBlankOrNull(actionFilter) && actionFilter.equals(""+actionRequiredData.getActionType()))
							|| Utils.isBlankOrNull(actionFilter)){
						atr = new AttributesImpl();
						atr.addAttribute("", "id", "", "", String.valueOf(data.getApplicantId()));
						wr.startElement("", "row", "", atr);
	
						String name = (data.getApplicantName() == null) ? "" : data.getApplicantName();
						String experience = data.getApplicantExperience();
						String currentEmployer = (data.getApplicantCurrentEmployer() == null) ? "" : data.getApplicantCurrentEmployer();
						String experienceAndCurrentEmployer = null;
						if (!Utils.isBlankOrNull(currentEmployer)) {
							experienceAndCurrentEmployer = experience + " - " + currentEmployer;
						} else {
							experienceAndCurrentEmployer = experience;
						}
	
						atr = new AttributesImpl();
						atr.addAttribute("", "name", "", "", "name");
						wr.startElement("", "userdata", "", atr);
						wr.characters(name);
						wr.endElement("userdata");
	
						atr = new AttributesImpl();
						atr.addAttribute("", "name", "", "", "Col_I_Comment");
						wr.startElement("", "userdata", "", atr);
						wr.characters(name + "\n" + experienceAndCurrentEmployer);
						wr.endElement("userdata");
	
						String flags = data.getFlags();
						if (Utils.isBlankOrNull(flags)) {
							flags = new String();
						}
						atr = new AttributesImpl();
						atr.addAttribute("", "name", "", "", "flags");
						wr.startElement("", "userdata", "", atr);
						wr.characters(flags);
						wr.endElement("userdata");
	
						String mobile = data.getApplicantCellPhone();
						if (Utils.isBlankOrNull(mobile)) {
							mobile = new String();
						}
						atr = new AttributesImpl();
						atr.addAttribute("", "name", "", "", "mobile");
						wr.startElement("", "userdata", "", atr);
						wr.characters(mobile);
						wr.endElement("userdata");
	
						if (name.length() > 22) {
							name = name.substring(0, 19) + "...";
						}
						if (experienceAndCurrentEmployer.length() > 22) {
							experienceAndCurrentEmployer = experienceAndCurrentEmployer.substring(0, 19) + "...";
						}
	
						String[] flagArray = flags.split(",");
						String image = new String();
						String flagText = new String();
	
						if (!Utils.isBlankOrNull(flags) && flagArray.length > 0) {					
							for (int i = 0; i < SelectionProcessConstants.MAX_FLAGS_TO_SHOW && i < flagArray.length; i++) {
								if (i > 0) {
									image += "<br/>";
									flagText += "\n";
								}
								image += "<a href=\"#\" style=\"cursor:default;\"><img src=\"" + CommonUtils.getFlagImage(flagArray[i]) + "\" border=0></a>";
								flagText += CommonUtils.getFlagText(flagArray[i]);
							}
							if (flagArray.length < SelectionProcessConstants.MAX_FLAGS_TO_SHOW) {
								for (int i = flagArray.length; i < SelectionProcessConstants.MAX_FLAGS_TO_SHOW; i++) {
									image += "<br/>&nbsp;";
								}
							}
						} else {
							image = "<br/><br/>";
						}
						wr.startElement("cell");
						wr.characters(image);
						wr.endElement("cell");
	
						atr = new AttributesImpl();
						atr.addAttribute("", "name", "", "", "Col_0_Comment");
						wr.startElement("", "userdata", "", atr);
						wr.characters(flagText);
						wr.endElement("userdata");
	
						String applicantLink = "<a href=\"#\" onclick=\"onClickApplicant('" +  data.getApplicantId() + "');\" onmouseover=\"showAjaxTip(event,'" + data.getApplicantId() + "')\" onmouseout=\"hideToolTip()\" >" + wr.doubleEscape(name) + "</a>";
						
						wr.startElement("cell");
						//wr.characters("<a href=\"#\" onclick=\"onClickApplicant(" + data.getApplicantId() + ");\">" + wr.doubleEscape(name) + "</a><br/>" + wr.doubleEscape(experienceAndCurrentEmployer));
						wr.characters(applicantLink);
						wr.endElement("cell");
						
						String source = data.getApplicantSourceTitle();
						
						atr = new AttributesImpl();
						atr.addAttribute("", "name", "", "", "Col_II_Comment");
						wr.startElement("", "userdata", "", atr);
						wr.characters(source);
						wr.endElement("userdata");
						
						atr = new AttributesImpl();
						atr.addAttribute("", "name", "", "", "source");
						wr.startElement("", "userdata", "", atr);
						if(!ImportConfigurationManager.isApplicantFieldViewable(ImportConfigurationConstants.FIELD_SOURCE ,permissionSet.isSHOW_CONFIDENTIAL_DATA())){
							wr.characters(GlobalConstants.CONFIDENTIAL_CHARACTER); 
						}else{
							wr.characters(source);
						}
						wr.endElement("userdata");
						
						wr.startElement("cell");
						if(!ImportConfigurationManager.isApplicantFieldViewable(ImportConfigurationConstants.FIELD_SOURCE ,permissionSet.isSHOW_CONFIDENTIAL_DATA())){
							wr.characters(wr.doubleEscape(GlobalConstants.CONFIDENTIAL_CHARACTER)); 
						}else{
							if (!Utils.isBlankOrNull(source) && source.length() > 21) {
								source = source.substring(0, 18) + "...";
							}
							wr.characters(wr.doubleEscape(source));
						}
						wr.endElement("cell");

						String step = (data.getApplicantStep() == null) ? "" : data.getApplicantStep();
						String status = (data.getApplicantStatus() == null) ? "" : data.getApplicantStatus();
	
						atr = new AttributesImpl();
						atr.addAttribute("", "name", "", "", "step");
						wr.startElement("", "userdata", "", atr);
						wr.characters(step);
						wr.endElement("userdata");
	
						atr = new AttributesImpl();
						atr.addAttribute("", "name", "", "", "Col_III_Comment");
						wr.startElement("", "userdata", "", atr);
						wr.characters(step + "\n" + status);
						wr.endElement("userdata");
	
						if (step.length() > 27) {
							step = step.substring(0, 24) + "...";
						}
						if (status.length() > 27) {
							status = status.substring(0, 24) + "...";
						}
	
						if (Utils.isBlankOrNull(status)) {
							status = "&nbsp;";
						}
						wr.startElement("cell");
						wr.characters(wr.doubleEscape(step) + "<br/>" + wr.doubleEscape(status));
						wr.endElement("cell");
	
						String users = data.getResponsibleUsers();
						atr = new AttributesImpl();
						atr.addAttribute("", "name", "", "", "Col_IV_Comment");
						
						wr.startElement("", "userdata", "", atr);
						wr.characters(wr.doubleEscape(users) + " to \n" +wr.doubleEscape(actionRequired));
						wr.endElement("userdata");
	
						if (actionRequired.length() > 32) {
							actionRequired = actionRequired.substring(0, 29) + "...";
						}
	
						wr.startElement("cell");
						wr.characters(wr.doubleEscape(users) + " to<br/>" +wr.doubleEscape(actionRequired));
						wr.endElement("cell");
						
						wr.endElement("row");
					}
				}
				}
			wr.endElement("rows");
			wr.endDocument();

		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return sWr.toString();
	}
	
	public String getXMLForRejectedCandidates(List<RejectedCandidateData> rejectedCandidates) {
		StringWriter sWr = new StringWriter();
		DHTMLXXMLWriter wr = new DHTMLXXMLWriter(sWr);		
		try {
			wr.startDocument();
			wr.startElement(DHTMLXXMLWriterConstants.ROWS);
			if (!Utils.isListEmptyOrNull(rejectedCandidates)) {
				for (RejectedCandidateData rejectedCandidateData : rejectedCandidates) {
					int applicantId 	 	 		= rejectedCandidateData.getApplicantId();
					String applciantName 			= (rejectedCandidateData.getApplicantName().length() > 45) ? rejectedCandidateData.getApplicantName().substring(0, 36) + "..." : rejectedCandidateData.getApplicantName();
					String applicantLink = "<a href=\"#\" onclick=\"onClickApplicant('" +  applicantId + "');\" onmouseover=\"showAjaxTip(event,'" + applicantId + "')\" onmouseout=\"hideToolTip()\" >" + wr.doubleEscape(applciantName) + "</a>";
					String[] columns 	= {"",rejectedCandidateData.getApplicantName(), applicantLink, rejectedCandidateData.getStepTitle(), 
												rejectedCandidateData.getRejectedBy(), 
												rejectedCandidateData.getRejectedDateToDisplay()};
					wr.createDHTMLXRow(""+applicantId, columns);
				}
			}
			wr.endElement(DHTMLXXMLWriterConstants.ROWS);
			wr.endDocument();

		}catch (SAXException e) {
			TPLogger.getLogger().error("Error while creating xml for rejected candidates", e);
		}
		return sWr.getBuffer().toString();
	}
	
	/**
	 * @param appliedCandidates
	 * @return xml for rendering applicants in positionsummary page.
	 */
	public String getXmlForCandidatesAppliedForPosition(List<SelectionProcessData> appliedCandidates) {
		StringWriter sWr = new StringWriter();
		DHTMLXXMLWriter wr = new DHTMLXXMLWriter(sWr);	
		try{
			wr.startDocument();
			wr.startElement(DHTMLXXMLWriterConstants.ROWS);
			if (!Utils.isListEmptyOrNull(appliedCandidates)) {
				for (SelectionProcessData appliedCandidatesData : appliedCandidates) {
					int applicantId 	 	 		= appliedCandidatesData.getApplicantId();
					String applicantName 			= (appliedCandidatesData.getApplicantName().length() > 45) ? appliedCandidatesData.getApplicantName().substring(0, 36) + "..." : appliedCandidatesData.getApplicantName();
					String applicantLink = "<a href=\"#\" onclick=\"onClickApplicant('" +  applicantId + "');\" onmouseover=\"showAjaxTip(event,'" + applicantId + "')\" onmouseout=\"hideToolTip()\" >" + wr.doubleEscape(applicantName) + "</a>";
					String[] columns 	= {applicantLink,appliedCandidatesData.getApplicantSourceTitle(),appliedCandidatesData.getAppliedDate().toString()};
					wr.createDHTMLXRow(""+applicantId, columns);
				}
			}
			wr.endElement(DHTMLXXMLWriterConstants.ROWS);
			wr.endDocument();
		}catch (SAXException e) {
			TPLogger.getLogger().error("Error while creating xml for applied candidates", e);
		}
		return sWr.getBuffer().toString();
	}
}
