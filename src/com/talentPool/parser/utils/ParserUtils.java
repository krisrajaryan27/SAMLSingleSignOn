package com.talentPool.parser.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Properties;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.TieredMergePolicy;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.index.MergePolicy;
import org.apache.lucene.index.TieredMergePolicy;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.NativeFSLockFactory;
import org.apache.lucene.util.Version;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.properties.TPApplicationProperties;
import com.talentPool.common.utils.Utils;
import com.talentPool.parser.MonsterParser;
import com.talentPool.parser.NaukriParser;
import com.talentPool.parser.TechFetchParser;
import com.talentPool.parser.DiceParser;
import com.talentPool.parser.TimesjobsParser;
import com.talentPool.parser.dataobject.ParsedResultData;
import com.talentPool.repository.RepositoryConstants;
import com.talentPool.repository.TpCustomAnalyzer;

public class ParserUtils {
	private static Properties _naukriProperties = null;
	
	private static Properties _monsterProperties = null;
	
	private static Properties _timejobsProperties = null;
	private static Properties _monsterUsProperties=null;
	private static Properties _diceProperties=null;
	private static Properties _techFetchProperties=null;
	/**
	 * Method used to dynamically change the properties of NaukriParser
	 */
	public static void loadNaukriProperties() {
		if(_naukriProperties==null){
			try {
				String basePath 		= Utils.concatFilePath(TPApplicationProperties.getProperty("application.path"), TPApplicationProperties.getProperty("ui.dir"));
				String naukriFilePath	= Utils.concatFilePath(basePath, TPApplicationProperties.getProperty("parser.file.naukri"));
				File naukriFile 		= new File(naukriFilePath); 
				if(naukriFile.exists() && naukriFile.isFile()) {
					_naukriProperties = new Properties();
					_naukriProperties.load(new FileInputStream(naukriFilePath));
					Utils.loadStaticVariables(NaukriParser.class, _naukriProperties);
				}else {
					TPLogger.getLogger().error("Could Not locate the file having properties of NaukriParser");
				}
			} catch (Exception e) {
				TPLogger.getLogger().error("Error in loading Nakri Properties", e);
			}
		}
	}
	/**
	 * Method used to dynamically change the properties of MonsterParser
	 */
	public static void loadMonsterProperties() {
		if(_monsterProperties==null){
			try {
				String basePath 		= Utils.concatFilePath(TPApplicationProperties.getProperty("application.path"), TPApplicationProperties.getProperty("ui.dir"));
				String monsterFilePath 	= Utils.concatFilePath(basePath, TPApplicationProperties.getProperty("parser.file.monster"));
				File monsterFile 		= new File(monsterFilePath);
				if(monsterFile.exists() && monsterFile.isFile()) {
					_monsterProperties = new Properties();
					_monsterProperties.load(new FileInputStream(monsterFilePath));
					Utils.loadStaticVariables(MonsterParser.class, _monsterProperties);
				}else {
					TPLogger.getLogger().error("Could Not locate the file having properties of MonsterParser");
				}
			} catch (Exception e) {
				TPLogger.getLogger().error("Error in loading Monster Properties", e);
			}
		}
	}
	/**
	 * Method used to dynamically change the properties of TimesjobsParser
	 */
	public static void loadTimesJobsProperties() {
		if(_timejobsProperties==null){
			try {
				String basePath 		 = Utils.concatFilePath(TPApplicationProperties.getProperty("application.path"), TPApplicationProperties.getProperty("ui.dir"));
				String timesJobsFilePath = Utils.concatFilePath(basePath, TPApplicationProperties.getProperty("parser.file.timesjobs"));
				File timesJobsFile		 = new File(timesJobsFilePath);		
				if(timesJobsFile.exists() && timesJobsFile.isFile()){
					_timejobsProperties = new Properties();
					_timejobsProperties.load(new FileInputStream(timesJobsFilePath));
					Utils.loadStaticVariables(TimesjobsParser.class, _timejobsProperties);	
				}else {
					TPLogger.getLogger().error("Could Not locate the file having properties of TimesjobsParser");
				}
			} catch (Exception e) {
				TPLogger.getLogger().error("Error in loading TimesJobs Properties", e);
			}
		}
	}
	
