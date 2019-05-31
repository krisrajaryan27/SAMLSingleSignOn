package com.talentPool.struts2.common;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ParameterAware;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.constants.GlobalForwards;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.user.manager.PermissionSet;

/**
 * @author PraveenK
 * @since  Nov 13, 2011
 */
public abstract class TPActionSupport extends ActionSupport implements ParameterAware, GlobalForwards {

	/**
	 * 
	 */
	private static final long serialVersionUID = 623884974743089790L;
	
	private Map<String, String[]> parameters;

	/* (non-Javadoc)
	 * @see com.opensymphony.xwork2.Action#execute()
	 */
	@Override
	public String execute() throws Exception {
		return SUCCESS;
	}
	
	protected Map<String,Object> getSession(){
		return ActionContext.getContext().getSession();
	}
	
	protected HttpServletRequest getRequest(){
		return ServletActionContext.getRequest();
	}
	
	protected HttpServletResponse getResponse(){
		return ServletActionContext.getResponse();
	}
	
	protected String getUserId(){
		return (String) getSession().get("userId");
	}
	
	protected PermissionSet getPermissionSet(){
		return (PermissionSet) getSession().get("permissionSet");
	}
	
	protected int getUserRoleId(){
		try {
			return Integer.parseInt((String) getSession().get("userRoles"));			
		} catch (NumberFormatException nfe) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, nfe);
			return 0;
		}
	}
	
	/**
	 * Set Attribute in req with attrName as name and attrValue as value
	 * @param attrName
	 * @param attrValue
	 */
	protected void setReqAttr(String attrName, Object attrValue){
		getRequest().setAttribute(attrName, attrValue);
	}
	
	
	/**
	 * Implementing classes can override this method to modify parameters at action level.
	 * @see org.apache.struts2.interceptor.ParameterAware#setParameters(java.util.Map)
	 * @param parameters
	 */
	@Override
	public void setParameters(Map<String, String[]> parameters) {
		this.parameters=parameters;
	}
	
	/**
	 * @return Request parameters map
	 */
	protected Map<String, String[]> getParameters(){
		return this.parameters;
	}
	
	/**
	 * @param param
	 * @return req Parameter value if exists with paramName
	 * <Br>null if no parameter exists with paramName 
	 */
	protected String getParamValue(String paramName){
		if(getParameters().get(paramName)!=null && getParameters().get(paramName).length>0)
			return getParameters().get(paramName)[0];
		else
			return null;
	}
	
	/**
	 * @param param
	 * @return req Parameter values if exists with paramName
	 * <Br>null if no parameter exists with paramName 
	 */
	protected String[] getParamValues(String paramName){
		return getParameters().get(paramName);
	}
	
	/**
	 * @return applicantId from Req 
	 * <Br>null if no param exists with paramName applicantId 
	 */
	protected String getApplicantId(){
		return getParamValue("applicantId");
	}
	
	
	protected void setXMLInRequest(String xml){
		getRequest().setAttribute("xmlFile", xml);
	}
	
}
