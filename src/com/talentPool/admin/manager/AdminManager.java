/**
 *                                        
 */
package com.talentPool.admin.manager;

import java.io.StringWriter;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import com.talentPool.admin.AdminConstants;
import com.talentPool.admin.dataobject.PermissionData;
import com.talentPool.admin.dataobject.ReportLevelData;
import com.talentPool.applicant.dataobject.ImportFieldData;
import com.talentPool.common.CommonConstants;
import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.dataobject.DateTimePattern;
import com.talentPool.common.db.DBPreparedQuery;
import com.talentPool.common.db.DBQuery;
import com.talentPool.common.db.DBTransaction;
import com.talentPool.common.db.SimpleDataObject;
import com.talentPool.common.db.Exception.NoResultFoundException;
import com.talentPool.common.properties.GlobalApplicationProperties;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.utils.EncryptionUtils;
import com.talentPool.common.utils.Utils;
import com.talentPool.common.xmlutils.XMLWriter;
import com.talentPool.masters.constants.MastersConstants;
import com.talentPool.masters.dataobject.SourceData;
import com.talentPool.masters.dataobject.SourceTypeData;
import com.talentPool.masters.manager.MastersManager;
import com.talentPool.positions.PositionConstants;
import com.talentPool.positions.constants.PositionConfigurationConstants;
import com.talentPool.positions.dataobject.PositionFieldData;
import com.talentPool.positions.manager.PositionScreenConfigurationManager;
import com.talentPool.sms.SMSGatewayImpl;
import com.talentPool.user.UserConstants;
import com.talentPool.user.dataobject.LoginData;
import com.talentPool.user.dataobject.RoleData;
import com.talentPool.user.exception.EmailExistException;
import com.talentPool.user.exception.EmployeeCodeExistException;
import com.talentPool.user.exception.SourceExistException;
import com.talentPool.user.exception.SourceOrEmployeeCodeExistException;
import com.talentPool.user.exception.UsernameExistException;
import com.talentPool.user.manager.LoginManager;
import com.talentPool.user.manager.UserManager;

/**
 * @author shivprasad
 * 
 */
@Component
public class AdminManager {

