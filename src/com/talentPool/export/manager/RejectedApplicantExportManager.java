/**
 * 
 */
package com.talentPool.export.manager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.talentPool.applicant.constants.ImportConfigurationConstants;
import com.talentPool.applicant.manager.ImportConfigurationManager;
import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.db.DBPreparedQuery;
import com.talentPool.common.db.SimpleDataObject;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.utils.Utils;
import com.talentPool.selectionProcess.SelectionProcessConstants;
import com.talentPool.user.manager.PermissionSet;

/**
 * @author SiddharthK
 * 
 */
public class RejectedApplicantExportManager extends ExportManager {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.talentPool.export.manager.ExportManager#getData(java.lang.String,
	 * com.talentPool.user.manager.PermissionSet)
	 * 
	 * @return List<SimpleDataObject>
	 * 
	 * @throws SQLException
	 * 
	 * @param String ids, permissionSet
	 */
	@SuppressWarnings("unchecked")
	public List<SimpleDataObject> getData(String ids,
			PermissionSet permissionSet, String positionId, String rejectedBy, String applicantName, String stepName) throws SQLException {
		DBPreparedQuery dq = null;
		List<SimpleDataObject> data = null;
		try {
			String[] dynParams = new String[3];
			dynParams[0] = "ts.source_title";
			dynParams[2] = "";
			ArrayList<String> dynamicContent = new ArrayList<String>();
			ArrayList<String> dynamicContentForParam1 = new ArrayList<String>();
			
			if (!ImportConfigurationManager.isApplicantFieldViewable(
					ImportConfigurationConstants.FIELD_SOURCE,
					permissionSet.isSHOW_CONFIDENTIAL_DATA())) {
				dynParams[0] = "'" + GlobalConstants.CONFIDENTIAL_CHARACTER
						+ "'";
			}
			if (Utils.isBlankOrNull(ids)) {
				ids = "-1";
			}
			
			String qMarks = Utils.setDynamicParamsAndReturnQmarks(ids, dynamicContentForParam1);
			dynParams[1] = "AND ta.applicant_id IN (" + qMarks + ")";
			
			if(!Utils.isBlankOrNull(positionId)){
				dynParams[2] += " AND tp.position_id =?  ";
				dynamicContent.add(positionId);
			}
			
			if(!Utils.isBlankOrNull(stepName)){
				dynParams[2] += " AND tps.position_step_title =?  ";
				dynamicContent.add(stepName);
			}
			
			if(!Utils.isBlankOrNull(rejectedBy)){
				dynParams[2] += " AND CONCAT(tu.user_fname,' ',tu.user_lname) =?  ";
				dynamicContent.add(rejectedBy);
			}
			
			if (!Utils.isBlankOrNull(applicantName)) {
				dynParams[2] += " AND ta.applicant_name like ? ";
				dynamicContent.add(applicantName + "%");
			}
			
			dq = new DBPreparedQuery("dRejectedApplicantExportManager_GetData",
					dynParams);

			// Show Current CTC or not
			dq.setBoolean(1, ImportConfigurationManager
					.isCurrentCTCViewable(permissionSet));
			dq.setString(2, GlobalConstants.CONFIDENTIAL_CHARACTER);

			// Show Expected CTC or not
			dq.setBoolean(3, ImportConfigurationManager
					.isExpectedCTCViewable(permissionSet));
			dq.setString(4, GlobalConstants.CONFIDENTIAL_CHARACTER);

			dq.setString(5, SelectionProcessConstants.STEP_REJECT);
			dq.setString(6,
					SelectionProcessConstants.STEP_NOT_INTERESTED_REJECT);
			dq.setString(7, SelectionProcessConstants.STEP_NOT_ATTENDED);
			dq.setString(8,
					SelectionProcessConstants.STEP_POSITION_CLOSED_REJECT);
			
			int cnt=9;
			
			for (String dynParamVal : dynamicContentForParam1) {
				dq.setString(cnt++, dynParamVal);
			} 
			
			for (String dynParamVal : dynamicContent) {
				dq.setString(cnt++, dynParamVal);
			} 

			data = (List<SimpleDataObject>) dq.getResult();
		} catch (SQLException sqle) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, sqle);
			throw sqle;
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return data;
	}
}
