package com.talentPool.positions.utils;

import com.talentPool.positions.dataobject.PositionTemplateFilterData;
import com.talentPool.positions.form.PositionForm;

public class PositionTemplateUtils {

	public static PositionTemplateFilterData getTemplateFilterData(PositionForm form, String userId){
		PositionTemplateFilterData filterData = new PositionTemplateFilterData();
		filterData.setPositionOwnerId(form.getPositionOwnerId());
		filterData.setDepartmentId(form.getDepartmentId());
		filterData.setSubDepartmentId(form.getSubDepartmentId());		
		filterData.setSubSubDepartmentId(form.getSubSubDepartmentId());
		filterData.setSub3DepartmentId(form.getSub3DepartmentId());
		filterData.setSub4DepartmentId(form.getSub4DepartmentId());
		filterData.setLocationId(form.getLocationId());
		filterData.setPositionName(form.getPositionName());
		filterData.setRecruiterId(form.getRecruiterId());
		filterData.setSkillId(form.getSkillId());
		filterData.setCustomFieldFilterId(form.getCustomFieldFilterId());
		filterData.setCustomFieldFilterType(form.getCustomFieldFilterType());
		filterData.setCustomFieldFilterValue(form.getCustomFieldFilterValue());
		filterData.setUserId(userId);
		return filterData;
	}
}
