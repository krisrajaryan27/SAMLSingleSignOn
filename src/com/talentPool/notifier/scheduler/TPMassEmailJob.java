package com.talentPool.notifier.scheduler;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.GregorianCalendar;

import javax.mail.Header;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.event.TransportEvent;
import javax.mail.event.TransportListener;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.talentPool.applicant.ApplicantConstants;
import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.utils.Utils;
import com.talentPool.inbox.InboxConstants;
import com.talentPool.inbox.TPMailSender;
import com.talentPool.inbox.dataobject.InboxData;
import com.talentPool.inbox.dataobject.MessageData;
import com.talentPool.inbox.manager.InboxManager;
import com.talentPool.inbox.manager.MassEmailManager;
import com.talentPool.notifier.OutBoundConstants;
import com.talentPool.notifier.dataobject.OutBoundData;
import com.talentPool.notifier.manager.OutBoundManager;
import com.talentPool.notifier.utils.TemplateUtils;

public class TPMassEmailJob implements Job, TransportListener {

	public void execute(JobExecutionContext context) throws JobExecutionException {
		TPLogger.getLogger().debug("Start Mass Email trigger");
		if (MassEmailSchedular.JOB_STATUS_BUZY) {
			TPLogger.getLogger().debug("mass email is already running. Exiting mass email job");
			return;
		}
		MassEmailSchedular.JOB_STATUS_BUZY = true;
		// get emails to sent
		try {
			OutBoundManager outBoundManager = new OutBoundManager();
			ArrayList notifications = outBoundManager.getAllNotifications(OutBoundConstants.MODE_EMAIL, OutBoundConstants.SENT_STATUS_WAIT, 1000);
			if (notifications == null || notifications.size() == 0) {
				TPLogger.getLogger().debug("Exiting mass email job");
				MassEmailSchedular.JOB_STATUS_BUZY = false;
				return;
			}
			InboxManager inboxManager = new InboxManager();
			InboxData inboxData = inboxManager.getCurrentInboxSettings();
			String globalStr = TemplateUtils.getConvertedGlobalVars(inboxData);
			TPMailSender sender = new TPMailSender(this);
			for (int i = 0; i < notifications.size(); i++) {
				OutBoundData outboundData = (OutBoundData) notifications.get(i);
				try {
					MassEmailManager massEmailManager = new MassEmailManager();
					MessageData messageData = massEmailManager.getMessageData(outboundData.getOutboundType(), outboundData.getEntityId(), globalStr, null);
					if (Utils.isBlankOrNull(outboundData.getSendFrom())) {
						messageData.setFrom(inboxData.getInboxDisplayName() + " <" + inboxData.getInboxEmail() + ">");
					} else {
						messageData.setFrom(outboundData.getSendFrom());
						messageData.setReplyTo(outboundData.getSendFrom());
						String from = Utils.replaceUserEmailWithInboxEmail(outboundData.getSendFrom(), inboxData.getInboxEmail());						
						messageData.setFrom(from);
					}

					messageData.setApplicantId(outboundData.getEntityId());
					messageData.setUserId(outboundData.getUserId());

					messageData.setOutboundId("" + outboundData.getOutboundId());
					try {
						// save message data to DB
						
						outBoundManager.setMessageData(outboundData.getOutboundId(), messageData);
						// send the messages
						sender.send(messageData, InboxConstants.EMAIL_BODY_HTML, null);
					} catch (Exception e) {
						TPLogger.getLogger().error("Error", e);
						updateOutBoundStatus(outboundData, OutBoundConstants.SENT_STATUS_FAIL);
					}
				} catch (Exception em) {
					TPLogger.getLogger().error("Error", em);
					updateOutBoundStatus(outboundData, OutBoundConstants.SENT_STATUS_FAIL);
				}
			}

		} catch (Exception e) {
			TPLogger.getLogger().error("Error while sending mass emails", e);
		}
		TPLogger.getLogger().debug("Exiting mass email job");
		MassEmailSchedular.JOB_STATUS_BUZY = false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.mail.event.TransportListener#messageDelivered(javax.mail.event.TransportEvent)
	 */
	public void messageDelivered(TransportEvent event) {
		// TODO Auto-generated method stub
		updateMessageStatusFor(event, OutBoundConstants.SENT_STATUS_SUCCESS);
	}

	private void updateMessageStatusFor(TransportEvent event, String status) {
		Message message = event.getMessage();
		String outboundIdStr = null;
		String messageId = null;
		int outBoundId = 0;
		try {

			Enumeration headers = message.getAllHeaders();
			while (headers.hasMoreElements()) {
				Header header = (Header) headers.nextElement();
				String name = header.getName();
				String value = header.getValue();
				if (name.equals("Outbound-Id"))
					outboundIdStr = value;
				else if (name.equalsIgnoreCase("Message-Id"))
					messageId = value;
			}

		} catch (MessagingException e) {
			TPLogger.getLogger().error("Error", e);
		}
		try {
			outBoundId = Integer.parseInt(outboundIdStr);
			OutBoundData outboundData = new OutBoundData();
			outboundData.setOutboundId(outBoundId);
			outboundData.setSentMessageId(messageId);
			updateOutBoundStatus(outboundData, status);
			if (status.equals(OutBoundConstants.SENT_STATUS_SUCCESS)) {
				OutBoundManager outBoundManager = new OutBoundManager();
				MessageData messageData = outBoundManager.getMessageData(outBoundId);
				Calendar cal = new GregorianCalendar();
				messageData.setSendDate(new java.sql.Date(new Timestamp(cal.getTime().getTime()).getTime()));
				InboxManager inboxManager = new InboxManager();
				inboxManager.saveCommunicationMessage(messageData, ApplicantConstants.APPLICANT_EMAIL_FOLDER_SENT, messageData.getUserId(),ApplicantConstants.EMAIL_NOT_IMPORTED);
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error", e);
		}

	}

	private void updateOutBoundStatus(OutBoundData outboundData, String status) {
		TPLogger.getLogger().debug("STATUS ====== "+status);
		outboundData.setSentStatus(status);
		OutBoundManager outBoundManager = new OutBoundManager();
		outBoundManager.update(outboundData);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.mail.event.TransportListener#messageNotDelivered(javax.mail.event.TransportEvent)
	 */
	public void messageNotDelivered(TransportEvent event) {
		// TODO Auto-generated method stub
		updateMessageStatusFor(event, OutBoundConstants.SENT_STATUS_FAIL);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.mail.event.TransportListener#messagePartiallyDelivered(javax.mail.event.TransportEvent)
	 */
	public void messagePartiallyDelivered(TransportEvent arg0) {
		// TODO Auto-generated method stub

	}

}
