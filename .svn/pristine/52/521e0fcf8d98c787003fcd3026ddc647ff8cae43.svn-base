package com.talentPool.porting.manager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.talentPool.admin.manager.AdminManager;
import com.talentPool.applicant.manager.ApplicantManager;
import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.db.SimpleDataObject;
import com.talentPool.common.properties.TPLabels;
import com.talentPool.common.utils.Utils;
import com.talentPool.masters.dataobject.DepartmentData;
import com.talentPool.masters.dataobject.LocationData;
import com.talentPool.masters.exception.MasterExistException;
import com.talentPool.masters.manager.LocationManager;
import com.talentPool.masters.manager.MastersManager;
import com.talentPool.porting.dataobject.FailedStatusObject;
import com.talentPool.user.UserConstants;
import com.talentPool.user.dataobject.LoginData;
import com.talentPool.user.dataobject.RoleData;
import com.talentPool.user.exception.EmailExistException;
import com.talentPool.user.exception.EmployeeCodeExistException;
import com.talentPool.user.exception.SourceExistException;
import com.talentPool.user.exception.SourceOrEmployeeCodeExistException;
import com.talentPool.user.exception.UsernameExistException;
import com.talentPool.user.manager.LoginManager;

public class UserImportManager extends ImportManager{
	public List<FailedStatusObject> saveData(List<SimpleDataObject> entities){
		MastersManager manager = new MastersManager();
		List<FailedStatusObject> failedObjects = new ArrayList<FailedStatusObject>();
		int counter = 2;
		
		List<String> exceptions = null;
		FailedStatusObject failedObject = null;
		for( SimpleDataObject simpleDataObject : entities) {		
			exceptions = createUser(simpleDataObject);
			if(exceptions.size()>0){
				String errorString = "";
				for (int i = 0; i < exceptions.size(); i++) {
					if(i!=0){
						errorString += "<br/>";
					}
					errorString += exceptions.get(i);
				}
				failedObjects.add(new FailedStatusObject(simpleDataObject.getString("fld2")+" "+simpleDataObject.getString("fld3"),counter, errorString));
			}		
			counter++;
		}
		return failedObjects;		
	}	
	
	
	public List<FailedStatusObject> updateData(List<SimpleDataObject> entities){
		List<FailedStatusObject> failedObjects = new ArrayList<FailedStatusObject>();
		int counter = 2;
		
		List<String> exceptions = null;
		for( SimpleDataObject simpleDataObject : entities) {		
			exceptions = updateUser(simpleDataObject);
			if(exceptions.size()>0){
				String errorString = "";
				for (int i = 0; i < exceptions.size(); i++) {
					if(i!=0){
						errorString += "<br/>";
					}
					errorString += exceptions.get(i);
				}
				failedObjects.add(new FailedStatusObject(simpleDataObject.getString("fld2")+" "+simpleDataObject.getString("fld3"),counter, errorString));
			}		
			counter++;
		}
		return failedObjects;		
	}
	
