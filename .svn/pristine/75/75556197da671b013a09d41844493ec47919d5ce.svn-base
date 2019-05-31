package com.talentPool.positions.action;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.talentPool.common.CommonConstants;
import com.talentPool.common.NavigationConstants;
import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.base.TPDispatchAction;
import com.talentPool.common.db.SimpleDataObject;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.utils.Utils;
import com.talentPool.positions.PositionConstants;
import com.talentPool.positions.form.PositionDraftForm;
import com.talentPool.positions.manager.PositionDraftsManager;
import com.talentPool.positions.utils.PositionDraftsXMLGenerator;
import com.talentPool.requisition.manager.RequisitionManager;
import com.talentPool.user.constants.PermissionConstants;
import com.talentPool.user.manager.PermissionSet;
import com.talentPool.user.manager.SessionManager;

public class PositionDraftsAction extends TPDispatchAction {
	
	public ActionForward draftsHome(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "draftsHome";
		String userId = (String) request.getSession().getAttribute("userId");			
		
		Integer[] permissions = new Integer[1];
		permissions[0] = PermissionConstants.PERMISSION_POSITIONS;
		if(!isUserAuthorized(request, CommonConstants.NO_MODULE, permissions,null,null,null)) {
			forward = "authorizationFailure";			
			return mapping.findForward(forward);
		}		
		
		boolean canAddRequisition = true;
		if (PositionConstants.REQUISITION_MANAGEMENT_ENABLED) {
			RequisitionManager requisitionManager = new RequisitionManager();
			if (!requisitionManager.isRequisitionAllowed(userId)) {
				canAddRequisition = false;
			}
		}
		request.setAttribute("canAddRequisition", canAddRequisition);
		
		request.setAttribute("t", NavigationConstants.T_POSITIONS);
		return mapping.findForward(forward);
	}
	
