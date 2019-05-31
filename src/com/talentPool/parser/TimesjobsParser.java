/**
 * 
 */
package com.talentPool.parser;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;

import com.talentPool.applicant.dataobject.EducationalData;
import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.properties.GlobalApplicationProperties;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.utils.CommonUtils;
import com.talentPool.common.utils.RegexUtils;
import com.talentPool.common.utils.Utils;
import com.talentPool.parser.utils.ParserUtils;

/**
 * @author pallavi
 *
 */
public class TimesjobsParser implements Parser {
	private String sourceContent;
	private String textContent;
	private HashMap resultMap;
	private static final String timesjobsFormatExp = "timesjobs";
	public static final String part1 = "(?mids)(Phone:.*)";
	public static final String part2 = "(?mids)(Mobile.*)";
	public static final String part4 = "(?mids)(Download Resume.*)";
	public static final String part5 = "(?mids)(Forward.*)";	
	public static final String part6 = "(?mids)(Current Employer:.*)";
	public static final String part7 = "(?mids)(Current Location:.*)";
	public static final String part8 = "(?mids)(Work Experience.*)";
	public static final String part9 = "(?mids)(Salary:.*)";
	public static final String part10 = "Rs.";
	public static final String part11 = "(?mids)(Current Location :.*)";
	public static final String part12 = "(?mids)(Times\\s*Job*)";
	public static final String part13 = "(?mids)(Current Employer.*)";	
	public static final String part14 = "(?mids)(Educational Information.*)";
	public static final String part15 = "Candidate Resume";
	public static final String part16 = "Highest Qualification";
	public static final String part17 = "2nd Highest";
	public static final String part18 = "Fresher";
	
	
	public TimesjobsParser(String sourceContent, String textContent) {
		this.sourceContent = sourceContent;
		this.textContent = textContent;
		ParserUtils.loadTimesJobsProperties();
	}

	public boolean isFormat() {
		if (sourceContent.contains(timesjobsFormatExp)) {
			return true;
		} else {
			return false;
		}
	}
	
	public HashMap getParsedMap() {
		parse();
		return resultMap;
	}
	
