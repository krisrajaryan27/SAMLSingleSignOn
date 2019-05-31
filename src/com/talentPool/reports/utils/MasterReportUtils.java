/**
 * 
 */
package com.talentPool.reports.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.jsoup.Jsoup;

import com.talentPool.applicant.constants.ImportConfigurationConstants;
import com.talentPool.applicant.manager.ImportConfigurationManager;
import com.talentPool.common.CommonConstants;
import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.db.SimpleDataObject;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.properties.TPApplicationProperties;
import com.talentPool.common.properties.TPLabels;
import com.talentPool.common.utils.Utils;
import com.talentPool.custom.constants.CustomFieldConstants;
import com.talentPool.custom.dataobject.CustomFieldData;
import com.talentPool.custom.manager.CustomFieldManager;
import com.talentPool.masters.dataobject.MasterStepData;
import com.talentPool.masters.manager.StepManager;
import com.talentPool.positions.PositionConstants;
import com.talentPool.positions.manager.PositionManager;
import com.talentPool.reports.ReportConstants;
import com.talentPool.reports.dataobject.FilterData;
import com.talentPool.reports.dataobject.MasterReportData;
import com.talentPool.reports.manager.MasterReportManager;
import com.talentPool.selectionProcess.SelectionProcessConstants;
import com.talentPool.user.dataobject.LoginData;
import com.talentPool.user.manager.LoginManager;
import com.talentPool.user.manager.PermissionSet;

/**
 * @author Ajeet
 *
 */
public class MasterReportUtils {

