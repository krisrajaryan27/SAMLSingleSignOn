/**
 * 
 */
package com.talentPool.search;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.talentPool.common.properties.TPLabels;
import com.talentPool.common.utils.CommonUtils;

/**
 * @author Dhakane
 * 
 */
public class SearchConstants {	
	public static final String SEARCH_ALL = "0";
	public static final String SEARCH_INPROCESS = "1";
	public static final String SEARCH_NOT_INPROCESS = "2";
	public static final String SEARCH_EXCLUDE_EMPLOYEE = "3";
	public static final String SEARCH_BLACKLISTED = "4";
	public static final String SEARCH_APPLIED_STAGE="5";
	
	public static final String SORT_BY_SCORE = "1";
	public static final String SORT_BY_IMPORT_DATE = "2";
	public static final String SORT_BY_EXP = "3";
	public static final String SORT_BY_NAME = "4";
	
	public static final String OR="0";
	public static final String AND="1";
	
	public static final String MATCH_ALL="1";
	public static final String MATCH_ANY="2";
	public static final String MATCH_EXACT="3";
	public static final String MATCH_ID="4";
	public static final String MATCH_EMP_ID="5";
	
	public static final String CRITERIA_NOT_SPECIFIED = "[NS]";

	public static final String CRITERIA_EQUAL_TO = "1";
	public static final String CRITERIA_GREATER_THAN = "2";
	public static final String CRITERIA_LESS_THAN = "3";
	public static final String CRITERIA_BETWEEN = "4";
	
	public static final String REJECTED_IN_SHORTLIST="1";
	public static final String REJECTED_IN_SELECTION="2";
	public static final String REJECTED_IN_HIRE="3";
	public static final String REJECTED_POSITION_CLOSED="4";
	
	//using in table tp_saved_searches as flag
	public static final String SEARCH_ID_SAVED_SEARCHES = "1";
	public static final String SEARCH_ID_RECENT_SEARCHES = "2";
	
	public static final String SHOW_ONLY = "1";
	public static final String EXCLUDE = "2";
	
	public static String SEARCH_RANGE_OPTIONS = "";
	
	public static Map<String, String> rangeCriteriaMapValue = new HashMap<String, String>();
	static {
		ArrayList<String> ids = new ArrayList<String>();
		ids.add("-1");
		ids.add(CRITERIA_EQUAL_TO);
		ids.add(CRITERIA_GREATER_THAN);
		ids.add(CRITERIA_LESS_THAN);
		ids.add(CRITERIA_BETWEEN);
		//ids.add(CRITERIA_NOT_SPECIFIED);
		ArrayList<String> names = new ArrayList<String>();
		names.add(TPLabels.getLabel("common.option.all"));
		names.add(TPLabels.getLabel("search_applicant.home.criteria.equalto"));
		names.add(TPLabels.getLabel("search_applicant.home.criteria.grreaterthan"));
		names.add(TPLabels.getLabel("search_applicant.home.criteria.lesserthan"));
		names.add(TPLabels.getLabel("search_applicant.home.criteria.between"));
		//names.add(TPLabels.getLabel("search_applicant.home.criteria.not_specified"));
		
		rangeCriteriaMapValue.put("-1", TPLabels.getLabel("common.option.all"));
		rangeCriteriaMapValue.put(CRITERIA_EQUAL_TO, TPLabels.getLabel("search_applicant.home.criteria.equalto"));
		rangeCriteriaMapValue.put(CRITERIA_GREATER_THAN, TPLabels.getLabel("search_applicant.home.criteria.grreaterthan"));
		rangeCriteriaMapValue.put(CRITERIA_LESS_THAN, TPLabels.getLabel("search_applicant.home.criteria.lesserthan"));
		rangeCriteriaMapValue.put(CRITERIA_BETWEEN, TPLabels.getLabel("search_applicant.home.criteria.between"));
		//rangeCriteriaMapValue.put(CRITERIA_NOT_SPECIFIED, TPLabels.getLabel("search_applicant.home.criteria.not_specified"));
		
		SEARCH_RANGE_OPTIONS = CommonUtils.getListJavaScriptArray(ids, names);
	}
	
	public static final String SEARCH_SHARED = "1";
	public static final String SEARCH_NOT_SHARED = "0";
}
