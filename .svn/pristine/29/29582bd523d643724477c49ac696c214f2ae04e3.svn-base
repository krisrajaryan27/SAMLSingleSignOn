/**
 * 
 */
package com.talentPool.customReports.dao.impl;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.constants.DateConstants;
import com.talentPool.common.db.DBPreparedQuery;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.utils.Utils;
import com.talentPool.customReports.dao.ICustomReportDAO;
import com.talentPool.customReports.dataobject.CRColumn;
import com.talentPool.customReports.dataobject.CRFilters;
import com.talentPool.customReports.dataobject.CRReportTypeColumnMapping;
import com.talentPool.customReports.dataobject.CRReportTypes;
import com.talentPool.customReports.dataobject.CRTableType;
import com.talentPool.customReports.dataobject.CustomReportData;
import com.talentPool.customReports.dataobject.CustomReportDetails;
import com.talentPool.customReports.dataobject.CustomReportModel;
import com.talentPool.hibernate.utils.HibernateUtil;
import com.talentPool.stepsMigration.constants.StepsMigrationWizardConstants;
import com.talentPool.stepsMigration.dao.impl.StepsMigrationWizardDao;
import com.talentPool.stepsMigration.services.impl.StepsMigrationWizardService;

/**
 * @author PraveenK
 * @since Nov 8, 2011
 */
public class CustomReportDAO implements ICustomReportDAO {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.talentPool.poolReports.dao.IReportTemplateDAO#getReportColumns()
	 */
	@Override
	public List<CRColumn> getReportColumnsList() throws HibernateException {
		List<CRColumn> reportColumnList = null;
		Query query = null;
			Session session = HibernateUtil.getSessionFactory().openSession();
			Transaction transaction = null;
			StringBuilder queryString = new StringBuilder("from CRColumn crc ");
			try {
				queryString.append(" ORDER BY crc.columnDisplayName");
				query = session.createQuery(queryString.toString());
				transaction = session.beginTransaction();			
				reportColumnList = query.list();
	            transaction.commit();
			} catch (HibernateException e) {
				transaction.rollback();
				TPLogger.getLogger().error(GlobalConstants.ERROR, e);
				throw e;
			} finally {
				session.close();
			}			
		return reportColumnList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.talentPool.poolReports.dao.IReportTemplateDAO#getReportColumns(java
	 * .lang.String)
	 */
	@Override
	public List<CRColumn> getReportColumnsList(boolean activeCols) { 
		List<CRColumn> reportColumnList = null;
		Query query = null;
			Session session = HibernateUtil.getSessionFactory().openSession();
			Transaction transaction = null;
			StringBuilder queryString = new StringBuilder("from CRColumn crc ");
			try {
				queryString.append(" where crc.isActive = :activeCols");
				queryString.append(" AND crc.columnProperty in (:columnProperty)");				
				query = session.createQuery(queryString.toString());
				query.setParameter("activeCols", activeCols);				
				transaction = session.beginTransaction();			
				reportColumnList = query.list();
	            transaction.commit();
			} catch (HibernateException e) {
				transaction.rollback();
				TPLogger.getLogger().error(GlobalConstants.ERROR, e);
				throw e;
			} finally {
				session.close();
			}			
		return reportColumnList;
	}
	
	/* (non-Javadoc)
	 * @see com.talentPool.customReports.dao.ICustomReportDAO#getActiveReportColumnsList()
	 */
	@Override
	public List<CRColumn> getActiveReportColumnsList() throws HibernateException { 
		return getReportColumnsList(true);
	}
	
	/* (non-Javadoc)
	 * @see com.talentPool.customReports.dao.ICustomReportDAO#getReportColumns(java.lang.String)
	 */
	@Override
	public List<CRColumn> getReportColumns(String columnIds) {
		List<CRColumn> reportColumnList = null;
		Query query = null;
		if(!Utils.isBlankOrNull(columnIds)){
			Session session = HibernateUtil.getSessionFactory().openSession();
			Transaction transaction = null;
			try {
				String queryString = "from CRColumn crc where crc.columnProperty in (:columnPropertyNames)";
				query = session.createQuery(queryString);
				query.setParameterList("columnPropertyNames", columnIds.split(","));
				transaction = session.beginTransaction();
				reportColumnList = query.list();
	            transaction.commit();
			} catch (HibernateException e) {
				transaction.rollback();
				TPLogger.getLogger().error(GlobalConstants.ERROR, e);
				throw e;
			} finally {
				session.close();
			}			
		}
		return reportColumnList;
	}

