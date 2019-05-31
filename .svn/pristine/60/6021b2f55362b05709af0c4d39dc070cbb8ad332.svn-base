/**
 * 
 */
package com.talentPool.documents.utils;

import java.io.File;
import java.io.InputStream;
import java.util.Calendar;
import java.util.GregorianCalendar;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.dataobject.FormFileData;
import com.talentPool.common.utils.FileHandler;
import com.talentPool.common.utils.FileUploader;
import com.talentPool.common.utils.Utils;
import com.talentPool.common.utils.Exception.FileUploadException;
import com.talentPool.common.utils.Exception.InvalidMimeTypeException;
import com.talentPool.documents.DocumentConstants;
import com.talentPool.documents.dataobject.DocumentData;

/**
 * @author shivprasad
 * 
 */
public class DocumentUploader {
	public DocumentData saveFormFile(FormFileData formFileData) throws FileUploadException, InvalidMimeTypeException {
		DocumentData documentData = null;
		try {
			documentData = saveFileFromStream(formFileData.getInputStream(), formFileData.getFileName());
		} catch (InvalidMimeTypeException e) {
			throw e;
		} catch (Exception e) {
			throw new FileUploadException("Error saving form file");
		}

		return documentData;
	}

	public DocumentData saveFileFromStream(InputStream in, String fileName) throws FileUploadException, InvalidMimeTypeException {
		DocumentData documentData = null;
		try {
			String dayFolderPath = createTodayFolder(DocumentConstants.documentsPath);
			String relativePath = dayFolderPath.substring(DocumentConstants.documentsPath.length() + 1);
			FileUploader fileUploader = new FileUploader();
			String uploadedFileName = fileUploader.uploadFileFromStream(in, dayFolderPath, fileName, false, true);
			
			documentData = new DocumentData();
			documentData.setOriginalFileName(fileName);
			documentData.setChangedFileName(uploadedFileName);
			documentData.setRelativeFilePath(Utils.concatFilePath(relativePath, uploadedFileName));
			documentData.setAbsoluteFilePath(Utils.concatFilePath(dayFolderPath, uploadedFileName));
		} catch (InvalidMimeTypeException e) {
			throw e;
		} catch (Exception e) {
			throw new FileUploadException("Error saving form file");
		}

		return documentData;
	}
	
	public DocumentData saveTextAsDocument(String DocumentContent, String fileExt) throws FileUploadException {
		DocumentData documentData = null;
		try {
			String dayFolderPath = createTodayFolder(DocumentConstants.documentsPath);
			String relativePath = dayFolderPath.substring(DocumentConstants.documentsPath.length() + 1);

			String destFilename = File.createTempFile("DOC", fileExt, new File(dayFolderPath)).getName();
			String destPath = Utils.concatFilePath(dayFolderPath, destFilename);

			FileHandler fileHandler = new FileHandler();
			fileHandler.writeToFile(destPath, DocumentContent, false);

			documentData = new DocumentData();
			documentData.setChangedFileName(destFilename);
			documentData.setRelativeFilePath(Utils.concatFilePath(relativePath, destFilename));
			documentData.setAbsoluteFilePath(Utils.concatFilePath(dayFolderPath, destFilename));
		} catch (Exception e) {
			throw new FileUploadException("Error saving form file");
		}
		return documentData;
	}

	public String createTodayFolder(String basePath) throws Exception {
		Calendar cal = new GregorianCalendar();
		String monthFolder = cal.get(Calendar.YEAR) + ((cal.get(Calendar.MONTH) + 1 < 10) ? "0" + (cal.get(Calendar.MONTH) + 1) : "" + (cal.get(Calendar.MONTH) + 1));
		String dayFolder = cal.get(Calendar.DAY_OF_MONTH) < 10 ? "0" + cal.get(Calendar.DAY_OF_MONTH) : "" + cal.get(Calendar.DAY_OF_MONTH);

		String monthFolderPath = Utils.concatFilePath(basePath, monthFolder);
		String dayFolderPath = Utils.concatFilePath(monthFolderPath, dayFolder);
		FileHandler fileHandler = new FileHandler();
		fileHandler.createDirectory(monthFolderPath);
		fileHandler.createDirectory(dayFolderPath);
		return dayFolderPath;

	}
	
