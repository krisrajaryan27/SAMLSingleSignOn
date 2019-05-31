/**
 * 
 */
package com.talentPool.demo;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import com.talentPool.applicant.dataobject.ApplicantData;
import com.talentPool.applicant.dataobject.EducationalData;
import com.talentPool.applicant.manager.ApplicantManager;
import com.talentPool.common.utils.CommonUtils;
import com.talentPool.common.utils.Utils;
import com.talentPool.custom.constants.CustomFieldConstants;
import com.talentPool.custom.dataobject.CustomFieldData;
import com.talentPool.custom.manager.CustomFieldManager;
import com.talentPool.parser.converter.GenericConverter;
import com.talentPool.selectionProcess.manager.SelectionProcessManager;


/**
 * @author Ajeet
 *
 */
public class createRandomApplicant {
	public static ArrayList<String> sourceIds = CommonUtils.getSourceIds();
	public static ArrayList<String> skillIds = CommonUtils.getSkillIds();
	public static ArrayList<String> degreeIds = CommonUtils.getDegreeIds();
	public static ArrayList<String> branchIds = CommonUtils.getBranchIds();
	
	public static String positionId = null;
	public static String userId1 = null;
	public static String userId2 = null;
	
	
	public static File[] listOfFiles = createDemoData.folder.listFiles();
	
	public static GenericConverter conv = new GenericConverter();
	public static Random generator = new Random();
	
	public static String name = "Applicant";
	public static int start = 19043 ;
	public static int count = 100000 ;
	
	public static void main(String[] args) {
		createApplicants();
//		try{
//		SelectionProcessManager selectionProcessManager = new SelectionProcessManager();
//		selectionProcessManager.saveSelectionProcessResult("124", "29", "124", "126", "1", null, null, false, null, null, null, null, 
//				null, null, null, null, null, null, null);
//		}catch (Exception e) {
//			e.printStackTrace();
//		}
	}

