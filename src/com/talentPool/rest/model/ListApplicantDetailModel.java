/**
 * 
 */
package com.talentPool.rest.model;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Shantanu
 *
 */
@XmlRootElement
public class ListApplicantDetailModel {
	public List<ApplicantDetailModel> applicantsDetail;	
}
