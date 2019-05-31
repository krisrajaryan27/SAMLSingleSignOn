package com.talentPool.applicant.bc;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.HashMap;


public class XMLFile 

{
	RchilliMapFields map;
	boolean exp=false;
	boolean edu=false;
	boolean skill=false;
	boolean linkedIn=false;
	ArrayList<HashMap<String,String>>  EducationSplit = new ArrayList<HashMap<String,String>>();
	ArrayList<HashMap<String,String>>  ExperienceSplit= new ArrayList<HashMap<String,String>>();
	ArrayList<HashMap<String,String>>  SkillSplit = new ArrayList<HashMap<String,String>>();
	ArrayList<HashMap<String,String>>  LinkedinSplit = new ArrayList<HashMap<String,String>>();
	ArrayList<HashMap<String,String>>  projectSplit = new ArrayList<HashMap<String,String>>();
	HashMap<String,String> educations;
	HashMap<String,String> experiences;
	HashMap<String,String> skills;
	HashMap<String,String> linkedin;
	HashMap<String,String> projects;
	int expCount=0;
	
	public XMLFile(RchilliMapFields map)
	{
		this.map=map;
		
		
	}	       
	  /* ---------------------------------------Others ends--------------------------------------------*/  
 public	void XmlRead(String xmlString)
	{
	    
    try {
 
	SAXParserFactory factory = SAXParserFactory.newInstance();
	SAXParser saxParser = factory.newSAXParser();
 
	DefaultHandler handler = new DefaultHandler() {
    
	boolean bfname = false;
	
	String nodeName="";
 
	public void startElement(String uri, String localName,String qName, Attributes attributes) throws SAXException
	{
		nodeName=qName;
		if(nodeName.equals("EducationSplit"))
		{
			educations= new  HashMap<String,String> ();
		}
		if(nodeName.equals("WorkHistory"))
		{
			experiences= new  HashMap<String,String> ();
			experiences.put("ExperienceId", Integer.toString(expCount));
		}
		if(nodeName.equals("SkillSet"))
		{
			skills= new  HashMap<String,String> ();
		}
		if(nodeName.equals("Recomendation"))
		{
			linkedin = new  HashMap<String,String> ();
		}
		if(nodeName.equals("Projects"))
		{
			projects = new  HashMap<String,String> ();
			projects.put("ExperienceId", Integer.toString(expCount));
		}
		bfname=true;
	}
	public void endElement(String uri, String localName, String qName) throws SAXException {
		
		nodeName=qName;
		
		if(nodeName.equals("EducationSplit"))
		{
			EducationSplit.add(educations);
		}
		if(nodeName.equals("WorkHistory"))
		{
			ExperienceSplit.add(experiences);
			expCount++;
		}
		if(nodeName.equals("SkillSet"))
		{
			SkillSplit.add(skills);
		}
		if(nodeName.equals("Recomendation"))
		{
			LinkedinSplit.add(linkedin);
		}
		if(nodeName.equals("SegregatedQualification"))
		{
			 map.setQualificationSegrigation(EducationSplit);
		}
		if(nodeName.equals("SegregatedExperience"))
		{
			map.setExperienceSegrigation(ExperienceSplit);
		}
		if(nodeName.equals("SkillSet"))
		{
			map.setSkillSegrigation(SkillSplit);
		}
		if(nodeName.equals("Recommendations"))
		{
			map.setLinkedInRecommendation(LinkedinSplit);
		}
		if(nodeName.equals("Projects"))
		{
			projects.put("ExperienceId", Integer.toString(expCount));
		}
		
	
	}
 
	
	public void characters(char ch[], int start, int length) throws SAXException {
 
		if (bfname)
		{
			
			 if(nodeName.equals( "ResumeFileName")) { map.setResumeFileName  	(new String(ch, start, length));}
			 else  if(nodeName.equals( "ParsingDate")) { map.setParsingDate  	(new String(ch, start, length));}
			 else  if(nodeName.equals( "TitleName")) { map.setTitleName  	(new String(ch, start, length));}
			 else  if(nodeName.equals( "FirstName")) { map.setFirstName  	(new String(ch, start, length));}
			 else  if(nodeName.equals( "Middlename")) { map.setMiddlename  	(new String(ch, start, length));}
			 else  if(nodeName.equals( "LastName")) { map.setLastName  	(new String(ch, start, length));}
			 else  if(nodeName.equals( "DateOfBirth")) { map.setDateOfBirth  	(new String(ch, start, length));}
			 else  if(nodeName.equals( "Gender")) { map.setGender  	(new String(ch, start, length));}
			 else  if(nodeName.equals( "FatherName")) { map.setFatherName  	(new String(ch, start, length));}
			 else  if(nodeName.equals( "MotherName")) { map.setMotherName  	(new String(ch, start, length));}
			 else  if(nodeName.equals( "MaritalStatus")) { map.setMaritalStatus  	(new String(ch, start, length));}
			 else  if(nodeName.equals( "Nationality")) { map.setNationality  	(new String(ch, start, length));}
			 else  if(nodeName.equals( "LanguageKnown")) { map.setLanguageKnown  	(new String(ch, start, length));}
			 else  if(nodeName.equals( "UniqueID")) { map.setUniqueID  	(new String(ch, start, length));}
			 else  if(nodeName.equals( "LicenseNo")) { map.setLicenseNo  	(new String(ch, start, length));}
			 else  if(nodeName.equals( "PassportNo")) { map.setPassportNo  	(new String(ch, start, length));}
			 else  if(nodeName.equals( "PanNo")) { map.setPanNo  	(new String(ch, start, length));}
			 else  if(nodeName.equals( "VisaStatus")) { map.setVisaStatus  	(new String(ch, start, length));}
			 else  if(nodeName.equals( "Email")) { map.setEmail  	(new String(ch, start, length));}
			 else  if(nodeName.equals( "AlternateEmail")) { map.setAlternateEmail  	(new String(ch, start, length));}
			 else  if(nodeName.equals( "Phone")) { map.setPhone  	(new String(ch, start, length));}
			 else  if(nodeName.equals( "FormattedPhone")) { map.setFormattedPhone(new String(ch, start, length));}			 
			 else  if(nodeName.equals( "Mobile")) { map.setMobile  	(new String(ch, start, length));}
			 else  if(nodeName.equals( "FormattedMobile")) { map.setFormattedMobile  	(new String(ch, start, length));}
			 else  if(nodeName.equals( "FaxNo")) { map.setFaxNo  	(new String(ch, start, length));}
			 else  if(nodeName.equals( "Address")) { map.setAddress  	(new String(ch, start, length));}
			 else  if(nodeName.equals( "City")) { map.setCity  	(new String(ch, start, length));}
			 else  if(nodeName.equals( "State")) { map.setState  	(new String(ch, start, length));}
			 else  if(nodeName.equals( "Country")) { map.setCountry  	(new String(ch, start, length));}
			 else  if(nodeName.equals( "ZipCode")) { map.setZipCode  	(new String(ch, start, length));}
			 else  if(nodeName.equals( "FormattedAddress")) { map.setFormattedAddress  	(new String(ch, start, length));}			 
			 else  if(nodeName.equals( "PermanentAddress")) { map.setPermanentAddress  	(new String(ch, start, length));}
			 else  if(nodeName.equals( "PermanentCity")) { map.setPermanentCity  	(new String(ch, start, length));}
			 else  if(nodeName.equals( "PermanentState")) { map.setPermanentState  	(new String(ch, start, length));}
			 else  if(nodeName.equals( "PermanentCountry")) { map.setPermanentCountry  	(new String(ch, start, length));}
			 else  if(nodeName.equals( "PermanentZipCode")) { map.setPermanentZipCode  	(new String(ch, start, length));}
			 else  if(nodeName.equals( "Category")) { map.setCategory  	(new String(ch, start, length));}
			 else  if(nodeName.equals( "SubCategory")) { map.setSubCategory  	(new String(ch, start, length));}
			 else  if(nodeName.equals( "CurrentSalary")) { map.setCurrentSalary  	(new String(ch, start, length));}
			 else  if(nodeName.equals( "ExpectedSalary")) { map.setExpectedSalary  	(new String(ch, start, length));}
			 else  if(nodeName.equals( "Qualification	")) { map.setQualification	(new String(ch, start, length));}
			 else  if(nodeName.equals( "Skills	")) { map.setSkills	(new String(ch, start, length));}
			 else  if(nodeName.equals( "Experience	")) { map.setExperience	(new String(ch, start, length));}
			 else  if(nodeName.equals( "CurrentEmployer")) { map.setCurrentEmployer  	(new String(ch, start, length));}
			 else  if(nodeName.equals( "JobProfile")) { map.setJobProfile  	(new String(ch, start, length));}
			 else  if(nodeName.equals( "TotalExperienceInYear")) { map.setTotalExperienceInYear  	(new String(ch, start, length));}
			 else  if(nodeName.equals( "TotalExperienceInMonths")) { map.setTotalExperienceInMonths  	(new String(ch, start, length));}
			 else  if(nodeName.equals( "TotalExperienceRange")) { map.setTotalExperienceRange  	(new String(ch, start, length));}
			 else  if(nodeName.equals( "GapPeriod")) { map.setGapPeriod  	(new String(ch, start, length));}
			 else  if(nodeName.equals( "PreferredLocation	")) { map.setPreferredLocation	(new String(ch, start, length));}
			 else  if(nodeName.equals( "NumberofJobChanged	")) { map.setNumberofJobChanged	(new String(ch, start, length));}
			 else  if(nodeName.equals( "AverageStay	")) { map.setAverageStay	(new String(ch, start, length));}
			 else  if(nodeName.equals( "LongestStay	")) { map.setLongestStay	(new String(ch, start, length));}
			 else  if(nodeName.equals( "Availability	")) { map.setAvailability	(new String(ch, start, length));}			
			 else  if(nodeName.equals( "Hobbies")) { map.setHobbies  	(new String(ch, start, length));}
			 else  if(nodeName.equals( "Objectives")) { map.setObjectives  	(new String(ch, start, length));}
			 else  if(nodeName.equals( "Achievements")) { map.setAchievements  	(new String(ch, start, length));}
			 else  if(nodeName.equals( "References")) { map.setReferences  	(new String(ch, start, length));}
			 else  if(nodeName.equals( "CoverLetter")) { map.setCoverLetter  	(new String(ch, start, length));}
			 else  if(nodeName.equals( "Certification")) { map.setCertification  	(new String(ch, start, length));}
			 else  if(nodeName.equals( "Publication")) { map.setPublication  	(new String(ch, start, length));}			
			 else  if(nodeName.equals( "Certification	")) { map.setCertification	(new String(ch, start, length));}
			 else  if(nodeName.equals( "CustomFields	")) { map.setCustomFields	(new String(ch, start, length));}
			 else  if(nodeName.equals( "EmailFrom	")) { map.setEmailFrom	(new String(ch, start, length));}
			 else  if(nodeName.equals( "EmailTo	")) { map.setEmailTo	(new String(ch, start, length));}
			 else  if(nodeName.equals( "EmailSubject	")) { map.setEmailSubject	(new String(ch, start, length));}
			 else  if(nodeName.equals( "EmailBody	")) { map.setEmailBody	(new String(ch, start, length));}
			 else  if(nodeName.equals( "EmailCC	")) { map.setEmailCC	(new String(ch, start, length));}
			 else  if(nodeName.equals( "EmailReplyTo	")) { map.setEmailReplyTo	(new String(ch, start, length));}
			 else  if(nodeName.equals( "EmailSignature	")) { map.setEmailSignature	(new String(ch, start, length));}
			 else  if(nodeName.equals( "DetailResume")) { map.setDetailResume  	(new String(ch, start, length));}
			 else  if(nodeName.equals( "htmlresume	")) { map.sethtmlresume	(new String(ch, start, length));}
			 else  if(nodeName.equals( "CandidateImageFormat")) { map.setCandidateImageFormat  	(new String(ch, start, length));}
			 else  if(nodeName.equals( "CandidateImageData	")) { map.setCandidateImageData	(new String(ch, start, length));}
			 else  if(nodeName.equals( "BehaviorSkills")) { map.setBehaviorSkills  	(new String(ch, start, length));}
			 else  if(nodeName.equals( "SoftSkills	")) { map.setSoftSkills	(new String(ch, start, length));}
			 
			 else if (nodeName.equals("Employer") || nodeName.equals("JobProfile")|| nodeName.equals("JobLocation")|| nodeName.equals("JobPeriod")|| nodeName.equals("StartDate")|| nodeName.equals("EndDate")|| nodeName.equals("JobDescription")) 
			 {
				 experiences.put(nodeName, new String(ch, start, length));
				  
			 }
			 else if (nodeName.equals("University") || nodeName.equals("Degree")|| nodeName.equals("Year")||nodeName.equals("UniversityName")||nodeName.equals("UniversityCity")||nodeName.equals("UniversityState")||nodeName.equals("UniversityCountry")||nodeName.equals("InstituteName")||nodeName.equals("InstituteCity")||nodeName.equals("InstituteState")||nodeName.equals("InstituteCountry")||nodeName.equals("Aggregate")) 
			 {
				 educations.put(nodeName, new String(ch, start, length)); 
			 }
			 else if (nodeName.equals("PersonName") || nodeName.equals("PositionTitle")|| nodeName.equals("CompanyName")|| nodeName.equals("Relation")|| nodeName.equals("Description")) 
			 {
				 linkedin.put(nodeName, new String(ch, start, length)); 
			 }
			 else if (nodeName.equals("Skill") || nodeName.equals("ExperienceInMonths")) 
			 {
				 skills.put(nodeName, new String(ch, start, length)); 
			 }
			 else if (nodeName.equals("ProjectName") || nodeName.equals("UsedSkills") || nodeName.equals("TeamSize")) 
			 {
				 projects.put(nodeName, new String(ch, start, length)); 
			 }
			 bfname = false;
		}
	}
};
     
     ByteArrayInputStream in = new ByteArrayInputStream(xmlString.getBytes());
     InputSource is = new InputSource();
     is.setEncoding("UTF-8");
     is.setByteStream(in);
     saxParser.parse(is, handler);
     } 
    catch (Exception e) 
    {
       e.printStackTrace();
     }
   
    
	 
	  
	  /*--------------------  Personal Info ends--------------------------------------------------------------	 */ 		  
	  
	  
	  
	  /* ---------------------------------------Skills  begins---------------------------------------------*/  
	  
	 
		
		
   }
 
}