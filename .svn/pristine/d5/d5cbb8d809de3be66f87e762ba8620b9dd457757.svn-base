package com.talentPool.custom.action;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.base.TPDispatchAction;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.custom.dataobject.CustomFieldData;
import com.talentPool.custom.form.CustomFieldForm;
import com.talentPool.custom.manager.CustomFieldManager;
import com.talentPool.custom.utils.CustomFieldUtils;
import com.talentPool.user.manager.SessionManager;

public class CustomFieldsFilterAction extends TPDispatchAction {
	public ActionForward showCustomFeildFilterScreen(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		CustomFieldForm customFieldForm = (CustomFieldForm) actionForm;
		CustomFieldManager customFieldManager = new CustomFieldManager();
		CustomFieldData customFieldData = customFieldManager.getCustomFieldByName(customFieldForm.getCustomFieldName(), true);
		request.setAttribute("customFieldData", customFieldData);
		String forward = "showCustomFeildFilterScreen";
		return mapping.findForward(forward);
	}
	
	public ActionForward openCustomFieldFilterScreen(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		ArrayList<CustomFieldData> customFieldDataMap = null;
		String forward = "openCustomFieldFilterScreen";
		try {
			customFieldDataMap = CustomFieldUtils.getPositonCustomFeids();
			request.setAttribute("customFieldDataList", customFieldDataMap);
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR,e);
		}
		return mapping.findForward(forward);
	}
}
