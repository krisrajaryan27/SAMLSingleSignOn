package com.talentPool.socialNetwork.utils;

import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

import com.talentPool.common.db.SimpleDataObject;

/**
 * @author SumeetS
 *
 */
public class SocialEventStack {
	
	private static SocialEventStack eventStack = null;
	private static int eventId = 0;
	private Map<Integer,SimpleDataObject> events =
			Collections.synchronizedMap(new TreeMap<Integer,SimpleDataObject>());

	private SocialEventStack(){
		
	}
	
	/**
	 * @return singeletonEventStack
	 */
	public static SocialEventStack getInstance(){
		if(eventStack==null){
			eventStack = new SocialEventStack();
		}
		return eventStack;
	}
	
	/**
	 * @param sdo
	 * @return eventId
	 */
	public Integer addEvent(SimpleDataObject sdo){
		// TODO : create event_id pool
		if(eventId==10000){  //Upper Limit for eventId
			eventId=0;
		}
		events.put(eventId, sdo);
		return eventId++;
	}
	
	/**
	 * @param eventId
	 * @return event sdo
	 */
	public SimpleDataObject getEvent(Integer eventId){
		SimpleDataObject sdo = events.get(eventId);
		events.remove(eventId);
		return sdo;
	}
}
