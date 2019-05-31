package com.talentPool.customReports.inerceptor;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;
import com.talentPool.common.NavigationConstants;
 
public class CustomReportsInterceptor implements Interceptor {
 
   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//called during interceptor destruction
	public void destroy() {
		// TODO
	}
 
	//called during interceptor initialization
	public void init() {
		// TODO
	}
 
	//put interceptor code here
	public String intercept(ActionInvocation invocation) throws Exception {
		ServletActionContext.getRequest().setAttribute("t", NavigationConstants.T_REPORT);
		return invocation.invoke();
	}
}