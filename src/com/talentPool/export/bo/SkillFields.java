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
public class SkillFields extends Fields {
	public SkillFields() {
		setHeaders(new ArrayList<Field>());
		
		int counter = 0;
		getHeaders().add(new Field(("fld" + counter++), TPLabels.getLabel("exportToExcel.skill.label.name")));
		getHeaders().add(new Field(("fld" + counter++), TPLabels.getLabel("exportToExcel.skill.label.category")));
		getHeaders().add(new Field(("fld" + counter++), TPLabels.getLabel("exportToExcel.skill.label.alias1")));
		getHeaders().add(new Field(("fld" + counter++), TPLabels.getLabel("exportToExcel.skill.label.alias2")));
		getHeaders().add(new Field(("fld" + counter++), TPLabels.getLabel("exportToExcel.skill.label.alias3")));
		
	}
}
