/**
 * 
 */
package com.talentPool.employeeservice.manager;

import java.util.HashMap;

import com.talentPool.applicant.manager.ApplicantDuplicateChecker;
import com.talentPool.common.properties.GlobalApplicationProperties;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.employeeservice.scheduler.EmployeeDuplicateUploadAttemptJobScheduler;


/**
 * @author Shantanu
 *
 */
public class EmployeeApplicantDuplicateManager {
	public boolean isDuplicate(HashMap<String, String[]> nameValues) {
		ApplicantDuplicateChecker applicantDuplicateChecker = new ApplicantDuplicateChecker();
		return applicantDuplicateChecker.isDuplicateEmployeeResume(nameValues);
	}

	public void sendEmployeeDuplicateUploadAttemptEmail(String sourceName, String userId, String userFirstName, String positionId, String positionName, HashMap<String, String[]> nameValues) {
		if (GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_SEND_DUPLICATE_RESUME_UPLOAD_TRIED_NOTIFICATION).equals(GlobalConstants.ENABLED)) {
			EmployeeDuplicateUploadAttemptJobScheduler.resetTrigger(sourceName, userId, userFirstName, positionId, positionName, nameValues);
		}
	}
}
