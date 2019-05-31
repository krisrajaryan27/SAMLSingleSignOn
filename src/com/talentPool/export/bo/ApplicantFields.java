/**
 * 
 */
package com.talentPool.export.bo;

import java.util.ArrayList;

import com.talentPool.common.properties.TPLabels;
/**
 * @author pallavi
 *
 */
public class ApplicantFields extends Fields {
	public ApplicantFields() {
		setHeaders(new ArrayList<Field>());
		
		int counter = 0;
		getHeaders().add(new Field(("fld" + counter++), TPLabels.getLabel("exportToExcel.applicant.label.id")));
		getHeaders().add(new Field(("fld" + counter++), TPLabels.getLabel("exportToExcel.applicant.label.name")));
		getHeaders().add(new Field(("fld" + counter++), TPLabels.getLabel("exportToExcel.applicant.label.city")));
		getHeaders().add(new Field(("fld" + counter++), TPLabels.getLabel("exportToExcel.applicant.label.email1")));
		getHeaders().add(new Field(("fld" + counter++), TPLabels.getLabel("exportToExcel.applicant.label.email2")));
		getHeaders().add(new Field(("fld" + counter++), TPLabels.getLabel("exportToExcel.applicant.label.home_phone")));
		getHeaders().add(new Field(("fld" + counter++), TPLabels.getLabel("exportToExcel.applicant.label.cell_phone")));
		getHeaders().add(new Field(("fld" + counter++), TPLabels.getLabel("exportToExcel.applicant.label.work_phone")));
		getHeaders().add(new Field(("fld" + counter++), TPLabels.getLabel("exportToExcel.applicant.label.experience")));
		getHeaders().add(new Field(("fld" + counter++), TPLabels.getLabel("common.position_code")));
		getHeaders().add(new Field(("fld" + counter++), TPLabels.getLabel("common.position_name")));
		getHeaders().add(new Field(("fld" + counter++), TPLabels.getLabel("exportToExcel.applicant.label.department")));
		getHeaders().add(new Field(("fld" + counter++), TPLabels.getLabel("exportToExcel.applicant.label.step")));
		getHeaders().add(new Field(("fld" + counter++), TPLabels.getLabel("exportToExcel.applicant.label.import_date")));
		getHeaders().add(new Field(("fld" + counter++), TPLabels.getLabel("exportToExcel.applicant.label.source")));
		getHeaders().add(new Field(("fld" + counter++), TPLabels.getLabel("exportToExcel.applicant.label.joining_date")));
		getHeaders().add(new Field(("fld" + counter++), TPLabels.getLabel("exportToExcel.applicant.label.current_employer")));
		getHeaders().add(new Field(("fld" + counter++), TPLabels.getLabel("exportToExcel.applicant.label.imported_by")));
		getHeaders().add(new Field(("fld" + counter++), TPLabels.getLabel("exportToExcel.applicant.label.current_ctc")));
		getHeaders().add(new Field(("fld" + counter++), TPLabels.getLabel("exportToExcel.applicant.label.expected_ctc")));
		getHeaders().add(new Field(("fld" + counter++), TPLabels.getLabel("exportToExcel.applicant.label.notice_period")));
		getHeaders().add(new Field(("fld" + counter++), TPLabels.getLabel("exportToExcel.applicant.label.level_offered")));
		getHeaders().add(new Field(("fld" + counter++), TPLabels.getLabel("exportToExcel.applicant.label.designation_offered")));
		getHeaders().add(new Field(("fld" + counter++), TPLabels.getLabel("exportToExcel.applicant.label.offered_ctc")));
		getHeaders().add(new Field(("fld" + counter++), TPLabels.getLabel("exportToExcel.applicant.label.employee_id")));
		getHeaders().add(new Field(("fld" + counter++), TPLabels.getLabel("exportToExcel.applicant.label.dob")));
		getHeaders().add(new Field(("fld" + counter++), TPLabels.getLabel("exportToExcel.Sr_No")));
		getHeaders().add(new Field(("fld" + counter++), TPLabels.getLabel("exportToExcel.applicant.sub_dept")));
		getHeaders().add(new Field(("fld" + counter++), TPLabels.getLabel("exportToExcel.applicant.status")));
		getHeaders().add(new Field(("fld" + counter++), TPLabels.getLabel("exportToExcel.applicant.label.joining_bonus")));
		getHeaders().add(new Field(("fld" + counter++), TPLabels.getLabel("exportToExcel.applicant.label.variable_offered")));
	}
}
