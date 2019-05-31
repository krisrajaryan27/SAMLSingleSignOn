package com.talentPool.custom.manager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.db.DBPreparedQuery;
import com.talentPool.common.db.DBQuery;
import com.talentPool.common.db.DBTransaction;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.utils.Utils;
import com.talentPool.custom.dataobject.CRColumnCustomFieldMapData;
import com.talentPool.customReports.constants.CustomReportColumnConstants;

public class CRColumnCustomFieldMapManager {
	
	/* mapCustomFields holds the map of entitytype and all custom fields */
	private HashMap<String, ArrayList<CRColumnCustomFieldMapData>> mapCustomFields = null;

	public void reloadCustomFieldsMaps() {
		DBQuery dq = null;
		try {
			// initialize all maps
			mapCustomFields = new HashMap<String, ArrayList<CRColumnCustomFieldMapData>>();

			dq = new DBQuery("dCustomFieldMapManager_GetAllFields");
			ArrayList<CRColumnCustomFieldMapData> result = dq.getResult();
			for (int i = 0; result != null && i < result.size(); i++) {
				CRColumnCustomFieldMapData data = result.get(i);
				ArrayList<CRColumnCustomFieldMapData> eTypeList = mapCustomFields.get("" + data.getEntityType());
				if (eTypeList == null) {
					eTypeList = new ArrayList<CRColumnCustomFieldMapData>();
				}
				eTypeList.add(data);
				mapCustomFields.put("" + data.getEntityType(), eTypeList);				
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
	}
	
	public ArrayList<CRColumnCustomFieldMapData> getCustomFieldsFor(int entityType) {
		reloadCustomFieldsMaps();
		return mapCustomFields.get("" + entityType);
	}

	public TreeMap<String, CRColumnCustomFieldMapData> getReportPosCustomFieldPropertyMap(DBTransaction tran) {
		DBPreparedQuery dq = null;
		TreeMap<String, CRColumnCustomFieldMapData> customFieldPropertyMap = new TreeMap<String, CRColumnCustomFieldMapData>();

		try {
			dq = new DBPreparedQuery("dCustomFieldMapManager_GetCustomFieldsByProperty", tran);
			ArrayList<CRColumnCustomFieldMapData> result = dq.getResult();
			for (int i = 0; result != null && i < result.size(); i++) {
				CRColumnCustomFieldMapData data = result.get(i);
				customFieldPropertyMap.put(data.getColumnProperty(), data);				
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			if (dq != null) {
				dq.closeOpenCursors();
			}
		}
		return customFieldPropertyMap;
	}

	public void saveCustomFieldsMapping(String selectedCustomFieldMapping) throws SQLException {		
		if(Utils.isBlankOrNull(selectedCustomFieldMapping))
			return;
		
		DBPreparedQuery dq = null;
		DBTransaction tran = null;
		try {
			tran = new DBTransaction();
			String[] customFieldMaps = selectedCustomFieldMapping.split("#");
			String[] customFieldMap = new String[2];
			for(int i = 0; i < customFieldMaps.length; i++) {
				customFieldMap = customFieldMaps[i].split("=");
				dq = new DBPreparedQuery("dCustomFieldMapManager_UpdateCustomFieldMapping", tran);
				dq.setString(1, customFieldMap[1]);
				dq.setString(2, customFieldMap[0]);
				dq.execute();
				deletePositionCustomFieldFromReportTypeColumn(customFieldMap[0],tran);
				if(!customFieldMap[1].equals("-1")){
					if(customFieldMap[0].startsWith(CustomReportColumnConstants.CANDIDATE_CUSTOM_FIELD_PREFIX)) {
						insertPositionCustomFieldFromReportTypeColumn(customFieldMap[0],"4",tran);
					} else {
						insertPositionCustomFieldFromReportTypeColumn(customFieldMap[0],"1",tran);
						insertPositionCustomFieldFromReportTypeColumn(customFieldMap[0],"2",tran);
						insertPositionCustomFieldFromReportTypeColumn(customFieldMap[0],"4",tran);
					}
					//CustomFieldManager cfm = new CustomFieldManager();
					//CustomFieldData cfd = cfm.getCustomField(customFieldMap[1]);
					//updatePositionCustomFieldOnReportColumn(customFieldMap[0],cfd.getFieldDisplayName(),"1",tran);
				}/*else{
					updatePositionCustomFieldOnReportColumn(customFieldMap[0],"","0",tran);
				}*/
				tran.commit();
			}			
		} catch (SQLException e) {
			TPLogger.getLogger().error("Error while saving custom field mapping", e);
			throw e;
		} finally {
			if (dq != null) {
				dq.releaseTransaction(tran);
			}		
		}
	}
		
	private void updatePositionCustomFieldOnReportColumn(String columnProperty, String colDisplayName, String isActive, DBTransaction tran){
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dCustomFieldMapManager_UpdatePositionCustomFieldOnReportColumn", tran);
			dq.setString(1, colDisplayName);
			dq.setString(2, "java.lang.String");
			dq.setString(3, isActive);
			dq.setString(4, columnProperty);
			dq.execute();
		}  catch (Exception e) {
			TPLogger.getLogger().error(e);
		} finally {
			if (dq != null) {
				dq.closeOpenCursors();
			}
		}		
	}
	
	private void deletePositionCustomFieldFromReportTypeColumn(String columnProperty, DBTransaction tran){
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dCustomFieldMapManager_DeletePositionCustomFieldFromReportTypeColumn", tran);
			//dq.setString(1, reportTypeConstant);
			dq.setString(1, columnProperty);
			dq.execute();
		}  catch (Exception e) {
			TPLogger.getLogger().error(e);
		} finally {
			if (dq != null) {
				dq.closeOpenCursors();
			}
		}		
	}
	
	private void insertPositionCustomFieldFromReportTypeColumn(String columnProperty,String reportTypeConstant,DBTransaction tran) {
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dCustomFieldMapManager_InsertPositionCustomFieldToReportTypeColumn", tran);
			dq.setString(1, reportTypeConstant);
			dq.setString(2, columnProperty);
			dq.execute();
		} catch (Exception e) {
			TPLogger.getLogger().error(e);
		} finally {
			if (dq != null) {
				dq.closeOpenCursors();
			}
		}		
	}
	
}
