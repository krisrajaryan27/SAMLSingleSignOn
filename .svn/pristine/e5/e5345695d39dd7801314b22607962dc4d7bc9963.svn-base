package com.talentPool.dynamicReports.generator;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.dynamicReports.data.DynamicReportsDataSource;
import com.talentPool.dynamicReports.utils.DynamicReportsColumnUtils;
import com.talentPool.reportDesign.constants.ReportDesignConstants;
import com.talentPool.reportDesign.utils.ColumnUtils;
import com.talentPool.reports.ReportVersionConstants;
import com.talentPool.reports.dataobject.CandidateOneStopFileReportData;

import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.builders.ColumnBuilderException;
import ar.com.fdvs.dj.domain.builders.FastReportBuilder;
import net.sf.jasperreports.engine.JRDataSource;

public class CandidateOneStopFileReport extends DynamicReportGenerator{
	private List<CandidateOneStopFileReportData> candidateOneStopFileReportData = null;
	private String filterCriteria = null;
	
	public CandidateOneStopFileReport(HttpServletRequest request,
			String filePathname, String exportTo) {
		super(request, filePathname, exportTo);
	}

	public CandidateOneStopFileReport(HttpServletRequest request, String filePathname, String exportTo,
			List<CandidateOneStopFileReportData> candidateOneStopFileReportData,
			String filterCriteria) {
		super(request, filePathname, exportTo);
		this.candidateOneStopFileReportData = candidateOneStopFileReportData;
		this.filterCriteria=filterCriteria;
	}

	@Override
	public DynamicReport buildReport() throws Exception {
FastReportBuilder drb = new FastReportBuilder();
		
		Map<String,String> madFieldsMap = DynamicReportsColumnUtils.getMandatoryFields(null,
				ReportVersionConstants.ONE_STOP_FILE_REPORT, null);
		addMandatoryColumns(drb, madFieldsMap);
		setReportDefaultProperties(drb,ReportVersionConstants.getReportTitle(ReportVersionConstants.ONE_STOP_FILE_REPORT), 
				this.filterCriteria);
		return drb.build();
	}

