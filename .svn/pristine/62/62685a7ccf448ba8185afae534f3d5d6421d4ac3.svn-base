/**
 * 
 */
package com.talentPool.common.dataobject;

import java.util.HashMap;
import java.util.Map;

import com.talentPool.user.constants.ModuleConstants;

/**
 * @author pallavi
 *
 */
public class LicenseErrorMessages {
	private static Map<String, String> messages = null;
	static {
		messages = new HashMap<String, String>();
		messages.put(String.valueOf(ModuleConstants.MODULE_SMS), "common.error.purchase_sms_license");
		messages.put(String.valueOf(ModuleConstants.MODULE_OUTLOOK_MEETING_REQUEST), "common.error.purchase_outlook_meeting_license");
		messages.put(String.valueOf(ModuleConstants.MODULE_MASS_EMAILS), "common.error.purchase_mass_email_license");
		messages.put(String.valueOf(ModuleConstants.MODULE_LDAP), "common.error.purchase_ldap_license");
		messages.put(String.valueOf(ModuleConstants.MODULE_AUTO_RESPONSE_EMAIL), "common.error.purchase_auto_response_license");
		messages.put(String.valueOf(ModuleConstants.MODULE_CUSTOM_FIELDS), "common.error.purchase_custom_fields_license");
		messages.put(String.valueOf(ModuleConstants.MODULE_COSTS), "common.error.purchase_cost_license");
		messages.put(String.valueOf(ModuleConstants.MODULE_WEB_INTEGRATION), "common.error.purchase_web_integration_license");
		messages.put(String.valueOf(ModuleConstants.MODULE_VENDOR), "common.error.purchase_vendor_license");
		messages.put(String.valueOf(ModuleConstants.MODULE_REQUISITION), "common.error.purchase_requisition_license");
		messages.put(String.valueOf(ModuleConstants.MODULE_REPORT_SCHEDULER), "common.error.purchase_report_schedular_license");
		messages.put(String.valueOf(ModuleConstants.MODULE_EMPLOYEE), "common.error.purchase_employee_license");
		messages.put(String.valueOf(ModuleConstants.MODULE_WALKIN), "common.error.purchase_walkin_license");
		messages.put(String.valueOf(ModuleConstants.MODULE_BUDGET), "common.error.purchase_budget_license");
		messages.put(String.valueOf(ModuleConstants.MODULE_API), "common.error.purchase_api_license");
	}
	
	public static String get(int module) {
		return messages.get(String.valueOf(module));
	}
}
