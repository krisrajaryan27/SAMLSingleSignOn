package com.talentPool.masters.manager;

import java.io.StringWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.db.DBPreparedQuery;
import com.talentPool.common.db.DBTransaction;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.properties.TPLabels;
import com.talentPool.common.utils.Utils;
import com.talentPool.common.xmlutils.XMLWriter;
import com.talentPool.masters.dataobject.ReportTemplateData;
import com.talentPool.reportDesign.dataobject.ColumnData;
import com.talentPool.reports.ReportVersionConstants;

public class ReportTemplateManager {
	public void saveReportTemplateData(ReportTemplateData reportTemplateData,String userId) throws Exception {
		if(!Utils.isBlankOrNull(reportTemplateData.getReportTemplateId())){
			updateReportTemplateData(reportTemplateData);
		}else {
			insertReportTemplateData(reportTemplateData, userId);
		}
	}
	
	public void insertReportTemplateData(ReportTemplateData reportTemplateData,String userId) throws Exception {
		DBPreparedQuery dq = null;
		DBTransaction tran = null;
		try {
			tran = new DBTransaction();
			dq = new DBPreparedQuery("dReportTemplateManager_InsertReportTemplateData",tran);
			dq.setString(1, reportTemplateData.getReportId());
			dq.setString(2, reportTemplateData.getTemplateName());
			dq.setString(3, reportTemplateData.getReportFilePath());
			dq.setString(4, reportTemplateData.getOriginalFileName());
			dq.setString(5, userId);
			dq.setString(6, reportTemplateData.getSheetIndex());
			dq.setString(7, reportTemplateData.getRowIndex());
			dq.execute();
			
			dq = new DBPreparedQuery("dFetchLastInsertID", tran);
			String reportTemplateId = dq.getIdResult();
			
			insertReportTemplateColumns(reportTemplateData.getTemplateColumns(), reportTemplateId, tran);
			
			tran.commit();
		}catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			tran.rollback();
			throw e;
		}finally {
			if (dq != null) {
				dq.releaseTransaction(tran);
			}
		}		
	}
	
	public void updateReportTemplateData(ReportTemplateData reportTemplateData) throws Exception {
		DBPreparedQuery dq = null;
		DBTransaction tran = null;
		try {
			tran = new DBTransaction();
			dq = new DBPreparedQuery("dReportDesignManager_updateReportTemplateData",tran);
			dq.setString(1, reportTemplateData.getReportId());
			dq.setString(2, reportTemplateData.getTemplateName());
			dq.setString(3, reportTemplateData.getReportFilePath());
			dq.setString(4, reportTemplateData.getOriginalFileName());
			dq.setString(5, reportTemplateData.getSheetIndex());
			dq.setString(6, reportTemplateData.getRowIndex());
			dq.setString(7, reportTemplateData.getReportTemplateId());
			dq.execute();
			
			dq = new DBPreparedQuery("dReportTemplateManager_DeleteReportTemplateColumns",tran);
			dq.setString(1, reportTemplateData.getReportTemplateId());
			dq.execute();
			
			insertReportTemplateColumns(reportTemplateData.getTemplateColumns(), reportTemplateData.getReportTemplateId(), tran);
			
			tran.commit();
		}catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			tran.rollback();
			throw e;
		}finally {
			if (dq != null) {
				dq.releaseTransaction(tran);
			}
		}		
	}
	
	public void insertReportTemplateColumns(String columns,String reportTemplateId,DBTransaction tran) throws SQLException{
		DBPreparedQuery dq = null;
		try {
			if(!Utils.isBlankOrNull(columns)){
				String[] cols = columns.split(",");
				for (int i = 0; i < cols.length; i++) {
					dq = new DBPreparedQuery("dReportTemplateManager_InsertReportTemplateColumns",tran);
					dq.setString(1, reportTemplateId);
					dq.setString(2, cols[i]);
					try{
						Integer.parseInt(cols[i]);
						dq.setString(3, "1"); //custom field	
					}catch (Exception e) {
						dq.setString(3, "0"); //non custom field
					}
					dq.setInt(4, i+1);
					dq.execute();
			}
		}} finally {
			if (dq != null) {
				if (tran == null) {
					dq.releaseConnection();
				} else {
					dq.closeOpenCursors();
				}
			}
		}
	}
	
	public List<ReportTemplateData> getReportTemplates() throws SQLException {
		List<ReportTemplateData> templates = new ArrayList<ReportTemplateData>();
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dReportTemplateManager_GetReportTemplates");
			templates = (List<ReportTemplateData>) dq.getResult();
		} catch (SQLException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			throw e;
		}finally {
			if(dq != null) {
				dq.releaseConnection();
			}
		}
		return templates;
	}
	
	public List<ReportTemplateData> getReportTemplates(String reportId){
		DBPreparedQuery dq = null;
		List<ReportTemplateData> reportTemplateDataList = null;
		try {
			dq = new DBPreparedQuery("dReportTemplateManager_GetReportTemplatesForaTemplateType");
			dq.setString(1, reportId);
			reportTemplateDataList = (List<ReportTemplateData>) dq.getResult();
		}catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}	
		return reportTemplateDataList;
	}
	
	public ReportTemplateData getReportTemplateData(String reportTemplateId){
		DBPreparedQuery dq = null;
		ReportTemplateData reportTemplateData = null;
		try {
			dq = new DBPreparedQuery("dReportTemplateManager_GetReportTemplateData");
			dq.setString(1, reportTemplateId);
			reportTemplateData = (ReportTemplateData)dq.getSingleObjectResult();
		}catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}	
		return reportTemplateData;
	}
	
	public String getXMLForReportTemplates(List<ReportTemplateData> templates) {
		StringWriter sWr = new StringWriter();
		XMLWriter wr = new XMLWriter(sWr);
		ReportTemplateData data = null;
		try {
			wr.startDocument();
			wr.startElement("rows");
			if(templates != null && templates.size() > 0) {
				for (int i = 0; templates != null && i < templates.size(); i++) {
					data = templates.get(i);
					AttributesImpl at = new AttributesImpl();
					at.addAttribute("", "id", "", "", data.getReportTemplateId());
					wr.startElement("", "row", "", at);

					at = new AttributesImpl();
					
					at.addAttribute("", "name", "", "", "Col_I_Comment");
					wr.startElement("", "userdata", "", at);
					wr.characters(TPLabels.getLabel("common.delete"));
					wr.endElement("userdata");

					at = new AttributesImpl();
					at.addAttribute("", "name", "", "", "reportName");
					wr.startElement("", "userdata", "", at);
					wr.characters(ReportVersionConstants.getReportTitle(data.getReportId()));
					wr.endElement("userdata");
					
					at = new AttributesImpl();
					at.addAttribute("", "name", "", "", "templateName");
					wr.startElement("", "userdata", "", at);
					wr.characters(data.getTemplateName());
					wr.endElement("userdata");

					wr.startElement("cell");
					wr.characters("<img src=\"images/ico_delete.gif\" border=0>^javascript:deleteRecord(" + data.getReportTemplateId() + ");^_self");
					wr.endElement("cell");

					wr.startElement("cell");
					wr.characters(wr.doubleEscape(data.getTemplateName()) + "^javascript:editRecord(" + data.getReportTemplateId() + ");^_self");
					wr.endElement("cell");
					
					wr.startElement("cell");
					wr.characters(wr.doubleEscape(ReportVersionConstants.getReportTitle(data.getReportId())));
					wr.endElement("cell");

					wr.endElement("row");
				}
			}			
			wr.endElement("rows");
			wr.endDocument();
		} catch (SAXException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}

		return sWr.getBuffer().toString();
	}

	public void deleteReportTemplate(String reportTemplateId) throws SQLException {
		DBPreparedQuery dq = null;
		DBTransaction tran = null;
		try {
			tran = new DBTransaction();
			dq = new DBPreparedQuery("dReportTemplateManager_DeleteReportTemplateColumns",tran);			
			dq.setString(1, reportTemplateId);
			dq.execute();
			
			dq = new DBPreparedQuery("dReportTemplateManager_DeleteReportTemplate", tran);			
			dq.setString(1, reportTemplateId);
			dq.execute();

			tran.commit();
		} catch (SQLException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			try {
				tran.rollback();
			} catch (SQLException sq) {
				TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			}
			throw e;
		}finally {
			if(dq != null) {
				dq.releaseTransaction(tran);
			}
		}
	}
	
	public ArrayList<ColumnData> getReportTemplateColumnData(String reportTemplateId) {
		DBPreparedQuery dq = null;
		ArrayList<ColumnData> data = new ArrayList<ColumnData>();
		try {
			dq = new DBPreparedQuery("dReportTemplateManager_GetReportTemplateColumnData");
			dq.setString(1, reportTemplateId);
			data = dq.getResult();
		}catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}	
		return data;
	}
	
}
