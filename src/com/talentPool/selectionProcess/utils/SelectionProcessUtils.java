/**
 * 
 */
package com.talentPool.selectionProcess.utils;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.db.SimpleDataObject;
import com.talentPool.common.properties.GlobalApplicationProperties;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.properties.TPLabels;
import com.talentPool.common.utils.CommonUtils;
import com.talentPool.common.utils.DateUtils;
import com.talentPool.common.utils.Utils;
import com.talentPool.common.xmlutils.XMLWriter;
import com.talentPool.positions.PositionConstants;
import com.talentPool.positions.dataobject.StepData;
import com.talentPool.positions.dataobject.UserData;
import com.talentPool.positions.manager.PositionManager;
import com.talentPool.selectionProcess.SelectionProcessConstants;
import com.talentPool.user.dataobject.LoginData;
import com.talentPool.user.manager.PermissionSet;
import static com.talentPool.common.CommonConstants.DEFAULT_DELIMITER;
import static com.talentPool.common.CommonConstants.NEW_ARRAY;

/**
 * @author shivaprasad
 * 
 */
public class SelectionProcessUtils {

	public static String getInvalidUserNames(String[] arrNames, ArrayList<LoginData> users) {
		String invalidUsers = "";
		try {
			for (int i = 0; i < arrNames.length; i++) {
				String name = arrNames[i];
				if (!Utils.isBlankOrNull(name)) {
					boolean validUser = false;
					for (int j = 0; j < users.size(); j++) {
						LoginData loginData = users.get(j);
						if (!Utils.isBlankOrNull(loginData.getName())) {
							if (loginData.getName().trim().equalsIgnoreCase(name.trim())) {
								validUser = true;
								break;
							}
						}
					}
					if (!validUser) {
						if (invalidUsers.length() > 0) {
							invalidUsers += ", ";
						}
						invalidUsers += name;
					}
				}
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("error while getting invalid names from cc names in messages", e);
		}

		return invalidUsers;
	}

	public static String getInvalidUsersXML(String invalidUsers) {
		StringWriter sWr = new StringWriter();
		try {
			XMLWriter wr = new XMLWriter(sWr);
			wr.startDocument();
			wr.startElement("names");
			wr.startElement("name");
			wr.characters(invalidUsers);
			wr.endElement("name");
			wr.endElement("names");
			wr.endDocument();
		} catch (SAXException e) {
			TPLogger.getLogger().error("Error while getting xml from invalidUsers", e);
		}
		return sWr.getBuffer().toString();
	}

	public static ArrayList<String> getUserIdsFromNames(String[] arrNames, ArrayList<LoginData> users) {
		ArrayList<String> userIds = new ArrayList<String>();
		try {
			for (int i = 0; i < arrNames.length; i++) {
				String name = arrNames[i];
				if (!Utils.isBlankOrNull(name)) {
					for (int j = 0; j < users.size(); j++) {
						LoginData loginData = users.get(j);
						if (!Utils.isBlankOrNull(loginData.getName())) {
							if (loginData.getName().trim().equalsIgnoreCase(name.trim())) {
								if (!userIds.contains(loginData.getUserId())) {
									userIds.add(loginData.getUserId());
								}
								break;
							}
						}
					}
				}
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("error while getting userId from names in messages", e);
		}
		return userIds;
	}
	
	public static String getXMLForFilter(List<SimpleDataObject> filters, String filterFor, String departmentId, 
			String positionId, String stepName, String locationTitle, String positionTypeExtInt, String actionRequired,
			String selectedUserId,String sourceTypeId) {
		StringWriter sWr = new StringWriter();
		XMLWriter wr = new XMLWriter(sWr);
		try {
			wr.startDocument();
			wr.startElement("filters");
			String selectedId = "";
			if (filterFor.equals(SelectionProcessConstants.FILTER_DEPARTMENT)) {
				selectedId = departmentId;
			} else if (filterFor.equals(SelectionProcessConstants.FILTER_POSITION)) {
				selectedId = positionId;
			} else if (filterFor.equals(SelectionProcessConstants.FILTER_STEP)) {
				selectedId = stepName;
			} else if (filterFor.equals(SelectionProcessConstants.FILTER_LOCATION)) {
				selectedId = locationTitle;
			} else if (filterFor.equals(SelectionProcessConstants.FILTER_POSITION_TYPE)) {
				selectedId = positionTypeExtInt;
			} else if (filterFor.equals(SelectionProcessConstants.FILTER_ACTION)) {
				selectedId = actionRequired;
			} else if (filterFor.equals(SelectionProcessConstants.FILTER_USER)) {
				selectedId = selectedUserId;
			} else if (filterFor.equals(SelectionProcessConstants.FILTER_SOURCE)) {
				selectedId = sourceTypeId;
			}

			for (int i = 0; filters != null && i < filters.size(); i++) {
				SimpleDataObject sdo = filters.get(i);
				
				String filterId = sdo.getString("filterId");
				String filterFullName = sdo.getString("filterFullName");
				String filterShortName = sdo.getString("filterShortName");
				
				if (filterFor.equals(SelectionProcessConstants.FILTER_POSITION_TYPE)){
					if(filterId.equals(PositionConstants.POSITIONS_TYPE_INTERNAL)){
						filterFullName = TPLabels.getLabel("position.description.position_type_internal");
						filterShortName = TPLabels.getLabel("position.description.position_type_internal");
					}else if(filterId.equals(PositionConstants.POSITIONS_TYPE_EXTERNAL)){
						filterFullName = TPLabels.getLabel("position.description.position_type_external");
						filterShortName= TPLabels.getLabel("position.description.position_type_external");						
					}else{
						filterFullName = TPLabels.getLabel("position.description.position_type_not_set");
						filterShortName= TPLabels.getLabel("position.description.position_type_not_set");
					}
				}
				
				StringBuffer sb = new StringBuffer();
				boolean isSelected = false;
				String name = filterFullName;
				if (filterFor.equals(SelectionProcessConstants.FILTER_POSITION) &&
						GlobalConstants.ENABLED.equals(GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_SHOW_POSITION_CODE))) {
					name = filterShortName;
				}

				if (filterId.equals(selectedId)) {
					isSelected = true;
				}

				filterId = filterId.replaceAll("\"", "&quot;").replaceAll("'", "\\\\'");
				if (isSelected) {
					sb.append("<font class=red>" + wr.doubleEscape(name) + "</font>");
				} else {
					sb.append("<a href=\"#\" onclick=\"applyFilter('" + filterFor + "','" + wr.doubleEscape(filterId) + "');\" class=\"green\">" + wr.doubleEscape(name) + "</a>");
				}
				wr.startElement("filter");

				wr.startElement("link");
				wr.characters(sb.toString());
				wr.endElement("link");

				wr.startElement("title");
				if (filterFor.equals(SelectionProcessConstants.FILTER_POSITION)) {
					wr.characters(wr.doubleEscape(filterFullName + " [" + filterShortName + "]"));
				} else {
					wr.characters(wr.doubleEscape(name));
				}						
				wr.endElement("title");

				wr.endElement("filter");
			}
			wr.endElement("filters");
			wr.endDocument();
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);			
		}
		return sWr.getBuffer().toString();
	}
	
