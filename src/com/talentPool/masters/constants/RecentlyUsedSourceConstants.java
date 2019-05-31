/**
 * 
 */
package com.talentPool.masters.constants;

import com.talentPool.common.properties.TPApplicationProperties;

/**
 * @author shivprasad
 *
 */
public class RecentlyUsedSourceConstants {
	public static final int noOfRecentlyUsedSources;
	static {
		noOfRecentlyUsedSources = Integer.parseInt(TPApplicationProperties.getProperty("recently.used.source.count"));
	}
}
