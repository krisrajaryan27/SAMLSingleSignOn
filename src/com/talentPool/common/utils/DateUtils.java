package com.talentPool.common.utils;

import static com.talentPool.common.CommonConstants.DEFAULT_DELIMITER;
import static com.talentPool.common.CommonConstants.NEW_ARRAY;
import static com.talentPool.common.constants.DateConstants.DB_DATE_TIME_PATTERN;
import static com.talentPool.common.constants.DateConstants.GRID_SORTING_PATTERN;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

import com.talentPool.admin.manager.AdminManager;
import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.constants.DateConstants;
import com.talentPool.common.dataobject.DateTimePattern;
import com.talentPool.common.properties.GlobalApplicationProperties;
import com.talentPool.common.properties.GlobalConstants;

/**
 * @author PraveenK
 * @since  Sep 20, 2011
 */
public class DateUtils {
	
	/**
	 * dateTimePatterns are loaded only once when class is loaded by JRE.
	 * Patterns are application defined and cannot be modified.( final )
	 */
	private static final Map<String,DateTimePattern> dateTimePatterns;
	
	private static String systemDatePattern = null;
	private static String systemTimePattern = null;
	private static String systemDateTimePattern = null;
	
	private static String systemDbDatePattern = null;
	private static String systemDbTimePattern = null;
	private static String systemDbDateTimePattern = null;
	
	
	static {
		dateTimePatterns = new AdminManager().getSystemDateTimePatterns();
		resetSystemDateTimeFormats();
	}
	
	/**
	 * @return datePattern defined on application level
	 */
	public static String getSystemDatePattern(){
		return systemDatePattern;
	}
	
	/**
	 * @return timePattern defined on application level
	 */
	public static String getSystemDbTimePattern(){
		return systemDbTimePattern;
		
	}
	
	/**
	 * @return dateTimePattern defined on application level  
	 */
	public static String getSystemDbDateTimePattern(){
		return systemDbDateTimePattern;
	}
	
	/**
	 * @return datePattern defined on application level
	 */
	public static String getSystemDbDatePattern(){
		return systemDbDatePattern;
	}
	
	/**
	 * @return timePattern defined on application level
	 */
	public static String getSystemTimePattern(){
		return systemTimePattern;
		
	}
	
	/**
	 * @return dateTimePattern defined on application level  
	 */
	public static String getSystemDateTimePattern(){
		return systemDateTimePattern;
	}
	
	/**
	 * Converts the date to application level defined date format
	 * @param date
	 * @return
	 */
	public static String getSystemDateFormat(java.util.Date date){
		return getDateFormated(date,getSystemDatePattern());
	}
	
	/**
	 * Converts the date to application level defined date format
	 * @param date
	 * @return
	 */
	public static String getSystemDateFormatToDisplay(java.sql.Date date){
		return getDateFormated(date,Utils.regEUDateFormat);
	}
	
	/**
	 * Converts the date to application level defined time format
	 * @param date
	 * @return
	 */
	public static String getSystemTimeFormat(java.util.Date date){
		return getDateFormated(date,getSystemTimePattern());
	}
	
	/**
	 * Converts the date to application level defined dateTime format
	 * @param date
	 * @return
	 */
	public static String getSystemDateTimeFormat(java.util.Date date){
		return getDateFormated(date,getSystemDateTimePattern());
	}
	
