/**
 * 
 */
package com.talentPool.vendorservice.manager;

import java.util.ArrayList;
import java.util.Calendar;

import com.talentPool.applicant.dataobject.ApplicantData;
import com.talentPool.applicant.manager.ApplicantManager;
import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.properties.GlobalApplicationProperties;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.utils.Utils;
import com.talentPool.positions.dataobject.UserData;
import com.talentPool.positions.manager.PositionManager;
import com.talentPool.selectionProcess.dataobject.CommunicationData;
import com.talentPool.selectionProcess.manager.SelectionProcessManager;
import com.talentPool.user.UserConstants;
import com.talentPool.vendorservice.dataobject.VcommunicationData;

/**
 * @author shivprasad
 * 
 */
public class VendorCommunicationManager {

	public boolean setCommunication(String applicantId, String userId, String communicationId, String communicationType, String note) {
		boolean success = false;
		try {
			CommunicationData cData = new CommunicationData();
			cData.setApplicantId(applicantId);
			cData.setUserId(userId);
			cData.setCommunicationType(Integer.parseInt(communicationType));
			cData.setCommunicationText(note);
			Calendar cal = Calendar.getInstance();
			cData.setCommunicationDate(new java.sql.Timestamp(cal.getTimeInMillis()));
			SelectionProcessManager selectionProcessManager = new SelectionProcessManager();
			if (Utils.isBlankOrNull(communicationId)) {
				selectionProcessManager.addPhoneLog(cData);
			} else {
				cData.setCommunicationId(communicationId);
				selectionProcessManager.updatePhoneLog(cData);
			}
			setMessages(applicantId, userId, note);
			success = true;
		} catch (Exception e) {
			TPLogger.getLogger().error(e);
		}
		return success;
	}

	private void setMessages(String applicantId, String userId, String note) {
		try {
			if (GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_NOTIFY_VENDOR_ACTIVITY_TO_HR).equals(GlobalConstants.ENABLED)) {
				ApplicantManager applicantManager = new ApplicantManager();
				ApplicantData applicantData = applicantManager.getApplicantSummaryData(applicantId);
				if (!Utils.isBlankOrNull(applicantData.getApplicantPositionId())) {
					String[] roles = new String[2];
					roles[0] = "" + UserConstants.ROLE_HR_MANAGER;
					roles[1] = "" + UserConstants.ROLE_RECRUITER;
					PositionManager positionManager = new PositionManager();
					ArrayList<UserData> users = positionManager.getUsersForPosition(applicantData.getApplicantPositionId(), roles);
					ArrayList<String> toUserIds = new ArrayList<String>();
					for (int i = 0; users != null && i < users.size(); i++) {
						toUserIds.add("" + users.get(i).getUserId());
					}
					SelectionProcessManager selectionProcessManager = new SelectionProcessManager();
					selectionProcessManager.postMessage(applicantId, userId, toUserIds, note, null);
				}
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(e);
		}
	}

	public VcommunicationData getCommunicationData(String applicantId, String communicationId, String communicationType) {
		VcommunicationData vcommunicationData = new VcommunicationData();
		try {
			SelectionProcessManager selectionProcessManager = new SelectionProcessManager();
			CommunicationData communicationData = selectionProcessManager.getCommunicationData(communicationId);
			if (communicationData != null) {
				vcommunicationData.setCommunicationText(communicationData.getCommunicationText());
				vcommunicationData.setCommunicationDate(communicationData.getCommunicationDate());
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(e);
		}
		return vcommunicationData;
	}

}
