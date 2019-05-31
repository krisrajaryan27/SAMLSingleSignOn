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
import com.talentPool.desktop.constants.DesktopConstants;
import com.talentPool.desktop.utils.DesktopUploadUtils;


/**
 * @author shivprasad
 * 
 */
public class UploadEmailServlet extends HttpServlet {
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
		boolean success = false;
		try {

			String sessionId = request.getHeader(DesktopConstants.HEADER_SESSION_ID);
			String emailId = request.getHeader(DesktopConstants.HEADER_EMAIL_ID);

			if (!Utils.isBlankOrNull(sessionId) && !Utils.isBlankOrNull(emailId)) {
				String fileName = request.getHeader(DesktopConstants.HEADER_FILE_NAME);
				DesktopUploadUtils desktopUploadUtils = new DesktopUploadUtils();
				desktopUploadUtils.saveUploadedEmailFile(request.getInputStream(), fileName, sessionId, emailId);
//				String isLastFile = request.getHeader(DesktopConstants.HEADER_IS_LAST_FILE);
//				if (new Boolean(isLastFile).booleanValue()) {
//					String userId = request.getHeader(DesktopConstants.HEADER_USER_ID);
//					String applicantId = request.getHeader(DesktopConstants.HEADER_APPLICANT_ID);
//					String emailFrom = request.getHeader(DesktopConstants.HEADER_EMAIL_FROM);
//					String emailTo = request.getHeader(DesktopConstants.HEADER_EMAIL_TO);
//					String emailCc = request.getHeader(DesktopConstants.HEADER_EMAIL_CC);
//					String emailBcc = request.getHeader(DesktopConstants.HEADER_EMAIL_BCC);
//					String emailSubject = request.getHeader(DesktopConstants.HEADER_EMAIL_SUBJECT);
//					String dateSent = request.getHeader(DesktopConstants.HEADER_DATE_SENT);
//					String dateReceived = request.getHeader(DesktopConstants.HEADER_DATE_RECEIVED);
//					String emailSize = request.getHeader(DesktopConstants.HEADER_EMAIL_SIZE);
//					EmailAttacher emailAttacher = new EmailAttacher(userId, sessionId, emailId, applicantId, emailFrom, emailTo, emailCc, emailBcc, emailSubject, dateSent, dateReceived, emailSize);
//				}
				success = true;
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("ERROR ==>" + e.getMessage());
		}
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");
		if (success) {
			out.print(DesktopConstants.SUCCESS);
		} else {
			out.print(DesktopConstants.FAIL);
		}

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
