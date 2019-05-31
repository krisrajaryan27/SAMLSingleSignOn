/**
 * 
 */
package com.talentPool.common.base;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.talentPool.applicant.ApplicantConstants;
import com.talentPool.applicant.dataobject.ApplicantData;
import com.talentPool.applicant.manager.ApplicantManager;
import com.talentPool.budget.utils.BudgetUtils;
import com.talentPool.common.CommonConstants;
import com.talentPool.common.NavigationConstants;
import com.talentPool.common.dataobject.LicenseErrorMessages;
import com.talentPool.common.dataobject.PermissionErrorMessages;
import com.talentPool.common.dataobject.ReportErrorMessages;
import com.talentPool.common.db.SimpleDataObject;
import com.talentPool.common.properties.TPLabels;
import com.talentPool.common.utils.Utils;
import com.talentPool.dashboard.constants.DashboardConstants;
import com.talentPool.dashboard.manager.DashboardManager;
import com.talentPool.positions.PositionConstants;
import com.talentPool.positions.dataobject.UserData;
import com.talentPool.positions.manager.PositionManager;
import com.talentPool.reports.ReportVersionConstants;
import com.talentPool.reports.dataobject.FilterData;
import com.talentPool.reports.manager.ReportManager;
import com.talentPool.selectionProcess.dataobject.SelectionProcessData;
import com.talentPool.selectionProcess.manager.SelectionProcessManager;
import com.talentPool.user.constants.ModuleConstants;
import com.talentPool.user.manager.ModuleSet;
import com.talentPool.user.manager.PermissionSet;

/**
 * @author shivprasad
 * 
 */
public class TPDispatchAction extends DispatchAction {

	private static final String AUTHORIZATION_FAILURE_FORWARD = "authorizationFailure";
	
	/**
	 * @return the authorizationFailureForward
	 */
	public static String getAuthorizationFailureForward() {
		return AUTHORIZATION_FAILURE_FORWARD;
	}

	/**
	 * Sets the title. Takes the key of the title as in mpstringlabel.properties
	 * file.
	 * 
	 * @param title
	 *            The key of the title in mpstringlabel.properties
	 * @param request
	 */
	public void setTitle(String title, HttpServletRequest request) {
		request.setAttribute("pageTitle", title);
	}

	public ActionForward execute(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) throws Exception {
		ActionForward retValue=null;
		if(validRequest(httpServletRequest)){
			setTitle(TPLabels.getLabel("title.common"), httpServletRequest);
			retValue = super.execute(actionMapping, actionForm, httpServletRequest, httpServletResponse);
			httpServletResponse.addHeader("Cache-Control","no-store, no-cache, must-revalidate"); //HTTP 1.1
			httpServletResponse.addHeader("Pragma","no-cache"); //HTTP 1.0
			httpServletResponse.addHeader("X-Frame-Options", "SAMEORIGIN");
			httpServletResponse.addHeader("X-Content-Type-Options", "nosniff");
			httpServletResponse.addHeader("X-XSS-Protection", "1; mode=block");
			httpServletResponse.addDateHeader ("Expires", 0); //prevents caching at the proxy server	
		}
		return retValue;
	}
	
	/**
	 * Return <code>false</code> if the request has a <code>TOKEN</code> 
	 * and is not valid meaning the request is of type submit and has a invalid or expired token.  
	 * <br> Returns <code>true</code> if 
     *
     * <ul>
     *
     * <li>Request does not have the <code>TOKEN</code> meaning the request is not submit but just a URL navigation</li> 
     * <li>Request has <code>TOKEN</code> and is a valid token</li>
     * 
     *  </ul> 
     *  Note: This method is written to avoid CSRF ( Cross Site Request Forging) attack.
     *  
	 * @param request
	 * @return
	 */
	private boolean validRequest(HttpServletRequest request){
		String tokenFromSession=null;
		if(request.getSession() != null){
			tokenFromSession = (String) request.getSession().getAttribute("org.apache.struts.action.TOKEN");	
		}
		if(tokenFromSession != null && request.getMethod().equals("POST")){
		   String tokenFromRequest = (String) request.getParameter("org.apache.struts.taglib.html.TOKEN"); // in case of form submission struts adds automatically token in request parameter
		   if(tokenFromRequest==null){
			   tokenFromRequest=(String) request.getParameter("CSRF_TOKEN");   //in case of ajax calls no form submission , so added explicitly CSRF_TOKEN in templates 
		   }
		   if (tokenFromRequest != null) {
			  if (tokenFromSession.equals(tokenFromRequest)) {
				return true;
			  }
		   }
			return false;
		}
		return true;
	}

