package com.talentPool.reports.views;
import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.db.SimpleDataObject;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.utils.DateUtils;

public class importReportView extends SimpleDataObject {

	 public importReportView(){
		 super();
	 }
	 
	 public String getApplicantName(){
			return getString("applicantName");
		}
	 
	 public String getDateCreated() {
		 try {
			return DateUtils.getSystemDateFormat(getDate("dateCreated"));
		} catch (ClassCastException cce) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, cce);
		}
		return null;
	}
	 
	 public String getUserName(){
		 return getString("userName");
	 }
	 
	 public String getSourceTitle(){
		 return getString("sourceTitle");
	 }
	 
	 
	 
	 
}
 