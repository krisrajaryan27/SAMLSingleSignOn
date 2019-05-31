/**
 * WebsiteServiceSoapBindingImpl.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.talentPool.services;

import java.rmi.RemoteException;
import java.util.TimeZone;

import com.talentPool.applicant.dataobject.ApplicantData;
import com.talentPool.applicant.manager.ApplicantManager;
import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.properties.TPApplicationProperties;
import com.talentPool.common.utils.Utils;
import com.talentPool.employeeservice.utils.WebsiteUnMarshaller;
import com.talentPool.inbox.scheduler.AutoImportJob;
import com.talentPool.jobPortals.manager.ApplicantJobPortalManager;
import com.talentPool.socialNetwork.dataobject.Person;
import com.talentPool.socialNetwork.manager.SocialMediaManager;
import com.talentPool.timeZone.TimeZoneUtils;
import com.talentPool.websiteservice.dataobject.WapplicantData;
import com.talentPool.websiteservice.dataobject.WcustomFieldDataList;
import com.talentPool.websiteservice.dataobject.WebsiteSettingsData;
import com.talentPool.websiteservice.dataobject.WerrorData;
import com.talentPool.websiteservice.dataobject.WidsNames;
import com.talentPool.websiteservice.dataobject.WimportFieldList;
import com.talentPool.websiteservice.dataobject.WinboxData;
import com.talentPool.websiteservice.dataobject.WitemData;
import com.talentPool.websiteservice.dataobject.WpositionData;
import com.talentPool.websiteservice.dataobject.WpositionFieldList;
import com.talentPool.websiteservice.dataobject.WpositionList;
import com.talentPool.websiteservice.manager.WebsiteApplicantManager;
import com.talentPool.websiteservice.manager.WebsiteBufferDataManager;
import com.talentPool.websiteservice.manager.WebsitePositionManager;
import com.talentPool.websiteservice.utils.WebsiteMarshaller;
import com.talentPool.websiteservice.dataobject.WpositionFilters;
import com.talentPool.websiteservice.dataobject.WsocialMediaSourceList;
import com.talentPool.user.UserConstants;
import com.talentPool.user.manager.ModuleSet;

public class WebsiteServiceSoapBindingImpl implements com.talentPool.services.WebsiteService{
    public String getIdsAndNames(String type) throws RemoteException {
		String xmlFile = "";
		WebsiteBufferDataManager websiteBufferDataManager = new WebsiteBufferDataManager();
		WidsNames widsNames = websiteBufferDataManager.getIdNamesData(type);
		WebsiteMarshaller websiteMarshaller = new WebsiteMarshaller();
		xmlFile = websiteMarshaller.marshallIdNames(widsNames);
		return xmlFile;
	}

	public String getCustomFields() throws RemoteException {
		String xmlFile = "";
		WebsiteBufferDataManager websiteBufferDataManager = new WebsiteBufferDataManager();
		WcustomFieldDataList wcustomFieldDataList = websiteBufferDataManager.getCustomFieldList();
		WebsiteMarshaller websiteMarshaller = new WebsiteMarshaller();
		xmlFile = websiteMarshaller.marshallCustomFieldsList(wcustomFieldDataList);
		return xmlFile;
	}
	
 	public java.lang.String getPositions(String skillId, String locationId, String deptId, String timeZone, String geographyId) throws java.rmi.RemoteException {
    	String xmlFile = "";
    	/*if (!Utils.isBlankOrNull(timeZone)){
    		TimeZone.setDefault(TimeZone.getTimeZone(TimeZoneUtils.getTimeZoneId(timeZone)));
    	}*/
		WebsitePositionManager websitePositionManager = new WebsitePositionManager();
		WpositionList wpositionList = websitePositionManager.getPositionsOpenToWebsite(skillId,locationId,deptId);
		WebsiteMarshaller websiteMarshaller = new WebsiteMarshaller();
		xmlFile = websiteMarshaller.marshallPositionList(wpositionList);
		return xmlFile;
    }

	public String getPositionDetails(String positionId, String timeZone) throws RemoteException {
		String xmlFile = "";
		/*if (!Utils.isBlankOrNull(timeZone)){
    		TimeZone.setDefault(TimeZone.getTimeZone(TimeZoneUtils.getTimeZoneId(timeZone)));
    	}*/
		WebsitePositionManager websitePositionManager = new WebsitePositionManager();
		WpositionData wpositionData = websitePositionManager.getPositionDetails(positionId);
		WebsiteMarshaller websiteMarshaller = new WebsiteMarshaller();
		xmlFile = websiteMarshaller.marshallPositionData(wpositionData);
		return xmlFile;
	}

	public String getImportFieldList() throws RemoteException {
		String xmlFile = "";
		WebsiteBufferDataManager websiteBufferDataManager = new WebsiteBufferDataManager();
		WimportFieldList wimportFieldList = websiteBufferDataManager.getImportFieldData();
		WebsiteMarshaller websiteMarshaller = new WebsiteMarshaller();
		xmlFile = websiteMarshaller.marshallImportFieldList(wimportFieldList);
		return xmlFile;
	}
	
	 public String getItem(String type) throws RemoteException {
    	String xmlFile = "";
    	WebsiteBufferDataManager websiteBufferDataManager = new WebsiteBufferDataManager();
		WitemData witemData = websiteBufferDataManager.getItem(type);
		WebsiteMarshaller websiteMarshaller = new WebsiteMarshaller();
		xmlFile = websiteMarshaller.marshallItemData(witemData);
		return xmlFile;
    }
	
	public String getInboxData() throws RemoteException {
		String xmlFile = "";
		WebsiteBufferDataManager websiteBufferDataManager = new WebsiteBufferDataManager();
		WinboxData winboxData = websiteBufferDataManager.getInboxData();
		WebsiteMarshaller websiteMarshaller = new WebsiteMarshaller();
		xmlFile = websiteMarshaller.marshallInboxData(winboxData);
		return xmlFile;
	}
	
	public String getPositionFieldList() throws RemoteException {
		String xmlFile = "";
		WebsiteBufferDataManager websiteBufferDataManager = new WebsiteBufferDataManager();
		WpositionFieldList wpositionFieldList = websiteBufferDataManager.getPositionFieldData();
		WebsiteMarshaller websiteMarshaller = new WebsiteMarshaller();
		xmlFile = websiteMarshaller.marshallPositionFieldList(wpositionFieldList);
		return xmlFile;
	}
	
	public String getFieldsDataOnPositionList() throws RemoteException {
		String xmlFile = "";
		WebsiteBufferDataManager websiteBufferDataManager = new WebsiteBufferDataManager();
		WpositionFieldList wpositionFieldList = websiteBufferDataManager.getFieldsDataOnPositionList();
		WebsiteMarshaller websiteMarshaller = new WebsiteMarshaller();
		xmlFile = websiteMarshaller.marshallPositionFieldList(wpositionFieldList);
		return xmlFile;
	}
	
	public String getWebsiteSettings() throws RemoteException {
		String xmlFile = "";
		WebsiteBufferDataManager websiteBufferDataManager = new WebsiteBufferDataManager();
		WebsiteSettingsData websiteSettingsData = websiteBufferDataManager.getWebsiteSettings();
		WebsiteMarshaller websiteMarshaller = new WebsiteMarshaller();
		xmlFile = websiteMarshaller.marshallWebsiteSettingsData(websiteSettingsData);
		return xmlFile;
	}
	
	public String getPositionFilters() throws RemoteException {
		String xmlFile = "";
		WebsitePositionManager websitePositionManager = new WebsitePositionManager();
		WpositionFilters wPositionFilters=websitePositionManager.getPositionFilters();
		WebsiteMarshaller websiteMarshaller = new WebsiteMarshaller();
		xmlFile = websiteMarshaller.marshallPositionSkills(wPositionFilters);
		return xmlFile;
	}
	
	public void registerStub() throws RemoteException {
		// Empty method just to register a stub to session to add proper authentication
		return;
	}
	
	public String getSocialMediaSources() throws RemoteException {
		String xmlFile = "";
		WebsitePositionManager websitePositionManager = new WebsitePositionManager();
		WsocialMediaSourceList wsocialMediaSourceList=websitePositionManager.getSocialMediaSources();
		WebsiteMarshaller websiteMarshaller = new WebsiteMarshaller();
		xmlFile = websiteMarshaller.marshallSocialMediaData(wsocialMediaSourceList);
		return xmlFile;
	}
	
	public String fetchCandidateProfile(String oAuthToken, String source)throws RemoteException {
		String xmlFile = "";
		Person person = null;
		SocialMediaManager socialMediaManager = new SocialMediaManager();
		person = socialMediaManager.getApplicantDetails(oAuthToken, source);
		WapplicantData wapplicantData = new WapplicantData();
		WebsiteApplicantManager websiteApplicantManager = new WebsiteApplicantManager(); 
		wapplicantData = websiteApplicantManager.getWapplicantDataFromPerson(person);
		String uuid = socialMediaManager.generateUuid(oAuthToken, new ApplicantManager().getSourceId(source));
		wapplicantData.setUuid(uuid);
		WebsiteMarshaller websiteMarshaller = new WebsiteMarshaller();
		xmlFile = websiteMarshaller.marshallApplicantData(wapplicantData);
		return xmlFile;
	}
	
	
	public String addApplicant(String xml) throws RemoteException {
		String xmlFile = "";
		WebsiteUnMarshaller unmarshaller = new WebsiteUnMarshaller();
		WebsiteMarshaller websiteMarshaller = new WebsiteMarshaller();
		try {
			WapplicantData wapplicantData = unmarshaller.unmarshallApplicantData(xml);
			WebsiteApplicantManager websiteApplicantManager = new WebsiteApplicantManager();
			ApplicantData aData = websiteApplicantManager.convertWApplicantDataToApplicantData(wapplicantData);
			AutoImportJob aij = new AutoImportJob();
			aij.addApplicantFromPortal(aData, wapplicantData.getNote(), wapplicantData.getUserName());
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while uploading applicant from website", e);
			WerrorData werrorData = new WerrorData();
			werrorData.putError(e.getMessage());
			xmlFile = websiteMarshaller.marshallErrorData(werrorData);
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
	
	public boolean isModuleSocialNetwork() throws RemoteException {
		return ModuleSet.isMODULE_SOCIAL_NETWORK();
	}

	@Override
	public String addApplicantFromNaukri(String xml) throws RemoteException {
		String xmlFile = "";
		WebsiteUnMarshaller unmarshaller = new WebsiteUnMarshaller();
		WebsiteMarshaller websiteMarshaller = new WebsiteMarshaller();
		try {
			WapplicantData wapplicantData = unmarshaller.unmarshallApplicantData(xml);
			WebsiteApplicantManager websiteApplicantManager = new WebsiteApplicantManager();
			ApplicantData aData = websiteApplicantManager.convertWApplicantDataToApplicantData(wapplicantData);
			if(Utils.isBlankOrNull(aData.getUserId())){
				aData.setUserId(UserConstants.ADMIN_ID);
				//jobimportmanager uses originaldocpath instead of originalresumepath to pass the doc path
				aData.setApplicantOriginalDocPath(aData.getApplicantOriginalResumePath());
			}
			ApplicantJobPortalManager manager = new ApplicantJobPortalManager();
			manager.importApplicant(aData);
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while uploading applicant from website", e);
			WerrorData werrorData = new WerrorData();
			werrorData.putError(e.getMessage());
			xmlFile = websiteMarshaller.marshallErrorData(werrorData);
		}
		return xmlFile;
	}
	
	public String getImportFieldListForWebsite() throws RemoteException {
		String xmlFile = "";
		WebsiteBufferDataManager websiteBufferDataManager = new WebsiteBufferDataManager();
		WimportFieldList wimportFieldList = websiteBufferDataManager.getImportFieldDataForWebsite();
		WebsiteMarshaller websiteMarshaller = new WebsiteMarshaller();
		xmlFile = websiteMarshaller.marshallImportFieldList(wimportFieldList);
		return xmlFile;
	}
}
