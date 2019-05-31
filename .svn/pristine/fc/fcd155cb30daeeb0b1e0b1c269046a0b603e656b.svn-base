package com.talentPool.export.manager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.talentPool.common.CommonConstants;
import com.talentPool.common.db.DBPreparedQuery;
import com.talentPool.common.db.SimpleDataObject;
import com.talentPool.common.properties.TPLabels;
import com.talentPool.common.utils.Utils;
import com.talentPool.custom.constants.CustomFieldConstants;
import com.talentPool.custom.dataobject.CustomFieldData;
import com.talentPool.custom.manager.CustomFieldManager;
import com.talentPool.positions.PositionConstants;
import com.talentPool.positions.manager.PositionManager;
import com.talentPool.selectionProcess.SelectionProcessConstants;
import com.talentPool.user.manager.PermissionSet;

/**
 * @author SumeetS
 * exports the positions to excel
 *
 */
public class PositionExportManager extends ExportManager {
	@SuppressWarnings("unchecked")
	public List<SimpleDataObject> getData(String ids, PermissionSet permissionSet) throws SQLException {
		List<SimpleDataObject> result = null;
		DBPreparedQuery dq = null;
		try {
			String[] dynParams = new String[1];
			ArrayList<String> dynamicContent = new ArrayList<String>();
			String qMarks = Utils.setDynamicParamsAndReturnQmarks(ids,
					dynamicContent);
			dynParams[0] = qMarks;
			dq = new DBPreparedQuery("dPositionExportManger_GetData", dynParams);
			int cnt = 1;
			dq.setString(cnt++, PositionConstants.POSITIONS_TYPE_EXTERNAL);
			dq.setString(cnt++, PositionConstants.POSITIONS_TYPE_INTERNAL);
			dq.setString(cnt++, PositionConstants.POSITION_STATUS_DELETED);
			dq.setString(cnt++, PositionConstants.POSITION_STATUS_OPENED);
			dq.setString(cnt++, PositionConstants.POSITION_STATUS_CLOSED);
			dq.setString(cnt++, PositionConstants.POSITION_STATUS_INPROCESS);
			dq.setString(cnt++, PositionConstants.POSITION_STATUS_REJECTED);
			dq.setString(cnt++, PositionConstants.POSITION_STATUS_HOLD);
			dq.setString(cnt++, PositionConstants.POSITION_STATUS_TEMPLATE);
			dq.setString(cnt++, PositionConstants.POSITION_STATUS_TO_BE_OPENED);
			dq.setString(cnt++, PositionConstants.POSITION_STATUS_MY_APPROVAL_PENDING);
			dq.setString(cnt++, PositionConstants.STEP_LEVEL_SHORTLIST);
			dq.setString(cnt++, PositionConstants.STEP_LEVEL_SELECT);
			dq.setString(cnt++, PositionConstants.STEP_LEVEL_ACCEPT);
			dq.setString(cnt++, SelectionProcessConstants.STEP_REJECT);
			dq.setString(cnt++, SelectionProcessConstants.STEP_NOT_INTERESTED_REJECT);
			dq.setString(cnt++, SelectionProcessConstants.STEP_NOT_ATTENDED);
			dq.setString(cnt++, SelectionProcessConstants.STEP_POSITION_CLOSED_REJECT);
			dq.setString(cnt++, PositionConstants.POSITIONS_TYPE_OF_VACANCY_FRESH);
			dq.setString(cnt++, PositionConstants.POSITIONS_TYPE_OF_VACANCY_REPLACEMENT);
			dq.setString(cnt++, PositionConstants.POSITION_SKILL_PRIMARY);
			dq.setString(cnt++, PositionConstants.POSITION_SKILL_SECONDARY);

			for (String t : dynamicContent) {
				dq.setString(cnt++, t);
			}
			result = (List<SimpleDataObject>) dq.getResult();
			ArrayList<CustomFieldData> fields = null;
			if (CustomFieldManager.isCustomFieldsAvailable(CustomFieldConstants.ENTITY_TYPE_POSITION)) {
				CustomFieldManager customFieldManager = new CustomFieldManager();
				fields = customFieldManager.getCustomFieldsForInputAllowed(CustomFieldConstants.ENTITY_TYPE_POSITION, CustomFieldConstants.INPUT_ALLOWED, true);
				for (SimpleDataObject sdo:result){
					
					ArrayList<CustomFieldData> customFields = customFieldManager.getCustomFieldDataForEntity(sdo.getString("positionId"), CustomFieldConstants.ENTITY_TYPE_POSITION);
					for (CustomFieldData customField:customFields){
						int i = 32;
						for (CustomFieldData field:fields){
							if (field.getFieldDisplayName().equalsIgnoreCase(customField.getFieldDisplayName())){
								if (CustomFieldConstants.TYPE_DATE.equalsIgnoreCase(customField.getFieldType())){
									sdo.setAttribute("fld"+String.valueOf(i), Utils.getDateConvertedToString(customField.getFieldDateValue(), "dd MMM yyyy"));
								}else{
									sdo.setAttribute("fld"+String.valueOf(i), customField.getFieldStringValue());
									if (Utils.isBlankOrNull(customField.getFieldStringValue())){
										sdo.setAttribute("fld"+String.valueOf(i), customField.getFieldNumberValueInString());
									}
								}
							}
							i++;
						}
					}
					if (!Utils.isBlankOrNull(sdo.getString("fld27"))){
						if (PositionConstants.POSITIONS_TYPE_OF_VACANCY_FRESH.equals(sdo.getString("fld27"))){
							sdo.setAttribute("fld27", TPLabels.getLabel("position.description.fresh"));
						}
						if (PositionConstants.POSITIONS_TYPE_OF_VACANCY_REPLACEMENT.equals(sdo.getString("fld27"))){
							sdo.setAttribute("fld27", TPLabels.getLabel("position.description.replacement"));
						}
					}
					if (!PositionConstants.POSITION_STATUS_CLOSED.equals(sdo.getString("positionStatus"))){
						sdo.setAttribute("fld28", "");
						sdo.setAttribute("fld26", "");
					} else {
						String closedBy = sdo.getString("fld28");
						if(CommonConstants.DEFAULT_SELECT_VALUE.equals(closedBy)) {
							sdo.setAttribute("fld41", TPLabels.getLabel("position.closed_by.auto_workflow"));
						}
					}
					PositionManager positionManager = new PositionManager();
					List<SimpleDataObject> hiringProcess = positionManager.getPositionHiringProcessToView(sdo.getString("positionId"));
					String allUsers = "";
					for (SimpleDataObject hdo:hiringProcess){
						if (!Utils.isBlankOrNull(hdo.getString("assignedToUsers"))){
							allUsers= allUsers + "," +hdo.getString("assignedToUsers");
						}
						if (!Utils.isBlankOrNull(hdo.getString("scheduledByUsers"))){
							allUsers= allUsers + "," +hdo.getString("scheduledByUsers");
						}
						if (!Utils.isBlankOrNull(hdo.getString("decisionMakerUsers"))){
							allUsers= allUsers + "," +hdo.getString("decisionMakerUsers");
						}
					}
					if (!Utils.isBlankOrNull(allUsers)){
						allUsers = allUsers.substring(1, allUsers.length());
					}
					sdo.setAttribute("fld31", allUsers );
				}
			} else {
				for (SimpleDataObject sdo:result){
					if (!Utils.isBlankOrNull(sdo.getString("fld37"))){
						if (PositionConstants.POSITIONS_TYPE_OF_VACANCY_FRESH.equals(sdo.getString("fld37"))){
							sdo.setAttribute("fld27", TPLabels.getLabel("position.description.fresh"));
						}
						if (PositionConstants.POSITIONS_TYPE_OF_VACANCY_REPLACEMENT.equals(sdo.getString("fld37"))){
							sdo.setAttribute("fld27", TPLabels.getLabel("position.description.replacement"));
						}
					}
					if (!PositionConstants.POSITION_STATUS_CLOSED.equals(sdo.getString("positionStatus"))){
						sdo.setAttribute("fld28", "");
						sdo.setAttribute("fld26", "");
					} else {
						String closedBy = sdo.getString("fld28");
					}
					PositionManager positionManager = new PositionManager();
					List<SimpleDataObject> hiringProcess = positionManager.getPositionHiringProcessToView(sdo.getString("positionId"));
					String allUsers = "";
					for (SimpleDataObject hdo:hiringProcess){
						if (!Utils.isBlankOrNull(hdo.getString("assignedToUsers"))){
							allUsers= allUsers + "," +hdo.getString("assignedToUsers");
						}
						if (!Utils.isBlankOrNull(hdo.getString("scheduledByUsers"))){
							allUsers= allUsers + "," +hdo.getString("scheduledByUsers");
						}
						if (!Utils.isBlankOrNull(hdo.getString("decisionMakerUsers"))){
							allUsers= allUsers + "," +hdo.getString("decisionMakerUsers");
						}
					}
					if (!Utils.isBlankOrNull(allUsers)){
						allUsers = allUsers.substring(1, allUsers.length());
					}
					sdo.setAttribute("fld30", allUsers );
				}
			}
			
		}finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return result;
	}
}
