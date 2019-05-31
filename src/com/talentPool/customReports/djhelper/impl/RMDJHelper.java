/**
 * 
 */
package com.talentPool.customReports.djhelper.impl;

import java.util.ArrayList;
import java.util.List;

import net.sf.jasperreports.engine.JRException;
import ar.com.fdvs.dj.core.DynamicJasperHelper;
import ar.com.fdvs.dj.core.layout.ClassicLayoutManager;
import ar.com.fdvs.dj.core.layout.LayoutManager;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.builders.ColumnBuilderException;
import ar.com.fdvs.dj.domain.entities.DJGroup;
import ar.com.fdvs.dj.domain.entities.columns.PropertyColumn;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.utils.Utils;
import com.talentPool.customReports.djhelper.manager.TPCustomLayoutManager;
import com.talentPool.customReports.exception.DynamicReportGenerationException;
import com.talentPool.customReports.jaxb.CustomReport;
import com.talentPool.customReports.jaxb.Field;

/**
 * @author PraveenK
 * @since  Dec 18, 2011
 */
public class RMDJHelper extends AbstarctDJHelper {

	/**
	 * @param customReport
	 */
	public RMDJHelper(CustomReport customReport) {
		super(customReport);
	}


	/**
	 * Generates {@link DynamicReport} using the reportDetails
	 * <li>Adds the report Details</li>
	 * <li>Builds and add the report columns</li>
	 * <li>Builds and add the report groups if any available</li>
	 * @return {@link DynamicReport} built using reportDetails
	 * @throws DynamicReportGenerationException
	 */
	public DynamicReport generateDynamicReport() throws DynamicReportGenerationException {
		TPLogger.getLogger().debug("Generating DynamicReport for reportId"+customReport.getReportId());
		try {
			addReportStyles();
			addReportDetails();
			List<Field> fields = new ArrayList<Field>();
			fields.addAll(customReport.getRows());
			fields.addAll(customReport.getMeasures());
			addReportColumns(fields);
			addGrouping();
		} catch (ColumnBuilderException cbe) {
			throw new DynamicReportGenerationException("Error in building columns for Dynamic Report:"+customReport.getReportId());
		}
		return frb.build();
	}
	
	protected void addGrouping() throws ColumnBuilderException {
		List<Field> rows = customReport.getRows();
		DJGroup djGroup = null;
		if(!Utils.isListEmptyOrNull(rows)){
			for (int i = 0; i < rows.size()-1; i++) {
				djGroup = buildDJGroup((PropertyColumn)frb.getColumn(i));
				frb.addGroup(djGroup);
			}
		}else{
			TPLogger.getLogger().debug("This Report does not have any columns to build");
		}
	}

	/* (non-Javadoc)
	 * @see com.talentPool.customReports.service.IDJHelper#generateJRXML(com.talentPool.customReports.jaxb.CustomReport, java.lang.String)
	 */
	@Override
	public void generateJRXML(CustomReport customReport, String filePath) throws DynamicReportGenerationException, JRException {
		generateJRXML(customReport, filePath, new TPCustomLayoutManager());
	}

	/* (non-Javadoc)
	 * @see com.talentPool.customReports.service.IDJHelper#generateJRXML(com.talentPool.customReports.jaxb.CustomReport, java.lang.String, ar.com.fdvs.dj.core.layout.LayoutManager)
	 */
	@Override
	public void generateJRXML(CustomReport customReport, String filePath,
		LayoutManager layout) throws DynamicReportGenerationException, JRException {
		DynamicReport dynamicReport = generateDynamicReport(); 
		DynamicJasperHelper.generateJRXML(dynamicReport, layout, null, "UTF-8", filePath);
	}

}
