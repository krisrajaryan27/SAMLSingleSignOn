/**
 * 
 */
package com.talentPool.repository;

import java.util.Calendar;
import java.util.Date;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.properties.GlobalApplicationProperties;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.utils.Utils;


/**
 * @author shivprasad
 *
 */
public class TPRepositorySearcherUtils {
	public static String[] getEducationList(String[] degrees, String[] institutes, String[] majors, Date[] yops) {
		int dCnt = (degrees == null) ? 0 : degrees.length;
		String eduList[] = new String[dCnt];
		for (int i = 0; i < dCnt; i++) {
			eduList[i] =getEduFormated(degrees[i], institutes[i], majors[i], yops[i]);
		}
		return eduList;
	}

	public static String getEduFormated(String degree, String inst, String major, Date yop) {
		StringBuffer sb = new StringBuffer();
		try {
			if (!Utils.isBlankOrNull(degree)) {
				sb.append(degree);
			}

			boolean brackets = false;
			if (!Utils.isBlankOrNull(inst) || !Utils.isBlankOrNull(major) || yop != null) {
				brackets = true;
			}
			if (brackets)
				sb.append(" ( ");
			if (!Utils.isBlankOrNull(inst)) {
				sb.append(inst);
			}
			if (!Utils.isBlankOrNull(major)) {
				sb.append(", ");
				sb.append(major);
			}
			if (yop != null) {
				sb.append(", ");
				sb.append(Utils.getDateConvertedToString(yop, Utils.regYYYYFormat));
			}
			if (brackets)
				sb.append(" )");
		} catch (Exception e) {
			TPLogger.getLogger().error("Error", e);
		}
		return sb.toString().trim();
	}
	
	public static boolean isNewRecord(Date importDate) {
		boolean isnew = false;
		int newDays = Integer.parseInt(GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_DURATION_AS_NEW_RESUME));
		Calendar calImp = Calendar.getInstance();
		calImp.add(Calendar.DATE, (0 - newDays));
		if (calImp.getTime().before(importDate)) {
			isnew = true;
		}
		return isnew;
	}
	
	
	public static String[] getEmploymentHistoryList(String[] previousEmployers, String[] designation) {
		int dCnt = (previousEmployers == null) ? 0 : previousEmployers.length;
		String empHistList[] = new String[dCnt];
		for (int i = 0; i < dCnt; i++) {
			empHistList[i] =getEmploymentHistoryFormated(previousEmployers[i], designation[i]);
		}
		return empHistList;
	}

	public static String getEmploymentHistoryFormated(String previousEmployers, String designation) {
		StringBuffer sb = new StringBuffer();
		try {
			if (!Utils.isBlankOrNull(previousEmployers)) {
				sb.append(previousEmployers);
			}

			boolean brackets = false;
			if (!Utils.isBlankOrNull(designation)) {
				brackets = true;
			}
			if (brackets)
				sb.append(" ( ");
			if (!Utils.isBlankOrNull(designation)) {
				sb.append(designation);
			}			
			if (brackets)
				sb.append(" )");
		} catch (Exception e) {
			TPLogger.getLogger().error("Error", e);
		}
		return sb.toString().trim();
	}
}
