/**
 * WebsiteService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.talentPool.services;

public interface WebsiteService extends java.rmi.Remote {
    public java.lang.String getFieldsDataOnPositionList() throws java.rmi.RemoteException;
    public java.lang.String getSocialMediaSources() throws java.rmi.RemoteException;
    public java.lang.String fetchCandidateProfile(java.lang.String oAuthToken, java.lang.String source) throws java.rmi.RemoteException;
    public boolean isModuleRChilliIntegration() throws java.rmi.RemoteException;
    public boolean isModuleSocialNetwork() throws java.rmi.RemoteException;
    public java.lang.String getInboxData() throws java.rmi.RemoteException;
    public void registerStub() throws java.rmi.RemoteException;
    public java.lang.String addApplicant(java.lang.String xml) throws java.rmi.RemoteException;
    public java.lang.String addApplicantFromNaukri(java.lang.String xml) throws java.rmi.RemoteException;
    public java.lang.String getIdsAndNames(java.lang.String type) throws java.rmi.RemoteException;
    public java.lang.String getCustomFields() throws java.rmi.RemoteException;
    public java.lang.String getPositionDetails(java.lang.String positionId, java.lang.String timeZone) throws java.rmi.RemoteException;
    public java.lang.String getImportFieldList() throws java.rmi.RemoteException;
    public java.lang.String getPositionFieldList() throws java.rmi.RemoteException;
    public java.lang.String getWebsiteSettings() throws java.rmi.RemoteException;
    public java.lang.String getPositionFilters() throws java.rmi.RemoteException;
    public java.lang.String getItem(java.lang.String type) throws java.rmi.RemoteException;
    public java.lang.String getPositions(java.lang.String skillId, java.lang.String locationId, java.lang.String deptId, java.lang.String timeZone, java.lang.String geographyId) throws java.rmi.RemoteException;
    public java.lang.String getImportFieldListForWebsite() throws java.rmi.RemoteException;
}
