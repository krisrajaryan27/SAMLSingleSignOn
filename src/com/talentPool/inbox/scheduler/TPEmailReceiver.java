package com.talentPool.inbox.scheduler;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.Properties;
import java.util.TimeZone;

import javax.mail.Address;
import javax.mail.FetchProfile;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.URLName;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeUtility;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.properties.GlobalApplicationProperties;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.properties.POPProperties;
import com.talentPool.common.properties.TPLabels;
import com.talentPool.common.utils.FileHandler;
import com.talentPool.common.utils.Utils;
import com.talentPool.documents.DocumentConstants;
import com.talentPool.documents.utils.DocumentUtils;
import com.talentPool.exchangeWebService.FetchExchangeEmail;
import com.talentPool.inbox.InboxConstants;
import com.talentPool.inbox.dataobject.AttachmentData;
import com.talentPool.inbox.dataobject.InboxData;
import com.talentPool.inbox.dataobject.MessageData;
import com.talentPool.inbox.manager.InboxManager;
import com.talentPool.inbox.utils.CharSetUtils;
import com.talentPool.inbox.utils.InboxUtils;
import com.talentPool.notifier.CandidateStateTemplateEnums;
import com.talentPool.scheduler.TPDefaultScheduler;
import com.talentPool.user.manager.ModuleSet;

public class TPEmailReceiver {

	private String protocol;
	private String host = null;
	private String user = null;
	private String password = null;
	private String mbox = null;
	private String url = null;
	private int port = -1;
	private boolean debug = false;
	private Store store = null;
	private int inboxId = 0;
	private InboxData inboxData=null;	
	/**
	 * Create new Receiver using inboxData
	 * 
	 * @param inboxData
	 */
	public TPEmailReceiver(InboxData inboxData) {
		try {
			/*TimeZone tzone = TimeZone.getTimeZone("Asia/Calcutta");
			TimeZone.setDefault(tzone);*/
			this.inboxData = inboxData;
			this.protocol = InboxConstants.PROTOCOL_POP3;
			if (InboxConstants.SERVER_TYPE_IMAP.equals("" + inboxData.getInboxServerType())) {
				if (InboxConstants.SSL_ENABLED.equals("" + inboxData.getInboxIncomingSSLEnabled())) {
					this.protocol = InboxConstants.PROTOCOL_SECURE_IMAP;
				} else {
					this.protocol = InboxConstants.PROTOCOL_IMAP;
				}
			} else if (InboxConstants.SERVER_TYPE_EXCHANGE.equals("" + inboxData.getInboxServerType())) {
				this.protocol = InboxConstants.PROTOCOL_EXCHANGE;//				
			} else if (InboxConstants.SSL_ENABLED.equals("" + inboxData.getInboxIncomingSSLEnabled())) {
				this.protocol = InboxConstants.PROTOCOL_SECURE_POP3;
			}

			this.host = inboxData.getPopHost();
			this.user = inboxData.getUserName();
			this.password = inboxData.getPassword();
			this.inboxId = inboxData.getInboxId();
			this.port = Integer.parseInt(inboxData.getInboxIncomingPort());
			
		} catch (Exception e) {
			TPLogger.getLogger().error(e);
		}
	}

	/**
	 * Connects to the specified store and return connected store object
	 * 
	 * @return
	 * @throws Exception
	 */
	public Store openStore() throws Exception {
		Properties props = System.getProperties();
		props.setProperty("mail.mime.base64.ignoreerrors", "true");
		// load extra properties if any
		Properties extraProperties = POPProperties.getMailProperties();
		if (extraProperties != null) {
			Enumeration enumx = extraProperties.keys();
			while (enumx.hasMoreElements()) {
				String key = (String) (enumx.nextElement());
				props.put(key, extraProperties.get(key));
			}
		}
		// Get a Session object
		Session session = Session.getInstance(props, null);
		session.setDebug(debug);

		// Get Store Object
		if (url != null) {
			URLName urln = new URLName(url);
			store = session.getStore(urln);
			store.connect();
		} else {
			if (protocol != null)
				store = session.getStore(protocol);
			else
				store = session.getStore();

			if (host != null || user != null || password != null)
				store.connect(host, port, user, password);
			else
				store.connect();
		}
		return store;

	}

