package com.talentPool.budget.scheduler;

import java.util.HashMap;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.talentPool.budget.dataobject.BudgetItem;
import com.talentPool.budget.manager.BudgetManager;
import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.db.SimpleDataObject;
import com.talentPool.inbox.InboxConstants;
import com.talentPool.inbox.TPMailSender;
import com.talentPool.inbox.dataobject.InboxData;
import com.talentPool.inbox.dataobject.MessageData;
import com.talentPool.inbox.manager.InboxManager;
import com.talentPool.notifier.TemplateConstants;
import com.talentPool.notifier.dataobject.TemplateData;
import com.talentPool.notifier.manager.TemplateManager;
import com.talentPool.notifier.manager.VelocityManager;
import com.talentPool.notifier.utils.TemplateUtils;
import com.talentPool.positions.dataobject.PositionData;
import com.talentPool.positions.manager.PositionManager;
import com.talentPool.user.dataobject.LoginData;
import com.talentPool.user.manager.LoginManager;

public class BudgetOwnerNotficationJob implements Job{
	private static Logger log = TPLogger.getLogger();
	
	public void execute(JobExecutionContext context) throws JobExecutionException {
		log.debug("Sending email notification when budget item changes");
		LoginManager loginManager = null;
		BudgetManager budgetManager = null;
		try {
			loginManager = new LoginManager();
			budgetManager = new BudgetManager();
			String budgetItemId = context.getTrigger().getJobDataMap().getString("budgetItemId");
			String userId = context.getTrigger().getJobDataMap().getString("userId");
			String templateTypeId = context.getTrigger().getJobDataMap().getString("templateTypeId");
			String transferredHeads = context.getTrigger().getJobDataMap().getString("transferredHeads");
			String positionId = context.getTrigger().getJobDataMap().getString("positionId");
			
			TemplateManager templateManager = new TemplateManager();
			TemplateData templateData = templateManager.getTemplateDataByTemplateTypeId(templateTypeId);
			String contentVM = templateData.getTemplateContentFile();
			String subjectVM = templateData.getTemplateSubjectFile();
			String keyMap = templateData.getTemplateVariables();
			
			LoginData userData = loginManager.getUser(userId);		
			String contentStr = TemplateUtils.getConvertedUserData(userData);
			
			String transferredHeadsToken = TemplateUtils.getConstructedToken("BUDGET_TRANSFERRED_HEADS", transferredHeads);
			contentStr = TemplateUtils.appndToToken(contentStr, transferredHeadsToken);
			
			if (TemplateUtils.isTypeExist(templateData.getTemplateVariableIds(), TemplateConstants.TEMPLATE_VAR_TYPE_POSITIONS)) {
				String positionStr = templateManager.getPositionStr(positionId,true);
				contentStr = TemplateUtils.appndToToken(contentStr, positionStr);
			}

			BudgetItem budgetItem = budgetManager.getBudgetItemToView(budgetItemId);
			String budgetItemData = TemplateUtils.getConvertedBudgetItemData(budgetItem);
			contentStr = TemplateUtils.appndToToken(contentStr, budgetItemData);

			HashMap keyValMap = TemplateUtils.getKeyValueMap(keyMap, contentStr);
			VelocityManager velocityManager = new VelocityManager();
			String subject = velocityManager.handle(subjectVM, keyValMap);
			String content = velocityManager.handle(contentVM, keyValMap);

			InboxManager inboxManager = new InboxManager();
			InboxData inboxData = inboxManager.getCurrentInboxSettings();
			
			MessageData messageData = new MessageData();
			messageData.setFrom(inboxData.getInboxDisplayName() + " <" + inboxData.getInboxEmail() + ">");
			messageData.setReadStatus(InboxConstants.INBOX_EMAIL_STATUS_UNREAD);
			messageData.setHtmlBody(content);
			messageData.setSubject(subject);
			messageData.setTo(userData.getEmail());
			System.out.println(content);
			TPMailSender sender = new TPMailSender();
			sender.send(messageData, InboxConstants.EMAIL_BODY_HTML, null);
		} catch (Exception e) {
			log.error("error while sending notification when budget item changes", e);
		}

	}
}