	public static String generateMasterReport(ArrayList<MasterReportData> applicantData, FilterData filterData,PermissionSet permissionSet) throws IOException {
		String newFileName = "";
		try {
			Date fromDate = Utils.convertToDate(filterData.getFromDate(), "dd/MM/yyyy");
			Date toDate = Utils.convertToDate(filterData.getToDate(), "dd/MM/yyyy");
			
			String filePath = Utils.concatFilePath(ReportConstants.JRXML_FOLDER_PATH, TPLabels.getLabel("master.report.file_name"));
			POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(filePath));
			HSSFWorkbook wb = new HSSFWorkbook(fs);
			
			//Make USER -SRF PROGRESS sheet
			setDateRangeForSheets(wb, fromDate, toDate);
			
			// Make Master Sheet
			HSSFSheet sheet = wb.getSheet(TPLabels.getLabel("master.report.master_sheet_name"));

			// Create a new font and alter it.
			HSSFFont font = wb.createFont();
			font.setFontHeightInPoints((short) 8);
			font.setFontName(HSSFFont.FONT_ARIAL);

			// define cellStyles
			HSSFCellStyle cellStyleDate = wb.createCellStyle();
			cellStyleDate.setDataFormat(HSSFDataFormat.getBuiltinFormat("d-mmm-yy"));
			cellStyleDate.setBorderRight((short) 20);
			cellStyleDate.setFont(font);

			HSSFCellStyle cellStyleTime = wb.createCellStyle();
			cellStyleTime.setDataFormat(HSSFDataFormat.getBuiltinFormat("h:mm AM/PM"));
			cellStyleTime.setFont(font);

			HSSFCellStyle cellStyleFont = wb.createCellStyle();
			cellStyleFont.setFont(font);
			
			// delete all previous data
			Iterator rowItr = sheet.rowIterator();
			while (rowItr.hasNext()) {
				HSSFRow row = (HSSFRow) rowItr.next();
				Iterator cellItr = row.cellIterator();
				while (cellItr.hasNext()) {
					HSSFCell cell = (HSSFCell) cellItr.next();
					cell.setCellValue("");
				}
			}

			// Create header Row.
			HSSFRow headerRow = sheet.createRow((short) 0);
			createHeaderRow(wb, headerRow);


			MasterReportManager masterReportManager = new MasterReportManager();
			ArrayList<SimpleDataObject> joiningList = masterReportManager.getPositionIdsWithJoiningCandidates();
			CustomFieldManager customFieldManager = new CustomFieldManager();
			for (int i = 0; i < applicantData.size(); i++) {
				MasterReportData mrd = applicantData.get(i);

				String applicantId = mrd.getApplicantId();
				String positionId = mrd.getPositionId();
				String processId = mrd.getProcessId();
				String backout = ""; // Final Status
				String rejected = "Rejected";
				String joined = "Joined";
				String offered = "Offered";
				String inprocess = "Inprocess";
				String select = "SELECT";
				String reject = "REJECT";
				String finalStatus = "";
				String positionStatus = "";
				String positionNote = mrd.getPositionNote();
				String cvShortlistedSatus = "F/B Await";
				String recruiter = "";
				Date currentDate = new Date();
				Date offerDate = null;
				Date shortlistedDate = null;
				Date cvSubmittedDate = mrd.getProcessDateCreated();
				Date openDate = mrd.getPositionDateCreated();
				Date hireByDate = mrd.getPositionDateExpiry();
				Date DOJ = mrd.getApplicantDateJoined();
				Date rejectedDate = null;
				Date workingSince = mrd.getApplicantWorkingSince();

				int aging = Integer.parseInt(Utils.getDateDiffenence(openDate, new Date()));
				// for Join candidate Aging = (joining date-position create date)
				
				

				// set position status
				positionStatus = getPositionStatus(mrd.getPositionStatus(), positionId, joiningList);
				
				int openDays = Integer.parseInt(Utils.getDateDiffenence(openDate, new Date()));
				// for close position openDays = close date - position create date
				if(positionStatus.equals("Closed") || positionStatus.equals("Cancelled")){
					Date closedDate = mrd.getPositionDateClosed();
					if(closedDate!=null){
						openDays = Integer.parseInt(Utils.getDateDiffenence(openDate, closedDate));
					}
				}

				// get applicant step data
				ArrayList<MasterReportData> iData = masterReportManager.getApplicantInteractionData(applicantId, positionId, processId);
				String[] stepName = { "", "", "", "", "" };
				String[] interviewers = { "", "", "", "", "" };
				Date[] interviewDate = { null, null, null, null, null };
				String[] interviewStatus = { "", "", "", "", "" };
				boolean checkOfferDate = true;
				boolean checkShortlistedDate = true;
				boolean checkRecruiter = true;
				int count = 0; // counter to get only 5 scheduled steps
				for (int j = 0; j < iData.size(); j++) {
					MasterReportData interactionData = iData.get(j);
					String stepIdTo = interactionData.getPositionStepIdTo();
					String stepLevelTo = interactionData.getPositionStepLevelTo();
					String stepLevelFrom = interactionData.getPositionStepLevelFrom();
					if (count < 5 && interactionData.getPositionStepIsscheduled() != null && (interactionData.getPositionStepIsscheduled()).equals(String.valueOf(PositionConstants.STEP_SCHEDULED))) {
						interviewers[count] = interactionData.getInterviewers();
						stepName[count] = interactionData.getPositionStepTitle();
						Date inDate = interactionData.getAppointmentFromDate();
						if (inDate != null) {
							interviewDate[count] = inDate;
							if (!stepIdTo.equals(SelectionProcessConstants.STEP_NOT_INTERESTED_REJECT) && !stepIdTo.equals(SelectionProcessConstants.STEP_POSITION_CLOSED_REJECT)
									&& !stepIdTo.equals(SelectionProcessConstants.STEP_NOT_ATTENDED) && !stepIdTo.equals(SelectionProcessConstants.STEP_REJECT)) {
								interviewStatus[count] = select;
							} else {
								interviewStatus[count] = reject;
							}
						}
						count++;
					}
					
					// to get recruiter
					if (checkRecruiter) {
						if (interactionData.getRecruiter() != null) {
							recruiter = interactionData.getRecruiter();
						}
						checkRecruiter = false;
					}

					// to get offered date
					if (checkOfferDate && !Utils.isBlankOrNull(stepLevelFrom) && stepLevelFrom.equals(PositionConstants.STEP_LEVEL_ACCEPT)) {
						if (interactionData.getProcessDateCreated() != null) {
							offerDate = interactionData.getProcessDateCreated();
						}
						checkOfferDate = false;
					}
					// to get cvSubmitted Date
					if (j == 0) { // first step
						if (interactionData.getProcessDateCreated() != null) {
							cvSubmittedDate = interactionData.getProcessDateCreated();
						}

					}

					// to get technical shortlisted date
					if (checkShortlistedDate && !Utils.isBlankOrNull(stepLevelTo)
							&& (stepLevelTo.equals(PositionConstants.STEP_LEVEL_SELECT) || stepLevelTo.equals(PositionConstants.STEP_LEVEL_ACCEPT))) {
						if (interactionData.getProcessDateCreated() != null) {
							shortlistedDate = interactionData.getProcessDateCreated();
							cvShortlistedSatus = "S/L";
						}
						checkShortlistedDate = false;
					}

					if (j == iData.size() - 1) { // last step to get final status
						if (!Utils.isBlankOrNull(stepIdTo)) {
							if (stepIdTo.equals(SelectionProcessConstants.STEP_NOT_INTERESTED_REJECT) || stepIdTo.equals(SelectionProcessConstants.STEP_POSITION_CLOSED_REJECT)
									|| stepIdTo.equals(SelectionProcessConstants.STEP_NOT_ATTENDED) || stepIdTo.equals(SelectionProcessConstants.STEP_REJECT)) {
								finalStatus = rejected;
								rejectedDate = interactionData.getProcessDateCreated();
								if (!Utils.isBlankOrNull(stepLevelFrom) && stepLevelFrom.equals(PositionConstants.STEP_LEVEL_ACCEPT)) {
									backout = "Backout";
								}
							} else if (stepIdTo.equals(SelectionProcessConstants.STEP_JOIN)) {
								finalStatus = joined;
								if (DOJ != null) {
									aging = Integer.parseInt(Utils.getDateDiffenence(openDate, DOJ));
								}
							} else {
								if (!Utils.isBlankOrNull(stepLevelTo) && stepLevelTo.equals(PositionConstants.STEP_LEVEL_ACCEPT)) {
									finalStatus = offered;
								} else {
									finalStatus = inprocess;
								}
							}
						}
					}
				}
				if (checkShortlistedDate) {
					if (finalStatus.equals(rejected)) {
						cvShortlistedSatus = "Reject";
					}
				}
				
				
				// get applicant custom fields.
				ArrayList<CustomFieldData> cData = customFieldManager.getCustomFieldDataForEntity(applicantId, CustomFieldConstants.ENTITY_TYPE_APPLICANT);
				int nooffields = CustomFieldManager.getMaxRank(CustomFieldConstants.ENTITY_TYPE_APPLICANT);
				ArrayList<String> field = new ArrayList<String>();
				ArrayList<String> fieldType = new ArrayList<String>();
				for (int y = 0; y < nooffields ; y++) {
					field.add(y,"");
					fieldType.add(y,"");
				}
				
				if(cData!=null && cData.size()>0){
					for (int j = 0; j < cData.size(); j++) {
						CustomFieldData cfData = cData.get(j);
						field.add(cfData.getFieldRank()-1, cfData.getDisplayValue());
						fieldType.add(cfData.getFieldRank()-1, cfData.getFieldType());
					}
				}
				
				short column = 0; // start column count

				HSSFRow row = sheet.createRow((short) (i + 1));
				row.setHeight((short) 0x130);

				/** * CELL CREATION START ** */

				HSSFCell cell = row.createCell(column++); // Sr.no
				cell.setCellStyle(cellStyleFont);
				cell.setCellValue(i + 1);

				cell = row.createCell(column++); // SRF.no / position Code
				cell.setCellStyle(cellStyleFont);
				cell.setCellValue(mrd.getPositionCode());
				
				cell = row.createCell(column++); // position Title
				cell.setCellStyle(cellStyleFont);
				cell.setCellValue(mrd.getPositionTitle());

				cell = row.createCell(column++); // Position date
				if (openDate != null) {
					cell.setCellValue(openDate);
					cell.setCellStyle(cellStyleDate);
				} else {
					cell.setCellValue("");
				}
				
				cell = row.createCell(column++); // Position date
				if (hireByDate != null) {
					cell.setCellValue(hireByDate);
					cell.setCellStyle(cellStyleDate);
				} else {
					cell.setCellValue("");
				}
				cell = row.createCell(column++); // position status
				cell.setCellStyle(cellStyleFont);
				cell.setCellValue(positionStatus);

//				cell = row.createCell(column++); // 
//				cell.setCellStyle(cellStyleFont);
//				cell.setCellValue(TPLabels.getLabel("master.report.header.billable"));

				cell = row.createCell(column++); // Department
				cell.setCellStyle(cellStyleFont);
				cell.setCellValue(mrd.getDepartment());

				cell = row.createCell(column++); // Sub Department
				cell.setCellStyle(cellStyleFont);
				cell.setCellValue(mrd.getSubDepartment());

				cell = row.createCell(column++); // Sub SubDepartment
				cell.setCellStyle(cellStyleFont);
				cell.setCellValue(mrd.getSubSubdepartment());

				cell = row.createCell(column++); // project manager changing it to requested by
				cell.setCellStyle(cellStyleFont);
				cell.setCellValue(mrd.getRequisitioner());

				cell = row.createCell(column++); // recruiter
				cell.setCellStyle(cellStyleFont);
				cell.setCellValue(recruiter);				

				cell = row.createCell(column++); // No of vacancies
				cell.setCellStyle(cellStyleFont);
				cell.setCellValue(Integer.parseInt(mrd.getVacancies()));

				cell = row.createCell(column++); // position level
				cell.setCellStyle(cellStyleFont);
				cell.setCellValue(mrd.getPositionLevel());

				cell = row.createCell(column++); // position skills
				cell.setCellStyle(cellStyleFont);
				cell.setCellValue(mrd.getSkills());
				
				cell = row.createCell(column++); // position owner
				cell.setCellStyle(cellStyleFont);
				cell.setCellValue(Utils.isBlankOrNull(mrd.getPositionOwner())?"":mrd.getPositionOwner());
				
				cell = row.createCell(column++); // band
				cell.setCellStyle(cellStyleFont);
				cell.setCellValue(Utils.isBlankOrNull(mrd.getPositionBand())?"":mrd.getPositionBand());
				
				cell = row.createCell(column++); // position grade
				cell.setCellStyle(cellStyleFont);
				cell.setCellValue(Utils.isBlankOrNull(mrd.getPositionGrade())?"":mrd.getPositionGrade());
				
				cell = row.createCell(column++); // position budget
				cell.setCellStyle(cellStyleFont);
				cell.setCellValue(Utils.isBlankOrNull(mrd.getPositionBudget())?"":mrd.getPositionBudget());
				
				cell = row.createCell(column++); // position business unit
				cell.setCellStyle(cellStyleFont);
				//cell.setCellValue(mrd.getPositionBusinessUnit());
				cell.setCellValue(Utils.isBlankOrNull(mrd.getPositionBusinessUnit())?"":mrd.getPositionBusinessUnit());
				
				cell = row.createCell(column++); // position cost Center
				cell.setCellStyle(cellStyleFont);
				cell.setCellValue(Utils.isBlankOrNull(mrd.getPositionCostCenter())?"":mrd.getPositionCostCenter());
				
				cell = row.createCell(column++); // position Type Of Vacancy
				cell.setCellStyle(cellStyleFont);
				cell.setCellValue(mrd.getPositionTypeOfVacancy().equals(PositionConstants.POSITIONS_TYPE_OF_VACANCY_REPLACEMENT)?"REPLACEMENT":"FRESH");
				
				cell = row.createCell(column++); // position Note
				cell.setCellStyle(cellStyleFont);
				cell.setCellValue(positionNote);

				cell = row.createCell(column++);
				if (cvSubmittedDate != null) {
					cell.setCellValue(cvSubmittedDate);
					cell.setCellStyle(cellStyleDate);
				} else {
					cell.setCellValue("");
				}

				cell = row.createCell(column++); // candidate name
				cell.setCellStyle(cellStyleFont);
				cell.setCellValue(mrd.getApplicantName());

//				cell = row.createCell(column++); // external/internal
//				cell.setCellStyle(cellStyleFont);
//				cell.setCellValue(TPLabels.getLabel("master.report.header.external"));

				cell = row.createCell(column++); // source
				cell.setCellStyle(cellStyleFont);
				if(ImportConfigurationManager.isApplicantFieldViewable(ImportConfigurationConstants.FIELD_SOURCE, permissionSet.isSHOW_CONFIDENTIAL_DATA())){
					cell.setCellValue(mrd.getSourceTitle());
				}else {
					cell.setCellValue(GlobalConstants.CONFIDENTIAL_CHARACTER);
				}

				cell = row.createCell(column++); // location
				cell.setCellStyle(cellStyleFont);
				cell.setCellValue(mrd.getApplicantCity());

				cell = row.createCell(column++); // email
				cell.setCellStyle(cellStyleFont);
				cell.setCellValue(mrd.getApplicantEmail1());

				cell = row.createCell(column++); // cell phone
				cell.setCellStyle(cellStyleFont);
				cell.setCellValue(mrd.getApplicantCellPhone());

				cell = row.createCell(column++); // Experience
				if (workingSince != null) {
					cell.setCellValue(Utils.getExperienceConstructed(workingSince));					
					cell.setCellStyle(cellStyleDate);
				} else {
					cell.setCellValue("");
				}

				cell = row.createCell(column++); // current Employer
				cell.setCellStyle(cellStyleFont);
				cell.setCellValue(mrd.getApplicantCurrentEmployer());

				cell = row.createCell(column++); // current ctc
				cell.setCellStyle(cellStyleFont);
				if(ImportConfigurationManager.isCurrentCTCViewable(permissionSet)){
					cell.setCellValue(mrd.getCurrentCTC());
				}else{
					cell.setCellValue(GlobalConstants.CONFIDENTIAL_CHARACTER);
				}

				cell = row.createCell(column++); // expected ctc
				cell.setCellStyle(cellStyleFont);
				if(ImportConfigurationManager.isExpectedCTCViewable(permissionSet)){
					cell.setCellValue(mrd.getExpectedCTC());
				}else{
					cell.setCellValue(GlobalConstants.CONFIDENTIAL_CHARACTER);
				}

				cell = row.createCell(column++); // notice period
				cell.setCellStyle(cellStyleFont);
				cell.setCellValue(mrd.getApplicantNoticePeriod());

				cell = row.createCell(column++); // Reference-1
				cell.setCellStyle(cellStyleFont);
				cell.setCellValue("");

				cell = row.createCell(column++); // Mobile Number
				cell.setCellStyle(cellStyleFont);
				cell.setCellValue("");

				cell = row.createCell(column++); // Reference-2
				cell.setCellStyle(cellStyleFont);
				cell.setCellValue("");

				cell = row.createCell(column++); // Mobile Number
				cell.setCellStyle(cellStyleFont);
				cell.setCellValue("");

				cell = row.createCell(column++); // cv shortlisted status
				cell.setCellStyle(cellStyleFont);
				cell.setCellValue(cvShortlistedSatus);

				cell = row.createCell(column++);
				if (shortlistedDate != null) {
					cell.setCellValue(shortlistedDate);
					cell.setCellStyle(cellStyleDate);
				} else {
					cell.setCellValue("");
				}

				// start interviewer status
				cell = row.createCell(column++); // Step 1
				cell.setCellStyle(cellStyleFont);
				cell.setCellValue(stepName[0]);				
				
				cell = row.createCell(column++); // Interviewer 1
				cell.setCellStyle(cellStyleFont);
				cell.setCellValue(interviewers[0]);

				if (interviewDate[0] != null) {
					cell = row.createCell(column++); // date
					cell.setCellValue(interviewDate[0]);
					cell.setCellStyle(cellStyleDate);

					cell = row.createCell(column++);// time
					cell.setCellValue(interviewDate[0]);
					cell.setCellStyle(cellStyleTime);

				} else {
					cell = row.createCell(column++); // date
					cell.setCellValue("");

					cell = row.createCell(column++);// time
					cell.setCellValue("");
				}

				cell = row.createCell(column++); // Interview status
				cell.setCellStyle(cellStyleFont);
				cell.setCellValue(interviewStatus[0]);
				
				cell = row.createCell(column++); // Step 2
				cell.setCellStyle(cellStyleFont);
				cell.setCellValue(stepName[1]);	

				cell = row.createCell(column++); // Interviewer 2
				cell.setCellStyle(cellStyleFont);
				cell.setCellValue(interviewers[1]);

				if (interviewDate[1] != null) {
					cell = row.createCell(column++); // date
					cell.setCellValue(interviewDate[1]);
					cell.setCellStyle(cellStyleDate);

					cell = row.createCell(column++);// time
					cell.setCellValue(interviewDate[1]);
					cell.setCellStyle(cellStyleTime);

				} else {
					cell = row.createCell(column++); // date
					cell.setCellValue("");

					cell = row.createCell(column++);// time
					cell.setCellValue("");
				}

				cell = row.createCell(column++); // Interview status
				cell.setCellStyle(cellStyleFont);
				cell.setCellValue(interviewStatus[1]);

				cell = row.createCell(column++); // Step 3
				cell.setCellStyle(cellStyleFont);
				cell.setCellValue(stepName[2]);	
				
				cell = row.createCell(column++); // Interviewer 3
				cell.setCellStyle(cellStyleFont);
				cell.setCellValue(interviewers[2]);

				if (interviewDate[2] != null) {
					cell = row.createCell(column++); // date
					cell.setCellValue(interviewDate[2]);
					cell.setCellStyle(cellStyleDate);

					cell = row.createCell(column++);// time
					cell.setCellValue(interviewDate[2]);
					cell.setCellStyle(cellStyleTime);

				} else {
					cell = row.createCell(column++); // date
					cell.setCellValue("");

					cell = row.createCell(column++);// time
					cell.setCellValue("");
				}

				cell = row.createCell(column++); // Interview status
				cell.setCellStyle(cellStyleFont);
				cell.setCellValue(interviewStatus[2]);

				cell = row.createCell(column++); // Step 4
				cell.setCellStyle(cellStyleFont);
				cell.setCellValue(stepName[3]);	
				
				cell = row.createCell(column++); // Interviewer 4
				cell.setCellStyle(cellStyleFont);
				cell.setCellValue(interviewers[3]);

				if (interviewDate[3] != null) {
					cell = row.createCell(column++); // date
					cell.setCellValue(interviewDate[3]);
					cell.setCellStyle(cellStyleDate);

					cell = row.createCell(column++);// time
					cell.setCellValue(interviewDate[3]);
					cell.setCellStyle(cellStyleTime);

				} else {
					cell = row.createCell(column++); // date
					cell.setCellValue("");

					cell = row.createCell(column++);// time
					cell.setCellValue("");
				}

				cell = row.createCell(column++); // Interview status
				cell.setCellStyle(cellStyleFont);
				cell.setCellValue(interviewStatus[3]);

				cell = row.createCell(column++); // Step 5
				cell.setCellStyle(cellStyleFont);
				cell.setCellValue(stepName[4]);	
				
				cell = row.createCell(column++); // Interviewer 5
				cell.setCellStyle(cellStyleFont);
				cell.setCellValue(interviewers[4]);

				if (interviewDate[4] != null) {
					cell = row.createCell(column++); // date
					cell.setCellValue(interviewDate[4]);
					cell.setCellStyle(cellStyleDate);

					cell = row.createCell(column++);// time
					cell.setCellValue(interviewDate[4]);
					cell.setCellStyle(cellStyleTime);

				} else {
					cell = row.createCell(column++); // date
					cell.setCellValue("");

					cell = row.createCell(column++);// time
					cell.setCellValue("");
				}

				cell = row.createCell(column++); // Interview status
				cell.setCellStyle(cellStyleFont);
				cell.setCellValue(interviewStatus[4]);

				cell = row.createCell(column++); // offered date
				if (offerDate != null) {
					cell.setCellValue(offerDate);
					cell.setCellStyle(cellStyleDate);
				} else {
					cell.setCellValue("");
				}

				cell = row.createCell(column++); // level offered
				cell.setCellStyle(cellStyleFont);
				if(ImportConfigurationManager.isLevelOfferedViewable(permissionSet)){
					cell.setCellValue(mrd.getApplicantLevelOffered());
				}else{
					cell.setCellValue(GlobalConstants.CONFIDENTIAL_CHARACTER);
				}

				cell = row.createCell(column++); // designation offered
				cell.setCellStyle(cellStyleFont);
				if(ImportConfigurationManager.isDesignationOfferedViewable(permissionSet)){
					cell.setCellValue(mrd.getApplicantDesignationOffered());
				}else{
					cell.setCellValue(GlobalConstants.CONFIDENTIAL_CHARACTER);
				}

				cell = row.createCell(column++); // date of joining
				if (finalStatus.equals(offered) || finalStatus.equals(joined)) {
					if (DOJ != null) {
						cell.setCellValue(DOJ);
						cell.setCellStyle(cellStyleDate);
					} else {
						cell.setCellValue("");
					}
				} else {
					cell.setCellValue("");
				}

				cell = row.createCell(column++); // ctc offered
				cell.setCellStyle(cellStyleFont);
				if(ImportConfigurationManager.isCTCOfferedViewable(permissionSet)){
					cell.setCellValue(mrd.getOfferedCTC());
				}else{
					cell.setCellValue(GlobalConstants.CONFIDENTIAL_CHARACTER);
				}

				cell = row.createCell(column++); // final status
				cell.setCellStyle(cellStyleFont);
				cell.setCellValue(finalStatus);

				cell = row.createCell(column++); // backout
				cell.setCellStyle(cellStyleFont);
				cell.setCellValue(backout);

				cell = row.createCell(column++); // current date
				if (currentDate != null) {
					cell.setCellValue(currentDate);
					cell.setCellStyle(cellStyleDate);
				} else {
					cell.setCellValue("");
				}

				cell = row.createCell(column++); // openDays
				cell.setCellStyle(cellStyleFont);
				cell.setCellValue(openDays);
				
				cell = row.createCell(column++); // aging for position
				cell.setCellStyle(cellStyleFont);
				cell.setCellValue(aging);

				// details of Inprocess, offered, joined, and Rejected.
				String strToDate = (toDate == null) ? "" : Utils.getDateConvertedToString(toDate, Utils.redDDMMYYYYFormat);
				String strFromDate = (fromDate == null) ? "" : Utils.getDateConvertedToString(fromDate, Utils.redDDMMYYYYFormat);

				cell = row.createCell(column++); // CV SOURCED
				cell.setCellStyle(cellStyleFont);
				String strCvSubmittedDate = (cvSubmittedDate == null) ? "" : Utils.getDateConvertedToString(cvSubmittedDate, Utils.redDDMMYYYYFormat);
				if (cvSubmittedDate != null && (cvSubmittedDate.before(toDate) || strCvSubmittedDate.equals(strToDate)) && (cvSubmittedDate.after(fromDate) || strCvSubmittedDate.equals(strFromDate))) {
					cell.setCellValue(1);
				} else {
					cell.setCellValue(0);
				}

				cell = row.createCell(column++); // CV S/L
				cell.setCellStyle(cellStyleFont);
				String strShortlistedDate = (shortlistedDate == null) ? "" : Utils.getDateConvertedToString(shortlistedDate, Utils.redDDMMYYYYFormat);
				if (shortlistedDate != null && (shortlistedDate.before(toDate) || strShortlistedDate.equals(strToDate)) && (shortlistedDate.after(fromDate) || strShortlistedDate.equals(strFromDate))) {
					cell.setCellValue(1);
				} else {
					cell.setCellValue(0);
				}
				
				cell = row.createCell(column++); // inprocess
				cell.setCellStyle(cellStyleFont);
				if (finalStatus.equals(inprocess)) {
					cell.setCellValue(1);
				} else {
					cell.setCellValue(0);
				}

				cell = row.createCell(column++); // offered
				cell.setCellStyle(cellStyleFont);
				if (finalStatus.equals(offered) || finalStatus.equals(joined)) {
					String strOfferDate = (offerDate == null) ? "" : Utils.getDateConvertedToString(offerDate, Utils.redDDMMYYYYFormat);
					if (offerDate != null && (offerDate.before(toDate) || strOfferDate.equals(strToDate)) && (offerDate.after(fromDate) || strOfferDate.equals(strFromDate))) {
						cell.setCellValue(1);
					} else {
						cell.setCellValue(0);
					}
				} else {
					cell.setCellValue(0);
				}

				cell = row.createCell(column++); // joined
				cell.setCellStyle(cellStyleFont);
				if (finalStatus.equals(joined)) {
					if (DOJ != null && (DOJ.before(toDate) || DOJ.equals(toDate)) && (DOJ.after(fromDate) || DOJ.equals(fromDate))) {
						cell.setCellValue(1);
					} else {
						cell.setCellValue(0);
					}
				} else {
					cell.setCellValue(0);
				}

				cell = row.createCell(column++); // rejected
				cell.setCellStyle(cellStyleFont);

				if (finalStatus.equals(rejected)) {
					String strRejectedDate = (rejectedDate == null) ? "" : Utils.getDateConvertedToString(rejectedDate, Utils.redDDMMYYYYFormat);

					if (rejectedDate != null && (rejectedDate.before(toDate) || strRejectedDate.equals(strToDate)) && (rejectedDate.after(fromDate) || strRejectedDate.equals(strFromDate))) {
						cell.setCellValue(1);
					} else {
						cell.setCellValue(0);
					}
				} else {
					cell.setCellValue(0);
				}
				
				cell = row.createCell(column++); // backout
				cell.setCellStyle(cellStyleFont);
				if(backout.equals("Backout")){
					cell.setCellValue(1);
				}else{
					cell.setCellValue(0);
				}
				
				//add custom fields at the end of row
				Date dateOfBirth = null;
				for(int x=0;x<field.size();x++){
					cell = row.createCell(column++);
					cell.setCellStyle(cellStyleFont);
					cell.setCellValue(field.get(x));
					if(fieldType.get(x).equals(CustomFieldConstants.TYPE_DATE)){
						cell.setCellStyle(cellStyleDate);
						dateOfBirth = Utils.convertToDate(field.get(x), Utils.regEUDateFormat);
						if(dateOfBirth==null){
							cell.setCellValue("");
						}else{
							cell.setCellValue(dateOfBirth);
						}
					}
				}
			}

			// Write the output to a file
			String filePostFix = "" + System.currentTimeMillis();
			newFileName = TPLabels.getLabel("master.report.file_name_prefix") + filePostFix + ".xls";
			String basePath = Utils.concatFilePath(TPApplicationProperties.getProperty("application.path"), TPApplicationProperties.getProperty("ui.dir"));
			String destinationPath = Utils.concatFilePath(basePath, ReportConstants.REPORT_DESTINATION_FOLDER);
			filePath = Utils.concatFilePath(destinationPath, newFileName);
			// filePath = Utils.concatFilePath(destinationPath,
			// TPLabels.getLabel("master.report.file_name") );
			FileOutputStream fileOut = new FileOutputStream(filePath);
			wb.write(fileOut);
			fileOut.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return newFileName;
	}
	
