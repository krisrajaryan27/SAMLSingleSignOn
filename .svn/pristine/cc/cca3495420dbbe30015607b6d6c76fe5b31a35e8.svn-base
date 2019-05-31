/**
 * 
 */
package com.talentPool.employeeservice.utils;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.vendorservice.VendorServiceConstants;
import com.talentPool.vendorservice.dataobject.VapplicantData;
import com.talentPool.vendorservice.dataobject.VerrorData;
import com.talentPool.vendorservice.dataobject.VinboxData;
import com.talentPool.vendorservice.dataobject.VloginData;
import com.talentPool.vendorservice.dataobject.VpositionData;




/**
 * @author shivprasad
 * 
 */
public class VendorUnMarshaller extends CastorUnMarshaller {
	public VerrorData unmarshallErrorData(String xmlFile) {
		VerrorData verrorData = null;
		try {
			verrorData = (VerrorData) unmarshall(VendorServiceConstants.VERRORDATA_MAPPING, xmlFile, VerrorData.class);
		} catch (Exception e) {
			TPLogger.getLogger().error(e);
		}
		return verrorData;

	}

	public VloginData unMarshallLoginData(String xmlFile) {
		VloginData vLoginData = null;
		try {
			vLoginData = (VloginData) unmarshall(VendorServiceConstants.VLOGINDATA_MAPPING, xmlFile, VloginData.class);
		} catch (Exception e) {
			TPLogger.getLogger().error(e);
		}
		return vLoginData;
	}


	public VinboxData unmarshallInboxData(String xmlFile) {
		VinboxData vinboxData = null;
		try {
			vinboxData = (VinboxData) unmarshall(VendorServiceConstants.VINBOXDATA_MAPPING, xmlFile, VinboxData.class);
		} catch (Exception e) {
			TPLogger.getLogger().error(e);
		}
		return vinboxData;
	}

	public VpositionData unmarshallPositionData(String xmlFile) {
		VpositionData vpositionData = null;
		try {
			vpositionData = (VpositionData) unmarshall(VendorServiceConstants.VPOSITIONDATA_MAPPING, xmlFile, VpositionData.class);
		} catch (Exception e) {
			TPLogger.getLogger().error(e);
		}
		return vpositionData;
	}


	public VapplicantData unmarshallApplicantData(String xmlFile) {
		VapplicantData vapplicantData = null;
		try {
			vapplicantData = (VapplicantData) unmarshall(VendorServiceConstants.VAPPLICANTDATA_MAPPING, xmlFile, VapplicantData.class);
		} catch (Exception e) {
			TPLogger.getLogger().error(e);
		}
		return vapplicantData;
	}

	

	

}
