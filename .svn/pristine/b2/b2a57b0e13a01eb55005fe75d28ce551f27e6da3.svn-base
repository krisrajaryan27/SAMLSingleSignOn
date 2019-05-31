/**
 * 
 */
package com.talentPool.rest.resource;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.rest.manager.RestServiceDataManager;
import com.talentPool.rest.model.ApplicantDetailModel;
import com.talentPool.rest.model.ListApplicantDetailModel;
import com.talentPool.selectionProcess.manager.SelectionProcessManager;

/**
 * @author Shantanu
 *
 */
@Path("/appData")
public class RestServiceResource {
	
		
	@GET
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})	
	public ListApplicantDetailModel fetchApplicantsDetails(){		
		ListApplicantDetailModel listApplicantDetailModel = new ListApplicantDetailModel();
		RestServiceDataManager rsdm = new RestServiceDataManager();
		listApplicantDetailModel= rsdm.fetchApplicantsDetails();
		return listApplicantDetailModel;
	}
	
	@POST
	@Consumes({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	public void setEmpId(ListApplicantDetailModel listapplicantDetailModel){
		SelectionProcessManager spm = new SelectionProcessManager();
		try{
			List<ApplicantDetailModel> lstAdm = listapplicantDetailModel.applicantsDetail;
			if(lstAdm!=null && !lstAdm.isEmpty()){
				for(ApplicantDetailModel adm:lstAdm){			
					spm.updateApplicantAttribute(adm.employeeCode, adm.applicantId, "employee_code");
				}
			}
		}catch (Exception e) {
			TPLogger.getLogger().debug(GlobalConstants.ERROR,e);
		}		
	}
}