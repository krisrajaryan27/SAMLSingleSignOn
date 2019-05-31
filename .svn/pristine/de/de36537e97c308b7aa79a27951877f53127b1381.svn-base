/**
 * 
 */
package com.talentPool.ie.servlets;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.talentPool.admin.manager.AdminManager;
import com.talentPool.applicant.bc.ApplicantBC;
import com.talentPool.applicant.bc.Soap;
import com.talentPool.applicant.dataobject.ApplicantData;
import com.talentPool.applicant.dataobject.EducationalData;
import com.talentPool.applicant.dataobject.EmploymentHistoryData;
import com.talentPool.applicant.form.ApplicantForm;
import com.talentPool.applicant.manager.ApplicantManager;
import com.talentPool.applicant.utils.ApplicantUtils;
import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.properties.TPApplicationProperties;
import com.talentPool.common.utils.FileHandler;
import com.talentPool.common.utils.Utils;
import com.talentPool.common.utils.Exception.RChilliParseException;
import com.talentPool.desktop.constants.DesktopConstants;
import com.talentPool.desktop.manager.BulkImportManager;
import com.talentPool.documents.DocumentConstants;
import com.talentPool.documents.dataobject.DocumentData;
import com.talentPool.documents.utils.DocumentUploader;
import com.talentPool.documents.utils.DocumentUtils;
import com.talentPool.employeeservice.dataobject.EapplicantData;
import com.talentPool.employeeservice.manager.EmployeeApplicantManager;
import com.talentPool.employeeservice.utils.EmployeeMarshaller;
import com.talentPool.ie.constants.ImportConstants;
import com.talentPool.inbox.InboxConstants;
import com.talentPool.inbox.dataobject.AttachmentData;
import com.talentPool.inbox.dataobject.MessageData;
import com.talentPool.masters.dataobject.SourceData;
import com.talentPool.parser.converter.GenericConverter;
import com.talentPool.parser.converter.WordToHtmlConverter;
import com.talentPool.vendorservice.dataobject.VapplicantData;
import com.talentPool.vendorservice.dataobject.VerrorData;
import com.talentPool.vendorservice.manager.VendorApplicantManager;
import com.talentPool.vendorservice.utils.VendorMarshaller;
import com.talentPool.websiteservice.dataobject.WapplicantData;
import com.talentPool.websiteservice.dataobject.WerrorData;
import com.talentPool.websiteservice.manager.WebsiteApplicantManager;
import com.talentPool.websiteservice.utils.WebsiteMarshaller;

/**
 * @author pallavi
 * 
 */
