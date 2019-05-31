/**
 * 
 */
package com.talentPool.masters.manager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.properties.TPApplicationProperties;
import com.talentPool.common.utils.CommonUtils;
import com.talentPool.common.utils.Utils;
import com.talentPool.masters.constants.RecentlyUsedSourceConstants;
import com.talentPool.masters.dataobject.RecentlyUsedSourceData;

/**
 * @author shivprasad
 * 
 */
public enum RecentlyUsedSources {
	// New way to implement singleton class. you can access methods using syntax
	// RecentlyUsedSources.INSTANCE.someMethod( );
	INSTANCE;
	private ArrayList<RecentlyUsedSourceData> recentlyUsedSources;
	
	private static final Calendar cal;
	private static RecentlyUsedSourcesManager recentlyUsedSourcesManager;
	static {
		cal = Calendar.getInstance();
		recentlyUsedSourcesManager = new RecentlyUsedSourcesManager();
	}

	/**
	 * @return the recentlyUsedSources
	 */
	public ArrayList<RecentlyUsedSourceData> getRecentlyUsedSources() {
		if (recentlyUsedSources == null) {
			loadSourcesFromDB();
		}
		return recentlyUsedSources;
	}

	/**
	 * @param recentlyUsedSources
	 *            the recentlyUsedSources to set
	 */
	public void setRecentlyUsedSources(ArrayList<RecentlyUsedSourceData> recentlyUsedSources) {
		this.recentlyUsedSources = recentlyUsedSources;
	}

	/**
	 * loads recently used sources from DB
	 */
	private void loadSourcesFromDB() {
		recentlyUsedSources = recentlyUsedSourcesManager.getRecentlyUsedSourcesFromDB(RecentlyUsedSourceConstants.noOfRecentlyUsedSources);
	}

	public void addSourceToRecentlyUsed(String sourceId) {
		try {
			getRecentlyUsedSources();
			if (recentlyUsedSources == null) {
				recentlyUsedSources = new ArrayList<RecentlyUsedSourceData>();
			}
			boolean exists = false;
			for (int i = 0; i < recentlyUsedSources.size(); i++) {
				if (recentlyUsedSources.get(i).getSourceId().equals(sourceId)) {
					recentlyUsedSources.get(i).setLastUsedDate(Utils.convertDateToSQLDate(cal.getTime()));
					exists = true;
					break;
				}
			}
			if (!exists) {
				String sourceTitle = CommonUtils.getSourceNameWithoutEmployeeSource(sourceId);
				if (!Utils.isBlankOrNull(sourceTitle)) {
					recentlyUsedSources.add(new RecentlyUsedSourceData(sourceId, sourceTitle, Utils.convertDateToSQLDate(cal.getTime())));
					// first remove least used source
					removeLeastUsed();
					// order it by name
					orderBySourceTitle();
					recentlyUsedSourcesManager.insertOrUpdateRecentlyUsedSource(sourceId);
				}
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
	}

	private void removeLeastUsed() {
		int idx = 0;
		int sz = recentlyUsedSources.size();
		if (sz > RecentlyUsedSourceConstants.noOfRecentlyUsedSources) {
			RecentlyUsedSourceData prev = recentlyUsedSources.get(0);
			for (int i = 1; i < sz; i++) {
				RecentlyUsedSourceData curr = recentlyUsedSources.get(i);
				if (curr.getLastUsedDate().before(prev.getLastUsedDate()) || curr.getLastUsedDate().equals(prev.getLastUsedDate())) {
					idx = i;
					prev = curr;
				}
			}
			recentlyUsedSources.remove(idx);
		}
	}

	private void orderBySourceTitle() {
		List ul = Collections.synchronizedList(recentlyUsedSources);
		Collections.sort(ul, new Comparator<RecentlyUsedSourceData>() {
			public int compare(RecentlyUsedSourceData o1, RecentlyUsedSourceData o2) {
				if (o1.getSourceTitle().compareTo(o2.getSourceTitle()) > 0) {
					return 1;
				} else {
					return -1;
				}
			}
		});
		recentlyUsedSources = new ArrayList(ul);
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		int sz = recentlyUsedSources.size();
		for (int i = 0; i < sz; i++) {
			sb.append(recentlyUsedSources.get(i) + "\n");
		}
		return sb.toString();
	}

	public static void main(String[] args) {
		// RecentlyUsedSources.INSTANCE.getRecentlyUsedSources();
		// System.out.println("0 - #########");
		// System.out.println(RecentlyUsedSources.INSTANCE.toString());
		// System.out.println("191 - #########");
		// RecentlyUsedSources.INSTANCE.addSourceToRecentlyUsed("191");
		// System.out.println(RecentlyUsedSources.INSTANCE.toString());
		// System.out.println("194 - #########");
		// RecentlyUsedSources.INSTANCE.addSourceToRecentlyUsed("194");
		// System.out.println(RecentlyUsedSources.INSTANCE.toString());
		// System.out.println("196 - #########");
		// RecentlyUsedSources.INSTANCE.addSourceToRecentlyUsed("196");
		// System.out.println(RecentlyUsedSources.INSTANCE.toString());
		// System.out.println("2 - #########");
		// RecentlyUsedSources.INSTANCE.addSourceToRecentlyUsed("2");
		// System.out.println(RecentlyUsedSources.INSTANCE.toString());
	}
}
