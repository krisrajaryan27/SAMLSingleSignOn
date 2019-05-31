/**
 * Created : Oct 24, 2013 4:03:20 PM
 * @author : Sachinm
 */
package com.talentPool.socialNetwork.action;

import java.util.ArrayList;

import com.talentPool.common.NavigationConstants;
import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.utils.Utils;
import com.talentPool.socialNetwork.constants.SocialMediaConstants;
import com.talentPool.socialNetwork.dataobject.SocialSettingsData;
import com.talentPool.socialNetwork.manager.SocialSettingsManager;
import com.talentPool.socialNetwork.utils.SocialMediaUtils;
import com.talentPool.struts2.common.TPActionSupport;

/**
 * @author Sachinm
 *
 */
public class SocialSettingsAction extends TPActionSupport {

	/**
	 * default serial version UID
	 */
	private static final long serialVersionUID = 1L;
	
	private String socialId;
	private String socialName;
	private String url;
	private String token;
	private ArrayList<SocialSettingsData> sources;
	private String selectedSources;
	private ArrayList<SocialSettingsData> groupList;
	private String linkedInClientId;
	private String linkedInClientSecret;
	private String facebookClientId;
	private String facebookClientSecret;
	private String linkedInCompanyIds;
	
	/**
	 * @return linkedInCompanyIds
	 */
	public String getLinkedInCompanyIds() {
		return linkedInCompanyIds;
	}

	/**
	 * @param linkedInCompanyIds
	 */
	public void setLinkedInCompanyIds(String linkedInCompanyIds) {
		this.linkedInCompanyIds = linkedInCompanyIds;
	}

	/**
	 * @return linkedInClientId
	 */
	public String getLinkedInClientId() {
		return linkedInClientId;
	}

	/**
	 * @param linkedInClientId
	 */
	public void setLinkedInClientId(String linkedInClientId) {
		this.linkedInClientId = linkedInClientId;
	}

	/**
	 * @return linkedInClientSecret
	 */
	public String getLinkedInClientSecret() {
		return linkedInClientSecret;
	}

	/**
	 * @param linkedInClientSecret
	 */
	public void setLinkedInClientSecret(String linkedInClientSecret) {
		this.linkedInClientSecret = linkedInClientSecret;
	}

	/**
	 * @return facebookClientId
	 */
	public String getFacebookClientId() {
		return facebookClientId;
	}

	/**
	 * @param facebookClientId
	 */
	public void setFacebookClientId(String facebookClientId) {
		this.facebookClientId = facebookClientId;
	}

	/**
	 * @return facebookClientSecret
	 */
	public String getFacebookClientSecret() {
		return facebookClientSecret;
	}

	/**
	 * @param facebookClientSecret
	 */
	public void setFacebookClientSecret(String facebookClientSecret) {
		this.facebookClientSecret = facebookClientSecret;
	}

	/**
	 * @return selected source type
	 */
	public String getSelectedSources() {
		return selectedSources;
	}

	/**
	 * @param selectedSources
	 */
	public void setSelectedSources(String selectedSources) {
		this.selectedSources = selectedSources;
	}

	
	/**
	 * @return list of sources
	 */
	public ArrayList<SocialSettingsData> getSources() {
		return sources;
	}

	/**
	 * @param sources
	 */
	public void setSources(ArrayList<SocialSettingsData> sources) {
		this.sources = sources;
	}


	/**
	 * @return token
	 */
	public String getToken() {
		return token;
	}

	/**
	 * @param token
	 */
	public void setToken(String token) {
		this.token = token;
	}

	/**
	 * @return groups list
	 */
	public ArrayList<SocialSettingsData> getGroupList() {
		return groupList;
	}

	/**
	 * @param groupList
	 */
	public void setGroupList(ArrayList<SocialSettingsData> groupList) {
		this.groupList = groupList;
	}

	/**
	 * @return the socialId
	 */
	public String getSocialId() {
		return socialId;
	}

	/**
	 * @param socialId the socialId to set
	 */
	public void setSocialId(String socialId) {
		this.socialId = socialId;
	}

	/**
	 * @return the socialName
	 */
	public String getSocialName() {
		return socialName;
	}

