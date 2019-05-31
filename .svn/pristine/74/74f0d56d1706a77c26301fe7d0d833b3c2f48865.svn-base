package com.talentPool.parser;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
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

public class TechFetchParser implements Parser{
	private String sourceContent;
	private String textContent;
	private HashMap resultMap;
	public static final String part1 = "(?mids)(Ph:.*)";
	public static final String part2 = "(?mids)(Email:.*)";
	public static final String part3 = "(?mids)(Contact Information.*)";
	public static final String part31 = "(?mids)(Last Modified:.*)";
	public static final String part4 = "Current Location:";
	public static final String part5 = "(?mids)(Gender:.*)";
	public static final String part6 = "(?mids)(Salary.*)";
	public static final String part7 = "(Tot Exp.*)";
	public static final String part8 = "(?mids)(Key Skills:.*)";
	public static final String part9 = "(?mids)(Current Employer.*)";
	public static final String part10 = "(?mids)(Current Location:.*)";
	public static final String part11 = "(?mids)(Mobile.*)";
	public static final String part12 = "(?mids)(Highest Degree Held.*)";
	public static final String part13 = "2nd Highest Degree Held";
	public static final String part14 = "Preferred Job Location";
	public static final String part15 = "techfetch";
	public static final String part16 = "5iantlavalamp";
	public static final String part17 = "(?mids)(.*?2nd Highest Degree Held)";
	public static final String part18 = "(?mids)(Previous Employer.*)";
	public static final String part40 = "(?mids)(Ph)(.*)";
	public static final String part19 = "(?mids)(EDUCATION.*)";
	public static final String part20 = "EXPERIENCE";
	public static final String part21 = "Certification";
	public static final String part22 = "WORK AUTHORISATION";
	public static final String part23 = "TEXT RESUME";
	public static final String part24 = "(?mids).*?from.*?in.*?[0-9]{4}";
	public static final String part25 = "(?mids)from(.*)? in ";
	public static final String part26 = "(?mids)(Work Experience)(.*)";
	public static final String part27 = "(?mids)(Current Company.*)";
	public static final String part28 = "(?mids)(Last Active:.*)";
	public static final String part29 = "(Location.*)";
	public static final String part30 = "(?mids)(Work Experience.*)";
	public static final String part32 = "WORK AUTHORISATION";
	public static final String part33 = "TEXT RESUME";
	public static final String part35 = "(?mids)(Register for Event.*)";
	public static final String part36 = "(?mids)(Register for Event.*)";
			// "(?m)(US Citizen.*)";
	public static final String part37 = "(?m)(Green Card.*)"; 
	public static final String part39 = "References";
	public static final String part41 = "Education";
	public static final String part42 = "d";
	public static final String part43 = "Â";
	public static final String part44 = "Comment";
	public static final String part45 = "Remaining";
	public static final String part46 = "Characters";
	public static final String part47 = "Preview";
	public static final String part48 = "ModifyForward Print";
	public static final String part49 = "ModifySMS Forward Print";
	public static final String part51 = "Work Experience"; 
	public static final String part52 = "(((?i)jan|feb|mar|apr|may|jun|jul|aug|sep|oct|nov|dec) ((19|20)\\d{2})) (to)";
	public static final String part53 = "(to) (((?i)jan|feb|mar|apr|may|jun|jul|aug|sep|oct|nov|dec) ((19|20)\\d{2}))";	
	public static final String part54 = " N/A to ";
	public static final String part55 = " N/A";
	public static final String part56= "to till date";
	public static final String part57= "till date";
	public static final String part58 = "Modify";
	public static final String part59 = "div";
	public static final String part60 = "Tot Exp";
	public static final String NotDisclosed = "Not Disclosed";
	public static final String AnnualSalary = "Annual Salary:";
	public static final String CurrentLocation = "Location";
	public static final String CurrentCompany = "Current Company:";
	private static final String regexMobile = "(?mids)[0-9]*[\\s&&[^\\n]]?[0-9]+\\(M\\)";
	private static final String regexLandline = "(?mids)[0-9]+[\\s&&[^\\n]][0-9]+[\\s&&[^\\n]][0-9]+\\(R\\)";
	private static final String regexEmail = "(?mids)[a-z0-9.\\-_]+@([a-z0-9\\-_]+\\.)+(" + "com|net|org|edu|int|mil|gov|arpa|biz|" + "aero|name|coop|info|pro|museum|tv|([a-z]{2}))";

