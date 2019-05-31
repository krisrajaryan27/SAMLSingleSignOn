/**
 * 
 */
package com.talentPool.calendar.utils;

import java.util.Calendar;
import java.util.Date;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.utils.Utils;

/**
 * @author shivprasad
 * 
 */
public class CalendarUtils {
	public static boolean isValidDateTime(String datetime) {
		boolean isValid = true;
		try {
			Date appointmentTime = Utils.convertToDate(datetime, "yyyy-MM-dd HH:mm");
			Calendar cal = Calendar.getInstance();
			if (cal.getTime().after(appointmentTime)) {
				isValid = false;
			}
		} catch (Exception e) {
			TPLogger.getLogger().debug("Error while validating time", e);
		}

		return isValid;
	}
}
