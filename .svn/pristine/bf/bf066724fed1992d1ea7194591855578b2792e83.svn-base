/**
 * 
 */
package com.talentPool.vendorservice.manager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.db.DBPreparedQuery;
import com.talentPool.common.db.SimpleDataObject;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.utils.DateUtils;
import com.talentPool.common.utils.Utils;
import com.talentPool.positions.PositionConstants;
import com.talentPool.selectionProcess.SelectionProcessConstants;
import com.talentPool.vendorservice.dataobject.VactivityData;
import com.talentPool.vendorservice.dataobject.VactivityList;
import com.talentPool.vendorservice.dataobject.Vpagination;
import com.talentPool.vendorservice.dataobject.VpositionData;

/**
 * @author pallavi
 * 
 */
public class VendorActivityManager {
	public VactivityList getActivityList(String applicantId, String positionId, String noOfDays, String pageNo, int pageSize, String vendorId, String sourceId) {
		VactivityList vactivityList = new VactivityList();
		List<VactivityData> vactivities = getActivities(applicantId, positionId, noOfDays, pageNo, pageSize, vendorId, sourceId);
		vactivityList.setActivities((ArrayList<VactivityData>) vactivities);
		Vpagination vpagination = getPaginationData(applicantId, positionId, noOfDays, pageNo, pageSize, vendorId, sourceId);
		vactivityList.setPagination(vpagination);
		List<VpositionData> positions = getVendorPositions(sourceId);
		vactivityList.setPositions((ArrayList<VpositionData>) positions);
		return vactivityList;
	}