	public boolean isUserAuthorized(HttpServletRequest request, int module,
			Integer[] permissions, String applicantId, String positionId, String reportId) {
		boolean isUserAuthorized = true;
		String authError = null;
		String userId = (String) request.getSession().getAttribute("userId");
		String userRole = (String) request.getSession(false).getAttribute("userRoles");
		PermissionSet permissionSet = (PermissionSet) request.getSession(false).getAttribute("permissionSet");
		BitSet reportBitSet = (BitSet) request.getSession(false).getAttribute("reportBitSet");
		
		authError = checkLicenseModule(module);		
		if (Utils.isBlankOrNull(authError) && permissions!=null) {
			for (int i = 0; i < permissions.length && Utils.isBlankOrNull(authError); i++) {
				int permission = permissions[i];
				authError = checkPermission(permission, permissionSet);				
			}
		}		
		if(Utils.isBlankOrNull(authError) && !Utils.isBlankOrNull(positionId)) {
			authError = checkPositionAuthorization(positionId, userId, permissionSet);
		}
		
		if(Utils.isBlankOrNull(authError) && !Utils.isBlankOrNull(applicantId)) {
			authError = checkApplicantProfileAccess(applicantId, permissionSet, userId, userRole);
		}
		
		if(Utils.isBlankOrNull(authError) && !Utils.isBlankOrNull(reportId)) {
			authError = checkReportAvailability(reportId, reportBitSet);
		}
		
		if(Utils.isBlankOrNull(authError) && ModuleConstants.MODULE_BUDGET==module && !BudgetUtils.isBudgetModuleActive()) {
			authError = "common.budgt.error.no_active_module";
		}
		
		if(!Utils.isBlankOrNull(authError)) {
			isUserAuthorized = false;
			ActionErrors actionErrors = new ActionErrors();
			actionErrors.add(authError, new ActionError(authError));
			saveErrors(request, actionErrors);
		}
		return isUserAuthorized;
	}

	private String checkReportAvailability(String reportId, BitSet reportBitSet) {
		String authError = null;
		if(!ReportVersionConstants.isReportAvailable(reportBitSet, reportId)) {
			authError = ReportErrorMessages.get(reportId);
		}
		return authError;
	}

	private String checkApplicantProfileAccess(String applicantId, PermissionSet permissionSet, String userId, String userRole) {
		String authError = null;
		
		// check for each applicant profile access
		// Users with permission to 'search' can search and view any candidate's profile
		// Other users will be able to see only candidates belonging to positions they have access to.
		// consider select, hire, positions tab access, dashboard position summary access and 
		// rejected, joined candidate access as well
		// 7th Oct 2013 : If none of the permissions are given, an user will still have ToDo access;
		// and he/she should be able see profiles of candidates from his/her ToDo list.
		boolean hasAccess = true;
		if(permissionSet.isPERMISSION_SCREEN()) {
			if (permissionSet.isDO_NOT_SHOW_CONFIDENTIAL_PROFILE()) {
				ApplicantManager applicantManager = new ApplicantManager();
				ApplicantData aData = applicantManager.getApplicantSummaryData(applicantId);
				if (aData != null) {
					if (ApplicantConstants.APPLICANT_CONFIDENTIAL.equals(aData.getIsConfidential())) {
						hasAccess = false;
					}
				}
			}
		} else if(permissionSet.isPERMISSION_SELECT() || permissionSet.isPERMISSION_HIRE() 
				|| permissionSet.isPERMISSION_POSITIONS()) {
			hasAccess = getAllAccessibleCanidatesAndCheckAccess(applicantId, userId, userRole, permissionSet);
			if (permissionSet.isPERMISSION_SHOW_ALL_CANDIDATES()){
				hasAccess = true;
			}
		} else {
			hasAccess = false;
			ArrayList<SimpleDataObject> todoList = new DashboardManager().getToDoList(userId, 
					permissionSet, DashboardConstants.TODO_LIST_TYPE_LIST, null, null, 
					null, userRole);
			for(SimpleDataObject todo : todoList) {
				String appId = todo.getString("applicantId");
				if(applicantId.equals(appId)) {
					hasAccess = true;					
					break;
				}
			}
			if (permissionSet.isPERMISSION_SHOW_ALL_CANDIDATES()){
				hasAccess = true;
			}
		}
		if(!hasAccess) {
			authError = "common.error.permission_do_not_show_candidate_profile";
		} else {
			authError = null;
		}
		return authError;
	}

