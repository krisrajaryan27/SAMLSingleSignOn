/**
 * Copyright 2009 - Talentica Software Pvt. Ltd. All Rights Reserved. 
 * This software is the proprietary information of Talentica Software Pvt. Ltd. 
 * Use is subject to license terms.
 */
package com.talentPool.common.filters;

import java.util.Enumeration;
import java.util.TimeZone;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.base.TPDispatchAction;
import com.talentPool.common.utils.Utils;
import com.talentPool.common.MyThreadLocal;
import com.talentPool.common.ThreadLocalContextObject;
import com.talentPool.timeZone.TimeZoneUtils;

/**
 * @author anikets
 * 
 */
public class LogRequestFilter implements javax.servlet.Filter {
	
	private FilterConfig filterConfig;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
	}

	private static String getFormattedMemberName(HttpSession session) {

		String memberInfoString = session.getAttribute("userFirstName") + " "
				+ session.getAttribute("userLastName") + "("
				+ session.getAttribute("userId") + ")";
		return memberInfoString;
	}

	public void doFilter(final ServletRequest servletRequest,
			final ServletResponse servletResponse, FilterChain chain)
			throws java.io.IOException, javax.servlet.ServletException {

		// System.out.println("Entering Filter");
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		long startTime = System.nanoTime();

		String timeZoneId = (String)request.getSession().getAttribute("timeZoneId");
		
		ThreadLocalContextObject obj = new ThreadLocalContextObject();
		obj.setTimeZone(timeZoneId);
		MyThreadLocal.set(obj);
		
		if(Utils.isBlankOrNull(timeZoneId)){
			timeZoneId = TimeZoneUtils.DEFAULT_TIME_ZONE_ID;
		}
		
		/*TimeZone tzone = TimeZone.getTimeZone(timeZoneId);
		TimeZone.setDefault(tzone);*/
		
		chain.doFilter(request, response);
		logRequestData(request, response, startTime);
		
		MyThreadLocal.unset();
	}

	private void logRequestData(HttpServletRequest request,
			HttpServletResponse response, long startTime) {
		long endTime = System.nanoTime();
		Long timeTaken = (endTime - startTime)/1000000;
		String performanceStatus = constructPrintablePerfStatus(timeTaken);
		StringBuffer printRequest = new StringBuffer();
		printRequest.append("[" + timeTaken + " ms "+performanceStatus+"] ");
		String mode = constructPrintableUserInfo(request, response);
		if (!Utils.isBlankOrNull(mode)) {
			printRequest.append(mode);
			String URI = getPrintableURI(request.getRequestURI());
			if (!skipLog(URI)) {
				printRequest.append(" [" + URI + "]");
				String parameterInfo = constructPrintableParameterInfo(request);
				printRequest.append(parameterInfo);
				TPLogger.getLogger().info(printRequest.toString());
			}
		}
	}

	private String constructPrintableParameterInfo(
			HttpServletRequest request) {
		StringBuffer parameterInfo = new StringBuffer();
		int count = 0;
		Enumeration<String> paramNames = request.getParameterNames();
		while (paramNames.hasMoreElements()) {
			String paramName = (String) paramNames.nextElement();
			if (count == 0) {
				parameterInfo.append(" (");
			}
			String seperator = "&";
			if (count == 0)
				seperator = "";
			count++;
			parameterInfo.append(seperator + paramName);
			String[] paramValues = request.getParameterValues(paramName);
			if (paramValues.length == 1) {
				String paramValue = paramValues[0];
				if (paramValue.length() == 0) {
					parameterInfo.append("=");
				} else {
					paramValue = convertToPrintableFormats(paramName,
							paramValue);
					parameterInfo.append("=" + paramValue);
				}
			} else {
				parameterInfo.append("=");
				for (int i = 0; i < paramValues.length; i++) {
					parameterInfo.append(paramValues[i] + ",");
				}
			}
		}
		if (count > 0) {
			parameterInfo.append(")");
		}
		return parameterInfo.toString();
	}

	private String constructPrintableUserInfo(HttpServletRequest request,
			HttpServletResponse response) {
		//String mode = "Visitor" + " [" + request.getSession().getId() + "]";
		String mode = "";
		if (null != request.getSession().getAttribute("userId")) {
			String clientIpAddr = TPDispatchAction.getClientIpAddr(request);
			mode = "[" + clientIpAddr + "] " + "User "
					+ getFormattedMemberName(request.getSession());
			//mode = mode + " [" + request.getSession().getId() + "]";
		}
		return mode;
	}

	private String constructPrintablePerfStatus(Long timeTaken) {
		String performanceStatus = "PERF-OK";
		if(timeTaken > 2000 && timeTaken < 10000) {
			performanceStatus = "PERF-SLOW";
		} else if (timeTaken > 10000) {
			performanceStatus = "PERF-NOK";
		}
		return performanceStatus;
	}

	private String getPrintableURI(String URI) {
		String[] URIarr = URI.split("/");
		if (URIarr != null && URIarr.length > 2) {
			URI = URIarr[2];
		}
		return URI;
	}

	private boolean skipLog(String URI) {
		if (URI.equals("docs.do"))
			return true;
		return false;
	}

	private String convertToPrintableFormats(String paramName, String paramValue) {
		if (paramName.equals("userPassword") || paramName.equals("oldPassword")
				|| paramName.equals("confirmNewPassword") || paramName.equals("newPassword")
				|| paramName.equals("inboxPassword") || paramName.equals("inboxSmtpPassword")
				|| paramName.equals("linkedInClientSecret") || paramName.equals("facebookClientSecret")
				|| paramName.equals("authToken")) {
			paramValue = "xxxxxx";
		}
		return paramValue;
	}

	@Override
	public void destroy() {

	}

}
