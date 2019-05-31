/**
 * VendorServiceSoapBindingImpl.java
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
import com.talentPool.applicant.manager.ApplicantManager;
import com.talentPool.common.CommonConstants;
import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.db.DBPreparedQuery;
import com.talentPool.common.db.SimpleDataObject;
import com.talentPool.common.properties.GlobalApplicationProperties;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.properties.TPApplicationProperties;
import com.talentPool.common.utils.Utils;
import com.talentPool.employeeservice.manager.EmployeeApplicantManager;
import com.talentPool.inbox.scheduler.AutoImportJob;
import com.talentPool.parser.SourceParser;
import com.talentPool.user.dataobject.LoginData;
import com.talentPool.user.manager.LoginManager;
import com.talentPool.user.manager.ModuleSet;
import com.talentPool.user.manager.UserManager;
import com.talentPool.vendorservice.dataobject.VactivityList;
import com.talentPool.vendorservice.dataobject.VapplicantData;
import com.talentPool.vendorservice.dataobject.VapplicantList;
import com.talentPool.vendorservice.dataobject.VcommunicationData;
import com.talentPool.vendorservice.dataobject.VcustomFieldDataList;
import com.talentPool.vendorservice.dataobject.VerrorData;
import com.talentPool.vendorservice.dataobject.VidsNames;
import com.talentPool.vendorservice.dataobject.VimportFieldList;
import com.talentPool.vendorservice.dataobject.VinboxData;
import com.talentPool.vendorservice.dataobject.VloginData;
import com.talentPool.vendorservice.dataobject.Vpagination;
import com.talentPool.vendorservice.dataobject.VpositionData;
import com.talentPool.vendorservice.manager.VendorActivityManager;
import com.talentPool.vendorservice.manager.VendorApplicantDuplicateManager;
import com.talentPool.vendorservice.manager.VendorApplicantManager;
import com.talentPool.vendorservice.manager.VendorBufferDataManager;
import com.talentPool.vendorservice.manager.VendorCommunicationManager;
import com.talentPool.vendorservice.manager.VendorLoginManager;
import com.talentPool.vendorservice.manager.VendorPositionManager;
import com.talentPool.user.UserConstants;
import com.talentPool.user.manager.SingleSignOnManager;
import com.talentPool.vendorservice.manager.VendorUserManager;
import com.talentPool.vendorservice.utils.VendorMarshaller;
import com.talentPool.vendorservice.utils.VendorUnMarshaller;

public class VendorServiceSoapBindingImpl implements com.talentPool.services.VendorService{
	
	public String isValidLogin(String userName, String password) throws RemoteException {
		String xmlFile = "";
		MessageContext context = AxisEngine.getCurrentMessageContext();
		Session session = context.getSession();
		VendorLoginManager vendorLoginManager = new VendorLoginManager();
		VloginData vloginData = vendorLoginManager.isValidLogin(userName, password, session);
		VendorMarshaller vendorMarshaller = new VendorMarshaller();
		if (vloginData != null) {
			if (vloginData.getUserStatus() == UserConstants.DEACTIVE) {
				xmlFile = vendorMarshaller.marshallDataWhenUserDisabled(UserConstants.DEACTIVE);
				return xmlFile;
			}
			if (!Utils.isBlankOrNull(vloginData.getUserName()) && Utils.isBlankOrNull(vloginData.getUserId())
					&& VendorLoginManager.USER_ATTEMPTS_MAP.containsKey(vloginData.getUserName())) {
				if (VendorLoginManager.USER_ATTEMPTS_MAP.get(vloginData.getUserName()).equals("3")) {
					VendorLoginManager.USER_ATTEMPTS_MAP.remove(vloginData.getUserName());
					xmlFile = vendorMarshaller.marshallDataWhenUserDisabled(UserConstants.ACTIVE);
					return xmlFile;
				}
				vloginData = null;
			}
		}
		xmlFile = vendorMarshaller.marshallLoginData(vloginData);
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

	public String getIdsAndNames(String type) throws RemoteException {
		String xmlFile = "";
		VendorBufferDataManager vendorBufferDataManager = new VendorBufferDataManager();
		VidsNames vidsNames = vendorBufferDataManager.getIdNamesData(type);
		VendorMarshaller vendorMarshaller = new VendorMarshaller();
		xmlFile = vendorMarshaller.marshallIdNames(vidsNames);
		return xmlFile;
	}

	public String getCustomFields() throws RemoteException {
		String xmlFile = "";
		VendorBufferDataManager vendorBufferDataManager = new VendorBufferDataManager();
		VcustomFieldDataList vcustomFieldDataList = vendorBufferDataManager.getCustomFieldList();
		VendorMarshaller vendorMarshaller = new VendorMarshaller();
		xmlFile = vendorMarshaller.marshallCustomFieldsList(vcustomFieldDataList);
		return xmlFile;
	}

	public String getInboxData() throws RemoteException {
		String xmlFile = "";
		VendorBufferDataManager vendorBufferDataManager = new VendorBufferDataManager();
		VinboxData vinboxData = vendorBufferDataManager.getInboxData();
		VendorMarshaller vendorMarshaller = new VendorMarshaller();
		xmlFile = vendorMarshaller.marshallInboxData(vinboxData);
		return xmlFile;
	}

	public String getPositions(String userId) throws RemoteException {
		String xmlFile = "";
		VendorPositionManager vendorPositionManager = new VendorPositionManager();
		List<SimpleDataObject> positions = vendorPositionManager.getPositionsOpenToVendor(userId);
		xmlFile = vendorPositionManager.getPositionXml(positions);
		return xmlFile;
	}

	public String getPositionDetails(String positionId) throws RemoteException {
		String xmlFile = "";
		VendorPositionManager vendorPositionManager = new VendorPositionManager();
		MessageContext context = AxisEngine.getCurrentMessageContext();
		Session session = context.getSession();
		String userId = (String) (session.get("userId"));		
		VpositionData vpositionData = vendorPositionManager.getPositionDetails(positionId, userId);
		VendorMarshaller vendorMarshaller = new VendorMarshaller();
		xmlFile = vendorMarshaller.marshallPositionData(vpositionData);
		return xmlFile;
	}

	public String getApplicants(boolean doShowApplicantsInProcess, String sortBy, String pageNo, int pageSize, String userId, String sourceId) throws RemoteException {
		String xmlFile = "";
		VendorApplicantManager vendorApplicantManager = new VendorApplicantManager();
		List<VapplicantData> applicants = vendorApplicantManager.getApplicantsForVendor(doShowApplicantsInProcess, sortBy, pageNo, pageSize, userId, sourceId);
		Vpagination pagination = vendorApplicantManager.getPaginationData(doShowApplicantsInProcess, pageNo, pageSize, userId, sourceId);
		VapplicantList vapplicants = new VapplicantList();
		vapplicants.setApplicants((ArrayList<VapplicantData>) applicants);
		vapplicants.setPagination(pagination);
		VendorMarshaller vendorMarshaller = new VendorMarshaller();
		xmlFile = vendorMarshaller.marshallApplicantListData(vapplicants);
		return xmlFile;
	}

	public String getApplicantDetails(String applicantId, String userId, String sourceId) throws RemoteException {
		String xmlFile = "";
		VendorApplicantManager vendorApplicantManager = new VendorApplicantManager();
		VapplicantData vapplicantData = vendorApplicantManager.getApplicantDisplayData(applicantId, userId, sourceId);
		VendorMarshaller vendorMarshaller = new VendorMarshaller();
		xmlFile = vendorMarshaller.marshallApplicantData(vapplicantData);
		return xmlFile;
	}

	public String getApplicantHistory(String applicantId, String userId, String sourceId) throws RemoteException {
		String xmlFile = "";
		VendorApplicantManager vendorApplicantManager = new VendorApplicantManager();
		ArrayList<SimpleDataObject> interactions = vendorApplicantManager.getApplicantInteractions(applicantId, userId, sourceId);
		xmlFile = vendorApplicantManager.getApplicantInteractionsXML(interactions);
		return xmlFile;
	}

	public String getCommunicationData(String applicantId, String communicationId, String communicationType) throws RemoteException {
		String xmlFile = "";
		VendorCommunicationManager vendorCommunicationManager = new VendorCommunicationManager();
		VcommunicationData vcommunicationData = vendorCommunicationManager.getCommunicationData(applicantId, communicationId, communicationType);
		VendorMarshaller vendorMarshaller = new VendorMarshaller();
		xmlFile = vendorMarshaller.marshallCommunicationData(vcommunicationData);
		return xmlFile;
	}

	public boolean setCommunication(String applicantId, String userId, String communicationId, String communicationType, String note) throws RemoteException {
		boolean success = false;
		VendorCommunicationManager vendorCommunicationManager = new VendorCommunicationManager();
		success = vendorCommunicationManager.setCommunication(applicantId, userId, communicationId, communicationType, note);
		return success;
	}

	public String getApplicantResume(String resumePath, String applicantId) throws RemoteException {
		String xmlFile = "";
		VendorApplicantManager vendorApplicantManager = new VendorApplicantManager();
		xmlFile = vendorApplicantManager.getApplicantHtmlResume(resumePath, applicantId);
		return xmlFile;
	}

	public String getActivities(String applicantId, String positionId, String noOfDays, String pageNo, int pageSize, String vendorId, String sourceId) throws RemoteException {
		String xmlFile = "";
		VendorActivityManager vendorActivityManager = new VendorActivityManager();
		VactivityList vactivityList = vendorActivityManager.getActivityList(applicantId, positionId, noOfDays, pageNo, pageSize, vendorId, sourceId);
		VendorMarshaller vendorMarshaller = new VendorMarshaller();
		xmlFile = vendorMarshaller.marshallActivityListData(vactivityList);
		return xmlFile;
	}

	public boolean isDuplicate(String sourceName, String userId, String userFirstName, String positionId, String positionName, HashMap nameValues) throws RemoteException {
		boolean duplicate = false;
		VendorApplicantDuplicateManager vendorApplicantDuplicateManager = new VendorApplicantDuplicateManager();
		duplicate = vendorApplicantDuplicateManager.isDuplicate(nameValues);
		if (duplicate) {
			vendorApplicantDuplicateManager.sendVendorDuplicateUploadAttemptEmail(sourceName, userId, userFirstName, positionId, positionName, nameValues);
		}
		return duplicate;
	}

	public String changePassword(String userId, String oldPassword, String newPassword, String confirmNewPassword, String securityQuestionId, String securityAnswer, boolean forgotPassword) throws RemoteException {
		String xmlFile = "";
		VendorUserManager vendorUserManager = new VendorUserManager();
		VerrorData verrorData = vendorUserManager.changePassword(userId, oldPassword, newPassword, confirmNewPassword,  securityQuestionId, securityAnswer, forgotPassword);
		if (verrorData.getErrors().size() > 0) {
			VendorMarshaller vendorMarshaller = new VendorMarshaller();
			xmlFile = vendorMarshaller.marshallErrorData(verrorData);
		} else {
			xmlFile = "true";
		}
		return xmlFile;
	}

	public String getImportFieldList() throws RemoteException {
		String xmlFile = "";
		VendorBufferDataManager vendorBufferDataManager = new VendorBufferDataManager();
		VimportFieldList vimportFieldList = vendorBufferDataManager.getImportFieldData();
		VendorMarshaller vendorMarshaller = new VendorMarshaller();
		xmlFile = vendorMarshaller.marshallImportFieldList(vimportFieldList);
		return xmlFile;
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

	public boolean sendConfirmationLink(String emailAddress,String requestUri) throws RemoteException {
		boolean success = false;
		UserManager userManager = new UserManager();
		success = userManager.saveUUIDandSendConfirmationLink(emailAddress, CommonConstants.PRODUCT_TVENDOR ,requestUri);
		return success;
	}

	public String getUserIfValidUUID(String uuid) throws RemoteException {
		String xmlFile = "";
		UserManager userManager = new UserManager();
		String userId = userManager.getUserIdForUuid(uuid);
		VendorMarshaller vendorMarshaller = new VendorMarshaller();
		VerrorData verrorData = new VerrorData(); 
		if(!Utils.isBlankOrNull(userId)) {
			VendorLoginManager vendorLoginManager = new VendorLoginManager();
			VloginData vloginData = vendorLoginManager.getUserById(userId);
			if (vloginData != null) {
				xmlFile = vendorMarshaller.marshallLoginData(vloginData);
			}
		} else {
			verrorData.putError("forgot_password.error.invalid_link");
			xmlFile = vendorMarshaller.marshallErrorData(verrorData);
		}
		return xmlFile;
	}
	private String getSourceId(String content) {
		try {
			SourceParser sourceParser = new SourceParser();
			String sourceId = sourceParser.getParsedSourceIdFromMaster(content);
			return sourceId;
		} catch (Exception e) {
			TPLogger.getLogger().error("Error", e);
		}
		return null;
	}
	private LoginData getVendorData(String userName) {
		LoginData loginData = null;
		try {
			LoginManager loginManager = new LoginManager();
			loginData = loginManager.getLoginDataFor(userName.trim());
		} catch (Exception e) {
			TPLogger.getLogger().error("ERROR in auto import, login name: " + userName + " does not exists");
		}
		return loginData;
	}
	public String addApplicant(String xml) throws RemoteException {
		String xmlFile = "";
		String sourceId=null;
		VapplicantData vapplicantData = null;
		VendorApplicantManager vendorApplicantManager = new VendorApplicantManager();
		VendorUnMarshaller vendorUnMarshaller= new VendorUnMarshaller();
		VendorMarshaller vendorMarshaller = new VendorMarshaller();
		ApplicantManager applicantManager=new ApplicantManager();
		String positionId=null;
		String userName="";
		String sourceTitle =null;
		int perVendorCvLimit=0;
		int globalResumeUploadLimit=Integer.parseInt(GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_VENDOR_RESUME_UPLOAD_PER_POSITION));
		int uploadedResumeByvendor=0;
		try {
			vapplicantData=vendorUnMarshaller.unMarshallApplicantData(xml);
			ApplicantData aData = vendorApplicantManager.convertVApplicantDataToApplicantData(vapplicantData);
			AutoImportJob aij = new AutoImportJob();
			positionId=aData.getApplicantPositionId();
			userName=vapplicantData.getUserName();
			sourceTitle = aData.getApplicantSourceTitle();
			sourceId=getSourceIdForvandor(userName,sourceTitle);
			//number of resume which has been uploaded by vendor
			uploadedResumeByvendor=applicantManager.getApplicantCount(sourceId, positionId);
			//Number of Resume which a vendor can upload
			perVendorCvLimit=getPerVendorCvLimit(sourceId,applicantManager);
			if(perVendorCvLimit>0){
				if(uploadedResumeByvendor<perVendorCvLimit)
					aij.addApplicantFromPortal(aData, vapplicantData.getNote(), vapplicantData.getUserName());
				else{
					VerrorData verrorData = new VerrorData(); 
					verrorData.putError("upload_applicant_Venor_crossLimit_error");
					xmlFile = vendorMarshaller.marshallErrorData(verrorData);
				}
			}
			//Number of Resume which any vendor can upload max
			else{
					if(uploadedResumeByvendor<globalResumeUploadLimit){
						aij.addApplicantFromPortal(aData, vapplicantData.getNote(), vapplicantData.getUserName());
					}
					else{
						VerrorData verrorData = new VerrorData(); 
						verrorData.putError("upload_applicant_Venor_crossLimit_error");
						xmlFile = vendorMarshaller.marshallErrorData(verrorData);
					}
			}
			
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while uploading applicant from vendor", e);
			VerrorData verrorData = new VerrorData(); 
			verrorData.putError(e.getMessage());
			xmlFile = vendorMarshaller.marshallErrorData(verrorData);
		}
		return xmlFile;
	}
	public int getPerVendorCvLimit(String sourceId,ApplicantManager applicantManager){
		int perVendorCvLimit=0;
		String cvLimit="";
		cvLimit=applicantManager.getVendorCvLimit(sourceId);
		if(!Utils.isBlankOrNull(cvLimit)){
			if(Utils.isNumeric(cvLimit)){
				perVendorCvLimit=Integer.parseInt(cvLimit);
			}
		}
		return perVendorCvLimit;
	}
	public String getSourceIdForvandor(String userName,String sourceTitle){
		String sourceId=null;
		LoginData vendorLoginData = null;
		if (Utils.isBlankOrNull(userName)) {
			sourceId = getSourceId(sourceTitle);
		} else {
			vendorLoginData = getVendorData(userName);
			if (vendorLoginData != null) {
				sourceId = vendorLoginData.getUserSourceId();
			}
		}
		return sourceId;
	}
	public boolean checkVendorCvUploadValidity(String source,String positionId){
		boolean canUpload=false;
		
		return canUpload;
	}
	
	public boolean isModuleRChilliIntegration() throws RemoteException {
		if (ModuleSet.isMODULE_RCHILLI_INTEGRATION()&& "1".equals(TPApplicationProperties.getProperty("is_rchilli_integration"))) {
			return true;
		} else {
			return false;
		}
	}
	
	public String getPositionFields() throws RemoteException {
		String xmlFile = "";
		VendorPositionManager vendorPositionManager = new VendorPositionManager();
		VimportFieldList vimportFieldList = vendorPositionManager.getPositionFieldList();
		VendorMarshaller vendorMarshaller = new VendorMarshaller();
		xmlFile = vendorMarshaller.marshallPositionFieldList(vimportFieldList);
		return xmlFile;
	}	
	
	public String getPositionsLocation(String location) throws RemoteException {
		
		String xmlFile = "";
		VendorPositionManager vendorPositionManager = new VendorPositionManager();
		ArrayList<SimpleDataObject> locations = vendorPositionManager.getpositionlocations(location);
		xmlFile = vendorPositionManager.getLocationsXML(locations,"location");
		
		return xmlFile;
	}
	
	public String getPositionsSearch(String positionTitle,String userId) throws RemoteException {
		
		String xmlFile = "";
		VendorPositionManager vendorPositionManager = new VendorPositionManager();
		ArrayList<SimpleDataObject> positions = vendorPositionManager.getPositionsSearch(positionTitle,userId);
		xmlFile = vendorPositionManager.getLocationsXML(positions,"positionTitle");
		
		return xmlFile;
	}
	
	public String getPositionsForFilter(String userId,String positionTitle,String location) throws RemoteException {
		String xmlFile = "";
		VendorPositionManager vendorPositionManager = new VendorPositionManager();
		List<SimpleDataObject> positions = vendorPositionManager.getPositionsOpenToVendorForFilter(userId,positionTitle,location);
		xmlFile = vendorPositionManager.getPositionXml(positions);
		return xmlFile;
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
}