	public ActionForward xmlForPositionDrafts(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response){
		String forward = "xmlFile";
		String xmlFile = "";
		Integer[] permissions = new Integer[1];
		permissions[0] = PermissionConstants.PERMISSION_POSITIONS;
		if(!isUserAuthorized(request, CommonConstants.NO_MODULE, permissions,null,null,null)) {
			forward = "authorizationFailure";			
			return mapping.findForward(forward);
		}
		try {			
			if (!SessionManager.isLoginFound(mapping, actionForm, request, response, this)) {
				xmlFile = Utils.getXMLForSessionExpiry();
			} else {
				String userId = (String) request.getSession(false).getAttribute("userId");
				PermissionSet permissionSet = (PermissionSet) request.getSession(false).getAttribute("permissionSet");
				PositionDraftsManager positionDraftManager = new PositionDraftsManager();
				
				ArrayList positionsDrafts = positionDraftManager.getAllDraftPosition(userId, permissionSet);
				PositionDraftsXMLGenerator positionDraftXMLGenerator = new PositionDraftsXMLGenerator();
				xmlFile = positionDraftXMLGenerator.getXMLforPositionsDraftsHome(positionsDrafts, userId, permissionSet);				
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error in getting XML for drafts:", e);
			xmlFile = Utils.getXMLForError();
		} finally {
			request.setAttribute("xmlFile", xmlFile);
		}
		return mapping.findForward(forward);
	}
	
	public ActionForward deletePositionDraft(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response){
		String forward = "xmlFile";
		String xmlFile = "";	
		Integer[] permissions = new Integer[1];
		permissions[0] = PermissionConstants.PERMISSION_POSITIONS;
		if(!isUserAuthorized(request, CommonConstants.NO_MODULE, permissions,null,null,null)) {
			forward = "authorizationFailure";			
			return mapping.findForward(forward);
		}
		try {
			if (!SessionManager.isLoginFound(mapping, actionForm, request, response, this)) {
				xmlFile = Utils.getXMLForSessionExpiry();
			} else {
				PositionDraftForm form = (PositionDraftForm) actionForm;
				
				PositionDraftsManager positionDraftManager = new PositionDraftsManager();
				positionDraftManager.deleteDraft(form.getDraftId(), null);
			}
		} catch(Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			xmlFile = Utils.getXMLForError();
		} finally {
			request.setAttribute("xmlFile", xmlFile);
		}
		return mapping.findForward(forward);
	}
	
	public ActionForward showDraftStatus(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response){
		String forward = "positionDraftStatus";		
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		Integer[] permissions = new Integer[1];
		permissions[0] = PermissionConstants.PERMISSION_POSITIONS;
		if(!isUserAuthorized(request, CommonConstants.NO_MODULE, permissions,null,null,null)) {
			forward = "authorizationFailure";			
			return mapping.findForward(forward);
		}
		try {
			PositionDraftsManager positionDraftManager = new PositionDraftsManager();
			PositionDraftForm positionDraftForm = (PositionDraftForm) actionForm;
			String draftId = positionDraftForm.getDraftId();
		
			SimpleDataObject sdo = positionDraftManager.getDraftData(draftId);
			positionDraftForm.setShowStatus(sdo.getString("visibility"));
			request.setAttribute("draftName", sdo.getString("fileName"));			
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while getting status for Position", e);
		}
		return mapping.findForward(forward);
	}
	
	public ActionForward changePositionDraftStatus(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response){
		String forward = "positionDraftStatus";		
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		Integer[] permissions = new Integer[1];
		permissions[0] = PermissionConstants.PERMISSION_POSITIONS;
		if(!isUserAuthorized(request, CommonConstants.NO_MODULE, permissions,null,null,null)) {
			forward = "authorizationFailure";			
			return mapping.findForward(forward);
		}
		try{
			PositionDraftForm positionDraftForm = (PositionDraftForm)actionForm;
			
			PositionDraftsManager positionDraftManager = new PositionDraftsManager();
			String draftId = positionDraftForm.getDraftId();
			SimpleDataObject sdo = positionDraftManager.getDraftData(draftId);
			String status = sdo.getString("visibility");
			String showStatusChanged = positionDraftForm.getShowStatus();
			if (!Utils.isBlankOrNull(draftId) && !Utils.isBlankOrNull(showStatusChanged)) {
				if(!status.equalsIgnoreCase(showStatusChanged)){ // if changed then only fire update query. 
					positionDraftManager.updateDraftStatus(draftId,showStatusChanged);
				}
			}
			request.setAttribute("update", "1");
		}catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return mapping.findForward(forward);
	}
	
	public ActionForward showDraftName(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response){
		String forward = "positionDraftName";		
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		Integer[] permissions = new Integer[1];
		permissions[0] = PermissionConstants.PERMISSION_POSITIONS;
		if(!isUserAuthorized(request, CommonConstants.NO_MODULE, permissions,null,null,null)) {
			forward = "authorizationFailure";			
			return mapping.findForward(forward);
		}
		try {
			PositionDraftsManager positionDraftManager = new PositionDraftsManager();
			PositionDraftForm positionDraftForm = (PositionDraftForm) actionForm;
			String draftId = positionDraftForm.getDraftId();
		
			SimpleDataObject sdo = positionDraftManager.getDraftData(draftId);
			positionDraftForm.setFileName(sdo.getString("fileName"));
			request.setAttribute("draftName", sdo.getString("fileName"));
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while getting status for Position", e);
		}
		return mapping.findForward(forward);
	}
	
	public ActionForward changePositionDraftName(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response){
		String forward = "positionDraftName";
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		Integer[] permissions = new Integer[1];
		permissions[0] = PermissionConstants.PERMISSION_POSITIONS;
		if(!isUserAuthorized(request, CommonConstants.NO_MODULE, permissions,null,null,null)) {
			forward = "authorizationFailure";			
			return mapping.findForward(forward);
		}
		try{
			PositionDraftForm positionDraftForm = (PositionDraftForm)actionForm;
			
			PositionDraftsManager positionDraftManager = new PositionDraftsManager();
			String draftId = positionDraftForm.getDraftId();
			String fileName = positionDraftForm.getFileName();
			if (!Utils.isBlankOrNull(fileName)) {
				//check if draft name is already exists
				ActionErrors errors = new ActionErrors();
				positionDraftManager.checkDraftNameExists(errors,fileName.trim());
				if (errors!=null && errors.size() > 0) {
					saveErrors(request, errors);
				} else {
					SimpleDataObject sdo = positionDraftManager.getDraftData(draftId);
					positionDraftManager.updateDraftTitle(draftId,fileName.trim());
					request.setAttribute("update", "1");
				}
			}
		}catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return mapping.findForward(forward);
	}
}
