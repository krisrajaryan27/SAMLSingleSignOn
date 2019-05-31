/**
 * 
 */
package com.talentPool.positions.utils;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

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
import com.talentPool.custom.constants.CustomFieldConstants;
import com.talentPool.custom.dataobject.CustomFieldData;
import com.talentPool.custom.utils.CustomFieldUtils;
import com.talentPool.dashboard.manager.RecentViewManager;
import com.talentPool.documents.DocumentConstants;
import com.talentPool.positions.PositionConstants;
import com.talentPool.positions.constants.PositionConfigurationConstants;
import com.talentPool.positions.dataobject.PositionData;
import com.talentPool.positions.manager.PositionManager;
import com.talentPool.selectionProcess.SelectionProcessConstants;
import com.talentPool.user.constants.DataViewConstants;
import com.talentPool.user.dataobject.LastViewedEntity;
import com.talentPool.user.manager.PermissionSet;


/**
 * @author Ajeet
 *
 */
public class PositionUtils {

	public static String getJSArrayForPositionStatus(String positionStatus){
		StringBuffer sb = new StringBuffer();
		try {
				
				sb.append("[");
				if(!Utils.isBlankOrNull(positionStatus) &&positionStatus.equals(PositionConstants.POSITION_STATUS_INPROCESS)){
					sb.append("new SelectOption('" + PositionConstants.POSITION_STATUS_OPENED+ "','" + TPLabels.getLabel("position.status.label.opened") + "'),");
					sb.append("new SelectOption('" + PositionConstants.POSITION_STATUS_DROPPED+ "','" + TPLabels.getLabel("position.status.label.dropped") + "')");
				}else{
					sb.append("new SelectOption('" + PositionConstants.POSITION_STATUS_OPENED+ "','" + TPLabels.getLabel("position.status.label.opened") + "'),");
					sb.append("new SelectOption('" + PositionConstants.POSITION_STATUS_CLOSED+ "','" + TPLabels.getLabel("position.status.label.closed") + "'),");
					sb.append("new SelectOption('" + PositionConstants.POSITION_STATUS_HOLD+ "','" + TPLabels.getLabel("position.status.label.onhold") + "'),");
					sb.append("new SelectOption('" + PositionConstants.POSITION_STATUS_DROPPED+ "','" + TPLabels.getLabel("position.status.label.dropped") + "')");
				}
				sb.append("]");
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			sb = new StringBuffer("new Array()");
		}
		return sb.toString();
	}
	
