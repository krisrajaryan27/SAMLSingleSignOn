package com.talentPool.user.manager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.BitSet;

import org.springframework.stereotype.Component;

import com.talentPool.admin.dataobject.PermissionData;
import com.talentPool.admin.manager.AdminManager;
import com.talentPool.common.CommonConstants;
import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.db.DBPreparedQuery;
import com.talentPool.common.db.SimpleDataObject;
import com.talentPool.reports.ReportVersionConstants;
import com.talentPool.user.dataobject.LoginData;
import com.talentPool.user.exception.InvalidLoginException;

@Component
public class LoginManager {

	public LoginData getLoginDataFor(String userName) throws InvalidLoginException {
		DBPreparedQuery dq = null;
		LoginData loginData = null;

		try {
			dq = new DBPreparedQuery("dFetchLoginInfo");
			dq.setString(1, userName);
			loginData = (LoginData) (dq.getSingleObjectResult());
		} catch (Exception e) {
			TPLogger.getLogger().error("Error in getLoginDataFor", e);
			throw new InvalidLoginException("INVALID_LOGIN_NAME");
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		if (loginData == null) {
			throw new InvalidLoginException("INVALID_LOGIN_NAME");
		}
		return loginData;
	}

	public LoginData login(LoginData loginData) throws InvalidLoginException, SQLException {
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dFetchLoginInfo");
			dq.setString(1, loginData.getUserName());
			loginData = (LoginData) (dq.getSingleObjectResult());
		} catch (SQLException e) {
			TPLogger.getLogger().error("Error While Getting Login Info", e);
			throw e;
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		if (loginData == null) {
			throw new InvalidLoginException("INVALID_LOGIN_NAME");
		}
		return loginData;
	}
	
	public LoginData candidateLogin(LoginData loginData) throws InvalidLoginException, SQLException {
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dFetchCandidateLoginInfo");
			dq.setString(1, loginData.getEmail());
			loginData = (LoginData) (dq.getSingleObjectResult());
		} catch (SQLException e) {
			TPLogger.getLogger().error("Error While Getting Login Info", e);
			throw e;
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		if (loginData == null) {
			throw new InvalidLoginException("INVALID_LOGIN_NAME");
		}
		return loginData;
	}

	public void updateLastLogin(String userId) {
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dUpdateLastLogin");
			dq.setId(1, userId);
			dq.execute();
		} catch (Exception e) {
			TPLogger.getLogger().error("Error While Updating Last Login", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
	}

	public LoginData getUser(String userId) {
		DBPreparedQuery dq = null;
		LoginData loginData = null;
		try {
			dq = new DBPreparedQuery("dFetchLoginInfoForId");
			dq.setId(1, userId);
			loginData = (LoginData) (dq.getSingleObjectResult());
		} catch (Exception e) {
			TPLogger.getLogger().error("Error While getting UserInfo from db", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return loginData;
	}
	
	public LoginData getCandidateUser(String userId) {
		DBPreparedQuery dq = null;
		LoginData loginData = null;
		try {
			dq = new DBPreparedQuery("dFetchCandidateLoginInfoForId");
			dq.setId(1, userId);
			loginData = (LoginData) (dq.getSingleObjectResult());
		} catch (Exception e) {
			TPLogger.getLogger().error("Error While getting UserInfo from db", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return loginData;
	}

	public final String getLatestVersion() {
		String version = null;
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dGetLatestVersion");
			SimpleDataObject object = (SimpleDataObject) dq.getSingleObjectResult();
			version = object.getString("version");
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while getting the latest version", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return version;
	}

	public BitSet getUserReportsBitSet(String userId) {
		DBPreparedQuery dq = null;
		BitSet userReportsBitSet = new BitSet(128);
		try {
			dq = new DBPreparedQuery("dGetUserReports");
			dq.setId(1, userId);
			ArrayList<SimpleDataObject> results = dq.getResult();
			ArrayList<String> userReports = new ArrayList<String>();
			for (int i = 0; results != null && i < results.size(); i++) {
				userReports.add(results.get(i).getString("reportId"));
			}
			for (int i = 0; i < ReportVersionConstants.availableReports.size(); i++) {
				if (userReports.contains(ReportVersionConstants.availableReports.get(i))) {
					userReportsBitSet.set(Integer.parseInt(ReportVersionConstants.availableReports.get(i)));
				}
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error While Updating Last Login", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return userReportsBitSet;
	}
	
	public BitSet getUserPermissionsBitSet(String userId) {
		DBPreparedQuery dq = null;
		BitSet userPermissionBitSet=null;
		try {
			AdminManager adminManager = new AdminManager();
			ArrayList<PermissionData> userPermissions=adminManager.getUserPermissions(userId);
			dq = new DBPreparedQuery("dGetMaxUserId");
			dq.setId(1, userId);
			ArrayList<PermissionData> results = dq.getResult();
			String maxPermissionId = results.get(0).getPermissionId();
			int maxPermissionIdInt=Integer.parseInt(maxPermissionId);
			userPermissionBitSet = new BitSet(maxPermissionIdInt+1);
			for (int i = 0; userPermissions != null && i < userPermissions.size(); i++) {
				if((userPermissions.get(i).getPermissionValue()).equals("1")){	
					userPermissionBitSet.set(Integer.parseInt(userPermissions.get(i).getPermissionId()));
				}
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error While Updating Last Login", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return userPermissionBitSet;
	}
	
	public void updateUserSourceId(String userId, String sourceId){
		DBPreparedQuery dq = null;		
		try{
			dq = new DBPreparedQuery("dLoginManager_UpdateUserSourceId");
			dq.setString(1, sourceId);
			dq.setString(2, userId);
			dq.execute();			
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while Updating Employee Source Id", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}		
		
	}
	
	/**
	 * @param userId
	 * @return true or false
	 */
	public boolean isFirstTimeLogin(String userId) {
		boolean isFirstTimeLogin = true;
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dLoginManager_isFirstTimeLogin");
			dq.setString(1, userId);
			String firstTimeLogin = dq.getStringResult();
			if(firstTimeLogin.equals(CommonConstants.NO)) {
				isFirstTimeLogin = false;
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while checking isFirstTimeLogin", e);
		} finally {
			if(dq != null) {
				dq.releaseConnection();
			}
		}
		return isFirstTimeLogin;
	}

	public String getUserIdFromEmail(String toEmailId) {
		String userId = null;
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dLoginManager_getUserIdFromEmail");
			dq.setString(1, toEmailId);
			userId = dq.getStringResult();
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while fetching userid from email", e);
		} finally {
			if(dq != null) {
				dq.releaseConnection();
			}
		}
		return userId;
	}
	
	
	/*Added by Krishna for SAML user Authenticated to fetch its data*/
	public LoginData getUserFromUserName(String userName) {
		LoginData loginData = null;
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dFetchLoginInfoForUserName");
			dq.setString(1, userName);
			loginData = (LoginData) (dq.getSingleObjectResult());
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while fetching user Details from email", e);
		} finally {
			if(dq != null) {
				dq.releaseConnection();
			}
		}
		return loginData;
	}
}
