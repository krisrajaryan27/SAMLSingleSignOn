/**
 * 
 */
package com.talentPool.masters.manager;

import java.io.StringWriter;
import java.sql.Types;
import java.util.ArrayList;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.db.DBPreparedQuery;
import com.talentPool.common.db.DBTransaction;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.utils.Utils;
import com.talentPool.common.xmlutils.XMLWriter;
import com.talentPool.masters.constants.FeedbackFieldsConstant;
import com.talentPool.masters.constants.FeedbackFormConstants;
import com.talentPool.masters.dataobject.FeedbackFieldCategoryData;
import com.talentPool.masters.dataobject.FeedbackFieldData;
import com.talentPool.positions.PositionConstants;

/**
 * @author shivprasad
 * 
 */
public class FeedbackFieldsManager {
	public ArrayList<FeedbackFieldCategoryData> getActiveFeedbackCategories(String systemGenerated) {
		DBPreparedQuery dq = null;
		ArrayList<FeedbackFieldCategoryData> result = null;
		try {
			String[] dynParam = new String[1];
			ArrayList<String> dynamicContent = new ArrayList<String>();
			dynParam[0] = " ";
			
			if (!Utils.isBlankOrNull(systemGenerated) && systemGenerated.equals(FeedbackFormConstants.SYSTEM_GENERATED)) {
				dynParam[0] = " AND system_generated != ? " ;
				dynamicContent.add(systemGenerated);
			}
			
			dq = new DBPreparedQuery("dFeedbackFields_GetAllCategories",dynParam);
			dq.setString(1, FeedbackFieldsConstant.CATEGORY_ACTIVE);
			int cnt = 2;
			for (int i = 0; i < dynamicContent.size(); i++) {
				dq.setString(cnt++, dynamicContent.get(i));
			}
			result = dq.getResult();
			if (result != null) {
				for (int i = 0; i < result.size(); i++) {
					ArrayList<FeedbackFieldData> feedbackFields = getActiveFeedbackFieldsForCategory(result.get(i).getFeedbackFieldCategoryId());
					result.get(i).setFeedbackFields(feedbackFields);
				}
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return result;
	}

	public ArrayList<FeedbackFieldData> getActiveFeedbackFieldsForCategory(String categoryId) {
		DBPreparedQuery dq = null;
		ArrayList<FeedbackFieldData> result = null;
		try {
			dq = new DBPreparedQuery("dFeedbackFields_GetFieldsForCatgory");
			dq.setString(1, categoryId);
			dq.setString(2, FeedbackFieldsConstant.FIELD_ACTIVE);
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

	public String getFeedbackFieldCategoriesInXML(ArrayList<FeedbackFieldCategoryData> feedbackFieldCategories) {
		StringWriter sWr = new StringWriter();
		XMLWriter wr = new XMLWriter(sWr);

		try {
			wr.startDocument();
			wr.startElement("rows");
			for (int i = 0; i < feedbackFieldCategories.size(); i++) {
				FeedbackFieldCategoryData data = feedbackFieldCategories.get(i);
				String systemGenerated = data.getSystemGenerated();

				AttributesImpl at = new AttributesImpl();
				at.addAttribute("", "id", "", "", "" + data.getFeedbackFieldCategoryId());
				wr.startElement("", "row", "", at);

				at = new AttributesImpl();
				at.addAttribute("", "name", "", "", "Col_I_Comment");
				wr.startElement("", "userdata", "", at);
				if(systemGenerated.equals(FeedbackFormConstants.SYSTEM_GENERATED)){
					wr.characters("");
				}else{
					wr.characters("Delete");
				}
				wr.endElement("userdata");

				at = new AttributesImpl();
				at.addAttribute("", "name", "", "", "category");
				wr.startElement("", "userdata", "", at);
				wr.characters(data.getFeedbackFieldCategory());
				wr.endElement("userdata");

				wr.startElement("cell");
				if(systemGenerated.equals(FeedbackFormConstants.SYSTEM_GENERATED)){
					wr.characters("");
				}else{
					wr.characters("<img src=\"images/ico_delete.gif\" border=0>^javascript:deleteRecord(" + data.getFeedbackFieldCategoryId() + ");^_self");
				}
				wr.endElement("cell");

				wr.startElement("cell");
				wr.characters(wr.doubleEscape(data.getFeedbackFieldCategory()) + "^javascript:editRecord(" + data.getFeedbackFieldCategoryId() + ");^_self");
				wr.endElement("cell");

				ArrayList<FeedbackFieldData> feedbackFields = data.getFeedbackFields();
				String fields = "";
				StringBuffer commaSeptStr = new StringBuffer();
				if (feedbackFields != null && feedbackFields.size() > 0) {
					FeedbackFieldData sData = feedbackFields.get(0);
					fields = sData.getFeedbackFieldTitle();
					int sz = feedbackFields.size() - 1;
					if (sz > 0) {
						fields += " (" + sz + " more >>)";
					}

				} else {
					fields = "NA";
				}

				wr.startElement("cell");
				wr.characters(wr.doubleEscape(fields) + "^javascript:manageFields(" + data.getFeedbackFieldCategoryId() + ");^_self");
				wr.endElement("cell");

				wr.endElement("row");
			}
			wr.endElement("rows");
			wr.endDocument();
		} catch (SAXException e) {
			TPLogger.getLogger().error("error while creating xml file for emails", e);
		}
		return sWr.getBuffer().toString();
	}

	public FeedbackFieldCategoryData getFeedbackCategoryData(String categoryId) {
		DBPreparedQuery dq = null;
		FeedbackFieldCategoryData feedbackFieldCategoryData = null;
		try {
			dq = new DBPreparedQuery("dFeedbackFields_GetCatgoryData");
			dq.setString(1, categoryId);
			feedbackFieldCategoryData = (FeedbackFieldCategoryData) dq.getSingleObjectResult();
			if (feedbackFieldCategoryData != null) {
				ArrayList<FeedbackFieldData> feedbackFields = getActiveFeedbackFieldsForCategory(categoryId);
				feedbackFieldCategoryData.setFeedbackFields(feedbackFields);
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return feedbackFieldCategoryData;

	}

	public void AddFeedbackCategory(String categoryTitle,String isSummaryCategory) throws Exception {
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dFeedbackFields_AddCategory");
			dq.setString(1, categoryTitle);
			dq.setString(2, isSummaryCategory);
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

	public void UpdateFeedbackCategory(String categoryTitle, String isSummaryCategory, String categoryId) throws Exception {
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dFeedbackFields_UpdateCategory");
			dq.setString(1, categoryTitle);
			dq.setString(2, isSummaryCategory);
			dq.setString(3, categoryId);
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

	public void deleteFeedbackCategory(String categoryId) throws Exception {
		DBPreparedQuery dq = null;
		DBTransaction tran = null;
		try {
			tran = new DBTransaction();
			dq = new DBPreparedQuery("dFeedbackFields_DeleteAllFieldsForCategory", tran);
			dq.setString(1, categoryId);
			dq.execute();
			dq = new DBPreparedQuery("dFeedbackFields_DeleteCategory", tran);
			dq.setString(1, categoryId);
			dq.execute();
			tran.commit();
		} catch (Exception e) {
			TPLogger.getLogger().error("Error", e);
			if (tran != null) {
				tran.rollback();
			}
			throw e;
		} finally {
			if (dq != null) {
				dq.releaseTransaction(tran);
			}
		}
	}

	public String getFeedbackFieldsInXML(ArrayList<FeedbackFieldData> feedbackFields) {
		StringWriter sWr = new StringWriter();
		XMLWriter wr = new XMLWriter(sWr);
		try {
			wr.startDocument();
			wr.startElement("rows");
			for (int i = 0; i < feedbackFields.size(); i++) {
				FeedbackFieldData data = feedbackFields.get(i);
				String systemGenerated = data.getSystemGenerated();
				
				AttributesImpl at = new AttributesImpl();
				at.addAttribute("", "id", "", "", "" + data.getFeedbackFieldId());
				wr.startElement("", "row", "", at);

				at = new AttributesImpl();
				at.addAttribute("", "name", "", "", "Col_I_Comment");
				wr.startElement("", "userdata", "", at);
				if(systemGenerated.equals(FeedbackFormConstants.SYSTEM_GENERATED)){
					wr.characters("");
				}else{
					wr.characters("Delete");
				}
				wr.endElement("userdata");

				at = new AttributesImpl();
				at.addAttribute("", "name", "", "", "field");
				wr.startElement("", "userdata", "", at);
				wr.characters(data.getFeedbackFieldTitle());
				wr.endElement("userdata");

				wr.startElement("cell");
				if(systemGenerated.equals(FeedbackFormConstants.SYSTEM_GENERATED)){
					wr.characters("");
				}else{
					wr.characters("<img src=\"images/ico_delete.gif\" border=0>^javascript:deleteRecord(" + data.getFeedbackFieldId() + ");^_self");
				}
				wr.endElement("cell");

				wr.startElement("cell");
				wr.characters(wr.doubleEscape(data.getFeedbackFieldTitle()) + "^javascript:editRecord(" + data.getFeedbackFieldId() + ");^_self");
				wr.endElement("cell");

				wr.startElement("cell");
				wr.characters(wr.doubleEscape(data.getFeedbackFieldDesc()));
				wr.endElement("cell");

				wr.endElement("row");
			}
			wr.endElement("rows");
			wr.endDocument();
		} catch (SAXException e) {
			TPLogger.getLogger().error("error while creating xml file for emails", e);
		}
		return sWr.getBuffer().toString();
	}

	public void AddFeedbackField(String categoryId, String feedbackFieldTitle, String feedbackFieldDesc,String feedbackFieldType,String applicantFieldId) throws Exception {
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dFeedbackFields_AddFeedbackField");
			dq.setString(1, categoryId);
			dq.setString(2, feedbackFieldTitle);
			dq.setString(3, feedbackFieldDesc);
			dq.setString(4, feedbackFieldType);
			if(FeedbackFieldsConstant.FIELD_TYPE_APPLICANT.equals(feedbackFieldType)){
				dq.setString(5, applicantFieldId);
			}else{
				dq.setNull(5, Types.NULL);
			}
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

	public void UpdateFeedbackField(String feedbackFieldId, String feedbackFieldTitle, String feedbackFieldDesc,String feedbackFieldType,String applicantFieldId) throws Exception {
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dFeedbackFields_UpdateFeedbackField");
			dq.setString(1, feedbackFieldTitle);
			dq.setString(2, feedbackFieldDesc);
			dq.setString(3, Utils.isBlankOrNull(feedbackFieldType)?FeedbackFieldsConstant.FIELD_TYPE_NORMAL:feedbackFieldType);
			if(FeedbackFieldsConstant.FIELD_TYPE_APPLICANT.equals(feedbackFieldType)){
				dq.setString(4, applicantFieldId);
			}else{
				dq.setNull(4, Types.NULL);
			}
			dq.setString(5, feedbackFieldId);
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

	public void deleteFeedbackField(String feedbackFieldId) throws Exception {
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dFeedbackFields_DeleteFeedbackField");
			dq.setString(1, feedbackFieldId);
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

	public FeedbackFieldData getFeedbackFieldData(String feedbackFieldId) {
		DBPreparedQuery dq = null;
		FeedbackFieldData feedbackFieldData = null;
		try {
			dq = new DBPreparedQuery("dFeedbackFields_GetFeedbackFieldData");
			dq.setString(1, feedbackFieldId);
			feedbackFieldData = (FeedbackFieldData) dq.getSingleObjectResult();
		} catch (Exception e) {
			TPLogger.getLogger().error("Error", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return feedbackFieldData;

	}

	public String getSystemGeneratedFieldId(String positionStepId) {
		DBPreparedQuery dq = null;
		String fieldId = null;
		try {
			dq = new DBPreparedQuery("dFeedbackFields_GetSystemGeneratedFieldId");
			dq.setString(1, FeedbackFormConstants.FORM_STATUS_ACTIVE);
			dq.setString(2, FeedbackFormConstants.SYSTEM_GENERATED);
			dq.setString(3, FeedbackFormConstants.FIELD_TYPE_FIELD);
			dq.setString(4, positionStepId);
			fieldId = dq.getIdResult();
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return fieldId;
	}
}
