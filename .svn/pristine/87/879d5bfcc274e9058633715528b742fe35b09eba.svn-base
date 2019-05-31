package com.talentPool.timeZone;

import java.util.Date;
import java.util.TimeZone;

import com.talentPool.common.properties.GlobalApplicationProperties;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.utils.Utils;

public class TimeZoneUtils {

	public static final String DEFAULT_TIME_ZONE_ID ="UTC";
	
	
	public static String getOffset(String timeZoneId){
		String timeZone = "+00:00";
		TimeZone zone = TimeZone.getTimeZone(timeZoneId);
		int offset = 0;
		if (zone.inDaylightTime(new Date())){
			offset = (zone.getRawOffset() + zone.getDSTSavings())/1000;
		} else {
			offset = zone.getRawOffset()/1000;
		}
		int hour = offset/3600;
		int minutes = (offset % 3600)/60;
		if(hour>=0){
			timeZone = "+"+hour+":"+minutes;
		}else{
			timeZone = hour+":"+minutes;
		}
		return timeZone;
	}
	
	public static void main(String[] args){
		
		System.out.println(330%60);
		TimeZone zone = TimeZone.getDefault();
		int offset = zone.getRawOffset();
		System.out.println(offset);
	}
	
	public static String getTimeZoneId(String timeZone){
		try {
			String [] ids = GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_ALL_SELECTED_TIME_ZONES).split(",");
			int time = Integer.parseInt(timeZone);
			time = -time;
			int currentDiff = Math.abs(TimeZone.getTimeZone(ids[0]).getRawOffset()/60000 - time);
			String currentId = ids[0];
			for(String id:ids) {
				TimeZone zone = TimeZone.getTimeZone(id);
				int minOffset = zone.getRawOffset()/60000;
				if ((Math.abs(minOffset-time))<currentDiff){
					currentDiff = Math.abs(minOffset-time);
					currentId = id;
				}
				if (minOffset == time){
					currentId = id;
					break;
				}
			}  
			return currentId;
		} catch(Exception e){
			return DEFAULT_TIME_ZONE_ID;
		}
	}
	
}
