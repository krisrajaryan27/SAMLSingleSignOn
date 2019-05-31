/**
 * 
 */
package com.talentPool.selectionProcess;

import java.util.HashMap;

import com.talentPool.common.properties.TPLabels;

/**
 * @author pallavi
 * 
 */
public class SelectionProcessConstants {
	public static final String DEFAULT_FILTER_VALUE = "All";
	public static final String DEFAULT_FILTER_LABEL = "";

	public static final String MOVE_UP = "up";
	public static final String MOVE_DOWN = "down";
	
	public static final String STEP_REJECT = "0";
	// public static final String STEP_JOINED = "1";
	public static final String STEP_ON_HOLD = "-1"; // moved to on hold
	public static final String STEP_JOIN = "-2"; // moved to joined
	public static final String STEP_REPEAT = "-3"; // confirm attendance, not attended reschedule
	public static final String STEP_NOT_INTERESTED_REJECT = "-4"; // rejected before interview
	public static final String STEP_ATTENDED = "-5"; // confirm attendance/ attended
	public static final String STEP_NOT_ATTENDED = "-6"; // constant for not attended reject
	public static final String STEP_POSITION_CLOSED_REJECT = "-7"; // rejected because position is
																	// closed
	
	public static final String STEP_APPLICANT_RESPONSE = "-10";
	
	public static final String STEP_INVALID = "-1000";

	public static final String SEPARATOR = " / ";

	public static final String STEP_TITLE_SHORTLIST = "Shortlist";
	public static final String STEP_TITLE_REJECT = "Rejected";
	public static final String STEP_TITLE_ON_HOLD = "On Hold";
	public static final String STEP_TITLE_JOINED = "Joined";
	public static final String STEP_TITLE_RESCHEDULE = "Reschedule";
	public static final String STEP_TITLE_ATTENDED = "Attended";
	public static final String STEP_TITLE_NOT_ATTENDED = "Not Attended Reject";
	public static final String STEP_TITLE_POSITION_CLOSED_REJECT = "Position close reject";
	public static final String STEP_TITLE_BACK_OUT = "Back Out";
	
	public static final String TRAIT_ = "trait_";
	public static final String DATE_ = "date";
	public static final String COMMA = ", ";
	public static final String UNDERSCORE = "_";
	public static final String DOUBLE_UNDERSCORE = "__";
	public static final String SINGLE_PIPE = "|";
	public static final String COMMENT = "comment";
	public static final String APPLICANT_JOINED = "1";
	public static final String APPLICANT_NOT_JOINED = "0";
	public static final String RATING_ = "rating_";
	public static final String RATINGDESC_ = "ratingdesc_";
	public static final String MULTIPLE_SELECT_ = "multiple_select_";
	public static final String MULTIPLE_SELECTDESC_ = "multiple_selectdesc_";

	// Following are interaction constants
	public static final int INTERACTION_IMPORT = -1;
	public static final int INTERACTION_EMAIL_RECEIVED = 1;
	public static final int INTERACTION_EMAIL_SENT = 2;
	public static final int INTERACTION_APPOINTMENTS = 3;
	public static final int INTERACTION_INTERVIEW = 4;
	public static final int INTERACTION_MESSAGE = 5;
	// 5 to 9 are reserved for future use
	public static final int INTERACTION_PHONE = 10;
	public static final int INTERACTION_NOTE = 11;
	public static final int INTERACTION_STATUS_MESSAGE = 12;
	public static final int INTERACTION_SMS = 13;
	public static final int INTERACTION_SHORTLISTED = 14;
	public static final int INTERACTION_APPLICANT_RESPONSE = 15;
	
	/**
	 *This interaction is deprecated and will be removed in future releases.
	 *Use {@link SelectionProcessConstants}{@link #INTERACTION_OFFER_SHEET_GENERATION} instead 
	 */
	@Deprecated
	public static final int INTERACTION_OFFER_DETAILS_MODIFIED = 15;
	public static final int INTERACTION_BLACKLISTED = 16;
	public static final int INTERACTION_UNBLACKLISTED = 17;
	
	public static final int INTERACTION_OFFER_PROPOSAL = 18;
	public static final int INTERACTION_OFFER_SHEET_GENERATION = 19;
	
	public static final String INTERACTION_HIDE = "1";
	public static final String INTERACTION_UNHIDE = "0";

