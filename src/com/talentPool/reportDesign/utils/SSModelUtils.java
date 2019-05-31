package com.talentPool.reportDesign.utils;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class SSModelUtils {

	public static Workbook createWorkBookInstance(String filePath) {
		try {
			if(filePath.endsWith("xlsx")){
				return new XSSFWorkbook(filePath);	
			}else{
				return new HSSFWorkbook(new POIFSFileSystem(new FileInputStream(filePath))); 
			}
		} catch (IOException e) {
			return null;
		}
	}
}
