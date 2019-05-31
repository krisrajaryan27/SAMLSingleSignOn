package com.talentPool.socialNetwork.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.db.SimpleDataObject;
import com.talentPool.common.properties.GlobalApplicationProperties;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.properties.TPApplicationProperties;
import com.talentPool.common.properties.TPLabels;
import com.talentPool.common.utils.CommonUtils;
import com.talentPool.common.utils.DateUtils;
import com.talentPool.common.utils.Utils;
import com.talentPool.masters.dataobject.SourceData;
import com.talentPool.masters.manager.MastersManager;
import com.talentPool.notifier.manager.TemplateManager;
import com.talentPool.positions.dataobject.PositionData;
import com.talentPool.positions.manager.PositionManager;
import com.talentPool.socialNetwork.ITransferObject.IPostOn;
import com.talentPool.socialNetwork.ITransferObject.ISocialNetwork;
import com.talentPool.socialNetwork.ITransferObjectImpl.FacebookTO;
import com.talentPool.socialNetwork.ITransferObjectImpl.LinkedInTO;
import com.talentPool.socialNetwork.ITransferObjectImpl.PostOnGroup;
import com.talentPool.socialNetwork.ITransferObjectImpl.PostOnPage;
import com.talentPool.socialNetwork.constants.SocialMediaConstants;
import com.talentPool.socialNetwork.dataobject.SocialPostObject;
import com.talentPool.socialNetwork.dataobject.SocialPostTransferObject;
import com.talentPool.socialNetwork.dataobject.SocialSettingsData;
import com.talentPool.socialNetwork.manager.SocialMediaManager;
import com.talentPool.socialNetwork.manager.SocialSettingsManager;
import com.talentPool.socialNetwork.utils.SocialEventStack;
import com.talentPool.socialNetwork.utils.SocialMediaUtils;
import com.talentPool.struts2.common.TPActionSupport;


/**
 * @author SumeetS
 *
 */
@SuppressWarnings("serial")
public class PublishPositionAction extends TPActionSupport{

	private String publishOrUnPublish;
	private String positionId;
	private String publishType;
	private String socialMediaId;
	private String postContent;
	private String postTitle;
	private String socialId;
	private String templateCode;
	private String selectedGroupTitles;
	
	/**
	 * @return selectedGroupTitles
	 */
	public String getSelectedGroupTitles() {
		return selectedGroupTitles;
	}

	/**
	 * @param selectedGroupTitles
	 */
	public void setSelectedGroupTitles(String selectedGroupTitles) {
		this.selectedGroupTitles = selectedGroupTitles;
	}

	/**
	 * @return template code
	 */
	public String getTemplateCode() {
		return templateCode;
	}

	/**
	 * @param templateCode
	 */
	public void setTemplateCode(String templateCode) {
		this.templateCode = templateCode;
	}

	/**
	 * @return postContent
	 */
	public String getPostContent() {
		return postContent;
	}
	
	/**
	 * @param postContent
	 */
	public void setPostContent(String postContent) {
		this.postContent = postContent;
	}
	
	/**
	 * @return postTitle
	 */
	public String getPostTitle() {
		return postTitle;
	}
	
	/**
	 * @param postTitle
	 */
	public void setPostTitle(String postTitle) {
		this.postTitle = postTitle;
	}
	
	/**
	 * @return publishOrUnPublish
	 */
	public String getPublishOrUnPublish() {
		return publishOrUnPublish;
	}
	
	/**
	 * @param publishOrUnPublish
	 */
	public void setPublishOrUnPublish(String publishOrUnPublish) {
		this.publishOrUnPublish = publishOrUnPublish;
	}
	
	/**
	 * @return positionId
	 */
	public String getPositionId() {
		return positionId;
	}
	
	/**
	 * @param positionId
	 */
	public void setPositionId(String positionId) {
		this.positionId = positionId;
	}
	
	/**
	 * @return publishType
	 */
	public String getPublishType() {
		return publishType;
	}
	