	public void closeStore() throws Exception {
		store.close();
	}

	public Folder openFolder() throws Exception {
		Folder folder = store.getDefaultFolder();
		if (folder == null) {
			throw new Exception("No Default Folder");
		}

		if (mbox == null)
			mbox = "INBOX";
		folder = folder.getFolder(mbox);
		if (folder == null) {
			throw new Exception("Invalid folder");
		}

		// try to open read/write and if that fails try read-only
		try {
			folder.open(Folder.READ_WRITE);
		} catch (MessagingException ex) {
			TPLogger.getLogger().error("Error while opening pop", ex);
			folder.open(Folder.READ_ONLY);
		}
		return folder;
	}

	public void getAllMessages(Folder folder) throws Exception {
		Message[] msgs = folder.getMessages();
		// Use a suitable FetchProfile
		FetchProfile fp = new FetchProfile();
		fp.add(FetchProfile.Item.ENVELOPE);
		fp.add(FetchProfile.Item.FLAGS);
		fp.add("X-Mailer");
		folder.fetch(msgs, fp);
		for (int i = 0; i < msgs.length; i++) {
			try {
				InboxUtils inboxUtils = new InboxUtils();
				InboxManager inboxManager = new InboxManager();
				TPLogger.getLogger().debug("--------------------------");
				TPLogger.getLogger().debug("MESSAGE #" + (i + 1) + ":");
				/* Create empty message object to store message received */
				MessageData msg = new MessageData();

				if (msgs[i] instanceof Message) {
					dumpEnvelope(msgs[i], msg);
				}
				dumpPart(msgs[i], msg);
				// First Save the message to DB then set it's flag to Deleted
				msg.setAutoImportFormat(InboxConstants.FORMAT_NOAUTO_IMPORT);
				if (ModuleSet.isMODULE_WEB_INTEGRATION() || ModuleSet.isMODULE_VENDOR() || ModuleSet.isMODULE_EMPLOYEE()) {
					if (inboxUtils.isAutoImportFormat(msg.getTextBody())) {
						msg.setAutoImportFormat(InboxConstants.FORMAT_AUTO_IMPORT);
					}
				}
				inboxUtils.convertDocAttachments(msg, DocumentConstants.documentsPath);
				if (TPDefaultScheduler.isDefaultSchedularShutDown()) {
					break;
				}
				TPLogger.getLogger().debug("SAVING EMAIL WITH ATTACHMENT TO DB");
				inboxManager.saveMessage(msg, InboxConstants.INBOX_FOLDER_INBOX, null);
				TPLogger.getLogger().debug("SETTING FLAG AS DELETED");
				msgs[i].setFlag(Flags.Flag.DELETED, true);
				if (msg.getAutoImportFormat() == InboxConstants.FORMAT_AUTO_IMPORT) {
					AutoImportScheduler.addTrigger();
				}

				// check and add auto reply trigger
				if (GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_SEND_AUTO_REPLY_EMAIL).equals(GlobalConstants.ENABLED)) {
					AutoReplyScheduler.addTrigger(msg.getFrom(),CandidateStateTemplateEnums.ADDED,null);
				}
			} catch (Exception e) {
				TPLogger.getLogger().error("Error while fetching message", e);
			}
			try {
				// delete the message irrespective of weather u got error or not
				// msgs[i].setFlag(Flags.Flag.DELETED, true);
			} catch (Exception e) {
				TPLogger.getLogger().error("Error while deleting message", e);
			}
		}
	}

