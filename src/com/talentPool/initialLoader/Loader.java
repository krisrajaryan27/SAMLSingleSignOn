/**
 * 
 */
package com.talentPool.initialLoader;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.SessionCookieConfig;

import com.talentPool.applicant.scheduler.SourceLockinCheckerJobScheduler;
import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.Logger.scheduler.LogFileArchiveSchedular;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.properties.TPApplicationProperties;
import com.talentPool.customReports.scheduler.UpdateMasterTablesScheduler;
import com.talentPool.inbox.scheduler.InboxScheduler;
import com.talentPool.lookupTalentpool.scheduler.LookupTPShortlistCompareScheduler;
import com.talentPool.masters.scheduler.AsianPaintsMasterSchedular;
import com.talentPool.notifier.scheduler.EscalationEmailSchedular;
import com.talentPool.notifier.scheduler.MassEmailSchedular;
import com.talentPool.otherApplications.rest.scheduler.GetDataForGreytipScheduler;
import com.talentPool.otherApplications.rest.scheduler.PostDataFromGreytipScheduler;
import com.talentPool.otherApplications.scheduler.StepLevelChangeCSVScheduler;
import com.talentPool.parser.GateApp;
import com.talentPool.parser.utils.ParserUtils;
import com.talentPool.repository.IndexManager;
import com.talentPool.scheduler.TPDefaultScheduler;

/**
 * @author shivprasad
 * 
 *         This class will load when application starts
 * 
 *         This is used where the application needs to start some threads right
 *         at the begining
 * 
 *         In this class we are instantiating the email threads to be required
 *         to run when application starts and which will run as long as
 *         application is running
 */

public class Loader implements ServletContextListener {

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.
	 * ServletContextEvent)
	 */
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		try {
			TPDefaultScheduler.shutDownDefaultScheduler(true);
			TPLogger.getLogger().error("CLOSING INDEX WRITER AT CONTEXT CLOSE");
			ParserUtils.getIndexWriter().close();
			TPLogger.getLogger().debug("Conext is distroyed");
			System.gc();
		} catch (Exception e) {
			TPLogger.getLogger().error("Error when context is distroyed", e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.servlet.ServletContextListener#contextInitialized(javax.servlet
	 * .ServletContextEvent)
	 */
	public void contextInitialized(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		TPLogger.getLogger().debug("Conext is started");
		try {
			// start inbox mail receive thread
			InboxScheduler.resetAllReceiver();
		} catch (Exception e) {
			TPLogger.getLogger().fatal("Error while starting mail receive threads", e);
		}
		try {
			// start mass email thread
			MassEmailSchedular.triggerMassEmail();
		} catch (Exception e) {
			TPLogger.getLogger().fatal("Error while starting  mass email threads", e);
		}
		try {
			// start position approval escalation email
			EscalationEmailSchedular.triggerEscalationEmail();
		} catch (Exception e) {
			TPLogger.getLogger().fatal("Error while starting position approval escalation email threads", e);
		}
		try {
			// start log file archive thread
			LogFileArchiveSchedular.triggerLogFileArchive();
		} catch (Exception e) {
			TPLogger.getLogger().fatal("Error while starting log file archive threads", e);
		}

		try {
			// start source lock thread
			SourceLockinCheckerJobScheduler.resetTrigger();
		} catch (Exception e) {
			TPLogger.getLogger().fatal("Error while starting source lock  threads", e);
		}

		try {
			// index all documents when application starts start the scheduler
			String flag = TPApplicationProperties.getProperty("run.index.onstartup");
			if ("1".equalsIgnoreCase(flag)) {
				IndexManager.indexAllDocuments();
			}
			TPLogger.getLogger().debug("Starting Default Schedular");
			TPDefaultScheduler.startDefaultScheduler();
		} catch (Exception e) {
			TPLogger.getLogger().fatal("Error while starting default schedular", e);
		}

		try {
			// start report table update scheduler
			UpdateMasterTablesScheduler.triggerUpdateMasterTablesJob();
		} catch (Exception e) {
			TPLogger.getLogger().fatal("Error while starting master tables scheduler", e);
		}

		try {
			// Load GateApp for NLP Parser
			if ("1".equals(TPApplicationProperties.getProperty("use_nlp_parser"))) {
				GateApp.get();
			}
		} catch (Exception e) {
			TPLogger.getLogger().fatal(GlobalConstants.ERROR, e);
		}

		// start lookup DB Find shortlisted Candidate
		try {
			if ("1".equals(TPApplicationProperties.getProperty("is_lookup.db.enabled"))) {
				LookupTPShortlistCompareScheduler.triggerLookupTPShortlisted();
			}
		} catch (Exception e) {
			TPLogger.getLogger().fatal("Error while lookupDB Find shortlisted", e);
		}

		// start Scheduler for Hired Candidate
		try {
			if (!TPApplicationProperties.getProperty("is_step_level_csv_schedule").equals("0")) {
				StepLevelChangeCSVScheduler.triggerStepLevelChangeCSV();
			}
		} catch (Exception e) {
			TPLogger.getLogger().fatal("Error while lookupDB Find shortlisted", e);
		}

		// Greytip Scheduler
		try {
			if (!TPApplicationProperties.getProperty("is_greytip_integrate").equals("0")) {
				GetDataForGreytipScheduler.triggerGetDataForGreytip();
				PostDataFromGreytipScheduler.triggerPostDataFromGreytip();
			}
		} catch (Exception e) {
			TPLogger.getLogger().fatal("Error while Greytip Integration", e);
		}

		// Asian Paints Master Data Scheduler 
		/*try {
			// start Asian paints master data sync
			AsianPaintsMasterSchedular.triggerAsianPaintsMasterSync();
		} catch (Exception e) {
			TPLogger.getLogger().fatal("Error while starting asian paints master data sync scheduler", e);
		}*/

		ServletContext servletContext = arg0.getServletContext();
		SessionCookieConfig scc = servletContext.getSessionCookieConfig();
		if ("1".equals(TPApplicationProperties.getProperty("secure.cookie.flag"))) {
			scc.setSecure(true);
		} else {
			scc.setSecure(false);
		}
	}

}
