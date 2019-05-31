/**
 * 
 */
package com.talentPool.customReports.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.sql.SQLException;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;

import com.talentPool.customReports.dataobject.RunCustomReportModel;
import com.talentPool.customReports.service.ICRDataSourceService;
import com.talentPool.dynamicReports.utils.ReportExporter;
import com.talentPool.reports.ReportConstants;

/**
 * @author PraveenK
 * @since  Nov 29, 2011
 */
public class JasperHTMLExporter extends JasperExporter {
	
	/**
	 * @param _crDataSourceService the _crDataSourceService to set
	 */
	public void setCrDataSourceService(ICRDataSourceService _crDataSourceService) {
		super.setCrDataSourceService(_crDataSourceService);
	}

	/* (non-Javadoc)
	 * @see com.talentPool.customReports.service.ICRExporter#exportReport(java.lang.String, java.lang.String, java.lang.String[])
	 */
	@Override
	public String exportReport(RunCustomReportModel rcrm, String outputFileName) throws JRException, FileNotFoundException, SQLException {
		JasperPrint jasperPrint = generateReport(rcrm);
		outputFileName = outputFileName+".html";
		ReportExporter.exportReportHtml(jasperPrint, outputFileName, null);
		return outputFileName;
	}

	/* (non-Javadoc)
	 * @see com.talentPool.customReports.service.ICRExporter#exportReport(java.lang.String, java.lang.String, java.lang.String[], java.io.OutputStream)
	 */
	@Override
	public ByteArrayOutputStream exportReport(RunCustomReportModel rcrm) throws Exception {
		JasperPrint jasperPrint = generateReport(rcrm);
		return exportReportHtmlStream(jasperPrint);
	}
	
	public static ByteArrayOutputStream exportReportHtmlStream(JasperPrint jasperPrint) throws JRException, FileNotFoundException {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		String htmlHeader = ReportConstants.DEFAULT_REPORT_HTML_HEADER;
		String htmlFooter = "";
		JRExporter exporter = new JRHtmlExporter();
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outputStream);
		exporter.setParameter(JRHtmlExporterParameter.IS_WRAP_BREAK_WORD, Boolean.TRUE);
		exporter.setParameter(JRHtmlExporterParameter.SIZE_UNIT, "px");
		exporter.setParameter(JRHtmlExporterParameter.HTML_HEADER, htmlHeader);
		exporter.setParameter(JRHtmlExporterParameter.HTML_FOOTER, htmlFooter);
		exporter.exportReport();
		return outputStream;
	}

}
