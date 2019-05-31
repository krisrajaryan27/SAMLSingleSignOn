package com.talentPool.salaryStructure.form;

import com.talentPool.common.base.TPActionForm;

public class SalaryStructureForm extends TPActionForm {
	
	/**
	 * Salary Component Related
	 */
	private String salaryComponentId;
	private String salaryComponentName;
	private String salaryComponentDescription;
	private String salaryComponentType;
	private String salaryComponentCategoryId;
	
	/**
	 * Salary Formula Related
	 */
	private int formulaId;
	private int gradeId;
	private int maxLimit;
	private int variable1;
	private double variable1Factor;
	private double constantFactor;
	private String isAdjustable;
	private String roundingType;
	
	/**
	 * Rounding Related
	 */
	private String ctcRoundingType;
	
	/**
	 * @return the salaryComponentId
	 */
	public String getSalaryComponentId() {
		return salaryComponentId;
	}
	/**
	 * @param salaryComponentId the salaryComponentId to set
	 */
	public void setSalaryComponentId(String salaryComponentId) {
		this.salaryComponentId = salaryComponentId;
	}
	/**
	 * @return the salaryComponentName
	 */
	public String getSalaryComponentName() {
		return salaryComponentName;
	}
	/**
	 * @param salaryComponentName the salaryComponentName to set
	 */
	public void setSalaryComponentName(String salaryComponentName) {
		this.salaryComponentName = salaryComponentName;
	}
	/**
	 * @return the salaryComponentDescription
	 */
	public String getSalaryComponentDescription() {
		return salaryComponentDescription;
	}
	/**
	 * @param salaryComponentDescription the salaryComponentDescription to set
	 */
	public void setSalaryComponentDescription(String salaryComponentDescription) {
		this.salaryComponentDescription = salaryComponentDescription;
	}
	/**
	 * @return the formulaId
	 */
	public int getFormulaId() {
		return formulaId;
	}
	/**
	 * @param formulaId the formulaId to set
	 */
	public void setFormulaId(int formulaId) {
		this.formulaId = formulaId;
	}
	/**
	 * @return the gradeId
	 */
	public int getGradeId() {
		return gradeId;
	}
	/**
	 * @param gradeId the gradeId to set
	 */
	public void setGradeId(int gradeId) {
		this.gradeId = gradeId;
	}
	/**
	 * @return the variable1
	 */
	public int getVariable1() {
		return variable1;
	}
	/**
	 * @param variable1 the variable1 to set
	 */
	public void setVariable1(int variable1) {
		this.variable1 = variable1;
	}
	/**
	 * @return the variable1Factor
	 */
	public double getVariable1Factor() {
		return variable1Factor;
	}
	/**
	 * @param variable1Factor the variable1Factor to set
	 */
	public void setVariable1Factor(double variable1Factor) {
		this.variable1Factor = variable1Factor;
	}
	/**
	 * @return the constantFactor
	 */
	public double getConstantFactor() {
		return constantFactor;
	}
	/**
	 * @param constantFactor the constantFactor to set
	 */
	public void setConstantFactor(double constantFactor) {
		this.constantFactor = constantFactor;
	}
	/**
	 * @return the maxLimit
	 */
	public int getMaxLimit() {
		return maxLimit;
	}
	/**
	 * @param maxLimit the maxLimit to set
	 */
	public void setMaxLimit(int maxLimit) {
		this.maxLimit = maxLimit;
	}
	/**
	 * @return the isAdjustable
	 */
	public String getIsAdjustable() {
		return isAdjustable;
	}
	/**
	 * @param isAdjustable the isAdjustable to set
	 */
	public void setIsAdjustable(String isAdjustable) {
		this.isAdjustable = isAdjustable;
	}
	/**
	 * @return the salaryComponentType
	 */
	public String getSalaryComponentType() {
		return salaryComponentType;
	}
	/**
	 * @param salaryComponentType the salaryComponentType to set
	 */
	public void setSalaryComponentType(String salaryComponentType) {
		this.salaryComponentType = salaryComponentType;
	}
	/**
	 * @return the ctcRoundingType
	 */
	public String getCtcRoundingType() {
		return ctcRoundingType;
	}
	/**
	 * @param ctcRoundingType the ctcRoundingType to set
	 */
	public void setCtcRoundingType(String ctcRoundingType) {
		this.ctcRoundingType = ctcRoundingType;
	}
	/**
	 * @return the salaryComponentCategoryId
	 */
	public String getSalaryComponentCategoryId() {
		return salaryComponentCategoryId;
	}
	/**
	 * @param salaryComponentCategoryId the salaryComponentCategoryId to set
	 */
	public void setSalaryComponentCategoryId(String salaryComponentCategoryId) {
		this.salaryComponentCategoryId = salaryComponentCategoryId;
	}
	/**
	 * @return the roundingType
	 */
	public String getRoundingType() {
		return roundingType;
	}
	/**
	 * @param roundingType the roundingType to set
	 */
	public void setRoundingType(String roundingType) {
		this.roundingType = roundingType;
	}

}
