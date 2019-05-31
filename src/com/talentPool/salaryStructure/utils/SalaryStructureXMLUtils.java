package com.talentPool.salaryStructure.utils;

import java.io.StringWriter;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import com.talentPool.admin.AdminConstants;
import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.constants.DHTMLXXMLWriterConstants;
import com.talentPool.common.dataobject.DHTMLXUserData;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.properties.TPLabels;
import com.talentPool.common.utils.Utils;
import com.talentPool.common.xmlutils.DHTMLXXMLWriter;
import com.talentPool.common.xmlutils.XMLWriter;
import com.talentPool.masters.dao.impl.SalaryComponentCategoryDAOImpl;
import com.talentPool.masters.service.impl.SalCompCategoryService;
import com.talentPool.salaryStructure.constants.SalaryStructureConstants;
import com.talentPool.salaryStructure.databject.SalaryComponentsData;
import com.talentPool.salaryStructure.databject.SalaryFormulaData;
import com.talentPool.salaryStructure.databject.SalaryStructure;
import com.talentPool.salaryStructure.entity.SalaryComponentCategory;

public class SalaryStructureXMLUtils {
	
	public static String getSalaryComponentXML(List<SalaryComponentsData> salaryComponents) {
		StringWriter sWr = new StringWriter();
		DHTMLXXMLWriter wr = new DHTMLXXMLWriter(sWr);		
		try {
			wr.startDocument();
			wr.startElement(DHTMLXXMLWriterConstants.ROWS);
			for (int i = 0; i < salaryComponents.size(); i++) {
				SalaryComponentsData salaryComponentsData = (SalaryComponentsData) salaryComponents.get(i);
				String salaryComponentId = salaryComponentsData.getSalaryComponentId();
				String salaryComponentName = salaryComponentsData.getSalaryComponentName();
				String salaryComponentDescription = salaryComponentsData.getSalaryComponentDescription();
				String salaryComponentType = SalaryStructureUtils.getSalaryComponentTypeDescription(salaryComponentsData.getSalaryComponentType());
				String salaryComponentCategoryName = salaryComponentsData.getSalaryComponentCategoryName();
				String deleteImg = "";
				
				if(AdminConstants.SYSTEM_GENERATED.equals(salaryComponentsData.getSystemDefined())){
					deleteImg = "&nbsp;";
				}else{
					deleteImg = "<a href=\"#\" onclick=\"onDeleteSalaryComponent("+ salaryComponentId + ");\" title=\"Delete\"><img src=\"images/ico_delete.gif\" border=0></a>";
				}

				String[] columns 	= {deleteImg,
				"<a href=\"#\" onclick=\"onClickSalaryComponent("+ salaryComponentId + ");\" title=\"Delete\">"+wr.doubleEscape(salaryComponentName)+"</a>",
				wr.doubleEscape(salaryComponentDescription), 
				wr.doubleEscape(salaryComponentType),
				wr.doubleEscape(salaryComponentCategoryName)};
				
				DHTMLXUserData[] userData = {new DHTMLXUserData("salaryComponentId",salaryComponentId),
												new DHTMLXUserData("salaryComponentName",salaryComponentName)};
				
				wr.createDHTMLXRow(salaryComponentId, userData, columns);
			}
			wr.endElement(DHTMLXXMLWriterConstants.ROWS);
			wr.endDocument();
		} catch (SAXException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return sWr.getBuffer().toString();
	}
	
	public static String getSalaryFormulaXML(List<SalaryFormulaData> salaryFormula) {
		StringWriter sWr = new StringWriter();
		XMLWriter wr = new XMLWriter(sWr);
		SalaryFormulaData salaryFormulaData = null;
		try {
			wr.startDocument();
			wr.startElement("rows");
			for (int i = 0; i < salaryFormula.size(); i++) {
				salaryFormulaData = (SalaryFormulaData) salaryFormula.get(i);
				String salaryFormulaId = ""+salaryFormulaData.getFormulaId();
				String salaryGradeName = salaryFormulaData.getGradeName();
				String salaryComponentName = salaryFormulaData.getSalaryComponentName();
				String salaryFormulaDescription = SalaryStructureUtils.getSalaryFormulaDescription(salaryFormulaData);
				String salaryComponentType = SalaryStructureUtils.getSalaryComponentTypeDescription(salaryFormulaData.getSalaryComponentType());
				String salaryComponentMaxLimit = (0==salaryFormulaData.getMaxLimit())?"":""+salaryFormulaData.getMaxLimit();
				String isAdjustable=SalaryStructureConstants.ADJUSTABLE_COMPONENT.equals(salaryFormulaData.getIsAdjustable())?"(A)":"";

				AttributesImpl at = new AttributesImpl();
				at.addAttribute("", "id", "", "", "" + salaryFormulaId);
				wr.startElement("", "row", "", at);

				at = new AttributesImpl();
				at.addAttribute("", "name", "", "", "salaryFormulaId");
				writeUserData(wr,at,salaryFormulaId);

				at = new AttributesImpl();
				at.addAttribute("", "name", "", "", "salaryFormulaName");
				writeUserData(wr,at,salaryGradeName);
				
				if(salaryFormulaData.getFormulaId()!=0) {
					if(AdminConstants.SYSTEM_GENERATED.equals(salaryFormulaData.getSystemDefined())){
						writeToCell(wr,"&nbsp;");
					}else{
						writeToCell(wr,"<a href=\"#\" onclick=\"onDeleteSalaryFormula("+ salaryFormulaId + ");\" title=\"Delete\"><img src=\"images/ico_delete.gif\" border=0></a>");	
					}
					
					writeToCell(wr,wr.doubleEscape(salaryComponentName+" "+isAdjustable));
					writeToCell(wr,wr.doubleEscape(salaryFormulaDescription));
					writeToCell(wr,wr.doubleEscape(salaryComponentType));
					writeToCell(wr,wr.doubleEscape(salaryComponentMaxLimit));
				}else {
					writeEmptyCells(wr,5);
				}
				writeToCell(wr,wr.doubleEscape(salaryGradeName));
				
				wr.endElement("row");
			}
			wr.endElement("rows");
			wr.endDocument();
		} catch (SAXException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return sWr.getBuffer().toString();
	}
	
	public static String getSalaryStructurePreviewXML(Map<String, List<SalaryFormulaData>> salaryFormulae, SalaryStructure salary) {
		StringBuffer html = new StringBuffer("");
		if(salaryFormulae.size()>0){
			SalaryComponentCategoryDAOImpl salCompCategoryDao = new SalaryComponentCategoryDAOImpl();
			SalCompCategoryService salCmpCatService = new SalCompCategoryService();
			salCmpCatService.setSalCompCategoryDao(salCompCategoryDao);
			Map<String,SalaryComponentCategory> salCompMap = salCmpCatService.getSalaryComponentCategoryMap();
			html.append("<table id=\"salaryStructureDiv\">");
			html.append("<tr><th width=\"239\">Component</th><th width=\"75\">Monthly</th><th width=\"75\">Annual</th></tr>");
			String categoryId = "", categoryName = "";
			for (Entry<String,List<SalaryFormulaData>> entry : salaryFormulae.entrySet()) {
				List<SalaryFormulaData> salaryFormulaDataLst = entry.getValue();
				if(!Utils.isListEmptyOrNull(salaryFormulaDataLst)){
					html.append("<tr>");
					categoryId = salaryFormulaDataLst.get(0).getSalaryCategoryId();
					categoryName = (salCompMap.containsKey(categoryId)) ? salCompMap.get(categoryId).getCategoryName() : "";
					html.append("<th colspan=\"3\">"+categoryName+"</th>");
					html.append("</tr>");
					for (SalaryFormulaData salaryFormulaData : salaryFormulaDataLst) {
						html.append("<tr>");
						html.append("<td>"+salaryFormulaData.getSalaryComponentName()+"</td>");
						if(SalaryStructureConstants.SALARY_PERIOD_MONTHLY.equals(salaryFormulaData.getSalaryComponentType())){
							html.append("<td>"+salary.getMonthlyValue(salaryFormulaData.getSalaryComponentId())+"</td>");
						}else{
							html.append("<td/>");
						}
						html.append("<td>"+salary.getAnnualValue(salaryFormulaData.getSalaryComponentId())+"</td>");
						html.append("</tr>");
					}
					html.append("<tr>");
					html.append("<td>Total</td>");
					html.append("<td>"+Utils.getBlankIfNull(salary.getMonthlyCatValue(salaryFormulaDataLst.get(0).getSalaryCategoryId()))+"</td>");
					html.append("<td id=\"annual_cat_total_"+salaryFormulaDataLst.get(0).getSalaryCategoryId()+"\" >"+Utils.getBlankIfNull(salary.getAnnualCatValue(salaryFormulaDataLst.get(0).getSalaryCategoryId()))+"</td>");
					html.append("</tr>");
				}
			}
			html.append("<tr/>");
			html.append("<tr style=\"border-top:solid 2px;\">");
			html.append("<td><b>Total</b></td>");
			html.append("<td>"+Utils.getBlankIfNull(salary.getMonthlyTotal())+"</td>");
			html.append("<td>"+Utils.getBlankIfNull(salary.getAnnualTotal())+"</td>");
			html.append("</tr>");
			html.append("</table>");
			html.append("<div class=\"navBtn\" style=\"\">" +
					"<a href=\"#\" style=\"float: left;width:60px;\" class=\"active\" onclick=\"javascript: hidePreviewSalary();return false;\"><span class=\"rightC\"></span><span class=\"leftC\"></span>"+TPLabels.getLabel("common.hide")+"</a>" +
					"<a href=\"#\" style=\"float: right;width:60px;\" class=\"active\" onclick=\"javascript: updateSalary("+salary.getMonthlyBasic()+","+salary.getAnnualTotal()+");return false;\"><span class=\"rightC\"></span><span class=\"leftC\"></span>"+TPLabels.getLabel("common.update")+"</a>" +
							"</div>");
		}else{
			html.append("<b>"+TPLabels.getLabel("selection_feedback.error.salary_structure_not_defined")+"</b><br/>");
			html.append("<div class=\"navBtn\" style=\"\">" +
					"<a href=\"#\" style=\"float: left;width:60px;\" class=\"active\" onclick=\"javascript: hidePreviewSalary();return false;\"><span class=\"rightC\"></span><span class=\"leftC\"></span>"+TPLabels.getLabel("common.hide")+"</a>" +
							"</div>");
		}
		return html.toString();
	}
	
	private static void writeEmptyCells(XMLWriter wr,int noOfCells) throws SAXException {
		for (int i = 0; i < noOfCells; i++) {
			wr.startElement("cell");
			wr.characters(" ");
			wr.endElement("cell");
		}
	}
	
	private static void writeToCell(XMLWriter wr,String cellValue) throws SAXException {
		wr.startElement("cell");
		wr.characters(cellValue);
		wr.endElement("cell");
	}
	
	private static void writeUserData(XMLWriter wr,AttributesImpl at,String userData) throws SAXException {
		wr.startElement("", "userdata", "", at);
		wr.characters(userData);
		wr.endElement("userdata");
	}
}
