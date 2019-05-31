/**
 * 
 */
package com.talentPool.ldap.dataobject;

import com.talentPool.common.db.SimpleDataObject;

/**
 * @author shivprasad
 * 
 */
public class LDAPServerData extends SimpleDataObject {

	public String getServerId() {
		return getString("serverId");
	}

	public void setServerId(String serverId) {
		setAttribute("serverId", serverId);
	}
	
	public String getServerURL() {
		return getString("serverURL");
	}

	public void setServerURL(String serverURL) {
		setAttribute("serverURL", serverURL);
	}

	public String getSecurityPrincipal() {
		return getString("securityPrincipal");
	}

	public void setSecurityPrincipal(String securityPrincipal) {
		setAttribute("securityPrincipal", securityPrincipal);
	}
}
