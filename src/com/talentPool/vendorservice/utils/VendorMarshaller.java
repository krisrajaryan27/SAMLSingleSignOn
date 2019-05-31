/**
 * 
 */
package com.talentPool.vendorservice.utils;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.user.UserConstants;
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
public class VendorMarshaller extends CastorMarshaller {
	public String marshallErrorData(VerrorData verrorData) {
		String xmlFile = "";
		try {
			if (verrorData != null) {
				xmlFile = marshall(VendorServiceConstants.VERRORDATA_MAPPING, verrorData);
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(e);
		}
		return xmlFile;

	}

	public String marshallLoginData(VloginData vloginData) {
		String xmlFile = "";
		try {
			if (vloginData != null) {
				xmlFile = marshall(VendorServiceConstants.VLOGINDATA_MAPPING, vloginData);
			} else {
				VerrorData verrorData = new VerrorData();
				verrorData.putError("login.error.invalid_login");
				xmlFile = marshall(VendorServiceConstants.VERRORDATA_MAPPING, verrorData);
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

	public String marshallIdNames(VidsNames vidsNames) {
		String xmlFile = "";
		try {
			if (vidsNames != null) {
				xmlFile = marshall(VendorServiceConstants.VIDSNAMES_MAPPING, vidsNames);
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(e);
		}
		return xmlFile;
	}

	public String marshallCustomFieldsList(VcustomFieldDataList vcustomFieldDataList) {
		String xmlFile = "";
		try {
			if (vcustomFieldDataList != null) {
				xmlFile = marshall(VendorServiceConstants.VCUSTOMFIELDDATALIST_MAPPING, vcustomFieldDataList);
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(e);
		}
		return xmlFile;
	}

	public String marshallInboxData(VinboxData vinboxData) {
		String xmlFile = "";
		try {
			if (vinboxData != null) {
				xmlFile = marshall(VendorServiceConstants.VINBOXDATA_MAPPING, vinboxData);
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(e);
		}
		return xmlFile;
	}

	public String marshallPositionData(VpositionData vpositionData) {
		String xmlFile = "";
		try {
			if (vpositionData != null) {
				xmlFile = marshall(VendorServiceConstants.VPOSITIONDATA_MAPPING, vpositionData);
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(e);
		}
		return xmlFile;
	}

	public String marshallApplicantListData(VapplicantList vapplicants) {
		String xmlFile = "";
		try {
			if (vapplicants != null) {
				xmlFile = marshall(VendorServiceConstants.VAPPLICANTLIST_MAPPING, vapplicants);
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(e);
		}
		return xmlFile;
	}

	public String marshallApplicantData(VapplicantData vapplicantData) {
		String xmlFile = "";
		try {
			if (vapplicantData != null) {
				xmlFile = marshall(VendorServiceConstants.VAPPLICANTDATA_MAPPING, vapplicantData);
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(e);
		}
		return xmlFile;
	}

	public String marshallCommunicationData(VcommunicationData vcommunicationData) {
		String xmlFile = "";
		try {
			if (vcommunicationData != null) {
				xmlFile = marshall(VendorServiceConstants.VCOMMUNICATIONDATA_MAPPING, vcommunicationData);
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(e);
		}
		return xmlFile;
	}
	
	public String marshallActivityListData(VactivityList vactivityList) {
		String xmlFile = "";
		try {
			if (vactivityList != null) {
				xmlFile = marshall(VendorServiceConstants.VACTIVITYLIST_MAPPING, vactivityList);
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(e);
		}
		return xmlFile;
	}

	public String marshallImportFieldList(VimportFieldList vimportFieldList) {
		String xmlFile = "";
		try {
			if (vimportFieldList != null) {
				xmlFile = marshall(VendorServiceConstants.VIMPORTFIELDLIST_MAPPING, vimportFieldList);
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(e);
		}
		return xmlFile;
	}
	
	public String marshallImportFieldData(VimportFieldData vimportFieldData) {
		String xmlFile = "";
		try {
			if (vimportFieldData != null) {
				xmlFile = marshall(VendorServiceConstants.VIMPORTFIELDDATA_MAPPING, vimportFieldData);
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(e);
		}
		return xmlFile;
	}
	
	public String marshallPositionFieldList(VimportFieldList vimportFieldList) {
		String xmlFile = "";
		try {
			if (vimportFieldList != null) {
				xmlFile = marshall(VendorServiceConstants.VIMPORTFIELDLIST_MAPPING, vimportFieldList);
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(e);
		}
		return xmlFile;
	}
}
