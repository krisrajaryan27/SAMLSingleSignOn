package com.talentPool.salaryStructure.manager;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import com.talentPool.applicant.manager.ApplicantManager;
import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.db.SimpleDataObject;
import com.talentPool.common.properties.GlobalApplicationProperties;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.properties.TPLabels;
import com.talentPool.common.utils.NumberFormatUtils;
import com.talentPool.common.utils.Utils;
import com.talentPool.salaryStructure.constants.SalaryStructureConstants;
import com.talentPool.salaryStructure.databject.SalaryFormulaData;
import com.talentPool.salaryStructure.databject.SalaryStructure;
import com.talentPool.salaryStructure.exception.SalaryCalculationException;
import com.talentPool.salaryStructure.utils.SalaryStructureUtils;

/**
 * Salary is calculated for each component based on the formula <code>AX+B</code>, where 
 * 		A & B are constants AND
 * 		X is Basic (Variable Component)
 * @author PraveenK
 * 
 */
public class SalaryCalculator {
	
	/**
	 * Calculates the Salary for given Applicant ID (only if the applicant is associated to one of the grades) 
	 * @param applicantId
	 * @return applicantSalary (ApplicantSalary Data Object)
	 */
	public SalaryStructure calculateSalary(String applicantId) throws SalaryCalculationException {
		ApplicantManager applicantManager = new ApplicantManager();
		SimpleDataObject sdo = applicantManager.getApplicantGradeCTCBasic(applicantId);
		SalaryStructure applicantSalary = calculateSalary(sdo.getString("gradeId"),sdo.getString("offeredCtc"),sdo.getString("offeredBasic"),sdo.getString("inputSalaryVariable"));
		return applicantSalary;
	}
	
