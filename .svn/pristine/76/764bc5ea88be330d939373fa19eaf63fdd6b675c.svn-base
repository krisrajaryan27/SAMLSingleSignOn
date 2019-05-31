package com.talentPool.positions.action;

import java.io.StringWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.talentPool.applicant.manager.ApplicantManager;
import com.talentPool.common.NavigationConstants;
import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.base.TPDispatchAction;
import com.talentPool.common.constants.DHTMLXXMLWriterConstants;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.properties.TPLabels;
import com.talentPool.common.utils.Utils;
import com.talentPool.common.xmlutils.DHTMLXXMLWriter;
import com.talentPool.positions.PositionConstants;
import com.talentPool.positions.dataobject.PositionData;
import com.talentPool.positions.manager.PositionManager;
import com.talentPool.positions.manager.PositionSummaryXMLUtil;
import com.talentPool.selectionProcess.dataobject.RejectedCandidateData;
import com.talentPool.selectionProcess.dataobject.SelectionProcessData;
import com.talentPool.selectionProcess.form.SelectionProcessForm;
import com.talentPool.selectionProcess.manager.SelectionProcessManager;
import com.talentPool.user.manager.PermissionSet;
import com.talentPool.user.manager.SessionManager;

public class PositionSummaryAction extends TPDispatchAction {
	public ActionForward getInprocessApplicantsXml(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "xmlFile";
		String xmlFile = "";
		if (!SessionManager.isLoginFound(mapping, actionForm, request, response, this)) {
			xmlFile = Utils.getXMLForSessionExpiry();
		} else {
			String userId = (String) request.getSession(false).getAttribute("userId");
			String userRole = (String) request.getSession(false).getAttribute("userRoles");
			SelectionProcessForm selectionProcessForm = (SelectionProcessForm) actionForm;
			String pId=request.getParameter("positionId");
			String stepLevelFixed=PositionConstants.STEP_LEVEL_SHORTLIST+","+PositionConstants.STEP_LEVEL_SELECT+","+PositionConstants.STEP_LEVEL_ACCEPT;
			String stepLevel=Utils.isBlankOrNull(selectionProcessForm.getStepLevel())?stepLevelFixed:selectionProcessForm.getStepLevel();
			String positionId=Utils.isBlankOrNull(selectionProcessForm.getPositionId())?pId:selectionProcessForm.getPositionId();
			String tenthMarksFilter=request.getParameter("tenthMarksFilter");
			 String tenthMarks=request.getParameter("tenthMarks");
			 String twelvethMarksFilter=request.getParameter("twelvethMarksFilter");
			 String twelvethMarks=request.getParameter("twelvethMarks");
			 String gradeMarksFilter=request.getParameter("gradeMarksFilter");
			 String gradeMarks=request.getParameter("gradeMarks");
			 String postGradeMarksFilter=request.getParameter("postGradeMarksFilter");
			 String postGradeMarks=request.getParameter("postGradeMarks");
			 String ageFilter=request.getParameter("ageFilter");
			 String age=request.getParameter("age");
			 String yearOfExperienceFilter=request.getParameter("yearOfExperienceFilter");
			 String yearOfExperience=request.getParameter("yearOfExperience");
			 String gapInAcademics=request.getParameter("gapInAcademics");
			 String gender=request.getParameter("gender");
			 
			PermissionSet permissionSet = (PermissionSet) request.getSession(false).getAttribute("permissionSet");
			SelectionProcessManager selectionProcessManager = new SelectionProcessManager();
			PositionSummaryXMLUtil positionSummaryManager= new PositionSummaryXMLUtil();
			List<SelectionProcessData> applicantsInProcess = selectionProcessManager.getInProcessApplicantsXml(permissionSet, userRole, userId, selectionProcessForm.getDepartmentId(),positionId, selectionProcessForm.getApplicantName(), selectionProcessForm.getStepName(), stepLevel, selectionProcessForm.getLocationTitle(), 
					selectionProcessForm.getPositionTypeExtInt(), selectionProcessForm.getSelectedUserId(),selectionProcessForm.getSourceId(),NavigationConstants.T_POSITIONS,
					 tenthMarksFilter, tenthMarks, twelvethMarksFilter, twelvethMarks, gradeMarksFilter, gradeMarks,
					  postGradeMarksFilter,  postGradeMarks, ageFilter, age, yearOfExperienceFilter, yearOfExperience,gapInAcademics,gender);
			xmlFile = positionSummaryManager.getXMLForInProcessApplicants(applicantsInProcess, selectionProcessForm.getActionRequired(),permissionSet);
		}
		request.setAttribute("xmlFile", xmlFile);
		return mapping.findForward(forward);
	}
	