	public ArrayList getUsers(String filterByName, String filterByUser, String filterByRole, String filterByEmpCode) {
		DBPreparedQuery dq = null;
		ArrayList users = null;
		try {
			String[] dynParam = new String[1];
			dynParam[0] = " ";
			ArrayList<String> dynamicContent = new ArrayList<String>();

			if (!Utils.isBlankOrNull(filterByName)) {
				dynParam[0] += "AND tu.user_name like ? ";
				dynamicContent.add(filterByName + "%");
			}
			if (!Utils.isBlankOrNull(filterByUser)) {
				dynParam[0] += "AND CONCAT(tu.user_fname,' ',tu.user_lname) like ? ";
				dynamicContent.add(filterByUser + "%");
			}
			if (!Utils.isBlankOrNull(filterByEmpCode)) {
				dynParam[0] += "AND ts.employee_code like ? ";
				dynamicContent.add(filterByEmpCode + "%");
			}
			if (!Utils.isBlankOrNull(filterByRole)) {

				if (filterByRole.equals(String.valueOf(UserConstants.ROLE_ALL_EXCEPT_EMPLOYEE))) {
					dynParam[0] += "AND tur.role_id!=? ";
					dynamicContent.add(String.valueOf(UserConstants.ROLE_EMPLOYEE));
				} else {
					dynParam[0] += "AND tur.role_id=? ";
					dynamicContent.add(filterByRole);
				}
			}

			dq = new DBPreparedQuery("dFetchUsers", dynParam);
			dq.setInt(1, UserConstants.DELETED);
			int cnt = 2;
			for (int i = 0; i < dynamicContent.size(); i++) {
				dq.setString(cnt++, dynamicContent.get(i));
			}
			users = dq.getResult();
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return users;
	}

	public String getXMLForUsers(ArrayList users) {
		StringWriter sWr = new StringWriter();
		XMLWriter wr = new XMLWriter(sWr);

		try {
			wr.startDocument();
			wr.startElement("rows");
			for (int i = 0; i < users.size(); i++) {
				LoginData userData = (LoginData) users.get(i);

				AttributesImpl at = new AttributesImpl();
				at.addAttribute("", "id", "", "", "" + userData.getUserId());
				wr.startElement("", "row", "", at);

				at = new AttributesImpl();
				at.addAttribute("", "name", "", "", "roleId");
				wr.startElement("", "userdata", "", at);
				wr.characters(userData.getRoleId());
				wr.endElement("userdata");

				at = new AttributesImpl();
				at.addAttribute("", "name", "", "", "status");
				wr.startElement("", "userdata", "", at);
				wr.characters("" + userData.getStatus());
				wr.endElement("userdata");

				at = new AttributesImpl();
				at.addAttribute("", "name", "", "", "userName");
				wr.startElement("", "userdata", "", at);
				wr.characters(userData.getUserName());
				wr.endElement("userdata");

				wr.startElement("cell");
				wr.characters("<a href=\"#\" onclick=\"performDelete(" + userData.getUserId() + "," + userData.getRoleId() + "," + userData.getUserSourceId() + ");\" title=\"Delete\"><img src=\"images/ico_delete.gif\" border=0></a>");
				wr.endElement("cell");

				wr.startElement("cell");
				if ((Integer.parseInt(userData.getRoleId())) != (UserConstants.ROLE_VENDOR) && (Integer.parseInt(userData.getRoleId())) != (UserConstants.ROLE_EMPLOYEE)) {
					wr.characters("<a href=\"#\" onclick=\"AdvancePermissions(" + userData.getUserId() + ");\" title=\"Advance Permissions\"><img src=\"images/ico_user_permissions.gif\" border=0></a>");
				} else {
					wr.characters("<img src=\"images/blank.gif\" border=0>");
				}
				wr.endElement("cell");

				
				wr.startElement("cell");
				wr.characters(wr.doubleEscape(userData.getUserName()));
				wr.endElement("cell");

				wr.startElement("cell");
				wr.characters("" + userData.getFirstName() + " " + userData.getLastName());
				wr.endElement("cell");
				
				wr.startElement("cell");
				wr.characters(wr.doubleEscape(Utils.getBlankIfNull(userData.getEmployeeCode())));
				wr.endElement("cell");

				wr.startElement("cell");
				wr.characters(userData.getRoleTitle());
				wr.endElement("cell");

				wr.startElement("cell");
				wr.characters("" + userData.getStatus());				
				wr.endElement("cell");

				wr.endElement("row");
			}
			wr.endElement("rows");
			wr.endDocument();
		} catch (SAXException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return sWr.getBuffer().toString();
	}

	/**
	 * @param userId
	 * @return
	 */
	public int getNoOfPositionsForUser(String userId) {
		DBPreparedQuery dq = null;
		int noOfPositions = 0;
		try {
			dq = new DBPreparedQuery("dGetActivePositionsForUser");
			dq.setString(1, userId);
			dq.setString(2, PositionConstants.POSITION_STATUS_OPENED);
			dq.setString(3, PositionConstants.STEP_ACTIVE);
			noOfPositions = dq.getIntResult();
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return noOfPositions;
	}

	public void deleteUser(String userId, String roleId, String userSourceId) throws Exception {
		DBPreparedQuery dq = null;
		DBTransaction tran = null;
		try {
			tran = new DBTransaction();

			//delete user from report level
			dq = new DBPreparedQuery("dAdminManager_DeleteFromReportLevel", tran);
			dq.setString(1, userId);
			dq.execute();
			
			// delete user Role
			dq = new DBPreparedQuery("dDeleteUserRoles", tran);
			dq.setString(1, userId);
			dq.execute();

			// delete user permissions
			dq = new DBPreparedQuery("dAdminManager_DeleteUserPermissions", tran);
			dq.setString(1, userId);
			dq.execute();

			// delete User
			dq = new DBPreparedQuery("dDeleteUser", tran);
			dq.setId(1, userId);
			dq.execute();

			// delete Employee source
			if (!roleId.equals(String.valueOf(UserConstants.ROLE_VENDOR))) {
				if (!Utils.isBlankOrNull(userSourceId)) {
					MastersManager mastersManager = new MastersManager();
					mastersManager.deleteSource(userSourceId, tran);
				}
			}
			
			UserManager userManager = new UserManager(); 
			//delete BU
			userManager.deleteUserBU(userId, tran);
						
			//delete Cost Center
			userManager.deleteUserCostCenter(userId, tran);
			
			tran.commit();

		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			tran.rollback();
			throw e;
		} finally {
			if (dq != null) {
				dq.releaseTransaction(tran);
			}
		}
	}

	public void changeUserStatus(String userId, int status) {
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dUpdateUserStatus");
			dq.setInt(1, status);
			dq.setId(2, userId);
			dq.execute();
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
	}
	
	public void changeUserStatusBySourceId(String sourceId, int status,DBTransaction tran) throws SQLException,Exception{
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dUpdateUsersStatusBySourceId",tran);
			dq.setInt(1, status);
			dq.setId(2, sourceId);
			dq.execute();
		}catch (SQLException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			tran.rollback();
			throw e;
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			throw e;
		}finally {
			if (dq != null) {
				if (tran == null) {
					dq.releaseConnection();
				} else {
					dq.closeOpenCursors();
				}

			}
		}
	}
	
	public ArrayList<RoleData> getAllRoles() {
		DBQuery dq = null;
		ArrayList<RoleData> userRoles = null;
		try {
			dq = new DBQuery("dFetchAllRoles");
			userRoles = dq.getResult();
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return userRoles;
	}
	
	public String createUser(LoginData loginData, String roleId, boolean createSource) throws EmployeeCodeExistException,SourceExistException, UsernameExistException, EmailExistException, SQLException, Exception {
		return createUser(loginData, roleId, createSource, false);
	}

	/**
	 * Create user
	 * 
	 * @param loginData
	 * @return
	 * @throws SQLException
	 *             if username already exists in DB
	 */
	public String createUser(LoginData loginData, String roleId, boolean createSource, boolean forcePass) throws EmployeeCodeExistException,SourceExistException, UsernameExistException, EmailExistException, SQLException, Exception {
		DBPreparedQuery dq = null;
		String userId = null;
		DBTransaction tran = null;
		try {
			tran = new DBTransaction();

			// create Employee source for Employee user
			if (!roleId.equals(String.valueOf(UserConstants.ROLE_VENDOR)) && createSource) {
				try {
					String userSourceId = createUpadateEmployeeSource(loginData, "1", tran);
					loginData.setUserSourceId(userSourceId);
				} catch (EmployeeCodeExistException e) {					
					throw new EmployeeCodeExistException();
				} catch (SourceExistException e) {					
					throw new SourceExistException();
				} catch (Exception e) {					
					throw new SourceExistException();
				}
			}

			LoginManager loginManager = new LoginManager();
			LoginData duplicateLogin = null;
			try {
				duplicateLogin = loginManager.getLoginDataFor(loginData.getUserName());
			} catch (Exception e) {
			}
			if (duplicateLogin != null) {
				throw new UsernameExistException();
			}

			UserManager userManager = new UserManager();
			if (userManager.isEmailAddressExists(loginData.getEmail())) {
				throw new EmailExistException();
			}
			String encryptedPassword = EncryptionUtils.encryptString(loginData.getPassword());
			dq = new DBPreparedQuery("dCreateUser", tran);
			dq.setString(1, loginData.getUserName());
			dq.setString(2, loginData.getFirstName());
			dq.setString(3, loginData.getLastName());
			dq.setString(4, encryptedPassword);
			dq.setString(5, loginData.getEmail());
			dq.setString(6, loginData.getHomePhone());
			dq.setString(7, loginData.getCellPhone());

			if (Utils.isBlankOrNull(loginData.getUserSourceId())) {
				dq.setNull(8, Types.NULL);
			} else {
				dq.setString(8, loginData.getUserSourceId());
			}

			if (Utils.isBlankOrNull(loginData.getIsUserLdapSetting())) {
				dq.setString(9, UserConstants.IS_USER_LDAP_SETTING_ENABLED);
			} else {
				dq.setString(9, loginData.getIsUserLdapSetting());
			}

			if(Utils.isBlankOrNull(loginData.getDepartmentId())){
				dq.setNull(10, Types.NULL);
			}else{
				dq.setString(10, loginData.getDepartmentId());	
			}
			
			if(Utils.isBlankOrNull(loginData.getSubDepartmentId())){
				dq.setNull(11, Types.NULL);
			}else{
				dq.setString(11, loginData.getSubDepartmentId());	
			}
			
			if(Utils.isBlankOrNull(loginData.getSubSubDepartmentId())){
				dq.setNull(12, Types.NULL);
			}else{
				dq.setString(12, loginData.getSubSubDepartmentId());	
			}
			
			if(Utils.isBlankOrNull(loginData.getSub3DepartmentId())){
				dq.setNull(13, Types.NULL);
			}else{
				dq.setString(13, loginData.getSub3DepartmentId());	
			}
			
			if(Utils.isBlankOrNull(loginData.getSub4DepartmentId())){
				dq.setNull(14, Types.NULL);
			}else{
				dq.setString(14, loginData.getSub4DepartmentId());	
			}
			
			if(Utils.isBlankOrNull(loginData.getLocationId())){
				dq.setNull(15, Types.NULL);
			}else{
				dq.setString(15, loginData.getLocationId());
			}
			
			if(Utils.isBlankOrNull(loginData.getGradeId())){
				dq.setNull(16, Types.NULL);
			}else{
				dq.setString(16, loginData.getGradeId());
			}
			
			if(Utils.isBlankOrNull(loginData.getBandId())){
				dq.setNull(17, Types.NULL);
			}else{
				dq.setString(17, loginData.getBandId());
			}
			if (forcePass){
				dq.setString(18, CommonConstants.NO);
			}else {
				dq.setString(18, CommonConstants.YES);
			}
			
			dq.setString(19, encryptedPassword);
			
			dq.execute();
			dq = new DBPreparedQuery("dFetchLastInsertID", tran);
			userId = dq.getIdResult();
			// update permissions for New User
			updateRoleForUser(userId, roleId, tran);
			copyUserPermissionFromRoles(userId, roleId, tran);
			copyUserReportsFromRoles(userId, roleId, tran);
			
			//insert LOB
			userManager.deleteUserBU(userId, tran);
			if (!Utils.isBlankOrNull(loginData.getBuId())) {
				String[] buIds = loginData.getBuId().split(",");
				for (int i = 0; i < buIds.length; i++) {
					userManager.insertUserBU(buIds[i].trim(), userId,tran);
				}
			}
			
			//insert Cost Center
			userManager.deleteUserCostCenter(userId, tran);
			if (!Utils.isBlankOrNull(loginData.getCostCenterId())) {
				String[] costCenters = loginData.getCostCenterId().split(",");
				for (int i = 0; i < costCenters.length; i++) {
					userManager.insertUserCostCenter(costCenters[i].trim(), userId, tran);
				}
			}
			
			if(!roleId.equals(String.valueOf(UserConstants.ROLE_VENDOR))){
				HierarchyManager hierarchyManager = new HierarchyManager();
				hierarchyManager.saveUserToHierarchy(userId, loginData.getParentId(),tran);
			}
			
			tran.commit();
		} catch (SourceExistException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			tran.rollback();
			throw e;
		} catch (EmployeeCodeExistException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			tran.rollback();
			throw e;
		} catch (UsernameExistException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			tran.rollback();
			throw e;
		} catch (EmailExistException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			tran.rollback();
			throw e;
		} catch (SQLException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			tran.rollback();
			throw e;
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			if (dq != null) {
				dq.releaseTransaction(tran);
			}else if (tran!=null){
				tran.release();
			}
		}
		return userId;
	}

	private String createUpadateEmployeeSource(LoginData loginData, String createUpdate, DBTransaction tran) throws SourceOrEmployeeCodeExistException, EmployeeCodeExistException, SourceExistException, SQLException, Exception {
		String sourceId = "";
		try {
			SourceData sourceData = new SourceData();
			ArrayList<SourceTypeData> sourceTypeDetail = getEmployeeSourceTypeId();
			SourceTypeData stData = sourceTypeDetail.get(0);

			sourceData.setSourceTypeId(stData.getSourceTypeId());
			sourceData.setSourceTitle(loginData.getFirstName() + " " + loginData.getLastName());
			sourceData.setSourceEmail(loginData.getEmail());
			sourceData.setSourcePhone(loginData.getHomePhone());
			sourceData.setSourceMobile(loginData.getCellPhone());
			sourceData.setEmployeeCode(loginData.getEmployeeCode());
			sourceData.setSendEmailToSource("0");
			sourceData.setSendSMSToSource("0");
			sourceData.setLockInPeriodOnImport("0");
			sourceData.setSourceId(loginData.getUserSourceId());
			MastersManager mastersManager = new MastersManager();
			
			if (createUpdate.equals("1")) {
				if(mastersManager.employeeCodeExists(sourceData.getEmployeeCode())){
					throw new EmployeeCodeExistException();
				}
				sourceId = mastersManager.addSourcetoSourceType(sourceData, tran);
			} else {
				mastersManager.updateSources(sourceData, tran);
				sourceId = loginData.getUserSourceId();
			}
		} catch (SQLException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			throw new SourceOrEmployeeCodeExistException();
		} 
		return sourceId;
	}

	private void updateRoleForUser(String userId, String roleId, DBTransaction tran) throws Exception {
		DBPreparedQuery dq = new DBPreparedQuery("dDeleteUserRoles", tran);
		dq.setId(1, userId);
		dq.execute();
		if (!Utils.isBlankOrNull(roleId)) {
			dq = new DBPreparedQuery("dAddUserRole", tran);
			dq.setId(1, userId);
			dq.setId(2, roleId);
			dq.execute();
		}
	}

	private void copyUserPermissionFromRoles(String userId, String roleId, DBTransaction tran) throws Exception {
		DBPreparedQuery dq = new DBPreparedQuery("dAdminManager_InsertNewUserPermissions", tran);
		dq.setString(1, userId);
		dq.setString(2, roleId);
		dq.execute();
	}

	private void copyUserReportsFromRoles(String userId, String roleId, DBTransaction tran) throws Exception {
		DBPreparedQuery dq = new DBPreparedQuery("dAdminManager_InsertNewUserReportLevel", tran);
		dq.setString(1, userId);
		dq.setString(2, roleId);
		dq.execute();
	}

	/**
	 * Updates user including username
	 * 
	 * @param userId
	 * @param loginData
	 * @throws SQLException
	 */
	public void updateUser(String userId, LoginData loginData, String roleId, boolean createSource) throws SourceOrEmployeeCodeExistException, EmployeeCodeExistException, SourceExistException, EmailExistException, SQLException, Exception {
		DBPreparedQuery dq = null;
		DBTransaction tran = null;
		try {
			tran = new DBTransaction();
			// update Employee source for Employee user
			String createUpdate = "0"; // 0= update , 1= create
			if (!Utils.isBlankOrNull(roleId) && !roleId.equals(String.valueOf(UserConstants.ROLE_VENDOR))) {
				if (createSource && Utils.isBlankOrNull(loginData.getUserSourceId())) {
					createUpdate = "1";
				}
				try{					
					String userSourceId = createUpadateEmployeeSource(loginData, createUpdate, tran);
					loginData.setUserSourceId(userSourceId);
				} catch (EmployeeCodeExistException e) {					
					throw new EmployeeCodeExistException();
				} catch (SourceExistException e) {					
					throw new SourceExistException();
				} catch (Exception e) {					
					throw new SourceOrEmployeeCodeExistException();
				}
			}
			
			LoginManager loginManager = new LoginManager();
			
			UserManager userManager = new UserManager();
			if (userManager.isDuplicateEmailAddressExists(loginData.getEmail(), userId)) {
				throw new EmailExistException();
			}
			
						
			LoginData userData = loginManager.getUser(userId);

			dq = new DBPreparedQuery("dUpdateUser", tran);
			dq.setString(1, loginData.getUserName());
			dq.setString(2, loginData.getFirstName());
			dq.setString(3, loginData.getLastName());
			dq.setString(4, loginData.getEmail());
			dq.setString(5, loginData.getHomePhone());
			dq.setString(6, loginData.getCellPhone());

			if (Utils.isBlankOrNull(loginData.getUserSourceId())) {
				dq.setNull(7, Types.NULL);
			} else {
				dq.setString(7, loginData.getUserSourceId());
			}
			dq.setString(8, loginData.getIsUserLdapSetting());
			
			if(Utils.isBlankOrNull(loginData.getDepartmentId())){
				dq.setNull(9, Types.NULL);
			}else{
				dq.setString(9, loginData.getDepartmentId());	
			}
			
			if(Utils.isBlankOrNull(loginData.getSubDepartmentId())){
				dq.setNull(10, Types.NULL);
			}else{
				dq.setString(10, loginData.getSubDepartmentId());	
			}
			
			if(Utils.isBlankOrNull(loginData.getSubSubDepartmentId())){
				dq.setNull(11, Types.NULL);
			}else{
				dq.setString(11, loginData.getSubSubDepartmentId());	
			}
			
			if(Utils.isBlankOrNull(loginData.getSub3DepartmentId())){
				dq.setNull(12, Types.NULL);
			}else{
				dq.setString(12, loginData.getSub3DepartmentId());	
			}
			
			if(Utils.isBlankOrNull(loginData.getSub4DepartmentId())){
				dq.setNull(13, Types.NULL);
			}else{
				dq.setString(13, loginData.getSub4DepartmentId());	
			}
			
			if(Utils.isBlankOrNull(loginData.getLocationId())){
				dq.setNull(14, Types.NULL);
			}else{
				dq.setString(14, loginData.getLocationId());
			}

			if(Utils.isBlankOrNull(loginData.getGradeId())){
				dq.setNull(15, Types.NULL);
			}else{
				dq.setString(15, loginData.getGradeId());
			}
			
			if(Utils.isBlankOrNull(loginData.getBandId())){
				dq.setNull(16, Types.NULL);
			}else{
				dq.setString(16, loginData.getBandId());
			}
			
			dq.setId(17, userId);

			dq.execute();
			
			//insert LOB
			userManager.deleteUserBU(userId, tran);
			if (!Utils.isBlankOrNull(loginData.getBuId())) {
				String[] buIds = loginData.getBuId().split(",");
				for (int i = 0; i < buIds.length; i++) {
					userManager.insertUserBU(buIds[i].trim(), userId,tran);
				}
			}
			
			//insert Cost Center
			userManager.deleteUserCostCenter(userId, tran);
			if (!Utils.isBlankOrNull(loginData.getCostCenterId())) {
				String[] costCenters = loginData.getCostCenterId().split(",");
				for (int i = 0; i < costCenters.length; i++) {
					userManager.insertUserCostCenter(costCenters[i].trim(), userId, tran);
				}
			}
			
			
			if (!Utils.isBlankOrNull(roleId) && !roleId.equals(userData.getRoleId())) {
				updateRoleForUser(userId, roleId, tran);
				deleteUserPermissions(userId, tran);
				deleteUserReports(userId, tran);
				copyUserPermissionFromRoles(userId, roleId, tran);
				copyUserReportsFromRoles(userId, roleId, tran);
			}

			dq = new DBPreparedQuery("dAdminManager_UpdatePositionVendorId", tran);
			dq.setString(1, loginData.getUserSourceId());
			dq.setString(2, userData.getUserSourceId());
			dq.execute();
			
			tran.commit();
		}  catch (SourceExistException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			tran.rollback();
			throw e;
		} catch (EmployeeCodeExistException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			tran.rollback();
			throw e;
		} catch (EmailExistException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			tran.rollback();
			throw e;
		}catch (SQLException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			tran.rollback();
			throw e;
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			if (dq != null) {
				dq.releaseTransaction(tran);
			}else{
				tran.release();
			}
		}
	}

	/**
	 * Update user account info, this will not update username
	 * 
	 * @param userId
	 * @param loginData
	 * @throws SQLException
	 */
	public void updateUserAccount(String userId, LoginData loginData) throws SQLException {
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dUpdateUserAccount");
			dq.setString(1, loginData.getFirstName());
			dq.setString(2, loginData.getLastName());
			dq.setString(3, loginData.getEmail());
			dq.setString(4, loginData.getHomePhone());
			dq.setString(5, loginData.getCellPhone());
			dq.setString(6, loginData.getTimeZone());
			dq.setId(7, userId);
			dq.execute();
		} catch (SQLException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
	}

	/**
	 * This method will allow user to insert or update Inbox & other Settings
	 * 
	 * @param inboxEmail
	 * @param inboxUserName
	 * @param inboxPassword
	 * @param inboxSmtpHost
	 * @param inboxpopHost
	 * @param inboxPollingDuration
	 * @param companyName
	 * @return
	 */
	public int InsertUpdateSettings(String inboxEmail, String inboxUserName, String inboxPassword, String inboxSmtpHost, String inboxpopHost, String inboxPollingDuration, String inboxDisplayName, String inboxServerType, String inboxSmtpAuthRequired, String inboxSmtpAuthSame,
			String inboxSmtpUserName, String inboxSmtpPassword, String inboxOutgoingPort, String inboxOutgoingSSLEnabled, String inboxOutgoingTLSEnabled, String inboxIncomingPort, String inboxIncomingSSLEnabled,
			String exchangeServerName, String domainName, String exchangeServerVersion, String exchangeSmtp) {
		DBPreparedQuery dq = null;
		DBQuery dbq = null;
		int inboxrows = 0;
		try {
			dbq = new DBQuery("dGetNoOfRows");
			inboxrows = dbq.getIntResult();
		} catch (NoResultFoundException e) {
			e.printStackTrace();
			inboxrows = 0;
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			TPLogger.getLogger().error(GlobalConstants.ERROR, sqle);
			inboxrows = 0;
		}
		try {
			if (inboxrows == 0) {
				dq = new DBPreparedQuery("dSaveInboxSettings");
				dq.setString(1, inboxEmail);
				dq.setString(2, inboxUserName);
				dq.setString(3, inboxPassword);
				dq.setString(4, inboxSmtpHost);
				dq.setString(5, inboxpopHost);
				dq.setId(6, inboxPollingDuration);
				dq.setString(7, inboxDisplayName);
				dq.setId(8, inboxServerType);
				dq.setId(9, inboxSmtpAuthRequired);
				dq.setId(10, inboxSmtpAuthSame);
				dq.setString(11, inboxSmtpUserName);
				dq.setString(12, inboxSmtpPassword);
				dq.setString(13, inboxIncomingPort);
				dq.setId(14, inboxIncomingSSLEnabled);
				dq.setString(15, inboxOutgoingPort);
				dq.setId(16, inboxOutgoingSSLEnabled);
				dq.setId(17, inboxOutgoingTLSEnabled);
				dq.setString(18, exchangeServerName);
				dq.setString(19, domainName);				
				dq.setString(20, exchangeServerVersion);
				dq.setString(21, exchangeSmtp);
				dq.execute();
			} else {
				dq = new DBPreparedQuery("dUpdateInboxSettings");
				dq.setString(1, inboxEmail);
				dq.setString(2, inboxUserName);
				dq.setString(3, inboxPassword);
				dq.setString(4, inboxSmtpHost);
				dq.setString(5, inboxpopHost);
				dq.setId(6, inboxPollingDuration);
				dq.setString(7, inboxDisplayName);
				dq.setId(8, inboxServerType);
				dq.setId(9, inboxSmtpAuthRequired);
				dq.setId(10, inboxSmtpAuthSame);
				dq.setString(11, inboxSmtpUserName);
				dq.setString(12, inboxSmtpPassword);
				dq.setString(13, inboxIncomingPort);
				dq.setId(14, inboxIncomingSSLEnabled);
				dq.setString(15, inboxOutgoingPort);
				dq.setId(16, inboxOutgoingSSLEnabled);
				dq.setId(17, inboxOutgoingTLSEnabled);
				dq.setString(18, exchangeServerName);
				dq.setString(19, domainName);				
				dq.setString(20, exchangeServerVersion);
				dq.setString(21, exchangeSmtp);
				dq.execute();
			}
		} catch (SQLException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			if (dbq != null) {
				dbq.releaseConnection();
			}
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return inboxrows;
	}

	/**
	 * Method to retrieve Inbox Settings to display
	 * 
	 * @return
	 */
	public SimpleDataObject getInboxSettings() {
		DBPreparedQuery dq = null;
		SimpleDataObject sDo = null;
		try {
			dq = new DBPreparedQuery("dGetInboxSettings");
			sDo = (SimpleDataObject) dq.getSingleObjectResult();
		} catch (SQLException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}

		return sDo;
	}

	public SimpleDataObject getSMSProviderSettings() {
		SimpleDataObject setting = null;
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dAdminManager_GetDefaultSMSProvider");
			dq.setString(1, AdminConstants.SMS_PROVIDER_DEFAULT_YES);
			setting = (SimpleDataObject) dq.getSingleObjectResult();
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return setting;
	}

	public void saveSMSSettings(String provider, String value) throws Exception {
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dAdminManager_SaveDefaultSMSProvider");
			dq.setString(1, provider);
			dq.setString(2, value);
			dq.setString(3, AdminConstants.SMS_PROVIDER_DEFAULT_YES);
			dq.execute();
			SMSGatewayImpl.setProviderURL(null);
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			throw e;
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
	}

	public ArrayList<SimpleDataObject> fetchGlobalProperties() {
		DBQuery dq = null;
		ArrayList<SimpleDataObject> results = null;
		try {
			dq = new DBQuery("dAdminManager_FetchApplicationProperties");
			results = dq.getResult();
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return results;
	}

	public void updateGlobalSettings(HashMap<String, String> props) {
		DBTransaction tran = null;
		try {
			tran = new DBTransaction();
			for (Iterator itProp = props.keySet().iterator(); itProp.hasNext();) {
				String propertyName = (String) itProp.next();
				String propertyValue = props.get(propertyName);
				updateApplicationProperty(propertyName, propertyValue, tran);
				if(GlobalConstants.PROPERTY_MAX_DEPT_LEVEL.equals(propertyName) &&
					!GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_MAX_DEPT_LEVEL)
						.equals(propertyValue)) {
					resetDeptlLevelConfigOnWebsite(GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_MAX_DEPT_LEVEL),propertyValue,tran);
				} 
			}
			tran.commit();
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			try {
				tran.rollback();
			} catch (Exception ex) {
				TPLogger.getLogger().error(GlobalConstants.ERROR, ex);
			}
		} finally {
			if (tran != null) {
				tran.release();
			}
		}
	}
	
	private void resetDeptlLevelConfigOnWebsite(String currentVal,String changedVal,DBTransaction tran) throws SQLException{
		if(MastersConstants.DEPARTMENT_LEVEL_3.equals(changedVal)){
			deleteFieldsOnWebsite(PositionConfigurationConstants.FIELD_DEPARTMENT_LEVEL_4,tran);
			deleteFieldsOnWebsite(PositionConfigurationConstants.FIELD_DEPARTMENT_LEVEL_5, tran);
		}else if(MastersConstants.DEPARTMENT_LEVEL_4.equals(changedVal)){
			if(MastersConstants.DEPARTMENT_LEVEL_3.equals(currentVal)){
				insertFieldsOnWebsite(PositionConfigurationConstants.FIELD_DEPARTMENT_LEVEL_4, tran);
			}else if(MastersConstants.DEPARTMENT_LEVEL_5.equals(currentVal)){
				deleteFieldsOnWebsite(PositionConfigurationConstants.FIELD_DEPARTMENT_LEVEL_5, tran);
			}
		}else if(MastersConstants.DEPARTMENT_LEVEL_5.equals(changedVal)){
			if(MastersConstants.DEPARTMENT_LEVEL_3.equals(currentVal)){
				insertFieldsOnWebsite(PositionConfigurationConstants.FIELD_DEPARTMENT_LEVEL_4,tran);
				insertFieldsOnWebsite(PositionConfigurationConstants.FIELD_DEPARTMENT_LEVEL_5, tran);
			}else if(MastersConstants.DEPARTMENT_LEVEL_4.equals(currentVal)){
				insertFieldsOnWebsite(PositionConfigurationConstants.FIELD_DEPARTMENT_LEVEL_5, tran);
			}
		}
	}
	
	private void deleteFieldsOnWebsite(String fieldId,DBTransaction tran) throws SQLException{
		DBPreparedQuery dq = null;
		int result;
		try {
			if(tran == null) {
				dq = new DBPreparedQuery("dAdminManager_deleteFieldsOnWebsite");
			} else {
				dq = new DBPreparedQuery("dAdminManager_deleteFieldsOnWebsite",tran);
			}
			dq.setString(1, fieldId);
			result = dq.execute();
			if(result!=-1 && result!=0){
				updateFieldRankOnListPage(fieldId, tran);
				updateFieldRankOnDetailsPage(fieldId, tran);
			}
		} finally {
			if(tran == null) {
				dq.releaseConnection();
			}
		}
	}
	
	private void insertFieldsOnWebsite(String fieldId, DBTransaction tran) throws SQLException{
		DBPreparedQuery dq = null;
		try {
			if(tran == null) {
				dq = new DBPreparedQuery("dAdminManager_insertDeptFieldsOnWebsite");
			} else {
				dq = new DBPreparedQuery("dAdminManager_insertDeptFieldsOnWebsite", tran);
			}
			dq.setString(1, fieldId);
			dq.setString(2, PositionConfigurationConstants.FIELD_TYPE_NORMAL);
			dq.execute();
		} finally {
			if(tran == null) {
				dq.releaseConnection();
			}
		}
	}
	
	private void updateFieldRankOnListPage(String fieldId, DBTransaction tran) throws SQLException{
		DBPreparedQuery dq = null;
		try {
			String fieldRankOnListPage = PositionScreenConfigurationManager.getPositionFieldListPageRank(fieldId);
			if(tran == null) {
				dq = new DBPreparedQuery("dAdminManager_updateFieldRankOnListPage");
			} else {
				dq = new DBPreparedQuery("dAdminManager_updateFieldRankOnListPage", tran);
			}
			dq.setString(1, fieldRankOnListPage);
			dq.execute();
		} finally {
			if(tran == null) {
				dq.releaseConnection();
			}
		}
	}
	
	private void updateFieldRankOnDetailsPage(String fieldId, DBTransaction tran) throws SQLException{
		DBPreparedQuery dq = null;
		try {
			String fieldRankOnDetailsPage = PositionScreenConfigurationManager.getPositionFieldDetailsPageRank(fieldId);
			if(tran == null) {
				dq = new DBPreparedQuery("dAdminManager_updateFieldRankOnDetailsPage");
			} else {
				dq = new DBPreparedQuery("dAdminManager_updateFieldRankOnDetailsPage", tran);
			}
			dq.setString(1, fieldRankOnDetailsPage);
			dq.execute();
		} finally {
			if(tran == null) {
				dq.releaseConnection();
			}
		}
	}
	
	public void updateApplicationProperty(String propertyName, String propertyValue, DBTransaction tran) throws SQLException {
		DBPreparedQuery dq = null;
		try {
			if(tran == null) {
				dq = new DBPreparedQuery("dAdminManager_UpdateApplicationProperties");
			} else {
				dq = new DBPreparedQuery("dAdminManager_UpdateApplicationProperties", tran);
			}
			dq.setString(1, propertyValue);
			dq.setString(2, propertyName);
			dq.execute();
		} finally {
			if(tran == null) {
				dq.releaseConnection();
			}
		}
	}

	/**
	 * @param getRoles
	 * @return
	 */
	public ArrayList getRoles() {
		DBPreparedQuery dq = null;
		ArrayList roles = null;

		try {
			dq = new DBPreparedQuery("dAdminManager_FetchRoles");
			dq.setInt(1, UserConstants.ROLE_VENDOR);
			dq.setInt(2, UserConstants.ROLE_ADMIN);
			dq.setInt(3, UserConstants.ROLE_EMPLOYEE);
			roles = dq.getResult();
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return roles;
	}
	
	public RoleData getRoleByTitle(String roleTitle) {
		DBPreparedQuery dq = null;
		RoleData role = null;

		try {
			dq = new DBPreparedQuery("dAdminManager_FetchRoleByTitle");
			dq.setString(1, roleTitle);			
			role = (RoleData) dq.getSingleObjectResult();
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return role;
	}
	
	public LoginData getUserByName(String name) {
		DBPreparedQuery dq = null;
		LoginData user = null;

		try {
			dq = new DBPreparedQuery("dFetchUserByName");
			dq.setString(1, name);			
			user = (LoginData) dq.getSingleObjectResult();
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return user;
	}

	public String getXMLForRoles(ArrayList roles) {
		StringWriter sWr = new StringWriter();
		XMLWriter wr = new XMLWriter(sWr);
		try {
			wr.startDocument();
			wr.startElement("rows");
			// ArrayList roles = roleData.getRoles();
			String roleId = "";
			String roleTitle = "";
			for (int i = 0; i < roles.size(); i++) {
				RoleData roleData = (RoleData) roles.get(i);
				roleId = "" + roleData.getRoleId();
				roleTitle = roleData.getRoleTitle();

				AttributesImpl at = new AttributesImpl();
				at.addAttribute("", "id", "", "", roleId);
				wr.startElement("", "row", "", at);

				at = new AttributesImpl();
				at.addAttribute("", "name", "", "", "roleTitle");
				wr.startElement("", "userdata", "", at);
				wr.characters(roleTitle);
				wr.endElement("userdata");

				wr.startElement("cell");
				wr.characters("<a href=\"#\" onclick=\"viewDetails(" + roleId + ");\">" + roleTitle + "</a>");
				wr.endElement("cell");

				wr.endElement("row");
			}
			wr.endElement("rows");
			wr.endDocument();
		} catch (Exception e) {

		}

		return sWr.getBuffer().toString();
	}

	/**
	 * @param getPermissions
	 * @return
	 */
	public ArrayList<PermissionData> getRolePermissions(String roleId) {
		DBPreparedQuery dq = null;
		ArrayList<PermissionData> permissions = null;
		try {
			dq = new DBPreparedQuery("dAdminManager_FetchRolePermissions");
			dq.setId(1, roleId);
			permissions = dq.getResult();
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return permissions;
	}

	/**
	 * @param getUserPermissions
	 * @return
	 */
	public ArrayList<PermissionData> getUserPermissions(String userId) {
		DBPreparedQuery dq = null;
		ArrayList<PermissionData> userPermissions = null;
		try {
			dq = new DBPreparedQuery("dAdminManager_FetchUserPermissions");
			dq.setId(1, userId);
			userPermissions = dq.getResult();			
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return userPermissions;
	}

	/**
	 * @param getAvailableModule
	 * @return
	 */
	public ArrayList<PermissionData> getAvailableModulePermissions() {
		DBPreparedQuery dq = null;
		ArrayList<PermissionData> AvailableModules = null;
		try {
			dq = new DBPreparedQuery("dAdminManager_FetchModulePermissions");
			AvailableModules = dq.getResult();
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return AvailableModules;
	}

	/**
	 * @param getAvailableLevelPermissions
	 * @return
	 */
	public ArrayList<ReportLevelData> getAvailableReportLevelForRole(String roleId) {
		DBPreparedQuery dq = null;
		ArrayList<ReportLevelData> availableReportLevelForRole = null;
		try {
			dq = new DBPreparedQuery("dAdminManager_FetchReportLevelForRole");
			dq.setId(1, roleId);
			availableReportLevelForRole = dq.getResult();
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return availableReportLevelForRole;
	}

	/**
	 * @param getAvailableReportLevelForUser
	 * @return
	 */
	public ArrayList<ReportLevelData> getAvailableReportLevelForUser(String userId) {
		DBPreparedQuery dq = null;
		ArrayList<ReportLevelData> availableReportLevelForUser = null;
		try {
			dq = new DBPreparedQuery("dAdminManager_FetchReportLevelForUser");
			dq.setId(1, userId);
			availableReportLevelForUser = dq.getResult();
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return availableReportLevelForUser;
	}
	
	public ArrayList<ReportLevelData> getAllReportLevels() {
		DBPreparedQuery dq = null;
		ArrayList<ReportLevelData> allReportLevels = null;
		try {
			dq = new DBPreparedQuery("dAdminManager_FetchAllReportLevels");
			allReportLevels = dq.getResult();
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return allReportLevels;
	}

	/**
	 * @param Update
	 *            Permissions for role
	 * @return
	 */
	public void updateRolePermissions(String roleId, String strPermissions, String strLevelPermissions) throws Exception {
		DBPreparedQuery dq = null;
		DBTransaction tran = null;
		try {
			tran = new DBTransaction();
			// update permissions for roleId
			dq = new DBPreparedQuery("dAdminManager_DeleteRolePermissions", tran);
			dq.setId(1, roleId);
			dq.execute();
			String[] permission = strPermissions.split(",");
			for (int i = 1; i < permission.length; i++) {
				String[] permissionValueId = permission[i].split(":");
				String permissionId = permissionValueId[0];
				String permissionValue = permissionValueId[1];
				if (permissionValue.equals("1")) {
					dq = new DBPreparedQuery("dAdminManager_InsertRolePermissions", tran);
					dq.setString(1, roleId);
					dq.setString(2, permissionId);
					dq.execute();
				}
			}

			// update Levels for roleId
			dq = new DBPreparedQuery("dAdminManager_DeleteLevelForRole", tran);
			dq.setId(1, roleId);
			dq.execute();
			String[] levelsPresent = strLevelPermissions.split(",");
			for (int i = 1; i < levelsPresent.length; i++) {

				String[] levelsPresentStr = levelsPresent[i].split(":");
				String levelId = levelsPresentStr[0];
				String levelsPresentValue = levelsPresentStr[1];
				if (levelsPresentValue.equals("1")) {
					dq = new DBPreparedQuery("dAdminManager_InsertLevelForRole", tran);
					dq.setString(1, roleId);
					dq.setString(2, levelId);
					dq.execute();
				}
			}
			tran.commit();
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			tran.rollback();
			throw e;
		} finally {
			if (dq != null) {
				dq.releaseTransaction(tran);
			}
		}
	}

	/**
	 * @param updateUserPermissions
	 * @return
	 */
	public void updateUserPermissions(String userId, String strPermissions, String strLevelPermissionsForUser) throws Exception {
		DBPreparedQuery dq = null;
		DBTransaction tran = null;
		try {
			tran = new DBTransaction();
			// update user permissions
			deleteUserPermissions(userId, tran);
			String[] permission = strPermissions.split(",");
			for (int i = 1; i < permission.length; i++) {
				String[] permissionValueId = permission[i].split(":");
				String permissionId = permissionValueId[0];
				String permissionValue = permissionValueId[1];
				if (permissionValue.equals("1")) {
					dq = new DBPreparedQuery("dAdminManager_InsertPermissionForUser", tran);
					dq.setString(1, userId);
					dq.setString(2, permissionId);
					dq.execute();
				}
			}
			// update user reportLevel
			dq = new DBPreparedQuery("dAdminManager_DeleteLevelForUser", tran);
			dq.setId(1, userId);
			dq.execute();
			
			String[] levelsPresent = strLevelPermissionsForUser.split(",");
			for (int i = 1; i < levelsPresent.length; i++) {
				String[] levelsPresentStr = levelsPresent[i].split(":");
				String levelId = levelsPresentStr[0];
				String levelsPresentValue = levelsPresentStr[1];
				if (levelsPresentValue.equals("1")) {
					dq = new DBPreparedQuery("dAdminManager_InsertReportLevelForUser", tran);
					dq.setString(1, userId);
					dq.setString(2, levelId);
					dq.execute();
				}
			}
			tran.commit();
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			tran.rollback();
			throw e;
		} finally {
			if (dq != null) {
				dq.releaseTransaction(tran);
			}
		}

	}

	private void deleteUserPermissions(String userId, DBTransaction tran) throws Exception {
		DBPreparedQuery dq = new DBPreparedQuery("dAdminManager_DeleteUserPermissions", tran);
		dq.setId(1, userId);
		dq.execute();
	}

	private void deleteUserReports(String userId, DBTransaction tran) throws Exception {
		DBPreparedQuery dq = new DBPreparedQuery("dAdminManager_DeleteUserReports", tran);
		dq.setId(1, userId);
		dq.execute();
	}
	
	/**
	 * @param getChangeInUserPermission
	 * @return
	 */
	public ArrayList<PermissionData> getChangeInUserPermission(String roleId, String changePermissionID) {
		DBPreparedQuery dq = null;
		ArrayList<PermissionData> changeInUserPermission = null;
		try {
			String[] dynParams = new String[1];
			dynParams[0] = "";
			ArrayList<String> dynamicContent = new ArrayList<String>();
			String qMarks = Utils.setDynamicParamsAndReturnQmarks(changePermissionID, dynamicContent);
			dynParams[0] = qMarks;
			dq = new DBPreparedQuery("dAdminManager_FetchChangeInUserPermission", dynParams);
			dq.setId(1, roleId);
			int cnt = 2;
			for (int i = 0; i < dynamicContent.size(); i++) {
				dq.setString(cnt++, dynamicContent.get(i));
			}
			dq.setId(cnt, roleId);
			changeInUserPermission = dq.getResult();
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return changeInUserPermission;
	}

	/**
	 * @param getChangeInReportLevel
	 * @return
	 */
	public ArrayList<ReportLevelData> getChangeInReportLevel(String roleId, String changedReportLevelIds) {
		DBPreparedQuery dq = null;
		ArrayList<ReportLevelData> changeInReportLevel = null;
		try {
			String[] dynParams = new String[1];
			dynParams[0] = "";
			ArrayList<String> dynamicContent = new ArrayList<String>();
			String qMarks = Utils.setDynamicParamsAndReturnQmarks(changedReportLevelIds, dynamicContent);
			dynParams[0] = qMarks;
			dq = new DBPreparedQuery("dAdminManager_FetchChangeInReportLevel", dynParams);
			dq.setId(1, roleId);
			int cnt = 2;
			for (int i = 0; i < dynamicContent.size(); i++) {
				dq.setString(cnt++, dynamicContent.get(i));
			}
			dq.setId(cnt, roleId);
			changeInReportLevel = dq.getResult();
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return changeInReportLevel;
	}

	/**
	 * @param getActiveUsersForRole
	 * @return
	 * @return
	 */
	public ArrayList<LoginData> getActiveUsersForRole(String roleId) {
		DBPreparedQuery dq = null;
		ArrayList<LoginData> availableUsersForRole = null;
		try {
			dq = new DBPreparedQuery("dAdminManager_FetchActiveUsersForRole");
			dq.setId(1, roleId);
			availableUsersForRole = dq.getResult();
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return availableUsersForRole;
	}

	/**
	 * @param updateUserPermissions
	 * @return
	 */
	public void updateUserPermissionForChangedRolePermission(String userId, String changePermissionIds, String changePermissionIdValues, String changedReportLevelIds, String changedReportLevelValues) throws Exception {
		DBPreparedQuery dq = null;
		DBTransaction tran = null;
		try {
			tran = new DBTransaction();
			// update user permissions
			String[] changePermissionId = changePermissionIds.split(",");
			String[] changePermissionIdValue = changePermissionIdValues.split(",");
			for (int j = 0; j < changePermissionId.length; j++) {
				dq = new DBPreparedQuery("dAdminManager_DeletePermissionForUser", tran);
				dq.setString(1, userId);
				dq.setString(2, changePermissionId[j]);
				dq.execute();
				if (changePermissionIdValue[j].equals("1")) {
					dq = new DBPreparedQuery("dAdminManager_InsertPermissionForUser", tran);
					dq.setString(1, userId);
					dq.setString(2, changePermissionId[j]);
					dq.execute();
				}
			}
			// update user level
			String[] changeReportLevelId = changedReportLevelIds.split(",");
			String[] changeReportLevelValue = changedReportLevelValues.split(",");
			for (int k = 0; k < changeReportLevelId.length; k++) {
				dq = new DBPreparedQuery("dAdminManager_DeleteReportLevelForUser", tran);
				dq.setString(1, userId);
				dq.setString(2, changeReportLevelId[k]);
				dq.execute();
				if (changeReportLevelValue[k].equals("1")) {
					dq = new DBPreparedQuery("dAdminManager_InsertReportLevelForUser", tran);
					dq.setString(1, userId);
					dq.setString(2, changeReportLevelId[k]);
					dq.execute();
				}
			}
			tran.commit();
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			tran.rollback();
			throw e;
		} finally {
			if (dq != null) {
				dq.releaseTransaction(tran);
			}
		}

	}

	/**
	 * Report Level Settings
	 */

	/**
	 * 
	 * @return
	 */
	public ArrayList<ReportLevelData> fetchLevelDetails() {
		DBQuery dbq = null;
		ArrayList<ReportLevelData> results = null;
		try {
			dbq = new DBQuery("dAdminManager_FetchLevelDetails");
			results = dbq.getResult();
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			if (dbq != null) {
				dbq.releaseConnection();
			}
		}
		return results;
	}

	/**
	 * @return
	 */
	public ArrayList getLevels() {
		DBPreparedQuery dq = null;
		ArrayList levels = null;
		try {
			dq = new DBPreparedQuery("dAdminManager_FetchLevelDetails");
			levels = dq.getResult();
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return levels;
	}

	/**
	 * @param users
	 * @return
	 */
	public String getXMLForReportLevel(ArrayList levels) {
		StringWriter sWr = new StringWriter();
		XMLWriter wr = new XMLWriter(sWr);

		try {
			wr.startDocument();
			wr.startElement("rows");
			for (int i = 0; i < levels.size(); i++) {
				ReportLevelData reportLevelData = (ReportLevelData) levels.get(i);
				String levelId = "" + reportLevelData.getLevelId();

				AttributesImpl at = new AttributesImpl();
				at.addAttribute("", "id", "", "", "" + reportLevelData.getLevelId());
				wr.startElement("", "row", "", at);

				at = new AttributesImpl();
				at.addAttribute("", "name", "", "", "levelId");
				wr.startElement("", "userdata", "", at);
				wr.characters(levelId);
				wr.endElement("userdata");

				at = new AttributesImpl();
				at.addAttribute("", "name", "", "", "levelName");
				wr.startElement("", "userdata", "", at);
				wr.characters(reportLevelData.getLevelName());
				wr.endElement("userdata");

				wr.startElement("cell");
				wr.characters("<a href=\"#\" onclick=\"performDelete(" + reportLevelData.getLevelId() + ");\" title=\"Delete\"><img src=\"images/ico_delete.gif\" border=0></a>");
				wr.endElement("cell");

				wr.startElement("cell");
				wr.characters(wr.doubleEscape(reportLevelData.getLevelName()));
				wr.endElement("cell");

				wr.endElement("row");
			}
			wr.endElement("rows");
			wr.endDocument();
		} catch (SAXException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return sWr.getBuffer().toString();
	}

	public ReportLevelData getLevelData(String levelId) {
		DBPreparedQuery dq = null;
		ReportLevelData reportLevelData = null;
		try {
			dq = new DBPreparedQuery("dAdminManager_GetLevelData");
			dq.setId(1, levelId);
			reportLevelData = (ReportLevelData) dq.getSingleObjectResult();
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return reportLevelData;
	}

	public ArrayList<String> getReportsForLevel(String levelId) {
		DBPreparedQuery dq = null;
		ArrayList<String> reportIds = new ArrayList<String>();
		ArrayList<SimpleDataObject> results = null;
		try {
			dq = new DBPreparedQuery("dAdminManager_GetReportsForLevel");
			dq.setId(1, levelId);
			results = dq.getResult();
			for (int i = 0; results != null && i < results.size(); i++) {
				reportIds.add(results.get(i).getString("reportId")+"_"+results.get(i).getInt("reportType"));
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return reportIds;
	}

	/**
	 * Create user
	 * 
	 * @param loginData
	 * @return
	 * @throws SQLException
	 *             if levelName already exists in DB
	 */
	public String createLevel(ReportLevelData reportLevelData, String[] separatedlevelReportID) throws SQLException {
		DBPreparedQuery dq = null;
		String levelId = null;
		DBTransaction tran = null;
		try {
			tran = new DBTransaction();
			dq = new DBPreparedQuery("dAdminManager_AddNewLevel", tran);
			dq.setString(1, reportLevelData.getLevelName());
			dq.execute();
			dq = new DBPreparedQuery("dFetchLastInsertID", tran);
			levelId = dq.getIdResult();
			for (int i = 0; i < separatedlevelReportID.length; i++) {
				dq = new DBPreparedQuery("dAdminManager_AddLevelReport", tran);
				dq.setString(1, levelId);
				String[] idAndType =  separatedlevelReportID[i].split("_");
				dq.setString(2, idAndType[0]);
				dq.setString(3, idAndType[1]);
				dq.execute();
			}
			tran.commit();
		} catch (SQLException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			tran.rollback();
			throw e;
		} finally {
			if (dq != null) {
				dq.releaseTransaction(tran);
			}
		}
		return levelId;
	}

	/**
	 * Updates levelId including levelName
	 * 
	 * @param levelId
	 * @param reportLevelData
	 * @throws SQLException
	 */
	public void updateLevel(String levelId, ReportLevelData reportLevelData, String[] separatedlevelReportID) throws SQLException {
		DBPreparedQuery dq = null;
		DBTransaction tran = null;
		try {
			tran = new DBTransaction();
			dq = new DBPreparedQuery("dAdminManager_UpdateLevel", tran);
			dq.setString(1, reportLevelData.getLevelName());
			dq.setId(2, levelId);
			dq.execute();
			dq = new DBPreparedQuery("dAdminManager_deleteLevelReport", tran);
			dq.setId(1, levelId);
			dq.execute();
			for (int i = 0; i < separatedlevelReportID.length; i++) {
				dq = new DBPreparedQuery("dAdminManager_AddLevelReport", tran);
				dq.setString(1, levelId);
				String[] idAndType =  separatedlevelReportID[i].split("_");
				dq.setString(2, idAndType[0]);
				dq.setString(3, idAndType[1]);
				dq.execute();
			}
			tran.commit();
		} catch (SQLException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			tran.rollback();
			throw e;
		} finally {
			if (dq != null) {
				dq.releaseTransaction(tran);
			}
		}
	}

	/**
	 * Updates levelId including levelName
	 * 
	 * @param levelId
	 * @param reportLevelData
	 * @throws SQLException
	 */
	public void addCustomReportToLevels(Long reportId, String levelIds) throws SQLException {
		DBPreparedQuery dq = null;
		DBTransaction tran = null;
		try {
			tran = new DBTransaction();
			String[] reportLevelIds = levelIds.split(",");
			for (int i = 0; i < reportLevelIds.length; i++) {
				dq = new DBPreparedQuery("dAdminManager_AddLevelReport", tran);
				dq.setString(1, reportLevelIds[i]);
				dq.setString(2, ""+reportId);
				dq.setString(3, "2");
				dq.execute();
			}
			tran.commit();
		} catch (SQLException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			tran.rollback();
			throw e;
		} finally {
			if (dq != null) {
				dq.releaseTransaction(tran);
			}
		}
	}

	/**
	 * Delete levelId including levelName
	 * 
	 * @param levelId
	 * @throws SQLException
	 */
	public void removeLevel(String levelId) throws SQLException {
		DBPreparedQuery dq = null;
		DBTransaction tran = null;
		try {
			tran = new DBTransaction();
			dq = new DBPreparedQuery("dAdminManager_deleteLevelReport", tran);
			dq.setId(1, levelId);
			dq.execute();
			dq = new DBPreparedQuery("dAdminManager_deleteLevels", tran);
			dq.setId(1, levelId);
			dq.execute();

			tran.commit();
		} catch (SQLException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			tran.rollback();
			throw e;
		} finally {
			if (dq != null) {
				dq.releaseTransaction(tran);
			}
		}
	}

	public ArrayList getEmployeeSource() {
		DBPreparedQuery dq = null;
		ArrayList<SimpleDataObject> employeeSource = new ArrayList();
		try {
			dq = new DBPreparedQuery("dAdminManager_GetEmployeeSource");
			dq.setString(1, AdminConstants.SOURCE_CATEGORY_EMPLOYEE_REFERAL);
			employeeSource = dq.getResult();

		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return employeeSource;
	}

	public ArrayList getEmployeeSourceTypeId() {
		DBPreparedQuery dq = null;
		ArrayList<SourceTypeData> sourceTypeDetail = new ArrayList<SourceTypeData>();
		try {
			dq = new DBPreparedQuery("dAdminManager_GetEmployeeSourceType");
			dq.setString(1, AdminConstants.SOURCE_CATEGORY_EMPLOYEE_REFERAL);
			sourceTypeDetail = dq.getResult();

		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return sourceTypeDetail;
	}
	
	public SourceData getCompanyWebSiteSource() {
		DBPreparedQuery dq = null;
		SourceData sourceDetail =  null;
		try {
			dq = new DBPreparedQuery("dAdminManager_GetCompanyWebSiteSource");
			dq.setString(1, AdminConstants.SOURCE_CATEGORY_WEB_SITE);
			sourceDetail = (SourceData)dq.getSingleObjectResult();
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return sourceDetail;
	}

	public int getEmployeeSourceUser(String userId, String userSourceId) {
		DBPreparedQuery dq = null;
		int count = 0;
		try {
			dq = new DBPreparedQuery("dAdminManager_GetEmployeeSourceUser");
			dq.setString(1, userId);
			dq.setString(2, userSourceId);
			count = dq.getIntResult();
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return count;
	}

	public String getXMLForMessages() {
		StringWriter sWr = new StringWriter();
		XMLWriter wr = new XMLWriter(sWr);
		try {
			Properties properties = Utils.loadAppliocationLabels(null);
			Set<Object> keys = properties.keySet();
			List<String> newKeys = new ArrayList<String>();
			for(Object key : keys) {
				newKeys.add((String)key);
			}
			Collections.sort(newKeys);
			
			Iterator itr = newKeys.iterator();
			
			wr.startDocument();
			wr.startElement("rows");
			
			while(itr.hasNext()) {				
				String key = (String) itr.next();
				String value = properties.getProperty(key);
				
				AttributesImpl at = new AttributesImpl();
				at.addAttribute("", "id", "", "", key);
				wr.startElement("", "row", "", at);
				
				at = new AttributesImpl();
				at.addAttribute("", "name", "", "", "value");
				wr.startElement("", "userdata", "", at);
				wr.characters(value);
				wr.endElement("userdata");
				
				wr.startElement("cell");
				wr.characters(key);
				wr.endElement("cell");
				
				wr.startElement("cell");
				wr.characters(wr.doubleEscape(value)+"^javascript:viewDetails(\"" + key + "\");^_self");
				wr.endElement("cell");

				wr.endElement("row");
			}
			wr.endElement("rows");
			wr.endDocument();
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while getting XML for messages", e);
		}		
		return sWr.getBuffer().toString();
	}
	
	public void addOrUpdateLabel(String key, String value) throws Exception {		
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dAdminManager_AddOrUpdateLabel");
			dq.setString(1, key);
			dq.setString(2, value);
			dq.setString(3, value);
			dq.execute();
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			throw e;
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
	}
	
	public void deleteCustomLabels() throws SQLException {
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dAdminManager_DeleteCustomLabels");
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

	public String getSourceIdForEmployeeCode(String employeeCode) {
		DBPreparedQuery dq = null;
		String sourceId = null;
		try {
			dq = new DBPreparedQuery("dAdminManager_GetSourceIdForEmployeeCode");
			dq.setString(1, employeeCode);
			sourceId = dq.getIdResult();
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return sourceId;
	}

	public void updateImportConfiguration(ArrayList<ImportFieldData> array) throws Exception {
		DBPreparedQuery dq = null;
		DBTransaction tran = null;
		try {
			tran = new DBTransaction();
			for (int i = 0; array!=null && i < array.size(); i++) {
				ImportFieldData importData = array.get(i);
				dq = new DBPreparedQuery("dAdminManager_UpdateScreenConfiguration", tran);
				dq.setString(1, importData.getFieldImportShow());
				dq.setString(2, importData.getFieldEditShow());
				dq.setString(3, importData.getFieldImportMandatory());
				dq.setString(4, importData.getFieldVendorShow());
				dq.setString(5, importData.getFieldVendorMandatory());				
				dq.setString(6, importData.getFieldRank());				
				dq.setString(7, importData.getFieldEmployeeShow());
				dq.setString(8, importData.getFieldEmployeeMandatory());
				dq.setString(9, importData.getFieldWebsiteShow());
				dq.setString(10, importData.getFieldWebsiteMandatory());
				dq.setString(11, importData.getFieldConfidential());
				dq.setString(12, importData.getFieldId());
				dq.execute();
			}
			tran.commit();
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			tran.rollback();
			throw e;
		} finally {
			if (dq != null) {
				dq.releaseTransaction(tran);
			}
		}
	}	
	
	public void updatePositionListConfiguration(ArrayList<PositionFieldData> array) throws Exception {
		DBPreparedQuery dq = null;
		DBTransaction tran = null;
		try {
			tran = new DBTransaction();
			for (int i = 0; array!=null && i < array.size(); i++) {
				PositionFieldData positionData = array.get(i);
				dq = new DBPreparedQuery("dAdminManager_UpdateWebsitePositionListScreenConfiguration", tran);
				dq.setString(1, positionData.getFieldRank());
				dq.setString(2, positionData.getFieldOnPositionListShow());
				dq.setString(3, positionData.getFieldIsFilter());
				dq.setString(4, positionData.getFieldIsFilterEditable());
				dq.setString(5, positionData.getFieldId());
				dq.execute();
			}
			tran.commit();
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			tran.rollback();
			throw e;
		} finally {
			if (dq != null) {
				dq.releaseTransaction(tran);
			}
		}
	}
	
	public void updatePositionScreenConfiguration(ArrayList<PositionFieldData> array) throws Exception {
		DBPreparedQuery dq = null;
		DBTransaction tran = null;
		try {
			tran = new DBTransaction();
			for (int i = 0; array!=null && i < array.size(); i++) {
				PositionFieldData positionData = array.get(i);
				dq = new DBPreparedQuery("dAdminManager_UpdatePositionScreenConfiguration", tran);
				dq.setString(1, positionData.getFieldRank());
				dq.setString(2, positionData.getFieldOnPositionPrintShow());
				dq.setString(3, positionData.getFieldId());
				dq.execute();
			}
			tran.commit();
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			tran.rollback();
			throw e;
		} finally {
			if (dq != null) {
				dq.releaseTransaction(tran);
			}
		}
	}	
	
	public void updatePositionDescriptionScreenConfiguration(ArrayList<PositionFieldData> array) throws Exception {
		DBPreparedQuery dq = null;
		DBTransaction tran = null;
		try {
			tran = new DBTransaction();
			for (int i = 0; array!=null && i < array.size(); i++) {
				PositionFieldData positionData = array.get(i);
				dq = new DBPreparedQuery("dAdminManager_UpdatePositionDescriptionScreenConfiguration", tran);
				dq.setString(1, positionData.getFieldRank());
				dq.setString(2, positionData.getFieldPositionShow());
				dq.setString(3, positionData.getFieldPositionMandatory());
				dq.setString(4, positionData.getFieldVendorShow());
				dq.setString(5, positionData.getFieldEmployeeShow());
				dq.setString(6, positionData.getFieldId());
				dq.execute();
			}
			tran.commit();
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			tran.rollback();
			throw e;
		} finally {
			if (dq != null) {
				dq.releaseTransaction(tran);
			}
		}
	}	
	
	public void updatePositionRequirementsScreenConfiguration(ArrayList<PositionFieldData> array) throws Exception {
		DBPreparedQuery dq = null;
		DBTransaction tran = null;
		try {
			tran = new DBTransaction();
			for (int i = 0; array!=null && i < array.size(); i++) {
				PositionFieldData positionData = array.get(i);
				dq = new DBPreparedQuery("dAdminManager_UpdatePositionRequirementsScreenConfiguration", tran);
				dq.setString(1, positionData.getFieldRank());
				dq.setString(2, positionData.getFieldPositionShow());
				dq.setString(3, positionData.getFieldPositionMandatory());
				dq.setString(4, positionData.getFieldVendorShow());
				dq.setString(5, positionData.getFieldEmployeeShow());
				dq.setString(6, positionData.getFieldId());
				dq.execute();
			}
			tran.commit();
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			tran.rollback();
			throw e;
		} finally {
			if (dq != null) {
				dq.releaseTransaction(tran);
			}
		}
	}	
	
	public void updatePositionDetailsConfiguration(ArrayList<PositionFieldData> array) throws Exception {
		DBPreparedQuery dq = null;
		DBTransaction tran = null;
		try {
			tran = new DBTransaction();
			for (int i = 0; array!=null && i < array.size(); i++) {
				PositionFieldData positionData = array.get(i);
				dq = new DBPreparedQuery("dAdminManager_UpdateWebsitePositionDetailsScreenConfiguration", tran);
				dq.setString(1, positionData.getFieldPositionDetailsRank());
				dq.setString(2, positionData.getFieldOnPositionDetailsShow());
				dq.setString(3, positionData.getFieldId());
				dq.execute();
			}
			tran.commit();
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			tran.rollback();
			throw e;
		} finally {
			if (dq != null) {
				dq.releaseTransaction(tran);
			}
		}
	}
	
	public boolean isUserSourceBlackListed(String userId){
		DBPreparedQuery dq = null;
		boolean status=false;
		String result;
		try {
			dq = new DBPreparedQuery("dIsUserSourceBlacklisted");
			dq.setString(1, userId);
			result = dq.getStringResult();
			if(AdminConstants.SOURCE_BLACKLISTED.equals(result)){
				status = true;
			}else if(AdminConstants.SOURCE_NOT_BLACKLISTED.equals(result)) {
				status=false;
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return status;
	}
	
	public boolean isSourceBlackListed(String sourceId){
		DBPreparedQuery dq = null;
		boolean status=false;
		String result;
		try {
			dq = new DBPreparedQuery("dIsSourceBlacklisted");
			dq.setString(1, sourceId);
			result = dq.getStringResult();
			if(AdminConstants.SOURCE_BLACKLISTED.equals(result)){
				status = true;
			}else if(AdminConstants.SOURCE_NOT_BLACKLISTED.equals(result)) {
				status=false;
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return status;
	}
	
	public Map<String,DateTimePattern> getSystemDateTimePatterns() {
		DBPreparedQuery dq = null;
		Map<String,DateTimePattern> systemDateTimePatterns 	= null;
		List<SimpleDataObject> sysDateTimePatternsSdo 		= null;
		DateTimePattern dateTimePattern						= null;
		try {
			dq = new DBPreparedQuery("dAdminManager_GetSystemDateTimePatterns");
			sysDateTimePatternsSdo = dq.getResult();
			if(sysDateTimePatternsSdo!=null){
				systemDateTimePatterns = new HashMap<String, DateTimePattern>();
				for (SimpleDataObject sdo : sysDateTimePatternsSdo) {
					dateTimePattern = new DateTimePattern(sdo.getString("patternId"),
										sdo.getString("patternValue"),sdo.getString("dbPatternValue"),sdo.getInt("patternType"));
					systemDateTimePatterns.put(sdo.getString("patternId"), dateTimePattern);
				}
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return systemDateTimePatterns;
	}
	
	public SourceData getCandidatePortalSource() {
		DBPreparedQuery dq = null;
		SourceData sourceDetail =  null;
		try {
			dq = new DBPreparedQuery("dAdminManager_GetCompanyWebSiteSource");
			dq.setString(1, AdminConstants.SOURCE_CATEGORY_CANDIDATE_PORTAL);
			sourceDetail = (SourceData)dq.getSingleObjectResult();
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return sourceDetail;
	}
	
	public String getEmployeeSourceUserId(String userSourceId) {
		DBPreparedQuery dq = null;
		String userId = "";
		try {
			dq = new DBPreparedQuery("dAdminManager_GetEmployeeSourceUserId");
			dq.setString(1, userSourceId);
			int id = dq.getIntResult();
			userId = String.valueOf(id);
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return userId;
	}

}