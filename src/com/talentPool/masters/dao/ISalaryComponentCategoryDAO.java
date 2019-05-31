/**
 * Copyright 2009 - Nitman Software Pvt. Ltd. All Rights Reserved. 
 * This software is the proprietary information of Nitman Software Pvt. Ltd. 
 * Use is subject to license terms.
 */
package com.talentPool.masters.dao;

import java.util.List;

import org.hibernate.HibernateException;

import com.talentPool.salaryStructure.entity.SalaryComponentCategory;

/**
 * @author PraveenK
 * @since  Apr 23, 2012
 */
public interface ISalaryComponentCategoryDAO {
	
	public void saveOrUpdate(SalaryComponentCategory salaryComponentCat) throws HibernateException;
	public SalaryComponentCategory findById(int salaryComponentCatId) throws HibernateException;
	public List<SalaryComponentCategory> findAll() throws HibernateException;
	public void deleteSalaryCompCategory(int salaryComponentCatId) throws HibernateException;

}
