/**
 * 
 */
package com.talentPool.customReports.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.Namespace;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import net.sf.jasperreports.engine.JRParameter;
import ar.com.fdvs.dj.core.DJConstants;
import ar.com.fdvs.dj.domain.DJCalculation;
import ar.com.fdvs.dj.domain.DJCrosstab;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.builders.CrosstabBuilder;
import ar.com.fdvs.dj.domain.builders.StyleBuilder;
import ar.com.fdvs.dj.domain.constants.Border;
import ar.com.fdvs.dj.domain.constants.Font;
import ar.com.fdvs.dj.domain.constants.HorizontalAlign;
import ar.com.fdvs.dj.domain.constants.Page;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.utils.Utils;
import com.talentPool.customReports.djhelper.impl.DJHelper;
import com.talentPool.customReports.exception.DynamicReportGenerationException;
import com.talentPool.customReports.jaxb.CustomReport;
import com.talentPool.customReports.jaxb.Field;

/**
 * @author PraveenK
 * @since  Dec 1, 2011
 */
public class DJCrossTabHelper extends DJHelper {

	/**
	 * @param reportType
	 */
	public DJCrossTabHelper(CustomReport reportType) {
		super(reportType);
	}
	/* (non-Javadoc)
	 * @see com.talentPool.customReports.utils.DJHelper#generateDynamicReport()
	 */
	@Override
	public DynamicReport generateDynamicReport()
			throws DynamicReportGenerationException {
		try {
			super.frb.setPageSizeAndOrientation(new Page(3000, 4000, true))
	        .setPrintColumnNames(false)
	        .setUseFullPageWidth(true)
	        .setIgnorePagination(true)
			.setTitle("Cross Tab");
			
//			addReportStyles();
			
			DJCrosstab djcross = new CrosstabBuilder()
	        .setHeight(200)
	        .setDatasource(JRParameter.REPORT_DATA_SOURCE,DJConstants.DATA_SOURCE_ORIGIN_PARAMETER, DJConstants.DATA_SOURCE_TYPE_JRDATASOURCE)
	        .setColorScheme(DJConstants.COLOR_SCHEMA_LIGHT_GREEN)
	        .setAutomaticTitle(true)
	        .setCellBorder(Border.THIN)
	        .addRow("Position Owner","position_owner",String.class.getName(),false)
	        .addRow("Position Title","position_title",String.class.getName(),false)
	        .addColumn("Stage", "position_stage", String.class.getName(),false)
	        .addColumn("Step", "position_step_title", String.class.getName(),false)
	        .addMeasure("applicant_id", Integer.class.getName(), DJCalculation.COUNT , "Applicant Id",measureStyle())
	        .setRowHeaderWidth(80)
	        .build();

			frb.addHeaderCrosstab(djcross);
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		
		return frb.build();
	}
	
	private Style measureStyle(){
		return new StyleBuilder(false).setPattern("#,###").setHorizontalAlign(HorizontalAlign.RIGHT).setFont(Font.ARIAL_MEDIUM).build();
	}
	
	/**
	 * @param jrxmlPath
	 * @param customReport
	 */
	public static void modifyCrossTabHeader(String jrxmlPath, CustomReport customReport) {
		try {			
		 	List<Field> rows = customReport.getRows();
		 	List<Field> columns = customReport.getColumns();
		 	if(Utils.isListEmptyOrNull(rows) || Utils.isListEmptyOrNull(columns))
		 		return;
			
			SAXBuilder builder = new SAXBuilder();
			File xmlFile = new File(jrxmlPath);
	 
			Document doc = (Document) builder.build(xmlFile);
			Namespace ns = Namespace.getNamespace("http://jasperreports.sourceforge.net/jasperreports");
			Element rootNode = doc.getRootElement();
	 
			Element group = rootNode.getChild("group", ns);			
			Element groupHeader = group.getChild("groupHeader", ns);
			Element band = groupHeader.getChild("band", ns);			
			Element crosstab = band.getChild("crosstab", ns);			
			Element crosstabHeaderCell = crosstab.getChild("crosstabHeaderCell", ns);
			
			Element cellContents = crosstabHeaderCell.getChild("cellContents", ns);
			cellContents.removeChildren("textField", ns);
			int childCount = cellContents.getChildren().size();
			
			int width, x = 0, y = (columns.size()-1) * 35;	
			Element reportElement, text, staticText, box, rightPen, topPen;
			String name;
			for(Field row : rows) {
				name = row.getDisplayName();
				width = row.getWitdh();
				
				staticText = new Element("staticText", ns);
				reportElement = new Element("reportElement", ns);
				reportElement.setAttribute("x", ""+x);
				reportElement.setAttribute("y", ""+y);
				reportElement.setAttribute("width", ""+width);
				reportElement.setAttribute("height", "35");				
				staticText.addContent(reportElement);
				box = new Element("box", ns);
				if (columns.size() > 1) {
					topPen = new Element("topPen", ns);
					topPen.setAttribute("lineWidth", "0.5");
					topPen.setAttribute("lineStyle", "Solid");
					topPen.setAttribute("lineColor", "#000000");
					box.addContent(topPen);
				}
				if (rows.indexOf(row) < rows.size() - 1) {
					rightPen = new Element("rightPen", ns);
					rightPen.setAttribute("lineWidth", "0.5");
					rightPen.setAttribute("lineStyle", "Solid");
					rightPen.setAttribute("lineColor", "#000000");
					box.addContent(rightPen);
				}
				staticText.addContent(box);
				text = new Element("text", ns);
				text.setText(name);
				staticText.addContent(text);
				
				cellContents.addContent(++childCount, staticText);
				x += width;
			}
			
			XMLOutputter xmlOutput = new XMLOutputter();
	 
			// display nice nice
			xmlOutput.setFormat(Format.getPrettyFormat());
			xmlOutput.output(doc, new FileWriter(jrxmlPath));
	 
			TPLogger.getLogger().info("CrossTab Header updated!");
		  } catch (IOException io) {
			TPLogger.getLogger().error(io.getMessage());
		  } catch (JDOMException e) {
			  TPLogger.getLogger().error(e.getMessage());
		  }
	}
}
