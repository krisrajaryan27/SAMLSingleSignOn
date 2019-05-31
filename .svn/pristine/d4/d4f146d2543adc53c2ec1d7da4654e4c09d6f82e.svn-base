package com.talentPool.repository;

import java.util.HashMap;
import java.util.Map;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.core.KeywordAnalyzer;
import org.apache.lucene.analysis.miscellaneous.PerFieldAnalyzerWrapper;
import org.apache.lucene.analysis.standard.StandardAnalyzer;

/**
 * @author SumeetS
 *
 */
public class TpCustomAnalyzer{
	private static Map<String,Analyzer> analyzerPerField = new HashMap<String,Analyzer>();
	private static PerFieldAnalyzerWrapper perfieldAnaylzer;
	 static {
		 analyzerPerField.put(TPDocument.EMAIL_FIELD, new KeywordAnalyzer());
		 perfieldAnaylzer=new PerFieldAnalyzerWrapper(new StandardAnalyzer(), analyzerPerField);
	}
	
	 /**
	 * @return Custom Analyzer
	 */
	public static Analyzer getAnalyzer(){
		return perfieldAnaylzer;
	 }
	 
}
