package com.talentPool.porting.manager;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JFormattedTextField;

import org.apache.commons.digester.RegexMatcher;
import org.apache.tools.ant.util.regexp.RegexpUtil;

import com.talentPool.common.db.SimpleDataObject;
import com.talentPool.common.utils.RegexUtils;
import com.talentPool.common.utils.Utils;
import com.talentPool.documents.DocumentConstants;
import com.talentPool.inbox.InboxConstants;
import com.talentPool.porting.dataobject.FailedStatusObject;

public class ImportManager {

	public List<FailedStatusObject> importData(String sessionId, String fieldMappings, String importFilePath, String userId){
		String filePath = Utils.concatFilePath(DocumentConstants.documentsPath, importFilePath);			
		try {
			int columnCount = 0;
			String[] mappingStr = fieldMappings.split(",");
			BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(filePath)));
			String line = in.readLine(); // remove header line
			int rowNumber = 1;
			int startInd,endInd;
			char FieldSeparator = ',';
			
			List<SimpleDataObject> entities = new ArrayList<SimpleDataObject>();
			while(!Utils.isBlankOrNull(line= in.readLine())){
				SimpleDataObject object = new SimpleDataObject();
				line = replaceComaWithChrInBetwnQuotes(line);
				columnCount = 0;
				int len = line.length();
				for (startInd = -1, endInd = 0; endInd >= 0;) {
					String dataText = "";
					endInd = line.indexOf(FieldSeparator, startInd + 1);
					if (endInd < 1) {
						dataText = line.substring(startInd+1,len);
					} else {
						dataText = line.substring(startInd+1,endInd);
					}					
					if (!Utils.isBlankOrNull(dataText)) {
						dataText = dataText.replaceAll(InboxConstants.EXCEL_REPLACE_STR, ",");
					}					
					String colName = "0";
					// edited for a header bug  replaced "<="  with "<"
					if (columnCount < mappingStr.length) {
						colName = mappingStr[columnCount];						
					}
					columnCount++;
					startInd = endInd;
					dataText = trimAndRemoveInvalidCharacters(dataText);
					object.setAttribute(colName, dataText);
					
				}
				entities.add(object);
				  
			}
			return saveData(entities);
		}catch(Exception e){
			return null;
		}
			
	}
	
	public List<FailedStatusObject> saveData(List<SimpleDataObject> entities){
		return new ArrayList<FailedStatusObject>();
	}
	
	public String replaceComaWithChrInBetwnQuotes(String line) {
		String[] lineStr = line.split("\"");
		String newLine = "";
		for (int i = 0; i < lineStr.length; i++) {
			String txt = lineStr[i];
			if (i % 2 == 0) {
				// ignore even numbers
			} else {
				String[] txtStr = txt.split(",");
				String newText = "";
				for (int j = 0; j < txtStr.length; j++) {
					if (newText.equals("")) {
						newText = txtStr[j];
					} else {
						newText += InboxConstants.EXCEL_REPLACE_STR + txtStr[j];
					}
				}
				txt = newText;
			}
			if (newLine.equals("")) {
				newLine = txt;
			} else {
				newLine += txt;
			}
		}
		return newLine;
	}
	
	public String trimAndRemoveInvalidCharacters(String input){
		if(!Utils.isBlankOrNull(input)){
			char[] charArray = input.toCharArray();
		
			int start = 0;	
			int end = charArray.length-1;
			while(Character.isSpaceChar(charArray[start])){
				start += 1;
			}		
			while(Character.isSpaceChar(charArray[end])){
				end -= 1;
			}
			
			return input.substring(start, end+1);
		}
		else
			return input;
	}
}
