package com.talentPool.customReports.exception;

import ar.com.fdvs.dj.domain.DynamicReport;

/**
 * Exception thrown when any problem in building {@link DynamicReport}  
 * @author PraveenK
 * @since  Nov 7, 2011
 */
public class DynamicReportGenerationException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public DynamicReportGenerationException() {
	}

	public DynamicReportGenerationException(String msg) {
		super(msg);
	}
}
