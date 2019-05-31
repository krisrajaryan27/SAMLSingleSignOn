/**
 * Created : Oct 10, 2013 12:50:54 PM 
 * @author : Sachinm
 */
package com.talentPool.reports.dataobject;

import com.talentPool.common.db.SimpleDataObject;

/**
 * @author Sachinm
 *
 */
public class CustomizedReportData extends SimpleDataObject {

	private static final long serialVersionUID = 1L;
	
	/**
	 * @return the reportId
	 */
	public String getReportId() {		
		return getString("reportId");
		
	}

	/**
	 * @param reportId the reportId to set
	 */
	public void setReportId(String reportId) {
		setAttribute("reportId", reportId);
	}

	/**
	 * @return the reportName
	 */
	public String getReportName() {		
		return getString("reportName");
		
	}

	/**
	 * @param reportName the reportName to set
	 */
	public void setReportName(String reportName) {
		setAttribute("reportName", reportName);
	}
	
	/**
	 * @return the reportLabel
	 */
	public String getReportLabel() {		
		return getString("reportLabel");
		
	}

	/**
	 * @param reportLabel the reportLabel to set
	 */
	public void setReportLabel(String reportLabel) {
		setAttribute("reportLabel", reportLabel);
	}
	
	/**
	 * @return the reportJrxml
	 */
	public String getReportJrxml() {		
		return getString("reportJrxml");
		
	}

	/**
	 * @param reportJrxml the reportJrxml to set
	 */
	public void setReportJrxml(String reportJrxml) {
		setAttribute("reportJrxml", reportJrxml);
	}
	
	/**
	 * @return the reportJsp
	 */
	public String getReportJsp() {		
		return getString("reportJsp");
		
	}

	/**
	 * @param reportJsp the reportJsp to set
	 */
	public void setReportJsp(String reportJsp) {
		setAttribute("reportJsp", reportJsp);
	}
}
