/**
 * 
 */
package com.talentPool.masters.manager;

import java.sql.Types;
import java.util.ArrayList;

import com.talentPool.common.CommonConstants;
import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.db.DBPreparedQuery;
import com.talentPool.common.db.DBTransaction;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.properties.TPLabels;
import com.talentPool.common.utils.Utils;
import com.talentPool.masters.constants.FeedbackFieldsConstant;
import com.talentPool.masters.constants.FeedbackFormConstants;
import com.talentPool.masters.dataobject.FeedbackFormData;
import com.talentPool.masters.dataobject.FeedbackFormFieldData;
import com.talentPool.masters.form.FeedbackForm;
import com.talentPool.positions.PositionConstants;

/**
 * @author shivprasad
 * 
 */
public class FeedbackFormManager {
	public ArrayList<FeedbackFormData> getActiveFeedbackForms() {
		DBPreparedQuery dq = null;
		ArrayList<FeedbackFormData> result = null;
		try {
			dq = new DBPreparedQuery("dFeedbackForm_GetAllFeedbackForms");
			dq.setString(1, FeedbackFormConstants.FORM_STATUS_ACTIVE);
			result = dq.getResult();
		} catch (Exception e) {
			TPLogger.getLogger().error("Error", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return result;

	}

	public ArrayList<FeedbackFormFieldData> getFeedbackFormFields(String feedbackFormId) {
		DBPreparedQuery dq = null;
		ArrayList<FeedbackFormFieldData> result = null;
		try {
			dq = new DBPreparedQuery("dFeedbackForm_GetFeedbackFormFields");
			dq.setString(1, FeedbackFormConstants.SUMMARY_FIELD);
			dq.setString(2, " ("+TPLabels.getLabel("common.summary_feedback_field")+")");
			dq.setString(3, FeedbackFormConstants.FIELD_TYPE_CATEGORY);
			dq.setString(4, FeedbackFieldsConstant.FIELD_TYPE_NORMAL);
			dq.setString(5, FeedbackFormConstants.FIELD_TYPE_FIELD);
			dq.setString(6, feedbackFormId);
			result = dq.getResult();
		} catch (Exception e) {
			TPLogger.getLogger().error("Error", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return result;

	}

	public FeedbackFormData getFeedbackFormData(String feedbackFormId) {
		DBPreparedQuery dq = null;
		FeedbackFormData feedbackFormData = null;
		try {
			dq = new DBPreparedQuery("dFeedbackForm_GetFeedbackFormData");
			dq.setString(1, feedbackFormId);
			feedbackFormData = (FeedbackFormData) dq.getSingleObjectResult();
			if (feedbackFormData != null) {
				ArrayList<FeedbackFormFieldData> feedbackFormFields = getFeedbackFormFields(feedbackFormId);
				feedbackFormData.setFeedbackFormFields(feedbackFormFields);
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return feedbackFormData;

	}

	public int getOpenPositionsForForm(String feedbackFormId) throws Exception {
		DBPreparedQuery dq = null;
		int tot = 0;
		try {
			if (!Utils.isBlankOrNull(feedbackFormId)) {
				dq = new DBPreparedQuery("dFeedbackForm_CountOpenPositionsForForm");
				dq.setString(1, PositionConstants.POSITION_STATUS_OPENED);
				dq.setString(2, PositionConstants.STEP_ACTIVE);
				dq.setString(3, feedbackFormId);
				tot = dq.getIntResult();
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error", e);
			throw e;
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return tot;
	}

	public void changeFeedbackFormStatus(String feedbackFormId) throws Exception {
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dFeedbackForm_ChangeFeedbackFormStatus");
			dq.setString(1, FeedbackFormConstants.FORM_STATUS_DELETED);
			dq.setString(2, feedbackFormId);
			dq.execute();
		} catch (Exception e) {
			TPLogger.getLogger().error("Error", e);
			throw e;
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
	}

	public int getTotalCommentsSubmittedForFields(String feedbackFormFieldIds) throws Exception {
		DBPreparedQuery dq = null;
		String[] dynParam = new String[1];
		int tot = 0;
		try {
			if (!Utils.isBlankOrNull(feedbackFormFieldIds)) {
				dynParam[0] = "";
				ArrayList<String> dynamicContent = new ArrayList<String>();
				String qMarks = Utils.setDynamicParamsAndReturnQmarks(feedbackFormFieldIds, dynamicContent);
				dynParam[0] = qMarks;
				dq = new DBPreparedQuery("dFeedbackForm_CountCommentsAgainstFields", dynParam);
				int cnt = 1;
				for (int i = 0; i < dynamicContent.size(); i++) {
					dq.setString(cnt++, dynamicContent.get(i));
				}
				tot = dq.getIntResult();
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error", e);
			throw e;
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return tot;
	}

	public void addFeedbackForm(String feedbackFormTitle, String feedbackFormDesc, String displayType, ArrayList<FeedbackFormFieldData> formFields, String userId) throws Exception {
		DBPreparedQuery dq = null;
		DBTransaction tran = null;
		try {
			tran = new DBTransaction();
			dq = new DBPreparedQuery("dFeedbackForm_AddForm", tran);
			dq.setString(1, feedbackFormTitle);
			dq.setString(2, feedbackFormDesc);
			dq.setString(3, displayType);
			dq.setString(4, userId);
			dq.setString(5, FeedbackFormConstants.FORM_STATUS_ACTIVE);
			dq.execute();

			dq = new DBPreparedQuery("dFetchLastInsertID", tran);
			String feedbackFormId = dq.getIdResult();

			for (int i = 0; formFields != null && i < formFields.size(); i++) {
				FeedbackFormFieldData feedbackFormFieldData = formFields.get(i);
				addFormField(feedbackFormId, feedbackFormFieldData.getFeedbackFieldId(), feedbackFormFieldData.getFeedbackFormFieldDesc(), i + 1, feedbackFormFieldData
						.getFeedbackFormFieldType(), feedbackFormFieldData.getRatingId(), feedbackFormFieldData.getMultipleSelectId(),feedbackFormFieldData.getFeedbackFormFieldCommentRequired(), 
						feedbackFormFieldData.getFieldDisplayType(), feedbackFormFieldData.getFieldIsMandatory(), tran);
			}
			tran.commit();
		} catch (Exception e) {
			TPLogger.getLogger().error("", e);
			try {
				tran.rollback();
			} catch (Exception ex) {
				// TODO: handle exception
			}
			throw e;
		} finally {
			if (dq != null) {
				dq.releaseTransaction(tran);
			}
		}
	}

	public void addFormField(String feedbackFormId, String feedbackFieldId, String fieldDesc, int rank, String fieldType, String ratingId, String multipleSelectId, String fieldCommentRequired, String fieldDisplayType, String fieldIsMandatory, DBTransaction tran) throws Exception {
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dFeedbackForm_AddFormField", tran);
			dq.setString(1, feedbackFormId);
			dq.setString(2, feedbackFieldId);
			dq.setString(3, fieldDesc);
			dq.setInt(4, rank);
			dq.setString(5, fieldType);
			dq.setString(6, Utils.isBlankOrNull(ratingId) ? null : ratingId);
			dq.setString(7, Utils.isBlankOrNull(multipleSelectId) ? null : multipleSelectId);
			dq.setString(8, fieldCommentRequired);
			dq.setString(9, fieldDisplayType);
			dq.setString(10, fieldIsMandatory);
			dq.execute();

		} catch (Exception e) {
			TPLogger.getLogger().error("", e);
			throw e;
		} finally {
			if (dq != null) {
				dq.closeOpenCursors();
			}
		}
	}

	public void updateFeedbackForm(String feedbackFormId, String feedbackFormTitle, String feedbackFormDesc, ArrayList<FeedbackFormFieldData> formFields, String userId) throws Exception {
		DBPreparedQuery dq = null;
		DBTransaction tran = null;
		try {
			tran = new DBTransaction();
			dq = new DBPreparedQuery("dFeedbackForm_UpdateForm", tran);
			dq.setString(1, feedbackFormTitle);
			dq.setString(2, feedbackFormDesc);
			dq.setString(3, feedbackFormId);
			dq.execute();

			// get previous formfields, and delete fields which don't exist
			ArrayList<FeedbackFormFieldData> prevFormFields = getFeedbackFormFields(feedbackFormId);
			for (int i = 0; prevFormFields != null && i < prevFormFields.size(); i++) {
				boolean fieldIsdeleted = true;
				for (int k = 0; formFields != null && k < formFields.size(); k++) {
					if (formFields.get(k).getFeedbackFormFieldId().equals(prevFormFields.get(i).getFeedbackFormFieldId())) {
						fieldIsdeleted = false;
						break;
					}
				}
				if (fieldIsdeleted) {
					deleteFormField(prevFormFields.get(i).getFeedbackFormFieldId(), tran);
				}
			}
			for (int i = 0; formFields != null && i < formFields.size(); i++) {
				FeedbackFormFieldData feedbackFormFieldData = formFields.get(i);
				if (feedbackFormFieldData.getFeedbackFormFieldId().equals("0")) {
					// add new
					addFormField(feedbackFormId, feedbackFormFieldData.getFeedbackFieldId(), feedbackFormFieldData.getFeedbackFormFieldDesc(), i + 1, feedbackFormFieldData
							.getFeedbackFormFieldType(), feedbackFormFieldData.getRatingId(), feedbackFormFieldData.getMultipleSelectId(), feedbackFormFieldData.getFeedbackFormFieldCommentRequired(), 
							feedbackFormFieldData.getFieldDisplayType(), feedbackFormFieldData.getFieldIsMandatory(), tran);

				} else {
					// update rank for existing
					updateFormField(feedbackFormFieldData.getFeedbackFormFieldId(), feedbackFormFieldData.getFeedbackFormFieldDesc(), i + 1,
							feedbackFormFieldData.getRatingId(), feedbackFormFieldData.getMultipleSelectId(), feedbackFormFieldData.getFeedbackFormFieldCommentRequired(), 
							feedbackFormFieldData.getFieldDisplayType(), feedbackFormFieldData.getFieldIsMandatory(), tran);
				}
			}
			tran.commit();
		} catch (Exception e) {
			TPLogger.getLogger().error("", e);
			try {
				tran.rollback();
			} catch (Exception ex) {
				// TODO: handle exception
			}
			throw e;
		} finally {
			if (dq != null) {
				dq.releaseTransaction(tran);
			}
		}
	}

	private void deleteFormField(String fieldId, DBTransaction tran) throws Exception {
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dFeedbackForm_DeleteFormField", tran);
			dq.setString(1, fieldId);
			dq.execute();
		} catch (Exception e) {
			TPLogger.getLogger().error("", e);
			throw e;
		} finally {
			if (dq != null) {
				dq.closeOpenCursors();
			}
		}
	}

	public void updateFormField(String fieldId, String fieldDesc, int rank, String ratingId, String multipleSelectId, String fieldCommentRequired, String fieldDisplayType, String fieldIsMandatory, DBTransaction tran) throws Exception {
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dFeedbackForm_UpdateFormField", tran);
			dq.setString(1, fieldDesc);
			dq.setInt(2, rank);
			dq.setString(3, Utils.isBlankOrNull(ratingId) ? null : ratingId);
			dq.setString(4, Utils.isBlankOrNull(multipleSelectId) ? null : multipleSelectId);
			dq.setString(5, fieldCommentRequired);
			dq.setString(6, fieldDisplayType);
			dq.setString(7, fieldIsMandatory);
			dq.setString(8, fieldId);
			dq.execute();
		} catch (Exception e) {
			TPLogger.getLogger().error("", e);
			throw e;
		} finally {
			if (dq != null) {
				dq.closeOpenCursors();
			}
		}
	}

	public ArrayList<FeedbackFormFieldData> getDefaultFeedbackFormFields(String displayType) {
		DBPreparedQuery dq = null;
		ArrayList<FeedbackFormFieldData> result = null;
		try {
			dq = new DBPreparedQuery("dFeedbackForm_GetDefaultFeedbackFormFields");
			dq.setString(1, FeedbackFormConstants.FIELD_TYPE_CATEGORY);
			dq.setString(2, FeedbackFormConstants.SYSTEM_GENERATED);
			dq.setString(3, FeedbackFormConstants.FIELD_TYPE_FIELD);	
			dq.setString(4, displayType);	
			dq.setString(5, FeedbackFormConstants.SYSTEM_GENERATED);
					
			result = dq.getResult();
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return result;
	}
	
	public String isFeedBackExistForFeedBackForm(String feedbackFormId){
		String isFeedbackFormUsed=null;
		DBPreparedQuery dq = null;
		ArrayList<FeedbackFormData> result = null;
		String[] dynParam = new String[1];
		ArrayList<String> dynamicContent = new ArrayList<String>();
		int total=0;
		try{
			dynParam[0] = "";
			String qMarks = Utils.setDynamicParamsAndReturnQmarks(feedbackFormId, dynamicContent);
			dynParam[0] = qMarks;
			dq = new DBPreparedQuery("dFeedbackForm_isFeedbackFormUsed", dynParam);			
			int cnt=1;
			for (int i = 0; i < dynamicContent.size(); i++) {
				dq.setString(cnt++, dynamicContent.get(i));
			}
			total = dq.getIntResult();
			isFeedbackFormUsed=""+total;
		}catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return isFeedbackFormUsed;
	}
}
