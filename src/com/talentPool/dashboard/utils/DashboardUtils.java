package com.talentPool.dashboard.utils;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import com.talentPool.budget.utils.BudgetUtils;
import com.talentPool.common.CommonConstants;
import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.constants.DateConstants;
import com.talentPool.common.db.SimpleDataObject;
import com.talentPool.common.properties.GlobalApplicationProperties;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.properties.TPLabels;
import com.talentPool.common.utils.CommonUtils;
import com.talentPool.common.utils.DateUtils;
import com.talentPool.common.utils.Utils;
import com.talentPool.common.xmlutils.XMLWriter;
import com.talentPool.dashboard.constants.DashboardConstants;
import com.talentPool.positions.PositionConstants;
import com.talentPool.positions.constants.PositionConfigurationConstants;
import com.talentPool.positions.manager.PositionScreenConfigurationManager;
import com.talentPool.todo.constants.ToDoConstants;
import com.talentPool.user.manager.ModuleSet;
import com.talentPool.user.manager.PermissionSet;

/**
 * @author Ajeet
 * 
 */
public class DashboardUtils {
	
	public static String getXMLForToDo(ArrayList<SimpleDataObject> todoList, PermissionSet permissionSet) {
		StringWriter sWr = new StringWriter();
		XMLWriter wr = new XMLWriter(sWr);
		try {
			wr.startDocument();
			wr.startElement("rows");
			if (todoList != null && todoList.size() > 0) {
				for (int i = 0; i < todoList.size(); i++) {
					SimpleDataObject data = todoList.get(i);
					String todoType = data.getString("todoType");
					//Budget approval type to-dos to be excluded when budget module is not active or not present
					if(todoType.equals(ToDoConstants.TODO_TYPE_BUDGET_APPROVAL)){
						if(!ModuleSet.isMODULE_BUDGET() || !BudgetUtils.isBudgetModuleActive())
							continue;
					}	
					int actionType = data.getInt("actionType");					
					String applicantId = data.getString("applicantId");
					String applicantName = data.getString("applicantName");
					String positionId = data.getString("positionId");
					String positionCode = data.getString("positionCode");
					String positionTitle = data.getString("positionTitle");
					String stepName = data.getString("stepName");
					String dueDate = DateUtils.getSystemDateFormatForDbDate(data.getString("dueDate"));
					String actualDueDate = data.getString("dueDate");
					String emailId= data.getString("emailId");
					String currentStepId= data.getString("currentStepId");
					String flagId= data.getString("flagId");
						
					AttributesImpl at = new AttributesImpl();
					at.addAttribute("", "id", "", "", ""+i);					
					wr.startElement("", "row", "", at);

					at = new AttributesImpl();
					at.addAttribute("", "name", "", "", "param");
					wr.startElement("", "userdata", "", at);
					if(DashboardConstants.ACTION_REQUIRED_REQUISITION_APPROVAL == actionType) {
						wr.characters(positionId);
					} else if(DashboardConstants.ACTION_CLEAR_DRAFT == actionType) {
						wr.characters(data.getString("emailId"));
					} else if(DashboardConstants.ACTION_REQUIRED_BUDGET_APPROVAL == actionType) {
						wr.characters(currentStepId); //currentStepId==BudgetItemId for budget approval
					} else {
						wr.characters(applicantId);
					}
					wr.endElement("userdata");
					
					String image = new String();
					String flagText = new String();
					if(!Utils.isBlankOrNull(flagId)){
						image = "<a href=\"#\" style=\"cursor:default;\"><img src=\"" + CommonUtils.getFlagImage(flagId) + "\" border=0></a>";
						flagText = CommonUtils.getFlagText(flagId);
					}else{
						image = "";
					}
					
					at = new AttributesImpl();
					at.addAttribute("", "name", "", "", "flags");
					wr.startElement("", "userdata", "", at);
					wr.characters(flagText);
					wr.endElement("userdata");
					
					at = new AttributesImpl();
					at.addAttribute("", "name", "", "", "actionRequired");
					wr.startElement("", "userdata", "", at);
					wr.characters("" + actionType);
					wr.endElement("userdata");
					
					at = new AttributesImpl();
					at.addAttribute("", "name", "", "", "Col_I_Title");
					wr.startElement("", "userdata", "", at);
					if(actionType != DashboardConstants.ACTION_REQUIRED_REQUISITION_APPROVAL ||
							actionType != DashboardConstants.ACTION_REQUIRED_BUDGET_APPROVAL) {
						wr.characters("" + applicantName);
					} else {
						wr.characters("NA");
					}									
					wr.endElement("userdata");
										
					String title = positionTitle + " [" + positionCode + "]"; 
					at = new AttributesImpl();
					at.addAttribute("", "name", "", "", "Col_II_Title");
					wr.startElement("", "userdata", "", at);
					if(actionType == DashboardConstants.ACTION_REQUIRED_BUDGET_APPROVAL) {
						wr.characters("");
					} else{
						wr.characters(title);	
					}					
					wr.endElement("userdata");					
					
					String onClick = "";
					String actionRequired = "";
						
					if(DashboardConstants.ACTION_REQUIRED_REQUISITION_APPROVAL == actionType) {
						onClick = "^javascript:onClickToDo(" + positionId + "," + actionType +");^_self";
						actionRequired = TPLabels.getLabel("dashboard.label.todo.requisition_approval"); 
					} else if(DashboardConstants.ACTION_REQUIRED_BUDGET_APPROVAL == actionType) {
						onClick = "^javascript:onClickToDo(" + currentStepId + "," + actionType +");^_self";
						actionRequired = TPLabels.getLabel("dashboard.label.todo.budget_approval"); 
					} else if(DashboardConstants.ACTION_CLEAR_DRAFT == actionType) {
						onClick = "^javascript:onClickToDo(" + emailId + "," + actionType +");^_self";
						actionRequired = TPLabels.getLabel("dashboard.label.todo.clear_draft");
					} else if(DashboardConstants.ACTION_REQUIRED_SCHEDULE == actionType) {
						onClick = "^javascript:onClickToDo(" + applicantId + "," + actionType +");^_self";
						actionRequired = TPLabels.getLabel("dashboard.label.todo.schedule");
					}else if(DashboardConstants.ACTION_REQUIRED_CONFIRM_ATTENDANCE == actionType) {
						onClick = "^javascript:onClickToDo(" + applicantId + "," + actionType +");^_self";
						actionRequired = TPLabels.getLabel("dashboard.label.todo.confirm");
					}else if(DashboardConstants.ACTION_REQUIRED_FEEDBACK == actionType) {
						onClick = "^javascript:onClickToDo(" + applicantId + "," + actionType +");^_self";
						actionRequired = TPLabels.getLabel("dashboard.label.todo.feedback");
					}else if(DashboardConstants.ACTION_ON_CONDUCT == actionType) {
						onClick = "^javascript:onClickToDo(" + applicantId + "," + actionType +");^_self";
						actionRequired = TPLabels.getLabel("dashboard.label.todo.conduct");
					}				
					
					if(DashboardConstants.ACTION_CLEAR_DRAFT != actionType){
						actionRequired += " "+stepName;
					}
					
					at = new AttributesImpl();
					at.addAttribute("", "name", "", "", "todo");
					wr.startElement("", "userdata", "", at);
					wr.characters(actionRequired);
					wr.endElement("userdata");
					
					wr.startElement("cell");
					wr.characters(image);
					wr.endElement("cell");
					
					String applicantLink = "<a href=\"#\" onclick=\"onClickApplicant('" +  applicantId + "');\" onmouseover=\"showAjaxTip(event,'" + applicantId + "')\" onmouseout=\"hideToolTip()\" >" + wr.doubleEscape(applicantName) + "</a>";	
					wr.startElement("cell");
					if(actionType != DashboardConstants.ACTION_REQUIRED_REQUISITION_APPROVAL ||
							actionType != DashboardConstants.ACTION_REQUIRED_BUDGET_APPROVAL) {
						wr.characters(applicantLink);
					} else {
						wr.characters("NA");
					}					
					wr.endElement("cell");
					
					wr.startElement("cell");
					if(actionType == DashboardConstants.ACTION_REQUIRED_BUDGET_APPROVAL) {
						wr.characters(wr.doubleEscape("NA"));
					}else{
						String positionName = null;
						if(GlobalConstants.ENABLED.equals(GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_SHOW_POSITION_CODE))) {
							positionName = positionCode;
						} else {
							positionName = positionTitle;
						}
						
						if(permissionSet.isPERMISSION_POSITION_DETAILS()) {
							wr.characters(wr.doubleEscape(positionName) + "^javascript:onClickPosition(" + positionId + ");^_self");
						} else {
							wr.characters(wr.doubleEscape(positionName));
						}
					}
					wr.endElement("cell");
					
					if (actionRequired.length() > 45) {
						actionRequired = actionRequired.substring(0, 43) + "...";
					}
					
					wr.startElement("cell");
					String actionLink = wr.doubleEscape(actionRequired)+ onClick ;
					wr.characters(actionLink);
					wr.endElement("cell");
					
					at = new AttributesImpl();
					at.addAttribute("", "name", "", "", "actualDueDate");
					wr.startElement("", "userdata", "", at);
					wr.characters(Utils.getBlankIfNull(actualDueDate));
					wr.endElement("userdata");
					
					wr.startElement("cell");
					wr.characters(dueDate);
					wr.endElement("cell");

					wr.startElement("cell");
					wr.characters(dueDate);
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
	
	public static String getXmlForAggregateToDos(String listType, List<SimpleDataObject> applicantsToDos, PermissionSet permissionSet) {
		StringWriter sWr = new StringWriter();
		XMLWriter wr = new XMLWriter(sWr);		
		int totalCount = 0;
		String xml = "";
		try {			
			wr.startDocument();
			wr.startElement("items");
			if (applicantsToDos != null && applicantsToDos.size() > 0) {				
				for (int indx = 0; indx < applicantsToDos.size(); indx++) {
					SimpleDataObject data = applicantsToDos.get(indx);
					int actionType = data.getInt("actionType");
					String count = data.getString("count");

					wr.startElement("item");

					String currentId="";
					String todo = "";
					if (DashboardConstants.TODO_LIST_TYPE_ACTION.equals(listType)) {						
						currentId = ""+actionType;						
						if(DashboardConstants.ACTION_REQUIRED_SCHEDULE == actionType) {
							todo = TPLabels.getLabel("dashboard.label.todo.schedule");
						} else if(DashboardConstants.ACTION_REQUIRED_FEEDBACK == actionType) {
							todo = TPLabels.getLabel("dashboard.label.todo.feedback");
						} else if(DashboardConstants.ACTION_REQUIRED_CONFIRM_ATTENDANCE == actionType) {
							todo = TPLabels.getLabel("dashboard.label.todo.confirm");
						} else if(DashboardConstants.ACTION_REQUIRED_REQUISITION_APPROVAL == actionType) {
							todo = TPLabels.getLabel("dashboard.label.todo.requisition_approval");
						} else if(DashboardConstants.ACTION_REQUIRED_BUDGET_APPROVAL == actionType) {
							todo = TPLabels.getLabel("dashboard.label.todo.budget_approval");
						} else if(DashboardConstants.ACTION_CLEAR_DRAFT == actionType) {
							todo = TPLabels.getLabel("dashboard.label.todo.clear_draft");
						}
					} else if (DashboardConstants.TODO_LIST_TYPE_STEP.equals(listType)) {						
						currentId = Utils.escapeHTML(data.getString("stepName")).replaceAll(" ", "__");							
						todo = data.getString("stepName");		
					} else if (DashboardConstants.TODO_LIST_TYPE_POSITION.equals(listType)) {
						currentId = data.getString("positionId");
						todo = data.getString("positionTitle");		
						if(DashboardConstants.ACTION_REQUIRED_BUDGET_APPROVAL == actionType) {
							todo = TPLabels.getLabel("dashboard.label.todo.budget_approval");
							currentId = ""+actionType;
						}
					} 
					
					wr.startElement("outerContentDivId");
					wr.characters("outerContentToDoId_" + currentId);
					wr.endElement("outerContentDivId");

					wr.startElement("contentDivId");
					wr.characters("contentToDoId_" + currentId);
					wr.endElement("contentDivId");

					wr.startElement("expand");
					wr.characters("<a href=\"#\" id=\"linktodo_" + currentId + "\" onclick=\"javascript:expandToDo('" + currentId + "'," + listType + ");\">" + "<img src=\"images/ico_plus.gif\" id=" + "imgtodo_" + currentId + " style=\"border: 0\">");
					wr.endElement("expand");
					
					wr.startElement("f_todo");
					wr.characters(wr.doubleEscape(todo) + " ["+count+"]");
					wr.endElement("f_todo");

					wr.startElement("todo");
					wr.characters(wr.doubleEscape(todo) + " <FONT COLOR=\"gray\"> ["+count+"]</FONT>");
					wr.endElement("todo");

					wr.endElement("item");
					totalCount += Integer.parseInt(count);
				}
			}
			wr.endElement("items");
			wr.endDocument();
		} catch (SAXException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		xml = totalCount + "|" + sWr.toString();	
		return xml;
	}
	
	public static String getXmlForGroupToDos(List<SimpleDataObject> applicantsToDos, PermissionSet permissionSet) {
		StringWriter sWr = new StringWriter();
		XMLWriter wr = new XMLWriter(sWr);
		try {
			wr.startDocument();
			wr.startElement("items");
			if (applicantsToDos != null && applicantsToDos.size() > 0) {
				for (int indx = 0; indx < applicantsToDos.size(); indx++) {
					SimpleDataObject data = applicantsToDos.get(indx);
						int actionType = data.getInt("actionType");
						String applicantId = data.getString("applicantId");
						String applicantName = data.getString("applicantName");
						String positionId = data.getString("positionId");
						String positionTitle = data.getString("positionTitle");
						String positionCode = data.getString("positionCode");
						String stepName = data.getString("stepName");
						String emailId = data.getString("emailId");
						String currentStepId= data.getString("currentStepId");
						String flagId= data.getString("flagId");
						String dueDate = DateUtils.getSystemDateFormatForDbDate(data.getString("dueDate"));
						
						String onClick = "";
						String actionRequired = "";
							
						if(DashboardConstants.ACTION_REQUIRED_REQUISITION_APPROVAL == actionType) {
							onClick = "javascript:onClickToDo(" + positionId + "," + actionType +");";
							actionRequired = TPLabels.getLabel("dashboard.label.todo.requisition_approval"); 
						} else if(DashboardConstants.ACTION_REQUIRED_BUDGET_APPROVAL == actionType) {
							onClick = "javascript:onClickToDo(" + currentStepId + "," + actionType +");";
							actionRequired = TPLabels.getLabel("dashboard.label.todo.budget_approval"); 
						} else if(DashboardConstants.ACTION_CLEAR_DRAFT == actionType) {
							onClick = "javascript:onClickToDo(" + emailId + "," + actionType +");";
							actionRequired = TPLabels.getLabel("dashboard.label.todo.clear_draft");
						} else if(DashboardConstants.ACTION_REQUIRED_SCHEDULE == actionType) {
							onClick = "javascript:onClickToDo(" + applicantId + "," + actionType +");";
							actionRequired = TPLabels.getLabel("dashboard.label.todo.schedule");
						}else if(DashboardConstants.ACTION_REQUIRED_CONFIRM_ATTENDANCE == actionType) {
							onClick = "javascript:onClickToDo(" + applicantId + "," + actionType +");";
							actionRequired = TPLabels.getLabel("dashboard.label.todo.confirm");
						}else if(DashboardConstants.ACTION_REQUIRED_FEEDBACK == actionType) {
							onClick = "javascript:onClickToDo(" + applicantId + "," + actionType +");";
							actionRequired = TPLabels.getLabel("dashboard.label.todo.feedback");
						}else if(DashboardConstants.ACTION_ON_CONDUCT == actionType) {
							onClick = "javascript:onClickToDo(" + applicantId + "," + actionType +");";
							actionRequired = TPLabels.getLabel("dashboard.label.todo.conduct");
						}		
						
						if(DashboardConstants.ACTION_CLEAR_DRAFT != actionType){
							actionRequired += " "+stepName;
						}

						
						wr.startElement("item");
						
						String applicantLink = "<a href=\"#\" onclick=\"onClickApplicant('" +  applicantId + "');\" onmouseover=\"showAjaxTip(event,'" + applicantId + "')\" onmouseout=\"hideToolTip()\" >" + wr.doubleEscape(applicantName) + "</a>";
						wr.startElement("applicant");
						if (!Utils.isBlankOrNull(applicantName)) {
							wr.characters(applicantLink);							
						}else{
							wr.characters("&nbsp;");
						}
						wr.endElement("applicant");
						
						if (Utils.isBlankOrNull(applicantName)) {
							applicantName = "";
						}

						String image = new String();
						String flagText = new String();
						if(!Utils.isBlankOrNull(flagId)){
							image = "<a href=\"#\" style=\"cursor:default;\"><img src=\"" + CommonUtils.getFlagImage(flagId) + "\" border=0></a>";
							flagText = CommonUtils.getFlagText(flagId);
						}else{
							image = "";
						}
						
						wr.startElement("f_flag");
						wr.characters(wr.doubleEscape(flagText));
						wr.endElement("f_flag");
						
						wr.startElement("flag");
						wr.characters(image);
						wr.endElement("flag");
						
						wr.startElement("f_applicant");
						wr.characters(wr.doubleEscape(""));
						wr.endElement("f_applicant");
						
						wr.startElement("f_clientTitle");
						wr.characters(wr.doubleEscape(positionTitle + " [" + positionCode + "]"));						
						wr.endElement("f_clientTitle");
						
						String pName = positionTitle;
						
						if (!Utils.isBlankOrNull(pName)) {
							if(pName.length() > 34){
								pName = pName.substring(0, 31) + "...";
							}
						}else{
							pName = "NA";
						}
						
						wr.startElement("clientTitle");
						wr.characters("<a href=\"#\" onclick=\"javascript:onClickPosition(\'" + positionId + "\');\">" + wr.doubleEscape(pName) + "</a>");
						wr.endElement("clientTitle");
					
						wr.startElement("f_actionRequired");
						wr.characters(wr.doubleEscape(actionRequired));
						wr.endElement("f_actionRequired");
						
						if (actionRequired.length() > 45) {
							actionRequired = actionRequired.substring(0, 43) + "...";
						}
						
						wr.startElement("actionRequired");
						wr.characters("<a href=\"#\" onclick="+onClick+">" + wr.doubleEscape(actionRequired) + "</a>");
						wr.endElement("actionRequired");


						wr.startElement("f_dueDate");
						wr.characters(dueDate);
						wr.endElement("f_dueDate");
						
						dueDate = Utils.isBlankOrNull(dueDate) ? "&nbsp;" : dueDate;						
						wr.startElement("dueDate");
						wr.characters(dueDate);
						wr.endElement("dueDate");

						wr.endElement("item");
					}
				}
			wr.endElement("items");
			wr.endDocument();

		} catch (SAXException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return sWr.toString();
	}
	
	/**
	 * @param positions
	 * @param positionGrouping
	 * @param permissionSet
	 * @return
	 */
	public static String getXmlForPositionSummaryGrouped(List<SimpleDataObject> positions,String positionGrouping, PermissionSet permissionSet) {
		StringWriter sWr = new StringWriter();
		XMLWriter wr = new XMLWriter(sWr);		
		int totalCount = 0;
		int totalJoined = 0;
		int totalRejected = 0;
		int totalInprocess = 0;
		int totalOffered = 0;
		int totalVacancies=0;
		String xml = "";
		try {			
			wr.startDocument();
			wr.startElement("items");
			if (positions != null && positions.size() > 0) {				
				for (int indx = 0; indx < positions.size(); indx++) {
					SimpleDataObject data = positions.get(indx);
					String groupedName =""; 
					String count = data.getString("count");

					wr.startElement("item");

					String currentId="";
					if (DashboardConstants.POSITION_GRID_TYPE_DEPARTMENT.equals(positionGrouping)) {						
						currentId = ""+data.getString("departmentId");
						groupedName = data.getString("departmentName");
					} else if (DashboardConstants.POSITION_GRID_TYPE_SUB_DEPARTMENT.equals(positionGrouping)) {
						currentId = ""+data.getString("departmentId");
						groupedName = data.getString("departmentName");
					} else if (DashboardConstants.POSITION_GRID_TYPE_SUB_SUB_DEPARTMENT.equals(positionGrouping)) {
						currentId = ""+data.getString("departmentId");
						groupedName = data.getString("departmentName");
					}else if (DashboardConstants.POSITION_GRID_TYPE_SUB4_DEPARTMENT.equals(positionGrouping)) {
						currentId = ""+data.getString("departmentId");
						groupedName = data.getString("departmentName");
					}else if (DashboardConstants.POSITION_GRID_TYPE_SUB3_DEPARTMENT.equals(positionGrouping)) {
						currentId = ""+data.getString("departmentId");
						groupedName = data.getString("departmentName");
					}else if (DashboardConstants.POSITION_GRID_TYPE_POSITION_OWNER.equals(positionGrouping)) {
						currentId = ""+data.getString("positionOwnerId");
						groupedName = data.getString("positionOwnerName");
					}else if (DashboardConstants.POSITION_GRID_TYPE_REQUESTED.equals(positionGrouping)) {
						currentId = ""+data.getString("reqestedById");
						groupedName = data.getString("reqestedByName");
					}else if (DashboardConstants.POSITION_GRID_TYPE_LOCATION.equals(positionGrouping)) {
						currentId = ""+data.getString("locationId");
						groupedName = data.getString("locationName");
					}else if (DashboardConstants.POSITION_GRID_TYPE_POSITION_TYPE.equals(positionGrouping)) {
						currentId = ""+data.getString("positionTypeExtInt");
						if(PositionConstants.POSITIONS_TYPE_INTERNAL.equals(currentId)){
							groupedName = TPLabels.getLabel("position.description.position_type_internal");
						}else if(PositionConstants.POSITIONS_TYPE_EXTERNAL.equals(currentId)){
							groupedName = TPLabels.getLabel("position.description.position_type_external");
						}else{
							groupedName = TPLabels.getLabel("position.description.position_type_not_set");
						}
						
					}
					
					String noOfOpenings = data.getString("noOfPositions");
					int shortlist = data.getInt("shortlistCandidates");
					int select = data.getInt("selectCandidates");
					//String inProcess = data.getString("candidates");
					String inProcess = ""+(shortlist+select);
					String offered = data.getString("offered");
					String joined = data.getString("joined");
					String rejected = data.getString("rejected");
					
					wr.startElement("positionOuterContentDivId");
					wr.characters("positionOuterContentDivId_" + currentId);
					wr.endElement("positionOuterContentDivId");

					wr.startElement("positionContentDivId");
					wr.characters("positionContentDivId_" + currentId);
					wr.endElement("positionContentDivId");

					wr.startElement("expand");
					wr.characters("<a href=\"#\" id=\"linkPositionGroup_" + currentId + "\" onclick=\"javascript:expandPostionGroup('" + currentId + "'," + positionGrouping + ");\">" + "<img src=\"images/ico_plus.gif\" id=" + "imgPostionGroup_" + currentId + " style=\"border: 0\">");
					wr.endElement("expand");
					
					wr.startElement("groupedNameTitle");
					wr.characters(wr.doubleEscape(groupedName) + " ["+count+"]");
					wr.endElement("groupedNameTitle");

					wr.startElement("groupedName");
					wr.characters(wr.doubleEscape(groupedName) + " <FONT COLOR=\"gray\"> ["+count+"]</FONT>");
					wr.endElement("groupedName");
					
					wr.startElement("noOfOpenings");
					wr.characters(noOfOpenings);
					wr.endElement("noOfOpenings");
					
					wr.startElement("inProcess");
					wr.characters(inProcess);
					wr.endElement("inProcess");
					
					wr.startElement("offered");
					wr.characters(offered);
					wr.endElement("offered");
					
					wr.startElement("joined");
					wr.characters(joined);
					wr.endElement("joined");
					
					wr.startElement("rejected");
					wr.characters(rejected);
					wr.endElement("rejected");

					wr.endElement("item");
					totalCount += Integer.parseInt(count);
					totalJoined+=Integer.parseInt(joined);
					totalRejected+=Integer.parseInt(rejected);
					totalInprocess+=Integer.parseInt(inProcess);
					totalOffered+=Integer.parseInt(offered);
					totalVacancies+=Integer.parseInt(noOfOpenings);
				}
			}
			
			if (!DashboardConstants.POSITION_GRID_TYPE_LOCATION.equals(positionGrouping)) {
				wr.startElement("total");
				wr.startElement("vacancies");
				wr.characters("" + totalVacancies);
				wr.endElement("vacancies");
				wr.startElement("joined");
				wr.characters("" + totalJoined);
				wr.endElement("joined");
				wr.startElement("inprocess");
				wr.characters("" + totalInprocess);
				wr.endElement("inprocess");
				wr.startElement("offered");
				wr.characters("" + totalOffered);
				wr.endElement("offered");
				wr.startElement("rejected");
				wr.characters("" + totalRejected);
				wr.endElement("rejected");			
				wr.endElement("total");
			}
			
			wr.endElement("items");
			wr.endDocument();
		} catch (SAXException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		xml = totalCount + "|" + sWr.toString();	
		return xml;
	}
	
	/**
	 * @param positions
	 * @param permissionSet
	 * @return
	 */
	public static String getXmlForPositionDetails(List<SimpleDataObject> positions, PermissionSet permissionSet) {
		StringWriter sWr = new StringWriter();
		XMLWriter wr = new XMLWriter(sWr);
		try {
			wr.startDocument();
			wr.startElement("items");
			if (!Utils.isListEmptyOrNull(positions)) {
				for (SimpleDataObject data : positions) {
					String positionId = data.getString("positionId");
					String positionCode = data.getString("positionCode");
					String positionTitle = data.getString("positionTitle");
					String noOfOpenings = data.getString("noOfPositions");
					int shortlist = data.getInt("shortlistCandidates");
					int select = data.getInt("selectCandidates");
					//String inProcess = data.getString("candidates");
					String inProcess = ""+(shortlist+select);
					String offered = data.getString("offered");
					String joined = data.getString("joined");
					String rejected = data.getString("rejected");
					//String recruiters = data.getString("recruiters");
					String positionStatus = data.getString("positionStatus");
					String hireByDate = (data.getString("expiryDate") == null) ? "" : data.getString("expiryDate");
					String positionPriority = data.getString("positionPriority");
					String ImgPositionPriority = "";
					String priorityLevel = "";
					String editPriority = "";
					String rowCss = "";
					String defaultTitle=TPLabels.getLabel("dashboard.tooltip.details");

						wr.startElement("item");
						
						if (!Utils.isBlankOrNull(positionPriority)) {
							if (positionPriority.equals(PositionConstants.POSITION_LEVEL_HIGH)) {
								ImgPositionPriority = "<img src=\"images/ico_priority_high.jpg\" border=0>";
								priorityLevel = "HIGH";
							} else if (positionPriority.equals(PositionConstants.POSITION_LEVEL_MEDIUM)) {
								ImgPositionPriority = "<img src=\"images/ico_priority_medium.jpg\" border=0>";
								priorityLevel = "MEDIUM";
							} else if (positionPriority.equals(PositionConstants.POSITION_LEVEL_LOW)) {
								ImgPositionPriority = "<img src=\"images/ico_priority_low.jpg\" border=0>";
								priorityLevel = "LOW";
							}
							if (permissionSet.isPERMISSION_POSITION_PRIORITY()) {
								editPriority = "<a href=\"#\" onclick=\"javascript:onClickPositionPriority(" + positionId + ");\" title=\"Position Priority\">" + ImgPositionPriority + "</a>";
							} else {
								editPriority = ImgPositionPriority;
							}
						} else {
							editPriority = "";
						}
						
						wr.startElement("priority");
						wr.characters(editPriority);							
						wr.endElement("priority");
						

						wr.startElement("f_priority");
						wr.characters(wr.doubleEscape(priorityLevel));
						wr.endElement("f_priority");
						
						String position = positionTitle;
						if(GlobalConstants.ENABLED.equals(GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_SHOW_POSITION_CODE))) {
							position = positionCode;
						}
						
						
						wr.startElement("f_position");
						wr.characters(wr.doubleEscape(positionTitle+" "+TPLabels.getLabel("common.openingSquareBracket")+positionCode+TPLabels.getLabel("common.closingSquareBracket")));						
						wr.endElement("f_position");
						
						position = (position.length() > 45) ? position.substring(0, 42) + "..." : position;
						
						String positionAnchor ="<a href=\"#\" onclick=\"javascript:onClickPosition(" + positionId + ");\" >";
						wr.startElement("position");
						if (permissionSet.isPERMISSION_POSITION_DETAILS()) {
							wr.characters(positionAnchor+wr.doubleEscape(position)+"</a>");
						} else {
							wr.characters(wr.doubleEscape(position));
						}
						wr.endElement("position");
						
						
						wr.startElement("noOfOpenings");
						wr.characters(noOfOpenings);
						wr.endElement("noOfOpenings");
						
						wr.startElement("inProcess");
						if (inProcess.equals("0") || !permissionSet.isPERMISSION_SELECT()) {
							wr.characters(inProcess);
						} else {
							wr.characters("<a href=\"#\" onclick=\"javascript:onClickCandidates(" + positionId + ");\">" + inProcess + "</a>");
						}
						wr.endElement("inProcess");
						
						wr.startElement("offered");
						if (offered.equals("0") || !permissionSet.isPERMISSION_HIRE()) {
							wr.characters(offered);
						} else {
							wr.characters("<a href=\"#\" onclick=\"javascript:onClickHired(" + positionId + ");\">" + offered + "</a>");
						}
						wr.endElement("offered");
						
						wr.startElement("joined");
						if (joined.equals("0") || !permissionSet.isPERMISSION_HIRE() || !permissionSet.isPERMISSION_SHOW_JOINED_CANDIDATES()) {
							wr.characters(joined);
						} else {
							wr.characters("<a href=\"#\" onclick=\"javascript:onClickJoined(" + positionId + ");\">" + joined + "</a>");
						}
						wr.endElement("joined");
						
						wr.startElement("rejected");
						if (rejected.equals("0") || !permissionSet.isPERMISSION_VIEW_REJECTED_CANDIDATES()) {
							wr.characters(rejected);
						} else {
							wr.characters("<a href=\"#\" onclick=\"javascript:onClickRejected(" + positionId + ");\">" + rejected + "</a>");
						}
						wr.endElement("rejected");
						
						if(!Utils.isBlankOrNull(hireByDate) && PositionConstants.POSITION_STATUS_OPENED.equals(positionStatus)){
							Date dt = Utils.convertToDate(hireByDate, DateConstants.DB_DATE_TIME_PATTERN);
							if(dt.before(Calendar.getInstance().getTime())){
								positionStatus = PositionConstants.POSITION_STATUS_OVERDUE;
							}
						}
						
						if(PositionConstants.POSITION_STATUS_CLOSED.equals(positionStatus) ||PositionConstants.POSITION_STATUS_REJECTED.equals(positionStatus)){
							rowCss="disabledrow";
						}else if(PositionConstants.POSITION_STATUS_HOLD.equals(positionStatus)){
					 		rowCss="onholdrow";
						}else if(PositionConstants.POSITION_STATUS_OVERDUE.equals(positionStatus)){
							rowCss="overdue";
						}
						
						wr.startElement("rowCss");
						wr.characters(rowCss);
						wr.endElement("rowCss");
						
						wr.startElement("defaultTitle");
						wr.characters(defaultTitle);
						wr.endElement("defaultTitle");
						
						wr.endElement("item");
					}
				}
			
			wr.endElement("items");
			wr.endDocument();

		} catch (SAXException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return sWr.toString();
	}
	
	public static String getPositionSummaryGroupByJSArray(){
		StringBuffer sb = new StringBuffer();
		try {
			sb.append("[");
			HashMap<String, String> options = getPositionSummaryGroupByOptions();
			Iterator<String> itr = options.keySet().iterator();
			while(itr.hasNext()) {
				String key = itr.next();
				Utils.getJSArraySelectOption(key, options.get(key), sb);
				sb.append(CommonConstants.DEFAULT_DELIMITER);
			}
			sb.replace(sb.length()-1, sb.length(), ""); // remove trailing comma (for it to work with IE8)
			sb.append("]");			
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			sb = new StringBuffer("new Array()");
		}
		return sb.toString();
	}
	
	/**
	 * @return HashMap of groupBy options for Position Summary
	 */
	public static HashMap<String, String> getPositionSummaryGroupByOptions() {
		HashMap<String, String> options = new LinkedHashMap<String, String>();
		options.put(DashboardConstants.POSITION_GRID_TYPE_LIST, "-----Select-----");
		options.putAll(CommonUtils.getDepartmentLevelOptions());
		options.put(DashboardConstants.POSITION_GRID_TYPE_REQUESTED, TPLabels.getLabel("dashboard.postionSummary.label.requestedBy"));
		options.put(DashboardConstants.POSITION_GRID_TYPE_LOCATION, TPLabels.getLabel("dashboard.postionSummary.label.location"));
		if(PositionScreenConfigurationManager.isDescriptionFieldShow(PositionConfigurationConstants.FIELD_POSITION_OWNER)){
			options.put(DashboardConstants.POSITION_GRID_TYPE_POSITION_OWNER, TPLabels.getLabel("global.position_owner"));
		}
		if(PositionScreenConfigurationManager.isDescriptionFieldShow(PositionConfigurationConstants.FIELD_POSITION_TYPE_EXT_INT)){
			options.put(DashboardConstants.POSITION_GRID_TYPE_POSITION_TYPE, TPLabels.getLabel("position.description.position_type_ext_int"));
		}
		
		return options;
	}
}

