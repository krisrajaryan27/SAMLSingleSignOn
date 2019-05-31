package com.talentPool.reports.utils;

import java.util.ArrayList;

public class MonthlyJoinedCandidatesCSVReport {

	public String generateMontlyJoinedCandidateCSVReport(String reportName,
			ArrayList<?> data) {
		ArrayList<String> fieldNames = new ArrayList<String>();
		ArrayList<String> headerNames = new ArrayList<String>();

		fieldNames.add("applicantId");
		headerNames.add("Id");

		fieldNames.add("applicantName");
		headerNames.add("Name");

		fieldNames.add("applicantCity");
		headerNames.add("City");

		fieldNames.add("applicantEmail");
		headerNames.add("Email");

		fieldNames.add("applicantEmail2");
		headerNames.add("Secondary Email");

		fieldNames.add("applicantHomePhone");
		headerNames.add("Home Phone");

		fieldNames.add("applicantCellPhone");
		headerNames.add("Cell Phone");

		fieldNames.add("applicantWorkPhone");
		headerNames.add("Work Phone");

		fieldNames.add("applicantWorkingSince");
		headerNames.add("Work Experience");

		fieldNames.add("positionTitle");
		headerNames.add("Position");

		fieldNames.add("applicantDateCreated");
		headerNames.add("Date Created");

		fieldNames.add("sourceTitle");
		headerNames.add("Source");

		fieldNames.add("joiningDate");
		headerNames.add("Joining Date");

		fieldNames.add("applicantCurrentEmployer");
		headerNames.add("Current Employer");

		fieldNames.add("currentCtc");
		headerNames.add("Current CTC");

		fieldNames.add("expectedCtc");
		headerNames.add("Expected CTC");

		fieldNames.add("applicantNoticePeriod");
		headerNames.add("Notice Period");

		fieldNames.add("levelOffered");
		headerNames.add("Level Offered");

		fieldNames.add("designationOffered");
		headerNames.add("Designation Offered");

		fieldNames.add("offeredCtc");
		headerNames.add("Offered CTC");

		fieldNames.add("employeeCode");
		headerNames.add("Employee Code");

		return CSVReportUtil.generateCSVReport(headerNames, fieldNames, data);

	}
}