package com.talentPool.parser;

import gate.Annotation;
import gate.AnnotationSet;
import gate.Corpus;
import gate.Document;
import gate.Factory;
import gate.FeatureMap;
import gate.Gate;
import gate.creole.SerialAnalyserController;
import gate.util.OffsetComparator;
import gate.util.persistence.PersistenceManager;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.talentPool.applicant.dataobject.ApplicantData;
import com.talentPool.applicant.dataobject.EducationalData;
import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.db.SimpleDataObject;
import com.talentPool.common.properties.GlobalApplicationProperties;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.properties.TPApplicationProperties;
import com.talentPool.common.utils.Utils;
import com.talentPool.parser.exception.EduExpParserException;

public final class GateApp {
	private static GateApp _gateApp;

	private SerialAnalyserController  eduExpApp;
	private Corpus eduExpCorpus;
	
	/**
	 * Initializes {@link Gate}, with resources provided in path specified in by value of property nlp.benchmark.folder.
	 * Loads {@link SerialAnalyserController} - eduExpApp and sets empty {@link Corpus} - eduExpCorpus. 
	 */
	private GateApp() {
		try {
			TPLogger.getLogger().info("************* Gate Initializing *************");
			
			String gateHome = Utils.concatFilePath(TPApplicationProperties.getProperty("installation.path"),TPApplicationProperties.getProperty("nlp.benchmark.folder"));
			Gate.setPluginsHome(new File(gateHome));
			Gate.setGateHome(new File(gateHome));
			Gate.setUserConfigFile(new File(gateHome, "user-gate.xml"));
			Gate.init();
			TPLogger.getLogger().debug("Gate.init();");
			
			/*Load Edu Exp Parser*/
			File gappEduExp = new File(gateHome, "nlp.xgapp");			
			eduExpApp = (SerialAnalyserController) PersistenceManager.loadObjectFromFile(gappEduExp);			
			Gate.setExecutable(eduExpApp);
			
			eduExpCorpus = Factory.newCorpus("Education Process Corpus");
			eduExpApp.setCorpus(eduExpCorpus);
			
			TPLogger.getLogger().info("************* Gate Initialized *************");
		} catch (Exception e) {
			TPLogger.getLogger().error(e.toString(), e);
		}
	}

	/**
	 * Static way of getting or creating singleton {@link GateApp} (Gate resource r engine.) 
	 * @return
	 */
	public static GateApp get() {
		if (_gateApp == null) {
			_gateApp = new GateApp();
		}
		return _gateApp;
	}
	
	/**
	 * Adds htmlContent(txtContent if htmlContent is null or blank) to corpus, 
	 * then {@link SerialAnalyserController} will executed to parse the date with rules defined in Gate resources.
	 * Parsed data will be available with {@link Document} object in the form of {@link AnnotationSet}.  
	 * @param applicantData
	 * @param htmlContent
	 * @param txtContent
	 * @throws EduExpParserException
	 */
	public void setParsedInfo(ApplicantData applicantData, String htmlContent, String txtContent) throws EduExpParserException {
		try {
			Document doc=null;
			if(htmlContent != null && htmlContent.length() > 0){
				doc = Factory.newDocument(htmlContent);					
			}
			else{
				doc = Factory.newDocument(txtContent);
			}	
			eduExpCorpus.add(doc);
			//eduExpApp.execute();
			executeEduExpProcess();
			setName(applicantData,doc);
			setEmails(applicantData,doc);
			setPhoneNos(applicantData,doc);
			setLocation(applicantData, doc);
			setEducation(applicantData, doc);
			setExperience(applicantData, doc);
			setCurrentCTC(applicantData, doc);
			setExpectedCTC(applicantData, doc);
			setSkills(applicantData, txtContent);
			
			Factory.deleteResource(doc);
			eduExpCorpus.clear(); // TODO: Move these clean up code to finally block 

		}catch (EduExpParserException ede) {
			throw ede;
		}  catch (Exception e) {
			TPLogger.getLogger().debug(e.toString(), e);
		}
	}
	
