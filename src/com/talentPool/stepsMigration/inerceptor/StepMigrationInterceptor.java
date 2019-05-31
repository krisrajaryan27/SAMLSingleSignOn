/**
 * 
 */
package com.talentPool.stepsMigration.inerceptor;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;
import com.talentPool.masters.constants.MastersConstants;

/**
 * @author PraveenK
 * @since  Jan 8, 2012
 */
public class StepMigrationInterceptor implements Interceptor  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/* (non-Javadoc)
	 * @see com.opensymphony.xwork2.interceptor.Interceptor#destroy()
	 */
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.opensymphony.xwork2.interceptor.Interceptor#init()
	 */
	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.opensymphony.xwork2.interceptor.Interceptor#intercept(com.opensymphony.xwork2.ActionInvocation)
	 */
	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		ServletActionContext.getRequest().setAttribute("masterType", MastersConstants.MASTER_TYPE_STEPS_MIGRATION);
		return invocation.invoke();
	}

}
