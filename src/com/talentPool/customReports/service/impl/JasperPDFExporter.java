/**
 * 
 */
package com.talentPool.customReports.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.sql.SQLException;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;

import com.talentPool.customReports.dataobject.RunCustomReportModel;
import com.talentPool.customReports.service.ICRDataSourceService;
import com.talentPool.dynamicReports.utils.ReportExporter;

/**
 * @author PraveenK
 * @since  Nov 29, 2011
 */
public class JasperPDFExporter extends JasperExporter {
	
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
		ReportExporter.exportReportPdf(jasperPrint, outputFileName);
		return outputFileName;
	}

	/* (non-Javadoc)
	 * @see com.talentPool.customReports.service.ICRExporter#exportReport(java.lang.String, java.lang.String, java.lang.String[], java.io.OutputStream)
	 */
	@Override
	public ByteArrayOutputStream exportReport(RunCustomReportModel rcrm) throws Exception {
		JasperPrint jasperPrint = generateReport(rcrm);
		return exportReportXlsStream(jasperPrint);
	}
	
	public static ByteArrayOutputStream exportReportXlsStream(JasperPrint jasperPrint) throws JRException, FileNotFoundException {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//		JRExporter exporter = new JRPdfExporter();
//		exporter.setParameter(JRExporterParameter.JASPER_PRINT_LIST, jasperPrint.getPages());
//		exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outputStream);
//		exporter.exportReport();
		
		JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
		return outputStream;
	}

}
