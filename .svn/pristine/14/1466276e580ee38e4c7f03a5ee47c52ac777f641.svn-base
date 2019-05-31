package com.talentPool.positions.dataobject;

import java.util.ArrayList;

import com.talentPool.common.db.SimpleDataObject;

public class PositionSkillsData extends SimpleDataObject {

	public PositionSkillsData() {

	}

	public String getPositionId() {
		return getId("positionId");
	}

	public void setPositionId(String positionId) {
		setAttribute("positionId", positionId);
	}

	public String getSkillName() {
		return getString("skillName");
	}

	public void setSkillName(String skillName) {
		setAttribute("skillName", skillName);
	}

	public int getSkillId() {
		return getInt("skillId");
	}

	public void setSkillId(String skillId) {
		setAttribute("skillId", skillId);
	}

	public void setSkillStatus(String skillStatus) {
		setAttribute("skillStatus", skillStatus);
	}

	public String getSkillStatus() {
		return getString("skillStatus");
	}
	public int getSkillCategoryId() {
		return getInt("skillCategoryId");
	}

	public void setSkillCategoryId(String skillCategoryId) {
		setAttribute("skillCategoryId", skillCategoryId);
	}

	public String getSkillCategoryDescription() {
		return getString("skillCategoryDescription");
	}

	public void setSkillCategoryDescription(String skillCategoryDescription) {
		setAttribute("skillCategoryDescription", skillCategoryDescription);
	}
	
	public ArrayList getAliases(){
		return (ArrayList)getAttribute("aliases");
	}
	
	public void setAliases(ArrayList aliases){
		setAttribute("aliases", aliases);
	}
}