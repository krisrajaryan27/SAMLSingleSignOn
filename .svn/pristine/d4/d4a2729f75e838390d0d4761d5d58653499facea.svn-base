package com.talentPool.inbox.scheduler;

import java.util.ArrayList;

import com.talentPool.common.utils.Utils;
import com.talentPool.inbox.InboxConstants;
import com.talentPool.inbox.dataobject.AutoImportData;
import com.talentPool.inbox.dataobject.MessageData;
import com.talentPool.inbox.manager.AutoImportManager;
import com.talentPool.inbox.utils.InboxUtils;
import com.talentPool.parser.converter.HTMLToPlainTextConverter;

public class AutoImportTest {
	public static void main(String[] args) {
		AutoImportManager autoImportManager = new AutoImportManager();
		InboxUtils inboxUtils = new InboxUtils();
		ArrayList emails = autoImportManager.getEmailsToAutoImport(InboxConstants.FORMAT_AUTO_IMPORT, false);
		for (int i = 0; i < emails.size(); i++) {
			MessageData messageData = (MessageData) emails.get(i);
			try {
				String content = messageData.getTextBody();
				if (Utils.isBlankOrNull(content)) {
					content = messageData.getHtmlBody();
					HTMLToPlainTextConverter converter = new HTMLToPlainTextConverter();
					content = converter.convertText(content);
				}
				AutoImportData autoImportData = inboxUtils.getAutoImportData(content);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
}