//	public void getAllMyMessages(Folder folder) throws Exception {
//		Message[] msgs = folder.getMessages();
//		// Use a suitable FetchProfile
//		FetchProfile fp = new FetchProfile();
//		fp.add(FetchProfile.Item.ENVELOPE);
//		fp.add(FetchProfile.Item.FLAGS);
//		fp.add("X-Mailer");
//		folder.fetch(msgs, fp);
//		for (int i = 0; i < msgs.length; i++) {
//			try {
//				InboxUtils inboxUtils = new InboxUtils();
//				InboxManager inboxManager = new InboxManager();
//				TPLogger.getLogger().debug("--------------------------");
//				TPLogger.getLogger().debug("MESSAGE #" + (i + 1) + ":");
//				/* Create empty message object to store message received */
//				MessageData msg = new MessageData();
//
//				if (msgs[i] instanceof Message) {
//					dumpEnvelope(msgs[i], msg);
//				}
//				dumpPart(msgs[i], msg);
//				// First Save the message to DB then set it's flag to Deleted
//				msg.setAutoImportFormat(InboxConstants.FORMAT_NOAUTO_IMPORT);
//				if (ModuleSet.isMODULE_WEB_INTEGRATION() || ModuleSet.isMODULE_VENDOR() || ModuleSet.isMODULE_EMPLOYEE()) {
//					if (inboxUtils.isAutoImportFormat(msg.getTextBody())) {
//						msg.setAutoImportFormat(InboxConstants.FORMAT_AUTO_IMPORT);
//					}
//				}
//				inboxUtils.convertDocAttachments(msg, DocumentConstants.documentsPath);
//				if (TPDefaultScheduler.isDefaultSchedularShutDown()) {
//					break;
//				}
//				TPLogger.getLogger().debug("SAVING EMAIL WITH ATTACHMENT TO DB");
//				// inboxManager.saveMessage(msg, InboxConstants.INBOX_FOLDER_INBOX, null);
//				// TPLogger.getLogger().debug("SETTING FLAG AS DELETED");
//				// //msgs[i].setFlag(Flags.Flag.DELETED, true);
//				// if (msg.getAutoImportFormat() == InboxConstants.FORMAT_AUTO_IMPORT) {
//				// AutoImportScheduler.addTrigger();
//				// }
//				//
//				// // check and add auto reply trigger
//				// if
//				// (GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_SEND_AUTO_REPLY_EMAIL).equals(GlobalConstants.ENABLED))
//				// {
//				// AutoReplyScheduler.addTrigger(msg.getFrom());
//				// }
//			} catch (Exception e) {
//				TPLogger.getLogger().error("Error while fetching message", e);
//			}
//			try {
//				// delete the message irrespective of weather u got error or not
//				// msgs[i].setFlag(Flags.Flag.DELETED, true);
//			} catch (Exception e) {
//				TPLogger.getLogger().error("Error while deleting message", e);
//			}
//		}
//	}

	public void dumpPart(Part p, MessageData msg) throws Exception {
		String disposition = p.getDisposition();
		boolean saveAsAttachement = false;
		if (disposition == null || p.isMimeType("multipart/*") || (disposition.equalsIgnoreCase(Part.INLINE) && Utils.isBlankOrNull(p.getFileName()))) {
			/*
			 * IF CONTENT DISPOSITION IS NULL MEANS THE MESSAGE IS EITHER BODYPART, MULTIPART OR
			 * ATTACHMENT
			 */
			if (p.isMimeType("text/plain")) {
				// If textbody is null then keep this in text body
				if (msg.getTextBody() == null) {
					String msgTxt = CharSetUtils.getContentIfNonSupportedCharset(p);
					if (msgTxt == null) {
						msgTxt = (String) p.getContent();
					}
					msg.setTextBody(msgTxt);
				} else {
					TPLogger.getLogger().debug("SECOND PART FOR text/plain MIME: save as attachment");
					saveAsAttachement = true;
				}
			} else if (p.isMimeType("text/html")) {
				// If html body is null then set the html body of the message
				if (msg.getHtmlBody() == null) {
					String msgTxt = CharSetUtils.getContentIfNonSupportedCharset(p);
					if (msgTxt == null) {
						msgTxt = (String) p.getContent();
					}
					msg.setHtmlBody(msgTxt);
				} else {
					TPLogger.getLogger().debug("SECOND PART FOR text/html MIME: save as attachment");
					saveAsAttachement = true;
				}
			} else if (p.isMimeType("multipart/*")) {
				Multipart mp = (Multipart) p.getContent();
				int count = mp.getCount();
				for (int i = 0; i < count; i++)
					dumpPart(mp.getBodyPart(i), msg);
			} else if (p.isMimeType("message/rfc822")) {
				dumpPart((Part) p.getContent(), msg);
			} else {

				/*
				 * If we actually want to see the data, and it's not a MIME type we know, fetch it
				 * and check its Java type. Don't understand MIMEType ? save as attachment if not
				 * string
				 */
				Object o = p.getContent();
				if (o instanceof String) {
					// This is a string, set text body if not set yet
					if (msg.getTextBody() == null) {
						msg.setTextBody((String) o);
					} else {
						// save attachment
						saveAsAttachement = true;
					}

				} else if (o instanceof InputStream) {
					// This is just an input stream
					// save attachment
					saveAsAttachement = true;
				} else {
					// save attachment
					saveAsAttachement = true;
				}
				TPLogger.getLogger().debug("UNKNOWN MIME TYPE: save as attachment");
			}

		} else if (disposition.equalsIgnoreCase(Part.ATTACHMENT)) {
			
			TPLogger.getLogger().debug("Attachment: " + p.getFileName() + " : " + p.getContentType());
			// Save this file
			saveAsAttachement = true;
		} else if (disposition.equalsIgnoreCase(Part.INLINE)) {
			TPLogger.getLogger().debug("Inline File: " + p.getFileName() + " : " + p.getContentType());
			// Save this file
			saveAsAttachement = true;
		}

		if (saveAsAttachement) {
			// Save this attachment
			String originalFileName = p.getFileName();

			// See If folder exists or not and create if not
			String fileExt = "";
			if (!Utils.isBlankOrNull(originalFileName)) {
				originalFileName = MimeUtility.decodeText(originalFileName);
				if (originalFileName.indexOf(".") > 0) {
					fileExt = originalFileName.substring(originalFileName.lastIndexOf("."));
				}
			}
			// Create attachment directory if not created
			FileHandler fileHandler = new FileHandler();
			fileHandler.createDirectory(DocumentConstants.documentsPath);

			// Create Directory structure in attachments
			Calendar cal = new GregorianCalendar();

			String monthFolder = cal.get(Calendar.YEAR) + ((cal.get(Calendar.MONTH) + 1 < 10) ? "0" + (cal.get(Calendar.MONTH) + 1) : "" + (cal.get(Calendar.MONTH) + 1));
			String dayFolder = cal.get(Calendar.DAY_OF_MONTH) < 10 ? "0" + cal.get(Calendar.DAY_OF_MONTH) : "" + cal.get(Calendar.DAY_OF_MONTH);

			String monthFolderPath = Utils.concatFilePath(DocumentConstants.documentsPath, monthFolder);
			String dayFolderPath = Utils.concatFilePath(monthFolderPath, dayFolder);
			fileHandler.createDirectory(monthFolderPath);
			fileHandler.createDirectory(dayFolderPath);

			// Create temporary file in dayPolderPath
			String filename = File.createTempFile("ATT", fileExt, new File(dayFolderPath)).getName();
			try {
				String attachmentFilePath = Utils.concatFilePath(dayFolderPath, filename);
				File f = new File(attachmentFilePath);
				if (!f.exists()) {
					TPLogger.getLogger().error("Duplicate attachment file name");
					throw new IOException("Duplicate attachment file name");
				}
				try {
					if (fileHandler.commonFileTypeValidator(originalFileName)) {
						((MimeBodyPart) p).saveFile(f);
						if (!DocumentUtils.isValidMimeType(attachmentFilePath)) {
							if (f.exists()){
								f.delete();
							}
							f = new File(attachmentFilePath + ".txt");
							filename = filename+".txt";
							FileWriter fw = new FileWriter(f);
							fw.write(TPLabels.getLabel("mail_attachment.error_content"));
							TPLogger.getLogger().error(TPLabels.getLabel("mail_attachment.error_content"));
							fw.close();
						}
					} else {
						if (f.exists()){
							f.delete();
						}
						f = new File(attachmentFilePath + ".txt");
						filename = filename+".txt";
						FileWriter fw = new FileWriter(f);
						fw.write(TPLabels.getLabel("mail_attachment.error_ext"));
						TPLogger.getLogger().error(TPLabels.getLabel("mail_attachment.error_ext"));
						fw.close();
					}
				} catch (ClassCastException e) {
					if (f.exists()){
						f.delete();
					}
					f = new File(attachmentFilePath + ".txt");
					filename = filename+".txt";
					FileOutputStream out = new FileOutputStream(f);
					InputStream in = p.getInputStream();
					int bytesRead = 0;
					byte[] buffer = new byte[8192];
					if ((bytesRead = in.read(buffer, 0, 8192)) != -1) {
						if (DocumentUtils.isValidMimeTypeBuffer(buffer)) {
							out.write(buffer, 0, bytesRead);
							bytesRead = 0;
							while ((bytesRead = in.read(buffer, 0, 8192)) != -1) {
								out.write(buffer, 0, bytesRead);
							}
						} else {
							buffer = TPLabels.getLabel("mail_attachment.error_content").getBytes();
							TPLogger.getLogger().error(TPLabels.getLabel("mail_attachment.error_content"));
							out.write(buffer);
						}
					}
				}
				if (Utils.isBlankOrNull(originalFileName)) {
					originalFileName = filename;
				}

				String relativePath = Utils.concatFilePath(monthFolder, dayFolder);
				AttachmentData attachment = new AttachmentData(Utils.concatFilePath(relativePath, filename), originalFileName);

				try {
					attachment.setAttachmentSize(f.length());
				} catch (Exception e) {
					TPLogger.getLogger().debug("UNABLE TO GET ATTACHMENT SIZE", e);
				}

				// Set the content type of the attachment
				String contentType = "";
				try {
					contentType = p.getContentType();
				} catch (Exception e) {
					TPLogger.getLogger().debug("UNABLE TO GET CONTENT TYPE", e);
				}
				attachment.setContentType(contentType);

				// Get Content-ID header for attachment
				String[] cIds = p.getHeader("Content-ID");
				String contentId = "";
				if (cIds != null) {
					contentId = cIds[0];
					contentId = contentId.replaceAll("<", "");
					contentId = contentId.replaceAll(">", "");
				}
				attachment.setContentId(contentId);

				// Decide Attachment Type
				attachment.setAttachmentType(Utils.isBlankOrNull(contentId) ? InboxConstants.ATTACHMENT_TYPE_NOTRELATED : InboxConstants.ATTACHMENT_TYPE_RELATED);

				ArrayList tmpAttachments = msg.getAttachments();
				if (tmpAttachments == null) {
					tmpAttachments = new ArrayList();
				}
				tmpAttachments.add(attachment);
				msg.setAttachments(tmpAttachments);

			} catch (IOException ex) {
				TPLogger.getLogger().debug("Failed to save attachment: " + ex);
				// ex.printStackTrace();
				throw ex;
			}
		}

	}
	public void dumpEnvelope(Message m, MessageData msg) throws Exception {
		TPLogger.getLogger().debug("This is the message envelope");
		TPLogger.getLogger().debug("---------------------------");
		Address[] a;
		// FROM
		if ((a = m.getFrom()) != null) {
			for (int j = 0; j < a.length; j++){
				msg.setFrom(a[j].toString());
				msg.setFromAddress(Utils.getEmailAddress(a[j].toString()));
			}
		}
		// TO
		if ((a = m.getRecipients(Message.RecipientType.TO)) != null) {
			StringBuffer to = new StringBuffer();
			for (int j = 0; j < a.length; j++) {
				to.append(a[j].toString() + "; ");

				InternetAddress ia = (InternetAddress) a[j];
				if (ia.isGroup()) {
					InternetAddress[] aa = ia.getGroup(false);
					for (int k = 0; k < aa.length; k++)
						to.append(aa[k].toString() + "; ");
				}

			}
			String msgTo = to.toString();
			if (msgTo.length() > 0) {
				msgTo = msgTo.substring(0, msgTo.length() - 2);
			}
			msg.setTo(msgTo);
		}

		// CC
		if ((a = m.getRecipients(Message.RecipientType.CC)) != null) {
			StringBuffer to = new StringBuffer();
			for (int j = 0; j < a.length; j++) {
				to.append(a[j].toString() + "; ");

				InternetAddress ia = (InternetAddress) a[j];
				if (ia.isGroup()) {
					InternetAddress[] aa = ia.getGroup(false);
					for (int k = 0; k < aa.length; k++)
						to.append(aa[k].toString() + "; ");
				}

			}
			String msgTo = to.toString();
			if (msgTo.length() > 0) {
				msgTo = msgTo.substring(0, msgTo.length() - 2);
			}
			msg.setCc(msgTo);
		}
		// BCC
		if ((a = m.getRecipients(Message.RecipientType.BCC)) != null) {
			StringBuffer to = new StringBuffer();
			for (int j = 0; j < a.length; j++) {
				to.append(a[j].toString() + "; ");

				InternetAddress ia = (InternetAddress) a[j];
				if (ia.isGroup()) {
					InternetAddress[] aa = ia.getGroup(false);
					for (int k = 0; k < aa.length; k++)
						to.append(aa[k].toString() + "; ");
				}

			}
			String msgTo = to.toString();
			if (msgTo.length() > 0) {
				msgTo = msgTo.substring(0, msgTo.length() - 2);
			}
			msg.setBcc(msgTo);
		}

		// SUBJECT
		msg.setSubject(m.getSubject());
		if (m.getSize() > 0) {
			msg.setSize(m.getSize());
		} else {
			msg.setSize(0);
		}

		// DATE
		Date d = m.getSentDate();
		// String sendDate = (d != null ? d.toString() : null);
		msg.setSendDate(getFormattedDate(d));

		d = m.getReceivedDate();
		if (d != null) {
			msg.setReceivedDate(getFormattedDate(d));
		} else {
			Calendar cal = new GregorianCalendar();
			msg.setReceivedDate(new java.sql.Date(new Timestamp(cal.getTime().getTime()).getTime()));
		}

	}

	public java.sql.Date getFormattedDate(Date dt) {
		java.sql.Date sdt = null;
		try {
			sdt = new java.sql.Date(dt.getTime());
		} catch (Exception e) {
			TPLogger.getLogger().debug("Error While Converting Date to SQLDATE: set to null", e);
			sdt = null;
		}
		return sdt;
	}

	

	public void fetchAllMessages() {
		try {
			TPLogger.getLogger().debug("####################### START RECEIVING ########################");
			if (this.protocol.equals(InboxConstants.PROTOCOL_EXCHANGE)) {
				TPLogger.getLogger().debug("########## GETTING MAILS FROM EXCHANGE ####################");
				FetchExchangeEmail fee = new FetchExchangeEmail(this.inboxData);
				fee.receiveEmails();
				TPLogger.getLogger().debug("########## FINISHED MAILS FROM EXCHANGE ####################");
			}else{
				openStore();
				Folder inbox = openFolder();
				getAllMessages(inbox);
				/*
				 *If flag is true all the changed made to message are saved.
				 */
				inbox.close(true);
				closeStore();
			}
			
			TPLogger.getLogger().debug("####################### EXIT RECEIVING ########################");
		} catch (Exception e) {
			TPLogger.getLogger().debug("Opps Caught Excepton !!! ", e);
		}
	}
}