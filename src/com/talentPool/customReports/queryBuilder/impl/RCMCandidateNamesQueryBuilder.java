/**
 * Copyright 2009 - Nitman Software Pvt. Ltd. All Rights Reserved. 
 * This software is the proprietary information of Nitman Software Pvt. Ltd. 
 * Use is subject to license terms.
 */
package com.talentPool.customReports.queryBuilder.impl;

import java.util.Map;

import com.talentPool.customReports.dataobject.CRColumn;
import com.talentPool.customReports.dataobject.CRTableType;
import com.talentPool.customReports.exception.QueryBuilderException;
import com.talentPool.customReports.jaxb.CustomReport;

/**
 * @author PraveenK
 * @since  Dec 30, 2011
 */
public class RCMCandidateNamesQueryBuilder extends RCMQueryBuilder {

	/**
	 * @param columnsMap
	 */
	public RCMCandidateNamesQueryBuilder(Map<String, CRColumn> columnsMap, Map<Long,CRTableType> tableTypeMap) {
		super(columnsMap, tableTypeMap);
	}
	
	/* (non-Javadoc)
	 * @see com.talentPool.customReports.queryBuilder.IQueryBuilder#build(com.talentPool.customReports.jaxb.CustomReport)
	 */
	@Override
	public StringBuilder build(CustomReport customReport)
			throws QueryBuilderException {
		RCMCountQueryBuilder rcmCountQueryBuilder = new RCMCountQueryBuilder(columnsMap, tableTypeMap);
		return rcmCountQueryBuilder.build(customReport);
	}
}