package com.talentPool.common.Logger.scheduler;

import java.io.File;
import java.io.FilenameFilter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ToBeZippedFilesFilter implements FilenameFilter {

	private Calendar thresholdDate;

	private Calendar startingDate;

	private SimpleDateFormat format = null;

	private ArrayList<String> archiveLogFileNames = new ArrayList<String>();

	public ToBeZippedFilesFilter(int numberOfDays, String logFileDateFormat,
			String archiveLogFileNames) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		this.thresholdDate = (Calendar) calendar.clone();
		calendar.add(Calendar.DATE, -numberOfDays);
		this.startingDate = calendar;
		this.format = new SimpleDateFormat(logFileDateFormat);
		String[] fileNames = archiveLogFileNames.split(",");
		for (int i = 0; i < fileNames.length; i++) {
			this.archiveLogFileNames.add(fileNames[i].trim());
		}
	}

	@Override
	public boolean accept(File dir, String name) {

		try {
			if (archiveLogFileNames.contains(name.substring(0, name
					.indexOf(".")))) {
				Date date = format.parse(name.substring(1 + name
						.lastIndexOf(".")));
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(date);
				if (calendar.before(thresholdDate)) {
					if (calendar.before(startingDate)) {
						startingDate = calendar;
					}
					return true;
				}
			}
		} catch (ParseException e) {

		}
		return false;
	}

	public Calendar getThresholdDate() {
		return this.thresholdDate;
	}

	public Calendar getStartingDate() {
		return this.startingDate;
	}
}
