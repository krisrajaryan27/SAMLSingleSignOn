/**
 * 
 */
package com.talentPool.desktop.manager;

import java.io.StringWriter;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import com.talentPool.applicant.ApplicantConstants;
import com.talentPool.applicant.dataobject.ApplicantData;
import com.talentPool.applicant.dataobject.ApplicantDuplicateSearchData;
import com.talentPool.applicant.dataobject.EducationalData;
import com.talentPool.applicant.manager.ApplicantDuplicateChecker;
import com.talentPool.applicant.manager.ApplicantManager;
import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.db.DBPreparedQuery;
import com.talentPool.common.db.DBTransaction;
import com.talentPool.common.db.SimpleDataObject;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.utils.Utils;
import com.talentPool.common.xmlutils.XMLWriter;
import com.talentPool.custom.constants.CustomFieldConstants;
import com.talentPool.custom.dataobject.CustomFieldData;
import com.talentPool.custom.manager.CustomFieldManager;
import com.talentPool.desktop.constants.DesktopConstants;
import com.talentPool.desktop.dataobjects.BulkImportResultData;
import com.talentPool.desktop.dataobjects.BulkImportSessionData;
import com.talentPool.documents.dataobject.DocumentData;
import com.talentPool.inbox.InboxConstants;
import com.talentPool.inbox.dataobject.AttachmentData;
import com.talentPool.inbox.dataobject.MessageData;
import com.talentPool.inbox.manager.InboxManager;
import com.talentPool.repository.TPIndexEvent;
import com.talentPool.repository.TPIndexEventQueue;
import com.talentPool.selectionProcess.SelectionProcessConstants;
import com.talentPool.selectionProcess.dataobject.CommunicationData;
import com.talentPool.selectionProcess.manager.SelectionProcessManager;


/**
 * @author shivprasad
 * 
 */
public class BulkImportManager {
	public String saveMessage(MessageData msg) throws Exception {
		DBTransaction tran = null;
		DBPreparedQuery dq = null;
		String emailId = "";
		try {
			tran = new DBTransaction();
			String subject = (Utils.isBlankOrNull(msg.getSubject())) ? "" : msg.getSubject();
			String fromEmail = (Utils.isBlankOrNull(msg.getFrom())) ? "" : msg.getFrom();
			subject = (subject.length() > 250) ? subject.substring(0, 249) : subject;
			fromEmail = (fromEmail.length() > 100) ? fromEmail.substring(0, 99) : fromEmail;

			dq = new DBPreparedQuery("dBulkImportManager_InsertEmail", tran);
			dq.setString(1, fromEmail);
			dq.setString(2, msg.getTo());
			dq.setString(3, msg.getCc());
			dq.setString(4, msg.getBcc());
			dq.setString(5, subject);
			if (msg.getSendDate() == null) {
				dq.setTimestamp(6, null);
			} else {
				dq.setTimestamp(6, new Timestamp(msg.getSendDate().getTime()));
			}
			if (msg.getReceivedDate() == null) {
				dq.setTimestamp(7, null);
			} else {
				dq.setTimestamp(7, new Timestamp(msg.getReceivedDate().getTime()));
			}
			dq.setString(8, msg.getTextBody());
			dq.setString(9, msg.getHtmlBody());
			dq.setInt(10, msg.getSize());
			dq.setString(11, msg.getEntryId());
			dq.setString(12, msg.getSessionId());
			dq.setString(13, msg.getImportStatus());
			dq.execute();
			dq = new DBPreparedQuery("dFetchLastInsertID", tran);
			emailId = dq.getIdResult();
			// Save the attachments
			ArrayList attachments = msg.getAttachments();
			if (attachments != null) {
				for (int i = 0; i < attachments.size(); i++) {
					AttachmentData aData = (AttachmentData) attachments.get(i);
					String contentId = (Utils.isBlankOrNull(aData.getContentId())) ? "" : aData.getContentId();
					contentId = (contentId.length() > 150) ? contentId.substring(0, 149) : contentId;
					dq = new DBPreparedQuery("dBulkImportManager_InsertAttachments", tran);
					dq.setId(1, emailId);
					dq.setString(2, aData.getAttachmentFilePath());
					dq.setString(3, aData.getOriginalFileName());
					dq.setString(4, aData.getContentType());
					dq.setString(5, contentId);
					dq.setString(6, aData.getAttachmentType());
					dq.setLong(7, aData.getAttachmentSize());
					dq.setString(8, aData.getImportStatus());
					dq.execute();
				}
			}
			tran.commit();
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			try {
				tran.rollback();
			} catch (Exception ex) {
				TPLogger.getLogger().error(GlobalConstants.ERROR, ex);
			}
			throw e;
		} finally {
			if (dq != null) {
				dq.releaseTransaction(tran);
			}
		}
		return emailId;
	}

	public String saveDocument(String sessionId, String documentPath, String fileName, String importStatus) throws Exception {
		DBTransaction tran = null;
		DBPreparedQuery dq = null;
		String documentId = "";
		try {
			tran = new DBTransaction();
			dq = new DBPreparedQuery("dBulkImportManager_InsertDocument", tran);
			dq.setString(1, sessionId);
			dq.setString(2, documentPath);
			dq.setString(3, fileName);
			dq.setString(4, importStatus);
			dq.execute();
			dq = new DBPreparedQuery("dFetchLastInsertID", tran);
			documentId = dq.getIdResult();
			tran.commit();
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			try {
				tran.rollback();
			} catch (Exception ex) {
				TPLogger.getLogger().error(GlobalConstants.ERROR, ex);
			}
			throw e;
		} finally {
			if (dq != null) {
				dq.releaseTransaction(tran);
			}
		}
		return documentId;
	}

