/**
 * 
 */
package com.talentPool.desktop.manager;

import java.io.File;
import java.sql.Date;
import java.util.ArrayList;

import com.talentPool.applicant.ApplicantConstants;
import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.utils.FileHandler;
import com.talentPool.common.utils.FileUploader;
import com.talentPool.common.utils.RegexUtils;
import com.talentPool.common.utils.Utils;
import com.talentPool.desktop.constants.DesktopConstants;
import com.talentPool.desktop.utils.DesktopUploadUtils;
import com.talentPool.documents.DocumentConstants;
import com.talentPool.documents.utils.DocumentUploader;
import com.talentPool.inbox.InboxConstants;
import com.talentPool.inbox.dataobject.AttachmentData;
import com.talentPool.inbox.dataobject.MessageData;
import com.talentPool.inbox.manager.InboxManager;
import com.talentPool.inbox.utils.InboxUtils;


/**
 * @author shivprasad
 * 
 */
public class EmailAttacher {
	private String absoluteSessionFolderPath;

	public EmailAttacher() {
	}

	public ArrayList<ArrayList<String>> addEmailToCandidateHistory(String sessionId, String applicantId, String userId) {
		ArrayList<String> emailsToAttach = new ArrayList<String>();
		ArrayList<String> attachedEmails = new ArrayList<String>();
		ArrayList<ArrayList<String>> result = new ArrayList<ArrayList<String>>();
		try {

			this.absoluteSessionFolderPath = Utils.concatFilePath(DesktopConstants.uploadsPath, sessionId);
			FileHandler fileHandler = new FileHandler();
			File sessionFolder = new File(this.absoluteSessionFolderPath);
			if (sessionFolder.exists()) {
				File[] emails = sessionFolder.listFiles();
				if (emails != null) {
					for (int i = 0; i < emails.length; i++) {
						if (emails[i].isDirectory()) {

							String emailFolder = emails[i].getName();
							emailsToAttach.add(emailFolder);

							String textBody = getFileContent(fileHandler, DesktopConstants.TEXT_BODY_FILE, emailFolder);
							String htmlBody = getFileContent(fileHandler, DesktopConstants.HTML_BODY_FILE, emailFolder);
							String headerContent = getFileContent(fileHandler, DesktopConstants.EMAIL_HEADER_FILE, emailFolder);
							// construct message date
							MessageData messageData = getMessageData(headerContent, textBody, htmlBody, applicantId);
							// copy files to documents folder excluding htmlbody.html and
							// txtbody.txt and prepare
							// attachements
							ArrayList<AttachmentData> attachments = getAttachments(fileHandler, emailFolder, htmlBody);

							messageData.setAttachments(attachments);
							messageData.setEntryId(emailFolder);
							try {
								InboxManager inboxManager = new InboxManager();
								String inboxFolder = getFolder(messageData.getSendDate(), messageData.getReceivedDate(), ApplicantConstants.APPLICANT_EMAIL_FOLDER_SENT);
								inboxManager.saveCommunicationMessage(messageData, inboxFolder, userId, ApplicantConstants.EMAIL_NOT_IMPORTED);
								attachedEmails.add(emailFolder);
							} catch (Exception e) {
								TPLogger.getLogger().error("ERROR ", e);
							}
						}
					}
				}
			}

		} catch (Exception e) {
			TPLogger.getLogger().error("ERROR ", e);
		}
		result.add(emailsToAttach);
		result.add(attachedEmails);
		return result;
	}