	/**
	 * @param publishType
	 */
	public void setPublishType(String publishType) {
		this.publishType = publishType;
	}
	
	/**
	 * @return socialMediaId
	 */
	public String getSocialMediaId() {
		return socialMediaId;
	}
	
	/**
	 * @param socialMediaId
	 */
	public void setSocialMediaId(String socialMediaId) {
		this.socialMediaId = socialMediaId;
	}
	
	/**
	 * @return socialId
	 */
	public String getSocialId() {
		return socialId;
	}
	
	/**
	 * @param socialId
	 */
	public void setSocialId(String socialId) {
		this.socialId = socialId;
	}
	
	
	/**
	 * opens popup for social media publish
	 * @return
	 * @throws Exception
	 */
	public String publishPositionToSocialMedia() throws Exception {

		try {
			PositionManager positionManager = new PositionManager();
			PositionData positionData = positionManager.getPositionSummary(positionId);
			MastersManager mastersManager = new MastersManager();
			ArrayList<SourceData> allSources =mastersManager.getSourcesBySorceCategory(SocialMediaConstants.SOCIAL_MEDIA_SOURCE_CATEGORY_TYPE);
			Map<String, String> sourceDateMap = positionManager.getSourcesWithLastPostedDetails(positionId);
			String lastPostedDate = "";
			String userId= (String) getRequest().getSession(false).getAttribute("userId");
			SocialMediaManager socialMediaManager = new SocialMediaManager();
			String tokensCheck = socialMediaManager.checkTokenForUser(userId);
			for(SourceData sd : allSources) {
				if(tokensCheck!=null && tokensCheck.contains(sd.getSourceId())){
					sd.setTokenFlag("true");
				}else{
					sd.setTokenFlag("false");
				}
				lastPostedDate = sourceDateMap.get(sd.getSourceId());
				sd.setLastPostedDate(lastPostedDate);
			}
			getRequest().setAttribute("publishOrUnPublish", publishOrUnPublish);
			getRequest().setAttribute("vacancies", String.valueOf(positionData.getNoOfPositions()));
			getRequest().setAttribute("positionHireByDate", DateUtils.getSystemDateFormat(positionData.getPositionExpiryDate()));
			getRequest().setAttribute("sources",allSources);
			getRequest().setAttribute("positionId", positionId);
			getRequest().setAttribute("socialId", socialId);
			getRequest().setAttribute("postTitle",postTitle);
			getRequest().setAttribute("postContent",postContent);
			getRequest().setAttribute("templateCode",templateCode);
			//selectedSocialMediaTypeId is used to trace back the selected Social Media type after being redirected from LinkedIn oauth service.
			String selectedSocialMediaTypeId= getRequest().getParameter("selectedSocialMediaTypeId");
			getRequest().setAttribute("selectedSocialMediaTypeId",selectedSocialMediaTypeId );
			getTemplates();
			if(GlobalConstants.ENABLED.equals(GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_SHOW_POSITION_CODE))){
				getRequest().setAttribute("positionTitle", positionData.getPositionReferenceCode());
			} else {
				getRequest().setAttribute("positionTitle",positionData.getPositionTitle());
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			return ERROR;
		}
		return SUCCESS;
	}
	
