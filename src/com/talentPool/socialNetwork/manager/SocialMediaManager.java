package com.talentPool.socialNetwork.manager;

import java.io.StringWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.db.DBPreparedQuery;
import com.talentPool.common.db.DBTransaction;
import com.talentPool.common.db.SimpleDataObject;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.properties.TPApplicationProperties;
import com.talentPool.common.utils.Utils;
import com.talentPool.common.xmlutils.XMLWriter;
import com.talentPool.notifier.TemplateConstants;
import com.talentPool.notifier.dataobject.TemplateData;
import com.talentPool.notifier.manager.TemplateManager;
import com.talentPool.notifier.manager.VelocityManager;
import com.talentPool.notifier.utils.TemplateUtils;
import com.talentPool.socialNetwork.dataobject.Person;
import com.talentPool.socialNetwork.dataobject.SocialSettingsData;
import com.talentPool.socialNetwork.utils.SocialMediaUtils;

/**
 * @author SiddharthK
 *
 */
public class SocialMediaManager {
	
	/**
	 * @param personId
	 * @return
	 */
	public Person getPersonDetails(String personId){
		Person person = null;
		String personUrl = Utils.buildSocialURL(TPApplicationProperties.getProperty("person.profile.url"));
		personUrl = personUrl + personId;
		person = SocialMediaUtils.fetchCandidateProfile(personUrl);
		return person;
	}
	
	/**
	 * @param oAuthToken
	 * @param socialMediaType
	 * @return
	 */
	public Person getApplicantDetails(String oAuthToken, String socialMediaType){
		Person person = null;
		String candidateProfileUrl = Utils.buildSocialURL(TPApplicationProperties.getProperty("candidate.profile.url"));
		candidateProfileUrl = candidateProfileUrl + oAuthToken + "&socialMediaType=" + socialMediaType;
		person = SocialMediaUtils.fetchCandidateProfile(candidateProfileUrl);
		return person;
	}

	/**
	 * @param userId
	 * @return status of oauthtoken availability for a user
	 */
	@SuppressWarnings("unchecked")
	public Map<String,String> getTokenForUser(String userId) {
		DBPreparedQuery dq = null;
		int cnt=1;
		ArrayList<SocialSettingsData> result = new ArrayList<SocialSettingsData>();
		Map<String , String> tokenMap = new HashMap<String,String>();
		try {
			dq= new DBPreparedQuery("dSocialSettingsManager_getOAuthToken");
			dq.setString(cnt++, userId);
			result=dq.getResult();
			for(SocialSettingsData sd:result){
				tokenMap.put(sd.getSourceId(), sd.getToken());
			}
			
		} catch (SQLException e) {
			TPLogger.getLogger().error("Unable to get token ", e);
		}finally{
			if(dq!=null){
				dq.releaseConnection();
			}
		}
		return tokenMap;
	}
	
	/**
	 * @param userId
	 * @return
	 */
	public String checkTokenForUser(String userId) {
		DBPreparedQuery dq = null;
		int cnt=1;
		String result =null;
		try {
			dq= new DBPreparedQuery("dSocialSettingsManager_checkOAuthToken");
			dq.setString(cnt++, userId);
			result=dq.getStringResult();
			
		} catch (SQLException e) {
			TPLogger.getLogger().error("Unable to get token ", e);
		}finally{
			if(dq!=null){
				dq.releaseConnection();
			}
		}
		return result;
	}
	
