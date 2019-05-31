/**
 * 
 */
package com.talentPool.inbox;

import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.SendFailedException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.event.TransportListener;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import org.apache.struts.action.ActionError;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.properties.GlobalApplicationProperties;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.properties.SMTPProperties;
import com.talentPool.common.properties.TPApplicationProperties;
import com.talentPool.common.utils.MailAuthenticator;
import com.talentPool.common.utils.Utils;
import com.talentPool.exchangeWebService.ExchangeMailSender;
import com.talentPool.inbox.dataobject.AttachmentData;
import com.talentPool.inbox.dataobject.InboxData;
import com.talentPool.inbox.dataobject.MessageData;
import com.talentPool.inbox.exception.SendMailException;
import com.talentPool.inbox.manager.InboxManager;
import com.talentPool.positions.dataobject.UserData;
import com.talentPool.positions.manager.PositionManager;



/**
 * @author shivprasad
 * 
 */
public class TPMailSender {
	Session _session = null;
	private String[] emailSeparator = { ",", ";" };
	TransportListener listener;
	Transport transport;
	InboxData inboxData;

	/**
	 * Constructor
	 */

	public TPMailSender() {
		initInstance(null);
	}

	public TPMailSender(TransportListener listener) {
		initInstance(listener);
	}

	private void initInstance(TransportListener listener) {
		if (listener != null) {
			this.listener = listener;
		}
		InboxManager inboxManager = new InboxManager();
		inboxData = inboxManager.getCurrentInboxSettings();		
		Properties props = new Properties();
		boolean isSecure = false;
		if (InboxConstants.SSL_ENABLED.equals("" + inboxData.getInboxOutgoingSSLEnabled())) {
			isSecure = true;
		}
		String protocol = InboxConstants.PROTOCOL_SMTP;
		if (isSecure) {
			protocol = InboxConstants.PROTOCOL_SECURE_SMTP;
		}
		props.put("mail.transport.protocol", InboxConstants.PROTOCOL_SECURE_SMTP);
		if (isSecure) {
			props.put("mail.smtps.host", inboxData.getSmtpHost());
			props.put("mail.smtps.quitwait", "false");
			props.put("mail.smtps.port", inboxData.getInboxOutgoingPort());
		} else {
			props.put("mail.smtp.host", inboxData.getSmtpHost());
			props.put("mail.smtp.port", inboxData.getInboxOutgoingPort());
		}
		if(InboxConstants.TLS_ENABLED.equals("" + inboxData.getInboxOutgoingTLSEnabled())) {
			props.put("mail.smtp.starttls.enable","true");
		}
		if(TPApplicationProperties.getProperty("mail.debug").equals("1"))
			props.put("mail.debug", "true");

		// load extra properties if any
		Properties extraProperties = SMTPProperties.getMailProperties();
		if (extraProperties != null) {
			Enumeration enumx = extraProperties.keys();
			while (enumx.hasMoreElements()) {
				String key = (String) (enumx.nextElement());
				props.put(key, extraProperties.get(key));
			}
		}

		if (InboxConstants.SMTP_AUTHENTICATION_REQUIRED.equals("" + inboxData.getInboxSmtpAuthRequired())) {
			if (isSecure) {
				props.put("mail.smtps.auth", "true");
			} else {
				props.put("mail.smtp.auth", "true");
			}
			props.put("mail.smtp.from", inboxData.getInboxEmail());

			if (InboxConstants.SMTP_USE_INCOMING_AUTH.equals("" + inboxData.getInboxSmtpAuthSame())) {
				_session = Session.getInstance(props, new MailAuthenticator(inboxData.getUserName(), inboxData.getPassword()));
			} else {
				_session = Session.getInstance(props, new MailAuthenticator(inboxData.getInboxSmtpUserName(), inboxData.getInboxSmtpPassword()));
			}
		} else {
			_session = Session.getInstance(props);
		}
		try {
			transport = _session.getTransport(protocol);
			if (listener != null) {
				transport.addTransportListener(listener);
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error", e);
		}

	}
	
	/**
	 * Depending on the Inbox Server Type the send() method will call the relevant sendEmail() method.  
	 * 
	 * @param messageData
	 * @param emailType
	 * @param absolutePath
	 * @throws SendMailException
	 */
	 
	public void send(MessageData messageData, String emailType, String absolutePath) throws SendMailException, Exception {
		//try{			
			if(inboxData.getInboxServerType()==Integer.parseInt(InboxConstants.SERVER_TYPE_EXCHANGE) & inboxData.getExchangeSmtp().equals(InboxConstants.EXCHANGE_NO_SMTP)){
				TPLogger.getLogger().debug(" ******************  MS EXCHANGE SERVER*********");
				ExchangeMailSender ems = new ExchangeMailSender();
				ems.sendMail(messageData, emailType, absolutePath);
			}else{
				TPLogger.getLogger().debug(" ******************  SMTP *********");
				sendEmail(messageData, emailType, absolutePath);
			}
		//}catch (Exception e) {
			//TPLogger.getLogger().debug(GlobalConstants.ERROR,e);
		//}
	}

	/**
	 * Construct an email using messageData and send it in either text or html fromat which is
	 * decided by emailType If absolute path is given then it is preattached to all attachment path
	 * 
	 * @param messageData
	 * @param emailType
	 * @param absolutePath
	 * @throws SendMailException
	 */
	public void sendEmail(MessageData messageData, String emailType, String absolutePath) throws SendMailException {
		try {
			Message message = new MimeMessage(_session);
			// message.setHeader("Return-Path",inboxData.getInboxEmail());

			// set subject
			message.setSubject(messageData.getSubject());

			// Set receipents
			String[] to = getSeparatedList(messageData.getTo());
			addReceipents(message, Message.RecipientType.TO, to);
			
	
			String hrManagerEmail="";
			hrManagerEmail=getHrManagerAsCC();
			if(!Utils.isBlankOrNull(messageData.getCc())){
				hrManagerEmail=hrManagerEmail+messageData.getCc();
			}
			if(GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_ENABLE_CC_HR_MANAGER_WHILE_SENDING_EMAIL).equals(GlobalConstants.ENABLED)){
				
				String[] cc = getSeparatedList(hrManagerEmail);
				addReceipents(message, Message.RecipientType.CC, cc);
			}
			else{
				String[] cc = getSeparatedList(messageData.getCc());
				addReceipents(message, Message.RecipientType.CC, cc);
			}
			
			
			
			String[] bcc = getSeparatedList(messageData.getBcc());
			addReceipents(message, Message.RecipientType.BCC, bcc);

			// set From
			try {
				message.setFrom(new InternetAddress(messageData.getFrom()));
			} catch (Exception e) {
				message.setFrom(new InternetAddress(inboxData.getInboxEmail()));
			}
			if (!Utils.isBlankOrNull(messageData.getOutboundId())) {
				message.setHeader("Outbound-Id", messageData.getOutboundId());
			}
			
			// set ReplyTo
			if(!Utils.isBlankOrNull(messageData.getReplyTo())) {
				InternetAddress[] replyTo = new InternetAddress[1];
				try {
					replyTo[0] = new InternetAddress(messageData.getReplyTo());
				} catch (AddressException e) {
					replyTo[0] = new InternetAddress(inboxData.getInboxEmail());
				}				
				message.setReplyTo(replyTo);
			}			
			
			// read receipt configuration
			// message.setHeader("Disposition-Notification-To", "shivprasad@talentpool.in");

			// Construct multipart message
			Multipart mp = new MimeMultipart();
			MimeBodyPart bodyPart1 = new MimeBodyPart();
			if (emailType.equals(InboxConstants.EMAIL_BODY_HTML)) {
				bodyPart1.setDataHandler(new DataHandler(new ByteArrayDataSource(messageData.getHtmlBody(), "text/html")));
			} else {
				bodyPart1.setText(messageData.getTextBody());
			}
			mp.addBodyPart(bodyPart1);

			// add attachments as body parts
			ArrayList attachments = messageData.getAttachments();
			if (attachments != null && attachments.size() > 0) {
				for (int i = 0; i < attachments.size(); i++) {
					AttachmentData attachment = (AttachmentData) attachments.get(i);
					MimeBodyPart bodyPart2 = new MimeBodyPart();
					String path = attachment.getAttachmentFilePath();
					if (absolutePath != null) {
						path = Utils.concatFilePath(absolutePath, attachment.getAttachmentFilePath());
					}
					FileDataSource fds = new FileDataSource(path);
					bodyPart2.setDataHandler(new DataHandler(fds));
					if (!Utils.isBlankOrNull(attachment.getContentId())) {
						bodyPart2.setContentID(attachment.getContentId());
					}
					bodyPart2.setFileName(attachment.getOriginalFileName());
					mp.addBodyPart(bodyPart2);
				}
			}

			// set message content
			message.setContent(mp);
			// send message, can be send using Traqnsport.send, but creating an
			// instance
			// will provide more flexibility to you
			// Transport mailTransport =
			// _session.getTransport(InboxConstants.PROTOCOL_SMTP);
			// mailTransport.connect();
			transport.connect();
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
			// Transport.send(message);

		} catch (SendFailedException sfe) {
			TPLogger.getLogger().error("Error while sending email", sfe);
			// Address[] list = sfe.getInvalidAddresses();
			// for (int i = 0; i < list.length; i++) {
			// TPLogger.getLogger().error("Invalid Address: " + list[i]);
			// }
			//
			// list = sfe.getValidUnsentAddresses();
			// for (int i = 0; i < list.length; i++) {
			// TPLogger.getLogger().error("Valid Unsent Address: " + list[i]);
			// }
			//
			// list = sfe.getValidSentAddresses();
			// for (int i = 0; i < list.length; i++) {
			// TPLogger.getLogger().error("Valid Sent Address: " + list[i]);
			// }
			throw new SendMailException("Error while sending email");
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while sending email", e);
			throw new SendMailException("Error while sending email");
		} finally {
			try {
				transport.close();
			} catch (Exception e) {

			}
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
	public void sendAppointment(String toAddress, String fromAddress, String subject, String htmlBody, 
			String calBody, String outboundId, String inReplyTo, Date fromDate, Date toDate) throws SendMailException {
		try{
			if(inboxData.getInboxServerType()==Integer.parseInt(InboxConstants.SERVER_TYPE_EXCHANGE) & inboxData.getExchangeSmtp().equals(InboxConstants.EXCHANGE_NO_SMTP)){
				TPLogger.getLogger().debug(" ******************  MS EXCHANGE SERVER*********");
				ExchangeMailSender ems = new ExchangeMailSender();
				if(inboxData.getExchangeServerVersion().equals(InboxConstants.EXCHANGE_VERSION_2007_SP1)){
					ems.sendAppointmentMail(toAddress, fromAddress, subject, htmlBody, calBody, outboundId, inReplyTo);
				}else if(inboxData.getExchangeServerVersion().equals(InboxConstants.EXCHANGE_VERSION_2010_SP1)){
					ems.sendAppointmentMail(toAddress, fromAddress, subject, htmlBody, calBody, outboundId, inReplyTo, fromDate, toDate);
				}				
			}else{
				sendAppointmentMail(toAddress, fromAddress, subject, htmlBody, calBody, outboundId, inReplyTo);
			}	
		}catch(Exception e){
			
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
	public void sendAppointmentMail(String toAddress, String fromAddress, String subject, String htmlBody, String calBody, String outboundId, String inReplyTo) throws SendMailException {
		try {
			Message message = new MimeMessage(_session);
			// message.setHeader("Return-Path",inboxData.getInboxEmail());

			// set subject
			message.setSubject(subject);

			// Set receipents
			String[] to = getSeparatedList(toAddress);
			addReceipents(message, Message.RecipientType.TO, to);

			// set From
			message.setFrom(new InternetAddress(fromAddress));
			if (!Utils.isBlankOrNull(outboundId)) {
				message.setHeader("Outbound-Id", outboundId);
			}
			//set cc receipents
			String hrManagerEmail="";
			hrManagerEmail=getHrManagerAsCC();
			String[] cc = getSeparatedList(hrManagerEmail);
			
			/*if(GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_ENABLE_CC_HR_MANAGER_WHILE_SENDING_EMAIL).equals(GlobalConstants.ENABLED)){
				addReceipents(message, Message.RecipientType.CC, cc);
			}*/
			
			// set ReplyTo
			if(!Utils.isBlankOrNull(inReplyTo)) {
				InternetAddress[] replyTo = new InternetAddress[1];
				replyTo[0] = new InternetAddress(inReplyTo);
				message.setReplyTo(replyTo);
			}			
			
			Multipart mpx = new MimeMultipart();

			Multipart mp = new MimeMultipart("alternative");
			MimeBodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setDataHandler(new DataHandler(new ByteArrayDataSource(htmlBody, "text/html")));
			mp.addBodyPart(messageBodyPart);

			messageBodyPart = new MimeBodyPart();
			messageBodyPart.setDataHandler(new DataHandler(new ByteArrayDataSource(calBody, "text/calendar; method=REQUEST; charset=ISO-8859-1")));
			mp.addBodyPart(messageBodyPart);

			messageBodyPart = new MimeBodyPart();
			messageBodyPart.setContent(mp);
			mpx.addBodyPart(messageBodyPart);

			messageBodyPart = new MimeBodyPart();
			messageBodyPart.setFileName("appointment.ics");
			messageBodyPart.setDataHandler(new DataHandler(new ByteArrayDataSource(calBody, "application/ics;")));
			messageBodyPart.setDisposition(Part.ATTACHMENT);
			mpx.addBodyPart(messageBodyPart);

			// Put parts in message
			message.setContent(mpx);
			transport.connect();
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();

		} catch (SendFailedException sfe) {
			TPLogger.getLogger().error("Error while sending email", sfe);
			throw new SendMailException("Error while sending email");
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while sending email", e);
			throw new SendMailException("Error while sending email");
		} finally {
			try {
				transport.close();
			} catch (Exception e) {

			}
		}
	}

	/**
	 * Adds recipents to message
	 * 
	 * @param message
	 * @param receipentType
	 * @param to
	 * @throws Exception
	 */
	/*private void addReceipentqqs(Message message, Message.RecipientType receipentType, String[] to) throws Exception {
		if (to != null) {
			for (int i = 0; i < to.length; i++) {
				String tmpTo = to[i];
				if (!Utils.isBlankOrNull(tmpTo)) {
					Address[] toAdd = InternetAddress.parse(to[i]);
					message.addRecipients(receipentType, toAdd);
				}
			}
		}
	}*/

	private void addReceipents(Message message, Message.RecipientType receipentType, String[] to) throws Exception {
		if (to != null) {
			StringBuilder sb=new StringBuilder();
			for (int i = 0; i < to.length; i++) {
				String tmpTo = to[i];
				
				if (!Utils.isBlankOrNull(tmpTo)) {
					sb.append(tmpTo);
					sb.append(",");
				}
			}
			String address = sb.toString();
			InternetAddress[] iAdressArray = InternetAddress.parse(address);
			message.setRecipients(receipentType, iAdressArray);
		}
	}
	
	/**
	 * Takes , or ; separated list of email and returns String[] of emails
	 * 
	 * @param email
	 * @return
	 */
	private String[] getSeparatedList(String email) {
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
		} catch (Exception e) {
			TPLogger.getLogger().debug("Error in spliting email list", e);
		}

		return emails;
	}
	
	private String getHrManagerAsCC(){
		String hrManagerEmail="";
		PositionManager positionManager =new PositionManager();
		List<UserData> hrManagerList=positionManager.getActiveHRManager();
		for (int i = 0; hrManagerList != null && i < hrManagerList.size(); i++) {
			if (Utils.isBlankOrNull(hrManagerEmail)) {
				hrManagerEmail = hrManagerList.get(i).getUserName() + " <" + hrManagerList.get(i).getUserEmail() + ">;";
			} else {
				hrManagerEmail += hrManagerList.get(i).getUserName() + " <" + hrManagerList.get(i).getUserEmail() + ">;";
			}
		}
		return hrManagerEmail;
	}
	/**
	 * Depending on the Inbox Server Type the send() method will call the relevant sendEmail() method.  
	 * 
	 * @param messageData
	 * @param emailType
	 * @param absolutePath
	 * @throws SendMailException
	 */
	 
	public void send(MessageData messageData, String emailType, String absolutePath,boolean sendMail) throws SendMailException, Exception {
		//try{			
			if(inboxData.getInboxServerType()==Integer.parseInt(InboxConstants.SERVER_TYPE_EXCHANGE) & inboxData.getExchangeSmtp().equals(InboxConstants.EXCHANGE_NO_SMTP)){
				TPLogger.getLogger().debug(" ******************  MS EXCHANGE SERVER*********");
				ExchangeMailSender ems = new ExchangeMailSender();
				ems.sendMailForPassword(messageData, emailType, absolutePath,sendMail);
			}else{
				TPLogger.getLogger().debug(" ******************  SMTP *********");
				sendEmail(messageData, emailType, absolutePath,sendMail);
			}
		//}catch (Exception e) {
			//TPLogger.getLogger().debug(GlobalConstants.ERROR,e);
		//}
	}
	
	/**
	 * Construct an email using messageData and send it in either text or html fromat which is
	 * decided by emailType If absolute path is given then it is preattached to all attachment path
	 * 
	 * @param messageData
	 * @param emailType
	 * @param absolutePath
	 * @throws SendMailException
	 */
	public void sendEmail(MessageData messageData, String emailType, String absolutePath,boolean sendMail) throws SendMailException {
		try {
			Message message = new MimeMessage(_session);
			// message.setHeader("Return-Path",inboxData.getInboxEmail());

			// set subject
			message.setSubject(messageData.getSubject());

			// Set receipents
			String[] to = getSeparatedList(messageData.getTo());
			addReceipents(message, Message.RecipientType.TO, to);
			// message.setRecipient(Message.RecipientType.TO, new
			// InternetAddress("asd") );
			//set HRmanager as CC receipient
		/*	String hrManagerEmail="";
			hrManagerEmail=getHrManagerAsCC();
			if(!Utils.isBlankOrNull(messageData.getCc())){
				hrManagerEmail=hrManagerEmail+messageData.getCc();
			}
			if(GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_ENABLE_CC_HR_MANAGER_WHILE_SENDING_EMAIL).equals(GlobalConstants.ENABLED)){
				
				String[] cc = getSeparatedList(hrManagerEmail);
				addReceipents(message, Message.RecipientType.CC, cc);
			}
			else{
				String[] cc = getSeparatedList(messageData.getCc());
				addReceipents(message, Message.RecipientType.CC, cc);
			}*/
			
			
			
			String[] bcc = getSeparatedList(messageData.getBcc());
			addReceipents(message, Message.RecipientType.BCC, bcc);

			// set From
			try {
				message.setFrom(new InternetAddress(messageData.getFrom()));
			} catch (Exception e) {
				message.setFrom(new InternetAddress(inboxData.getInboxEmail()));
			}
			if (!Utils.isBlankOrNull(messageData.getOutboundId())) {
				message.setHeader("Outbound-Id", messageData.getOutboundId());
			}
			
			// set ReplyTo
			if(!Utils.isBlankOrNull(messageData.getReplyTo())) {
				InternetAddress[] replyTo = new InternetAddress[1];
				try {
					replyTo[0] = new InternetAddress(messageData.getReplyTo());
				} catch (AddressException e) {
					replyTo[0] = new InternetAddress(inboxData.getInboxEmail());
				}				
				message.setReplyTo(replyTo);
			}			
			
			// read receipt configuration
			// message.setHeader("Disposition-Notification-To", "shivprasad@talentpool.in");

			// Construct multipart message
			Multipart mp = new MimeMultipart();
			MimeBodyPart bodyPart1 = new MimeBodyPart();
			if (emailType.equals(InboxConstants.EMAIL_BODY_HTML)) {
				bodyPart1.setDataHandler(new DataHandler(new ByteArrayDataSource(messageData.getHtmlBody(), "text/html")));
			} else {
				bodyPart1.setText(messageData.getTextBody());
			}
			mp.addBodyPart(bodyPart1);

			// add attachments as body parts
			ArrayList attachments = messageData.getAttachments();
			if (attachments != null && attachments.size() > 0) {
				for (int i = 0; i < attachments.size(); i++) {
					AttachmentData attachment = (AttachmentData) attachments.get(i);
					MimeBodyPart bodyPart2 = new MimeBodyPart();
					String path = attachment.getAttachmentFilePath();
					if (absolutePath != null) {
						path = Utils.concatFilePath(absolutePath, attachment.getAttachmentFilePath());
					}
					FileDataSource fds = new FileDataSource(path);
					bodyPart2.setDataHandler(new DataHandler(fds));
					if (!Utils.isBlankOrNull(attachment.getContentId())) {
						bodyPart2.setContentID(attachment.getContentId());
					}
					bodyPart2.setFileName(attachment.getOriginalFileName());
					mp.addBodyPart(bodyPart2);
				}
			}

			// set message content
			message.setContent(mp);
			// send message, can be send using Traqnsport.send, but creating an
			// instance
			// will provide more flexibility to you
			// Transport mailTransport =
			// _session.getTransport(InboxConstants.PROTOCOL_SMTP);
			// mailTransport.connect();
			transport.connect();
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
			// Transport.send(message);

		} catch (SendFailedException sfe) {
			TPLogger.getLogger().error("Error while sending email", sfe);
			// Address[] list = sfe.getInvalidAddresses();
			// for (int i = 0; i < list.length; i++) {
			// TPLogger.getLogger().error("Invalid Address: " + list[i]);
			// }
			//
			// list = sfe.getValidUnsentAddresses();
			// for (int i = 0; i < list.length; i++) {
			// TPLogger.getLogger().error("Valid Unsent Address: " + list[i]);
			// }
			//
			// list = sfe.getValidSentAddresses();
			// for (int i = 0; i < list.length; i++) {
			// TPLogger.getLogger().error("Valid Sent Address: " + list[i]);
			// }
			throw new SendMailException("Error while sending email");
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while sending email", e);
			throw new SendMailException("Error while sending email");
		} finally {
			try {
				transport.close();
			} catch (Exception e) {

			}
		}
	}
}