	public BulkImportSessionData getBulkImportSessionData(String sessionId) {
		DBPreparedQuery dq = null;
		BulkImportSessionData aData = null;
		try {
			dq = new DBPreparedQuery("dBulkImportManager_GetSessionData");
			dq.setString(1, sessionId);
			aData = (BulkImportSessionData) dq.getSingleObjectResult();
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return aData;
	}
	
	
	public void saveBulkImportParameters(ApplicantData data, String sessionId,String sessionStatus, String sessionType, String userId, String skillIds, String categoryIds, String note) throws Exception {
		DBPreparedQuery dq = null;
		DBTransaction tran = null;
		try {
			tran = new DBTransaction();
			dq = new DBPreparedQuery("dBulkImportManager_InsertBulkImportParameters", tran);
			dq.setString(1, sessionId);
			if(data.getApplicantSourceId()==-1 || data.getApplicantSourceId()==0){
				dq.setNull(2, Types.NULL);
			}else{
				dq.setInt(2,data.getApplicantSourceId());
			}			
			dq.setString(3, data.getApplicantCity());
			dq.setDate(4, data.getApplicantWorkingSince());
			dq.setString(5, data.getApplicantCurrentEmployer());
			dq.setString(6, Utils.isBlankOrNull(data.getCurrentCTC()) ? null : data.getCurrentCTC());
			dq.setString(7, Utils.isBlankOrNull(data.getExpectedCTC()) ? null : data.getExpectedCTC());
			dq.setString(8, Utils.isBlankOrNull(data.getNoticePeriod()) ? null : data.getNoticePeriod());
			dq.setString(9, note);			
			dq.setString(10, sessionStatus);
			dq.setString(11, sessionType);
			dq.setId(12, userId);
			if(data.getApplicantSourceId()==-1 || data.getApplicantSourceId()==0){
				dq.setNull(13, Types.NULL);
			}else{
				dq.setInt(13,data.getApplicantSourceId());
			}			
			dq.setString(14, data.getApplicantCity());
			dq.setDate(15, data.getApplicantWorkingSince());
			dq.setString(16, data.getApplicantCurrentEmployer());
			dq.setString(17, Utils.isBlankOrNull(data.getCurrentCTC()) ? null : data.getCurrentCTC());
			dq.setString(18, Utils.isBlankOrNull(data.getExpectedCTC()) ? null : data.getExpectedCTC());
			dq.setString(19, Utils.isBlankOrNull(data.getNoticePeriod()) ? null : data.getNoticePeriod());
			dq.setString(20, note);
			dq.setId(21, userId);
			dq.execute();

			// save skills
			if (!Utils.isBlankOrNull(skillIds)) {
				String[] skillsIds = skillIds.split(",");
				for (int i = 0; i < skillsIds.length; i++) {
					dq = new DBPreparedQuery("dBulkImportManager_InsertBulkImportSkills", tran);
					dq.setString(1, sessionId);
					dq.setString(2, skillsIds[i]);
					dq.execute();
				}
			}
			
			// Add Educational Qualifications
			ArrayList eduDetails = data.getEducationalDetails();
			addEducationDetails(eduDetails, sessionId, tran);			
			// Add custom Field values
			CustomFieldManager customFieldManager = new CustomFieldManager();
			customFieldManager.insertCustomFieldValues(data.getCustomFields(), sessionId, CustomFieldConstants.ENTITY_TYPE_BULK_IMPORT, tran);
			tran.commit();

		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			try {
				tran.rollback();
			} catch (SQLException sq) {
				TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			}
			throw new Exception();
		} finally {
			if (dq != null) {
				dq.releaseTransaction(tran);
			}
		}
	}
	
	public void createBrowserBulkImportSession(String sessionId, String sessionType, String sessionStatus) throws SQLException {
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dBulkImportManager_InsertBrowserBulkImportParameters");				
			dq.setString(1, sessionId);
			dq.setString(2, sessionType);
			dq.setString(3, sessionStatus);
			dq.execute();		
		} finally {
			if(dq != null) {
				dq.releaseConnection();
			}
		}
	}
	
	public void addEducationDetails(ArrayList eduDetails, String sessionId, DBTransaction tran) throws Exception {
		DBPreparedQuery dq = null;
		try {
			if (eduDetails != null) {
				ApplicantManager applicantManager = new ApplicantManager();
				for (int i = 0; i < eduDetails.size(); i++) {
					EducationalData eData = (EducationalData) eduDetails.get(i);
					if (!Utils.isBlankOrNull(eData.getInstitute()) && eData.getInstitute().length()<150) {
						String instituteId = applicantManager.autoUpdateInstitute(eData.getInstitute().trim(), tran);
						eData.setInstituteId(instituteId);
					}
					dq = new DBPreparedQuery("dBulkImportManager_AddEducationInfo", tran);
					dq.setString(1, sessionId);
					dq.setId(2, eData.getDegreeId() <= 0 ? null : "" + eData.getDegreeId());
					dq.setString(3, eData.getMajorId() <= 0 ? null : "" + eData.getMajorId());
					dq.setString(4, Utils.isBlankOrNull(eData.getInstituteId()) ? null : eData.getInstituteId());
					dq.setDate(5, eData.getYearOfPassing());
					dq.setString(6, eData.getGrade());
					dq.execute();
				}
			}
		} catch (Exception e) {
			throw e;
		} finally {
			if (dq != null) {
				if (tran == null) {
					dq.releaseConnection();
				} else {
					dq.closeOpenCursors();
				}
			}
		}
	}

