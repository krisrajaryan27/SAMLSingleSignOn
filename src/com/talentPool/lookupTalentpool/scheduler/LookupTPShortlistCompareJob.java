
/**
 * 
 */
package com.talentPool.lookupTalentpool.scheduler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.talentPool.applicant.manager.ApplicantManager;
import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.db.SimpleDataObject;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.utils.Utils;
import com.talentPool.inbox.InboxConstants;
import com.talentPool.inbox.TPMailSender;
import com.talentPool.inbox.dataobject.InboxData;
import com.talentPool.inbox.dataobject.MessageData;
import com.talentPool.inbox.exception.SendMailException;
import com.talentPool.inbox.manager.InboxManager;
import com.talentPool.lookupTalentpool.manager.LookupTPManager;
import com.talentPool.lookupTalentpool.properties.LookupTPConstant;

/**
 * @author Shantanu
 *
 */

public class LookupTPShortlistCompareJob implements Job{

	public void execute(JobExecutionContext context) throws JobExecutionException {
		TPLogger.getLogger().debug("************ SHORTLIST COMPARE JOB STARTED ************");
		
		if (LookupTPShortlistCompareScheduler.JOB_STATUS_BUZY) {
			TPLogger.getLogger().debug("SHORTLIST COMPARE JOB ALREADY RUNNING. EXITING SHORTLIST COMPARE JOB");
			return;
		}
		LookupTPShortlistCompareScheduler.JOB_STATUS_BUZY = true;
		
		try{			
			LookupTPManager lookupTPManager = new LookupTPManager();
			//Get shortlisted applicant to be checked for duplication at lookup
			List shortlistList = lookupTPManager.getApplicantListForLookup();
			
			ApplicantManager applicantManager = new ApplicantManager();	

			if(shortlistList.size()>0){			
				for(int i=0;i<shortlistList.size();i++){
					SimpleDataObject sdo = (SimpleDataObject) shortlistList.get(i);
					String stepLevel = postApplicantsForLookupDuplication(sdo);//will return some data
					TPLogger.getLogger().debug("IN JOB STEP LEVEL" + stepLevel);
					if(!Utils.isBlankOrNull( stepLevel)){
						String flagId = lookupTPManager.getFlagIdForLookupStepLevel(stepLevel);
						applicantManager.setApplicantFlags(sdo.getString("applicantId"),flagId,"1");
						lookupTPManager.updateApplicantListCheckedForLookup(sdo.getString("applicantId"), flagId);//update the candidate after check at lookup
						if("1".equals(LookupTPConstant.NOTIFY_SHORTLISTING_USER) | "1".equals(LookupTPConstant.NOTIFY_CURRENT_USER) | "1".equals(LookupTPConstant.NOTIFY_DEFAULT_USER) ){
							sendDuplicateShortlistMailNotification(sdo,stepLevel);//the return data will be mailed to concern users
						}
					}else{
						lookupTPManager.updateApplicantListCheckedForLookup(sdo.getString("applicantId"),"0");
					}					
				}
			}
		}catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		TPLogger.getLogger().debug("************ SHORTLIST COMPARE JOB FINISHED ************");
		LookupTPShortlistCompareScheduler.JOB_STATUS_BUZY = false;
	}
	/*
	 * Send mail to concern manager and user stating the duplication parameter 
	 */
	