	public void parse() {
		resultMap = new HashMap();
		try{
			getParseNameAndEmail(part4);			
			parseOthers();
			parsePhone();
			parseSource();
			parseSkills();
			parseEducation();
			parseWorkingSince();
			parseCurrentCTC();
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
	}
	
	public void parseOthers(){	
		ArrayList<String> curentEmp = getSingleParseValueOnSameLine(part6);
		ArrayList<String> location  = getSingleParseValueOnSameLine(part7);
		
		try{
			if (curentEmp.size()>0 && !Utils.isBlankOrNull((String)curentEmp.get(0)) && !((String)curentEmp.get(0)).contains(part18)  && !((String)curentEmp.get(0)).contains("Confidential")){
				resultMap.put(ParserConstants.FIELD_CURRENT_EMPLOYER, curentEmp);
			}
			if (location.size()>0 && !Utils.isBlankOrNull((String)location.get(0)) && !((String)location.get(0)).contains("Not Specified")  && !((String)location.get(0)).contains("Confidential")){
				location.set(0, Utils.removeExtendedAsciiChars((String)location.get(0)));
				resultMap.put(ParserConstants.FIELD_LOCATION, location);
			}
		}catch(Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
	}
	
	public void parsePhone(){
		ArrayList<String> phones = getSingleParseValueOnSameLine(part1); // Home phone number
		ArrayList<String> mobile = getSingleParseValueOnSameLine(part2);  // mobile number
		try{	
			ArrayList<String> phoneNumbers= new ArrayList<String>();
			if (mobile.size()>0 && !Utils.isBlankOrNull((String)mobile.get(0)) && !((String)mobile.get(0)).contains("Not Specified")  && !((String)mobile.get(0)).contains("Confidential")){
				phoneNumbers.add(Utils.removeExtendedAsciiChars((String)mobile.get(0)));
			}
			if (phones.size()>0 && !Utils.isBlankOrNull((String)phones.get(0)) && !((String)phones.get(0)).contains("Not Specified")  && !((String)mobile.get(0)).contains("Confidential")){
				phoneNumbers.add(Utils.removeExtendedAsciiChars((String)phones.get(0)));
			}
			resultMap.put(ParserConstants.FIELD_PHONE, phoneNumbers);
		}catch(Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
	}
	
	public void parseSource(){
		try{
			String sourceId = "";
			ArrayList<String> sourceIds = CommonUtils.getSourceIds();
			ArrayList<String> sourceNames = CommonUtils.getSourceNames();
			int idx =0;
			for(int i=0 ; i < sourceNames.size();i++){
				String name = (String) sourceNames.get(i);
				name = name.toLowerCase();
				ArrayList<String> matches = RegexUtils.getMatches(name, part12);
				if(matches.size() > 0) {
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
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
	}
	
	public void parseCurrentLocation() {
		ArrayList<String> currentLocation = getSingleParseValueOnSameLine(part11);
		resultMap.put(ParserConstants.FIELD_LOCATION, currentLocation);
	}
	
	public void parseSkills(){
		try{
			SkillsParser skillsParser = new SkillsParser();
			int noOfSkiils = Integer.parseInt(GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_MAX_SKILLS_PARSED).trim());
			ArrayList<String> skills = skillsParser.getArrayListOfParsedSkillIdsAndNames(textContent, noOfSkiils);
			resultMap.put(ParserConstants.FIELD_SKILLS, skills);
		}catch(Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
	}
	
	
	
	public void parseWorkingSince(){
		ArrayList<String> totalExp 	 = getSingleParseValueOnSameLine(part8);		
		try{
			if (totalExp.size()>0 && !Utils.isBlankOrNull((String)totalExp.get(0)) && !((String)totalExp.get(0)).contains("Not Specified")){
				String totalExpStr=  (String) totalExp.get(0);
				String[] totalExperience = totalExpStr.split(" ");
				int yr =0 , mnth = 0;
				if(totalExperience.length>0) {
					yr = Integer.parseInt(totalExperience[0].trim());
				}					
				if(totalExperience.length>2) {
					mnth = Integer.parseInt(totalExperience[2].trim());
				}
				int numberOfUnits = -yr*12-mnth;
				int unit = Calendar.MONTH; //For month				
				if(numberOfUnits<0){
					Date date = new Date();
					Date wrkSince = Utils.adjustDateBy(date, unit, numberOfUnits);					
					totalExpStr=Utils.getDateConvertedToString(wrkSince, Utils.regMMMYYYYFormat);
					resultMap.put(ParserConstants.FIELD_WORKING_SINCE, totalExpStr);
				}
			}
		}catch(Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
	}
	
	public void parseCurrentEmployer() {
		List<String> currentEmp = getSingleParseValueOnSameLine(part13);
		resultMap.put(ParserConstants.FIELD_CURRENT_EMPLOYER, currentEmp);
	}
	
	public void parseCurrentCTC(){
		ArrayList<String> currentCTC = getSingleParseValueOnSameLine(part9);
		try{
			if (currentCTC.size()>0 && !Utils.isBlankOrNull((String)currentCTC.get(0)) && !((String)currentCTC.get(0)).contains("Not Specified")){
				String ctc = (String)currentCTC.get(0);
				String[] ctcStr = ctc.split(" ");
				if(ctcStr.length>0){
					if(ctcStr[0].indexOf(part10)>-1){
						ctc=ctcStr[1];
					}					
					resultMap.put(ParserConstants.FIELD_CURRENT_CTC,ctc);
				}
			}
		}catch(Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
	}
	
	private void getParseNameAndEmail(String exp) {
		ArrayList<String> name = new ArrayList<String>();
		ArrayList<String> email = new ArrayList<String>();
		String lineStr="";
		try {
			Matcher m = RegexUtils.getMatcher(textContent, exp);
			if (m != null && m.find()) {
				lineStr=textContent.substring(m.start(), m.end());
				Scanner scanner = new Scanner(lineStr);
				scanner.nextLine(); //To remove Name line							
				String value = scanner.nextLine();
				while(Utils.isBlankOrNull(value)) {
					value = scanner.nextLine();
				}
				name.add(value.trim());
				scanner.nextLine();
				String emailAdd = (String)scanner.nextLine().trim();
				if(Utils.isBlankOrNull(emailAdd)){
					emailAdd = (String)scanner.nextLine().trim();
				}
				email.add(emailAdd);
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		if (name.size()>0 && !Utils.isBlankOrNull((String)name.get(0)) && !((String)name.get(0)).contains("Not Specified")  && !((String)name.get(0)).contains("Confidential")){
			resultMap.put(ParserConstants.FIELD_NAME, name);
		}
		if (email.size()>0 && !Utils.isBlankOrNull((String)email.get(0)) && !((String)email.get(0)).contains("Not Specified")  && !((String)email.get(0)).contains("Confidential")){
			resultMap.put(ParserConstants.FIELD_EMAIL, email);
		}
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
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return result;
	}
	
	public void parseEducation(){		
		ArrayList<EducationalData> allEducations = new ArrayList<EducationalData>();
		try {
			String education = "";

			ArrayList<String> educationList = new ArrayList<String>();
			educationList = getEducationPara(part14);

			for (int i = 0; i < educationList.size(); i++) {
				EducationParser educationParser = new EducationParser();
				education = (String) educationList.get(i);
				EducationalData eData = educationParser.getEducationParsedFromMaster(education);
				
				if((i + 1) < educationList.size()) {
					education = (String) educationList.get(i + 1);
					String institute = "";
					if(!Utils.isBlankOrNull(education)) {
						institute = (String) education.split(":")[1].trim();
						institute = educationParser.getInstituteParsedFromMaster(institute);
						eData.setInstitute(institute);
					}
					//String institute = "";
				}
				allEducations.add(eData);
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		resultMap.put(ParserConstants.FIELD_EDUCATION, allEducations);
	}
	
	private ArrayList<String> getEducationPara(String exp) {
		String result = "";
		ArrayList<String> eduPara= new ArrayList<String>();
		try {
			Matcher m = RegexUtils.getMatcher(textContent, exp);
			if (m != null && m.find()) {
				result = textContent.substring(m.start(), m.end());
				Scanner scanner = new Scanner(result);
				while(scanner.hasNextLine()){
					String nextLine = scanner.nextLine().trim();
					if(nextLine.contains(part15)) {
						break;
					}
					if(nextLine.contains(part16) || nextLine.contains(part17)){
						eduPara.add(nextLine);
					}
				}
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return eduPara;
	}
	
}
