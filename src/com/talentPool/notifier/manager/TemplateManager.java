/**
 * 
 */
package com.talentPool.notifier.manager;

import java.io.StringWriter;
import java.sql.SQLException;
import java.util.ArrayList;

import com.talentPool.applicant.dataobject.ApplicantData;
import com.talentPool.applicant.manager.ApplicantManager;
import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.db.DBPreparedQuery;
import com.talentPool.common.db.DBTransaction;
import com.talentPool.common.db.SimpleDataObject;
import com.talentPool.common.utils.FileHandler;
import com.talentPool.common.utils.Utils;
import com.talentPool.common.xmlutils.XMLWriter;
import com.talentPool.custom.constants.CustomFieldConstants;
import com.talentPool.custom.dataobject.CustomFieldData;
import com.talentPool.custom.manager.CustomFieldManager;
import com.talentPool.notifier.TemplateConstants;
import com.talentPool.notifier.dataobject.TemplateData;
import com.talentPool.notifier.utils.TemplateUtils;
import com.talentPool.positions.dataobject.PositionData;
import com.talentPool.positions.manager.PositionManager;

/**
 * @author shivprasad
 * 
 */
public class TemplateManager {

	public TemplateData getTemplateData(String templateCode) throws Exception {
		DBPreparedQuery dq = null;
		TemplateData data = null;

		try {
			dq = new DBPreparedQuery("dTemplateManager_GetTemplate");
			dq.setString(1, templateCode);
			data = (TemplateData) (dq.getSingleObjectResult());
		} catch (Exception e) {
			TPLogger.getLogger().error("Error in getting template", e);
			throw e;
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return data;
	}

	public TemplateData getTemplateDataByTemplateTypeId(String templateTypeId) throws Exception {
		DBPreparedQuery dq = null;
		TemplateData data = null;

		try {
			dq = new DBPreparedQuery("dTemplateManager_GetTemplateByTemplateTypeId");
			dq.setString(1, templateTypeId);
			dq.setString(2, TemplateConstants.AUTO_NOT_CREATED);
			data = (TemplateData) (dq.getSingleObjectResult());
		} catch (Exception e) {
			TPLogger.getLogger().error("Error in getting template by template type id", e);
			throw e;
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return data;
	}

	public ArrayList getAllTemplates(String userId, boolean systemTemplates) throws Exception {
		DBPreparedQuery dq = null;
		ArrayList data = null;

		try {
			String[] dynParams = new String[1];
			dynParams[0] = "";
			if (!Utils.isBlankOrNull(userId)) {
				dynParams[0] = " And (tp.user_id = " + userId + " OR tp.template_private=" + TemplateConstants.TEMPLATE_GLOBAL + ")";
			}
			if (systemTemplates) {
				dynParams[0] += " OR tp.template_private= " + TemplateConstants.TEMPLATE_SYSTEM;
			} else {
				dynParams[0] += " AND tp.template_private != " + TemplateConstants.TEMPLATE_SYSTEM;
			}
			dq = new DBPreparedQuery("dTemplateManager_GetAllTemplates", dynParams);
			dq.setString(1, TemplateConstants.AUTO_NOT_CREATED);
			data = dq.getResult();
		} catch (Exception e) {
			TPLogger.getLogger().error("Error in getting template", e);
			throw e;
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return data;
	}

	public String getTemplateDataInXML(TemplateData templateData) {
		StringWriter sWr = new StringWriter();
		XMLWriter wr = new XMLWriter(sWr);
		try {
			wr.startDocument();
			wr.startElement("template");
			if (templateData != null) {
				String templateCode = templateData.getTemplateCode();
				String subjectFile = templateData.getTemplateSubjectFile();
				String contentFile = templateData.getTemplateContentFile();
				VelocityManager velocityManager = new VelocityManager();
				String subject = velocityManager.getContent(subjectFile);
				String content = velocityManager.getContent(contentFile);
				wr.startElement("templateCode");
				wr.characters(wr.doubleEscape(templateCode));
				wr.endElement("templateCode");
				wr.startElement("subject");
				wr.characters(wr.doubleEscape(subject));
				wr.endElement("subject");
				wr.startElement("content");
				wr.characters(wr.doubleEscape(content));
				wr.endElement("content");
			}
			wr.endElement("template");
			wr.endDocument();
		} catch (Exception e) {
			TPLogger.getLogger().error("error while creating xml file for emails", e);
		}
		return sWr.getBuffer().toString();

	}

	/**
	 * create template and save in DB
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public String createTemplate(TemplateData data) throws Exception {
		// get new template code
		String newTemplateCode = TemplateUtils.getNewTemplateCode();
		if (data.getTemplateAuto().equals(TemplateConstants.AUTO_CREATED)) {
			newTemplateCode = "AUTO_" + newTemplateCode;
		}
		// data.setTemplateCode(templateCode);
		if (Utils.isBlankOrNull(data.getTemplateName())) {
			data.setTemplateName(newTemplateCode);
		}
		String subjectVM = newTemplateCode + TemplateConstants.POSTFIX_SUBJECT;
		String contentVM = newTemplateCode + TemplateConstants.POSTFIX_CONTENT;
		data.setTemplateSubjectFile(subjectVM);
		data.setTemplateContentFile(contentVM);
		// write new templates to disk
		
		String repeat = TemplateUtils.isContainRepeat(data.getTemplateContentText());
		saveTemplateFiles(data);

		DBPreparedQuery dq = null;
		DBTransaction tran = null;
		try {
			tran = new DBTransaction();
			String templateTypeId = null;
			if(!Utils.isBlankOrNull(data.getTemplateCode())) {
				templateTypeId = getTemplateTypeId(data.getTemplateCode());
			} else {
				templateTypeId = data.getTemplateTypeId();
			}

			dq = new DBPreparedQuery("dTemplateManager_CreateTemplate", tran);
			dq.setString(1, newTemplateCode);
			dq.setString(2, data.getTemplateName());
			dq.setString(3, data.getTemplateSubjectFile());
			dq.setString(4, data.getTemplateContentFile());
			dq.setString(5, data.getTemplateAuto());
			dq.setString(6, data.getTemplateFormat());
			dq.setString(7, data.getTemplatePrivate());
			dq.setString(8, data.getUserId());
			dq.setString(9, templateTypeId);
			if(Utils.isBlankOrNull(data.getIsTemplateSaveAsDraft())) {
				dq.setString(10, TemplateConstants.TEMPLATE_DONT_SAVE_AS_DRAFT);
			} else{
				dq.setString(10, data.getIsTemplateSaveAsDraft());
			}			
			dq.setString(11, TemplateConstants.TEMPLATE_DONT_SHOW_SAVE_AS_DRAFT_OPTION);
			dq.setString(12, repeat);
			dq.execute();
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			if (dq != null) {
				dq.releaseTransaction(tran);
			}
		}
		return newTemplateCode;
	}

	/**
	 * create template and save in DB
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public String createUserTemplate(TemplateData data) throws Exception {
		// get new template code
		String templateCode = TemplateUtils.getNewTemplateCode();
		data.setTemplateCode(templateCode);

		String subjectVM = templateCode + TemplateConstants.POSTFIX_SUBJECT;
		String contentVM = templateCode + TemplateConstants.POSTFIX_CONTENT;
		data.setTemplateSubjectFile(subjectVM);
		data.setTemplateContentFile(contentVM);
		
		String repeat = TemplateUtils.isContainRepeat(data.getTemplateContentText());
		
		// write new templates to disk
		saveTemplateFiles(data);

		DBPreparedQuery dq = null;
		DBTransaction tran = null;
		try {
			tran = new DBTransaction();
			dq = new DBPreparedQuery("dTemplateManager_CreateTemplate", tran);
			dq.setString(1, templateCode);
			dq.setString(2, data.getTemplateName());
			dq.setString(3, data.getTemplateSubjectFile());
			dq.setString(4, data.getTemplateContentFile());
			dq.setString(5, data.getTemplateAuto());
			dq.setString(6, data.getTemplateFormat());
			dq.setString(7, data.getTemplatePrivate());
			dq.setString(8, data.getUserId());
			dq.setString(9, data.getTemplateTypeId());
			dq.setString(10, data.getIsTemplateSaveAsDraft());
			dq.setString(11, data.getDoShowSaveAsDraftOption());
			dq.setString(12, repeat);
			dq.execute();
			if (TemplateConstants.TEMPLATE_DEFAULT.equalsIgnoreCase(data.getIsTemplateDefault())) {
				setDefaultTemplate(templateCode, data.getTemplateTypeId(), tran);
			}
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			if (dq != null) {
				dq.releaseTransaction(tran);
			}
		}
		return templateCode;
	}

	/**
	 * edit template and save in DB
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public String editUserTemplate(TemplateData data) throws Exception {
		String templateCode = data.getTemplateCode();

		String subjectVM = templateCode + TemplateConstants.POSTFIX_SUBJECT;
		String contentVM = templateCode + TemplateConstants.POSTFIX_CONTENT;
		data.setTemplateSubjectFile(subjectVM);
		data.setTemplateContentFile(contentVM);
		// write new subject and contents to disk
		String repeat = TemplateUtils.isContainRepeat(data.getTemplateContentText());
		saveTemplateFiles(data);

		DBPreparedQuery dq = null;
		DBTransaction tran = null;
		try {
			tran = new DBTransaction();
			String[] dynParam = new String[1];
			if (TemplateConstants.TEMPLATE_SYSTEM.equalsIgnoreCase(data.getTemplatePrivate())) {
				dynParam[0] = "";
			} else {
				dynParam[0] = ", template_private = " + data.getTemplatePrivate();
			}
			dq = new DBPreparedQuery("dTemplateManager_EditTemplate", dynParam, tran);
			dq.setString(1, data.getTemplateName());
			dq.setString(2, data.getTemplateTypeId());
			dq.setString(3, data.getIsTemplateSaveAsDraft());
			dq.setString(4, repeat);
			dq.setString(5, templateCode);
			dq.execute();
			if (TemplateConstants.TEMPLATE_DEFAULT.equalsIgnoreCase(data.getIsTemplateDefault())) {
				setDefaultTemplate(templateCode, data.getTemplateTypeId(), tran);
			}
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			if (dq != null) {
				dq.releaseTransaction(tran);
			}
		}
		return templateCode;
	}

	private void setDefaultTemplate(String templateCode, String templateTypeId, DBTransaction tran) throws SQLException {
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dTemplateManager_ResetDefaultTemplate", tran);
			dq.setString(1, TemplateConstants.TEMPLATE_NOT_DEFAULT);
			dq.setString(2, templateTypeId);
			dq.execute();

			dq = new DBPreparedQuery("dTemplateManager_SetDefaultTemplate", tran);
			dq.setString(1, TemplateConstants.TEMPLATE_DEFAULT);
			dq.setString(2, templateCode);
			dq.execute();
		} finally {
			if (dq != null) {
				if (tran == null) {
					dq.releaseConnection();
				} else {
					dq.closeOpenCursors();
				}
			}
		}
	}

	/**
	 * @param data
	 * @throws Exception
	 *             save template in file systems
	 */
	public void saveTemplateFiles(TemplateData data) throws Exception {
		String subjectTemplate = Utils.concatFilePath(TemplateConstants.VELOCITY_TEMPLATE_DIR, data.getTemplateSubjectFile());
		String contentTemplate = Utils.concatFilePath(TemplateConstants.VELOCITY_TEMPLATE_DIR, data.getTemplateContentFile());
		FileHandler fileHandler = new FileHandler();
		fileHandler.writeToFile(subjectTemplate, data.getTemplateSubjectText(), true);
		fileHandler.writeToFile(contentTemplate, data.getTemplateContentText(), true);
	}

	public String getTemplateTypeId(String templateCode) throws SQLException {
		DBPreparedQuery dq = null;
		String templateTypeId = null;
		try {
			dq = new DBPreparedQuery("dTemplateManager_GetTemplateTypeId");
			dq.setString(1, templateCode);
			SimpleDataObject sDo = (SimpleDataObject) dq.getSingleObjectResult();
			templateTypeId = sDo.getString("templateTypeId");
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return templateTypeId;
	}

	public ArrayList<SimpleDataObject> getTemplateTypes() {
		ArrayList<SimpleDataObject> records = new ArrayList<SimpleDataObject>();
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dTemplateManager_GetTemplateTypes");
			records = dq.getResult();
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while getting the template types", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return records;
	}
	
	public String getPositionStr(String positionId,boolean includeCustomFieldsStr){
		String positionStr = "";
		PositionManager positionManager = null;
		PositionData positionData = null;
		if(!Utils.isBlankOrNull(positionId)){
			positionManager = new PositionManager();
			positionData =positionManager.getPositionSummary(positionId);
			positionStr = getPositionStr(positionData, includeCustomFieldsStr);
		}
		return positionStr;
	}
	
	public String getPositionStr(PositionData positionData,boolean includeCustomFieldsStr){
		String positionStr = "";
		String positionCustomFieldsStr = "";
		if(positionData!=null){
			positionStr = TemplateUtils.getConvertedPositionDescriptionData(positionData);
			if(includeCustomFieldsStr){
				positionCustomFieldsStr=getCustomFieldStr(positionData.getPositionId(), CustomFieldConstants.ENTITY_TYPE_POSITION);
				positionStr=TemplateUtils.appndToToken(positionStr, positionCustomFieldsStr);
			}
		}
		return positionStr;
	}
	
	public String getCustomFieldStr(String positionId,int entityType){
		ArrayList<CustomFieldData> custFieldList = null;
		CustomFieldManager customFieldManager = new CustomFieldManager();
		custFieldList = customFieldManager.getCustomFieldDataForEntity(positionId, entityType);
		String customFieldStr = TemplateUtils.getConvertedCustomFieldData(custFieldList);
		return customFieldStr;
	}
	
	public String getCandidateStr(ApplicantData applicantData,String lastFeedback,boolean includeCustomFieldsStr){
		String candidateStr = null;
		String candidateCustomFieldsStr = null;
		if(applicantData!=null){
			candidateStr = TemplateUtils.getConvertedApplicantData(applicantData,lastFeedback);
			if(includeCustomFieldsStr){
				candidateCustomFieldsStr=getCustomFieldStr(applicantData.getApplicantId(), CustomFieldConstants.ENTITY_TYPE_APPLICANT);
				candidateStr=TemplateUtils.appndToToken(candidateStr, candidateCustomFieldsStr);
			}
		}
		return candidateStr;
	}
	
	public String getCandidateStr(String applicantId,boolean includeCustomFieldsStr){
		String candidateStr = null;
		String lastFeedback = null;
		ApplicantManager applicantManager = new ApplicantManager();
		ApplicantData applicantData = applicantManager.getApplicantData(applicantId);
		lastFeedback = applicantManager.getCandidateInterviewFeedback(applicantData.getApplicantId(), applicantData.getApplicantPositionId(), applicantData.getApplicantStepId());
		candidateStr = getCandidateStr(applicantData, lastFeedback, includeCustomFieldsStr);
		return candidateStr;
	}
	
	public String getCandidateStr(ApplicantData applicantData,boolean includeCustomFieldsStr){
		String candidateStr = null;
		String lastFeedback = null;
		ApplicantManager applicantManager = new ApplicantManager();
		lastFeedback = applicantManager.getCandidateInterviewFeedback(applicantData.getApplicantId(), applicantData.getApplicantPositionId(), applicantData.getApplicantStepId());
		candidateStr = getCandidateStr(applicantData, lastFeedback, includeCustomFieldsStr);
		return candidateStr;
	}

	public ArrayList getSocialMediaTemplates(String userId, boolean systemTemplates) throws Exception {
		DBPreparedQuery dq = null;
		ArrayList data = null;

		try {
			String[] dynParams = new String[1];
			dynParams[0] = "";
			if (!Utils.isBlankOrNull(userId)) {
				dynParams[0] = " And (tp.user_id = " + userId + " OR tp.template_private=" + TemplateConstants.TEMPLATE_GLOBAL + ")";
			}
			if (systemTemplates) {
				dynParams[0] += " OR tp.template_private= " + TemplateConstants.TEMPLATE_SYSTEM;
			} else {
				dynParams[0] += " AND tp.template_private != " + TemplateConstants.TEMPLATE_SYSTEM;
			}
			dynParams[0] += " AND tp.template_type_id= " + TemplateConstants.TEMPLATE_TYPE_SOCIAL_MEDIA;
			dq = new DBPreparedQuery("dTemplateManager_GetAllTemplates", dynParams);
			dq.setString(1, TemplateConstants.AUTO_NOT_CREATED);
			data = dq.getResult();
		} catch (Exception e) {
			TPLogger.getLogger().error("Error in getting template", e);
			throw e;
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return data;
	}
	
}
