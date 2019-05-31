/**
 * 
 */
package com.talentPool.application.action;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.talentPool.application.manager.ApplicationManager;
import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.base.TPDispatchAction;
import com.talentPool.common.db.SimpleDataObject;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.properties.TPApplicationProperties;
import com.talentPool.common.utils.FileHandler;
import com.talentPool.common.utils.Utils;
import com.talentPool.license.manager.LicenseObj;
import com.talentPool.user.manager.SessionManager;

/**
 * @author shivprasad
 * 
 */
public class ApplicationAction extends TPDispatchAction {
	public ActionForward viewAbout(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "about";
		try {
			ApplicationManager applicationManager = new ApplicationManager();
			ArrayList<SimpleDataObject> history = applicationManager.getReleaseHistory();
			request.setAttribute("history", history);
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return mapping.findForward(forward);
	}

	public ActionForward getHelp(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "fileDownload";
		try {
			String filePath = Utils.concatFilePath(TPApplicationProperties.getProperty("installation.path"), TPApplicationProperties.getProperty("help.file.path"));
			request.setAttribute("filePath", filePath);
			FileHandler fileHandler = new FileHandler();
			String contentType = fileHandler.getContentType(filePath);
			request.setAttribute("contentType", contentType);

		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return mapping.findForward(forward);
	}
	
	public ActionForward getHelpMobile(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "fileDownload";
		try {
			String filePath = Utils.concatFilePath(TPApplicationProperties.getProperty("installation.path"), TPApplicationProperties.getProperty("mobile.help.file.path"));
			request.setAttribute("filePath", filePath);
			FileHandler fileHandler = new FileHandler();
			String contentType = fileHandler.getContentType(filePath);
			request.setAttribute("contentType", contentType);

		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return mapping.findForward(forward);
	}
	
	public ActionForward downloadPlugin(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "fileDownload";
		try {
			String pluginId=(String) request.getParameter("pluginId");
			String folderPath = Utils.concatFilePath(TPApplicationProperties.getProperty("installation.path"), TPApplicationProperties.getProperty("plugin.folder.name"));
			ApplicationManager applicationManager = new ApplicationManager();
			String filePath=Utils.concatFilePath(folderPath, applicationManager.getPluginFileName(pluginId));
			request.setAttribute("filePath", filePath);
			FileHandler fileHandler = new FileHandler();
			String contentType = fileHandler.getContentType(filePath);
			request.setAttribute("contentType", contentType);

		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return mapping.findForward(forward);
	}

	public ActionForward showAbout(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "showAbout";
		try {
			ApplicationManager applicationManager = new ApplicationManager();
			String version = applicationManager.getProductVersion();
			String validity = LicenseObj.getLicenseObject().getLicenseValidity();
			request.setAttribute("version", version);
			request.setAttribute("validity", validity);
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return mapping.findForward(forward);
	}
	
}
