/**
 * 
 */
package com.talentPool.parser.converter;

import java.io.FileInputStream;

import javax.swing.text.Document;
import javax.swing.text.rtf.RTFEditorKit;

import com.talentPool.common.Logger.TPLogger;

/**
 * @author shivprasad
 * 
 */
public class RTFToPlainTextConverter extends AbstractConverter {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.talentPool.parser.converter.AbstractConverter#convert(java.lang.String)
	 */
	public String convert(String filePath) {
		String result = "";
		try {
			FileInputStream fio = new FileInputStream(filePath);

			RTFEditorKit rtf = new RTFEditorKit();
			Document doc = rtf.createDefaultDocument();
			rtf.read(fio, doc, 0);
			result = doc.getText(0, doc.getLength());
		} catch (Exception e) {
			TPLogger.getLogger().error("Error in parser, can not read text from rtf", e);
		}
		return result;
	}

}
