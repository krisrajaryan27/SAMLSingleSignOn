package com.talentPool.masters.manager;

import java.io.StringWriter;
import java.sql.SQLException;
import java.util.ArrayList;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.db.DBPreparedQuery;
import com.talentPool.common.db.SimpleDataObject;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.xmlutils.XMLWriter;

/**
 * @author Sachinm
 * 
 */
public class SecurityQuestionMasterManager {

	/**
	 * @param securityQuestion
	 * @throws SQLException
	 */
	public void addSecurityQuestion(String securityQuestion) throws SQLException {
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dSecurityQuestionMasters_AddSecurityQuestion");
			dq.setString(1, securityQuestion.trim());
			dq.execute();
		} catch (SQLException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			throw new SQLException();
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
	}

	/**
	 * @param securityQuestionId
	 * @return Security Question for given id
	 * @throws SQLException
	 */
	public SimpleDataObject getSecurityQuestion(String securityQuestionId) throws SQLException {
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dSecurityQuestionMasters_GetSecurityQuestion");
			dq.setString(1, securityQuestionId);
			return (SimpleDataObject) dq.getSingleObjectResult();
		} catch (SQLException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			throw new SQLException();
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
	}

	/**
	 * @return list of all security questions
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<SimpleDataObject> getSecurityQuestions() throws SQLException {
		ArrayList<SimpleDataObject> securityQuestions = new ArrayList<SimpleDataObject>();
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dSecurityQuestionMasters_GetAllSecurityQuestions");
			securityQuestions = (ArrayList<SimpleDataObject>) dq.getResult();
		} catch (SQLException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			throw e;
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return securityQuestions;
	}

	/**
	 * @param securityQuestionId
	 * @param securityQuestion
	 * @throws SQLException
	 */
	public void updateSecurityQuestion(String securityQuestionId, String securityQuestion) throws SQLException {
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dSecurityQuestionMasters_UpdateSecurityQuestion");
			dq.setString(1, securityQuestion.trim());
			dq.setString(2, securityQuestionId);
			dq.execute();
		} catch (SQLException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			throw new SQLException();
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
	}

	/**
	 * @param securityQuestionId
	 * @throws SQLException
	 */
	public void deleteSecurityQuestion(String securityQuestionId) throws SQLException {
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dSecurityQuestionMasters_DeleteSecurityQuestion");
			dq.setString(1, securityQuestionId);
			dq.execute();
		} catch (SQLException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			throw new SQLException();
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
	}

	/**
	 * @param securityQuestions
	 * @return security questions in XML format
	 */
	public String getXMLForSecurityQuestions(ArrayList<SimpleDataObject> securityQuestions) {
		StringWriter sWr = new StringWriter();
		XMLWriter wr = new XMLWriter(sWr);
		try {
			wr.startDocument();
			wr.startElement("rows");
			if (securityQuestions != null && securityQuestions.size() > 0) {
				for (int i = 0; securityQuestions != null && i < securityQuestions.size(); i++) {
					SimpleDataObject data = securityQuestions.get(i);

					AttributesImpl at = new AttributesImpl();
					at.addAttribute("", "id", "", "", data.getAttribute("securityQuestionId") + "");
					wr.startElement("", "row", "", at);

					at = new AttributesImpl();
					at.addAttribute("", "name", "", "", "Col_I_Comment");
					wr.startElement("", "userdata", "", at);
					wr.characters("Delete");
					wr.endElement("userdata");

					wr.startElement("cell");
					wr.characters("<img src=\"images/ico_delete.gif\" border=0 onclick=\"javascript:deleteRecord("
							+ data.getAttribute("securityQuestionId")
							+ ");\" />");
					wr.endElement("cell");

					at = new AttributesImpl();
					at.addAttribute("", "name", "", "", "itemName");
					wr.startElement("", "userdata", "", at);
					wr.characters((String) data.getAttribute("securityQuestion"));
					wr.endElement("userdata");

					wr.startElement("cell");
					wr.characters(wr.doubleEscape((String) data.getAttribute("securityQuestion"))
							+ "^javascript:editRecord("
							+ data.getAttribute("securityQuestionId")
							+ ");^_self");
					wr.endElement("cell");

					wr.endElement("row");
				}
			}
			wr.endElement("rows");
			wr.endDocument();
		} catch (SAXException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return sWr.getBuffer().toString();
	}
}
