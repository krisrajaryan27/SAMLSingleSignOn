package com.talentPool.employeeservice.utils;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.employeeservice.EmployeeServiceConstants;
import com.talentPool.employeeservice.dataobject.EapplicantData;
import com.talentPool.employeeservice.dataobject.EerrorData;

public class EmployeeUnMarshaller extends CastorUnMarshaller {
	public EerrorData unmarshallErrorData(String xmlFile) {
		EerrorData eerrorData = null;
		try {
			eerrorData = (EerrorData) unmarshall(EmployeeServiceConstants.EERRORDATA_MAPPING, xmlFile, EerrorData.class);
		} catch (Exception e) {
			TPLogger.getLogger().error(e);
		}
		return eerrorData;
	}
	
	public EapplicantData unMarshallApplicantData(String xmlFile) {		
		EapplicantData eapplicantData = null;
		try {
			eapplicantData = (EapplicantData) unmarshall(EmployeeServiceConstants.EAPPLICANTDATA_MAPPING, xmlFile, EapplicantData.class);
		} catch (Exception e) {
			TPLogger.getLogger().error(e);
			eapplicantData = null;
		}
		return eapplicantData;
	}
	
}
