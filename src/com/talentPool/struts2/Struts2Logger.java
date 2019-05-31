package com.talentPool.struts2;


import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;
import com.talentPool.common.Logger.TPLogger;

public class Struts2Logger implements Interceptor{

	@Override
	public void destroy() {
		  System.out.println("Destroying MyLoggingInterceptor...");
		
	}

	@Override
	public void init() {
		System.out.println("Initializing MyLoggingInterceptor...");
		
	}

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		TPLogger.getLogger().info("Struts2Logger");
		return invocation.invoke();
		
	}

}
