package com.talentPool.positions.form;

import com.talentPool.common.base.TPActionForm;

public class PublishToSiteForm extends TPActionForm  {
	private String positionName;
	private String positionDescription;
	
	public String getPositionName() {
		return positionName;
	}
	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}
	public String getPositionDescription() {
		return positionDescription;
	}
	public void setPositionDescription(String positionDescription) {
		this.positionDescription = positionDescription;
	}
	
}
