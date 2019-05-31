package com.talentPool.customReports.dataobject;

import com.talentPool.common.db.SimpleDataObject;

public class EventSummaryData extends SimpleDataObject{

	private static final long serialVersionUID = 3292749888831344020L;

	public String getPositionId() {
		return getString("positionId");
	}
	
	public void setPositionId(String positionId) {
		setAttribute("positionId", positionId);
	}
	
	public String getUserId() {
		return getString("userId");
	}
	
	public void setUserId(String userId) {
		setAttribute("userId", userId);
	}
	
	public String getStepId() {
		return getString("stepId");
	}
	
	public void setStepId(String stepId) {
		setAttribute("stepId", stepId);
	}
	
	public String getStepLevel() {
		return getString("stepLevel");
	}
	
	public void setStepLevel(String stepLevel) {
		setAttribute("stepLevel", stepLevel);
	}
	
	public String getRejected() {
		return getString("rejected");
	}
	
	public void setRejected(String rejected) {
		setAttribute("rejected", rejected);
	}
	
	public String getOnhold() {
		return getString("onhold");
	}
	
	public void setOnhold(String onhold) {
		setAttribute("onhold", onhold);
	}
	
	public String getJoined() {
		return getString("joined");
	}
	
	public void setJoined(String joined) {
		setAttribute("joined", joined);
	}
	
	public String getReceived() {
		return getString("received");
	}
	
	public void setReceived(String received) {
		setAttribute("received", received);
	}
	
	public String getCleared() {
		return getString("cleared");
	}
	
	public void setCleared(String cleared) {
		setAttribute("cleared", cleared);
	}
	
	public String getInprocess() {
		return getString("inprocess");
	}
	
	public void setInprocess(String inprocess) {
		setAttribute("inprocess", inprocess);
	}

	public String getBacklog() {
		return getString("backlog");
	}
	
	public void setBacklog(String backlog) {
		setAttribute("backlog", backlog);
	}
	
	public String getProcessDate() {
		return getString("processDate");
	}
	
	public void setProcessDate(String processDate) {
		setAttribute("processDate", processDate);
	}
	
	public String getRejectedIds() {
		return getString("rejectedIds");
	}
	
	public void setRejectedIds(String rejectedIds) {
		setAttribute("rejectedIds", rejectedIds);
	}
	
	public String getReceivedIds() {
		return getString("receivedIds");
	}
	
	public void setReceivedIds(String receivedIds) {
		setAttribute("receivedIds", receivedIds);
	}
	
	public String getClearedIds() {
		return getString("clearedIds");
	}
	
	public void setClearedIds(String clearedIds) {
		setAttribute("clearedIds", clearedIds);
	}
	
	public String getInprocessIds() {
		return getString("inprocessIds");
	}
	
	public void setInprocessIds(String inprocessIds) {
		setAttribute("inprocessIds", inprocessIds);
	}

	public String getBacklogIds() {
		return getString("backlogIds");
	}
	
	public void setBacklogIds(String backlogIds) {
		setAttribute("backlogIds", backlogIds);
	}
}
