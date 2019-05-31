package com.talentPool.employeeservice.manager;

import java.sql.SQLException;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.employeeservice.dataobject.EloginData;
import com.talentPool.user.dataobject.LoginData;
import com.talentPool.user.manager.UserManager;

public class EmployeeUserManager {

	/**
	 * For the given usedId creates and returns a {@link EloginData}  
	 * @param userId
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 */
	public EloginData getUserData(String userId)  {
		EloginData eloginData 		= null;
		EmployeeAccountManager eacm = null;
		UserManager userManager 	= null;
		LoginData loginData 		= null;
		try {
			eacm 			= new EmployeeAccountManager();
			userManager 	= new UserManager();
			loginData 		= userManager.getUserDataToPopulateApplicantDetails(userId);	
			eloginData 		= eacm.convertLoginToELogin(loginData);
		}catch(Exception e){
			TPLogger.getLogger().error("Error while geting userData for userId: "+userId,e);
		}
		return eloginData;
	}
}
