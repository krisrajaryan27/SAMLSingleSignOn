/**
 * Copyright 2009 - Nitman Software Pvt. Ltd. All Rights Reserved. 
 * This software is the proprietary information of Nitman Software Pvt. Ltd. 
 * Use is subject to license terms.
 */
package com.talentPool.masters.inerceptors;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;
import com.talentPool.masters.constants.MastersConstants;

/**
 * @author PraveenK
 * @since  May 8, 2012
 */
public class SalaryComponentCategoryMasterInterceptor implements Interceptor {

	/**
	 * Auto Generated Serial Version Id
	 */
	private static final long serialVersionUID = -4592520964837224472L;

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
		ServletActionContext.getRequest().setAttribute("masterType", MastersConstants.MASTER_TYPE_SALARY_COMPONENT_CATEGORY);
		return invocation.invoke();
	}

}
