/**
 * 
 */
package com.talentPool.documents.manager;

import java.io.File;
import java.io.StringWriter;
import java.sql.SQLException;
import java.util.ArrayList;

import org.xml.sax.helpers.AttributesImpl;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.dataobject.FormFileData;
import com.talentPool.common.db.DBPreparedQuery;
import com.talentPool.common.db.DBTransaction;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.utils.DateUtils;
import com.talentPool.common.utils.Exception.FileUploadException;
import com.talentPool.common.utils.Exception.InvalidMimeTypeException;
import com.talentPool.common.xmlutils.XMLWriter;
import com.talentPool.documents.DocumentConstants;
import com.talentPool.documents.dataobject.DocumentData;
import com.talentPool.documents.utils.DocumentUploader;
import com.talentPool.inbox.dataobject.AttachmentData;
import com.talentPool.inbox.scheduler.DeleteFilesThread;
import com.talentPool.selectionProcess.SelectionProcessConstants;
import com.talentPool.user.manager.PermissionSet;

/**
 * @author shivprasad
 * 
 */
public class DocumentManager {
	public AttachmentData uploadDocument(FormFileData formFileData, String applicantId, 
			String userId) throws FileUploadException, InvalidMimeTypeException {
		DBTransaction tran = null;
		AttachmentData aData = null;
		DocumentData documentData = null;
		try {
			// First upload the attachment
			// get absolute attachment path
			DocumentUploader documentUploader = new DocumentUploader();
			documentData = documentUploader.saveFormFile(formFileData);

			tran = new DBTransaction();
			String attachmentId = insertApplicantDocument(applicantId, formFileData.getFileName(), documentData.getRelativeFilePath(), userId, tran);

			tran.commit();

			aData = new AttachmentData();
			aData.setAttachmentId(attachmentId);
			aData.setOriginalFileName(formFileData.getFileName());
			aData.setAttachmentSize(formFileData.getFileSize());

		} catch (InvalidMimeTypeException e) {
			throw e;
		} catch (SQLException sqle) {
			TPLogger.getLogger().error("Error while updating DB", sqle);
			try {
				if (tran != null) {
					tran.rollback();
				}
			} catch (SQLException ex) {
			}
			throw new FileUploadException("Unable to upload file");
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while uploading file to destination ", e);
			try {
				if (documentData != null) {
					File f = new File(documentData.getAbsoluteFilePath());
					if (f.exists()) {
						f.delete();
					}
				}
			} catch (Exception fe) {
				TPLogger.getLogger().debug("Unable to delete file", fe);
			}
			throw new FileUploadException("Unable to upload file");
		} finally {
			if(tran!=null)
				tran.release();
		}
		return aData;
	}
	
