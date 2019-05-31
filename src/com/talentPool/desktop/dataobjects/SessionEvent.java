/**
 * 
 */
package com.talentPool.desktop.dataobjects;

/**
 * @author shivprasad
 *
 */
public class SessionEvent {
	private boolean stopFlag;
	private String sessionId;
	public SessionEvent(String sessionId){
		this.sessionId=sessionId;
		this.stopFlag=false;
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
	
}
