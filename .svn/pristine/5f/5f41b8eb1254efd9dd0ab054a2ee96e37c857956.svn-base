/**
 * 
 */
package com.talentPool.parser.converter;

import java.io.InputStream;

import com.talentPool.common.Logger.TPLogger;

/**
 * @author shivprasad
 * 
 */
public class WordToHtmlConverter extends AbstractConverter {

	public String convert(String filePath) {
		return " ";
	}

	/**
	 * Takes srcfile and save it to destination file
	 * 
	 * @param srcFilePath
	 * @param destFilePath
	 * @return
	 */

	private boolean convert(String srcFilePath, String destFilePath) {
		boolean result = false;
		try {
			String lowersrcpath = srcFilePath.toLowerCase();
			if (lowersrcpath.endsWith(".doc") || lowersrcpath.endsWith(".docx") ) {
				result = WordConverter.convert(srcFilePath, destFilePath);
			} 
			/*
			if(lowersrcpath.endsWith(".doc") || lowersrcpath.endsWith(".docx")){
				JNIProcess process = new JNIProcess(SaaSUtils.getQueryStringForWordToHtml(srcFilePath.replace("/", "\\"), destFilePath.replace("/", "\\")));
				Thread converterThread = new Thread(process);
				converterThread.start();
				converterThread.join();
				result=process.getProcessResult();
			}*/
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while converting word doc to html", e);
		}
		return result;
	}
	
	private boolean convert(InputStream in ,String srcFilePath, String destFilePath) {
		boolean result = false;
		try {
			String lowersrcpath = srcFilePath.toLowerCase();
			if (lowersrcpath.endsWith(".doc") || lowersrcpath.endsWith(".docx") ) {
				result = WordConverter.convert(in,srcFilePath, destFilePath);
			} 
			/*
			if(lowersrcpath.endsWith(".doc") || lowersrcpath.endsWith(".docx")){
				JNIProcess process = new JNIProcess(SaaSUtils.getQueryStringForWordToHtml(srcFilePath.replace("/", "\\"), destFilePath.replace("/", "\\")));
				Thread converterThread = new Thread(process);
				converterThread.start();
				converterThread.join();
				result=process.getProcessResult();
			}*/
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while converting word doc to html", e);
		}
		return result;
	}

	public String convertToHtml(String srcFilePath) {
		String destPath = srcFilePath;
		try {
			String lowersrcpath = srcFilePath.toLowerCase();
			if (lowersrcpath.endsWith(".doc") || lowersrcpath.endsWith(".rtf") || lowersrcpath.endsWith(".docx") ) {
				destPath = srcFilePath.substring(0, srcFilePath.lastIndexOf(".")) + ".html";
				TPLogger.getLogger().debug("Converting " + srcFilePath + " TO " + destPath);
				if (!convert(srcFilePath, destPath)) {
					destPath = srcFilePath;
				}
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while converting word, rtf to html", e);
		}
		return destPath;
	}
	
	public String convertToHtml(InputStream in ,String srcFilePath) {
		String destPath = srcFilePath;
		try {
			String lowersrcpath = srcFilePath.toLowerCase();
			if (lowersrcpath.endsWith(".doc") || lowersrcpath.endsWith(".rtf") || lowersrcpath.endsWith(".docx") ) {
				destPath = srcFilePath.substring(0, srcFilePath.lastIndexOf(".")) + ".html";
				TPLogger.getLogger().debug("Converting " + srcFilePath + " TO " + destPath);
				if (!convert(in,srcFilePath, destPath)) {
					destPath = srcFilePath;
				}
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while converting word, rtf to html", e);
		}
		return destPath;
	}

//	public static void main(String[] args) {
//		try {
//			WordToHtmlConverter con = new WordToHtmlConverter();
//
//			// File srcFile = new File("c:\\distinct\\test1");
//			// String destPath = "c:\\distinct\\test1result\\";
//			File srcFile = new File("D:\\TalentPool\\DOC61988.doc");
//			String destPath = "D:\\TalentPool\\DOC61988.doc";
//			if (srcFile.isDirectory()) {
//				File[] listFiles = srcFile.listFiles();
//				for (int i = 0; i < listFiles.length; i++) {
//					File file = listFiles[i];
//					if (file.getName().endsWith(".doc") || file.getName().endsWith(".rtf")) {
//						String srcPath = file.getAbsolutePath();
//						String destFile = destPath + file.getName().substring(0, file.getName().lastIndexOf(".")) + ".html";
//						try {
//							if (con.convert(srcPath, destFile)) {
//							} else {
//							}
//						} catch (Exception e) {
//							e.printStackTrace();
//						}
//					}
//				}
//			} else {
//				String destP = con.convertToHtml("D:\\TalentPool\\DOC61988.doc");
//			}
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//	}
}