	/**
	 * XML of rejected candidates for a position is returned. 
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward getRejectedCandidatesXml(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "xmlFile";
		String xmlFile = "";
		if (!SessionManager.isLoginFound(mapping, actionForm, request, response, this)) {
			xmlFile = Utils.getXMLForSessionExpiry();
		} else {
			SelectionProcessForm selectionProcessForm = (SelectionProcessForm) actionForm;
			
			String pId=request.getParameter("positionId");
			String stepLevelFixed=PositionConstants.STEP_LEVEL_SHORTLIST+","+PositionConstants.STEP_LEVEL_SELECT+","+PositionConstants.STEP_LEVEL_ACCEPT;
			String stepLevel=Utils.isBlankOrNull(selectionProcessForm.getStepLevel())?stepLevelFixed:selectionProcessForm.getStepLevel();
			String positionId=Utils.isBlankOrNull(selectionProcessForm.getPositionId())?pId:selectionProcessForm.getPositionId();
			String tenthMarksFilter=request.getParameter("tenthMarksFilter");
			 String tenthMarks=request.getParameter("tenthMarks");
			 String twelvethMarksFilter=request.getParameter("twelvethMarksFilter");
			 String twelvethMarks=request.getParameter("twelvethMarks");
			 String gradeMarksFilter=request.getParameter("gradeMarksFilter");
			 String gradeMarks=request.getParameter("gradeMarks");
			 String postGradeMarksFilter=request.getParameter("postGradeMarksFilter");
			 String postGradeMarks=request.getParameter("postGradeMarks");
			 String ageFilter=request.getParameter("ageFilter");
			 String age=request.getParameter("age");
			 String yearOfExperienceFilter=request.getParameter("yearOfExperienceFilter");
			 String yearOfExperience=request.getParameter("yearOfExperience");
			 String gapInAcademics=request.getParameter("gapInAcademics");
			 String gender=request.getParameter("gender");
			 
			try {
				PermissionSet permissionSet = (PermissionSet) request.getSession(false).getAttribute("permissionSet");
				if(permissionSet.isPERMISSION_VIEW_REJECTED_CANDIDATES()){
					SelectionProcessManager selectionProcessManager = new SelectionProcessManager();
					PositionSummaryXMLUtil positionSummaryManager= new PositionSummaryXMLUtil();
					String rejectedApplicantName=request.getParameter("rejectedApplicantName");
					String stepName=request.getParameter("stepName");
					String rejectedBy=request.getParameter("rejectedBy");
					List<RejectedCandidateData> rejectedCanidates = selectionProcessManager.getRejectedCandidatesData(positionId, permissionSet, stepName, rejectedBy, rejectedApplicantName );
					xmlFile = positionSummaryManager.getXMLForRejectedCandidates(rejectedCanidates);					
				}else{
					xmlFile = "";
				}
			} catch (SQLException e) {
				TPLogger.getLogger().error("Error while fetching rejected candidates ", e);
			}
		}
		request.setAttribute("xmlFile", xmlFile);
		return mapping.findForward(forward);
	}
	
	
	public ActionForward getCandidatesAppliedForPosition(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response){
		String forward = "xmlFile";
		String xmlFile = "";
		if (!SessionManager.isLoginFound(mapping, actionForm, request, response, this)) {
			xmlFile = Utils.getXMLForSessionExpiry();
		} else{
			String pId = request.getParameter("positionId");
			SelectionProcessForm selectionProcessForm = (SelectionProcessForm) actionForm;
			String stepLevelFixed=PositionConstants.STEP_LEVEL_SHORTLIST+","+PositionConstants.STEP_LEVEL_SELECT+","+PositionConstants.STEP_LEVEL_ACCEPT;
			String stepLevel=Utils.isBlankOrNull(selectionProcessForm.getStepLevel())?stepLevelFixed:selectionProcessForm.getStepLevel();
			String positionId=Utils.isBlankOrNull(selectionProcessForm.getPositionId())?pId:selectionProcessForm.getPositionId();
			String tenthMarksFilter=request.getParameter("tenthMarksFilter");
			 String tenthMarks=request.getParameter("tenthMarks");
			 String twelvethMarksFilter=request.getParameter("twelvethMarksFilter");
			 String twelvethMarks=request.getParameter("twelvethMarks");
			 String gradeMarksFilter=request.getParameter("gradeMarksFilter");
			 String gradeMarks=request.getParameter("gradeMarks");
			 String postGradeMarksFilter=request.getParameter("postGradeMarksFilter");
			 String postGradeMarks=request.getParameter("postGradeMarks");
			 String ageFilter=request.getParameter("ageFilter");
			 String age=request.getParameter("age");
			 String yearOfExperienceFilter=request.getParameter("yearOfExperienceFilter");
			 String yearOfExperience=request.getParameter("yearOfExperience");
			 String gapInAcademics=request.getParameter("gapInAcademics");
			 String gender=request.getParameter("gender");
			 
			PositionManager positioManager = new PositionManager();
			List<SelectionProcessData> appliedCandidates = positioManager.getAppliedCandidatesDataForPosition(positionId,
					 tenthMarksFilter, tenthMarks, twelvethMarksFilter, twelvethMarks, gradeMarksFilter, gradeMarks,
					  postGradeMarksFilter,  postGradeMarks, ageFilter, age, yearOfExperienceFilter, yearOfExperience,gapInAcademics,gender);
			PositionSummaryXMLUtil positionSummaryManager= new PositionSummaryXMLUtil();
			xmlFile = positionSummaryManager.getXmlForCandidatesAppliedForPosition(appliedCandidates);
		}
		request.setAttribute("xmlFile", xmlFile);
		return mapping.findForward(forward);
	}
	
	public ActionForward getPositionsApplicantAppliedFor(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response){
		String forward = "xmlFile";
		String xmlFile = "";
		StringWriter sWr = new StringWriter();
		try {
			if (!SessionManager.isLoginFound(mapping, actionForm, request, response, this)) {
				xmlFile = Utils.getXMLForSessionExpiry();
				forward = "xmlFile";
			} else {
				String applicantId = (String) request.getParameter("applicantId");
				ApplicantManager applicantManger = new ApplicantManager();
				ArrayList<PositionData> positionsApplied = applicantManger.getPositionsAndResumesAppliedByCandidate(applicantId,false);
				DHTMLXXMLWriter wr = new DHTMLXXMLWriter(sWr);	
				wr.startDocument();
				wr.startElement(DHTMLXXMLWriterConstants.ROWS);
				if (!Utils.isListEmptyOrNull(positionsApplied)) {
					for (PositionData position : positionsApplied) {
						String positionName = Utils.isBlankOrNull(position.getPositionTitle())?TPLabels.getLabel("naukri_position_defaultNotImported"):position.getPositionTitle();
						String positionId = Utils.isBlankOrNull(position.getPositionId())?"NULL":position.getPositionId();
						String resumePath = position.getHTMLResumePath();
						if(Utils.isBlankOrNull(resumePath)){
							resumePath = position.getDocResumePath();
						}
						String docResumePath = position.getDocResumePath();
						String[] columns 	= {"","",docResumePath,positionName,resumePath};
						wr.createDHTMLXRow(Utils.isBlankOrNull(positionId)?"0":positionId, columns);
					}
				}
				wr.endElement(DHTMLXXMLWriterConstants.ROWS);
				wr.endDocument();
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			xmlFile = Utils.getXMLForError();
		}
		request.setAttribute("xmlFile",  sWr.getBuffer().toString());
		return mapping.findForward(forward);
	}
	
}