	/**
	 * Below code executes {@link SerialAnalyserController} eduExpApp, using a timer. 
	 * If execution is taking time more than 30s then interrupts {@link SerialAnalyserController}. 
	 * @throws EduExpParserException
	 * @throws InterruptedException
	 */
	private synchronized void executeEduExpProcess() throws EduExpParserException, InterruptedException {
		EduExpParserProcess eduExpParserProcess = new EduExpParserProcess(eduExpApp);
		Thread eduExpParserThread = new Thread(eduExpParserProcess);
		eduExpParserThread.start();
		eduExpParserThread.join();
		if(eduExpApp.isInterrupted()){
			throw new EduExpParserException("EduExpParser Interupted");
		}
	}
	
	/**
	 * Extracts education info from ({@link Document})doc {@link AnnotationSet} and updates {@link ApplicantData}
	 * @param applicantData
	 * @param doc
	 */
	private void setEducation(ApplicantData applicantData, Document doc) {
		try {
			AnnotationSet annAll = doc.getAnnotations();
			AnnotationSet result = annAll.get("MappedEduToken");
			EducationParser ep = new EducationParser();
			ArrayList results = ep.getListWithAliases("DEG");
			if (result != null && result.size() > 0) {
				List<Annotation> termList = new ArrayList(result);
				Collections.sort(termList, new OffsetComparator());
				ArrayList<EducationalData> educationalDetails = new ArrayList<EducationalData>();
				for(Annotation ann : termList){
					FeatureMap m = ann.getFeatures();
					EducationalData eData = new EducationalData();
					String degree = null;
					String institute = null;
					String year = null;
					
					if(m.containsKey("degree")){
						degree = m.get("degree").toString();
						SimpleDataObject degreeSdo = ep.getMatchedDO(results, degree);
						if (degreeSdo != null) {
							eData.setDegreeId(degreeSdo.getInt("id"));
						}
					}						
					if(m.containsKey("institute")){
						institute = m.get("institute").toString();
						eData.setInstitute(institute);
					}
					if(m.containsKey("year")){
						year = m.get("year").toString();
						try{
						java.sql.Date dtYOP = Utils.convertToSQLDate("01/01/" + year, "dd/MM/yyyy");
						eData.setYearOfPassing(dtYOP);
						}
						catch(Exception ex){
							TPLogger.getLogger().debug(ex.toString(), ex);
						}
					}
					educationalDetails.add(eData);
				}
				applicantData.setEducationalDetails(educationalDetails);
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(e);
		}
	}

	/**
	 * Extracts Name from ({@link Document})doc {@link AnnotationSet} and updates {@link ApplicantData}
	 * @param applicantData
	 * @param doc
	 */
	private void setName(ApplicantData applicantData, Document doc) {
		try {

			AnnotationSet annAll = doc.getAnnotations();
			
			FeatureMap map = Factory.newFeatureMap();
			map.put("category", "basicInfo");
			map.put("kind", "name");
			AnnotationSet result = annAll.get("ResumeToken", map);
			if (result.size() > 0) {
				List termList = new ArrayList(result);
				Collections.sort(termList, new AnnotationComparator());
				Annotation ann = (Annotation) termList.get(0);
				FeatureMap m = ann.getFeatures();
				String name_rule = m.get("rule").toString();
				if (name_rule.equalsIgnoreCase("name_extracted_from_email")) {
					name_rule = "name_extracted_from_email" + "  "
							+ m.get("email").toString();
					applicantData.setApplicantName(m.get("name").toString());
				} else {
					long start = ann.getStartNode().getOffset();
					long end = ann.getEndNode().getOffset();
					String annotation = doc.getContent().getContent(start, end)
							.toString();
					applicantData.setApplicantName(annotation);
				}
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(e);
		}
	}
	
	/**
	 * Extracts emails (Email1 & Email2)info from ({@link Document})doc {@link AnnotationSet} and updates {@link ApplicantData}
	 * @param applicantData
	 * @param doc
	 */
	private void setEmails(ApplicantData applicantData, Document doc) {
		try {

			AnnotationSet annAll = doc.getAnnotations();
			FeatureMap map = Factory.newFeatureMap();
			map.put("category", "basicInfo");
			map.put("kind", "email");
			AnnotationSet result = annAll.get("ResumeToken", map);
			if (result.size() > 0) {
				List termList = new ArrayList(result);
				Collections.sort(termList, new AnnotationComparator());
				Annotation ann = (Annotation) termList.get(0);
				long start = ann.getStartNode().getOffset();
				long end = ann.getEndNode().getOffset();
				String annotation = doc.getContent().getContent(start, end).toString();
				applicantData.setApplicantEmail1(annotation);
				if (result.size() > 1) {
					ann = (Annotation) termList.get(1);
					start = ann.getStartNode().getOffset();
					end = ann.getEndNode().getOffset();
					annotation = doc.getContent().getContent(start, end).toString();
					applicantData.setApplicantEmail2(annotation);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Extracts Phone No.'s from ({@link Document})doc {@link AnnotationSet} and updates {@link ApplicantData}
	 * @param applicantData
	 * @param doc
	 */
	private void setPhoneNos(ApplicantData applicantData, Document doc) {
		try {

			AnnotationSet annAll = doc.getAnnotations();
			FeatureMap map = Factory.newFeatureMap();
			map.put("category", "basicInfo");
			map.put("kind", "phone");
			AnnotationSet result = annAll.get("ResumeToken", map);
			if (result.size() > 0) {
				List termList = new ArrayList(result);
				Collections.sort(termList, new AnnotationComparator());
				Annotation ann = (Annotation) termList.get(0);
				long start = ann.getStartNode().getOffset();
				long end = ann.getEndNode().getOffset();
				String annotation = doc.getContent().getContent(start, end).toString();
		
				applicantData.setApplicantCellPhone(trimPhoneNo(annotation));
//				FeatureMap fm = ann.getFeatures();
//				candInfo.phone1Kind = fm.get("phoneKind").toString();
				if (result.size() > 1) {
					ann = (Annotation) termList.get(1);
					start = ann.getStartNode().getOffset();
					end = ann.getEndNode().getOffset();
					annotation = doc.getContent().getContent(start, end).toString();
					applicantData.setApplicantWorkPhone(trimPhoneNo(annotation));
//					fm = ann.getFeatures();
//					candInfo.phone2Kind = fm.get("phoneKind").toString();					
				}
				if (result.size() > 2) {
					ann = (Annotation) termList.get(2);
					start = ann.getStartNode().getOffset();
					end = ann.getEndNode().getOffset();
					annotation = doc.getContent().getContent(start, end).toString();
					applicantData.setApplicantHomePhone(trimPhoneNo(annotation));
//					fm = ann.getFeatures();
//					candInfo.phone3Kind = fm.get("phoneKind").toString();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Utility method, helps in trimming Phone No.s
	 * @param phoneNo
	 * @return
	 */
	private String trimPhoneNo(String phoneNo) {
		phoneNo = phoneNo.trim();
		if (phoneNo.endsWith("(") || phoneNo.endsWith(".")
				|| phoneNo.endsWith(")")) {
			phoneNo = phoneNo.substring(0, phoneNo.length() - 1);
		}
		phoneNo = phoneNo.trim();
		if (phoneNo.endsWith("(") || phoneNo.endsWith(".")
				|| phoneNo.endsWith(")")) {
			phoneNo = phoneNo.substring(0, phoneNo.length() - 1);
		}
		return phoneNo.trim();
	}
	
	/**
	 * Extracts Applicant City from ({@link Document})doc {@link AnnotationSet} and updates {@link ApplicantData}
	 * @param applicantData
	 * @param doc
	 */
	private void setLocation(ApplicantData applicantData, Document doc) {
		try {

			AnnotationSet annAll = doc.getAnnotations();
			
			FeatureMap map = Factory.newFeatureMap();
			map.put("category", "basicInfo");
			map.put("kind", "location");
			AnnotationSet result = annAll.get("ResumeToken", map);
			if (result.size() > 0) {
				List termList = new ArrayList(result);
				Collections.sort(termList, new AnnotationComparator());
				Annotation ann = (Annotation) termList.get(0);
				FeatureMap m = ann.getFeatures();
			
				long start = ann.getStartNode().getOffset();
				long end = ann.getEndNode().getOffset();
				String annotation = doc.getContent().getContent(start, end).toString();
				applicantData.setApplicantCity(annotation);
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Extracts Working since and current employer from ({@link Document})doc {@link AnnotationSet} and updates {@link ApplicantData}
	 * @param applicantData
	 * @param doc
	 */
	private void setExperience(ApplicantData applicantData, Document doc) {
		try {

			AnnotationSet annAll = doc.getAnnotations();
			AnnotationSet result = annAll.get("ExperienceToken");
			if (result != null && result.size() > 0) {
				List<Annotation> termList = new ArrayList(result);
				Collections.sort(termList, new OffsetComparator());
				
				String expDateFrom = null;
				String currentEmployer = null;
				for(Annotation ann : termList){
					FeatureMap m = ann.getFeatures();
					String fromDate = null;
					String isTillDate = null;
					String companyName = null;
					
					if(m.containsKey("isTillDate")){
						isTillDate = m.get("isTillDate").toString();
					}
					if(m.containsKey("company_name")){
						companyName = m.get("company_name").toString();
					}
					if(m.containsKey("fromDate")){
						fromDate = m.get("fromDate").toString();
					}
					
					if(isTillDate.equalsIgnoreCase("true")){
						currentEmployer = companyName;
					}else{
						expDateFrom = fromDate;
					}
				}
				if(!Utils.isBlankOrNull(expDateFrom)){
					java.sql.Date dateFrom = Utils.convertToSQLDate(expDateFrom, Utils.redDDMMYYYYFormat);
					applicantData.setApplicantWorkingSince(dateFrom);
				}
				if(!Utils.isBlankOrNull(currentEmployer)){
					applicantData.setApplicantCurrentEmployer(currentEmployer);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Extracts Applicants Current CTC from ({@link Document})doc {@link AnnotationSet} and updates {@link ApplicantData}
	 * @param applicantData
	 * @param doc
	 */
	private void setCurrentCTC(ApplicantData applicantData, Document doc) {
		try {

			AnnotationSet annAll = doc.getAnnotations();
			
			FeatureMap map = Factory.newFeatureMap();
			map.put("category", "basicInfo");
			map.put("kind", "salary_current");
			AnnotationSet result = annAll.get("ResumeToken", map);
			if (result.size() > 0) {
				List termList = new ArrayList(result);
				Collections.sort(termList, new AnnotationComparator());
				Annotation ann = (Annotation) termList.get(0);
				FeatureMap m = ann.getFeatures();
			
				long start = ann.getStartNode().getOffset();
				long end = ann.getEndNode().getOffset();
				String annotation = doc.getContent().getContent(start, end).toString();
				applicantData.setCurrentCTC(annotation);
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Extracts Applicant Expected CTC from ({@link Document})doc {@link AnnotationSet} and updates {@link ApplicantData}
	 * @param applicantData
	 * @param doc
	 */
	private void setExpectedCTC(ApplicantData applicantData, Document doc) {
		try {

			AnnotationSet annAll = doc.getAnnotations();
			
			FeatureMap map = Factory.newFeatureMap();
			map.put("category", "basicInfo");
			map.put("kind", "salary_expected");
			AnnotationSet result = annAll.get("ResumeToken", map);
			if (result.size() > 0) {
				List termList = new ArrayList(result);
				Collections.sort(termList, new AnnotationComparator());
				Annotation ann = (Annotation) termList.get(0);
				FeatureMap m = ann.getFeatures();
			
				long start = ann.getStartNode().getOffset();
				long end = ann.getEndNode().getOffset();
				String annotation = doc.getContent().getContent(start, end).toString();
				applicantData.setExpectedCTC(annotation);
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	/**
	 * Extracts Applicant skills from txtContent.
	 * Note: NLP is not involved in parsing Skills
	 * TODO: Move this method out from this class. As this class serves to extract data using NLP.
	 * @param applicantData
	 * @param txtContent
	 */
	private void setSkills(ApplicantData applicantData, String txtContent){
		SkillsParser skillsParser = new SkillsParser();
		int noOfSkiils = Integer.parseInt(GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_MAX_SKILLS_PARSED).trim());
		ArrayList<String> skills = skillsParser.getArrayListOfParsedSkillIdsAndNames(txtContent, noOfSkiils);
		applicantData.setApplicantSkills(skills);
	}
}
