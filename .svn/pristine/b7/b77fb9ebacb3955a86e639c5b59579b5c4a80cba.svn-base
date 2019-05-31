/**
 * 
 */
package com.talentPool.select.action;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.talentPool.common.base.TPDispatchAction;
import com.talentPool.common.db.SimpleDataObject;
import com.talentPool.common.utils.Utils;
import com.talentPool.latestActivity.manager.LatestActivityManager;
import com.talentPool.latestActivity.utils.LatestActivityUtils;
import com.talentPool.positions.PositionConstants;
import com.talentPool.user.manager.PermissionSet;
import com.talentPool.user.manager.SessionManager;

/**
 * @author Ajeet
 *
 */
public class SelectAction extends TPDispatchAction{

	public ActionForward getLatestActivityXmlForSelectStage(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "xmlFile";
		String xmlFile = "";
		if (!SessionManager.isLoginFound(mapping, actionForm, request, response, this)) {
			xmlFile = Utils.getXMLForSessionExpiry();
		} else {
			String userId = (String) request.getSession(false).getAttribute("userId");
			PermissionSet permissionSet = (PermissionSet) request.getSession(false).getAttribute("permissionSet");

			LatestActivityManager latestActivityManager = new LatestActivityManager();
			ArrayList<SimpleDataObject> latestActivities = latestActivityManager.getLatestActivity(userId, PositionConstants.STEP_LEVEL_SELECT+","+PositionConstants.STEP_LEVEL_SHORTLIST, permissionSet);
			
			xmlFile = LatestActivityUtils.getXmlForLatestActivity(latestActivities, permissionSet);
		}
		request.setAttribute("xmlFile", xmlFile);

		return mapping.findForward(forward);
	}
	
	public ActionForward getLatestActivityXmlForPostionSummary(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "xmlFile";
		String xmlFile = "";
		if (!SessionManager.isLoginFound(mapping, actionForm, request, response, this)) {
			xmlFile = Utils.getXMLForSessionExpiry();
		} else {
			String userId = (String) request.getSession(false).getAttribute("userId");
			PermissionSet permissionSet = (PermissionSet) request.getSession(false).getAttribute("permissionSet");
			String positionId = request.getParameter("positionId");

			LatestActivityManager latestActivityManager = new LatestActivityManager();
			String stepLevel  = PositionConstants.STEP_LEVEL_SHORTLIST + "," +
								PositionConstants.STEP_LEVEL_SELECT + "," +
								PositionConstants.STEP_LEVEL_ACCEPT;
			ArrayList<SimpleDataObject> latestActivities = latestActivityManager.getLatestActivity(userId,stepLevel,positionId, permissionSet);
			
			xmlFile = LatestActivityUtils.getXmlForLatestActivity(latestActivities, permissionSet);
		}
		request.setAttribute("xmlFile", xmlFile);

		return mapping.findForward(forward);
	}
}
