package com.talentPool.common.base;

/**
 * @author shivprasad
 *
 */
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class TPAction extends Action {
	
	/**
	 * Sets the title. Takes the key of the title as in mpstringlabel.properties file.
	 * 
	 * @param title
	 *            The key of the title in mpstringlabel.properties
	 * @param request
	 */
	public void setTitle(String title, HttpServletRequest request) {
		request.setAttribute("pageTitle", title);
	}

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
		ActionForward retValue = null;
		setTitle("title.common",httpServletRequest);
		return retValue;
	}

}
