/**
 * Copyright 2009 - Nitman Software Pvt. Ltd. All Rights Reserved. 
 * This software is the proprietary information of Nitman Software Pvt. Ltd. 
 * Use is subject to license terms.
 */
package com.talentPool.masters.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.utils.Utils;
import com.talentPool.masters.dao.ISalaryComponentCategoryDAO;
import com.talentPool.masters.service.ISalCompCategoryService;
import com.talentPool.salaryStructure.entity.SalaryComponentCategory;

/**
 * @author praveenk
 * @since  May 9, 2012
 */
public class SalCompCategoryService implements ISalCompCategoryService {
	
	private ISalaryComponentCategoryDAO _salCompCategoryDao;
	
	/**
	 * @return the _salCompCategoryDao
	 */
	public ISalaryComponentCategoryDAO getSalCompCategoryDao() {
		return _salCompCategoryDao;
	}

	/**
	 * @param _salCompCategoryDao the _salCompCategoryDao to set
	 */
	public void setSalCompCategoryDao(ISalaryComponentCategoryDAO salCompCategoryDao) {
		this._salCompCategoryDao = salCompCategoryDao;
	}

	/* (non-Javadoc)
	 * @see com.talentPool.masters.service.ISalCompCategoryService#saveOrUpdate(int, java.lang.String)
	 */
	@Override
	public void saveOrUpdate(int salaryComponentCatId, String categoryName) throws HibernateException {
		try {
			SalaryComponentCategory salaryComponentCategory = new SalaryComponentCategory();
			salaryComponentCategory.setCategoryId(salaryComponentCatId);
			salaryComponentCategory.setCategoryName(categoryName);
			_salCompCategoryDao.saveOrUpdate(salaryComponentCategory);
		} catch (HibernateException e) {
			TPLogger.getLogger().error("Error while saving data for categoryId: "+salaryComponentCatId+" and categoryName: "+ categoryName , e);
			throw e;
		}
	}

	/* (non-Javadoc)
	 * @see com.talentPool.masters.service.ISalCompCategoryService#findById(java.lang.Integer)
	 */
	@Override
	public SalaryComponentCategory findById(Integer salaryComponentCatId){
		try {
			return _salCompCategoryDao.findById(salaryComponentCatId);			
		} catch (HibernateException e) {
			return null;
		}
	}

	/* (non-Javadoc)
	 * @see com.talentPool.masters.service.ISalCompCategoryService#findAll()
	 */
	@Override
	public List<SalaryComponentCategory> findAll() {
		try {
			return _salCompCategoryDao.findAll();			
		} catch (HibernateException e) {
			return null;
		}
	}
	
	/* (non-Javadoc)
	 * @see com.talentPool.masters.service.ISalCompCategoryService#findAll()
	 */
	@Override
	public Map<String,SalaryComponentCategory> getSalaryComponentCategoryMap() {
		Map<String,SalaryComponentCategory> salCompCatMap = null;
		try {
			List<SalaryComponentCategory> salCompCatLst = _salCompCategoryDao.findAll();
			if(!Utils.isListEmptyOrNull(salCompCatLst)){
				salCompCatMap = new HashMap<>();
				for (SalaryComponentCategory salaryComponentCategory : salCompCatLst) {
					salCompCatMap.put(""+salaryComponentCategory.getCategoryId(), salaryComponentCategory);
				}					
			}
		} catch (HibernateException e) {
			return null;
		}
		return salCompCatMap;
	}

	/* (non-Javadoc)
	 * @see com.talentPool.masters.service.ISalCompCategoryService#deleteSalCompCategory(int)
	 */
	@Override
	public void deleteSalCompCategory(int salaryComponentCatId) throws HibernateException {
		_salCompCategoryDao.deleteSalaryCompCategory(salaryComponentCatId);			
	}

}
