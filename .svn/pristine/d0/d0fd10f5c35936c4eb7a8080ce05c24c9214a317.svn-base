package com.talentPool.masters.scheduler;

import javax.mail.event.TransportEvent;
import javax.mail.event.TransportListener;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.masters.action.MasterActionForAP;
import com.talentPool.positions.action.PositionActionForAP;

/**
 * @author ArvindKhatik
 *
 */
public class AsianPaintsMasterJob implements Job, TransportListener {

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		TPLogger.getLogger().debug("Start Asian Paints Master Job");
		if (AsianPaintsMasterSchedular.JOB_STATUS_BUZY) {
			TPLogger.getLogger().debug("Asian Paints Master is already running. Exiting Asian Paints Master job");
			return;
		}
		AsianPaintsMasterSchedular.JOB_STATUS_BUZY = true;
		try {
			MasterActionForAP.syncMasterDataForAP();
			PositionActionForAP.syncPositionDataForAP();
		} catch (Exception e) {
			TPLogger.getLogger().error("Error Asian Paints Master Job", e);
		}
		TPLogger.getLogger().debug("End Asian Paints Master Job");
		AsianPaintsMasterSchedular.JOB_STATUS_BUZY = false;

	}

	public AsianPaintsMasterJob() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void messageDelivered(TransportEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void messageNotDelivered(TransportEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void messagePartiallyDelivered(TransportEvent arg0) {
		// TODO Auto-generated method stub

	}

}