	private List<VactivityData> getActivities(String applicantId, String positionId, String noOfDays, String pageNo, int pageSize, String vendorId, String sourceId) {
		List<SimpleDataObject> activities = null;
		List<VactivityData> vactivities = new ArrayList<VactivityData>();
		DBPreparedQuery dq = null;
		try {
			String[] dynParam = new String[3];
			if (!Utils.isBlankOrNull(noOfDays)) {
				dynParam[0] = " and datediff(now(), tasp.process_moved_date) <= ?";
			} else {
				dynParam[0] = "";
			}
			if (!Utils.isBlankOrNull(applicantId)) {
				dynParam[1] = " and tasp.applicant_id = ?";
			} else {
				dynParam[1] = "";
			}
			if (!Utils.isBlankOrNull(positionId)) {
				dynParam[2] = " and tasp.position_id = ?";
			} else {
				dynParam[2] = "";
			}
			int lowerLimit = (Integer.parseInt(pageNo) - 1) * pageSize;
			dq = new DBPreparedQuery("dVendorActivityManager_GetActivities", dynParam);
			dq.setString(1, SelectionProcessConstants.STEP_JOIN);
			dq.setString(2, SelectionProcessConstants.STEP_REJECT);
			dq.setString(3, SelectionProcessConstants.STEP_NOT_ATTENDED);
			dq.setString(4, SelectionProcessConstants.STEP_NOT_INTERESTED_REJECT);
			dq.setString(5, SelectionProcessConstants.STEP_POSITION_CLOSED_REJECT);
			dq.setString(6, PositionConstants.STEP_LEVEL_ACCEPT);
			dq.setString(7, GlobalConstants.PROPERTY_SHOW_DETAILED_ACTIVITY_TO_VENDOR);
			dq.setString(8, GlobalConstants.ENABLED);
			dq.setInt(9, PositionConstants.STEP_SCHEDULED);
			dq.setId(10, vendorId);
			dq.setId(11, sourceId);
			int index = 12;
			if (!Utils.isBlankOrNull(noOfDays)) {
				dq.setString(index++, noOfDays);
			}
			if (!Utils.isBlankOrNull(applicantId)) {
				dq.setString(index++, applicantId);
			}
			if (!Utils.isBlankOrNull(positionId)) {
				dq.setString(index++, positionId);
			}
			dq.setInt(index++, lowerLimit);
			dq.setInt(index++, pageSize);
			activities = (List<SimpleDataObject>) dq.getResult();

			if (activities != null && activities.size() > 0) {
				Iterator<SimpleDataObject> itr = activities.iterator();
				while (itr.hasNext()) {
					SimpleDataObject sDo = itr.next();
					vactivities.add(constructVactivityData(sDo));
				}
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while getting activities", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return vactivities;
	}

	private VactivityData constructVactivityData(SimpleDataObject sDo) {
		VactivityData vactivityData = new VactivityData();
		vactivityData.setApplicantId(sDo.getString("applicantId"));
		vactivityData.setApplicantName(sDo.getString("applicantName"));
		vactivityData.setPositionId(sDo.getString("positionId"));
		vactivityData.setPositionTitle(sDo.getString("positionTitle"));
		try {
			vactivityData.setActivityDate(DateUtils.getSystemDateFormat(sDo.getDate("activityDate")));			
		} catch (ClassCastException cce) {
			vactivityData.setActivityDate("");
			TPLogger.getLogger().error(GlobalConstants.ERROR, cce);
		}
		vactivityData.setActivity(sDo.getString("activity"));
		return vactivityData;
	}

	private Vpagination getPaginationData(String applicantId, String positionId, String noOfDays, String pageNo, int pageSize, String vendorId, String sourceId) {
		long recordCount = getRecordCount(applicantId, positionId, noOfDays, vendorId, sourceId);
		Vpagination vpagination = new Vpagination(recordCount, pageSize, Integer.parseInt(pageNo));
		return vpagination;
	}

	private long getRecordCount(String applicantId, String positionId, String noOfDays, String vendorId, String sourceId) {
		long recordCount = 0;
		DBPreparedQuery dq = null;
		try {
			String[] dynParam = new String[3];
			dynParam[0] = "";
			if (!Utils.isBlankOrNull(noOfDays)) {
				dynParam[0] = " and datediff(now(), tasp.process_moved_date) <= ? ";
			} 
			dynParam[1] = "";
			if (!Utils.isBlankOrNull(applicantId)) {
				dynParam[1] = " and tasp.applicant_id = ? ";
			}
			dynParam[2] = "";
			if (!Utils.isBlankOrNull(positionId)) {
				dynParam[2] = " and tasp.position_id = ? ";
			}
			dq = new DBPreparedQuery("dVendorActivityManager_GetActivitiesCount", dynParam);
			dq.setId(1, vendorId);
			dq.setId(2, sourceId);
			int index = 3;
			if (!Utils.isBlankOrNull(noOfDays)) {
				dq.setString(index++, noOfDays);
			}
			if (!Utils.isBlankOrNull(applicantId)) {
				dq.setString(index++, applicantId);
			}
			if (!Utils.isBlankOrNull(positionId)) {
				dq.setString(index++, positionId);
			}
			recordCount = new Long(dq.getIdResult()).longValue();
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while getting the record count", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return recordCount;
	}

	private List<VpositionData> getVendorPositions(String sourceId) {
		List<VpositionData> vpositions = new ArrayList<VpositionData>();
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dVendorActivityManager_GetPositions");
			dq.setId(1, sourceId);
			dq.setId(2, PositionConstants.POSITION_STATUS_OPENED);
			List<SimpleDataObject> positions = dq.getResult();
			if (positions != null && positions.size() > 0) {
				Iterator<SimpleDataObject> itr = positions.iterator();
				while (itr.hasNext()) {
					SimpleDataObject sDo = itr.next();
					vpositions.add(constructVpositionData(sDo));
				}
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while getting positions for vendor", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return vpositions;
	}

	private VpositionData constructVpositionData(SimpleDataObject sDo) {
		VpositionData vpositionData = new VpositionData();
		vpositionData.setPositionId(sDo.getString("positionId"));
		vpositionData.setPositionTitle(sDo.getString("positionTitle"));
		return vpositionData;
	}
}
