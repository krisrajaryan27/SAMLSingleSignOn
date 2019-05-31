/**
 * The basic interface for regular expression matching in
 * TalentPool
 */

package com.talentPool.parser.regex;

import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class TPRegExp {
	// private String groupPatternStr;
	// private int numPatterns;
	private CharSequence matchStr;

	private Pattern pattern;
	private Matcher matcher;

	private ArrayList expressions;

	/* Constructor */
	public TPRegExp() {
		// groupPatternStr = new String();
		// numPatterns = 0;
		expressions = new ArrayList();
	}

	public void addNewRegExp(String regExpStr) {
		expressions.add(regExpStr);
		// Need extra '(' for first addition, else need |
		// if (groupPatternStr.length() == 0)
		// groupPatternStr = "(";
		// else
		// groupPatternStr += "|";
		// groupPatternStr += ("(" + regExpStr + ")");
		// numPatterns++;
	}

	private String getGroupExpression() {
		StringBuffer groupPatternStr = new StringBuffer();
		int numPatterns = expressions.size();
		for (int i = 0; i < numPatterns; i++) {
			if (i == 0) {
				groupPatternStr.append("(");
			} else {
				groupPatternStr.append("|");
			}
			groupPatternStr.append("(" + expressions.get(i) + ")");
			if (i == numPatterns - 1) {
				groupPatternStr.append(")");
			}
		}
		return groupPatternStr.toString();
	}

	public void stringToBeMatched(CharSequence matchStr) {
		// First complete pattern str with ')'
		this.matchStr = matchStr;
	}

	public Matcher match() {
		String groupPatternStr = getGroupExpression();
		
		// Create a pattern based on it
		//pattern = Pattern.compile(groupPatternStr, Pattern.CASE_INSENSITIVE);
		pattern = Pattern.compile(groupPatternStr, Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.UNIX_LINES);
		
		// Set matchStr and create a matcher for it.
		matcher = pattern.matcher(this.matchStr);
		return matcher;
	}

	public boolean matchFirst() {
		matcher.reset();
		return matcher.find(0);
	}

	public boolean matchNext() {
		return matcher.find();
	}

	/*
	 * Return an array of strings that matched the regular expressions up to numPatterns. Note: For patterns that are not matched, null is returned in the corresponding position!!
	 */
	public String[] matchedStrs() {
		int numPatterns = expressions.size();
		String matchedStrArray[] = new String[numPatterns];

		// Don't start from 0 - that is the whole pattern string!
		for (int i = 1; i <= numPatterns; i++) {
			matchedStrArray[i - 1] = matcher.group(i);
		}
		return matchedStrArray;
	}
};