	/**
	 * publishes Position to Social media
	 * @return
	 * @throws Exception
	 */
	public String publishPositionTo() throws Exception {
		PositionManager positionManager = new PositionManager();
		MastersManager mastersManager = new MastersManager();
		String userId= (String) getRequest().getSession(false).getAttribute("userId");
		PositionData positionData = positionManager.getPositionSummary(positionId);
		SocialMediaManager socialMediaManager = new SocialMediaManager();
		String lastPostedDate = "";
		ArrayList<SourceData> allSources =mastersManager.getSourcesBySorceCategory(SocialMediaConstants.SOCIAL_MEDIA_SOURCE_CATEGORY_TYPE);
		String tokensCheck = socialMediaManager.checkTokenForUser(userId);
		Map<String, String> sourceDateMap = positionManager.getSourcesWithLastPostedDetails(positionId);
		for(SourceData sd : allSources) {
			if(tokensCheck!=null && tokensCheck.contains(sd.getSourceId())){
				sd.setTokenFlag("true");
			}else{
				sd.setTokenFlag("false");
			}
			lastPostedDate = sourceDateMap.get(sd.getSourceId());
			sd.setLastPostedDate(lastPostedDate);
		}
		getRequest().setAttribute("publishOrUnPublish", publishOrUnPublish);
		getRequest().setAttribute("vacancies", String.valueOf(positionData.getNoOfPositions()));
		getRequest().setAttribute("positionHireByDate", DateUtils.getSystemDateFormat(positionData.getPositionExpiryDate()));
		getRequest().setAttribute("sources",allSources);
		getRequest().setAttribute("positionTitle", positionData.getPositionTitle());
		getRequest().setAttribute("positionId",positionData.getPositionId());
		getRequest().setAttribute("selectedSocialMediaTypeId", socialMediaId);
		getRequest().setAttribute("postTitle",postTitle);
		getRequest().setAttribute("postContent",postContent);
		getTemplates();
		getRequest().setAttribute("socialId", socialId);
		//social id coming from jsp is a combination of social id and implementation id separated by $$.
		String [] socialIds = socialId.split(",");
		String failedGroups = "";
	
		SocialPostObject postObject = new SocialPostObject.Builder(SocialMediaUtils.getTextContentFromHTML(postTitle),SocialMediaUtils.getTextContentFromHTML(postContent)).companyUrl(TPApplicationProperties.getProperty("social.post.companyURL")).imageUrl("").build();
		SocialPostTransferObject sTranferObject = new SocialPostTransferObject();
		Map<String,String> tokenMap = socialMediaManager.getTokenForUser(userId);
		ISocialNetwork linkedInTO = new LinkedInTO(postObject, tokenMap.get(CommonUtils.getSourceId(SocialMediaConstants.LINKEDIN)));
		ISocialNetwork facebookTO = new FacebookTO(postObject, tokenMap.get(CommonUtils.getSourceId(SocialMediaConstants.FACEBOOK)));
		Map<String,String> idVsTitleMap = new HashMap<String,String>();
		String [] selectedTitles = selectedGroupTitles.split(",");
		int cnt = 0;
		for(String socialId: socialIds){
			String temp[] = socialId.split("\\$\\$");
			String groupid = temp[0].trim();
			String implementationId = temp[1].trim();
			String idVsTitleEntry = "";
			if (SocialMediaConstants.LINKEDIN_GROUP.equals(implementationId)){
				linkedInTO.addGrpAndPages(new PostOnGroup(groupid));
				idVsTitleEntry += SocialMediaConstants.LINKEDIN;
			} else if (SocialMediaConstants.LINKEDIN_COMPANY.equals(implementationId)){
				linkedInTO.addGrpAndPages(new PostOnPage(groupid));
				idVsTitleEntry += SocialMediaConstants.LINKEDIN;
			}else if (SocialMediaConstants.FACEBOOK_GROUP.equals(implementationId)){
				facebookTO.addGrpAndPages(new PostOnGroup(groupid));
				idVsTitleEntry += SocialMediaConstants.FACEBOOK;
			}else if (SocialMediaConstants.FACEBOOK_COMPANY.equals(implementationId)){
				facebookTO.addGrpAndPages(new PostOnPage(groupid));
				idVsTitleEntry += SocialMediaConstants.FACEBOOK;
			}
			idVsTitleEntry += "-"+groupid;
			idVsTitleMap.put(idVsTitleEntry,selectedTitles[cnt++]);
		}
		if(!Utils.isBlankOrNull(linkedInTO.getOauthToken())){
			sTranferObject.addPostObjForSocialJobPosting(linkedInTO);
		}
		if(!Utils.isBlankOrNull(facebookTO.getOauthToken())){
			sTranferObject.addPostObjForSocialJobPosting(facebookTO);
		}

		sTranferObject = SocialMediaUtils.postOnSocialMedia(sTranferObject);
		
		for(ISocialNetwork socialType: sTranferObject.getPostObjectsForSocialJobPosting()){
			Map<String,String> responseMap = socialType.getResponsesForJobPostings();
			String srcName = "";
			if(socialType instanceof LinkedInTO){
				srcName+=SocialMediaConstants.LINKEDIN;
			}
			if(socialType instanceof FacebookTO){
				srcName+=SocialMediaConstants.FACEBOOK;
			}
			if(socialType.getOauthTokenExpiredFlag()){
				String srcId = CommonUtils.getSourceId(srcName);
				socialMediaManager.deleteTokenForUser(srcId, userId);
				getRequest().setAttribute("selectedSocialMediaTypeId", null);
				for(SourceData sd: allSources){
					if(srcId.equalsIgnoreCase(sd.getSourceId())){
						sd.setTokenFlag("false");
					}
				}
			}
			for(IPostOn postOn: socialType.getListOfGrpAndPagesToPostOn()){
				if(responseMap.containsKey(postOn.getId())){
					//for failed groups
					
					String key = "";
					key +=srcName+"-"+postOn.getId();
					failedGroups += idVsTitleMap.get(key) + "[" + responseMap.get(postOn.getId())+"], ";
				}else{
					//successfully posted
					positionManager.updatePositionPostingHistory(positionId, CommonUtils.getSourceId(srcName), postOn.getId());
				}
			}
		}
		if(!Utils.isBlankOrNull(failedGroups)){
			getRequest().setAttribute("saved", "0");
			getRequest().setAttribute("failedFor", failedGroups);
		}else{
			getRequest().setAttribute("saved", "1");
		}
		
		return SUCCESS;
	}
	
