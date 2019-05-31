/**
 * 
 */
package com.talentPool.websiteservice.utils;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.websiteservice.WebsiteServiceConstants;
import com.talentPool.websiteservice.dataobject.WapplicantData;
import com.talentPool.websiteservice.dataobject.WcustomFieldDataList;
import com.talentPool.websiteservice.dataobject.WebsiteSettingsData;
import com.talentPool.websiteservice.dataobject.WerrorData;
import com.talentPool.websiteservice.dataobject.WidsNames;
import com.talentPool.websiteservice.dataobject.WimportFieldData;
import com.talentPool.websiteservice.dataobject.WimportFieldList;
import com.talentPool.websiteservice.dataobject.WinboxData;
import com.talentPool.websiteservice.dataobject.WitemData;
import com.talentPool.websiteservice.dataobject.WpositionData;
import com.talentPool.websiteservice.dataobject.WpositionFieldList;
import com.talentPool.websiteservice.dataobject.WpositionFilters;
import com.talentPool.websiteservice.dataobject.WpositionList;
import com.talentPool.websiteservice.dataobject.WsocialMediaSourceList;

/**
 * @author shivprasad
 * 
 */
public class WebsiteMarshaller extends CastorMarshaller {
	public String marshallErrorData(WerrorData werrorData) {
		String xmlFile = "";
		try {
			if (werrorData != null) {
				xmlFile = marshall(WebsiteServiceConstants.WERRORDATA_MAPPING, werrorData);
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(e);
		}
		return xmlFile;

	}
	
	public String marshallIdNames(WidsNames widsNames) {
		String xmlFile = "";
		try {
			if (widsNames != null) {
				xmlFile = marshall(WebsiteServiceConstants.WIDSNAMES_MAPPING, widsNames);
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(e);
		}
		return xmlFile;
	}

	public String marshallCustomFieldsList(WcustomFieldDataList wcustomFieldDataList) {
		String xmlFile = "";
		try {
			if (wcustomFieldDataList != null) {
				xmlFile = marshall(WebsiteServiceConstants.WCUSTOMFIELDDATALIST_MAPPING, wcustomFieldDataList);
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(e);
		}
		return xmlFile;
	}

	public String marshallPositionList(WpositionList wpositionList) {
		String xmlFile = "";
		try {
			if (wpositionList != null) {
				xmlFile = marshall(WebsiteServiceConstants.WPOSITIONLIST_MAPPING, wpositionList);
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(e);
		}
		return xmlFile;
	}	
	
	public String marshallPositionData(WpositionData wpositionData) {
		String xmlFile = "";
		try {
			if (wpositionData != null) {
				xmlFile = marshall(WebsiteServiceConstants.WPOSITIONDATA_MAPPING, wpositionData);
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(e);
		}
		return xmlFile;
	}

	public String marshallImportFieldList(WimportFieldList wimportFieldList) {
		String xmlFile = "";
		try {
			if (wimportFieldList != null) {
				xmlFile = marshall(WebsiteServiceConstants.WIMPORTFIELDLIST_MAPPING, wimportFieldList);
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(e);
		}
		return xmlFile;
	}
	
	public String marshallImportFieldData(WimportFieldData wimportFieldData) {
		String xmlFile = "";
		try {
			if (wimportFieldData != null) {
				xmlFile = marshall(WebsiteServiceConstants.WIMPORTFIELDDATA_MAPPING, wimportFieldData);
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(e);
		}
		return xmlFile;
	}
	
	public String marshallInboxData(WinboxData winboxData) {
		String xmlFile = "";
		try {
			if (winboxData != null) {
				xmlFile = marshall(WebsiteServiceConstants.WINBOXDATA_MAPPING, winboxData);
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(e);
		}
		return xmlFile;
	}
	
	public String marshallItemData(WitemData witemData) {
		String xmlFile = "";
		try {
			if (witemData != null) {
				xmlFile = marshall(WebsiteServiceConstants.WITEMDATA_MAPPING, witemData);
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(e);
		}
		return xmlFile;
	}
	
	public String marshallPositionFieldList(WpositionFieldList wpositionFieldList) {
		String xmlFile = "";
		try {
			if (wpositionFieldList != null) {
				xmlFile = marshall(WebsiteServiceConstants.WPOSITIONFIELDLIST_MAPPING, wpositionFieldList);
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(e);
		}
		return xmlFile;
	}
	
	public String marshallWebsiteSettingsData(WebsiteSettingsData websiteSettingsData) {
		String xmlFile = "";
		try {
			if (websiteSettingsData != null) {
				xmlFile = marshall(WebsiteServiceConstants.WEBSITE_SETTINGS_MAPPING, websiteSettingsData);
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(e);
		}
		return xmlFile;
	}
	
	public String marshallPositionSkills(WpositionFilters wpositionFilters) {
		String xmlFile = "";
		try {
			if (wpositionFilters != null) {
				xmlFile = marshall(WebsiteServiceConstants.WEBSITE_POSITION_FILTERS_MAPPING, wpositionFilters);
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(e);
		}
		return xmlFile;
	}
	
	public String marshallSocialMediaData(WsocialMediaSourceList wsocialMediaSourceList) {
		String xmlFile = "";
		try {
			if (wsocialMediaSourceList != null) {
				xmlFile = marshall(WebsiteServiceConstants.WSOCIALMEDIASOURCELIST_MAPPING, wsocialMediaSourceList);
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(e);
		}
		return xmlFile;
	}
	
	public String marshallApplicantData(WapplicantData wapplicantData) {
		String xmlFile = "";
		try {
			if (wapplicantData != null) {
				xmlFile = marshall(WebsiteServiceConstants.WAPPLICANTDATA_MAPPING, wapplicantData);
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(e);
		}
		return xmlFile;
	}
}