	public static void createHeaderRow(HSSFWorkbook wb, HSSFRow headerRow) {

		short column = 0;

		headerRow.setHeight((short) 0x130);

		HSSFFont hssfFont = wb.createFont();
		hssfFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		hssfFont.setColor(HSSFColor.BLUE.index);
		hssfFont.setFontHeightInPoints((short) 8);
		hssfFont.setFontName("Arial");

		HSSFCellStyle cellStyle = wb.createCellStyle();
		cellStyle.setFont(hssfFont);
		cellStyle.setFillBackgroundColor(HSSFColor.BLUE.index);

		HSSFCell cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(TPLabels.getLabel("master.report.header.sr_no"));

		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(TPLabels.getLabel("common.position_code"));
		
		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(TPLabels.getLabel("common.position_name"));
		
		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(TPLabels.getLabel("master.report.header.open_date"));
		
		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(TPLabels.getLabel("master.report.header.hire_by_date"));
		
		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(TPLabels.getLabel("common.position_status"));

//		cell = headerRow.createCell(column++);
//		cell.setCellStyle(cellStyle);
//		cell.setCellValue(TPLabels.getLabel("master.report.header.billable") + TPLabels.getLabel("common.or") + TPLabels.getLabel("master.report.header.nonbillable"));

		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(TPLabels.getLabel("master.report.header.department"));

		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(TPLabels.getLabel("master.report.header.sub_department"));

		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(TPLabels.getLabel("master.report.header.group"));

		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(TPLabels.getLabel("master.report.header.requested_by"));

		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(TPLabels.getLabel("master.report.header.recruiter"));

		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(TPLabels.getLabel("common.hash")+" "+TPLabels.getLabel("common.positions"));

		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(TPLabels.getLabel("master.report.header.level"));

		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(TPLabels.getLabel("master.report.header.skills"));
		

		//Position Owner
		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(TPLabels.getLabel("master.report.header.position_owner"));
		
		//Band
		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(TPLabels.getLabel("master.report.header.band"));
		
		//Grade
		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(TPLabels.getLabel("master.report.header.grade"));
		
		//Budget
		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(TPLabels.getLabel("master.report.header.budget"));
		
		//Business Unit
		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(TPLabels.getLabel("master.report.header.business_unit"));
		
		//Cost Center
		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(TPLabels.getLabel("master.report.header.cost_center"));
		
		// Type of Vacancy
		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(TPLabels.getLabel("master.report.header.type_of_vacancy"));
		
		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue("Position Note");

		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(TPLabels.getLabel("master.report.header.cv_submition_date"));

		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(TPLabels.getLabel("master.report.header.candidate_name"));

//		cell = headerRow.createCell(column++);
//		cell.setCellStyle(cellStyle);
//		cell.setCellValue(TPLabels.getLabel("master.report.header.external") + TPLabels.getLabel("common.or") + TPLabels.getLabel("master.report.header.internal"));

		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(TPLabels.getLabel("master.report.header.source"));

		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(TPLabels.getLabel("master.report.header.currentlocation"));

		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(TPLabels.getLabel("master.report.header.candidate_email"));

		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(TPLabels.getLabel("master.report.header.contact_1"));

		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(TPLabels.getLabel("master.report.header.experience"));

		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(TPLabels.getLabel("master.report.header.current_company"));

		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(TPLabels.getLabel("master.report.header.current_ctc"));

		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(TPLabels.getLabel("master.report.header.expected_ctc"));

		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(TPLabels.getLabel("master.report.header.notice_period"));

		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(TPLabels.getLabel("master.report.header.reference1"));

		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(TPLabels.getLabel("master.report.header.mobile_number"));

		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(TPLabels.getLabel("master.report.header.reference2"));

		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(TPLabels.getLabel("master.report.header.mobile_number"));

		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(TPLabels.getLabel("master.report.header.cv_shortlisted_status"));

		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(TPLabels.getLabel("master.report.header.shortlisting_date"));

		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(TPLabels.getLabel("master.report.header.step")+"1");
		
		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue("1st "+TPLabels.getLabel("master.report.header.interviewer_name"));

		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(TPLabels.getLabel("master.report.header.date"));

		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(TPLabels.getLabel("master.report.header.time"));

		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue("1st "+TPLabels.getLabel("master.report.header.interview_status"));

		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(TPLabels.getLabel("master.report.header.step")+"2");
		
		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue("2nd "+TPLabels.getLabel("master.report.header.interviewer_name"));

		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(TPLabels.getLabel("master.report.header.date"));

		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(TPLabels.getLabel("master.report.header.time"));

		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue("2nd "+TPLabels.getLabel("master.report.header.interview_status"));

		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(TPLabels.getLabel("master.report.header.step")+"3");
		
		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue("3rd "+TPLabels.getLabel("master.report.header.interviewer_name"));

		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(TPLabels.getLabel("master.report.header.date"));

		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(TPLabels.getLabel("master.report.header.time"));

		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue("3rd "+TPLabels.getLabel("master.report.header.interview_status"));

		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(TPLabels.getLabel("master.report.header.step")+"4");
		
		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue("4th "+TPLabels.getLabel("master.report.header.interviewer_name"));

		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(TPLabels.getLabel("master.report.header.date"));

		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(TPLabels.getLabel("master.report.header.time"));

		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue("4th "+TPLabels.getLabel("master.report.header.interview_status"));
		
		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue("Final "+TPLabels.getLabel("master.report.header.step"));

		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue("Final "+TPLabels.getLabel("master.report.header.interviewer_name"));

		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(TPLabels.getLabel("master.report.header.date"));

		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(TPLabels.getLabel("master.report.header.time"));

		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue("final "+TPLabels.getLabel("master.report.header.interview_status"));

		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(TPLabels.getLabel("master.report.header.offer_date"));

		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(TPLabels.getLabel("master.report.header.level_offered"));

		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(TPLabels.getLabel("master.report.header.designation_offered"));

		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(TPLabels.getLabel("master.report.header.date_of_joining"));

		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(TPLabels.getLabel("master.report.header.ctc_offered"));

		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(TPLabels.getLabel("master.report.header.status"));

		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(TPLabels.getLabel("master.report.header.backout"));

		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(TPLabels.getLabel("master.report.header.current_date"));

		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(TPLabels.getLabel("master.report.header.open_days"));
		
		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(TPLabels.getLabel("master.report.header.aging"));
		
		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(TPLabels.getLabel("master.report.header.cv_sourced"));

		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(TPLabels.getLabel("master.report.header.cv_shortlisted"));

		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(TPLabels.getLabel("master.report.header.inprocess"));

		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(TPLabels.getLabel("master.report.header.offered"));

		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(TPLabels.getLabel("master.report.header.joined"));

		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(TPLabels.getLabel("master.report.header.rejected"));
		
		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(TPLabels.getLabel("master.report.header.backout_count"));
		
				
		
		for(int x=0;x<25;x++){
			cell = headerRow.createCell(column++);
			cell.setCellStyle(cellStyle);
			cell.setCellValue("Field" + (x+1));
		}

	}

