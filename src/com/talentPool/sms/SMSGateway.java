/**
 * 
 */
package com.talentPool.sms;

/**
 * @author pallavi
 *
 */
public interface SMSGateway {
	public void send(String message, String cellPhoneNumber, String senderId) throws Exception;
}