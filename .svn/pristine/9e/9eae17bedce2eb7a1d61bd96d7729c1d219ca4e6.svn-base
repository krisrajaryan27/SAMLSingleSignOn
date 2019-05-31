/**
 * 
 */
package com.talentPool.offerSheet.manager;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import com.talentPool.applicant.dataobject.EducationalData;
import com.talentPool.applicant.manager.ApplicantManager;
import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.constants.DateConstants;
import com.talentPool.common.dataobject.FormFileData;
import com.talentPool.common.db.CustomIncrementConstants;
import com.talentPool.common.db.CustomIncrementCounter;
import com.talentPool.common.db.DBPreparedQuery;
import com.talentPool.common.db.DBTransaction;
import com.talentPool.common.db.SimpleDataObject;
import com.talentPool.common.db.Exception.NoResultFoundException;
import com.talentPool.common.properties.GlobalApplicationProperties;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.properties.TPLabels;
import com.talentPool.common.utils.DateUtils;
import com.talentPool.common.utils.Utils;
import com.talentPool.common.utils.Exception.FileUploadException;
import com.talentPool.common.utils.Exception.InvalidMimeTypeException;
import com.talentPool.common.utils.FileUtils.FileHandlerUtils;
import com.talentPool.documents.DocumentConstants;
import com.talentPool.documents.dataobject.DocumentData;
import com.talentPool.documents.manager.DocumentManager;
import com.talentPool.documents.utils.DocumentUploader;
import com.talentPool.inbox.scheduler.DeleteFilesThread;
import com.talentPool.offerSheet.constants.OfferSheetConstants;
import com.talentPool.offerSheet.dataobject.ApplicantOfferSheetDetails;
import com.talentPool.offerSheet.dataobject.OfferSheetTemplateData;
import com.talentPool.offerSheet.dataobject.OfferSheetTemplateVariable;
import com.talentPool.offerSheet.exception.GenerateOfferSheetException;
import com.talentPool.offerSheet.factory.OfferSheetGeneratorFactory;
import com.talentPool.offerSheet.model.ApplicantOfferModel;
import com.talentPool.offerSheet.utils.OfferSheetUtils;
import com.talentPool.offerSheet.utils.OfferSheetXmlGenerator;
import com.talentPool.salaryStructure.databject.ApplicantOfferDetails;
import com.talentPool.salaryStructure.databject.SalaryStructure;
import com.talentPool.salaryStructure.exception.SalaryCalculationException;
import com.talentPool.salaryStructure.manager.SalaryCalculator;
import com.talentPool.selectionProcess.SelectionProcessConstants;
import com.talentPool.selectionProcess.dataobject.CommunicationData;
import com.talentPool.selectionProcess.dataobject.FeedbackData;
import com.talentPool.selectionProcess.manager.CommunicationManager;
import com.talentPool.selectionProcess.manager.SelectionProcessManager;

/**
 * @author pallavi
 *
 */
public class OfferSheetManager {	
	public final static int OFFER_VERSION = 1;
	public final static int OFFER_VERSION_INCREMENT = 1;
	