	public static String getJSArrayForSelectionStageOnSelectTab() {
		ArrayList<String> ids = new ArrayList<String>();
		ArrayList<String> names = new ArrayList<String>();
		ids.add(PositionConstants.STEP_LEVEL_SHORTLIST);
		ids.add(PositionConstants.STEP_LEVEL_SELECT);
		names.add(TPLabels.getLabel("common.shortlist"));
		names.add(TPLabels.getLabel("common.select"));
		String JSStageCriteria = CommonUtils.getListJavaScriptArray(ids, names);
		return JSStageCriteria;
	}
	
	public static String getInvolvedUsersFilterXml(List filter) {
		StringWriter sWr = new StringWriter();
		XMLWriter wr = new XMLWriter(sWr);		
		try {
			wr.startDocument();
			wr.startElement("rows");
			if (filter != null && filter.size() > 0) {
				for (int i = 0; i < filter.size(); i++) {
					SimpleDataObject sDo = (SimpleDataObject) filter.get(i);
					
					String involvedUserId = (sDo.getString("filterId") == null) ? "" : sDo.getString("filterId");
					String involvedUserName = (sDo.getString("filterFullName") == null) ? "" : sDo.getString("filterFullName");					
					
					AttributesImpl atr = new AttributesImpl();					
					atr.addAttribute("", "id", "", "", String.valueOf(involvedUserId));
					wr.startElement("", "row", "", atr);
										
					atr = new AttributesImpl();
					atr.addAttribute("", "name", "", "", "activeUserName");
					wr.startElement("", "userdata", "", atr);
					wr.characters(involvedUserName);
					wr.endElement("userdata");
					
					wr.startElement("cell");
					wr.characters(wr.doubleEscape(involvedUserName));
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
	
	/**
	 * Builds a JS Array of SelectOptions of <code>steps</code>. 
	 * It appends "Select Action", "Keep On Hold" in the start and "Move to other position", "Reject"
	 * @param steps
	 * @return
	 */
	public static String getJsArrayForSelectionSteps(final List<StepData> steps,final PermissionSet permissionSet){
		StringBuffer sb = new StringBuffer();
		String stepId = null;
		try {
			sb.append("[");
			Utils.getJSArraySelectOption(SelectionProcessConstants.STEP_INVALID, TPLabels.getLabel("selection_feedback.label.select_action"), sb);
			for (StepData stepData : steps) {
				stepId = String.valueOf(stepData.getStepId());
				if (SelectionProcessConstants.STEP_TITLE_JOINED.equalsIgnoreCase(stepData.getStepTitle())) {
		          stepId = "";
		        }
				Utils.getJSArraySelectOption(stepId, TPLabels.getLabel("selection_feedback.label.moveTo")+" "+Utils.escapeHTML(stepData.getStepTitle()), sb.append(DEFAULT_DELIMITER));
			}
			if(permissionSet.isPERMISSION_MOVE_TO_OTHER_POSITION()){
				Utils.getJSArraySelectOption("-11", TPLabels.getLabel("selection_feedback.label.moveToOtherPosition")+" "+TPLabels.getLabel("common.position"), sb.append(DEFAULT_DELIMITER));
			}
			Utils.getJSArraySelectOption(SelectionProcessConstants.STEP_ON_HOLD, TPLabels.getLabel("selection_feedback.label.onHold"), sb.append(DEFAULT_DELIMITER));
			Utils.getJSArraySelectOption(SelectionProcessConstants.STEP_REJECT, TPLabels.getLabel("selection_feedback.label.reject"), sb.append(DEFAULT_DELIMITER));
			sb.append("]");
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			sb = new StringBuffer(NEW_ARRAY);
		}
		return sb.toString();
	}
	
	/**
	 * @param interactions
	 * @param permissionSet
	 * @return
	 */
	public static String getXMLForApplicantsInteractions(ArrayList<SimpleDataObject> interactions, PermissionSet permissionSet) {
		StringWriter sWr = new StringWriter();
		XMLWriter wr = new XMLWriter(sWr);
		try {
			if (interactions != null) {
				wr.startDocument();
				wr.startElement("rows");
				for (int indx = 0; indx < interactions.size(); indx++) {
					SimpleDataObject data = (SimpleDataObject) interactions.get(indx);
					AttributesImpl atr = new AttributesImpl();
					atr.addAttribute("", "id", "", "", "" + (indx + 1));
					wr.startElement("", "row", "", atr);

					String interactionId = (data.getString("interactionId") == null) ? "0" : data.getString("interactionId");
					atr = new AttributesImpl();
					atr.addAttribute("", "name", "", "", "interactionId");
					wr.startElement("", "userdata", "", atr);
					wr.characters(interactionId);
					wr.endElement("userdata");

					atr = new AttributesImpl();
					atr.addAttribute("", "name", "", "", "type");
					wr.startElement("", "userdata", "", atr);
					wr.characters((String) SelectionProcessConstants.INTERACTION_TYPES.get(data.getString("interactionType")));
					wr.endElement("userdata");
					
					atr = new AttributesImpl();
					atr.addAttribute("", "name", "", "", "documentId");
					wr.startElement("", "userdata", "", atr);
					String documentId = Utils.isBlankOrNull(data.getString("documentId")) ? "" : data.getString("documentId");
					wr.characters(documentId);
					wr.endElement("userdata");

					atr = new AttributesImpl();
					atr.addAttribute("", "name", "", "", "interactionType");
					wr.startElement("", "userdata", "", atr);
					wr.characters(data.getString("interactionType"));
					wr.endElement("userdata");

					wr.startElement("cell");
					wr.characters(data.getString("interactionType"));
					wr.endElement("cell");

					String interactionIsHidden = data.getString("interactionIsHidden");
					atr = new AttributesImpl();
					atr.addAttribute("", "name", "", "", "interactionIsHidden");
					wr.startElement("", "userdata", "", atr);
					wr.characters(interactionIsHidden);
					wr.endElement("userdata");
					
					boolean showInteractionImg = true;
					
					if (permissionSet.isSHOW_CONFIDENTIAL_DATA()  
							&& !data.getString("interactionType").equals("" + SelectionProcessConstants.INTERACTION_APPOINTMENTS)
							&& !data.getString("interactionType").equals("" + SelectionProcessConstants.INTERACTION_BLACKLISTED)
							&& !data.getString("interactionType").equals("" + SelectionProcessConstants.INTERACTION_UNBLACKLISTED)
							) {
						showInteractionImg= false;
					}

					wr.startElement("cell");
					if (!showInteractionImg) {
						if (interactionIsHidden.equals(SelectionProcessConstants.INTERACTION_HIDE)) {
							wr.characters("<a href=\"#\" onclick=\"hideInteraction(" + (indx + 1) + ");\" title=\"Hide/Show\"><img src=\"images/ico_show_interaction.gif\" border=0></a>");
						} else {
							wr.characters("<a href=\"#\" onclick=\"hideInteraction(" + (indx + 1) + ");\" title=\"Hide/Show\"><img src=\"images/ico_hide_interaction.gif\" border=0></a>");
						}
					} else {
						wr.characters("<img src=\"images/blank.gif\" border=0>");
					}
					wr.endElement("cell");

					String interactionSubject = (data.getString("interactionSubject") == null) ? "" : "" + data.getString("interactionSubject");
					if (Utils.isBlankOrNull(interactionSubject)) {
						interactionSubject = "&nbsp;";
					}
					AttributesImpl at = new AttributesImpl();
					at.addAttribute("", "name", "", "", "subject");
					wr.startElement("", "userdata", "", at);
					wr.characters(wr.doubleEscape(interactionSubject));
					wr.endElement("userdata");

					if (interactionSubject.length() > 27) {
						interactionSubject = interactionSubject.substring(0, 24) + "...";
					}
					wr.startElement("cell");
					if (permissionSet.isDO_NOT_SHOW_CONFIDENTIAL_DATA() && interactionIsHidden.equals(SelectionProcessConstants.INTERACTION_HIDE)) {
						wr.characters(interactionSubject);
					} else {
						wr.characters("<a href=\"#\" onclick=\"viewDetails(" + (indx + 1) + ");\">" + wr.doubleEscape(interactionSubject) + "</a>");
						// wr.characters(interactionSubject +
						// "^javascript:viewDetails(" + (indx + 1) +
						// ");^_self");
					}
					wr.endElement("cell");

					String name = (data.getString("name") == null) ? "" : data.getString("name");
					at = new AttributesImpl();
					at.addAttribute("", "name", "", "", "name");
					wr.startElement("", "userdata", "", at);
					wr.characters(wr.doubleEscape(name));
					wr.endElement("userdata");
					if (name.length() > 15) {
						name = name.substring(0, 12) + "...";
					}
					wr.startElement("cell");
					wr.characters(wr.doubleEscape(name));
					wr.endElement("cell");

					String interactionDate = "";
					if (data.getAttribute("interactionDate") != null) {
						try {
							interactionDate = DateUtils.getSystemDateTimeFormat(data.getDate("interactionDate"));
						} catch (Exception e) {
							e.printStackTrace();
							interactionDate = "UNKNOWN";
						}
					}

					wr.startElement("cell");
					wr.characters(interactionDate);
					wr.endElement("cell");

					wr.startElement("cell");
					wr.characters("" + data.getAttribute("interactionIsHidden"));
					wr.endElement("cell");
					
					wr.startElement("cell");
					wr.characters(data.getString("interactionType"));
					wr.endElement("cell");

					wr.endElement("row");
				}
				wr.endElement("rows");
				wr.endDocument();
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while generating xml for interactions", e);
		}
		return sWr.toString();
	}
	public String getHrManagerEmailForPosition(String positionId){
		String hrManagerEmail="";
		PositionManager positionManager =new PositionManager();
		List<UserData> hrManagerList=positionManager.getHRManagerForPosition(positionId);
		for (int i = 0; hrManagerList != null && i < hrManagerList.size(); i++) {
			if (Utils.isBlankOrNull(hrManagerEmail)) {
				hrManagerEmail = hrManagerList.get(i).getUserName() + " <" + hrManagerList.get(i).getUserEmail() + ">;";
			} else {
				hrManagerEmail += hrManagerList.get(i).getUserName() + " <" + hrManagerList.get(i).getUserEmail() + ">;";
			}
		}
		return hrManagerEmail;
	}
	
	public String getRecruiterEmailForPosition(String applicantPositionId){
		String recruiterEmail="";
		PositionManager positionManager =new PositionManager();
	
		List<UserData> recruiterList=positionManager.getActiveRecruiters(applicantPositionId);
		for (int i = 0; recruiterList != null && i < recruiterList.size(); i++) {
			if (Utils.isBlankOrNull(recruiterEmail)) {
				recruiterEmail = recruiterList.get(i).getUserName() + " <" + recruiterList.get(i).getUserEmail() + ">;";
			} else {
				recruiterEmail += recruiterList.get(i).getUserName() + " <" + recruiterList.get(i).getUserEmail() + ">;";
			}
		}
		return recruiterEmail;
	}
}