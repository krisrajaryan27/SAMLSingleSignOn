/**
 * VendorService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.talentPool.services;

public interface VendorService extends java.rmi.Remote {
    public boolean isUserLoggedInFromDifferentIP(java.lang.String userId, java.lang.String ipAddress) throws java.rmi.RemoteException;
    public boolean isModuleRChilliIntegration() throws java.rmi.RemoteException;
    public void saveTimeZone(java.lang.String userId, java.lang.String timeZone) throws java.rmi.RemoteException;
    public java.lang.String isValidLogin(java.lang.String userName, java.lang.String password) throws java.rmi.RemoteException;
    public java.lang.String getInboxData() throws java.rmi.RemoteException;
    public boolean isDuplicate(java.lang.String sourceName, java.lang.String userId, java.lang.String userFirstName, java.lang.String positionId, java.lang.String positionName, java.util.HashMap nameValues) throws java.rmi.RemoteException;
    public void addUserIp(java.lang.String userId, java.lang.String ipAddress) throws java.rmi.RemoteException;
    public void removeUserIp(java.lang.String userId) throws java.rmi.RemoteException;
    public java.lang.String addApplicant(java.lang.String xml) throws java.rmi.RemoteException;
    public java.lang.String getIdsAndNames(java.lang.String type) throws java.rmi.RemoteException;
    public java.lang.String getCustomFields() throws java.rmi.RemoteException;
    public java.lang.String getPositionDetails(java.lang.String positionId) throws java.rmi.RemoteException;
    public java.lang.String getApplicants(boolean doShowApplicantsInProcess, java.lang.String sortBy, java.lang.String pageNo, int pageSize, java.lang.String userId, java.lang.String sourceId) throws java.rmi.RemoteException;
    public java.lang.String getApplicantDetails(java.lang.String applicantId, java.lang.String userId, java.lang.String sourceId) throws java.rmi.RemoteException;
    public java.lang.String getApplicantHistory(java.lang.String applicantId, java.lang.String userId, java.lang.String sourceId) throws java.rmi.RemoteException;
    public java.lang.String getCommunicationData(java.lang.String applicantId, java.lang.String communicationId, java.lang.String communicationType) throws java.rmi.RemoteException;
    public boolean setCommunication(java.lang.String applicantId, java.lang.String userId, java.lang.String communicationId, java.lang.String communicationType, java.lang.String note) throws java.rmi.RemoteException;
    public java.lang.String getApplicantResume(java.lang.String resumePath, java.lang.String applicantId) throws java.rmi.RemoteException;
    public java.lang.String getActivities(java.lang.String applicantId, java.lang.String positionId, java.lang.String noOfDays, java.lang.String pageNo, int pageSize, java.lang.String vendorId, java.lang.String sourceId) throws java.rmi.RemoteException;
    public java.lang.String changePassword(java.lang.String userId, java.lang.String oldPassword, java.lang.String newPassword, java.lang.String confirmNewPassword, java.lang.String securityQuestionId, java.lang.String securityAnswer, boolean forgotPassword) throws java.rmi.RemoteException;
    public java.lang.String getImportFieldList() throws java.rmi.RemoteException;
    public boolean isValidIPRequest(java.lang.String userId, java.lang.String ipAddress) throws java.rmi.RemoteException;
    public boolean sendConfirmationLink(java.lang.String emailAddress,java.lang.String requestUri) throws java.rmi.RemoteException;
    public java.lang.String getUserIfValidUUID(java.lang.String uuid) throws java.rmi.RemoteException;
    public java.lang.String getPositionFields() throws java.rmi.RemoteException;
    public java.lang.String getPositions(java.lang.String userId) throws java.rmi.RemoteException;
    public java.lang.String getPositionsLocation(java.lang.String location) throws java.rmi.RemoteException;
    public java.lang.String getPositionsSearch(java.lang.String positionTitle,java.lang.String userId) throws java.rmi.RemoteException;
    public java.lang.String getPositionsForFilter(java.lang.String userId,java.lang.String positionTitle,java.lang.String location) throws java.rmi.RemoteException;
    public boolean getApplicationProperty(java.lang.String property) throws java.rmi.RemoteException;
}
