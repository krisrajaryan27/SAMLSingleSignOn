/**
 * 
 */
package com.talentPool.documents.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Collection;
import java.util.HashMap;

import net.sf.jmimemagic.Magic;
import net.sf.jmimemagic.MagicMatch;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.properties.TPApplicationProperties;
import com.talentPool.common.utils.FileHandler;
import com.talentPool.common.utils.Utils;
import com.talentPool.common.utils.Exception.PathTraversalException;
import com.talentPool.documents.DocumentConstants;

import eu.medsea.mimeutil.MimeUtil;

/**
 * @author shivprasad
 *
 */
public class DocumentUtils {
	static HashMap<String, String> mapExtIcon = new HashMap<String, String>();
	static HashMap<String, String> validMimeTypes = new HashMap<String, String>();
	static {
		reloadMimeTypes();
		mapExtIcon.put("doc", "logo_word.gif");
		mapExtIcon.put("rtf", "logo_rtf.gif");
		mapExtIcon.put("pdf", "logo_pdf.gif");
		mapExtIcon.put("txt", "logo_notepad.gif");
		mapExtIcon.put("gif", "logo_image.gif");
		mapExtIcon.put("jpeg", "logo_image.gif");
		mapExtIcon.put("png", "logo_image.gif");
		mapExtIcon.put("ico", "logo_image.gif");
		mapExtIcon.put("xls", "logo_excel.gif");
		mapExtIcon.put("ppt", "logo_ppt.gif");
		mapExtIcon.put("gzip", "logo_zip.gif");
		mapExtIcon.put("zip", "logo_zip.gif");
		mapExtIcon.put("gz", "logo_zip.gif");
		mapExtIcon.put("xml", "logo_xml.gif");
		mapExtIcon.put("css", "logo_html.gif");
		mapExtIcon.put("html", "logo_html.gif");
		mapExtIcon.put("htm", "logo_html.gif");
		mapExtIcon.put("js", "logo_javascript.gif");
		mapExtIcon.put("mpeg", "logo_video.gif");
		mapExtIcon.put("mpg", "logo_video.gif");
		mapExtIcon.put("avi", "logo_video.gif");
		mapExtIcon.put("mp3", "logo_audio.gif");
		mapExtIcon.put("wav", "logo_audio.gif");
		mapExtIcon.put("pub", "logo_pub.gif");
		mapExtIcon.put("exe", "logo_exe.gif");
		mapExtIcon.put("bat", "logo_bat.gif");
		mapExtIcon.put("odt", "logo_odt.gif");
		mapExtIcon.put("docx", "logo_word.gif");
		mapExtIcon.put("xlsx", "logo_excel.gif");

	}
	public static String getDocumentURL(String fileName, String contentDisposition){
		String url = DocumentConstants.DOCUMENT_URL;
		url = url.replace("$fileName", fileName);
		url = url.replace("$contentDisposition", contentDisposition);
		return url;
	}
	public static String getFileImageName(String fileName) {
		String imageName = "ico_file.gif";
		try {
			fileName = fileName.toLowerCase();
			String ext = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
			imageName = mapExtIcon.get(ext);
			if (Utils.isBlankOrNull(imageName)) {
				imageName = "ico_file.gif";
			}
		} catch (Exception e) {
			// TLogger.getLogger().debug("Can not find logo for " + fileName,
			// e);
		}
		return imageName;
	}
	
	public static void reloadMimeTypes(){
		String[] commonMimeTypes = TPApplicationProperties.getProperty("common.mimetypes.allowed").split(",");
		for (int i =0; i<commonMimeTypes.length ; i++){
			validMimeTypes.put(commonMimeTypes[i],commonMimeTypes[i]);
		}
	}
	
	/**
	 * @change siddharthk
	 * @param is
	 * method added to check mime types from the input stream.
	 * External Dependencies added : mime-util-2.1.3.jar
	 * References: http://www.rgagnon.com/javadetails/java-0487.html,
	 * (javadoc) http://www.jarvana.com/jarvana/view/eu/medsea/mimeutil/mime-util/2.1/mime-util-2.1-javadoc.jar!/eu/medsea/mimeutil/MimeUtil.html
	 * http://stackoverflow.com/questions/13775494/java-get-file-type-from-content-using-mimeutil-is-not-working-as-expected
	 * MimeUtil is registered with different mime detectors:
	 * eu.medsea.mimeutil.detector.MagicMimeMimeDetector detects mime type on the basis of headers.
	 * eu.medsea.mimeutil.detector.ExtensionMimeDetector detects mime type on the basis of extension.
	 * @return
	 */
	public static boolean isValidMimeType(String fileName) {
		boolean isValidMimeType = true;
		try {
			File file = new File(fileName);
			MimeUtil.registerMimeDetector("eu.medsea.mimeutil.detector.MagicMimeMimeDetector");
			MimeUtil.registerMimeDetector("eu.medsea.mimeutil.detector.ExtensionMimeDetector");
			MimeUtil.registerMimeDetector("eu.medsea.mimeutil.detector.OpendesktopMimeDetector");
			Collection<?> mimeTypes = MimeUtil.getMimeTypes(file);
			String[] types = mimeTypes.toString().split(",");
			String extension = MimeUtil.getExtension(fileName);
			if (extension.equals("csv")) {
				validMimeTypes.put("application/octet-stream",
						"application/octet-stream");
				try {
					MagicMatch mm = Magic.getMagicMatch(file, false);
					if (mm.getDescription().contains("EXE")
							|| mm.getDescription().contains("batch")
							|| mm.getDescription().contains("MS-DOS")
							|| mm.getDescription().contains("executable")
							|| mm.getMimeType().equals("???")) {
						return false;
					}
				} catch (Exception e) {
					TPLogger.getLogger().error("Error in finding mime type via JMimeMagic", e);
				}
			}
			for (int i = 0; i < types.length; i++) {
				if (Utils.isMapEmptyOrNull(validMimeTypes))
					reloadMimeTypes();
				if (!validMimeTypes.containsKey(types[i])) {
					isValidMimeType = false;
					if (file.exists()) {
						file.delete();
					}
					break;
				}
			}
			if (validMimeTypes.containsKey("application/octet-stream"))
				validMimeTypes.remove("application/octet-stream");
		} finally {
			MimeUtil.unregisterMimeDetector("eu.medsea.mimeutil.detector.MagicMimeMimeDetector");
			MimeUtil.unregisterMimeDetector("eu.medsea.mimeutil.detector.ExtensionMimeDetector");
			MimeUtil.unregisterMimeDetector("eu.medsea.mimeutil.detector.OpendesktopMimeDetector");
		}
		return isValidMimeType;
	}

