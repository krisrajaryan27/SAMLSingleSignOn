/**
 * 
 */
package com.talentPool.export.bo;

import java.util.ArrayList;

import com.talentPool.common.properties.GlobalApplicationProperties;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.properties.TPLabels;
/**
 * @author pallavi
 *
 */
public class UserFields extends Fields {
	public UserFields() {
		setHeaders(new ArrayList<Field>());
		
		int counter = 0;
		getHeaders().add(new Field(("fld" + counter++), TPLabels.getLabel("exportToExcel.user.label.user_name")));
		getHeaders().add(new Field(("fld" + counter++), TPLabels.getLabel("exportToExcel.user.label.password")));
		getHeaders().add(new Field(("fld" + counter++), TPLabels.getLabel("exportToExcel.user.label.first_name")));
		getHeaders().add(new Field(("fld" + counter++), TPLabels.getLabel("exportToExcel.user.label.last_name")));
		getHeaders().add(new Field(("fld" + counter++), TPLabels.getLabel("exportToExcel.user.label.email")));
		getHeaders().add(new Field(("fld" + counter++), TPLabels.getLabel("exportToExcel.user.label.employee_code")));
		getHeaders().add(new Field(("fld" + counter++), TPLabels.getLabel("exportToExcel.user.label.home_phone")));
		getHeaders().add(new Field(("fld" + counter++), TPLabels.getLabel("exportToExcel.user.label.cell_phone")));
		getHeaders().add(new Field(("fld" + counter++), TPLabels.getLabel("exportToExcel.user.label.location")));
		getHeaders().add(new Field(("fld" + counter++), GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_DEPARTMENT_LEVEL_1)));
		getHeaders().add(new Field(("fld" + counter++), GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_DEPARTMENT_LEVEL_2)));
		getHeaders().add(new Field(("fld" + counter++), GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_DEPARTMENT_LEVEL_3)));	
		getHeaders().add(new Field(("fld" + counter++), GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_DEPARTMENT_LEVEL_4)));	
		getHeaders().add(new Field(("fld" + counter++), GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_DEPARTMENT_LEVEL_5)));	
		getHeaders().add(new Field(("fld" + counter++), TPLabels.getLabel("exportToExcel.user.label.grade")));
		getHeaders().add(new Field(("fld" + counter++), TPLabels.getLabel("exportToExcel.user.label.band")));
		getHeaders().add(new Field(("fld" + counter++), TPLabels.getLabel("exportToExcel.user.label.assign_under")));
		getHeaders().add(new Field(("fld" + counter++), TPLabels.getLabel("exportToExcel.user.label.role")));
		getHeaders().add(new Field(("fld" + counter++), TPLabels.getLabel("exportToExcel.user.label.vendor")));		
	}
}
