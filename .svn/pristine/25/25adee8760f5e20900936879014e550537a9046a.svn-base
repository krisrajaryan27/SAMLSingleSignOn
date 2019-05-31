/**
 * 
 */
package com.talentPool.exchangeWebService;

import java.io.File;
import java.io.FileWriter;
import java.net.URI;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;

import microsoft.exchange.webservices.data.AffectedTaskOccurrence;
import microsoft.exchange.webservices.data.Attachment;
import microsoft.exchange.webservices.data.AttachmentCollection;
import microsoft.exchange.webservices.data.DeleteMode;
import microsoft.exchange.webservices.data.EmailAddress;
import microsoft.exchange.webservices.data.EmailAddressCollection;
import microsoft.exchange.webservices.data.EmailMessage;
import microsoft.exchange.webservices.data.ExchangeCredentials;
import microsoft.exchange.webservices.data.ExchangeService;
import microsoft.exchange.webservices.data.ExchangeVersion;
import microsoft.exchange.webservices.data.FileAttachment;
import microsoft.exchange.webservices.data.FindItemsResults;
import microsoft.exchange.webservices.data.Folder;
import microsoft.exchange.webservices.data.Item;
import microsoft.exchange.webservices.data.ItemId;
import microsoft.exchange.webservices.data.ItemView;
import microsoft.exchange.webservices.data.PropertySet;
import microsoft.exchange.webservices.data.SendCancellationsMode;
import microsoft.exchange.webservices.data.WebCredentials;
import microsoft.exchange.webservices.data.WellKnownFolderName;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.properties.GlobalApplicationProperties;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.properties.TPApplicationProperties;
import com.talentPool.common.properties.TPLabels;
import com.talentPool.common.utils.FileHandler;
import com.talentPool.common.utils.Utils;
import com.talentPool.documents.DocumentConstants;
import com.talentPool.documents.utils.DocumentUtils;
import com.talentPool.inbox.InboxConstants;
import com.talentPool.inbox.dataobject.AttachmentData;
import com.talentPool.inbox.dataobject.InboxData;
import com.talentPool.inbox.dataobject.MessageData;
import com.talentPool.inbox.manager.InboxManager;
import com.talentPool.inbox.scheduler.AutoImportScheduler;
import com.talentPool.inbox.scheduler.AutoReplyScheduler;
import com.talentPool.inbox.utils.InboxUtils;
import com.talentPool.notifier.CandidateStateTemplateEnums;
import com.talentPool.scheduler.TPDefaultScheduler;
import com.talentPool.user.manager.ModuleSet;

/**
 * @author Shantanu
 *
 */
public class FetchExchangeEmail {
	public static String installationPath = TPApplicationProperties.getProperty("installation.path");
	private InboxData inboxData=null;
	
	public FetchExchangeEmail(InboxData inboxData){
		this.inboxData=inboxData;
	}
	
