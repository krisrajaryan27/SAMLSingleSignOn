/**
 * 
 */
package com.talentPool.common.utils;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.talentPool.common.Logger.TPLogger;

/**
 * @author shivprasad
 * 
 */
public class RegexUtils {
	public static ArrayList<String> getMatches(String content, String exp) {
		ArrayList<String> matches = new ArrayList<String>();
		try {
			Pattern p = Pattern.compile(exp);
			Matcher m = p.matcher(content);
			boolean theEnd = false;
			while (!theEnd) {
				theEnd = !m.find();
				if (!theEnd) {
					matches.add(content.substring(m.start(), m.end()));
				}
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error", e);
		}
		return matches;
	}

	public static Matcher getMatcher(String content, String exp) {
		Matcher m = null;
		try {
			Pattern p = Pattern.compile(exp);
			m = p.matcher(content);
		} catch (Exception e) {
			TPLogger.getLogger().error("Error", e);
		}
		return m;
	}
	
	public static ArrayList<String> getGroups(String content, String exp) {
		ArrayList<String> groups = new ArrayList<String>();
		try {
			Pattern p = Pattern.compile(exp);
			Matcher m = p.matcher(content);
			int groupCount = m.groupCount();
			if (groupCount > 1) {
				m.find();
				for (int i = 0; i <= groupCount; i++) {
					String grp = m.group(i);
					groups.add(grp);
				}
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error", e);
		}
		return groups;
	}
}