	public List<OfferSheetTemplateData> getOfferSheetTemplates() throws SQLException {
		List<OfferSheetTemplateData> templates = null;
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dOfferSheetManager_GetOfferSheetTemplates");
			templates = dq.getResult();
		} finally {
			if(dq != null) {
				dq.releaseConnection();
			}
		}
		return templates;
	}
	
	public String getXmlForOfferSheetTemplates() throws SQLException {
		List<OfferSheetTemplateData> templates = getOfferSheetTemplates();
		OfferSheetXmlGenerator xmlGenerator = new OfferSheetXmlGenerator();
		String xmlFile = xmlGenerator.getXmlForOfferSheetTemplates(templates);
		return xmlFile;
	}

	public OfferSheetTemplateData getOfferSheetTemplate(String templateId) throws SQLException {
		OfferSheetTemplateData template = null;
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dOfferSheetManager_GetOfferSheetTemplate");
			dq.setString(1, templateId);
			template = (OfferSheetTemplateData) dq.getSingleObjectResult();
		} finally {
			if(dq != null) {
				dq.releaseConnection();
			}
		}
		return template;
	}
	
	private void deleteOfferSheetTemplateFromDatabase(String templateId, DBTransaction tran) throws SQLException {		
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dOfferSheetManager_DeleteOfferSheetTemplate", tran);
			dq.setString(1, templateId);
			dq.execute();
		} finally {
			if(dq != null) {
				dq.closeOpenCursors();
			}
		}
	}
	
	private void deleteOfferSheetTemplateVariablesFromDatabase(String templateId, DBTransaction tran) throws SQLException {		
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dOfferSheetManager_DeleteOfferSheetTemplateVariables", tran);
			dq.setString(1, templateId);
			dq.execute();
		} finally {
			if(dq != null) {
				dq.closeOpenCursors();
			}
		}
	}
	
	private void deleteOfferSheetTemplateDocumentsFromServer(OfferSheetTemplateData template) {		
		if(template!=null){
			ArrayList<String> files = new ArrayList<String>();
			files.add(template.getTemplateFilePath());
			DeleteFilesThread t = new DeleteFilesThread(files, false);
		}
	}
	
	public void deleteOfferSheetTemplate(String templateId) throws Exception {
		DBTransaction tran = null;
		try {
			tran = new DBTransaction();
			OfferSheetTemplateData template = getOfferSheetTemplate(templateId);			
			deleteOfferSheetTemplateVariablesFromDatabase(templateId, tran);
			deleteOfferSheetTemplateFromDatabase(templateId, tran);
			tran.commit();
			deleteOfferSheetTemplateDocumentsFromServer(template);			
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			tran.rollback();
			throw e;
		} finally {
			if(tran != null) {
				tran.release();
			}
		}		
	}
	
	public void addNewOfferSheetTemplate(String templateName, String templateDesc, FormFileData templateDocument, 
			String userId) throws FileUploadException, FileNotFoundException, IOException,
			SQLException, InvalidFormatException, InvalidMimeTypeException {
		DBTransaction tran = null;
		try {
			tran = new DBTransaction();
			DocumentUploader uploader = new DocumentUploader();
			DocumentData documentData = uploader.saveFormFile(templateDocument);		
			OfferSheetTemplateData templateData = constructOfferSheetTemplate(templateName, templateDesc, documentData, userId);
			createNewOfferSheetTemplate(templateData, tran);
			
			OfferSheetUtils offerSheetUtils = new OfferSheetUtils();
			List<String> templateVariables = offerSheetUtils.extractAndStoreOfferSheetTemplateVariables(templateData);
			createOfferSheetTemplateVariables(templateVariables, templateData, tran);
			tran.commit();
		} catch (SQLException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			tran.rollback();			
			throw e;
		} finally {
			if(tran != null) {
				tran.release();
			}
		}
	}
	
	private void createOfferSheetTemplateVariables(List<String> templateVariables, OfferSheetTemplateData templateData, DBTransaction tran) throws SQLException {
		DBPreparedQuery dq = null;
		try {
			if(templateVariables != null && templateVariables.size() > 0) {
				for(int i = 0; i < templateVariables.size(); i++) {
					dq = new DBPreparedQuery("dOfferSheetManager_CreateOfferSheetTemplateVariable", tran);
					dq.setString(1, templateData.getTemplateId());
					dq.setString(2, templateVariables.get(i));
					dq.execute();
				}
			}			
		} finally {
			if(dq != null) {
				dq.closeOpenCursors();
			}
		}
	}

	private OfferSheetTemplateData constructOfferSheetTemplate(String templateName, String templateDesc, DocumentData documentData, String userId) {
		OfferSheetTemplateData templateData = new OfferSheetTemplateData();
		templateData.setTemplateName(templateName);
		templateData.setTemplateDesc(templateDesc);
		templateData.setTemplateFileName(documentData.getOriginalFileName());
		templateData.setTemplateFilePath(documentData.getRelativeFilePath());
		templateData.setUserId(userId);
		return templateData;
	}

	private void createNewOfferSheetTemplate(OfferSheetTemplateData template, DBTransaction tran) throws SQLException {
		DBPreparedQuery dq = null;
		try {			
			dq = new DBPreparedQuery("dOfferSheetManager_CreateOfferSheetTemplate", tran);
			dq.setString(1, template.getTemplateName().trim());
			dq.setString(2, template.getTemplateDesc().trim());
			dq.setString(3, template.getTemplateFilePath());
			dq.setString(4, template.getTemplateFileName());
			dq.setString(5, template.getUserId());
			dq.execute();
			
			dq = new DBPreparedQuery("dFetchLastInsertID", tran);
			String templateId = dq.getIdResult();
			template.setTemplateId(templateId);
		} finally {
			if(dq != null) {
				dq.closeOpenCursors();
			}
		}
	}
	
	public boolean isOfferSheetTemplateNamePresent(String templateName) {
		boolean isOfferSheetTemplateNamePresent = false; 
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dOfferSheetManager_GetTemplateWithName");
			dq.setString(1, templateName);
			String templateId = dq.getIdResult();
			if(!Utils.isBlankOrNull(templateId)) {
				isOfferSheetTemplateNamePresent = true;
			}
		} catch (SQLException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			if(dq != null) {
				dq.releaseConnection();
			}
		}
		return isOfferSheetTemplateNamePresent;
	}
	
	public List<OfferSheetTemplateVariable> getOfferSheetTemplateVariables(String templateId) throws SQLException {
		List<OfferSheetTemplateVariable> templateVariables = null;
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dOfferSheetManager_GetTemplateVariables");
			dq.setString(1, templateId);
			templateVariables = dq.getResult();
		} finally {
			if(dq != null) {
				dq.releaseConnection();
			}
		}
		return templateVariables;
	}
	
	public String[] generateOfferSheet1(String applicantId, String templateId, String operation, 
			String userId, int offerFormat, HttpServletRequest request) throws GenerateOfferSheetException, Exception {
		OfferSheetTemplateData templateData = getOfferSheetTemplate(templateId);
		List<OfferSheetTemplateVariable> templateVariables = getOfferSheetTemplateVariables(templateId);
		populateOfferSheetTemplateVariableValues(templateVariables, request);
				
		OfferSheetUtils offerSheetUtils = new OfferSheetUtils();
		String[] filePath = offerSheetUtils.getDestinationPath(templateData, operation, userId, offerFormat);
		
		OfferSheetGenerator generator = OfferSheetGeneratorFactory.getOfferSheetGenerator(templateData.getTemplateFileName(), offerFormat);
		generator.generateOfferSheet(templateData, templateVariables, filePath[0]);
		
		if(!"preview".equals(operation)) {
			String[] parts = filePath[0].split(DocumentConstants.documentsPath);
			DocumentManager documentManager = new DocumentManager();
			String documentId = documentManager.insertApplicantDocument(applicantId, filePath[2], parts[1], userId, null);
			
			CommunicationData cData = getCommunicationData(applicantId, userId, null, templateData);
			
			SelectionProcessManager selectionProcessManager = new SelectionProcessManager();
			selectionProcessManager.addPhoneLog(cData);
			selectionProcessManager.changeCommunicationHideInteraction(cData.getCommunicationId());
		}
		return filePath;
	}
	
	public String[] generateOfferSheet(ApplicantOfferModel applicantOfferModel, String operation, String userId, HttpServletRequest request) throws Exception {
		return generateOfferSheet(applicantOfferModel.getApplicantId(), 
					applicantOfferModel.getOfferSheetTemplateId(), operation, 
					userId, applicantOfferModel.getOfferFormat(), request);
	}
	
	public String[] generateOfferSheet(String applicantId, String offerSheetTemplateId, String operation, String userId, int offerFormat, HttpServletRequest request) throws Exception {
		OfferSheetTemplateData templateData = getOfferSheetTemplate(offerSheetTemplateId);
		List<OfferSheetTemplateVariable> templateVariables = getOfferSheetTemplateVariables(offerSheetTemplateId);
		populateOfferSheetTemplateVariableValues(templateVariables, request);
				
		OfferSheetUtils offerSheetUtils = new OfferSheetUtils();
		String[] filePath = offerSheetUtils.getDestinationPath(templateData, operation, userId, offerFormat);
		
		OfferSheetGenerator generator = OfferSheetGeneratorFactory.getOfferSheetGenerator(templateData.getTemplateFileName(), offerFormat);
		generator.generateOfferSheet(templateData, templateVariables, filePath[0]);
		
		return filePath;
	}
	
	public String saveGeneratedOfferData(ApplicantOfferModel applicantOfferModel, String[] offerSheetPath, String userId, HttpServletRequest request) throws Exception {
		String applicantId = applicantOfferModel.getApplicantId();
		SelectionProcessManager spm		= new SelectionProcessManager();
		ApplicantManager am				= new ApplicantManager();
		CommunicationManager cm			= new CommunicationManager();
		DBTransaction tran = null;
		String offerFileName = null;
		try {
			Long positionId=spm.getPositionId(applicantId);
			String applicantName = am.getApplicantNameandSource(applicantId)[0];
			ApplicantOfferDetails previousOfferDetails = buildApplicantOfferDetails(spm.getApplicantOfferDetails(applicantId)); 
			tran = new DBTransaction();
			spm.saveOfferDetails(applicantId, applicantOfferModel.getAppOfferDetails(), tran);
			String ext = FileHandlerUtils.getFileExtention(offerSheetPath[2]); 
			offerFileName = applicantName+" ["+ applicantOfferModel.getOfferCode() + "] "+ext;
			if(applicantOfferModel.isModifyOffer()){
				ApplicantOfferSheetDetails aosd = getExistingOfferSheetDetails(applicantId, tran);
				updateOfferSheetDetails(applicantId, applicantOfferModel.getOfferCode(), applicantOfferModel.getOfferSheetTemplateId(), tran);				
				updateOfferDocument(aosd.getOfferLetterDocId(), offerSheetPath, offerFileName, userId, tran);
				int latestVersion=getLatestOfferVersion(applicantOfferModel,tran);
				saveOfferDetails(applicantOfferModel, request,tran,positionId,latestVersion+OFFER_VERSION_INCREMENT);
				cm.logOfferGeneratedInteraction(applicantId, applicantOfferModel.getOfferCode(), offerFileName, previousOfferDetails, applicantOfferModel.getAppOfferDetails(), true, userId, tran);
			}else {
				String documentId = insertOfferDocument(applicantId, offerSheetPath, offerFileName, userId, tran);
				saveOfferSheetDetails(applicantId, applicantOfferModel.getOfferCode(), documentId, applicantOfferModel.getOfferSheetTemplateId(), tran);
				cm.logOfferGeneratedInteraction(applicantId, applicantOfferModel.getOfferCode(), offerFileName, previousOfferDetails, applicantOfferModel.getAppOfferDetails(), false, userId, tran);
				saveOfferDetails(applicantOfferModel, request,tran,positionId,OFFER_VERSION);
			}
			tran.commit();			
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while saving Generated Offer data for applicant: "+applicantId, e);
			try {
				if(tran!=null)
					tran.rollback();
			} catch (Exception te) {
				TPLogger.getLogger().error("error in transaction roll back", te);
			}
			throw e;
		} finally {
			if(tran!=null)
				tran.release();
		}
		return offerFileName;
	}

	private int getLatestOfferVersion(ApplicantOfferModel applicantOfferModel, DBTransaction tran) {
		DBPreparedQuery dq = null;
		int version = 0;
		try {
				if(tran == null) {
					dq = new DBPreparedQuery("dOfferSheetManager_getLatestOfferVersion");
				} else {
					dq = new DBPreparedQuery("dOfferSheetManager_getLatestOfferVersion", tran);
				}			
				dq.setString(1, applicantOfferModel.getApplicantId());
				dq.setString(2, applicantOfferModel.getOfferSheetTemplateId());
				dq.setString(3,applicantOfferModel.getOfferCode());
				version = dq.getIntResult();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NoResultFoundException e) {
			e.printStackTrace();
		} finally {		
			if(dq != null) {
				if(tran != null) {
					dq.closeOpenCursors();
				} else {
					dq.releaseConnection();
				}
			}			
		}	
		return version;
	}
	private void saveOfferDetails(ApplicantOfferModel applicantOfferModel, HttpServletRequest request,DBTransaction tran, Long positionId,int version)
			throws SQLException {
		List<OfferSheetTemplateVariable> templateVariables = getOfferSheetTemplateVariables(applicantOfferModel.getOfferSheetTemplateId());
		populateOfferSheetTemplateVariableValues(templateVariables, request);
		saveOfferDetails(applicantOfferModel,templateVariables,tran,positionId,version);
		
	}
	private void saveOfferDetails(ApplicantOfferModel applicantOfferModel, List<OfferSheetTemplateVariable> templateVariables,DBTransaction tran,Long positionId,int version){
		DBPreparedQuery dq = null;
		try {
			for(OfferSheetTemplateVariable templateVariable :templateVariables){
				if(tran == null) {
					dq = new DBPreparedQuery("dOfferSheetManager_insertOfferDetails");
				} else {
					dq = new DBPreparedQuery("dOfferSheetManager_insertOfferDetails", tran);
				}			
				dq.setString(1, applicantOfferModel.getApplicantId());
				dq.setLong(2, positionId);
				dq.setString(3, applicantOfferModel.getOfferSheetTemplateId());
				dq.setString(4, applicantOfferModel.getOfferCode());
				dq.setInt(5, version);
				dq.setString(6, templateVariable.getTemplateVariableAttribute());
				dq.setString(7, templateVariable.getTemplateVariableVal());
				dq.execute();	
			}
			if(tran == null) {
				dq = new DBPreparedQuery("dOfferSheetManager_updateLatestOfferVersion");
			} else {
				dq = new DBPreparedQuery("dOfferSheetManager_updateLatestOfferVersion", tran);
			}
			dq.setInt(1,version);
			dq.setString(2, applicantOfferModel.getApplicantId());
			dq.setString(3, applicantOfferModel.getOfferSheetTemplateId());
			dq.setString(4,applicantOfferModel.getOfferCode());
			dq.execute();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {		
			if(dq != null) {
				if(tran != null) {
					dq.closeOpenCursors();
				} else {
					dq.releaseConnection();
				}
			}			
		}	
	}

	private String insertOfferDocument(String applicantId, String[] offerSheetPath, String offerFileName, String userId, DBTransaction tran) throws SQLException{
		String[] parts = offerSheetPath[0].split(DocumentConstants.documentsPath);
		DocumentManager documentManager = new DocumentManager();
		return documentManager.insertApplicantDocument(applicantId, offerFileName, parts[1], true, userId, tran);
	}
	
	private void updateOfferDocument(String documentId, String[] offerSheetPath, String offerFileName, String userId, DBTransaction tran) throws SQLException {
		String[] parts = offerSheetPath[0].split(DocumentConstants.documentsPath);
		DocumentManager documentManager = new DocumentManager();
		documentManager.updateApplicantDocument(documentId, offerFileName, parts[1], userId, tran);
	}
	
	private void saveOfferSheetDetails(String applicantId, String offerCode, String documentId, String offerSheetTemplateId, DBTransaction tran)throws SQLException{
		deleteExistingOfferSheetDetails(applicantId, tran);
		insertOfferSheetDetails(applicantId, offerCode, documentId, offerSheetTemplateId, tran);
	}
	
	private void deleteExistingOfferSheetDetails(String applicantId, DBTransaction tran) throws SQLException{
		DBPreparedQuery dq = null;
		try {
			if(tran == null) {
				dq = new DBPreparedQuery("dOfferSheetManager_deleteExistingOfferSheetDetails");
			} else {
				dq = new DBPreparedQuery("dOfferSheetManager_deleteExistingOfferSheetDetails", tran);
			}			
			dq.setString(1, applicantId);
			dq.execute();
		} finally {		
			if(dq != null) {
				if(tran != null) {
					dq.closeOpenCursors();
				} else {
					dq.releaseConnection();
				}
			}			
		}	
	}
	
	private void insertOfferSheetDetails(String applicantId,String offerCode,String documentId,String offerSheetTemplateId,DBTransaction tran) throws SQLException{
		DBPreparedQuery dq = null;
		try {
			if(tran == null) {
				dq = new DBPreparedQuery("dOfferSheetManager_insertOfferSheetDetails");
			} else {
				dq = new DBPreparedQuery("dOfferSheetManager_insertOfferSheetDetails", tran);
			}			
			dq.setString(1, applicantId);
			dq.setString(2, offerCode);
			dq.setString(3, documentId);
			dq.setString(4, offerSheetTemplateId);
			dq.execute();
		} finally {		
			if(dq != null) {
				if(tran != null) {
					dq.closeOpenCursors();
				} else {
					dq.releaseConnection();
				}
			}			
		}	
	}
	
	private void updateOfferSheetDetails(String applicantId, String offerCode, String offerSheetTemplateId, DBTransaction tran) throws SQLException{
		DBPreparedQuery dq = null;
		try {
			if(tran == null) {
				dq = new DBPreparedQuery("dOfferSheetManager_updateOfferSheetDetails");
			} else {
				dq = new DBPreparedQuery("dOfferSheetManager_updateOfferSheetDetails", tran);
			}			
			dq.setString(1, offerCode);
			dq.setString(2, offerSheetTemplateId);
			dq.setString(3, applicantId);
			dq.execute();
		} finally {		
			if(dq != null) {
				if(tran != null) {
					dq.closeOpenCursors();
				} else {
					dq.releaseConnection();
				}
			}			
		}	
	}
	
	private CommunicationData getCommunicationData(String applicantId, String userId, 
			String documentId, OfferSheetTemplateData templateData) {
		Calendar cal = new GregorianCalendar();
		Date dtLogDate = cal.getTime();		
		
		CommunicationData cData = new CommunicationData();
		cData.setApplicantId(applicantId);
		cData.setUserId(userId);
		cData.setCommunicationType(SelectionProcessConstants.INTERACTION_NOTE);
		cData.setCommunicationDate(new java.sql.Timestamp(dtLogDate.getTime()));
		cData.setCommunicationText(templateData.getTemplateName() + " " + TPLabels.getLabel("hire.label.offer_generated"));	
		cData.setDocumentId(documentId);
		
		return cData;
	}

	private void populateOfferSheetTemplateVariableValues(List<OfferSheetTemplateVariable> templateVariables, HttpServletRequest request) {
		if(templateVariables != null && templateVariables.size() > 0) {
			for(int i = 0; i < templateVariables.size(); i++) {
				OfferSheetTemplateVariable variable = templateVariables.get(i);
				variable.setTemplateVariableVal(request.getParameter(variable.getTemplateVariable()));
			}
		}
	}

	public void populateTemplateVariableVals(SimpleDataObject aData, List<OfferSheetTemplateVariable> templateVariables) throws SQLException {
		if(templateVariables != null && aData != null) {
			for(int i = 0; i < templateVariables.size(); i++) {
				OfferSheetTemplateVariable variable = templateVariables.get(i);				
				if(!Utils.isBlankOrNull(variable.getTemplateVariableAttribute())) {
					variable.setTemplateVariableVal(aData.getString(variable.getTemplateVariableAttribute()));
				}
			}
		}		
	}
	
	public SimpleDataObject getTemplateVariableDataMapping(String applicantId, ApplicantOfferDetails aod) throws SQLException{
		ApplicantManager applicantManager = new ApplicantManager();
		SalaryCalculator salaryCalculator = new SalaryCalculator();
		SalaryStructure salaryComponentData = null;
		
		SimpleDataObject aData = applicantManager.getApplicantDetails(applicantId);
		if(aod==null)
			try {
				salaryComponentData = salaryCalculator.calculateSalary(applicantId);
			} catch (SalaryCalculationException e) {
				salaryComponentData = null;
			}
		else{
			SimpleDataObject sdo = applicantManager.getApplicantGradeCTCBasic(applicantId);
			try {
				salaryComponentData = salaryCalculator.calculateSalary(sdo.getString("gradeId"), aod.getOfferedCTC(), aod.getOfferedBasic(), aod.getInputSalaryVariable());
			} catch (SalaryCalculationException e) {
				salaryComponentData = null;
			}
			aData.setAttribute(OfferSheetConstants.OFFERED_CTC, aod.getOfferedCTC());
			aData.setAttribute(OfferSheetConstants.INPUT_SALARY_VARIABLE, aod.getInputSalaryVariable());
			aData.setAttribute(OfferSheetConstants.OFFERED_BASIC, aod.getOfferedBasic());
			aData.setAttribute(OfferSheetConstants.APPLICANT_DESIGNATION_OFFERED, aod.getOfferedDesignation());
			aData.setAttribute(OfferSheetConstants.APPLICANT_LEVEL_OFFERED, aod.getOfferedLevel());
		}

		populateSalaryVariables(salaryComponentData, aData);
		
		EducationalData eduData = applicantManager.getLatestEducationalInfo(applicantId);
		if(eduData!=null)
			aData.addAttributes(eduData);
		modifyOfferDataObject(aData);
		return aData;
	} 

	public String getTemplateVariableValue(String applicantId,
			String templateId, String templateVariable,
			String templateVariableAttribute) throws SQLException {		
		String attributeVal = "";
		updateOfferSheetTemplateVariableAttribute(templateId, templateVariable, templateVariableAttribute);
		
		ApplicantManager applicantManager = new ApplicantManager();
		SimpleDataObject aData = applicantManager.getApplicantDetails(applicantId);
		attributeVal = aData.getString(templateVariableAttribute);
		return attributeVal;
	}

	public void updateOfferSheetTemplateVariableAttribute(
			String templateId, String templateVariable,
			String templateVariableAttribute) throws SQLException {
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dOfferSheetManager_UpdateTemplateVariableAttribute");
			dq.setString(1, templateVariableAttribute);
			dq.setString(2, templateId);
			dq.setString(3, templateVariable);
			dq.execute();
		} finally {
			if(dq != null) {
				dq.releaseConnection();
			}
		}
	}
	
	private void modifyOfferDataObject(SimpleDataObject offerDetailsData){
		formatToSystemDate(offerDetailsData,OfferSheetConstants.APPLICANT_DATE_JOINED);
		formatToSystemDate(offerDetailsData,OfferSheetConstants.TODAY_DATE);
		formatDate(offerDetailsData,OfferSheetConstants.APPLICANT_YOP,DateConstants.YEAR_PATTERN);
	}
	
	private void formatToSystemDate(SimpleDataObject offerDetailsData, String param){
		try {
			offerDetailsData.setAttribute(param, DateUtils.getSystemDateFormat(offerDetailsData.getDate(param))) ;			
		} catch (ClassCastException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
	}
	
	private void formatDate(SimpleDataObject offerDetailsData, String param, String pattern){
		try {
			offerDetailsData.setAttribute(param, DateUtils.getDateFormated(offerDetailsData.getDate(param),pattern));			
		} catch (ClassCastException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
	}
	
	public String generateOfferCode(SimpleDataObject aData) {
		Calendar calendar = GregorianCalendar.getInstance();
		String year = String.valueOf(calendar.get(GregorianCalendar.YEAR));
		String month = String.valueOf(calendar.get(GregorianCalendar.MONTH) + 1);
		if(month.length() < 2) {
			month = "0" + month;
		}
		String date = String.valueOf(calendar.get(GregorianCalendar.DATE));
		if(date.length() < 2) {
			date = "0" + date;
		}
		String offerCodeTemplate = GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_OFFER_CODE_TEMPLATE);
		Pattern p = Pattern.compile(com.talentPool.offerSheet.OfferSheetConstants.PATTERN_OFFER_CODE_COMPONENT);
		Matcher m = p.matcher(offerCodeTemplate);
		String offerCode = new String(offerCodeTemplate);
		ArrayList<String> components = new ArrayList<String>();
		while (m.find()) {
			//capsbghymdn
			String match = m.group();
			if (!Utils.isBlankOrNull(match)) {
				if (match.indexOf("c") != -1 && !Utils.isBlankOrNull(aData.getString(OfferSheetConstants.COMPANY_NAME))) {
					offerCode = replaceMatch(offerCode, aData.getString(OfferSheetConstants.COMPANY_NAME) , match, m, components);
				} else if(match.indexOf("a") != -1 && !Utils.isBlankOrNull(aData.getString(OfferSheetConstants.APPLICANT_NAME))) {
					offerCode = replaceMatch(offerCode, aData.getString(OfferSheetConstants.APPLICANT_NAME), match, m, components);
				} else if (match.indexOf("p") != -1 && !Utils.isBlankOrNull(aData.getString(OfferSheetConstants.POSITION_TITLE))) {
					offerCode = replaceMatch(offerCode, aData.getString(OfferSheetConstants.POSITION_TITLE), match, m, components);
				} else if (match.indexOf("s") != -1 && !Utils.isBlankOrNull(aData.getString(OfferSheetConstants.DEPT_NAME))) {
					offerCode = replaceMatch(offerCode, aData.getString(OfferSheetConstants.DEPT_NAME), match, m, components);
				} else if (match.indexOf("b") != -1 && !Utils.isBlankOrNull(aData.getString(OfferSheetConstants.POSITION_BAND_NAME))) {
					offerCode = replaceMatch(offerCode, aData.getString(OfferSheetConstants.POSITION_BAND_NAME), match, m, components);
				} else if (match.indexOf("g") != -1 && !Utils.isBlankOrNull(aData.getString(OfferSheetConstants.POSITION_GRADE_NAME))) {
					offerCode = replaceMatch(offerCode, aData.getString(OfferSheetConstants.POSITION_GRADE_NAME), match, m, components);
				} else if (match.indexOf("y") != -1) {
					offerCode = replaceMatch(offerCode, year, match, m, components);
				} else if (match.indexOf("m") != -1) {
					offerCode = replaceMatch(offerCode, month, match, m, components);
				} else if (match.indexOf("d") != -1) {
					offerCode = replaceMatch(offerCode, date, match, m, components);
				} else if (match.indexOf("n") != -1) {
					Long noOfOffers = getNumberOfOffersGenerated();
					noOfOffers += 1;
					String num = "";
					if ((match.length() - 2) > String.valueOf(noOfOffers).length()) {
						for (int i = 0; i < (match.length() - 2 - String.valueOf(noOfOffers).length()); i++) {
							num += "0";
						}
						num += String.valueOf(noOfOffers);
					} else {
						num = String.valueOf(noOfOffers);
					}
					offerCode = replaceMatch(offerCode, num, match, m, components);
				}
			}
		}
		
		p = Pattern.compile(com.talentPool.offerSheet.OfferSheetConstants.PATTERN_OFFER_CODE_APPLICANT_ID_COMPONENT);
		m = p.matcher(offerCodeTemplate);
		while (m.find()) {
			String match = m.group();
			if (!Utils.isBlankOrNull(match)) {
				if (match.indexOf("i") != -1) {
					offerCode = replaceApplicantId(offerCode, aData.getString(OfferSheetConstants.APPLICANT_ID), m);
				}
			}
		}
		
		for (int i = 0; i < components.size(); i += 2) {
			String toBeReplacedString = components.get(i);
			String replaceWith = components.get(i + 1);
			offerCode = offerCode.replace(toBeReplacedString, replaceWith);
		}
		offerCode = offerCode.replaceAll("\\{", "").replaceAll("\\}", "");
		return offerCode;
	}
	
	private static String replaceMatch(String offerCode, String component, String match, Matcher m, ArrayList<String> components) {
		if (!Utils.isBlankOrNull(component)) {
			if (component.length() < match.length() - 2) {
				offerCode = offerCode.substring(0, m.start()) + "{" + match.substring(1, match.length() - 1 - component.length()) + component + "}" + offerCode.substring(m.end(), offerCode.length());
				components.add(match.substring(1, match.length() - 1 - component.length()) + component);
				components.add(component);
			} else {
				offerCode = offerCode.substring(0, m.start()) + "{" + component.substring(0, m.group().length() - 2) + "}" + offerCode.substring(m.end(), offerCode.length());
			}
		}
		return offerCode;
	}
	
	private Long getNumberOfOffersGenerated(){
		CustomIncrementCounter cic = new CustomIncrementCounter();
		Long offerNum = null;
		DBTransaction tran = null;
		try {
			tran = new DBTransaction();
			offerNum = cic.getCurrentCounter(CustomIncrementConstants.OFFER_NUMBER, tran);
			cic.incrementCounterVariable(CustomIncrementConstants.OFFER_NUMBER, tran);
			tran.commit();
		} catch (SQLException e) {
			TPLogger.getLogger().error("Error in incrementing increment counter value", e);
			try {
				tran.rollback();
			} catch (SQLException e1) {
				TPLogger.getLogger().error("Not able to rollback incremented counter", e);
			}
		} finally{
			if(tran != null) {
				tran.release();
			}
		}
		return offerNum;
		
	}
	
	private static String replaceApplicantId(String offerCode, String component, Matcher m) {
		offerCode = offerCode.substring(0, m.start()) + "{" + component + "}" + offerCode.substring(m.end(), offerCode.length());
		return offerCode;
	}
	
	/**
	 * CHecks if offer is already generated for the given applicantId 
	 * @param applicantId
	 * @return
	 */
	public boolean isOfferAlreadygenerated(String applicantId) {
		DBPreparedQuery dq = null;
		int cnt = -1;
		try {
			dq = new DBPreparedQuery("dOfferSheetManager_isOfferAlreadyGenerated");
			dq.setString(1, applicantId);
			cnt = dq.getIntResult();
			if(cnt==1)
				return true;
			else
				return false;
		} catch(SQLException | NoResultFoundException e){
			return false;
		} finally {
			if(dq != null) {
				dq.releaseConnection();
			}
		}
	}
	
	public String getApplicantCurrentOfferSheet(String applicantId) throws SQLException {
		DBPreparedQuery dq = null;
		String offerSheetPath = null;
		try {
			dq = new DBPreparedQuery("dOfferSheetManager_getApplicantCurrentOfferSheet");
			dq.setId(1, applicantId);
			offerSheetPath = dq.getStringResult();
		} catch(SQLException e){
			throw e;
		} finally {
			if(dq != null) {
				dq.releaseConnection();
			}
		}
		return offerSheetPath;
	}
	
	public ApplicantOfferSheetDetails getExistingOfferSheetDetails(String applicantId, DBTransaction tran) throws Exception {
		DBPreparedQuery dq = null;
		ApplicantOfferSheetDetails aosd = null;
		try {
			if(tran!=null)
				dq = new DBPreparedQuery("dOfferSheetManager_getExistingOfferSheetDetails", tran);
			else
				dq = new DBPreparedQuery("dOfferSheetManager_getExistingOfferSheetDetails");
			
			dq.setId(1, applicantId);
			aosd = (ApplicantOfferSheetDetails) dq.getSingleObjectResult();
		} catch(SQLException e){
			TPLogger.getLogger().error("Error while fetching offer sheet details for applicantId: " + applicantId, e);
			throw e;
		} finally {
			if(dq != null) {
				if(tran != null) {
					dq.closeOpenCursors();
				} else {
					dq.releaseConnection();
				}
			}	
		}
		return aosd;
	}
	
	private ApplicantOfferDetails buildApplicantOfferDetails(FeedbackData fd){
		if(fd!=null)
			return new ApplicantOfferDetails(fd.getCtcOffered(), fd.getInputSalaryVariable(), fd.getBasicOffered(), fd.getDesignationOffered(), fd.getLevelOffered());
		else
			return new ApplicantOfferDetails();
		
	}
	
	private void populateSalaryVariables(SalaryStructure salStructure, SimpleDataObject aData){
		if(salStructure!=null){
			Map<Integer, Integer> annaulSalVals = salStructure.getAnnualSalCompVals();
			Map<Integer, Integer> monthlySalVals = salStructure.getMonthlySalCompVals();
			Map<String, Integer> annualSalCatTotals = salStructure.getAnnualCatTotals();
			Map<String, Integer> monthlySalCatTotals = salStructure.getMonthlyCatTotals();
			if(annaulSalVals!=null){
				for (Entry<Integer, Integer> entry : annaulSalVals.entrySet()) {
					aData.setAttribute(com.talentPool.offerSheet.OfferSheetConstants.SAL_COMPONENT_PREFIX_ANNUAL+entry.getKey(), entry.getValue());
				}				
			}
			if(monthlySalVals!=null){
				for (Entry<Integer, Integer> entry : monthlySalVals.entrySet()) {
					aData.setAttribute(com.talentPool.offerSheet.OfferSheetConstants.SAL_COMPONENT_PREFIX_MONTHLY+entry.getKey(), entry.getValue());
				}				
			}
			if(annualSalCatTotals!=null){
				for (Entry<String, Integer> entry : annualSalCatTotals.entrySet()) {
					aData.setAttribute(com.talentPool.offerSheet.OfferSheetConstants.SAL_CATEGORY_PREFIX_ANNUAL+entry.getKey(), entry.getValue());
				}	
			}
			if(monthlySalCatTotals!=null){
				for (Entry<String, Integer> entry : monthlySalCatTotals.entrySet()) {
					aData.setAttribute(com.talentPool.offerSheet.OfferSheetConstants.SAL_CATEGORY_PREFIX_MONTHLY+entry.getKey(), entry.getValue());
				}	
			}
			aData.setAttribute(com.talentPool.offerSheet.OfferSheetConstants.MONTHLY_TOTAL, salStructure.getMonthlyTotal());
			aData.setAttribute(com.talentPool.offerSheet.OfferSheetConstants.ANNUAL_TOTAL, salStructure.getAnnualTotal());
			aData.setAttribute(com.talentPool.offerSheet.OfferSheetConstants.INPUT_SALARY_VARIABLE_MONTHLY, salStructure.getInputSalaryVariableMonthly());
			aData.setAttribute(com.talentPool.offerSheet.OfferSheetConstants.INPUT_SALARY_VARIABLE_ANNUAL, salStructure.getInputSalaryVariableMonthly());
		}		
	}
}