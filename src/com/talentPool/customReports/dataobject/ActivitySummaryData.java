package com.talentPool.customReports.dataobject;

import com.talentPool.common.db.SimpleDataObject;

public class ActivitySummaryData extends SimpleDataObject{

	public String getUserId() {
		return getString("userId");
	}
	
	public void setUserId(String userId) {
		setAttribute("userId", userId);
	}
	
	public String getUserName() {
		return getString("userName");
	}
	
	public void setUserName(String userName) {
		setAttribute("userName", userName);
	}
	
	public String getImported() {
		return getString("imported");
	}
	
	public void setImported(String imported) {
		setAttribute("imported", imported);
	}
	
	public String getEmailRecieved() {
		return getString("emailRecieved");
	}
	
	public void setEmailRecieved(String emailRecieved) {
		setAttribute("emailRecieved", emailRecieved);
	}
	
	public String getEmailSent() {
		return getString("emailSent");
	}
	
	public void setEmailSent(String emailSent) {
		setAttribute("emailSent", emailSent);
	}
	
	public String getAppointment() {
		return getString("appointment");
	}
	
	public void setAppointment(String appointment) {
		setAttribute("appointment", appointment);
	}
	
	public String getInterview() {
		return getString("interview");
	}
	
	public void setInterview(String interview) {
		setAttribute("interview", interview);
	}
	
	public String getMessages() {
		return getString("messages");
	}
	
	public void setMessages(String messages) {
		setAttribute("messages", messages);
	}
	
	public String getPhone() {
		return getString("phone");
	}
	
	public void setPhone(String phone) {
		setAttribute("phone", phone);
	}
	
	public String getNote() {
		return getString("note");
	}
	
	public void setNote(String note) {
		setAttribute("note", note);
	}
	
	public String getStatusMessage() {
		return getString("statusMessage");
	}
	
	public void setStatusMessage(String statusMessage) {
		setAttribute("statusMessage", statusMessage);
	}
	
	public String getSms() {
		return getString("sms");
	}
	
	public void setSms(String sms) {
		setAttribute("sms", sms);
	}
	
	public String getShortlisted() {
		return getString("shortlisted");
	}
	
	public void setShortlisted(String shortlisted) {
		setAttribute("shortlisted", shortlisted);
	}
	
	public String getOfferDetailModified() {
		return getString("offerDetailModified");
	}
	
	public void setOfferDetailModified(String offerDetailModified) {
		setAttribute("offerDetailModified", offerDetailModified);
	}
	
	public String getBlacklisted() {
		return getString("blacklisted");
	}
	
	public void setBlacklisted(String blacklisted) {
		setAttribute("blacklisted", blacklisted);
	}
	
	public String getUnblacklisted() {
		return getString("unblacklisted");
	}
	
	public void setUnblacklisted(String unblacklisted) {
		setAttribute("unblacklisted", unblacklisted);
	}
	
}
