/**
 * 
 */
package com.talentPool.miscutils.manager;

import java.io.File;
import java.util.ArrayList;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.db.DBPreparedQuery;
import com.talentPool.common.db.SimpleDataObject;
import com.talentPool.common.utils.FileHandler;
import com.talentPool.common.utils.Utils;
import com.talentPool.common.utils.FileUtils.FileHandlerUtils;
import com.talentPool.documents.DocumentConstants;
import com.talentPool.parser.converter.HTMLToPlainTextConverter;

/**
 * @author shivprasad
 * 
 */
public class MiscUtilsManager {
	private static boolean isDocumentRelatedToApplicantResume(String fileName) {
		DBPreparedQuery dq = null;
		boolean validDocument = false;
		try {
			dq = new DBPreparedQuery("dMiscUtils_GetApplicantForFile");
			dq.setString(1, fileName + "%");
			dq.setString(2, fileName + "%");
			int cnt = dq.getIntResult();
			if (cnt > 0) {
				validDocument = true;
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return validDocument;
	}

	private static boolean isDocumentRelatedToInboxEmail(String fileName) {
		DBPreparedQuery dq = null;
		boolean validDocument = false;
		try {
			dq = new DBPreparedQuery("dMiscUtils_GetInboxAttachmentsForFile");
			dq.setString(1, fileName + "%");
			int cnt = dq.getIntResult();
			if (cnt > 0) {
				validDocument = true;
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return validDocument;
	}

	private static boolean isDocumentRelatedToApplicantEmail(String fileName) {
		DBPreparedQuery dq = null;
		boolean validDocument = false;
		try {
			dq = new DBPreparedQuery("dMiscUtils_GetApplicantEmailAttachmentsForFile");
			dq.setString(1, fileName + "%");
			int cnt = dq.getIntResult();
			if (cnt > 0) {
				validDocument = true;
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return validDocument;
	}

	private static boolean isDocumentRelatedToApplicantDocuments(String fileName) {
		DBPreparedQuery dq = null;
		boolean validDocument = false;
		try {
			dq = new DBPreparedQuery("dMiscUtils_GetApplicantDocumentsForFile");
			dq.setString(1, fileName + "%");
			int cnt = dq.getIntResult();
			if (cnt > 0) {
				validDocument = true;
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return validDocument;
	}

	private static boolean isValidDocument(String fileName) {
		boolean validDocument = false;
		if (isDocumentRelatedToApplicantResume(fileName)) {
			validDocument = true;
		} else if (isDocumentRelatedToApplicantEmail(fileName)) {
			validDocument = true;
		} else if (isDocumentRelatedToInboxEmail(fileName)) {
			validDocument = true;
		} else if (isDocumentRelatedToApplicantDocuments(fileName)) {
			validDocument = true;
		}
		return validDocument;
	}

	private static void deleteFile(File file) {
		String absolutePath = file.getAbsolutePath();
		file.delete();
		FileHandler fileHandler = new FileHandler();
		String ext = FileHandlerUtils.getFileExtention(absolutePath, "");
		if (ext.equalsIgnoreCase(".doc") || ext.equalsIgnoreCase(".rtf")) {
			String htmlFile = absolutePath.substring(0, absolutePath.lastIndexOf(".")) + ".html";
			File htmlf = new File(htmlFile);
			if (htmlf.exists()) {
				if (!htmlf.delete()) {
					TPLogger.getLogger().error("unable to delete file = " + absolutePath);
				}
				String htmlFolderPath = absolutePath.substring(0, absolutePath.lastIndexOf(".")) + "_files";
				fileHandler.deleteDirectory(htmlFolderPath);
			}
		}

	}

	public static void cleanDocumentsFolder() {
		File documentFolder = new File(DocumentConstants.documentsPath);
		String[] yearMonthFolders = documentFolder.list();
		for (int i = 0; i < yearMonthFolders.length; i++) {
			try {
				if (!yearMonthFolders[i].startsWith(".")) {
					File yearMonthFolder = new File(Utils.concatFilePath(DocumentConstants.documentsPath, yearMonthFolders[i]));
					String[] dayFolders = yearMonthFolder.list();
					for (int d = 0; dayFolders != null && d < dayFolders.length; d++) {
						try {
							File dayFolder = new File(Utils.concatFilePath(yearMonthFolder.getAbsolutePath(), dayFolders[d]));
							String[] files = dayFolder.list();
							for (int f = 0; files != null && f < files.length; f++) {
								try {
									File file = new File(Utils.concatFilePath(dayFolder.getAbsolutePath(), files[f]));
									if (file.exists() && file.isFile()) {
										String fileName = files[f];
										if (files[f].lastIndexOf(".") > 0) {
											fileName = files[f].substring(0, files[f].lastIndexOf("."));
										}
										fileName = yearMonthFolders[i] + "/" + dayFolders[d] + "/" + fileName;
										boolean validDocument = isValidDocument(fileName);
										if (!validDocument) {
											// delete file and html converted file
											TPLogger.getLogger().debug("**INVALID document " + fileName);
											deleteFile(file);
										} else {
											TPLogger.getLogger().debug("##VALID document " + fileName);
										}
									}
								} catch (Exception e) {
									TPLogger.getLogger().error("Error", e);
									e.printStackTrace();
								}
							}
							if (dayFolder.list() == null || dayFolder.list().length <= 0) {
								dayFolder.delete();
							}
						} catch (Exception e) {
							TPLogger.getLogger().error(e);
						}
					}
					if (yearMonthFolder.list() == null || yearMonthFolder.list().length <= 0) {
						yearMonthFolder.delete();
					}
				}
			} catch (Exception e) {
				TPLogger.getLogger().error(e);
			}
		}

	}

	public static void updatetextresume() {
		int start = 0;
		int batchSize = 100;
		int fetched = batchSize;
		while (batchSize == fetched) {
			fetched = 0;
			ArrayList<SimpleDataObject> applicants = getApplicantsResumes(start, batchSize);
			if (applicants != null) {
				fetched = applicants.size();
				for (int i = 0; i < fetched; i++) {
					checkAndUpdateTextResume(applicants.get(i));
				}
			}
			start = start + batchSize;
		}

	}

	private static void checkAndUpdateTextResume(SimpleDataObject sdo) {
		try {
			String applicantTextResume = sdo.getString("applicantTextResume");
			if (!Utils.isBlankOrNull(applicantTextResume)) {
				if (applicantTextResume.indexOf("<html") >= 0) {
					HTMLToPlainTextConverter conv = new HTMLToPlainTextConverter();
					applicantTextResume = conv.convertText(applicantTextResume);
					updateApplicantTextResume(sdo.getString("applicantId"), applicantTextResume);
				}
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(e);
		}
	}

	private static void updateApplicantTextResume(String applicantId, String applicantTextResume) {
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dMiscUtils_UpdateApplicantsResumeText");
			dq.setString(1, applicantTextResume);
			dq.setString(2, applicantId);
			dq.execute();
		} catch (Exception e) {
			TPLogger.getLogger().error(e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
	}

	private static ArrayList<SimpleDataObject> getApplicantsResumes(int start, int batchSize) {
		DBPreparedQuery dq = null;
		ArrayList<SimpleDataObject> applicants = null;
		try {
			dq = new DBPreparedQuery("dMiscUtils_GetApplicantsResumeText");
			dq.setInt(1, start);
			dq.setInt(2, batchSize);
			applicants = dq.getResult();
		} catch (Exception e) {
			TPLogger.getLogger().error(e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return applicants;
	}

	public static void main(String[] args) {
		cleanDocumentsFolder();
	}
}