	private static final String regexMobile1 = "(?mids)[0-9]*[\\s&&[^\\n]]?[0-9]+[0-9]*[\\s&&[^\\n]]?[0-9]*";
	
	public TechFetchParser(String sourceContent, String textContent) {
		this.sourceContent = sourceContent;
		this.textContent = textContent;
		ParserUtils.loadTechFetchProperties();
	}
	
	public TechFetchParser(String textContent) {
		
		this.textContent = textContent;
		
	}

	public boolean isFormat() {
		if (sourceContent.contains(part15)) {
			return true;
		} else {
			return false;
		}
	}
	
	public HashMap getParsedMap() {
		parse();
		return resultMap;
	}
	public static void main(String args[]){
		HTMLToPlainTextConverter cov = new HTMLToPlainTextConverter();
		String text=cov.convert("D:/projects/v_15_0_0_GA/documents/201505/13/ATT6245107982889559001.html");
		TechFetchParser techFetchParser=new TechFetchParser(text);
		//ArrayList phones = techFetchParser.parseApplicantPhone();
		//techFetchParser.parsePhone(phones);
		//techFetchParser.parseWorkinSince();
		//techFetchParser.parseApplicantName();
		//String eduParagraph = techFetchParser.getEducationPara(part19);
		techFetchParser.parseWorkinSince(part7);
		//techFetchParser.parseSkills();
		//techFetchParser.parsePhone();
		
	}
	private ArrayList<String> parseApplicantEmail() {
		ArrayList<String> emails = new ArrayList<String>();
		Matcher matcherEmail = RegexUtils.getMatcher(textContent, regexEmail);		
		while(matcherEmail.find()) {
			emails.add(matcherEmail.group().trim());
		}	
		return emails;
	}
	private void parsePhone() {
		ArrayList<String> phoneNumbers = new ArrayList<String>();	
		try{
				String phoneContent =getPhonePara(part40);
				if(!Utils.isBlankOrNull(phoneContent)){
					phoneContent=phoneContent.replaceAll(part43, "");
				}
				String[] phoneStr = phoneContent.split("Ph");
				
				for (int i = 0; i < phoneStr.length; i++) {
					String phone = phoneStr[i];
					String phoneNumber="";
					if(!Utils.isBlankOrNull(phone)){
						int length=phone.length();
						if(length>10){
						phoneNumber=phone.substring(phone.lastIndexOf(":")+1, phone.length());
						phoneNumber=phoneNumber.trim();
						}
					}
					if(!Utils.isBlankOrNull(phoneNumber)){
						phoneNumber=phoneNumber.replaceAll("\\+","");
						phoneNumbers.add(phoneNumber);
					}
				}
				
			
			resultMap.put(ParserConstants.FIELD_PHONE, phoneNumbers);
		}catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
	}
	