	/**
	 * @return jsArray of groups and companies.
	 */
	public String getGroupsAndCompaniesBySource(){
		SocialSettingsManager socialSettingsManager = new SocialSettingsManager();
		String xmlFile = "";
		ArrayList<SocialSettingsData> groups = null;
		if(!Utils.isBlankOrNull(socialMediaId)){
			 groups= socialSettingsManager.getCompaniesAndGroupBySource(socialMediaId);	
		}
		xmlFile = SocialMediaUtils.getXMLForGroupCompanyListPublish(groups);
		getRequest().setAttribute("xmlFile", xmlFile);
		return SUCCESS;
	}
	
	/**
	 * redirect to OAuth Generator Service
	 * @return
	 * @throws Exception
	 */
	public String redirectToOAuthGeneration() throws Exception {
		SimpleDataObject sdo = new SimpleDataObject();
		String serviceURL = Utils.buildSocialURL(TPApplicationProperties.getProperty("social.service.url"));
		String userId = (String) getRequest().getSession(false).getAttribute("userId");
		//String socialMediaType = SocialMediaManager.getSocialMediaName(getSocialMediaId());
		SourceData sData = new MastersManager().getSource(getSocialMediaId());
		String socialMediaType = (sData == null) ? "" : sData.getSourceTitle();
		String returnPath = (String) getRequest().getParameter("returnPath");
		returnPath= returnPath.replace("|", "&");
		sdo.setAttribute("socialMediaTypeId", getSocialMediaId());
		sdo.setAttribute("positionId", getPositionId());
		sdo.setAttribute("socialMediaType", socialMediaType);
		sdo.setAttribute("returnPath", returnPath);
		int eventId=SocialEventStack.getInstance().addEvent(sdo);
		returnPath=	SocialMediaUtils.getReturnPath(); 
		String forwardURL=serviceURL+"?id="+userId+"&returnPath="+returnPath+"&eventId="+eventId+"&socialMediaType="+socialMediaType;
		getRequest().setAttribute("forwardURL", forwardURL);
		return SUCCESS;
	}
	
