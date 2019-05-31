/**
 * 
 */
package com.talentPool.todo.utils;

import java.util.Calendar;

import com.talentPool.calendar.CalendarConstants;
import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.properties.TPLabels;
import com.talentPool.common.utils.Utils;
import com.talentPool.dashboard.constants.DashboardConstants;
import com.talentPool.positions.PositionConstants;
import com.talentPool.selectionProcess.dataobject.ActionRequiredData;
import com.talentPool.selectionProcess.dataobject.SelectionProcessData;


/**
 * @author Ajeet
 *
 */
public class ToDoUtils {

	public static ActionRequiredData getActionRequired(SelectionProcessData data) {
		ActionRequiredData actionRequiredData = new ActionRequiredData();
		try {
			boolean isSchedulable = ("" + PositionConstants.STEP_SCHEDULED).equals(data.getPositionStepIsscheduled()) ? true : false;

			actionRequiredData.setActionType(DashboardConstants.ACTION_REQUIRED_FEEDBACK);
			String action = TPLabels.getLabel("dashboard.label.todo.feedback") +" ";
			String dt = null;
			if (isSchedulable) {
				if (data.getAppointmentFromDate() == null) {
					action = TPLabels.getLabel("dashboard.label.todo.schedule") +" ";
					actionRequiredData.setActionType(DashboardConstants.ACTION_REQUIRED_SCHEDULE);
				} else if (data.getAppointmentFromDate().after(Calendar.getInstance().getTime())) {
					action = TPLabels.getLabel("dashboard.label.todo.conduct") +" ";
					dt = Utils.getDateConvertedToString(data.getAppointmentFromDate(), Utils.regDDMMMhhmmaFormat);
					actionRequiredData.setActionType(DashboardConstants.ACTION_ON_CONDUCT);
				} else {
					if (!data.getAppointmentStatus().equals(""+CalendarConstants.APPOINTMENT_STATUS_NOSHOW) && 
							!data.getAppointmentStatus().equals(""+CalendarConstants.APPOINTMENT_STATUS_HAPPENED) ) {
						action = TPLabels.getLabel("dashboard.label.todo.confirm") +" ";
						actionRequiredData.setActionType(DashboardConstants.ACTION_REQUIRED_CONFIRM_ATTENDANCE);
					}
				}
			}

			StringBuffer sb = new StringBuffer();

			if(Utils.isBlankOrNull(data.getResponsibleUsers())){
					action = " " + TPLabels.getLabel("dashboard.label.todo.onhold") +" ";
					dt = null;
					actionRequiredData.setActionType(DashboardConstants.ACTION_ON_HOLD);
					sb.append(action);
			}else{
				sb.append(action + data.getApplicantStep());	
			}
			
			if (dt != null) {
				sb.append(" on " + dt);
			}

			actionRequiredData.setActionRequired(sb.toString());

		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return actionRequiredData;
	}
}
