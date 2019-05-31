package com.talentPool.dynamicReports.generator;

import java.awt.Color;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import net.sf.jasperreports.engine.JRDataSource;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.builders.ColumnBuilderException;
import ar.com.fdvs.dj.domain.builders.FastReportBuilder;
import ar.com.fdvs.dj.domain.constants.Border;

import com.talentPool.common.utils.Utils;
import com.talentPool.custom.constants.CustomFieldConstants;
import com.talentPool.custom.dataobject.CustomFieldData;
import com.talentPool.custom.manager.CustomFieldManager;
import com.talentPool.dynamicReports.data.DynamicReportsDataSource;
import com.talentPool.dynamicReports.utils.DJStyles;
import com.talentPool.dynamicReports.utils.DynamicReportsColumnUtils;
import com.talentPool.masters.manager.StepManager;
import com.talentPool.reports.ReportVersionConstants;
import com.talentPool.reports.dataobject.CandidateTATData;

/**
 * @author Sachinm
 *
 */
public class ClosedPositionTATReport extends DynamicReportGenerator {
	
	private ArrayList<CandidateTATData> candidateTATDatas = null;
	private String stepsToShow 	= null;
	private String filterCriteria 	= null;

	/**
	 * @param request
	 * @param filePathname
	 * @param exportTo
	 */
	public ClosedPositionTATReport(HttpServletRequest request,
			String filePathname, String exportTo) {
		super(request, filePathname, exportTo);
	}
	
	/**
	 * @param request
	 * @param filePathname
	 * @param exportTo
	 * @param candidateTATDatas
	 * @param stepsToShow
	 * @param filterCriteria
	 * @param groupBy
	 */
	public ClosedPositionTATReport(HttpServletRequest request,
			String filePathname, String exportTo, ArrayList<CandidateTATData> candidateTATDatas,
			String stepsToShow,String filterCriteria) {
		super(request, filePathname, exportTo);
		this.candidateTATDatas = candidateTATDatas;
		this.stepsToShow=stepsToShow;
		this.filterCriteria=filterCriteria;
	}

	/* (non-Javadoc)
	 * @see com.talentPool.dynamicReports.generator.DynamicReportGenerator#buildReport()
	 */
	@Override
	public DynamicReport buildReport() throws Exception {
		FastReportBuilder drb = new FastReportBuilder();
		
		Map<String,String> madFieldsMap = DynamicReportsColumnUtils.getMandatoryFields(null,
				ReportVersionConstants.REPORT_CLOSED_POSITION_TAT, null);
		addMandatoryColumns(drb, madFieldsMap);
		if(!Utils.isBlankOrNull(stepsToShow)) {
			addStepColumns(stepsToShow,drb);
		}
		
		setReportDefaultProperties(drb, 
				ReportVersionConstants.getReportTitle(ReportVersionConstants.REPORT_CLOSED_POSITION_TAT), 
				this.filterCriteria);
		Style defaultRowDetail = new Style();
		defaultRowDetail.setBorderBottom(Border.THIN);
		defaultRowDetail.setBorderColor(Color.LIGHT_GRAY);
		if(!Utils.isBlankOrNull(filterCriteria)) {
			drb.setSubtitleHeight(30 + filterCriteria.split("\\\\n").length * 10);
		}
		return drb.build();
	}
	
	/* (non-Javadoc)
	 * @see com.talentPool.dynamicReports.generator.DynamicReportGenerator#getDataSource()
	 */
	@Override
	public JRDataSource getDataSource() {
		JRDataSource jd = new DynamicReportsDataSource(getDataSourceCollection());
		return jd;
	}

	/**
	 * @return
	 */
	private ArrayList<CandidateTATData> getDataSourceCollection() {
		return candidateTATDatas;
	}
	
