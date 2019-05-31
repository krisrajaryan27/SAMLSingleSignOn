/**
 * Copyright 2009 - Nitman Software Pvt. Ltd. All Rights Reserved. 
 * This software is the proprietary information of Nitman Software Pvt. Ltd. 
 * Use is subject to license terms.
 */
package com.talentPool.masters.service;

import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;

import com.talentPool.salaryStructure.entity.SalaryComponentCategory;

/**
 * @author praveenk
 * @since  May 9, 2012
 */
public interface ISalCompCategoryService {
	
	/**
	 * Adds or updates a entry for given salaryComponentCatId
	 * @param salaryComponentCatId
	 * @param categoryName
	 */
	public void saveOrUpdate(int salaryComponentCatId, String categoryName) throws HibernateException;
	
	/**
	 * Fetches data for given categoryId.
	 * @param salaryComponentCatId
	 * @return SalaryComponentCategory bean for given salaryComponentCatId. Null if no data found or any sql exception 
	 */
	public SalaryComponentCategory findById(Integer salaryComponentCatId);
	
	/**
	 * Fetches all categories  
	 * @return
	 */
	public List<SalaryComponentCategory> findAll();
	
	public Map<String,SalaryComponentCategory> getSalaryComponentCategoryMap();
	
	/**
	 * Deletes Category with given salaryComponentCatId
	 * @param salaryComponentCatId
	 */
	public void deleteSalCompCategory(int salaryComponentCatId) throws HibernateException;
}
