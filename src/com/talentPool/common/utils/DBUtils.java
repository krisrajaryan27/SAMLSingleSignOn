/**
 * 
 */
package com.talentPool.common.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.db.DBConstants;

/**
 * @author Ajeet
 *
 */
public class DBUtils {

	public static final int increaseBy = 1;
	public static final char suffix = 'x';
	public static final char preffix = 'w';
	
	
	public static String encryptString(String string){
		String newString = "";
		for (int i = 0; i < string.length(); i++) {
			newString += encryptChar(string.charAt(i));
		}
		return suffix+newString+preffix;
	}
	
	public static String decryptString(String string){
		String newString = "";
		string = string.substring(1,string.length()-1);
		for (int i = 0; i < string.length(); i++) {
			newString += decryptChar(string.charAt(i));
		}
		return newString;
	}
	
	public static char encryptChar(char character){
		return character = (char) (character + increaseBy);
	}
	
	public static char decryptChar(char character){
		return character = (char) (character - increaseBy);
	}
	
	/**
	 * @param args
	 */
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String name = "N!tm@n";
		name = encryptString(name);
		System.out.println(name);
		name = decryptString(name);
		System.out.println(name);
	}

	/**
	 * @param sql
	 * @return map of columns which has encrypted data and keys used for them
	 */
	public static HashMap<String, String> findEncryptedDbColumns(String sql) {
		HashMap<String, String> columnAliasNamesAndKeys = new HashMap<String, String>();
		Set<Map.Entry<String, String>> entries = DBConstants.columnKeyMap.entrySet();
		String columnAliasName = null, column = null, key = null;
		for(Map.Entry<String, String> entry : entries) {
			column = entry.getKey();
			key = entry.getValue();
			if(sql.contains(column)) {
				columnAliasName = DBUtils.findAliasNameForColumn(sql, column);
				columnAliasNamesAndKeys.put(columnAliasName, key);
			}
		}
		return columnAliasNamesAndKeys;
	}
	
	/**
	 * @param sql
	 * @param column
	 * @return if an alias is used for the column
	 */
	public static String findAliasNameForColumn(String sql, String column) {
		String aliasName = column;
		int columnIndex = sql.indexOf(column);
		int nextCommaOrFromClauseIndex = sql.indexOf(",", columnIndex);
		if(nextCommaOrFromClauseIndex!=-1 && nextCommaOrFromClauseIndex<sql.length()){
			String temp = null;
			try{
				temp= sql.substring(columnIndex, nextCommaOrFromClauseIndex);
			}catch(StringIndexOutOfBoundsException e){
				TPLogger.getLogger().debug("error: " + temp);
			}
			if(temp.contains(" as ")) {
				int asIndex = temp.indexOf(" as ");
				aliasName = temp.substring(asIndex + 4);
			}
		}
		return aliasName;
	}
		
}