public class ImportServlet extends HttpServlet {
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}

	/**
	 * Destroys the servlet.
	 */
	public void destroy() {

	}

	/**
	 * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
	 * methods.
	 * 
	 * @param request
	 *            servlet request
	 * @param response
	 *            servlet response
	 */
	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		boolean success = false;
		String uploadFileName = "";
		String sessionId = "";
		String responseText = "";
		String mode = request.getHeader(ImportConstants.MODE);
		String fromPortal = request.getHeader(ImportConstants.FROM_PORTAL);
		String fileName = request.getHeader(ImportConstants.HEADER_FILE_NAME);
		String folderPath = request.getHeader(ImportConstants.HEADER_FOLDER_PATH);
		String newFileName = request.getHeader(ImportConstants.HEADER_NEW_FILE_NAME);
		String isParsingRequired = request.getHeader(ImportConstants.IS_PARSING_REQUIRED);
		String userId = request.getHeader(ImportConstants.USER_ID);
		try {
			sessionId = request.getHeader(ImportConstants.SESSION_ID);			
			BulkImportManager bulkImportManager = new BulkImportManager();			
			
			if(ImportConstants.AUTHORIZE.equals(mode)) {
				//do Nothing.
				// Don't treat this as dead code.
			} else {
				if(ImportConstants.GET_BULK_IMPORT_SESSION_ID.equals(mode)) { 
					sessionId = request.getRemoteAddr() + "_" + System.currentTimeMillis();
					bulkImportManager.createBrowserBulkImportSession(sessionId, DesktopConstants.SESSION_TYPE_BROWSER_IMPORT, DesktopConstants.SESSION_STATUS_INPROCESS);
				} else if(ImportConstants.UPLOAD_FILE.equals(mode)) {
					if(!Utils.isBlankOrNull(folderPath)) {						
						uploadFileName = saveSupportedFiles(fileName, folderPath, request);
					} else {
						DocumentData documentData = saveSingleDocument(fileName, request);					
						uploadFileName = documentData.getRelativeFilePath();						
					}					
				} else if(ImportConstants.CREATE_BULK_IMPORT_SESSION_EMAIL.equals(mode)) {
					DocumentData documentData = saveSingleDocument(fileName, request);
					uploadFileName = documentData.getRelativeFilePath();
					
					String srcFilePath = Utils.concatFilePath(DocumentConstants.documentsPath, uploadFileName);
					WordToHtmlConverter converter = new WordToHtmlConverter();
					converter.convertToHtml(srcFilePath);
					
					String emailBodyFile = request.getHeader(ImportConstants.EMAIL_BODY_FILE);
					String commentUrl = request.getHeader(ImportConstants.HEADER_COMMENT_URL);
					MessageData data = getMessageDataForBrowserImportSession(emailBodyFile, uploadFileName, fileName, sessionId, commentUrl);					
					bulkImportManager.saveMessage(data);
					
					// Save commentUrl Here
				}  else if(ImportConstants.UPLOAD_FILE_AND_PARSE.equals(mode)) {
					if(!Utils.isBlankOrNull(folderPath)) {						
						uploadFileName = saveSupportedFiles(fileName, folderPath, request);
					} else {
						DocumentData documentData = saveSingleDocument(fileName, request);					
						uploadFileName = documentData.getRelativeFilePath();
						Soap soap = new Soap();
						try {
							ApplicantData data = new ApplicantData();
							if ("true".equalsIgnoreCase(isParsingRequired)){
								if("1".equalsIgnoreCase(TPApplicationProperties.getProperty("is_rchilli_integration"))){
									data = soap.rchilliParseAndSetData(documentData.getAbsoluteFilePath(), "");
								}else{
									ApplicantBC appBC = new ApplicantBC();
									GenericConverter conv = new GenericConverter();
									String filePath = Utils.concatFilePath(DocumentConstants.documentsPath,uploadFileName);
									String textContent = conv.convert(filePath);
									ApplicantForm form = new ApplicantForm();
									form.setApplicantTextResume(textContent);
									AdminManager adminManger = new AdminManager();
									SourceData sourceData = adminManger.getCandidatePortalSource();
									form.setSourceId(sourceData.getSourceId());
									form.setSource(sourceData.getSourceTitle());
									appBC.parseAndSetFormFields(form, DocumentUtils.getHTMLFileContent(filePath), textContent, filePath, userId);
									ApplicantUtils appUtils = new ApplicantUtils();
									data = appUtils.getApplicantDataConstructed(form);
								}
							}
							data.setApplicantOriginalResumePath(uploadFileName);
							if(ImportConstants.EMPLOYEE_PORTAL.equals(fromPortal)){
								EmployeeApplicantManager manager = new EmployeeApplicantManager();
								EapplicantData eData = manager.getEapplicantDataFromApplicantData(data);
								EmployeeMarshaller employeeMarshaller = new EmployeeMarshaller();
								responseText = employeeMarshaller.marshallApplicantData(eData);
							}
							if(ImportConstants.VENDOR_PORTAL.equals(fromPortal)){
								VendorApplicantManager manager = new VendorApplicantManager();
								VapplicantData eData = manager.getVapplicantDataFromApplicantData(data);
								VendorMarshaller vendorMarshaller = new VendorMarshaller();
								responseText =vendorMarshaller.marshallApplicantData(eData);
							}
							if(ImportConstants.WEBSITE_PORTAL.equals(fromPortal)){
								WebsiteApplicantManager manager = new WebsiteApplicantManager();
								WapplicantData eData = manager.getWapplicantDataFromApplicantData(data);
								WebsiteMarshaller websiteMarshaller = new WebsiteMarshaller();
								responseText = websiteMarshaller.marshallApplicantData(eData);
							}
							if(ImportConstants.CANDIDATE_PORTAL.equals(fromPortal)){
								WebsiteApplicantManager manager = new WebsiteApplicantManager();
								WapplicantData eData = manager.getWapplicantDataFromApplicantData(data);
								Gson gson = new GsonBuilder().create();
								String json = gson.toJson(eData);
								responseText = json;
							}
						} catch (RChilliParseException e){
							if(ImportConstants.EMPLOYEE_PORTAL.equals(fromPortal)){
								EmployeeMarshaller employeeMarshaller = new EmployeeMarshaller();
								responseText = employeeMarshaller.marshallErrorData(e.getMessage());
							}
							if(ImportConstants.VENDOR_PORTAL.equals(fromPortal)){
								VendorMarshaller vendorMarshaller = new VendorMarshaller();
								VerrorData error = new VerrorData();
								ArrayList<String> errors = new ArrayList<String>();
								errors.add(e.getMessage());
								error.setErrors(errors);
								responseText =vendorMarshaller.marshallErrorData(error);
							}
							if(ImportConstants.WEBSITE_PORTAL.equals(fromPortal)){
								WebsiteMarshaller websiteMarshaller = new WebsiteMarshaller();
								WerrorData error = new WerrorData();
								ArrayList<String> errors = new ArrayList<String>();
								errors.add(e.getMessage());
								error.setErrors(errors);
								responseText = websiteMarshaller.marshallErrorData(error);
							}
							if(ImportConstants.CANDIDATE_PORTAL.equals(fromPortal)){
								WebsiteMarshaller websiteMarshaller = new WebsiteMarshaller();
								WerrorData error = new WerrorData();
								ArrayList<String> errors = new ArrayList<String>();
								errors.add(e.getMessage());
								error.setErrors(errors);
								responseText = websiteMarshaller.marshallErrorData(error);
							}
						}
						
					}					
				}else {
					if (Utils.isBlankOrNull(folderPath) && Utils.isBlankOrNull(newFileName)) {
						DocumentData documentData = saveSingleDocument(fileName, request);
						uploadFileName = documentData.getRelativeFilePath();
					} else if (!Utils.isBlankOrNull(folderPath)) {
						uploadFileName = saveSupportedFiles(fileName, folderPath, request);
					} else if (!Utils.isBlankOrNull(newFileName)) {
						uploadFileName = createNewFileName();
					}
					
					if(Utils.isBlankOrNull(newFileName) && Utils.isBlankOrNull(folderPath)) {
						sessionId = request.getHeader(ImportConstants.SESSION_ID);
						
						if(Utils.isBlankOrNull(sessionId)) {
							sessionId = request.getRemoteAddr() + "_" + System.currentTimeMillis();
						} else {
							String srcFilePath = Utils.concatFilePath(DocumentConstants.documentsPath, uploadFileName);
							WordToHtmlConverter converter = new WordToHtmlConverter();
							converter.convertToHtml(srcFilePath);
						}	
						String commentUrl = request.getHeader(ImportConstants.HEADER_COMMENT_URL);
						
						ApplicantManager manager = new ApplicantManager();
						manager.createBrowserImportSessionDocument(sessionId, fileName, uploadFileName, commentUrl);
					}
				}				
				success = true;
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("ERROR ==>" + e.getMessage());
		}
		if(!ImportConstants.AUTHORIZE.equals(mode) && !ImportConstants.UPLOAD_FILE_AND_PARSE.equals(mode)) {
			if (success) {
				responseText = ImportConstants.SUCCESS + " " + uploadFileName + " " + sessionId;
			} else {
				responseText = ImportConstants.FAIL;
			}
		}
		writeResponse(response, responseText);		
	}

	private void writeResponse(HttpServletResponse response, String responseText) throws IOException {
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");
		out.print(responseText);
	}
	
	private String createNewFileName() {
		String fileName = "";
		try {
			DocumentUploader documentUploader = new DocumentUploader();
			String destinationPath = documentUploader.createTodayFolder(DocumentConstants.documentsPath);
			fileName = File.createTempFile("ATT", "", new File(destinationPath)).getName();
			File file = new File(destinationPath +"\\" + fileName);
			if (file.exists()) {
				file.delete();
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR + e.getMessage());
		}
		return fileName;
	}

	private DocumentData saveSingleDocument(String fileName, HttpServletRequest request) throws Exception {
		DocumentUploader documentUploader = new DocumentUploader();
		DocumentData documentData = documentUploader.saveFileFromBrowser(request.getInputStream(), fileName);
		return documentData;
	}

	private String saveSupportedFiles(String fileName, String folderPath, HttpServletRequest request) throws Exception {
		DocumentUploader documentUploader = new DocumentUploader();
		String uploadedFileName = documentUploader.saveSupportedFileFromBrowser(request.getInputStream(), fileName, folderPath);
		return uploadedFileName;
	}

	private MessageData getMessageDataForBrowserImportSession(String emailBodyFile, String emailAttachmentFilePath, 
			String emailAttachmentFileName, String sessionId, String commentUrl) throws Exception {
		MessageData messageData = new MessageData();
		
		messageData.setSessionId(sessionId);
		messageData.setTo("");
		messageData.setCc("");
		messageData.setFrom("");
		messageData.setFromAddress("");		
		if(!Utils.isBlankOrNull(emailBodyFile)) {
			FileHandler fileHandler = new FileHandler();
			String filePath = Utils.concatFilePath(DocumentConstants.documentsPath, emailBodyFile);
			String htmlBody = fileHandler.getTextFileContent(filePath, null);
						
			ApplicantUtils applicantUtils = new ApplicantUtils();
			htmlBody = applicantUtils.getImagePathReplaced(htmlBody, emailBodyFile); // for images			
			htmlBody = applicantUtils.getJsPathReplaced(htmlBody, emailBodyFile, "script", "src"); // for JS
			htmlBody = applicantUtils.getJsPathReplaced(htmlBody, emailBodyFile, "link", "href"); // for CSS		
			
			messageData.setHtmlBody(htmlBody);
			
			GenericConverter conv = new GenericConverter();
			String textContent = conv.convert(filePath);
			messageData.setTextBody(textContent);
		} else {
			messageData.setHtmlBody("");
			messageData.setTextBody("");
		}		
		if(!Utils.isBlankOrNull(emailAttachmentFilePath)) {
			AttachmentData attachmentData = new AttachmentData();
			attachmentData.setAttachmentFilePath(emailAttachmentFilePath);
			attachmentData.setOriginalFileName(emailAttachmentFileName);
			attachmentData.setContentType("");
			attachmentData.setContentId("");
			attachmentData.setAttachmentType(InboxConstants.ATTACHMENT_TYPE_NOTRELATED);
			attachmentData.setImportStatus(DesktopConstants.IMPORT_STATUS_FILES_TRANSFERRED);
			
			File file = new File(Utils.concatFilePath(DocumentConstants.documentsPath, emailAttachmentFilePath));
			attachmentData.setAttachmentSize(file.getTotalSpace());
			
			ArrayList<AttachmentData> attachments = new ArrayList<AttachmentData>();
			attachments.add(attachmentData);
			messageData.setAttachments(attachments);
		}
		messageData.setEntryId("");
		messageData.setSendDate(Utils.convertDateToSQLDate(new Date()));
		messageData.setReceivedDate(Utils.convertDateToSQLDate(new Date()));
		messageData.setImportStatus(DesktopConstants.IMPORT_STATUS_FILES_TRANSFERRED);
		messageData.setCommentUrl(commentUrl);
		return messageData;
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