	public AttachmentData uploadPositionDocument(FormFileData formFileData, String positionId, 
			String userId) throws FileUploadException, InvalidMimeTypeException {
		DBTransaction tran = null;
		AttachmentData aData = null;
		DocumentData documentData = null;
		try {
			// First upload the attachment
			// get absolute attachment path
			DocumentUploader documentUploader = new DocumentUploader();
			documentData = documentUploader.saveFormFile(formFileData);

			tran = new DBTransaction();
			String attachmentId = insertPositionDocument(positionId, formFileData.getFileName(), documentData.getRelativeFilePath(), userId, tran);

			tran.commit();

			aData = new AttachmentData();
			aData.setAttachmentId(attachmentId);
			aData.setOriginalFileName(formFileData.getFileName());
			aData.setAttachmentSize(formFileData.getFileSize());

		} catch (InvalidMimeTypeException e) {
			throw e;
		}catch (SQLException sqle) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, sqle);
			try {
				if (tran != null) {
					tran.rollback();
				}
			} catch (SQLException ex) {
			}
			throw new FileUploadException("Unable to upload file");
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			try {
				if (documentData != null) {
					File f = new File(documentData.getAbsoluteFilePath());
					if (f.exists()) {
						f.delete();
					}
				}
			} catch (Exception fe) {
				TPLogger.getLogger().debug(GlobalConstants.ERROR, fe);
			}
			throw new FileUploadException("Unable to upload file");
		} finally {
			if(tran!=null)
				tran.release();
		}
		return aData;
	}
	
	public String insertApplicantDocument(String applicantId, String fileName, String relativeFilePath, String userId, DBTransaction tran) throws SQLException {
		return insertApplicantDocument(applicantId, fileName, relativeFilePath, false, userId, tran);
	}

	public String insertApplicantDocument(String applicantId, String fileName, String relativeFilePath, boolean confidential, String userId, DBTransaction tran) throws SQLException {
		DBPreparedQuery dq = null;
		String documentId = null;
		try {
			if(tran == null) {
				dq = new DBPreparedQuery("dDocumentManager_AddApplicantDocument");
			} else {
				dq = new DBPreparedQuery("dDocumentManager_AddApplicantDocument", tran);
			}			
			dq.setString(1, applicantId);
			dq.setString(2, fileName);
			dq.setString(3, relativeFilePath);
			dq.setString(4, userId);
			if(confidential){
				dq.setId(5, SelectionProcessConstants.INTERACTION_HIDE);
			}else{
				dq.setId(5, SelectionProcessConstants.INTERACTION_UNHIDE);
			}
			dq.execute();
			
			if(tran == null) {
				dq = new DBPreparedQuery("dFetchLastInsertID");
			} else {
				dq = new DBPreparedQuery("dFetchLastInsertID", tran);
			}			
			documentId = dq.getIdResult();
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {		
			if(dq != null) {
				if(tran != null) {
					dq.closeOpenCursors();
				} else {
					dq.releaseConnection();
				}
			}			
		}	
		return documentId;
	}
	
	public void updateApplicantDocument(String documentId, String fileName, String relativeFilePath, String userId, DBTransaction tran) throws SQLException {
		DBPreparedQuery dq = null;
		try {
			if(tran == null) {
				dq = new DBPreparedQuery("dDocumentManager_updateApplicantDocument");
			} else {
				dq = new DBPreparedQuery("dDocumentManager_updateApplicantDocument", tran);
			}			
			dq.setString(1, fileName);
			dq.setString(2, relativeFilePath);
			dq.setString(3, userId);
			dq.setString(4, documentId);
			dq.execute();
		} finally {		
			if(dq != null) {
				if(tran != null) {
					dq.closeOpenCursors();
				} else {
					dq.releaseConnection();
				}
			}			
		}	
	}

	/**
	 * @param applicantIds
	 * @return list of <DocumentData> objects
	 */
	public ArrayList<DocumentData> getApplicantDocuments(String applicantIds) {
		DBPreparedQuery dq = null;
		ArrayList<DocumentData> results = null;
		String[] dynParams = null;
		try {
			dynParams = new String[1];
			dynParams[0] = applicantIds;
			
			dq = new DBPreparedQuery("dDocumentManager_GetApplicantDocuments", dynParams);
			results = dq.getResult();
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while generating applicant documents", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return results;
	}

	/**
	 * @param docs
	 * @param userId
	 * @param roleId
	 * @return xml for documents
	 */
	public String getXMLForApplicantDocuments(ArrayList<DocumentData> docs, String userId, String role, PermissionSet permissionSet) {
		StringWriter sWr = new StringWriter();
		XMLWriter wr = new XMLWriter(sWr);
		try {
			if (docs != null) {
				wr.startDocument();
				wr.startElement("rows");
				for (int indx = 0; indx < docs.size(); indx++) {
					DocumentData data = (DocumentData) docs.get(indx);
					if (data.getDocumentIsHidden().equals(SelectionProcessConstants.INTERACTION_UNHIDE) || permissionSet.isSHOW_CONFIDENTIAL_DATA() || data.getUserId().equals(userId)) {
						String documentId = data.getDocumentId();

						AttributesImpl atr = new AttributesImpl();
						atr.addAttribute("", "id", "", "", data.getDocumentId());
						wr.startElement("", "row", "", atr);

						String deleteStr = "&nbsp;";
						if (userId.equals(data.getUserId()) || permissionSet.isSHOW_CONFIDENTIAL_DATA()) {
							deleteStr = "<a href=\"#\" onclick=\"onClickDeleteDocument(" + documentId + ");\" title=\"Delete\"><img src=\"images/ico_delete.gif\" border=0></a>";
						}

						wr.startElement("cell");
						wr.characters(deleteStr);
						wr.endElement("cell");
						
						wr.startElement("cell");
						if (userId.equals(data.getUserId()) || permissionSet.isSHOW_CONFIDENTIAL_DATA()) {
							if(data.getDocumentIsHidden().equals(SelectionProcessConstants.INTERACTION_HIDE)){
								wr.characters("<a href=\"#\" onclick=\"onClickHideDocument(" + documentId + ");\" title=\"Hide/Show\"><img src=\"images/ico_show_interaction.gif\" border=0></a>");
							}else{
								wr.characters("<a href=\"#\" onclick=\"onClickHideDocument(" + documentId + ");\" title=\"Hide/Show\"><img src=\"images/ico_hide_interaction.gif\" border=0></a>");
							}
						}
						wr.endElement("cell");

						wr.startElement("cell");
						wr.characters("<img src=\"images/" + data.getImageName() + "\" border=0 width=15 height=13>");
						wr.endElement("cell");

						String fileName = data.getOriginalFileName();
						atr = new AttributesImpl();
						atr.addAttribute("", "name", "", "", "fileName");
						wr.startElement("", "userdata", "", atr);
						wr.characters(wr.doubleEscape(fileName));
						wr.endElement("userdata");
						atr = new AttributesImpl();
						atr.addAttribute("", "name", "", "", "filePath");
						wr.startElement("", "userdata", "", atr);
						wr.characters(wr.doubleEscape(data.getRelativeFilePath()));
						wr.endElement("userdata");

						if (fileName.length() > 22) {
							fileName = fileName.substring(0, 22) + "...";
						}
						wr.startElement("cell");
						wr.characters(wr.doubleEscape(fileName) + "^javascript:onClickDocument(" + documentId + ");^_self");
						wr.endElement("cell");

						String name = data.getOwnerName();
						atr = new AttributesImpl();
						atr.addAttribute("", "name", "", "", "name");
						wr.startElement("", "userdata", "", atr);
						wr.characters(wr.doubleEscape(name));
						wr.endElement("userdata");

						if (name.length() > 15) {
							name = name.substring(0, 12) + "...";
						}
						wr.startElement("cell");
						wr.characters(wr.doubleEscape(name));
						wr.endElement("cell");

						String dateCreated = "";
						if (data.getDateCreated() != null) {
							try {
								dateCreated = DateUtils.getSystemDateTimeFormat(data.getDateCreated());
							} catch (Exception e) {
								e.printStackTrace();
								dateCreated = "UNKNOWN";
							}
						}
						// String interactionDate =
						// Utils.getDateConvertedToString((java.sql.Date)data.getAttribute("interactionDate"),
						// "EEE MM/dd/yyyy hh:mm a");
						wr.startElement("cell");
						wr.characters(dateCreated);
						wr.endElement("cell");
						
						wr.startElement("cell");
						wr.characters(data.getDocumentIsHidden());
						wr.endElement("cell");
						
						wr.endElement("row");
					}
				}
				wr.endElement("rows");
				wr.endDocument();
			}

		} catch (Exception e) {
			TPLogger.getLogger().error("Error while generating xml for interactions", e);
		}
		return sWr.toString();
	}

	/**
	 * return applicant document data
	 * 
	 * @param documentId
	 * @return
	 */
	public DocumentData getApplicantDocumentData(String documentId) {
		DBPreparedQuery dq = null;
		DocumentData documentData = null;
		try {
			dq = new DBPreparedQuery("dDocumentManager_GetApplicantDocumentData");
			dq.setId(1, documentId);
			documentData = (DocumentData) dq.getSingleObjectResult();
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while getting applicant doc data", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return documentData;

	}

	/**
	 * deletes document record and file
	 * 
	 * @param documentId
	 * @throws Exception
	 */
	public void deleteApplicantDocument(String documentId) throws Exception {
		DBPreparedQuery dq = null;

		try {
			dq = new DBPreparedQuery("dDocumentManager_GetApplicantDocumentData");
			dq.setId(1, documentId);
			DocumentData documentData = (DocumentData) dq.getSingleObjectResult();
			if (documentData != null) {
				dq = new DBPreparedQuery("dDocumentManager_DeleteApplicantDocument");
				dq.setId(1, documentId);
				dq.execute();
				ArrayList<String> files = new ArrayList<String>();
				files.add(documentData.getRelativeFilePath());
				DeleteFilesThread t = new DeleteFilesThread(files, false);
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while deleting applicant documents", e);
			throw e;
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}

	}
	
	public void hideShowApplicantDocument(String documentId) throws Exception {
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dDocumentManager_hideOrShowApplicantDocument");
			dq.setString(1, SelectionProcessConstants.INTERACTION_UNHIDE);
			dq.setString(2, SelectionProcessConstants.INTERACTION_HIDE);
			dq.setString(3, SelectionProcessConstants.INTERACTION_UNHIDE);
			dq.setId(4, documentId);
			dq.execute();
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while deleting applicant documents", e);
			throw e;
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}

	}
	
	public String insertPositionDocument(String positionId, String fileName, String relativeFilePath, String userId, DBTransaction tran) throws SQLException {
		DBPreparedQuery dq = null;
		String documentId = null;
		try {
			if(tran == null) {
				dq = new DBPreparedQuery("dDocumentManager_AddPositionDocument");
			} else {
				dq = new DBPreparedQuery("dDocumentManager_AddPositionDocument", tran);
			}			
			dq.setString(1, positionId);
			dq.setString(2, fileName);
			dq.setString(3, relativeFilePath);
			dq.setString(4, userId);
			dq.execute();
			
			if(tran == null) {
				dq = new DBPreparedQuery("dFetchLastInsertID");
			} else {
				dq = new DBPreparedQuery("dFetchLastInsertID", tran);
			}			
			documentId = dq.getIdResult();
		} finally {		
			if(dq != null) {
				if(tran != null) {
					dq.closeOpenCursors();
				} else {
					dq.releaseConnection();
				}
			}			
		}	
		return documentId;
	}
	/**
	 * @param docs
	 * @param userId
	 * @param roleId
	 * @return xml for documents
	 */
	public String getXMLForPositionDocuments(ArrayList<DocumentData> docs, String userId, String role, PermissionSet permissionSet) {
		StringWriter sWr = new StringWriter();
		XMLWriter wr = new XMLWriter(sWr);
		try {
			if (docs != null) {
				wr.startDocument();
				wr.startElement("rows");
				for (int indx = 0; indx < docs.size(); indx++) {
					DocumentData data = (DocumentData) docs.get(indx);
					if (data.getDocumentIsHidden().equals(DocumentConstants.UNHIDE) || permissionSet.isSHOW_CONFIDENTIAL_DATA() || data.getUserId().equals(userId)) {
						
						String documentId = data.getDocumentId();

						AttributesImpl atr = new AttributesImpl();
						atr.addAttribute("", "id", "", "", data.getDocumentId());
						wr.startElement("", "row", "", atr);

						String deleteStr = "&nbsp;";
						if (userId.equals(data.getUserId()) || permissionSet.isSHOW_CONFIDENTIAL_DATA()) {
							deleteStr = "<a href=\"#\" onclick=\"onClickDeleteDocument(" + documentId + ");\" title=\"Delete\"><img src=\"images/ico_delete.gif\" border=0></a>";
						}

						wr.startElement("cell");
						wr.characters(deleteStr);
						wr.endElement("cell");
						
						wr.startElement("cell");
						if (userId.equals(data.getUserId()) || permissionSet.isSHOW_CONFIDENTIAL_DATA()) {
							if(data.getDocumentIsHidden().equals(SelectionProcessConstants.INTERACTION_HIDE)){
								wr.characters("<a href=\"#\" onclick=\"onClickHideDocument(" + documentId + ");\" title=\"Hide/Show\"><img src=\"images/ico_show_interaction.gif\" border=0></a>");
							}else{
								wr.characters("<a href=\"#\" onclick=\"onClickHideDocument(" + documentId + ");\" title=\"Hide/Show\"><img src=\"images/ico_hide_interaction.gif\" border=0></a>");
							}
						}
						wr.endElement("cell");

						wr.startElement("cell");
						wr.characters("<img src=\"images/" + data.getImageName() + "\" border=0 width=15 height=13>");
						wr.endElement("cell");

						String fileName = data.getOriginalFileName();
						atr = new AttributesImpl();
						atr.addAttribute("", "name", "", "", "fileName");
						wr.startElement("", "userdata", "", atr);
						wr.characters(wr.doubleEscape(fileName));
						wr.endElement("userdata");
						atr = new AttributesImpl();
						atr.addAttribute("", "name", "", "", "filePath");
						wr.startElement("", "userdata", "", atr);
						wr.characters(wr.doubleEscape(data.getRelativeFilePath()));
						wr.endElement("userdata");

						if (fileName.length() > 35) {
							fileName = fileName.substring(0, 33) + "...";
						}
						wr.startElement("cell");
						wr.characters(wr.doubleEscape(fileName) + "^javascript:onClickDocument(" + documentId + ");^_self");
						wr.endElement("cell");

						String name = data.getOwnerName();
						atr = new AttributesImpl();
						atr.addAttribute("", "name", "", "", "name");
						wr.startElement("", "userdata", "", atr);
						wr.characters(wr.doubleEscape(name));
						wr.endElement("userdata");

						if (name.length() > 30) {
							name = name.substring(0, 26) + "...";
						}
						wr.startElement("cell");
						wr.characters(wr.doubleEscape(name));
						wr.endElement("cell");

						String dateCreated = DateUtils.getSystemDateTimeFormat(data.getDateCreated());
						
						wr.startElement("cell");
						wr.characters(dateCreated);
						wr.endElement("cell");
						
						wr.startElement("cell");
						wr.characters(data.getDocumentIsHidden());
						wr.endElement("cell");
						
						wr.endElement("row");
					}
				}
				wr.endElement("rows");
				wr.endDocument();
			}

		} catch (Exception e) {
			TPLogger.getLogger().error("Error while generating xml for interactions", e);
		}
		return sWr.toString();
	}
	
	public ArrayList<DocumentData> getPositionDocuments(String positionId) {
		DBPreparedQuery dq = null;
		ArrayList<DocumentData> documentData = null;
		try {
			dq = new DBPreparedQuery("dDocumentManager_GetPositionDocuments");
			dq.setId(1, positionId);
			documentData = dq.getResult();
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return documentData;
	}
	
	public void deletePositionDocument(String documentId,String positionId) throws Exception {
		DBPreparedQuery dq = null;
		try {
			
			//dq = new DBPreparedQuery("dDocumentManager_GetPositionDocumentData");
			//dq.setId(1, documentId);
			//DocumentData documentData = (DocumentData) dq.getSingleObjectResult();
			//if (documentData != null) {
			
			dq = new DBPreparedQuery("dDocumentManager_DeletePositionDocument");
			dq.setId(1, documentId);
			dq.setId(2, positionId);
			dq.execute();
			
			/***** Below Code is Commented as the deleted Documents are deleted from server during cleanup**********/ 
				/*ArrayList<String> files = new ArrayList<String>();
				files.add(documentData.getRelativeFilePath());
				DeleteFilesThread t = new DeleteFilesThread(files, false);*/
			//}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			throw e;
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
	}
	
	public void hideShowPositionDocument(String documentId) throws Exception {
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dDocumentManager_hideOrShowPositionDocument");
			dq.setString(1, DocumentConstants.UNHIDE);
			dq.setString(2, DocumentConstants.HIDE);
			dq.setString(3, DocumentConstants.UNHIDE);
			dq.setId(4, documentId);
			dq.execute();
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while deleting applicant documents", e);
			throw e;
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
	}
	
	public void copyPositionDocuments(String srcPositionId, String destPositionId) throws Exception {
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dDocumentManager_CopyPositionDocuments");
			dq.setString(1, destPositionId);
			dq.setString(2, srcPositionId);
			dq.execute();
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while copying position documents", e);
			throw e;
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
	}
}
