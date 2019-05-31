package com.talentPool.salaryStructure.manager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.db.DBPreparedQuery;
import com.talentPool.common.db.DBTransaction;
import com.talentPool.common.db.SimpleDataObject;
import com.talentPool.common.db.Exception.NoResultFoundException;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.utils.Utils;
import com.talentPool.salaryStructure.constants.SalaryStructureConstants;
import com.talentPool.salaryStructure.databject.SalaryComponentsData;
import com.talentPool.salaryStructure.databject.SalaryFormulaData;

public class SalaryStructureManager {
	public List<SalaryComponentsData> getSalaryComponents() throws SQLException {
		List<SalaryComponentsData> salaryComponents = null;
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dSalaryStructureManager_getSalaryComponents");
			salaryComponents = (List<SalaryComponentsData>)dq.getResult();
		} catch (SQLException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			throw e;
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return salaryComponents;
	}
	
	public SalaryComponentsData getSalaryComponentById(String salaryComponentId) throws SQLException {
		SalaryComponentsData data = null;
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dSalaryStructureManager_GetSalaryComponentById");
			dq.setString(1, salaryComponentId);
			data = (SalaryComponentsData) dq.getSingleObjectResult();
		} catch (SQLException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			throw e;
		} finally {
			if(dq != null) {
				dq.releaseTransaction(null);
			}
		}
		return data;
	}
	
	public void addSalaryComponent(String salaryComponentName) throws SQLException {
		DBPreparedQuery dq = null;		
		try {
			dq = new DBPreparedQuery("dSalaryStructureManager_AddSalaryComponent");
			dq.setString(1, salaryComponentName);
			dq.execute();
		} catch (SQLException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			throw e;
		}finally {
			if(dq != null) {
				dq.releaseTransaction(null);
			}
		}
	}
	
	public void saveSalaryComponent(String salaryComponentId, String salaryComponentName, 
							String salaryComponentDescription, String salaryComponentType,
								String salaryComponentCategoryId) throws SQLException {
		DBPreparedQuery dq = null;		
		try {
			if(!Utils.isBlankOrNull(salaryComponentId)){
				dq = new DBPreparedQuery("dSalaryStructureManager_UpdateSalaryComponent");
				dq.setString(1, salaryComponentName);
				dq.setString(2, salaryComponentDescription);
				dq.setString(3, salaryComponentType);
				dq.setId(4, salaryComponentCategoryId);
				dq.setString(5, salaryComponentId);
			}else{
				dq = new DBPreparedQuery("dSalaryStructureManager_AddSalaryComponent");
				dq.setString(1, salaryComponentName);
				dq.setString(2, salaryComponentDescription);
				dq.setString(3, salaryComponentType);
				dq.setId(4, salaryComponentCategoryId);
			}
			dq.execute();
		} catch (SQLException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			throw e;
		}finally {
			if(dq != null) {
				dq.releaseTransaction(null);
			}
		}
	}
	
	public void deleteSalaryComponent(String salaryComponentId) throws SQLException {
		DBPreparedQuery dq = null;		
		try {
			dq = new DBPreparedQuery("dSalaryStructureManager_DeleteSalaryComponent");			
			dq.setString(1, salaryComponentId);
			dq.execute();
		} catch (SQLException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			throw e;
		}finally {
			if(dq != null) {
				dq.releaseTransaction(null);
			}
		}
	}
	
	public void saveSalaryFormula(SalaryFormulaData salaryFormulaData) throws SQLException {
		DBPreparedQuery dq = null;
		try {
			if(salaryFormulaData.getFormulaId()!=0){
				dq = new DBPreparedQuery("dSalaryStructureManager_UpdateSalaryFormula");
				dq.setInt(1, salaryFormulaData.getGradeId());
				dq.setInt(2, salaryFormulaData.getSalaryComponentId());
				dq.setInt(3, salaryFormulaData.getMaxLimit());
				dq.setInt(4, salaryFormulaData.getVariable1());
				dq.setDouble(5, salaryFormulaData.getVariable1Factor());
				dq.setDouble(6, salaryFormulaData.getConstantFactor());
				dq.setString(7, salaryFormulaData.getIsAdjustable());
				dq.setString(8, salaryFormulaData.getRoundingType());
				dq.setInt(9, salaryFormulaData.getFormulaId());
			}else{
				dq = new DBPreparedQuery("dSalaryStructureManager_AddSalaryFormula");
				dq.setInt(1, salaryFormulaData.getGradeId());
				dq.setInt(2, salaryFormulaData.getSalaryComponentId());
				dq.setInt(3, salaryFormulaData.getMaxLimit());
				dq.setInt(4, salaryFormulaData.getVariable1());
				dq.setDouble(5, salaryFormulaData.getVariable1Factor());
				dq.setDouble(6, salaryFormulaData.getConstantFactor());
				dq.setString(7, salaryFormulaData.getIsAdjustable());
				dq.setString(8, salaryFormulaData.getRoundingType());
				dq.setInt(9, salaryFormulaData.getUserId());
				dq.setDate(10,Utils.convertDateToSQLDate(new java.util.Date()));
			}
			dq.execute();
		} catch (SQLException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			throw e;
		}finally {
			if(dq != null) {
				dq.releaseTransaction(null);
			}
		}
	}
	
	public void deleteSalaryFormula(int salaryFormulaId) throws SQLException {
		DBPreparedQuery dq = null;		
		try {
			dq = new DBPreparedQuery("dSalaryStructureManager_DeleteSalaryFormula");			
			dq.setInt(1, salaryFormulaId);
			dq.execute();
		} catch (SQLException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			throw e;
		}finally {
			if(dq != null) {
				dq.releaseTransaction(null);
			}
		}
	}
	
	public List<SalaryFormulaData> getSalaryFormulae() throws SQLException {
		List<SalaryFormulaData> salaryFormulae = null;
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dSalaryStructureManager_getSalaryFormulae");
			salaryFormulae = (List<SalaryFormulaData>)dq.getResult();
		} catch (SQLException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			throw e;
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return salaryFormulae;
	}
	
	public SalaryFormulaData getSalaryFormulaById(int salaryFormulaId) throws SQLException {
		SalaryFormulaData data = null;
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dSalaryStructureManager_GetSalaryFormulaById");
			dq.setInt(1, salaryFormulaId);
			data = (SalaryFormulaData) dq.getSingleObjectResult();
		} catch (SQLException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			throw e;
		} finally {
			if(dq != null) {
				dq.releaseTransaction(null);
			}
		}
		return data;
	}
	
	public List<SalaryFormulaData> getSalaryFormulaeForaGrade(int gradeId) throws SQLException {
		List<SalaryFormulaData> salaryFormulae = null;
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dSalaryStructureManager_getSalaryFormulaeForaGrade");
			dq.setInt(1, gradeId);
			salaryFormulae = (List<SalaryFormulaData>)dq.getResult();
		} catch (SQLException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			throw e;
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return salaryFormulae;
	}
	
	public Map<String, List<SalaryFormulaData>> getSalaryFormulaeCategoryWiseForaGrade(int gradeId) throws SQLException {
		Map<String, List<SalaryFormulaData>> salaryFormulaeMap = new HashMap<>(); 
		List<SalaryFormulaData> salaryFormulae =  getSalaryFormulaeForaGrade(gradeId);
		if(!Utils.isListEmptyOrNull(salaryFormulae)){
			for (SalaryFormulaData salaryFormulaData : salaryFormulae) {
				if(salaryFormulaeMap.containsKey(salaryFormulaData.getSalaryCategoryId())){
					salaryFormulaeMap.get(salaryFormulaData.getSalaryCategoryId()).add(salaryFormulaData);
				}else{
					List<SalaryFormulaData> salFormulaLst = new ArrayList<>();
					salFormulaLst.add(salaryFormulaData);
					salaryFormulaeMap.put(salaryFormulaData.getSalaryCategoryId(),salFormulaLst);
				}	
			}			
		}
		return salaryFormulaeMap;
	}
	
	public List<SalaryComponentsData> getSalaryComponentsNotDefinedForAGrade(int gradeId)throws SQLException{
		DBPreparedQuery dq = null;
		List<SalaryComponentsData> salaryComponents = null;
		try {
			dq = new DBPreparedQuery("dSalaryStructureManager_getSalaryComponentsNotDefinedForAGrade");
			dq.setInt(1, gradeId);
			salaryComponents = (List<SalaryComponentsData>) dq.getResult();
		} catch (SQLException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			throw e;
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return salaryComponents;
	}
	public int isGradeHasAdjustableComponent(int gradeId)throws SQLException{
		DBPreparedQuery dq = null;
		int hasAdjustableComponent = 0;
		try {
			dq = new DBPreparedQuery("dalaryStructureManager_getGradeHasAdjustable");
			dq.setInt(1, gradeId);
			dq.setString(2, SalaryStructureConstants.ADJUSTABLE_COMPONENT);
			hasAdjustableComponent =  dq.getIntResult();
		} catch (SQLException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			throw e;
		} catch (NoResultFoundException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return hasAdjustableComponent;
	}
	public String getSalaryRoundingPolicy() {
		DBPreparedQuery dq = null;
		String ctcRoundingType = "";
		try {
			dq = new DBPreparedQuery("dSalaryStructureManager_getSalaryRoundingPolicy");
			ctcRoundingType =  dq.getStringResult();
		} catch (SQLException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			return null;
		}finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return ctcRoundingType;
	}
	
	public void saveSalaryRoundingPolicy(String ctcRoundingType) throws SQLException {
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dSalaryStructureManager_saveSalaryRoundingPolicy");
			dq.setString(1, ctcRoundingType);
			dq.execute();
		} catch (SQLException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			throw e;
		}finally {
			if(dq != null) {
				dq.releaseTransaction(null);
			}
		}
	}
	
	/**
	 * Fetches Existing Salary Details (Totals of categories of all components) for given applicant_id 
	 * @return
	 * @throws SQLException
	 */
	public List<SimpleDataObject> getExistingSalaryDetails(String applicantId) throws SQLException {
		List<SimpleDataObject> existingSalaryDetails = null;
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dSalaryStructureManager_getExistingSalaryDetails");
			dq.setId(1, applicantId);
			existingSalaryDetails = (List<SimpleDataObject>)dq.getResult();
		} catch (SQLException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			throw e;
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return existingSalaryDetails;
	}
	
	/**
	 * Deletes applicants existing salary cat totals (Fixed, variable etc) and inserts the values provided in given map 
	 * @param applicantId
	 * @param SalCatsExistingValMap
	 * @param tran
	 * @throws SQLException
	 */
	public void saveExisitngSalaryDetails(String applicantId, Map<String,String> salCatsExistingValMap, DBTransaction tran) throws SQLException {
		deleteExisitngSalaryDetails(applicantId, tran);
		insertExisitngSalaryDetails(applicantId, salCatsExistingValMap, tran);
	}
	
	/**
	 * Deletes applicants existing salary cat totals (Fixed, variable etc)
	 * @param applicantId
	 * @param SalCatsExistingValMap
	 * @param tran
	 * @throws SQLException
	 */
	public void deleteExisitngSalaryDetails(String applicantId, DBTransaction tran) throws SQLException {
		DBPreparedQuery dq = null;
		try {
			if(tran!=null){
				dq = new DBPreparedQuery("dSalaryStructureManager_deleteExistingSalaryDetails", tran);				
			}else{
				dq = new DBPreparedQuery("dSalaryStructureManager_deleteExistingSalaryDetails");
			}
			dq.setString(1, applicantId);
			dq.execute();
		} finally {
			if (dq != null) {
				if (tran == null) {
					dq.releaseConnection();
				} else {
					dq.closeOpenCursors();
				}
			}
		}
	}
	
	/**
	 * Inserts applicants existing salary cat totals (Fixed, variable etc) provided in given map
	 * @param applicantId
	 * @param SalCatsExistingValMap
	 * @param tran
	 * @throws SQLException
	 */
	public void insertExisitngSalaryDetails(String applicantId, Map<String,String> salCatsExistingValMap, DBTransaction tran) throws SQLException {
		DBPreparedQuery dq = null;
		try {
			for (Entry<String, String> entry : salCatsExistingValMap.entrySet()) {
				if(tran!=null){
					dq = new DBPreparedQuery("dSalaryStructureManager_insertExistingSalaryDetails", tran);				
				}else{
					dq = new DBPreparedQuery("dSalaryStructureManager_insertExistingSalaryDetails");
				}
				dq.setString(1, applicantId);
				dq.setString(2, entry.getKey());
				dq.setString(3, Utils.isBlankOrNull(entry.getValue())?"0":entry.getValue());
				dq.execute();				
			}
		} finally {
			if (dq != null) {
				if (tran == null) {
					dq.releaseConnection();
				} else {
					dq.closeOpenCursors();
				}
			}
		}
	}
	
	
	/**
	 * Adds default formula for Basic for given gradeId as 1.0 * Basic + 0.0 and as system defined formula
	 * @param gradeId
	 * @param tran
	 * @throws SQLException
	 */
	public void insertDefaultBasicFormulaForGrade(String gradeId, DBTransaction tran) throws SQLException {
		DBPreparedQuery dq = null;
		try {
			if(tran!=null){
				dq = new DBPreparedQuery("dSalaryStructureManager_insertDefaultBasicFormulaForGrade", tran);				
			}else{
				dq = new DBPreparedQuery("dSalaryStructureManager_insertDefaultBasicFormulaForGrade");
			}
			dq.setString(1, gradeId);
			dq.setInt(2, SalaryStructureConstants.SALARY_VARIABLE_BASIC_COMPONENT_ID);
			dq.execute();				
		} finally {
			if (dq != null) {
				if (tran == null) {
					dq.releaseConnection();
				} else {
					dq.closeOpenCursors();
				}
			}
		}
	}
}