	private void addMandatoryColumns(FastReportBuilder drb, Map<String, String> madFieldsMap) {
		int minColWidth = 50;
		int maxColWidth = 200;
		try {
			drb.addColumn(ColumnUtils.getColumnLabel(ReportDesignConstants.REPORT_SERIAL_NUMBER),"srNo" ,String.class.getName(),minColWidth);
			drb.addColumn(ColumnUtils.getColumnLabel(ReportDesignConstants.COLUMN_REQUISITIONER_NUMBER), "requisitionNumber", String.class.getName(),maxColWidth);	
			drb.addColumn(ColumnUtils.getColumnLabel(ReportDesignConstants.COLUMN_REQUISITION_RAISE_DATE),"requisitionRaiseDate", String.class.getName(),maxColWidth);
			drb.addColumn(ColumnUtils.getColumnLabel(ReportDesignConstants.COLUMN_DEPARTMENT_SUB),"subBU", String.class.getName(),maxColWidth);	
			drb.addColumn(ColumnUtils.getColumnLabel(ReportDesignConstants.COLUMN_HIRE_TYPE), "hireType", String.class.getName(),maxColWidth);
			drb.addColumn(ColumnUtils.getColumnLabel(ReportDesignConstants.COLUMN_POSITION_DATE_OPENED), "requisitionAllocationDate", String.class.getName(),maxColWidth);	
			drb.addColumn(ColumnUtils.getColumnLabel(ReportDesignConstants.COLUMN_CANDIDATE_SOURCE), "source", String.class.getName(),maxColWidth);
			drb.addColumn(ColumnUtils.getColumnLabel(ReportDesignConstants.COLUMN_CANDIDATE_SOURCE_TYPE),"sourceName", String.class.getName(),maxColWidth);	
			drb.addColumn(ColumnUtils.getColumnLabel(ReportDesignConstants.COLUMN_CANDIDATE_NAME), "candidateName", String.class.getName(),maxColWidth);
			drb.addColumn(ColumnUtils.getColumnLabel(ReportDesignConstants.COLUMN_CANDIDATE_GENDER), "gender", String.class.getName(),maxColWidth);
			drb.addColumn(ColumnUtils.getColumnLabel(ReportDesignConstants.COLUMN_CANDIDATE_DEGREE), "qualification", String.class.getName(),maxColWidth);	
			drb.addColumn(ColumnUtils.getColumnLabel(ReportDesignConstants.COLUMN_CANDIDATE_SKILLS), "primarySkills", String.class.getName(),maxColWidth);
			drb.addColumn(ColumnUtils.getColumnLabel(ReportDesignConstants.COLUMN_CANDIDATE_NOTICE_PERIOD), "noticePeriod", String.class.getName(),maxColWidth);
			drb.addColumn(ColumnUtils.getColumnLabel(ReportDesignConstants.COLUMN_INTERVIEW_SCORE), "interviewScore", String.class.getName(),maxColWidth);	
			drb.addColumn(ColumnUtils.getColumnLabel(ReportDesignConstants.COLUMN_COMMUNICATION_SCORE), "communicationScore", String.class.getName(),maxColWidth);	
			drb.addColumn(ColumnUtils.getColumnLabel(ReportDesignConstants.COLUMN_LATERAL_CAMPUS_INTERN), "lateralCampusIntern", String.class.getName(),maxColWidth);
			drb.addColumn(ColumnUtils.getColumnLabel(ReportDesignConstants.COLUMN_LOCATION), "location", String.class.getName(),maxColWidth);
			drb.addColumn(ColumnUtils.getColumnLabel(ReportDesignConstants.COLUMN_LOCAL_OUTSTATION),"localOutstation", String.class.getName(),maxColWidth);
			drb.addColumn(ColumnUtils.getColumnLabel(ReportDesignConstants.COLUMN_LAST_COMPANY), "lastCompany", String.class.getName(),maxColWidth);	
			drb.addColumn(ColumnUtils.getColumnLabel(ReportDesignConstants.COLUMN_TIER), "tier", String.class.getName(),maxColWidth);
			drb.addColumn(ColumnUtils.getColumnLabel(ReportDesignConstants.COLUMN_CONTACT_NO), "contactDetails", String.class.getName(),maxColWidth);
			drb.addColumn(ColumnUtils.getColumnLabel(ReportDesignConstants.COLUMN_CANDIDATE_DESIGNATION_OFFERED), "offeredDesignation", String.class.getName(),maxColWidth);	
			drb.addColumn(ColumnUtils.getColumnLabel(ReportDesignConstants.COLUMN_CANDIDATE_EXPERIENCE), "experience", String.class.getName(),maxColWidth);
			drb.addColumn(ColumnUtils.getColumnLabel(ReportDesignConstants.COLUMN_CANDIDATE_CURRENT_CTC), "currentCTC", String.class.getName(),maxColWidth);	
			drb.addColumn(ColumnUtils.getColumnLabel(ReportDesignConstants.COLUMN_CANDIDATE_OFFERED_CTC), "offeredCTC", String.class.getName(),maxColWidth);
			drb.addColumn(ColumnUtils.getColumnLabel(ReportDesignConstants.COLUMN_HIKE_PERCENT), "hikePercent", String.class.getName(),maxColWidth);	
			drb.addColumn(ColumnUtils.getColumnLabel(ReportDesignConstants.COLUMN_NOTICE_PERIOD_BUY_OUT_DAYS), "noticePeriodBuyOutDays", String.class.getName(),maxColWidth);	
			drb.addColumn(ColumnUtils.getColumnLabel(ReportDesignConstants.COLUMN_JOINING_BONUS), "joiningBonus", String.class.getName(),maxColWidth);
			drb.addColumn(ColumnUtils.getColumnLabel(ReportDesignConstants.COLUMN_CONTRACTOR_COST), "contractorCost", String.class.getName(),maxColWidth);	
			drb.addColumn(ColumnUtils.getColumnLabel(ReportDesignConstants.COLUMN_RECRUITER_NAME), "recruiterName", String.class.getName(),maxColWidth);
			drb.addColumn(ColumnUtils.getColumnLabel(ReportDesignConstants.COLUMN_BAND),"band", String.class.getName(),maxColWidth);
			drb.addColumn(ColumnUtils.getColumnLabel(ReportDesignConstants.COLUMN_OFFERED_APPROVAL_DATE),"offeredApprovalDate", String.class.getName(),maxColWidth);	
			drb.addColumn(ColumnUtils.getColumnLabel(ReportDesignConstants.COLUMN_OFFERED_DATE), "offeredDate", String.class.getName(),maxColWidth);
			drb.addColumn(ColumnUtils.getColumnLabel(ReportDesignConstants.COLUMN_EXPECTED_DOJ), "expectedDOJ", String.class.getName(),maxColWidth);	
			drb.addColumn(ColumnUtils.getColumnLabel(ReportDesignConstants.COLUMN_EMP_ID), "empID", String.class.getName(),maxColWidth);
			drb.addColumn(ColumnUtils.getColumnLabel(ReportDesignConstants.COLUMN_DOJ), "doj", String.class.getName(),maxColWidth);	
			drb.addColumn(ColumnUtils.getColumnLabel(ReportDesignConstants.COLUMN_REQUISITION_STATUS), "requisitionStatus", String.class.getName(),maxColWidth);
			
		} catch (ColumnBuilderException e) {
			TPLogger.getLogger().debug("method exit - Error Generating One Stop File Report");
		} catch (ClassNotFoundException e) {
			TPLogger.getLogger().debug("method exit - Error Generating One Stop File Report");
		}
	}

	@Override
	public JRDataSource getDataSource() {
		JRDataSource jd = new DynamicReportsDataSource(getDataSourceCollection());
		return jd;
	}

	/**
	 * @return
	 */
	private List<CandidateOneStopFileReportData> getDataSourceCollection() {
		return candidateOneStopFileReportData;
	}
}
