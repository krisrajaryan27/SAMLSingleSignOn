/**
 * 
 */
package com.talentPool.user.manager;

import java.util.BitSet;

import com.talentPool.common.properties.TPApplicationProperties;
import com.talentPool.license.manager.LicenseObj;
import com.talentPool.user.constants.ModuleConstants;

/**
 * @author shivprasad
 * 
 */
public class ModuleSet {
	private static BitSet modules;

	static {
		// read modules available from license
		modules = LicenseObj.getLicenseObject().getModules();
//		BitSet m = new BitSet(64);
//		m.set(ModuleConstants.MODULE_SMS);
//		m.set(ModuleConstants.MODULE_OUTLOOK_MEETING_REQUEST);
//		m.set(ModuleConstants.MODULE_MASS_EMAILS);
//		m.set(ModuleConstants.MODULE_LDAP);
//		m.set(ModuleConstants.MODULE_AUTO_RESPONSE_EMAIL);
//		m.set(ModuleConstants.MODULE_CUSTOM_FIELDS);
//		m.set(ModuleConstants.MODULE_COSTS);
//		m.set(ModuleConstants.MODULE_WEB_INTEGRATION);
//		m.set(ModuleConstants.MODULE_VENDOR);
//		m.set(ModuleConstants.MODULE_REQUISITION);
//		modules = m;

	}

	public static void setBit(int access, boolean state) {
		if (state) {
			modules.set(access);
		} else {
			modules.clear(access);
		}
	}

	public static boolean isModuleAvailable(int moduleNo) {
		return modules.get(moduleNo);
	}

	/**
	 * @return modules.get(ModuleConstants.the mODULE_AUTO_RESPONSE_EMAIL
	 */
	public static boolean isMODULE_AUTO_RESPONSE_EMAIL() {
		return modules.get(ModuleConstants.MODULE_AUTO_RESPONSE_EMAIL);
	}

	/**
	 * @return modules.get(ModuleConstants.the mODULE_COSTS
	 */
	public static boolean isMODULE_COSTS() {
		return modules.get(ModuleConstants.MODULE_COSTS);
	}

	/**
	 * @return modules.get(ModuleConstants.the mODULE_CUSTOM_FIELDS
	 */
	public static boolean isMODULE_CUSTOM_FIELDS() {
		return modules.get(ModuleConstants.MODULE_CUSTOM_FIELDS);
	}

	/**
	 * @return modules.get(ModuleConstants.the mODULE_LDAP
	 */
	public static boolean isMODULE_LDAP() {
		return modules.get(ModuleConstants.MODULE_LDAP);
	}

	/**
	 * @return modules.get(ModuleConstants.the mODULE_MASS_EMAILS
	 */
	public static boolean isMODULE_MASS_EMAILS() {
		return modules.get(ModuleConstants.MODULE_MASS_EMAILS);
	}

	/**
	 * @return modules.get(ModuleConstants.the mODULE_OUTLOOK_MEETING_REQUEST
	 */
	public static boolean isMODULE_OUTLOOK_MEETING_REQUEST() {
		return modules.get(ModuleConstants.MODULE_OUTLOOK_MEETING_REQUEST);
	}

	/**
	 * @return modules.get(ModuleConstants.the mODULE_REQUISITION
	 */
	public static boolean isMODULE_REQUISITION() {
		return modules.get(ModuleConstants.MODULE_REQUISITION);
	}

	/**
	 * @return modules.get(ModuleConstants.the mODULE_SMS
	 */
	public static boolean isMODULE_SMS() {
		return modules.get(ModuleConstants.MODULE_SMS);
	}

	/**
	 * @return modules.get(ModuleConstants.the mODULE_VENDOR
	 */
	public static boolean isMODULE_VENDOR() {
		return modules.get(ModuleConstants.MODULE_VENDOR);
	}

	/**
	 * @return modules.get(ModuleConstants.the mODULE_WEB_INTEGRATION
	 */
	public static boolean isMODULE_WEB_INTEGRATION() {
		return modules.get(ModuleConstants.MODULE_WEB_INTEGRATION);
	}

	//Report Scheduler Only for Professional and Enterprise version 
	public static boolean isMODULE_REPORT_SCHEDULER() {
		return modules.get(ModuleConstants.MODULE_REPORT_SCHEDULER);
	}

	public static boolean isMODULE_EMPLOYEE() {
		return modules.get(ModuleConstants.MODULE_EMPLOYEE);
	}

	public static boolean isMODULE_WALKIN() {
		return modules.get(ModuleConstants.MODULE_WALKIN);
	}
	
	public static boolean isMODULE_BUDGET() {
		return modules.get(ModuleConstants.MODULE_BUDGET);
	}
	
	// API module for Enterprise plus version
	/**
	 * @return if API module is available with the license
	 * @since TalentPool v13.0.0
	 * Available only with 'Enterprise Plus' version
	 * Added By : Sachin More
	 */
	public static boolean isMODULE_API() {
		return modules.get(ModuleConstants.MODULE_API);
	}
	
	// SOCIAL module for Enterprise plus version
	/**
	 * @return if SOCIAL module is available with the license
	 * @since TalentPool v13.1.0
	 * Available only with 'Enterprise Plus' version
	 * Added By : Sachin More
	 */
	public static boolean isMODULE_SOCIAL_NETWORK() {
		return modules.get(ModuleConstants.MODULE_SOCIAL_NETWORK);
	}
	
	public static boolean isMODULE_RCHILLI_INTEGRATION() {
		return (modules.get(ModuleConstants.MODULE_RCHILLI_INTEGRATION)&& "1".equals(TPApplicationProperties.getProperty("is_rchilli_integration")));
	}
}
