package com.talentPool.parser.converter.test;

import java.io.File;
import java.io.FileWriter;

import com.talentPool.parser.converter.GenericConverter;

/**
 * @author shivprasad
 * 
 * converts all files in the folder to txt files
 */
public class TestConverter {
	public static void main(String[] args) {
		File srcFile = new File("C:\\distinct");
		// File srcFile = new File("C:\\Temp\\NAUKRI\\DOC49309.html");

		if (srcFile.isDirectory()) {
			File[] listFiles = srcFile.listFiles();
			for (int i = 0; i < listFiles.length; i++) {
				File file = listFiles[i];

				GenericConverter conv = new GenericConverter();
				String content = conv.convert(file.getAbsolutePath());
				writeTEXTFile(content, file.getAbsolutePath());

			}
		}

	}

	public static void writeTEXTFile(String content, String filePath) {
		try {
			int dotindex = filePath.indexOf(".");
			String destinationFile = filePath.substring(0, dotindex) + ".txt";

			FileWriter out = new FileWriter(destinationFile);
			out.write(content);
			out.close();

		} catch (Exception e) {

		}

	}

}