	/* (non-Javadoc)
	 * @see com.talentPool.customReports.dao.ICustomReportDAO#saveCustomReport(com.talentPool.customReports.dataobject.CustomReportDetails, java.lang.String)
	 */	
	public void saveCustomReport(CustomReportDetails crd, String createdBy)
			throws SQLException {
		
		// TODO add transatcion
		DBPreparedQuery dq = null;
		try {
			int cnt = 1;
			dq = new DBPreparedQuery("dAdminManager_SaveCustomReportDetails");
			dq.setString(cnt++, crd.getReportName());
			dq.setString(cnt++, crd.getReportDesc());
			dq.setString(cnt++, crd.getCreatedBy());
			dq.setTimestamp(cnt++, crd.getDateCreated());
			dq.setString(cnt++, crd.getCreatedBy());
			dq.setTimestamp(cnt++,crd.getDateModified());
			dq.setString(cnt++, crd.getXmlFilePath());
			dq.execute();
		} catch (SQLException sqle) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, sqle);
			throw sqle;
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
	}
	
	public Long saveCustomReport(CustomReportDetails reportData) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		Long reportId = null;
		try {
			transaction = session.beginTransaction();
			reportId = (Long) session.save(reportData);
            transaction.commit();
		} catch (HibernateException e) {
			transaction.rollback();
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			session.close();
		}
		return reportId;
	}
	
	public List<CustomReportDetails> fetchCustomReportList() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		List<CustomReportDetails> reportList = null;
		try {
			transaction = session.beginTransaction();			
			Query query = session.createQuery("from CustomReportDetails");
			reportList = query.list();
            transaction.commit();
		} catch (HibernateException e) {
			transaction.rollback();
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			session.close();
		}
		return reportList;
	}
	
	public List<CustomReportDetails> fetchCustomReportListForUser(String userId) {
		DBPreparedQuery dq = null;
		List<CustomReportData> crReportList = new ArrayList<CustomReportData>();
		List<CustomReportDetails>reportList = new ArrayList<CustomReportDetails>();
		try {
			dq = new DBPreparedQuery("dCustomReportManager_getCustomReportListForUser");
			dq.setString(1, userId);
			dq.setString(2, userId);
			crReportList = (ArrayList<CustomReportData>) dq.getResult();
			
			for (CustomReportData crData : crReportList) {
				CustomReportDetails crDetails = new CustomReportDetails();
				crDetails.setReportId(Long.parseLong(crData.getReportId()));
				crDetails.setReportName(crData.getReportName());
				reportList.add(crDetails);
			}
			
		} catch (SQLException sqle) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, sqle);
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return reportList;
	}
	
	public void deleteCustomReport(Long reportId) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			CustomReportDetails crd = (CustomReportDetails)session.get(CustomReportDetails.class, reportId);
			session.delete(crd);
            transaction.commit();
		} catch (HibernateException e) {
			transaction.rollback();
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			session.close();
		}
	}

	public void updateCRFilePath(Long reportId, String xmlFilePath, String templateFilePath, String queryFilePath, String inactivePositionsQueryFilePath) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			CustomReportDetails reportData = (CustomReportDetails) session.get(CustomReportDetails.class, reportId);
			reportData.setXmlFilePath(xmlFilePath);
			reportData.setTemplateFilePath(templateFilePath);
			reportData.setQueryFilePath(queryFilePath);
			reportData.setInactivePositionsQueryFilePath(inactivePositionsQueryFilePath);
            transaction.commit();
		} catch (HibernateException e) {
			transaction.rollback();
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			session.close();
		}
	}
	
	@Override
	public void updateCustomReport(Long reportId, String title, String desc, String modifiedBy) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			CustomReportDetails reportData = (CustomReportDetails) session.get(CustomReportDetails.class, reportId);
			reportData.setReportName(title);
			reportData.setReportDesc(desc);
			reportData.setModifiedBy(modifiedBy);
			reportData.setDateModified(new Timestamp(new Date().getTime()));
			session.update(reportData);
            transaction.commit();
		} catch (HibernateException e) {
			transaction.rollback();
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			session.close();
		}
	}
	
	/* (non-Javadoc)
	 * @see com.talentPool.customReports.dao.ICustomReportDAO#getCustomReport(java.lang.String)
	 */
	@Override
	public CustomReportDetails getCustomReport(String reportId) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		CustomReportDetails customReportDetails = null;
		try {
			transaction = session.beginTransaction();			
			Query query = session.createQuery("from CustomReportDetails where reportId=:reportId");
			query.setParameter("reportId", Long.parseLong(reportId));
			customReportDetails = (CustomReportDetails)query.list().get(0);
            transaction.commit();
		} catch (HibernateException e) {
			transaction.rollback();
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			session.close();
		}
		return customReportDetails;
	}

	/* (non-Javadoc)
	 * @see com.talentPool.customReports.dao.ICustomReportDAO#getTableTypeMap()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<CRTableType> getTableTypeList() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		List<CRTableType> crTableTypeLst = null;
		try {
			transaction = session.beginTransaction();			
			crTableTypeLst = session.createQuery("from CRTableType").list();
            transaction.commit();
		} catch (HibernateException e) {
			transaction.rollback();
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			session.close();
		}
		return crTableTypeLst;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<CRFilters> getCustomReportFilters(CustomReportModel reportModel){
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		List crFiltersObjList=new ArrayList();
		List<CRFilters> crFiltersList=new ArrayList<CRFilters>();
		Query query = null;		
		try{			
			//crFiltersList = session.createQuery("FROM CRFilters").list();left outer join
			String queryString = ("select distinct crfilters0_.filter_id as col_0_0_,  crfilters0_.filter_name as col_1_0_ from tp_cr_filters crfilters0_ left join tp_cr_column_filter_map crcolumnfi1_ on (crfilters0_.filter_id=crcolumnfi1_.filter_id) where crcolumnfi1_.column_property in (:columnProperties) ");
			//String queryString = ("FROM CRColumnFilterMap crfm WHERE crfm.columnPropertyIds IN (:columnProperties) ");
			//String queryString = ("FROM CRFilters");
			query = session.createSQLQuery(queryString);
			Object[] arr1 = reportModel.getColumnIds().split(",");
			Object[] arr2 = reportModel.getRowIds().split(",");
			Object[] arr3 = reportModel.getMeasureIds().split(",");
			Object[] main = ArrayUtils.addAll(arr1, arr2); 
			main = ArrayUtils.addAll(main, arr3);
			query.setParameterList("columnProperties", main);		
			transaction = session.beginTransaction();
			crFiltersObjList = (ArrayList<Object>)query.list();			
			transaction.commit();
			
			for(Object obj: crFiltersObjList){
				Object[] crf = (Object[])obj;
				CRFilters crf1 = new CRFilters();
				crf1.setFilterId(Integer.valueOf((Byte)crf[0]));
				crf1.setFilterName((String)crf[1]);
				crFiltersList.add(crf1); 
			}
            
		}catch (HibernateException e) {
			transaction.rollback();
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}catch (Exception e) {
			System.out.println(e);
		} finally {
			session.close();
		}		
		return crFiltersList;
	}
	
	public Date getLastRunDate() {		
		// do not show last run time until migration is completed (at least for open positions)
		StepsMigrationWizardDao stepsMigrationWizardDao = new StepsMigrationWizardDao();
		StepsMigrationWizardService stepsMigrationWizardService = new StepsMigrationWizardService();
		stepsMigrationWizardService.setStepsMigrationWizardDao(stepsMigrationWizardDao);
		Map<String, String> migrationStatusMap = stepsMigrationWizardService.getMigrationStatus();
										
		if (migrationStatusMap.get("open").equals(StepsMigrationWizardConstants.MIGRATION_STATUS_PENDING)) {
			TPLogger.getLogger().info("migration not completed, no master data available");
			return null;
		}			
				
		DBPreparedQuery dq = null;
		Date lastRunDate = null;
		try {
			dq = new DBPreparedQuery("dReportTableManager_GetLastRunDate");
			String lastRunDateStr = dq.getIdResult();
			lastRunDate = Utils.convertToDate(lastRunDateStr, DateConstants.DB_DATE_TIME_PATTERN);
		} catch (SQLException sqle) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, sqle);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return lastRunDate;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public CRReportTypes getCRReportTypes(String reportTypeId) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		CRReportTypes crReportType = null;
		try {
			transaction = session.beginTransaction();
			Query query = session.createQuery("from CRReportTypes WHERE crReportTypeId=:reportTypeId");
			query.setParameter("reportTypeId", Long.parseLong(reportTypeId));
			crReportType=(CRReportTypes)query.list().get(0);
            transaction.commit();
		} catch (HibernateException e) {
			transaction.rollback();
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			session.close();
		}
		return crReportType;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<CRReportTypes> getCRReportTypes() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		List<CRReportTypes> crReportTypes = null;
		try {
			transaction = session.beginTransaction();			
			crReportTypes = session.createQuery("from CRReportTypes").list();
            transaction.commit();
		} catch (HibernateException e) {
			transaction.rollback();
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			session.close();
		}
		return crReportTypes;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<CRReportTypeColumnMapping> getCRReportTypeColumnMap(String reportTypeId) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;		
		List<CRReportTypeColumnMapping> crReportTypeColMap = null;		
		long repTypId=Long.parseLong(reportTypeId);
		try {
			transaction = session.beginTransaction();			
			Query query = session.createQuery("from CRReportTypeColumnMapping WHERE id.crReportTypeId = :rprtTypId");
			query.setParameter("rprtTypId", repTypId);		
			crReportTypeColMap = query.list();
            transaction.commit();
		} catch (HibernateException e) {
			transaction.rollback();
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			session.close();
		}
		return crReportTypeColMap;
	}
		
	public List<CRColumn> getReportColumnsList(boolean activeCols, String columnProperties) { 
		List<CRColumn> reportColumnList = null;
		Query query = null;
			Session session = HibernateUtil.getSessionFactory().openSession();
			Transaction transaction = null;
			StringBuilder queryString = new StringBuilder("from CRColumn crc ");
			try {
				queryString.append(" where crc.isActive = :activeCols");
				queryString.append(" AND crc.columnProperty in (:columnProperty)");
				queryString.append(" ORDER BY crc.columnDisplayName");
				query = session.createQuery(queryString.toString());
				query.setParameter("activeCols", activeCols);
				query.setParameterList("columnProperty", columnProperties.split(","));
				transaction = session.beginTransaction();			
				reportColumnList = query.list();
	            transaction.commit();
			} catch (HibernateException e) {
				transaction.rollback();
				TPLogger.getLogger().error(GlobalConstants.ERROR, e);
				throw e;
			} finally {
				session.close();
			}			
		return reportColumnList;
	}
}