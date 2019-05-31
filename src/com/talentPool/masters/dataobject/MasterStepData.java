package com.talentPool.masters.dataobject;

import com.talentPool.common.db.SimpleDataObject;

public class MasterStepData extends SimpleDataObject {
	
	
	public String getStepId() {
		return getString("stepId");
	}
		
	public void setStepId(String stepId) {
		setAttribute("stepId", stepId);
	}
	
	public String getStepName() {
		return getString("stepName");
	}
		
	public void setStepName(String stepName) {
		setAttribute("stepName", stepName);
	}
	
	public String getStepRank() {
		return getString("stepRank");
	}
		
	public void setStepRank(String stepRank) {
		setAttribute("stepRank", stepRank);
	}
	
	public String getSystemStep() {
		return getString("systemStep");
	}
		
	public void setSystemStep(String systemStep) {
		setAttribute("systemStep", systemStep);
	}
	
	public String getStepDesc() {
		return getString("stepDesc");
	}
		
	public void setStepDesc(String stepDesc) {
		setAttribute("stepDesc", stepDesc);
	}

	public String getStepLevel() {
		return getString("stepLevel");
	}
		
	public void setStepLevel(String stepLevel) {
		setAttribute("stepLevel", stepLevel);
	}

	public String getStage() {
		return getString("stage");
	}
		
	public void setStage(String stage) {
		setAttribute("stage", stage);
	}

	public String getStepSchedulable() {
		return getString("stepSchedulable");
	}
		
	public void setStepSchedulable(String stepSchedulable) {
		setAttribute("stepSchedulable", stepSchedulable);
	}
	
	public String getStepDisabled() {
		return getString("stepDisabled");
	}
		
	public void setStepDisabled(String stepDisabled) {
		setAttribute("stepDisabled", stepDisabled);
	}
	
}
