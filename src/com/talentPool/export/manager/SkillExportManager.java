/**
 * 
 */
package com.talentPool.export.manager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.talentPool.common.db.DBPreparedQuery;
import com.talentPool.common.db.SimpleDataObject;
import com.talentPool.common.utils.Utils;
import com.talentPool.user.manager.PermissionSet;

/**
 * @author pallavi
 *
 */
public class SkillExportManager extends ExportManager {
	public List<SimpleDataObject> getData(String ids, PermissionSet permissionSet) throws SQLException {
		DBPreparedQuery dq = null;
		List<SimpleDataObject> data = null;
		try {
			String[] dynParams = new String[1];
			if(Utils.isBlankOrNull(ids)) {
				ids = "-1";
			}
			ArrayList<String> dynamicContent = new ArrayList<String>();
			String qMarks = Utils.setDynamicParamsAndReturnQmarks(ids, dynamicContent);
			dynParams[0] = "WHERE tsc.skill_category_id IN (" + qMarks + ")";
			dq = new DBPreparedQuery("dSkillExportManager_GetData", dynParams);
			int cnt=1;
			for (String dynParamVal : dynamicContent) {
				dq.setString(cnt++, dynParamVal);
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
