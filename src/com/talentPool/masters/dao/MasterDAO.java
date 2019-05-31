/**
 * 
 */
package com.talentPool.masters.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.hibernate.utils.HibernateUtil;
import com.talentPool.masters.dataobject.DesignationAliasesData;
import com.talentPool.masters.dataobject.DesignationsData;
import com.talentPool.masters.dataobject.EmployerAliasesData;
import com.talentPool.masters.dataobject.EmployerAliasesId;
import com.talentPool.masters.dataobject.EmployersData;

/**
 * @author Shantanu
 *
 */
public class MasterDAO implements IMasterDAO{
	
	@SuppressWarnings("unchecked")
	@Override
	public List<EmployersData> getEmployers() throws HibernateException	{		
		List<EmployersData> empDataList = null;
		Transaction transaction = null;
		Query query = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		StringBuilder queryString = new StringBuilder("from EmployersData empd ");
		try{
			queryString.append("ORDER BY empd.employerName");
			query = session.createQuery(queryString.toString());
			transaction = session.beginTransaction();
			empDataList = query.list();			
			transaction.commit();
		}catch (Exception e) {
			transaction.rollback();
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			throw e;
		}finally{
			session.close();
		}	
		return empDataList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<EmployersData> getEmployer(Long[] employerIds) throws HibernateException	{		
		List<EmployersData> empDataList = null;
		Transaction transaction = null;
		Query query = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		StringBuilder queryString = new StringBuilder("from EmployersData empd ");
		try{			
			queryString.append("WHERE empd.employerId in (:employersIds)");			
			query = session.createQuery(queryString.toString());
			query.setParameterList("employersIds", employerIds);
			transaction = session.beginTransaction();
			empDataList = query.list(); 
			transaction.commit();
		}catch (Exception e) {
			transaction.rollback();
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			throw e;
		}finally{
			session.close();
		}	
		return empDataList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<EmployersData> getEmployersIds(String[] employerNames) throws HibernateException{		
		List<EmployersData> empDataList = null;
		Transaction transaction = null;
		Query query = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		StringBuilder queryString = new StringBuilder("from EmployersData empd ");
		try{			
			queryString.append("WHERE empd.employerName in (:employersNames)");			
			query = session.createQuery(queryString.toString());
			query.setParameterList("employersNames", employerNames);
			transaction = session.beginTransaction();
			empDataList = query.list(); 
			transaction.commit();
		}catch (Exception e) {
			transaction.rollback();
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			throw e;
		}finally{
			session.close();
		}	
		return empDataList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<EmployersData> getLikeEmployers(String likeParam) throws HibernateException	{		
		List<EmployersData> empDataList = null;
		Transaction transaction = null;
		Query query = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		StringBuilder queryString = new StringBuilder("from EmployersData empd ");
		try{
			queryString.append("WHERE empd.employerName like  (:employersNames)");
			queryString.append("ORDER BY empd.employerName");			
			query = session.createQuery(queryString.toString());
			query.setParameter("employersNames", likeParam +"%");
			transaction = session.beginTransaction();
			empDataList = query.list();			
			transaction.commit();
		}catch (Exception e) {
			transaction.rollback();
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			throw e;
		}finally{
			session.close();
		}	
		return empDataList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<EmployerAliasesData> getEmployersAliases() throws HibernateException	{		
		List<EmployerAliasesData> empAliasesDataList = null;
		Transaction transaction = null;
		Query query = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		StringBuilder queryString = new StringBuilder("from EmployerAliasesData empda ");
		try{			
			query = session.createQuery(queryString.toString());
			transaction = session.beginTransaction();
			empAliasesDataList = query.list(); 
			transaction.commit();
		}catch (Exception e) {
			transaction.rollback();
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			throw e;
		}finally{
			session.close();
		}	
		return empAliasesDataList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<EmployerAliasesData> getEmployerAliases(Long[] employerIds) throws HibernateException{		
		List<EmployerAliasesData> empAliasesDataList = null;
		Transaction transaction = null;
		Query query = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		StringBuilder queryString = new StringBuilder("from EmployerAliasesData empda ");
		try{			
			queryString.append("WHERE empda.id.employerId in (:employersIds)");		
			query = session.createQuery(queryString.toString());
			query.setParameterList("employersIds", employerIds);
			transaction = session.beginTransaction();
			empAliasesDataList = query.list(); 
			transaction.commit();
		}catch (Exception e) {
			transaction.rollback();
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			throw e;
		}finally{
			session.close();
		}	
		return empAliasesDataList;
	}
	

	@SuppressWarnings("unchecked")
	@Override
	public List<EmployerAliasesData> getEmployerAliasesIds(String[] employerNames) throws HibernateException{		
		List<EmployerAliasesData> empAliasesDataList = null;
		Transaction transaction = null;
		Query query = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		StringBuilder queryString = new StringBuilder("from EmployerAliasesData empda ");
		try{			
			queryString.append("WHERE empda.id.alias in (:employersNames)");		
			query = session.createQuery(queryString.toString());
			query.setParameterList("employersNames", employerNames);
			transaction = session.beginTransaction();
			empAliasesDataList = query.list(); 
			transaction.commit();
		}catch (Exception e) {
			transaction.rollback();
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			throw e;
		}finally{
			session.close();
		}	
		return empAliasesDataList;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public Long saveEmployer(EmployersData empData){
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		Long employerId = null;
		try {
			transaction = session.beginTransaction();
			employerId = (Long) session.save(empData);
			empData.setEmployerId(employerId);
			session = saveEmployerAliases(session,empData);
            transaction.commit();
		} catch (HibernateException e) {
			transaction.rollback();
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			session.close();
		}
		return employerId;	
	}
	
	public Session saveEmployerAliases(Session session,EmployersData empData){				
		try {
			for(EmployerAliasesData empAlDat : empData.getEmployerAliasesesData()){
				empAlDat.getId().setEmployerId(empData.getEmployerId());
				empAlDat.getEmployersData().setEmployerId(empData.getEmployerId());
				session.save(empAlDat);
			}
		} catch (HibernateException e) {			
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return session;	
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void updateEmployer(EmployersData empData){
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			EmployersData empDat = (EmployersData)session.load(EmployersData.class, empData.getEmployerId());
			deleteEmployerAliases(session,empDat);
			empDat.setEmployerAliasesesData(empData.getEmployerAliasesesData());
			saveEmployerAliases(session, empDat);
			empDat.setEmployerName(empData.getEmployerName());
			session.update(empDat);
			transaction.commit();
		}catch (HibernateException e) {
			transaction.rollback();
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}finally{
			session.close();
		}
	}
		
	@SuppressWarnings("unchecked")
	@Override
	public void deleteEmployer(Long employerId){
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			EmployersData empData = (EmployersData) session.get(EmployersData.class, employerId);
			session.delete(empData);
			transaction.commit();
		}catch (HibernateException e) {
			transaction.rollback();
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}finally{
			session.close();
		}
	}
	
	public Session deleteEmployerAliases(Session session,EmployersData empData){
		try {
			EmployersData empDat = (EmployersData)session.load(EmployersData.class, empData.getEmployerId());
			for (EmployerAliasesData empl : empDat.getEmployerAliasesesData()) {
					session.delete(empl);
			}
		}catch (HibernateException e) {			
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return session;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<DesignationsData> getDesignations() throws HibernateException	{		
		List<DesignationsData> designationDataList = null;
		Transaction transaction = null;
		Query query = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		StringBuilder queryString = new StringBuilder("from DesignationsData desgd ");
		try{
			queryString.append("ORDER BY desgd.designationName");
			query = session.createQuery(queryString.toString());
			transaction = session.beginTransaction();
			designationDataList = query.list(); 
			transaction.commit();
		}catch (Exception e) {
			transaction.rollback();
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			throw e;
		}finally{
			session.close();
		}	
		return designationDataList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<DesignationsData> getDesignation(Long[] designationIds) throws HibernateException	{		
		List<DesignationsData> designationDataList = null;
		Transaction transaction = null;
		Query query = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		StringBuilder queryString = new StringBuilder("from DesignationsData desgd ");
		try{
			queryString.append("WHERE desgd.designationId in (:designationIds)");			
			query = session.createQuery(queryString.toString());
			query.setParameterList("designationIds", designationIds);
			transaction = session.beginTransaction();
			designationDataList = query.list(); 
			transaction.commit();
		}catch (Exception e) {
			transaction.rollback();
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			throw e;
		}finally{
			session.close();
		}	
		return designationDataList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<DesignationsData> getDesignationsIds(String[] designationNames) throws HibernateException	{		
		List<DesignationsData> designationDataList = null;
		Transaction transaction = null;
		Query query = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		StringBuilder queryString = new StringBuilder("from DesignationsData desgd ");
		try{
			queryString.append("WHERE desgd.designationName in (:designationsNames)");			
			query = session.createQuery(queryString.toString());
			query.setParameterList("designationsNames", designationNames);
			transaction = session.beginTransaction();
			designationDataList = query.list(); 
			transaction.commit();
		}catch (Exception e) {
			transaction.rollback();
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			throw e;
		}finally{
			session.close();
		}	
		return designationDataList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<DesignationsData> getLikeDesignations(String likeParam) throws HibernateException	{		
		List<DesignationsData> designationDataList = null;
		Transaction transaction = null;
		Query query = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		StringBuilder queryString = new StringBuilder("from DesignationsData desgd ");
		try{
			queryString.append("WHERE desgd.designationName like  (:designationsNames)");
			queryString.append("ORDER BY desgd.designationName");
			query = session.createQuery(queryString.toString());
			query.setParameter("designationsNames", likeParam +"%");
			transaction = session.beginTransaction();
			designationDataList = query.list(); 
			transaction.commit();
		}catch (Exception e) {
			transaction.rollback();
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			throw e;
		}finally{
			session.close();
		}	
		return designationDataList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<DesignationAliasesData> getDesignationsAliases() throws HibernateException	{		
		List<DesignationAliasesData> designationAliasesDataList = null;
		Transaction transaction = null;
		Query query = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		StringBuilder queryString = new StringBuilder("from DesignationAliasesData desda ");
		try{			
			query = session.createQuery(queryString.toString());
			transaction = session.beginTransaction();
			designationAliasesDataList = query.list(); 
			transaction.commit();
		}catch (Exception e) {
			transaction.rollback();
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			throw e;
		}finally{
			session.close();
		}	
		return designationAliasesDataList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<DesignationAliasesData> getDesignationAliases(Long[] designationIds) throws HibernateException{		
		List<DesignationAliasesData> designationAliasesDataList = null;
		Transaction transaction = null;
		Query query = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		StringBuilder queryString = new StringBuilder("from DesignationAliasesData desAlda ");
		try{			
			queryString.append("WHERE desAlda.id.designationId in (:designationsIds)");		
			query = session.createQuery(queryString.toString());
			query.setParameterList("designationsIds", designationIds);
			transaction = session.beginTransaction();
			designationAliasesDataList = query.list(); 
			transaction.commit();
		}catch (Exception e) {
			transaction.rollback();
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			throw e;
		}finally{
			session.close();
		}	
		return designationAliasesDataList;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<DesignationAliasesData> getDesignationAliasesIds(String[] designationNames) throws HibernateException{		
		List<DesignationAliasesData> designationAliasesDataList = null;
		Transaction transaction = null;
		Query query = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		StringBuilder queryString = new StringBuilder("from DesignationAliasesData desAlda ");
		try{			
			queryString.append("WHERE desAlda.id.alias in (:designationsNames)");		
			query = session.createQuery(queryString.toString());
			query.setParameterList("designationsNames", designationNames);
			transaction = session.beginTransaction();
			designationAliasesDataList = query.list(); 
			transaction.commit();
		}catch (Exception e) {
			transaction.rollback();
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			throw e;
		}finally{
			session.close();
		}	
		return designationAliasesDataList;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public Long saveDesignation(DesignationsData designationData) throws HibernateException {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		Long designationId = null;
		try {
			transaction = session.beginTransaction();
			designationId = (Long) session.save(designationData);
			designationData.setDesignationId(designationId);
			session = saveDesignationAliases(session,designationData);
            transaction.commit();
		} catch (HibernateException e) {
			transaction.rollback();
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			session.close();
		}
		return designationId;	
	}
	
	public Session saveDesignationAliases(Session session,DesignationsData designationData){				
		try {
			for(DesignationAliasesData designationAlDat : designationData.getDesignationsAliasesesData()){
				designationAlDat.getId().setDesignationId(designationData.getDesignationId());
				designationAlDat.getDesignationsData().setDesignationId(designationData.getDesignationId());
				session.save(designationAlDat);
			}
		} catch (HibernateException e) {			
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return session;	
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void updateDesignation(DesignationsData designationData){
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			DesignationsData desData = (DesignationsData)session.load(DesignationsData.class, designationData.getDesignationId());
			deleteDesignationAliases(session,desData);
			desData.setDesignationsAliasesesData(designationData.getDesignationsAliasesesData());
			saveDesignationAliases(session, desData);
			desData.setDesignationName(designationData.getDesignationName());
			desData.setDesignationId(designationData.getDesignationId());
			session.update(desData);
			transaction.commit();
		}catch (HibernateException e) {
			transaction.rollback();
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}finally{
			session.close();
		}
	}
	
	public void updateEmployer1(EmployersData empData){
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			EmployersData empDat = (EmployersData)session.load(EmployersData.class, empData.getEmployerId());
			deleteEmployerAliases(session,empDat);
			empDat.setEmployerAliasesesData(empData.getEmployerAliasesesData());
			saveEmployerAliases(session, empDat);
			empDat.setEmployerName(empData.getEmployerName());
			session.update(empDat);
			transaction.commit();
		}catch (HibernateException e) {
			transaction.rollback();
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}finally{
			session.close();
		}
	}
	
	
	
	
	
	
	@SuppressWarnings("unchecked")
	@Override
	public void deleteDesignation(Long designationId){
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			DesignationsData designationData = (DesignationsData) session.get(DesignationsData.class, designationId); 
			session.delete(designationData);
			transaction.commit();
		}catch (HibernateException e) {
			transaction.rollback();
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}finally{
			session.close();
		}
	}
	
	public Session deleteDesignationAliases(Session session,DesignationsData designationData){
		try {
			DesignationsData desDat = (DesignationsData)session.load(DesignationsData.class, designationData.getDesignationId());
			for (DesignationAliasesData desAl : desDat.getDesignationsAliasesesData()) {
					session.delete(desAl);
			}
		}catch (HibernateException e) {			
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return session;
	}
}