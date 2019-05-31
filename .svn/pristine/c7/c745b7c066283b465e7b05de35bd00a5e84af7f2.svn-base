/**
 * 
 */
package com.talentPool.demo;

import java.io.File;

import com.talentPool.parser.converter.WordToHtmlConverter;

/**
 * @author Ajeet
 *
 */
public class convertDocToHTml {

	public static File folder = new File("D:\\Projects\\talentpool\\Branches\\v_3_9_0\\documents\\resume");
	
	/**
	 * @param to generate HTML files for all document
	 */
	public static void main(String[] args) {
		WordToHtmlConverter converter = new WordToHtmlConverter();
		File[] listOfFiles = folder.listFiles();
		for (int i = 0; i < listOfFiles.length; i++) {
			converter.convertToHtml(listOfFiles[i].getAbsolutePath());
		}
	}

}
