package com.talentPool.applicant.bc;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map.Entry;

import com.talentPool.applicant.dataobject.ApplicantData;
import com.talentPool.applicant.dataobject.EducationalData;
import com.talentPool.applicant.dataobject.EmploymentHistoryData;
import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.properties.TPApplicationProperties;
import com.talentPool.common.utils.Utils;
import com.talentPool.common.utils.Exception.RChilliParseException;
import com.talentPool.masters.manager.MastersManager;
public class Soap {

	/**
	 * @param args
	 * @throws Exception 
	 */
	private static final String ServiceUrl = TPApplicationProperties.getProperty("talentpool.rchilli.ServiceUrl");
	private static final String userKey =TPApplicationProperties.getProperty("talentpool.rchilli.userKey");
	private static final String version = TPApplicationProperties.getProperty("talentpool.rchilli.version");
	private static final String subUserId = TPApplicationProperties.getProperty("talentpool.rchilli.subUserId");
	public Soap() {
		
		
	}

	
	
	static String sendRequest(String base64,String fileName,String userKey,String version,String subUserId ,String ServiceUrl)throws Exception 
	{  
	    URL url = new URL(ServiceUrl);  
	    HttpURLConnection rc = (HttpURLConnection)url.openConnection();  
	    rc.setRequestMethod("POST");  
	    rc.setDoOutput( true );  
	    rc.setDoInput( true );   
	    rc.setRequestProperty( "Content-Type", "text/xml; charset=utf-8" );  
	    String reqStr = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:rch=\"http://RchilliResumeParser\">"+
	    				"<soapenv:Header/>"+
	    					"<soapenv:Body>"+
	    						"<rch:parseResumeBinary>"+
	    							"<rch:filedata>"+base64+"</rch:filedata>"+
	    							" <rch:fileName>"+fileName+"</rch:fileName>"+
	    							"<rch:userkey>"+userKey+"</rch:userkey>"+
	    							"<rch:version>"+version+"</rch:version>"+
	    							" <rch:subUserId>"+subUserId+"</rch:subUserId>"+
	    						"</rch:parseResumeBinary>"+
	    					"</soapenv:Body>"+
	    				"</soapenv:Envelope>"; 
	    int len = reqStr.length();  
	    rc.setRequestProperty( "Content-Length", Integer.toString( len ) );  
	    rc.connect();      
	    OutputStreamWriter out = new OutputStreamWriter( rc.getOutputStream() );   
	    out.write( reqStr, 0, len );  
	    out.flush();  
 
	    InputStreamReader read = new InputStreamReader( rc.getInputStream() );  
	    StringBuilder sb = new StringBuilder();     
	    int ch = read.read();  
	    while( ch != -1 ){  
	      sb.append((char)ch);  
	      ch = read.read();  
	    }  
	    String   response = sb.toString();  
	    read.close();  
	    rc.disconnect();  
	    return response ;
	  }   
	
	
	public ApplicantData rchilliParseAndSetData(String originalDocPath,String textContent) throws RChilliParseException {
	
		ApplicantData applicantdata = new ApplicantData();
		
		String resumefileName = "";
		String encodedString = "";
		File file = null;
		File f = new File(originalDocPath);
		try {
			String fileName = "";
			//For parsing mail content
			if (f.isDirectory()) {
				fileName = "test.txt";
				encodedString = Base64.encodeBytes(textContent.getBytes());
			}
			//for parsing Resume file
			else if (f.isFile()) {
				fileName = f.getName();
				resumefileName = originalDocPath;
				file = new File(resumefileName);
				FileInputStream fin = null;

				try {
					fin = new FileInputStream(file);
				} catch (FileNotFoundException e) {

					e.printStackTrace();
				}

				byte[] fileContent = new byte[(int) file.length()];
				try {
					fin.read(fileContent);
				} catch (IOException e) {

					TPLogger.getLogger().error("error while parsing resume file", e);
				}
				try{
					fin.close();
				} catch (IOException e) {
					TPLogger.getLogger().error("error while parsing resume file", e);

				}
				encodedString = Base64.encodeBytes(fileContent);
			
			}

			String RchilliXML = sendRequest(encodedString, fileName,
					userKey, version, subUserId, ServiceUrl).replace("&lt;",
					"<").replace("&gt;", ">");
			if (!RchilliXML.contains("<error>")) {
				RchilliMapFields response = new RchilliMapFields();
				XMLFile readResult = new XMLFile(response);
				readResult.XmlRead(RchilliXML);
				String name="";
				String firstName=response.getFirstName();
				String lastName=response.getLastName();
				
				if(!Utils.isBlankOrNull(firstName)&&!Utils.isBlankOrNull(lastName)){
					name=firstName+" "+lastName;
				}
				else{
					name=firstName;
				}
				String email=response.getEmail();
				String alternateEmail=response.getAlternateEmail();
				String cellPhone=response.getFormattedMobile();
				String applicantHomePhone=response.getFormattedPhone();
				String applicantAddress=response.getFormattedAddress();
				String city=response.getCity();
				String currentEmployer=response.getCurrentEmployer();
				
				String currentCTC=response.getCurrentSalary();
				String expectedCTC=response.getExpectedSalary();
			//	String noticePeriod=map.getNoticePeriod();
				String applicantWorkingSince=response.getTotalExperienceInYear();
				
				String jobProfile=response.getJobProfile();
				String passportNo=response.getPassportNo();
				//String originalResumepath=map.gethtmlresume()
				if(!Utils.isBlankOrNull(passportNo)){
					applicantdata.setPassportNumber(passportNo);
				}
				if(!Utils.isBlankOrNull(email)){
					applicantdata.setApplicantEmail1(email);
				}
				if(!Utils.isBlankOrNull(name)){
					applicantdata.setApplicantName(name);
				}
				if(!Utils.isBlankOrNull(alternateEmail)){
					applicantdata.setApplicantEmail2(alternateEmail);
				}
				if(!Utils.isBlankOrNull(cellPhone)){
					String phones[]=cellPhone.split("\\s*,\\s*");
					if(phones!=null&&phones.length>0){
					applicantdata.setApplicantCellPhone(phones[0]);
					}
					else{
						applicantdata.setApplicantCellPhone(cellPhone);
					}
				}
				if(!Utils.isBlankOrNull(applicantHomePhone)){
					String phones[]=applicantHomePhone.split("\\s*,\\s*");
					if(phones!=null&&phones.length>0){
					applicantdata.setApplicantHomePhone(phones[0]);
					}
					else{
						applicantdata.setApplicantHomePhone(applicantHomePhone);
					}
				}
				if(!Utils.isBlankOrNull(city)){
					applicantdata.setApplicantCity(city);
				}
				if(!Utils.isBlankOrNull(currentEmployer)){
					applicantdata.setApplicantCurrentEmployer(currentEmployer);
				}
				if(!Utils.isBlankOrNull(currentCTC)){
					applicantdata.setCurrentCTC(currentCTC);
				}
				if(!Utils.isBlankOrNull(expectedCTC)){
					applicantdata.setExpectedCTC(expectedCTC);
				}
				/*if(!Utils.isBlankOrNull(noticePeriod)){
					applicantdata.setNoticePeriod(noticePeriod);
				}*/
				if(!Utils.isBlankOrNull(applicantWorkingSince)){
					applicantdata.setApplicantExperience(applicantWorkingSince);
					
				}
				
				
				
				//applicantdata.setPassportNumber(map.getPassportNo());
				applicantdata.setDateOfBirth(Utils.convertToSQLDate(response.getDateOfBirth(), Utils.regEUDateFormat));
				
				ArrayList<HashMap<String, String>> qualification = response.getQualificationSegrigation();
				ArrayList<HashMap<String, String>> workHistory = response.getExperienceSegrigation();
				ArrayList<EmploymentHistoryData> employmentHistoryDetails=new ArrayList<EmploymentHistoryData>();
				ArrayList<EducationalData> educationalDetails=new ArrayList<EducationalData> ();
				MastersManager mastersManager = new MastersManager();
				if (qualification != null && qualification.size() > 0) {
					int sz = qualification.size();
					String[] eduYop = new String[sz];
					String[] eduInstitute = new String[sz];
					String[] eduDegree = new String[sz];
					String[] eduMajor = new String[sz];
					String[] eduGrades = new String[sz];
					for (int i = 0; i < sz; i++) {
						HashMap<String, String> eData = qualification.get(i);
						eduYop[i] = eData.get("Year");
						eduInstitute[i] = eData.get("UniversityName");
						String degreeTitle=eData.get("Degree");
						if (!Utils.isBlankOrNull(eData.get("Degree"))){
	    					eduDegree[i] = mastersManager.getDegreeId(eData.get("Degree"));
	    				}
						
						if (!Utils.isBlankOrNull(eData.get("Branch"))){
							eduMajor[i] = mastersManager.getBranchId(eData.get("Branch"));
	    				}
						eduGrades[i] = eData.get("Aggregate");
						EducationalData educationalData=new EducationalData();
						//educationalData.setDegreeId(degreeId);
						if (!Utils.isBlankOrNull(degreeTitle)){
							educationalData.setDegreeTitle(degreeTitle);
	    				}
						if (!Utils.isBlankOrNull(eduDegree[i])){
							String edDegree=eduDegree[i].trim();
							if(Utils.isInteger(edDegree)){
							int degree=Integer.parseInt(eduDegree[i]);
							educationalData.setDegreeId(degree);
							}
	    				}
						/*if (!Utils.isBlankOrNull(eduInstitute[i])){
	    					int instiId = mastersManager.getInstituteId(Integer.parseInt(eduInstitute[i]));
	    				}*/
						educationalData.setInstituteId(eduInstitute[i]);
						educationalData.setInstitute(eduInstitute[i]);
						//educationalData.setMajorId(majorId);
						educationalData.setMajor(eduMajor[i]);
						if (!Utils.isBlankOrNull(eduYop[i])){
							educationalData.setYearOfPassing(Utils.convertToSQLDate(eduYop[i],Utils.regYYYYFormat));
	    				}
						
						if(!Utils.isBlankOrNull(eduGrades[i])){
							Double percentage=Double.parseDouble(eduGrades[i]);
							if(percentage>=60){
						educationalData.setGrade("1st");
							}
							else{
								educationalData.setGrade("2nd");
							}
						}
						educationalDetails.add(educationalData);
					}
					applicantdata.setEducationalDetails(educationalDetails);
				}
				if (workHistory != null && workHistory.size() > 0) {
					int sz = workHistory.size();
					String[] employmentFromDate = new String[sz];
					String[] employmentToDate = new String[sz];
					String[] employmentEmployerId = new String[sz];
					String[] employmentExperience = new String[sz];
					String[] employmentDesignationId = new String[sz];
					for (int i = 0; i < sz; i++) {
						HashMap<String, String> eData = workHistory.get(i);
						employmentFromDate[i] = eData.get("StartDate");
						employmentToDate[i] = eData.get("EndDate");
						employmentEmployerId[i] = eData.get("Employer");
						employmentDesignationId[i] = eData.get("JobProfile");
						Date fromDate=new Date();
						Date  toDate=new Date();
						if(!Utils.isBlankOrNull(employmentFromDate[i])&& !Utils.isBlankOrNull(employmentToDate[i]))
						{
						if(!Utils.isBlankOrNull(employmentFromDate[i])){
							fromDate=Utils.convertToDate(employmentFromDate[i],Utils.regEUDateFormat);
						}
						if(!Utils.isBlankOrNull(employmentToDate[i])){
							toDate=Utils.convertToDate(employmentToDate[i],Utils.regEUDateFormat);
						}
						employmentExperience[i] = Utils.getDateDifferenceInYearMonthString(fromDate,toDate,null);
						}
						else{
							employmentExperience[i]="0";
						}
						
						EmploymentHistoryData employmentHistoryData=new EmploymentHistoryData(employmentFromDate[i],employmentToDate[i],employmentEmployerId[i],employmentDesignationId[i],employmentExperience[i]);
						employmentHistoryData.setEmployerFromDate(Utils.convertToSQLDate(employmentFromDate[i],Utils.regEUDateFormat));
						employmentHistoryData.setEmployerToDate(Utils.convertToSQLDate(employmentToDate[i],Utils.regEUDateFormat));
						employmentHistoryData.setEmployerName(employmentEmployerId[i]);
						employmentHistoryData.setEmployerExperience(employmentExperience[i]);
						employmentHistoryData.setDesignationName(employmentDesignationId[i]);
						//employmentHistoryData.setEmployerId(employmentEmployerId[i]);
						employmentHistoryDetails.add(employmentHistoryData);
					}
					applicantdata.setEmploymentHistoryDetails(employmentHistoryDetails);
					
					
				}
				ArrayList<HashMap<String, String>> skills = response.getSkillSegrigation();
				if (skills != null) {
					StringBuffer skillList = new StringBuffer();
					String skillIds = "";

					for (int i = 0; i < skills.size(); i++) {
						HashMap<String, String> eData = skills.get(i);

						for (Entry<String, String> entry : eData.entrySet()) {

							skillList.append(entry.getValue());
							String skillId = mastersManager.getSkillId(entry.getValue());
							if (!Utils.isBlankOrNull(skillId)){
								if (Utils.isBlankOrNull(skillIds)){
									skillIds = skillId;
								}else {
									skillIds = skillIds + "," + skillId;
								}
							}
						}

						if (i < skills.size() - 1) {
							skillList.append(", ");

						}
					}
					applicantdata.setSkillIds(skillIds);
					/*applicantdata.setPrimarySkills(skillList.toString());
					applicantdata.setPrimarySkillIds(skillList.toString());*/
				}

			}
			else{
				if(RchilliXML.contains("<errorcode>")){
					int length="<errorcode>".length();
					int index=RchilliXML.indexOf("<errorcode>");
					int lastIndex=RchilliXML.lastIndexOf("</errorcode>");
					int beginIndex=index+length;
					String errorCode=RchilliXML.substring(beginIndex, lastIndex);
					TPLogger.getLogger().error("Error occured while Parsing Resume Using Rchilli"+"ErrorCode"+errorCode);
				}
				if(RchilliXML.contains("<errormsg>")){
					int length="<errormsg>".length();
					int index=RchilliXML.indexOf("<errormsg>");
					int lastIndex=RchilliXML.lastIndexOf("</errormsg>");
					int beginIndex=index+length;
					String msg=RchilliXML.substring(beginIndex, lastIndex);
					TPLogger.getLogger().error("Error occured while Parsing Resume Using Rchilli"+"ErrorMsg"+msg);
					throw new RChilliParseException(msg);
				}
			}
		} catch (RChilliParseException e) {
			throw e;
		}
		catch (Exception e) {
			
			TPLogger.getLogger().error("problem occured while parsing resume",e);
			
		}
		return applicantdata;
	}

}