	/**
	 * @param drb
	 * @param map
	 * @throws ColumnBuilderException
	 * @throws ClassNotFoundException
	 */
	private void addMandatoryColumns(FastReportBuilder drb, Map<String, String> map) 
			throws ColumnBuilderException, ClassNotFoundException {
		Set<String> keys = map.keySet();
		Iterator<String> itr = keys.iterator();
		while(itr.hasNext()) {
			String key = itr.next();
			
			int width = 0;
			int minColWidth = 50;
			int medColWidth = 100;
			int maxColwidth=150;
			String className = "";
			String stringClassName = String.class.getName();
			String numberClassName = Number.class.getName();
			
			if("srNo".equals(key)){
				className = numberClassName;
				width = minColWidth;
			} else if("client".equals(key) 
					|| "applicantName".equals(key) ||"joinedDate".equals(key) ||"offerAcceptedDate".equals(key) || "applicantJoiningDate".equals(key) 
					|| "positionCreatedDate".equals(key) || "positionTitle".equals(key) || "positionCode".equals(key)||"positionLocation".equals(key)
					|| "applicantCurrentEmployer".equals(key) || "currentCtc".equals(key)|| "expectedCtc".equals(key) || "applicantNoticePeriod".equals(key)|| "currentDesignation".equals(key)){
				className = stringClassName;
				width = medColWidth;
			} 
			 else if("applicantOfferStatus".equals(key)||"applicantDesignationOffered".equals(key)||"applicantLevelOffered".equals(key)){
					className = stringClassName;
					width = medColWidth;
				}
				else if("experience".equals(key)){
					className = stringClassName;
					width = minColWidth;
				}
			 else if("sourceTitle".equals(key)){
					className = stringClassName;
					width = medColWidth;
				}
			 else if("qualification".equals(key)){
					className = stringClassName;
					width = maxColwidth;
				}	
			/* else if("noOfOpenings".equals(key)){
				className = numberClassName;
				width = minColWidth;
			}*/
			 else if("candidateStatus".equals(key)){
					className = stringClassName;
					width = medColWidth;
				}
			 else if("rejectReason".equals(key)){
					className = stringClassName;
					width = medColWidth;
				}
			 else if("realExperience".equals(key)){
					className = stringClassName;
					width = medColWidth;
				}
			 else if("comment".equals(key)){
					className = stringClassName;
					width = medColWidth;
				}
			drb.addColumn(map.get(key), key, className, width, DJStyles.getDefaultRowStyle());
		}
	}
	
	/**
	 * @param stepsToShow
	 * @param drb
	 * @throws ColumnBuilderException
	 * @throws ClassNotFoundException
	 */
	private void addStepColumns(String stepsToShow,FastReportBuilder drb) throws ColumnBuilderException, ClassNotFoundException{
		String[] stepsList = stepsToShow.split(",");
		for (int i = 0; i < stepsList.length; i++) {
			addStepColumn(stepsList[i],drb);
		}
	}
	

	
	/**
	 * @param stepId
	 * @param drb
	 * @throws ColumnBuilderException
	 * @throws ClassNotFoundException
	 */
	private void addStepColumn(String stepId,FastReportBuilder drb) throws ColumnBuilderException, ClassNotFoundException{
		String stepName = "";
		StepManager stepManager = new StepManager();
		try {
			stepName = stepManager.getStepNameForStepId(stepId);
			if(!Utils.isBlankOrNull(stepName)){
				if(stepName.toLowerCase().contains("interview")){
					drb.addColumn(stepName + " Date", stepName + " Date", String.class.getName(), 90, DJStyles.getDefaultRowStyle());
					drb.addColumn(stepName + " Interviewer", stepName + " Interviewer", String.class.getName(), 90, DJStyles.getDefaultRowStyle());
					drb.addColumn(stepName + " Status", stepName + " Status", String.class.getName(), 90, DJStyles.getDefaultRowStyle());
					drb.addColumn(stepName + " Duration", stepName + " Duration", Integer.class.getName(), 70, DJStyles.getDefaultRowStyle());
				}
				else{
					drb.addColumn(stepName + " Date", stepName + " Date", String.class.getName(), 90, DJStyles.getDefaultRowStyle());
					//drb.addColumn(stepName + " Interviewer", stepName + " Interviewer", String.class.getName(), 90, DJStyles.getDefaultRowStyle());
					drb.addColumn(stepName + " Duration", stepName + " Duration", Integer.class.getName(), 70, DJStyles.getDefaultRowStyle());
				
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}						
	}
	
	private void addCustomColumn(String key,FastReportBuilder drb) throws ColumnBuilderException, ClassNotFoundException{
//		int minColWidth = 50;
//		int maxColWidth = 150;
		int medColWidth = 100;
	
		if (CustomFieldManager.isCustomFieldsAvailable(CustomFieldConstants.ENTITY_TYPE_APPLICANT)) {
			CustomFieldManager customFieldManager = new CustomFieldManager();
			ArrayList<CustomFieldData> customFields = customFieldManager.getCustomFieldsForInputAllowed(CustomFieldConstants.ENTITY_TYPE_APPLICANT, CustomFieldConstants.INPUT_ALLOWED, true);
			for (int i = 0; i < customFields.size(); i++) {
				CustomFieldData cData = customFields.get(i);
				if(!Utils.isBlankOrNull(cData.getFieldId()) && cData.getFieldId().endsWith(key)){
					drb.addColumn(cData.getFieldDisplayName(),"CUS_"+cData.getFieldId(), String.class.getName(),medColWidth,DJStyles.getDefaultRowStyle());
				}
			}
		}
	}
}
