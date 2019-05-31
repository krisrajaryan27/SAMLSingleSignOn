/**
 * 
 */
package com.talentPool.userConfiguration.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.talentPool.applicant.constants.ImportConfigurationConstants;
import com.talentPool.applicant.dataobject.ApplicantData;
import com.talentPool.applicant.dataobject.EducationalData;
import com.talentPool.applicant.dataobject.EmploymentHistoryData;
import com.talentPool.applicant.manager.ApplicantManager;
import com.talentPool.applicant.manager.ImportConfigurationManager;
import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.base.TPDispatchAction;
import com.talentPool.common.constants.DateConstants;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.properties.TPLabels;
import com.talentPool.common.utils.DateUtils;
import com.talentPool.common.utils.Utils;
import com.talentPool.custom.constants.CustomFieldConstants;
import com.talentPool.custom.dataobject.CustomFieldData;
import com.talentPool.custom.manager.CustomFieldManager;
import com.talentPool.custom.utils.CustomFieldDataProcessor;
import com.talentPool.positions.dataobject.PositionSkillsData;
import com.talentPool.user.manager.PermissionSet;
import com.talentPool.user.manager.SessionManager;
import com.talentPool.userConfiguration.constants.UserConfigurationConstants;
import com.talentPool.userConfiguration.form.UserConfigurationForm;
import com.talentPool.userConfiguration.utils.UserConfigurationUtils;


/**
 * @author Ajeet
 *
 */
public class UserConfigurationAction extends TPDispatchAction {

