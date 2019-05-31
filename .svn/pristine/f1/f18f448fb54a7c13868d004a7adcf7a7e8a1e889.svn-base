/**
 * 
 */
package com.talentPool.dashboard.action;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.talentPool.common.NavigationConstants;
import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.base.TPDispatchAction;
import com.talentPool.common.db.SimpleDataObject;
import com.talentPool.common.properties.GlobalApplicationProperties;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.utils.Utils;
import com.talentPool.common.xsl.XSLTransformer;
import com.talentPool.dashboard.bc.DashboardBC;
import com.talentPool.dashboard.constants.DashboardConstants;
import com.talentPool.dashboard.form.DashboardForm;
import com.talentPool.dashboard.manager.DashboardManager;
import com.talentPool.dashboard.utils.DashboardUtils;
import com.talentPool.user.MessageConstants;
import com.talentPool.user.manager.PermissionSet;
import com.talentPool.user.manager.SessionManager;

/**
 * @author shivprasad
 * 
 */
public class DashboardAction extends TPDispatchAction {
	public ActionForward dashboard(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "dashboard";
		try {
			DashboardManager dashboardManager = new DashboardManager();
			PermissionSet permissionSet = (PermissionSet) request.getSession().getAttribute("permissionSet");			
			String userId = (String) request.getSession(false).getAttribute("userId");
			DashboardBC.setLeftPanel(request, userId);
			int totalSent = dashboardManager.getTotalSentMessagesForUser(userId,permissionSet);
			String positionSummaryGroupByJSArray = DashboardUtils.getPositionSummaryGroupByJSArray();
			request.setAttribute(GlobalConstants.PROPERTY_SHOW_REMINDER, GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_SHOW_REMINDER));
			request.setAttribute("t", NavigationConstants.T_DASHBOARD);
			request.setAttribute("totalSent", "" + totalSent);
			request.setAttribute("positionSummaryGroupByJSArray", positionSummaryGroupByJSArray);
		} catch (Exception e) {
			TPLogger.getLogger().error("", e);
		}
		return mapping.findForward(forward);
	}

	public ActionForward getUpcomingEvents(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "xmlFile";
		String xmlFile = "";
		try {
			DashboardManager dashboardManager = new DashboardManager();
			PermissionSet permissionSet = (PermissionSet) request.getSession().getAttribute("permissionSet");
			if (!SessionManager.isLoginFound(mapping, actionForm, request, response, this)) {
				xmlFile = Utils.getXMLForSessionExpiry();
			} else {
				HttpSession session = request.getSession();
				ArrayList upcomingEvents = dashboardManager.getUpcomingEvents((String) session.getAttribute("userId"),permissionSet);
				xmlFile = dashboardManager.getXMLForUpcomingEvents(upcomingEvents);
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("error in getting PendingInterviewsDetails");
		}
		request.setAttribute("xmlFile", xmlFile);
		return mapping.findForward(forward);
	}

	public ActionForward getToDoList(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "xmlFile";
		String xmlFile = "";
		try {
			DashboardForm form = (DashboardForm) actionForm;
			DashboardManager dashboardManager = new DashboardManager();
			if (!SessionManager.isLoginFound(mapping, actionForm, request, response, this)) {
				xmlFile = Utils.getXMLForSessionExpiry();
			} else {
				PermissionSet permissionSet = (PermissionSet) request.getSession().getAttribute("permissionSet");
				String userId = (String) request.getSession().getAttribute("userId");
				String userRole = (String) request.getSession(false).getAttribute("userRoles");
				String listType = form.getToDoType();
				ArrayList<SimpleDataObject> todoList = new ArrayList<SimpleDataObject>();

				todoList = dashboardManager.getToDoList(userId, permissionSet, listType, form.getGroupItemId(), form.getSortOrder(), form.getSortByColumn(), userRole);
				
				if(DashboardConstants.TODO_LIST_TYPE_LIST.equals(listType)) {
					xmlFile = DashboardUtils.getXMLForToDo(todoList,permissionSet);
				} else if(Utils.isBlankOrNull(form.getGroupItemId())){
					xmlFile = DashboardUtils.getXmlForAggregateToDos(listType, todoList, permissionSet);
					String totalCount = xmlFile.substring(0,xmlFile.indexOf("|"));
					xmlFile = xmlFile.substring(xmlFile.indexOf("|")+1);
					xmlFile = XSLTransformer.getTransformedXMLusingXSL(xmlFile, XSLTransformer.XSL_DASHBOARD_TODO_SUMMARY);
					xmlFile = totalCount + "|" + xmlFile;
				} else {
					xmlFile = DashboardUtils.getXmlForGroupToDos(todoList, permissionSet);
					xmlFile = XSLTransformer.getTransformedXMLusingXSL(xmlFile, XSLTransformer.XSL_DASHBOARD_TODO_DETAILS);
					xmlFile = form.getGroupItemId().replaceAll(" ", "__") + "|" + xmlFile;
				}
				
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		request.setAttribute("xmlFile", xmlFile);
		return mapping.findForward(forward);
	}
	
	public ActionForward getPositionSummaryGrouped(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "xmlFile";
		String xmlFile = "";
		try {
			DashboardForm dashBoardForm = (DashboardForm) actionForm;
			DashboardManager dashboardManager = new DashboardManager();
			ArrayList<SimpleDataObject> positions = null;
			if (!SessionManager.isLoginFound(mapping, actionForm, request, response, this)) {
				xmlFile = Utils.getXMLForSessionExpiry();
			} else {
				HttpSession session = request.getSession();
				String userId = (String) session.getAttribute("userId");
				PermissionSet permissionSet = (PermissionSet) session.getAttribute("permissionSet");
				String positionGrouping = dashBoardForm.getPostionSummaryGridType();
				
				if(DashboardConstants.POSITION_GRID_TYPE_LOCATION.equals(positionGrouping))
					positions = dashboardManager.getPositionSummaryLocationGrouped(userId, positionGrouping, permissionSet);
				else
					positions = dashboardManager.getPositionSummaryGrouped(userId, positionGrouping, permissionSet);
				
				if(Utils.isBlankOrNull(dashBoardForm.getPositionGroupItemId())){
					xmlFile = DashboardUtils.getXmlForPositionSummaryGrouped(positions, positionGrouping, permissionSet);
					String totalCount = xmlFile.substring(0,xmlFile.indexOf("|"));
					xmlFile = xmlFile.substring(xmlFile.indexOf("|")+1);
					xmlFile = XSLTransformer.getTransformedXMLusingXSL(xmlFile, XSLTransformer.XSL_DASHBOARD_POSITION_SUMMARY);
					xmlFile = totalCount + "|" + xmlFile;
				}
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR,e);
		}
		request.setAttribute("xmlFile", xmlFile);
		return mapping.findForward(forward);
	}

	public ActionForward getMessagesForUser(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "xmlFile";
		String xmlFile = "";
		try {
			PermissionSet permissionSet = (PermissionSet) request.getSession().getAttribute("permissionSet");
			DashboardManager dashboardManager = new DashboardManager();
			if (!SessionManager.isLoginFound(mapping, actionForm, request, response, this)) {
				xmlFile = Utils.getXMLForSessionExpiry();
			} else {
				HttpSession session = request.getSession();
				ArrayList msgs = dashboardManager.getMessagesForUser((String) session.getAttribute("userId"), MessageConstants.MESSAGE_NOT_READ, permissionSet);
				xmlFile = dashboardManager.getXmlForMessages(msgs);
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("error in getting PendingInterviewsDetails");
		}
		request.setAttribute("xmlFile", xmlFile);
		return mapping.findForward(forward);
	}

	public ActionForward deleteMessage(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "xmlFile";
		String xmlFile = "";
		try {
			DashboardManager dashboardManager = new DashboardManager();
			if (!SessionManager.isLoginFound(mapping, actionForm, request, response, this)) {
				xmlFile = Utils.getXMLForSessionExpiry();
			} else {
				DashboardForm dashboardForm = (DashboardForm) actionForm;
				String userId = (String) request.getSession(false).getAttribute("userId");
				dashboardManager.markMessagesRead(dashboardForm.getMessageIds(), userId);
				// Create deleted xml
				xmlFile = Utils.getXMLForIds(dashboardForm.getMessageIds());
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while deleting message", e);
			xmlFile = Utils.getXMLForError(null);
		}
		request.setAttribute("xmlFile", xmlFile);
		return mapping.findForward(forward);
	}

	public ActionForward getSentMessages(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "xmlFile";
		String xmlFile = "";
		try {
			DashboardManager dashboardManager = new DashboardManager();
			PermissionSet permissionSet = (PermissionSet) request.getSession().getAttribute("permissionSet");
			if (!SessionManager.isLoginFound(mapping, actionForm, request, response, this)) {
				xmlFile = Utils.getXMLForSessionExpiry();
			} else {
				HttpSession session = request.getSession();
				ArrayList msgs = dashboardManager.getSentMessagesForUser((String) session.getAttribute("userId"), permissionSet);
				xmlFile = dashboardManager.getXmlForSentMessages(msgs);
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("error in getting PendingInterviewsDetails");
		}
		request.setAttribute("xmlFile", xmlFile);
		return mapping.findForward(forward);
	}

	public ActionForward getReminderList(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "xmlFile";
		String xmlFile = "";
		try {
			DashboardManager dashboardManager = new DashboardManager();
			if (!SessionManager.isLoginFound(mapping, actionForm, request, response, this)) {
				xmlFile = Utils.getXMLForSessionExpiry();
			} else {
				PermissionSet permissionSet = (PermissionSet) request.getSession().getAttribute("permissionSet");
				String userId = (String) request.getSession().getAttribute("userId");				
				ArrayList reminder = dashboardManager.getReminderList(userId);				
				xmlFile = dashboardManager.getXmlForReminderList(reminder, permissionSet);
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("error while getting Reminder", e);
		}
		request.setAttribute("xmlFile", xmlFile);
		return mapping.findForward(forward);
	}
	
	public ActionForward deleteReminder(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "xmlFile";
		String xmlFile = "";		
		try {
			DashboardForm dashboardForm = (DashboardForm) actionForm;
			String reminderId = dashboardForm.getReminderId();
			DashboardManager dashboardManager = new DashboardManager();
			dashboardManager.removeReminder(reminderId);
			xmlFile = Utils.getXMLForIds(reminderId);
		}
		catch (Exception e) {
			TPLogger.getLogger().error("Error while deleting Reminder", e);
			xmlFile = Utils.getXMLForError(null);
		}
		request.setAttribute("xmlFile", xmlFile);
		return mapping.findForward(forward);
	}
	
	public ActionForward getPositionSummaryDetails(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "xmlFile";
		String xmlFile = "";
		try {
			DashboardForm dashBoardForm = (DashboardForm) actionForm;
			DashboardManager dashboardManager = new DashboardManager();
			ArrayList<SimpleDataObject> positions = null;
			if (!SessionManager.isLoginFound(mapping, actionForm, request, response, this)) {
				xmlFile = Utils.getXMLForSessionExpiry();
			} else {
				HttpSession session = request.getSession();
				String userId = (String) session.getAttribute("userId");
				PermissionSet permissionSet = (PermissionSet) session.getAttribute("permissionSet");
				String positionGrouping = dashBoardForm.getPostionSummaryGridType();
				positions = dashboardManager.getPositionSummary(userId, positionGrouping,dashBoardForm.getPositionGroupItemId(),permissionSet);
				if(DashboardConstants.POSITION_GRID_TYPE_LIST.equals(positionGrouping))
					xmlFile = dashboardManager.getXmlForPositionSummary(positions,permissionSet,userId);
				else {
					xmlFile = DashboardUtils.getXmlForPositionDetails(positions,permissionSet);
					xmlFile = XSLTransformer.getTransformedXMLusingXSL(xmlFile, XSLTransformer.XSL_DASHBOARD_POSITION_DETAILS);
					xmlFile = dashBoardForm.getPositionGroupItemId().replaceAll(" ", "__") + "|" + xmlFile;
				}
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR,e);
		}
		request.setAttribute("xmlFile", xmlFile);
		return mapping.findForward(forward);
	}
}
