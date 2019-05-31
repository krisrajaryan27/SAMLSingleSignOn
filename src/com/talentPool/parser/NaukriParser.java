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

/**
 * @author Ajeet
 * 
 */
public class NaukriParser implements Parser {	
	private static final String naukriFormatExp = "(?mids)(Last Active:.*)(Last Modified:.*)(Brief Profile.*)(Education.*)(Candidate Text Resume.*)";
	private static final String naukriFormatExp2 = "(?mids)(Brief Profile.*)(Education.*)(Candidate Text Resume.*)";
	private static final String naukriFormatExp3 = "(?mids)(Current Location.*)(Functional Area.*)(Role.*)(Industry.*)";
	private static final String naukriFormatExp4 = "(?mids)(Download Save.*)(To FolderContact.*)(Send Mail.*)(Send SMSForward Print.*)";

	private String sourceContent;
	private String textContent;
	private HashMap resultMap;
	public static final String part1 = "(?mids)(Telephone:.*)";
	public static final String part2 = "(?mids)(Verified Phone Number.*)";
	public static final String part3 = "(?mids)(Contact by Email.*)";
	public static final String part4 = "(?mids)(Date of Birth:.*)";
	public static final String part5 = "(?mids)(Gender:.*)";
	public static final String part6 = "(?mids)(Cost to Company.*)";
	public static final String part7 = "(?mids)(Total Years of Experience:)(.*)";
	public static final String part8 = "(?mids)(Key Skills:.*)";
	public static final String part9 = "(?mids)(Current Employer:)(.*)";
	public static final String part10 = "(?mids)(Current:)(.*)";
	public static final String part11 = "(?mids)(EDUCATION.*)";
	public static final String part12 = "EXPERIENCE";
	public static final String part13 = "naukri";
	public static final String part14 = "resdex";
	public static final String part15 = "CONTACT INFORMATION";
	public static final String part16 = "Certification";
	public static final String part17 = "(?mids).*?from.*?in.*?[0-9]{4}";
	public static final String part18 = "(?mids)from(.*)? in ";
	public static final String part19 = "WORK AUTHORISATION";
	public static final String part20 = "TEXT RESUME";
	public static final String part21 = "GENERAL INFORMATION";
	public static final String part22 = "IT Skills and Projects";
	public static final String part23 = "Work Authorization";
	public static final String part24 = "Candidate Text Resume";
	public static final String part25 = "(?mids)(Education.*)";
	public static final String part26 = "(?mids)(Annual Salary\\s:)(.*)";
	public static final String part27 = "(?mids)(Work Experience:)(.*)";
	public static final String part28 = "(?mids)(Last Active:.*)";
	public static final String part29 = "(?mids)(Current Employer)(.*)";
	public static final String part30 = "(?mids)(Highest Degree)(.*)";
	public static final String part31 = "(?m)(Commented by: CommentAuthor on CommentTime.*)";
	public static final String part32 = "(Current.*)";
	public static final String part33 = "(?mids)(Work Experience)(.*)";
	public static final String part34 = "(Annual Salary.*)";
	
	public static final String part35 = "(?mids)(Send Email.*)";
	public static final String part36 = "(?mids)(Current Company.*)";
	public static final String part37 = "(Current Location.*)";
	public static final String part38 = "IT Skills";
	public static final String part39 = "(?mids)(Total Experience:)(.*)";
	public static final String part40 = "(?mids)(Phone:)(.*)";
	public static final String part41 = "Education";
	public static final String part42 = "d";
	public static final String part43 = "Â";
	public static final String part44 = "Comment";
	public static final String part45 = "Remaining";
	public static final String part46 = "Characters";
	public static final String part47 = "Preview";
	public static final String part48 = "ModifyForward Print";
	public static final String part49 = "ModifySMS Forward Print";
	public static final String part50 = "(?mids)(Work Experience.*)";
	public static final String part51 = "Work Experience"; 
	public static final String part52 = "(((?i)jan|feb|mar|apr|may|jun|jul|aug|sep|oct|nov|dec) ((19|20)\\d{2})) (to)";
	public static final String part53 = "(to) (((?i)jan|feb|mar|apr|may|jun|jul|aug|sep|oct|nov|dec) ((19|20)\\d{2}))";	
	public static final String part54 = " N/A to ";
	public static final String part55 = " N/A";
	public static final String part56= "to till date";
	public static final String part57= "till date";
	public static final String part58 = "Modify";
	public static final String part59 = "(?mids)(Candidate CV.*)";
	public static final String part60 = "(?mids)(Resume.*)";
	public static final String NotDisclosed = "Not Disclosed";
	public static final String AnnualSalary = "Annual Salary:";
	public static final String CurrentLocation = "Current Location:";
	public static final String CurrentCompany = "Current Company:";
	
