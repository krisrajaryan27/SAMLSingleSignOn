/**
 * 
 */
package com.talentPool.costs.manager;

import java.io.StringWriter;
import java.util.ArrayList;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.db.DBPreparedQuery;
import com.talentPool.common.xmlutils.XMLWriter;
import com.talentPool.costs.dataobject.CostTypeData;

/**
 * @author shivprasad
 * 
 */
public class CostTypeManager {
	/**
	 * add cost type and return costType Id
	 * 
	 * @param costType
	 * @return
	 * @throws Exception
	 */
	public String addCostType(String costType) throws Exception{
		String costTypeId = "";
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dCostTypeManager_AddCostType");
			dq.setString(1, costType);
			dq.execute();
			dq = new DBPreparedQuery("dFetchLastInsertID");
			costTypeId = dq.getIdResult();
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while adding cost type", e);
			throw e;
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return costTypeId;
	}

	/**
	 * updates cost type
	 * 
	 * @param costTypeId
	 * @param costType
	 * @throws Exception
	 */
	public void updateCostType(String costTypeId, String costType) throws Exception{
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dCostTypeManager_UpdateCostType");
			dq.setString(1, costType);
			dq.setId(2, costTypeId);
			dq.execute();
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while updating cost type", e);
			throw e;
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
	}

	/**
	 * Deletes cost type with passed ID
	 * 
	 * @param costTypeId
	 * @throws Exception
	 */
	public void deleteCostType(String costTypeId) throws Exception{
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dCostTypeManager_DeleteCostType");
			dq.setId(1, costTypeId);
			dq.execute();
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while deleting cost type", e);
			throw e;
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
	}

	/**
	 * return costTypeData for given ID
	 * 
	 * @param costTypeId
	 * @return
	 */
	public CostTypeData getCostTypeData(String costTypeId) {
		DBPreparedQuery dq = null;
		CostTypeData costTypeData = null;
		try {
			dq = new DBPreparedQuery("dCostTypeManager_GetCostTypeData");
			dq.setId(1, costTypeId);
			costTypeData = (CostTypeData) dq.getSingleObjectResult();
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while getting cost type data", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return costTypeData;
	}

	/**
	 * @return Arraylist of type CostTypeData for all cost types
	 * @throws Exception
	 */
	public ArrayList<CostTypeData> getCostTypes() {
		DBPreparedQuery dq = null;
		ArrayList<CostTypeData> results = null;
		try {
			dq = new DBPreparedQuery("dCostTypeManager_GetAllCostTypes");
			results = dq.getResult();
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while getting all cost type", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return results;
	}

	/**
	 * @param list
	 * @return xml of cost types
	 */
	public String getXMLForCostTypes(ArrayList<CostTypeData> list) {
		StringWriter sWr = new StringWriter();
		XMLWriter wr = new XMLWriter(sWr);

		try {
			wr.startDocument();
			wr.startElement("rows");
			for (int i = 0; i < list.size(); i++) {
				CostTypeData data = list.get(i);

				AttributesImpl at = new AttributesImpl();
				at.addAttribute("", "id", "", "", "" + data.getItemId());
				wr.startElement("", "row", "", at);

				at = new AttributesImpl();
				at.addAttribute("", "name", "", "", "Col_I_Comment");
				wr.startElement("", "userdata", "", at);
				wr.characters("Delete");
				wr.endElement("userdata");

				at = new AttributesImpl();
				at.addAttribute("", "name", "", "", "costType");
				wr.startElement("", "userdata", "", at);
				wr.characters(data.getItemName());
				wr.endElement("userdata");

				wr.startElement("cell");
				wr.characters("<img src=\"images/ico_delete.gif\" border=0>^javascript:deleteRecord(" + data.getItemId() + ");^_self");
				wr.endElement("cell");

				wr.startElement("cell");
				wr.characters(wr.doubleEscape(data.getItemName()) + "^javascript:editRecord(" + data.getItemId() + ");^_self");
				wr.endElement("cell");

				wr.endElement("row");
			}
			wr.endElement("rows");
			wr.endDocument();
		} catch (SAXException e) {
			TPLogger.getLogger().error("Error while creating xml file for costTypes", e);
		}

		return sWr.getBuffer().toString();
	}

}