	private void sendDuplicateShortlistMailNotification(SimpleDataObject sdo, String stepLevel){
		TPLogger.getLogger().debug("========== SENDING MAIL Lookup TP Shortlisted============");
		LookupTPManager lookupTPManager = new LookupTPManager(); 
		InboxManager inboxManager = new InboxManager();
		try{
			ArrayList<SimpleDataObject> dupApp = lookupTPManager.duplicateApplicantLookupData(sdo.getString("applicantId"));
			SimpleDataObject sdoNew = new SimpleDataObject();
			for(int i = 0;i<dupApp.size();i++){
				sdoNew = dupApp.get(i); 
			}		
			
			InboxData inboxData = inboxManager.getCurrentInboxSettings();
			MessageData messageData = new MessageData();
			messageData.setFrom(inboxData.getInboxDisplayName() + " <" + inboxData.getInboxEmail() + ">");
			messageData.setHtmlBody(getBodyData(sdoNew,stepLevel));
			messageData.setSubject(LookupTPConstant.MAIL_SUBJECT);
			messageData.setTo(getSendTo(sdoNew));
			TPMailSender sender = new TPMailSender();
			sender.send(messageData, InboxConstants.EMAIL_BODY_HTML, null);
		}catch (SendMailException sme) {
			TPLogger.getLogger().error("Error",sme);
		}catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR,e);
		}
	}
	
	private String getBodyData(SimpleDataObject sdo,String stepLevel) {
		StringBuffer sb = new StringBuffer();
		try {			
			sb.append(LookupTPConstant.MAIL_BODY_LINE1 + sdo.getString("applicantName") + "<br/><br/>");
			sb.append(LookupTPConstant.MAIL_BODY_LINE2 + sdo.getString("positionTitle") + "<br/><br/>");
			if(stepLevel.equals(LookupTPConstant.LOOKUP_TP_LEVEL_SHORTLIST)){
				sb.append(LookupTPConstant.MAIL_BODY_LINE3 + " Shortlisted at "+ LookupTPConstant.LOOKUP_ORGANIZATION + "<br/><br/>");
			}else if(stepLevel.equals(LookupTPConstant.LOOKUP_TP_LEVEL_SELECT)){
				sb.append(LookupTPConstant.MAIL_BODY_LINE3 + " Selection Stage at "+ LookupTPConstant.LOOKUP_ORGANIZATION + "<br/><br/>");
			}else if(stepLevel.equals(LookupTPConstant.LOOKUP_TP_LEVEL_ACCEPT)){
				sb.append(LookupTPConstant.MAIL_BODY_LINE3 + " Hire Stage at "+ LookupTPConstant.LOOKUP_ORGANIZATION+ "<br/><br/>");
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return sb.toString();
	}
	
	private String getSendTo(SimpleDataObject sdo) {
		String sendTo = null; 
		LookupTPManager ltm = new LookupTPManager();
		try{
			String responsibleUsers = ltm.responsibleUserEmails(sdo.getString("applicantId"));
			
			sendTo = "1".equals(LookupTPConstant.NOTIFY_SHORTLISTING_USER)?sdo.getString("userEmail"):"";
			
			if("1".equals(LookupTPConstant.NOTIFY_CURRENT_USER)){
				if(!Utils.isBlankOrNull(responsibleUsers)){
					sendTo += Utils.isBlankOrNull(sendTo)?"":",";
					sendTo += responsibleUsers;	
				}				
			}
			
			if("1".equals(LookupTPConstant.NOTIFY_DEFAULT_USER)){
				if(!Utils.isBlankOrNull(LookupTPConstant.LOOKUP_DUPLICATE_NOTIFICATION_SENDTO)){
					sendTo += Utils.isBlankOrNull(sendTo)?"":",";
					sendTo += LookupTPConstant.LOOKUP_DUPLICATE_NOTIFICATION_SENDTO;
				}
			}		
			
		}catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR,e);
		}
		return sendTo;
	}

	/*
	 * This method will post data to the other server for duplication check
	 * And will return the data who are duplicate.
	 */
	public String postApplicantsForLookupDuplication(SimpleDataObject sdo)throws UnsupportedEncodingException, IOException{
		//String data = null;
		String stepLevel = null;

		try{
			TPLogger.getLogger().debug("--------- IN JOB : POSTING DATA TO LOOKUP TP STARTS HERE-------");
			// Construct data		    
		    String data = URLEncoder.encode("applicantName", "UTF-8") + "=" + URLEncoder.encode(Utils.isBlankOrNull(sdo.getString("applicantName"))?"":sdo.getString("applicantName"), "UTF-8");
		    data += "&" + URLEncoder.encode("applicantEmail1", "UTF-8") + "=" + URLEncoder.encode(Utils.isBlankOrNull(sdo.getString("applicantEmail1"))?"":sdo.getString("applicantEmail1"), "UTF-8");
		    data += "&" + URLEncoder.encode("applicantEmail2", "UTF-8") + "=" + URLEncoder.encode(Utils.isBlankOrNull(sdo.getString("applicantEmail2"))?"":sdo.getString("applicantEmail2"), "UTF-8");
		    data += "&" + URLEncoder.encode("applicantCellPhone", "UTF-8") + "=" + URLEncoder.encode(Utils.isBlankOrNull(sdo.getString("applicantCellPhone"))?"":sdo.getString("applicantCellPhone"), "UTF-8");
		    data += "&" + URLEncoder.encode("applicantHomePhone", "UTF-8") + "=" + URLEncoder.encode(Utils.isBlankOrNull(sdo.getString("applicantHomePhone"))?"":sdo.getString("applicantHomePhone"), "UTF-8");
		    data += "&" + URLEncoder.encode("applicantWorkPhone", "UTF-8") + "=" + URLEncoder.encode(Utils.isBlankOrNull(sdo.getString("applicantWorkPhone"))?"":sdo.getString("applicantWorkPhone"), "UTF-8");
		       
		    
		    // Send data
		    TPLogger.getLogger().debug("--------- IN JOB : REQUEST DATA Starts Here-------"+data);
		    URL url = new URL(LookupTPConstant.LOOKUP_URL);
		    URLConnection conn = url.openConnection();
		    conn.setDoOutput(true);
		    OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
		    wr.write(data);
		    wr.flush();

	    	// Get the response
		    TPLogger.getLogger().debug("---------IN JOB : RESPONSE DATA STARTS-------");
	    	BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	    	
	    	stepLevel = rd.readLine();
	    	
	    	stepLevel=Character.toString(stepLevel.charAt(0));

	    	wr.close();
	    	rd.close();
	    	
		}catch (Exception e) {
			TPLogger.getLogger().debug("---------CONNECTION REFUSED-------");
			TPLogger.getLogger().error(GlobalConstants.ERROR,e);
			return null;
		}
		return stepLevel;
	}

}