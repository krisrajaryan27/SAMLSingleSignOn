package com.talentPool.positions.action;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.base.TPDispatchAction;
import com.talentPool.common.db.SimpleDataObject;
import com.talentPool.common.utils.Utils;
import com.talentPool.positions.dataobject.PositionData;
import com.talentPool.positions.form.PositionForm;
import com.talentPool.positions.manager.PositionManager;

public class PublishPositionToSiteAction  extends TPDispatchAction{

	public ActionForward positionsOnSite(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "positionsOnSite";
		try {
			PositionManager positionManager = new PositionManager();
			List<PositionData> positions = positionManager.getPositionsToPublishToSite();
			
			request.setAttribute("positions", positions);
		} catch (Exception e) {
			TPLogger.getLogger().error("Error in display position on site", e);
		}
		return mapping.findForward(forward);
	}
	
	public ActionForward positionDescription(ActionMapping mapping,ActionForm actionForm,HttpServletRequest request, HttpServletResponse response){
		String forward = "positionDescription";
		SimpleDataObject positionDescription = new SimpleDataObject();
		SimpleDataObject positionRequirements = new SimpleDataObject();
		try {
			String positionId = request.getParameter("positionId");
			PositionForm form = (PositionForm) actionForm;
			PositionManager positionManager = new PositionManager();
			positionDescription = (SimpleDataObject)positionManager.getPositionDescriptionToView(positionId);
			populatePositionDescription(form, positionDescription);
			positionRequirements = (SimpleDataObject)positionManager.getPositionRequirementsToView(positionId);
			populatePositionRequirements(form, positionRequirements);
			request.setAttribute("positionId",positionId);
		} catch (Exception e) {
			TPLogger.getLogger().error("Error in display position on site", e);
		}
		return mapping.findForward(forward);
	}
	
	private void populatePositionDescription(PositionForm form, SimpleDataObject positionDescription) {
		form.setPositionName(positionDescription.getString("positionName"));
		form.setPositionCode(positionDescription.getString("positionCode"));
		form.setRequisitioner(positionDescription.getString("requisitioner"));
		form.setRequisitionerId(positionDescription.getString("requisitionerId"));
		form.setLocationId(positionDescription.getString("locationId"));
		form.setLocationName(positionDescription.getString("locationName"));
		form.setDepartment(positionDescription.getString("department"));
		form.setSubDepartment(positionDescription.getString("subDepartment"));
		form.setSubSubDepartment(positionDescription.getString("subSubDepartment"));
		form.setDepartmentId(positionDescription.getString("departmentId"));
		form.setSubDepartmentId(positionDescription.getString("subDepartmentId"));
		form.setSubSubDepartmentId(positionDescription.getString("subSubDepartmentId"));
		form.setVacancies(positionDescription.getString("vacancies"));
		form.setHireByDate(positionDescription.getString("hireByDate"));
		form.setPositionLevel(positionDescription.getString("positionLevel"));
		form.setPositionReferalFees(positionDescription.getString("positionReferalFees"));
		form.setNote(positionDescription.getString("note"));
		String responsibilities = positionDescription.getString("responsibilities");
		form.setResponsibilities(responsibilities==null ? "" : responsibilities );
		// Bug:-154 Position Create Date on Position Tab
		String positionDate = positionDescription.getString("positionCreateDate");
		if(!Utils.isBlankOrNull(positionDate)){
			Date dt = Utils.convertToDate(positionDate, "yyyy-MM-dd hh:mm:ss");
			positionDate = Utils.getDateConvertedToString(dt, "dd-MM-yyyy");
			form.setPositionCreateDate(positionDate);
		}
	}
	
	private void populatePositionRequirements(PositionForm form, SimpleDataObject positionRequirements) {
		form.setDegreeId(positionRequirements.getString("degreeId"));
		form.setBranchId(positionRequirements.getString("branchId"));
		form.setMinimumExperience(positionRequirements.getString("minimumExperience"));
		form.setMaximumExperience(positionRequirements.getString("maximumExperience"));
		form.setPrimarySkills(positionRequirements.getString("primarySkills"));
		form.setSecondarySkills(positionRequirements.getString("secondarySkills"));
		String requ = positionRequirements.getString("requirements");
		form.setDegreeTitle(positionRequirements.getString("degreeTitle"));
		form.setBranchName(positionRequirements.getString("branchName"));
		form.setRequirements(requ==null ? "" : requ);
	}
}
