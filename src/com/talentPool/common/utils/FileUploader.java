/**
 * 
 */
package com.talentPool.common.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.struts.upload.FormFile;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.utils.Exception.FileUploadException;
import com.talentPool.common.utils.Exception.InvalidMimeTypeException;
import com.talentPool.common.utils.FileUtils.FileHandlerUtils;
import com.talentPool.documents.utils.DocumentUtils;

/**
 * @author shivprasad
 * 
 */
public class FileUploader {

	/**
	 * @param formFile
	 * @param destinationPath
	 * @param overwrite
	 * @param useDifferentName
	 * @return
	 * @throws FileUploadException
	 */
	public String uploadFile(FormFile formFile, String destinationPath, String fileName, boolean overwrite, boolean useDifferentName) throws FileUploadException {
		try {
			fileName = uploadFileFromStream(formFile.getInputStream(), destinationPath, fileName, overwrite, useDifferentName);
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while uploading file", e);
			throw new FileUploadException("Error while uploading file");
		}
		return fileName;
	}
	
	public String uploadFileFromStream(InputStream in, String destinationPath, String fileName, boolean overwrite, boolean useDifferentName) throws FileUploadException, InvalidMimeTypeException {
		FileOutputStream out = null;
		try {
			// if overwrite is false, take new file name if the file with
			// fileName already exist.
			
			if (DocumentUtils.checkForMultipleExtension(fileName)){
				throw new Exception("File with multiple extension");
			}
			
			if (!DocumentUtils.isValidMImeTypeInputStream(in, fileName)){
				throw new InvalidMimeTypeException("Invalid Mime type");
			}
			
			if (!overwrite) {
				File f = new File(Utils.concatFilePath(destinationPath, fileName));
				if (f.exists()) {
					useDifferentName = true;
				}
			}
			if (useDifferentName) {
				String fileExt = "";
				if (!Utils.isBlankOrNull(fileName) && fileName.indexOf(".") > 0) {
					fileExt = fileName.substring(fileName.lastIndexOf("."));
				}
				fileName = File.createTempFile("ATT", fileExt, new File(destinationPath)).getName();
			}
			out = new FileOutputStream(new File(Utils.concatFilePath(destinationPath, fileName)));
			
			int bytesRead = 0;
			byte[] buffer = new byte[8192];
			if ((bytesRead = in.read(buffer, 0, 8192)) != -1){
				if (!DocumentUtils.isValidMimeTypeBuffer(buffer)){
					throw new InvalidMimeTypeException("Invalid Mime Type");
				}
				out.write(buffer, 0, bytesRead);
				bytesRead = 0;
			}
			while ((bytesRead = in.read(buffer, 0, 8192)) != -1) {
				out.write(buffer, 0, bytesRead);
			}
			
			if (!DocumentUtils.isValidMimeType(Utils.concatFilePath(destinationPath, fileName))){
				throw new InvalidMimeTypeException("Invalid Mime type");
			}
			
			// int c;
			// while ((c = in.read()) != -1)
			// out.write(c);
		} catch (InvalidMimeTypeException e) {
			throw e;
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while uploading file", e);
			throw new FileUploadException("Error while uploading file");
		} finally {
			try {
				in.close();
				if (out != null)
					out.close();
			} catch (Exception e) {
				TPLogger.getLogger().error("Error in closing IO Streams", e);
			}
		}
		return fileName;
	}
	
	public String copyFile(File file, String destinationPath, String fileName, boolean overwrite, boolean useDifferentName) throws FileUploadException {
		FileInputStream in = null;
		FileOutputStream out = null;
		try {
			// if overwrite is false, take new file name if the file with
			// fileName already exist.

			if (!overwrite) {
				File f = new File(Utils.concatFilePath(destinationPath, fileName));
				if (f.exists()) {
					useDifferentName = true;
				}
			}
			if (useDifferentName) {
				String fileExt = "";
				if (!Utils.isBlankOrNull(fileName) && fileName.indexOf(".") > 0) {
					fileExt = fileName.substring(fileName.lastIndexOf("."));
				}
				fileName = File.createTempFile("ATT", fileExt, new File(destinationPath)).getName();
			}
			in = new FileInputStream(file);
			out = new FileOutputStream(new File(Utils.concatFilePath(destinationPath, fileName)));

			int bytesRead = 0;
			byte[] buffer = new byte[8192];
			while ((bytesRead = in.read(buffer, 0, 8192)) != -1) {
				out.write(buffer, 0, bytesRead);
			}
			// int c;
			// while ((c = in.read()) != -1)
			// out.write(c);
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while uploading file", e);
			throw new FileUploadException("Error while uploading file");
		} finally {
			try {
				in.close();
			} catch (IOException io) {
			}
			try {
				out.close();
			} catch (IOException io) {
			}
		}
		return fileName;
	}
	
	public String uploadFileFromFireFox(InputStream in, String destinationPath, String fileName) throws FileUploadException {
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(new File(Utils.concatFilePath(destinationPath, fileName)));

			int bytesRead = 0;
			byte[] buffer = new byte[8192];
			while ((bytesRead = in.read(buffer, 0, 8192)) != -1) {
				out.write(buffer, 0, bytesRead);
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			throw new FileUploadException("Error while uploading file");
		} finally {
			try {
				in.close();
			} catch (IOException io) {
			}
			try {
				out.close();
			} catch (IOException io) {
			}
		}
		return fileName;
	}
}
