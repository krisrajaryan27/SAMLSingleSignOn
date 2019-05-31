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
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;

import com.talentPool.common.utils.Utils;
import com.talentPool.customReports.dataobject.RunCustomReportModel;
import com.talentPool.customReports.service.ICRDataSourceService;
import com.talentPool.reports.ReportConstants;

/**
 * @author PraveenK
 * @since  Nov 29, 2011
 */
public class JasperXLSXExporter extends JasperExporter {
	
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
	public String exportReport(RunCustomReportModel rcrm, String ouputFileName) throws JRException, FileNotFoundException, SQLException {
		JasperPrint jasperPrint = generateReport(rcrm);
		ouputFileName = ouputFileName+".html";
		exportReportXlsx(jasperPrint, ouputFileName);
		return ouputFileName;
	}

	/* (non-Javadoc)
	 * @see com.talentPool.customReports.service.ICRExporter#exportReport(java.lang.String, java.lang.String, java.lang.String[], java.io.OutputStream)
	 */
	@Override
	public ByteArrayOutputStream exportReport(RunCustomReportModel rcrm) throws Exception {
		JasperPrint jasperPrint = generateReport(rcrm);
		return exportReportXlsxStream(jasperPrint);
	}
	
	public static ByteArrayOutputStream exportReportXlsxStream(JasperPrint jasperPrint) throws JRException, FileNotFoundException {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		JRExporter exporter = new JRXlsxExporter();
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outputStream);
		exporter.setParameter(JRXlsExporterParameter.SHEET_NAMES, new String[]{"Report"});
		exporter.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
		exporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
		exporter.exportReport();
		return outputStream;
	}
	
	public static void exportReportXlsx(JasperPrint jasperPrint, String outputFileName) throws JRException, FileNotFoundException {
		JRExporter exporter = new JRXlsxExporter();
		String destinationPath = Utils.concatFilePath(ReportConstants.REPORT_DESTINATION_FOLDER_ABSOLUTE_PATH, outputFileName);
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, destinationPath);
		exporter.setParameter(JRXlsExporterParameter.SHEET_NAMES, new String[]{"Report"});
		exporter.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
		exporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
		exporter.exportReport();
	}

}
