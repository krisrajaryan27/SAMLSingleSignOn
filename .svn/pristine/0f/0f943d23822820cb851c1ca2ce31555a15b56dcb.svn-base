/**
 * 
 */
package com.talentPool.repository;

import org.apache.lucene.search.highlight.Formatter;
import org.apache.lucene.search.highlight.TokenGroup;

/**
 * @author shivprasad
 * 
 */
public class TPHighlighter implements Formatter {

	public String highlightTerm(String originalText, TokenGroup group) {
		if (group.getTotalScore() <= 0) {
			return originalText;
		}
		// numHighlights++; // update stats used in assertions
		return "<font class=\"highlighter\">" + originalText + "</font>";
	}

}
