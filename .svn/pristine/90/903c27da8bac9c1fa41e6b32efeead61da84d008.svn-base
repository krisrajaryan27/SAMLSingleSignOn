/**
 * 
 */
package com.talentPool.positions;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.db.SimpleDataObject;
import com.talentPool.common.properties.GlobalApplicationProperties;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.properties.TPLabels;
import com.talentPool.common.utils.DateUtils;
import com.talentPool.common.utils.Utils;
import com.talentPool.common.xmlutils.XMLWriter;
import com.talentPool.masters.dataobject.BranchesData;
import com.talentPool.masters.dataobject.DegreeData;
import com.talentPool.masters.dataobject.InstituteData;
import com.talentPool.masters.manager.MastersManager;
import com.talentPool.positions.dataobject.PositionData;
import com.talentPool.positions.dataobject.PositionRuleData;
import com.talentPool.positions.dataobject.RuleInstituteData;
import com.talentPool.positions.manager.PositionManager;
import com.talentPool.requisition.constants.RequisitionConstants;
import com.talentPool.requisition.dataobject.RequisitionFeedbackData;
import com.talentPool.user.manager.PermissionSet;

/**
 * @author shivprasad
 * 
 */
public class PositionXMLGenerator {
	public String getXMLforPositionsHome(ArrayList positions, String userId, PermissionSet permissionSet) {
		StringWriter sWr = new StringWriter();
		XMLWriter wr = new XMLWriter(sWr);
		try {
			wr.startDocument();
			wr.startElement("rows");
			if (positions != null && positions.size() > 0) {
				for (int i = 0; i < positions.size(); i++) {
					SimpleDataObject sDo = (SimpleDataObject) positions.get(i);
					
					String positionPriority=sDo.getString("positionPriority");
					String ImgPositionPriority = "";
					String editPriority="";
					String priorityLevel = "";
					if(!Utils.isBlankOrNull(positionPriority)){
						if (positionPriority.equals(PositionConstants.POSITION_LEVEL_HIGH)) {
							ImgPositionPriority = "<img src=\"images/ico_priority_high.jpg\" border=0>";
							priorityLevel="HIGH";
						} else if (positionPriority.equals(PositionConstants.POSITION_LEVEL_MEDIUM)) {
							ImgPositionPriority = "<img src=\"images/ico_priority_medium.jpg\" border=0>";
							priorityLevel="MEDIUM";
						} else if (positionPriority.equals(PositionConstants.POSITION_LEVEL_LOW)) {
							ImgPositionPriority = "<img src=\"images/ico_priority_low.jpg\" border=0>";
							priorityLevel="LOW";
						}						
						if(permissionSet.isPERMISSION_POSITION_PRIORITY()){
							editPriority= "<a href=\"#\" onclick=\"javascript:onClickPositionPriority(" + sDo.getString("positionId") + ");\" title=\"Position Priority\">" +ImgPositionPriority+"</a>" ;
						}else{
							editPriority=ImgPositionPriority;
						}						
					}else{
						editPriority="";
					}
					
					
					String positionStatus = sDo.getString("positionStatus");
					AttributesImpl atr = new AttributesImpl();
					atr.addAttribute("", "id", "", "", String.valueOf(sDo.getString("positionId")));
					wr.startElement("", "row", "", atr);
					
					atr = new AttributesImpl();
					atr.addAttribute("", "name", "", "", "positionStatus");
					wr.startElement("", "userdata", "", atr);
					wr.characters(positionStatus);
					wr.endElement("userdata");
										
					atr = new AttributesImpl();
					atr.addAttribute("", "name", "", "", "positionPriority");
					wr.startElement("", "userdata", "", atr);
					wr.characters(positionPriority);
					wr.endElement("userdata");
					
					atr = new AttributesImpl();
					atr.addAttribute("", "name", "", "", "priorityLevel");
					wr.startElement("", "userdata", "", atr);
					wr.characters(priorityLevel);
					wr.endElement("userdata");

					String ImgPositionStatus = "";
					if (positionStatus.equals(PositionConstants.POSITION_STATUS_INPROCESS)) {
						ImgPositionStatus = "<img src=\"images/ico_open_requisition.gif\">";
					} else if (positionStatus.equals(PositionConstants.POSITION_STATUS_REJECTED)) {
						ImgPositionStatus = "<img src=\"images/ico_rejected_requisition.gif\">";
					} else if (positionStatus.equals(PositionConstants.POSITION_STATUS_OPENED)) {
						ImgPositionStatus = "<img src=\"images/ico_open_position.gif\" title=\"open pos\">";
					} else if (positionStatus.equals(PositionConstants.POSITION_STATUS_HOLD)) {
						ImgPositionStatus = "<img src=\"images/ico_onhold_position.gif\" title=\"on hold\">";
					} else if (positionStatus.equals(PositionConstants.POSITION_STATUS_CLOSED)) {
						ImgPositionStatus = "<img src=\"images/ico_closed_position.gif\">";
					}
					wr.startElement("cell");
					wr.characters(ImgPositionStatus);
					wr.endElement("cell");
					
					
					wr.startElement("cell");					
					wr.characters(editPriority);
					wr.endElement("cell");
					

					String positionName = sDo.getString("positionTitle") + " [" + sDo.getString("positionCode") + "]";
					
					atr = new AttributesImpl();
					atr.addAttribute("", "name", "", "", "positionName");
					wr.startElement("", "userdata", "", atr);
					wr.characters(positionName);
					wr.endElement("userdata");

					if(GlobalConstants.ENABLED.equals(GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_SHOW_POSITION_CODE))) {
						positionName = sDo.getString("positionCode");
					} else {
						positionName = sDo.getString("positionTitle");
					}
					if (positionName.length() > 28) {
						positionName = positionName.substring(0, 25) + "...";
					}
					
					
					if(permissionSet.isPERMISSION_POSITION_DETAILS()){
						wr.startElement("cell");
						wr.characters("<a href=\"#\" onclick=\"javascript:viewPositionDetails(" + sDo.getString("positionId") + ");\">" + wr.doubleEscape(positionName) + "</a>");
						wr.endElement("cell");	
					}else{
						wr.startElement("cell");
						wr.characters(wr.doubleEscape(positionName));
						wr.endElement("cell");
					}
					

					String department = (sDo.getString("departmentName") == null) ? "" : sDo.getString("departmentName");

					atr = new AttributesImpl();
					atr.addAttribute("", "name", "", "", "department");
					wr.startElement("", "userdata", "", atr);
					wr.characters(department);
					wr.endElement("userdata");

					if (department.length() > 17) {
						department = department.substring(0, 14) + "...";
					}
					wr.startElement("cell");
					wr.characters(wr.doubleEscape(department));
					wr.endElement("cell");

					String totalVacancies = (sDo.getInt("noOfPositions") == 0) ? "" : sDo.getString("noOfPositions");
					wr.startElement("cell");
					wr.characters(totalVacancies);
					wr.endElement("cell");

					String inProcess = (sDo.getInt("candidates") == 0) ? "0" : sDo.getString("candidates");

					wr.startElement("cell");
					if (PositionConstants.POSITION_STATUS_CLOSED.equalsIgnoreCase(positionStatus) || inProcess.equals("0") || !permissionSet.isPERMISSION_SELECT()) {
						wr.characters(inProcess);
					} else {
						wr.characters("<a href=\"#\" onclick=\"javascript:onClickInProcess(" + sDo.getString("positionId") + ");\">" + wr.doubleEscape(inProcess) + "</a>");
					}

					wr.endElement("cell");

					atr = new AttributesImpl();
					atr.addAttribute("", "name", "", "", "inProcess");
					wr.startElement("", "userdata", "", atr);
					wr.characters(inProcess);
					wr.endElement("userdata");

					String offered = (sDo.getInt("offered") == 0) ? "0" : sDo.getString("offered");

					wr.startElement("cell");
					if (PositionConstants.POSITION_STATUS_CLOSED.equalsIgnoreCase(positionStatus) || offered.equals("0") || !permissionSet.isPERMISSION_HIRE()) {
						wr.characters(offered);
					} else {
						wr.characters("<a href=\"#\" onclick=\"javascript:onClickOffered(" + sDo.getString("positionId") + ");\">" + wr.doubleEscape(offered) + "</a>");
					}
					wr.endElement("cell");

					atr = new AttributesImpl();
					atr.addAttribute("", "name", "", "", "offered");
					wr.startElement("", "userdata", "", atr);
					wr.characters(offered);
					wr.endElement("userdata");

					String joined = (sDo.getInt("joined") == 0) ? "0" : sDo.getString("joined");

					wr.startElement("cell");
					if (PositionConstants.POSITION_STATUS_CLOSED.equalsIgnoreCase(positionStatus) || joined.equals("0") || !permissionSet.isPERMISSION_HIRE() || !permissionSet.isPERMISSION_SHOW_JOINED_CANDIDATES()) {
						wr.characters(joined);
					} else {
						wr.characters("<a href=\"#\" onclick=\"javascript:onClickJoined(" + sDo.getString("positionId") + ");\">" + wr.doubleEscape(joined) + "</a>");
					}
					wr.endElement("cell");

					atr = new AttributesImpl();
					atr.addAttribute("", "name", "", "", "joined");
					wr.startElement("", "userdata", "", atr);
					wr.characters(joined);
					wr.endElement("userdata");
					
					String rejected = sDo.getString("rejected");
					
					atr = new AttributesImpl();
					atr.addAttribute("", "name", "", "", "rejected");
					wr.startElement("", "userdata", "", atr);
					wr.characters(rejected);
					wr.endElement("userdata");					
					
					wr.startElement("cell");
					if (rejected.equals("0") || !permissionSet.isPERMISSION_VIEW_REJECTED_CANDIDATES()) {
						wr.characters(rejected);
					} else {
						wr.characters("<a href=\"#\" onclick=\"javascript:onClickRejected(" + sDo.getString("positionId") + ");\">" + rejected + "</a>");
					}
					wr.endElement("cell");
					
					atr = new AttributesImpl();
					atr.addAttribute("", "name", "", "", "isBudgetCommitted");
					wr.startElement("", "userdata", "", atr);
					String isBudgetCommitted  =sDo.getString("isBudgetCommitted");
					if(!Utils.isBlankOrNull(isBudgetCommitted)){
						wr.characters(isBudgetCommitted);
					}
					wr.endElement("userdata");					
					String hireByDate = "";
					try {
						hireByDate = DateUtils.getSystemDateFormat(sDo.getDate("expiryDate"));
					} catch (ClassCastException e) {
						TPLogger.getLogger().error("ClassCastException: expiryDate cannot be fetched in DATETIME Format.");
					}
					wr.startElement("cell");
					wr.characters(hireByDate);
					wr.endElement("cell");
					
					if(!Utils.isBlankOrNull(hireByDate) && PositionConstants.POSITION_STATUS_OPENED.equals(positionStatus)){
						Date dt = sDo.getDate("expiryDate");
						if(dt.before(Calendar.getInstance().getTime())){
							positionStatus = PositionConstants.POSITION_STATUS_OVERDUE;
						}
					}
					wr.startElement("cell");
					wr.characters(positionStatus);
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

	public String getRequisitionApprovalHistoryXML(ArrayList<RequisitionFeedbackData> history, String userId, boolean editAllowed, PermissionSet permissionSet) {
		StringWriter sWr = new StringWriter();
		XMLWriter wr = new XMLWriter(sWr);
		try {
			wr.startDocument();
			wr.startElement("rows");
			if (history != null) {
				for (int i = 0; i < history.size(); i++) {
					RequisitionFeedbackData rDo = (RequisitionFeedbackData) history.get(i);

					AttributesImpl atr = new AttributesImpl();
					atr.addAttribute("", "id", "", "", rDo.getFeedbackId());
					wr.startElement("", "row", "", atr);

					String uId = rDo.getByUserId();
					boolean deleteAllowed = false;
					if (uId.equals(userId) || permissionSet.isSHOW_ALL_POSITIONS()) {
						if (editAllowed && i == history.size() - 1 && i != 0) {
							deleteAllowed = true;
						}
					}
					wr.startElement("cell");
					if (deleteAllowed) {
						wr.characters("<a href=\"#\" onclick=\"onClickDeleteFeedback(" + rDo.getFeedbackId() + ");\" title=\"Delete\"><img src=\"images/ico_delete.gif\" border=0></a>");
					} else {
						wr.characters(" ");
					}
					wr.endElement("cell");

					String date = rDo.getFeedbackDateToDisplay();
					wr.startElement("cell");
					wr.characters(date);
					wr.endElement("cell");

					wr.startElement("cell");
					wr.characters(wr.doubleEscape(rDo.getFromUserName()));
					wr.endElement("cell");

					wr.startElement("cell");
					wr.characters(wr.doubleEscape(rDo.getFromStepName()));
					wr.endElement("cell");

					String status = "";
					String feedbackDecision = rDo.getFeedbackDecision();
					if (feedbackDecision.equals(RequisitionConstants.FEEDBACK_ACTION_APPROVE)) {
						status = TPLabels.getLabel("position.approval.label.approved");
					} else if (feedbackDecision.equals(RequisitionConstants.FEEDBACK_ACTION_REJECT)) {
						status = TPLabels.getLabel("position.approval.label.rejected");
					} else if (feedbackDecision.equals(RequisitionConstants.FEEDBACK_ACTION_HOLD)) {
						status = TPLabels.getLabel("position.approval.label.onhold");
					}
					wr.startElement("cell");
					wr.characters(wr.doubleEscape(status) + "^javascript:onClickFeedback(" + rDo.getFeedbackId() + ");^_self");
					wr.endElement("cell");

					wr.endElement("row");
				}
			}
			wr.endElement("rows");
			wr.endDocument();

		} catch (SAXException e) {
			TPLogger.getLogger().error("Error while creating xml for approval history", e);
		}
		return sWr.getBuffer().toString();
	}
	
	public static String getPublishPositionsToVendorsXML(List<PositionData> positions) {
		StringWriter sWr = new StringWriter();
		XMLWriter wr = new XMLWriter(sWr);
		String vendors 		= null;
		String isPublished 	= null;
		String locations 	= null;
		String positionCode = null;
		String positionTitle = null;
		try {
			wr.startDocument();
			wr.startElement("rows");
			for(PositionData positionData:positions){
				
				positionCode = Utils.getStringTrimmed(Utils.getBlankIfNull(positionData.getPositionReferenceCode()), 26);
				positionTitle = Utils.getStringTrimmed(Utils.getBlankIfNull(positionData.getPositionTitle()), 30);
				locations = Utils.getStringTrimmed(Utils.getBlankIfNull(positionData.getLocationName()), 15);
				vendors = Utils.getStringTrimmed(Utils.getBlankIfNull(positionData.getPositionVendors()), 17);
				
				if(PositionConstants.POSITION_PUBLISHED.equals(positionData.getPublishedToVendors())){
					isPublished = TPLabels.getLabel("position.publish.label.published");
				}else{
					isPublished = TPLabels.getLabel("position.publish.label.unpublished");
				}
				
				AttributesImpl atr = new AttributesImpl();
				atr.addAttribute("", "id", "", "", positionData.getPositionId());
				wr.startElement("", "row", "", atr);
				writeValueToCell(wr, "");
				
				writeUserData(wr, "Col_I_Comment", Utils.getBlankIfNull(positionData.getPositionReferenceCode()));
				writeValueToCell(wr, positionCode);
				
				writeUserData(wr, "Col_II_Comment", Utils.getBlankIfNull(positionData.getPositionTitle()));
				writeValueToCell(wr, positionTitle);
				
				writeUserData(wr, "Col_III_Comment", Utils.getBlankIfNull(positionData.getLocationName()));
				writeValueToCell(wr, locations);
				
				writeUserData(wr, "Col_IV_Comment", Utils.getBlankIfNull(positionData.getPositionVendors()));
				writeValueToCell(wr, vendors);
				
				writeUserData(wr, "Col_V_Comment", isPublished);
				writeValueToCell(wr, isPublished);
				wr.endElement("row");
			}
			wr.endElement("rows");
			wr.endDocument();

		} catch (SAXException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return sWr.getBuffer().toString();
	}
	
	public static String getPublishPositionsToWebSiteXML(List<PositionData> positions) throws Exception {
		StringWriter sWr = new StringWriter();
		XMLWriter wr = new XMLWriter(sWr);
		String ruleText 		= null;
		String isPublished 	= null;
		String locations 	= null;
		String positionCode = null;
		String positionTitle = null;
		String ruleTitle  	 = null;
		try {
			wr.startDocument();
			wr.startElement("rows");
			for(PositionData positionData:positions){
				
				positionCode = Utils.getStringTrimmed(Utils.getBlankIfNull(positionData.getPositionReferenceCode()), 26);
				positionTitle = Utils.getStringTrimmed(Utils.getBlankIfNull(positionData.getPositionTitle()), 30);
				locations = Utils.getStringTrimmed(Utils.getBlankIfNull(positionData.getLocationName()), 15);
				ruleTitle = getRuleText(Utils.getBlankIfNull(positionData.getPositionId()));
				ruleText = Utils.getStringTrimmed(ruleTitle, 17);
				
				if(PositionConstants.POSITION_PUBLISHED_TO_WEBSITE.equals(positionData.getPublishToSite())){
					isPublished = TPLabels.getLabel("position.publish.label.published");
				}else{
					isPublished = TPLabels.getLabel("position.publish.label.unpublished");
				}
				
				AttributesImpl atr = new AttributesImpl();
				atr.addAttribute("", "id", "", "", positionData.getPositionId());
				wr.startElement("", "row", "", atr);
				writeValueToCell(wr, "");
				
				writeUserData(wr, "Col_I_Comment", Utils.getBlankIfNull(positionData.getPositionReferenceCode()));
				writeValueToCell(wr, positionCode);
				
				writeUserData(wr, "Col_II_Comment", Utils.getBlankIfNull(positionData.getPositionTitle()));
				writeValueToCell(wr, positionTitle);
				
				writeUserData(wr, "Col_III_Comment", Utils.getBlankIfNull(positionData.getLocationName()));
				writeValueToCell(wr, locations);
				
				writeUserData(wr, "Col_IV_Comment", ruleTitle);
				writeValueToCell(wr, ruleText);
				
				writeUserData(wr, "Col_V_Comment", isPublished);
				writeValueToCell(wr, isPublished);
				wr.endElement("row");
			}
			wr.endElement("rows");
			wr.endDocument();

		} catch (SAXException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return sWr.getBuffer().toString();
	}
	
	public static String getPublishPositionsNaukriXML(List<PositionData> positions) throws Exception {
		StringWriter sWr = new StringWriter();
		XMLWriter wr = new XMLWriter(sWr);
		String ruleText 		= null;
		String isPublished 	= null;
		String locations 	= null;
		String positionCode = null;
		String positionTitle = null;
		String ruleTitle  	 = null;
		try {
			wr.startDocument();
			wr.startElement("rows");
			for(PositionData positionData:positions){
				
				positionCode = Utils.getStringTrimmed(Utils.getBlankIfNull(positionData.getPositionReferenceCode()), 26);
				positionTitle = Utils.getStringTrimmed(Utils.getBlankIfNull(positionData.getPositionTitle()), 30);
				locations = Utils.getStringTrimmed(Utils.getBlankIfNull(positionData.getLocationName()), 15);
				ruleTitle = getRuleText(Utils.getBlankIfNull(positionData.getPositionId()));
				ruleText = Utils.getStringTrimmed(ruleTitle, 17);
				
				if(PositionConstants.POSITION_PUBLISHED_TO_WEBSITE.equals(positionData.getPublishToSite())){
					isPublished = TPLabels.getLabel("position.publish.label.published");
				}else{
					isPublished = TPLabels.getLabel("position.publish.label.unpublished");
				}
				
				AttributesImpl atr = new AttributesImpl();
				atr.addAttribute("", "id", "", "", positionData.getPositionId());
				wr.startElement("", "row", "", atr);
				writeValueToCell(wr, "");
				
				writeUserData(wr, "Col_I_Comment", Utils.getBlankIfNull(positionData.getPositionReferenceCode()));
				writeValueToCell(wr, positionCode);
				
				writeUserData(wr, "Col_II_Comment", Utils.getBlankIfNull(positionData.getPositionTitle()));
				writeValueToCell(wr, positionTitle);
				
				writeUserData(wr, "Col_III_Comment", Utils.getBlankIfNull(positionData.getLocationName()));
				writeValueToCell(wr, locations);
				
				writeUserData(wr, "Col_IV_Comment", isPublished);
				writeValueToCell(wr, isPublished);
				wr.endElement("row");
			}
			wr.endElement("rows");
			wr.endDocument();

		} catch (SAXException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return sWr.getBuffer().toString();
	}
	
	private static String getRuleText(String positionId) throws Exception {
		String ruleText = "";
		PositionManager positionManager = new PositionManager();
		MastersManager masterManager = new MastersManager();
		//set rule text
		PositionRuleData  ruleData = (PositionRuleData)positionManager.getPositionRule(positionId);
		if(ruleData!=null){
			String ruleType = ruleData.getRuleType();
			if(ruleType.equalsIgnoreCase(PositionConstants.RULE_GO_TO_DATABASE)){
				ruleText = "Resume will go to database";
			}else if(ruleType.equalsIgnoreCase(PositionConstants.RULE_GO_TO_INBOX)){
				ruleText = "Resume will go to inbox";
			}else if(ruleType.equalsIgnoreCase(PositionConstants.RULE_MANDATORY)){
				ruleText += "Short list if";
				if(!Utils.isBlankOrNull(ruleData.getMinExp())){
					ruleText += " Experience: "+ruleData.getMinExp() + "-";
				}
				if(!Utils.isBlankOrNull(ruleData.getMaxExp())){
					ruleText += ruleData.getMaxExp() + " yrs ;";
				}
				if(!Utils.isBlankOrNull(ruleData.getDegreeId())){
					DegreeData degreeData = (DegreeData)masterManager.getDegreeFromMaster(ruleData.getDegreeId());
					ruleText += " Education : "+degreeData.getItemName() + ";";
				}
				if(!Utils.isBlankOrNull(ruleData.getBranchId())){
					BranchesData branchData = (BranchesData)masterManager.getBranchFromMaster(ruleData.getBranchId());
					ruleText += " Branch : "+ branchData.getItemName() + ";";
				}
				if(!Utils.isBlankOrNull(ruleData.getCurrentLocation())){
					ruleText +=" Location :"+ruleData.getCurrentLocation() + ";";
				}
				
				ArrayList<RuleInstituteData> ruleIinstituteData = positionManager.getPositionRuleInstitutes(ruleData.getRuleId());
				if(ruleIinstituteData!=null && ruleIinstituteData.size()>0){
					String instituteList = "";
					for (Iterator iterator1 = ruleIinstituteData.iterator(); iterator1.hasNext();) {
						RuleInstituteData ruleInstituteData = (RuleInstituteData) iterator1.next();
						InstituteData insData = (InstituteData)masterManager.getInstituteData(ruleInstituteData.getInstituteId());
						if(instituteList.isEmpty()){
							instituteList += " Institutes : "+insData.getItemName();
						}else{
							instituteList+=";"+insData.getItemName();
						}
					}
					ruleText +=instituteList;
				}
			}
		}else{
			ruleText = "-";
		}
		return ruleText;
	}
	
	public static String getPublishPositionsToWalkInXML(List<PositionData> positions) throws Exception {
		StringWriter sWr = new StringWriter();
		XMLWriter wr = new XMLWriter(sWr);
		String isPublished 	= null;
		String positonPublished = null;
		String locations 	= null;
		String positionCode = null;
		String positionTitle = null;
		try {
			wr.startDocument();
			wr.startElement("rows");
			for(PositionData positionData:positions){
				
				positionCode = Utils.getStringTrimmed(Utils.getBlankIfNull(positionData.getPositionReferenceCode()), 29);
				positionTitle = Utils.getStringTrimmed(Utils.getBlankIfNull(positionData.getPositionTitle()), 33);
				locations = Utils.getStringTrimmed(Utils.getBlankIfNull(positionData.getLocationName()), 23);
				
				if(PositionConstants.POSITION_PUBLISHED_FOR_WALK_IN.equals(positionData.getPublishedForWalkIn())){
					positonPublished = PositionConstants.PUBLISHED;
					isPublished = TPLabels.getLabel("position.publish.label.published");
				}else{
					positonPublished = PositionConstants.UNPUBLISHED;
					isPublished = TPLabels.getLabel("position.publish.label.unpublished");
				}
				
				AttributesImpl atr = new AttributesImpl();
				atr.addAttribute("", "id", "", "", positionData.getPositionId());
				wr.startElement("", "row", "", atr);
				writeValueToCell(wr, "");
				
				writeUserData(wr, "Col_I_Comment", Utils.getBlankIfNull(positionData.getPositionReferenceCode()));
				writeValueToCell(wr, positionCode);
				
				writeUserData(wr, "Col_II_Comment", Utils.getBlankIfNull(positionData.getPositionTitle()));
				writeValueToCell(wr, positionTitle);
				
				writeUserData(wr, "Col_III_Comment", Utils.getBlankIfNull(positionData.getLocationName()));
				writeValueToCell(wr, locations);
				
				writeUserData(wr, "Col_IV_Comment", isPublished);
				writeValueToCell(wr, isPublished);
				writeUserData(wr,"positionPublished", positonPublished);
				wr.endElement("row");
			}
			wr.endElement("rows");
			wr.endDocument();

		} catch (SAXException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return sWr.getBuffer().toString();
	}
	
	public static String getPublishPositionsToSocialMediaXML(List<PositionData> positions) throws Exception {
		StringWriter sWr = new StringWriter();
		XMLWriter wr = new XMLWriter(sWr);
		String positionTitle = null;
		String lastPostedOn = null;
		String lastPostedDate = null;
		try {
			wr.startDocument();
			wr.startElement("rows");
			for(PositionData positionData:positions){
				
				positionTitle = Utils.getStringTrimmed(Utils.getBlankIfNull(positionData.getPositionTitle()), 33);
				lastPostedOn = Utils.getStringTrimmed(Utils.getBlankIfNull(positionData.getLastPostedSocialMediaType()), 33);
				if(positionData.getLastPostedSocialMediaDate() != null) {
					lastPostedDate = positionData.getLastPostedSocialMediaDate().toString();
				}else {
					lastPostedDate= null;
				}
				
				AttributesImpl atr = new AttributesImpl();
				atr.addAttribute("", "id", "", "", positionData.getPositionId());
				wr.startElement("", "row", "", atr);
				writeValueToCell(wr, "");
				
				writeUserData(wr, "Col_I_Comment", Utils.getBlankIfNull(positionTitle));
				writeValueToCell(wr, positionTitle);
				
				writeUserData(wr, "Col_II_Comment", Utils.getBlankIfNull(lastPostedOn));
				writeValueToCell(wr, lastPostedOn);
				
				writeUserData(wr, "Col_III_Comment", Utils.getBlankIfNull(lastPostedDate));
				writeValueToCell(wr, lastPostedDate);
				
				wr.endElement("row");
			}
			wr.endElement("rows");
			wr.endDocument();

		} catch (SAXException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return sWr.getBuffer().toString();
	}
	
	public static String getPublishPositionsToEmpPortalXML(List<PositionData> positions) throws Exception {
		StringWriter sWr = new StringWriter();
		XMLWriter wr = new XMLWriter(sWr);
		String isPublished 	= null;
		String positonPublished = null;
		String locations 	= null;
		String positionCode = null;
		String positionTitle = null;
		String employeeApplyRefer = "";
		try {
			wr.startDocument();
			wr.startElement("rows");
			for(PositionData positionData:positions){
				
				positionCode = Utils.getStringTrimmed(Utils.getBlankIfNull(positionData.getPositionReferenceCode()), 29);
				positionTitle = Utils.getStringTrimmed(Utils.getBlankIfNull(positionData.getPositionTitle()), 33);
				locations = Utils.getStringTrimmed(Utils.getBlankIfNull(positionData.getLocationName()), 23);
				
				if(PositionConstants.POSITION_PUBLISHED_FOR_EMPLOYEE_PORTAL.equals(positionData.getPublishedEmployeePortal())){
					positonPublished = PositionConstants.PUBLISHED;
					isPublished = TPLabels.getLabel("position.publish.label.published");
					if(PositionConstants.POSITIONS_EMPLOYEE_APPLY_REFER.equals(positionData.getEmployeeApplyRefer())){
						employeeApplyRefer = TPLabels.getLabel("position.publish.label.apply_and_refer");
					}else if(PositionConstants.POSITIONS_EMPLOYEE_APPLY.equals(positionData.getEmployeeApplyRefer())){
						employeeApplyRefer = TPLabels.getLabel("position.publish.label.apply");
					}else if(PositionConstants.POSITIONS_EMPLOYEE_REFER.equals(positionData.getEmployeeApplyRefer())){
						employeeApplyRefer = TPLabels.getLabel("position.publish.label.refer");
					}
				}else{
					positonPublished = PositionConstants.UNPUBLISHED;
					isPublished = TPLabels.getLabel("position.publish.label.unpublished");
					employeeApplyRefer = "";
				}
				
				AttributesImpl atr = new AttributesImpl();
				atr.addAttribute("", "id", "", "", positionData.getPositionId());
				wr.startElement("", "row", "", atr);
				writeValueToCell(wr, "");
				
				writeUserData(wr, "Col_I_Comment", Utils.getBlankIfNull(positionData.getPositionReferenceCode()));
				writeValueToCell(wr, positionCode);
				
				writeUserData(wr, "Col_II_Comment", Utils.getBlankIfNull(positionData.getPositionTitle()));
				writeValueToCell(wr, positionTitle);
				
				writeUserData(wr, "Col_III_Comment", Utils.getBlankIfNull(positionData.getLocationName()));
				writeValueToCell(wr, locations);
				
				writeUserData(wr, "Col_IV_Comment", isPublished);
				writeValueToCell(wr, isPublished);
				writeUserData(wr,"positionPublished", positonPublished);
				
				writeUserData(wr, "Col_V_Comment", employeeApplyRefer);
				writeValueToCell(wr, wr.doubleEscape(employeeApplyRefer));
				
				wr.endElement("row");
			}
			wr.endElement("rows");
			wr.endDocument();

		} catch (SAXException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return sWr.getBuffer().toString();
	}
	
	private static void writeValueToCell(final XMLWriter wr,String value) throws SAXException{
		wr.startElement("cell");
		wr.characters(wr.doubleEscape(value));
		wr.endElement("cell");
	}
	
	private static void writeUserData(final XMLWriter wr,String name,String data) throws SAXException{
		AttributesImpl atr = new AttributesImpl();
		atr.addAttribute("", "name", "", "", name);
		wr.startElement("", "userdata", "", atr);
		wr.characters(data);
		wr.endElement("userdata");
	}
}
