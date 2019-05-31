/**
 * 
 */
package com.talentPool.ldap;

import java.util.ArrayList;
import java.util.Hashtable;

import javax.naming.AuthenticationException;
import javax.naming.CommunicationException;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.utils.Utils;
import com.talentPool.ldap.constants.LDAPConstants;
import com.talentPool.ldap.dataobject.LDAPServerData;
import com.talentPool.ldap.dataobject.LDAPUserData;

/**
 * @author shivprasad
 * 
 */
public class LDAPQueryProcessor {

	/**
	 * Try to initiate InitialLdapContext with given LDAP Server with given
	 * username and password.
	 * 
	 * @param serverDO
	 * @param userName
	 * @param password
	 * @return
	 * @throws CommunicationException
	 */
	public boolean isValidLDAPUser(LDAPServerData serverDO, String userName, String password) throws CommunicationException {
		boolean isValid = false;
		/*
		 * The method InitialLdapContext don't throw AuthenticationException if
		 * username and password is blank so it is checked here
		 */
		if (Utils.isBlankOrNull(userName) || Utils.isBlankOrNull(password)) {
			return isValid;
		}
		try {
			getInitialLdapContext(serverDO, userName, password);
			isValid = true;
		} catch (CommunicationException e) {
			throw e;
		} catch (NamingException e) {
			TPLogger.getLogger().error("Error while validating user", e);
		}
		return isValid;
	}

	/**
	 * This method gives you LdapContext initialized, may throw NamingException
	 * if it is not able to create LdapContext
	 * 
	 * @param serverDO
	 * @param userName
	 * @param password
	 * @return
	 * @throws NamingException
	 */
	private LdapContext getInitialLdapContext(LDAPServerData serverDO, String userName, String password) throws NamingException {
		Hashtable<String, String> env = getLDAPEnv(serverDO, userName, password);
		LdapContext ctx = null;
		try {
			ctx = new InitialLdapContext(env, null);
		} catch (AuthenticationException e) {
			String securityPrincipal = getModifiedSecurityPrincipal(serverDO.getSecurityPrincipal());
			env.put(Context.SECURITY_PRINCIPAL, userName + "@" + securityPrincipal);
			ctx = new InitialLdapContext(env, null);
		}
		return ctx;
	}

