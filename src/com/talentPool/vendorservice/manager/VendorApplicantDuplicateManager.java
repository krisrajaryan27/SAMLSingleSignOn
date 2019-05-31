/**
 * 
 */
package com.talentPool.vendorservice.manager;

import java.util.HashMap;

import com.talentPool.applicant.manager.ApplicantDuplicateChecker;
import com.talentPool.common.properties.GlobalApplicationProperties;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.vendorservice.scheduler.VendorDuplicateUploadAttemptJobScheduler;

/**
 * @author shivprasad
 * 
 */
public class VendorApplicantDuplicateManager {
	public boolean isDuplicate(HashMap<String, String[]> nameValues) {
		ApplicantDuplicateChecker applicantDuplicateChecker = new ApplicantDuplicateChecker();
		return applicantDuplicateChecker.isDuplicateVendorResume(nameValues);
	}

	public void sendVendorDuplicateUploadAttemptEmail(String sourceName, String userId, String userFirstName, String positionId, String positionName, HashMap<String, String[]> nameValues) {
		if (GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_SEND_DUPLICATE_RESUME_UPLOAD_TRIED_NOTIFICATION).equals(GlobalConstants.ENABLED)) {
			VendorDuplicateUploadAttemptJobScheduler.resetTrigger(sourceName, userId, userFirstName, positionId, positionName, nameValues);
		}
	}
}
