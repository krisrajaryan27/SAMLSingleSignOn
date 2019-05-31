/**
 * 
 */
package com.talentPool.todo.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.base.TPDispatchAction;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.todo.manager.ToDoManager;
import com.talentPool.user.manager.SessionManager;

/**
 * @author Ajeet
 *
 */
public class ToDoAction extends TPDispatchAction{

	public ActionForward regenerateAllToDos(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "regenerateAllToDos";
		TPLogger.getLogger().debug("Start TODO generation");
		try {
			System.out.println("Start TODO generation");
			ToDoManager toDoManager = new ToDoManager();
			toDoManager.regenerateAllToDos();
			System.out.println("Finish TODO generation");
			TPLogger.getLogger().debug("Finish TODO generation");
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return mapping.findForward(forward);
	}
}