	/**
	 * Converts the date in the format of the <code>pattern</code> given
	 * @param date
	 * @return
	 */
	public static String getDateFormated(java.util.Date date, String pattern){
		try {
			if(date!=null){
				DateFormat format = new SimpleDateFormat(pattern, GlobalConstants.LOCALE);
				return format.format(date);
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return "";
	}
	
	/**
	 * Converts the date in the format of the <code>pattern</code> given
	 * @param date
	 * @return
	 */
	public static String getDateFormatedToDisplay(java.sql.Date date, String pattern){
		try {
			if(date!=null){
				DateFormat format = new SimpleDateFormat(pattern, GlobalConstants.LOCALE);
				return format.format(date);
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return "";
	}
	
	/**
	 * @param date
	 * @return
	 */
	public static String getDateFormatForGridSorting(java.util.Date date){
		try {
			if(date!=null){
				DateFormat format = new SimpleDateFormat(GRID_SORTING_PATTERN, GlobalConstants.LOCALE);
				return format.format(date);	
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return "";
	}
	
	/**
	 * @param date
	 * @return
	 */
	public static String getSystemDateFormatForDbDate(String date){
		return getSystemDateFormat(date, DB_DATE_TIME_PATTERN);
	}
	
	/**
	 * @param date
	 * @return
	 */
	public static String getSystemDateFormat(String date,String srcPattern){
		DateFormat format = null;
		try {
			if(!Utils.isBlankOrNull(date)){
				format = new SimpleDateFormat(srcPattern, GlobalConstants.LOCALE);
				return getSystemDateFormat(format.parse(date));
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return "";
	}
	
	/**
	 * @param date
	 * @return
	 */
	public static String getSystemDateTimeFormatForDbDate(String date){
		return getSystemDateTimeFormat(date, DB_DATE_TIME_PATTERN);
	}
	
	/**
	 * @param date
	 * @return
	 */
	public static String getSystemDateTimeFormat(String date,String srcPattern){
		DateFormat format = null;
		try {
			if(!Utils.isBlankOrNull(date)){
				format = new SimpleDateFormat(srcPattern, GlobalConstants.LOCALE);
				return getSystemDateTimeFormat(format.parse(date));
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return "";
	}
	
	/**
	 * parses a input String to <code>java.util.Date</code> object
	 * @param input
	 * @param dateFormat
	 * @return
	 */
	public static java.util.Date convertToDate(String input, String dateFormat) {
		java.util.Date dt = null;
		try {
			if (!Utils.isBlankOrNull(input)) {
				SimpleDateFormat sdfInput = new SimpleDateFormat(dateFormat);
				dt = sdfInput.parse(input);
			}
		} catch (ParseException  e) {
			TPLogger.getLogger().debug("Error While converting to Date", e);
			dt = null;
		}
		return dt;
	}
	
	/**
	 * parses a input String to <code>java.sql.Date</code> object
	 * @param input
	 * @param dateFormat
	 * @return
	 */
	public static java.sql.Date convertToSqlDate(String input, String dateFormat) {
		return convertToSqlDate(convertToDate(input, dateFormat));
	}
	
	public static java.sql.Date convertToSqlDate(java.util.Date date) {
		if(date!=null){
			return new java.sql.Date(date.getTime());
		}
		return null;
	}
	
	
	
	/**
	 * resets the system defined Date, Time, Date Time patterns
	 */
	public static void resetSystemDateTimeFormats(){
		try {
			systemDatePattern =	getPattern(GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_DEFAULT_DATEFORMAT)); 
			systemTimePattern = getPattern(GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_DEFAULT_TIMEFORMAT)); 
			systemDateTimePattern = systemDatePattern+" "+systemTimePattern;
			systemDbDatePattern = getDbPattern(GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_DEFAULT_DATEFORMAT)); 
			systemDbTimePattern = getDbPattern(GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_DEFAULT_TIMEFORMAT)); 
			systemDbDateTimePattern =systemDbDatePattern+" "+systemDbTimePattern;			

		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
	}
	
	/**
	 * For the given patternId returns the patternValue
	 * @param patternId
	 * @return
	 */
	public static String getPattern(String patternId){
		return dateTimePatterns.get(patternId).getPatternValue();
	}
	
	/**
	 * For the given patternId returns the DB patternValue
	 * @param patternId
	 * @return
	 */
	public static String getDbPattern(String patternId){
		return dateTimePatterns.get(patternId).getDbPatternValue();
	}
	
	/**
	 * Builds JS Array for the given pattern type.
	 * 
	 * @param patternType <code>{@link DateConstants}.{DATE or TIME}</code> 
	 * @return 
	 */
	public static String buildDatePatternsJSArray(int patternType){
		StringBuilder sb = null;
		try {
			if(dateTimePatterns!=null){
				sb = new StringBuilder("[");
				for (Map.Entry<String, DateTimePattern> entry : dateTimePatterns.entrySet()) {
					if(patternType == entry.getValue().getPatternType()){
						Utils.getJSArraySelectOption(entry.getKey(), entry.getValue().getPatternValue(), sb);
						sb.append(DEFAULT_DELIMITER);
					}
				}
				if(sb.length()-1==sb.lastIndexOf(DEFAULT_DELIMITER))
					sb.replace(sb.lastIndexOf(DEFAULT_DELIMITER),sb.length(), ""); 
				return sb.append("]").toString();
			}
			return NEW_ARRAY;
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			return NEW_ARRAY; 
		}
	}
	
	public static Date getCurrentQuarterStartDate(){
		Date date =null;
		Calendar cal = Calendar.getInstance();
		int currentMonth=cal.get(Calendar.MONTH);
		try{
			switch(currentMonth){
				case Calendar.JANUARY:
				case Calendar.APRIL:
				case Calendar.JULY:
				case Calendar.OCTOBER:
					date = new Date();
					date =Utils.adjustDateBy(date,Calendar.DATE, -cal.get(Calendar.DAY_OF_MONTH)+1);
					break;
				case Calendar.FEBRUARY:
				case Calendar.MAY:
				case Calendar.AUGUST:
				case Calendar.NOVEMBER:
					date =Utils.adjustDateBy(new Date(),Calendar.MONTH, -1);
					date =Utils.adjustDateBy(date,Calendar.DATE, -cal.get(Calendar.DAY_OF_MONTH)+1);
					break;
				case Calendar.MARCH:			
				case Calendar.JUNE:
				case Calendar.SEPTEMBER:
				case Calendar.DECEMBER:
					date =Utils.adjustDateBy(new Date(),Calendar.MONTH, -2);
					date =Utils.adjustDateBy(date,Calendar.DATE, -cal.get(Calendar.DAY_OF_MONTH)+1);
					break;
			}
		} catch (Exception e) {			
			TPLogger.getLogger().error("Error while getting current quarter start date", e);
		}
		
		return date;
	}	
	
	public static Date getCurrentFinancialStartDate(){
		Date date =null;		
		try{
			Calendar cal = Calendar.getInstance();			
			int financialStartMonth =Integer.parseInt( GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_FINANCIAL_YEAR_START_MONTH));
			int currentMonth=cal.get(Calendar.MONTH);
			if(currentMonth>=financialStartMonth){
				cal.set(cal.get(Calendar.YEAR),financialStartMonth,1);
			}else{
				cal.set(cal.get(Calendar.YEAR)-1,financialStartMonth,1);
			}			
			date=cal.getTime();
		} catch (Exception e) {			
			TPLogger.getLogger().error("Error while getting current financial year start date", e);
		}
		return date;
	}	
	
	public static String getDateConversionFromOneStringFormatToAnother(String srcDateFormat, String targetDateFormat,
			String dateToBeParsed) {
		DateFormat originalFormat = new SimpleDateFormat(srcDateFormat, Locale.ENGLISH);
		DateFormat targetFormat = new SimpleDateFormat(targetDateFormat);
		Date date = null;
		try {
			date = originalFormat.parse(dateToBeParsed);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			TPLogger.getLogger().error("Error while converting date date format", e);
		}
		String formattedDate = targetFormat.format(date);
		return formattedDate;
	}
}