	/**
	 * @param userId
	 * @param informationShared
	 * @return
	 */
	public static boolean checkTokenAndInformationSharedForUser(String userId, String graphStatus) {
		DBPreparedQuery dq = null;
		int cnt=1;
		boolean infoShared = false;
		String result =null;
		try {
			dq= new DBPreparedQuery("dSocialSettingsManager_checkOAuthTokenAndInformationShared");
			dq.setString(cnt++, userId);
			dq.setString(cnt++, graphStatus);
			result=dq.getStringResult();
			if (!Utils.isBlankOrNull(result)){
				infoShared = true;
			}
			
		} catch (SQLException e) {
			TPLogger.getLogger().error("Unable to get token ", e);
		}finally{
			if(dq!=null){
				dq.releaseConnection();
			}
		}
		return infoShared;
	}
	
	
	/**
	 * @param userId
	 * @param graphStatus
	 * @return
	 */
	public String checkIfUserPresentInGraph(String userId, String graphStatus){
		DBPreparedQuery dq = null;
		String result =null;
		try {
			dq= new DBPreparedQuery("dGetActivePersonId");
			dq.setString(1, userId);
			dq.setString(2, graphStatus);
			SimpleDataObject sdo = (SimpleDataObject) dq.getSingleObjectResult();
			if (sdo != null)
				result = sdo.getString("personId");
		} catch (SQLException e) {
			TPLogger.getLogger().error("Unable to get token ", e);
		}finally{
			if(dq!=null){
				dq.releaseConnection();
			}
		}
		return result;
	}
	
	/**
	 * @param userId
	 * @param graphStatus
	 * @return
	 */
	public String addUserForGraphUpload(String userId, String graphStatus){
		DBPreparedQuery dq = null;
		String result =null;
		DBTransaction trans = null;
		try {
			dq= new DBPreparedQuery("dGetPersonId");
			dq.setString(1, userId);
			SimpleDataObject sdo = (SimpleDataObject) dq.getSingleObjectResult();
			if (sdo != null)
				result = sdo.getString("personId");
			
			if (Utils.isBlankOrNull(result)){
				trans = new DBTransaction();
				dq= new DBPreparedQuery("dAddUserForGraphUpload",trans);
				dq.setString(1, userId);
				dq.setString(2, graphStatus);
				dq.execute();
				
				dq = new DBPreparedQuery("dFetchLastInsertID",trans);
				result = dq.getIdResult();
			//	System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX:-"+result);
				trans.commit();
			}
			
		} catch (SQLException e) {
			TPLogger.getLogger().error("Unable to get person Id ", e);
		}finally{
			if(dq!=null){
				dq.releaseConnection();
			}
		}
		return result;
	}

	/**
	 * @param templateCode
	 * @param positionId
	 * @return position Details for social media publish
	 */
	public SimpleDataObject  getPositionDetails(String templateCode, String positionId){
		TemplateManager templateManager = new TemplateManager();
		VelocityManager velocityManager = new VelocityManager(); 
		HashMap<String,String> keyValMap	= null;
		TemplateData templateData;
		SimpleDataObject sdo = new SimpleDataObject();
		try {
			templateData = templateManager.getTemplateData(templateCode);
			String contentVM 	= templateData.getTemplateContentFile();
			String titleVM 	= templateData.getTemplateSubjectFile();
			String totalContent = velocityManager.getContent(contentVM);
			String totalTitle = velocityManager.getContent(titleVM);
			String positionStr= null;
			String contentStr = null;
			if (TemplateUtils.isTypeExist(templateData.getTemplateVariableIds(), TemplateConstants.TEMPLATE_VAR_TYPE_POSITIONS)) {
				positionStr = templateManager.getPositionStr(positionId, true);
				contentStr = TemplateUtils.appndToToken(positionStr,contentStr);
			}
			keyValMap = TemplateUtils.getKeyValueMap(templateData.getTemplateVariables(), contentStr);
			totalContent = velocityManager.handleContent(totalContent, keyValMap);
			totalTitle = velocityManager.handleContent(totalTitle, keyValMap);
			sdo.setAttribute("title", totalTitle);
			sdo.setAttribute("content", totalContent);
		} catch (Exception e) {
			TPLogger.getLogger().error("Error getting position details for template xml ", e);
		}		
		return sdo;
		
	}
	
