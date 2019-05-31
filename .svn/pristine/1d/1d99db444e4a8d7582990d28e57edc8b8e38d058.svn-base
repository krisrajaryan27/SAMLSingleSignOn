/**
 * 
 */
package com.talentPool.export.bo;

import java.util.ArrayList;

import com.talentPool.common.properties.TPLabels;

/**
 * @author shantanu
 *
 */
public class EmploymentHistoryFields extends Fields {
	
	public EmploymentHistoryFields(){
		setHeaders(new ArrayList<Field>());
		
		int counter=1;
		for(;counter<6;counter++){
			getHeaders().add(new Field(("fld" + counter), TPLabels.getLabel("common.employment_from_date")+counter));
			getHeaders().add(new Field(("fld" + counter), TPLabels.getLabel("common.employment_to_date")+counter));
			getHeaders().add(new Field(("fld" + counter), TPLabels.getLabel("common.employer")+counter));
			getHeaders().add(new Field(("fld" + counter), TPLabels.getLabel("common.designation")+counter));			
		}
		//System.out.println(getHeaders());
	}
//	public static void main(String[] args) {
//		EmploymentHistoryFields ehf = new EmploymentHistoryFields();	
//	}
}
