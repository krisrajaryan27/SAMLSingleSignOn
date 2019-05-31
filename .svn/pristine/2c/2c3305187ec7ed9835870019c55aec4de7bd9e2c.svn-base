package com.talentPool.customReports.manager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeMap;

import com.talentPool.common.CommonConstants;
import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.db.DBPreparedQuery;
import com.talentPool.common.db.DBTransaction;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.utils.Utils;
import com.talentPool.custom.constants.CustomFieldConstants;
import com.talentPool.custom.dataobject.CRColumnCustomFieldMapData;
import com.talentPool.custom.manager.CRColumnCustomFieldMapManager;
import com.talentPool.customReports.dataobject.PositionMasterData;
import com.talentPool.positions.PositionConstants;
import com.talentPool.positions.utils.PositionUtils;

/**
 * @author Sachinm
 *
 */
public class PositionMasterTableManager {

	/**
	 * @param tran
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void updatePositionMaster(DBTransaction tran) throws Exception {
		DBPreparedQuery dq = null;
		ArrayList<PositionMasterData> positions = new ArrayList<PositionMasterData>();
		try {
			TPLogger.getLogger().info("========= START Position Master Update=============");
			dq = new DBPreparedQuery("dReportTableManager_DeleteAllPositionRows", tran);
			dq.execute();

			updateCustomFieldReportColumns(tran);

			dq = new DBPreparedQuery("dReportTableManager_FetchPositionData", tran);
			dq.setString(1, PositionConstants.POSITION_STATUS_OPENED);
			dq.setString(2, PositionConstants.POSITION_STATUS_CLOSED);
			dq.setString(3, PositionConstants.POSITION_STATUS_HOLD);
			positions = dq.getResult();

			TreeMap<Integer, CRColumnCustomFieldMapData> reportPosCustomFieldIdMap = getReportCustomFieldIdMapFor(CustomFieldConstants.ENTITY_TYPE_POSITION);
			positions = formatPositionMasterData(positions,	reportPosCustomFieldIdMap);

			for (int i = 0; i < positions.size(); i++) {
				PositionMasterData aData = positions.get(i);
				dq = new DBPreparedQuery("dReportTableManager_AddPositionData",	tran);

				int cnt = 1;
				dq.setString(cnt++, aData.getPositionId());
				dq.setString(cnt++,	Utils.getBlankIfNull(aData.getPositionCode()));
				dq.setString(cnt++, aData.getPositionTitle());
				dq.setString(cnt++, aData.getPositionNoofOpenings());
				dq.setString(cnt++, aData.getPositionStatus());
				dq.setString(cnt++, PositionUtils.getPositionStatusValues(aData.getPositionStatus()));
				dq.setDate(cnt++, aData.getPositionDateExpiry());
				dq.setDate(cnt++, aData.getPositionDateCreated());
				dq.setDate(cnt++, aData.getPositionDateApproved());
				dq.setString(cnt++,	Utils.getBlankIfNull(aData.getPositionNote()));
				dq.setString(cnt++, PositionUtils.getPositionPriorityValues(aData.getPositionPriority()));
				dq.setString(cnt++,	Utils.getBlankIfNull(aData.getPositionLevel()));
				dq.setString(cnt++,	Utils.getBlankIfNull(aData.getPositionReferalFees()));
				dq.setString(cnt++, PositionUtils.getVacancyTypeValues(aData.getTypeOfVacancy()));
				dq.setString(cnt++,	Utils.getBlankIfNull(aData.getReplacementEmpCode()));
				dq.setString(cnt++, aData.getPositionCreatedBy());
				dq.setString(cnt++, aData.getPositionOwnerId());
				dq.setString(cnt++,	Utils.getBlankIfNull(aData.getPositionOwner()));
				dq.setString(cnt++,	Utils.getBlankIfNull(aData.getPositionRequestedBy()));
				dq.setString(cnt++, aData.getDeptId());
				dq.setString(cnt++, aData.getDeptName());
				dq.setString(cnt++, aData.getSubDeptId());
				dq.setString(cnt++, Utils.getBlankIfNull(aData.getSubDept()));
				dq.setString(cnt++, aData.getSub2DeptId());
				dq.setString(cnt++, Utils.getBlankIfNull(aData.getSub2Dept()));
				dq.setString(cnt++, aData.getSub3DeptId());
				dq.setString(cnt++, Utils.getBlankIfNull(aData.getSub3Dept()));
				dq.setString(cnt++, aData.getSub4DeptId());
				dq.setString(cnt++, Utils.getBlankIfNull(aData.getSub4Dept()));
				dq.setString(cnt++,	Utils.getBlankIfNull(aData.getBudgetItemName()));
				dq.setString(cnt++, Utils.getBlankIfNull(aData.getGradeName()));
				dq.setString(cnt++, Utils.getBlankIfNull(aData.getBandName()));
				dq.setString(cnt++, aData.getSkills());
				dq.setString(cnt++, aData.getLocations());
				dq.setString(cnt++, Utils.getBlankIfNull(aData.getCostCenter()));
				dq.setString(cnt++, Utils.getBlankIfNull(aData.getBu()));

				// Add custom field values to insert query for position master
				for (String cusField : aData.getCustomFields()) {
					dq.setString(cnt++, cusField);
				}
				dq.setDate(cnt++, aData.getPositionDateClosed());

				dq.execute();
			}
			TPLogger.getLogger().info("========= END Position Master Update=============");
		} catch (SQLException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			throw e;
		} finally {
			if (dq != null) {
				dq.closeOpenCursors();
			}
		}
	}

	private ArrayList<PositionMasterData> formatPositionMasterData(ArrayList<PositionMasterData> positions,
			TreeMap<Integer, CRColumnCustomFieldMapData> reportPosCustomFields) {
		try {
			Set<Integer> cusFieldIds = reportPosCustomFields.keySet();
			
			for (int i = 0; i < positions.size(); i++) {
				PositionMasterData aData = positions.get(i);
				String custom = aData.getCustom();
				String[] cusFields = new String[20];
				if (!Utils.isBlankOrNull(custom)) {
					String[] customStr = custom.split("##");
					if (customStr != null && customStr.length > 0) {
						for (int j = 0; j < customStr.length; j++) {
							String customField = customStr[j];
							String[] customFieldPair = customField.split("=");
							if (null != customFieldPair	&& customFieldPair.length > 1
									&& !Utils.isBlankOrNull(customFieldPair[1])
									&& cusFieldIds.contains(Integer.parseInt(customFieldPair[0]))) {
								CRColumnCustomFieldMapData data = reportPosCustomFields.get(Integer.parseInt(customFieldPair[0]));
								String displayName = data.getColumnDisplayName();
								int cf_number = Integer.parseInt(displayName.substring(displayName.lastIndexOf(' ') + 1));
								cusFields[cf_number-1] = customFieldPair[1];
							}
						}
					}
				}
				aData.setCustomFields(cusFields);
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return positions;
	}
	
	public TreeMap<Integer, CRColumnCustomFieldMapData> getReportCustomFieldIdMapFor(int entityType) {
		TreeMap<Integer, CRColumnCustomFieldMapData> reportPosCustomFieldsById = new TreeMap<Integer, CRColumnCustomFieldMapData>();
		CRColumnCustomFieldMapManager customFieldManager = new CRColumnCustomFieldMapManager();
		ArrayList<CRColumnCustomFieldMapData> posCustomFields = customFieldManager.getCustomFieldsFor(entityType);
		for (CRColumnCustomFieldMapData posCusField : posCustomFields) {
			if (!Utils.isBlankOrNull(posCusField.getCustomFieldId())) {
				reportPosCustomFieldsById.put(Integer.parseInt(posCusField.getCustomFieldId()), posCusField);
			}
		}
		return reportPosCustomFieldsById;
	}
	
	public void updateCustomFieldReportColumns(DBTransaction tran) throws Exception {
		
		CRColumnCustomFieldMapManager customFieldManager = new CRColumnCustomFieldMapManager();
		TreeMap<String, CRColumnCustomFieldMapData> reportPosCustomFields = customFieldManager.getReportPosCustomFieldPropertyMap(tran);
						
		Set<String> iter = reportPosCustomFields.keySet();
		DBPreparedQuery dq = null;
		Iterator<String> it = iter.iterator();
		while (it.hasNext()) {
			CRColumnCustomFieldMapData customField = reportPosCustomFields.get(it.next());

			dq = new DBPreparedQuery("dReportTableManager_UpdateCustomReportColumn", tran);
			int j = 0;
			
			String displayName = "";
			String dataType = "";
			String active = CommonConstants.NO;
			if(Utils.isBlankOrNull(customField.getCustomFieldId()) || customField.getCustomFieldId().equals("-1")) {
				displayName = customField.getColumnDisplayName() + "()";
			} else {
				displayName = customField.getColumnDisplayName() + "(" + customField.getCustomFieldDisplayName() + ")";
				active = CommonConstants.YES;
				dataType = customField.getCustomFieldType();
				if(dataType.equals("date"))
					dataType = Date.class.getName();
				else if(dataType.equals("number"))
					dataType = Integer.class.getName();
				else 
					dataType = String.class.getName();
			}
			dq.setString(++j, displayName);
			dq.setString(++j, dataType);
			dq.setString(++j, active);
			
			dq.setString(++j, CommonConstants.YES);
			dq.setString(++j, customField.getColumnProperty());

			dq.execute();
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		PositionMasterTableManager manager = new PositionMasterTableManager();
		DBTransaction tran = null;
		try {
			tran = new DBTransaction();
			manager.updatePositionMaster(tran);
			tran.commit();
		} catch (Exception e) {
			try {
				tran.rollback();
			} catch (SQLException e1) {
			}
		}
	}

}
