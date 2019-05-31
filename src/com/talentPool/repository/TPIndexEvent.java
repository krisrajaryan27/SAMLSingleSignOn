/**
 * 
 */
package com.talentPool.repository;

/**
 * @author shivprasad
 * 
 * event object holds the information of index modification event
 */
public class TPIndexEvent {
	public static final String TYPE_ALL = "ALL";
	public static final String TYPE_ADD_APPLICANT = "ADD_APPLICANT";
	
	public static final String TYPE_UPDATE_APPLICANT = "UPDATE_APPLICANT";
	public static final String TYPE_DELETE_APPLICANT = "DELETE_APPLICANT";
	public static final String TYPE_SKILLS = "SKILLS";
	public static final String TYPE_DEGREE = "DEGREE";
	public static final String TYPE_INSTITUTE = "INSTITUTE";
	public static final String TYPE_BRANCH = "BRANCH";
	public static final String TYPE_SOURCE = "SOURCE";
	public static final String TYPE_PREVIOUS_EMPLOYER = "PREVIOUS_EMPLOYER";
	public static final String TYPE_DESIGNATION = "DESIGNATION";	
	public static final String TYPE_OPTIMIZE_REPOSITORY="OPTIMIZE_REPO";

	public static final int PRIORITY_LEAST = 0; //this priority should be only used for optimize type event
	public static final int PRIORITY_NORMAL = 1;
	public static final int PRIORITY_HIGH = 10;

	private long eventTime;
	private String eventType;
	private String eventTypeId;
	private int priority;

	public TPIndexEvent(String eventType, String eventTypeId, int priority) {
		this.eventType = eventType;
		this.eventTypeId = eventTypeId;
		this.priority = priority;
	}

	/**
	 * @return Returns the eventType.
	 */
	public String getEventType() {
		return eventType;
	}

	/**
	 * @param eventType
	 *            The eventType to set.
	 */
	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	/**
	 * @return Returns the eventTypeId.
	 */
	public String getEventTypeId() {
		return eventTypeId;
	}

	/**
	 * @param eventTypeId
	 *            The eventTypeId to set.
	 */
	public void setEventTypeId(String eventTypeId) {
		this.eventTypeId = eventTypeId;
	}

	/**
	 * @return Returns the priority.
	 */
	public int getPriority() {
		return priority;
	}

	/**
	 * @param priority
	 *            The priority to set.
	 */
	public void setPriority(int priority) {
		this.priority = priority;
	}

	/**
	 * @return Returns the eventTime.
	 */
	public long getEventTime() {
		return eventTime;
	}

	/**
	 * @param eventTime
	 *            The eventTime to set.
	 */
	public void setEventTime(long eventTime) {
		this.eventTime = eventTime;
	}
	public String toString() {
		// TODO Auto-generated method stub
		StringBuffer sb = new StringBuffer(100);
		sb.append(" [eventType = " + eventType +", ");
		sb.append("eventTypeId = " + eventTypeId +", ");
		sb.append("priority = " + priority +", ");
		sb.append("eventTime = " + eventTime + "]\n");
		return sb.toString();
	}
}
