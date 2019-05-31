package com.talentPool.export.bo;

import java.util.ArrayList;

import com.talentPool.common.properties.GlobalApplicationProperties;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.properties.TPLabels;
import com.talentPool.custom.constants.CustomFieldConstants;
import com.talentPool.custom.dataobject.CustomFieldData;
import com.talentPool.custom.manager.CustomFieldManager;

/**
 * @author SumeetS
 *
 */
public class PositionFields extends Fields{
	
	public PositionFields(){
		setHeaders(new ArrayList<Field>());
		int counter =0;
		getHeaders().add(new Field(("fld" + counter++), TPLabels.getLabel("exportToExcel.position.label.name")));
		getHeaders().add(new Field(("fld" + counter++), TPLabels.getLabel("exportToExcel.position.label.department")));
		getHeaders().add(new Field(("fld" + counter++), TPLabels.getLabel("exportToExcel.position.label.vacancies")));
		getHeaders().add(new Field(("fld" + counter++), TPLabels.getLabel("exportToExcel.position.label.inProcess")));
		getHeaders().add(new Field(("fld" + counter++), TPLabels.getLabel("exportToExcel.position.label.pendingOffers")));
		getHeaders().add(new Field(("fld" + counter++), TPLabels.getLabel("exportToExcel.position.label.joined")));
		getHeaders().add(new Field(("fld" + counter++), TPLabels.getLabel("exportToExcel.position.label.rejected")));
		getHeaders().add(new Field(("fld" + counter++), TPLabels.getLabel("exportToExcel.position.label.hireByDate")));
		getHeaders().add(new Field(("fld" + counter++), TPLabels.getLabel("exportToExcel.position.label.subDepartment")));
		getHeaders().add(new Field(("fld" + counter++), TPLabels.getLabel("exportToExcel.position.label.positionCode")));
		getHeaders().add(new Field(("fld" + counter++), TPLabels.getLabel("exportToExcel.position.label.positionType")));
		getHeaders().add(new Field(("fld" + counter++), TPLabels.getLabel("exportToExcel.position.label.positionOwner")));
		getHeaders().add(new Field(("fld" + counter++), TPLabels.getLabel("exportToExcel.position.label.grade")));
		getHeaders().add(new Field(("fld" + counter++), TPLabels.getLabel("exportToExcel.position.label.band")));
		getHeaders().add(new Field(("fld" + counter++), TPLabels.getLabel("exportToExcel.position.label.location")));
		getHeaders().add(new Field(("fld" + counter++), TPLabels.getLabel("exportToExcel.position.label.positionStatus")));//16
		
		getHeaders().add(new Field(("fld" + counter++), GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_DEPARTMENT_LEVEL_3)));
		getHeaders().add(new Field(("fld" + counter++), GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_DEPARTMENT_LEVEL_4)));
		getHeaders().add(new Field(("fld" + counter++), GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_DEPARTMENT_LEVEL_5)));
		//added for Requisition name and position creation date
		
		getHeaders().add(new Field(("fld" + counter++), TPLabels.getLabel("exportToExcel.position.label.requisitionDate")));
		getHeaders().add(new Field(("fld" + counter++), TPLabels.getLabel("exportToExcel.position.label.requisitionBy")));
		
		getHeaders().add(new Field(("fld" + counter++), TPLabels.getLabel("position.description.replacement_emp_code")));
		getHeaders().add(new Field(("fld" + counter++), TPLabels.getLabel("position.requirements.primary_skills")));
		getHeaders().add(new Field(("fld" + counter++), TPLabels.getLabel("position.requirements.secondary_skills")));
		getHeaders().add(new Field(("fld" + counter++), TPLabels.getLabel("position.requirements.minimum_education")));
		getHeaders().add(new Field(("fld" + counter++), TPLabels.getLabel("position.requirements.experience")));
		getHeaders().add(new Field(("fld" + counter++), TPLabels.getLabel("exportToExcel.position_date_closed")));
		getHeaders().add(new Field(("fld" + counter++), TPLabels.getLabel("position.description.type_of_vacancy")));
		getHeaders().add(new Field(("fld" + counter++), TPLabels.getLabel("position.closed_by")));
		getHeaders().add(new Field(("fld" + counter++), TPLabels.getLabel("hire.label.joined_candidates")));
		getHeaders().add(new Field(("fld" + counter++), TPLabels.getLabel("position.num_of_attachments")));
		getHeaders().add(new Field(("fld" + counter++), TPLabels.getLabel("position.all_hiring_process_users")));//32
		
		ArrayList<CustomFieldData> customFields = null;
		if (CustomFieldManager.isCustomFieldsAvailable(CustomFieldConstants.ENTITY_TYPE_POSITION)) {
			CustomFieldManager customFieldManager = new CustomFieldManager();
			customFields = customFieldManager.getCustomFieldsForInputAllowed(CustomFieldConstants.ENTITY_TYPE_POSITION, CustomFieldConstants.INPUT_ALLOWED, true);
			for (CustomFieldData data:customFields){
				getHeaders().add(new Field(("fld" + counter++), data.getFieldDisplayName()));
			}
		}
	}
}
