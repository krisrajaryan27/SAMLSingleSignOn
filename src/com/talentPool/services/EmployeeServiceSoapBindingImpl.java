/**
 * EmployeeServiceSoapBindingImpl.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.talentPool.services;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.axis.AxisEngine;
import org.apache.axis.MessageContext;
import org.apache.axis.session.Session;

import com.talentPool.admin.manager.AdminManager;
import com.talentPool.applicant.dataobject.ApplicantData;
import com.talentPool.common.CommonConstants;
import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.db.SimpleDataObject;
import com.talentPool.common.properties.GlobalApplicationProperties;
import com.talentPool.common.properties.TPApplicationProperties;
import com.talentPool.common.utils.Utils;
import com.talentPool.employeeservice.dataobject.EPortalSettings;
import com.talentPool.employeeservice.dataobject.EapplicantData;
import com.talentPool.employeeservice.dataobject.EapplicantList;
import com.talentPool.employeeservice.dataobject.EpositionFilters;
import com.talentPool.employeeservice.dataobject.EcommunicationData;
import com.talentPool.employeeservice.dataobject.EcustomFieldDataList;
import com.talentPool.employeeservice.dataobject.EidsNames;
import com.talentPool.employeeservice.dataobject.EimportFieldList;
import com.talentPool.employeeservice.dataobject.EinboxData;
import com.talentPool.employeeservice.dataobject.EloginData;
import com.talentPool.employeeservice.dataobject.Epagination;
import com.talentPool.employeeservice.dataobject.EpositionData;
import com.talentPool.employeeservice.dataobject.EpositionList;
import com.talentPool.employeeservice.manager.EmployeeAccountManager;
import com.talentPool.employeeservice.manager.EmployeeAdminManager;
import com.talentPool.employeeservice.manager.EmployeeApplicantDuplicateManager;
import com.talentPool.employeeservice.manager.EmployeeApplicantManager;
import com.talentPool.employeeservice.manager.EmployeeBufferDataManager;
import com.talentPool.employeeservice.manager.EmployeeCommunicationManager;
import com.talentPool.employeeservice.manager.EmployeeLoginManager;
import com.talentPool.employeeservice.manager.EmployeePositionManager;
import com.talentPool.employeeservice.manager.EmployeeUserManager;
import com.talentPool.employeeservice.utils.EmployeeMarshaller;
import com.talentPool.employeeservice.utils.EmployeeUnMarshaller;
import com.talentPool.user.UserConstants;
import com.talentPool.user.dataobject.LoginData;
import com.talentPool.user.exception.EmailExistException;
import com.talentPool.user.exception.EmployeeCodeExistException;
import com.talentPool.user.exception.SourceExistException;
import com.talentPool.user.exception.SourceOrEmployeeCodeExistException;
import com.talentPool.user.exception.UsernameExistException;
import com.talentPool.user.manager.LoginManager;
import com.talentPool.user.manager.ModuleSet;
import com.talentPool.user.manager.SingleSignOnManager;
import com.talentPool.user.manager.UserManager;
import com.talentPool.employeeservice.dataobject.EerrorData;
import com.talentPool.inbox.scheduler.AutoImportJob;

public class EmployeeServiceSoapBindingImpl implements com.talentPool.services.EmployeeService{
	public String isValidLogin(String userName, String password) throws RemoteException {
		MessageContext context = AxisEngine.getCurrentMessageContext();
		Session session = context.getSession();
		String xmlFile = "";
		EmployeeLoginManager employeeLoginManager = new EmployeeLoginManager();
		EloginData eloginData = employeeLoginManager.isValidLogin(userName, password, session);
		EmployeeMarshaller employeeMarshaller = new EmployeeMarshaller();
		if (eloginData != null) {
			if (eloginData.getUserStatus() == UserConstants.DEACTIVE) {
				xmlFile = employeeMarshaller.marshallDataWhenUserDisabled(UserConstants.DEACTIVE);
				return xmlFile;
			}
			if (!Utils.isBlankOrNull(eloginData.getUserName()) && Utils.isBlankOrNull(eloginData.getUserId())
					&& EmployeeLoginManager.USER_ATTEMPTS_MAP.containsKey(eloginData.getUserName())) {
				if (EmployeeLoginManager.USER_ATTEMPTS_MAP.get(eloginData.getUserName()).equals("3")) {
					EmployeeLoginManager.USER_ATTEMPTS_MAP.remove(eloginData.getUserName());
					xmlFile = employeeMarshaller.marshallDataWhenUserDisabled(UserConstants.ACTIVE);
					return xmlFile;
				}
				eloginData = null;
			}
		}
		xmlFile = employeeMarshaller.marshallLoginData(eloginData);
		return xmlFile;
	}

	public void saveTimeZone(String userId, String timeZone){
		MessageContext context = AxisEngine.getCurrentMessageContext();
		Session session = context.getSession();
		//String userId = (String)session.get("userId");
		LoginManager loginManager = new LoginManager();
		LoginData loginData = loginManager.getUser(userId);
		loginData.setTimeZone(timeZone);
		AdminManager adminManager = new AdminManager();
		try {
			adminManager.updateUserAccount(userId, loginData);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		session.set("timeZoneId", timeZone);
	}
	
	public String createEmployeeAccount(String userName, String firstName, String lastName, String password, String emailAddress, String homePhone, String cellPhone, String employeeCode, String timeZone) throws RemoteException {
		String xmlFile = "";
		EloginData eloginData = new EloginData();
		EmployeeAccountManager employeeAccountManager = new EmployeeAccountManager();
		EmployeeMarshaller employeeMarshaller = new EmployeeMarshaller();
		try {
			eloginData = employeeAccountManager.createEmployeeAccount(userName, firstName, lastName, password, emailAddress, homePhone, cellPhone, employeeCode, timeZone);
			MessageContext context = AxisEngine.getCurrentMessageContext();
			Session session = context.getSession();
			session.set("timeZoneId", timeZone);
			xmlFile = employeeMarshaller.marshallRegistrationData(eloginData);
		} catch (EmailExistException eee) {			
			xmlFile = employeeMarshaller.marshallErrorData("employee_user.error.duplicate_email");
		} catch (UsernameExistException ee) {
			xmlFile = employeeMarshaller.marshallErrorData("employee_user.error.duplicate_user_name");
		} catch (EmployeeCodeExistException e){
			xmlFile = employeeMarshaller.marshallErrorData("employee_user.error.duplicate_employee_code");
		} catch (SourceExistException e){
			xmlFile = employeeMarshaller.marshallErrorData("employee_user.error.duplicate_source_name");
		} 
		return xmlFile;
	}

	public String updateEmployeeAccount(String userName, String firstName, String lastName, String emailAddress, String homePhone, String cellPhone, String employeeCode, String userId, String sourceId, String roleId, String timeZone) throws RemoteException {
		String xmlFile = "";
		EloginData eloginData = new EloginData();
		EmployeeAccountManager employeeAccountManager = new EmployeeAccountManager();
		EmployeeMarshaller employeeMarshaller = new EmployeeMarshaller();
		try {
			eloginData = employeeAccountManager.updateEmployeeAccount(userName, firstName, lastName, emailAddress, homePhone, cellPhone, employeeCode, userId, sourceId, roleId, timeZone);
			MessageContext context = AxisEngine.getCurrentMessageContext();
			Session session = context.getSession();
			session.set("timeZoneId", timeZone);
			xmlFile = employeeMarshaller.marshallRegistrationData(eloginData);
		} catch (EmailExistException eee) {
			xmlFile = employeeMarshaller.marshallErrorData("employee_user.error.duplicate_email");
		} catch (SourceOrEmployeeCodeExistException e) {
			xmlFile = employeeMarshaller.marshallErrorData("employee_user.error.duplicate_source_name_or_employee_code");
		} catch (SQLException e) {
			xmlFile = employeeMarshaller.marshallErrorData("employee_user.error.duplicate_user_name");
		} catch (Exception e) {
			xmlFile = employeeMarshaller.marshallErrorData("employee_user.error.duplicate_email");
		}
		return xmlFile;
	}

	public String getPositions(String userId, String sourceId, String skillId, String exp,
									String applyOrReferFilter, String sortBy,
										String pageNo, int pageSize) throws RemoteException {
		String xmlFile = "";
		EmployeePositionManager employeePositionManager = new EmployeePositionManager();
		List<EpositionData> positions = employeePositionManager.getPositionsOpenToEmployee(userId, sourceId, skillId, exp, applyOrReferFilter,  sortBy, pageNo, pageSize);
		Epagination pagination = employeePositionManager.getPositionPaginationData(userId, sourceId, skillId, exp, sortBy, pageNo, pageSize);
		EpositionList epositions = new EpositionList();
		epositions.setPositions((ArrayList<EpositionData>) positions);
		epositions.setPagination(pagination);
		EmployeeMarshaller employeeMarshaller = new EmployeeMarshaller();
		xmlFile = employeeMarshaller.marshallPositionListData(epositions);
		return xmlFile;
	}
	public String getPositionsSearched(String userId, String sourceId, String skillId, String exp,
			String applyOrReferFilter, String sortBy,
				String pageNo, int pageSize,String position,String location) throws RemoteException {
			String xmlFile = "";
			EmployeePositionManager employeePositionManager = new EmployeePositionManager();
			List<EpositionData> positions = employeePositionManager.getPositionsSearch(userId, sourceId, skillId, exp, applyOrReferFilter,  sortBy, pageNo, pageSize,position,location);
			Epagination pagination = employeePositionManager.getPositionPaginationData(userId, sourceId, skillId, exp, sortBy, pageNo, pageSize);
			EpositionList epositions = new EpositionList();
			epositions.setPositions((ArrayList<EpositionData>) positions);
			epositions.setPagination(pagination);
			EmployeeMarshaller employeeMarshaller = new EmployeeMarshaller();
			xmlFile = employeeMarshaller.marshallPositionListData(epositions);
			return xmlFile;
			}
	
	public String getPositionDetails(String positionId) throws RemoteException {
		String xmlFile = "";
		EmployeePositionManager employeePositionManager = new EmployeePositionManager();
		MessageContext context = AxisEngine.getCurrentMessageContext();
		Session session = context.getSession();
		String userId = (String) (session.get("userId"));
		EpositionData epositionData = employeePositionManager.getPositionDetails(positionId, userId);
		EmployeeMarshaller employeeMarshaller = new EmployeeMarshaller();
		xmlFile = employeeMarshaller.marshallPositionData(epositionData);
		return xmlFile;
	}

	public String getIdsAndNames(String type) throws RemoteException {
		String xmlFile = "";
		EmployeeBufferDataManager employeeBufferDataManager = new EmployeeBufferDataManager();
		EidsNames eidsNames = employeeBufferDataManager.getIdNamesData(type);
		EmployeeMarshaller employeeMarshaller = new EmployeeMarshaller();
		xmlFile = employeeMarshaller.marshallIdNames(eidsNames);
		return xmlFile;
	}

	public String getCustomFields() throws RemoteException {
		String xmlFile = "";
		EmployeeBufferDataManager employeeBufferDataManager = new EmployeeBufferDataManager();
		EcustomFieldDataList ecustomFieldDataList = employeeBufferDataManager.getCustomFieldList();
		EmployeeMarshaller employeeMarshaller = new EmployeeMarshaller();
		xmlFile = employeeMarshaller.marshallCustomFieldsList(ecustomFieldDataList);
		return xmlFile;
	}

	public String getInboxData() throws RemoteException {
		String xmlFile = "";
		EmployeeBufferDataManager employeeBufferDataManager = new EmployeeBufferDataManager();
		EinboxData einboxData = employeeBufferDataManager.getInboxData();
		EmployeeMarshaller employeeMarshaller = new EmployeeMarshaller();
		xmlFile = employeeMarshaller.marshallInboxData(einboxData);
		return xmlFile;
	}

	public String getApplicants(boolean doShowApplicantsInProcess, boolean doShowApplicantsJoined, boolean doShowApplicantsRejected, String sortBy, String pageNo, int pageSize, String userId, String sourceId,String isEmployeeApply, String position,String location) throws RemoteException {
		String xmlFile = "";
		EmployeeApplicantManager employeeApplicantManager = new EmployeeApplicantManager();
		List<EapplicantData> applicants =null;
		if(isEmployeeApply.equals("1")){
			applicants= employeeApplicantManager.getEmployeeDetails(doShowApplicantsInProcess, doShowApplicantsJoined, doShowApplicantsRejected, sortBy, pageNo, pageSize, userId, sourceId,isEmployeeApply,"");
			
		}
		else{
		applicants= employeeApplicantManager.getApplicantsForEmployee(doShowApplicantsInProcess, doShowApplicantsJoined, doShowApplicantsRejected, sortBy, pageNo, pageSize, userId, sourceId,isEmployeeApply,position,location);
		}
		Epagination pagination = employeeApplicantManager.getPaginationData(doShowApplicantsInProcess, doShowApplicantsJoined, doShowApplicantsRejected, pageNo, pageSize, userId, sourceId);
		EapplicantList eapplicants = new EapplicantList();
		eapplicants.setApplicants((ArrayList<EapplicantData>) applicants);
		eapplicants.setPagination(pagination);
		EmployeeMarshaller employeeMarshaller = new EmployeeMarshaller();
		xmlFile = employeeMarshaller.marshallApplicantListData(eapplicants);
		return xmlFile;
	}
	
	public String getEmployeeDetail(boolean doShowApplicantsInProcess, boolean doShowApplicantsJoined, boolean doShowApplicantsRejected, String sortBy, String pageNo, int pageSize, String userId, String sourceId,String isEmployeeApply,String positionTitle) throws RemoteException {
		String xmlFile = "";
		EmployeeApplicantManager employeeApplicantManager = new EmployeeApplicantManager();
		List<EapplicantData> applicants = employeeApplicantManager.getEmployeeApplicationDetails(doShowApplicantsInProcess, doShowApplicantsJoined, doShowApplicantsRejected, sortBy, pageNo, pageSize, userId, sourceId,isEmployeeApply,positionTitle);
		Epagination pagination = employeeApplicantManager.getPaginationData(doShowApplicantsInProcess, doShowApplicantsJoined, doShowApplicantsRejected, pageNo, pageSize, userId, sourceId);
		EapplicantList eapplicants = new EapplicantList();
		eapplicants.setApplicants((ArrayList<EapplicantData>) applicants);
		eapplicants.setPagination(pagination);
		EmployeeMarshaller employeeMarshaller = new EmployeeMarshaller();
		xmlFile = employeeMarshaller.marshallApplicantListData(eapplicants);
		return xmlFile;
	}
	
	public String getApplicantDetails(String applicantId, String userId, String sourceId) throws RemoteException {
		String xmlFile = "";
		EmployeeApplicantManager employeeApplicantManager = new EmployeeApplicantManager();
		EapplicantData eapplicantData = employeeApplicantManager.getApplicantDisplayData(applicantId, userId, sourceId);
		EmployeeMarshaller employeeMarshaller = new EmployeeMarshaller();
		xmlFile = employeeMarshaller.marshallApplicantData(eapplicantData);
		return xmlFile;
	}

	public String getApplicantHistory(String applicantId, String userId, String sourceId) throws RemoteException {
		String xmlFile = "";
		EmployeeApplicantManager employeeApplicantManager = new EmployeeApplicantManager();
		ArrayList<SimpleDataObject> interactions = employeeApplicantManager.getApplicantInteractions(applicantId, userId, sourceId);
		xmlFile = employeeApplicantManager.getApplicantInteractionsXML(interactions);
		return xmlFile;
	}

	public boolean isDuplicate(String sourceName, String userId, String userFirstName, String positionId, String positionName, HashMap nameValues) throws RemoteException {
		boolean duplicate = false;
		EmployeeApplicantDuplicateManager employeeApplicantDuplicateManager = new EmployeeApplicantDuplicateManager();
		duplicate = employeeApplicantDuplicateManager.isDuplicate(nameValues);
		if (duplicate) {
			employeeApplicantDuplicateManager.sendEmployeeDuplicateUploadAttemptEmail(sourceName, userId, userFirstName, positionId, positionName, nameValues);
		}
		return duplicate;
	}

	public String getApplicantResume(String resumePath, String applicantId) throws RemoteException {
		String xmlFile = "";
		EmployeeApplicantManager employeeApplicantManager = new EmployeeApplicantManager();
		xmlFile = employeeApplicantManager.getApplicantHtmlResume(resumePath, applicantId);
		return xmlFile;
	}

	public String getCommunicationData(String applicantId, String communicationId, String communicationType) throws RemoteException {
		String xmlFile = "";
		EmployeeCommunicationManager employeeCommunicationManager = new EmployeeCommunicationManager();
		EcommunicationData ecommunicationData = employeeCommunicationManager.getCommunicationData(applicantId, communicationId, communicationType);
		EmployeeMarshaller employeeMarshaller = new EmployeeMarshaller();
		xmlFile = employeeMarshaller.marshallCommunicationData(ecommunicationData);
		return xmlFile;
	}

	public boolean setCommunication(String applicantId, String userId, String communicationId, String communicationType, String note) throws RemoteException {
		boolean success = false;
		EmployeeCommunicationManager employeeCommunicationManager = new EmployeeCommunicationManager();
		success = employeeCommunicationManager.setCommunication(applicantId, userId, communicationId, communicationType, note);
		return success;
	}

	public boolean sendNewPassword(String emailAddress, String requestUri) throws RemoteException {
		boolean success = false;
		UserManager userManager = new UserManager();
		success = userManager.saveUUIDandSendConfirmationLink(emailAddress, CommonConstants.PRODUCT_TEMPLOYEE,requestUri);
		return success;
	}

	public String updateEmployeePassword(String userName, String userId, String userPassword, String oldPassword, String securityQuestionId, String securityAnswer, boolean forgotPassword) {
		String xmlFile = "";
		EmployeeAccountManager employeeAccountManager = new EmployeeAccountManager();
		EerrorData eErrorData = employeeAccountManager.updateEmployeePassword(userName, userId, userPassword, oldPassword,  securityQuestionId, securityAnswer, forgotPassword);
		if (eErrorData.getErrors().size() > 0) {
			EmployeeMarshaller employeeMarshaller = new EmployeeMarshaller();
			xmlFile = employeeMarshaller.marshallErrorData(eErrorData);
		} else {
			xmlFile = "true";
		}
		return xmlFile;
	}

	public String getImportFieldList() throws RemoteException {
		String xmlFile = "";
		EmployeeBufferDataManager employeeBufferDataManager = new EmployeeBufferDataManager();
		EimportFieldList eimportFieldList = employeeBufferDataManager.getImportFieldData();
		EmployeeMarshaller employeeMarshaller = new EmployeeMarshaller();
		xmlFile = employeeMarshaller.marshallImportFieldList(eimportFieldList);
		return xmlFile;
	}
	
	public String getAnnouncements() throws RemoteException {
		String xmlFile = "";
		EmployeePositionManager employeePositionManager = new EmployeePositionManager();
		xmlFile = employeePositionManager.getAnnouncements();
		return xmlFile;
	}
	
	public String getHrTeamEmailId() throws RemoteException {
		String xmlFile = "";
		EmployeeAdminManager employeeAdminManager = new EmployeeAdminManager();
		xmlFile = employeeAdminManager.getHrTeamEmailId();
		return xmlFile;
	}
	
	public String getPositionsScreenFiltersData() throws RemoteException {
		String xmlFile = "";
		EmployeePositionManager employeePositionManager = new EmployeePositionManager();
		EmployeeMarshaller employeeMarshaller = new EmployeeMarshaller();
		EpositionFilters ePositionFilters = employeePositionManager.getPositionScreenFilters();
		xmlFile = employeeMarshaller.marshallPositionScreenFilterData(ePositionFilters);
		return xmlFile;
	}
	
	public String getUserData(String userId) throws RemoteException {
		String xmlFile = "";
		EmployeeUserManager employeeUserManager = new EmployeeUserManager();
		EloginData eloginData = employeeUserManager.getUserData(userId);
		EmployeeMarshaller employeeMarshaller = new EmployeeMarshaller();
		xmlFile = employeeMarshaller.marshallLoginData(eloginData);
		return xmlFile;
	}
	
	public String getEPortalSettings() throws RemoteException {
		String xmlFile = "";
		EmployeeAdminManager employeeAdminManager = new EmployeeAdminManager();
		EPortalSettings ePortalSettings = employeeAdminManager.getEPortalSettings();
		EmployeeMarshaller employeeMarshaller = new EmployeeMarshaller();
		xmlFile = employeeMarshaller.marshallEPortalSettings(ePortalSettings);
		return xmlFile;
	}

	public boolean updateEmployeePortalProperties(
			HashMap employeePortalProperties, String updatedBy)
			throws RemoteException {
		EmployeeAdminManager employeeAdminManager = new EmployeeAdminManager();
		boolean isUpdated = employeeAdminManager.updateEmpoyeePortalProperties(employeePortalProperties,updatedBy);
		return isUpdated;
	}
	
	public boolean isUserLoggedInFromDifferentIP(String userId, String ipAddress) throws RemoteException {
		return SingleSignOnManager.isUserLoggedInFromDifferentIP(userId, ipAddress);
	}

	public void addUserIp(String userId, String ipAddress) throws RemoteException {
		SingleSignOnManager.addUserIp(userId, ipAddress);
	}
	
	public void removeUserIp(String userId) throws RemoteException{
		SingleSignOnManager.removeUserIp(userId);
	}
	
	public boolean isValidIPRequest(String userId, String ipAddress) throws java.rmi.RemoteException{
		return SingleSignOnManager.isValidIPRequest(userId, ipAddress);
	}
	
	public String getUserIfValidUUID(String uuid) throws RemoteException {
		String xmlFile = "";
		UserManager userManager = new UserManager();
		String userId = userManager.getUserIdForUuid(uuid);
		EmployeeMarshaller employeeMarshaller = new EmployeeMarshaller();
		EerrorData errorData = new EerrorData(); 
		if(!Utils.isBlankOrNull(userId)) {
			EmployeeLoginManager employeeLoginManager = new EmployeeLoginManager();
			EloginData eloginData = employeeLoginManager.getUserById(userId);
			if (eloginData != null) {
				xmlFile = employeeMarshaller.marshallLoginData(eloginData);
			}
		} else {
			errorData.putError("forgot_password.error.invalid_link");
			xmlFile = employeeMarshaller.marshallErrorData(errorData);
		}
		return xmlFile;
	}

	public String getPositionFields() throws RemoteException {
		String xmlFile = "";
		EmployeePositionManager employeePositionManager = new EmployeePositionManager();
		EimportFieldList eimportFieldList = employeePositionManager.getPositionFieldList();
		EmployeeMarshaller employeeMarshaller = new EmployeeMarshaller();
		xmlFile = employeeMarshaller.marshallPositionFieldList(eimportFieldList);
		return xmlFile;
	}
	
	public String addApplicant(String xml) throws RemoteException {
		String xmlFile = "";
		EmployeeApplicantManager employeeApplicantManager = new EmployeeApplicantManager();
		EmployeeUnMarshaller employeeUnMarshaller= new EmployeeUnMarshaller();
		EmployeeMarshaller employeeMarshaller = new EmployeeMarshaller();
		try {
			EapplicantData eapplicantData=employeeUnMarshaller.unMarshallApplicantData(xml);
			ApplicantData aData = employeeApplicantManager.convertEApplicantDataToApplicantData(eapplicantData);
			AutoImportJob aij = new AutoImportJob();
			aij.addApplicantFromPortal(aData, eapplicantData.getNote(), eapplicantData.getUserName());
		} catch (Exception e){
			TPLogger.getLogger().error("Error while uploading applicant from employee", e);
			//String errorMsg="Employee is in process ,hence can not apply for other position";
			xmlFile = employeeMarshaller.marshallErrorData(e.getMessage());
		}
		return xmlFile;
	}
	
	public boolean isModuleRChilliIntegration() throws RemoteException {
		if (ModuleSet.isMODULE_RCHILLI_INTEGRATION()&& "1".equals(TPApplicationProperties.getProperty("is_rchilli_integration"))) {
			return true;
		} else {
			return false;
		}
	}
	public boolean getApplicationProperty(String property) throws java.rmi.RemoteException{
		boolean result =false;
		String value=GlobalApplicationProperties.getProperty(property);
		if(!Utils.isBlankOrNull(value)){
			if(value.equals("0")){
				result =false;
			}
			if(value.equals("1")){
				result =true;
			}
		}
		return result;
	}
	
	public boolean getApplicationPropertyNew(String property) throws java.rmi.RemoteException{
		boolean result =false;
		String value=GlobalApplicationProperties.getProperty(property);
		if(!Utils.isBlankOrNull(value)){
			if(value.equals("0")){
				result =false;
			}
			if(value.equals("1")){
				result =true;
			}
		}
		return result;
	}
	
	public String getApplicantNames(String applicantName) throws RemoteException {
		String xmlFile = "";
		EmployeeApplicantManager employeeApplicantManager = new EmployeeApplicantManager();
		List<EapplicantData> applicants =null;
		applicants= employeeApplicantManager.getApplicantNames(applicantName);
		EapplicantList eapplicants = new EapplicantList();
		eapplicants.setApplicants((ArrayList<EapplicantData>) applicants);
	
		EmployeeMarshaller employeeMarshaller = new EmployeeMarshaller();
		xmlFile = employeeMarshaller.marshallApplicantListData(eapplicants);
		return xmlFile;
	}
	
	public String getPositionsForSearch(String position) throws RemoteException {
		String xmlFile = "";
		EmployeeApplicantManager employeeApplicantManager = new EmployeeApplicantManager();
		List<EpositionData> positions =null;
		positions= employeeApplicantManager.getPositions(position);
		EpositionList epositions = new EpositionList();
		epositions.setPositions((ArrayList<EpositionData>) positions);
	
		EmployeeMarshaller employeeMarshaller = new EmployeeMarshaller();
		xmlFile = employeeMarshaller.marshallPositionListData(epositions);
		return xmlFile;
	}
	
	public String getPositionsLocation(String location) throws RemoteException {
		
		String xmlFile = "";
		EmployeeApplicantManager employeeApplicantManager = new EmployeeApplicantManager();
		ArrayList<SimpleDataObject> locations = employeeApplicantManager.getpositionlocations(location);
		xmlFile = employeeApplicantManager.getLocationsXML(locations);
		return xmlFile;
	}

	//Added to get the User Details from EmailID by Krishna
	public String getUserDataFromEmailID(String emailID) throws RemoteException {
		MessageContext context = AxisEngine.getCurrentMessageContext();
		Session session = context.getSession();
		String xmlFile = "";
		EmployeeLoginManager employeeLoginManager = new EmployeeLoginManager();
		EloginData eloginData = employeeLoginManager.getUserDataFromUserName(emailID);
		EmployeeMarshaller employeeMarshaller = new EmployeeMarshaller();
		xmlFile = employeeMarshaller.marshallLoginData(eloginData);
		return xmlFile;
	}
	
}
