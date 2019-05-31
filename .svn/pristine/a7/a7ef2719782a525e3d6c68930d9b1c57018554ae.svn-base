/**
 * 
 */
package com.talentPool.masters.manager;

import java.io.StringWriter;
import java.util.ArrayList;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.db.DBPreparedQuery;
import com.talentPool.common.db.DBTransaction;
import com.talentPool.common.xmlutils.XMLWriter;
import com.talentPool.masters.constants.FeedbackFormConstants;
import com.talentPool.masters.constants.MultipleSelectsConstants;
import com.talentPool.masters.dataobject.MultipleSelectFieldsData;
import com.talentPool.masters.dataobject.MultipleSelectsData;

/**
 * @author shivprasad
 * 
 */
public class MultipleSelectsManager {
	public ArrayList<MultipleSelectsData> getAllMultipleSelects() {
		DBPreparedQuery dq = null;
		ArrayList result = null;
		try {
			dq = new DBPreparedQuery("dMultipleSelectsManager_GetAllActiveMultipleSelects");
			dq.setString(1, MultipleSelectsConstants.MULTIPLE_SELECT_STATUS_ACTIVE);
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

	public String getXMLForMultipleSelects(ArrayList<MultipleSelectsData> list) {
		StringWriter sWr = new StringWriter();
		XMLWriter wr = new XMLWriter(sWr);

		try {
			wr.startDocument();
			wr.startElement("rows");
			for (int i = 0; list != null && i < list.size(); i++) {
				MultipleSelectsData data = list.get(i);

				AttributesImpl at = new AttributesImpl();
				at.addAttribute("", "id", "", "", data.getSelectId());
				wr.startElement("", "row", "", at);

				at = new AttributesImpl();
				at.addAttribute("", "name", "", "", "Col_I_Comment");
				wr.startElement("", "userdata", "", at);
				wr.characters("Delete");
				wr.endElement("userdata");

				at = new AttributesImpl();
				at.addAttribute("", "name", "", "", "selectTitle");
				wr.startElement("", "userdata", "", at);
				wr.characters(data.getSelectTitle());
				wr.endElement("userdata");

				wr.startElement("cell");
				wr.characters("<img src=\"images/ico_delete.gif\" border=0>^javascript:deleteRecord(" + data.getSelectId() + ");^_self");
				wr.endElement("cell");

				wr.startElement("cell");
				wr.characters(wr.doubleEscape(data.getSelectTitle()) + "^javascript:editRecord(" + data.getSelectId() + ");^_self");
				wr.endElement("cell");

				wr.endElement("row");
			}
			wr.endElement("rows");
			wr.endDocument();
		} catch (SAXException e) {
			TPLogger.getLogger().error("Error while creating xml", e);
		}

		return sWr.getBuffer().toString();
	}

	public ArrayList<MultipleSelectsData> getAllActiveMultipleSelectsWithFields() {
		DBPreparedQuery dq = null;
		ArrayList<MultipleSelectsData> result = null;
		try {
			dq = new DBPreparedQuery("dMultipleSelectsManager_GetAllActiveMultipleSelects");
			dq.setString(1, MultipleSelectsConstants.MULTIPLE_SELECT_STATUS_ACTIVE);
			result = dq.getResult();
			if (result != null) {
				for (int i = 0; i < result.size(); i++) {
					MultipleSelectsData MultipleSelectsData = result.get(i);
					ArrayList<MultipleSelectFieldsData> multipleSelectFields = getMultipleSelectsFields(MultipleSelectsData.getSelectId());
					MultipleSelectsData.setMultipleSelectFields(multipleSelectFields);
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

	public ArrayList<MultipleSelectsData> getAllMultipleSelectsWithFields() {
		DBPreparedQuery dq = null;
		ArrayList<MultipleSelectsData> result = null;
		try {
			dq = new DBPreparedQuery("dMultipleSelectsManager_GetAllMultipleSelects");
			result = dq.getResult();
			if (result != null) {
				for (int i = 0; i < result.size(); i++) {
					MultipleSelectsData MultipleSelectsData = result.get(i);
					ArrayList<MultipleSelectFieldsData> multipleSelectFields = getMultipleSelectsFields(MultipleSelectsData.getSelectId());
					MultipleSelectsData.setMultipleSelectFields(multipleSelectFields);
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
	
	public MultipleSelectsData getMultipleSelectsData(String selectId) throws Exception {
		DBPreparedQuery dq = null;
		MultipleSelectsData MultipleSelectsData = null;
		try {
			dq = new DBPreparedQuery("dMultipleSelectsManager_GetMultipleSelectData");
			dq.setString(1, selectId);
			MultipleSelectsData = (MultipleSelectsData) dq.getSingleObjectResult();
			if (MultipleSelectsData != null) {
				ArrayList<MultipleSelectFieldsData> multipleSelectFields = getMultipleSelectsFields(selectId);
				MultipleSelectsData.setMultipleSelectFields(multipleSelectFields);
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return MultipleSelectsData;
	}

	public ArrayList<MultipleSelectFieldsData> getMultipleSelectsFields(String selectId) throws Exception {
		DBPreparedQuery dq = null;
		ArrayList<MultipleSelectFieldsData> result = null;
		try {
			dq = new DBPreparedQuery("dMultipleSelectsManager_GetMultipleSelectFields");
			dq.setString(1, selectId);
			dq.setString(2, MultipleSelectsConstants.MULTIPLE_SELECT_FIELD_STATUS_ACTIVE);
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

	public void addMultipleSelect(String selectTitle, String userId, ArrayList<MultipleSelectFieldsData> multipleSelectFields) throws Exception {
		DBPreparedQuery dq = null;
		DBTransaction tran = null;
		try {
			tran = new DBTransaction();
			dq = new DBPreparedQuery("dMultipleSelectsManager_AddMultipleSelect", tran);
			dq.setString(1, selectTitle);
			dq.setString(2, userId);
			dq.setString(3, MultipleSelectsConstants.MULTIPLE_SELECT_STATUS_ACTIVE);
			dq.execute();

			dq = new DBPreparedQuery("dFetchLastInsertID", tran);
			String selectId = dq.getIdResult();
			addMultipleSelectFields(selectId, multipleSelectFields, tran);
			tran.commit();
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while adding multiple select", e);
			tran.rollback();
			throw e;
		} finally {
			if (dq != null) {
				dq.releaseTransaction(tran);
			}
		}
	}

	public void addMultipleSelectFields(String selectId, ArrayList<MultipleSelectFieldsData> multipleSelectFields, DBTransaction tran) throws Exception {
		DBPreparedQuery dq = null;
		boolean isCommitAllowed = true;
		try {
			if (tran != null) {
				isCommitAllowed = false;
			}
			for (int i = 0; multipleSelectFields != null && i < multipleSelectFields.size(); i++) {
				addMultipleSelectField(selectId, multipleSelectFields.get(i).getSelectFieldDesc(), i + 1, tran);
			}
			if (isCommitAllowed) {
				tran.commit();
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while adding multiple select", e);
			if (isCommitAllowed) {
				tran.rollback();
			}
			throw e;
		} finally {
			if (dq != null) {
				if (isCommitAllowed) {
					dq.releaseTransaction(tran);
				} else {
					dq.closeOpenCursors();
				}

			}
		}

	}

	private void addMultipleSelectField(String selectId, String selectFieldDesc, int rank, DBTransaction tran) throws Exception {
		DBPreparedQuery dq = null;
		try {
			if (tran != null) {
				dq = new DBPreparedQuery("dMultipleSelectsManager_AddMultipleSelectField", tran);
				dq.setString(1, selectId);
				dq.setString(2, selectFieldDesc);
				dq.setString(3, MultipleSelectsConstants.MULTIPLE_SELECT_FIELD_STATUS_ACTIVE);
				dq.setInt(4, rank);
				dq.execute();
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while adding multiple select field", e);
			throw e;
		} finally {
			if (dq != null) {
				dq.closeOpenCursors();
			}
		}

	}

	private void updateMultipleSelectField(String selectFieldId, String selectFieldDesc, int rank, DBTransaction tran) throws Exception {
		DBPreparedQuery dq = null;
		try {
			if (tran != null) {
				dq = new DBPreparedQuery("dMultipleSelectsManager_UpdateMultipleSelectField", tran);
				dq.setString(1, selectFieldDesc);
				dq.setInt(2, rank);
				dq.setString(3, selectFieldId);
				dq.execute();
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while updating select field", e);
			throw e;
		} finally {
			if (dq != null) {
				dq.closeOpenCursors();
			}
		}

	}

	public void updateMultipleSelect(String selectId, String selectTitle, ArrayList<MultipleSelectFieldsData> multipleSelectFields) throws Exception {
		DBPreparedQuery dq = null;
		DBTransaction tran = null;
		try {
			tran = new DBTransaction();
			dq = new DBPreparedQuery("dMultipleSelectsManager_UpdateMultipleSelect", tran);
			dq.setString(1, selectTitle);
			dq.setString(2, selectId);
			dq.execute();
			deleteFeedbackFieldsNotIn(selectId, multipleSelectFields, tran);
			addOrUpdateMultipleSelectFields(selectId, multipleSelectFields, tran);
			tran.commit();
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while updating multiple select", e);
			tran.rollback();
			throw e;
		} finally {
			if (dq != null) {
				dq.releaseTransaction(tran);
			}
		}

	}

	public void deleteFeedbackFieldsNotIn(String selectId, ArrayList<MultipleSelectFieldsData> multipleSelectFields, DBTransaction tran) throws Exception {
		DBPreparedQuery dq = null;
		try {
			if (tran != null) {
				if (multipleSelectFields != null && multipleSelectFields.size() > 0) {
					String[] dynParam = new String[1];
					dynParam[0] = "";
					ArrayList<String> dynamicContent = new ArrayList<String>();
					for (int i = 0; i < multipleSelectFields.size(); i++) {
						if (!multipleSelectFields.get(i).getSelectFieldId().equals("0")) {
							if (dynParam[0].length() > 0) {
								dynParam[0] += ",";
							}
							dynParam[0] += "?";
							dynamicContent.add(multipleSelectFields.get(i).getSelectFieldId());
						}
					}
					if (dynamicContent.size() > 0) {
						dq = new DBPreparedQuery("dMultipleSelectsManager_DeleteFieldsNotIn", dynParam, tran);
						dq.setString(1, selectId);
						int cnt = 2;
						for (int i = 0; i < dynamicContent.size(); i++) {
							dq.setString(cnt++, dynamicContent.get(i));
						}
						dq.execute();
					}
				}
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while adding multiple select", e);
			throw e;
		} finally {
			if (dq != null) {
				dq.closeOpenCursors();
			}
		}

	}

	public void addOrUpdateMultipleSelectFields(String selectId, ArrayList<MultipleSelectFieldsData> multipleSelectFields, DBTransaction tran) throws Exception {
		DBPreparedQuery dq = null;
		try {
			if (tran != null) {
				if (multipleSelectFields != null && multipleSelectFields.size() > 0) {
					for (int i = 0; i < multipleSelectFields.size(); i++) {
						MultipleSelectFieldsData selectFieldsData = multipleSelectFields.get(i);
						if (selectFieldsData.getSelectFieldId().equals("0")) {
							addMultipleSelectField(selectId, selectFieldsData.getSelectFieldDesc(), i + 1, tran);
						} else {
							updateMultipleSelectField(selectFieldsData.getSelectFieldId(), selectFieldsData.getSelectFieldDesc(), i + 1, tran);
						}
					}
				}
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while adding select field", e);
			throw e;
		} finally {
			if (dq != null) {
				dq.closeOpenCursors();
			}
		}

	}

	public int getCountOfActiveFeedbackFormForMultipleSelect(String selectId) {
		DBPreparedQuery dq = null;
		int cnt = 0;
		try {
			dq = new DBPreparedQuery("dMultipleSelectsManager_CountActiveFormForMultipleSelect");
			dq.setId(1, FeedbackFormConstants.FORM_STATUS_ACTIVE);
			dq.setId(2, selectId);
			cnt = dq.getIntResult();
		} catch (Exception e) {
			TPLogger.getLogger().error("Error", e);
			cnt = 0;
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return cnt;
	}

	public void deleteMultipleSelect(String selectId) throws Exception {
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dMultipleSelectsManager_ChangeMultipleSelectstatus");
			dq.setString(1, MultipleSelectsConstants.MULTIPLE_SELECT_STATUS_DELETED);
			dq.setString(2, selectId);
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
	
	public String getMultipleSelectFieldDescFromIds(String ids) {
		String selectFieldDesc = "";
		DBPreparedQuery dq = null;
		try {
			String[] dynParam = new String[1];
			dynParam[0] = "";
			ArrayList<String> dynamicContent = new ArrayList<String>();
			String[] arrIds = ids.split(",");
			String qMarks = "";
			for (int i = 0; i < arrIds.length; i++) {
				qMarks += (i > 0) ? ",?" : "?";
				dynamicContent.add(arrIds[i].trim());
			}
			dynParam[0] += " AND select_field_id IN (" + qMarks + ")";
			
			dq = new DBPreparedQuery("dMultipleSelectsManager_GetMultipleSelectFieldDesc", dynParam);
			int cnt = 1;
			for (int i = 0; i < dynamicContent.size(); i++) {
				dq.setString(cnt++, dynamicContent.get(i));
			}
			selectFieldDesc = dq.getStringResult();
		} catch (Exception e) {
			TPLogger.getLogger().error("error while getting source titles", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}

		return selectFieldDesc;
	}
	
}