	public createRandomApplicant(String posIds, int str, int cnt){
		try{
			this.positionId = posIds;
			this.start=str;
			this.count = cnt;
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	 
	public static void createApplicants() {
		try {	
			ApplicantManager applicantManager = new ApplicantManager();
			SelectionProcessManager selectionProcessManager = new SelectionProcessManager();
			for (int i = start; i < count ; i++) {
				String applicantId = createApplicant("1",i);
				System.out.println(i + " applicantId = "+applicantId);
				
				//create applicant and start shortlist them
//				int id = generator.nextInt(createDemoData.position);
				
//				System.out.println("positionIds[id]="+positionIds[id]);
				//selectionProcessManager.shortListApplicant(applicantId, positionId, null, "1", true);
				
				/*String currentStep = applicantManager.getApplicantCurrentStepId(applicantId);
				if(!Utils.isBlankOrNull(currentStep)){
					int moveToStep =  (Integer.parseInt(currentStep)+1);
					selectionProcessManager.saveSelectionProcessResult(applicantId, positionId, currentStep, ""+moveToStep, "1", null, null, false, null, null, null, null, 
						null, null, null, null, null, null, null);
				}*/
//				if(i%2 == 0){ //move alternate applicant to hire step = step+2  
//					currentStep = applicantManager.getApplicantCurrentStepId(applicantId);
//					if(!Utils.isBlankOrNull(currentStep)){
//						int moveToStep =  (Integer.parseInt(currentStep)+1);
//						selectionProcessManager.saveSelectionProcessResult(applicantId, positionIds[id], currentStep, ""+moveToStep, userId2, null, null, false, null, null, null, null, 
//							null, null, null, null, null, null, null);
//					}
//				}			
			}
		} catch (Exception e) {
			e.printStackTrace();
		}			
	}

	private static File getRandomFile() {
		try {
			int ranInt = generator.nextInt(listOfFiles.length);
			int i = ranInt;
			if( i < listOfFiles.length) {
				if (listOfFiles[i].isFile()) {
					return listOfFiles[i];
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String createApplicant(String userId, int i){
		String applicantId = "";
		try {
			ApplicantManager applicantManager = new ApplicantManager();
			ApplicantData aData = new ApplicantData();
			aData = getApplicantDataConstructed(aData, i);
			
			aData.setUserId(userId);
			String skillId = "";
			if(skillIds != null && skillIds.size()>0){
				int ranSkill = generator.nextInt(skillIds.size());
				if(!Utils.isBlankOrNull(skillIds.get(ranSkill))){
					skillId = skillIds.get(ranSkill);
				}
			}

			applicantId = applicantManager.addApplicant(aData, skillId);
			// add Note
			applicantManager.addNote(applicantId, userId, "Default Note");
			
//			// Add Categories
//			if (!Utils.isBlankOrNull(appForm.getCategoryIds()) && !Utils.isBlankOrNull(applicantId)) {
//				CategoryManager categoryManager = new CategoryManager();
//				categoryManager.applyCategories(userId, applicantId, appForm.getCategoryIds(), null);
//			}
			if (Utils.isBlankOrNull(applicantId)) {
				applicantId = "0";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return applicantId;
	}
	
		
	
	private static ApplicantData getApplicantDataConstructed(ApplicantData aData, int i) {
		aData = new ApplicantData();
		int ranInt = generator.nextInt(10);
		int ranInt2 = generator.nextInt(10);
		
		aData.setApplicantName(name+i);
		aData.setApplicantCity("Pune");
		aData.setApplicantEmail1(ranInt+ "A1"+ ranInt2+i+"@A"+ ranInt + ".com");
		aData.setApplicantEmail2(ranInt2 +"B2"+ ranInt +i+"@B"+ ranInt2 +".com");
		aData.setApplicantWorkPhone("98"+ranInt+ranInt2+"89"+ranInt+"25"+ranInt2);
		aData.setApplicantHomePhone("98"+ranInt2+ranInt+"79"+ranInt+"35"+ranInt2);
		aData.setApplicantCellPhone("98"+ranInt+ranInt2+"69"+ranInt+"45"+ranInt2);
		aData.setApplicantCurrentEmployer("Talentica");
		aData.setCurrentCTC(""+ranInt2+ranInt);
		aData.setExpectedCTC(""+ ranInt+ ranInt2);
		aData.setApplicantOriginalResumePath("");
		java.sql.Date dtWorkingFrom = null;
		String fresher = "0";
			try {
				int months = ranInt * 12;
				dtWorkingFrom = Utils.convertDateToSQLDate(Utils.adjustDateBy(new Date(), Calendar.MONTH, -months));
			} catch (Exception e) {
				e.printStackTrace();
			}		
		aData.setApplicantIsFresher(fresher);
		aData.setApplicantWorkingSince(dtWorkingFrom);

		
		if(sourceIds != null && sourceIds.size()>0){
			int ranSource = generator.nextInt(sourceIds.size());
			if(!Utils.isBlankOrNull(sourceIds.get(ranSource))){
				aData.setApplicantSourceId(Integer.parseInt(sourceIds.get(ranSource)));
			}
		}

		ArrayList<EducationalData> educationalDetails = new ArrayList<EducationalData>();
		EducationalData eData = new EducationalData();
		Date tmpDt = null;
		if (!Utils.isBlankOrNull("2006")) {
			tmpDt = Utils.convertToDate("1" + Utils.dateDescSeparator + "1" + Utils.dateDescSeparator + "2006", Utils.redDDMMYYYYFormat);
			if (tmpDt != null) {
				eData.setYearOfPassing(new java.sql.Date(tmpDt.getTime()));
			}
		} else {
			eData.setYearOfPassing(null);
		}
		eData.setInstitute("IIT kgp");
		if(degreeIds != null && degreeIds.size()>0){
			int ranDegree = generator.nextInt(degreeIds.size());
			if(!Utils.isBlankOrNull(degreeIds.get(ranDegree))){
				eData.setDegreeId(Integer.parseInt(degreeIds.get(ranDegree)));
			}
		}	
		if(branchIds != null && branchIds.size()>0){
			int ranBranch = generator.nextInt(branchIds.size());
			if(!Utils.isBlankOrNull(branchIds.get(ranBranch))){
				eData.setMajorId(Integer.parseInt(branchIds.get(ranBranch)));
			}
		}		
		educationalDetails.add(eData);
		aData.setEducationalDetails(educationalDetails);

		CustomFieldManager customFieldManager = new CustomFieldManager();
		ArrayList customFields = customFieldManager.getCustomFieldsForInputAllowed(CustomFieldConstants.ENTITY_TYPE_APPLICANT, CustomFieldConstants.INPUT_ALLOWED, true);
		for (int j = 0; j < customFields.size(); j++) {
			CustomFieldData cData = (CustomFieldData)customFields.get(j);
			
			if(cData.getFieldName().equals("app_dateOfBirth")){
				String[] vals = new String[1];
//				vals[0] = Utils.getDateConvertedToString(new Date(), Utils.regDDMMYYYYHHMMSSEFromat);
				vals[0] = "02/07/200"+ranInt;
				cData.setFieldValues(vals);
				
//				cData.setFieldDateValue(Utils.convertDateToSQLDate(new Date()));
			}
			if(cData.getFieldName().equals("app_passportnumber")){
				String[] vals = new String[1];
				vals[0] = "XYZ"+ranInt+"485746"+ranInt2;
				cData.setFieldValues(vals);
			}
			if(cData.getFieldName().equals("app_gender")){
				String[] vals = new String[1];
				vals[0] = "Male";
				cData.setFieldValues(vals);
			}
		}
		aData.setCustomFields(customFields);

		
		//	set resume path
		File file = getRandomFile();
		try{
			if(file != null){
				String fileName = file.getName();
				String textContent = "";
				if (fileName.endsWith(".doc")) {
					fileName = fileName.substring(0, fileName.length() - 4);
					aData.setApplicantOriginalResumePath("resume/"+fileName+".html");
					aData.setApplicantOriginalDocPath("resume/"+fileName+".doc");
					/*try{
						textContent = conv.convert(file.toString());
						aData.setApplicantTextResume(textContent);
					}catch (Exception e) {
						e.printStackTrace();
					}*/
				}
				if (fileName.endsWith(".html")) {
					fileName = fileName.substring(0, fileName.length() - 5);
					aData.setApplicantOriginalResumePath("resume/"+fileName+".html");
					aData.setApplicantOriginalDocPath("resume/"+fileName+".doc");
					/*try{
						textContent = conv.convert(file.toString());
						aData.setApplicantTextResume(textContent);
					}catch (Exception e) {
						e.printStackTrace();
					}*/
				}
				if (fileName.endsWith(".txt")) {
					fileName = fileName.substring(0, fileName.length() - 4);
					aData.setApplicantOriginalResumePath("resume/"+fileName+".html");
					aData.setApplicantOriginalDocPath("resume/"+fileName+".doc");
					/*try{
						textContent = conv.convert(file.toString());
						aData.setApplicantTextResume(textContent);
					}catch (Exception e) {
						e.printStackTrace();
					}*/
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return aData;
	}
	
}
