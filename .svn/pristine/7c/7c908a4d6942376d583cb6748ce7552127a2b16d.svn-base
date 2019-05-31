/**
 * 
 */
package com.talentPool.costs.manager;

import java.io.StringWriter;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.db.DBPreparedQuery;
import com.talentPool.common.db.DBTransaction;
import com.talentPool.common.properties.GlobalApplicationProperties;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.utils.DateUtils;
import com.talentPool.common.utils.Utils;
import com.talentPool.common.xmlutils.XMLWriter;
import com.talentPool.costs.dataobject.CostData;
import com.talentPool.user.manager.PermissionSet;

/**
 * @author shivprasad
 * 
 */
public class CostManager {
	/**
	 * @param fromDate
	 * @param toDate
	 * @return ArrayList of CostData which is costs incurrered during the given period
	 */
	public ArrayList<CostData> getAllCosts(Date fromDate, Date toDate) {
		ArrayList<CostData> results = null;
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dCostManager_GetAllCosts");
			dq.setDate(1, Utils.convertDateToSQLDate(fromDate));
			dq.setDate(2, Utils.convertDateToSQLDate(toDate));
			results = dq.getResult();

		} catch (Exception e) {
			TPLogger.getLogger().error("Error while getting all costs", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return results;
	}

	/**
	 * @return arraylist of CostData type for current financial year
	 */
	public ArrayList<CostData> getCostsForCurrentFinancialYear() {
		ArrayList<CostData> results = null;
		try {
			GregorianCalendar cal = new GregorianCalendar();
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);

			Date toDate = cal.getTime();
			String financialStartMonth = GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_FINANCIAL_YEAR_START_MONTH);
			cal.set(Calendar.DATE, 1);
			cal.set(Calendar.MONTH, Integer.parseInt(financialStartMonth));
			if (cal.getTime().after(toDate)) {
				cal.set(Calendar.YEAR, cal.get(Calendar.YEAR) - 1);
			}
			Date fromDate = cal.getTime();
			cal.set(Calendar.YEAR, cal.get(Calendar.YEAR) + 1);
			toDate = cal.getTime();

			results = getAllCosts(fromDate, toDate);
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while getting all costs", e);
		}
		return results;
	}

	public String getXMLForCosts(ArrayList<CostData> list, PermissionSet permissionSet) {
		StringWriter sWr = new StringWriter();
		XMLWriter wr = new XMLWriter(sWr);

		try {
			wr.startDocument();
			wr.startElement("rows");
			for (int i = 0; list != null && i < list.size(); i++) {
				CostData data = list.get(i);

				AttributesImpl at = new AttributesImpl();
				at.addAttribute("", "id", "", "", "" + data.getCostId());
				wr.startElement("", "row", "", at);

				String date = "";
				String fullDate = "";
				
				date = DateUtils.getSystemDateFormat(data.getCostPaidDate());
				date = Utils.isBlankOrNull(date)?"UNKNOW":date;
				fullDate = DateUtils.getDateFormatForGridSorting(data.getCostPaidDate());

				wr.startElement("cell");
				if (permissionSet.isPERMISSION_ADD_EXPENSE()) {
					wr.characters("<img src=\"images/ico_delete.gif\" border=0>^javascript:deleteRecord(" + data.getCostId() + ");^_self");
				} else {
					wr.characters("&nbsp;");
				}
				wr.endElement("cell");

				wr.startElement("cell");
				wr.characters(date);
				wr.endElement("cell");

				String costType = data.getCostTypeName();
				String cType = costType;
				if (cType.length() > 23) {
					cType = cType.substring(0, 23) + "...";
				}
				wr.startElement("cell");
				wr.characters(wr.doubleEscape(cType));
				wr.endElement("cell");

				at = new AttributesImpl();
				at.addAttribute("", "name", "", "", "cost_type");
				wr.startElement("", "userdata", "", at);
				wr.characters(costType);
				wr.endElement("userdata");

				wr.startElement("cell");
				if (permissionSet.isPERMISSION_ADD_EXPENSE()) {
					wr.characters(wr.doubleEscape(data.getCostAmount()) + "^javascript:editRecord(" + data.getCostId() + ");^_self");
				} else {
					wr.characters(wr.doubleEscape(data.getCostAmount()));
				}
				wr.endElement("cell");

				String costRemark = data.getCostRemark() == null ? "" : data.getCostRemark();
				String cRemark = costRemark;
				if (costRemark.length() > 50) {
					cRemark = cRemark.substring(0, 50) + "...";
				}
				wr.startElement("cell");
				wr.characters(wr.doubleEscape(cRemark));
				wr.endElement("cell");

				at = new AttributesImpl();
				at.addAttribute("", "name", "", "", "remark");
				wr.startElement("", "userdata", "", at);
				wr.characters(costRemark);
				wr.endElement("userdata");

				wr.startElement("cell");
				wr.characters(fullDate);
				wr.endElement("cell");

				wr.endElement("row");
			}
			wr.endElement("rows");
			wr.endDocument();
		} catch (SAXException e) {
			TPLogger.getLogger().error("Error while creating xml file for costs", e);
		}

		return sWr.getBuffer().toString();
	}

	/**
	 * @param costPaidDate
	 * @param amount
	 * @param costTypeId
	 * @param positionId
	 * @param sourceId
	 * @param subscriptionDateFrom
	 * @param subscriptionDateTo
	 * @param remarks
	 * @param userId
	 * @return costId
	 */
	public String addCost(Date costPaidDate, double amount, String costTypeId, String positionIds, String sourceId, Date subscriptionDateFrom, Date subscriptionDateTo, String remarks, String userId) {
		String costId = null;
		DBPreparedQuery dq = null;
		DBTransaction tran = null;
		try {
			tran = new DBTransaction();

			// get temporary stored textresume
			dq = new DBPreparedQuery("dCostManager_AddCost", tran);
			dq.setString(1, costTypeId);
			dq.setDouble(2, amount);
			dq.setTimestamp(3, new Timestamp(costPaidDate.getTime()));
			dq.setString(4, Utils.isBlankOrNull(sourceId) ? null : sourceId);
			dq.setString(5, remarks);
			dq.setString(6, userId);

			dq.setDate(7, Utils.convertDateToSQLDate(subscriptionDateFrom));
			dq.setDate(8, Utils.convertDateToSQLDate(subscriptionDateTo));

			dq.execute();

			dq = new DBPreparedQuery("dFetchLastInsertID", tran);
			costId = dq.getIdResult();
			addCostPositionDetails(costId, positionIds, tran);
			tran.commit();
		} catch (Exception e) {
			costId = null;
			TPLogger.getLogger().error("Error while adding cost", e);
			try {
				tran.rollback();
			} catch (Exception re) {
				TPLogger.getLogger().error("Unable to rollback", e);
			}
		} finally {
			if (dq != null) {
				dq.releaseTransaction(tran);
			}
		}
		return costId;
	}

	/**
	 * @param costId
	 * @param costPaidDate
	 * @param amount
	 * @param costTypeId
	 * @param positionId
	 * @param sourceId
	 * @param subscriptionDateFrom
	 * @param subscriptionDateTo
	 * @param remarks
	 * @param userId
	 * @return
	 */
	public boolean updateCost(String costId, Date costPaidDate, double amount, String costTypeId, String positionIds, String sourceId, Date subscriptionDateFrom, Date subscriptionDateTo,
			String remarks, String userId) {
		DBPreparedQuery dq = null;
		DBTransaction tran = null;
		boolean success = false;

		try {
			tran = new DBTransaction();
			dq = new DBPreparedQuery("dCostManager_UpdateCost", tran);
			dq.setString(1, costTypeId);
			dq.setDouble(2, amount);
			dq.setDate(3, Utils.convertDateToSQLDate(costPaidDate));
			dq.setString(4, Utils.isBlankOrNull(sourceId) ? null : sourceId);
			dq.setString(5, remarks);
			dq.setString(6, userId);
			dq.setDate(7, Utils.convertDateToSQLDate(subscriptionDateFrom));
			dq.setDate(8, Utils.convertDateToSQLDate(subscriptionDateTo));
			dq.setString(9, costId);
			dq.execute();
			deleteCostPositionDetails(costId, tran);
			addCostPositionDetails(costId, positionIds, tran);
			tran.commit();
			success = true;
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while updating cost", e);
			try {
				tran.rollback();
			} catch (Exception re) {
				TPLogger.getLogger().error("Unable to rollback", e);
			}
		} finally {
			if (dq != null) {
				dq.releaseTransaction(tran);
			}
		}
		return success;
	}

	/**
	 * @param costId
	 * @return CostData for given costId
	 */
	public CostData getCostData(String costId) {
		DBPreparedQuery dq = null;
		CostData costData = null;
		try {
			dq = new DBPreparedQuery("dCostManager_GetCostData");
			dq.setString(1, costId);
			costData = (CostData) dq.getSingleObjectResult();
		} catch (SQLException e) {
			TPLogger.getLogger().error("Error while getting costData", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return costData;
	}

	/**
	 * @param costId
	 * @return true if delete successful else false
	 */
	public boolean deleteCost(String costId) {
		DBPreparedQuery dq = null;
		DBTransaction tran = null;
		boolean success = false;
		try {
			tran = new DBTransaction();
			deleteCostPositionDetails(costId, tran);
			dq = new DBPreparedQuery("dCostManager_DeleteCost", tran);
			dq.setString(1, costId);
			dq.execute();
			tran.commit();
			success = true;
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while deleting cost", e);
			try {
				tran.rollback();
			} catch (Exception re) {
				TPLogger.getLogger().error("Unable to rollback", e);
			}
		} finally {
			if (dq != null) {
				dq.releaseTransaction(tran);
			}
		}
		return success;
	}

	/**
	 * @param costId
	 * @param positionIds
	 *            is comma separated position Ids
	 * @param tran
	 * @throws Exception
	 */
	public void addCostPositionDetails(String costId, String positionIds, DBTransaction tran) throws Exception {
		DBPreparedQuery dq = null;
		try {
			if (!Utils.isBlankOrNull(positionIds)) {
				String[] pIds = positionIds.split(",");
				for (int i = 0; i < pIds.length; i++) {
					dq = new DBPreparedQuery("dCostManager_AddCostPositions", tran);
					dq.setId(1, costId);
					dq.setId(2, pIds[i]);
					dq.execute();
				}
			}
		} catch (Exception e) {
			throw e;
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
	 * @param costId
	 * @param tran
	 * @throws Exception
	 */
	public void deleteCostPositionDetails(String costId, DBTransaction tran) throws Exception {
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dCostManager_DeleteCostPositions", tran);
			dq.setId(1, costId);
			dq.execute();
		} catch (Exception e) {
			throw e;
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
}
