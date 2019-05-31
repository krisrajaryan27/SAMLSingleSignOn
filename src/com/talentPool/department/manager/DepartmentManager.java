package com.talentPool.department.manager;

import java.util.ArrayList;
import java.util.List;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.db.DBPreparedQuery;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.utils.CommonUtils;
import com.talentPool.common.utils.Utils;
import com.talentPool.masters.dataobject.DepartmentData;
import com.talentPool.masters.dataobject.LocationData;

public class DepartmentManager {

	public String getJSArrayDepartments() {
		List departmentIds = CommonUtils.getDeptIds();
		List departmentNames = CommonUtils.getDeptNames();
		String jsArrayDepartments = CommonUtils.getListJavaScriptArray((ArrayList) departmentIds, (ArrayList) departmentNames);
		return jsArrayDepartments;
	}

	public String getJSArraySubDepartments(String departmentId) {
		String jsArraySubDepartments = "new Array()";
		if (!Utils.isBlankOrNull(departmentId)) {
			ArrayList<DepartmentData> departments = getSubDepartments(departmentId);
			jsArraySubDepartments = CommonUtils.getJobCodeListJavaScriptArrayWithProperties(departments, "itemId", "itemName","itemExternalCode");
		}
		return jsArraySubDepartments;
	}
	
	public ArrayList<DepartmentData> getSubDepartments(String departmentId){
		ArrayList<DepartmentData> departments = null;
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dPositionManager_GetSubDepartments");
			dq.setId(1, departmentId);
			departments = dq.getResult();
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return departments;
	}
	
	//Code for Asian Paints BEGIN
	public String getJSArrayDepartmentsById(String deptId) {
		String jsArrayDepartments = "new Array()";
		if (!Utils.isBlankOrNull(deptId)) {
			ArrayList<DepartmentData> departments = getDepartmentsById(deptId);
			jsArrayDepartments = CommonUtils.getListJavaScriptArrayWithProperties(departments, "itemId", "itemName");
		}
		return jsArrayDepartments;
	}
	
	public ArrayList<DepartmentData> getDepartmentsById(String deptId){
		ArrayList<DepartmentData> departments = null;
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dPositionManagerForAP_GetDepartmentById");
			dq.setId(1, deptId);
			departments = dq.getResult();
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return departments;
	}
	
	public String getJSArrayJobFunction() {
		List jobFunctionIds = CommonUtils.getJobFunctionIds();
		List jobFunctionNames = CommonUtils.getJobFunctionNames();
		String jsArrayJobFunction = CommonUtils.getListJavaScriptArray((ArrayList) jobFunctionIds, (ArrayList) jobFunctionNames);
		return jsArrayJobFunction;
	}
	
	public String getJSArrayJobPayGrade() {
		List jobPayGradeIds = CommonUtils.getJobPayGradeIds();
		List jobPayGradeNames = CommonUtils.getJobPayGradeNames();
		String jsArrayJobPayGrade = CommonUtils.getListJavaScriptArray((ArrayList) jobPayGradeIds, (ArrayList) jobPayGradeNames);
		return jsArrayJobPayGrade;
	}
	
	public String getJSArrayJobCode() {
		List jobCodeIds = CommonUtils.getJobCodeIds();
		List jobCodeNames = CommonUtils.getJobCodeNames();
		String jsArrayJobCode = CommonUtils.getListJavaScriptArray((ArrayList) jobCodeIds, (ArrayList) jobCodeNames);
		return jsArrayJobCode;
	}
	
	public String getJSArrayJobFunctionById(String jobFunctionId) {
		String jsArrayJobFunction = "new Array()";
		if (!Utils.isBlankOrNull(jobFunctionId)) {
			ArrayList<DepartmentData> jobFunction = getJobFunctionById(jobFunctionId);
			jsArrayJobFunction = CommonUtils.getListJavaScriptArrayWithProperties(jobFunction, "itemId", "itemName");
		}
		return jsArrayJobFunction;
	}
	
	public ArrayList<DepartmentData> getJobFunctionById(String jobFunctionId){
		ArrayList<DepartmentData> jobFunction = null;
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dPositionManagerForAP_GetJobFunctionById");
			dq.setId(1, jobFunctionId);
			jobFunction = dq.getResult();
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return jobFunction;
	}
	
	public String getJSArrayJobPayGradeById(String jobPayGradeId) {
		String jsArrayJobPayGrade = "new Array()";
		if (!Utils.isBlankOrNull(jobPayGradeId)) {
			ArrayList<DepartmentData> jobPayGrade = getJobPayGradeById(jobPayGradeId);
			jsArrayJobPayGrade = CommonUtils.getListJavaScriptArrayWithProperties(jobPayGrade, "itemId", "itemName");
		}
		return jsArrayJobPayGrade;
	}
	
	public ArrayList<DepartmentData> getJobPayGradeById(String jobPayGradeId){
		ArrayList<DepartmentData> jobPayGrade = null;
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dPositionManagerForAP_GetJobPayGradeById");
			dq.setId(1, jobPayGradeId);
			jobPayGrade = dq.getResult();
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return jobPayGrade;
	}
	
	public String getJSArrayJobCodeById(String jobCodeId) {
		String jsArrayJobCode = "new Array()";
		if (!Utils.isBlankOrNull(jobCodeId)) {
			ArrayList<DepartmentData> jobCode = getJobCodeById(jobCodeId);
			jsArrayJobCode = CommonUtils.getJobCodeListJavaScriptArrayWithProperties(jobCode, "itemId", "itemName","jobCode");
		}
		return jsArrayJobCode;
	}
	
	public ArrayList<DepartmentData> getJobCodeById(String jobCodeId){
		ArrayList<DepartmentData> jobCode = null;
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dPositionManagerForAP_GetJobCodeById");
			dq.setId(1, jobCodeId);
			jobCode = dq.getResult();
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return jobCode;
	}
	//Code for Asian Paints END
	
}
