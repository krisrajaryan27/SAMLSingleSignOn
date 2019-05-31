package com.talentPool.applicant.utils;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;

import com.talentPool.common.properties.GlobalApplicationProperties;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.utils.Utils;

public class ApplicantValidators {
	
	/**
	 * Validates Applicant Email Addresses and if any error found will be added to ActionErrors
	 * @param errors
	 * @param applicantEmail1
	 * @param applicantEmail2
	 * @return
	 */
	public static void validateApplicantEmail(ActionErrors errors,String applicantEmail1,String applicantEmail2){
		if (!Utils.isBlankOrNull(applicantEmail1)) {
			if (!Utils.isValidPattern(applicantEmail1, Utils.regEmail)) {
				errors.add("add_applicant.errors.email1_not_valid", new ActionError("add_applicant.errors.email1_not_valid"));
			}
		}
		if (!Utils.isBlankOrNull(applicantEmail2)) {
			if (!Utils.isValidPattern(applicantEmail2, Utils.regEmail)) {
				errors.add("add_applicant.errors.email2_not_valid", new ActionError("add_applicant.errors.email2_not_valid"));
			}
		}
	}
	
	/**
	 * Validates Applicant Input Salary variable to integer and if any error found will be added to ActionErrors
	 * @param errors
	 * @param applicantEmail1
	 * @param applicantEmail2
	 * @return
	 */
	public static void validateApplicantInputSalaryVariable(ActionErrors errors,String inputSalaryVariable){
		try {
			if(!Utils.isBlankOrNull(inputSalaryVariable))
				Integer.parseInt(inputSalaryVariable);
		} catch (NumberFormatException e) {
			errors.add("selection_feedback.error.please_enter_input_salary_variable_number", new ActionError("selection_feedback.error.please_enter_input_salary_variable_number", GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_SALARY_VARIABLE_INPUT_SALARY_LABEL)));
		}
	}

}
