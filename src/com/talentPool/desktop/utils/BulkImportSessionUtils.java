/**
 * 
 */
package com.talentPool.desktop.utils;

import java.util.ArrayList;
import java.util.List;

import com.talentPool.applicant.bc.ApplicantBC;
import com.talentPool.applicant.dataobject.ApplicantData;
import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.utils.FileHandler;
import com.talentPool.common.utils.Utils;
import com.talentPool.desktop.constants.DesktopConstants;
import com.talentPool.desktop.dataobjects.BulkImportSessionData;
import com.talentPool.desktop.manager.BulkImportManager;
import com.talentPool.documents.DocumentConstants;
import com.talentPool.documents.dataobject.DocumentData;
import com.talentPool.documents.utils.DocumentUploader;
import com.talentPool.inbox.dataobject.AttachmentData;
import com.talentPool.inbox.dataobject.MessageData;
import com.talentPool.parser.converter.GenericConverter;
import com.talentPool.parser.converter.HTMLToPlainTextConverter;
import com.talentPool.parser.converter.WordToHtmlConverter;


/**
 * @author pallavi
 *
 */
public class BulkImportSessionUtils {
	private BulkImportManager bulkImportManager;
	private WordToHtmlConverter wordToHtmlConverter;
	private GenericConverter genericConverter;
	private FileHandler fileHandler;
	private ApplicantBC applicantBC;
	private DocumentUploader documentUploader;
	
	
	
	public BulkImportSessionUtils() {
		super();
		bulkImportManager = new BulkImportManager();
		wordToHtmlConverter = new WordToHtmlConverter();
		genericConverter = new GenericConverter();
		fileHandler = new FileHandler();
		applicantBC = new ApplicantBC();
		documentUploader = new DocumentUploader();
	}

	public void processSingleEmail(MessageData email, BulkImportSessionData bulkImportSessionData) {
		try {
			// fetch emails or documents and process import
			ArrayList<AttachmentData> attachments = email.getAttachments();
			boolean resumeAsAttachment = false;
			for (int x = 0; attachments != null && x < attachments.size() && !DesktopConstants.SESSION_TYPE_BROWSER_IMPORT.equals(bulkImportSessionData.getSessionType()); x++) {
				AttachmentData aData = (AttachmentData) attachments.get(x);
				String relativeFilePath = aData.getAttachmentFilePath();
				if (parseSingleDocument(relativeFilePath, bulkImportSessionData, email.getMessageId(), null)) {
					resumeAsAttachment = true;
				}
			}
			if (!resumeAsAttachment) {
				String resumeBody = "";
				if (!Utils.isBlankOrNull(email.getHtmlBody())) {
					resumeBody = email.getHtmlBody();
				} else if (!Utils.isBlankOrNull(email.getTextBody())) {
					resumeBody = email.getTextBody();
				}
				DocumentData documentData = documentUploader.saveTextAsDocument(resumeBody, ".html");
				if (parseSingleDocument(documentData.getRelativeFilePath(), bulkImportSessionData, email.getMessageId(), null)) {

				}

			}

		} catch (Exception e) {
			TPLogger.getLogger().error(e);
		}
	}

