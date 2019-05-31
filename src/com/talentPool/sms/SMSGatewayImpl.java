/**
 * 
 */
package com.talentPool.sms;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import com.talentPool.admin.manager.AdminManager;
import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.db.SimpleDataObject;
import com.talentPool.common.utils.Utils;

/**
 * @author pallavi
 *
 */
public class SMSGatewayImpl implements SMSGateway {
	private static String providerURL = null;
	
	/**
	 * @return the providerURL
	 */
	public static String getProviderURL() {		
		return providerURL;
	}

	/**
	 * @param providerURL the providerURL to set
	 */
	public static void setProviderURL(String providerURL) {
		SMSGatewayImpl.providerURL = providerURL;
	}

	public SMSGatewayImpl() {
		super();
		if (Utils.isBlankOrNull(getProviderURL())) {
			AdminManager adminManager = new AdminManager();
			SimpleDataObject settings = adminManager.getSMSProviderSettings();
			if (settings != null) {
				setProviderURL(settings.getString("value"));
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.talentPool.sms.SMSGateway#send(java.lang.String, java.lang.String)
	 */
	public void send(String message, String cellPhoneNumber, String senderId) throws Exception {
		try {
			// Currently we are supporting formats for sms providers 'globalbulksms' and 'valueleaf'.
			// globalbulksms takes anything as senderId whereas valueleaf needs a register senderId, 
			// which is usually a generic name. Thus commenting the below check for senderId 
			if (Utils.isBlankOrNull(cellPhoneNumber) || cellPhoneNumber.length() < SMSConstants.noOfDigitsInCellPhone) 
					//|| Utils.isBlankOrNull(senderId) || senderId.length() < SMSConstants.noOfDigitsInCellPhone) 
				{
				return;
			}
			if (cellPhoneNumber.length() > SMSConstants.noOfDigitsInCellPhone) {
				cellPhoneNumber = cellPhoneNumber.substring(cellPhoneNumber.length() - SMSConstants.noOfDigitsInCellPhone, 
						cellPhoneNumber.length());
			}
			if (senderId.length() > SMSConstants.noOfDigitsInCellPhone) {
				senderId = senderId.substring(senderId.length() - SMSConstants.noOfDigitsInCellPhone, 
						senderId.length());
			}
			
			String smsUrl = "";
			if (!Utils.isBlankOrNull(providerURL)) {
				smsUrl = providerURL; 
				smsUrl = smsUrl.replaceFirst(SMSConstants.PHONE, URLEncoder.encode(cellPhoneNumber, "UTF-8"));
				smsUrl = smsUrl.replaceFirst(SMSConstants.MESSAGE, URLEncoder.encode(message, "UTF-8"));
				smsUrl = smsUrl.replaceFirst(SMSConstants.SENDERID, URLEncoder.encode(senderId, "UTF-8"));
			}
			URL url = new URL(smsUrl);
			//URL url = new URL("http://66.36.229.70:8800/?user=talentp&password=tal567&PhoneNumber="+URLEncoder.encode(cellPhoneNumber, "UTF-8")+"&Text="+URLEncoder.encode(message, "UTF-8")+"&Sender=SMSjunction");
	        URLConnection conn = url.openConnection();
	        conn.setDoOutput(true);	        
	        
	        // Get the response
	        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	        String line;
	        while ((line = rd.readLine()) != null) {
	            TPLogger.getLogger().debug(line);
	        }
	        rd.close();
		} catch (Exception e) {
			TPLogger.getLogger().error("Error encountered while sending sms", e);
			throw e;
		}
	}
}
