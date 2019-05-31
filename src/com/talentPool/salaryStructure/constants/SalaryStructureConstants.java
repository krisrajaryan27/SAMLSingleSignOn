package com.talentPool.salaryStructure.constants;

public class SalaryStructureConstants {
	/**
	 * A Variable similar to BASIC, Can be renamed as per the client requirement from application settings.
	 * Client can use this variable as Annual Gross or Input CTC. 
	 */
	public static final String SALARY_VARIABLE_INPUT_SALARY = "1";
	
	/**
	 * Variable Basic, usually Basic will be again a formula of variable {@link SalaryStructureConstants}{@link #SALARY_VARIABLE_INPUT_VALUE}  
	 */
	public static final String SALARY_VARIABLE_BASIC = "2";
	
	
	/**
	 * Salary Component Id to refer Basic Component to update. 
	 */
	public static final int SALARY_VARIABLE_BASIC_COMPONENT_ID = 200;
	
	/***
	 *Whether the Given formula is for monthly or yearly
	 ***/
	public static final String SALARY_PERIOD_MONTHLY = "M";
	public static final String SALARY_PERIOD_YEARLY = "Y";
	
	public static final String ADJUSTABLE_COMPONENT = "1";
	public static final String NON_ADJUSTABLE_COMPONENT = "0";
	
	public static final String ROUNDING_TO_ZERO = "0";
	public static final String ROUNDING_TO_TEN = "10";
	public static final String ROUNDING_TO_HUNDRED = "100";
	public static final String ROUNDING_TO_FIFTY = "50";
	public static final String ROUNDING_TO_THOUSAND = "1000";
}
