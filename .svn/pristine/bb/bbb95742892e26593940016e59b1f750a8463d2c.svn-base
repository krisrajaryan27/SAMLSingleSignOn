package com.talentPool.positions.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.talentPool.common.properties.TPLabels;
import com.talentPool.common.utils.Utils;
import com.talentPool.custom.dataobject.CustomFieldData;
import com.talentPool.positions.PositionParserConstants;

public class PositionParser {
	private static String REGEXP_KEY = "(?i)(^\\w+\\s*\\w*\\s*\\w*\\s*\\w*\\s*" + PositionParserConstants.POSITION_ATTRIBUTE_INDICATOR + "$)";
	private static String REGEXP_WORD = "(?i)(\\b\\S+\\b)";
	private static String REGEXP_COMMENT = "(?i)(^" + PositionParserConstants.POSITION_COMMENT + ")";
	private static String REGEXP_POSITION_SEPARATOR = "(?i)(^" + PositionParserConstants.POSITION_DEFINITION_SEPARATOR + ")";

	private Pattern keyPattern = null;
	private Pattern wordPattern = null;
	private Pattern commentPattern = null;
	private Pattern positionSeparatorPattern = null;
	
	private int lineNo;
	private int _lineNo;
	
	public PositionParser() {
		super();
		keyPattern = Pattern.compile(REGEXP_KEY);
		wordPattern = Pattern.compile(REGEXP_WORD);
		commentPattern = Pattern.compile(REGEXP_COMMENT);
		positionSeparatorPattern = Pattern.compile(REGEXP_POSITION_SEPARATOR);		 
		lineNo=0;
	}

	public List<Map<String, String>> parse(String contents, List<CustomFieldData> customFields) {
		List<Map<String, String>> parsedPositions = new ArrayList<Map<String,String>>();
		List<String> positions = getPositionDefinitions(contents);
		if(positions != null && positions.size() > 0) {
			for(int i = 0; i < positions.size(); i++) {
				if(!Utils.isBlankOrNull(positions.get(i))) {
					Map<String, String> position = parsePosition(positions.get(i), customFields);
					parsedPositions.add(position);					
				}
			}
		}
		return parsedPositions;
	}
	
	public String matchKey(String line) {
		StringBuffer key = new StringBuffer();
		if(!Utils.isBlankOrNull(line)) {
			line = line.trim();						
			Matcher keyMatcher = keyPattern.matcher(line);
			if(keyMatcher != null && keyMatcher.find()) {	
				List<String> words = getWords(keyMatcher.group());
				if(words != null && words.size() > 0) {
					for(int i = 0; i < words.size(); i++) {
						if(key.length() > 0) {
							key.append(" ");
						}
						key.append(words.get(i));
					}
				}
			}
		}
		return key.toString();
	}
	
	public List<String> getWords(String line) {
		List<String> words = null;
		if(!Utils.isBlankOrNull(line)) {
			words = new ArrayList<String>();
			Matcher wordMatcher = wordPattern.matcher(line.trim());
			if(wordMatcher != null) {
				while(wordMatcher.find()) {
					String group = wordMatcher.group();
					group = group.substring(0, 1).toUpperCase() + group.substring(1, group.length());					
					words.add(group);
				}
			}
		}		
		return words;
	}
	
	public List<String> getPositionDefinitions(String contents) {
		List<String> positions = null;
		if(!Utils.isBlankOrNull(contents)) {
			positions = new ArrayList<String>();
			Scanner scanner = new Scanner(contents);
			StringBuffer positionDef = new StringBuffer();
			while(scanner.hasNextLine()) {
				String line = scanner.nextLine();			
				if(isEndPositionDef(line)) {
					positions.add(positionDef.toString());
					positionDef = new StringBuffer();
				} else {
					if(positionDef.length() > 0) {
						positionDef.append(System.getProperty("line.separator"));
					}
					positionDef.append(line);
				}
			}
			if(positionDef.length() > 0) {
				positions.add(positionDef.toString());
			}
		}
		return positions;
	}
	
	public boolean isSinglePositionDef(String contents) {
		boolean isSinglePositionDef = true;
		List<String> positions = getPositionDefinitions(contents);
		if(positions == null) {
			isSinglePositionDef = false;
		} else if(positions.size() > 1) {
			isSinglePositionDef = false;
		}
		return isSinglePositionDef;
	}
	
	public Map<String, String> parsePosition(String contents, List<CustomFieldData> customFields) {		
		PositionParserUtils parserUtils = new PositionParserUtils();
		Map<String, String> position = parserUtils.getPositionAttributesToBeImported(customFields);
		if(!Utils.isBlankOrNull(contents)) {
			Scanner scanner = new Scanner(contents);
			String key = "";
			String value = "";			
			while(scanner.hasNextLine()) {
				String line = scanner.nextLine();				
				++lineNo;
				if(!Utils.isBlankOrNull(line) && !isComment(line)) {
//					String returnVal = matchKey(line);
//					if(!Utils.isBlankOrNull(returnVal)) {						
//						if(!Utils.isBlankOrNull(key)) {
//							setAttribute(position, key, value);
//						}
//						key = returnVal;
//						value = "";
//						_lineNo = lineNo;
//					} else {
//						value = value + System.getProperty("line.separator") + line;
//					}
					if (line.endsWith(PositionParserConstants.POSITION_ATTRIBUTE_INDICATOR)){
						key = line.substring(0, line.length()-1);
						if (scanner.hasNextLine()){
							value = scanner.nextLine();
						} else{
							value = "";
						}
					}
					if(!Utils.isBlankOrNull(key)) {
						setAttribute(position, key, value);
					}
				}
			}
			
		}
		return position;
	}
	
	public boolean isComment(String line) {
		boolean isComment = false;
		if(!Utils.isBlankOrNull(line)) {
			Matcher commentMatcher = commentPattern.matcher(line);
			isComment = commentMatcher.find();
		}		
		return isComment;
	}
	
	public boolean isEndPositionDef(String line) {
		boolean isEndPositionDef = false;
		if(!Utils.isBlankOrNull(line)) {
			line = line.trim();
			Matcher positionSeparatorMatcher = positionSeparatorPattern.matcher(line);
			isEndPositionDef = positionSeparatorMatcher.find();
		}		
		return isEndPositionDef;
	}

	private void setAttribute(Map<String, String> position, String key, String value) {
		if(position.containsKey(key)) {
			position.put(key, value.trim());			
		} else {
			if(!position.containsKey(PositionParserConstants.PARSING_ERRORS)) {
				position.put(PositionParserConstants.PARSING_ERRORS, new String());
			}
			String errors = position.get(PositionParserConstants.PARSING_ERRORS);
			if(errors.length() > 0) {
				errors = errors.concat("<br/>");
			}	
			errors = errors.concat(TPLabels.getLabel("position_parser.label.unknown_attribute"));
			errors = errors.concat("&nbsp;");
			errors = errors.concat(key);
			errors = errors.concat("&nbsp;");
			errors = errors.concat(TPLabels.getLabel("position_parser.label.on_line_number"));
			errors = errors.concat("&nbsp;");
			errors = errors.concat(new Integer(_lineNo).toString());
			position.put(PositionParserConstants.PARSING_ERRORS, errors);
		}
	}
}
 