	public ActionForward getApplicantToolTip(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String userId = (String) request.getSession(false).getAttribute("userId");
		String forward = "xmlFile";
		String xmlFile = "";
		PermissionSet permissionSet = (PermissionSet)request.getSession(false).getAttribute("permissionSet");
		try {
			HashMap<String, List<String>> applicantTooltipMap = UserConfigurationUtils.getApplicantTooltipMap();
			List<String> appList = applicantTooltipMap.get(userId);
			if(appList==null){
				appList = UserConfigurationUtils.getDefaultApplicantTooltipList();
			}
			if(appList!=null){
				UserConfigurationForm dashboardForm = (UserConfigurationForm)actionForm;
				ApplicantManager applicationManager = new ApplicantManager();
				ApplicantData aData = applicationManager.getApplicantSummaryData(dashboardForm.getApplicantId());
							
				xmlFile += "<table>";	
					
				if(appList.contains(UserConfigurationConstants.APP_NAME)){
					xmlFile += "<tr><td><strong>" + Utils.escapeHTML(aData.getApplicantName()) +"</strong>";
					if(appList.contains(UserConfigurationConstants.APP_SOURCE) && !Utils.isBlankOrNull(aData.getApplicantSourceTitle())){
						 if (ImportConfigurationManager.isApplicantFieldViewable(ImportConfigurationConstants.FIELD_SOURCE ,permissionSet.isSHOW_CONFIDENTIAL_DATA()) ){
							 xmlFile += " (" + Utils.escapeHTML(aData.getApplicantSourceTitle())+")";
						 }						
					}
					xmlFile += "</td></tr>";
				}
				if(appList.contains(UserConfigurationConstants.APP_ID) && !Utils.isBlankOrNull(aData.getApplicantId())){
					xmlFile += "<tr><td>";
					xmlFile += "ID: "+aData.getApplicantId();
					xmlFile += "</td></tr>";
				}
				if(appList.contains(UserConfigurationConstants.APP_EMAIL1) && !Utils.isBlankOrNull(aData.getApplicantEmail1())){
					xmlFile += "<tr><td>";
					xmlFile += "<a class=\"Grey\" href=\'mailto:"+aData.getApplicantEmail1()+"\'>"+aData.getApplicantEmail1()+"</a>";
					if(appList.contains(UserConfigurationConstants.APP_EMAIL2) && !Utils.isBlankOrNull(aData.getApplicantEmail2())){
						xmlFile += ", <a class=\"Grey\" href=\'mailto:"+aData.getApplicantEmail2()+"\'>"+aData.getApplicantEmail2()+"</a>";
					}
					xmlFile += "</td></tr>";
				}
				if(appList.contains(UserConfigurationConstants.APP_CELL_PHONE) && !Utils.isBlankOrNull(aData.getApplicantCellPhone())){
					xmlFile += "<tr><td>";
					xmlFile += Utils.escapeHTML(aData.getApplicantCellPhone());
					if(appList.contains(UserConfigurationConstants.APP_HOME_PHONE) && !Utils.isBlankOrNull(aData.getApplicantHomePhone())){
						xmlFile += ", " + Utils.escapeHTML(aData.getApplicantHomePhone());
					}
					if(appList.contains(UserConfigurationConstants.APP_WORK_PHONE) && !Utils.isBlankOrNull(aData.getApplicantWorkPhone())){
						xmlFile += ", " + Utils.escapeHTML(aData.getApplicantWorkPhone());
					}
					xmlFile += "</td></tr>";
				}
				if(appList.contains(UserConfigurationConstants.APP_LOCATION) && !Utils.isBlankOrNull(aData.getApplicantCity())){
					xmlFile += "<tr><td>";
					xmlFile += Utils.escapeHTML(aData.getApplicantCity());
					xmlFile += "</td></tr>";
				}
				if(ImportConfigurationManager.isCurrentCTCViewable(permissionSet) && appList.contains(UserConfigurationConstants.APP_CURRENT_CTC) && !Utils.isBlankOrNull(aData.getCurrentCTC())){
					xmlFile += "<tr><td>";
					xmlFile += Utils.escapeHTML(aData.getCurrentCTC());
					if(ImportConfigurationManager.isExpectedCTCViewable(permissionSet) && appList.contains(UserConfigurationConstants.APP_EXPECTED_CTC) && !Utils.isBlankOrNull(aData.getExpectedCTC())){
						xmlFile += "/";
						xmlFile += Utils.escapeHTML(aData.getExpectedCTC());
					}
					xmlFile += "</td></tr>";
				}
				if(appList.contains(UserConfigurationConstants.APP_EXPERIENCE) && !Utils.isBlankOrNull(aData.getApplicantExperience())){
					xmlFile += "<tr><td>";
					xmlFile += aData.getApplicantExperience();
					xmlFile += "</td></tr>";
				}
				if(appList.contains(UserConfigurationConstants.APP_CURRENT_EMPLOYER) && !Utils.isBlankOrNull(aData.getApplicantCurrentEmployer())){
					xmlFile += "<tr><td>";
					xmlFile += Utils.escapeHTML(aData.getApplicantCurrentEmployer());
					xmlFile += "</td></tr>";
				}
				if(appList.contains(UserConfigurationConstants.APP_SKILLS)){
					ArrayList skills = applicationManager.getSkills(dashboardForm.getApplicantId());
					if(skills!=null){
						StringBuffer skillList = new StringBuffer();
						for (int i = 0; i < skills.size(); i++) {
							PositionSkillsData pData = (PositionSkillsData) skills.get(i);
							skillList.append(pData.getSkillName());
							if (i < skills.size() - 1) {
								skillList.append(", ");
							}
						}
						xmlFile += "<tr><td>";
						xmlFile += Utils.escapeHTML(skillList.toString());
						xmlFile += "</td></tr>";
					}
				}
				if(appList.contains(UserConfigurationConstants.APP_EDUCATION)){
					ArrayList<EducationalData> eduList = applicationManager.getEducationalInfo(dashboardForm.getApplicantId());
					if(eduList!=null){
						for (int i = 0; i < eduList.size(); i++) {
							EducationalData sduData =eduList.get(i); 
							xmlFile += "<tr><td>";
							if(sduData.getYearOfPassing()!=null){
								xmlFile += DateUtils.getDateFormated(sduData.getYearOfPassing(), DateConstants.YEAR_PATTERN);
							}
							if(!Utils.isBlankOrNull(sduData.getDegreeTitle())){
								xmlFile += " "+Utils.escapeHTML(sduData.getDegreeTitle());
							}
							if(!Utils.isBlankOrNull(sduData.getInstitute())){
								xmlFile += " "+Utils.escapeHTML(sduData.getInstitute());
							}
							if(!Utils.isBlankOrNull(sduData.getMajor())){
								xmlFile += " "+Utils.escapeHTML(sduData.getMajor());		
							}					
							xmlFile += "</td></tr>";
						}
					}					
				}
				if(appList.contains(UserConfigurationConstants.APP_DATE_OF_BIRTH) && !Utils.isBlankOrNull(aData.getDateOfBirthToDisplay())){
					xmlFile += "<tr><td>";
					xmlFile += TPLabels.getLabel("common.date_of_birth")+": "+ Utils.escapeHTML(aData.getDateOfBirthToDisplay());
					xmlFile += "</td></tr>";
				}
				
				if(appList.contains(UserConfigurationConstants.APP_PASSPORT) && !Utils.isBlankOrNull(aData.getPassportNumber())){
					xmlFile += "<tr><td>";
					xmlFile += TPLabels.getLabel("common.passport")+": "+ Utils.escapeHTML(aData.getPassportNumber());
					xmlFile += "</td></tr>";
				}
				if(appList.contains(UserConfigurationConstants.APP_NOTICE_PERIOD) && !Utils.isBlankOrNull(aData.getNoticePeriod())){
					xmlFile += "<tr><td>";
					xmlFile += Utils.escapeHTML(aData.getNoticePeriod());
					xmlFile += "</td></tr>";
				}
				if(appList.contains(UserConfigurationConstants.APP_HRMS_CODE) && !Utils.isBlankOrNull(aData.getApplicantHRMSCode())){
					xmlFile += "<tr><td>";
					xmlFile += TPLabels.getLabel("common.hrms_code")+": "+ Utils.escapeHTML(aData.getApplicantHRMSCode());
					xmlFile += "</td></tr>";
				}
				if(appList.contains(UserConfigurationConstants.APP_RESUME_TYPE) && !Utils.isBlankOrNull(aData.getResumeType())){
					xmlFile += "<tr><td>";
					xmlFile += Utils.escapeHTML(aData.getResumeType());
					xmlFile += "</td></tr>";
				}
				
				if(appList.contains(UserConfigurationConstants.APP_EMPLOYMENT_HISTORY)){
					ArrayList<EmploymentHistoryData> eduList = applicationManager.getEmploymentHistoryInfo(dashboardForm.getApplicantId());
					ApplicantData ada =  applicationManager.getApplicantData(dashboardForm.getApplicantId());					
					if(!ada.getFullEmploymentHistoryDetailsFormatted().isEmpty()){												 
						xmlFile += "<tr><td>";						
						xmlFile += ada.getFullEmploymentHistoryDetailsFormatted();
						xmlFile += "</td></tr>";						
					}					
				}
				
				if(appList.contains(UserConfigurationConstants.APP_CUSTOM_FIELDS)){
					CustomFieldManager customFieldManager = new CustomFieldManager();
					ArrayList<CustomFieldData> aCustomFields = customFieldManager.getCustomFieldDataForEntity(dashboardForm.getApplicantId(), CustomFieldConstants.ENTITY_TYPE_APPLICANT);
					if (CustomFieldManager.isCustomFieldsAvailable(CustomFieldConstants.ENTITY_TYPE_APPLICANT)) {
						ArrayList<CustomFieldData> customFields = customFieldManager.getCustomFieldsForInputAllowed(CustomFieldConstants.ENTITY_TYPE_APPLICANT, CustomFieldConstants.INPUT_ALLOWED, true);
						CustomFieldDataProcessor customFieldDataProcessor = new CustomFieldDataProcessor();
						customFields = customFieldDataProcessor.setCustomFieldValuesFromPreviousValues(customFields, aCustomFields);
						for (int i = 0; i < customFields.size(); i++) {
							CustomFieldData cData = customFields.get(i);
							if(!Utils.isBlankOrNull(cData.getDisplayValue())){
								xmlFile += "<tr><td>";
								xmlFile += Utils.escapeHTML(cData.getFieldDisplayName()) +": "+Utils.escapeHTML(cData.getDisplayValue());
								xmlFile += "</td></tr>";
							}
						}
					}
				}
				if(appList.contains(UserConfigurationConstants.APP_FLAGS)){
					String flags = applicationManager.getApplicantFlagsName(dashboardForm.getApplicantId());
					if(!Utils.isBlankOrNull(flags)){
						xmlFile += "<tr><td>";
						xmlFile += Utils.escapeHTML(flags);
						xmlFile += "</td></tr>";
					}
				}
				xmlFile += "</table>";
			}			
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			xmlFile = Utils.getXMLForError();
		}
		request.setAttribute("xmlFile", xmlFile);
		return mapping.findForward(forward);
	}
	
}