	/**
	 * Calculates the Salary, all computation are done using the formula defined for the Grade and using given CTC and Basic Values.
	 * @param gradeId
	 * @param ctc
	 * @param basic
	 * @return applicantSalary (ApplicantSalary Data Object)
	 */
	public SalaryStructure calculateSalary(String gradeId, String ctc, String  basic, String inputSalary) throws SalaryCalculationException {
		SalaryStructureManager salaryStructureManager = null;
		List<SalaryFormulaData> salaryFormulae = null;
		SalaryFormulaData adjustableComponent = null;
		SalaryFormulaData basicComponent = null;
		SalaryStructure salaryStructure = null;
		boolean basicBased 		= false;
		boolean inputSalaryVarBased 	= false;
		int ctcVal = 0;
		int adjustmentvalue = 0;
		double basicVal = 0.0;
		double inputSalaryVal = 0.0;
		int calculatedCTC = 0;
		try {
			salaryStructureManager 	= new SalaryStructureManager();
			salaryFormulae			= salaryStructureManager.getSalaryFormulaeForaGrade(Integer.parseInt(gradeId));
			ctcVal 			= validateInt(ctc);
			basicVal 		= validateDouble(basic);
			inputSalaryVal 	= validateDouble(inputSalary);
			for (SalaryFormulaData salaryFormulaData : salaryFormulae) {
				if(SalaryStructureConstants.ADJUSTABLE_COMPONENT.equals(salaryFormulaData.getIsAdjustable())){
					adjustableComponent=salaryFormulaData;
				}
				if(SalaryStructureConstants.SALARY_VARIABLE_BASIC_COMPONENT_ID==salaryFormulaData.getSalaryComponentId()){
					basicComponent=salaryFormulaData;
					if(SalaryStructureConstants.SALARY_VARIABLE_BASIC.equals(basicComponent.getVariable1()+""))
						basicBased = true;
				}
				if(SalaryStructureConstants.SALARY_VARIABLE_INPUT_SALARY.equals(salaryFormulaData.getVariable1()+""))
					inputSalaryVarBased = true;
			}
			if(inputSalaryVarBased && basicBased){
				if(basicVal == 0.0 || inputSalaryVal == 0.0){
					throw new SalaryCalculationException(TPLabels.getLabel("selection_feedback.error.invalid_value_for_input_sal_var_and_basic", new String[]{GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_SALARY_VARIABLE_INPUT_SALARY_LABEL)}));
				}
				salaryStructure = calculateSalary(salaryFormulae, inputSalaryVal, basicVal);
			} else if(inputSalaryVarBased){
				if(inputSalaryVal == 0.0){
					throw new SalaryCalculationException(TPLabels.getLabel("selection_feedback.error.invalid_value_for_input_sal_var", new String[]{GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_SALARY_VARIABLE_INPUT_SALARY_LABEL)}));
				}
				basicVal 		= divide(getSalaryComponentValue(inputSalaryVal, basicComponent),12);  
				salaryStructure = calculateSalary(salaryFormulae, inputSalaryVal, basicVal);
			} else if(basicBased){
				if(basicVal == 0.0){
					throw new SalaryCalculationException(TPLabels.getLabel("selection_feedback.error.invalid_value_for_basic"));
				}
				salaryStructure = calculateSalary(salaryFormulae, basicVal);
			}	
			if(salaryStructure!=null){
			calculatedCTC = salaryStructure.getAnnualTotal();
			}
			if(adjustableComponent!=null){
				if(ctcVal==0){
					salaryStructureManager = new SalaryStructureManager();
					String ctcRoundingType = salaryStructureManager.getSalaryRoundingPolicy(); 
					if(!Utils.isBlankOrNull(ctcRoundingType) && !SalaryStructureConstants.ROUNDING_TO_ZERO.equals(ctcRoundingType)){
						adjustmentvalue = (int)SalaryStructureUtils.getRoundedValue(calculatedCTC, ctcRoundingType)-calculatedCTC;
					}
				}else if(ctcVal!=calculatedCTC){
					adjustmentvalue = ctcVal - calculatedCTC;
				}
				int adjustedVal = salaryStructure.getAnnualValue(adjustableComponent.getSalaryComponentId())+adjustmentvalue;
				if(SalaryStructureConstants.SALARY_PERIOD_YEARLY.equals(adjustableComponent.getSalaryComponentType())){
					salaryStructure.putAnnualSalCompVal(adjustableComponent.getSalaryComponentId(), adjustedVal);
					addToAnnualCategoryTotal(adjustableComponent.getSalaryCategoryId(), adjustmentvalue, salaryStructure);
				}else{
					salaryStructure.putAnnualSalCompVal(adjustableComponent.getSalaryComponentId(), adjustedVal);
					salaryStructure.putMonthlySalCompVal(adjustableComponent.getSalaryComponentId(), divide(adjustedVal,12));
					addToAnnualCategoryTotal(adjustableComponent.getSalaryCategoryId(), adjustmentvalue, salaryStructure);
					addToMonthlyCategoryTotal(adjustableComponent.getSalaryCategoryId(), divide(adjustmentvalue,12), salaryStructure);
				}
			}
		} catch (NumberFormatException ne) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, ne);
		} catch (SQLException sqle) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, sqle);
		} catch (SalaryCalculationException sce) {
			throw sce;
		} 
		return salaryStructure;
	}
	
	public SalaryStructure calculateSalary(List<SalaryFormulaData> salaryFormulae, double basicVal){
		return calculateSalary(salaryFormulae, 0.0, basicVal);
	}
	
	public SalaryStructure calculateSalary(List<SalaryFormulaData> salaryFormulae, double inputCtcVal, double basicVal){
		SalaryStructure salaryStructure 	= new SalaryStructure((int)inputCtcVal, divide(inputCtcVal,12));
		double salaryComponentValue;
		int monthlyValue=0;
		int annualValue=0;
		for (SalaryFormulaData salaryFormulaData : salaryFormulae) {
			
			salaryComponentValue = getSalaryComponentValue(inputCtcVal, basicVal, salaryFormulaData);
			
			if(salaryFormulaData.getVariable1Factor()==0.0){ // means it is a constant factor
				if(SalaryStructureConstants.SALARY_PERIOD_YEARLY.equals(salaryFormulaData.getSalaryComponentType())){
					annualValue = (int)SalaryStructureUtils.getRoundedValue(salaryComponentValue, salaryFormulaData.getRoundingType());
					monthlyValue = 0;
				}else if(SalaryStructureConstants.SALARY_PERIOD_MONTHLY.equals(salaryFormulaData.getSalaryComponentType())){
					if(SalaryStructureConstants.ROUNDING_TO_ZERO.equals(salaryFormulaData.getRoundingType())){
						monthlyValue = (int)Math.round(salaryComponentValue);
						annualValue  = (int)Math.round(multiply(salaryComponentValue,12));
					}else{
						monthlyValue = (int)SalaryStructureUtils.getRoundedValue(salaryComponentValue, salaryFormulaData.getRoundingType());
						annualValue  = multiply(monthlyValue,12);						
					}
				}
			}else {
				if(SalaryStructureConstants.SALARY_VARIABLE_BASIC.equals(""+salaryFormulaData.getVariable1())){
					if(SalaryStructureConstants.ROUNDING_TO_ZERO.equals(salaryFormulaData.getRoundingType())){
						monthlyValue = (int)Math.round(salaryComponentValue);
						annualValue  = (int)Math.round(multiply(salaryComponentValue,12));
					}else{
						monthlyValue = (int)SalaryStructureUtils.getRoundedValue(salaryComponentValue, salaryFormulaData.getRoundingType());
						annualValue  = multiply(monthlyValue,12);						
					}
				}else if(SalaryStructureConstants.SALARY_VARIABLE_INPUT_SALARY.equals(""+salaryFormulaData.getVariable1())){
					if(SalaryStructureConstants.ROUNDING_TO_ZERO.equals(salaryFormulaData.getRoundingType())){
						monthlyValue = (int)Math.round(divide(salaryComponentValue,12));
						annualValue  = (int)Math.round(salaryComponentValue);
					}else{
						annualValue   = (int)SalaryStructureUtils.getRoundedValue(salaryComponentValue, salaryFormulaData.getRoundingType());
						monthlyValue  = divide(annualValue,12);
					}
				}
			}
			
			if(SalaryStructureConstants.SALARY_PERIOD_YEARLY.equals(salaryFormulaData.getSalaryComponentType())){
				salaryStructure.putAnnualSalCompVal(salaryFormulaData.getSalaryComponentId(), annualValue);
				addToAnnualCategoryTotal(salaryFormulaData.getSalaryCategoryId(), annualValue, salaryStructure);
			}else if(SalaryStructureConstants.SALARY_PERIOD_MONTHLY.equals(salaryFormulaData.getSalaryComponentType())){
				salaryStructure.putAnnualSalCompVal(salaryFormulaData.getSalaryComponentId(), annualValue);
				salaryStructure.putMonthlySalCompVal(salaryFormulaData.getSalaryComponentId(), monthlyValue);
				addToMonthlyCategoryTotal(salaryFormulaData.getSalaryCategoryId(), monthlyValue, salaryStructure);
				addToAnnualCategoryTotal(salaryFormulaData.getSalaryCategoryId(), annualValue, salaryStructure);
			}
		}
		return salaryStructure;
	}
	

	
	/**
	 * @param inputSalaryVal
	 * @param basicVal
	 * @param salaryFormulaData
	 * @return salaryComponentVal
	 * Computes the SalaryComponent Value from formula defined using ctc or basic
	 */
	public Double getSalaryComponentValue(double inputSalaryVal, SalaryFormulaData salaryFormulaData){
		return getSalaryComponentValue(inputSalaryVal, 0, salaryFormulaData);
	}
	
	/**
	 * @param inputSalaryVal
	 * @param basicVal
	 * @param salaryFormulaData
	 * @return salaryComponentVal
	 * Computes the SalaryComponent Value from formula defined using ctc or basic
	 */
	public Double getSalaryComponentValue(double inputSalaryVal,double basicVal,SalaryFormulaData salaryFormulaData){
		double salaryComponentVal = 0.0;
		String variable1 = ""+salaryFormulaData.getVariable1();
		double variable1Factor = salaryFormulaData.getVariable1Factor();
		double constantFactor = salaryFormulaData.getConstantFactor();
		
		if(SalaryStructureConstants.SALARY_VARIABLE_BASIC.equals(variable1))
			salaryComponentVal = evaluateExpression(basicVal,variable1Factor,constantFactor);
		else if(SalaryStructureConstants.SALARY_VARIABLE_INPUT_SALARY.equals(variable1)){
			salaryComponentVal = evaluateExpression(inputSalaryVal,variable1Factor,constantFactor);
		}else {
			salaryComponentVal = evaluateExpression(0.0,variable1Factor,constantFactor);
		}
		
		if(salaryFormulaData.getMaxLimit()!=0)
			salaryComponentVal = getMinOfTwo(salaryFormulaData.getMaxLimit(),salaryComponentVal);
		
		return salaryComponentVal;
	}
	
	private double validateDouble(String val) throws SalaryCalculationException {
		try {
			if(!Utils.isBlankOrNull(val))
				return Double.parseDouble(val);
			else 
				return 0.0;
		} catch (NumberFormatException e) {
			return 0.0;
		}
	}
	
	private int validateInt(String val) throws SalaryCalculationException {
		try {
			if(!Utils.isBlankOrNull(val))
				return Integer.parseInt(val);
			else 
				return 0;
		} catch (NumberFormatException e) {
			return 0;
		}
	}
	
	public double evaluateExpression(double val,double variable1Factor,double constantFactor) {
		return (variable1Factor * val) + constantFactor;
	}
	
	public double evaluateBasic(double val,double variable1Factor,double constantFactor) {
		return ((val-constantFactor) / variable1Factor);
	}
	
	public double multiply(double x,double y) {
		return x * y;
	}
	
	public int multiply(int x,int y) {
		return x * y;
	}
	
	public int divide(double x,double y){
		return (int) Math.round(x/y);
	}
	
	public double getMinOfTwo(double x,double y){
		if(x<y)
			return x;
		else 
			return y;
	}
	
	public double addValues(double x,double y){
		return x + y;
	}
	
	public int addValues(int x,int y){
		return x + y;
	}
	
	public String formatSalary(double number) {
		return NumberFormatUtils.format(number, NumberFormatUtils.commonFormat);
	}
	
	public double parseSalary(String number) {
		double parsedDouble = 0.0;
		try {
			parsedDouble = NumberFormatUtils.parse(number, NumberFormatUtils.commonFormat);
		} catch (ParseException pe) {
			TPLogger.getLogger().error(pe);
			parsedDouble=0.0;
		}
		return parsedDouble;
	}
	
	/**
	 * 
	 * @param categoryId
	 * @param value
	 * @param sdo
	 */
	private void addToMonthlyCategoryTotal(String categoryId, Integer value, SalaryStructure salStructure){
		Integer monthlyCatVal = salStructure.getMonthlyCatValue(categoryId);
		monthlyCatVal=monthlyCatVal==null?0:monthlyCatVal;
		salStructure.getMonthlyCatTotals().put(categoryId, addValues(monthlyCatVal, value));
	}
	
	/**
	 * 
	 * @param categoryId
	 * @param value
	 * @param sdo
	 */
	private void addToAnnualCategoryTotal(String categoryId, Integer value, SalaryStructure salStructure){
		Integer annaulCatVal = salStructure.getAnnualCatValue(categoryId);
		annaulCatVal=annaulCatVal==null?0:annaulCatVal;
		salStructure.getAnnualCatTotals().put(categoryId, addValues(annaulCatVal, value));
	}
	
	public static void main(String[] args) {
		try {
			SalaryCalculator sc = new SalaryCalculator();
			SalaryStructureManager salaryStructureManager = new SalaryStructureManager();
			ApplicantManager applicantManager = new ApplicantManager();
			SimpleDataObject sdo = applicantManager.getApplicantGradeCTCBasic("27");
			SalaryStructure applicantSalary = sc.calculateSalary(sdo.getString("gradeId"),"600000","17000","500000");
			List<SalaryFormulaData> salaryFormulae = salaryStructureManager.getSalaryFormulaeForaGrade(Integer.parseInt(sdo.getString("gradeId")));
			for (SalaryFormulaData salaryFormulaData : salaryFormulae) {
				String catName = salaryFormulaData.getSalaryComponentName();
				Integer monthlyVal  =  applicantSalary.getMonthlyValue(salaryFormulaData.getSalaryComponentId());
				Integer annualVal 	=  applicantSalary.getAnnualValue(salaryFormulaData.getSalaryComponentId());
				System.out.println(catName+": "+monthlyVal+"  "+annualVal);
			}			
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
	}
}
