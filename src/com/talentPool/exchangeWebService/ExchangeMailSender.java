/**
 * 
 */
package com.talentPool.exchangeWebService;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import javax.mail.SendFailedException;

import microsoft.exchange.webservices.data.Appointment;
import microsoft.exchange.webservices.data.BodyType;
import microsoft.exchange.webservices.data.EmailAddress;
import microsoft.exchange.webservices.data.EmailMessage;
import microsoft.exchange.webservices.data.ExchangeCredentials;
import microsoft.exchange.webservices.data.ExchangeService;
import microsoft.exchange.webservices.data.ExchangeVersion;
import microsoft.exchange.webservices.data.MessageBody;
import microsoft.exchange.webservices.data.TimeZoneDefinition;
import microsoft.exchange.webservices.data.WebCredentials;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.properties.TPApplicationProperties;
import com.talentPool.common.utils.Utils;
import com.talentPool.inbox.InboxConstants;
import com.talentPool.inbox.dataobject.AttachmentData;
import com.talentPool.inbox.dataobject.InboxData;
import com.talentPool.inbox.dataobject.MessageData;
import com.talentPool.inbox.exception.SendMailException;
import com.talentPool.inbox.manager.InboxManager;
import com.talentPool.notifier.OutBoundConstants;
import com.talentPool.notifier.dataobject.OutBoundData;
import com.talentPool.notifier.manager.OutBoundManager;

/**
 * @author Shantanu
 *
 */
public class ExchangeMailSender {
	
	private String[] emailSeparator = { ",", ";" };
	InboxData inboxData;
	ExchangeService service;
	
	//MessageData.
	private String emailStartSeparator = "<";
	private String emailEndSeparator = ">";
	
	public ExchangeMailSender(){
		initExchangeInstance();
	}
	/**
	 * Will initialize the inbox settings data.
	 */
	private void initExchangeInstance(){
		InboxManager inboxManager = new InboxManager();
		try{
			inboxData=inboxManager.getCurrentInboxSettings();			
			if(inboxData.getExchangeServerVersion().equals(InboxConstants.EXCHANGE_VERSION_2010_SP1)){
				service = new ExchangeService(ExchangeVersion.Exchange2010_SP1);
			}else{
				service = new ExchangeService(ExchangeVersion.Exchange2007_SP1);
			}
			service.setUrl(new URI("https://"+inboxData.getExchangeServerName()+"/ews/Exchange.asmx"));
			ExchangeCredentials credentials = new WebCredentials(inboxData.getUserName(),inboxData.getPassword(),inboxData.getDomainName());
			service.setCredentials(credentials);
		}catch (Exception e) {
			TPLogger.getLogger().debug(GlobalConstants.ERROR, e);
		}
	}
	
