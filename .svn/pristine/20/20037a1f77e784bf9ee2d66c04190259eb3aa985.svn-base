/**
 * 
 */
package com.talentPool.export.manager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.talentPool.applicant.constants.ImportConfigurationConstants;
import com.talentPool.applicant.manager.ImportConfigurationManager;
import com.talentPool.common.db.DBPreparedQuery;
import com.talentPool.common.db.SimpleDataObject;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.utils.Utils;
import com.talentPool.user.manager.PermissionSet;

/**
 * @author pallavi
 *
 */
public class ApplicantExportManager extends ExportManager {
	public List<SimpleDataObject> getData(String ids, PermissionSet permissionSet) throws SQLException {
		DBPreparedQuery dq = null;
		List<SimpleDataObject> data = null;
		try {
			String[] dynParams = new String[2];
			ArrayList<String> dynamicContent = new ArrayList<String>();
			dynParams[0] = "ts.source_title";
			if(!ImportConfigurationManager.isApplicantFieldViewable(ImportConfigurationConstants.FIELD_SOURCE ,permissionSet.isSHOW_CONFIDENTIAL_DATA())){
				dynParams[0] = "'"+GlobalConstants.CONFIDENTIAL_CHARACTER+"'";
			}else{
				dynParams[0] = "ts.source_title";
			}
			if(Utils.isBlankOrNull(ids)) {
				ids = "-1";
			}
			
			String qMarks = Utils.setDynamicParamsAndReturnQmarks(ids, dynamicContent);
			dynParams[1] = "WHERE ta.applicant_id IN (" + qMarks + ")";
			dq = new DBPreparedQuery("dApplicantExportManager_GetData", dynParams);
			
			//Show Current CTC or not
			dq.setBoolean(1, ImportConfigurationManager.isCurrentCTCViewable(permissionSet));
			dq.setString(2, GlobalConstants.CONFIDENTIAL_CHARACTER);

			//Show Expected CTC or not
			dq.setBoolean(3, ImportConfigurationManager.isExpectedCTCViewable(permissionSet));
			dq.setString(4, GlobalConstants.CONFIDENTIAL_CHARACTER);
			
			//Show Level Offered or not			
			dq.setBoolean(5, ImportConfigurationManager.isLevelOfferedViewable(permissionSet));
			dq.setString(6, GlobalConstants.CONFIDENTIAL_CHARACTER);
			
			//Show Designation Offered or not
			dq.setBoolean(7, ImportConfigurationManager.isDesignationOfferedViewable(permissionSet));
			dq.setString(8, GlobalConstants.CONFIDENTIAL_CHARACTER);
			
			//Show Target CTC or not
			dq.setBoolean(9, ImportConfigurationManager.isCTCOfferedViewable(permissionSet));
			dq.setString(10, GlobalConstants.CONFIDENTIAL_CHARACTER);
			
			int cnt = 11;
			for (int i = 0; i < dynamicContent.size(); i++) {
				dq.setString(cnt++, dynamicContent.get(i));
			}
			
			data = (List<SimpleDataObject>) dq.getResult();
		} finally {
			if(dq != null) {
				dq.releaseConnection();
			}
		}
		return data;
	}
}