	public void parseSource() {
		try {
			String sourceId = "";
			ArrayList<String> sourceIds = CommonUtils.getSourceIds();
			ArrayList<String> sourceNames = CommonUtils.getSourceNames();
			int idx = 0;
			for (int i = 0; i < sourceNames.size(); i++) {
				String name = sourceNames.get(i);
				name = name.toLowerCase();
				if (name.contains(part15)) {
					idx = i;
					break;
				}
			}
			if (idx >= 0) {
				sourceId = sourceIds.get(idx);
			}
			if (sourceId != null) {
				ArrayList<String> src = new ArrayList<String>();
				src.add(0, sourceId);
				src.add(1, sourceNames.get(idx));
				resultMap.put(ParserConstants.FIELD_SOURCE, src);
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
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
		String eduParagraph = getEducationPara(part19);
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
		ArrayList<String> currentCTC = new ArrayList<String>();
		Matcher m = RegexUtils.getMatcher(textContent, part6);
		if (m != null && m.find()) {				
			String lineStr = textContent.substring(m.start(), m.end());
			Scanner scanner = new Scanner(lineStr);
			String value = scanner.nextLine();
			if(!Utils.isBlankOrNull(value.trim())) {
				if(value.contains(part43)){
					value=value.replace(part43, "");
				}
				String[] parts = value.split(":");				
				if(parts.length == 2) {
					currentCTC.add(parts[1].trim());
				}				
			}				
		}
		try {			
			if (currentCTC.size() > 0 && !Utils.isBlankOrNull(currentCTC.get(0)) && !(currentCTC.get(0)).contains(NotDisclosed)) {
				String ctc = currentCTC.get(0);				
				String[] ctcStr = ctc.split(" ");
				if (ctcStr.length > 0) {
					ctc = ctcStr[0];
				}					
				if (ctcStr.length > 2) {
					ctc = ctc ;
				} else {
					ctc = ctc ;
				}
				if (ctc.equals("0.0") || ctc.equals("0")) {
					ctc = "";
				}	
				if(ctc.contains("/")){
					ctc=ctc.substring(0, ctc.indexOf("/"));
				}
				resultMap.put(ParserConstants.FIELD_CURRENT_CTC, ctc);
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
	}
	
	public void parseWorkinSince(String exp) {		
		ArrayList<String> totalExp = getSingleParseValue(exp);		
		// mm/yyyy or mmm-yyyy
		try {
			
			if(totalExp.size() == 0) {
				Matcher m = RegexUtils.getMatcher(textContent, part26);				
				if (m != null && m.find()) {
					String lineStr = m.group();
					Scanner scanner = new Scanner(lineStr);
					String value = scanner.nextLine();					
					while(value.indexOf("Month(s)") < 0) {
						value = scanner.nextLine();
					}
					if(!Utils.isBlankOrNull(value)) {
						String[] parts = value.split(":");
						if(parts.length > 1) {
							value = parts[parts.length - 1];
						}
					}
					totalExp.add(value.trim());
				} 
			}
			if (totalExp.size() > 0 && !Utils.isBlankOrNull((String) totalExp.get(0)) && !((String) totalExp.get(0)).contains(NotDisclosed)) {
				String totalExpStr = (String) totalExp.get(0);
				if(!Utils.isBlankOrNull(totalExpStr)){
					totalExpStr=totalExpStr.replaceAll(part60, "");
					totalExpStr=totalExpStr.substring(totalExpStr.lastIndexOf(part43)+1, totalExpStr.length());
					totalExpStr=totalExpStr.replaceAll(part43, "");
					totalExpStr=totalExpStr.replaceAll("-", "");
					totalExpStr=totalExpStr.replaceAll("\\s+","");
					totalExpStr=totalExpStr.replaceAll("yrs","");
					totalExpStr=totalExpStr.replaceAll("Yrs","");
					totalExpStr=totalExpStr.trim();
				}
					resultMap.put(ParserConstants.FIELD_WORKING_SINCE, totalExpStr);
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
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
	
	public void parse() {
		resultMap = new HashMap();
		try{
			parseApplicantName(); //done
			parsePhone(); //done
			parseCurrentCTC(); //done
			parseWorkinSince(part7); //done
			ArrayList<String> emails = parseApplicantEmail();			
			parseEmail(emails); // done
			parseEducation(); //done
			parseApplicantNewCurrentLocation(); // done
			parseApplicantNewCurrentEmployer();
			parseSource();
			parseSkills();
			List<String> empHistoryParagraph = getEmploymentHistoryPara(part30);			
			parseEmploymentHistory(empHistoryParagraph);
			
			
			
			
			
		} catch (Exception e) {
			TPLogger.getLogger().error("Error", e);
		}
	}
	
	public void parsePhone(ArrayList phones) {		
		ArrayList<String> phoneNumbers = new ArrayList<String>();
		try {
			if (phones.size() > 0 && !Utils.isBlankOrNull((String) phones.get(0)) && ((String) phones.get(0)).contains("(M)")) {
				String mobileNo = (String) phones.get(0);
				mobileNo = mobileNo.replace("(M)", ""); // Remove (M) extension
				phoneNumbers.add(mobileNo.trim());
			}
			if (phones.size() > 1 && !Utils.isBlankOrNull((String) phones.get(1)) && ((String) phones.get(1)).contains("(R)")) {
				String phoneNo = (String) phones.get(1);
				phoneNo = phoneNo.replace("(R)", "");// Remove (R) extension
				phoneNumbers.add(phoneNo.trim());
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		resultMap.put(ParserConstants.FIELD_PHONE, phoneNumbers);
	}
	
	private ArrayList<String> parseApplicantPhone() {
		ArrayList<String> phones = new ArrayList<String>();	
		Matcher matcherMobile = RegexUtils.getMatcher(textContent, regexMobile);
		if(matcherMobile.find()) {
			phones.add(matcherMobile.group());
		}		
		Matcher matcherLandline = RegexUtils.getMatcher(textContent, regexLandline);
		if(matcherLandline.find()) {
			phones.add(matcherLandline.group());
		}	
		return phones;
	}
	
	public List<String> getEmploymentHistoryPara(String exp) {
		String result = "";
		List<String> employmentHistoryPara = new ArrayList<String>();
		try {
			Matcher matcher = RegexUtils.getMatcher(textContent, exp);
			if (matcher != null && matcher.find()) {
				result = textContent.substring(matcher.start(), matcher.end());
				Scanner scanner = new Scanner(result);
				scanner.nextLine();
				boolean addWExp=false;
				while (scanner.hasNextLine()) {
					String nextLine = scanner.nextLine();					
					if(nextLine.contains(part51)){
						employmentHistoryPara.add("");
						addWExp=true;
					}					
					if (nextLine.contains(part41) || nextLine.contains(part32) || nextLine.contains(part33))
						break;
					
					if(addWExp)
						employmentHistoryPara.add(nextLine);					
				}
			}
			Collections.reverse(employmentHistoryPara);	
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}		
		return employmentHistoryPara;
	}
	
	public void parseEmploymentHistory(List<String> emplHistParaList) {		
		List<EmploymentHistoryData> empHistNaukriList = new ArrayList<EmploymentHistoryData>();
		try {
			List<String> empNaukriList = new ArrayList<String>();
			empNaukriList = getEmploymentHistoryLines(emplHistParaList);			
			for(int i = 0; i < empNaukriList.size(); i+=2){
				EmploymentHistoryData ehd = new EmploymentHistoryData();
				String stringDates =  (String) empNaukriList.get(i);
				setEmployerFromAndToDate(ehd,stringDates);
				String stringEmployer =  (String) empNaukriList.get(i+1);
				setEmployerAndDesignation(ehd,stringEmployer);			
				empHistNaukriList.add(ehd);
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		resultMap.put(ParserConstants.FIELD_EMPLOYMENT_HISTORY, empHistNaukriList);
	}
	public List<String> getEmploymentHistoryLines(List<String> emplHistParaList) {
		List<String> empHistList = new ArrayList<String>();
		boolean empFlag=false;
		try {				
			for(String strEmpHist:emplHistParaList){
				Matcher matcher = RegexUtils.getMatcher(strEmpHist, part52);
				Matcher matcher2 = RegexUtils.getMatcher(strEmpHist, part53);
				Matcher matcher3 = RegexUtils.getMatcher(strEmpHist, part54);
				Matcher matcher4 = RegexUtils.getMatcher(strEmpHist, part56);
				if (matcher != null && matcher.find()) {
					empHistList.add(strEmpHist);
					empFlag=true;
				}else if (!empFlag && matcher2 != null && matcher2.find()) {
					empHistList.add(strEmpHist);
					empFlag=true;
				}else if (!empFlag && matcher3 != null && matcher3.find()) {
					empHistList.add(strEmpHist);
					empFlag=true;
				}else if (!empFlag && matcher4 != null && matcher4.find()) {
					empHistList.add(strEmpHist);
					empFlag=true;
				}else if(empFlag){
					empHistList.add(strEmpHist);
					empFlag=false;
				}				
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return empHistList;
	}
	
	public void setEmployerFromAndToDate(EmploymentHistoryData ehd, String stringDates){
		Date date = new Date();
		String fromDate="";
		String toDate="";
		try{
			String[] dates = stringDates.split("to");
			fromDate=dates[0].trim();
			if(!Utils.isBlankOrNull(fromDate)){
				date = Utils.convertToDate(("1 "+ fromDate).replace(" ", Utils.dateDescSeparator), Utils.regDDMMMYYYYFormat);
				ehd.setEmployerFromDate(new java.sql.Date(date.getTime()));
			}
			toDate=dates[1].trim();
			if(!Utils.isBlankOrNull(toDate)){
				Matcher matcher = RegexUtils.getMatcher(toDate, part57);
				if (matcher != null && matcher.find()) {
					Calendar calendar = Calendar.getInstance();
					date = Utils.convertToDate("1"+Utils.dateDescSeparator+calendar.get(Calendar.MONTH)+Utils.dateDescSeparator+calendar.get(Calendar.YEAR),Utils.regDDMMMYYYYFormat);
					ehd.setEmployerToDate(new java.sql.Date(date.getTime()));
				}else{
					date = Utils.convertToDate(("1 " + toDate).replace(" ", Utils.dateDescSeparator), Utils.regDDMMMYYYYFormat);
					ehd.setEmployerToDate(new java.sql.Date(date.getTime()));
				}
			}
		}catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR,e);
		}
	}
	
	public void setEmployerAndDesignation(EmploymentHistoryData ehd, String stringEmployer){
		try{			
			String[] emplDesig = stringEmployer.split("as");
			ehd.setEmployerName(emplDesig[0].trim());
			ehd.setDesignationName(emplDesig[1].trim());
		}catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR,e);			
		}
	}
	private void parseApplicantNewCurrentLocation() {
		ArrayList<String> currentLocationArray = new ArrayList<String>();
		String currentLocation = "";
		try {
			Matcher m = RegexUtils.getMatcher(textContent, part29);
			if (m != null && m.find()) {	
				String temp = m.group();				
				Scanner scanner = new Scanner(temp);
				currentLocation = scanner.nextLine();
				if(Utils.isBlankOrNull(currentLocation) && scanner.hasNextLine()) {
					currentLocation = scanner.nextLine();
				}
				if(!Utils.isBlankOrNull(currentLocation)){
					currentLocation=currentLocation.substring(currentLocation.indexOf(":")+4,currentLocation.length());
					if(currentLocation.contains(part43)){
						currentLocation=currentLocation.replaceAll(part43,"");
					}
					if(currentLocation.contains(":")){
						currentLocation=currentLocation.replaceAll(":","");
					}
				}
				currentLocation= currentLocation.replaceFirst(CurrentLocation,"");	
				
				
				currentLocationArray.add(currentLocation.trim());
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		resultMap.put(ParserConstants.FIELD_LOCATION, currentLocationArray);
	}
	
	private void parseApplicantNewCurrentEmployer() {
		ArrayList<String> currentEmplArray = new ArrayList<String>();
		String currentCompany = "";
		try {
			Matcher m = RegexUtils.getMatcher(textContent, part27);
			if (m != null && m.find()) {	
				String temp = m.group();				
				Scanner scanner = new Scanner(temp);
				currentCompany = scanner.nextLine();
				if(Utils.isBlankOrNull(currentCompany) && scanner.hasNextLine()) {
					currentCompany = scanner.nextLine();
				}
				currentCompany= currentCompany.replaceFirst(CurrentCompany, "");	
				currentEmplArray.add(currentCompany.trim());
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		resultMap.put(ParserConstants.FIELD_CURRENT_EMPLOYER, currentEmplArray);
	}
	
	private void parseApplicantName() {
		ArrayList<String> nameArray = new ArrayList<String>();
		String applicantName = null;
		
		Matcher m = RegexUtils.getMatcher(textContent, part36);
		if(Utils.isBlankOrNull(applicantName)) {
			Scanner scanner = new Scanner(textContent);
			String value = null;
			while(scanner.hasNextLine()) {
				value = scanner.nextLine();				
				if(value.indexOf("US Citizen") > 0) {
					applicantName=value;
					break;
				}
				else if(value.indexOf("Green Card") > 0){
					
					applicantName=value;
					break;
				}
			}
			if(!Utils.isBlankOrNull(applicantName)){
				if(applicantName.contains(part43)) {
					applicantName = applicantName.replace(part43, "");
				}
				if(applicantName.contains("Green Card")){
					applicantName=applicantName.substring(0, applicantName.indexOf("(Green Card"));
							
				}
				if(applicantName.contains("US Citizen")){
					applicantName=applicantName.substring(0, applicantName.indexOf("(US Citizen"));
							
				}
				applicantName=applicantName.trim();
			}
			applicantName = applicantName;
		}
		else if(Utils.isBlankOrNull(applicantName)){
		if(m != null && m.find()) {
			String lineStr = textContent.substring(m.start(), textContent.length());
			Scanner scanner = new Scanner(lineStr);
			scanner.findInLine(part36);
			while(scanner.hasNextLine() && Utils.isBlankOrNull(applicantName)) {				
				applicantName = scanner.nextLine().trim();
			}	
			if(!Utils.isBlankOrNull(applicantName)){
				while(applicantName.contains(part43)) {
					applicantName = applicantName.replace(part43, "");
				}
				if(applicantName.contains("Green Card")){
					applicantName=applicantName.substring(0, applicantName.indexOf("(Green Card"));
							
				}
				applicantName=applicantName.trim();
			}
		} 
		
		}
		else if(Utils.isBlankOrNull(applicantName) ){
			m = RegexUtils.getMatcher(textContent, part35);
			if (m != null && m.find()) {
				String result = m.group();
				Scanner scanner = new Scanner(result);
				scanner.nextLine();
				for (int i = 0; i < 25; i++) {
					if(!Utils.isBlankOrNull(applicantName)) {
						if(applicantName.contains(part43)) {
							applicantName = applicantName.replace(part43, "");
						}
					}
					if( !Utils.isBlankOrNull(applicantName) &&
							(applicantName.equals(part42) || applicantName.trim().length()<3)){
						applicantName = "";
					}
					if(!Utils.isBlankOrNull(applicantName) && 
							(applicantName.contains(part44) || applicantName.contains(part45) || applicantName.contains(part46)||applicantName.contains(part59)
									|| applicantName.contains(part47) || applicantName.contains(part48) || applicantName.contains(part49) || applicantName.contains(part58))){
						applicantName = "";
					}
					if(Utils.isBlankOrNull(applicantName) && scanner.hasNextLine()) {
						applicantName = scanner.nextLine();
					}else if(!Utils.isBlankOrNull(applicantName)){
						break;
					}
				}
				scanner.close();			
			}
		}else {
			m = RegexUtils.getMatcher(textContent, part28);
			if (m != null && m.find()) {
				String result = m.group();
				Scanner scanner = new Scanner(result);
				
				scanner.nextLine();
				if(scanner.hasNextLine()) {
					applicantName = scanner.nextLine();
				}			
				if(Utils.isBlankOrNull(applicantName) && scanner.hasNextLine()) {
					applicantName = scanner.nextLine();
				}
				scanner.close();			
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
			if(applicantName.contains(part43)){
				applicantName=applicantName.replaceAll(part43,"");
			}
			nameArray.add(applicantName.trim());
		}
		resultMap.put(ParserConstants.FIELD_NAME, nameArray);
	}
	public void parseEducation(String eduParagraph) {		
		ArrayList<EducationalData> allEducations = new ArrayList<EducationalData>();
		try {
			String education = "";

			ArrayList educationList = new ArrayList();
			educationList = getEducationLines(eduParagraph);
			
			for (int i = 0; i < educationList.size(); i++) {
				education = (String) educationList.get(i);				
				EducationParser educationParser = new EducationParser();
				EducationalData eData = educationParser.getEducationParsedFromMaster(education);
				String institute = eData.getInstitute();
				if (Utils.isBlankOrNull(institute)) { // Setting the institue in btwn "from" and
					// "in"
					institute = getInstitute(education);
					eData.setInstitute(institute);
				}
				allEducations.add(eData);
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		resultMap.put(ParserConstants.FIELD_EDUCATION, allEducations);
	} 
	
	public String getInstitute(String education) {
		String institute = "";
		try {
			Pattern pattern = Pattern.compile(part25);
			Matcher m = pattern.matcher(education);
			while (m.find()) {
				institute = m.group(1);
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		if (!Utils.isBlankOrNull(institute)) {
			return institute.trim();
		} else {
			return "";
		}
	}
	
	public ArrayList<String> getEducationLines(String eduParagraph) {
		ArrayList<String> educationList = new ArrayList<String>();
		try {
			Pattern pattern = Pattern.compile(part24);
			Matcher m = pattern.matcher(eduParagraph);
			while (m.find()) {
				educationList.add(eduParagraph.substring(m.start(), m.end()));
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return educationList;
	}
	
	public String getEducationPara(String exp) {		
		String result = "";
		String eduPara = "";
		try {			
			Matcher m = RegexUtils.getMatcher(textContent, exp);			
			if (m != null && m.find()) {
				result = textContent.substring(m.start(), m.end());				
				Scanner scanner = new Scanner(result);
				scanner.nextLine(); // Remove Edu name line
				while (scanner.hasNextLine()) {
					String nextLine = scanner.nextLine();					
					if (nextLine.contains(part20) || nextLine.contains(part21) || nextLine.contains(part22) || nextLine.contains(part23)||nextLine.contains(part39))
						break;
					eduPara += nextLine;
					
				}
			}			
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return eduPara;
	}
	public String getPhonePara(String exp) {		
		String result = "";
		String phonePara = "";
		try {			
			Matcher m = RegexUtils.getMatcher(textContent, exp);			
			if (m != null && m.find()) {
				result = textContent.substring(m.start(), m.end());				
				Scanner scanner = new Scanner(result);
				scanner.nextLine(); // Remove Edu name line
				while (scanner.hasNextLine()) {
					String nextLine = scanner.nextLine();					
					if (nextLine.contains("Email") || nextLine.contains(part21) || nextLine.contains(part22) || nextLine.contains(part23)||nextLine.contains(part39))
						break;
					phonePara += nextLine;
					
				}
			}			
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return phonePara;
	}
	
	private ArrayList<String> getSingleParseValue(String exp) {
		ArrayList<String> result = new ArrayList<String>();
		String lineStr = "";

		try {
			Matcher m = RegexUtils.getMatcher(textContent, exp);			
			if (m != null && m.find()) {				
				String temp = m.group();				
				Scanner scanner = new Scanner(temp);
				String value = scanner.nextLine();
				
				
				if(!Utils.isBlankOrNull(value.trim())) {
					result.add(value.trim());
				}				
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
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
