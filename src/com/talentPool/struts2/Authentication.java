package com.talentPool.struts2;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.dispatcher.SessionMap;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;
import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.properties.TPLabels;
import com.talentPool.masters.constants.MastersConstants;

public class Authentication implements Interceptor{

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		TPLogger.getLogger().info("Authentication");
		SessionMap session = (SessionMap) ActionContext.getContext().get(ActionContext.SESSION);
		System.out.println("UserName="+session.get("username"));
		HttpServletRequest request = ServletActionContext.getRequest();
		request.setAttribute("masterType", MastersConstants.MASTER_TYPE_RESUME_TYPE);
		request.setAttribute("pageTitle", TPLabels.getLabel("title.common"));
		return invocation.invoke();
	}

}
