package com.talentPool.offerSheet.utils;

import java.util.LinkedList;
import java.util.List;

import com.talentPool.common.db.SimpleDataObject;
import com.talentPool.common.properties.GlobalApplicationProperties;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.properties.TPLabels;
import com.talentPool.offerSheet.constants.OfferSheetConstants;

/**
 * @author PraveenK
 * @since  Sep 23, 2011
 */
public class OfferSheetVariableUtils implements OfferSheetConstants {
	private static final List<SimpleDataObject> offerSheetCommonVariable;
	private static final List<SimpleDataObject> offerSheetApplicantVariable;
	private static final List<SimpleDataObject> offerSheetPositionVariable;
	
	static{
		populateOfferSheetCommonVariable(offerSheetCommonVariable = new LinkedList<SimpleDataObject>());
		populateOfferSheetApplicantVariable(offerSheetApplicantVariable = new LinkedList<SimpleDataObject>());
		populateOfferSheetPositionVariable(offerSheetPositionVariable = new LinkedList<SimpleDataObject>());
	}
	
	/**
	 * Populates Labels for common variables
	 * @param offerSheetApplicantVariable
	 */
	private static void populateOfferSheetCommonVariable(List<SimpleDataObject> offerSheetApplicantVariable){
		addMapping(offerSheetApplicantVariable,COMPANY_NAME,TPLabels.getLabel("generate_offer_sheet.name.company_name"));
		addMapping(offerSheetApplicantVariable,TODAY_DATE,TPLabels.getLabel("generate_offer_sheet.name.today_date"));
		addMapping(offerSheetApplicantVariable,OFFER_CODE,TPLabels.getLabel("generate_offer_sheet.label.offer_code"));
	}
	
	/**
	 * Populates Labels for Applicants 
	 * @param offerSheetApplicantVariable
	 */
	private static void populateOfferSheetApplicantVariable(List<SimpleDataObject> offerSheetApplicantVariable){
		addMapping(offerSheetApplicantVariable,APPLICANT_ID,TPLabels.getLabel("common.applicantId"));
		addMapping(offerSheetApplicantVariable,APPLICANT_NAME,"Applicant Name");
		addMapping(offerSheetApplicantVariable,APPLICANT_CITY,"Applicant City");
		addMapping(offerSheetApplicantVariable,APPLICANT_EMAIL1,"Applicant Email 1");
		addMapping(offerSheetApplicantVariable,APPLICANT_EMAIL2,"Applicant Email 2");
		addMapping(offerSheetApplicantVariable,APPLICANT_HOME_PHONE,"Applicant Home Phone");
		addMapping(offerSheetApplicantVariable,APPLICANT_CELL_PHONE,"Applicant Cell Phone");
		addMapping(offerSheetApplicantVariable,APPLICANT_WORK_PHONE,"Applicant Work Phone");
		addMapping(offerSheetApplicantVariable,APPLICANT_SKILLS,TPLabels.getLabel("common.applicant")+" "+TPLabels.getLabel("common.skills"));
		addMapping(offerSheetApplicantVariable,APPLICANT_YOP,TPLabels.getLabel("generate_offer_sheet.name.applicant_education_YOP"));
		addMapping(offerSheetApplicantVariable,APPLICANT_INSTITUTE,TPLabels.getLabel("common.applicant")+" "+TPLabels.getLabel("common.institute"));
		addMapping(offerSheetApplicantVariable,APPLICANT_DEGREE_TITLE,TPLabels.getLabel("common.applicant")+" "+TPLabels.getLabel("common.degree"));
		addMapping(offerSheetApplicantVariable,APPLICANT_BRANCH,TPLabels.getLabel("common.applicant")+" "+TPLabels.getLabel("common.branch"));
		addMapping(offerSheetApplicantVariable,APPLICANT_CLASS,TPLabels.getLabel("common.applicant")+" "+TPLabels.getLabel("common.class"));
		addMapping(offerSheetApplicantVariable,APPLICANT_WORKING_SINCE,TPLabels.getLabel("common.working_since"));
		addMapping(offerSheetApplicantVariable,APPLICANT_EXPERIENCE,TPLabels.getLabel("common.experience"));
		addMapping(offerSheetApplicantVariable,APPLICANT_CURRENT_EMPLOYER,"Current Employer");
		addMapping(offerSheetApplicantVariable,APPLICANT_CURRENT_CTC,"Current Ctc");
		addMapping(offerSheetApplicantVariable,APPLICANT_EXPECTED_CTC,"Expected Ctc");
		addMapping(offerSheetApplicantVariable,APPLICANT_SOURCE,"Source");
		addMapping(offerSheetApplicantVariable,APPLICANT_DATE_JOINED,"Joining date");
		addMapping(offerSheetApplicantVariable,APPLICANT_LEVEL_OFFERED,"Level Offered");
		addMapping(offerSheetApplicantVariable,APPLICANT_DESIGNATION_OFFERED,"Designation Offered");
		addMapping(offerSheetApplicantVariable,OFFERED_CTC,TPLabels.getLabel("selection_feedback.label.ctc_offered"));
		addMapping(offerSheetApplicantVariable,INPUT_SALARY_VARIABLE, GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_SALARY_VARIABLE_INPUT_SALARY_LABEL));
		addMapping(offerSheetApplicantVariable,OFFERED_BASIC,TPLabels.getLabel("selection_feedback.label.basic_offered"));
		addMapping(offerSheetApplicantVariable,EMPLOYEE_CODE,"Employee Code");
	}
	
	
	/**
	 * Populates labels for position related variables.
	 * @param offerSheetPositionVariable
	 */
	private static void populateOfferSheetPositionVariable(List<SimpleDataObject> offerSheetPositionVariable){
		addMapping(offerSheetPositionVariable,POSITION_CODE,TPLabels.getLabel("common.position_code"));
		addMapping(offerSheetPositionVariable,POSITION_TITLE,TPLabels.getLabel("common.position_title"));
		addMapping(offerSheetPositionVariable,LOCATION_NAME,TPLabels.getLabel("position.description.location"));
		addMapping(offerSheetPositionVariable,DEPT_NAME,GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_DEPARTMENT_LEVEL_1));
		addMapping(offerSheetPositionVariable,SUB_DEPT_NAME,GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_DEPARTMENT_LEVEL_2));
		addMapping(offerSheetPositionVariable,SUB_SUB_DEPT_NAME,GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_DEPARTMENT_LEVEL_3));
		addMapping(offerSheetPositionVariable,POSITION_GRADE_NAME,GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_BUDGET_ITEM_GRADE_LABEL));
		addMapping(offerSheetPositionVariable,POSITION_BAND_NAME,GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_BUDGET_ITEM_BAND_LABEL));
		addMapping(offerSheetPositionVariable,POSITION_LEVEL,TPLabels.getLabel("common.position")+" "+TPLabels.getLabel("common.level"));
		addMapping(offerSheetPositionVariable,POSITION_NOTE,TPLabels.getLabel("position.description.note"));
	}


	private static void addMapping(List<SimpleDataObject> mapping, String key, String value) {
		SimpleDataObject obj = new SimpleDataObject();
		obj.setAttribute("key", key);
		obj.setAttribute("value", value);
		mapping.add(obj);
	}
	
	public static List<SimpleDataObject> geOfferSheetCommonVariable() {
		return offerSheetCommonVariable;
	}
	
	public static List<SimpleDataObject> getOffersheetapplicantvariable() {
		return offerSheetApplicantVariable;
	}

	public static List<SimpleDataObject> getOffersheetpositionvariable() {
		return offerSheetPositionVariable;
	}
}