	/**
	 * @param socialName the socialName to set
	 */
	public void setSocialName(String socialName) {
		this.socialName = socialName;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return get Social settings
	 */
	public String socialSettings() {
		SocialSettingsManager socialSettingsManager = new SocialSettingsManager();
		try {
			ArrayList<SocialSettingsData> sources = socialSettingsManager.getAllMediaTypes();
			setSources(sources);
			setReqAttr("t", NavigationConstants.T_ADMIN);
			setReqAttr("mainPane", NavigationConstants.MAINPANE_ADMIN_SOCIAL_NETWORK);
			loadSocialCredentials();
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			return ERROR;
		}
		return SUCCESS;
	}
	
	/**
	 * @return save Social Settings
	 */
	public String saveSocialSettings() {
		SocialSettingsManager socialSettingsManager = new SocialSettingsManager();
		try {			
			SocialSettingsData ssd = new SocialSettingsData();
			ssd.setSocialId(getSocialId());
			ssd.setSocialName(getSocialName());
			ssd.setUrl(getUrl());
			ssd.setSocialType(getSelectedSources());
			socialSettingsManager.saveSocialSettings(ssd);
			setSources(sources);
			setReqAttr("t", NavigationConstants.T_ADMIN);
			setReqAttr("mainPane", NavigationConstants.MAINPANE_ADMIN_SOCIAL_NETWORK);
			
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			return ERROR;
		}
		return SUCCESS;
	}
	
	/**
	 *  loads Groups list
	 * @return 
	 */
	public String loadGroups(){
		SocialSettingsManager socialSettingsManager = new SocialSettingsManager();
		ArrayList<SocialSettingsData> groups = socialSettingsManager.loadGroupsOnSocialSettingsPage();
		setGroupList(groups);
		String xmlFile = new String();
		try {
			xmlFile = SocialMediaUtils.getXMLForGroupList(groups);
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			xmlFile = Utils.getXMLForError(null);
		}
		setReqAttr("xmlFile", xmlFile);
		return SUCCESS;
	}
	
	/**
	 * delets social media group
	 * @return
	 */
	public String deleteSocialGroup(){
		String rowId= getRequest().getParameter("rowId");
		String groupId= getRequest().getParameter("groupId");
		String impId = getRequest().getParameter("impId");
		SocialSettingsManager socialSettingsManager = new SocialSettingsManager();
		setReqAttr("rowId", rowId);
		try {
			socialSettingsManager.deleteSocialGroup(groupId, impId);
		} catch (Exception e) {
			TPLogger.getLogger().error("Error deleting social media group", e);
			return ERROR;
		}
		setReqAttr("xmlFile", "");
		return SUCCESS;
	}
	
	/**
	 * @return
	 */
	public String saveLinkedInSettings(){
		SocialSettingsManager manager = new SocialSettingsManager();
		try{
			if(manager.saveSocialCredentials(SocialMediaConstants.LINKEDIN,linkedInClientId.trim(),linkedInClientSecret.trim(),linkedInCompanyIds.trim())){
				SocialMediaUtils.setCredentials(SocialMediaConstants.LINKEDIN,linkedInClientId.trim(),linkedInClientSecret.trim(),linkedInCompanyIds.trim());
			}
		}catch(Exception e){
			TPLogger.getLogger().error("Error saving client id and secret for LinkedIn", e);
			return ERROR;
		}
		return SUCCESS;
	}
	
	/**
	 * @return
	 */
	public String saveFacebookSettings(){
		SocialSettingsManager manager = new SocialSettingsManager();
		try{
			if(manager.saveSocialCredentials(SocialMediaConstants.FACEBOOK,facebookClientId.trim(),facebookClientSecret.trim(),"")){
				SocialMediaUtils.setCredentials(SocialMediaConstants.FACEBOOK,facebookClientId.trim(),facebookClientSecret.trim(),"");
			}
		}catch(Exception e){
			TPLogger.getLogger().error("Error saving client id and secret for Facebook", e);
			return ERROR;
		}
		return SUCCESS;
	}
	
	/**
	 * 
	 */
	private void loadSocialCredentials(){
		SocialSettingsManager manager = new SocialSettingsManager();
		ArrayList<SocialSettingsData> ssds = new ArrayList<SocialSettingsData>();
		try{
			ssds=manager.loadSocialCredentials();
		}catch(Exception e){
			TPLogger.getLogger().error("Unable to retrieve Social Credentials", e);
		}
		for(SocialSettingsData ssd: ssds){
			if(SocialMediaConstants.LINKEDIN.equalsIgnoreCase(ssd.getSocialName())){
				setLinkedInClientId(ssd.getClientId());
				setLinkedInClientSecret(ssd.getClientSecret());
				setLinkedInCompanyIds(ssd.getCompanyId());
			}else if(SocialMediaConstants.FACEBOOK.equalsIgnoreCase(ssd.getSocialName())){
				setFacebookClientId(ssd.getClientId());
				setFacebookClientSecret(ssd.getClientSecret());
			}
		}
	}
}
