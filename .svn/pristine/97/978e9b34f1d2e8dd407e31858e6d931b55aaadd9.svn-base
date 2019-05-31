/**
 * 
 */
package com.talentPool.masters.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.talentPool.masters.dataobject.DesignationAliasesData;
import com.talentPool.masters.dataobject.DesignationsData;
import com.talentPool.masters.dataobject.EmployerAliasesData;
import com.talentPool.masters.dataobject.EmployersData;

/**
 * @author Shantanu
 *
 */
public interface IMasterDAO {
	
	public List<EmployersData> getEmployers() throws HibernateException;
	public List<EmployersData> getEmployer(Long[] employerIds) throws HibernateException;	
	public List<EmployersData> getEmployersIds(String[] employerNames) throws HibernateException;
	public List<EmployersData> getLikeEmployers(String likeParam) throws HibernateException;
	public List<EmployerAliasesData> getEmployersAliases() throws HibernateException;
	public List<EmployerAliasesData> getEmployerAliases(Long[] employerIds) throws HibernateException;	
	public List<EmployerAliasesData> getEmployerAliasesIds(String[] employerNames) throws HibernateException;
	public Long saveEmployer(EmployersData empData) throws HibernateException;	
	public void updateEmployer(EmployersData empData);	
	public void deleteEmployer(Long employerId);	

	public List<DesignationsData> getDesignations() throws HibernateException;	
	public List<DesignationsData> getDesignation(Long[] designationIds) throws HibernateException;
	public List<DesignationsData> getDesignationsIds(String[] designationNames) throws HibernateException;
	public List<DesignationsData> getLikeDesignations(String likeParam) throws HibernateException;
	public List<DesignationAliasesData> getDesignationsAliases() throws HibernateException;
	public List<DesignationAliasesData> getDesignationAliases(Long[] designationIds) throws HibernateException;
	public List<DesignationAliasesData> getDesignationAliasesIds(String[] designationIds) throws HibernateException;
	public Long saveDesignation(DesignationsData designationData) throws HibernateException;
	public void updateDesignation(DesignationsData designationData);
	public void deleteDesignation(Long designationId);
	
}