	private String getFolder(Date sentDate, Date receivedDate, String defaultFolder) {
		try {
			if (sentDate != null && receivedDate != null) {
				if (sentDate.after(receivedDate) || sentDate.equals(receivedDate)) {
					defaultFolder = ApplicantConstants.APPLICANT_EMAIL_FOLDER_SENT;
				} else {
					defaultFolder = ApplicantConstants.APPLICANT_EMAIL_FOLDER_INBOX;
				}
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("ERROR", e);
		}
		return defaultFolder;
	}

	/**
	 * Add email to tp_ibox_emails table. Convert the word attachment to html, required to import
	 * 
	 * @param sessionId
	 * @param userId
	 * @return emailId imported
	 */
	public String addEmailToInboxEmails(String sessionId, String userId) {
		String emailId = "";
		try {

			this.absoluteSessionFolderPath = Utils.concatFilePath(DesktopConstants.uploadsPath, sessionId);
			FileHandler fileHandler = new FileHandler();
			File sessionFolder = new File(this.absoluteSessionFolderPath);
			if (sessionFolder.exists()) {
				File[] emails = sessionFolder.listFiles();
				if (emails != null) {
					for (int i = 0; i < emails.length; i++) {
						if (emails[i].isDirectory()) {

							String emailFolder = emails[i].getName();
							String textBody = getFileContent(fileHandler, DesktopConstants.TEXT_BODY_FILE, emailFolder);
							String htmlBody = getFileContent(fileHandler, DesktopConstants.HTML_BODY_FILE, emailFolder);
							String headerContent = getFileContent(fileHandler, DesktopConstants.EMAIL_HEADER_FILE, emailFolder);
							// construct message date
							MessageData messageData = getMessageData(headerContent, textBody, htmlBody, "");
							// copy files to documents folder excluding htmlbody.html and txtbody.txt and prepare attachements
							ArrayList<AttachmentData> attachments = getAttachments(fileHandler, emailFolder, htmlBody);

							messageData.setAttachments(attachments);
							try {
								InboxManager inboxManager = new InboxManager();

								messageData.setAutoImportFormat(InboxConstants.FORMAT_NOAUTO_IMPORT);
								messageData.setEntryId(emailFolder);
								emailId = inboxManager.saveMessage(messageData, InboxConstants.INBOX_FOLDER_INBOX, userId);
								InboxUtils inboxUtils = new InboxUtils();
								inboxUtils.convertDocAttachments(messageData, DocumentConstants.documentsPath);

							} catch (Exception e) {
								TPLogger.getLogger().error("ERROR ", e);
							}
						}
					}
				}
			}

		} catch (Exception e) {
			TPLogger.getLogger().error("ERROR ", e);
		}
		return emailId;
	}

	public String addEmailToBulkSessionEmails(String sessionId, String emailFolder) {
		String emailId = "";
		try {
			this.absoluteSessionFolderPath = Utils.concatFilePath(DesktopConstants.uploadsPath, sessionId);
			FileHandler fileHandler = new FileHandler();
			File email = new File(Utils.concatFilePath(this.absoluteSessionFolderPath, emailFolder));
			if (email.exists()) {
				String textBody = getFileContent(fileHandler, DesktopConstants.TEXT_BODY_FILE, emailFolder);
				String htmlBody = getFileContent(fileHandler, DesktopConstants.HTML_BODY_FILE, emailFolder);
				String headerContent = getFileContent(fileHandler, DesktopConstants.EMAIL_HEADER_FILE, emailFolder);
				// construct message date
				MessageData messageData = getMessageData(headerContent, textBody, htmlBody, "");
				// copy files to documents folder excluding htmlbody.html and
				// txtbody.txt and prepare
				// attachements
				ArrayList<AttachmentData> attachments = getAttachments(fileHandler, emailFolder, htmlBody);

				messageData.setAttachments(attachments);
				try {

					messageData.setEntryId(emailFolder);
					messageData.setSessionId(sessionId);
					messageData.setImportStatus(DesktopConstants.IMPORT_STATUS_FILES_TRANSFERRED);
					BulkImportManager bulkImportManager = new BulkImportManager();
					emailId = bulkImportManager.saveMessage(messageData);
					// InboxUtils inboxUtils = new InboxUtils();
					// inboxUtils.convertDocAttachments(messageData,
					// DocumentConstants.documentsPath);

				} catch (Exception e) {
					TPLogger.getLogger().error("ERROR ", e);
				}
			}

		} catch (Exception e) {
			TPLogger.getLogger().error("ERROR ", e);
		}
		return emailId;
	}

	private String getFileContent(FileHandler fileHandler, String fileName, String emailFolderName) {
		String content = "";
		try {
			String filePath = Utils.concatFilePath(this.absoluteSessionFolderPath, emailFolderName);
			filePath = Utils.concatFilePath(filePath, fileName);
			content = fileHandler.getTextFileContent(filePath, null);
		} catch (Exception e) {
			TPLogger.getLogger().debug("ERROR Reading file", e);
		}
		return content;
	}

	private MessageData getMessageData(String headerContent, String textBody, String htmlBody, String applicantId) {
		// emailfrom:shivprasad <shivprasad@talentpool.in>
		// emailto:umesh.demo@talentpool.in
		// emailcc:
		// emailbcc:
		// emailsubject:FW: [talentica-all] Cut N Paste.....
		// datesent:04-12-2008 02:28:58 PM
		// datereceived:04-12-2008 02:33:13 PM
		// emailsize:218778
		MessageData messageData = new MessageData();
		DesktopUploadUtils desktopUploadUtils = new DesktopUploadUtils();
		ArrayList<String> groups = desktopUploadUtils.getHeaderGroups(headerContent, desktopUploadUtils.headerFormatExp);
		messageData.setFrom(groups.get(0));
		messageData.setFromAddress(Utils.getEmailAddress(groups.get(0)));
		messageData.setTo(groups.get(1));
		messageData.setCc(groups.get(2));
		messageData.setBcc(groups.get(3));
		messageData.setSubject(groups.get(4));
		String emailDateSend = groups.get(5);
		if (!Utils.isBlankOrNull(emailDateSend)) {
			try {
				messageData.setSendDate(Utils.convertToSQLDate(emailDateSend, Utils.regDDMMYYYYHHMMSSEFromat));
			} catch (Exception e) {
				TPLogger.getLogger().debug("ERROR", e);
			}
		}
		String emailDateReceived = groups.get(6);
		if (!Utils.isBlankOrNull(emailDateReceived)) {
			try {
				messageData.setReceivedDate(Utils.convertToSQLDate(emailDateReceived, Utils.regDDMMYYYYHHMMSSEFromat));
			} catch (Exception e) {
				TPLogger.getLogger().debug("ERROR", e);
			}
		}
		String emailSize = groups.get(7);
		try {
			messageData.setSize(new Integer(emailSize).intValue());
		} catch (Exception e) {
			TPLogger.getLogger().debug("ERROR", e);
		}
		messageData.setTextBody(textBody);
		messageData.setHtmlBody(htmlBody);
		messageData.setApplicantId(applicantId);
		return messageData;
	}

	private ArrayList<AttachmentData> getAttachments(FileHandler fileHandler, String emailFolderName, String htmlBody) throws Exception {
		ArrayList<AttachmentData> attachments = new ArrayList<AttachmentData>();
		DocumentUploader documentUploader = new DocumentUploader();
		FileUploader fileUploader = new FileUploader();
		String dayFolderPath = documentUploader.createTodayFolder(DocumentConstants.documentsPath);
		String relativePath = dayFolderPath.substring(DocumentConstants.documentsPath.length() + 1);

		String absoluteEmailFolderPath = Utils.concatFilePath(this.absoluteSessionFolderPath, emailFolderName);

		File inputFolder = new File(absoluteEmailFolderPath);
		if (inputFolder.exists()) {
			File[] children = inputFolder.listFiles();
			if (children != null) {
				for (int i = 0; i < children.length; i++) {
					File child = children[i];
					String attachmentName = child.getName();
					if (!attachmentName.equals(DesktopConstants.TEXT_BODY_FILE) && !attachmentName.equals(DesktopConstants.HTML_BODY_FILE)
							&& !attachmentName.equals(DesktopConstants.EMAIL_HEADER_FILE)) {
						String uploadedFileName = fileUploader.copyFile(child, dayFolderPath, attachmentName, false, true);
						AttachmentData attachmentData = new AttachmentData(Utils.concatFilePath(relativePath, uploadedFileName), attachmentName);
						attachmentData.setAttachmentSize(child.length());
						String contentId = getContentId(attachmentName, htmlBody);
						if (!Utils.isBlankOrNull(contentId)) {
							attachmentData.setAttachmentType(InboxConstants.ATTACHMENT_TYPE_RELATED);
							attachmentData.setContentId(contentId);
						} else {
							attachmentData.setAttachmentType(InboxConstants.ATTACHMENT_TYPE_NOTRELATED);
						}
						attachmentData.setImportStatus(DesktopConstants.IMPORT_STATUS_FILES_TRANSFERRED);
						attachments.add(attachmentData);
					}
				}
			}
		}

		return attachments;
	}

	private String getContentId(String fileName, String htmlBody) {
		String contentId = null;
		try {
			if(!Utils.isBlankOrNull(htmlBody)){
				ArrayList<String> grps = RegexUtils.getGroups(htmlBody, "(?mids)cid:(" + fileName + ".*?)(\")");
				if (grps != null && grps.size() > 2) {
					contentId = grps.get(1);
				}
			}
		} catch (Exception e) {
			TPLogger.getLogger().debug("CID NOT FOUND FOR : " + fileName);
		}
		return contentId;
	}

	public static void main(String[] args) {
		// try {
		// FileHandler fileHandler = new FileHandler();
		// String content = fileHandler.getTextFileContent("c:\\ts-email-header.txt", null);
		// ArrayList<String> groups = DesktopUploadUtils.getHeaderGroups(content,
		// DesktopUploadUtils.headerFormatExp);
		// System.out.println(groups);
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		// EmailAttacher em = new
		// EmailAttacher("1","admin2122008191627","0000000090A25A2AB24669418C15694BE1B053CE24022000","201366","shivprasad@talentpool.in","dada@gmail.com","","","Fw:
		// this is test", "03/12/2008 03:15:30 PM", "03/12/2008 03:25:00 PM","12323");
		// try {
		// FileHandler fileHandler = new FileHandler();
		// String fileContent = fileHandler.getTextFileContent("c:/htmlbody.html", null);
		// TPLogger.getLogger().debug(fileContent);
		// //ArrayList<String> grps = RegexUtils.getMatches(fileContent,
		// "(?mids)cid:image001\\.jpg.*?\"");
		// ArrayList<String> grps = RegexUtils.getGroups(fileContent,
		// "(?mids)cid:(image001.jpg.*?)(\")");
		// System.out.println(grps);
		//			
		// } catch (Exception e) {
		// e.printStackTrace();
		// }

		try {
			EmailAttacher em = new EmailAttacher();
//			em.addEmailToInboxEmails("admin8122008145450", "1");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