	/**
	 * @return event data saved prior to leaving Talentpool to the Social Media API service
	 * @throws Exception
	 */
	public String extractEvent() throws Exception{
		String eventId= getRequest().getParameter("eventId");
		String error =getRequest().getParameter("error");
		if (!Utils.isBlankOrNull(error)){
			getRequest().setAttribute("positionId", "");
			getRequest().removeAttribute("positionId");
			return "errorSocial";
			//response.sendRedirect("/tw/position.do?mode=positionDetails&positionId="+positionId);
			//return null;
		}
		SimpleDataObject sdo = SocialEventStack.getInstance().getEvent(Integer.parseInt(eventId));
		String userId= getRequest().getParameter("id");
		String token = getRequest().getParameter("authToken");
		String socialMediaTypeId = sdo.getString("socialMediaTypeId");
		String positionId= sdo.getString("positionId");
		if(userId.equalsIgnoreCase((String) getRequest().getSession(false).getAttribute("userId"))){
			SocialSettingsData ssd = new SocialSettingsData();
			ssd.setUserId(userId);
			ssd.setToken(token);
			ssd.setSocialId(socialMediaTypeId);
			SocialSettingsManager manager = new SocialSettingsManager();
			manager.saveOAuthToken(ssd);
//			SocialMediaManager socialMediaManager = new SocialMediaManager();
//			SourceData sData = new MastersManager().getSource(socialMediaTypeId);
//
//			String checkIfPersonExisting = socialMediaManager.checkIfUserPresentInGraph(userId, SocialMediaConstants.GRAPH_UPLOAD_COMPLETE);
//			if (Utils.isBlankOrNull(checkIfPersonExisting)){
//			
//				Person person = socialMediaManager.getApplicantDetails(token, sData.getSourceTitle());
//				String personId = socialMediaManager.addUserForGraphUpload(userId, SocialMediaConstants.GRAPH_UPLOAD_PENDING);
//				person.setPersonId(personId);
//				
//				UploadPersonList uploadPersonList = new UploadPersonList();
//				uploadPersonList.getPerson().add(person);
//				UploadPersonList responseList = SocialMediaUtils.uploadPersonNodesIntoGraph(uploadPersonList);
//				ApplicantNodeUploadJob job = new ApplicantNodeUploadJob();
//				job.updatepersonUploadStatusInDB(responseList.getPerson().get(0).getPersonId());
//			} else {
//				//update person
//			}
		}else{
			return ERROR;
		}
		String returnPath= sdo.getString("returnPath");
		getRequest().setAttribute("returnPath", returnPath);
		getRequest().setAttribute("positionId", positionId);
		getRequest().setAttribute("socialMediaTypeId", socialMediaTypeId);
		return SUCCESS;
	}
	
	/**
	 * list of templates
	 * @throws Exception
	 */
	public void getTemplates() throws Exception{
		String userId =  (String) getRequest().getSession(false).getAttribute("userId");
		TemplateManager templateManager = new TemplateManager();
		ArrayList lst = templateManager.getSocialMediaTemplates(userId, false);
		ArrayList<String> templateIds = new ArrayList<String>();
		ArrayList<String> templateNames = new ArrayList<String>();
		CommonUtils.populateIdsAndNames(lst, templateIds, templateNames, "templateCode", "templateName", null);
		String templateJSArray = CommonUtils.getListJavaScriptArray(templateIds, templateNames);
		getRequest().setAttribute("jsArrayForTemplate", templateJSArray);
	}
	
	/**
	 * @return xml for templates for posting on social media
	 * @throws Exception
	 */
	public String getTemplateXML() throws Exception {
		SocialMediaManager socialMediaManager = new SocialMediaManager();
		String templateCode = (String) getRequest().getParameter("templateCode");
		String positionId = (String) getRequest().getParameter("positionId");
		SimpleDataObject sdo = socialMediaManager.getPositionDetails(templateCode, positionId);
		String xmlFile = socialMediaManager.getTemplateXml(templateCode, sdo.getString("title"), sdo.getString("content"));
		getRequest().setAttribute("xmlFile", xmlFile);
		return SUCCESS;
	}
	
}