	/**
	 * Method used to dynamically change the properties of TechFetchParser
	 */
	public static void loadTechFetchProperties() {
		if(_techFetchProperties==null){
			try {
				String basePath 		= Utils.concatFilePath(TPApplicationProperties.getProperty("application.path"), TPApplicationProperties.getProperty("ui.dir"));
				String techFetchFilePath	= Utils.concatFilePath(basePath, TPApplicationProperties.getProperty("parser.file.techFetch"));
				File techFetchFile 		= new File(techFetchFilePath); 
				if(techFetchFile.exists() && techFetchFile.isFile()) {
					_techFetchProperties = new Properties();
					_techFetchProperties.load(new FileInputStream(techFetchFilePath));
					Utils.loadStaticVariables(TechFetchParser.class, _techFetchProperties);
				}else {
					TPLogger.getLogger().error("Could Not locate the file having properties of TechFetchParser");
				}
			} catch (Exception e) {
				TPLogger.getLogger().error("Error in loading TechFetch Properties", e);
			}
		}
	}
	
	/**
	 * Method used to dynamically change the properties of MonsterUSParser
	 */
	public static void loadMonsterUSProperties() {
		if(_monsterUsProperties==null){
			try {
				String basePath 		= Utils.concatFilePath(TPApplicationProperties.getProperty("application.path"), TPApplicationProperties.getProperty("ui.dir"));
				String monsterUSFilePath	= Utils.concatFilePath(basePath, TPApplicationProperties.getProperty("parser.file.monsterUS"));
				File monsterUSFile 		= new File(monsterUSFilePath); 
				if(monsterUSFile.exists() && monsterUSFile.isFile()) {
					_monsterUsProperties = new Properties();
					_monsterUsProperties.load(new FileInputStream(monsterUSFilePath));
					Utils.loadStaticVariables(TechFetchParser.class, _monsterUsProperties);
				}else {
					TPLogger.getLogger().error("Could Not locate the file having properties of MonsterUSParser");
				}
			} catch (Exception e) {
				TPLogger.getLogger().error("Error in loading MonsterUS Properties", e);
			}
		}
	}
	/**
	 * Method used to dynamically change the properties of DiceParser
	 */
	public static void loadDiceProperties() {
		if(_techFetchProperties==null){
			try {
				String basePath 		= Utils.concatFilePath(TPApplicationProperties.getProperty("application.path"), TPApplicationProperties.getProperty("ui.dir"));
				String diceFilePath	= Utils.concatFilePath(basePath, TPApplicationProperties.getProperty("parser.file.dice"));
				File diceFile 		= new File(diceFilePath); 
				if(diceFile.exists() && diceFile.isFile()) {
					_diceProperties = new Properties();
					_diceProperties.load(new FileInputStream(diceFilePath));
					Utils.loadStaticVariables(DiceParser.class, _diceProperties);
				}else {
					TPLogger.getLogger().error("Could Not locate the file having properties of DiceParser");
				}
			} catch (Exception e) {
				TPLogger.getLogger().error("Error in loading Dice Properties", e);
			}
		}
	}
	public static String getPipeSeparatedString(String strToCheck) {
		String[] str = strToCheck.trim().split("\\s+");
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < str.length; i++) {
			sb.append(str[i] + "|");
		}
		String strPipeSeparated = sb.toString();
		if (sb.indexOf("|") >= 0) {
			strPipeSeparated = sb.substring(0, sb.length() - 1);
		}
		return strPipeSeparated;

	}

	public static ArrayList removeShortMatches(ArrayList names, int maxlength) {
		for (int i = 0; i < names.size(); i++) {
			ParsedResultData pRData = (ParsedResultData) names.get(i);
			String matchedName = pRData.getMatchedString().replaceAll("\\.", " ");
			matchedName = matchedName.replaceAll("\\b[a-zA-Z]\\b", "");
			if (matchedName.length() < maxlength) {
				names.remove(i);
				i = i - 1;
			}
		}
		return names;
	}

	public static ArrayList removeDuplicateMatches(ArrayList results) {
		try {
			if (results != null) {
				for (int i = 0; i < results.size() - 1; i++) {
					ParsedResultData pRData = (ParsedResultData) results.get(i);
					for (int k = i + 1; k < results.size(); k++) {
						ParsedResultData pRData2 = (ParsedResultData) results.get(k);
						if (pRData.getMatchedString().trim().equalsIgnoreCase(pRData2.getMatchedString().trim())) {
							results.remove(k);
						}
					}

				}
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while removing duplicate matches", e);
		}
		return results;
	}

	public static String escapeCharacters(String strToEscape) {
		strToEscape = strToEscape.replaceAll("\\.", "\\\\\\.");
		return strToEscape;
	}

	public static String getTokenizedString(String content) {
		if (Utils.isBlankOrNull(content))
			return content;
		StringBuffer sb = new StringBuffer();
		try {
			StandardAnalyzer analyzer = new StandardAnalyzer();
			TokenStream stream = analyzer.tokenStream("", new StringReader(content));
			CharTermAttribute charAttr = stream.addAttribute(CharTermAttribute.class);
			stream.reset();
			while (stream.incrementToken()) {
				sb.append(charAttr.toString() + " ");
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error",e);
			sb.append(content);
		}
		return sb.toString().trim();
	}

	public static String getMatchFromList(String content, ArrayList names) {
		try {
			for (int i = 0; i < names.size(); i++) {
				String src = (String) names.get(i);
				String tkSrc = ParserUtils.getTokenizedString(src);
				if (tkSrc.equals(content)) {
					return src;
				}
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error ", e);
		}
		return "";
	}

	public static String findIndexOfShorter(String content, ArrayList names) {
		try {
			for (int i = 0; i < names.size(); i++) {
				String src = (String) names.get(i);
				String tkSrc = ParserUtils.getTokenizedString(src);
				String shorter = tkSrc;
				String longer = content;
				if (content.length() < tkSrc.length()) {
					shorter = content;
					longer = tkSrc;
				}
				if (longer.indexOf(shorter) >= 0) {
					return src;
				}
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error ", e);
		}
		return "";
	}

	public static String getIdForName(String src, ArrayList names, ArrayList ids) {
		try {
			int idx = names.indexOf(src);
			if (idx >= 0) {
				return (String) ids.get(idx);
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error",e);
		}
		return null;

	}

	public static String getREConstructed(String regExp) {
		regExp = regExp.replaceAll("[+]", "\\\\+");
		regExp = regExp.replaceAll("[\\s]+", "[\\\\s]+");
		regExp = "(^|[\\s,\\-:;\"'()/])" + regExp + "([()\\s,\\-:;\"'/]|$)";
		return regExp;
	}
	
	private static IndexWriter idxWriter = null;
	/**
	 * @return IndexWriter 
	 * @throws Exception
	 */
	public static IndexWriter getIndexWriter() throws Exception{
		IndexWriterConfig idc = new IndexWriterConfig(Version.LATEST, TpCustomAnalyzer.getAnalyzer()).setOpenMode(OpenMode.CREATE_OR_APPEND);
		idc.setRAMBufferSizeMB(40);
		idc.setMergePolicy(new TieredMergePolicy().setSegmentsPerTier(4).setMaxMergeAtOnce(3));
		String repositoryPath = RepositoryConstants.DEFAULT_REPOSITORY_PATH;
		try {
			File f = new File(repositoryPath);
			if(idxWriter==null){
				idxWriter = new IndexWriter(FSDirectory.open(f,new NativeFSLockFactory(f)),idc);
			}
			return idxWriter;
		} catch (IOException e) {
			throw e;
		}
	}
	
	public static void resetIndexWriter(){
		idxWriter = null;
	}

}
