/**
 * Created : Oct 24, 2013 4:06:32 PM
 * @author : Sachinm
 */
package com.talentPool.socialNetwork.manager;

import java.sql.SQLException;
import java.util.ArrayList;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.db.DBPreparedQuery;
import com.talentPool.common.db.DBQuery;
import com.talentPool.common.db.DBTransaction;
import com.talentPool.socialNetwork.dataobject.SocialSettingsData;

/**
 * @author Sachinm
 * 
 */
public class SocialSettingsManager {

	/**
	 * @return list of social settings
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<SocialSettingsData> getSocialSettings() {
		DBQuery dq = null;
		ArrayList<SocialSettingsData> socialSettings = null;
		try {
			dq = new DBQuery("dSocialSettingsManager_GetSocialSettings");
			socialSettings = dq.getResult();
		} catch (SQLException e) {
			TPLogger.getLogger().error("Unable to get social settings ", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}

		return socialSettings;
	}

	/**
	 * @param ssd
	 * @throws Exception 
	 */
	public boolean saveSocialSettings(SocialSettingsData ssd) throws Exception {
		DBPreparedQuery dq = null;
		DBTransaction tran = null;
		int cnt = 1;
		try {
			tran = new DBTransaction();
			dq = new DBPreparedQuery("dSocialSettingsManager_saveSocialSettings",tran);
			dq.setString(cnt++, ssd.getSocialId().trim());
			dq.setString(cnt++, ssd.getSocialName().trim());
			dq.setString(cnt++, ssd.getUrl().trim());
			dq.setString(cnt++, ssd.getSocialType().trim());
			dq.execute();
			tran.commit();
		} catch (SQLException e) {
			TPLogger.getLogger().error("Unable to save social settings ", e);
			tran.rollback();
			throw e;
		} finally {
			if (dq != null) {
				dq.releaseTransaction(tran);
			}

		}
		return true;
	}
	
	/**
	 * @return group list to be displayed on admin social settings page
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<SocialSettingsData> loadGroupsOnSocialSettingsPage(){
		DBQuery dq = null;
		ArrayList<SocialSettingsData> result= null;
		try {
			dq = new DBQuery("dSocialSettingsManager_loadGroupsOnSocialSettings");
			result = dq.getResult();
		} catch (SQLException e) {
			TPLogger.getLogger().error("Unable to fetch  social network groups ", e);
		}finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return result;
		
		
	}
	
	/**
	 * @param sourceId
	 * @return LIST OF GROUPS AND COMPANIES IN ONE SOURCE
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<SocialSettingsData> getCompaniesAndGroupBySource(String sourceId){
		DBPreparedQuery dq = null;
		String[] dynParam = new String[1];
		dynParam[0] = "" +sourceId;
		ArrayList<SocialSettingsData> result= null;
		try {
			dq = new DBPreparedQuery("dSocialSettingsManager_getCompaniesAndGroupBySource",dynParam);
			result = dq.getResult();
		} catch (SQLException e) {
			TPLogger.getLogger().error("Unable to fetch  social network groups ", e);
		}finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return result;
	}
	
//	@SuppressWarnings("unchecked")
//	public ArrayList<SocialSettingsData> getSocialSettingsDataBySocialId(String socialId){
//		DBPreparedQuery dq = null;
//		ArrayList<SocialSettingsData> result= null;
//		try {
//			dq = new DBPreparedQuery("dSocialSettingsManager_getCompaniesAndGroupBySource");
//			dq.setString(1, socialId);
//			result = dq.getResult();
//		} catch (SQLException e) {
//			TPLogger.getLogger().error("Unable to fetch  social network groups ", e);
//		}finally {
//			if (dq != null) {
//				dq.releaseConnection();
//			}
//		}
//		return result;
//	}

	/**
	 * @param userId
	 * @param token
	 * @return true if token saved
	 */
	public boolean saveOAuthToken(SocialSettingsData ssd) throws Exception{
		DBPreparedQuery dq = null;
		DBTransaction tran = null;
		int cnt=1;
		try{
			tran = new DBTransaction();
			dq = new DBPreparedQuery("dSocialSettingsManager_saveOAuthToken",tran);
			dq.setString(cnt++,ssd.getUserId().trim());
			dq.setString(cnt++,ssd.getToken().trim());
			dq.setString(cnt++, ssd.getSocialId().trim());
			dq.execute();
			tran.commit();
		}catch (SQLException e) {
			TPLogger.getLogger().error("Unable to save oauth token ", e);
			tran.rollback();
			throw e;
		}finally {
			if (dq != null) {
				dq.releaseTransaction(tran);
			}

		}
		return true;
	}

	/**
	 * @return list of all media types
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<SocialSettingsData> getAllMediaTypes() {
		ArrayList<SocialSettingsData> result = null;
		DBQuery dq = null;
		try{
			dq = new DBQuery("dSocialSettingsManager_getAllMediaTypes");
			result= dq.getResult();
		}catch (SQLException e) {
			TPLogger.getLogger().error("Unable to fetch the media types", e);
		}finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return result;
	}
	
	/**
	 * @param groupId
	 * @param impId
	 * @return
	 */
	public boolean deleteSocialGroup(String groupId, String impId) throws Exception {
		DBPreparedQuery dq= null;
		DBTransaction tran = null;
		int cnt=1;
		try{
			tran = new DBTransaction();
			dq = new DBPreparedQuery("dSocialSettingsManager_deleteSocialGroup",tran);
			dq.setString(cnt++, groupId.trim());
			dq.setString(cnt++, impId.trim());
			dq.execute();
			tran.commit();
		}catch(SQLException e){
			TPLogger.getLogger().error("Unable to delete the social media group", e);
			tran.rollback();
			throw e;
		}finally{
			if(dq!=null){
				dq.releaseTransaction(tran);
			}
		}
		return true;
	}

	/**
	 * @param sourceName
	 * @param clientId
	 * @param clientSecret
	 * @return
	 * @throws Exception
	 */
	public boolean saveSocialCredentials (String sourceName, String clientId, String clientSecret,String companyIds) throws Exception{
		DBPreparedQuery dq = null;
		int cnt=1;
		try{
			dq = new DBPreparedQuery("dSaveSocialCredentials");
			dq.setString(cnt++, clientId);
			dq.setString(cnt++,clientSecret);
			dq.setString(cnt++, sourceName);
			dq.setString(cnt++, companyIds);
			dq.execute();
		}catch(Exception e){
			throw e;
		}finally{
			if(dq!=null){
				dq.releaseConnection();
			}
		}
		return true;
	}

	/**
	 * @return clientids and secrets of all social networks configured
	 */
	public ArrayList<SocialSettingsData> loadSocialCredentials() throws Exception{
		DBPreparedQuery dq = null;
		ArrayList<SocialSettingsData> result = null;
		try{
			dq = new DBPreparedQuery("dLoadSocialCredentials");
			result = dq.getResult();
		}catch(Exception e){
			throw e;
		}finally{
			if(dq!=null){
				dq.releaseConnection();
			}
		}
		return result;

	}

}