	public static boolean isValidMImeTypeInputStream(InputStream is,
			String fileName) {
		boolean isValidMimeType = true;
		try{
			try {
				MimeUtil.registerMimeDetector("eu.medsea.mimeutil.detector.MagicMimeMimeDetector");
				MimeUtil.registerMimeDetector("eu.medsea.mimeutil.detector.ExtensionMimeDetector");
				MimeUtil.registerMimeDetector("eu.medsea.mimeutil.detector.OpendesktopMimeDetector");
				Collection<?> mimeTypes = null;
				String[] types = {};
				try{
					mimeTypes = MimeUtil.getMimeTypes(is);
				} catch (Exception e){
					//TPLogger.getLogger().error("Error in finding mime type via Mime Util input stream.", e);
				}
				String extension = MimeUtil.getExtension(fileName);
				if (extension.equals("csv") || extension.equals("html") || extension.equals("txt")) {
					validMimeTypes.put("application/octet-stream",
							"application/octet-stream");
				}
				if (mimeTypes != null){
					types = mimeTypes.toString().split(",");
				}
				for (int i = 0; i < types.length; i++) {
					if (Utils.isMapEmptyOrNull(validMimeTypes))
						reloadMimeTypes();
					if (!validMimeTypes.containsKey(types[i])) {
						isValidMimeType = false;
						break;
					}
				}
				if (validMimeTypes.containsKey("application/octet-stream"))
					validMimeTypes.remove("application/octet-stream");
			} finally {
				MimeUtil.unregisterMimeDetector("eu.medsea.mimeutil.detector.MagicMimeMimeDetector");
				MimeUtil.unregisterMimeDetector("eu.medsea.mimeutil.detector.ExtensionMimeDetector");
				MimeUtil.unregisterMimeDetector("eu.medsea.mimeutil.detector.OpendesktopMimeDetector");
			}
		}catch(Exception e){
			TPLogger.getLogger().error("Exception in finding mime type", e);
		}catch(Error e){
			TPLogger.getLogger().error("RuntimeError in finding mime type", e);
		}
		return isValidMimeType;
	}
	
	public static boolean isValidMimeTypeBuffer(byte[] buffer) {
		boolean isValidMimeType = true;
		try {
			MagicMatch mm = Magic.getMagicMatch(buffer);
			if (mm.getDescription().contains("EXE")
					|| mm.getDescription().contains("batch")
					|| mm.getDescription().contains("MS-DOS")
					|| mm.getDescription().contains("executable")
					|| mm.getMimeType().equals("???")) {
				return false;
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(
					"Error in finding mime type via JMimeMagic", e);
		}
		return isValidMimeType;
	}
	
	/**
	 * @param dirPath
	 * @param fileName
	 * @return sanitized file path
	 * @throws IOException
	 * @throws PathTraversalException
	 */
	public static String sanitize(final String dirPath, final String fileName) throws IOException, PathTraversalException {
	    final File dir = new File(dirPath);
	    final File file = new File(fileName);
		if (fileName.length() == 0
	    		|| file.isDirectory()
	    		|| file.isAbsolute()) {
	        throw new PathTraversalException(fileName);
	    }

	    final String canonicalDirPath = dir.getCanonicalPath() + File.separator;
	    final String canonicalEntryPath = new File(dir, fileName).getCanonicalPath();

	    if (!canonicalEntryPath.startsWith(canonicalDirPath)) {
	        throw new PathTraversalException(fileName);
	    }

	    return canonicalEntryPath.substring(canonicalDirPath.length());
	}
	
	/**
	 * @param fileName
	 * @return true when file has double extensions.
	 */
	public static boolean checkForMultipleExtension(String fileName){
		boolean haveMultipleExtensions = false;
		String fName = "";
		try {
			fName=fileName.substring(0,fileName.lastIndexOf("."));
			File f = new File(fName);
			String type = Files.probeContentType(f.toPath());
			if (!Utils.isBlankOrNull(type)){
				haveMultipleExtensions = true;
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error in finding whether file has multiple extensions", e);
		}
		return haveMultipleExtensions;
	}

	public static String getHTMLFileContent(String filePath) {
		String fileContent = "";
		try {
			FileHandler fileHandler = new FileHandler(); // added
			if (filePath.endsWith("html") || filePath.endsWith("htm")) {
				fileContent = fileHandler.getTextFileContent(filePath, null);// added
			}
			// do this when file is html
		} catch (Exception e) {
			TPLogger.getLogger().error(e);
			fileContent = "";
		}
		return fileContent;
	}
}
