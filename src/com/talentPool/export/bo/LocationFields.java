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
public class LocationFields extends Fields {
	public LocationFields() {
		setHeaders(new ArrayList<Field>());
		
		int counter = 0;
		getHeaders().add(new Field(("fld" + counter++), TPLabels.getLabel("exportToExcel.location.label.name")));	
	}
}
