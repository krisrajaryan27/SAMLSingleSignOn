/**
 * 
 */
package com.talentPool.user.manager;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.db.DBPreparedQuery;
import com.talentPool.positions.PositionConstants;
import com.talentPool.user.UserConstants;

/**
 * @author shivprasad
 * 
 */
public class PermissionsManager {

	public boolean isPermittedForPosition(String positionId, String userId) {
		DBPreparedQuery dq = null;
		boolean isPermitted = false;
		try {
			dq = new DBPreparedQuery("dPermission_GetPositionUserCount");
			dq.setId(1, positionId);
			dq.setString(2, PositionConstants.STEP_ACTIVE);
			dq.setId(3, userId);
			dq.setId(4, userId);
			dq.setId(5, userId);
			dq.setId(6, userId);
			
			int rows = dq.getIntResult();
			if (rows > 0) {
				isPermitted = true;
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error in checking position user count", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return isPermitted;
	}

	public boolean isAdmin(String role) {
		return checkRole(role, UserConstants.ROLE_ADMIN);
	}

	public boolean isHRM(String role) {
		return checkRole(role, UserConstants.ROLE_HR_MANAGER);
	}

	public boolean isCXO(String role) {
		return checkRole(role, UserConstants.ROLE_CXO);
	}

	public boolean isRecruiter(String role) {
		return checkRole(role, UserConstants.ROLE_RECRUITER);
	}

	public boolean isInterviewer(String role) {
		return checkRole(role, UserConstants.ROLE_INTERVIEWER);
	}

	public boolean isRequisitioner(String role) {
		return checkRole(role, UserConstants.ROLE_REQUISITIONER);
	}

	private boolean checkRole(String role, int expectedRole) {
		try {
			return role.equals("" + expectedRole);
		} catch (Exception ne) {
			TPLogger.getLogger().error("Error in checking role", ne);
		}
		return false;
	}
}
