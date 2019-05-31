/**
 * 
 */
package com.talentPool.scheduler;

import java.util.Properties;

import org.apache.log4j.Logger;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;

import com.talentPool.common.db.DBManager;
import com.talentPool.common.utils.PropsUtils;

/**
 * @author shivprasad
 * 
 * This is a singleton factory class. At any given time only one instance of
 * this class exist. To get instance of this class one should call
 * getTPDefaultSchedular() method
 * 
 */
public class TPDefaultScheduler {
	private static Logger log = Logger.getLogger("com.talentPool.calendar.scheduler");
	private static Scheduler scheduler = null;

	static {
		try {			
			Properties properties = getQuartzProperties();
			
			SchedulerFactory sf = new StdSchedulerFactory(properties);
			scheduler = sf.getScheduler();
			scheduler.addSchedulerListener(new SchedulerListenerImplementation());
			scheduler.addGlobalJobListener(new JobListenerImplementation());
			scheduler.addGlobalTriggerListener(new TriggerListenerImplementation());
		} catch (SchedulerException e) {
			log.error("e = ", e);
		} catch (Exception e) {
			log.error("e = ", e);
		}
	}

	/**
	 * At any time there is only one instance running
	 * 
	 * @return Schedular instance
	 * @throws SchedulerException
	 */
	public static Scheduler getDefaultScheduler() throws SchedulerException {
		if (scheduler == null) {
			SchedulerFactory sf = new StdSchedulerFactory();
			scheduler = sf.getScheduler();
		}
		return scheduler;
	}

	public static void startDefaultScheduler() throws SchedulerException {
		getDefaultScheduler();
		log.debug("start default schedualr");
		scheduler.start();
		log.debug("started default scheduler");
	}

	public static void shutDownDefaultScheduler(boolean wait) throws SchedulerException {
		log.debug("shut down default schedualr");
		if (scheduler != null) {
			scheduler.shutdown(wait);
		}
	}

	public static boolean isDefaultSchedularShutDown() {
		try {
			return scheduler.isShutdown();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return true;
	}
	
	public static Properties getQuartzProperties() throws Exception {
		PropsUtils propsUtils = new PropsUtils();
		Properties properties = propsUtils.load("quartz.properties");
		properties.put("org.quartz.dataSource.myDS.user", DBManager.getDBUsername());
		properties.put("org.quartz.dataSource.myDS.password", DBManager.getDBPassword());
		return properties;
	}
}
