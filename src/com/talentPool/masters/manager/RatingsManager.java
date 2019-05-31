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
import com.talentPool.masters.constants.RatingsConstants;
import com.talentPool.masters.dataobject.RatingFieldsData;
import com.talentPool.masters.dataobject.RatingsData;

/**
 * @author shivprasad
 * 
 */
public class RatingsManager {
	public ArrayList<RatingsData> getAllRatings() {
		DBPreparedQuery dq = null;
		ArrayList result = null;
		try {
			dq = new DBPreparedQuery("dRatingsManager_GetAllActiveRatings");
			dq.setString(1, RatingsConstants.RATING_STATUS_ACTIVE);
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

	public String getXMLForRatings(ArrayList<RatingsData> list) {
		StringWriter sWr = new StringWriter();
		XMLWriter wr = new XMLWriter(sWr);

		try {
			wr.startDocument();
			wr.startElement("rows");
			for (int i = 0; list != null && i < list.size(); i++) {
				RatingsData data = list.get(i);

				AttributesImpl at = new AttributesImpl();
				at.addAttribute("", "id", "", "", data.getRatingId());
				wr.startElement("", "row", "", at);

				at = new AttributesImpl();
				at.addAttribute("", "name", "", "", "Col_I_Comment");
				wr.startElement("", "userdata", "", at);
				wr.characters("Delete");
				wr.endElement("userdata");

				at = new AttributesImpl();
				at.addAttribute("", "name", "", "", "ratingTitle");
				wr.startElement("", "userdata", "", at);
				wr.characters(data.getRatingTitle());
				wr.endElement("userdata");

				wr.startElement("cell");
				wr.characters("<img src=\"images/ico_delete.gif\" border=0>^javascript:deleteRecord(" + data.getRatingId() + ");^_self");
				wr.endElement("cell");

				wr.startElement("cell");
				wr.characters(wr.doubleEscape(data.getRatingTitle()) + "^javascript:editRecord(" + data.getRatingId() + ");^_self");
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

	public ArrayList<RatingsData> getAllActiveRatingsWithFields() {
		DBPreparedQuery dq = null;
		ArrayList<RatingsData> result = null;
		try {
			dq = new DBPreparedQuery("dRatingsManager_GetAllActiveRatings");
			dq.setString(1, RatingsConstants.RATING_STATUS_ACTIVE);
			result = dq.getResult();
			if (result != null) {
				for (int i = 0; i < result.size(); i++) {
					RatingsData ratingsData = result.get(i);
					ArrayList<RatingFieldsData> ratingFields = getRatingsFields(ratingsData.getRatingId());
					ratingsData.setRatingFields(ratingFields);
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

	public ArrayList<RatingsData> getAllRatingsWithFields() {
		DBPreparedQuery dq = null;
		ArrayList<RatingsData> result = null;
		try {
			dq = new DBPreparedQuery("dRatingsManager_GetAllRatings");
			result = dq.getResult();
			if (result != null) {
				for (int i = 0; i < result.size(); i++) {
					RatingsData ratingsData = result.get(i);
					ArrayList<RatingFieldsData> ratingFields = getRatingsFields(ratingsData.getRatingId());
					ratingsData.setRatingFields(ratingFields);
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
	
	public RatingsData getRatingsData(String ratingId) throws Exception {
		DBPreparedQuery dq = null;
		RatingsData ratingsData = null;
		try {
			dq = new DBPreparedQuery("dRatingsManager_GetRatingData");
			dq.setString(1, ratingId);
			ratingsData = (RatingsData) dq.getSingleObjectResult();
			if (ratingsData != null) {
				ArrayList<RatingFieldsData> ratingFields = getRatingsFields(ratingId);
				ratingsData.setRatingFields(ratingFields);
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return ratingsData;
	}

	public ArrayList<RatingFieldsData> getRatingsFields(String ratingId) throws Exception {
		DBPreparedQuery dq = null;
		ArrayList<RatingFieldsData> result = null;
		try {
			dq = new DBPreparedQuery("dRatingsManager_GetRatingFields");
			dq.setString(1, ratingId);
			dq.setString(2, RatingsConstants.RATING_FIELD_STATUS_ACTIVE);
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

	public void addRating(String ratingName, String userId, ArrayList<RatingFieldsData> ratingFields) throws Exception {
		DBPreparedQuery dq = null;
		DBTransaction tran = null;
		try {
			tran = new DBTransaction();
			dq = new DBPreparedQuery("dRatingsManager_AddRating", tran);
			dq.setString(1, ratingName);
			dq.setString(2, userId);
			dq.setString(3, RatingsConstants.RATING_STATUS_ACTIVE);
			dq.execute();

			dq = new DBPreparedQuery("dFetchLastInsertID", tran);
			String ratingId = dq.getIdResult();
			addRatingFields(ratingId, ratingFields, tran);
			tran.commit();
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while adding rating", e);
			tran.rollback();
			throw e;
		} finally {
			if (dq != null) {
				dq.releaseTransaction(tran);
			}
		}
	}

	public void addRatingFields(String ratingId, ArrayList<RatingFieldsData> ratingFields, DBTransaction tran) throws Exception {
		DBPreparedQuery dq = null;
		boolean isCommitAllowed = true;
		try {
			if (tran != null) {
				isCommitAllowed = false;
			}
			for (int i = 0; ratingFields != null && i < ratingFields.size(); i++) {
				addRatingField(ratingId, ratingFields.get(i).getRatingFieldDesc(), i + 1, tran);
			}
			if (isCommitAllowed) {
				tran.commit();
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while adding rating", e);
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

	private void addRatingField(String ratingId, String ratingFieldDesc, int rank, DBTransaction tran) throws Exception {
		DBPreparedQuery dq = null;
		try {
			if (tran != null) {
				dq = new DBPreparedQuery("dRatingsManager_AddRatingField", tran);
				dq.setString(1, ratingId);
				dq.setString(2, ratingFieldDesc);
				dq.setString(3, RatingsConstants.RATING_FIELD_STATUS_ACTIVE);
				dq.setInt(4, rank);
				dq.execute();
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while adding rating", e);
			throw e;
		} finally {
			if (dq != null) {
				dq.closeOpenCursors();
			}
		}

	}

	private void updateRatingField(String ratingFieldId, String ratingFieldDesc, int rank, DBTransaction tran) throws Exception {
		DBPreparedQuery dq = null;
		try {
			if (tran != null) {
				dq = new DBPreparedQuery("dRatingsManager_UpdateRatingField", tran);
				dq.setString(1, ratingFieldDesc);
				dq.setInt(2, rank);
				dq.setString(3, ratingFieldId);
				dq.execute();
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while updating rating", e);
			throw e;
		} finally {
			if (dq != null) {
				dq.closeOpenCursors();
			}
		}

	}

	public void updateRating(String ratingId, String ratingName, ArrayList<RatingFieldsData> ratingFields) throws Exception {
		DBPreparedQuery dq = null;
		DBTransaction tran = null;
		try {
			tran = new DBTransaction();
			dq = new DBPreparedQuery("dRatingsManager_UpdateRating", tran);
			dq.setString(1, ratingName);
			dq.setString(2, ratingId);
			dq.execute();
			deleteFeedbackFieldsNotIn(ratingId, ratingFields, tran);
			addOrUpdateRatingFields(ratingId, ratingFields, tran);
			tran.commit();
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while adding rating", e);
			tran.rollback();
			throw e;
		} finally {
			if (dq != null) {
				dq.releaseTransaction(tran);
			}
		}

	}

	public void deleteFeedbackFieldsNotIn(String ratingId, ArrayList<RatingFieldsData> ratingFields, DBTransaction tran) throws Exception {
		DBPreparedQuery dq = null;
		try {
			if (tran != null) {
				if (ratingFields != null && ratingFields.size() > 0) {
					String[] dynParam = new String[1];
					dynParam[0] = "";
					ArrayList<String> dynamicContent = new ArrayList<String>();
					for (int i = 0; i < ratingFields.size(); i++) {
						if (!ratingFields.get(i).getRatingFieldId().equals("0")) {
							if (dynParam[0].length() > 0) {
								dynParam[0] += ",";
							}
							dynParam[0] += "?";
							dynamicContent.add(ratingFields.get(i).getRatingFieldId());
						}
					}
					if (dynamicContent.size() > 0) {
						dq = new DBPreparedQuery("dRatingsManager_DeleteFieldsNotIn", dynParam, tran);
						dq.setString(1, ratingId);
						int cnt = 2;
						for (int i = 0; i < dynamicContent.size(); i++) {
							dq.setString(cnt++, dynamicContent.get(i));
						}
						dq.execute();
					}
				}
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while adding rating", e);
			throw e;
		} finally {
			if (dq != null) {
				dq.closeOpenCursors();
			}
		}

	}

	public void addOrUpdateRatingFields(String ratingId, ArrayList<RatingFieldsData> ratingFields, DBTransaction tran) throws Exception {
		DBPreparedQuery dq = null;
		try {
			if (tran != null) {
				if (ratingFields != null && ratingFields.size() > 0) {
					for (int i = 0; i < ratingFields.size(); i++) {
						RatingFieldsData ratingFieldsData = ratingFields.get(i);
						if (ratingFieldsData.getRatingFieldId().equals("0")) {
							// add rating field
							addRatingField(ratingId, ratingFieldsData.getRatingFieldDesc(), i + 1, tran);
						} else {
							// update rating field
							updateRatingField(ratingFieldsData.getRatingFieldId(), ratingFieldsData.getRatingFieldDesc(), i + 1, tran);
						}
					}
				}
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while adding rating", e);
			throw e;
		} finally {
			if (dq != null) {
				dq.closeOpenCursors();
			}
		}

	}

	public int getCountOfActiveFeedbackFormForRating(String ratingId) {
		DBPreparedQuery dq = null;
		int cnt = 0;
		try {
			dq = new DBPreparedQuery("dRatingsManager_CountActiveFormForRating");
			dq.setId(1, FeedbackFormConstants.FORM_STATUS_ACTIVE);
			dq.setId(2, ratingId);
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

	public void deleteRating(String ratingId) throws Exception {
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dRatingsManager_ChangeRatingStatus");
			dq.setString(1, RatingsConstants.RATING_STATUS_DELETED);
			dq.setString(2, ratingId);
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
}
