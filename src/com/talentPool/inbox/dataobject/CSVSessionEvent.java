/**
 * 
 */
package com.talentPool.inbox.dataobject;

/**
 * @author Shantanu
 *
 */
public class CSVSessionEvent {
	
	private boolean stopFlag;
	private String sessionId;
	
	public CSVSessionEvent(boolean stopFlag, String sessionId){
		this.stopFlag=stopFlag;
		this.sessionId=sessionId;
	}
	
	/**
	 * @return the stopFlag
	 */
	public boolean isStopFlag() {
		return stopFlag;
	}

	/**
	 * @param stopFlag the stopFlag to set
	 */
	public void setStopFlag(boolean stopFlag) {
		this.stopFlag = stopFlag;
	}

	/**
	 * @return the sessionId
	 */
	public String getSessionId() {
		return sessionId;
	}

	/**
	 * @param sessionId the sessionId to set
	 */
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	

}
