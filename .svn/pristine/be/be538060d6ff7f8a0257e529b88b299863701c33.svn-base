package com.talentPool.audit.manager;

import java.sql.SQLException;

import com.talentPool.audit.utils.AuditUtils;
import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.db.DBPreparedQuery;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.utils.Utils;

/**
 * @author Ajeet
 *
 */
public class AuditManager {

	public String addAudit(String entityField, String auditType, String entityId, String entityType,
			String userId,String positionId,String positionStepIdFrom,String positionStepIdTo, 
			boolean entryToReport, String sourceIp) throws SQLException {
		DBPreparedQuery dq = null;
		String auditDesc = AuditUtils.generateAuditDesc(entityField, auditType, entityId, entityType, userId,positionId,positionStepIdFrom,positionStepIdTo, sourceIp);
		String auditTypeModified=AuditUtils.getAuditTypeForRejected(entityField, auditType, entityId, entityType, userId,positionId,positionStepIdFrom,positionStepIdTo, sourceIp);
		if(entryToReport){
			try {
				dq = new DBPreparedQuery("dAuditManager_AddAudit");
				dq.setString(1, auditDesc);
				//dq.setString(2, auditType);
				//modifie for Lumese to add new auditType rejected for rejected candidate
				dq.setString(2, auditTypeModified);
				dq.setString(3, entityId);
				dq.setString(4, entityType);
				dq.setString(5, entityField);
				dq.setString(6, userId);
				dq.setString(7, Utils.getBlankIfNull(sourceIp));
				dq.execute();
			} catch (SQLException e) {
				TPLogger.getLogger().error(GlobalConstants.ERROR, e);
				throw e;
			} finally {
				if(dq != null) {
					dq.releaseConnection();
				}
			}
		}
		return auditDesc;
	}

	public String getUserName(String id) throws SQLException{
		DBPreparedQuery dq = null;
		String userName = "";
		try {
			dq = new DBPreparedQuery("dAuditManager_GetUserName");
			dq.setString(1, id);
			userName = dq.getIdResult();
		} catch (SQLException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			throw e;
		} finally {
			if(dq != null) {
				dq.releaseConnection();
			}
		}
		return userName;
	}
	
	public String getPositionName(String id) throws SQLException{
		DBPreparedQuery dq = null;
		String positionName = "";
		try {
			dq = new DBPreparedQuery("dAuditManager_GetPositionName");
			dq.setString(1, id);
			positionName = dq.getIdResult();
		} catch (SQLException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			throw e;
		} finally {
			if(dq != null) {
				dq.releaseConnection();
			}
		}
		return positionName;
	}
	
	public String getCandidateName(String id) throws SQLException{
		DBPreparedQuery dq = null;
		String candidateName = "";
		try {
			dq = new DBPreparedQuery("dAuditManager_GetCandidateName");
			dq.setString(1, id);
			candidateName = dq.getIdResult();
		} catch (SQLException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			throw e;
		} finally {
			if(dq != null) {
				dq.releaseConnection();
			}
		}
		return candidateName;
	}
	
	public String getRoleName(String id) throws SQLException{
		DBPreparedQuery dq = null;
		String candidateName = "";
		try {
			dq = new DBPreparedQuery("dAuditManager_GetRoleName");
			dq.setString(1, id);
			candidateName = dq.getIdResult();
		} catch (SQLException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			throw e;
		} finally {
			if(dq != null) {
				dq.releaseConnection();
			}
		}
		return candidateName;
	}
	
	public String getReportLevelName(String id) throws SQLException{
		DBPreparedQuery dq = null;
		String candidateName = "";
		try {
			dq = new DBPreparedQuery("dAuditManager_GetReportLevelName");
			dq.setString(1, id);
			candidateName = dq.getIdResult();
		} catch (SQLException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			throw e;
		} finally {
			if(dq != null) {
				dq.releaseConnection();
			}
		}
		return candidateName;
	}
	
}