	public boolean parseSingleDocument(String relativeFilePath, BulkImportSessionData bulkImportSessionData, String emailId, String documentId) {
		boolean resumeParsed = false;
		try {
			String filePath = Utils.concatFilePath(DocumentConstants.documentsPath, relativeFilePath);
			String filePathInLowerCase = filePath.toLowerCase();
			
			if (isValidExtension(filePathInLowerCase)) {
				String resumePath = wordToHtmlConverter.convertToHtml(filePath);
				String textContent = genericConverter.convert(resumePath);
				String htmlContent = textContent;
				if (filePathInLowerCase.endsWith("html") || filePathInLowerCase.endsWith("htm")) {
					htmlContent = fileHandler.getTextFileContent(filePath, null);
				}
				ApplicantData applicantData = new ApplicantData();
				
				if(DesktopConstants.SESSION_TYPE_BROWSER_IMPORT.equals(bulkImportSessionData.getSessionType())) {					
					MessageData data = bulkImportManager.getEmailData(emailId);
					HTMLToPlainTextConverter converter = new HTMLToPlainTextConverter();
					String textBody = converter.convertText(data.getHtmlBody());
//					applicantData = applicantBC.getParsedApplicantData(htmlContent, textContent, filePath);
					// used html body for browser bulk import
					applicantData = applicantBC.getParsedApplicantData(data.getHtmlBody(), textBody, filePath);
					setPrefilledApplicantDetails(applicantData, bulkImportSessionData);	
					
					
					List<AttachmentData> attachments = data.getAttachments();
					if(attachments != null && attachments.size() > 0) {						
						String relativeResumePath = getRelativeResumePath(attachments.get(0).getAttachmentFilePath());
						applicantData.setApplicantOriginalResumePath(relativeResumePath);
						applicantData.setApplicantOriginalDocPath(attachments.get(0).getAttachmentFilePath());
					}
				} else {
					applicantData = applicantBC.getParsedApplicantData(htmlContent, textContent, filePath);
					setPrefilledApplicantDetails(applicantData, bulkImportSessionData);
					
					String relativeResumePath = getRelativeResumePath(relativeFilePath);		
					applicantData.setApplicantOriginalResumePath(relativeResumePath);
					applicantData.setApplicantOriginalDocPath(relativeFilePath);
				}

				applicantData.setApplicantTextResume(textContent);

				bulkImportManager.saveBulkParseResult(bulkImportSessionData.getSessionId(), emailId, documentId, applicantData.getApplicantName(), applicantData.getApplicantEmail1(), 
						applicantData.getApplicantEmail2(), applicantData.getApplicantCellPhone(), applicantData.getApplicantWorkPhone(), applicantData.getApplicantHomePhone(),
						applicantData.getApplicantCity(), applicantData.getApplicantSourceId(), applicantData.getApplicantCurrentEmployer(), applicantData.getCurrentCTC(), applicantData.getApplicantWorkingSince(),
						applicantData.getApplicantOriginalResumePath(), applicantData.getApplicantOriginalDocPath(), applicantData.getApplicantTextResume(), applicantData.getSkillIds(), applicantData.getEducationalDetails());
				resumeParsed = true;
			}

		} catch (Exception e) {
			TPLogger.getLogger().error(e);
		}
		return resumeParsed;
	}

	private String getRelativeResumePath(String relativeFilePath) {
		String filePath = Utils.concatFilePath(DocumentConstants.documentsPath, relativeFilePath);
		String relativeResumePath = documentUploader.getHtmlFilePathIfExist(filePath);
		relativeResumePath = (relativeResumePath == null) ? "" : relativeResumePath;
		if (relativeResumePath.length() > DocumentConstants.documentsPath.length()) {
			relativeResumePath = relativeResumePath.substring(DocumentConstants.documentsPath.length() + 1);
		}
		relativeResumePath = Utils.isBlankOrNull(relativeResumePath) ? relativeFilePath : relativeResumePath;
		return relativeResumePath;
	}
	
	private void setPrefilledApplicantDetails(ApplicantData applicantData, BulkImportSessionData bulkImportSessionData) {
		applicantData.setApplicantSourceId(bulkImportSessionData.getSourceId());
		if (!Utils.isBlankOrNull(bulkImportSessionData.getSkillIds())) {
			applicantData.setSkillIds(bulkImportSessionData.getSkillIds());
		}
	}
	
	private boolean isValidExtension(String fileNameInLowerCase) {
		boolean validExt = false;
		try {
			for (int i = 0; i < DocumentConstants.extensions.length; i++) {
				if (fileNameInLowerCase.endsWith("." + DocumentConstants.extensions[i])) {
					validExt = true;
					if (fileNameInLowerCase.endsWith(".docx") && !DocumentConstants.IS_OFFICE_2007) {
						validExt = false;
					}
					break;
				}
			}

		} catch (Exception e) {
			TPLogger.getLogger().debug("ERROR fileName = " + fileNameInLowerCase, e);
		}
		return validExt;
	}
}