	/**
	 * If normal security principal don't work this methods gives to different
	 * security principal in username@domain.com format
	 * 
	 * @param securityPrincipal
	 * @return
	 */
	private String getModifiedSecurityPrincipal(String securityPrincipal) {
		String userPrinicipalName = "";
		try {
			int index = 0;
			while (index <= securityPrincipal.length()) {
				index = securityPrincipal.indexOf("=", index);
				if (index > 0) {
					int beginIndex = index + 1;
					index = securityPrincipal.indexOf(",", index);
					if (index == -1) {
						index = securityPrincipal.length();
					}
					int lastIndex = index;
					userPrinicipalName = userPrinicipalName + (userPrinicipalName.length() > 0 ? "." : "") + securityPrincipal.substring(beginIndex, lastIndex).trim();
				} else {
					break;
				}
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error in creating modified security principal", e);
		}
		return userPrinicipalName;
	}

	/**
	 * This methods takes LDAPServerData, username and password and returns a
	 * HashMap of environment for LDAP
	 * 
	 * @param serverDO
	 * @param userName
	 * @param password
	 * @return
	 */
	private Hashtable<String, String> getLDAPEnv(LDAPServerData serverDO, String userName, String password) {
		Hashtable<String, String> env = new Hashtable<String, String>();
		env.put(Context.INITIAL_CONTEXT_FACTORY, LDAPConstants.INITIAL_CONTEXT_FACTORY);
		env.put(Context.PROVIDER_URL, serverDO.getServerURL());
		// set security credentials, note using simple cleartext authentication
		String adminName = "CN=" + userName + ",CN=Users";
		if (!Utils.isBlankOrNull(serverDO.getSecurityPrincipal())) {
			adminName += "," + serverDO.getSecurityPrincipal();
		}
		env.put(Context.SECURITY_AUTHENTICATION, LDAPConstants.SECURITY_AUTHENTICATION);
		env.put(Context.SECURITY_PRINCIPAL, adminName);
		env.put(Context.SECURITY_CREDENTIALS, password);
		return env;
	}

	/**
	 * This methods serach the given searchString in given list of ldap servers
	 * and returns a list of LDAPUserData
	 * 
	 * @param ldapServers
	 * @param searchBy
	 * @param serarchString
	 * @param userName
	 * @param password
	 * @return
	 */
	public ArrayList<LDAPUserData> getUsersList(ArrayList<LDAPServerData> ldapServers, String searchBy, String serarchString, String userName, String password) {
		ArrayList<LDAPUserData> users = new ArrayList<LDAPUserData>();
		try {
			for (int i = 0; i < ldapServers.size(); i++) {
				try {
					LDAPServerData serverDO = ldapServers.get(i);
					LdapContext ctx = getInitialLdapContext(serverDO, userName, password);

					SearchControls searchCtls = new SearchControls();
					searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);
					// specify the LDAP search filter
					String searchFilter = "(objectClass=user)";
					if (!Utils.isBlankOrNull(searchBy)) {
						if (searchBy.equalsIgnoreCase(LDAPConstants.SEARCH_BY_USER_NAME))
							searchFilter = "(&(objectClass=user)(" + LDAPConstants.LDAP_USER_EMAIL + "=*)(" + LDAPConstants.LDAP_GIVEN_NAME + "=*)(" + LDAPConstants.LDAP_SAM_ACCOUNT_NAME + "=" + serarchString + "*))";
						else if (searchBy.equalsIgnoreCase(LDAPConstants.SEARCH_BY_GIVEN_NAME))
							searchFilter = "(&(objectClass=user)(" + LDAPConstants.LDAP_GIVEN_NAME + "=" + serarchString + "))";
						else if (searchBy.equalsIgnoreCase(LDAPConstants.SEARCH_BY_SUR_NAME))
							searchFilter = "(&(objectClass=user)(" + LDAPConstants.LDAP_SUR_NAME + "=" + serarchString + "))";
						else if (searchBy.equalsIgnoreCase(LDAPConstants.SEARCH_BY_EMAIL))
							searchFilter = "(&(objectClass=user)(" + LDAPConstants.LDAP_USER_EMAIL + "=" + serarchString + "))";
					}

					String searchBase = serverDO.getSecurityPrincipal();
					int totalResults = 0;

					// Specify the attributes to return
					String returnedAtts[] = { LDAPConstants.LDAP_COMMON_NAME, LDAPConstants.LDAP_DOMAIN_COMPONENT, LDAPConstants.LDAP_GIVEN_NAME, LDAPConstants.LDAP_HOME_PHONE,
							LDAPConstants.LDAP_MOBILE, LDAPConstants.LDAP_ORGANIZATION_NAME, LDAPConstants.LDAP_ORGANIZATION_UNIT_NAME, LDAPConstants.LDAP_SAM_ACCOUNT_NAME,
							LDAPConstants.LDAP_SUR_NAME, LDAPConstants.LDAP_USER_PRINCIPAL_NAME, LDAPConstants.LDAP_USER_EMAIL };
					searchCtls.setReturningAttributes(returnedAtts);

					// Search for objects using the filter
					NamingEnumeration answer = ctx.search(searchBase, searchFilter, searchCtls);
					// Collection userList = new User().getUsersNames();
					while (answer.hasMoreElements()) {
						SearchResult sr = (SearchResult) answer.next();
						// Print out the groups
						Attributes attrs = sr.getAttributes();
						LDAPUserData ldapUserData = new LDAPUserData();
						if (attrs != null) {
							try {
								for (NamingEnumeration ae = attrs.getAll(); ae.hasMore();) {
									Attribute attr = (Attribute) ae.next();
									for (NamingEnumeration e = attr.getAll(); e.hasMore(); totalResults++) {
										String id = attr.getID();
										String value = (String) e.next();
										ldapUserData.setAttribute(id, value);
									}
								}
							} catch (NamingException e) {
								TPLogger.getLogger().error(GlobalConstants.ERROR, e);
							}
						}
						users.add(ldapUserData);
					}
				} catch (Exception e) {
					TPLogger.getLogger().error(GlobalConstants.ERROR, e);
				}
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return users;
	}
	
	
/*
	public static void main(String[] args) {
		LDAPServerData sdo = new LDAPServerData();
		sdo.setServerURL("ldap://10.10.1.11:389");
		sdo.setSecurityPrincipal("dc=talentica-all,dc=com");
		LDAPQueryProcessor qry = new LDAPQueryProcessor();
		try {
			ArrayList<LDAPServerData> ldapServers = new ArrayList<LDAPServerData>();
			ldapServers.add(sdo);
//			LDAPServerData sdo1 = new LDAPServerData();
//			sdo1.setServerURL("ldap://10.10.1.1:389");
//			sdo1.setSecurityPrincipal("dc=talentica,dc=com");
//			ldapServers.add(sdo1);
			//qry.getUsersList(ldapServers, LDAPConstants.SEARCH_BY_USER_NAME, "shivprasad", "shivprasad", "shiv3@d");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	*/
}
