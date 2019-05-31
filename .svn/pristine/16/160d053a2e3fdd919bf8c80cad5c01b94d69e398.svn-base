package com.talentPool.budget.dataobject;

import java.sql.Date;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.db.SimpleDataObject;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.utils.DateUtils;

public class BudgetItem extends SimpleDataObject{
	
	public BudgetItem() {
		
	}

	public String getBudgetItemId() {
		return getId("budgetItemId");
	}
	public void setBudgetItemId(String budgetItemId){
		setAttribute("budgetItemId",budgetItemId);
	}

	public String getBudgetItemName() {
		return getString("budgetItemName");
	}
	public void setBudgetItemName(String budgetItemName){
		setAttribute("budgetItemName",budgetItemName);
	}

	public String getOwnerId() {
		return getId("ownerId");
	}
	public void setOwnerId(String ownerId){
		setAttribute("ownerId",ownerId);
	}

	public String getOwnerName() {
		return getString("ownerName");
	}
	public void setOwnerName(String ownerName){
		setAttribute("ownerName",ownerName);
	}

	public String getDeptId() {
		return getId("deptId");
	}
	public void setDeptId(String deptId){
		setAttribute("deptId",deptId);
	}

	public String getDeptName() {
		return getString("deptName");
	}
	public void setDeptName(String deptName){
		setAttribute("deptName",deptName);
	}

	public Date getStartTime() {
		try {
			return getDate("startTime");	
		} catch (ClassCastException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return null;
	}
	
	public void setStartTime(java.sql.Date startTime){
		setAttribute("startTime",startTime);
	}
	
	public String getStartTimeToDisplay() {
		return DateUtils.getSystemDateFormat(getStartTime());
	}

	public Date getEndTime() {
		try {
			return getDate("endTime");			
		} catch (ClassCastException cce) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, cce);
		}
		return null;
	}
	
	public void setEndTime(Date endTime){
		setAttribute("endTime",endTime);
	}
	
	public String getEndTimeToDisplay() {
		return DateUtils.getSystemDateFormat(getEndTime());
	}

	public int getAvailableHeadCount() {
		return getInt("availableHeadCount");
	}
	public void setAvailableHeadCount(String availableHeadCount){
		setAttribute("availableHeadCount",availableHeadCount);
	}

	public int getCommittedHeadCount() {
		return getInt("committedHeadCount");
	}
	public void setCommittedHeadCount(String committedHeadCount){
		setAttribute("committedHeadCount",committedHeadCount);
	}

	public int getUsedHeadCount() {
		return getInt("usedHeadCount");
	}
	public void setUsedHeadCount(String usedHeadCount){
		setAttribute("usedHeadCount",usedHeadCount);
	}

	public String getStatus() {
		return getString("status");
	}
	public void setStatus(String status){
		setAttribute("status",status);
	}

	public String getCreatedByUserId() {
		return getId("createdByUserId");
	}
	public void setCreatedByUserId(String createdByUserId){
		setAttribute("createdByUserId",createdByUserId);
	}

	public String getCreatedByUserName() {
		return getString("createdByUserName");
	}
	public void setCreatedByUserName(String createdByUserName){
		setAttribute("createdByUserName",createdByUserName);
	}

	public String getSubDeptId() {
		return getId("subDeptId");
	}
	public void setSubDeptId(String subDeptId){
		setAttribute("subDeptId",subDeptId);
	}

	public String getSubDeptName() {
		return getString("subDeptName");
	}
	public void setSubDeptName(String subDeptName){
		setAttribute("subDeptName",subDeptName);
	}

	public String getSubSubDeptId() {
		return getId("subSubDeptId");
	}
	public void setSubSubDeptId(String subSubDeptId){
		setAttribute("subSubDeptId",subSubDeptId);
	}

	public String getSubSubDeptName() {
		return getString("subSubDeptName");
	}
	public void setSubSubDeptName(String subSubDeptName){
		setAttribute("subSubDeptName",subSubDeptName);
	}

	public String getGradeId() {
		return getString("gradeId");
	}
	public void setGradeId(String gradeId){
		setAttribute("gradeId",gradeId);
	}
	
	public String getGradeName() {
		return getString("gradeName");
	}
	public void setGradeName(String gradeName){
		setAttribute("gradeName",gradeName);
	}

	public String getBandId() {
		return getString("bandId");
	}
	public void setBandId(String bandId){
		setAttribute("bandId",bandId);
	}
	
	public String getBandName() {
		return getString("bandName");
	}
	public void setBandName(String bandName){
		setAttribute("bandName",bandName);
	}
	
	public String getCreationDate() {
		return getString("creationDate");
	}
	public void setCreationDate(String creationDate){
		setAttribute("creationDate",creationDate);
	}
	public String getSub3DeptId() {
		return getId("sub3DeptId");
	}
	public void setSub3DeptId(String sub3DeptId){
		setAttribute("sub3DeptId",sub3DeptId);
	}

	public String getSub3DeptName() {
		return getString("sub3DeptName");
	}
	public void setSub3DeptName(String sub3DeptName){
		setAttribute("sub3DeptName",sub3DeptName);
	}
	public String getSub4DeptId() {
		return getId("sub4DeptId");
	}
	public void setSub4DeptId(String sub4DeptId){
		setAttribute("sub4DeptId",sub4DeptId);
	}

	public String getSub4DeptName() {
		return getString("sub4DeptName");
	}
	public void setSub4DeptName(String sub4DeptName){
		setAttribute("sub4DeptName",sub4DeptName);
	}
}
