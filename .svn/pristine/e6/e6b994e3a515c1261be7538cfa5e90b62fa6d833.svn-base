/**
 * 
 */
package com.talentPool.vendorservice.manager;

import com.talentPool.admin.manager.AdminManager;
import com.talentPool.common.utils.EncryptionUtils;
import com.talentPool.common.utils.Utils;
import com.talentPool.user.UserConstants;
import com.talentPool.user.dataobject.LoginData;
import com.talentPool.user.manager.LoginManager;
import com.talentPool.user.manager.UserManager;
import com.talentPool.user.utils.UserUtils;
import com.talentPool.vendorservice.dataobject.VerrorData;

/**
 * @author shivprasad
 * 
 */
public class VendorUserManager {
	public VerrorData changePassword(String userId, String oldPassword, String newPassword, String confirmNewPassword, String securityQuestionId, String securityAnswer, boolean forgotPassword) {
		VerrorData verrorData = new VerrorData();
		LoginData data = new LoginData();
		UserManager userManager = new UserManager();
		try {
			if (!forgotPassword) {
				if (Utils.isBlankOrNull(oldPassword)) {
					verrorData.putError("change_password.error.old_password.nullOrBlank");
				} else {
					LoginManager loginManager = new LoginManager();
					data = loginManager.getUser(userId);
					if (!EncryptionUtils.encryptString(oldPassword).equalsIgnoreCase(data.getPassword())) {
						verrorData.putError("change_password.error.old_password.invalid");
					}
				}
			}
			if (Utils.isBlankOrNull(newPassword)) {
				verrorData.putError("change_password.error.new_password.nullOrBlank");
			} else if (!newPassword.equalsIgnoreCase(confirmNewPassword)) {
				verrorData.putError("change_password.error.new_password.mismatch");
			} else if (!Utils.isBlankOrNull(securityQuestionId) && Utils.isBlankOrNull(securityAnswer)) {
				verrorData.putError("change_password.error.security_Answer.null");
			} else if(!Utils.isBlankOrNull(UserConstants.PASSWORD_PATTERN) && !UserUtils.isValidPasswordPattern(newPassword)) {
				verrorData.putError("change_password.error.new_password.weak");
			} else if (newPassword.length() < UserConstants.REQUIRED_MIN_PASSWORD_LENGTH || newPassword.length() > UserConstants.REQUIRED_MAX_PASSWORD_LENGTH) {
				verrorData.putError("change_password.errors.short_password");
			} 
			
			if (verrorData.getErrors().size() == 0) {
				if (!userManager.valdatePasswordAgainstLastPasswords(userId, newPassword)){
					verrorData.putError("change_password.error.password_same_as_last");
				} else if (Utils.isBlankOrNull(securityQuestionId)){
					userManager.savePassword(userId, newPassword);
					if(forgotPassword && userManager.isUserDisabled(userId)){
						AdminManager adminManager = new AdminManager();
						adminManager.changeUserStatus(userId, UserConstants.ACTIVE);
					}
				} else {
					userManager.savePasswordAndSecrityQuestion(userId, newPassword, securityQuestionId, securityAnswer);
				}
			}

		} catch (Exception e) {
			verrorData.putError("change_password.error.password.update_failed");
		}
		return verrorData;
	}
	
}
