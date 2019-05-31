/**
 * 
 */
package com.talentPool.search.manager;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.db.DBPreparedQuery;
import com.talentPool.common.db.DBTransaction;
import com.talentPool.common.db.SimpleDataObject;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.utils.Utils;
import com.talentPool.search.SearchConstants;
import com.talentPool.search.dataobjects.SavedSearchData;
import com.talentPool.search.dataobjects.SearchCriteriaData;
import com.talentPool.user.dataobject.LastViewedEntity;

/**
 * @author shivprasad
 *
 */
public class SavedSearchManager {
	/**
	 * Method to save the recent search data.
	 * 
	 * @param userId
	 *            The identifier of the user.
	 * @param cData
	 *            The search criteria data.
	 */
	public void addRecentSearchData(String userId, SearchCriteriaData cData) {
		DBPreparedQuery dq = null;
		try {
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
			objectOutputStream.writeObject(cData);
			byte[] bytes = byteArrayOutputStream.toByteArray();

			dq = new DBPreparedQuery("dSavedSearchManager_InsertRecentSearchData");
			dq.setString(1, userId);
			dq.setBytes(2, bytes);
			dq.execute();
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while inserting the recent search data", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
	}

	/**
	 * Method to get recent searches.
	 * 
	 * @return The list of recent searches.
	 */
	public List getRecentSearches(String userId) {
		DBPreparedQuery dq = null;
		List recentSearches = null;
		try {
			dq = new DBPreparedQuery("dSavedSearchManager_GetRecentSearches");
			dq.setString(1, userId);
			recentSearches = dq.getResult();
			if (recentSearches != null && recentSearches.size() > 0) {
				for (int i = 0; i < recentSearches.size(); i++) {
					LastViewedEntity entity = (LastViewedEntity) recentSearches.get(i);
					ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream((byte[]) entity.getAttribute("description"));
					ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
					SearchCriteriaData data = (SearchCriteriaData) objectInputStream.readObject();
					entity.setDescription(data.getSearchText());
					entity.setTitle(Utils.getSearchFilterString(data));
				}
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while getting the recent searches", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return recentSearches;
	}

	/**
	 * Method to get the search criteria data.
	 * 
	 * @param searchId
	 *            The identifier of the search.
	 * @return The search criteria data.
	 */
	public SearchCriteriaData getSearchCriteriaData(String searchId) {
		DBPreparedQuery dq = null;
		SearchCriteriaData data = null;
		try {
			dq = new DBPreparedQuery("dSavedSearchManager_GetSearchCriteriaData");
			dq.setString(1, searchId);
			LastViewedEntity entity = (LastViewedEntity) dq.getSingleObjectResult();
			ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream((byte[]) entity.getAttribute("description"));
			ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
			data = (SearchCriteriaData) objectInputStream.readObject();
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while getting the search criteria data", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return data;
	}
	
	public void saveSearch(String searchName, String shared, String userId, SearchCriteriaData cData, String searchType) throws Exception {
		DBPreparedQuery dq = null;
		DBTransaction tran = null;
		try {
			tran = new DBTransaction();
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
			objectOutputStream.writeObject(cData);
			byte[] bytes = byteArrayOutputStream.toByteArray();

			String searchNameSaved=null;
			if(searchType.equals(SearchConstants.SEARCH_ID_RECENT_SEARCHES)){
				searchNameSaved = "";
			}else{
				searchNameSaved=searchName;
			}
			
			dq = new DBPreparedQuery("dSavedSearchManager_SaveSearch");
			dq.setString(1, userId);			
			dq.setString(2, searchNameSaved);
			dq.setString(3, shared);
			dq.setBytes(4, bytes);
			dq.execute();
			
//			dq = new DBPreparedQuery("dFetchLastInsertID", tran);
//			String searchId = dq.getIdResult();

//			insertCustomFieldValues(cData.getCustomFields(), searchId, tran);
			
			tran.commit();
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while inserting the recent search data", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}	
	}
	
	public ArrayList<SavedSearchData> getAllSavedSearches(String userId, String searchType) {
		
		return getAllSavedSearches(userId, searchType, SearchConstants.SEARCH_SHARED);
	}
	public ArrayList<SavedSearchData> getAllSavedSearches(String userId, String searchType, String showAll) {
		DBPreparedQuery dq = null;
		ArrayList<SavedSearchData> sList = null;		
		try {
			String[] dynParam = new String[1];
			ArrayList<String> dynamicContent = new ArrayList<String>();
			dynParam[0] ="";
			if(!Utils.isBlankOrNull(showAll) && showAll.equals("1")){
				dynParam[0]=" OR tss.shared =? ";
				dynamicContent.add(showAll);
			}
			dq = new DBPreparedQuery("dSavedSearchManager_GetAllSavedSearches",dynParam);
			int cnt = 1;
			dq.setString(cnt++, userId);
			if(!Utils.isBlankOrNull(showAll) && showAll.equals("1")){
				for (int i = 0; i < dynamicContent.size(); i++) {
					dq.setString(cnt++, dynamicContent.get(i));	
				}
			}
			sList = dq.getResult();			
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return sList;
	}

	public SearchCriteriaData getSavedSearchCriteriaData(String searchId) {
		DBPreparedQuery dq = null;
		SearchCriteriaData data = null;
		try {
			dq = new DBPreparedQuery("dSavedSearchManager_GetSavedSearchCriteriaData");
			dq.setString(1, searchId);
			SimpleDataObject entity = (SimpleDataObject) dq.getSingleObjectResult();
			ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream((byte[]) entity.getAttribute("description"));
			ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
			data = (SearchCriteriaData) objectInputStream.readObject();
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return data;
	}
	
	public void deleteSavedSearch(String searchId){
		DBPreparedQuery dq = null;
		DBTransaction tran = null;
		try {
			tran = new DBTransaction();
			dq = new DBPreparedQuery("dSavedSearchManager_deleteSavedSearch");
			dq.setString(1, searchId);			
			dq.execute();		
			tran.commit();
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while inserting the recent search data", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}	
	}
}