	/**
	 * 
	 * @param messageData
	 * @param emailType
	 * @param absolutePath
	 */
	public void sendMail(MessageData messageData, String emailType, String absolutePath) throws SendMailException{
		try{
			EmailMessage message = new EmailMessage(service);
			message.setSubject(messageData.getSubject());			
						
			String[] to = getSeparatedList(messageData.getTo());
			addRecipients(message,to,InboxConstants.MAIL_SENT_AS_TO);
			
			if(!Utils.isBlankOrNull(messageData.getCc())){
				String[] toCC = getSeparatedList(messageData.getCc());
				addRecipients(message,toCC,InboxConstants.MAIL_SENT_AS_CC);				
			}			
			
			if(!Utils.isBlankOrNull(messageData.getBcc())){
				String[] toBCC = getSeparatedList(messageData.getBcc());
				addRecipients(message,toBCC,InboxConstants.MAIL_SENT_AS_BCC);
			}
						
			if(!Utils.isBlankOrNull(messageData.getReplyTo())){
				message.getReplyTo().add(setFromEmail(messageData.getReplyTo()));
			}else{
				if(!Utils.isBlankOrNull(messageData.getFromAddress())){
					message.getReplyTo().add(setFromEmail(messageData.getFromAddress()));
				}else{
					message.getReplyTo().add(setFromEmail(Utils.isBlankOrNull(messageData.getFrom())?"":messageData.getFrom()));
				}
				
			}
			
			if(!Utils.isBlankOrNull(messageData.getHtmlBody())){
				message.setBody(MessageBody.getMessageBodyFromText(messageData.getHtmlBody()));
				message.getBody().setBodyType(BodyType.HTML);
			}else{
				message.setBody(MessageBody.getMessageBodyFromText(messageData.getTextBody()));
				message.getBody().setBodyType(BodyType.Text);				
			}
		
			if(messageData.getAttachments()!=null){
				addAttachments(message,messageData,absolutePath);
			}
			if(!Utils.isBlankOrNull(messageData.getOutboundId())){
				updateOutBoundStatus(messageData,OutBoundConstants.SENT_STATUS_SUCCESS);
			}
			
			message.sendAndSaveCopy();//saves the copy at sent item of OWA.

		}catch (SendFailedException sfe) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, sfe);
			updateOutBoundStatus(messageData,OutBoundConstants.SENT_STATUS_FAIL);
			throw new SendMailException("Error while sending email");
			
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			updateOutBoundStatus(messageData,OutBoundConstants.SENT_STATUS_FAIL);
			throw new SendMailException("Error while sending email");
		}
	}
	
	/**
	 * 
	 * @param toAddress
	 * @param fromAddress
	 * @param subject
	 * @param htmlBody
	 * @param calBody
	 * @param outboundId
	 * @param inReplyTo
	 * @param fromDate
	 * @param toDate
	 * @throws SendMailException
	 */
	public void sendAppointmentMail(String toAddress, String fromAddress, String subject, 
			String htmlBody, String calBody, String outboundId, String inReplyTo, Date fromDate, Date toDate) throws SendMailException {
		try{
			Appointment appointment = new Appointment(service);
			Collection<TimeZoneDefinition> collection = service.getServerTimeZones();
			
			TimeZoneDefinition tzd = null;
			for(TimeZoneDefinition timeZoneDefinition : collection){
				if(timeZoneDefinition.getId().equals("Asia/Calcutta")){
					tzd = timeZoneDefinition;					
					break;
				}
			}
			Calendar cal = Calendar.getInstance(); 
			cal.setTime(fromDate);
			cal.add(Calendar.HOUR, -5);
			cal.add(Calendar.MINUTE, -30);
			TPLogger.getLogger().debug("from cal date "+cal.getTime());			
			TPLogger.getLogger().debug("FROM DATE ------- "+fromDate);
			appointment.setStart(cal.getTime());
			//appointment.setStart(fromDate);
			//appointment.setStartTimeZone(tzd);
			
			
			cal.setTime(toDate);
			cal.add(Calendar.HOUR, -5);
			cal.add(Calendar.MINUTE, -30);
			TPLogger.getLogger().debug("to cal date "+cal.getTime());
			TPLogger.getLogger().debug("TO DATE ------- "+toDate);
			appointment.setEnd(cal.getTime());
			//appointment.setEnd(toDate);
			//appointment.setEndTimeZone(tzd);
			
			
			
			appointment.setSubject(subject);
			appointment.setBody(MessageBody.getMessageBodyFromText(htmlBody));
			
			appointment.getRequiredAttendees().add(toAddress);
			appointment.setInReplyTo(inReplyTo);
								
			appointment.save();
			
		}catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR,e);
		}
	}
	
	/**
	 * 
	 * @param toAddress
	 * @param fromAddress
	 * @param subject
	 * @param htmlBody
	 * @param calBody
	 * @param outboundId
	 * @param inReplyTo
	 * @throws SendMailException
	 */
	public void sendAppointmentMail(String toAddress, String fromAddress, String subject, 
			String htmlBody, String calBody, String outboundId, String inReplyTo) throws SendMailException {
		try{
			EmailMessage message = new EmailMessage(service);
			
			message.setSubject(subject);	
			
			String[] to = getSeparatedList(toAddress);
			addRecipients(message,to,InboxConstants.MAIL_SENT_AS_TO);
			
			message.getReplyTo().add(setFromEmail(inReplyTo));
						
			File file = new File(TPApplicationProperties.getProperty("installation.path")+"/"+"appointment.ics");
			FileOutputStream fos = new FileOutputStream(file);
			DataOutputStream dos = new DataOutputStream(fos);
			dos.writeChars(calBody);
			
			message.getAttachments().addFileAttachment("appointment.ics",file.getAbsolutePath());

			message.setBody(MessageBody.getMessageBodyFromText(htmlBody));			
			message.sendAndSaveCopy();//saves the copy at sent item of OWA.
			//message.send();
			file.delete();
			
		}catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR,e);
		}
	}
	
	/**
	 * Takes , or ; separated list of email and returns String[] of emails
	 * 
	 * @param email
	 * @return
	 */
	private String[] getSeparatedList(String email){
		String[] emails = null;
		try {
			if (!Utils.isBlankOrNull(email)) {
				for (int i = 0; i < emailSeparator.length; i++) {
					String separator = emailSeparator[i];
					if (email.indexOf(separator) > 0) {
						emails = email.split(separator);						
						break;
					}
				}
				if (emails == null) {
					emails = new String[1];
					emails[0] = email.trim();
				}
			}
		}catch(Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return emails;
	}
	
	/**
	 * 
	 * @param message
	 * @param to
	 * @throws Exception
	 */
	private void addRecipients(EmailMessage message,String[] to,String recipientType) throws Exception {
		try{
			if (to != null){
				for (int i = 0; i < to.length; i++){
					String tmpTo = to[i];
					if (!Utils.isBlankOrNull(tmpTo)){
						String[] tmp=emailAddressName(tmpTo);
						if(recipientType.equals(InboxConstants.MAIL_SENT_AS_BCC)){
							message.getBccRecipients().add(tmp[1]);
						}else if(recipientType.equals(InboxConstants.MAIL_SENT_AS_CC)){
							message.getCcRecipients().add(tmp[1]);
						}else{
							message.getToRecipients().add(tmp[1]);							
						}
					}
				}
			}
		}catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR,e);
		}
	}
	
	/**
	 * 
	 * @param nameEmail
	 * @return
	 */
	private String[] emailAddressName(String nameEmail){
		String[] emailAddressName = null;
		try{			
			if(nameEmail.contains(emailStartSeparator)){
				if(nameEmail.indexOf(emailStartSeparator)>0){
					emailAddressName = nameEmail.split(emailStartSeparator);
					emailAddressName[1] = emailAddressName[1].replace(emailEndSeparator, "");
				}else{
					emailAddressName = new String[2];
					emailAddressName[0] = " ";
					emailAddressName[1] = nameEmail;				
				}
			}else{
				emailAddressName = new String[2];
				emailAddressName[0] = " ";
				emailAddressName[1] = nameEmail;
			}
		}catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR,e);
		}
		return emailAddressName;
	}
	
	/**
	 * 
	 * @param email
	 * @return
	 */
	private EmailAddress setFromEmail(String email){
		EmailAddress emailAddress = new EmailAddress();
		try{
			String[] strArr = emailAddressName(email);
			emailAddress.setName(strArr[0]);			
			emailAddress.setAddress(strArr[1]);
		}catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}		
		return emailAddress;
	}
	
	/**
	 * 
	 * @param message
	 * @param messageData
	 * @param absolutePath
	 */
	private void addAttachments(EmailMessage message, MessageData messageData, String absolutePath){
		try{
			ArrayList<AttachmentData> attachments = messageData.getAttachments();
			if(attachments!=null && attachments.size()>0){
				for(int i=0;i<attachments.size();i++){
					AttachmentData attachment = (AttachmentData)attachments.get(i);
					String path = attachment.getAttachmentFilePath();
					if (absolutePath != null) {
						path = Utils.concatFilePath(absolutePath, attachment.getAttachmentFilePath());
					}
					message.getAttachments().addFileAttachment(attachment.getOriginalFileName(),path);
				}
			}
		}catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR,e);
		}		
	}
	
	private void updateOutBoundStatus(MessageData messageData , String status) {
		try{
			TPLogger.getLogger().debug("STATUS ====== "+status);
			OutBoundData outboundData = new OutBoundData();
			outboundData.setOutboundId(Integer.parseInt(messageData.getOutboundId()));
			outboundData.setSentStatus(status);
			OutBoundManager outBoundManager = new OutBoundManager();
			outBoundManager.update(outboundData);
		}catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR,e);
		}
	}
	
	/**
	 * 
	 * @param messageData
	 * @param emailType
	 * @param absolutePath
	 */
	public void sendMailForPassword(MessageData messageData, String emailType, String absolutePath,boolean sendEmail) throws SendMailException{
		try{
			EmailMessage message = new EmailMessage(service);
			message.setSubject(messageData.getSubject());			
						
			String[] to = getSeparatedList(messageData.getTo());
			addRecipients(message,to,InboxConstants.MAIL_SENT_AS_TO);
			
			if(!Utils.isBlankOrNull(messageData.getCc())){
				String[] toCC = getSeparatedList(messageData.getCc());
				addRecipients(message,toCC,InboxConstants.MAIL_SENT_AS_CC);				
			}			
			
			if(!Utils.isBlankOrNull(messageData.getBcc())){
				String[] toBCC = getSeparatedList(messageData.getBcc());
				addRecipients(message,toBCC,InboxConstants.MAIL_SENT_AS_BCC);
			}
						
			if(!Utils.isBlankOrNull(messageData.getReplyTo())){
				message.getReplyTo().add(setFromEmail(messageData.getReplyTo()));
			}else{
				if(!Utils.isBlankOrNull(messageData.getFromAddress())){
					message.getReplyTo().add(setFromEmail(messageData.getFromAddress()));
				}else{
					message.getReplyTo().add(setFromEmail(Utils.isBlankOrNull(messageData.getFrom())?"":messageData.getFrom()));
				}
				
			}
			
			if(!Utils.isBlankOrNull(messageData.getHtmlBody())){
				message.setBody(MessageBody.getMessageBodyFromText(messageData.getHtmlBody()));
				message.getBody().setBodyType(BodyType.HTML);
			}else{
				message.setBody(MessageBody.getMessageBodyFromText(messageData.getTextBody()));
				message.getBody().setBodyType(BodyType.Text);				
			}
		
			if(messageData.getAttachments()!=null){
				addAttachments(message,messageData,absolutePath);
			}
			if(!Utils.isBlankOrNull(messageData.getOutboundId())){
				updateOutBoundStatus(messageData,OutBoundConstants.SENT_STATUS_SUCCESS);
			}
			
			message.sendAndSaveCopy();//saves the copy at sent item of OWA.

		}catch (SendFailedException sfe) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, sfe);
			updateOutBoundStatus(messageData,OutBoundConstants.SENT_STATUS_FAIL);
			throw new SendMailException("Error while sending email");
			
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			updateOutBoundStatus(messageData,OutBoundConstants.SENT_STATUS_FAIL);
			throw new SendMailException("Error while sending email");
		}
	}
}