/**
 * Copyright 2009 - Nitman Software Pvt. Ltd. All Rights Reserved. 
 * This software is the proprietary information of Nitman Software Pvt. Ltd. 
 * Use is subject to license terms.
 */
package com.talentPool.salaryStructure.databject;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.talentPool.salaryStructure.constants.SalaryStructureConstants;

/**
 * @author PraveenK
 * @since  May 30, 2012
 */
public class SalaryStructure {
	
	private Integer inputSalaryVariableMonthly;
	private Integer inputSalaryVariableAnnual;
	private Map<Integer,Integer> monthlySalCompVals;
	private Map<Integer,Integer> annualSalCompVals;
	private Map<String,Integer> monthlyCatTotals;
	private Map<String,Integer> annualCatTotals;
	
	public SalaryStructure(Integer inputSalaryVariableMonthly, Integer inputSalaryVariableAnnual){
		this.inputSalaryVariableMonthly = inputSalaryVariableMonthly;
		this.inputSalaryVariableAnnual	= inputSalaryVariableAnnual;
		this.monthlySalCompVals 		= new HashMap<>();
		this.annualSalCompVals 			= new HashMap<>();
		this.monthlyCatTotals			= new HashMap<>();
		this.annualCatTotals			= new HashMap<>();
	}
	
	/**
	 * @return the inputSalaryVariableMonthly
	 */
	public Integer getInputSalaryVariableMonthly() {
		return inputSalaryVariableMonthly;
	}
	/**
	 * @param inputSalaryVariableMonthly the inputSalaryVariableMonthly to set
	 */
	public void setInputSalaryVariableMonthly(Integer inputSalaryVariableMonthly) {
		this.inputSalaryVariableMonthly = inputSalaryVariableMonthly;
	}
	/**
	 * @return the inputSalaryVariableAnnual
	 */
	public Integer getInputSalaryVariableAnnual() {
		return inputSalaryVariableAnnual;
	}
	/**
	 * @param inputSalaryVariableAnnual the inputSalaryVariableAnnual to set
	 */
	public void setInputSalaryVariableAnnual(Integer inputSalaryVariableAnnual) {
		this.inputSalaryVariableAnnual = inputSalaryVariableAnnual;
	}
	/**
	 * @return the monthlySalCompVals
	 */
	public Map<Integer, Integer> getMonthlySalCompVals() {
		return monthlySalCompVals;
	}
	/**
	 * @param monthlySalCompVals the monthlySalCompVals to set
	 */
	public void setMonthlySalCompVals(Map<Integer, Integer> monthlySalCompVals) {
		this.monthlySalCompVals = monthlySalCompVals;
	}
	/**
	 * @return the annualSalCompVals
	 */
	public Map<Integer, Integer> getAnnualSalCompVals() {
		return annualSalCompVals;
	}
	/**
	 * @param annualSalCompVals the annualSalCompVals to set
	 */
	public void setAnnualSalCompVals(Map<Integer, Integer> annualSalCompVals) {
		this.annualSalCompVals = annualSalCompVals;
	}
	/**
	 * @return the monthlyCatTotals
	 */
	public Map<String, Integer> getMonthlyCatTotals() {
		return monthlyCatTotals;
	}
	/**
	 * @param monthlyCatTotals the monthlyCatTotals to set
	 */
	public void setMonthlyCatTotals(Map<String, Integer> monthlyCatTotals) {
		this.monthlyCatTotals = monthlyCatTotals;
	}
	/**
	 * @return the annualCatTotals
	 */
	public Map<String, Integer> getAnnualCatTotals() {
		return annualCatTotals;
	}
	/**
	 * @param annualCatTotals the annualCatTotals to set
	 */
	public void setAnnualCatTotals(Map<String, Integer> annualCatTotals) {
		this.annualCatTotals = annualCatTotals;
	}
	
	public Integer getMonthlyValue(int salaryComponentId){
		return monthlySalCompVals.get(salaryComponentId);
	}
	
	public Integer getAnnualValue(int salaryComponentId){
		return annualSalCompVals.get(salaryComponentId);
	}
	
	public Integer getMonthlyCatValue(String categoryId){
		return monthlyCatTotals.get(categoryId);
	}
	
	public Integer getAnnualCatValue(String categoryId){
		return annualCatTotals.get(categoryId);
	}
	
	public Integer getMonthlyTotal(){
		Integer monthlytotal = 0;
		Collection<Integer>  values = monthlySalCompVals.values();
		for (Integer value : values) {
			monthlytotal+=value;
		}
		return monthlytotal;
	}
	
	public Integer getAnnualTotal(){
		Integer annualTotal = 0;
		Collection<Integer>  values = annualSalCompVals.values();
		for (Integer value : values) {
			annualTotal+=value;
		}
		return annualTotal;
	}
	
	public Integer getMonthlyBasic(){
		Integer monthlyBasic = 0;
		for (Entry<Integer,Integer> entry : monthlySalCompVals.entrySet()) {
			if(SalaryStructureConstants.SALARY_VARIABLE_BASIC_COMPONENT_ID==entry.getKey()){
				monthlyBasic = entry.getValue();
				break;
			}
		}
		return monthlyBasic;
	}
	
	public void putAnnualSalCompVal(int salCompId, int salCompVal){
		getAnnualSalCompVals().put(salCompId, salCompVal);	
	}
	
	public void putMonthlySalCompVal(int salCompId, int salCompVal){
		getMonthlySalCompVals().put(salCompId, salCompVal);	
	}
}
