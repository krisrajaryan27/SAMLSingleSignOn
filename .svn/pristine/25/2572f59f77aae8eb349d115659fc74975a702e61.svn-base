/**
 * 
 */
package com.talentPool.common.base;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import javax.servlet.http.HttpServletRequest;

/**
 * @author shivprasad
 * 
 */
public class TPActionForm extends ActionForm {
	private String mode="";
	//Outer TABS
	private String t="";
	private String st="";
	//Inner Tabs
	private String it="";
	private String sit="";
	private boolean isCancelled = false;
	
	/**
	 * Generates an instance of TPActionForm
	 */
	public TPActionForm(){
	}
	/**
	 * @return Returns the mode.
	 */
	public String getMode() {
		return mode;
	}

	/**
	 * @param mode The mode to set.
	 */
	public void setMode(String mode) {
		this.mode = mode;
	}

	/**
	 * @return Returns the st.
	 */
	public String getSt() {
		return st;
	}

	/**
	 * @param st The st to set.
	 */
	public void setSt(String st) {
		this.st = st;
	}

	/**
	 * @return Returns the t.
	 */
	public String getT() {
		return t;
	}

	/**
	 * @param t The t to set.
	 */
	public void setT(String t) {
		this.t = t;
	}

	
	public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
		ActionErrors retValue;
		retValue = super.validate(actionMapping, httpServletRequest);
		httpServletRequest.setAttribute("t", t);
		httpServletRequest.setAttribute("st", st);
		httpServletRequest.setAttribute("it", it);
		httpServletRequest.setAttribute("sit", sit);
		httpServletRequest.setAttribute("pageTitle", "title.common");
		return retValue;
	}
	/**
	 * @return Returns the it.
	 */
	public String getIt() {
		return it;
	}
	/**
	 * @param it The it to set.
	 */
	public void setIt(String it) {
		this.it = it;
	}
	/**
	 * @return Returns the sit.
	 */
	public String getSit() {
		return sit;
	}
	/**
	 * @param sit The sit to set.
	 */
	public void setSit(String sit) {
		this.sit = sit;
	}
	
	public boolean isCancelled() {
		return isCancelled;
	}

	public void setCancelled(boolean isCancelled) {
		this.isCancelled = isCancelled;
	}

}
