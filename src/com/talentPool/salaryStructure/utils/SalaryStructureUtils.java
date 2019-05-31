package com.talentPool.salaryStructure.utils;

import static com.talentPool.common.CommonConstants.DEFAULT_DELIMITER;

import java.util.ArrayList;
import java.util.List;

import com.talentPool.common.CommonConstants;
import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.properties.GlobalApplicationProperties;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.properties.TPLabels;
import com.talentPool.common.utils.CommonUtils;
import com.talentPool.common.utils.Utils;
import com.talentPool.salaryStructure.constants.SalaryStructureConstants;
import com.talentPool.salaryStructure.databject.SalaryFormulaData;
import com.talentPool.salaryStructure.entity.SalaryComponentCategory;

public class SalaryStructureUtils {
	
	public static String getSalaryCompCategoryJSArray(List<SalaryComponentCategory> salCompCatLst){
		StringBuilder sb = new StringBuilder();
		try {
			sb.append("[");
			if(!Utils.isListEmptyOrNull(salCompCatLst)){
				boolean first = true;
				for (SalaryComponentCategory salCompCat : salCompCatLst){
					if(first){
						Utils.getJSArraySelectOption(salCompCat.getCategoryId()+"", salCompCat.getCategoryName(), sb);
						first = false;
					}else{
						Utils.getJSArraySelectOption(salCompCat.getCategoryId()+"", salCompCat.getCategoryName(), sb.append(DEFAULT_DELIMITER));
					}
				}	
			}else{
				Utils.getJSArraySelectOption("-1","----"+ TPLabels.getLabel("common.select")+"----", sb);
			}
			sb.append("]");			
		} catch (Exception e) {
			sb = new StringBuilder(CommonConstants.NEW_ARRAY);
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return sb.toString();
	}
	
	/**
	 * Builds JS Array of variables that are used in slary calculation
	 * @return
	 */
	public static String getSalaryFormulaVariablesJSArray(){
		StringBuilder sb = new StringBuilder();
		try {
			sb.append("[");
			Utils.getJSArraySelectOption(SalaryStructureConstants.SALARY_VARIABLE_BASIC, TPLabels.getLabel("master_salary_structure_variable_basic"), sb);
			Utils.getJSArraySelectOption(SalaryStructureConstants.SALARY_VARIABLE_INPUT_SALARY, GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_SALARY_VARIABLE_INPUT_SALARY_LABEL), sb.append(DEFAULT_DELIMITER));
			sb.append("]");			
		} catch (Exception e) {
			sb = new StringBuilder(CommonConstants.NEW_ARRAY);
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return sb.toString();
	}

	public static String getSalaryFormulaDescription(SalaryFormulaData salaryFormulaData){
		String variable1 = getSalaryVarDesc(""+salaryFormulaData.getVariable1());
		String constantFactor = ""+salaryFormulaData.getConstantFactor();
		
		StringBuffer formulaDescription = new StringBuffer();
		if(!Utils.isBlankOrNull(variable1) && salaryFormulaData.getVariable1Factor()!=0.0){
			formulaDescription.append(salaryFormulaData.getVariable1Factor()).append(variable1);
		} 
		if(salaryFormulaData.getConstantFactor()!=0.0){
			if(salaryFormulaData.getConstantFactor()>0.0 && formulaDescription.length()>0)
				formulaDescription.append("+");
			formulaDescription.append(constantFactor);
		}
		return formulaDescription.toString();
	}
	
	public static String getSalaryVarDesc(String variabel){
		if(SalaryStructureConstants.SALARY_VARIABLE_INPUT_SALARY.equals(variabel)) {
			return GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_SALARY_VARIABLE_INPUT_SALARY_LABEL); 
		}else if(SalaryStructureConstants.SALARY_VARIABLE_BASIC.equals(variabel)) {
			return TPLabels.getLabel("master_salary_structure_variable_basic"); 
		}
		return "";
	}
	public static String getJSArraySalaryComponents(ArrayList salaryComponents){
		String jsArrayGrades = CommonUtils.getListJavaScriptArrayWithProperties(salaryComponents,"salaryComponentId","salaryComponentName");
		return jsArrayGrades;
	}
	
	public static String getSalaryComponentTypeDescription(String salaryComponentType){
		if(SalaryStructureConstants.SALARY_PERIOD_MONTHLY.equals(salaryComponentType)){
			return TPLabels.getLabel("master_salary_structure.salaryComponentType_monthly");
		}else if(SalaryStructureConstants.SALARY_PERIOD_YEARLY.equals(salaryComponentType)){
			return TPLabels.getLabel("master_salary_structure.salaryComponentType_yearly");
		}
		return "";
	}
	
	public static long getRoundedValue(double number,int roundTo){
		long roundedVal = Math.round(number);
		int factor = (int)(roundTo*0.5);
		roundedVal = ((roundedVal + factor) / roundTo) * roundTo;
		return roundedVal;
	}
	
	public static long getRoundedValue(double number,String roundTo){
		if(SalaryStructureConstants.ROUNDING_TO_TEN.equals(roundTo)){
			return getRoundedValue(number,10);
		} else if(SalaryStructureConstants.ROUNDING_TO_HUNDRED.equals(roundTo)){
			return getRoundedValue(number,100);
		} else if (SalaryStructureConstants.ROUNDING_TO_FIFTY.equals(roundTo)){
			return getRoundedValue(number,50);
		} else if(SalaryStructureConstants.ROUNDING_TO_THOUSAND.equals(roundTo)){
			return getRoundedValue(number,1000);
		} else{
			return Math.round(number);
		}
	}
	
	/**
	 * Builds JS Array of variables that are used in slary calculation
	 * @return
	 */
	public static String getRoundingJSArray(){
		StringBuilder sb = new StringBuilder();
		try {
			sb.append("[");
			Utils.getJSArraySelectOption(SalaryStructureConstants.ROUNDING_TO_ZERO, TPLabels.getLabel("master_salary_structure.ctcRounding.noRounding"), sb);
			Utils.getJSArraySelectOption(SalaryStructureConstants.ROUNDING_TO_TEN, TPLabels.getLabel("master_salary_structure.ctcRounding.roundToTen"), sb.append(DEFAULT_DELIMITER));
			Utils.getJSArraySelectOption(SalaryStructureConstants.ROUNDING_TO_FIFTY, TPLabels.getLabel("master_salary_structure.ctcRounding.roundToFifty"), sb.append(DEFAULT_DELIMITER));
			Utils.getJSArraySelectOption(SalaryStructureConstants.ROUNDING_TO_HUNDRED, TPLabels.getLabel("master_salary_structure.ctcRounding.roundToHundred"), sb.append(DEFAULT_DELIMITER));
			Utils.getJSArraySelectOption(SalaryStructureConstants.ROUNDING_TO_THOUSAND, TPLabels.getLabel("master_salary_structure.ctcRounding.roundToThousand"), sb.append(DEFAULT_DELIMITER));
			sb.append("]");			
		} catch (Exception e) {
			sb = new StringBuilder(CommonConstants.NEW_ARRAY);
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return sb.toString();
	}
}
