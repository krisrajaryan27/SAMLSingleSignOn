package com.talentPool.positions.manager;

import java.util.ArrayList;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.db.DBPreparedQuery;
import com.talentPool.common.properties.GlobalApplicationProperties;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.utils.Utils;
import com.talentPool.custom.constants.CustomFieldConstants;
import com.talentPool.positions.PositionConstants;
import com.talentPool.positions.dataobject.PositionTemplateFilterData;
import com.talentPool.user.manager.PermissionSet;

public class PositionTemplateManager {
	public ArrayList getAllTemplates(PositionTemplateFilterData filterData, PermissionSet permissionSet) {	
			DBPreparedQuery dq = null;
			ArrayList positions = null;
			try {
				String[] dynParam = new String[4];
				ArrayList<String> dynamicContent = new ArrayList<String>();
				dynParam[0] = " ";
				dynParam[1] = " ";
				dynParam[2] = " ";
				dynParam[3] = " ";				
				
				if (!Utils.isBlankOrNull(filterData.getPositionOwnerId())) {
					dynParam[2] += " AND tp.position_owner_id=? ";
					dynamicContent.add(filterData.getPositionOwnerId());
				}
				if (!Utils.isBlankOrNull(filterData.getDepartmentId())) {
					dynParam[2] += " AND tp.dept_id=? ";
					dynamicContent.add(filterData.getDepartmentId());
				}
				if (!Utils.isBlankOrNull(filterData.getSubDepartmentId())) {
					dynParam[2] += " AND tp.sub_dept_id=? ";
					dynamicContent.add(filterData.getSubDepartmentId());
				}
				if (!Utils.isBlankOrNull(filterData.getSubSubDepartmentId())) {
					dynParam[2] += " AND tp.sub_sub_dept_id=? ";
					dynamicContent.add(filterData.getSubSubDepartmentId());
				}
				if (!Utils.isBlankOrNull(filterData.getSub3DepartmentId())) {
					dynParam[2] += " AND tp.sub3_dept_id=? ";
					dynamicContent.add(filterData.getSub3DepartmentId());
				}
				if (!Utils.isBlankOrNull(filterData.getSub4DepartmentId())) {
					dynParam[2] += " AND tp.sub4_dept_id=? ";
					dynamicContent.add(filterData.getSub4DepartmentId());
				}
				if (!Utils.isBlankOrNull(filterData.getLocationId())) {
					dynParam[2] += " AND tpl.location_id=? ";
					dynamicContent.add(filterData.getLocationId());
				}		

				if (!Utils.isBlankOrNull(filterData.getRecruiterId())) {
					dynParam[1] += " , tp_position_step_users tpsu ";
					dynParam[2] += " AND tpsu.position_id = tp.position_id ";
					dynParam[2] += " AND tpsu.user_id = ? ";
					dynamicContent.add(filterData.getRecruiterId());
				}
				
				if (!Utils.isBlankOrNull(filterData.getSkillId())) {
					dynParam[1] += " , tp_position_skills tps ";
					dynParam[2] += " AND tps.position_id = tp.position_id ";
					dynParam[2] += " AND tps.skill_id = ? ";
					dynamicContent.add(filterData.getSkillId());
				}
				
				if (!Utils.isBlankOrNull(filterData.getPositionName())) {
					String showPositionCode = GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_SHOW_POSITION_CODE);
					if(showPositionCode.equals(GlobalConstants.DISABLED)){
						dynParam[2] += " AND tp.position_title LIKE ? ";
					}else{
						dynParam[2] += " AND tp.position_code LIKE ? ";
					}				
					dynamicContent.add(filterData.getPositionName() + "%");
				}				
				
				if (!Utils.isBlankOrNull(filterData.getCustomFieldFilterId())) {
					dynParam[0] +=" ,group_concat(DISTINCT ifnull(tvp.string_value,ifnull(tvp.number_value,tvp.date_value))   separator '|' ) as customVal ";
					dynParam[2] += " AND tvp.custom_field_id = ? ";
					dynamicContent.add(filterData.getCustomFieldFilterId());
										
					dynParam[3] = " WHERE customVal=?";
					try {
						if(CustomFieldConstants.TYPE_DATE.equals(filterData.getCustomFieldFilterType()))
							dynamicContent.add(Utils.getDateStringConvertedToOtherDateFormat(filterData.getCustomFieldFilterValue(),Utils.regEUDateFormat,Utils.redDDMMYYYYFormat));
						else	
							dynamicContent.add(filterData.getCustomFieldFilterValue());
					} catch (Exception e) {
						dynamicContent.add("");
					}
				}
				
			/*	if (permissionSet.isSHOW_POSITIONS_WITH_RIGHTS()) {
					dynParam[2] += " AND tp.position_id IN ( ";
					dynParam[2] += " SELECT su.position_id FROM tp_position_step_users su, tp_position_steps ps WHERE ";
					dynParam[2] += " su.position_step_id = ps.position_step_id and ps.position_step_status = ? AND ";
					dynParam[2] += " su.user_id = ? ";
					dynParam[2] += " UNION ";
					dynParam[2] += " SELECT position_id from tp_positions where position_requested_by =? ";
					dynParam[2] += " UNION ";
					dynParam[2] += " SELECT distinct position_id FROM tp_requisition_approval_feedback WHERE by_user_id=? OR to_user_id=? ";
					dynParam[2] += " )";
					dynamicContent.add(PositionConstants.STEP_ACTIVE);
					dynamicContent.add(filterData.getUserId());
					dynamicContent.add(filterData.getUserId());
					dynamicContent.add(filterData.getUserId());
					dynamicContent.add(filterData.getUserId());
				}*/

				dq = new DBPreparedQuery("dPositionTemplateManager_GetAllTemplates", dynParam);
				dq.setString(1, PositionConstants.POSITION_STATUS_TEMPLATE);
				int cnt = 2;
				for (int i = 0; i < dynamicContent.size(); i++) {
					dq.setString(cnt++, dynamicContent.get(i));
				}		
				positions = dq.getResult();
			} catch (Exception exep) {
				TPLogger.getLogger().error(GlobalConstants.ERROR, exep);
			} finally {
				if (dq != null) {
					dq.releaseConnection();
				}
			}
			return positions;
	}

}
