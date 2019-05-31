package com.talentPool.user.utils;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.db.SimpleDataObject;
import com.talentPool.common.properties.GlobalApplicationProperties;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.utils.CommonUtils;
import com.talentPool.common.utils.Utils;
import com.talentPool.common.xmlutils.XMLWriter;
import com.talentPool.user.UserConstants;
import com.talentPool.user.constants.PermissionConstants;
import com.talentPool.user.dataobject.LoginData;
import com.talentPool.user.manager.UserManager;

public class UserUtils {

	public static String generateUserGridDiplayString(SimpleDataObject userData){
		String userDisplayTemplate = GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_USER_DISPLAY_IN_GRID_TEMPLATE);
		StringBuffer displayString = new StringBuffer("");
		for (char codeElement : userDisplayTemplate.toCharArray()) {
			if(codeElement=='f'){
				displayString.append(Utils.isBlankOrNull(userData.getString("firstName"))?"":userData.getString("firstName"));
			}else if(codeElement=='n'){
				displayString.append(Utils.isBlankOrNull(userData.getString("lastName"))?"":userData.getString("lastName"));
			}else if(codeElement=='d'){
				displayString.append(Utils.isBlankOrNull(userData.getString("department"))?"":userData.getString("department"));
			}else if(codeElement=='s'){
				displayString.append(Utils.isBlankOrNull(userData.getString("subDepartment"))?"":userData.getString("subDepartment"));
			}else if(codeElement=='g'){
				displayString.append(Utils.isBlankOrNull(userData.getString("subSubDepartment"))?"":userData.getString("subSubDepartment"));
			}else if(codeElement=='l'){
				displayString.append(Utils.isBlankOrNull(userData.getString("location"))?"":userData.getString("location"));
			}else if(codeElement=='r'){
				displayString.append(Utils.isBlankOrNull(userData.getString("roleTitle"))?"":userData.getString("roleTitle"));
			}else if(codeElement=='e'){
				displayString.append(Utils.isBlankOrNull(userData.getString("employeeId"))?"":userData.getString("employeeId"));
			}else{
				displayString.append(codeElement);
			}
		}
		return displayString.toString();
	}
	
	public static String getListJavaScriptArray(ArrayList<LoginData> users) {
		StringBuffer sb = new StringBuffer();
		try {
			int totalOptions = users.size();
			if (totalOptions > 0) {
				sb.append("[");
				for (int i = 0; i < totalOptions; i++) {
					LoginData userData = users.get(i);
					sb.append("new SelectOption('" + Utils.escapeJavaScript(userData.getUserId()) + "','" + Utils.escapeJavaScript(generateUserGridDiplayString(userData)) + "')");
					if (i < totalOptions - 1)
						sb.append(",");
				}
				sb.append("]");
			} else {
				sb.append("new Array()");
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			sb = new StringBuffer("new Array()");
		}
		return sb.toString();
	}
	
	public static String getXMLforActiveUsers(List activeUsers) {
		StringWriter sWr = new StringWriter();
		XMLWriter wr = new XMLWriter(sWr);		
		try {
			wr.startDocument();
			wr.startElement("rows");
			if (activeUsers != null && activeUsers.size() > 0) {
				
				for (int i = 0; i < activeUsers.size(); i++) {
					SimpleDataObject sDo = (SimpleDataObject) activeUsers.get(i);
					
					String activeUserId = (sDo.getString("userId") == null) ? "" : sDo.getString("userId");
					
					AttributesImpl atr = new AttributesImpl();					
					atr.addAttribute("", "id", "", "", String.valueOf(activeUserId));
					wr.startElement("", "row", "", atr);
										
					String displayString  = UserUtils.generateUserGridDiplayString(sDo);
					atr = new AttributesImpl();
					atr.addAttribute("", "name", "", "", "activeUserName");
					wr.startElement("", "userdata", "", atr);					
					wr.characters(displayString);
					wr.endElement("userdata");
					
					atr = new AttributesImpl();
					atr.addAttribute("", "name", "", "", "userName");
					wr.startElement("", "userdata", "", atr);					
					wr.characters(Utils.getBlankIfNull(sDo.getString("firstName"))+" "+Utils.getBlankIfNull(sDo.getString("lastName")));
					wr.endElement("userdata");
					
					wr.startElement("cell");
					wr.characters(displayString);
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
	 * @param pswd
	 * @return true or false based on valid password pattern
	 */
	public static boolean isValidPasswordPattern(String pswd) {
		boolean validPattern = true;
		String passwordPattern = UserConstants.PASSWORD_PATTERN;
		if(!Utils.isBlankOrNull(pswd) && !Utils.isBlankOrNull(passwordPattern)) {
			validPattern = Utils.isValidPattern(pswd, passwordPattern);
		}
		return validPattern;
	}
	
	public static String getJSTimeZoneArray(){
		String [] ids = GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_ALL_SELECTED_TIME_ZONES).split(",");
		ArrayList<String> zoneIds = new ArrayList<String>();
		ArrayList<String> zoneValues = new ArrayList<String>();
		for(String id:ids) {
			zoneIds.add(id);
			TimeZone zone = TimeZone.getTimeZone(id);
			int offset = 0;
			if (zone.inDaylightTime(new Date())){
				offset = (zone.getRawOffset() + zone.getDSTSavings())/1000;
			} else {
				offset = zone.getRawOffset()/1000;
			}
			int hour = offset/3600;
			int minutes = (offset % 3600)/60;
			zoneValues.add(String.format("(GMT%+d:%02d) %s", hour, minutes, id));
		}   
		return CommonUtils.getListJavaScriptArray(zoneIds, zoneValues);
	}
	public static String getInterviewMode(){
		String [] ids = GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_ALL_INTERVIEW_MODE).split(",");
		ArrayList<String> interviewMode = new ArrayList<String>();
		ArrayList<String> interviewValue = new ArrayList<String>();
		for(String id:ids) {
			interviewMode.add(id);
			interviewValue.add(id);
		}   
		return CommonUtils.getListJavaScriptArray(interviewMode,interviewValue);
	}
	public static List<String> getInterviewModeAll(){
		String [] ids = GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_ALL_INTERVIEW_MODE).split(",");
		ArrayList<String> interviewMode = new ArrayList<String>();
		for(String id:ids) {
			interviewMode.add(id);
			
		}   
		return interviewMode;
	}
	
	public static boolean getShowPermissionForLoggedInUser(String userId) {
		UserManager userManager = new UserManager();
		ArrayList<LoginData> positionOwners = userManager.getUsersForPermission(PermissionConstants.SHOW_ALL_POSITIONS);
		boolean showAllPositionPermisssion = false;
		for(LoginData positionOwner : positionOwners){
			if(positionOwner.getUserId().equals(userId)){
				showAllPositionPermisssion = true;
				break;
			}
		}
		return showAllPositionPermisssion;
	}
}
