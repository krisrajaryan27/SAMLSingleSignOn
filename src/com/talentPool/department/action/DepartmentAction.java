package com.talentPool.department.action;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.base.TPDispatchAction;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.utils.CommonUtils;
import com.talentPool.common.utils.Utils;
import com.talentPool.department.form.DepartmentForm;
import com.talentPool.department.manager.DepartmentManager;
import com.talentPool.masters.dataobject.DepartmentData;
import com.talentPool.user.manager.SessionManager;

public class DepartmentAction extends TPDispatchAction{

	public ActionForward getSubDepartmentJS(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "xmlFile";
		String xmlFile = "";
		try {
			if (!SessionManager.isLoginFound(mapping, actionForm, request, response, this)) {
				xmlFile = Utils.getXMLForSessionExpiry();
				forward = "xmlFile";
			} else {
				DepartmentForm departmentForm = (DepartmentForm) actionForm;
				DepartmentManager departmentManager = new DepartmentManager();
				ArrayList<DepartmentData> departments = departmentManager.getSubDepartments(departmentForm.getDepartmentId());
				xmlFile = CommonUtils.getJobCodeListJavaScriptArrayWithProperties(departments, "itemId", "itemName","itemExternalCode");
			}

		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			xmlFile = Utils.getXMLForError();
		}
		request.setAttribute("xmlFile", xmlFile);
		return mapping.findForward(forward);
	}
	
}
