package com.talentPool.parser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.talentPool.applicant.dataobject.EducationalData;
import com.talentPool.applicant.dataobject.EmploymentHistoryData;
import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.properties.GlobalApplicationProperties;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.utils.CommonUtils;
import com.talentPool.common.utils.RegexUtils;
import com.talentPool.common.utils.Utils;
import com.talentPool.parser.converter.HTMLToPlainTextConverter;
import com.talentPool.parser.utils.ParserUtils;

public class MonsterParser implements Parser{
	private String sourceContent;
	private String textContent;
	private HashMap resultMap;
	public static final String part1 = "(?mids)(Phone:.*)";
	public static final String part2 = "(?mids)(Email:.*)";
	public static final String part3 = "(?mids)(Contact Information.*)";
	public static final String part31 = "(?mids)(Last Modified:.*)";
	public static final String part4 = "Current Location:";
	public static final String part5 = "(?mids)(Gender:.*)";
	public static final String part6 = "(?mids)(Current Annual Salary.*)";
	public static final String part7 = "(?mids)(Work Experience.*)";
	public static final String part8 = "(?mids)(Key Skills:.*)";
	public static final String part9 = "(?mids)(Current Employer.*)";
	public static final String part10 = "(?mids)(Current Location:.*)";
	public static final String part11 = "(?mids)(Mobile.*)";
	public static final String part12 = "(?mids)(Highest Degree Held.*)";
	public static final String part13 = "2nd Highest Degree Held";
	public static final String part14 = "Preferred Job Location";
	public static final String part15 = "monster";
	public static final String part16 = "5iantlavalamp";
	public static final String part17 = "(?mids)(.*?2nd Highest Degree Held)";
	public static final String part18 = "(?mids)(Previous Employer.*)";
	private int resumeType;
	private static final String monsterFormatexp = "(?mids)(Source Monster.*)(Highest Education.*)(Authorization US.*)(US Military Service.*)";
	private static final String regexEmail = "(?mids)[a-z0-9.\\-_]+@([a-z0-9\\-_]+\\.)+(" + "com|net|org|edu|int|mil|gov|arpa|biz|" + "aero|name|coop|info|pro|museum|tv|([a-z]{2}))";
	private static final String regexMobile="[\\s](\\({0,1}\\d{3}\\){0,1}" +
			"[- \\.]\\d{3}[- \\.]\\d{4})|" +
			"(\\+\\d{2}-\\d{2,4}-\\d{3,4}-\\d{3,4})";
	public static final String part19 = "(?mids)(View candidate(.+?)PrintÂ )";
	public static final String part20 = "(?mids)(PrintÂ.*)";
	public MonsterParser(String sourceContent, String textContent) {
		this.sourceContent = sourceContent;
		this.textContent = textContent;
		ParserUtils.loadMonsterProperties();
	}
	public MonsterParser(String textContent) {
		this.textContent = textContent;
	}
	/**
	 * @return the resumeType
	 */
	public int getResumeType() {
		return resumeType;
	}

