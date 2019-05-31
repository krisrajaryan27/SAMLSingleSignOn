/**
 * 
 */
package com.talentPool.vendorservice.utils;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.employeeservice.EmployeeServiceConstants;
import com.talentPool.employeeservice.dataobject.EapplicantData;
import com.talentPool.vendorservice.VendorServiceConstants;
import com.talentPool.vendorservice.dataobject.VactivityList;
import com.talentPool.vendorservice.dataobject.VapplicantData;
import com.talentPool.vendorservice.dataobject.VapplicantList;
import com.talentPool.vendorservice.dataobject.VcommunicationData;
import com.talentPool.vendorservice.dataobject.VcustomFieldDataList;
import com.talentPool.vendorservice.dataobject.VerrorData;
import com.talentPool.vendorservice.dataobject.VidsNames;
import com.talentPool.vendorservice.dataobject.VimportFieldData;
import com.talentPool.vendorservice.dataobject.VimportFieldList;
import com.talentPool.vendorservice.dataobject.VinboxData;
import com.talentPool.vendorservice.dataobject.VloginData;
import com.talentPool.vendorservice.dataobject.VpositionData;

/**
 * @author shivprasad
 * 
 */
public class VendorUnMarshaller extends CastorUnMarshaller {
	

	public VloginData unMarshallLoginData(String xmlFile) {
		VloginData vLoginData = null;
		try {
			vLoginData = (VloginData) unmarshall(VendorServiceConstants.VLOGINDATA_MAPPING, xmlFile, VloginData.class);
		} catch (Exception e) {
			TPLogger.getLogger().error(e);
		}
		return vLoginData;
	}

	public VapplicantData unMarshallApplicantData(String xmlFile) {		
		VapplicantData vapplicantData = null;
		try {
			vapplicantData = (VapplicantData) unmarshall(VendorServiceConstants.VAPPLICANTDATA_MAPPING, xmlFile, VapplicantData.class);
		} catch (Exception e) {
			TPLogger.getLogger().error(e);
			vapplicantData = null;
		}
		return vapplicantData;
	}

}