	public static void setDateRangeForSheets(HSSFWorkbook wb, Date frmDate, Date toDate) {

		HSSFFont font = wb.createFont();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
		font.setFontHeightInPoints((short) 10);
		font.setFontName(HSSFFont.FONT_ARIAL);

		HSSFCellStyle cellStyle = wb.createCellStyle();
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		cellStyle.setFont(font);

		String frmDateStr = Utils.getDateConvertedToString(frmDate, Utils.regDDMMMYYFormat);
		String toDateStr = Utils.getDateConvertedToString(toDate, Utils.regDDMMMYYFormat);

		// Department Wise 
		HSSFSheet sheet = wb.getSheet("Department Wise Activity Report");
		HSSFRow row = sheet.createRow((short) 2);

		HSSFCell cell = row.createCell((short) 0);
		cell.setCellValue("Date Range: " + frmDateStr + " To  " + toDateStr);
		cell.setCellStyle(cellStyle);

		// Recruiter Wise
		sheet = wb.getSheet("Recruiter Wise Activity Report");
		row = sheet.createRow((short) 2);

		cell = row.createCell((short) 0);
		cell.setCellValue("Date Range: " + frmDateStr + " To  " + toDateStr);
		cell.setCellStyle(cellStyle);

		// Pending Offers Report
		sheet = wb.getSheet("Pending Offers Report");
		row = sheet.createRow((short) 2);

		cell = row.createCell((short) 0);
		cell.setCellValue("Date Range: " + frmDateStr + " To  " + toDateStr);
		cell.setCellStyle(cellStyle);

		// Joined Report
		sheet = wb.getSheet("Joined Report");
		row = sheet.createRow((short) 2);

		cell = row.createCell((short) 0);
		cell.setCellValue("Date Range: " + frmDateStr + " To  " + toDateStr);
		cell.setCellStyle(cellStyle);
		
		// Ageing Report
		sheet = wb.getSheet("Ageing Report");
		row = sheet.createRow((short) 2);

		cell = row.createCell((short) 0);
		cell.setCellValue("Date Range: " + frmDateStr + " To  " + toDateStr);
		cell.setCellStyle(cellStyle);
	}
	
	public static String getPositionStatus(String mrdPositionStatus, String positionId, ArrayList<SimpleDataObject> joiningList) {
		String status = "";

		if (mrdPositionStatus.equals(PositionConstants.POSITION_STATUS_DELETED) || mrdPositionStatus.equals(PositionConstants.POSITION_STATUS_CLOSED)) {
			boolean checkJoinCandidatePresent = false;
			for (int j = 0; j < joiningList.size(); j++) { // loop to get position with joining candidates
				SimpleDataObject sDo = joiningList.get(j);
				String appPositionId = sDo.getString("applicantPositionId");
				if (positionId.equals(appPositionId)) {
					checkJoinCandidatePresent = true;
				}
			}
			if (checkJoinCandidatePresent) {
				status = "Closed";
			} else {
				status = "Cancelled";
			}

		} else if (mrdPositionStatus.equals(PositionConstants.POSITION_STATUS_OPENED)) {
			status = "Open";
		} else if (mrdPositionStatus.equals(PositionConstants.POSITION_STATUS_HOLD)) {
			status = "On Hold";
		} else if (mrdPositionStatus.equals(PositionConstants.POSITION_STATUS_DROPPED)) {
			status = "Dropped";
		}

		return status;
	}
	
