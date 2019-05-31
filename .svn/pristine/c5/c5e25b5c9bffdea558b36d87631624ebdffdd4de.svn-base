package com.talentPool.dynamicReports.generator;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.j2ee.servlets.ImageServlet;
import ar.com.fdvs.dj.core.DynamicJasperHelper;
import ar.com.fdvs.dj.core.layout.ClassicLayoutManager;
import ar.com.fdvs.dj.core.layout.LayoutManager;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.builders.ColumnBuilderException;
import ar.com.fdvs.dj.domain.builders.FastReportBuilder;
import ar.com.fdvs.dj.domain.constants.Page;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.properties.TPLabels;
import com.talentPool.dynamicReports.utils.DJStyles;
import com.talentPool.dynamicReports.utils.ReportExporter;
import com.talentPool.reports.ReportConstants;

/**
 *
 * @author ajeet
 */
public abstract class DynamicReportGenerator {

	protected HttpServletRequest request;
	protected HttpSession session;
	protected JasperPrint jp;
	protected JasperReport jr;
	protected Map params = new HashMap();
	protected DynamicReport dr;
	protected String exportTo;
	protected String filePathname;

	public DynamicReportGenerator(HttpServletRequest request, String filePathname, String exportTo) {
		this.session = request != null ? request.getSession() : null;
		this.request = request;
		this.filePathname = filePathname;
		this.exportTo = exportTo;
	}

	public void generateReport() throws Exception {
		try {
			dr = buildReport();
			JRDataSource ds = getDataSource();
			jp = DynamicJasperHelper.generateJasperPrint(dr, getLayoutManager(), ds);	//Creates the JasperPrint object, we pass as a Parameter
			if (session != null) {
				session.setAttribute(ImageServlet.DEFAULT_JASPER_PRINT_SESSION_ATTRIBUTE, jp);
			}
			ReportExporter.exportReport(jp, filePathname, exportTo,request.getContextPath());
			jr = DynamicJasperHelper.generateJasperReport(dr, getLayoutManager(), params);
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR,e);
			throw e;
		}
	}

	protected LayoutManager getLayoutManager() {
		return new ClassicLayoutManager();
	}
	
	public void setReportDefaultProperties(FastReportBuilder drb,String title,String subTitle) {
		drb.setTitle(title);
		drb.setTitleStyle(DJStyles.getTopHeaderStyle());
		drb.setTitleHeight(40);
		
		drb.setSubtitle(subTitle);		
		drb.setSubtitleHeight(40);

		drb.setHeaderHeight(20);
		
		drb.setWhenNoData(TPLabels.getLabel("report.error.no_data"), DJStyles.getNoDataStyle());
		

		if(!ReportConstants.FORMAT_EXCEL.equalsIgnoreCase(exportTo)){
			drb.setDefaultStyles(DJStyles.getTopHeaderStyle(), null, DJStyles.getDefaultColHeaderStyle(), DJStyles.getDefaultRowStyle());
		} else {
			drb.setIgnorePagination(true);
			drb.setDefaultStyles(DJStyles.getTopHeaderStyle(), null, DJStyles.getDefaultColHeaderStyle(), DJStyles.getDefaultExcelRowStyle());
		}
		
		if(ReportConstants.FORMAT_PDF.equalsIgnoreCase(exportTo)){
			
			Page customPage = Page.Page_Legal_Landscape();
			customPage.setWidth(3000);
			drb.setPageSizeAndOrientation(customPage);
			drb.setUseFullPageWidth(true);
			drb.setAllowDetailSplit(true);
			drb.setColumnSpace(10);
			
			
		}else if(ReportConstants.FORMAT_HTML.equalsIgnoreCase(exportTo)){
			Page customPage = Page.Page_A4_Portrait();
			drb.setPageSizeAndOrientation(customPage);
			customPage.setHeight(820);
		}
	}
	
	public void addColumn(FastReportBuilder drb,String title, String property,int colWidth) throws ColumnBuilderException, ClassNotFoundException {
		drb.addColumn(title,property, String.class.getName(),colWidth);
	}
	
	public void addColumn(FastReportBuilder drb,String title, String property,int colWidth,String className) throws ColumnBuilderException, ClassNotFoundException {
		if(!ReportConstants.FORMAT_EXCEL.equalsIgnoreCase(exportTo))
			drb.addColumn(title, property, className, colWidth,DJStyles.getDefaultRowStyle());
		else
			drb.addColumn(title, property, className, colWidth,DJStyles.getDefaultExcelRowStyle());
	}
	
	public abstract DynamicReport buildReport() throws Exception;

	public abstract JRDataSource getDataSource();
}