	/**
	 * 
	 */
	public void receiveEmails() {
		try{
			TPLogger.getLogger().debug("########## Inside Receive Emails ####################");
			ExchangeService service = null;
			if(inboxData.getExchangeServerVersion().equals(InboxConstants.EXCHANGE_VERSION_2010_SP1)){
				service = new ExchangeService(ExchangeVersion.Exchange2010_SP1);
			}else{
				service = new ExchangeService(ExchangeVersion.Exchange2007_SP1);
			}
			service.setUrl(new URI("https://"+inboxData.getExchangeServerName()+"/ews/Exchange.asmx"));
			ExchangeCredentials credentials = new WebCredentials(inboxData.getUserName(),inboxData.getPassword(),inboxData.getDomainName());
			service.setCredentials(credentials);
			readMail(service);
		}catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR,e);
		}
	}
	
	/**
	 * 
	 * @param service
	 */
	public void readMail(ExchangeService service){		
		try{
			
			Folder folder = Folder.bind( service, WellKnownFolderName.Inbox );
			FindItemsResults<Item> results = service.findItems(folder.getId(), new ItemView( 100 ) );
		
			ArrayList<ItemId> itemsToDelete = new ArrayList<ItemId>();
			InboxUtils inboxUtils = new InboxUtils();
			int i =1;
			for (Item item : results){
				TPLogger.getLogger().debug("--------------------------");
				TPLogger.getLogger().debug("MESSAGE #" + (i++ ) + ":");
				InboxManager inboxManager = new InboxManager();
				Item itm = Item.bind(service, item.getId(), PropertySet.FirstClassProperties);
				EmailMessage emailMessage = EmailMessage.bind(service, itm.getId());
				MessageData messageData = new MessageData();
				
				messageData.setSubject(Utils.isBlankOrNull(emailMessage.getSubject())?"":emailMessage.getSubject());
				if(Utils.isBlankOrNull(emailMessage.getFrom().getName())){
					messageData.setFrom(emailMessage.getFrom().getAddress());
				}else{
					messageData.setFrom(emailMessage.getFrom().getName()+" <"+emailMessage.getFrom().getAddress()+">");
				}
				
				messageData.setFromAddress(emailMessage.getFrom().getAddress());
				messageData.setTo(getEmailAddress(emailMessage.getToRecipients()));
				messageData.setCc(getEmailAddress(emailMessage.getCcRecipients()));
				messageData.setBcc(getEmailAddress(emailMessage.getBccRecipients()));
				
				Date dateTimeCreated = convertToLocalDate(emailMessage.getDateTimeCreated());				
				messageData.setSendDate(Utils.convertDateToSQLDate(dateTimeCreated));
				
				Date dateTimeRecieved = convertToLocalDate(emailMessage.getDateTimeReceived());
				messageData.setReceivedDate(Utils.convertDateToSQLDate(dateTimeRecieved));
				
				messageData.setSize(emailMessage.getSize());
				messageData.setFolderId(Integer.parseInt( InboxConstants.INBOX_FOLDER_INBOX));
				
				if("html".equalsIgnoreCase(emailMessage.getBody().getBodyType().toString())){
					messageData.setHtmlBody(emailMessage.getBody().toString());
				}else{
					messageData.setTextBody(emailMessage.getBody().toString());
				}
				
				
				messageData.setAttachments(downloadAttachment(emailMessage.getAttachments()));
				
				// First Save the message to DB then set it's flag to Deleted
				messageData.setAutoImportFormat(InboxConstants.FORMAT_NOAUTO_IMPORT);
				if (ModuleSet.isMODULE_WEB_INTEGRATION() || ModuleSet.isMODULE_VENDOR() || ModuleSet.isMODULE_EMPLOYEE()) {
					if (inboxUtils.isAutoImportFormat(messageData.getTextBody())) {
						messageData.setAutoImportFormat(InboxConstants.FORMAT_AUTO_IMPORT);
					}
				}
				
				inboxUtils.convertDocAttachments(messageData, DocumentConstants.documentsPath);
				if (TPDefaultScheduler.isDefaultSchedularShutDown()) {
					break;
				}
			
				inboxManager.saveMessage(messageData,InboxConstants.INBOX_FOLDER_INBOX,null);				
				/*DELETE emails*/
				itemsToDelete.add(item.getId());
				
				if(messageData.getAutoImportFormat() == InboxConstants.FORMAT_AUTO_IMPORT) {
					AutoImportScheduler.addTrigger();
				}
				
				// check and add auto reply trigger
				if (GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_SEND_AUTO_REPLY_EMAIL).equals(GlobalConstants.ENABLED)) {
					AutoReplyScheduler.addTrigger(messageData.getFromAddress(),CandidateStateTemplateEnums.ADDED,null);
				}
				
				TPLogger.getLogger().debug("--------------------------");
			}
			if(!itemsToDelete.isEmpty()){
				deleteInboxEmails(service,itemsToDelete);
			}
		}catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR,e);
		}
	}
	
	
	/**
	 * 
	 * @param service
	 * @param ItemsToDelete
	 */
	public void deleteInboxEmails(ExchangeService service,ArrayList<ItemId> ItemsToDelete)	{
		try{
			service.deleteItems(ItemsToDelete, DeleteMode.SoftDelete, SendCancellationsMode.SendToNone, AffectedTaskOccurrence.AllOccurrences);
		}catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR,e);
		}
	}
	
	/**
	 * Comma separated email address
	 * @param eadd
	 * @return
	 */
	public String getEmailAddress(EmailAddressCollection eadd){
		String emailAddress = "";
		try{			
			Iterator<EmailAddress> itr = eadd.iterator();
			while(itr.hasNext()){										
				emailAddress+=itr.next().toString();
				if(itr.hasNext()){
					emailAddress+=",";
				}
			}
		}catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR,e);
		}
		return emailAddress;
	}
		
	/**
	 *  
	 * @param attcoll
	 * @return
	 */
	public ArrayList<AttachmentData> downloadAttachment(AttachmentCollection attcoll){
		ArrayList<AttachmentData> attNames = new ArrayList<AttachmentData>();
		try{
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
			
			String relativePath = Utils.concatFilePath(monthFolder, dayFolder);
			
			Iterator<Attachment> itr = attcoll.iterator();
			while(itr.hasNext()){	
				if(itr != null){
					FileAttachment fatt=(FileAttachment)itr.next();
					String originalFileName = fatt.getName();
					String ext = fatt.getName().substring(fatt.getName().lastIndexOf("."));
					File filename = File.createTempFile("ATT", ext, new File(dayFolderPath));
					if (fileHandler.commonFileTypeValidator(originalFileName)) {
						fatt.load(dayFolderPath + "/" + filename.getName());
						if (!DocumentUtils.isValidMimeType(dayFolderPath + "/" + filename.getName())) {
							if (filename.exists()){
								filename.delete();
							}
							filename = new File(dayFolderPath + "/" + filename.getName() + ".txt");
							FileWriter fw = new FileWriter(filename);
							fw.write(TPLabels.getLabel("mail_attachment.error_content"));
							TPLogger.getLogger().error(TPLabels.getLabel("mail_attachment.error_content"));
							fw.close();
						}
					} else {
						if (filename.exists()){
							filename.delete();
						}
						filename = new File(dayFolderPath + "/" + filename.getName() + ".txt");
						FileWriter fw = new FileWriter(filename);
						fw.write(TPLabels.getLabel("mail_attachment.error_ext"));
						TPLogger.getLogger().error(TPLabels.getLabel("mail_attachment.error_ext"));
						fw.close();
					}
					File f = new File(dayFolderPath+"/"+filename.getName());
					AttachmentData attData = new AttachmentData(relativePath+"/"+f.getName(),originalFileName);					
						
					attData.setContentId(fatt.getContentId());
					
					String contentType="";
					try{
						contentType = fatt.getContentType();
					}catch (Exception e) {
						TPLogger.getLogger().error(GlobalConstants.ERROR,e);
					}					
					attData.setContentType(contentType);
					
					attData.setAttachmentType("1");//need to change.
					attData.setAttachmentSize(f.length());										
					attNames.add(attData);
				}
			}			
		}catch (ClassCastException cce) {
			TPLogger.getLogger().error(GlobalConstants.ERROR,cce);
		}catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR,e);
		}
		return attNames;
	}
	
	public Date convertToLocalDate(Date nonLocalDate){
		Date localDate=null;		
		try{			
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(nonLocalDate);						
			calendar.add(Calendar.MILLISECOND, calendar.get(Calendar.ZONE_OFFSET));
			localDate= calendar.getTime();
		}catch (Exception pe) {
			TPLogger.getLogger().debug(GlobalConstants.ERROR,pe);
		}
		return localDate;
	}	
}