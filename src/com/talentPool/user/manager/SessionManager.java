package com.talentPool.user.manager;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.BitSet;
import java.util.Date;
import java.util.TimeZone;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.utils.DateUtils;
import com.talentPool.common.utils.Utils;
import com.talentPool.user.dataobject.LoginData;

public class SessionManager {
	private static Class[] _parameterTypes = new Class[4];
	/** Creates a new instance of SessionManager */

	static {
		try {
			_parameterTypes[0] = Class.forName("org.apache.struts.action.ActionMapping");
			_parameterTypes[1] = Class.forName("org.apache.struts.action.ActionForm");
			_parameterTypes[2] = Class.forName("javax.servlet.http.HttpServletRequest");
			_parameterTypes[3] = Class.forName("javax.servlet.http.HttpServletResponse");
		} catch (Exception e) {
			TPLogger.getLogger().debug("ErrorLoading static parameters", e);
		}
	}

	public static boolean isLoginFound(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Object obj) {
		boolean isLoginFound = false;
		try {
			if ((null != request.getSession().getAttribute("userId"))) {
				String userId = (String) (request.getSession().getAttribute("userId"));
				if (userId != null) {
					if(SingleSignOnManager.isValidIPRequest(userId,request.getRemoteAddr())){
						isLoginFound = true;
					}else{
						invalidateSession(request, response, userId);
					}
				}
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return isLoginFound;
	}
	
	public static boolean isLoginFound(HttpServletRequest request) {
		return isLoginFound(null, null, request, null, null);
	}

	public static boolean validateSession(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Object obj) {
		try {
			if (isLoginFound(actionMapping, actionForm, request, response, obj)) {
				return true;
			} else {
				sessionExpireRedirect(actionMapping, actionForm, request, response, obj, true);
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error While validating session", e);
		}
		return false;
	}
	
	/**
	 * 1. removes tal+userId cookie
	 * 2. Unsets Session Vars
	 * 3. Invalidates session
	 * @param request
	 * @param response
	 * @param userId
	 * @author PraveenK
	 */
	public static void invalidateSession(HttpServletRequest request, HttpServletResponse response, String uId) {
		try {
		Cookie cookie = new Cookie("tal" + uId, uId);
		cookie.setMaxAge(0); // Delete the cookie
		cookie.setSecure(true);
		cookie.setHttpOnly(true);
		response.addCookie(cookie);

		HttpSession session = request.getSession(false);
		if (session != null) {
			session.setAttribute("username", null);
			session.setAttribute("userpassword", null);
			session.setAttribute("userId", null);
			session.setAttribute("userFirstName", null);
			session.setAttribute("userLastName", null);
			session.setAttribute("lastLogin", null);
			session.setAttribute("searchCriteria", null);
			session.setAttribute("userRoles", null);
			session.setAttribute("userTasks", null);
			session.setAttribute("tasks", null);
			session.setAttribute("validLdapUser", null);
			session.setAttribute("permissionSet", null);
			session.setAttribute("reportBitSet", null);
			session.invalidate();
		}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
	}
	
	public static void sessionExpireRedirect(ActionMapping actionMapping, ActionForm actionForm, 
			final HttpServletRequest request,final HttpServletResponse response, 
				Object actionClass, boolean saveActionState) throws ServletException, IOException{
		String parameter = actionMapping.getParameter();
		String invokingMethodName = "execute";
		if (parameter != null && !"".equals(parameter)) {
			invokingMethodName = request.getParameter(parameter);
			if (invokingMethodName == null || "".equals(invokingMethodName)) {
				invokingMethodName = "execute";
			}
		}
		if(saveActionState){
			HttpSession session = request.getSession(true);
			session.setAttribute("prevMap", actionMapping);
			session.setAttribute("prevForm", actionForm);
			session.setAttribute("prevAction", actionClass);
			session.setAttribute("prevMethod", invokingMethodName);
			// save t st and it
			session.setAttribute("t", request.getAttribute("t"));
			session.setAttribute("st", request.getAttribute("st"));
			session.setAttribute("it", request.getAttribute("it"));	
		}

		StringBuffer sb = new StringBuffer("/common/login.jsp");
		sb.append("?paramName=");
		sb.append(parameter);
		sb.append("&paramValue=");
		sb.append(invokingMethodName);
		String url = sb.toString();
		request.setAttribute("isExpired", "1");
		
		TPLogger.getLogger().info("Session expired");
		request.getRequestDispatcher(url).forward(request, response);
	}

	public static ActionForward continuePreviousSession(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		ActionForm prevForm = (ActionForm) (session.getAttribute("prevForm"));
		ActionMapping prevMap;
		if (prevForm != null) {
			prevMap = (ActionMapping) (session.getAttribute("prevMap"));
			if (prevMap != null) {
				// actionForm = prevForm;
				try {
					Object prevAction = session.getAttribute("prevAction");
					String methodName = (String) (session.getAttribute("prevMethod"));

					Method method = prevAction.getClass().getDeclaredMethod(methodName, _parameterTypes);
					Object[] args = new Object[4];
					args[0] = prevMap;
					args[1] = prevForm;
					args[2] = request;
					args[3] = response;
					session.setAttribute("prevMap", null);
					session.setAttribute("prevForm", null);
					session.setAttribute("prevAction", null);
					session.setAttribute("prevMethod", null);
					String beanName = prevMap.getName();
					String scope = prevMap.getScope();
					if ("request".equals(scope)) {
						request.setAttribute(beanName, prevForm);
						request.setAttribute("t", session.getAttribute("t"));
						request.setAttribute("st", session.getAttribute("st"));
						request.setAttribute("it", session.getAttribute("it"));
						session.setAttribute("t", null);
						session.setAttribute("st", null);
						session.setAttribute("it", null);
					} else if ("session".equals(scope)) {
						session.setAttribute(beanName, prevForm);
					}

					return (ActionForward) (method.invoke(prevAction, args));
				} catch (Exception e) {
					TPLogger.getLogger().error("", e);
					return null;
				}
			}
		}
		return null;
	}
	
	public void setSessionVariables(HttpSession session, LoginData loginData, PermissionSet permissionSet,BitSet reportBitSet, boolean validLdapUser, String userRoles, String password, String timeZone) {
		/*TimeZone tzone = TimeZone.getTimeZone(timeZone);
		TimeZone.setDefault(tzone);*/
		session.setAttribute("timeZoneId", timeZone);
		setSessionVariables(session, loginData, permissionSet, reportBitSet, validLdapUser, userRoles, password);
	}
	
	public void setSessionVariables(HttpSession session, LoginData loginData, PermissionSet permissionSet,BitSet reportBitSet, boolean validLdapUser, String userRoles, String password) {
		session.setAttribute("username", loginData.getUserName());		
		session.setAttribute("userId", loginData.getUserId());
		session.setAttribute("userFirstName", loginData.getFirstName());
		session.setAttribute("userLastName", loginData.getLastName());
		session.setAttribute("userpassword", password);
		session.setAttribute("userCellPhone", loginData.getCellPhone());
		session.setAttribute("permissionSet", permissionSet);
		session.setAttribute("reportBitSet", reportBitSet);
		if (validLdapUser) {
			session.setAttribute("validLdapUser", "1");
		}
		if (loginData.getLastLogin() == null) {
			session.setAttribute("lastLogin", Utils.getDateConvertedToString(new Date(), Utils.regddMMMyyyyhhmma));
		} else {
			session.setAttribute("lastLogin", DateUtils.getSystemDateTimeFormat(loginData.getLastLogin()));
		}
		session.setAttribute("userRoles", userRoles);
		session.setMaxInactiveInterval(-1);
	}
	
}