	private List<String> createUser(SimpleDataObject user){
		List<String> errors = new ArrayList<String>();
		try {			
			AdminManager adminManager = new AdminManager();
			if (user.getString("fld0").length() < UserConstants.REQUIRED_MIN_USERNAME_LENGTH || user.getString("fld0").length() > UserConstants.REQUIRED_MAX_USERNAME_LENGTH) {
				errors.add(TPLabels.getLabel("add_user.errors.short_username"));
			}
			if (user.getString("fld0").indexOf(" ") != -1) {
				errors.add(TPLabels.getLabel("add_user.errors.username_nospace"));
			}

			if (user.getString("fld1").length() < UserConstants.REQUIRED_MIN_PASSWORD_LENGTH || user.getString("fld1").length() > UserConstants.REQUIRED_MAX_PASSWORD_LENGTH) {
				errors.add(TPLabels.getLabel("add_user.errors.short_password"));
			}
			
			if (user.getString("fld2").length() < 1) {
				errors.add(TPLabels.getLabel("add_user.errors.fname_required"));
			} else if (!Utils.isValidPattern(user.getString("fld2"), Utils.regName)) {
				errors.add(TPLabels.getLabel("add_user.errors.invalid_fname"));
			}
			if (user.getString("fld3").length() < 1) {
				errors.add(TPLabels.getLabel("add_user.errors.lname_required"));
			} else if (!Utils.isValidPattern(user.getString("fld3"), Utils.regName)) {
				errors.add(TPLabels.getLabel("add_user.errors.invalid_lname"));
			}

			if (!Utils.isValidPattern(user.getString("fld4"), Utils.regEmail)) {
				errors.add(TPLabels.getLabel("add_user.errors.invalid_email"));
			}
			
			RoleData role = null;
			String userSourceId = null;
			boolean createSource = false;
			
			if (Utils.isBlankOrNull(user.getString("fld17"))) {
				errors.add(TPLabels.getLabel("add_user.errors.role_required"));
			}else{
				role = adminManager.getRoleByTitle(user.getString("fld17"));
				if(role == null){
					errors.add(TPLabels.getLabel("add_user.errors.invalid_role_name"));
				}else if(UserConstants.ROLE_VENDOR!= role.getRoleId()){
					if(Utils.isBlankOrNull(user.getString("fld5"))){				
						errors.add(TPLabels.getLabel("add_user.errors.employee_code_required"));
					}else{
						userSourceId = adminManager.getSourceIdForEmployeeCode(user.getString("fld5"));
						if (Utils.isBlankOrNull(userSourceId)) {							
							createSource = true;
						}
						int count = adminManager.getEmployeeSourceUser("", userSourceId);
						if (count > 0) {
							errors.add(TPLabels.getLabel("add_user.errors.employee_user_exist"));
						}
					}
				}else if (UserConstants.ROLE_VENDOR == role.getRoleId()) {
					if(Utils.isBlankOrNull(user.getString("fld18"))){
						errors.add(TPLabels.getLabel("add_user.errors.vendor_source_required"));
					}else{			
						ApplicantManager applicantManager = new ApplicantManager();
						userSourceId = applicantManager.getSourceId(user.getString("fld18"));
						if(adminManager.isSourceBlackListed(userSourceId)){				
							errors.add(TPLabels.getLabel("add_user.errors.vendor_source_blacklisted"));
						}
					}
				}
			}

			LoginData assignUnder = null;
			if (Utils.isBlankOrNull(user.getString("fld16"))) {
				errors.add(TPLabels.getLabel("add_user.errors.enter_assign_under"));
			}else{
				assignUnder = adminManager.getUserByName(user.getString("fld16"));
				if(assignUnder == null){
					assignUnder = new LoginData();
					assignUnder.setUserId("0");
				}
			}
			// if
			// (GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_LDAP_ENABLED).equals(GlobalConstants.ENABLED)
			// && UserConstants.ROLE_VENDOR !=
			// Integer.parseInt(adminForm.getSelectedRoleIds())) {
//			if (GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_LDAP_ENABLED).equals(GlobalConstants.ENABLED) && UserConstants.ROLE_VENDOR != Integer.parseInt(adminForm.getSelectedRoleIds()) && UserConstants.IS_USER_LDAP_SETTING_ENABLED.equals(adminForm.getIsUserLdapSetting())) {
//				LDAPQueryProcessor ldapQuery = new LDAPQueryProcessor();
//				LDAPManager manager = new LDAPManager();
//				ArrayList<LDAPServerData> serverList = manager.getLDAPServerList();
//				String loggedInUser = (String) request.getSession().getAttribute("username");
//				String loggedInUserPass = (String) request.getSession().getAttribute("userpassword");
//				if (!Utils.isBlankOrNull(adminForm.getUserName())) {
//					ArrayList<LDAPUserData> users = ldapQuery.getUsersList(serverList, LDAPConstants.SEARCH_BY_USER_NAME, adminForm.getUserName().trim(), loggedInUser, loggedInUserPass);
//					if (users.size() == 0) {
//						errors.add("add_user.errors.not_valid_ldap_username", new ActionError("add_user.errors.not_valid_ldap_username"));
//					}
//				}
//
//			}
			
			
			String deptId = null;
			String subDeptId = null;
			String subSubDeptId = null;
			String sub3DeptId = null;
			String sub4DeptId = null;
			
			if(!Utils.isBlankOrNull(user.getString("fld9"))){
				MastersManager mastersManager = new MastersManager();
				DepartmentData dept = mastersManager.getDeptDataByTitle(user.getString("fld9"));
				if(dept!= null){
					deptId = dept.getItemId()+"";
					if(!Utils.isBlankOrNull(user.getString("fld10"))){
						DepartmentData subDept = mastersManager.getDeptDataByTitle(user.getString("fld10"));
						if(subDept!= null){
							subDeptId = subDept.getItemId()+"";
							if(!Utils.isBlankOrNull(user.getString("fld11"))){
								DepartmentData subSubDept = mastersManager.getDeptDataByTitle(user.getString("fld11"));
								if(subDept!= null){
									subSubDeptId = subSubDept.getItemId()+"";
									if(!Utils.isBlankOrNull(user.getString("fld12"))){
										DepartmentData sub3Dept = mastersManager.getDeptDataByTitle(user.getString("fld12"));
										if(sub3Dept!= null){
											sub3DeptId = sub3Dept.getItemId()+"";
											if(!Utils.isBlankOrNull(user.getString("fld13"))){
												DepartmentData sub4Dept = mastersManager.getDeptDataByTitle(user.getString("fld13"));
												if(sub4Dept!= null){
													sub4DeptId = sub4Dept.getItemId()+"";
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
			
			String locationId = null;
			if(!Utils.isBlankOrNull(user.getString("fld8"))){
				LocationManager locationManager = new LocationManager();
				LocationData location = locationManager.getLocationByName(user.getString("fld8"));
				if(location != null){
					locationId = location.getLocationId();
				}
			}
		
			String gradeId = null;
			if(!Utils.isBlankOrNull(user.getString("fld14"))){
				MastersManager mastersManager = new MastersManager();
				gradeId = mastersManager.getGradeId(user.getString("fld14"));				
			}
			
			String bandId = null;
			if(!Utils.isBlankOrNull(user.getString("fld15"))){
				MastersManager mastersManager = new MastersManager();
				bandId = mastersManager.getBandId(user.getString("fld15"));				
			}
			
			if (errors.size() == 0) {
				LoginData loginData = new LoginData();
				loginData.setUserName(user.getString("fld0"));
				loginData.setFirstName(user.getString("fld2"));
				loginData.setLastName(user.getString("fld3"));
				loginData.setEmail(user.getString("fld4"));
				loginData.setParentId(assignUnder.getUserId());
				loginData.setDepartmentId(deptId);
				loginData.setSubDepartmentId(subDeptId);
				loginData.setSubSubDepartmentId(subSubDeptId);
				loginData.setSub3DepartmentId(sub3DeptId);
				loginData.setSub4DepartmentId(sub4DeptId);
				loginData.setLocationId(locationId);
				loginData.setGradeId(gradeId);
				loginData.setBandId(bandId);
				if (!Utils.isBlankOrNull(user.getString("fld6"))) {
					loginData.setHomePhone(user.getString("fld6").trim());
				}
				if (!Utils.isBlankOrNull(user.getString("fld7"))) {
					loginData.setCellPhone(user.getString("fld7").trim());
				}
//				if (!Utils.isBlankOrNull(user.getString("fld0"))) {
// TODO By default false is set
					loginData.setIsUserLdapSetting("");
//				}
				if (!Utils.isBlankOrNull(user.getString("fld5"))) {
					loginData.setEmployeeCode(user.getString("fld5"));
				}
				loginData.setUserSourceId(userSourceId);

				loginData.setPassword(user.getString("fld1"));
//					// update permissions for New User
					
				adminManager.createUser(loginData, role.getRoleId()+"", createSource);
			} 
		}catch (SQLException sqle) {
			errors.add(TPLabels.getLabel("add_user.errors.duplicate_username"));
		} catch (MasterExistException e) {
			errors.add(TPLabels.getLabel("add_user.errors.employee_source_exist"));
		}catch (SourceExistException e) {
			errors.add(TPLabels.getLabel("add_user.errors.employee_source_exist"));
		}catch (SourceOrEmployeeCodeExistException e) {
			errors.add(TPLabels.getLabel("add_user.errors.employee_source_exist"));
		} catch (EmailExistException e) {
			errors.add(TPLabels.getLabel("add_user.errors.duplicate_email_exists"));
		} catch (EmployeeCodeExistException e) {
			errors.add(TPLabels.getLabel("add_user.errors.duplicate_employee_code_exists"));
		} catch (UsernameExistException e) {
			errors.add(TPLabels.getLabel("add_user.errors.duplicate_username"));
		} catch (Exception e) {
			errors.add("User can not be added.");
		} 
		return errors;
	}
	
	private List<String> updateUser(SimpleDataObject user){
		List<String> errors = new ArrayList<String>();
		
		try {
			String userId = user.getString("userId");
			LoginManager loginManager = new LoginManager();
			LoginData loginData = loginManager.getUser(userId);
			if (!Utils.isBlankOrNull(user.getString("fld2"))){
				String s = user.getString("fld2").replaceAll("'", "");
				user.setAttribute("fld2", s);
			}
			if (!Utils.isBlankOrNull(user.getString("fld3"))){
				String s = user.getString("fld3").replaceAll("'", "");
				user.setAttribute("fld3", s);
			}
			if (!Utils.isBlankOrNull(user.getString("fld2"))){
				loginData.setFirstName(user.getString("fld2"));
			}
			if (!Utils.isBlankOrNull(user.getString("fld3"))){
				loginData.setLastName(user.getString("fld3"));
			}
			if (!Utils.isBlankOrNull(user.getString("fld4")) && !loginData.getEmail().equals(user.getString("fld4").trim())){
				loginData.setEmail(user.getString("fld4"));
			}
			
			String deptId = null;
			String subDeptId = null;
			String subSubDeptId = null;
			String sub3DeptId = null;
			String sub4DeptId = null;
			
			if(!Utils.isBlankOrNull(user.getString("fld9"))){
				MastersManager mastersManager = new MastersManager();
				DepartmentData dept = mastersManager.getDeptDataByTitle(user.getString("fld9"));
				if(dept!= null){
					deptId = dept.getItemId()+"";
					if(!Utils.isBlankOrNull(user.getString("fld10"))){
						DepartmentData subDept = mastersManager.getDeptDataByTitle(user.getString("fld10"));
						if(subDept!= null){
							subDeptId = subDept.getItemId()+"";
							if(!Utils.isBlankOrNull(user.getString("fld11"))){
								DepartmentData subSubDept = mastersManager.getDeptDataByTitle(user.getString("fld11"));
								if(subSubDept!= null){
									subSubDeptId = subSubDept.getItemId()+"";
									if(!Utils.isBlankOrNull(user.getString("fld12"))){
										DepartmentData sub3Dept = mastersManager.getDeptDataByTitle(user.getString("fld12"));
										if(sub3Dept!= null){
											sub3DeptId = sub3Dept.getItemId()+"";
											if(!Utils.isBlankOrNull(user.getString("fld13"))){
												DepartmentData sub4Dept = mastersManager.getDeptDataByTitle(user.getString("fld13"));
												if(sub4Dept!= null){
													sub4DeptId = sub4Dept.getItemId()+"";
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
			
			if (!Utils.isBlankOrNull(deptId)){
				loginData.setDepartmentId(deptId);
			}
			if (!Utils.isBlankOrNull(subDeptId)){
				loginData.setSubDepartmentId(subDeptId);
			}
			if (!Utils.isBlankOrNull(subSubDeptId)){
				loginData.setSubSubDepartmentId(subSubDeptId);
			}
			if (!Utils.isBlankOrNull(sub3DeptId)){
				loginData.setSub3DepartmentId(sub3DeptId);
			}
			if (!Utils.isBlankOrNull(sub4DeptId)){
				loginData.setSub4DepartmentId(sub4DeptId);
			}
			String locationId = null;
			if(!Utils.isBlankOrNull(user.getString("fld8"))){
				LocationManager locationManager = new LocationManager();
				LocationData location = locationManager.getLocationByName(user.getString("fld8"));
				if(location != null){
					locationId = location.getLocationId();
					loginData.setLocationId(locationId);
				}
			}
			
			if (errors.size() == 0) {
				AdminManager adminManager = new AdminManager();
				adminManager.updateUser(userId, loginData, loginData.getRoleId(), false);
			}
			
		} catch (SQLException sqle) {
			user.setAttribute("fld3", user.getString("fld3")+user.getString("fld5"));
			errors.addAll(updateUser(user));
		} catch (MasterExistException e) {
			errors.add(TPLabels.getLabel("add_user.errors.employee_source_exist"));
		}catch (SourceExistException e) {
			user.setAttribute("fld3", user.getString("fld3")+user.getString("fld5"));
			errors.addAll(updateUser(user));
		}catch (SourceOrEmployeeCodeExistException e) {
			user.setAttribute("fld3", user.getString("fld3")+user.getString("fld5"));
			errors.addAll(updateUser(user));
		} catch (EmailExistException e) {
			errors.add(TPLabels.getLabel("add_user.errors.duplicate_email_exists"));
		} catch (EmployeeCodeExistException e) {
			errors.add(TPLabels.getLabel("add_user.errors.duplicate_employee_code_exists"));
		} catch (UsernameExistException e) {
			errors.add(TPLabels.getLabel("add_user.errors.duplicate_username"));
		} catch (Exception e) {
			TPLogger.getLogger().error("User can not be updated.", e);
			errors.add("User can not be updated.");
		} 
		return errors;
	}
}
