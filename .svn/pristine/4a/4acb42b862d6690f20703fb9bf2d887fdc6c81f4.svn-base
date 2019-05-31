/**
 * Copyright 2009 - Nitman Software Pvt. Ltd. All Rights Reserved. 
 * This software is the proprietary information of Nitman Software Pvt. Ltd. 
 * Use is subject to license terms.
 */
package com.talentPool.masters.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.hibernate.utils.HibernateUtil;
import com.talentPool.masters.dao.ISalaryComponentCategoryDAO;
import com.talentPool.salaryStructure.entity.SalaryComponentCategory;

/**
 * @author PraveenK
 * @since  Apr 23, 2012
 */
public class SalaryComponentCategoryDAOImpl implements ISalaryComponentCategoryDAO {

	/* (non-Javadoc)
	 * @see com.talentPool.salaryStructure.dao.SalaryComponentCategoryDAO#saveOrUpdate(com.talentPool.salaryStructure.entity.SalaryComponentCategory)
	 */
	@Override
	public void saveOrUpdate(SalaryComponentCategory salaryComponentCat) throws HibernateException {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			if(salaryComponentCat.getCategoryId()==0)
				session.save(salaryComponentCat);
			else	
				session.update(salaryComponentCat);
            transaction.commit();
		} catch (HibernateException e) {
			transaction.rollback();
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			throw e;
		} finally {
			session.close();
		}			
	}

	/* (non-Javadoc)
	 * @see com.talentPool.salaryStructure.dao.SalaryComponentCategoryDAO#findById(java.lang.Integer)
	 */
	@Override
	public SalaryComponentCategory findById(int salaryComponentCatId) throws HibernateException{
		SalaryComponentCategory salCompCategory = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();			
			Query query = session.createQuery("from SalaryComponentCategory where categoryId=:categoryId");
			query.setParameter("categoryId", salaryComponentCatId);
			salCompCategory = (SalaryComponentCategory) query.list().get(0);
            transaction.commit();
		} catch (HibernateException e) {
			transaction.rollback();
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			throw e;
		} finally {
			session.close();
		}
		return salCompCategory;
	}

	 /*(non-Javadoc)
	  @see com.talentPool.salaryStxjructure.dao.SalaryComponentCategoryDAO#findAll()
	 */
	@Override
	public List<SalaryComponentCategory> findAll() {
		List<SalaryComponentCategory> salCompList = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		try {
			Criteria crit = session.createCriteria(SalaryComponentCategory.class);
			crit.addOrder(Order.asc("categoryName") );
			transaction = session.beginTransaction();		
			salCompList= crit.list();
            transaction.commit();
		} catch (HibernateException e) {
			transaction.rollback();
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			throw e;
		} finally {
			session.close();
		}			
		return salCompList;
	}
	
	/* (non-Javadoc)
	 * @see com.talentPool.masters.dao.ISalaryComponentCategoryDAO#deleteSalaryCompCategory(int)
	 */
	@Override
	public void deleteSalaryCompCategory(int salaryComponentCatId) throws HibernateException {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			SalaryComponentCategory salaryComponentCategory = (SalaryComponentCategory)session.load(SalaryComponentCategory.class, salaryComponentCatId);
			session.delete(salaryComponentCategory);
            transaction.commit();
		} catch (HibernateException e) {
			transaction.rollback();
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			throw e;
		} finally {
			session.close();
		}
		
	}
	
	public static void main(String[] args) {
		SalaryComponentCategoryDAOImpl sccdi = new SalaryComponentCategoryDAOImpl();
		System.out.println(sccdi.findAll()); 
	}

}
