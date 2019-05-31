/**
 * 
 */
package com.talentPool.calendar.manager;

import java.util.ArrayList;
import java.util.Date;

import com.talentPool.calendar.CalendarConstants;
import com.talentPool.calendar.scheduler.AppointmentNotificationScheduler;
import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.db.DBPreparedQuery;
import com.talentPool.common.db.DBTransaction;
import com.talentPool.common.properties.GlobalApplicationProperties;
import com.talentPool.common.properties.GlobalConstants;

/**
 * @author shivprasad
 * 
 */
public class AppointmentNotificationManager {
	/**
	 * @param appointmentId
	 *            as unique id for interview/appointment
	 * @param subject
	 *            as subject of the appointment
	 * @param fromDate
	 *            as start date
	 * @param toDate
	 *            as end date
	 * @param userId
	 *            as owner/creater of appointment
	 * @param applicantId
	 *            as applicant of the appointment
	 * @param positionId
	 *            as id of the position to be interviewed
	 * @param stepId
	 *            as selection step to be interviewed
	 * @param attendee
	 *            as comma separated list of attendees
	 * @param fromDateNew
	 *            as new modified start date, pass null if create or delete appointment
	 * @param toDateNew
	 *            as new modified end date, pass null if create or delete appointment
	 * @param attendeeNew
	 *            as new comma separated list of attendees
	 * @param notifyType
	 *            as ADD/EDIT/DELETE
	 * @param notifyProcessed
	 *            as flag weather new or already processed
	 * @param dateCreated
	 *            as date of creation of appointment
	 * @param dateModified
	 *            as date appointment is modified
	 */
	public void insertNotification(String appointmentId, String subject, String fromDate, String toDate, String userId, String applicantId, String positionId, String stepId, String attendee,
			String fromDateNew, String toDateNew, String attendeeNew, int notifyType, int notifyProcessed, Date dateCreated, Date dateModified, String remindMe, String remindAttendee,String interviewMode,String detailsInterviewMode,
			DBTransaction tran) {
		boolean commitRequired = false;
		int rMe = 0;
		int rAttendee = 0;
		try {
			rMe = Integer.parseInt(remindMe);
		} catch (Exception e) {
			rMe = 0;
		}
		try {
			rAttendee = Integer.parseInt(remindAttendee);
		} catch (Exception e) {
			rAttendee = 0;
		}
		DBPreparedQuery dq = null;

		try {
			if (GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_SEND_APPOINTMENT).equals(GlobalConstants.DISABLED)) {
				TPLogger.getLogger().info("send appointment is disabled");
				return;
			}
			if (tran == null) {
				tran = new DBTransaction();
				commitRequired = true;
			}
			dq = new DBPreparedQuery("dAppNotification_InsertNotification", tran);
			dq.setId(1, appointmentId);
			dq.setString(2, subject);
			dq.setString(3, fromDate);
			dq.setString(4, toDate);
			dq.setId(5, userId);
			dq.setId(6, applicantId);
			dq.setId(7, positionId);
			dq.setId(8, stepId);
			dq.setString(9, attendee);
			dq.setString(10, fromDateNew);
			dq.setString(11, toDateNew);
			dq.setString(12, attendeeNew);
			dq.setInt(13, notifyType);
			dq.setInt(14, notifyProcessed);
			dq.setTimestamp(15, new java.sql.Timestamp(dateCreated.getTime()));
			dq.setTimestamp(16, new java.sql.Timestamp(dateModified.getTime()));
			dq.setInt(17, rMe);
			dq.setInt(18, rAttendee);
			dq.setString(19, interviewMode);
			dq.setString(20, detailsInterviewMode);
			dq.execute();
			dq = new DBPreparedQuery("dFetchLastInsertID", tran);
			String notificationId = dq.getIdResult();
			dq = new DBPreparedQuery("dAppNotification_UpdateSequence", tran);
			dq.setId(1, appointmentId);
			dq.setId(2, notificationId);
			dq.setId(3, notificationId);
			dq.execute();
			if (commitRequired) {
				tran.commit();
			}
			AppointmentNotificationScheduler.addTrigger();
		} catch (Exception e) {
			try {
				if (commitRequired) {
					tran.rollback();
				}
			} catch (Exception ex) {
				TPLogger.getLogger().error("Error in rollback", e);
			}
		} finally {
			if (dq != null) {
				if (commitRequired) {
					dq.releaseTransaction(tran);
				} else {
					dq.closeOpenCursors();
				}

			}
		}
	}

	public ArrayList getNotificationsToSend() {
		ArrayList notifications = null;
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dAppNotification_FetchNotifications");
			dq.setInt(1, CalendarConstants.NOTIFICATION_NOT_PROCESSED);
			notifications = dq.getResult();
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while fetching notifications to send", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return notifications;

	}

	public void updateNotificationStatus(String notificationId, int status) {
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dAppNotification_UpdateStatus");
			dq.setInt(1, status);
			dq.setId(2, notificationId);
			dq.execute();
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while updating notification status", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
	}
}
