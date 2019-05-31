/**
 * 
 */
package com.talentPool.ldap.dataobject;

import com.talentPool.common.db.SimpleDataObject;
import com.talentPool.common.utils.Utils;
import com.talentPool.ldap.constants.LDAPConstants;

/**
 * @author shivprasad
 * 
 */
public class LDAPUserData extends SimpleDataObject {
	public String getSamAccountName() {
		return getBlankIfNull(getString(LDAPConstants.LDAP_SAM_ACCOUNT_NAME));
	}

	public String getSurName() {
		return getBlankIfNull(getString(LDAPConstants.LDAP_SUR_NAME));
	}

	public String getGivenName() {
		return getBlankIfNull(getString(LDAPConstants.LDAP_GIVEN_NAME));
	}

	private String getBlankIfNull(String str) {
		if (Utils.isBlankOrNull(str)) {
			return "";
		}
		return str;
	}
}