	private int resumeType;
	
	private static final String regexMobile = "(?mids)[0-9]*[\\s&&[^\\n]]?[0-9]+\\(M\\)";
	private static final String regexLandline = "(?mids)[0-9]+[\\s&&[^\\n]][0-9]+[\\s&&[^\\n]][0-9]+\\(R\\)";
	private static final String regexEmail = "(?mids)[a-z0-9.\\-_]+@([a-z0-9\\-_]+\\.)+(" + "com|net|org|edu|int|mil|gov|arpa|biz|" + "aero|name|coop|info|pro|museum|tv|([a-z]{2}))";

	private static final String regexMobile1 = "(?mids)[0-9]*[\\s&&[^\\n]]?[0-9]+[0-9]*[\\s&&[^\\n]]?[0-9]*";
	
	
	public NaukriParser(String sourceContent, String textContent) {
		this.sourceContent = sourceContent;
		this.textContent = textContent;
		ParserUtils.loadNaukriProperties();
	}

	public NaukriParser(String textContent) {
		
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
			if(isFormatMatched(naukriFormatExp3) ||isFormatMatched(naukriFormatExp4) ){
				return ParserConstants.LATEST_NAUKRI_RESUME;
			}else if(isFormatMatched(naukriFormatExp) || isFormatMatched(naukriFormatExp2) ) {
				return ParserConstants.NEW_NAUKRI_RESUME;
			}		
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		if (sourceContent.contains(part13) && sourceContent.contains(part14)) {
			return ParserConstants.OLD_NAUKRI_RESUME;
		}
		return ParserConstants.NON_NAUKRI_RESUME;
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
		if(getResumeType() == ParserConstants.OLD_NAUKRI_RESUME) {
			parse();
		}else if(getResumeType() == ParserConstants.LATEST_NAUKRI_RESUME){
			parseLatestNaukriResume();
		} else {
			parseNewNaukriResume();
		}
		
		return resultMap;
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

	public void parseEmail(ArrayList<String> email) {
		try {
			if (email.size() > 0 && !Utils.isBlankOrNull((String) email.get(0))) {
				resultMap.put(ParserConstants.FIELD_EMAIL, email);
			}
		} catch (Exception e) {
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
				if (name.contains(part13)) {
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

	public void parseSkills() {
		try {
			SkillsParser skillsParser = new SkillsParser();
			int noOfSkiils = Integer.parseInt(GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_MAX_SKILLS_PARSED).trim());
			ArrayList<String> skills = skillsParser.getArrayListOfParsedSkillIdsAndNames(textContent, noOfSkiils);
			resultMap.put(ParserConstants.FIELD_SKILLS, skills);
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
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

	public void parseCurrentCTC() {
		ArrayList<String> currentCTC = new ArrayList<String>();
		Matcher m = RegexUtils.getMatcher(textContent, part6);
		if (m != null && m.find()) {				
			String lineStr = textContent.substring(m.start(), m.end());
			Scanner scanner = new Scanner(lineStr);
			String value = scanner.nextLine();
			if(!Utils.isBlankOrNull(value.trim())) {
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
					ctc = ctc + "." + ctcStr[2];
				} else {
					ctc = ctc + ".00";
				}
				if (ctc.equals("0.0") || ctc.equals("0")) {
					ctc = "";
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
				Matcher m = RegexUtils.getMatcher(textContent, part33);				
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
				String[] totalExperience = totalExpStr.split(" ");
				int yr = 0, mnth = 0;
				if(totalExperience.length == 1) {
					totalExperience = totalExperience[0].split("[.]");
					if(totalExperience.length == 2) {
						yr = Integer.parseInt(totalExperience[0]);						
						mnth = (Integer.parseInt(totalExperience[1]) * 12)/100;
					}
				} else {
					if (totalExperience.length > 0)
						yr = Integer.parseInt(totalExperience[0]);
					if (totalExperience.length > 2)
						mnth = Integer.parseInt(totalExperience[2]);
				}
				
				int numberOfUnits = -yr * 12 - mnth;
				if (numberOfUnits < 0) {
					totalExpStr=yr+"."+mnth;
					resultMap.put(ParserConstants.FIELD_WORKING_SINCE, totalExpStr);
				}
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
	}

	public void parseOthers() {
		ArrayList<String> name = getNameParsedValue(part59);
		ArrayList<String> curentEmp = getSingleParseValue(part9);
		ArrayList<String> location = getSingleParseValue(part10);
		try {
			if (name.size() > 0 && !Utils.isBlankOrNull((String) name.get(0)) && !((String) name.get(0)).contains(NotDisclosed)) {
				resultMap.put(ParserConstants.FIELD_NAME, name);
			}
			if (curentEmp.size() > 0 && !Utils.isBlankOrNull((String) curentEmp.get(0)) && !((String) curentEmp.get(0)).contains(NotDisclosed)) {
				resultMap.put(ParserConstants.FIELD_CURRENT_EMPLOYER, curentEmp);
			}
			if (location.size() > 0 && !Utils.isBlankOrNull((String) location.get(0)) && !((String) location.get(0)).contains(NotDisclosed)) {
				resultMap.put(ParserConstants.FIELD_LOCATION, location);
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
	}

	public void parse() {
		resultMap = new HashMap();
		try {
			ArrayList phones = getMultiParseValue(part1);
			parsePhone(phones);
			
			ArrayList emails = parseApplicantEmail();
			parseEmail(emails);
			
			parseApplicantNameForOldNaukri();
			parseSource();
			parseSkills();
			
			String eduParagraph = getEducationPara(part11);
			parseEducation(eduParagraph);
			
			parseCurrentCTC();
			
			parseWorkinSince(part7);
			parseOtherValues();

		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
	}

	private ArrayList<String> getNameParsedValue(String exp) {
		ArrayList<String> result = new ArrayList<String>();
		String lineStr = "";

		try {
			Matcher m = RegexUtils.getMatcher(textContent, exp);
			if (m != null && m.find()) {
				lineStr = textContent.substring(m.start(), m.end());
				Scanner scanner = new Scanner(lineStr);
				scanner.nextLine(); // To remove Name line
				String value = scanner.nextLine();
				int cnt = 0;
				while ((Utils.isBlankOrNull(value) || value.trim().equals(part21)) && cnt++ < 6) {
					value = scanner.nextLine();
				}
				result.add(value.trim());
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return result;
	}

	private ArrayList<String> getSingleParseValue(String exp) {
		ArrayList<String> result = new ArrayList<String>();
		String lineStr = "";

		try {
			Matcher m = RegexUtils.getMatcher(textContent, exp);			
			if (m != null && m.find()) {				
				lineStr = textContent.substring(m.start(), m.end());
				lineStr = m.group(2);				
				Scanner scanner = new Scanner(lineStr);
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

	private ArrayList<String> getMultiParseValue(String exp) {
		ArrayList<String> groups = new ArrayList<String>();
		String result = "";
		try {
			Matcher m = RegexUtils.getMatcher(textContent, exp);
			if (m != null && m.find()) {
				result = textContent.substring(m.start(), m.end());
				Scanner scanner = new Scanner(result);
				scanner.nextLine(); // To remove Name line
				groups.add(scanner.nextLine()); // first value
				groups.add(scanner.nextLine()); // second value
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return groups;
	}

	public String getInstitute(String education) {
		String institute = "";
		try {
			Pattern pattern = Pattern.compile(part18);
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
			Pattern pattern = Pattern.compile(part17);
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
					if (nextLine.contains(part12) || nextLine.contains(part16) || nextLine.contains(part19) || nextLine.contains(part20))
						break;
					eduPara += nextLine;
					
				}
			}			
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return eduPara;
	}

	private void parseNewNaukriResume() {		
		resultMap = new HashMap();
		try {
			ArrayList phones = parseApplicantPhone();
			parsePhone(phones);
			
			ArrayList<String> emails = parseApplicantEmail();			
			parseEmail(emails);
			
			parseApplicantName();
			
			parseSource();
			parseSkills();
			
			String eduParagraph = getApplicantEducationPara(part25);			
			parseEducation(eduParagraph);
			
			List<String> empHistoryParagraph = getEmploymentHistoryPara(part50);			
			parseEmploymentHistory(empHistoryParagraph);
			
			parseApplicantCurrentCTC();
			parseWorkinSince(part27);
			
			parseApplicantCurrentEmployer();
			parseApplicantCurrentLocation();

		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
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
	
	private ArrayList<String> parseApplicantEmail() {
		ArrayList<String> emails = new ArrayList<String>();
		Matcher matcherEmail = RegexUtils.getMatcher(textContent, regexEmail);		
		while(matcherEmail.find()) {
			emails.add(matcherEmail.group().trim());
		}	
		return emails;
	}
	
	private String getApplicantEducationPara(String exp) {	
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
					if(nextLine.contains(part41)){
						eduPara="";
					}
					if (nextLine.contains(part22) || nextLine.contains(part23) || nextLine.contains(part24)
							|| nextLine.contains(part38)) {
						break;
					}					
					eduPara += nextLine;					
				}
			}			
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return eduPara;
	}
	
	private void parseApplicantCurrentCTC() {
		ArrayList<String> currentCTC = getSingleParseValue(part26);
		try {
			if(currentCTC.size() == 0) {
				Matcher m = RegexUtils.getMatcher(textContent, part34);
				if (m != null && m.find()) {
					String lineStr = textContent.substring(m.start(), textContent.length());
					Scanner scanner = new Scanner(lineStr);
					String value = "";
					if(Utils.isBlankOrNull(value) && scanner.hasNextLine()) {
						value = scanner.nextLine();
					}
					if(Utils.isBlankOrNull(value) && scanner.hasNextLine()) {
						value = scanner.nextLine();
					}
					if(Utils.isBlankOrNull(value) && scanner.hasNextLine()) {
						value = scanner.nextLine();
					}
					if(Utils.isBlankOrNull(value) && scanner.hasNextLine()) {
						value = scanner.nextLine();
					}
					value= value.replaceFirst(AnnualSalary, "");
					currentCTC.add(value.trim());
				} 
			}
			if (currentCTC.size() > 0 && !Utils.isBlankOrNull((String) currentCTC.get(0)) && !((String) currentCTC.get(0)).contains(NotDisclosed)) {
				String ctc = (String) currentCTC.get(0);
				String[] ctcStr = ctc.split(" ");
				if(!Utils.isBlankOrNull(ctcStr[1])){
					ctc = ctcStr[1];	
				}else if(!Utils.isBlankOrNull(ctcStr[0])){
					ctc = ctcStr[0];	
				}
				 		
				resultMap.put(ParserConstants.FIELD_CURRENT_CTC, ctc);
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
	}

	private void parseOtherValues() {
		ArrayList<String> name = getNameParsedValue(part3);
		ArrayList<String> curentEmp = getSingleParseValue(part9);
		ArrayList<String> location = getSingleParseValue(part10);
		try {
			if (name.size() > 0 && !Utils.isBlankOrNull((String) name.get(0)) && !((String) name.get(0)).contains(NotDisclosed)) {
				resultMap.put(ParserConstants.FIELD_NAME, name);
			}
			if (curentEmp.size() > 0 && !Utils.isBlankOrNull((String) curentEmp.get(0)) && !((String) curentEmp.get(0)).contains(NotDisclosed)) {
				resultMap.put(ParserConstants.FIELD_CURRENT_EMPLOYER, curentEmp);
			}
			if (location.size() > 0 && !Utils.isBlankOrNull((String) location.get(0)) && !((String) location.get(0)).contains(NotDisclosed)) {
				resultMap.put(ParserConstants.FIELD_LOCATION, location);
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
	}
	
	private void parseApplicantName() {
		ArrayList<String> nameArray = new ArrayList<String>();
		String applicantName = null;
		Matcher m = RegexUtils.getMatcher(textContent, part31);
		if(m != null && m.find()) {
			String lineStr = textContent.substring(m.start(), textContent.length());
			Scanner scanner = new Scanner(lineStr);
			scanner.nextLine();
			scanner.nextLine();
			while(scanner.hasNextLine() && Utils.isBlankOrNull(applicantName)) {				
				applicantName = scanner.nextLine().trim();
			}	
		} else if(Utils.isBlankOrNull(applicantName) && getResumeType() == ParserConstants.LATEST_NAUKRI_RESUME){
			m = RegexUtils.getMatcher(textContent, part35);
			if (m != null && m.find()) {
				String result = m.group();
				Scanner scanner = new Scanner(result);
				scanner.nextLine();
				for (int i = 0; i < 25; i++) {
					if(!Utils.isBlankOrNull(applicantName)) {
						while(applicantName.contains(part43)) {
							applicantName = applicantName.replace(part43, "");
						}
					}
					if( !Utils.isBlankOrNull(applicantName) &&
							(applicantName.equals(part42) || applicantName.trim().length()<3)){
						applicantName = "";
					}
					if(!Utils.isBlankOrNull(applicantName) && 
							(applicantName.contains(part44) || applicantName.contains(part45) || applicantName.contains(part46)
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
			nameArray.add(applicantName.trim());
		}
		resultMap.put(ParserConstants.FIELD_NAME, nameArray);
	}
	
	private void parseApplicantNameForOldNaukri() {
		ArrayList<String> nameArray = new ArrayList<String>();
		String applicantName = null;
		Matcher m = RegexUtils.getMatcher(textContent, part59);
		if(m != null && m.find()) {
			String lineStr = textContent.substring(m.start(), textContent.length());
			Scanner scanner = new Scanner(lineStr);
			scanner.nextLine();
		
			while(scanner.hasNextLine() && Utils.isBlankOrNull(applicantName)) {				
                                  				applicantName = scanner.nextLine().trim();
			}	
		} else if(Utils.isBlankOrNull(applicantName)){
			m = RegexUtils.getMatcher(textContent, part60);
			if (m != null && m.find()) {
				String result = m.group();
				Scanner scanner = new Scanner(result);
				scanner.nextLine();
				for (int i = 0; i < 25; i++) {
					if(!Utils.isBlankOrNull(applicantName)) {
						while(applicantName.contains(part43)) {
							applicantName = applicantName.replace(part43, "");
						}
					}
					if( !Utils.isBlankOrNull(applicantName) &&
							(applicantName.equals(part42) || applicantName.trim().length()<3)){
						applicantName = "";
					}
					if(!Utils.isBlankOrNull(applicantName) && 
							(applicantName.contains(part44) || applicantName.contains(part45) || applicantName.contains(part46)
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
		
		
		if(!Utils.isBlankOrNull(applicantName)) {
			nameArray.add(applicantName.trim());
		}
		resultMap.put(ParserConstants.FIELD_NAME, nameArray);
	}
	
	private void parseApplicantCurrentEmployer() {
		ArrayList<String> currentEmplArray = new ArrayList<String>();
		String currentEmpl = "";
		try {
			Matcher m = RegexUtils.getMatcher(textContent, part29);
			if (m != null && m.find()) {				
				String temp = m.group();
				Scanner scanner = new Scanner(temp);
				currentEmpl = scanner.nextLine();
				if(!Utils.isBlankOrNull(currentEmpl) && currentEmpl.indexOf(":") == -1) {
					scanner.nextLine();
					scanner.nextLine();
					currentEmpl = scanner.nextLine();
				}
				currentEmpl = currentEmpl.substring(currentEmpl.indexOf(":") + 1, currentEmpl.indexOf("["));
				currentEmplArray.add(currentEmpl.trim());
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		resultMap.put(ParserConstants.FIELD_CURRENT_EMPLOYER, currentEmplArray);
	}
	
	private void parseApplicantCurrentLocation() {
		ArrayList<String> currentLocationArray = new ArrayList<String>();
		String currentLocation = "";
		try {
			Matcher m = RegexUtils.getMatcher(textContent, part30);
			if (m != null && m.find()) {	
				String temp = m.group();				
				Scanner scanner = new Scanner(temp);
				scanner.nextLine();
				currentLocation = scanner.nextLine();
				Matcher m1 = RegexUtils.getMatcher(currentLocation, part32);
				if(!m1.find()) {
					String nextLine = new String();
					while(!m1.find()) {
						nextLine = scanner.nextLine();
						m1 = RegexUtils.getMatcher(nextLine, part32);
					}
					scanner.nextLine();
					scanner.nextLine();
					currentLocation = scanner.nextLine();
				}
				if(currentLocation.indexOf("[") != -1) {
					currentLocation = currentLocation.substring(currentLocation.indexOf(":") + 1, currentLocation.indexOf("["));
				} else {
					currentLocation = currentLocation.substring(currentLocation.indexOf(":") + 1, currentLocation.length());
				}			
				currentLocationArray.add(currentLocation.trim());
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		resultMap.put(ParserConstants.FIELD_LOCATION, currentLocationArray);
	}
	
	
	
	/*** New Naukri Format applicable from 10.7.0 Date 15/08/2010 ***/
	
	private void parseLatestNaukriResume() {		
		resultMap = new HashMap();
		try {
			parseNaukriPhone();
			
			ArrayList<String> emails = parseApplicantEmail();			
			parseEmail(emails);
			
			parseApplicantName();
			
			parseSource();
			parseSkills();
			
			String eduParagraph = getApplicantEducationPara(part25);			
			parseEducation(eduParagraph);
			
			List<String> empHistoryParagraph = getEmploymentHistoryPara(part50);			
			parseEmploymentHistory(empHistoryParagraph);
			
			parseApplicantCurrentCTC();
			parseWorkinSince(part39);
			
			parseApplicantNewCurrentEmployer();
			parseApplicantNewCurrentLocation();

		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
	}
	
	private void parseApplicantNewCurrentEmployer() {
		ArrayList<String> currentEmplArray = new ArrayList<String>();
		String currentCompany = "";
		try {
			Matcher m = RegexUtils.getMatcher(textContent, part36);
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
	
	private void parseApplicantNewCurrentLocation() {
		ArrayList<String> currentLocationArray = new ArrayList<String>();
		String currentLocation = "";
		try {
			Matcher m = RegexUtils.getMatcher(textContent, part37);
			if (m != null && m.find()) {	
				String temp = m.group();				
				Scanner scanner = new Scanner(temp);
				currentLocation = scanner.nextLine();
				if(Utils.isBlankOrNull(currentLocation) && scanner.hasNextLine()) {
					currentLocation = scanner.nextLine();
				}
				currentLocation= currentLocation.replaceFirst(CurrentLocation, "");	
				currentLocationArray.add(currentLocation.trim());
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		resultMap.put(ParserConstants.FIELD_LOCATION, currentLocationArray);
	}
	
	
	private void parseNaukriPhone() {
		ArrayList<String> phoneNumbers = new ArrayList<String>();	
		try{
			Matcher m = RegexUtils.getMatcher(textContent, part40);
			if (m != null && m.find()) {	
				String temp = m.group();				
				Scanner scanner = new Scanner(temp);
				String phoneContent = scanner.nextLine();
				String[] phoneStr = phoneContent.split(",");
				
				for (int i = 0; i < phoneStr.length; i++) {
					String phone = phoneStr[i];
					Matcher matcherMobile = RegexUtils.getMatcher(phone.trim(), regexMobile1);
					if (matcherMobile != null && matcherMobile.find()) {
						String phoneNo = matcherMobile.group();
						phoneNumbers.add(phoneNo);
					}
				}
				
			}
			resultMap.put(ParserConstants.FIELD_PHONE, phoneNumbers);
		}catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
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
					if (nextLine.contains(part41) || nextLine.contains(part19) || nextLine.contains(part20))
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
	public static void main(String args[]){
		HTMLToPlainTextConverter cov = new HTMLToPlainTextConverter();
		//String text=cov.convert("D:/projects/latest/v_15_2_0_GA/documents/201512/21/DOC6262052877716467554.html");
		//String text=cov.convert("D:/projects/latest/v_15_2_0_GA/documents/201512/21/ATT1702088371506735317.html");
		//String text=cov.convert("D:/projects/latest/v_15_2_0_GA/documents/201512/21/ATT2246560131784119313.html");
		String text=cov.convert("D:/projects/latest/v_15_2_0_GA/documents/201512/21/ATT3324492070337595814.html");
		NaukriParser naukriParser=new NaukriParser(text);
		//int type=naukriParser.isFormat();
		ArrayList emails =naukriParser.parseApplicantEmail();
		naukriParser.parseEmail(emails);
		
		naukriParser.parseApplicantNameForOldNaukri();
	}
}
