/**
 * 
 */
package com.talentPool.reportDesign.form;

import org.apache.struts.upload.FormFile;

import com.talentPool.common.base.TPActionForm;

/**
 * @author Ajeet
 *
 */
public class ReportDesignForm extends TPActionForm {

	private static final long serialVersionUID = 1L;
	private String reportId;
	private String reportCategory;
	private String reportType;
	private String reportName;
	private String reportFormat;
	private String columns;
	private String groupBy;
	private String filters;
	private String sortBy;
	private String sortWith;
	private String levelPermissions;
	private String isSharedReport;
	private String reportDescription;
	private FormFile attachedFile;
	private String reportFilePath;
	private String sheetIndex;
	private String rowIndex;
	
	
	/**
	 * @return the reportId
	 */
	public String getReportId() {
		return reportId;
	}

	/**
	 * @param reportId the reportId to set
	 */
	public void setReportId(String reportId) {
		this.reportId = reportId;
	}

	/**
	 * @return the reportType
	 */
	public String getReportType() {
		return reportType;
	}

	/**
	 * @param reportType the reportType to set
	 */
	public void setReportType(String reportType) {
		this.reportType = reportType;
	}

	/**
	 * @return the columns
	 */
	public String getColumns() {
		return columns;
	}

	/**
	 * @param columns the columns to set
	 */
	public void setColumns(String columns) {
		this.columns = columns;
	}

	/**
	 * @return the groupBy
	 */
	public String getGroupBy() {
		return groupBy;
	}

	/**
	 * @param groupBy the groupBy to set
	 */
	public void setGroupBy(String groupBy) {
		this.groupBy = groupBy;
	}

	/**
	 * @return the filters
	 */
	public String getFilters() {
		return filters;
	}

	/**
	 * @param filters the filters to set
	 */
	public void setFilters(String filters) {
		this.filters = filters;
	}

	/**
	 * @return the reportName
	 */
	public String getReportName() {
		return reportName;
	}

	/**
	 * @param reportName the reportName to set
	 */
	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	/**
	 * @return the reportCategory
	 */
	public String getReportCategory() {
		return reportCategory;
	}

	/**
	 * @param reportCategory the reportCategory to set
	 */
	public void setReportCategory(String reportCategory) {
		this.reportCategory = reportCategory;
	}

	/**
	 * @return the reportFormat
	 */
	public String getReportFormat() {
		return reportFormat;
	}

	/**
	 * @param reportFormat the reportFormat to set
	 */
	public void setReportFormat(String reportFormat) {
		this.reportFormat = reportFormat;
	}

	/**
	 * @return the sortBy
	 */
	public String getSortBy() {
		return sortBy;
	}

	/**
	 * @param sortBy the sortBy to set
	 */
	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}

	/**
	 * @return the sortWith
	 */
	public String getSortWith() {
		return sortWith;
	}

	/**
	 * @param sortWith the sortWith to set
	 */
	public void setSortWith(String sortWith) {
		this.sortWith = sortWith;
	}

	public String getLevelPermissions() {
		return levelPermissions;
	}

	public void setLevelPermissions(String levelPermissions) {
		this.levelPermissions = levelPermissions;
	}

	public String getReportDescription() {
		return reportDescription;
	}

	public void setReportDescription(String reportDescription) {
		this.reportDescription = reportDescription;
	}

	public String getIsSharedReport() {
		return isSharedReport;
	}

	public void setIsSharedReport(String isSharedReport) {
		this.isSharedReport = isSharedReport;
	}

	public FormFile getAttachedFile() {
		return attachedFile;
	}

	public void setAttachedFile(FormFile attachedFile) {
		this.attachedFile = attachedFile;
	}

	public String getReportFilePath() {
		return reportFilePath;
	}

	public void setReportFilePath(String reportFilePath) {
		this.reportFilePath = reportFilePath;
	}

	public String getSheetIndex() {
		return sheetIndex;
	}

	public void setSheetIndex(String sheetIndex) {
		this.sheetIndex = sheetIndex;
	}

	public String getRowIndex() {
		return rowIndex;
	}

	public void setRowIndex(String rowIndex) {
		this.rowIndex = rowIndex;
	}	
	
	
}
