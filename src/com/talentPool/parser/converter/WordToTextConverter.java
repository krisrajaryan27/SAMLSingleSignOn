package com.talentPool.parser.converter;

import java.io.FileInputStream;

import java.io.InputStream;

import org.apache.poi.POITextExtractor;
import org.apache.poi.extractor.ExtractorFactory;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.properties.GlobalConstants;

public class WordToTextConverter extends AbstractConverter {
	public String convert(String filePath) {
		String result = "";
		try {
			InputStream inputStream = new FileInputStream(filePath);
			POITextExtractor extractor = ExtractorFactory.createExtractor(inputStream);
			result = extractor.getText();
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return result;
	}
}