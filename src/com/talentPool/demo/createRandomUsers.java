/**
 * 
 */
package com.talentPool.demo;

import com.talentPool.admin.manager.AdminManager;
import com.talentPool.user.UserConstants;
import com.talentPool.user.dataobject.LoginData;

/**
 * @author Ajeet
 *
 */
public class createRandomUsers {

	/**
	 * @param args
	 */
	public static void main(String[] args) {	
		createUsers(args);
	}

	public static String createUsers(String[] args) {
		AdminManager adminManager = new AdminManager();
		LoginData loginData = new LoginData();
		String userId = null;
		if(args==null || args.length<2){
			args[0] = "demorecruiter"; 
			args[1] = ""+UserConstants.ROLE_RECRUITER;
		}
		
		try {
			if(args[1].equals(""+UserConstants.ROLE_RECRUITER)){
				loginData  = createRecruiter(loginData, args[0]);
			}else{
				loginData  = createManager(loginData, args[0]);
			}
			userId = adminManager.createUser(loginData, args[1], false);
			System.out.println("userId="+userId);
		} catch (Exception e) {
			e.printStackTrace();
		}	
		return userId;
	}

	private static LoginData createManager(LoginData loginData, String manager) {
		loginData.setUserName(manager);
		loginData.setPassword("123123");
		loginData.setFirstName("Manager");
		loginData.setLastName("Demo");
		loginData.setEmail(manager+"@demo.com");
		loginData.setCellPhone("3523452345");
		loginData.setIsUserLdapSetting("0");
		loginData.setEmployeeCode("2");
		loginData.setUserSourceId("1");
		return loginData;
	}

	private static LoginData createRecruiter(LoginData loginData, String recruiter) {
		loginData.setUserName(recruiter);
		loginData.setPassword("123123");
		loginData.setFirstName("Recruiter");
		loginData.setLastName("Demo");
		loginData.setEmail(recruiter+"@demo.com");
		loginData.setCellPhone("34523452345");
		loginData.setIsUserLdapSetting("0");
		loginData.setEmployeeCode("2");
		loginData.setUserSourceId("1");
		return loginData;
	}

}
