/**
 * This class uses FetchEx for retriving emails from exchange server using webdav
 * require properties file fetchExc.properties is stored in config folder
 * 
 * 1. Got the source code from fetchExc and put in package Fetchexc and changed the package declaration first
 * 2. Chnaged the class name{ to public class name{
 * 3. Chnaged the fetchAll method to public
 * 4. Replaced all sysouts and system.err.println by TPLogger.getLogger().error
 * 5. Chnaged System.exit(1) to return in all fetchExc package
 * 6. Chnaged the path in fetchAll method to if (alreadyRunning(Utils.concatFilePath(TPApplicationProperties.getProperty("installation.path"), "fetchExc.lock"))) {
 * 7. Added method deleteFetchExcLock to delete lock file and called from TPEmailreceiver
 * 
 */
package com.talentPool.inbox.scheduler;

import java.io.File;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.properties.TPApplicationProperties;
import com.talentPool.common.utils.Utils;
import com.talentPool.fetchExc.fetchMail;
import com.talentPool.inbox.dataobject.InboxData;

/**
 * @author shivprasad
 * 
 */
public class TPExchangeEmailReceiver {
	public static String configPath = "";
	private InboxData inboxData=null;
	static {
		configPath = Utils.concatFilePath(TPApplicationProperties.getProperty("installation.path"), TPApplicationProperties.getProperty("config.dir"));
		configPath = Utils.concatFilePath(configPath, "fetchExc.properties");
	}
	public TPExchangeEmailReceiver(InboxData inboxData){
		this.inboxData=inboxData;
	}
	public void receiveEmails() {
		try {
			fetchMail fetchmail = new fetchMail(configPath, inboxData);
			TPLogger.getLogger().debug("START EXCANGE RECEIVER");
			fetchmail.fetchAll();
			TPLogger.getLogger().debug("END EXCANGE RECEIVER");
		} catch (Exception e) {
			TPLogger.getLogger().error("Error ",e);
		} finally {
			TPLogger.getLogger().debug("########## DELETING LOCK ####################");
			deleteFetchExcLock();
		}
	}

	private void deleteFetchExcLock() {
		try {
			File flagFile = new File(Utils.concatFilePath(TPApplicationProperties.getProperty("installation.path"), "fetchExc.lock"));
			flagFile.delete();
		} catch (Exception e) {
			TPLogger.getLogger().error(e);
		}

	}

	public static void main(String[] args) {
		//TPExchangeEmailReceiver x = new TPExchangeEmailReceiver();
		//x.receiveEmails();
	}
}
