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
public class SourceFields extends Fields {
	public SourceFields() {
		setHeaders(new ArrayList<Field>());
		
		int counter = 0;
		getHeaders().add(new Field(("fld" + counter++), TPLabels.getLabel("exportToExcel.source.label.name")));
		getHeaders().add(new Field(("fld" + counter++), TPLabels.getLabel("exportToExcel.source.label.type")));
		getHeaders().add(new Field(("fld" + counter++), TPLabels.getLabel("exportToExcel.source.label.email")));
		getHeaders().add(new Field(("fld" + counter++), TPLabels.getLabel("exportToExcel.source.label.phone")));
		getHeaders().add(new Field(("fld" + counter++), TPLabels.getLabel("exportToExcel.source.label.mobile")));
		getHeaders().add(new Field(("fld" + counter++), TPLabels.getLabel("exportToExcel.source.label.employee_code")));
		getHeaders().add(new Field(("fld" + counter++), TPLabels.getLabel("exportToExcel.source.label.can_send_email_to_source")));
		getHeaders().add(new Field(("fld" + counter++), TPLabels.getLabel("exportToExcel.source.label.can_send_sms_to_source")));
		getHeaders().add(new Field(("fld" + counter++), TPLabels.getLabel("exportToExcel.source.label.lock_in_period_on_import")));
	}
}
