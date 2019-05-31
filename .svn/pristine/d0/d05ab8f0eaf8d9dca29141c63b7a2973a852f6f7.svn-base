/**
 * VendorServiceServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.talentPool.services;

public class VendorServiceServiceLocator extends org.apache.axis.client.Service implements com.talentPool.services.VendorServiceService {

    public VendorServiceServiceLocator() {
    }


    public VendorServiceServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public VendorServiceServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for VendorService
    private java.lang.String VendorService_address = "http://localhost:8080/tb350/services/VendorService";

    public java.lang.String getVendorServiceAddress() {
        return VendorService_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String VendorServiceWSDDServiceName = "VendorService";

    public java.lang.String getVendorServiceWSDDServiceName() {
        return VendorServiceWSDDServiceName;
    }

    public void setVendorServiceWSDDServiceName(java.lang.String name) {
        VendorServiceWSDDServiceName = name;
    }

    public com.talentPool.services.VendorService getVendorService() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(VendorService_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getVendorService(endpoint);
    }

    public com.talentPool.services.VendorService getVendorService(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.talentPool.services.VendorServiceSoapBindingStub _stub = new com.talentPool.services.VendorServiceSoapBindingStub(portAddress, this);
            _stub.setPortName(getVendorServiceWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setVendorServiceEndpointAddress(java.lang.String address) {
        VendorService_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.talentPool.services.VendorService.class.isAssignableFrom(serviceEndpointInterface)) {
                com.talentPool.services.VendorServiceSoapBindingStub _stub = new com.talentPool.services.VendorServiceSoapBindingStub(new java.net.URL(VendorService_address), this);
                _stub.setPortName(getVendorServiceWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("VendorService".equals(inputPortName)) {
            return getVendorService();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("urn:Vendor", "VendorServiceService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("urn:Vendor", "VendorService"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("VendorService".equals(portName)) {
            setVendorServiceEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
