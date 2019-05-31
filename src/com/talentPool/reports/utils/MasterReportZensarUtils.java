/**
 * 
 */
package com.talentPool.reports.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import com.talentPool.applicant.constants.ImportConfigurationConstants;
import com.talentPool.applicant.manager.ImportConfigurationManager;
import com.talentPool.common.db.SimpleDataObject;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.properties.TPApplicationProperties;
import com.talentPool.common.properties.TPLabels;
import com.talentPool.common.utils.Utils;
import com.talentPool.positions.PositionConstants;
import com.talentPool.reports.ReportConstants;
import com.talentPool.reports.dataobject.FilterData;
import com.talentPool.reports.dataobject.MasterReportData;
import com.talentPool.reports.manager.MasterReportManager;
import com.talentPool.selectionProcess.SelectionProcessConstants;
import com.talentPool.user.manager.PermissionSet;

/**
 * @author Ajeet
 *
 */
public class MasterReportZensarUtils {

	public static String generateZensarMasterReport(ArrayList<MasterReportData> applicantData, FilterData filterData,PermissionSet permissionSet) throws IOException {
		String newFileName = "";
		try {
			Date fromDate = Utils.convertToDate(filterData.getFromDate(), "dd/MM/yyyy");
			Date toDate = Utils.convertToDate(filterData.getToDate(), "dd/MM/yyyy");
			
			String filePath = Utils.concatFilePath(ReportConstants.JRXML_FOLDER_PATH, TPLabels.getLabel("master.report.file_name_zensar"));
			POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(filePath));
			HSSFWorkbook wb = new HSSFWorkbook(fs);
			
			//Make USER -SRF PROGRESS sheet
			makeUserSRFSheet(wb, fromDate, toDate);
			
			// MAke Master Sheet
			HSSFSheet sheet = wb.getSheet(TPLabels.getLabel("master.report.master_sheet_name_zensar"));

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

			for (int i = 0; i < applicantData.size(); i++) {
				MasterReportData mrd = applicantData.get(i);

				String applicantId = mrd.getApplicantId();
				String positionId = mrd.getPositionId();
				String processId = mrd.getProcessId();
				String backout = ""; // Finat Status
				String rejected = "Rejected";
				String joined = "Joined";
				String offered = "Offered";
				String inprocess = "Inprocess";
				String select = "SELECT";
				String reject = "REJECT";
				String finalStatus = "";
				String positionStatus = "";
				String cvShortlistedSatus = "F/B Await";
				Date currentDate = new Date();
				Date offerDate = null;
				Date shortlistedDate = null;
				Date cvSubmittedDate = mrd.getProcessDateCreated();
				Date SRFDate = mrd.getPositionDateCreated();
				Date DOJ = mrd.getApplicantDateJoined();
				Date rejectedDate = null;
				Date workingSince = mrd.getApplicantWorkingSince();

				int aging = Integer.parseInt(Utils.getDateDiffenence(SRFDate, new Date()));
				// for Join candidate Aging = (joining date-srf date)

				// set position status
				positionStatus = getPositionStatus(mrd.getPositionStatus(), positionId, joiningList);

				// get applicant step data
				ArrayList<MasterReportData> iData = masterReportManager.getApplicantInteractionData(applicantId, positionId, processId);

				String[] interviewers = { "", "", "", "", "" };
				Date[] interviewDate = { null, null, null, null, null };
				String[] interviewStatus = { "", "", "", "", "" };
				boolean checkOfferDate = true;
				boolean checkShortlistedDate = true;
				int count = 0; // counter to get only 5 scheduled steps
				for (int j = 0; j < iData.size(); j++) {
					MasterReportData interactionData = iData.get(j);
					String stepIdTo = interactionData.getPositionStepIdTo();
					String stepLevelTo = interactionData.getPositionStepLevelTo();
					String stepLevelFrom = interactionData.getPositionStepLevelFrom();
					if (count < 5 && interactionData.getPositionStepIsscheduled() != null && (interactionData.getPositionStepIsscheduled()).equals(String.valueOf(PositionConstants.STEP_SCHEDULED))) {
						interviewers[count] = interactionData.getInterviewers();
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
									aging = Integer.parseInt(Utils.getDateDiffenence(SRFDate, DOJ));
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

				cell = row.createCell(column++); // SRF/Position date
				if (SRFDate != null) {
					cell.setCellValue(SRFDate);
					cell.setCellStyle(cellStyleDate);
				} else {
					cell.setCellValue("");
				}

				cell = row.createCell(column++); // SRF.no / position Code
				cell.setCellStyle(cellStyleFont);
				cell.setCellValue(TPLabels.getLabel("master.report.header.billable"));

				cell = row.createCell(column++); // Department
				cell.setCellStyle(cellStyleFont);
				cell.setCellValue(mrd.getDepartment());

				cell = row.createCell(column++); // Sub Department
				cell.setCellStyle(cellStyleFont);
				cell.setCellValue(mrd.getSubDepartment());

				cell = row.createCell(column++); // Sub SubDepartment
				cell.setCellStyle(cellStyleFont);
				cell.setCellValue(mrd.getSubSubdepartment());

				cell = row.createCell(column++); // project manager
				cell.setCellStyle(cellStyleFont);
				cell.setCellValue(mrd.getRequisitioner());

				cell = row.createCell(column++); // position status
				cell.setCellStyle(cellStyleFont);
				cell.setCellValue(positionStatus);

				cell = row.createCell(column++); // recruiter
				cell.setCellStyle(cellStyleFont);
				cell.setCellValue(mrd.getRecruiter());

				cell = row.createCell(column++); // Recruiter Designation / position Title
				cell.setCellStyle(cellStyleFont);
				cell.setCellValue(mrd.getPositionTitle());

				cell = row.createCell(column++); // No of vacancies
				cell.setCellStyle(cellStyleFont);
				cell.setCellValue(Integer.parseInt(mrd.getVacancies()));

				cell = row.createCell(column++); // position level
				cell.setCellStyle(cellStyleFont);
				cell.setCellValue(mrd.getPositionLevel());

				cell = row.createCell(column++); // position skills
				cell.setCellStyle(cellStyleFont);
				cell.setCellValue(mrd.getSkills());

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

				cell = row.createCell(column++); // external/internal
				cell.setCellStyle(cellStyleFont);
				cell.setCellValue(TPLabels.getLabel("master.report.header.external"));

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

				cell = row.createCell(column++);
				if (workingSince != null) {
					cell.setCellValue(workingSince);
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

				cell = row.createCell(column++); // aging for position
				cell.setCellStyle(cellStyleFont);
				cell.setCellValue(aging);

				// details of Inprocess, offered, joined, and Rejected.
				String strToDate = (toDate == null) ? "" : Utils.getDateConvertedToString(toDate, Utils.redDDMMYYYYFormat);
				String strFromDate = (fromDate == null) ? "" : Utils.getDateConvertedToString(fromDate, Utils.redDDMMYYYYFormat);

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
		cell.setCellValue(TPLabels.getLabel("master.report.header.srf_no"));

		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(TPLabels.getLabel("master.report.header.srf_date"));

		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(TPLabels.getLabel("master.report.header.billable") + TPLabels.getLabel("common.or") + TPLabels.getLabel("master.report.header.nonbillable"));

		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(TPLabels.getLabel("master.report.header.sbu"));

		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(TPLabels.getLabel("master.report.header.practice"));

		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(TPLabels.getLabel("master.report.header.client"));

		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(TPLabels.getLabel("master.report.header.project_manager"));

		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(TPLabels.getLabel("master.report.header.srf_status"));

		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(TPLabels.getLabel("master.report.header.recruiter"));

		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(TPLabels.getLabel("master.report.header.designation"));

		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(TPLabels.getLabel("common.hash")+" "+TPLabels.getLabel("common.positions"));

		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(TPLabels.getLabel("master.report.header.level"));

		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(TPLabels.getLabel("master.report.header.skills"));

		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(TPLabels.getLabel("master.report.header.cv_submition_date"));

		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(TPLabels.getLabel("master.report.header.candidate_name"));

		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(TPLabels.getLabel("master.report.header.external") + TPLabels.getLabel("common.or") + TPLabels.getLabel("master.report.header.internal"));

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
		cell.setCellValue("1st "+TPLabels.getLabel("master.report.header.interviewer_name"));

		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(TPLabels.getLabel("master.report.header.date"));

		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(TPLabels.getLabel("master.report.header.time"));

		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(TPLabels.getLabel("master.report.header.interview_status"));

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
		cell.setCellValue(TPLabels.getLabel("master.report.header.interview_status"));

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
		cell.setCellValue(TPLabels.getLabel("master.report.header.interview_status"));

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
		cell.setCellValue(TPLabels.getLabel("master.report.header.interview_status"));

		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue("final "+TPLabels.getLabel("master.report.header.interviewer_name"));

		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(TPLabels.getLabel("master.report.header.date"));

		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(TPLabels.getLabel("master.report.header.time"));

		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(TPLabels.getLabel("master.report.header.interview_status"));

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
		cell.setCellValue(TPLabels.getLabel("master.report.header.aging"));

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
		cell.setCellValue(TPLabels.getLabel("master.report.header.cv_sourced"));

		cell = headerRow.createCell(column++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(TPLabels.getLabel("master.report.header.cv_shortlisted"));

	}

	public static void makeUserSRFSheet(HSSFWorkbook wb, Date frmDate, Date toDate) {

		HSSFFont font = wb.createFont();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		font.setFontHeightInPoints((short) 12);
		font.setFontName(HSSFFont.FONT_ARIAL);

		HSSFCellStyle cellStyle = wb.createCellStyle();
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		cellStyle.setFont(font);

		String frmDateStr = Utils.getDateConvertedToString(frmDate, Utils.regDDMMMYYFormat);
		String toDateStr = Utils.getDateConvertedToString(toDate, Utils.regDDMMMYYFormat);

		// SRF PROGRESS-Open
		HSSFSheet sheet = wb.getSheet("SRF PROGRESS-Open");
		HSSFRow row = sheet.createRow((short) 1);
		row.setHeight((short) 0x150);

		HSSFCell cell = row.createCell((short) 0);
		cell.setCellValue("From  " + frmDateStr + " To  " + toDateStr);
		cell.setCellStyle(cellStyle);

		// SRF PROGRESS-Closed
		sheet = wb.getSheet("SRF PROGRESS-Closed");
		row = sheet.createRow((short) 1);
		row.setHeight((short) 0x150);

		cell = row.createCell((short) 0);
		cell.setCellValue("From  " + frmDateStr + " To  " + toDateStr);
		cell.setCellStyle(cellStyle);

		// SRF PROGRESS-Cancelled
		sheet = wb.getSheet("SRF PROGRESS-Cancelled");
		row = sheet.createRow((short) 1);
		row.setHeight((short) 0x150);

		cell = row.createCell((short) 0);
		cell.setCellValue("From  " + frmDateStr + " To  " + toDateStr);
		cell.setCellStyle(cellStyle);

		// SRF PROGRESS-On Hold
		sheet = wb.getSheet("SRF PROGRESS-On Hold");
		row = sheet.createRow((short) 1);
		row.setHeight((short) 0x150);

		cell = row.createCell((short) 0);
		cell.setCellValue("From  " + frmDateStr + " To  " + toDateStr);
		cell.setCellStyle(cellStyle);
	}
	
	public static String getPositionStatus(String mrdPositionStatus, String positionId, ArrayList<SimpleDataObject> joiningList) {
		String status = "";

		if (mrdPositionStatus.equals(PositionConstants.POSITION_STATUS_DELETED) || mrdPositionStatus.equals(PositionConstants.POSITION_STATUS_CLOSED)) {
			boolean checkJoinCandidatePresent = false;
			for (int j = 0; j < joiningList.size(); j++) { // loop to get position with joining
				// candidates
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
		}

		return status;
	}
}
