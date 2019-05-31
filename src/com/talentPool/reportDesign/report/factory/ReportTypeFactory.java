package com.talentPool.reportDesign.report.factory;

import com.talentPool.reportDesign.constants.ReportDesignConstants;
import com.talentPool.reportDesign.report.AuditLogTypeReport;
import com.talentPool.reportDesign.report.BudgetTypeReport;
import com.talentPool.reportDesign.report.CandidateActivityTypeReport;
import com.talentPool.reportDesign.report.CandidatePositionTypeReport;
import com.talentPool.reportDesign.report.CandidateTypeReport;
import com.talentPool.reportDesign.report.ExpenseTypeReport;
import com.talentPool.reportDesign.report.PositionActivityTypeReport;
import com.talentPool.reportDesign.report.PositionCandidateTypeReport;
import com.talentPool.reportDesign.report.PositionTypeReport;
import com.talentPool.reportDesign.report.ReportTypes;

public class ReportTypeFactory {
	
	private static ReportTypeFactory reportTypeFactory = new ReportTypeFactory();

	private ReportTypeFactory() {
		// private constructor, to make it singleton
	}
	
	public static ReportTypeFactory getInstance() {
		return reportTypeFactory;
	}
	
	public ReportTypes getReportType(String reportType) {
		ReportTypes rt = null;
		if(reportType.equals(ReportDesignConstants.REPORT_TYPE_CANDIDATES)){
			rt = new CandidateTypeReport();
		}else if(reportType.equals(ReportDesignConstants.REPORT_TYPE_CANDIDATES_WITH_POSITION)){
			rt = new CandidatePositionTypeReport();
		}else if(reportType.equals(ReportDesignConstants.REPORT_TYPE_CANDIDATES_WITH_ACTIVITY)){
			rt = new CandidateActivityTypeReport();
		}else if(reportType.equals(ReportDesignConstants.REPORT_TYPE_EXPENSE)){
			rt = new ExpenseTypeReport();
		}else if(reportType.equals(ReportDesignConstants.REPORT_TYPE_POSITIONS)){
			rt = new PositionTypeReport();
		}else if(reportType.equals(ReportDesignConstants.REPORT_TYPE_POSITIONS_WITH_CANDIDATE)){
			rt = new PositionCandidateTypeReport();
		}else if(reportType.equals(ReportDesignConstants.REPORT_TYPE_POSITIONS_WITH_ACTIVITY)){
			rt = new PositionActivityTypeReport();
		}else if(reportType.equals(ReportDesignConstants.REPORT_TYPE_AUDIT)){
			rt = new AuditLogTypeReport();
		}else if(reportType.equals(ReportDesignConstants.REPORT_TYPE_BUDGET)){
			rt = new BudgetTypeReport();
		}else {
			// TODO:: Handle this
			//throw new UnSupportedReportTypeException();
		}
		return rt;
	}

}
