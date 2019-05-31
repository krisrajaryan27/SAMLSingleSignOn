/**
 * 
 */
package com.talentPool.ldap.manager;

import java.io.StringWriter;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.CommunicationException;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;


import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.db.DBPreparedQuery;
import com.talentPool.common.xmlutils.XMLWriter;
import com.talentPool.ldap.LDAPQueryProcessor;
import com.talentPool.ldap.dataobject.LDAPServerData;
import com.talentPool.masters.exception.MasterExistException;

/**
 * @author shivprasad
 * 
 */
public class LDAPManager {

	/**
	 * Adds LDAP Server Info to DB
	 * 
	 * @param ldapDO
	 * @throws MasterExistException
	 */
	public void addLDAPServerInfo(LDAPServerData ldapDO) throws MasterExistException {
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dLDAPManager_AddServer");
			dq.setString(1, ldapDO.getServerURL());
			dq.setString(2, ldapDO.getSecurityPrincipal());
			dq.execute();
		} catch (SQLException e) {
			TPLogger.getLogger().error("Error While Inserting LDAP Server INFO", e);
			throw new MasterExistException();
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
	}

	/**
	 * Update LDAP Server Info to DB
	 * 
	 * @param ldapDO
	 * @throws MasterExistException
	 */
	public void updateLDAPServerInfo(LDAPServerData ldapDO) throws MasterExistException {
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dLDAPManager_UpdateServer");
			dq.setString(1, ldapDO.getServerURL());
			dq.setString(2, ldapDO.getSecurityPrincipal());
			dq.setId(3, ldapDO.getServerId());
			dq.execute();
		} catch (SQLException e) {
			TPLogger.getLogger().error("Error While Inserting LDAP Server INFO", e);
			throw new MasterExistException();
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
	}

	/**
	 * @return ArrayList of all servers
	 */
	public ArrayList<LDAPServerData> getLDAPServerList() {
		DBPreparedQuery dq = null;
		ArrayList<LDAPServerData> result = null;
		try {
			dq = new DBPreparedQuery("dLDAPManager_GetAllServers");
			result = dq.getResult();
		} catch (SQLException e) {
			TPLogger.getLogger().error("Error While Getting All Servers", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return result;
	}

	/**
	 * return Single LDAPServerData object for given serverId
	 * 
	 * @param serverId
	 * @return
	 */
	public LDAPServerData getLDAPServerData(String serverId) {
		DBPreparedQuery dq = null;
		LDAPServerData ldapServerData = null;
		try {
			dq = new DBPreparedQuery("dLDAPManager_GetServerData");
			dq.setString(1, serverId);
			ldapServerData = (LDAPServerData) dq.getSingleObjectResult();
		} catch (SQLException e) {
			TPLogger.getLogger().error("Error While Getting All Servers", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return ldapServerData;
	}

	/**
	 * change the name of admin
	 * 
	 * @param userId
	 * @param userName
	 * @throws SQLException
	 */
	public void updateUserName(String userId, String userName) throws SQLException {
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dLDAPManager_UpdateAdminUserName");
			dq.setString(1, userName);
			dq.setString(2, userId);
			dq.execute();
		} catch (SQLException e) {
			TPLogger.getLogger().error("Error While Updating admin username", e);
			throw e;
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
	}

	/**
	 * @param ldapServers
	 * @return
	 */
	public String getLdapServerXML(ArrayList<LDAPServerData> ldapServers) {
		StringWriter sWr = new StringWriter();
		XMLWriter wr = new XMLWriter(sWr);

		try {
			wr.startDocument();
			wr.startElement("rows");
			for (int i = 0; i < ldapServers.size(); i++) {
				LDAPServerData data = (LDAPServerData) ldapServers.get(i);

				AttributesImpl at = new AttributesImpl();
				at.addAttribute("", "id", "", "", "" + data.getServerId());
				wr.startElement("", "row", "", at);

				at = new AttributesImpl();
				at.addAttribute("", "name", "", "", "serverURL");
				wr.startElement("", "userdata", "", at);
				wr.characters(data.getServerURL());
				wr.endElement("userdata");

				wr.startElement("cell");
				wr.characters(wr.doubleEscape(data.getServerURL()) + "^javascript:editRecord(" + data.getServerId() + ");^_self");
				wr.endElement("cell");

				wr.startElement("cell");
				wr.characters(wr.doubleEscape(data.getSecurityPrincipal()));
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

	/**
	 * Checks against all avilable servers and return data
	 * @param userName
	 * @param password
	 * @return
	 */
	public boolean isValidLdapUserOnAnyServer(String userName, String password) {
		boolean validLdapUser = false;
		ArrayList<LDAPServerData> ldapServerList = getLDAPServerList();
		LDAPQueryProcessor ldapQuery = new LDAPQueryProcessor();
		for (int i = 0; ldapServerList != null && i < ldapServerList.size(); i++) {
			LDAPServerData ldapDO = ldapServerList.get(i);
			try {
				if (ldapQuery.isValidLDAPUser(ldapDO, userName, password)) {
					validLdapUser = true;
					break;
				}
			} catch (CommunicationException e) {
				TPLogger.getLogger().error("Error connecting to ldap server", e);
			}
		}
		return validLdapUser;
	}
}