	public ArrayList<BulkImportResultData> getBulkImportProcessData(String sessionType, String sessionId, String lastRowId) {
		DBPreparedQuery dq = null;
		ArrayList<BulkImportResultData> data = null;
		try {
			String[] dynParams = new String[1];
			dynParams[0] = " ";
			ArrayList<String> dynamicContent = new ArrayList<String>();
			if (!Utils.isBlankOrNull(lastRowId)) {
				dynParams[0] += " AND tbir.result_id > ? ";
				dynamicContent.add(lastRowId);
			}

			if (DesktopConstants.SESSION_TYPE_EMAIL_IMPORT.equals(sessionType) || DesktopConstants.SESSION_TYPE_INBOX_BULK_IMPORT.equals(sessionType)
					|| DesktopConstants.SESSION_TYPE_BROWSER_IMPORT.equals(sessionType)) {
				dq = new DBPreparedQuery("dBulkImportManager_GetEmailBulkImportProcessData", dynParams);
			} else {
				dq = new DBPreparedQuery("dBulkImportManager_GetDocumentBulkImportProcessData", dynParams);
			}
			int cnt = 1;
			dq.setString(cnt++, sessionId);
			dq.setString(cnt++, DesktopConstants.IS_NOT_IMPORTED);
			for (int i = 0; i < dynamicContent.size(); i++) {
				dq.setString(cnt++, dynamicContent.get(i));
			}
			data = dq.getResult();
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return data;
	}

	public String getXMLforBulkImport(String sessionType, ArrayList<BulkImportResultData> data) {
		StringWriter sWr = new StringWriter();
		XMLWriter wr = new XMLWriter(sWr);
		try {
			wr.startDocument();
			wr.startElement("rows");
			for (int i = 0; i < data.size(); i++) {
				BulkImportResultData bData = data.get(i);

				AttributesImpl at = new AttributesImpl();
				at.addAttribute("", "id", "", "", "" + bData.getResultId());
				wr.startElement("", "row", "", at);

				at = new AttributesImpl();
				at.addAttribute("", "name", "", "", "Col_I_Comment");
				wr.startElement("", "userdata", "", at);
				wr.characters("Delete");
				wr.endElement("userdata");

				if (DesktopConstants.SESSION_TYPE_EMAIL_IMPORT.equals(sessionType) || DesktopConstants.SESSION_TYPE_INBOX_BULK_IMPORT.equals(sessionType)) {
					wr.startElement("cell");
					wr.characters(wr.doubleEscape(bData.getEmailFrom()));
					wr.endElement("cell");

					wr.startElement("cell");
					wr.characters(wr.doubleEscape(bData.getEmailSubject()));
					wr.endElement("cell");
				} else if(DesktopConstants.SESSION_TYPE_DESKTOP_IMPORT.equals(sessionType)){
					wr.startElement("cell");
					wr.characters(wr.doubleEscape(bData.getFileName()));
					wr.endElement("cell");
				}

				wr.startElement("cell");
				wr.characters(wr.doubleEscape(bData.getParsedName()));
				wr.endElement("cell");

				wr.startElement("cell");
				wr.characters(wr.doubleEscape(bData.getParsedEmail1()));
				wr.endElement("cell");

				wr.startElement("cell");
				wr.characters("<a href=\"#\" onclick=\"javascript:onClickDelete(" + bData.getResultId() + ");\">" + "<img src=\"images/ico_delete.gif\" style=\"border: 0\">");
				wr.endElement("cell");

				wr.endElement("row");
			}
			wr.endElement("rows");
			wr.endDocument();
		} catch (SAXException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return sWr.getBuffer().toString();
	}

	public ArrayList<MessageData> getEmailsToBulkImport(String sessionId) {
		ArrayList<MessageData> emails = null;
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dBulkImportManager_GetBulkImportEmails");
			dq.setString(1, DesktopConstants.IMPORT_STATUS_FILES_TRANSFERRED);
			dq.setString(2, sessionId);
			emails = dq.getResult();
			// Save the attachments
			if (emails != null) {
				for (int i = 0; i < emails.size(); i++) {
					ArrayList<AttachmentData> attachments = getEmailAttachments(emails.get(i).getMessageId());
					emails.get(i).setAttachments(attachments);
				}
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return emails;
	}

	public ArrayList<DocumentData> getDocumentsToBulkImport(String sessionId) {
		ArrayList<DocumentData> documents = null;
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dBulkImportManager_GetBulkImportDocuments");
			dq.setString(1, DesktopConstants.IMPORT_STATUS_FILES_TRANSFERRED);
			dq.setString(2, sessionId);
			documents = dq.getResult();
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return documents;
	}

	public String saveBulkParseResult(String sessionId, String emailId, String documentId, String name, String email1, String email2, String cellPhone, String workPhone, String homePhone,
			String city, int sourceId, String currentEmployer, String currentCtc, java.sql.Date workingSince,
			String originalResumePath, String originalDocPath, String textResume, String skillIds,ArrayList<EducationalData> eduData) throws Exception {
		DBTransaction tran = null;
		DBPreparedQuery dq = null;
		String resultId = "";
		try {
			tran = new DBTransaction();

			dq = new DBPreparedQuery("dBulkImportManager_InsertParseResult", tran);
			dq.setString(1, sessionId);
			dq.setString(2, emailId);
			dq.setString(3, documentId);
			dq.setString(4, name);
			dq.setString(5, email1);
			dq.setString(6, email2);
			dq.setString(7, cellPhone);
			dq.setString(8, workPhone);
			dq.setString(9, homePhone);
			dq.setString(10, originalResumePath);
			dq.setString(11, originalDocPath);
			dq.setString(12, textResume);
			
			dq.setString(13, city);
			dq.setDate(14, workingSince);
			if(!Utils.isBlankOrNull(currentCtc)){
				currentCtc = currentCtc.length()<=10?currentCtc:currentCtc.substring(0,10);
				dq.setString(15,currentCtc);
			}
			dq.setString(15,currentCtc);
			dq.setString(16, currentEmployer);			
			dq.setInt(17, sourceId);
			
			dq.execute();
			dq = new DBPreparedQuery("dFetchLastInsertID", tran);
			resultId = dq.getIdResult();
			// Save the skills
			if (!Utils.isBlankOrNull(skillIds)) {
				String[] skills = skillIds.split(",");
				for (int i = 0; i < skills.length; i++) {
					if (!Utils.isBlankOrNull(skills[i])) {
						dq = new DBPreparedQuery("dBulkImportManager_InsertParseResultSkills", tran);
						dq.setId(1, resultId);
						dq.setId(2, skills[i].trim());
						dq.execute();
					}
				}
			}
			if (eduData != null && eduData.size() > 0) {				
				addParsedEducationDetails(eduData, resultId, tran);
			}
			tran.commit();
		} catch (Exception e) {
			TPLogger.getLogger().error("Error bulk result save", e);
			try {
				tran.rollback();
			} catch (Exception ex) {
				TPLogger.getLogger().error(GlobalConstants.ERROR, ex);
			}
			throw e;
		} finally {
			if (dq != null) {
				dq.releaseTransaction(tran);
			}
		}
		return resultId;
	}

	public BulkImportResultData getParsedData(String resultId) {
		DBPreparedQuery dq = null;
		BulkImportResultData aData = null;
		try {
			dq = new DBPreparedQuery("dBulkImportManager_GetParsedData");
			dq.setString(1, resultId);
			aData = (BulkImportResultData) dq.getSingleObjectResult();
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return aData;
	}

	public void updateEmailImportStatus(String emailId, String status) {
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dBulkImportManager_UpdateEmailImportStatus");
			dq.setString(1, status);
			dq.setId(2, emailId);
			dq.execute();
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
	}

	public void updateDocumentImportStatus(String documentId, String status) {
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dBulkImportManager_UpdateDocumentImportStatus");
			dq.setString(1, status);
			dq.setId(2, documentId);
			dq.execute();
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
	}

	public void updateSessionImportStatus(String sessionId, String status) {
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dBulkImportManager_UpdateSessionImportStatus");
			dq.setString(1, status);
			dq.setString(2, sessionId);
			dq.execute();
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
	}

	public String getXMLFromParsedData(BulkImportResultData resultdata) {
		StringWriter sWr = new StringWriter();
		XMLWriter wr = new XMLWriter(sWr);
		try {
			wr.startDocument();
			wr.startElement("content");
			wr.startElement("name");
			wr.characters(wr.doubleEscape(resultdata.getParsedName()));
			wr.endElement("name");
			wr.startElement("phone1");
			wr.characters(wr.doubleEscape(resultdata.getParsedCellPhone()));
			wr.endElement("phone1");
			wr.startElement("phone2");
			wr.characters(wr.doubleEscape(resultdata.getParsedWorkPhone()));
			wr.endElement("phone2");
			wr.startElement("email1");
			wr.characters(wr.doubleEscape(resultdata.getParsedEmail1()));
			wr.endElement("email1");
			wr.startElement("email2");
			wr.characters(wr.doubleEscape(resultdata.getParsedEmail2()));
			wr.endElement("email2");
			wr.startElement("skills");
			wr.characters(wr.doubleEscape(resultdata.getParsedSkills()));
			wr.endElement("skills");
			wr.startElement("resumePath");
			wr.characters(wr.doubleEscape(resultdata.getParsedOriginalResumePath()));
			wr.endElement("resumePath");
			wr.startElement("resultId");
			wr.characters(wr.doubleEscape(resultdata.getResultId()));
			wr.endElement("resultId");
			wr.startElement("skillIds");
			wr.characters(wr.doubleEscape(resultdata.getParsedSkillIds()));
			wr.endElement("skillIds");
			wr.startElement("docPath");
			wr.characters(wr.doubleEscape(resultdata.getParsedOriginalDocPath()));
			wr.endElement("docPath");
			wr.endElement("content");
			wr.endDocument();
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return sWr.toString();
	}

	public void deleteParsedResult(String resultId) {
		DBPreparedQuery dq = null;
		DBTransaction tran = null;
		try {
			tran = new DBTransaction();

			dq = new DBPreparedQuery("dBulkImportManager_DeleteParsedResultSkill", tran);
			dq.setString(1, resultId);
			dq.execute();

			dq = new DBPreparedQuery("dBulkImportManager_DeleteParsedResult", tran);
			dq.setString(1, resultId);
			dq.execute();

			tran.commit();

		} catch (Exception exep) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, exep);
		} finally {
			if (dq != null) {
				dq.releaseTransaction(tran);
			}
		}
	}

	public void changeParsedResultData(BulkImportResultData resultdata) {
		DBPreparedQuery dq = null;
		try {
			if (resultdata != null) {
				dq = new DBPreparedQuery("dBulkImportManager_UpdateParsedResultData");
				dq.setString(1, resultdata.getParsedName());
				dq.setString(2, resultdata.getParsedCellPhone());
				dq.setString(3, resultdata.getParsedWorkPhone());
				dq.setString(4, resultdata.getParsedEmail1());
				dq.setString(5, resultdata.getResultId());
				dq.execute();
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
	}
	
	public ArrayList<EducationalData> getEducationalInfo(String sessionId) {
		DBPreparedQuery dq = null;
		ArrayList<EducationalData> educationalInfo = null;
		try {
			dq = new DBPreparedQuery("dBulkImportManager_GetEducationalInfo");
			dq.setString(1, sessionId);
			educationalInfo = dq.getResult();
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return educationalInfo;
	}
	

	public ArrayList<EducationalData> getParsedEducationalInfo(String resultId) {
		DBPreparedQuery dq = null;
		ArrayList<EducationalData> educationalInfo = null;
		try {
			dq = new DBPreparedQuery("dBulkImportManager_GetParsedEducationalInfo");
			dq.setString(1, resultId);
			educationalInfo = dq.getResult();
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return educationalInfo;
	}
	
	public void importBulkParsedResumes(BulkImportSessionData bulkImportSessionData) {
		try {
			ArrayList<BulkImportResultData> results = getBulkImportResultsToImport(bulkImportSessionData.getSessionId());
			String sessionId = bulkImportSessionData.getSessionId();
			if (results != null) {
				// get custom field values
				CustomFieldManager customFieldManager = new CustomFieldManager();
				ArrayList<CustomFieldData> customFields = customFieldManager.getCustomFieldDataForEntity(sessionId, CustomFieldConstants.ENTITY_TYPE_BULK_IMPORT);
				// get Education data
				ArrayList<EducationalData> educationDetails = getEducationalInfo(sessionId);				
				
				// check duplicate				
				ApplicantDuplicateChecker applicantDuplicateChecker = new ApplicantDuplicateChecker();
				ApplicantManager applicantManager = new ApplicantManager();
								
				for (int i = 0; i < results.size(); i++) {
					BulkImportResultData bulkImportResultData = results.get(i);

					ArrayList<ApplicantDuplicateSearchData> duplicates = applicantDuplicateChecker.getInternalDuplicateChecked("", bulkImportResultData.getParsedName(),
							bulkImportResultData.getParsedEmail1(), bulkImportResultData.getParsedEmail2(), bulkImportResultData.getParsedCellPhone(), null);
					if (duplicates == null || duplicates.size() == 0) { 
						// create applicant for result
						if(DesktopConstants.SESSION_TYPE_BROWSER_IMPORT.equals(bulkImportSessionData.getSessionType())|| DesktopConstants.SESSION_TYPE_DESKTOP_IMPORT.equals(bulkImportSessionData.getSessionType())) {
							bulkImportResultData.setEducationalDetails(getParsedEducationalInfo(bulkImportResultData.getResultId()));
							/*Override the parsed data by session parameters*/
							overrideParsedData(bulkImportResultData, bulkImportSessionData, educationDetails);						
						} else {					
							//set education data
							bulkImportResultData.setEducationalDetails(educationDetails);
						}
						//set custom field values						
						bulkImportResultData.setCustomFields(customFields);
						importSingleApplicant(bulkImportResultData, bulkImportSessionData.getUserId(), applicantManager, bulkImportSessionData.getSessionType());
					}
				}
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
	}
	
	private void overrideParsedData(BulkImportResultData bulkImportResultData, BulkImportSessionData bulkImportSessionData, ArrayList<EducationalData> educationDetails) {
		if(educationDetails != null && educationDetails.size() > 0) {
			boolean doOverride = false;
			for(int i = 0; i < educationDetails.size(); i++) {
				EducationalData eData = educationDetails.get(i);
				if(eData.getYearOfPassing() != null || !Utils.isBlankOrNull(eData.getInstitute()) || !Utils.isBlankOrNull(eData.getDegreeTitle()) || !Utils.isBlankOrNull(eData.getMajor()) || !Utils.isBlankOrNull(eData.getGrade())) { 
					doOverride = true;
					break;
				}
			}
			if(doOverride) {
				bulkImportResultData.setEducationalDetails(educationDetails);
			}			
		}							
		if(!Utils.isBlankOrNull(bulkImportSessionData.getApplicantCity())) {
			bulkImportResultData.setApplicantCity(bulkImportSessionData.getApplicantCity());
		}							
		if(!Utils.isBlankOrNull(bulkImportSessionData.getString("applicantIsFresher"))) {
			bulkImportResultData.setApplicantIsFresher(bulkImportSessionData.getString("applicantIsFresher"));
		}
		if(bulkImportSessionData.getApplicantWorkingSince() != null) {
			bulkImportResultData.setApplicantWorkingSince(bulkImportSessionData.getApplicantWorkingSince());
		}
		if(!Utils.isBlankOrNull(bulkImportSessionData.getCurrentEmployer())) {
			bulkImportResultData.setCurrentEmployer(bulkImportSessionData.getCurrentEmployer());
		}
		if(!Utils.isBlankOrNull(bulkImportSessionData.getCurrentCtc())) {
			bulkImportResultData.setCurrentCtc(bulkImportSessionData.getCurrentCtc());
		}
		if(!Utils.isBlankOrNull(bulkImportSessionData.getExpectedCtc())) {
			bulkImportResultData.setExpectedCtc(bulkImportSessionData.getExpectedCtc());
		}
		if(!Utils.isBlankOrNull(bulkImportSessionData.getTimeToJoin())) {
			bulkImportResultData.setTimeToJoin(bulkImportSessionData.getTimeToJoin());
		}
		if(!Utils.isBlankOrNull(bulkImportSessionData.getNote())) {
			bulkImportResultData.setNote(bulkImportSessionData.getNote());
		}		
	}
	
	public void importSingleApplicant(BulkImportResultData bulkImportResultData, String userId, ApplicantManager applicantManager, String sessionType) {
		try {
			ApplicantData applicantData = getApplicantDataFromParsedData(bulkImportResultData, userId);
			String applicantId = applicantManager.addApplicant(applicantData, applicantData.getSkillIds());
			if (!Utils.isBlankOrNull(bulkImportResultData.getEmailId())) {
				InboxManager inboxManager = new InboxManager();
				if(DesktopConstants.SESSION_TYPE_EMAIL_IMPORT.equals(sessionType) 
						|| DesktopConstants.SESSION_TYPE_INBOX_BULK_IMPORT.equals(sessionType) 
						|| DesktopConstants.SESSION_TYPE_BROWSER_IMPORT.equals(sessionType)) {
					MessageData messageData = getEmailData(bulkImportResultData.getEmailId());
					messageData.setApplicantId(applicantId);
					if(DesktopConstants.SESSION_TYPE_BROWSER_IMPORT.equals(sessionType)) {
						messageData.setSubject(applicantData.getApplicantName());
					}
					inboxManager.saveCommunicationMessage(messageData, ApplicantConstants.APPLICANT_EMAIL_FOLDER_INBOX, userId, ApplicantConstants.EMAIL_IMPORTED);
					if(DesktopConstants.SESSION_TYPE_INBOX_BULK_IMPORT.equals(sessionType)) {
						try {
							inboxManager.deleteInboxEmail(bulkImportResultData.getEntryId(), false, null);
						} catch (Exception e) {
							TPLogger.getLogger().error("Email is already deleted", e);
						}						
					}
				}
			}			
			
			// Add Applicant Note
			if (!Utils.isBlankOrNull(bulkImportResultData.getNote()) && !Utils.isBlankOrNull(applicantId)) {
				Calendar cal = new GregorianCalendar();
				Date dtLogDate = cal.getTime();
				CommunicationData cData = new CommunicationData();
				cData.setApplicantId(applicantId);
				cData.setUserId(userId);
				cData.setCommunicationType(SelectionProcessConstants.INTERACTION_NOTE);
				cData.setCommunicationDate(new java.sql.Timestamp(dtLogDate.getTime()));
				// cData.setCommunicationPhoneNo("");
				cData.setCommunicationText(bulkImportResultData.getNote());
				SelectionProcessManager selectionProcessManager = new SelectionProcessManager();
				selectionProcessManager.addPhoneLog(cData);
			}
			// Add applicant to index to event queue
			TPIndexEventQueue.push(new TPIndexEvent(TPIndexEvent.TYPE_ADD_APPLICANT, applicantId, TPIndexEvent.PRIORITY_NORMAL));
			// update import status for applicant
			updateImportStatusForResult(bulkImportResultData.getResultId(),applicantId, DesktopConstants.IS_IMPORTED);
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
	}

	private ArrayList<BulkImportResultData> getBulkImportResultsToImport(String sessionId) {
		ArrayList<BulkImportResultData> results = null;
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dBulkImportManager_GetBulkImportResultsToImport");
			dq.setString(1, sessionId);
			dq.setString(2, DesktopConstants.IS_NOT_IMPORTED);
			results = dq.getResult();
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return results;
	}

	public void updateImportStatusForResult(String resultId,String applicantId, String status) {
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dBulkImportManager_UpdateResultImportStatus");
			dq.setString(1, status);
			dq.setId(2, applicantId);
			dq.setId(3, resultId);
			dq.execute();
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
	}

	private ApplicantData getApplicantDataFromParsedData(BulkImportResultData bulkImportResultData, String userId) {
		ApplicantData aData = new ApplicantData();
		try {
			if(!Utils.isBlankOrNull(bulkImportResultData.getSourceId())){
				aData.setApplicantSourceId(Integer.parseInt(bulkImportResultData.getSourceId()));
			}
			aData.setApplicantName(bulkImportResultData.getParsedName());
			aData.setApplicantCity(bulkImportResultData.getApplicantCity());
			aData.setApplicantEmail1(bulkImportResultData.getParsedEmail1());
			aData.setApplicantEmail2(bulkImportResultData.getParsedEmail2());
			aData.setApplicantWorkPhone(bulkImportResultData.getParsedWorkPhone());
			aData.setApplicantHomePhone(bulkImportResultData.getParsedHomePhone());
			aData.setApplicantCellPhone(bulkImportResultData.getParsedCellPhone());
			aData.setApplicantOriginalResumePath(bulkImportResultData.getParsedOriginalResumePath());
			aData.setApplicantOriginalDocPath(bulkImportResultData.getParsedOriginalDocPath());
			aData.setApplicantTextResume(bulkImportResultData.getParsedTextResume());
			if (!Utils.isBlankOrNull(bulkImportResultData.getApplicantWorkingSince())) {
				java.sql.Date workingSince = Utils.convertToSQLDate(bulkImportResultData.getApplicantWorkingSince(), Utils.redYYYYMMDDFormat);
				aData.setApplicantWorkingSince(workingSince);
			}	
			aData.setCurrentCTC(bulkImportResultData.getCurrentCtc());
			aData.setExpectedCTC(bulkImportResultData.getExpectedCtc());
			aData.setApplicantCurrentEmployer(bulkImportResultData.getCurrentEmployer());
			aData.setNoticePeriod(bulkImportResultData.getTimeToJoin());
			aData.setUserId(userId);
			aData.setSkillIds(bulkImportResultData.getParsedSkillIds());
			aData.setCustomFields(bulkImportResultData.getCustomFields());
			aData.setEducationalDetails(bulkImportResultData.getEducationalDetails());
			aData.setCustomFields(bulkImportResultData.getCustomFields());
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return aData;
	}

	public MessageData getEmailData(String emailId) {
		MessageData email = null;
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dBulkImportManager_GetEmailData");
			dq.setId(1, emailId);

			// Save the attachments
			email = (MessageData) dq.getSingleObjectResult();
			if (email != null) {
				ArrayList<AttachmentData> attachments = getEmailAttachments(email.getMessageId());
				email.setAttachments(attachments);
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return email;
	}

	private ArrayList<AttachmentData> getEmailAttachments(String emailId) {
		ArrayList<AttachmentData> attachments = null;
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dBulkImportManager_GetBulkImportAttachments");
			dq.setId(1, emailId);
			attachments = dq.getResult();
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return attachments;
	}

	public String checkForSessionComplete(String sessionId) {
		DBPreparedQuery dq = null;
		String isSessionComplete = "";
		try {
			dq = new DBPreparedQuery("dBulkImportManager_CheckForSessionComplete");
			dq.setString(1, sessionId);
			isSessionComplete = dq.getIdResult();
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return isSessionComplete;
	}

	public int getCountForImportStatus(String sessionId, String status) {
		DBPreparedQuery dq = null;
		int cnt = 0;
		try {
			dq = new DBPreparedQuery("dBulkImportManager_GetImportStatusResultCount");
			dq.setString(1, sessionId);
			dq.setString(2, status);
			cnt = dq.getIntResult();
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return cnt;
	}

	public ArrayList<SimpleDataObject> getEmailEntryIdsForImportedResults(String sessionId, String importStatus, String sessionType) {
		ArrayList<SimpleDataObject> result = null;
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dBulkImportManager_GetEmailEntryIdsNamesForImported");
			dq.setString(1, sessionId);
			dq.setString(2, importStatus);
			result = dq.getResult();
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return result;
	}

	public void updateApplicantWithNewResume(String applicantId, String originalResumePath, String originalDocPath, String textResume) {
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dBulkImportManager_updateApplicantWithNewResume");
			dq.setString(1, originalResumePath);
			dq.setString(2, originalDocPath);
			dq.setString(3, textResume);
			dq.setString(4, applicantId);
			dq.execute();
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
	}
	
	public void updateParsedSkills(String resultId,String skillIds) throws Exception{
		DBPreparedQuery dq = null;
		DBTransaction tran = null;
		try {
			tran = new DBTransaction();
			dq = new DBPreparedQuery("dBulkImportManager_DeleteParsedSkills",tran);
			dq.setString(1, resultId);
			dq.execute();
			
			//update skills
			if (!Utils.isBlankOrNull(skillIds)) {
				String[] skillsIds = skillIds.split(",");
				for (int i = 0; i < skillsIds.length; i++) {
					dq = new DBPreparedQuery("dBulkImportManager_InsertBulkImportResultSkills", tran);
					dq.setString(1, resultId);
					dq.setString(2, skillsIds[i]);
					dq.execute();
				}
			}
			tran.commit();
			
		} catch (Exception e) {
			TPLogger.getLogger().error("Error", e);
			try {
				tran.rollback();
			} catch (Exception ex) {
				TPLogger.getLogger().error(GlobalConstants.ERROR, ex);
			}
			throw e;
		} finally {
			if (dq != null) {
				dq.releaseTransaction(tran);
			}
		}
	}

	public String getNumberOfParsedResume(String sessionId, String sessionType) {
		DBPreparedQuery dq = null;
		String numberOfParsedResume = "";
		try {
			if(sessionType.equals(DesktopConstants.SESSION_TYPE_EMAIL_IMPORT)
					|| sessionType.equals(DesktopConstants.SESSION_TYPE_BROWSER_IMPORT)){
				dq = new DBPreparedQuery("dBulkImportManager_GetNumberOfParsedResume");
				dq.setString(1, sessionId);
				dq.setString(2, sessionId);
				dq.setString(3, InboxConstants.ATTACHMENT_TYPE_RELATED);
				dq.setString(4, sessionId);
				dq.setString(5, sessionId);
				numberOfParsedResume = dq.getIdResult();
		}else{
				dq = new DBPreparedQuery("dBulkImportManager_GetNumberOfParsedResumeForDocuments");
				dq.setString(1, DesktopConstants.IMPORT_STATUS_FILES_TRANSFERRED);
				dq.setString(2, DesktopConstants.IMPORT_STATUS_PARSING_DONE);
				dq.setString(3, sessionId);				
				dq.setString(4, DesktopConstants.IMPORT_STATUS_FILES_TRANSFERRED);
				dq.setString(5, DesktopConstants.IMPORT_STATUS_PARSING_DONE);
				dq.setString(6, sessionId);
				numberOfParsedResume = dq.getIdResult();
		}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return numberOfParsedResume;
	}

	public BulkImportResultData getSingleImportData(String resultId) {
		DBPreparedQuery dq = null;
		BulkImportResultData data = null;
		try {
			dq = new DBPreparedQuery("dBulkImportManager_GetSingleImportData");
			dq.setString(1, resultId);
			data = (BulkImportResultData)dq.getSingleObjectResult();
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return data;
	}

	public String addEmailToInboxEmails(String emailId) throws Exception {
		DBTransaction tran = null;
		DBPreparedQuery dq = null;
		String inboxEmailId = "";
		try {
			tran = new DBTransaction();

			dq = new DBPreparedQuery("dBulkImportManager_InsertEmailToInboxEmail", tran);
			dq.setInt(1, InboxConstants.FORMAT_NOAUTO_IMPORT);
			dq.setString(2, emailId);
			dq.execute();
			
			dq = new DBPreparedQuery("dFetchLastInsertID", tran);
			inboxEmailId = dq.getIdResult();
			
			// Save the attachments
			dq = new DBPreparedQuery("dBulkImportManager_InsertAttachmentsToInboxAttachments", tran);
			dq.setId(1, inboxEmailId);
			dq.setString(2, emailId);
			dq.execute();

			tran.commit();
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			try {
				tran.rollback();
			} catch (Exception ex) {
				TPLogger.getLogger().error(GlobalConstants.ERROR, ex);
			}
			throw e;
		} finally {
			if (dq != null) {
				dq.releaseTransaction(tran);
			}
		}
		return inboxEmailId;
	}

	public String getAttachmentId(String inboxEmailId, String parsedOriginalDocPath) {
		DBPreparedQuery dq = null;
		String attachmentId = "";
		try {
			dq = new DBPreparedQuery("dBulkImportManager_GetAttachmentId");
			dq.setString(1, inboxEmailId);
			dq.setString(2, parsedOriginalDocPath + "%");
			attachmentId = dq.getIdResult();
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return attachmentId;
	}

	public String getApplicantIdsForImportedResume(String sessionId) {
		DBPreparedQuery dq = null;
		String applicantIds = "";
		try {
			dq = new DBPreparedQuery("dBulkImportManager_GetApplicantIdsForImportedResume");
			dq.setString(1, DesktopConstants.IS_IMPORTED);
			dq.setString(2, sessionId);
			applicantIds = dq.getIdResult();
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return applicantIds;
	}
	
	public List<SimpleDataObject> getCommentUrlsForImportedResume(String sessionId, String labelImportedToApp, String userName) {
		DBPreparedQuery dq = null;
		List<SimpleDataObject> commentUrls = null;
		try {
			dq = new DBPreparedQuery("dBulkImportManager_GetCommentUrlsForImportedResume");
			dq.setString(1, labelImportedToApp);
			dq.setString(2, userName);
			dq.setString(3, DesktopConstants.IS_IMPORTED);
			dq.setString(4, sessionId);
			commentUrls = dq.getResult();
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return commentUrls;
	}
	
	public void addParsedEducationDetails(ArrayList eduDetails, String resultId, DBTransaction tran) throws Exception {
		DBPreparedQuery dq = null;
		try {
			if (eduDetails != null) {
				ApplicantManager applicantManager = new ApplicantManager();
				for (int i = 0; i < eduDetails.size(); i++) {
					EducationalData eData = (EducationalData) eduDetails.get(i);
					if (!Utils.isBlankOrNull(eData.getInstitute()) && eData.getInstitute().length()<150) {
						String instituteId = applicantManager.autoUpdateInstitute(eData.getInstitute().trim(), tran);
						eData.setInstituteId(instituteId);
					}
					dq = new DBPreparedQuery("dBulkImportManager_AddParsedEducationInfo", tran);
					dq.setString(1, resultId);
					dq.setId(2, eData.getDegreeId() <= 0 ? null : "" + eData.getDegreeId());
					dq.setString(3, eData.getMajorId() <= 0 ? null : "" + eData.getMajorId());
					dq.setString(4, Utils.isBlankOrNull(eData.getInstituteId()) ? null : eData.getInstituteId());
					dq.setDate(5, eData.getYearOfPassing());
					dq.setString(6, eData.getGrade());
					dq.execute();
				}
			}
		} catch (Exception e) {
			throw e;
		} finally {
			if (dq != null) {
				if (tran == null) {
					dq.releaseConnection();
				} else {
					dq.closeOpenCursors();
				}
			}
		}
	}
}
