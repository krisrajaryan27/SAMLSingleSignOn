package com.talentPool.inbox.bc;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.utils.CommonUtils;
import com.talentPool.notifier.manager.TemplateManager;

public class InboxBC {
	public void getTemplates(HttpServletRequest request) {
		try {
			String userId = (String) request.getSession().getAttribute("userId");
			TemplateManager templateManager = new TemplateManager();
			ArrayList lst = templateManager.getAllTemplates(userId, false);
			ArrayList templateIds = new ArrayList();
			ArrayList templatesNames = new ArrayList();
			CommonUtils.populateIdsAndNames(lst, templateIds, templatesNames, "templateCode", "templateName", null);
			String templateJSArray = CommonUtils.getListJavaScriptArray(templateIds, templatesNames);
			request.setAttribute("templateJSArray", templateJSArray);
		} catch (Exception e) {
			TPLogger.getLogger().error("error while getting templates list ", e);
		}
	}
}
