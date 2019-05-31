/**
 * 
 */
package com.talentPool.offerSheet;

/**
 * @author pallavi
 *
 */
public interface OfferSheetConstants {
	public final static String  OFFERED_CTC = "offered_ctc";
	public final static String  MONTHLY_TOTAL = "monthly_total";
	public final static String  ANNUAL_TOTAL = "annual_total";
	public final static String  INPUT_SALARY_VARIABLE_MONTHLY = "input_salary_variable_monthly";
	public final static String  INPUT_SALARY_VARIABLE_ANNUAL = "input_salary_variable_annual";
	
	/***************** Auto Generate Code **********************/
	public static final String PATTERN_OFFER_CODE_COMPONENT = "(\\{[capsbghymdn]+\\})";
	public static final String PATTERN_OFFER_CODE_APPLICANT_ID_COMPONENT = "(\\{[i]+\\})";
	
	public final static int OFFER_FORMAT_PDF = 1;
	public final static int OFFER_FORMAT_TEMPLATE_TYPE = 2;
	
	public final static String SAL_COMPONENT_PREFIX_ANNUAL 	= "ann_sal_";
	public final static String SAL_COMPONENT_PREFIX_MONTHLY = "mon_sal_";
	public final static String SAL_CATEGORY_PREFIX_ANNUAL 	= "ann_sal_cat_";
	public final static String SAL_CATEGORY_PREFIX_MONTHLY 	= "mon_sal_cat_";
	
}
