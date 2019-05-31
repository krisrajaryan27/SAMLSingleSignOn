/**
 * 
 */
package com.talentPool.dashboard.bc;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.dashboard.manager.RecentViewManager;
import com.talentPool.search.manager.SavedSearchManager;
import com.talentPool.user.manager.PermissionSet;

/**
 * @author shivprasad
 *
 */
public class DashboardBC {
	public static void setLeftPanel(HttpServletRequest request, String userId) {
		try {
			PermissionSet permissionSet = (PermissionSet) request.getSession().getAttribute("permissionSet");
			RecentViewManager recentViewManager = new RecentViewManager();
			List lastViewedEntities = recentViewManager.getLastViewedEntities(userId,permissionSet);
			SavedSearchManager savedSearchManager = new SavedSearchManager();
			List recentSearches = savedSearchManager.getRecentSearches(userId);
			request.setAttribute("lastViewedEntities", lastViewedEntities);
			request.setAttribute("recentSearches", recentSearches);
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while setting left panel", e);
		}
	}
}
