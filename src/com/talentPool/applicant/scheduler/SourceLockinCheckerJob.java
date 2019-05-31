/**
 * 
 */
package com.talentPool.applicant.scheduler;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.talentPool.applicant.manager.SourceLockinCheckerJobManager;
import com.talentPool.common.Logger.TPLogger;

/**
 * @author shivprasad
 * 
 */
public class SourceLockinCheckerJob implements Job {
	public void execute(JobExecutionContext context) throws JobExecutionException {
		try {
			TPLogger.getLogger().debug("Start Source lockin checker job");
			SourceLockinCheckerJobManager sourceLockinCheckerJobManager = new SourceLockinCheckerJobManager();
			sourceLockinCheckerJobManager.changeLockinExpiredSources();
			TPLogger.getLogger().debug("End Source lockin checker job");
		} catch (Exception e) {
			TPLogger.getLogger().error(e);
		}

	}
}
