package com.talentPool.customReports.queryBuilder;

import com.talentPool.customReports.exception.QueryBuilderException;
import com.talentPool.customReports.jaxb.CustomReport;

public interface IQueryBuilder {
	
	/**
	 * Build the query based on the details provided by {@link CustomReport}. 
	 * @param customReport 
	 * @return
	 */
	StringBuilder build(final CustomReport customReport) throws QueryBuilderException;
}
