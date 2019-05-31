/**
 * 
 */
package com.talentPool.masters.service;

import java.util.List;

import com.talentPool.masters.dataobject.DesignationAliasesData;
import com.talentPool.masters.dataobject.DesignationsData;
import com.talentPool.masters.dataobject.EmployerAliasesData;
import com.talentPool.masters.dataobject.EmployersData;

/**
 * @author Shantanu
 *
 */
public interface IMasterService {
	
	public String getXMLForEmployers();
	public List<EmployersData> getEmployers();
	public List<EmployersData> getEmployer(String employerId);
	public List<EmployerAliasesData> getEmployerAliases(String employerIds);	
	public void updateEmployer(String employerId, String employerName, String[] aliases);
	public void saveEmployer(String employerId, String employerName, String[] aliases);
	public void deleteEmployer(String employerId);
	
	public String getXMLForDesignations();
	public List<DesignationsData> getDesignations();
	public List<DesignationsData> getDesignation(String designationIds);
	public List<DesignationAliasesData> getDesignationAliases(String designationIds);
	public void updateDesignation(String designationId, String designationName, String[] aliases);
	public void saveDesignation(String designationId, String designationName, String[] aliases);
	public void deleteDesignation(String designationId);
}