	/**
	 * @param applicantId
	 * @param userId
	 * @param userRole
	 * @param permissionSet
	 * @return true or false if user has access to view applicant profile
	 */
	private boolean getAllAccessibleCanidatesAndCheckAccess(String applicantId,
			String userId, String userRole, PermissionSet permissionSet) {
		
		boolean hasAccess = false;
		List<SelectionProcessData> applicants1 = new ArrayList<SelectionProcessData>();
		List<SimpleDataObject> applicants2 = new ArrayList<SimpleDataObject>();
		SelectionProcessManager selectionProcessManager = new SelectionProcessManager();
		List<SelectionProcessData> applicantsInProcess = selectionProcessManager
				.getInProcessApplicants(permissionSet, userRole, userId, null, null, null, null,
						PositionConstants.STEP_LEVEL_SHORTLIST + ","
								+ PositionConstants.STEP_LEVEL_SELECT + ","
								+ PositionConstants.STEP_LEVEL_ACCEPT, 
						null, null, null, null, NavigationConstants.T_POSITIONS, true);
		applicants1.addAll(applicantsInProcess);
		
		// rejected, joined candidate access as well
		if(permissionSet.isPERMISSION_SHOW_JOINED_CANDIDATES()) {
			@SuppressWarnings("unchecked")
			List<SimpleDataObject> joinedCandidates = (List<SimpleDataObject>) selectionProcessManager
					.getJoinedApplicants(userId, permissionSet, "", "", "");
			applicants2.addAll(joinedCandidates);
		}
		
		if(permissionSet.isPERMISSION_VIEW_REJECTED_CANDIDATES()){
			try {
				List<SimpleDataObject> rejectedCandidates = (new ReportManager())
						.getRejectedCandiadatesList(new FilterData(), userId, permissionSet);
				applicants2.addAll(rejectedCandidates);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		for(SelectionProcessData applicant : applicants1) {
			if(applicant.getApplicantId() == Integer.parseInt(applicantId)) {
				hasAccess = true;
				break;
			}				
		}
		for(SimpleDataObject applicant : applicants2) {
			if(applicant.getString("applicantId").equals(applicantId)) {
				hasAccess = true;
				break;
			}				
		}
		
		return hasAccess;
	}

	private String checkPositionAuthorization(String positionId, String userId, PermissionSet permissionSet) {
		String authError = null;
		if (permissionSet.isSHOW_POSITIONS_WITH_RIGHTS()) {
			String[] roles = new String[0];
			PositionManager positionManager = new PositionManager();
			ArrayList<UserData> list = positionManager.getUsersForPosition(positionId, roles);
			ArrayList<UserData> requisitionUsersList = positionManager.getRequisitionApprovalUsersForPosition(positionId);
			if(Utils.isListEmptyOrNull(list)) {
				list = requisitionUsersList;
			} else {
				list.addAll(requisitionUsersList);
			}
			boolean hasAccess = false;
			for (int i = 0; i < list.size() && !hasAccess; i++) {
				UserData uData = list.get(i);
				String uId = "" + uData.getUserId();
				if (uId.equals(userId)) {
					hasAccess = true;
				}
			}
			if (!hasAccess) {
				authError = "common.error.permission_do_not_show_position_with_rights";
			}
		}
		return authError;
	}

	private String checkLicenseModule(int module) {
		String authError = null;
		if (module != CommonConstants.NO_MODULE && !ModuleSet.isModuleAvailable(module)) {
			authError = LicenseErrorMessages.get(module);
		}
		return authError;
	}
	
	private String checkPermission(int permission, PermissionSet permissionSet) {
		String authError = null;
		if(permission != 0 && !permissionSet.isPermissionGranted(permission)) {
			authError = PermissionErrorMessages.get(permission);
		}		
		return authError;
	}
	
	/**
     * Read the remote IP of a servlet request. This may be in one of two
     * places. If we are behind an Apache server with mod_proxy_http, we get the
     * remote IP address from the request header x-forwarded-for. In that case
     * the remote ip of the requestor is always that of the Apache server, since
     * that is the last proxy, as per spec.
     * <p>
     * If this header is missing, we're probably running locally for testing. In
     * that case we can just use the remote IP from the request object itself.
     * 
     * @param request
     *            The request to get the remote IP from.
     * @return The remote IP address.
     */
	public static String getClientIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");
		if (Utils.isBlankOrNull(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (Utils.isBlankOrNull(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (Utils.isBlankOrNull(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (Utils.isBlankOrNull(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (Utils.isBlankOrNull(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}  
}