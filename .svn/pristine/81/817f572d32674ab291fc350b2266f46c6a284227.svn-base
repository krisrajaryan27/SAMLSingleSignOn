package com.talentPool.common.Logger.scheduler;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.talentPool.common.Logger.TPLogger;

public class LogFileArchiveJob implements Job {

	private static Logger log = TPLogger.getLogger();

	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		TPLogger.getLogger().debug("Start Log File Archive trigger");
		if (LogFileArchiveSchedular.JOB_STATUS_BUZY) {
			TPLogger
					.getLogger()
					.debug(
							"log file archieve is already running. Exiting mass email job");
			return;
		}
		LogFileArchiveSchedular.JOB_STATUS_BUZY = true;
		// get emails to sent
		try {
			File zipFile = new File("");
			try {

				String zipFolderPath = context.getTrigger().getJobDataMap()
						.getString("zipFolderPath");
				String logFolderPath = context.getTrigger().getJobDataMap()
						.getString("logFolderPath");
				String logFileDateFormat = context.getTrigger().getJobDataMap()
						.getString("logFileDateFormat");
				String zipFileDateFormat = context.getTrigger().getJobDataMap()
						.getString("zipFileDateFormat");
				String archiveLogFileNames = context.getTrigger()
						.getJobDataMap().getString("archiveLogFileNames");
				int BUFFER = 1024;

				ToBeZippedFilesFilter fileFilter = new ToBeZippedFilesFilter(7,
						logFileDateFormat, archiveLogFileNames);

				File file = new File(logFolderPath);
				File[] list = file.listFiles(fileFilter);

				Calendar thresholdDate = fileFilter.getThresholdDate();
				thresholdDate.add(Calendar.DATE, -1);
				String toDate = new SimpleDateFormat(zipFileDateFormat)
						.format(thresholdDate.getTime());
				String fromDate = new SimpleDateFormat(zipFileDateFormat)
						.format(fileFilter.getStartingDate().getTime());
				zipFile = new File(zipFolderPath, fromDate + "-" + toDate
						+ ".zip");
				if (zipFile.exists()) {
					zipFile.delete();
				}
				zipFile.createNewFile();
				FileOutputStream dest = new FileOutputStream(zipFile);

				ZipOutputStream out = new ZipOutputStream(
						new BufferedOutputStream(dest));

				BufferedInputStream origin = null;

				byte data[] = new byte[BUFFER];

				for (int j = 0; j < list.length; j++) {
					FileInputStream fi = new FileInputStream(list[j]);
					origin = new BufferedInputStream(fi, BUFFER);

					ZipEntry entry = new ZipEntry(list[j].getName());
					out.putNextEntry(entry);
					int count;
					while ((count = origin.read(data, 0, BUFFER)) != -1) {
						out.write(data, 0, count);
					}
					origin.close();
				}
				out.close();
				log.debug("Created Zip File: " + zipFile.getName());
				for (int i = 0; i < list.length; i++) {
					list[i].delete();
					log.debug("Deleted Log File: " + list[i].getName());
				}

				ArrayList<File> filesToBeRemoved = findZipFilesToBeRemoved(
						zipFolderPath, zipFileDateFormat);

				for (File file2 : filesToBeRemoved) {
					file2.delete();
					log.debug("Deleted Zip File: " + file2.getName());
				}

			} catch (Exception e) {

			}

		} catch (Exception e) {
			log.error("Error while sending mass emails", e);
		}
		log.debug("Exiting mass email job");
		LogFileArchiveSchedular.JOB_STATUS_BUZY = false;
	}

	private ArrayList<File> findZipFilesToBeRemoved(String zipFolderPath,
			String zipFileDateFormat) {
		File[] files = new File(zipFolderPath).listFiles();
		SimpleDateFormat format = new SimpleDateFormat(zipFileDateFormat);
		ArrayList<File> filesToBeRemoved = new ArrayList<File>();
		for (int i = 0; i < files.length; i++) {
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.DATE, -31);
			Date date = null;
			try {
				date = format.parse(files[i].getName().substring(
						1 + files[i].getName().lastIndexOf("-"),
						files[i].getName().lastIndexOf(".")));
			} catch (ParseException e) {
			}
			if (date.before(calendar.getTime())) {

				filesToBeRemoved.add(files[i]);
			}
		}

		return filesToBeRemoved;

	}
}
