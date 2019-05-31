/*
 * Created on Aug 30, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.talentPool.parser.converter;

import java.io.Reader;
import java.io.StringReader;

import javax.swing.text.html.HTMLEditorKit;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.utils.FileHandler;


/**
 * @author pallavi
 * @date Aug 30, 2006
 */
public class HTMLToPlainTextConverter extends AbstractConverter {

	public String convertText(String html) {
		String result = "";
		try {
			result = getResult(html);
		} catch (Exception e) {
			TPLogger.getLogger().error("Error in convertText ", e);
		}
		return result;
	}

	public String convert(String filePath) {
		String result = "";
		try {
			FileHandler filehandler = new FileHandler();
			String html = filehandler.getTextFileContent(filePath, null);
			result = getResult(html);
		} catch (Exception e) {
			TPLogger.getLogger().error("Error in convert ", e);
		}
		return result;
	}

	private String getResult(String content) {
		String result = "";
		try {
			Reader r = new StringReader(content);
			// remove title tag to avoid name getting picked from title
			content = content.replaceAll("(?mi)<title>.*?</title>", "");
			HtmlToTextCallBack callback = new HtmlToTextCallBack();
			HTMLEditorKit.Parser parser = new HtmlParse().getParser();
			parser.parse(new StringReader(content), callback, true);
			result = callback.getResult();
			result = result.trim();
		} catch (Exception e) {
			TPLogger.getLogger().error("Error in getResult ", e);
		}
		return result;
	}

	public static void main(String[] args) {
		// HTMLToPlainTextConverter cov = new HTMLToPlainTextConverter();
	}
}