	public static String generateMasterDataReport(ArrayList<MasterReportData> applicantData, FilterData filterData,PermissionSet permissionSet) throws Exception {
		String newFileName = "";
		try {
			Date fromDate = Utils.convertToDate(filterData.getFromDate(), "dd/MM/yyyy");
			Date toDate = Utils.convertToDate(filterData.getToDate(), "dd/MM/yyyy");
			
			String filePath = Utils.concatFilePath(ReportConstants.JRXML_FOLDER_PATH, TPLabels.getLabel("master.report.file_name"));
			//POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(filePath));
			HSSFWorkbook wb = new HSSFWorkbook();
			
			//Make USER -SRF PROGRESS sheet
			//setDateRangeForSheets(wb, fromDate, toDate);
			
			// Make Master Sheet
			HSSFSheet sheet = wb.createSheet();

			// Create a new font and alter it.
			HSSFFont font = wb.createFont();
			font.setFontHeightInPoints((short) 8);
			font.setFontName(HSSFFont.FONT_ARIAL);

			// define cellStyles
			HSSFCellStyle cellStyleDate = wb.createCellStyle();
			cellStyleDate.setDataFormat(HSSFDataFormat.getBuiltinFormat("d-mmm-yy"));
			cellStyleDate.setBorderRight((short) 20);
			cellStyleDate.setFont(font);

			HSSFCellStyle cellStyleTime = wb.createCellStyle();
			cellStyleTime.setDataFormat(HSSFDataFormat.getBuiltinFormat("h:mm AM/PM"));
			cellStyleTime.setFont(font);

			HSSFCellStyle cellStyleFont = wb.createCellStyle();
			cellStyleFont.setFont(font);
			
			// delete all previous data
			Iterator rowItr = sheet.rowIterator();
			while (rowItr.hasNext()) {
				HSSFRow row = (HSSFRow) rowItr.next();
				Iterator cellItr = row.cellIterator();
				while (cellItr.hasNext()) {
					HSSFCell cell = (HSSFCell) cellItr.next();
					cell.setCellValue("");
				}
			}

			// Create header Row.
			HSSFRow headerRow = sheet.createRow((short) 0);
			createMasterHeaderRow(wb, headerRow);


			MasterReportManager masterReportManager = new MasterReportManager();
			ArrayList<SimpleDataObject> joiningList = masterReportManager.getPositionIdsWithJoiningCandidates();
			CustomFieldManager customFieldManager = new CustomFieldManager();
			for (int i = 0; i < applicantData.size(); i++) {
				MasterReportData mrd = applicantData.get(i);
				
				

				String applicantId = mrd.getApplicantId();
				String positionId = mrd.getPositionId();
				String processId = mrd.getProcessId();
				String backout = ""; // Final Status
				String rejected = "Rejected";
				String joined = "Joined";
				String offered = "Offered";
				String inprocess = "Inprocess";
				String select = "SELECT";
				String reject = "REJECT";
				String finalStatus = "";
				String positionStatus = "";
				String positionNote = mrd.getPositionNote();
				String cvShortlistedSatus = "F/B Await";
				String recruiter = "";
				Date currentDate = new Date();
				Date offerDate = null;
				Date shortlistedDate = null;
				Date cvSubmittedDate = mrd.getProcessDateCreated();
				Date openDate = mrd.getPositionDateCreated();
				Date hireByDate = mrd.getPositionDateExpiry();
				Date DOJ = mrd.getApplicantDateJoined();
				Date rejectedDate = null;
				Date workingSince = mrd.getApplicantWorkingSince();
				
				String applicantsSubmitted = mrd.getApplicantsSubmitted();
				String applicantsScreened = mrd.getApplicantsScreened();
				String screenRejected = applicantsSubmitted;
				
				if (!Utils.isBlankOrNull(applicantsSubmitted) && !Utils.isBlankOrNull(applicantsScreened)){
					screenRejected = String.valueOf(Integer.parseInt(applicantsSubmitted) - Integer.parseInt(applicantsScreened));
				}

				int aging = Integer.parseInt(Utils.getDateDiffenence(openDate, new Date()));
				// for Join candidate Aging = (joining date-position create date)
				
				PositionManager positionManager = new PositionManager();
				String allUsers = mrd.getApprovalUsers();
				String users = "";
				if (!Utils.isBlankOrNull(allUsers)){
					String[] approvalUsers = allUsers.split(",");
					for (int j=0; j<approvalUsers.length; j++){
						LoginManager userManager = new LoginManager();
						LoginData logindata = userManager.getUser(String.valueOf(approvalUsers[j]));
						if (Utils.isBlankOrNull(users)){
							users = logindata.getFirstName() + " "+ logindata.getLastName();
						}else{
							users = users + "," + logindata.getFirstName() + " "+ logindata.getLastName();
						}
					}
					
				}
				
				SimpleDataObject sdo = positionManager.getPositionActionRequired(positionId);
				String approvalDate = "";
				if (sdo != null) {
					String status = sdo.getString("positionStatus");
					String requisitionStepName = sdo.getString("requisitionStepName");
					String feedbackDecision = sdo.getString("feedbackDecision");
					if (status.equals(PositionConstants.POSITION_STATUS_INPROCESS)) {
						if (Utils.isBlankOrNull(requisitionStepName)) {
							approvalDate = sdo.getString("feedbackDate");
						} 
					} else if (status.equals(PositionConstants.POSITION_STATUS_REJECTED)) {
						approvalDate = sdo.getString("feedbackDate");
					} else if (status.equals(PositionConstants.POSITION_STATUS_OPENED)) {
						approvalDate = sdo.getString("feedbackDate");
					} else if (status.equals(PositionConstants.POSITION_STATUS_CLOSED)) {
						approvalDate = sdo.getString("feedbackDate");
					}
				}
				
				String positionClosedDate = "";
				if(!Utils.isBlankOrNull(mrd.getVacancies())){
					if(!Utils.isBlankOrNull(mrd.getJoinedCandidates())){
						if(Integer.parseInt(mrd.getJoinedCandidates())>= Integer.parseInt(mrd.getVacancies())){
							positionClosedDate = mrd.getLastCandidateDateJoined();
						}
					}
				}
				
				
				//ArrayList<UserData> approvalUsers = positionManager.getRequisitionApprovalUsersForPosition(positionId);
//				for (UserData user:approvalUsers){
//					LoginManager userManager = new LoginManager();
//					LoginData logindata = userManager.getUser(String.valueOf(user.getUserId()));
//					if (Utils.isBlankOrNull(users)){
//						users = logindata.getFirstName() + " "+ logindata.getLastName();
//					}else{
//						users = users + "," + logindata.getFirstName() + " "+ logindata.getLastName();
//					}
//				}
				
				// set position status
				positionStatus = getPositionStatus(mrd.getPositionStatus(), positionId, joiningList);
				
				int openDays = Integer.parseInt(Utils.getDateDiffenence(openDate, new Date()));
				// for close position openDays = close date - position create date
				if(positionStatus.equals("Closed") || positionStatus.equals("Cancelled")){
					Date closedDate = mrd.getPositionDateClosed();
					if(closedDate!=null){
						openDays = Integer.parseInt(Utils.getDateDiffenence(openDate, closedDate));
					}
				}

				// get applicant step data
				ArrayList<MasterReportData> iData = masterReportManager.getApplicantInteractionData(applicantId, positionId, processId);
				ArrayList<String> stepName = new ArrayList<String>();
				ArrayList<String> interviewers = new ArrayList<String>();
				ArrayList<Date> interviewDate = new ArrayList<Date>();
				ArrayList<String> interviewStatus = new ArrayList<String>();
				StepManager stepManager = new StepManager();
				for (int j = 0; j < iData.size(); j++){
					stepName.add(null);
					interviewers.add(null);
					interviewDate.add(null);
					interviewStatus.add(null);
				}
				boolean checkOfferDate = true;
				boolean checkShortlistedDate = true;
				//boolean checkRecruiter = true;
				for (int j = 0; j < iData.size(); j++) {
					MasterReportData interactionData = iData.get(j);
					String stepIdTo = interactionData.getPositionStepIdTo();
					String stepLevelTo = interactionData.getPositionStepLevelTo();
					String stepLevelFrom = interactionData.getPositionStepLevelFrom();
					//if (count < 5 && interactionData.getPositionStepIsscheduled() != null && (interactionData.getPositionStepIsscheduled()).equals(String.valueOf(PositionConstants.STEP_SCHEDULED))) {
						interviewers.set(j, interactionData.getInterviewers());
						stepName.set(j,interactionData.getPositionStepTitle());
						Date inDate = interactionData.getAppointmentFromDate();
						//if (inDate != null) {
						if (interactionData.getPositionStepIsscheduled() != null && (interactionData.getPositionStepIsscheduled()).equals(String.valueOf(PositionConstants.STEP_SCHEDULED))) {
							interviewDate.set(j,inDate);
							if (inDate != null) {
								if (!stepIdTo.equals(SelectionProcessConstants.STEP_NOT_INTERESTED_REJECT) && !stepIdTo.equals(SelectionProcessConstants.STEP_POSITION_CLOSED_REJECT)
										&& !stepIdTo.equals(SelectionProcessConstants.STEP_NOT_ATTENDED) && !stepIdTo.equals(SelectionProcessConstants.STEP_REJECT)) {
									interviewStatus.set(j,select);
								} else {
									interviewStatus.set(j,reject);
								}
							}
						} else {
							inDate = interactionData.getProcessDateCreated();
							interviewDate.set(j,inDate);
							if (!stepIdTo.equals(SelectionProcessConstants.STEP_NOT_INTERESTED_REJECT) && !stepIdTo.equals(SelectionProcessConstants.STEP_POSITION_CLOSED_REJECT)
									&& !stepIdTo.equals(SelectionProcessConstants.STEP_NOT_ATTENDED) && !stepIdTo.equals(SelectionProcessConstants.STEP_REJECT)) {
								interviewStatus.set(j,select);
							} else {
								interviewStatus.set(j,reject);
							}
						}
					
					// to get recruiter
					//if (checkRecruiter) {
						//if (interactionData.getRecruiter() != null) {
						//	recruiter = interactionData.getRecruiter();
						//}
						//checkRecruiter = false;
					//}

					// to get offered date
					if (checkOfferDate && !Utils.isBlankOrNull(stepLevelFrom) && stepLevelFrom.equals(PositionConstants.STEP_LEVEL_ACCEPT)) {
						if (interactionData.getProcessDateCreated() != null) {
							offerDate = interactionData.getProcessDateCreated();
						}
						checkOfferDate = false;
					}
					// to get cvSubmitted Date
					if (j == 0) { // first step
						if (interactionData.getProcessDateCreated() != null) {
							cvSubmittedDate = interactionData.getProcessDateCreated();
						}

					}

					// to get technical shortlisted date
					if (checkShortlistedDate && !Utils.isBlankOrNull(stepLevelTo)
							&& (stepLevelTo.equals(PositionConstants.STEP_LEVEL_SELECT) || stepLevelTo.equals(PositionConstants.STEP_LEVEL_ACCEPT))) {
						if (interactionData.getProcessDateCreated() != null) {
							shortlistedDate = interactionData.getProcessDateCreated();
							cvShortlistedSatus = "S/L";
						}
						checkShortlistedDate = false;
					}

					if (j == iData.size() - 1) { // last step to get final status
						if (!Utils.isBlankOrNull(stepIdTo)) {
							if (stepIdTo.equals(SelectionProcessConstants.STEP_NOT_INTERESTED_REJECT) || stepIdTo.equals(SelectionProcessConstants.STEP_POSITION_CLOSED_REJECT)
									|| stepIdTo.equals(SelectionProcessConstants.STEP_NOT_ATTENDED) || stepIdTo.equals(SelectionProcessConstants.STEP_REJECT)) {
								finalStatus = rejected;
								rejectedDate = interactionData.getProcessDateCreated();
								if (!Utils.isBlankOrNull(stepLevelFrom) && stepLevelFrom.equals(PositionConstants.STEP_LEVEL_ACCEPT)) {
									backout = "Backout";
								}
							} else if (stepIdTo.equals(SelectionProcessConstants.STEP_JOIN)) {
								finalStatus = joined;
								if (DOJ != null) {
									aging = Integer.parseInt(Utils.getDateDiffenence(openDate, DOJ));
								}
							} else {
								if (!Utils.isBlankOrNull(stepLevelTo) && stepLevelTo.equals(PositionConstants.STEP_LEVEL_ACCEPT)) {
									finalStatus = offered;
								} else {
									finalStatus = inprocess;
								}
							}
						}
					}
				}
				if (checkShortlistedDate) {
					if (finalStatus.equals(rejected)) {
						cvShortlistedSatus = "Reject";
					}
				}
				
				
				// get applicant custom fields.
				ArrayList<CustomFieldData> cData = customFieldManager.getCustomFieldDataForEntity(applicantId, CustomFieldConstants.ENTITY_TYPE_APPLICANT);
				int nooffields = CustomFieldManager.getMaxRank(CustomFieldConstants.ENTITY_TYPE_APPLICANT);
				String[] field = new String[nooffields];
				//ArrayList<String> field = new ArrayList<String>();
				String[] fieldType = new String[nooffields];
				for (int y = 0; y < nooffields ; y++) {
					field[y] = "";
					fieldType[y] = "";
				}
				
				if(cData!=null && cData.size()>0){
					for (int j = 0; j < cData.size(); j++) {
						CustomFieldData cfData = cData.get(j);
						field[cfData.getFieldRank()-1] = cfData.getDisplayValue();
						fieldType[cfData.getFieldRank()-1] = cfData.getFieldType();
					}
				}
				
				ArrayList<CustomFieldData> positionCData = customFieldManager.getCustomFieldDataForEntity(positionId, CustomFieldConstants.ENTITY_TYPE_POSITION);
				int positionNooffields = CustomFieldManager.getMaxRank(CustomFieldConstants.ENTITY_TYPE_POSITION);
				String[] positionField= new String[positionNooffields];
				String[] positionfieldType = new String[positionNooffields];
				for (int y = 0; y < positionNooffields ; y++) {
					positionField[y] = "";
					positionfieldType[y] = "";
				}
				
				
				if(positionCData!=null && positionCData.size()>0){
					for (int j = 0; j < positionCData.size(); j++) {
						CustomFieldData cfData = positionCData.get(j);
						positionField[cfData.getFieldRank()-1] = cfData.getDisplayValue();
						positionfieldType[cfData.getFieldRank()-1] = cfData.getFieldType();
					}
				}
				
				short column = 0; // start column count

				HSSFRow row = sheet.createRow((short) (i + 1));
				row.setHeight((short) 0x130);

				/** * CELL CREATION START ** */

				HSSFCell cell = row.createCell(column++); // Sr.no
				cell.setCellStyle(cellStyleFont);
				cell.setCellValue(i + 1);

				cell = row.createCell(column++); // SRF.no / position Code
				cell.setCellStyle(cellStyleFont);
				cell.setCellValue(mrd.getPositionCode());
				
				cell = row.createCell(column++); // position Title
				cell.setCellStyle(cellStyleFont);
				cell.setCellValue(mrd.getPositionTitle());

				cell = row.createCell(column++); // Position date
				if (openDate != null) {
					cell.setCellValue(openDate);
					cell.setCellStyle(cellStyleDate);
				} else {
					cell.setCellValue("");
				}
				
				cell = row.createCell(column++); // Position date
				if (hireByDate != null) {
					cell.setCellValue(hireByDate);
					cell.setCellStyle(cellStyleDate);
				} else {
					cell.setCellValue("");
				}
				
				cell = row.createCell(column++); // position status
				cell.setCellStyle(cellStyleFont);
				cell.setCellValue(positionStatus);

//				cell = row.createCell(column++); // 
//				cell.setCellStyle(cellStyleFont);
//				cell.setCellValue(TPLabels.getLabel("master.report.header.billable"));

				cell = row.createCell(column++); // Department
				cell.setCellStyle(cellStyleFont);
				cell.setCellValue(mrd.getDepartment());

				cell = row.createCell(column++); // Sub Department
				cell.setCellStyle(cellStyleFont);
				cell.setCellValue(mrd.getSubDepartment());

				cell = row.createCell(column++); // Sub SubDepartment
				cell.setCellStyle(cellStyleFont);
				cell.setCellValue(mrd.getSubSubdepartment());

				cell = row.createCell(column++); // project manager changing it to requested by
				cell.setCellStyle(cellStyleFont);
				cell.setCellValue(mrd.getRequisitioner());

				cell = row.createCell(column++); // recruiter
				cell.setCellStyle(cellStyleFont);
				cell.setCellValue(recruiter);				

				cell = row.createCell(column++); // No of vacancies
				cell.setCellStyle(cellStyleFont);
				cell.setCellValue(Integer.parseInt(mrd.getVacancies()));

				cell = row.createCell(column++); // position level
				cell.setCellStyle(cellStyleFont);
				cell.setCellValue(mrd.getPositionLevel());

				cell = row.createCell(column++); // position skills
				cell.setCellStyle(cellStyleFont);
				cell.setCellValue(mrd.getSkills());
				
				cell = row.createCell(column++); // position owner
				cell.setCellStyle(cellStyleFont);
				cell.setCellValue(Utils.isBlankOrNull(mrd.getPositionOwner())?"":mrd.getPositionOwner());
				
				cell = row.createCell(column++); // band
				cell.setCellStyle(cellStyleFont);
				cell.setCellValue(Utils.isBlankOrNull(mrd.getPositionBand())?"":mrd.getPositionBand());
				
				cell = row.createCell(column++); // position grade
				cell.setCellStyle(cellStyleFont);
				cell.setCellValue(Utils.isBlankOrNull(mrd.getPositionGrade())?"":mrd.getPositionGrade());
				
				cell = row.createCell(column++); // position budget
				cell.setCellStyle(cellStyleFont);
				cell.setCellValue(Utils.isBlankOrNull(mrd.getPositionBudget())?"":mrd.getPositionBudget());
				
				cell = row.createCell(column++); // position business unit
				cell.setCellStyle(cellStyleFont);
				//cell.setCellValue(mrd.getPositionBusinessUnit());
				cell.setCellValue(Utils.isBlankOrNull(mrd.getPositionBusinessUnit())?"":mrd.getPositionBusinessUnit());
				
				cell = row.createCell(column++); // position cost Center
				cell.setCellStyle(cellStyleFont);
				cell.setCellValue(Utils.isBlankOrNull(mrd.getPositionCostCenter())?"":mrd.getPositionCostCenter());
				
				cell = row.createCell(column++); // position Type Of Vacancy
				cell.setCellStyle(cellStyleFont);
				cell.setCellValue(mrd.getPositionTypeOfVacancy().equals(PositionConstants.POSITIONS_TYPE_OF_VACANCY_REPLACEMENT)?"REPLACEMENT":"FRESH");
				
				cell = row.createCell(column++); // position Note
				cell.setCellStyle(cellStyleFont);
				cell.setCellValue(positionNote);

				cell = row.createCell(column++);
				if (cvSubmittedDate != null) {
					cell.setCellValue(cvSubmittedDate);
					cell.setCellStyle(cellStyleDate);
				} else {
					cell.setCellValue("");
				}

				cell = row.createCell(column++); // candidate name
				cell.setCellStyle(cellStyleFont);
				cell.setCellValue(mrd.getApplicantName());

//				cell = row.createCell(column++); // external/internal
//				cell.setCellStyle(cellStyleFont);
//				cell.setCellValue(TPLabels.getLabel("master.report.header.external"));

				cell = row.createCell(column++); // source
				cell.setCellStyle(cellStyleFont);
				if(ImportConfigurationManager.isApplicantFieldViewable(ImportConfigurationConstants.FIELD_SOURCE, permissionSet.isSHOW_CONFIDENTIAL_DATA())){
					cell.setCellValue(mrd.getSourceTitle());
				}else {
					cell.setCellValue(GlobalConstants.CONFIDENTIAL_CHARACTER);
				}

				cell = row.createCell(column++); // location
				cell.setCellStyle(cellStyleFont);
				cell.setCellValue(mrd.getApplicantCity());

				cell = row.createCell(column++); // email
				cell.setCellStyle(cellStyleFont);
				cell.setCellValue(mrd.getApplicantEmail1());

				cell = row.createCell(column++); // cell phone
				cell.setCellStyle(cellStyleFont);
				cell.setCellValue(mrd.getApplicantCellPhone());

				cell = row.createCell(column++); // Experience
				if (workingSince != null) {
					cell.setCellValue(Utils.getExperienceConstructed(workingSince));					
					cell.setCellStyle(cellStyleDate);
				} else {
					cell.setCellValue("");
				}

				cell = row.createCell(column++); // current Employer
				cell.setCellStyle(cellStyleFont);
				cell.setCellValue(mrd.getApplicantCurrentEmployer());

				cell = row.createCell(column++); // current ctc
				cell.setCellStyle(cellStyleFont);
				if(ImportConfigurationManager.isCurrentCTCViewable(permissionSet)){
					cell.setCellValue(mrd.getCurrentCTC());
				}else{
					cell.setCellValue(GlobalConstants.CONFIDENTIAL_CHARACTER);
				}

				cell = row.createCell(column++); // expected ctc
				cell.setCellStyle(cellStyleFont);
				if(ImportConfigurationManager.isExpectedCTCViewable(permissionSet)){
					cell.setCellValue(mrd.getExpectedCTC());
				}else{
					cell.setCellValue(GlobalConstants.CONFIDENTIAL_CHARACTER);
				}

				cell = row.createCell(column++); // notice period
				cell.setCellStyle(cellStyleFont);
				cell.setCellValue(mrd.getApplicantNoticePeriod());

				cell = row.createCell(column++); // Reference-1
				cell.setCellStyle(cellStyleFont);
				cell.setCellValue("");

				cell = row.createCell(column++); // Mobile Number
				cell.setCellStyle(cellStyleFont);
				cell.setCellValue("");

				cell = row.createCell(column++); // Reference-2
				cell.setCellStyle(cellStyleFont);
				cell.setCellValue("");

				cell = row.createCell(column++); // Mobile Number
				cell.setCellStyle(cellStyleFont);
				cell.setCellValue("");

				cell = row.createCell(column++); // cv shortlisted status
				cell.setCellStyle(cellStyleFont);
				cell.setCellValue(cvShortlistedSatus);

				cell = row.createCell(column++);
				if (shortlistedDate != null) {
					cell.setCellValue(shortlistedDate);
					cell.setCellStyle(cellStyleDate);
				} else {
					cell.setCellValue("");
				}
				
				for(int j=1; j<stepName.size(); j++){
					cell = row.createCell(column++); // Step 1
					cell.setCellStyle(cellStyleFont);
					cell.setCellValue(stepName.get(j));				
					
					cell = row.createCell(column++); // Interviewer 1
					cell.setCellStyle(cellStyleFont);
					cell.setCellValue(interviewers.get(j));

					if (interviewDate.get(j) != null) {
						cell = row.createCell(column++); // date
						cell.setCellValue(interviewDate.get(j));
						cell.setCellStyle(cellStyleDate);

						cell = row.createCell(column++);// time
						cell.setCellValue(interviewDate.get(j));
						cell.setCellStyle(cellStyleTime);

					} else {
						cell = row.createCell(column++); // date
						cell.setCellValue("");

						cell = row.createCell(column++);// time
						cell.setCellValue("");
					}
					
					cell = row.createCell(column++); // Interview status
					cell.setCellStyle(cellStyleFont);
					cell.setCellValue(interviewStatus.get(j));
				}

				// start interviewer status
				/*cell = row.createCell(column++); // Step 1
				cell.setCellStyle(cellStyleFont);
				cell.setCellValue(stepName[0]);				
				
				cell = row.createCell(column++); // Interviewer 1
				cell.setCellStyle(cellStyleFont);
				cell.setCellValue(interviewers[0]);

				if (interviewDate[0] != null) {
					cell = row.createCell(column++); // date
					cell.setCellValue(interviewDate[0]);
					cell.setCellStyle(cellStyleDate);

					cell = row.createCell(column++);// time
					cell.setCellValue(interviewDate[0]);
					cell.setCellStyle(cellStyleTime);

				} else {
					cell = row.createCell(column++); // date
					cell.setCellValue("");

					cell = row.createCell(column++);// time
					cell.setCellValue("");
				}
				
				cell = row.createCell(column++); // Interview status
				cell.setCellStyle(cellStyleFont);
				cell.setCellValue(interviewStatus[0]);
				
				cell = row.createCell(column++); // Step 2
				cell.setCellStyle(cellStyleFont);
				cell.setCellValue(stepName[1]);	

				cell = row.createCell(column++); // Interviewer 2
				cell.setCellStyle(cellStyleFont);
				cell.setCellValue(interviewers[1]);

				if (interviewDate[1] != null) {
					cell = row.createCell(column++); // date
					cell.setCellValue(interviewDate[1]);
					cell.setCellStyle(cellStyleDate);

					cell = row.createCell(column++);// time
					cell.setCellValue(interviewDate[1]);
					cell.setCellStyle(cellStyleTime);

				} else {
					cell = row.createCell(column++); // date
					cell.setCellValue("");

					cell = row.createCell(column++);// time
					cell.setCellValue("");
				}

				cell = row.createCell(column++); // Interview status
				cell.setCellStyle(cellStyleFont);
				cell.setCellValue(interviewStatus[1]);

				cell = row.createCell(column++); // Step 3
				cell.setCellStyle(cellStyleFont);
				cell.setCellValue(stepName[2]);	
				
				cell = row.createCell(column++); // Interviewer 3
				cell.setCellStyle(cellStyleFont);
				cell.setCellValue(interviewers[2]);

				if (interviewDate[2] != null) {
					cell = row.createCell(column++); // date
					cell.setCellValue(interviewDate[2]);
					cell.setCellStyle(cellStyleDate);

					cell = row.createCell(column++);// time
					cell.setCellValue(interviewDate[2]);
					cell.setCellStyle(cellStyleTime);

				} else {
					cell = row.createCell(column++); // date
					cell.setCellValue("");

					cell = row.createCell(column++);// time
					cell.setCellValue("");
				}

				cell = row.createCell(column++); // Interview status
				cell.setCellStyle(cellStyleFont);
				cell.setCellValue(interviewStatus[2]);

				cell = row.createCell(column++); // Step 4
				cell.setCellStyle(cellStyleFont);
				cell.setCellValue(stepName[3]);	
				
				cell = row.createCell(column++); // Interviewer 4
				cell.setCellStyle(cellStyleFont);
				cell.setCellValue(interviewers[3]);

				if (interviewDate[3] != null) {
					cell = row.createCell(column++); // date
					cell.setCellValue(interviewDate[3]);
					cell.setCellStyle(cellStyleDate);

					cell = row.createCell(column++);// time
					cell.setCellValue(interviewDate[3]);
					cell.setCellStyle(cellStyleTime);

				} else {
					cell = row.createCell(column++); // date
					cell.setCellValue("");

					cell = row.createCell(column++);// time
					cell.setCellValue("");
				}

				cell = row.createCell(column++); // Interview status
				cell.setCellStyle(cellStyleFont);
				cell.setCellValue(interviewStatus[3]);

				cell = row.createCell(column++); // Step 5
				cell.setCellStyle(cellStyleFont);
				cell.setCellValue(stepName[4]);	
				
				cell = row.createCell(column++); // Interviewer 5
				cell.setCellStyle(cellStyleFont);
				cell.setCellValue(interviewers[4]);

				if (interviewDate[4] != null) {
					cell = row.createCell(column++); // date
					cell.setCellValue(interviewDate[4]);
					cell.setCellStyle(cellStyleDate);

					cell = row.createCell(column++);// time
					cell.setCellValue(interviewDate[4]);
					cell.setCellStyle(cellStyleTime);

				} else {
					cell = row.createCell(column++); // date
					cell.setCellValue("");

					cell = row.createCell(column++);// time
					cell.setCellValue("");
				}

				cell = row.createCell(column++); // Interview status
				cell.setCellStyle(cellStyleFont);
				cell.setCellValue(interviewStatus[4]);*/

				cell = row.createCell(column++); // offered date
				if (offerDate != null) {
					cell.setCellValue(offerDate);
					cell.setCellStyle(cellStyleDate);
				} else {
					cell.setCellValue("");
				}

				cell = row.createCell(column++); // level offered
				cell.setCellStyle(cellStyleFont);
				if(ImportConfigurationManager.isLevelOfferedViewable(permissionSet)){
					cell.setCellValue(mrd.getApplicantLevelOffered());
				}else{
					cell.setCellValue(GlobalConstants.CONFIDENTIAL_CHARACTER);
				}

				cell = row.createCell(column++); // designation offered
				cell.setCellStyle(cellStyleFont);
				if(ImportConfigurationManager.isDesignationOfferedViewable(permissionSet)){
					cell.setCellValue(mrd.getApplicantDesignationOffered());
				}else{
					cell.setCellValue(GlobalConstants.CONFIDENTIAL_CHARACTER);
				}

				cell = row.createCell(column++); // date of joining
				if (finalStatus.equals(offered) || finalStatus.equals(joined)) {
					if (DOJ != null) {
						cell.setCellValue(DOJ);
						cell.setCellStyle(cellStyleDate);
					} else {
						cell.setCellValue("");
					}
				} else {
					cell.setCellValue("");
				}

				cell = row.createCell(column++); // ctc offered
				cell.setCellStyle(cellStyleFont);
				if(ImportConfigurationManager.isCTCOfferedViewable(permissionSet)){
					cell.setCellValue(mrd.getOfferedCTC());
				}else{
					cell.setCellValue(GlobalConstants.CONFIDENTIAL_CHARACTER);
				}

				cell = row.createCell(column++); // final status
				cell.setCellStyle(cellStyleFont);
				cell.setCellValue(finalStatus);

				cell = row.createCell(column++); // backout
				cell.setCellStyle(cellStyleFont);
				cell.setCellValue(backout);

				cell = row.createCell(column++); // current date
				if (currentDate != null) {
					cell.setCellValue(currentDate);
					cell.setCellStyle(cellStyleDate);
				} else {
					cell.setCellValue("");
				}

				cell = row.createCell(column++); // openDays
				cell.setCellStyle(cellStyleFont);
				cell.setCellValue(openDays);
				
				cell = row.createCell(column++); // aging for position
				cell.setCellStyle(cellStyleFont);
				cell.setCellValue(aging);

				// details of Inprocess, offered, joined, and Rejected.
				String strToDate = (toDate == null) ? "" : Utils.getDateConvertedToString(toDate, Utils.redDDMMYYYYFormat);
				String strFromDate = (fromDate == null) ? "" : Utils.getDateConvertedToString(fromDate, Utils.redDDMMYYYYFormat);

				cell = row.createCell(column++); // CV SOURCED
				cell.setCellStyle(cellStyleFont);
				String strCvSubmittedDate = (cvSubmittedDate == null) ? "" : Utils.getDateConvertedToString(cvSubmittedDate, Utils.redDDMMYYYYFormat);
				if (cvSubmittedDate != null && (cvSubmittedDate.before(toDate) || strCvSubmittedDate.equals(strToDate)) && (cvSubmittedDate.after(fromDate) || strCvSubmittedDate.equals(strFromDate))) {
					cell.setCellValue(1);
				} else {
					cell.setCellValue(0);
				}

				cell = row.createCell(column++); // CV S/L
				cell.setCellStyle(cellStyleFont);
				String strShortlistedDate = (shortlistedDate == null) ? "" : Utils.getDateConvertedToString(shortlistedDate, Utils.redDDMMYYYYFormat);
				if (shortlistedDate != null && (shortlistedDate.before(toDate) || strShortlistedDate.equals(strToDate)) && (shortlistedDate.after(fromDate) || strShortlistedDate.equals(strFromDate))) {
					cell.setCellValue(1);
				} else {
					cell.setCellValue(0);
				}
				
				cell = row.createCell(column++); // inprocess
				cell.setCellStyle(cellStyleFont);
				if (finalStatus.equals(inprocess)) {
					cell.setCellValue(1);
				} else {
					cell.setCellValue(0);
				}

				cell = row.createCell(column++); // offered
				cell.setCellStyle(cellStyleFont);
				if (finalStatus.equals(offered) || finalStatus.equals(joined)) {
					String strOfferDate = (offerDate == null) ? "" : Utils.getDateConvertedToString(offerDate, Utils.redDDMMYYYYFormat);
					if (offerDate != null && (offerDate.before(toDate) || strOfferDate.equals(strToDate)) && (offerDate.after(fromDate) || strOfferDate.equals(strFromDate))) {
						cell.setCellValue(1);
					} else {
						cell.setCellValue(0);
					}
				} else {
					cell.setCellValue(0);
				}

				cell = row.createCell(column++); // joined
				cell.setCellStyle(cellStyleFont);
				if (finalStatus.equals(joined)) {
					if (DOJ != null && (DOJ.before(toDate) || DOJ.equals(toDate)) && (DOJ.after(fromDate) || DOJ.equals(fromDate))) {
						cell.setCellValue(1);
					} else {
						cell.setCellValue(0);
					}
				} else {
					cell.setCellValue(0);
				}

				cell = row.createCell(column++); // rejected
				cell.setCellStyle(cellStyleFont);

				if (finalStatus.equals(rejected)) {
					String strRejectedDate = (rejectedDate == null) ? "" : Utils.getDateConvertedToString(rejectedDate, Utils.redDDMMYYYYFormat);

					if (rejectedDate != null && (rejectedDate.before(toDate) || strRejectedDate.equals(strToDate)) && (rejectedDate.after(fromDate) || strRejectedDate.equals(strFromDate))) {
						cell.setCellValue(1);
					} else {
						cell.setCellValue(0);
					}
				} else {
					cell.setCellValue(0);
				}
				
				cell = row.createCell(column++); // backout
				cell.setCellStyle(cellStyleFont);
				if(backout.equals("Backout")){
					cell.setCellValue(1);
				}else{
					cell.setCellValue(0);
				}
				
				//add custom fields at the end of row
				Date dateOfBirth = null;
				
				cell = row.createCell(column++); // position cost Center
				cell.setCellStyle(cellStyleFont);
				cell.setCellValue(Utils.isBlankOrNull(mrd.getMinExperience())?"":mrd.getMinExperience());
				
				cell = row.createCell(column++); // position cost Center
				cell.setCellStyle(cellStyleFont);
				cell.setCellValue(Utils.isBlankOrNull(mrd.getMaxExperience())?"":mrd.getMaxExperience());
				
				cell = row.createCell(column++); // position cost Center
				cell.setCellStyle(cellStyleFont);
				cell.setCellValue(Utils.isBlankOrNull(mrd.getRequirements())?"":Jsoup.parse(mrd.getRequirements()).text());
				
				cell = row.createCell(column++); // position cost Center
				cell.setCellStyle(cellStyleFont);
				cell.setCellValue(Utils.isBlankOrNull(mrd.getPriority())?"":mrd.getPriority());
				
				cell = row.createCell(column++); // position cost Center
				cell.setCellStyle(cellStyleFont);
				cell.setCellValue(Utils.isBlankOrNull(mrd.getPositionDateApproved())?"":mrd.getPositionDateApproved());
				
				cell = row.createCell(column++); // position cost Center
				cell.setCellStyle(cellStyleFont);
				cell.setCellValue(users);
				
				cell = row.createCell(column++); // position cost Center
				cell.setCellStyle(cellStyleFont);
				cell.setCellValue(Utils.isBlankOrNull(mrd.getPositionDropComment())?"":mrd.getPositionDropComment());
				
				cell = row.createCell(column++); // position cost Center
				cell.setCellStyle(cellStyleFont);
				cell.setCellValue(Utils.isBlankOrNull(mrd.getPositionDropDate())?"":mrd.getPositionDropDate());
				
				cell = row.createCell(column++); // position cost Center
				cell.setCellStyle(cellStyleFont);
				cell.setCellValue(mrd.getPositionDateClosed()==null?"":mrd.getPositionDateClosed().toString());
				
				cell = row.createCell(column++); // position cost Center
				cell.setCellStyle(cellStyleFont);
				cell.setCellValue(mrd.getPositionDateOpened()==null?"":mrd.getPositionDateOpened().toString());
				
				cell = row.createCell(column++); // position cost Center
				cell.setCellStyle(cellStyleFont);
				cell.setCellValue(approvalDate);
				
				cell = row.createCell(column++); // position cost Center
				cell.setCellStyle(cellStyleFont);
				cell.setCellValue(positionClosedDate);
				
				cell = row.createCell(column++); // position cost Center
				cell.setCellStyle(cellStyleFont);
				cell.setCellValue(applicantsSubmitted);
				
				cell = row.createCell(column++); // position cost Center
				cell.setCellStyle(cellStyleFont);
				cell.setCellValue(screenRejected);
				
				cell = row.createCell(column++); // position cost Center
				cell.setCellStyle(cellStyleFont);
				cell.setCellValue(applicantsScreened);
				
				for(int x=0;x<positionField.length;x++){
					cell = row.createCell(column++);
					cell.setCellStyle(cellStyleFont);
					cell.setCellValue(positionField[x]);
					if(positionfieldType[x].equals(CustomFieldConstants.TYPE_DATE)){
						cell.setCellStyle(cellStyleDate);
						dateOfBirth = Utils.convertToDate(positionField[x], Utils.regEUDateFormat);
						if(dateOfBirth==null){
							cell.setCellValue("");
						}else{
							cell.setCellValue(dateOfBirth);
						}
					}
				}
				
				for(int x=0;x<field.length;x++){
					cell = row.createCell(column++);
					cell.setCellStyle(cellStyleFont);
					cell.setCellValue(field[x]);
					if(fieldType[x].equals(CustomFieldConstants.TYPE_DATE)){
						cell.setCellStyle(cellStyleDate);
						dateOfBirth = Utils.convertToDate(field[x], Utils.regEUDateFormat);
						if(dateOfBirth==null){
							cell.setCellValue("");
						}else{
							cell.setCellValue(dateOfBirth);
						}
					}
				}
			}

			// Write the output to a file
			String filePostFix = "" + System.currentTimeMillis();
			newFileName = TPLabels.getLabel("master.report.file_name_prefix") + filePostFix + ".xls";
			String basePath = Utils.concatFilePath(TPApplicationProperties.getProperty("application.path"), TPApplicationProperties.getProperty("ui.dir"));
			String destinationPath = Utils.concatFilePath(basePath, ReportConstants.REPORT_DESTINATION_FOLDER);
			filePath = Utils.concatFilePath(destinationPath, newFileName);
			// filePath = Utils.concatFilePath(destinationPath,
			// TPLabels.getLabel("master.report.file_name") );
			FileOutputStream fileOut = new FileOutputStream(filePath);
			wb.write(fileOut);
			fileOut.close();

		} catch (Exception e) {
			TPLogger.getLogger().error("Error in master data report", e);
		}
		return newFileName;
	}
	
