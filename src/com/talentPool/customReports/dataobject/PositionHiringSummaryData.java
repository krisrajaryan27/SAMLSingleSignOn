package com.talentPool.customReports.dataobject;

import java.sql.Date;

import com.talentPool.common.db.SimpleDataObject;

public class PositionHiringSummaryData extends SimpleDataObject{

	private static final long serialVersionUID = 3292749888831344020L;
	
	public Date getProcessDate() {
		return getDate("processDate");
	}
	
	public void setProcessDate(Date processDate) {
		setAttribute("processDate", processDate);
	}

	public String getPositionId() {
		return getString("positionId");
	}
	
	public void setPositionId(String positionId) {
		setAttribute("positionId", positionId);
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
	
	public int getBacklog() {
		return getInt("backlog");
	}
	
	public void setBacklog(int backlog) {
		setAttribute("backlog", backlog);
	}
	
	public int getReceived() {
		return getInt("received");
	}
	
	public void setReceived(int received) {
		setAttribute("received", received);
	}
	
	public int getCleared() {
		return getInt("cleared");
	}
	
	public void setCleared(int cleared) {
		setAttribute("cleared", cleared);
	}
	
	public int getRejected() {
		return getInt("rejected");
	}
	
	public void setRejected(int rejected) {
		setAttribute("rejected", rejected);
	}
	
	public int getInprocess() {
		return getInt("inprocess");
	}
	
	public void setInprocess(int inprocess) {
		setAttribute("inprocess", inprocess);
	}
	
	public String getBacklogIds() {
		return getString("backlogIds");
	}
	
	public void setBacklogIds(String backlogIds) {
		setAttribute("backlogIds", backlogIds);
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
	
	public String getRejectedIds() {
		return getString("rejectedIds");
	}
	
	public void setRejectedIds(String rejectedIds) {
		setAttribute("rejectedIds", rejectedIds);
	}
	
	public String getInprocessIds() {
		return getString("inprocessIds");
	}
	
	public void setInprocessIds(String inprocessIds) {
		setAttribute("inprocessIds", inprocessIds);
	}
}
