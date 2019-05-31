/**
 * 
 */
package com.talentPool.desktop.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.utils.Utils;
import com.talentPool.desktop.manager.EmailAttacher;


/**
 * @author shivprasad
 * 
 */
public class ImportSingleEmailServlet extends HttpServlet {
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}

	/**
	 * Destroys the servlet.
	 */
	public void destroy() {

	}

	/**
	 * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
	 * 
	 * @param request
	 *            servlet request
	 * @param response
	 *            servlet response
	 */
	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String emailId = "";
		try {
			String sessionId = request.getParameter("sessionId");
			String userId = request.getParameter("userId");

			if (!Utils.isBlankOrNull(sessionId) && !Utils.isBlankOrNull(userId)) {
				EmailAttacher emailAttacher = new EmailAttacher();
				emailId = emailAttacher.addEmailToInboxEmails(sessionId, userId);
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("ERROR ==>" + e.getMessage());
		}
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");
		out.print(emailId);

	}

	/**
	 * Handles the HTTP <code>GET</code> method.
	 * 
	 * @param request
	 *            servlet request
	 * @param response
	 *            servlet response
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * Handles the HTTP <code>POST</code> method.
	 * 
	 * @param request
	 *            servlet request
	 * @param response
	 *            servlet response
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * Returns a short description of the servlet.
	 */
	public String getServletInfo() {
		return "Short description";
	}

}
