package com.talentPool.parser.converter;

import javax.swing.text.MutableAttributeSet;
import javax.swing.text.html.*;

import com.talentPool.common.Logger.TPLogger;

public class HtmlToTextCallBack extends HTMLEditorKit.ParserCallback {
	StringBuffer result = new StringBuffer(2000);

	public void handleComment(char[] data, int pos) {
		// insert code to handle comments
		
	}

	public void handleText(char[] data, int pos) {
		// insert code to handle comments
		try {
			String s = new String(data);
			result.append(s);
			
		} catch (Exception e) {
			TPLogger.getLogger().error(e);
		}
	}

	public void handleEndTag(HTML.Tag t, int pos) {
		// insert code to handle ending tags
		
	}

	public void handleError(String errorMsg, int pos) {
		// insert error handling code
	}

	public void handleSimpleTag(HTML.Tag t, MutableAttributeSet a, int pos) {
		// insert code to handle simple tags
		
		try {
			if (t == HTML.Tag.BR) {
				result.append("\n");
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(e);
		}
	}

	public void handleStartTag(HTML.Tag t, MutableAttributeSet a, int pos) {
		// insert code to handle starting tags
		
		try {
			if (t.breaksFlow()) {
				
				result.append("\n");
			} else if (t.isBlock()) {
				
				result.append("\n\n");
			} else if (t == HTML.Tag.P) {
				result.append("\n");
			}
			
		} catch (Exception e) {
			TPLogger.getLogger().error(e);
		}

	}

	public String getResult() {
		return result.toString();
	}

}
