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
import com.talentPool.desktop.manager.BulkImportManager;
import com.talentPool.desktop.manager.EmailAttacher;
import com.talentPool.desktop.scheduler.BulkImportSessionProcessor;
import com.talentPool.desktop.utils.DesktopUploadUtils;
import com.talentPool.documents.dataobject.DocumentData;
import com.talentPool.documents.utils.DocumentUploader;


/**
 * @author shivprasad
 * 
 */
public class BulkImportServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

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
			String transferComplete = request.getHeader(DesktopConstants.HEADER_TRANSFER_COMPLETE);

			if (!Utils.isBlankOrNull(sessionId)) {
				String fileName = request.getHeader(DesktopConstants.HEADER_FILE_NAME);
				if (!Utils.isBlankOrNull(emailId)) {
					// add to document bulk import session, set
					String lastFile = request.getHeader(DesktopConstants.HEADER_IS_LAST_FILE);
					boolean isLastFile = new Boolean(lastFile).booleanValue();
					saveEmailDocument(sessionId, emailId, fileName, isLastFile, request);
				} else {
					// add to email bulk import session
					try{
						saveSingleDocument(sessionId, fileName, request);
					} catch(Exception e){
						TPLogger.getLogger().error("ERROR ==>" + e.getMessage());
					}
				}
			}
			if (new Boolean(transferComplete).booleanValue()) {
				new BulkImportSessionProcessor(sessionId);
			}
			success = true;

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

	private void saveEmailDocument(String sessionId, String emailFolder, String fileName, boolean isLastFile, HttpServletRequest request) throws Exception {
		DesktopUploadUtils desktopUploadUtils = new DesktopUploadUtils();
		desktopUploadUtils.saveUploadedEmailFile(request.getInputStream(), fileName, sessionId, emailFolder);
		if (isLastFile) {
			EmailAttacher emailAttacher = new EmailAttacher();
			emailAttacher.addEmailToBulkSessionEmails(sessionId, emailFolder);
		}
	}

	private void saveSingleDocument(String sessionId, String fileName, HttpServletRequest request) throws Exception {
		DocumentUploader documentUploader = new DocumentUploader();
		DocumentData documentData = documentUploader.saveFileFromStream(request.getInputStream(), fileName);
		BulkImportManager bulkImportManager = new BulkImportManager();
		bulkImportManager.saveDocument(sessionId, documentData.getRelativeFilePath(), fileName, DesktopConstants.IMPORT_STATUS_FILES_TRANSFERRED);
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