	/**
	 * @param templateCode
	 * @param subject
	 * @param content
	 * @return xml for template
	 */
	public String getTemplateXml(String templateCode, String subject, String content) {
		StringWriter sWr = new StringWriter();
		XMLWriter wr = new XMLWriter(sWr);
		try {
			wr.startDocument();
			wr.startElement("template");
			wr.startElement("templateCode");
			wr.characters(wr.doubleEscape(templateCode));
			wr.endElement("templateCode");
			wr.startElement("subject");
			wr.characters(wr.doubleEscape(subject));
			wr.endElement("subject");
			wr.startElement("content");
			wr.characters(wr.doubleEscape(content));
			wr.endElement("content");
			wr.endElement("template");
			wr.endDocument();
		} catch (Exception e) {
			TPLogger.getLogger().error("error while creating xml file for template", e);
		}
		return sWr.getBuffer().toString();
	}

	/**
	 * @param oAuthToken
	 * @param sourceId
	 * @return uuid
	 */
	public String generateUuid(String oAuthToken, String sourceId) {
		String uuid = null;
		uuid = UUID.randomUUID().toString();
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dSocialSettingsManager_addUuidOAuth");
			int cnt = 1;
			dq.setString(cnt++, uuid);
			dq.setString(cnt++, oAuthToken);
			dq.setString(cnt++, sourceId);
			dq.execute();			
		} catch (SQLException e) {
			TPLogger.getLogger().error("Unable to add uuid token map", e);
			uuid = null;
		}finally{
			if(dq!=null){
				dq.releaseConnection();
			}
		}
		return uuid;
	}
	
	
	/**
	 * @param applicantId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<SimpleDataObject> getApplicantAuthToken(String applicantId){
		ArrayList<SimpleDataObject> result = new ArrayList<SimpleDataObject>();
		DBPreparedQuery dq= null;
		String [] dynParams = new String[1];
		dynParams[0] = applicantId; //comma separated ApplicantId
		try{
			dq = new DBPreparedQuery("dSocialSettingsManager_getApplicantOAuth",dynParams);
			result = dq.getResult();
		}catch(Exception e){
			TPLogger.getLogger().error("Error getting token for applicants ", e);
		}finally{
			if(dq!=null){
				dq.releaseConnection();
			}
		}
		return result;
	}
	
	/**
	 * @param personIds
	 * @return
	 */
	public String getUserAndJoinedFromPersonId(String personIds){
		DBPreparedQuery dq = null;
		String[] dynParams = new String[2];
		dynParams[0]=personIds;
		dynParams[1]=personIds;
		String result = null;
		try{
			dq = new DBPreparedQuery("dSocialSettingsManager_getUserAndJoined",dynParams);
			result = dq.getStringResult();
		}catch(Exception e){
			TPLogger.getLogger().error("Error getting users and joined candidates for personId ", e);
		}finally{
			if(dq!=null){
				dq.releaseConnection();
			}
		}
		return result;
	}

	/**
	 * @param sourceId
	 * @param userId
	 * @throws SQLException
	 */
	public void deleteTokenForUser(String sourceId, String userId) throws SQLException {
		DBPreparedQuery dq = null;
		DBTransaction trans = null;
		String[] dynParams = new String[1];
		int cnt = 1;
		try{
			trans = new DBTransaction();
			dq = new DBPreparedQuery("dDeleteTokenForUser");
			dq.setString(cnt++, userId);
			dq.setString(cnt++, sourceId);
			dq.execute();
			trans.commit();
		}catch (SQLException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			trans.rollback();
		} finally {
			if (dq != null) {
				dq.releaseTransaction(trans);
			}
		}
	}

	/**
	 * @param applicantId
	 * @return personId and token if it exists
	 */
	@SuppressWarnings("unchecked")
	public List<SimpleDataObject> getPersonIdForActiveApplicant(String applicantId) {
		DBPreparedQuery dq = null;
		int cnt = 1;
		List<SimpleDataObject> result = null;
		try{
			dq = new DBPreparedQuery("dGetPersonIdForActiveApplicants");
			dq.setString(cnt++, applicantId);
			//dq.setString(cnt++, SocialMediaConstants.GRAPH_UPLOAD_COMPLETE);
			result = dq.getResult();
		}catch(Exception e){
			TPLogger.getLogger().error(GlobalConstants.ERROR,e);
		}finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return result;
	}
}