	public String getHtmlFilePathIfExist(String srcFilePath) {
		String htmlFile = null;
		try {
			htmlFile = srcFilePath.substring(0, srcFilePath.lastIndexOf(".")) + ".html";
			File f = new File(htmlFile);
			if (!f.exists()) {
				htmlFile = null;
			}
		} catch (Exception e) {
			TPLogger.getLogger().debug(e);
			htmlFile = null;
		}
		return htmlFile;
	}	


	public DocumentData saveFileFromBrowser(InputStream in, String fileName) throws FileUploadException {
		DocumentData documentData = null;
		try {
			String dayFolderPath = createTodayFolder(DocumentConstants.documentsPath);
			String relativePath = dayFolderPath.substring(DocumentConstants.documentsPath.length() + 1);
			FileUploader fileUploader = new FileUploader();
			String uploadedFileName = fileUploader.uploadFileFromFireFox(in, dayFolderPath, fileName);

			documentData = new DocumentData();
			documentData.setOriginalFileName(fileName);
			documentData.setChangedFileName(uploadedFileName);
			documentData.setRelativeFilePath(Utils.concatFilePath(relativePath, uploadedFileName));
			documentData.setAbsoluteFilePath(Utils.concatFilePath(dayFolderPath, uploadedFileName));
		} catch (Exception e) {
			throw new FileUploadException("Error saving form file");
		}

		return documentData;
	}
	
	
	public String saveSupportedFileFromBrowser(InputStream in, String fileName, String folderPath) throws FileUploadException {
		String uploadedFileName = "";
		try {
			String supportedFolder = DocumentConstants.documentsPath+"\\"+folderPath+"_files";
			FileHandler fileHandler = new FileHandler();
			fileHandler.createDirectory(supportedFolder);
			FileUploader fileUploader = new FileUploader();
			uploadedFileName = fileUploader.uploadFileFromFireFox(in, supportedFolder, fileName);
		} catch (Exception e) {
			throw new FileUploadException("Error saving form file");
		}
		return uploadedFileName;
	}
	
	public String getWordFilePathIfExist(String srcFilePath) {
		String wordFilePath = null;
		String docFilePath = null;
		String docxFilePath = null;
		try {
			docFilePath = srcFilePath.substring(0, srcFilePath.lastIndexOf(".")) + ".doc";
			File f = new File(Utils.concatFilePath(DocumentConstants.documentsPath, docFilePath));
			if( f.exists() && !f.isDirectory()) {
				wordFilePath = docFilePath;
			}
			else {
				docxFilePath = srcFilePath.substring(0, srcFilePath.lastIndexOf(".")) + ".docx";
				f = new File(Utils.concatFilePath(DocumentConstants.documentsPath, docxFilePath));
				if (f.exists() && !f.isDirectory()) {
					wordFilePath = docxFilePath;
				}
			}
		}
		catch (Exception e) {
			TPLogger.getLogger().debug(e);
			wordFilePath = null;
		}
		return wordFilePath;
	}
	
	public String getPdfFilePathIfExist(String srcFilePath) {
		String pdfFilePath = null;
		try {
			pdfFilePath = srcFilePath.substring(0, srcFilePath.lastIndexOf(".")) + ".pdf";
			File f = new File(Utils.concatFilePath(DocumentConstants.documentsPath, pdfFilePath));
			if(f.exists() && !f.isDirectory()) {
				/* PDF file exists, do nothing */
			}
			else {
				pdfFilePath = null;
			}
		}
		catch (Exception e) {
			TPLogger.getLogger().debug(e);
			pdfFilePath = null;
		}
		return pdfFilePath;
	}
		}
