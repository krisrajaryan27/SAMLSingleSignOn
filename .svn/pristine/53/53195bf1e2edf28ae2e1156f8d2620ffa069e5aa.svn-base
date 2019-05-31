package com.talentPool.dynamicReports.utils;

import java.io.FileNotFoundException;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JExcelApiExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.utils.Utils;
import com.talentPool.reports.ReportConstants;

/**
 *
 * @author praveen
 */
public class ReportExporter {
	
	public static void exportReport(JasperPrint jp, String path,String reportFormat,String contextPath) throws JRException, FileNotFoundException {
		if (ReportConstants.FORMAT_PDF.equalsIgnoreCase(reportFormat)) {
			ReportExporter.exportReportPdf(jp, path);
		} else if (ReportConstants.FORMAT_EXCEL.equalsIgnoreCase(reportFormat)) {
			ReportExporter.exportReportXls(jp, path);
		} else {
			ReportExporter.exportReportHtml(jp, path, contextPath);
		}
	}
	
	public static void exportReport(JasperPrint jp, String path,String reportFormat) throws JRException, FileNotFoundException {
		if (ReportConstants.FORMAT_PDF.equalsIgnoreCase(reportFormat)) {
			ReportExporter.exportReportPdf(jp, path);
		} else if (ReportConstants.FORMAT_EXCEL.equalsIgnoreCase(reportFormat)) {
			ReportExporter.exportReportXls(jp, path);
		} else {
			ReportExporter.exportReportHtml(jp, path, "");
		}
	}

	public static void exportReportPdf(JasperPrint jp, String path) throws JRException, FileNotFoundException {
		JRPdfExporter exporter = new JRPdfExporter();
		String destinationPath = Utils.concatFilePath(ReportConstants.REPORT_DESTINATION_FOLDER_ABSOLUTE_PATH, path);
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, jp);
		exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, destinationPath);
		exporter.exportReport();
		TPLogger.getLogger().debug("PDF Report exported: " + destinationPath);
	}

	public static void exportReportHtml(JasperPrint jp, String path, String contextPath) throws JRException, FileNotFoundException {
		String htmlHeader = ReportConstants.DEFAULT_REPORT_HTML_HEADER;
		String htmlFooter = "";
		
		JRHtmlExporter exporter = new JRHtmlExporter();
		String destinationPath = Utils.concatFilePath(ReportConstants.REPORT_DESTINATION_FOLDER_ABSOLUTE_PATH, path);
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, jp);
		exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, destinationPath);
		exporter.setParameter(JRHtmlExporterParameter.IS_WRAP_BREAK_WORD, Boolean.TRUE);
		exporter.setParameter(JRHtmlExporterParameter.SIZE_UNIT, "px");
		exporter.setParameter(JRHtmlExporterParameter.HTML_HEADER, htmlHeader);
		exporter.setParameter(JRHtmlExporterParameter.HTML_FOOTER, htmlFooter);
		
		//exporter.setParameter(JRHtmlExporterParameter.IMAGES_URI,contextPath+"/image?image=");
		exporter.exportReport();
		TPLogger.getLogger().debug("HTML Report exported: " + destinationPath);
	}
	
	public static void exportReportXls(JasperPrint jp, String path) throws JRException, FileNotFoundException {
		JRXlsExporter exporter = new JRXlsExporter();
		String destinationPath = Utils.concatFilePath(ReportConstants.REPORT_DESTINATION_FOLDER_ABSOLUTE_PATH, path);
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, jp);
		exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, destinationPath);
		exporter.setParameter(JRXlsExporterParameter.SHEET_NAMES, new String[]{"Report"});
		exporter.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
		exporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
		exporter.exportReport();
		TPLogger.getLogger().debug("XLS Report exported: " + destinationPath);
	}

	public static void exportReportPlainXls(JasperPrint jp, String path) throws JRException, FileNotFoundException {
//		JRXlsExporter exporter = new JRXlsExporter();
		JExcelApiExporter exporter = new JExcelApiExporter();
		String destinationPath = Utils.concatFilePath(ReportConstants.REPORT_DESTINATION_FOLDER_ABSOLUTE_PATH, path);
		exporter.setParameter(JRXlsExporterParameter.SHEET_NAMES, new String[]{"Leave Details"});
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, jp);
		exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, destinationPath);
		exporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
		exporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
		exporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
		exporter.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
		exporter.exportReport();
		TPLogger.getLogger().debug("Plain Xls Report exported: " + destinationPath);
	}
}
