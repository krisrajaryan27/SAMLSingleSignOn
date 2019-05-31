package com.talentPool.parser.converter;

import java.io.File;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.utils.Utils;

public class GenericConverter extends AbstractConverter {

	public String convert(String filePath) {
		String result = "";
		try {
			File f = new File(filePath);
			String fileName = f.getName();
			int lastDot = fileName.lastIndexOf(".");
			String extension = fileName.substring(lastDot + 1).toLowerCase();
			if (extension.equals("txt")) {
				result = readFile(f);
			}
			if (extension.equals("rtf")) {
				RTFToPlainTextConverter conv = new RTFToPlainTextConverter();
				result = conv.convert(filePath);
				TPLogger.getLogger().debug("RTF Converted Document = " + result);
			}
			if (extension.equals("html") || extension.equals("htm") || extension.equals("xml")) {
				HTMLToPlainTextConverter conv = new HTMLToPlainTextConverter();
				// read header file content if present first
				String headerfilePath = Utils.concatFilePath(filePath.replace("." + extension, "_files"), "header.html");
				result = conv.convert(filePath);
				File file = new File(headerfilePath);
				if(file.exists()){
					String headcontent = conv.convert(headerfilePath);
					if (!Utils.isBlankOrNull(headcontent)) {
						result = headcontent + result;
					}
				}
				TPLogger.getLogger().debug(result);
			}
			if (extension.equals("doc") || extension.equals("docx")) {
				WordToTextConverter conv = new WordToTextConverter();
				result = conv.convert(filePath);
			}
			if (extension.equals("pdf")) {
				PDFToTextConverter conv = new PDFToTextConverter();
				result = conv.convert(filePath);
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error in GenericConverter -> convert", e);
		}
		return result;
	}
}
