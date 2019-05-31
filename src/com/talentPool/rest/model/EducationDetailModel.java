/**
 * 
 */
package com.talentPool.rest.model;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Shantanu
 *
 */
@XmlRootElement
public class EducationDetailModel{	
	
	public EducationDetailModel(){		
	}
	
	public EducationDetailModel(String msg){		  		  
		  branchTitle = msg;		  
		  degreeTitle = msg;		  
		  instituteTitle = msg;
		  yearOfPassing = msg;	
		  grade = msg;
	}
	
	public String educationInfoId;
	public String branchId;
	public String branchTitle;
	public String degreeId;
	public String degreeTitle;	
	public String instituteId;
	public String instituteTitle;
	public String yearOfPassing;	
	public String grade;
}