	public static String generatePositionCode(String positionName,String department,String location, String isQuickImport, String positionCount) throws Exception {
		Calendar calendar = GregorianCalendar.getInstance();
		String year = String.valueOf(calendar.get(GregorianCalendar.YEAR));
		String month = String.valueOf(calendar.get(GregorianCalendar.MONTH) + 1);
		if(month.length() < 2) {
			month = "0" + month;
		}
		String date = String.valueOf(calendar.get(GregorianCalendar.DATE));
		if(date.length() < 2) {
			date = "0" + date;
		}
		Pattern p = Pattern.compile(PositionConstants.PATTERN_POSITION_CODE_COMPONENT);
		String positionCodeTemplate = GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_POSITION_CODE_TEMPLATE);
		Matcher m = p.matcher(positionCodeTemplate);
		String positionCode = new String(positionCodeTemplate);
		ArrayList<String> components = new ArrayList<String>();
		while (m.find()) {
			String match = m.group();
			if (!Utils.isBlankOrNull(match)) {
				if (match.indexOf("p") != -1) {
					positionCode = replaceMatch(positionCode, positionName, match, m, components);
				} else if(match.indexOf("s") != -1 && !Utils.isBlankOrNull(department)) {
					positionCode = replaceMatch(positionCode, department, match, m, components);
				} else if (match.indexOf("l") != -1 && !Utils.isBlankOrNull(location)) {
					positionCode = replaceMatch(positionCode, location, match, m, components);
				} else if (match.indexOf("y") != -1) {
					positionCode = replaceMatch(positionCode, year, match, m, components);
				} else if (match.indexOf("m") != -1) {
					positionCode = replaceMatch(positionCode, month, match, m, components);
				} else if (match.indexOf("d") != -1) {
					positionCode = replaceMatch(positionCode, date, match, m, components);
				} else if (match.indexOf("n") != -1) {
					PositionManager manager = new PositionManager();
					int noOfPositions = manager.getNumberOfPositionsCreatedInCurrentFinancialYear();
					noOfPositions += 1;
					if(!Utils.isBlankOrNull(isQuickImport) && !Utils.isBlankOrNull(positionCount)) {
						noOfPositions += Integer.parseInt(positionCount);
					}
					String num = "";
					if ((match.length() - 2) > String.valueOf(noOfPositions).length()) {
						for (int i = 0; i < (match.length() - 2 - String.valueOf(noOfPositions).length()); i++) {
							num += "0";
						}
						num += String.valueOf(noOfPositions);
					} else {
						num = String.valueOf(noOfPositions);
					}
					positionCode = replaceMatch(positionCode, num, match, m, components);
				}
			}
		}
		for (int i = 0; i < components.size(); i += 2) {
			String toBeReplacedString = components.get(i);
			String replaceWith = components.get(i + 1);
			positionCode = positionCode.replace(toBeReplacedString, replaceWith);
		}
		positionCode = positionCode.replaceAll("\\{", "").replaceAll("\\}", "");
		return positionCode;
	}
	
	public static boolean existsInPositionCodeTemplate(String pattern){
		boolean exists = false;
		Pattern p = Pattern.compile(PositionConstants.PATTERN_POSITION_CODE_COMPONENT);
		String positionCodeTemplate = GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_POSITION_CODE_TEMPLATE);
		Matcher m = p.matcher(positionCodeTemplate);
		while (m.find()) {
			String match = m.group();
			if (!Utils.isBlankOrNull(match)) {
				if (match.indexOf(pattern) != -1) {
					exists = true;
				}
			}
		}
		return exists;
	}
	
	private static String replaceMatch(String positionCode, String component, String match, Matcher m, ArrayList<String> components) {
		if (!Utils.isBlankOrNull(component)) {
			if (component.length() < match.length() - 2) {
				positionCode = positionCode.substring(0, m.start()) + "{" + match.substring(1, match.length() - 1 - component.length()) + component + "}" + positionCode.substring(m.end(), positionCode.length());
				components.add(match.substring(1, match.length() - 1 - component.length()) + component);
				components.add(component);
			} else {
				positionCode = positionCode.substring(0, m.start()) + "{" + component.substring(0, m.group().length() - 2) + "}" + positionCode.substring(m.end(), positionCode.length());
			}
		}
		return positionCode;
	}
	
	public static String getJSArrayForPositionFields(){
		StringBuffer sb = new StringBuffer();
		//CustomFieldManager customFieldManager = new CustomFieldManager();
		try {
				//ArrayList<CustomFieldData> customFields = customFieldManager.getCustomFieldsFor(CustomFieldConstants.ENTITY_TYPE_POSITION, false);
			
				sb.append("[");
				sb.append("new SelectOption('" + PositionConfigurationConstants.FIELD_NAME+ "','" + TPLabels.getLabel("common.position_name") + "'),");
				sb.append("new SelectOption('" + PositionConfigurationConstants.FIELD_CODE+ "','" + TPLabels.getLabel("common.position_code") + "')");
				sb.append("]");
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			sb = new StringBuffer("new Array()");
		}
		return sb.toString();
	}
	
	public static boolean isValidExtension(String ext) {
		boolean isValidExtension = false;
		for(int i = 0; i < DocumentConstants.indentImportExtensions.length; i++) {
			if(("."+DocumentConstants.indentImportExtensions[i]).equalsIgnoreCase(ext)) {
				isValidExtension = true;
				break;
			}
		}
		return isValidExtension;
	}
	
	public static String getXMLForFilter(ArrayList<SimpleDataObject> filters, String filterFor, String positionId, String positionOwnerId, String departmentId, String subDepartmentId, String subSubDepartmentId,
			String sub3DepartmentId, String sub4DepartmentId, String recruiterId, String locationId, String positionTypeExtInt, String skillId, String positionName) {
		StringWriter sWr = new StringWriter();
		XMLWriter wr = new XMLWriter(sWr);
		try {
			wr.startDocument();
			wr.startElement("filters");
			String selectedId = "";
			if (filterFor.equals(PositionConstants.FILTER_POSITION_OWNER)) {
				selectedId = positionOwnerId;
			}else if (filterFor.equals(PositionConstants.FILTER_DEPARTMENT)) {
				selectedId = departmentId;
			} else if (filterFor.equals(PositionConstants.FILTER_SUB_DEPARTMENT)) {
				selectedId = subDepartmentId;
			} else if (filterFor.equals(PositionConstants.FILTER_SUB_SUB_DEPARTMENT)) {
				selectedId = subSubDepartmentId;
			} else if (filterFor.equals(PositionConstants.FILTER_SUB3_DEPARTMENT)) {
				selectedId = sub3DepartmentId;
			} else if (filterFor.equals(PositionConstants.FILTER_SUB4_DEPARTMENT)) {
				selectedId = sub4DepartmentId;
			} else if (filterFor.equals(PositionConstants.FILTER_POSITION)) {
				selectedId = positionId;
			} else if (filterFor.equals(PositionConstants.FILTER_RECRUITER)) {
				selectedId = recruiterId;
			} else if (filterFor.equals(PositionConstants.FILTER_LOCATION)) {
				selectedId = locationId;
			} else if (filterFor.equals(PositionConstants.FILTER_POSITION_TYPE)) {
				selectedId = positionTypeExtInt;
			} else if (filterFor.equals(PositionConstants.FILTER_PRIMARY_SKILLS)) {
				selectedId = skillId;
			}

			for (int i = 0; filters != null && i < filters.size(); i++) {
				SimpleDataObject sdo = filters.get(i);
				String filterId = sdo.getString("filterId");
				String filterShortName = sdo.getString("filterShortName");
				String filterFullName = sdo.getString("filterFullName");
				
				if (filterFor.equals(PositionConstants.FILTER_POSITION_TYPE)) {
					if(filterId.equals(PositionConstants.POSITIONS_TYPE_INTERNAL)){
						filterShortName = TPLabels.getLabel("position.description.position_type_internal");
						filterFullName = TPLabels.getLabel("position.description.position_type_internal");
					}else if(filterId.equals(PositionConstants.POSITIONS_TYPE_EXTERNAL)){
						filterShortName = TPLabels.getLabel("position.description.position_type_external");
						filterFullName = TPLabels.getLabel("position.description.position_type_external");
					}else{
						filterShortName = TPLabels.getLabel("position.description.position_type_not_set");
						filterFullName = TPLabels.getLabel("position.description.position_type_not_set");
					}
				}
				
				StringBuffer sb = new StringBuffer();
				boolean isSelected = false;
				String name = filterShortName;

				if (!Utils.isBlankOrNull(filterId)) {
					if (filterId.equals(selectedId)) {
						isSelected = true;
					}
					if (filterFor.equals(SelectionProcessConstants.FILTER_POSITION) &&
							GlobalConstants.ENABLED.equals(GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_SHOW_POSITION_CODE))) {
						name = filterShortName;
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
					if (filterFor.equals(PositionConstants.FILTER_POSITION)) {
						wr.characters(wr.doubleEscape(filterFullName + " [" + filterShortName + "]"));
					} else {
						wr.characters(wr.doubleEscape(name));
					}						
					wr.endElement("title");
					wr.endElement("filter");
				}
			}
			wr.endElement("filters");
			wr.endDocument();
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return sWr.getBuffer().toString();
	}
	
	public static String getJSArrayForSelectionStageOnPostionSummary() {
		ArrayList<String> ids = new ArrayList<String>();
		ArrayList<String> names = new ArrayList<String>();
		ids.add(PositionConstants.STEP_LEVEL_SHORTLIST);
		ids.add(PositionConstants.STEP_LEVEL_SELECT);
		ids.add(PositionConstants.STEP_LEVEL_ACCEPT);
		names.add(TPLabels.getLabel("common.shortlist"));
		names.add(TPLabels.getLabel("common.select"));
		names.add(TPLabels.getLabel("common.hire"));
		String JSStageCriteria = CommonUtils.getListJavaScriptArray(ids, names);
		return JSStageCriteria;
	}
	
	public static void setPositionSummaryLeftPanel(HttpServletRequest request, String userId,PermissionSet permissionSet) {
		RecentViewManager recentViewManager = new RecentViewManager();
		List<LastViewedEntity> lastViewedEntities = recentViewManager.getLastViewedPositions(userId,permissionSet);
		request.setAttribute("lastViewedPositions", lastViewedEntities);
	}
	
	public static HashMap<String, String> setPositionDynamicColumns(SimpleDataObject data) {
		HashMap<String, String> dataMap = new HashMap<String, String>();
		try {
			dataMap.put(DataViewConstants.POSITION_DEPARTMENT,Utils.getBlankIfNull(data.getString("departmentName")));
			dataMap.put(DataViewConstants.POSITION_SUB_DEPARTMENT,Utils.getBlankIfNull(data.getString("subDepartmentName")));
			dataMap.put(DataViewConstants.POSITION_SUB_SUB_DEPARTMENT,Utils.getBlankIfNull(data.getString("subSubDepartmentName")));
			dataMap.put(DataViewConstants.POSITION_SUB3_DEPARTMENT,Utils.getBlankIfNull(data.getString("sub3DeptName")));
			dataMap.put(DataViewConstants.POSITION_SUB4_DEPARTMENT,Utils.getBlankIfNull(data.getString("sub4DeptName")));
			dataMap.put(DataViewConstants.POSITION_HIRE_BY_DATE,DateUtils.getSystemDateFormat(data.getDate("expiryDate")));
			dataMap.put(DataViewConstants.POSITION_LOCATION,Utils.getBlankIfNull(data.getString("locationName")));
			dataMap.put(DataViewConstants.POSITION_RECRUITERS,Utils.getBlankIfNull(data.getString("recruiters")));
			dataMap.put(DataViewConstants.POSITION_OWNER,Utils.getBlankIfNull(data.getString("positionOwnerName")));
			dataMap.put(DataViewConstants.POSITION_REQUESTED_BY,Utils.getBlankIfNull(data.getString("requestedByName")));
			dataMap.put(DataViewConstants.POSITION_LEVEL,Utils.getBlankIfNull(data.getString("positionLevel")));
			dataMap.put(DataViewConstants.POSITION_CREATED_ON,DateUtils.getSystemDateFormat(data.getDate("positionCreatedOn")));
			dataMap.put(DataViewConstants.POSITION_GRADE,Utils.getBlankIfNull(data.getString("gradeName")));
			dataMap.put(DataViewConstants.POSITION_BAND,Utils.getBlankIfNull(data.getString("bandName")));
			dataMap.put(DataViewConstants.POSITION_REFERRAL_FEES,Utils.getBlankIfNull(data.getString("referalFees")));
			
			CustomFieldUtils.generateCustomFieldMap(dataMap,Utils.getBlankIfNull(data.getString("customFields")), "$$$");
			
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return dataMap;
	}
	
	public static String getPositionExperienceConstructed(float minExperience,float maxExperience) {
		StringBuffer sb = new StringBuffer("");
		sb.append(" ");
		sb.append(minExperience);
		sb.append(" ");
		sb.append(TPLabels.getLabel("position.requirements.experience.to"));
		sb.append(" ");
		sb.append(maxExperience);
		sb.append(" ");
		sb.append(TPLabels.getLabel("position.requirements.experience.years"));
		return sb.toString();
	}
	
	public static String generatePositionGridDiplayString(SimpleDataObject positionData){
		String positionDisplayTemplate = GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_POSITION_DISPLAY_IN_GRID_TEMPLATE);
		StringBuffer displayString = new StringBuffer("");
		for (char codeElement : positionDisplayTemplate.toCharArray()) {
			if(codeElement=='n'){
				displayString.append(Utils.isBlankOrNull(positionData.getString("positionTitle"))?"":positionData.getString("positionTitle"));
			}else if(codeElement=='c'){
				displayString.append(Utils.isBlankOrNull(positionData.getString("positionCode"))?"":positionData.getString("positionCode"));
			}else if(codeElement=='d'){
				displayString.append(Utils.isBlankOrNull(positionData.getString("deptName"))?"":positionData.getString("deptName"));
			}else if(codeElement=='s'){
				displayString.append(Utils.isBlankOrNull(positionData.getString("subDeptName"))?"":positionData.getString("subDeptName"));
			}else if(codeElement=='g'){
				displayString.append(Utils.isBlankOrNull(positionData.getString("groupName"))?"":positionData.getString("groupName"));
			}else if(codeElement=='l'){
				displayString.append(Utils.isBlankOrNull(positionData.getString("locationName"))?"":positionData.getString("locationName"));
			}else if(codeElement=='r'){
				displayString.append(Utils.isBlankOrNull(positionData.getString("gradeName"))?"":positionData.getString("gradeName"));
			}else{
				displayString.append(codeElement);
			}
		}
		return displayString.toString();
	}
	
	public static String getXmlForPosition(List<PositionData> positions) {
		StringWriter sWr = new StringWriter();
		XMLWriter wr = new XMLWriter(sWr);		
		try {
			wr.startDocument();
			wr.startElement("rows");
			if (positions != null && positions.size() > 0) {
				for (int i = 0; i < positions.size(); i++) {
					PositionData pData = (PositionData) positions.get(i);
					
					String positionId = pData.getPositionId();
					String positionName = Utils.getBlankIfNull(pData.getPositionTitle());					
					
					AttributesImpl atr = new AttributesImpl();					
					atr.addAttribute("", "id", "", "", String.valueOf(positionId));
					wr.startElement("", "row", "", atr);
										
					atr = new AttributesImpl();
					atr.addAttribute("", "name", "", "", "positionName");
					wr.startElement("", "userdata", "", atr);
					wr.characters(positionName);
					wr.endElement("userdata");
					
					positionName = generatePositionGridDiplayString(pData);
					
					wr.startElement("cell");
					wr.characters(positionName);
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
	
	public static String getPositionPriorityValues(String positionPriorityId){
		String positionPriority = "";
		try{
			if(positionPriorityId.equals(PositionConstants.POSITION_LEVEL_MEDIUM)){
				positionPriority = TPLabels.getLabel("position.priority.medium");
			}else if(positionPriorityId.equals(PositionConstants.POSITION_LEVEL_HIGH)){
				positionPriority = TPLabels.getLabel("position.priority.high");
			}else if(positionPriorityId.equals(PositionConstants.POSITION_LEVEL_LOW)){
				positionPriority = TPLabels.getLabel("position.priority.low");
			}			
		}catch (Exception e) {
			TPLogger.getLogger().debug(GlobalConstants.ERROR,e);
		}
		return positionPriority;
	}
	
	public static String getVacancyTypeValues(String vacancyTypeId){
		String vacancyType = "";
		try{
			if(vacancyTypeId.equals(PositionConstants.POSITIONS_TYPE_OF_VACANCY_FRESH)){
				vacancyType = TPLabels.getLabel("position.description.fresh");
			}else if(vacancyTypeId.equals(PositionConstants.POSITIONS_TYPE_OF_VACANCY_REPLACEMENT)){
				vacancyType = TPLabels.getLabel("position.description.replacement");
			}			
		}catch (Exception e) {
			TPLogger.getLogger().debug(GlobalConstants.ERROR,e);
		}
		return vacancyType;
	}
	
	public static String getPositionStatusValues(String positionStatusId){
		String positionStatus = "";
		try{
			if(positionStatusId.equals(PositionConstants.POSITION_STATUS_OPENED)){
				positionStatus = TPLabels.getLabel("position.status.label.opened");
			}else if(positionStatusId.equals(PositionConstants.POSITION_STATUS_CLOSED)){
				positionStatus = TPLabels.getLabel("position.status.label.closed");
			}else if(positionStatusId.equals(PositionConstants.POSITION_STATUS_HOLD)){
				positionStatus = TPLabels.getLabel("position.status.label.onhold");
			}else if(positionStatusId.equals(PositionConstants.POSITION_STATUS_INPROCESS)){
				positionStatus = TPLabels.getLabel("position.status.label.inprocess");
			}else if(positionStatusId.equals(PositionConstants.POSITION_STATUS_DELETED)){
				positionStatus = TPLabels.getLabel("position.status.label.deleted");
			}else if(positionStatusId.equals(PositionConstants.POSITION_STATUS_REJECTED)){
				positionStatus = TPLabels.getLabel("position.status.label.deleted");
			}else if(positionStatusId.equals(PositionConstants.POSITION_STATUS_TEMPLATE)){
				positionStatus = TPLabels.getLabel("position.status.label.template");
			}			
		}catch (Exception e) {
			TPLogger.getLogger().debug(GlobalConstants.ERROR,e);
		}
		return positionStatus;
	}
	
	public static List<SimpleDataObject> getPositionStatuses(){
		List<SimpleDataObject> statuses = new ArrayList<SimpleDataObject>();		
		ArrayList<String> ids = new ArrayList<String>();
		ArrayList<String> names = new ArrayList<String>();
		try{
			ids.add(PositionConstants.POSITION_STATUS_OPENED);
			ids.add(PositionConstants.POSITION_STATUS_CLOSED);
			ids.add(PositionConstants.POSITION_STATUS_HOLD);
			/*if (PositionConstants.REQUISITION_MANAGEMENT_ENABLED) {
				ids.add(PositionConstants.POSITION_STATUS_INPROCESS);
				ids.add(PositionConstants.POSITION_STATUS_MY_APPROVAL_PENDING);
				ids.add(PositionConstants.POSITION_STATUS_TO_BE_OPENED);
				ids.add(PositionConstants.POSITION_STATUS_REJECTED);
			}*/
			names.add(TPLabels.getLabel("positions_home.label.show_open_positions"));
			names.add(TPLabels.getLabel("positions_home.label.show_closed_positions"));
			names.add(TPLabels.getLabel("positions_home.label.show_hold_positions"));
			/*if (PositionConstants.REQUISITION_MANAGEMENT_ENABLED) {
				names.add(TPLabels.getLabel("positions_home.label.show_open_requisitions"));
				names.add(TPLabels.getLabel("positions_home.label.my_approval_pending"));
				names.add(TPLabels.getLabel("positions_home.label.show_to_be_opened"));
				names.add(TPLabels.getLabel("positions_home.label.show_rejected_requisitions"));
			}*/
			for(int i=0;i<ids.size();i++){
				SimpleDataObject sdo = new SimpleDataObject();
				//sdo.setAttribute(ids.get(i), names.get(i));
				sdo.setAttribute("statusId", ids.get(i));
				sdo.setAttribute("statusName", names.get(i));
				statuses.add(sdo);
			}
			
		}catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return statuses;		
	}
	
	public static boolean isPositionTypeSetForPosition(String positionId){
		boolean positionTypeSet = false;
		PositionManager positionManager = new PositionManager();
		try{
			SimpleDataObject positionDescription=(SimpleDataObject)positionManager.getPositionDescriptionToEdit(positionId);
			String positionType=positionDescription.getString("positionTypeExtInt");
			if(positionType.equals(PositionConstants.POSITIONS_TYPE_EXTERNAL)){
				positionTypeSet=true;
			}else if (positionType.equals(PositionConstants.POSITIONS_TYPE_INTERNAL)) {
				positionTypeSet=true;
			}			
		}catch (Exception e) {
			TPLogger.getLogger().debug(GlobalConstants.ERROR,e);
		}
		
		return positionTypeSet;
	}
	
	/**
	 * @param oldObject
	 * @param newObject
	 * @param changeFields
	 */
	public static void checkIfFieldValueChanged(SimpleDataObject oldObject, SimpleDataObject newObject, ArrayList<String[]> changeFields){
		try{
		Map attributes = oldObject.getAttributes();
		String[] changedValue = new String[3];
		Map<String, String> positionFieldLabelMap = CommonUtils.getPositionFieldLabelMap();
		for (Object field : attributes.keySet()) {
			String fieldName = (String)field;
			
			if(fieldName.equals("customFields")){
				ArrayList<CustomFieldData> oldCustomFields = (ArrayList<CustomFieldData>) oldObject.getAttribute(fieldName);
				ArrayList<CustomFieldData> newCustomFields = (ArrayList<CustomFieldData>) newObject.getAttribute(fieldName);
				for (int i = 0; i < oldCustomFields.size(); i++) {
					CustomFieldData oldData = oldCustomFields.get(i);
					CustomFieldData newData = newCustomFields.get(i);
					if(!getCustomDataStringValue(oldData).equals(getCustomDataStringValue(newData))) {					
						changedValue = new String[3];
						changedValue[0]=oldData.getFieldDisplayName();
						changedValue[1]=getCustomDataStringValue(oldData);
						changedValue[2]=getCustomDataStringValue(newData);
						changeFields.add(changedValue);
					}
				}
			}
			else{
				String oldValue = oldObject.getString(fieldName)==null? "":oldObject.getString(fieldName);
				String newValue = newObject.getString(fieldName)==null? "":newObject.getString(fieldName);				
				if (!newValue.equals(oldValue)) {					
					changedValue = new String[3];
					changedValue[0]=positionFieldLabelMap.get(fieldName);
					changedValue[1]=oldObject.getString(fieldName);
					changedValue[2]=newObject.getString(fieldName);
					changeFields.add(changedValue);
				}
			}
		}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * @param fieldData
	 * @return
	 */
	private static String getCustomDataStringValue(CustomFieldData fieldData){
		String comparisonVar = "";
		if(fieldData.getFieldType().equals(CustomFieldConstants.TYPE_NUMBER)){
			comparisonVar = "fieldNumberValue";
		}else if(fieldData.getFieldType().equals(CustomFieldConstants.TYPE_DATE)){
			comparisonVar = "fieldDateValue";
		}else{
			comparisonVar = "fieldStringValue";
		}
		return fieldData.getString(comparisonVar);
	}
	
	/**
	 * @return JS Array for position drop reason 
	 */
	public static String getJSArrayForPositionDropReason(){
		StringBuffer sb = new StringBuffer();
		try {
			sb.append("[");
			sb.append("new SelectOption('"
					+ PositionConstants.POSITION_DROP_OPPORTUNITY_LOST + "','"
					+ TPLabels.getLabel("position.drop.reason.opportunity_lost") + "'),");
			sb.append("new SelectOption('"
					+ PositionConstants.POSITION_DROP_PROJECT_DROPPED + "','"
					+ TPLabels.getLabel("position.drop.reason.project_dropped") + "'),");
			sb.append("new SelectOption('"
					+ PositionConstants.POSITION_DROP_BUFFER_INDENT + "','"
					+ TPLabels.getLabel("position.drop.reason.buffer_indent") + "'),");
			sb.append("new SelectOption('"
					+ PositionConstants.POSITION_DROP_TBD1 + "','"
					+ TPLabels.getLabel("position.drop.reason.tbd1") + "'),");
			sb.append("new SelectOption('"
					+ PositionConstants.POSITION_DROP_TBD2 + "','"
					+ TPLabels.getLabel("position.drop.reason.tbd2") + "'),");
			sb.append("new SelectOption('"
					+ PositionConstants.POSITION_DROP_OTHERS + "','"
					+ TPLabels.getLabel("position.drop.reason.others") + "')");
			sb.append("]");
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			sb = new StringBuffer("new Array()");
		}
		return sb.toString();
	}
}
