/**
 * EmployeeServiceSoapBindingSkeleton.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.talentPool.services;

import java.rmi.RemoteException;

public class EmployeeServiceSoapBindingSkeleton implements com.talentPool.services.EmployeeService, org.apache.axis.wsdl.Skeleton {
    private com.talentPool.services.EmployeeService impl;
    private static java.util.Map _myOperations = new java.util.Hashtable();
    private static java.util.Collection _myOperationsList = new java.util.ArrayList();

    /**
    * Returns List of OperationDesc objects with this name
    */
    public static java.util.List getOperationDescByName(java.lang.String methodName) {
        return (java.util.List)_myOperations.get(methodName);
    }

    /**
    * Returns Collection of OperationDescs
    */
    public static java.util.Collection getOperationDescs() {
        return _myOperationsList;
    }

    static {
        org.apache.axis.description.OperationDesc _oper;
        org.apache.axis.description.FaultDesc _fault;
        org.apache.axis.description.ParameterDesc [] _params;
        _params = new org.apache.axis.description.ParameterDesc [] {
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "userName"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "firstName"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "lastName"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "password"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "emailAddress"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "homePhone"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "cellPhone"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "employeeCode"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "timeZone"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("createEmployeeAccount", _params, new javax.xml.namespace.QName("", "createEmployeeAccountReturn"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        _oper.setElementQName(new javax.xml.namespace.QName("urn:Employee", "createEmployeeAccount"));
        _oper.setSoapAction("");
        _myOperationsList.add(_oper);
        if (_myOperations.get("createEmployeeAccount") == null) {
            _myOperations.put("createEmployeeAccount", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("createEmployeeAccount")).add(_oper);
        _params = new org.apache.axis.description.ParameterDesc [] {
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "userName"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "firstName"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "lastName"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "emailAddress"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "homePhone"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "cellPhone"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "employeeCode"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "userId"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "sourceId"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "roleId"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "timeZone"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("updateEmployeeAccount", _params, new javax.xml.namespace.QName("", "updateEmployeeAccountReturn"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        _oper.setElementQName(new javax.xml.namespace.QName("urn:Employee", "updateEmployeeAccount"));
        _oper.setSoapAction("");
        _myOperationsList.add(_oper);
        if (_myOperations.get("updateEmployeeAccount") == null) {
            _myOperations.put("updateEmployeeAccount", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("updateEmployeeAccount")).add(_oper);
        _params = new org.apache.axis.description.ParameterDesc [] {
        };
        _oper = new org.apache.axis.description.OperationDesc("getPositionsScreenFiltersData", _params, new javax.xml.namespace.QName("", "getPositionsScreenFiltersDataReturn"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        _oper.setElementQName(new javax.xml.namespace.QName("urn:Employee", "getPositionsScreenFiltersData"));
        _oper.setSoapAction("");
        _myOperationsList.add(_oper);
        if (_myOperations.get("getPositionsScreenFiltersData") == null) {
            _myOperations.put("getPositionsScreenFiltersData", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("getPositionsScreenFiltersData")).add(_oper);
        _params = new org.apache.axis.description.ParameterDesc [] {
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "employeePortalProperties"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://xml.apache.org/xml-soap", "Map"), java.util.HashMap.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "updatedBy"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("updateEmployeePortalProperties", _params, new javax.xml.namespace.QName("", "updateEmployeePortalPropertiesReturn"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        _oper.setElementQName(new javax.xml.namespace.QName("urn:Employee", "updateEmployeePortalProperties"));
        _oper.setSoapAction("");
        _myOperationsList.add(_oper);
        if (_myOperations.get("updateEmployeePortalProperties") == null) {
            _myOperations.put("updateEmployeePortalProperties", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("updateEmployeePortalProperties")).add(_oper);
        _params = new org.apache.axis.description.ParameterDesc [] {
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "userId"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "ipAddress"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("isUserLoggedInFromDifferentIP", _params, new javax.xml.namespace.QName("", "isUserLoggedInFromDifferentIPReturn"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        _oper.setElementQName(new javax.xml.namespace.QName("urn:Employee", "isUserLoggedInFromDifferentIP"));
        _oper.setSoapAction("");
        _myOperationsList.add(_oper);
        if (_myOperations.get("isUserLoggedInFromDifferentIP") == null) {
            _myOperations.put("isUserLoggedInFromDifferentIP", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("isUserLoggedInFromDifferentIP")).add(_oper);
        _params = new org.apache.axis.description.ParameterDesc [] {
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "userName"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "userId"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "userPassword"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "oldPassword"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "securityQuestionId"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "securityAnswer"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "forgotPassword"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"), boolean.class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("updateEmployeePassword", _params, new javax.xml.namespace.QName("", "updateEmployeePasswordReturn"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        _oper.setElementQName(new javax.xml.namespace.QName("urn:Employee", "updateEmployeePassword"));
        _oper.setSoapAction("");
        _myOperationsList.add(_oper);
        if (_myOperations.get("updateEmployeePassword") == null) {
            _myOperations.put("updateEmployeePassword", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("updateEmployeePassword")).add(_oper);
        _params = new org.apache.axis.description.ParameterDesc [] {
        };
        _oper = new org.apache.axis.description.OperationDesc("isModuleRChilliIntegration", _params, new javax.xml.namespace.QName("", "isModuleRChilliIntegrationReturn"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        _oper.setElementQName(new javax.xml.namespace.QName("urn:Employee", "isModuleRChilliIntegration"));
        _oper.setSoapAction("");
        _myOperationsList.add(_oper);
        if (_myOperations.get("isModuleRChilliIntegration") == null) {
            _myOperations.put("isModuleRChilliIntegration", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("isModuleRChilliIntegration")).add(_oper);
        _params = new org.apache.axis.description.ParameterDesc [] {
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "userId"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "timeZone"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("saveTimeZone", _params, null);
        _oper.setElementQName(new javax.xml.namespace.QName("urn:Employee", "saveTimeZone"));
        _oper.setSoapAction("");
        _myOperationsList.add(_oper);
        if (_myOperations.get("saveTimeZone") == null) {
            _myOperations.put("saveTimeZone", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("saveTimeZone")).add(_oper);
        _params = new org.apache.axis.description.ParameterDesc [] {
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "userName"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "password"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("isValidLogin", _params, new javax.xml.namespace.QName("", "isValidLoginReturn"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        _oper.setElementQName(new javax.xml.namespace.QName("urn:Employee", "isValidLogin"));
        _oper.setSoapAction("");
        _myOperationsList.add(_oper);
        if (_myOperations.get("isValidLogin") == null) {
            _myOperations.put("isValidLogin", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("isValidLogin")).add(_oper);
        _params = new org.apache.axis.description.ParameterDesc [] {
        };
        
        _oper = new org.apache.axis.description.OperationDesc("getInboxData", _params, new javax.xml.namespace.QName("", "getInboxDataReturn"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        _oper.setElementQName(new javax.xml.namespace.QName("urn:Employee", "getInboxData"));
        _oper.setSoapAction("");
        _myOperationsList.add(_oper);
        if (_myOperations.get("getInboxData") == null) {
            _myOperations.put("getInboxData", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("getInboxData")).add(_oper);
        _params = new org.apache.axis.description.ParameterDesc [] {
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "sourceName"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "userId"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "userFirstName"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "positionId"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "positionName"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "nameValues"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://xml.apache.org/xml-soap", "Map"), java.util.HashMap.class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("isDuplicate", _params, new javax.xml.namespace.QName("", "isDuplicateReturn"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        _oper.setElementQName(new javax.xml.namespace.QName("urn:Employee", "isDuplicate"));
        _oper.setSoapAction("");
        _myOperationsList.add(_oper);
        if (_myOperations.get("isDuplicate") == null) {
            _myOperations.put("isDuplicate", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("isDuplicate")).add(_oper);
        _params = new org.apache.axis.description.ParameterDesc [] {
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "userId"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "ipAddress"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("addUserIp", _params, null);
        _oper.setElementQName(new javax.xml.namespace.QName("urn:Employee", "addUserIp"));
        _oper.setSoapAction("");
        _myOperationsList.add(_oper);
        if (_myOperations.get("addUserIp") == null) {
            _myOperations.put("addUserIp", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("addUserIp")).add(_oper);
        _params = new org.apache.axis.description.ParameterDesc [] {
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "userId"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("removeUserIp", _params, null);
        _oper.setElementQName(new javax.xml.namespace.QName("urn:Employee", "removeUserIp"));
        _oper.setSoapAction("");
        _myOperationsList.add(_oper);
        if (_myOperations.get("removeUserIp") == null) {
            _myOperations.put("removeUserIp", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("removeUserIp")).add(_oper);
        _params = new org.apache.axis.description.ParameterDesc [] {
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "xml"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("addApplicant", _params, new javax.xml.namespace.QName("", "addApplicantReturn"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        _oper.setElementQName(new javax.xml.namespace.QName("urn:Employee", "addApplicant"));
        _oper.setSoapAction("");
        _myOperationsList.add(_oper);
        if (_myOperations.get("addApplicant") == null) {
            _myOperations.put("addApplicant", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("addApplicant")).add(_oper);
        _params = new org.apache.axis.description.ParameterDesc [] {
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "positionId"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("getPositionDetails", _params, new javax.xml.namespace.QName("", "getPositionDetailsReturn"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        _oper.setElementQName(new javax.xml.namespace.QName("urn:Employee", "getPositionDetails"));
        _oper.setSoapAction("");
        _myOperationsList.add(_oper);
        if (_myOperations.get("getPositionDetails") == null) {
            _myOperations.put("getPositionDetails", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("getPositionDetails")).add(_oper);
        _params = new org.apache.axis.description.ParameterDesc [] {
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "type"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("getIdsAndNames", _params, new javax.xml.namespace.QName("", "getIdsAndNamesReturn"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        _oper.setElementQName(new javax.xml.namespace.QName("urn:Employee", "getIdsAndNames"));
        _oper.setSoapAction("");
        _myOperationsList.add(_oper);
        if (_myOperations.get("getIdsAndNames") == null) {
            _myOperations.put("getIdsAndNames", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("getIdsAndNames")).add(_oper);
        _params = new org.apache.axis.description.ParameterDesc [] {
        };
        _oper = new org.apache.axis.description.OperationDesc("getCustomFields", _params, new javax.xml.namespace.QName("", "getCustomFieldsReturn"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        _oper.setElementQName(new javax.xml.namespace.QName("urn:Employee", "getCustomFields"));
        _oper.setSoapAction("");
        _myOperationsList.add(_oper);
        if (_myOperations.get("getCustomFields") == null) {
            _myOperations.put("getCustomFields", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("getCustomFields")).add(_oper);
        _params = new org.apache.axis.description.ParameterDesc [] {
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "doShowApplicantsInProcess"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"), boolean.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "doShowApplicantsJoined"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"), boolean.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "doShowApplicantsRejected"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"), boolean.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "sortBy"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "pageNo"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "pageSize"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"), int.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "userId"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "sourceId"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "isEmployeeApply"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "position"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "location"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false), 
          
            };
        _oper = new org.apache.axis.description.OperationDesc("getApplicants", _params, new javax.xml.namespace.QName("", "getApplicantsReturn"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        _oper.setElementQName(new javax.xml.namespace.QName("urn:Employee", "getApplicants"));
        _oper.setSoapAction("");
        _myOperationsList.add(_oper);
        if (_myOperations.get("getApplicants") == null) {
            _myOperations.put("getApplicants", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("getApplicants")).add(_oper);
        
        _params = new org.apache.axis.description.ParameterDesc [] {
                new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "doShowApplicantsInProcess"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"), boolean.class, false, false), 
                new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "doShowApplicantsJoined"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"), boolean.class, false, false), 
                new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "doShowApplicantsRejected"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"), boolean.class, false, false), 
                new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "sortBy"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false), 
                new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "pageNo"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false), 
                new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "pageSize"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"), int.class, false, false), 
                new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "userId"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false), 
                new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "sourceId"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false), 
                new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "isEmployeeApply"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false), 
                new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "positionTitle"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false), 
                
                };
        _oper = new org.apache.axis.description.OperationDesc("getEmployeeDetail", _params, new javax.xml.namespace.QName("", "getEmployeeDetailReturn"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        _oper.setElementQName(new javax.xml.namespace.QName("urn:Employee", "getEmployeeDetail"));
        _oper.setSoapAction("");
        _myOperationsList.add(_oper);
        if (_myOperations.get("getEmployeeDetail") == null) {
            _myOperations.put("getEmployeeDetail", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("getEmployeeDetail")).add(_oper);
        
        
      
        _params = new org.apache.axis.description.ParameterDesc [] {
                new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "applicantName"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false), 
                
                };
        _oper = new org.apache.axis.description.OperationDesc("getApplicantNames", _params, new javax.xml.namespace.QName("", "getApplicantNamesReturn"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        _oper.setElementQName(new javax.xml.namespace.QName("urn:Employee", "getApplicantNames"));
        _oper.setSoapAction("");
        _myOperationsList.add(_oper);
        if (_myOperations.get("getApplicantNames") == null) {
            _myOperations.put("getApplicantNames", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("getApplicantNames")).add(_oper);
        
        _params = new org.apache.axis.description.ParameterDesc [] {
                new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "position"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false), 
                
                };
        _oper = new org.apache.axis.description.OperationDesc("getPositionsForSearch", _params, new javax.xml.namespace.QName("", "getPositionsForSearchReturn"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        _oper.setElementQName(new javax.xml.namespace.QName("urn:Employee", "getPositionsForSearch"));
        _oper.setSoapAction("");
        _myOperationsList.add(_oper);
        if (_myOperations.get("getPositionsForSearch") == null) {
            _myOperations.put("getPositionsForSearch", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("getPositionsForSearch")).add(_oper);
        
        
        _params = new org.apache.axis.description.ParameterDesc [] {
                new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "location"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false), 
                
                };
        _oper = new org.apache.axis.description.OperationDesc("getPositionsLocation", _params, new javax.xml.namespace.QName("", "getPositionsLocationReturn"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        _oper.setElementQName(new javax.xml.namespace.QName("urn:Employee", "getPositionsLocation"));
        _oper.setSoapAction("");
        _myOperationsList.add(_oper);
        if (_myOperations.get("getPositionsLocation") == null) {
            _myOperations.put("getPositionsLocation", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("getPositionsLocation")).add(_oper);
        
        
        _params = new org.apache.axis.description.ParameterDesc [] {
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "applicantId"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "userId"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "sourceId"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false), 
        };
        
        
        _oper = new org.apache.axis.description.OperationDesc("getApplicantDetails", _params, new javax.xml.namespace.QName("", "getApplicantDetailsReturn"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        _oper.setElementQName(new javax.xml.namespace.QName("urn:Employee", "getApplicantDetails"));
        _oper.setSoapAction("");
        _myOperationsList.add(_oper);
        if (_myOperations.get("getApplicantDetails") == null) {
            _myOperations.put("getApplicantDetails", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("getApplicantDetails")).add(_oper);
        _params = new org.apache.axis.description.ParameterDesc [] {
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "applicantId"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "userId"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "sourceId"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("getApplicantHistory", _params, new javax.xml.namespace.QName("", "getApplicantHistoryReturn"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        _oper.setElementQName(new javax.xml.namespace.QName("urn:Employee", "getApplicantHistory"));
        _oper.setSoapAction("");
        _myOperationsList.add(_oper);
        if (_myOperations.get("getApplicantHistory") == null) {
            _myOperations.put("getApplicantHistory", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("getApplicantHistory")).add(_oper);
        _params = new org.apache.axis.description.ParameterDesc [] {
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "resumePath"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "applicantId"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("getApplicantResume", _params, new javax.xml.namespace.QName("", "getApplicantResumeReturn"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        _oper.setElementQName(new javax.xml.namespace.QName("urn:Employee", "getApplicantResume"));
        _oper.setSoapAction("");
        _myOperationsList.add(_oper);
        if (_myOperations.get("getApplicantResume") == null) {
            _myOperations.put("getApplicantResume", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("getApplicantResume")).add(_oper);
        _params = new org.apache.axis.description.ParameterDesc [] {
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "applicantId"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "communicationId"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "communicationType"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("getCommunicationData", _params, new javax.xml.namespace.QName("", "getCommunicationDataReturn"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        _oper.setElementQName(new javax.xml.namespace.QName("urn:Employee", "getCommunicationData"));
        _oper.setSoapAction("");
        _myOperationsList.add(_oper);
        if (_myOperations.get("getCommunicationData") == null) {
            _myOperations.put("getCommunicationData", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("getCommunicationData")).add(_oper);
        _params = new org.apache.axis.description.ParameterDesc [] {
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "applicantId"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "userId"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "communicationId"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "communicationType"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "note"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("setCommunication", _params, new javax.xml.namespace.QName("", "setCommunicationReturn"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        _oper.setElementQName(new javax.xml.namespace.QName("urn:Employee", "setCommunication"));
        _oper.setSoapAction("");
        _myOperationsList.add(_oper);
        if (_myOperations.get("setCommunication") == null) {
            _myOperations.put("setCommunication", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("setCommunication")).add(_oper);
        _params = new org.apache.axis.description.ParameterDesc [] {
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "emailAddress"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false),
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "requestUri"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false),
        };
        _oper = new org.apache.axis.description.OperationDesc("sendNewPassword", _params, new javax.xml.namespace.QName("", "sendNewPasswordReturn"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        _oper.setElementQName(new javax.xml.namespace.QName("urn:Employee", "sendNewPassword"));
        _oper.setSoapAction("");
        _myOperationsList.add(_oper);
        if (_myOperations.get("sendNewPassword") == null) {
            _myOperations.put("sendNewPassword", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("sendNewPassword")).add(_oper);
        _params = new org.apache.axis.description.ParameterDesc [] {
        };
        _oper = new org.apache.axis.description.OperationDesc("getImportFieldList", _params, new javax.xml.namespace.QName("", "getImportFieldListReturn"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        _oper.setElementQName(new javax.xml.namespace.QName("urn:Employee", "getImportFieldList"));
        _oper.setSoapAction("");
        _myOperationsList.add(_oper);
        if (_myOperations.get("getImportFieldList") == null) {
            _myOperations.put("getImportFieldList", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("getImportFieldList")).add(_oper);
        _params = new org.apache.axis.description.ParameterDesc [] {
        };
        _oper = new org.apache.axis.description.OperationDesc("getAnnouncements", _params, new javax.xml.namespace.QName("", "getAnnouncementsReturn"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        _oper.setElementQName(new javax.xml.namespace.QName("urn:Employee", "getAnnouncements"));
        _oper.setSoapAction("");
        _myOperationsList.add(_oper);
        if (_myOperations.get("getAnnouncements") == null) {
            _myOperations.put("getAnnouncements", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("getAnnouncements")).add(_oper);
        _params = new org.apache.axis.description.ParameterDesc [] {
        };
		_oper = new org.apache.axis.description.OperationDesc("getHrTeamEmailId", _params, new javax.xml.namespace.QName("", "getHrTeamEmailIdReturn"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        _oper.setElementQName(new javax.xml.namespace.QName("urn:Employee", "getHrTeamEmailId"));
        _oper.setSoapAction("");
        _myOperationsList.add(_oper);
        if (_myOperations.get("getHrTeamEmailId") == null) {
            _myOperations.put("getHrTeamEmailId", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("getHrTeamEmailId")).add(_oper);
        _params = new org.apache.axis.description.ParameterDesc [] {
        };
        _oper = new org.apache.axis.description.OperationDesc("getEPortalSettings", _params, new javax.xml.namespace.QName("", "getEPortalSettingsReturn"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        _oper.setElementQName(new javax.xml.namespace.QName("urn:Employee", "getEPortalSettings"));
        _oper.setSoapAction("");
        _myOperationsList.add(_oper);
        if (_myOperations.get("getEPortalSettings") == null) {
            _myOperations.put("getEPortalSettings", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("getEPortalSettings")).add(_oper);
        _params = new org.apache.axis.description.ParameterDesc [] {
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "userId"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "ipAddress"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("isValidIPRequest", _params, new javax.xml.namespace.QName("", "isValidIPRequestReturn"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        _oper.setElementQName(new javax.xml.namespace.QName("urn:Employee", "isValidIPRequest"));
        _oper.setSoapAction("");
        _myOperationsList.add(_oper);
        if (_myOperations.get("isValidIPRequest") == null) {
            _myOperations.put("isValidIPRequest", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("isValidIPRequest")).add(_oper);
        
        _params = new org.apache.axis.description.ParameterDesc [] {
                new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "property"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false), 
            };
        _oper = new org.apache.axis.description.OperationDesc("getApplicationProperty", _params, new javax.xml.namespace.QName("", "getApplicationPropertyReturn"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        _oper.setElementQName(new javax.xml.namespace.QName("urn:Employee", "getApplicationProperty"));
        _oper.setSoapAction("");
        _myOperationsList.add(_oper);
        if (_myOperations.get("getApplicationProperty") == null) {
            _myOperations.put("getApplicationProperty", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("getApplicationProperty")).add(_oper);
        
        
        _params = new org.apache.axis.description.ParameterDesc [] {
                new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "property"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false), 
            };
        _oper = new org.apache.axis.description.OperationDesc("getApplicationPropertyNew", _params, new javax.xml.namespace.QName("", "getApplicationPropertyNewReturn"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        _oper.setElementQName(new javax.xml.namespace.QName("urn:Employee", "getApplicationPropertyNew"));
        _oper.setSoapAction("");
        _myOperationsList.add(_oper);
        if (_myOperations.get("getApplicationPropertyNew") == null) {
            _myOperations.put("getApplicationPropertyNew", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("getApplicationPropertyNew")).add(_oper);
        
        
        
       
        _params = new org.apache.axis.description.ParameterDesc [] {
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "uuid"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("getUserIfValidUUID", _params, new javax.xml.namespace.QName("", "getUserIfValidUUIDReturn"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        _oper.setElementQName(new javax.xml.namespace.QName("urn:Employee", "getUserIfValidUUID"));
        _oper.setSoapAction("");
        _myOperationsList.add(_oper);
        if (_myOperations.get("getUserIfValidUUID") == null) {
            _myOperations.put("getUserIfValidUUID", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("getUserIfValidUUID")).add(_oper);
        _params = new org.apache.axis.description.ParameterDesc [] {
        };
        _oper = new org.apache.axis.description.OperationDesc("getPositionFields", _params, new javax.xml.namespace.QName("", "getPositionFieldsReturn"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        _oper.setElementQName(new javax.xml.namespace.QName("urn:Employee", "getPositionFields"));
        _oper.setSoapAction("");
        _myOperationsList.add(_oper);
        if (_myOperations.get("getPositionFields") == null) {
            _myOperations.put("getPositionFields", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("getPositionFields")).add(_oper);
        
        _params = new org.apache.axis.description.ParameterDesc [] {
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "userId"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "sourceId"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "skillId"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "exp"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "applyOrReferFilter"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "sortBy"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "pageNo"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false), 
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "pageSize"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"), int.class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("getPositions", _params, new javax.xml.namespace.QName("", "getPositionsReturn"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        _oper.setElementQName(new javax.xml.namespace.QName("urn:Employee", "getPositions"));
        _oper.setSoapAction("");
        _myOperationsList.add(_oper);
        if (_myOperations.get("getPositions") == null) {
            _myOperations.put("getPositions", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("getPositions")).add(_oper);
        
        _params = new org.apache.axis.description.ParameterDesc [] {
                new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "userId"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false), 
                new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "sourceId"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false), 
                new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "skillId"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false), 
                new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "exp"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false), 
                new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "applyOrReferFilter"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false), 
                new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "sortBy"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false), 
                new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "pageNo"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false), 
                new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "pageSize"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"), int.class, false, false), 
                new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "position"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false), 
                new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "location"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false), 
        };
            _oper = new org.apache.axis.description.OperationDesc("getPositionsSearched", _params, new javax.xml.namespace.QName("", "getPositionsSearchedReturn"));
            _oper.setReturnType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
            _oper.setElementQName(new javax.xml.namespace.QName("urn:Employee", "getPositionsSearched"));
            _oper.setSoapAction("");
            _myOperationsList.add(_oper);
            if (_myOperations.get("getPositionsSearched") == null) {
                _myOperations.put("getPositionsSearched", new java.util.ArrayList());
            }
            ((java.util.List)_myOperations.get("getPositionsSearched")).add(_oper);
            
            
            
        _params = new org.apache.axis.description.ParameterDesc [] {
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "userId"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("getUserData", _params, new javax.xml.namespace.QName("", "getUserDataReturn"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        _oper.setElementQName(new javax.xml.namespace.QName("urn:Employee", "getUserData"));
        _oper.setSoapAction("");
        _myOperationsList.add(_oper);
        if (_myOperations.get("getUserData") == null) {
            _myOperations.put("getUserData", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("getUserData")).add(_oper);
        
        
      //Added to get the User Details from EmailID by Krishna
        _params = new org.apache.axis.description.ParameterDesc [] {
        		new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "emailID"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false),
        };
        _oper = new org.apache.axis.description.OperationDesc("getUserDataFromEmailID", _params, new javax.xml.namespace.QName("", "getUserDataFromEmailIDReturn"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        _oper.setElementQName(new javax.xml.namespace.QName("urn:Employee", "getUserDataFromEmailID"));
        _oper.setSoapAction("");
        _myOperationsList.add(_oper);
        if (_myOperations.get("getUserDataFromEmailID") == null) {
            _myOperations.put("getUserDataFromEmailID", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("getUserDataFromEmailID")).add(_oper);
        
        
        
        
        
    }

    public EmployeeServiceSoapBindingSkeleton() {
        this.impl = new com.talentPool.services.EmployeeServiceSoapBindingImpl();
    }

    public EmployeeServiceSoapBindingSkeleton(com.talentPool.services.EmployeeService impl) {
        this.impl = impl;
    }
    public java.lang.String createEmployeeAccount(java.lang.String userName, java.lang.String firstName, java.lang.String lastName, java.lang.String password, java.lang.String emailAddress, java.lang.String homePhone, java.lang.String cellPhone, java.lang.String employeeCode, java.lang.String timeZone) throws java.rmi.RemoteException
    {
        java.lang.String ret = impl.createEmployeeAccount(userName, firstName, lastName, password, emailAddress, homePhone, cellPhone, employeeCode, timeZone);
        return ret;
    }

    public java.lang.String updateEmployeeAccount(java.lang.String userName, java.lang.String firstName, java.lang.String lastName, java.lang.String emailAddress, java.lang.String homePhone, java.lang.String cellPhone, java.lang.String employeeCode, java.lang.String userId, java.lang.String sourceId, java.lang.String roleId, java.lang.String timeZone) throws java.rmi.RemoteException
    {
        java.lang.String ret = impl.updateEmployeeAccount(userName, firstName, lastName, emailAddress, homePhone, cellPhone, employeeCode, userId, sourceId, roleId, timeZone);
        return ret;
    }

    public java.lang.String getPositionsScreenFiltersData() throws java.rmi.RemoteException
    {
        java.lang.String ret = impl.getPositionsScreenFiltersData();
        return ret;
    }

    public boolean updateEmployeePortalProperties(java.util.HashMap employeePortalProperties, java.lang.String updatedBy) throws java.rmi.RemoteException
    {
        boolean ret = impl.updateEmployeePortalProperties(employeePortalProperties, updatedBy);
        return ret;
    }

    public boolean isUserLoggedInFromDifferentIP(java.lang.String userId, java.lang.String ipAddress) throws java.rmi.RemoteException
    {
        boolean ret = impl.isUserLoggedInFromDifferentIP(userId, ipAddress);
        return ret;
    }

    public java.lang.String updateEmployeePassword(java.lang.String userName, java.lang.String userId, java.lang.String userPassword, java.lang.String oldPassword, java.lang.String securityQuestionId, java.lang.String securityAnswer, boolean forgotPassword) throws java.rmi.RemoteException
    {
        java.lang.String ret = impl.updateEmployeePassword(userName, userId, userPassword, oldPassword, securityQuestionId, securityAnswer, forgotPassword);
        return ret;
    }

    public boolean isModuleRChilliIntegration() throws java.rmi.RemoteException
    {
        boolean ret = impl.isModuleRChilliIntegration();
        return ret;
    }

    public void saveTimeZone(java.lang.String userId, java.lang.String timeZone) throws java.rmi.RemoteException
    {
        impl.saveTimeZone(userId, timeZone);
    }

    public java.lang.String isValidLogin(java.lang.String userName, java.lang.String password) throws java.rmi.RemoteException
    {
        java.lang.String ret = impl.isValidLogin(userName, password);
        return ret;
    }

    public java.lang.String getInboxData() throws java.rmi.RemoteException
    {
        java.lang.String ret = impl.getInboxData();
        return ret;
    }

    public java.lang.String getHrTeamEmailId() throws java.rmi.RemoteException
    {
        java.lang.String ret = impl.getHrTeamEmailId();
        return ret;
    }
    public boolean isDuplicate(java.lang.String sourceName, java.lang.String userId, java.lang.String userFirstName, java.lang.String positionId, java.lang.String positionName, java.util.HashMap nameValues) throws java.rmi.RemoteException
    {
        boolean ret = impl.isDuplicate(sourceName, userId, userFirstName, positionId, positionName, nameValues);
        return ret;
    }

    public void addUserIp(java.lang.String userId, java.lang.String ipAddress) throws java.rmi.RemoteException
    {
        impl.addUserIp(userId, ipAddress);
    }

    public void removeUserIp(java.lang.String userId) throws java.rmi.RemoteException
    {
        impl.removeUserIp(userId);
    }

    public java.lang.String addApplicant(java.lang.String xml) throws java.rmi.RemoteException
    {
        java.lang.String ret = impl.addApplicant(xml);
        return ret;
    }

    public java.lang.String getPositionDetails(java.lang.String positionId) throws java.rmi.RemoteException
    {
        java.lang.String ret = impl.getPositionDetails(positionId);
        return ret;
    }

    public java.lang.String getIdsAndNames(java.lang.String type) throws java.rmi.RemoteException
    {
        java.lang.String ret = impl.getIdsAndNames(type);
        return ret;
    }

    public java.lang.String getCustomFields() throws java.rmi.RemoteException
    {
        java.lang.String ret = impl.getCustomFields();
        return ret;
    }

    public java.lang.String getApplicants(boolean doShowApplicantsInProcess, boolean doShowApplicantsJoined, boolean doShowApplicantsRejected, java.lang.String sortBy, java.lang.String pageNo, int pageSize, java.lang.String userId, java.lang.String sourceId,java.lang.String isEmployeeApply,java.lang.String position,java.lang.String location) throws java.rmi.RemoteException
    {
        java.lang.String ret = impl.getApplicants(doShowApplicantsInProcess, doShowApplicantsJoined, doShowApplicantsRejected, sortBy, pageNo, pageSize, userId, sourceId,isEmployeeApply,position,location);
        return ret;
    }
    

    
    public java.lang.String getApplicantDetails(java.lang.String applicantId, java.lang.String userId, java.lang.String sourceId) throws java.rmi.RemoteException
    {
        java.lang.String ret = impl.getApplicantDetails(applicantId, userId, sourceId);
        return ret;
    }

    public java.lang.String getApplicantHistory(java.lang.String applicantId, java.lang.String userId, java.lang.String sourceId) throws java.rmi.RemoteException
    {
        java.lang.String ret = impl.getApplicantHistory(applicantId, userId, sourceId);
        return ret;
    }

    public java.lang.String getApplicantResume(java.lang.String resumePath, java.lang.String applicantId) throws java.rmi.RemoteException
    {
        java.lang.String ret = impl.getApplicantResume(resumePath, applicantId);
        return ret;
    }

    public java.lang.String getCommunicationData(java.lang.String applicantId, java.lang.String communicationId, java.lang.String communicationType) throws java.rmi.RemoteException
    {
        java.lang.String ret = impl.getCommunicationData(applicantId, communicationId, communicationType);
        return ret;
    }

    public boolean setCommunication(java.lang.String applicantId, java.lang.String userId, java.lang.String communicationId, java.lang.String communicationType, java.lang.String note) throws java.rmi.RemoteException
    {
        boolean ret = impl.setCommunication(applicantId, userId, communicationId, communicationType, note);
        return ret;
    }

    public boolean sendNewPassword(java.lang.String emailAddress , java.lang.String requestUri) throws java.rmi.RemoteException
    {
        boolean ret = impl.sendNewPassword(emailAddress,requestUri);
        return ret;
    }

    public java.lang.String getImportFieldList() throws java.rmi.RemoteException
    {
        java.lang.String ret = impl.getImportFieldList();
        return ret;
    }

    public java.lang.String getAnnouncements() throws java.rmi.RemoteException
    {
        java.lang.String ret = impl.getAnnouncements();
        return ret;
    }

    public java.lang.String getEPortalSettings() throws java.rmi.RemoteException
    {
        java.lang.String ret = impl.getEPortalSettings();
        return ret;
    }

    public boolean isValidIPRequest(java.lang.String userId, java.lang.String ipAddress) throws java.rmi.RemoteException
    {
        boolean ret = impl.isValidIPRequest(userId, ipAddress);
        return ret;
    }
    
    public boolean getApplicationProperty(java.lang.String property) throws java.rmi.RemoteException
    {
        boolean ret = impl.getApplicationProperty(property);
        return ret;
    }
    
    public boolean getApplicationPropertyNew(java.lang.String property) throws java.rmi.RemoteException
    {
        boolean ret = impl.getApplicationPropertyNew(property);
        return ret;
    }
    
    public java.lang.String getUserIfValidUUID(java.lang.String uuid) throws java.rmi.RemoteException
    {
        java.lang.String ret = impl.getUserIfValidUUID(uuid);
        return ret;
    }

    public java.lang.String getPositionFields() throws java.rmi.RemoteException
    {
        java.lang.String ret = impl.getPositionFields();
        return ret;
    }

    public java.lang.String getPositions(java.lang.String userId, java.lang.String sourceId, java.lang.String skillId, java.lang.String exp, java.lang.String applyOrReferFilter, java.lang.String sortBy, java.lang.String pageNo, int pageSize) throws java.rmi.RemoteException
    {
        java.lang.String ret = impl.getPositions(userId, sourceId, skillId, exp, applyOrReferFilter, sortBy, pageNo, pageSize);
        return ret;
    }
    
    public java.lang.String getPositionsSearched(java.lang.String userId, java.lang.String sourceId, java.lang.String skillId, java.lang.String exp, java.lang.String applyOrReferFilter, java.lang.String sortBy, java.lang.String pageNo, int pageSize, java.lang.String position, java.lang.String location) throws java.rmi.RemoteException
    {
        java.lang.String ret = impl.getPositionsSearched(userId, sourceId, skillId, exp, applyOrReferFilter, sortBy, pageNo, pageSize,position,location);
        return ret;
    }
    public java.lang.String getUserData(java.lang.String userId) throws java.rmi.RemoteException
    {
        java.lang.String ret = impl.getUserData(userId);
        return ret;
    }
    
    public java.lang.String getEmployeeDetail(boolean doShowApplicantsInProcess, boolean doShowApplicantsJoined, boolean doShowApplicantsRejected, java.lang.String sortBy, java.lang.String pageNo, int pageSize, java.lang.String userId, java.lang.String sourceId,java.lang.String isEmployeeApply,java.lang.String positionTitle) throws java.rmi.RemoteException
    {
        java.lang.String ret = impl.getEmployeeDetail(doShowApplicantsInProcess, doShowApplicantsJoined, doShowApplicantsRejected, sortBy, pageNo, pageSize, userId, sourceId,isEmployeeApply,positionTitle);
        return ret;
    }
    
    public java.lang.String getApplicantNames(java.lang.String applicantName) throws java.rmi.RemoteException
    {
        java.lang.String ret = impl.getApplicantNames( applicantName);
        return ret;
    }
    
    public java.lang.String getPositionsForSearch(java.lang.String position) throws java.rmi.RemoteException
    {
        java.lang.String ret = impl.getPositionsForSearch( position);
        return ret;
    }
    
    public java.lang.String getPositionsLocation(java.lang.String location) throws java.rmi.RemoteException
    {
        java.lang.String ret = impl.getPositionsLocation( location);
        return ret;
    }
    
  //Added to get the User Details from EmailID by Krishna
	public java.lang.String getUserDataFromEmailID(java.lang.String emailID) throws java.rmi.RemoteException
	{
		java.lang.String ret=impl.getUserDataFromEmailID(emailID);
		return ret;
	}
}