	public static void createMasterHeaderRow(HSSFWorkbook wb, HSSFRow headerRow) throws SQLException {

		short column = 0;

		headerRow.setHeight((short) 0x130);

		HSSFFont hssfFont = wb.createFont();
		hssfFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		hssfFont.setColor(HSSFColor.BLUE.index);
		hssfFont.setFontHeightInPoints((short) 8);
		hssfFont.setFontName("Arial");

		HSSFCellStyle cellStyle = wb.createCellStyle();
		cellStyle.setFont(hssfFont);
		cellStyle.setFillBackgroundColor(HSSFColor.BLUE.index);

		HSSFCell cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(TPLabels.getLabel("master.report.header.sr_no"));

		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(TPLabels.getLabel("common.position_code"));
		
		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(TPLabels.getLabel("common.position_name"));
		
		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(TPLabels.getLabel("master.report.header.open_date"));
		
		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(TPLabels.getLabel("master.report.header.hire_by_date"));
		
		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(TPLabels.getLabel("common.position_status"));

//		cell = headerRow.createCell(column++);
//		cell.setCellStyle(cellStyle);
//		cell.setCellValue(TPLabels.getLabel("master.report.header.billable") + TPLabels.getLabel("common.or") + TPLabels.getLabel("master.report.header.nonbillable"));

		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(TPLabels.getLabel("master.report.header.department"));

		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(TPLabels.getLabel("master.report.header.sub_department"));

		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(TPLabels.getLabel("master.report.header.group"));

		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(TPLabels.getLabel("master.report.header.requested_by"));

		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(TPLabels.getLabel("master.report.header.recruiter"));

		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(TPLabels.getLabel("common.hash")+" "+TPLabels.getLabel("common.positions"));

		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(TPLabels.getLabel("master.report.header.level"));

		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(TPLabels.getLabel("master.report.header.skills"));
		

		//Position Owner
		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(TPLabels.getLabel("master.report.header.position_owner"));
		
		//Band
		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(TPLabels.getLabel("master.report.header.band"));
		
		//Grade
		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(TPLabels.getLabel("master.report.header.grade"));
		
		//Budget
		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(TPLabels.getLabel("master.report.header.budget"));
		
		//Business Unit
		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(TPLabels.getLabel("master.report.header.business_unit"));
		
		//Cost Center
		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(TPLabels.getLabel("master.report.header.cost_center"));
		
		// Type of Vacancy
		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(TPLabels.getLabel("master.report.header.type_of_vacancy"));
		
		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue("Position Note");

		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(TPLabels.getLabel("master.report.header.cv_submition_date"));

		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(TPLabels.getLabel("master.report.header.candidate_name"));

//		cell = headerRow.createCell(column++);
//		cell.setCellStyle(cellStyle);
//		cell.setCellValue(TPLabels.getLabel("master.report.header.external") + TPLabels.getLabel("common.or") + TPLabels.getLabel("master.report.header.internal"));

		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(TPLabels.getLabel("master.report.header.source"));

		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(TPLabels.getLabel("master.report.header.currentlocation"));

		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(TPLabels.getLabel("master.report.header.candidate_email"));

		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(TPLabels.getLabel("master.report.header.contact_1"));

		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(TPLabels.getLabel("master.report.header.experience"));

		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(TPLabels.getLabel("master.report.header.current_company"));

		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(TPLabels.getLabel("master.report.header.current_ctc"));

		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(TPLabels.getLabel("master.report.header.expected_ctc"));

		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(TPLabels.getLabel("master.report.header.notice_period"));

		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(TPLabels.getLabel("master.report.header.reference1"));

		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(TPLabels.getLabel("master.report.header.mobile_number"));

		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(TPLabels.getLabel("master.report.header.reference2"));

		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(TPLabels.getLabel("master.report.header.mobile_number"));

		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(TPLabels.getLabel("master.report.header.cv_shortlisted_status"));

		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(TPLabels.getLabel("master.report.header.shortlisting_date"));
		
		StepManager stepManager = new StepManager();
		List<MasterStepData> steps = stepManager.getAllSteps(CommonConstants.TRUE);
		for (int j = 1; j < steps.size(); j++){
			cell = headerRow.createCell(column++);
			cell.setCellStyle(cellStyle);
			cell.setCellValue(TPLabels.getLabel("master.report.header.step")+j);
			
			cell = headerRow.createCell(column++);
			cell.setCellStyle(cellStyle);
			cell.setCellValue(j+" "+TPLabels.getLabel("master.report.header.interviewer_name"));

			cell = headerRow.createCell(column++);
			cell.setCellStyle(cellStyle);
			cell.setCellValue(TPLabels.getLabel("master.report.header.date"));

			cell = headerRow.createCell(column++);
			cell.setCellStyle(cellStyle);
			cell.setCellValue(TPLabels.getLabel("master.report.header.time"));

			cell = headerRow.createCell(column++);
			cell.setCellStyle(cellStyle);
			cell.setCellValue(j+" "+TPLabels.getLabel("master.report.header.interview_status"));
		}

		/*cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(TPLabels.getLabel("master.report.header.step")+"1");
		
		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue("1st "+TPLabels.getLabel("master.report.header.interviewer_name"));

		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(TPLabels.getLabel("master.report.header.date"));

		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(TPLabels.getLabel("master.report.header.time"));

		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue("1st "+TPLabels.getLabel("master.report.header.interview_status"));

		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(TPLabels.getLabel("master.report.header.step")+"2");
		
		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue("2nd "+TPLabels.getLabel("master.report.header.interviewer_name"));

		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(TPLabels.getLabel("master.report.header.date"));

		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(TPLabels.getLabel("master.report.header.time"));

		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue("2nd "+TPLabels.getLabel("master.report.header.interview_status"));

		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(TPLabels.getLabel("master.report.header.step")+"3");
		
		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue("3rd "+TPLabels.getLabel("master.report.header.interviewer_name"));

		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(TPLabels.getLabel("master.report.header.date"));

		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(TPLabels.getLabel("master.report.header.time"));

		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue("3rd "+TPLabels.getLabel("master.report.header.interview_status"));

		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(TPLabels.getLabel("master.report.header.step")+"4");
		
		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue("4th "+TPLabels.getLabel("master.report.header.interviewer_name"));

		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(TPLabels.getLabel("master.report.header.date"));

		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(TPLabels.getLabel("master.report.header.time"));

		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue("4th "+TPLabels.getLabel("master.report.header.interview_status"));
		
		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue("Final "+TPLabels.getLabel("master.report.header.step"));

		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue("Final "+TPLabels.getLabel("master.report.header.interviewer_name"));

		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(TPLabels.getLabel("master.report.header.date"));

		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(TPLabels.getLabel("master.report.header.time"));

		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue("final "+TPLabels.getLabel("master.report.header.interview_status"));*/

		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(TPLabels.getLabel("master.report.header.offer_date"));

		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(TPLabels.getLabel("master.report.header.level_offered"));

		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(TPLabels.getLabel("master.report.header.designation_offered"));

		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(TPLabels.getLabel("master.report.header.date_of_joining"));

		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(TPLabels.getLabel("master.report.header.ctc_offered"));

		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(TPLabels.getLabel("master.report.header.status"));

		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(TPLabels.getLabel("master.report.header.backout"));

		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(TPLabels.getLabel("master.report.header.current_date"));

		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(TPLabels.getLabel("master.report.header.open_days"));
		
		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(TPLabels.getLabel("master.report.header.aging"));
		
		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(TPLabels.getLabel("master.report.header.cv_sourced"));

		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(TPLabels.getLabel("master.report.header.cv_shortlisted"));

		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(TPLabels.getLabel("master.report.header.inprocess"));

		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(TPLabels.getLabel("master.report.header.offered"));

		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(TPLabels.getLabel("master.report.header.joined"));

		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(TPLabels.getLabel("master.report.header.rejected"));
		
		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(TPLabels.getLabel("master.report.header.backout_count"));
		
		CustomFieldManager customFieldManager = new CustomFieldManager();
		
		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(TPLabels.getLabel("position.requirements.minimum_experience"));
		
		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(TPLabels.getLabel("position.requirements.maximum_experience"));
		
		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(TPLabels.getLabel("position.requirements.job_requirements"));
		
		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(TPLabels.getLabel("common.priority"));
		
		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(TPLabels.getLabel("report.label.position_date_approved"));
		
		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(TPLabels.getLabel("master.report.header.approval_users"));
		
		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(TPLabels.getLabel("master.report.header.position_drop_comment"));
		
		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(TPLabels.getLabel("master.report.header.position_drop_date"));
		
		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(TPLabels.getLabel("master.report.header.position_closed_date"));
		
		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(TPLabels.getLabel("master.report.header.position_opened_date"));
		
		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(TPLabels.getLabel("master.report.header.position_activation_date"));
		
		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(TPLabels.getLabel("master.report.header.position_vacancies_fulfilled"));
		
		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(TPLabels.getLabel("master.report.header.applicants_submitted"));
		
		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(TPLabels.getLabel("master.report.header.screen_rejected"));
		
		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(TPLabels.getLabel("master.report.header.applicants_screened"));
		
		ArrayList<CustomFieldData> cData = customFieldManager.getCustomFieldsFor(CustomFieldConstants.ENTITY_TYPE_POSITION, true);
		
		if(cData!=null && cData.size()>0){
			for (int j = 0; j < cData.size(); j++) {
				CustomFieldData cfData = cData.get(j);
				cell = headerRow.createCell(column++);
				cell.setCellStyle(cellStyle);
				cell.setCellValue(cfData.getFieldDisplayName());
			}
		}
		
		cData = customFieldManager.getCustomFieldsFor(CustomFieldConstants.ENTITY_TYPE_APPLICANT, true);
		
		if(cData!=null && cData.size()>0){
			for (int j = 0; j < cData.size(); j++) {
				CustomFieldData cfData = cData.get(j);
				cell = headerRow.createCell(column++);
				cell.setCellStyle(cellStyle);
				cell.setCellValue(cfData.getFieldDisplayName());
			}
		}
		
		
//		for(int x=0;x<25;x++){
//			cell = headerRow.createCell(column++);
//			cell.setCellStyle(cellStyle);
//			cell.setCellValue("Field" + (x+1));
//		}

	}
}