	public static HashMap<String, String> INTERACTION_TYPES = new HashMap<>(6);
	static {
		INTERACTION_TYPES.put("" + INTERACTION_EMAIL_RECEIVED, TPLabels.getLabel("interaction.label.type.emailReceived"));
		INTERACTION_TYPES.put("" + INTERACTION_EMAIL_SENT, TPLabels.getLabel("interaction.label.type.emailSent"));
		INTERACTION_TYPES.put("" + INTERACTION_APPOINTMENTS, TPLabels.getLabel("interaction.label.type.appointment"));
		INTERACTION_TYPES.put("" + INTERACTION_INTERVIEW, TPLabels.getLabel("interaction.label.type.interview"));
		INTERACTION_TYPES.put("" + INTERACTION_PHONE, TPLabels.getLabel("interaction.label.type.phone"));
		INTERACTION_TYPES.put("" + INTERACTION_NOTE, TPLabels.getLabel("interaction.label.type.note"));
		INTERACTION_TYPES.put("" + INTERACTION_MESSAGE, TPLabels.getLabel("interaction.label.type.message"));
		INTERACTION_TYPES.put("" + INTERACTION_STATUS_MESSAGE, TPLabels.getLabel("interaction.label.type.status"));
		INTERACTION_TYPES.put("" + INTERACTION_SMS, TPLabels.getLabel("interaction.label.type.sms"));
		INTERACTION_TYPES.put("" + INTERACTION_IMPORT, TPLabels.getLabel("interaction.label.type.import"));
		INTERACTION_TYPES.put("" + INTERACTION_SHORTLISTED, TPLabels.getLabel("interaction.label.type.shortlist"));
		INTERACTION_TYPES.put("" + INTERACTION_OFFER_DETAILS_MODIFIED, TPLabels.getLabel("hire.note.offer_details_modified"));
		INTERACTION_TYPES.put("" + INTERACTION_BLACKLISTED, TPLabels.getLabel("black_list.note.blackList",new Object[]{TPLabels.getLabel("common.applicant")}));
		INTERACTION_TYPES.put("" + INTERACTION_UNBLACKLISTED, TPLabels.getLabel("black_list.note.unBlackList",new Object[]{TPLabels.getLabel("common.applicant")}));
		INTERACTION_TYPES.put("" + INTERACTION_UNBLACKLISTED, TPLabels.getLabel("black_list.note.unBlackList",new Object[]{TPLabels.getLabel("common.applicant")}));
		INTERACTION_TYPES.put("" + INTERACTION_OFFER_PROPOSAL, TPLabels.getLabel("offer_proposal.text.interaction_mesage"));
		INTERACTION_TYPES.put("" + INTERACTION_OFFER_SHEET_GENERATION, TPLabels.getLabel("generate_offer_sheet.text.offer_generated_modified"));
	}

	public static final int SHOW_REPEAT_STEP = 1;
	public static final int HIDE_REPEAT_STEP = 0;

	public static final int LATEST_ACTIVITY_DURATION_IN_DAYS = 7;

	public static final String PHONE_VALID = "0";
	public static final String PHONE_INVALID = "1";

	public static final String STATUS_SYSTEM_GENERATED = "1";
	public static final String STATUS_USER_GENERATED = "0";

	public static final String NOT_INTERESTED_REJECT = "0";
	public static final String CONFIRM_APPOINTMENT = "1";
	public static final String ENTER_FEEDBACK = "2";
	public static final String MOVE_APPLICANT = "3";
	
	public static final String REPORT_TYPE_CONSOLIDATED = "c";
	public static final String REPORT_TYPE_USERWISE = "u";
	
	// Following constants are user for filter screen in left panel
	public static final String FILTER_DEPARTMENT = "1";
	public static final String FILTER_POSITION = "2";
	public static final String FILTER_STEP = "3";
	public static final String FILTER_APPLICANT = "4";
	public static final String FILTER_LOCATION = "5";
	public static final String FILTER_ACTION = "6";
	public static final String FILTER_USER = "7";
	public static final String FILTER_SOURCE="8";
	public static final String FILTER_SELECTION_STAGE = "9";
	public static final String FILTER_POSITION_TYPE = "10";
	
	
	public static final String SCREEN_TYPE_FWD="FWD"; //forward to next step
	public static final String SCREEN_TYPE_NIR="NIR"; //not interested reject
	public static final String SCREEN_TYPE_CAT="CAT"; //confirm attendance
	public static final String SCREEN_TYPE_SCH="SCH"; //schedule interview
	public static final String SCREEN_TYPE_HLD="HLD"; //on hold
	public static final String SCREEN_TYPE_POS="POS"; //move to other position
	
	public static final String DECISION_APPROVED="x";
	public static final String DECISION_POSITION="p";
	public static final String DECISION_SCHEDULE="s";
	
	public static final String TAB_SELECT = "select";
	public static final String TAB_HIRE = "accept";
	
	
	public static final int MAX_FLAGS_TO_SHOW = 2;
	
	public static final String SUMMARY_FEEDBACK = "SUM_F";
	public static final String DETAILED_FEEDBACK = "DET_F";
	
}
