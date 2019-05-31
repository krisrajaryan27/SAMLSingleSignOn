/**
 * 
 */
package com.talentPool.employeeservice.utils;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.employeeservice.EmployeeServiceConstants;
import com.talentPool.employeeservice.dataobject.EPortalSettings;
import com.talentPool.employeeservice.dataobject.EapplicantData;
import com.talentPool.employeeservice.dataobject.EapplicantList;
import com.talentPool.employeeservice.dataobject.EcommunicationData;
import com.talentPool.employeeservice.dataobject.EcustomFieldDataList;
import com.talentPool.employeeservice.dataobject.EerrorData;
import com.talentPool.employeeservice.dataobject.EidsNames;
import com.talentPool.employeeservice.dataobject.EimportFieldList;
import com.talentPool.employeeservice.dataobject.EinboxData;
import com.talentPool.employeeservice.dataobject.EloginData;
import com.talentPool.employeeservice.dataobject.EpositionData;
import com.talentPool.employeeservice.dataobject.EpositionFilters;
import com.talentPool.employeeservice.dataobject.EpositionList;
import com.talentPool.user.UserConstants;
import com.talentPool.vendorservice.VendorServiceConstants;
import com.talentPool.vendorservice.dataobject.VerrorData;

/**
 * @author shivprasad
 * 
 */
public class EmployeeMarshaller extends CastorMarshaller {
	
	
	public String marshallErrorData(String errorMsg){
		String xmlFile = "";
		try{		
			EerrorData eerrorData =new EerrorData();
			eerrorData.putError(errorMsg);
			xmlFile = marshall(EmployeeServiceConstants.EERRORDATA_MAPPING, eerrorData);
		}catch (Exception e) {		
			TPLogger.getLogger().error(e);
		}
		return xmlFile;
	}

	/**
	 * @param errorData
	 * @return xmlFile as String
	 */
	public String marshallErrorData(EerrorData errorData) {
		String xmlFile = "";
		try {
			if (errorData != null) {
				xmlFile = marshall(EmployeeServiceConstants.EERRORDATA_MAPPING, errorData);
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(e);
		}
		return xmlFile;

	}
	
	public String marshallLoginData(EloginData employeeLoginData) {
		String xmlFile = "";
		try {
			if (employeeLoginData != null) {
				xmlFile = marshall(EmployeeServiceConstants.ELOGINDATA_MAPPING, employeeLoginData);				
			} else {
				EerrorData eerrorData = new EerrorData();
				eerrorData.putError("login.error.invalid_login");
				xmlFile = marshall(EmployeeServiceConstants.EERRORDATA_MAPPING, eerrorData);
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(e);
		}
		return xmlFile;
	}	
	
	
	public String marshallDataWhenUserDisabled(int status) {
		String xmlFile = "";
		try {
			VerrorData verrorData = new VerrorData();
			if (status != UserConstants.DEACTIVE){
				verrorData.putError("login.error.exceeded_login_atttempts");
			}
			verrorData.putError("login.error.user_disabled");
			xmlFile = marshall(VendorServiceConstants.VERRORDATA_MAPPING, verrorData);
		} catch (Exception e) {
			TPLogger.getLogger().error(e);
		}
		return xmlFile;
	}
	
	public String marshallRegistrationData(EloginData employeeLoginData) {
		String xmlFile = "";
		try {
			if (employeeLoginData != null) {
				xmlFile = marshall(EmployeeServiceConstants.ELOGINDATA_MAPPING, employeeLoginData);				
			} else {
				EerrorData eerrorData = new EerrorData();
				eerrorData.putError("login.error.invalid_login");
				xmlFile = marshall(EmployeeServiceConstants.EERRORDATA_MAPPING, eerrorData);
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(e);
		}
		return xmlFile;
	}	
	
	public String marshallPositionData(EpositionData epositionData) {
		String xmlFile = "";
		try {
			if (epositionData != null) {
				xmlFile = marshall(EmployeeServiceConstants.EPOSITIONDATA_MAPPING, epositionData);
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(e);
		}
		return xmlFile;
	}
	
	
	public String marshallPositionListData(EpositionList epositions) {
		String xmlFile = "";
		try {
			if (epositions != null) {
				xmlFile = marshall(EmployeeServiceConstants.EPOSITIONLIST_MAPPING, epositions);
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(e);
		}
		return xmlFile;
	}
	
	
	public String marshallIdNames(EidsNames eidsNames) {
		String xmlFile = "";
		try {
			if (eidsNames != null) {
				xmlFile = marshall(EmployeeServiceConstants.EIDSNAMES_MAPPING, eidsNames);
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(e);
		}
		return xmlFile;
	}
	
	public String marshallCustomFieldsList(EcustomFieldDataList ecustomFieldDataList) {
		String xmlFile = "";
		try {
			if (ecustomFieldDataList != null) {
				xmlFile = marshall(EmployeeServiceConstants.ECUSTOMFIELDDATALIST_MAPPING, ecustomFieldDataList);
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(e);
		}
		return xmlFile;
	}
	
	public String marshallInboxData(EinboxData einboxData) {
		String xmlFile = "";
		try {
			if (einboxData != null) {
				xmlFile = marshall(EmployeeServiceConstants.EINBOXDATA_MAPPING, einboxData);
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(e);
		}
		return xmlFile;
	}
	
	public String marshallApplicantListData(EapplicantList eapplicants) {
		String xmlFile = "";
		try {
			if (eapplicants != null) {
				xmlFile = marshall(EmployeeServiceConstants.EAPPLICANTLIST_MAPPING, eapplicants);
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(e);
		}
		return xmlFile;
	}
	public String marshallApplicantData(EapplicantData eapplicantData) {
		String xmlFile = "";
		try {
			if (eapplicantData != null) {
				xmlFile = marshall(EmployeeServiceConstants.EAPPLICANTDATA_MAPPING, eapplicantData);
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(e);
		}
		return xmlFile;
	}
	
	public String marshallCommunicationData(EcommunicationData ecommunicationData) {
		String xmlFile = "";
		try {
			if (ecommunicationData != null) {
				xmlFile = marshall(EmployeeServiceConstants.ECOMMUNICATIONDATA_MAPPING, ecommunicationData);
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(e);
		}
		return xmlFile;
	}
	
	public String marshallImportFieldList(EimportFieldList eimportFieldList) {
		String xmlFile = "";
		try {
			if (eimportFieldList != null) {
				xmlFile = marshall(EmployeeServiceConstants.EIMPORTFIELDLIST_MAPPING, eimportFieldList);
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(e);
		}
		return xmlFile;
	}
	
	public String marshallPositionScreenFilterData(EpositionFilters ePositionFilters) {
		String xmlFile = "";
		try {
			if (ePositionFilters != null) {
				xmlFile = marshall(EmployeeServiceConstants.EPOSITIONS_SCREEN_FILTERS_MAPPING, ePositionFilters);
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(e);
		}
		return xmlFile;
	}
	
	public String marshallEPortalSettings(EPortalSettings ePortalSettings){
		String xmlFile = "";
		try {
			if (ePortalSettings != null) {
				xmlFile = marshall(EmployeeServiceConstants.EPORTAL_SETTINGS, ePortalSettings);
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(e);
		}
		return xmlFile;
	}

	public String marshallPositionFieldList(EimportFieldList eimportFieldList) {
		String xmlFile = "";
		try {
			if (eimportFieldList != null) {
				xmlFile = marshall(EmployeeServiceConstants.EIMPORTFIELDLIST_MAPPING, eimportFieldList);
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(e);
		}
		return xmlFile;
	}
}
