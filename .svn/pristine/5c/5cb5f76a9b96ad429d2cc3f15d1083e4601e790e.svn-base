/**
 * 
 */
package com.talentPool.common.utils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.struts.upload.FormFile;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.properties.TPLabels;
import com.talentPool.common.utils.FileUtils.FileHandlerUtils;
import com.talentPool.documents.DocumentConstants;

/**
 * @author shivprasad
 * 
 */
public class FileHandler {
	public String getTextFileContent(String filePath, String encoding) throws Exception {
		StringBuffer sb = new StringBuffer();
		BufferedReader in = null;
		if (encoding == null) {
			in = new BufferedReader(new InputStreamReader(new FileInputStream(filePath)));
		} else {
			in = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), encoding));
		}
		String str;
		while ((str = in.readLine()) != null) {
			sb.append(str);
			sb.append("\n");
		}
		return sb.toString();
	}

	public String getContentType(String fileName) {
		String contentType = "";
		try {
			fileName = fileName.toLowerCase();
			String ext = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
			contentType = (String) FileHandlerUtils.getContentType(ext);
			if (Utils.isBlankOrNull(contentType)) {
				contentType = "application/octet-stream";
			}
		} catch (Exception e) {
			TPLogger.getLogger().debug("Can not detect content type for " + fileName, e);
			contentType = "application/octet-stream";
		}
		return contentType;
	}

	public void readFile(String filePath, ByteArrayOutputStream baos) {
		InputStream in = null;
		try {
			in = new BufferedInputStream(new FileInputStream(filePath));
			byte[] buf = new byte[4 * 1024];
			int bytesRead;
			while ((bytesRead = in.read(buf)) > 0) {
				baos.write(buf, 0, bytesRead);
			}
			baos.close();
		} catch (Exception e) {
			TPLogger.getLogger().error("Error reading file", e);
		} finally {
			try {
				if (in != null)
					in.close();
			} catch (Exception e) {
				TPLogger.getLogger().error("Error closing input stream", e);
			}
		}
	}

	public void copyFile(String sourcePath, String destinationPath) throws Exception {
		FileOutputStream out = null;
		FileInputStream in = null;
		try {
			out = new FileOutputStream(new File(destinationPath));
			in = new FileInputStream(new File(sourcePath));

			int bytesRead = 0;
			byte[] buffer = new byte[8192];
			while ((bytesRead = in.read(buffer, 0, 8192)) != -1) {
				out.write(buffer, 0, bytesRead);
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while copying File ", e);
			throw e;
		} finally {
			try {
				out.close();
			} catch (Exception ex) {
				TPLogger.getLogger().error("Error while closing dest file", ex);
			}
			try {
				in.close();
			} catch (Exception ex) {
				TPLogger.getLogger().error("Error while closing src file", ex);
			}
		}
	}

	public void copyFolder(String srcFolder, String destFolder) {
		try {
			File inputFolder = new File(srcFolder);
			if (!inputFolder.exists()) {
				return;
			}
			File outputFolder = new File(destFolder);
			boolean success = outputFolder.mkdir();
			if (!success) {
				return;
			}
			String[] children = inputFolder.list();
			if (children != null) {
				for (int i = 0; i < children.length; i++) {
					String f1 = Utils.concatFilePath(srcFolder, children[i]);
					String f2 = Utils.concatFilePath(destFolder, children[i]);
					try {
						copyFile(f1, f2);
					} catch (Exception ex) {
						TPLogger.getLogger().error("Error while copying file", ex);
					}
				}
			}

		} catch (Exception e) {
			TPLogger.getLogger().error("Error while copying folder", e);
		}
	}

	public boolean createDirectory(String dirName) throws Exception {
		File dirtocreate = new File(dirName);
		if (!dirtocreate.isDirectory()) {
			return dirtocreate.mkdir();
		}
		return false;
	}

	public boolean deleteDirectory(String folder) {
		try {
			File path = new File(folder);
			if (path.exists()) {
				File[] files = path.listFiles();
				for (int i = 0; i < files.length; i++) {
					if (files[i].isDirectory()) {
						deleteDirectory(files[i].getAbsolutePath());
					} else {
						files[i].delete();
					}
				}
			}
			return (path.delete());
		} catch (Exception e) {
			TPLogger.getLogger().error("Unable to delete folder = " + folder);
		}
		return false;
	}

	// public void deleteFolder(String folder) {
	// try {
	// File inputFolder = new File(folder);
	// if (inputFolder.exists()) {
	// String[] children = inputFolder.list();
	// if (children != null) {
	// for (int i = 0; i < children.length; i++) {
	// String f1 = Utils.concatFilePath(folder, children[i]);
	// try {
	// File f = new File(f1);
	// if (!f.delete()) {
	// TPLogger.getLogger().error("Unable to delete file = " + f1);
	// }
	// } catch (Exception ex) {
	// TPLogger.getLogger().error("Error while copying file", ex);
	// }
	// }
	// }
	// // delete folder
	// if (!inputFolder.delete()) {
	// TPLogger.getLogger().error("Unable to delete folder = " + folder);
	// }
	// }
	//
	// } catch (Exception e) {
	// TPLogger.getLogger().error("Error while deleting folder", e);
	// }
	// }

	public void writeToFile(String filePath, String fileContent, boolean replace) throws Exception {
		File f = new File(filePath);
		if (replace) {
			if (f.exists()) {
				f.delete();
			}
		}
		// f.createNewFile();
		BufferedWriter out = new BufferedWriter(new FileWriter(f));
		out.write(fileContent);
		out.close();

	}
	
	/**
	 * @param file
	 * @return
	 */
	public boolean isValidResume(String file) {
		return isValidExtension(DocumentConstants.SINGLE_IMPORT_EXTENSIONS,FileHandlerUtils.getFileExtention(file, null));
	}
	
	/**
	 * <p>
	 * Throughout the application if file type is not specific 
	 * then we allow only following file types to avoid security problems
	 * </p>   
	 * @param file
	 * @return
	 */
	public boolean commonFileTypeValidator(String file) {
		return commonExtValidator(FileHandlerUtils.getFileExtention(file));
	}
	
	public boolean commonExtValidator(String ext) {
		return isValidExtension(DocumentConstants.COMMON_EXTENSIONS,ext);
	}
	
	public boolean isValidExtension(String[] validExts,String ext) {
		boolean isValidExtension = false;
		for(int i = 0; i < validExts.length; i++) {
			if(("."+validExts[i]).equalsIgnoreCase(ext)) {
				isValidExtension = true;
				break;
			}
		}
		return isValidExtension;
	}
	
	/**
	 * This is common Form File validator.
	 * @param formFile
	 * @return
	 */
	public String commonFormFileValidator(FormFile formFile){
		StringBuilder error = new StringBuilder("");
		String fileName 	= formFile.getFileName();
		if (formFile.getFileSize() <= 0) {
			error.append(TPLabels.getLabel("upload_document.error.could_not_read_file"));
		}
		if(!commonFileTypeValidator(fileName)){
			error.append(TPLabels.getLabel("upload_document.error.invalid_file_type"));
		}
		return error.toString();
	}
	
	public String isValidEmailAttachment(String file){
		String error = "";
		if(!isValidExtForEmailAttachement(FileHandlerUtils.getFileExtention(file))){
			error = TPLabels.getLabel("upload_document.error.invalid_file_type");
		}	
		return error;
	}
	
	public boolean isValidExtForEmailAttachement(String ext){
		if (Utils.isBlankOrNull(ext) || 
			".bat".equalsIgnoreCase(ext) || 
			".exe".equalsIgnoreCase(ext)) {
			return false;
		}
		return true;
	}
	
}