	/**
	 * @param resumeType the resumeType to set
	 */
	public void setResumeType(int resumeType) {
		this.resumeType = resumeType;
	}
	
	
	public int isFormat() {
		try {
			if(isFormatMatched(monsterFormatexp)&&(sourceContent.contains(part15)) ){
				return ParserConstants.US_MONSTER;
			}else if(sourceContent.contains(part15)) {
				return ParserConstants.MONSTER_IND;
			}		
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		
		return ParserConstants.NON_MONSTER_RESUME;
	}

	private boolean isFormatMatched(String exp) {
		boolean isFormatMatched = false;
		Pattern p = Pattern.compile(exp);
		boolean theEnd = false;
		if (!Utils.isBlankOrNull(textContent)) {					
			Matcher m = p.matcher(textContent);
			if (m != null) {						
				while (!theEnd) {
					theEnd = !m.find();
					if (!theEnd) {
						TPLogger.getLogger().debug("Naukri format found");
						isFormatMatched = true;
						break;
					}
				}
			}
		}
		return isFormatMatched;
	}
	
	public HashMap getParsedMap() {
		if(getResumeType() == ParserConstants.MONSTER_IND) {
			parse();
		}else if(getResumeType() == ParserConstants.US_MONSTER){
			parseUSMonster();
		} 
		
		return resultMap;
	}
	
	public void parsePhone(){
		ArrayList<String> phones = getSingleParseValueOnSameLine(part1); // Home phone number
		ArrayList<String> mobile = getSingleParseValueOnSameLine(part11);  // mobile number
		try{	
			ArrayList<String> phoneNumbers= new ArrayList<String>();
			if (mobile.size()>0 && !Utils.isBlankOrNull(mobile.get(0)) && !(mobile.get(0)).contains("Not specified")  && !(mobile.get(0)).contains("Confidential")){
				phoneNumbers.add(Utils.removeExtendedAsciiChars(mobile.get(0)));
			}
			if (phones.size()>0 && !Utils.isBlankOrNull(phones.get(0)) && !(phones.get(0)).contains("Not specified")  && !(mobile.get(0)).contains("Confidential")){
				phoneNumbers.add(Utils.removeExtendedAsciiChars(phones.get(0)));
			}
			resultMap.put(ParserConstants.FIELD_PHONE, phoneNumbers);
		}catch(Exception e) {
			TPLogger.getLogger().error("Error", e);
		}
	}
	//parse US monster begin
	public void parseUSMonster() {
		resultMap = new HashMap();
		try{
			ArrayList<String> emails = parseApplicantEmail();			
			parseEmail(emails);
			ArrayList phones = parseMobile();//done
			if(phones!=null){
			parsePhone(phones);
			}
			parseUSApplicantName();
			parseApplicantNewCurrentLocation();
			parseSource();
			parseSkills();
			parseEducation();
			parseEmploymentHistory();
			parseCurrentCTC();
			parseWorkinSince();
		} catch (Exception e) {
			TPLogger.getLogger().error("Error", e);
		}
	}
	private void parseApplicantNewCurrentLocation() {
		ArrayList<String> currentLocationArray = new ArrayList<String>();
		String currentLocation = "";
		String result="";
		ArrayList<String> loc=new ArrayList<String>();
		try {
			if(Utils.isBlankOrNull(currentLocation)){
				String lineStr= textContent.substring(textContent.lastIndexOf("View candidate"),textContent.lastIndexOf("Rating"));
				Scanner scanner = new Scanner(lineStr);
				while(scanner.hasNextLine()) {	
					
					currentLocation = scanner.nextLine();
					currentLocation = scanner.nextLine();
					if(!Utils.isBlankOrNull(currentLocation)){
						loc.add(currentLocation);
						//currentLocation=currentLocation.trim();
						//break;
						if(currentLocation.equalsIgnoreCase("Rating")){
							break;
						}
					}
					
						
				}
				if(loc!=null &&loc.size()>=3){
				currentLocation=loc.get(2);
				}
				currentLocationArray.add(currentLocation.trim());
			}
			
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		resultMap.put(ParserConstants.FIELD_LOCATION, currentLocationArray);
	}
	
	private void parseUSApplicantName() {
		ArrayList<String> nameArray = new ArrayList<String>();
		String applicantName = null;
		
		Matcher m = RegexUtils.getMatcher(textContent, part20);
		if(Utils.isBlankOrNull(applicantName)) {
			String lineStr= textContent.substring(textContent.lastIndexOf("View candidate"),textContent.lastIndexOf("Print"));
			Scanner scanner = new Scanner(lineStr);
			while(scanner.hasNextLine()) {	
				
				applicantName = scanner.nextLine();
				applicantName = scanner.nextLine();
				if(!Utils.isBlankOrNull(applicantName)){
					
					applicantName=applicantName.trim();
					break;
				}
					if(Utils.isBlankOrNull(applicantName)){
						applicantName = scanner.nextLine();
						if(!Utils.isBlankOrNull(applicantName)){
							applicantName=applicantName;
							break;
						}
					}
					
			}	
			if(!Utils.isBlankOrNull(applicantName)){
			
				applicantName=applicantName.trim();
			}
		} 
		
	
	
		if(Utils.isBlankOrNull(applicantName)) {
			Scanner scanner = new Scanner(textContent);
			String value = null;
			while(scanner.hasNextLine()) {
				value = scanner.nextLine();				
				if(value.indexOf("@") > 0) {
					value = scanner.nextLine();
					
					break;
				}
			}
			while(Utils.isBlankOrNull(Utils.removeExtendedAsciiChars(value)) && scanner.hasNextLine()) {
				value = scanner.nextLine();				
			}
			applicantName = value;
		}
		
		if(!Utils.isBlankOrNull(applicantName)) {
			nameArray.add(applicantName.trim());
		}
		resultMap.put(ParserConstants.FIELD_NAME, nameArray);
	}
	
	private ArrayList<String> parseMobile() {
		ArrayList<String> phones = new ArrayList<String>();
		Matcher matcherPhone = RegexUtils.getMatcher(textContent, "regexMobile");		
		while(matcherPhone.find()) {
			phones.add(matcherPhone.group().trim());
		}	
		return phones;
	}
	
	public void parsePhone(ArrayList phones) {		
		ArrayList<String> phoneNumbers = new ArrayList<String>();
		try {
			if (phones.size() > 0 && !Utils.isBlankOrNull((String) phones.get(0))) {
				String mobileNo = (String) phones.get(0);
				phoneNumbers.add(mobileNo.trim());
			}
			
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		resultMap.put(ParserConstants.FIELD_PHONE, phoneNumbers);
	}
	
	private ArrayList<String> parseApplicantEmail() {
		ArrayList<String> emails = new ArrayList<String>();
		Matcher matcherEmail = RegexUtils.getMatcher(textContent, regexEmail);		
		while(matcherEmail.find()) {
			emails.add(matcherEmail.group().trim());
		}	
		return emails;
	}
	
	public void parseEmail(ArrayList<String> email) {
		try {
			if (email.size() > 0 && !Utils.isBlankOrNull((String) email.get(0))) {
				resultMap.put(ParserConstants.FIELD_EMAIL, email);
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
	}
	//end of US Monster parser
	public void parseSource(){
		try{
			String sourceId = "";
			ArrayList<String> sourceIds = CommonUtils.getSourceIds();
			ArrayList<String> sourceNames = CommonUtils.getSourceNames();
			int idx =0;
			for(int i=0 ; i < sourceNames.size();i++){
				String name = sourceNames.get(i);
				name = name.toLowerCase();
				if(name.contains(part15)){
					idx=i;
					break;
				}
			}		
			if (idx >= 0) {
				sourceId= (String) sourceIds.get(idx);
			}
			if(sourceId!=null){
				ArrayList<String> src = new ArrayList<String>();
				src.add(0, sourceId);
				src.add(1, sourceNames.get(idx));
				resultMap.put(ParserConstants.FIELD_SOURCE, src);
			}
		}catch(Exception e) {
			TPLogger.getLogger().error("Error", e);
		}
	}
	
	public void parseSkills(){
		try{
			SkillsParser skillsParser = new SkillsParser();
			int noOfSkiils = Integer.parseInt(GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_MAX_SKILLS_PARSED).trim());
			ArrayList<String> skills = skillsParser.getArrayListOfParsedSkillIdsAndNames(textContent, noOfSkiils);
			resultMap.put(ParserConstants.FIELD_SKILLS, skills);
		}catch(Exception e) {
			TPLogger.getLogger().error("Error", e);
		}
	}
	
	public void parseEducation(){
		String eduParagraph = getEducationPara(part12);
		String education[] = eduParagraph.split(part13);
		ArrayList<EducationalData> allEducations = new ArrayList<EducationalData>();
		try{
			String educationStr="";
			for(int i =0; i <education.length;i++){
				educationStr=education[i];
				EducationParser educationParser = new EducationParser();
				EducationalData eData = educationParser.getEducationParsedFromMaster(educationStr);
				String institute=eData.getInstitute();
				if(Utils.isBlankOrNull(institute)){ //Setting the institue from after second comma
					String[] eduStr= educationStr.split(",");
					if(eduStr.length>2){
						institute=eduStr[2].trim();
					}else {
						institute="";
					}
					eData.setInstitute(institute);
				}
				allEducations.add(eData);
			}
		}catch(Exception e) {
			TPLogger.getLogger().error("Error", e);
		}
		resultMap.put(ParserConstants.FIELD_EDUCATION, allEducations);
	}
	
	public void parseCurrentCTC(){
		ArrayList<String> currentCTC = getSingleParseValue(part6);
		try{
			if (currentCTC.size()>0 && !Utils.isBlankOrNull(currentCTC.get(0)) && !(currentCTC.get(0)).contains("Not specified")){				
				String ctc = currentCTC.get(0);
				if(ctc.startsWith(":")) {
					String[] parts = ctc.split(":");
					ctc = parts[1].trim();
				}
				String[] ctcStr = ctc.split(" ");
				if(ctcStr.length>0){
					ctc=ctcStr[0].trim();
					if(ctc.equals("0.0") || ctc.equals("0")) {
						ctc="";
					}
					try {
						Double.parseDouble(ctc);
					} catch (NumberFormatException e) {
						ctc = "";
					}
					resultMap.put(ParserConstants.FIELD_CURRENT_CTC,ctc);
				}
			}
		}catch(Exception e) {
			TPLogger.getLogger().error("Error", e);
		}
	}
	
	public void parseWorkinSince(){
		ArrayList<String> totalExp 	 = getSingleParseValue(part7);		
		//mm/yyyy or mmm-yyyy
		try{
			if (totalExp.size()>0 && !Utils.isBlankOrNull((String)totalExp.get(0)) && !((String)totalExp.get(0)).contains("Not specified")){
				String totalExpStr=  (String) totalExp.get(0);
				String[] totalExperience = totalExpStr.split(" ");
				int yr =0 , mnth = 0;
				if(totalExperience.length>3) {
					try {
						yr = Integer.parseInt(totalExperience[1].trim());
						mnth = Integer.parseInt(totalExperience[3].trim());
					} catch (NumberFormatException e) {
								
					}		
				} else if(totalExperience.length>0) {
					try {
						if("years".equals(totalExperience[2])) {
							yr = Integer.parseInt(totalExperience[1].trim());
						} else {
							mnth = Integer.parseInt(totalExperience[1].trim());
						}
						
					} catch (NumberFormatException e) {
					}				
				}	
				int numberOfUnits = -yr*12-mnth;
				if(numberOfUnits<0){
					totalExpStr=yr+"."+mnth;
					resultMap.put(ParserConstants.FIELD_WORKING_SINCE, totalExpStr);
				}
			}
		}catch(Exception e) {
			TPLogger.getLogger().error("Error", e);
		}
	}
		
	public void parseApplicantName(){
		ArrayList<String> name 		= getSingleParseValue(part3);
		if(name.size() == 0) {
			name 		= getSingleParseValueOnPreviousLine(part31, part4);
		}
		try{
			if (name.size()>0 && !Utils.isBlankOrNull((String)name.get(0)) && !((String)name.get(0)).contains("Not specified")  && !((String)name.get(0)).contains("Confidential")){
				resultMap.put(ParserConstants.FIELD_NAME, name);
			}
		}
		catch(Exception e) {
			TPLogger.getLogger().error("Error", e);
		}
	}
	
	public void parseOthers(){
		ArrayList<String> email 	= getSingleParseValueOnSameLine(part2);
		ArrayList<String> name 		= getSingleParseValue(part3);
		if(name.size() == 0) {
			name 		= getSingleParseValueOnPreviousLine(part31, part4);
		}
		ArrayList<String> curentEmp = getSingleParseValue(part9);
		if(curentEmp != null && curentEmp.size() > 0) {
			String currentEmployer = (String) curentEmp.get(0);
			if(currentEmployer.startsWith(":")) {
				String[] parts = currentEmployer.split(":");
				curentEmp.set(0, parts[1].trim());
			}
		}
		ArrayList<String> location  = getSingleParseValueOnSameLine(part10);
		
		try{
			if (email.size()>0 && !Utils.isBlankOrNull((String)email.get(0)) && !((String)email.get(0)).contains("Not specified") && !((String)email.get(0)).contains("Confidential")){
				email.set(0, Utils.removeExtendedAsciiChars((String)email.get(0)));
				resultMap.put(ParserConstants.FIELD_EMAIL, email);
			}
			if (name.size()>0 && !Utils.isBlankOrNull((String)name.get(0)) && !((String)name.get(0)).contains("Not specified")  && !((String)name.get(0)).contains("Confidential")){
				resultMap.put(ParserConstants.FIELD_NAME, name);
			}
			if (curentEmp.size()>0 && !Utils.isBlankOrNull((String)curentEmp.get(0)) && !((String)curentEmp.get(0)).contains("Not specified")  && !((String)curentEmp.get(0)).contains("Confidential")){
				resultMap.put(ParserConstants.FIELD_CURRENT_EMPLOYER, curentEmp);
			}
			if (location.size()>0 && !Utils.isBlankOrNull((String)location.get(0)) && !((String)location.get(0)).contains("Not specified")  && !((String)location.get(0)).contains("Confidential")){
				location.set(0, Utils.removeExtendedAsciiChars((String)location.get(0)));
				resultMap.put(ParserConstants.FIELD_LOCATION, location);
			}
		}catch(Exception e) {
			TPLogger.getLogger().error("Error", e);
		}
	}
	
	public void parse() {
		resultMap = new HashMap();
		try{
			parsePhone();
			parseSource();
			parseSkills();
			parseEducation();
			parseEmploymentHistory();
			parseCurrentCTC();
			parseWorkinSince();
			parseOthers();
			
		} catch (Exception e) {
			TPLogger.getLogger().error("Error", e);
		}
	}
	
	private ArrayList<String> getSingleParseValue(String exp) {
		ArrayList<String> result = new ArrayList<String>();
		String lineStr="";
		try {
			Matcher m = RegexUtils.getMatcher(textContent, exp);
			if (m != null && m.find()) {
				lineStr=textContent.substring(m.start(), m.end());
				Scanner scanner = new Scanner(lineStr);
				scanner.nextLine(); //To remove Name line							
				String value = scanner.nextLine();
				while(Utils.isBlankOrNull(Utils.removeExtendedAsciiChars(value))) {
					value = scanner.nextLine();
				}
				result.add(value.trim());
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error", e);
		}
		return result;
	}
	
	private ArrayList<String> getSingleParseValueOnSameLine(String exp) {
		ArrayList<String> result = new ArrayList<String>();
		String lineStr="";		
		try {
			Matcher m = RegexUtils.getMatcher(textContent, exp);
			if (m != null && m.find()) {
				lineStr=textContent.substring(m.start(), m.end());
				Scanner scanner = new Scanner(lineStr);										
				String value = scanner.nextLine();
				if(Utils.isBlankOrNull(value)) {
					value = scanner.nextLine();					
				}
				value = value.substring(value.indexOf(":")+2, value.length()).trim();
				result.add(value.trim());
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error", e);
		}
		return result;
	}
	
	private ArrayList<String> getSingleParseValueOnPreviousLine(String exp, String exp2) {
		ArrayList<String> result = new ArrayList<String>();
		String lineStr="";
		try {
			Matcher m = RegexUtils.getMatcher(textContent, exp);
			if (m != null && m.find()) {
				lineStr=textContent.substring(m.start(), m.end());
				Scanner scanner = new Scanner(lineStr);
				scanner.nextLine(); //To remove Name line							
				String value = scanner.nextLine();
				while(Utils.isBlankOrNull(Utils.removeExtendedAsciiChars(value))) {
					value = scanner.nextLine();
				}
				result.add(value.trim());
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error", e);
		}
		return result;
	}
	
	public String getEducationPara(String exp) {
		String result = "";
		String eduPara="";
		try {
			Matcher m = RegexUtils.getMatcher(textContent, exp);
			if (m != null && m.find()) {
				result = textContent.substring(m.start(), m.end());
				Scanner scanner = new Scanner(result);
				scanner.nextLine(); // Remove Edu name line
				while(scanner.hasNextLine()){
					String nextLine = scanner.nextLine();
					if(nextLine.contains(part14))
						break;
					eduPara+=nextLine;
				}
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error", e);
		}
		return eduPara;
	}
	
	public void parseEmploymentHistory(){
		ArrayList<String> previousEmployer = getSingleParseValue(part18);
		ArrayList<String> curentEmp = getSingleParseValue(part9);
		ArrayList<EmploymentHistoryData> allEmployHistory = new ArrayList<EmploymentHistoryData>();
		EmploymentHistoryData ehd = null; 
		try{
			if(previousEmployer!=null && previousEmployer.size()>0){
				String empLst = previousEmployer.get(0);
				if(empLst.startsWith(":")){
					String[] commaSepLst  = empLst.split(":");
					String[] prevEmplys = commaSepLst[1].split(",");
					for(String strEmp:prevEmplys){
						ehd = new EmploymentHistoryData();
						ehd.setEmployerName(strEmp);
						allEmployHistory.add(ehd);
					}
				}
			}
			if(curentEmp != null && curentEmp.size() > 0) {
				String currentEmployer = (String) curentEmp.get(0);
				if(currentEmployer.startsWith(":")) {
					String[] parts = currentEmployer.split(":");
					//curentEmp.set(0, parts[1].trim());
					ehd = new EmploymentHistoryData();
					ehd.setEmployerName(parts[1].trim());
					allEmployHistory.add(ehd);
				}
			}			
			Collections.reverse(allEmployHistory);			
		}catch (Exception e) {
			TPLogger.getLogger().debug(GlobalConstants.ERROR,e);
		}
		resultMap.put(ParserConstants.FIELD_EMPLOYMENT_HISTORY, allEmployHistory);
	}
}
