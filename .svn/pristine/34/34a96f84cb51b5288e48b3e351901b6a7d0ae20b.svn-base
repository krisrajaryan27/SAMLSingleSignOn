package com.talentPool.common.utils.FileUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.utils.Utils;

import eu.medsea.mimeutil.MimeUtil;

/**
 * @author PraveenK
 * @since  Aug 4, 2011
 */
public class FileHandlerUtils {
	private static final Map<String,String> contentTypes; 
	static{
		contentTypes = new HashMap<String,String>();
		contentTypes.put("doc", "application/msword");
		contentTypes.put("rtf", "application/msword");
		contentTypes.put("pdf", "application/pdf");
		contentTypes.put("gzip","application/x-gzip");
		contentTypes.put("zip", "application/zip");
		contentTypes.put("css", "text/css");
		contentTypes.put("html","text/html");
		contentTypes.put("htm", "text/html");
		contentTypes.put("txt", "text/plain");
		contentTypes.put("xml", "text/xml");
		contentTypes.put("gif", "image/gif");
		contentTypes.put("jpeg","image/jpeg");
		contentTypes.put("png", "image/png");
		contentTypes.put("mpeg","video/mpeg");
		contentTypes.put("tiff","image/tiff");
		contentTypes.put("xls", "application/vnd.ms-excel");
		contentTypes.put("ppt", "application/vnd.ms-powerpoint");
		contentTypes.put("xlsx","application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		contentTypes.put("docx","application/vnd.openxmlformats-officedocument.wordprocessingml.document");
		contentTypes.put("pptx","application/vnd.openxmlformats-officedocument.presentationml.presentation");
		/* other types which can be added
		 application/octet-stream	 application/postscript		 application/x-java-archive
		 application/x-java-vm		 audio/basic		 		 audio/x-aiff
		 audio/x-wav				 audio/midi		 video/quicktime
		 */	
		}
	
	/**
	 * Returns the contentType based on the extension provided
	 * @param ext
	 * @return
	 */
	public static String getContentType(String ext) {
		return contentTypes.get(ext);
	} 
	
	public static String getFileExtention(String file) {
		return getFileExtention(file,null);
	}

	public static String getFileExtention(String file, String defaultExt) {
		String ext = defaultExt;
		if (!Utils.isBlankOrNull(file)) {
			if (file.indexOf(".") > 0) {
				ext = file.substring(file.lastIndexOf("."));
			}
		}
		return ext;
	}
	
	public static boolean isExcelDoc(String file) {
		String ext= getFileExtention(file, null);
		if (".xls".equalsIgnoreCase(ext) || ".xlsx".equalsIgnoreCase(ext)) {
			return true;
		}
		return false;
	}
	
	public static boolean isWordDoc(String fileName) {
		String ext = getFileExtention(fileName, "");
		if(".doc".equals(ext) || ".docx".equals(ext)) {
			return true;
		}
		return false;
	}
	public static String checkMsWordFileExtnsion(String fileName){
		String ext="";
		MimeUtil.registerMimeDetector("eu.medsea.mimeutil.detector.MagicMimeMimeDetector");
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len;
	
		try {
			FileInputStream fis = new FileInputStream(new File(fileName));
			while ((len = fis.read(buffer)) > -1 ) {
			    baos.write(buffer, 0, len);
			}
		} catch(Exception e){
			TPLogger.getLogger().error("Error while checking mime type of the word file",e);
		}finally{
			try {
				baos.flush();
			} catch (IOException e) {
				TPLogger.getLogger().error("Error while checking mime type of the word file-- unable to close the bytearryoutputstream",e);
			}
		}
		
		Collection<?> mimeTypes = MimeUtil.getMimeTypes(baos.toByteArray());
		String mimeType = MimeUtil.getFirstMimeType(mimeTypes.toString()).toString();
		String subMimeType = MimeUtil.getSubType(mimeTypes.toString());
		TPLogger.getLogger().debug("The Mime type is: " + mimeTypes + ", " + mimeType + ", " + subMimeType);
		MimeUtil.unregisterMimeDetector("eu.medsea.mimeutil.detector.MagicMimeMimeDetector");
		if(mimeType.equals("application/zip")){
			ext=".docx";
		}else{
			ext=".doc";
		}
		return ext;
	}
}
