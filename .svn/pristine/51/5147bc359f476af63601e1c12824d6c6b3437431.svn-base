/**
 * 
 */
package com.talentPool.offerSheet.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.db.SimpleDataObject;
import com.talentPool.common.properties.GlobalApplicationProperties;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.properties.TPApplicationProperties;
import com.talentPool.common.properties.TPLabels;
import com.talentPool.common.utils.Utils;
import com.talentPool.common.utils.FileUtils.FileHandlerUtils;
import com.talentPool.custom.constants.CustomFieldConstants;
import com.talentPool.custom.dataobject.CustomFieldData;
import com.talentPool.custom.manager.CustomFieldManager;
import com.talentPool.documents.DocumentConstants;
import com.talentPool.documents.utils.DocumentUploader;
import com.talentPool.masters.dao.impl.SalaryComponentCategoryDAOImpl;
import com.talentPool.masters.service.impl.SalCompCategoryService;
import com.talentPool.offerSheet.OfferSheetConstants;
import com.talentPool.offerSheet.dataobject.OfferSheetTemplateData;
import com.talentPool.parser.converter.GenericConverter;
import com.talentPool.reports.ReportConstants;
import com.talentPool.salaryStructure.constants.SalaryStructureConstants;
import com.talentPool.salaryStructure.databject.SalaryComponentsData;
import com.talentPool.salaryStructure.entity.SalaryComponentCategory;
import com.talentPool.salaryStructure.manager.SalaryStructureManager;

/**
 * @author pallavi
 *
 */
public class OfferSheetUtils {
	private static String REGEXP_USER_DEFINED_VARIBLE = "\\$\\$\\$\\S+\\$\\$\\$";
	private static String USER_DEFINED_VARIBLE_BOUNDARY = "\\$\\$\\$";
	public static String getRegexpUserDefinedVariable() {
		return REGEXP_USER_DEFINED_VARIBLE;
	}
	public static String getUserDefinedVariableBoundary() {
		return USER_DEFINED_VARIBLE_BOUNDARY;
	}
	public boolean isValidExtension(String ext) {
		boolean isValidExtension = false;
		String[] validExts = TPApplicationProperties.getProperty("offer.sheet.ext.allowed").split(",");
		for(int i = 0; i < validExts.length; i++) {
			if(("."+validExts[i]).equalsIgnoreCase(ext)) {
				isValidExtension = true;
				break;
			}
		}
		return isValidExtension;
	}
	
	public String[] getDestinationPath(OfferSheetTemplateData templateData, String operation, String userId, int offerFormat) throws Exception {
		String[] retVal = new String[3];
		String destinationPath = null;
				
		String ext = null;			
		
		if(offerFormat==OfferSheetConstants.OFFER_FORMAT_PDF){
			ext = ".pdf";
		}else { 
			ext = getFileExtention(templateData.getTemplateFilePath());
		}
		
		String oFileName = userId + String.valueOf(System.currentTimeMillis()) + ext;
		
		if("preview".equals(operation)) {
			String basePath = Utils.concatFilePath(TPApplicationProperties.getProperty("application.path"), TPApplicationProperties.getProperty("ui.dir"));
			String reportsDir = Utils.concatFilePath(basePath, ReportConstants.REPORT_DESTINATION_FOLDER);
			destinationPath = Utils.concatFilePath(reportsDir, oFileName);
		} else {
			DocumentUploader documentUploader = new DocumentUploader();
			String dayFolder = documentUploader.createTodayFolder(DocumentConstants.documentsPath);
			destinationPath = Utils.concatFilePath(dayFolder, oFileName);
		}
		retVal[0] = destinationPath;
		retVal[1] = oFileName;
		retVal[2] = templateData.getTemplateName() + String.valueOf(System.currentTimeMillis()) + ext;
		
		return retVal;
	}
	
	private String getFileExtention(String templateFilePath) {		
		String ext = FileHandlerUtils.getFileExtention(templateFilePath, "");
		/**
		 * The WordReplaceUtil is saving in .doc format. 
		 * So return destination file extension as .doc 
		 */
		if(".docx".equals(ext)) {
			ext = ".doc";
		}
		return ext;
	}
	
	public List<String> extractAndStoreOfferSheetTemplateVariables(OfferSheetTemplateData template) throws FileNotFoundException, IOException, InvalidFormatException {
		List<String> templateVariables = null;
		String filePath = Utils.concatFilePath(DocumentConstants.documentsPath, template.getTemplateFilePath());
		
		if(FileHandlerUtils.isWordDoc(filePath)) {
			templateVariables = extractWordTemplateVariables(filePath);
		} else if(FileHandlerUtils.isExcelDoc(filePath)) {
			templateVariables = extractExcelTemplateVariables(filePath);
		} else {
			templateVariables = new ArrayList<String>();
		}
		
		return templateVariables;
	}
	
	private List<String> extractExcelTemplateVariables(String filePath) throws FileNotFoundException, IOException, InvalidFormatException {
		Pattern pattern = Pattern.compile(REGEXP_USER_DEFINED_VARIBLE);
				
		List<String> templateVariables = new ArrayList<String>();		
		InputStream stream = new FileInputStream(filePath);
		Workbook wb = WorkbookFactory.create(stream);

		Sheet sheet = wb.getSheetAt(0);

		Iterator<Row> rowItr = sheet.rowIterator();
		while (rowItr.hasNext()) {
			Row row = (Row) rowItr.next();
			Iterator<Cell> cellItr = row.cellIterator();
			while (cellItr.hasNext()) {
				Cell cell = (Cell) cellItr.next();
				String txt = cell.toString();
				if(!Utils.isBlankOrNull(txt)) {
					Matcher m = pattern.matcher(txt);
					if(m.find()) {
						String variable = m.group();
						if(!templateVariables.contains(variable)) {
							templateVariables.add(variable);
						}
					}					
				}
			}
		}	
		return templateVariables;
	}
	
	private List<String> extractWordTemplateVariables(String filePath) {
		Pattern pattern = Pattern.compile(REGEXP_USER_DEFINED_VARIBLE);			
		List<String> templateVariables = new ArrayList<String>();		
		GenericConverter genericConverter = new GenericConverter();
		String contents = genericConverter.convert(filePath);
		Matcher m = pattern.matcher(contents);
		while(m.find()) {
			String variable = m.group();
			if(!templateVariables.contains(variable)) {
				templateVariables.add(variable);
			}			
		}		
		return templateVariables;
	}
	
	public List<SimpleDataObject> getTemplateVariableAndApplicantAttributeMapping() {
		List<SimpleDataObject> mapping = new ArrayList<SimpleDataObject>();
		
		mapping.addAll(OfferSheetVariableUtils.geOfferSheetCommonVariable());
		
		mapping.addAll(OfferSheetVariableUtils.getOffersheetapplicantvariable());
		
		addApplicantCustomFields(mapping);
		
		mapping.addAll(OfferSheetVariableUtils.getOffersheetpositionvariable());
	
		addPositionCustomFields(mapping);
		
		addSalaryComponents(mapping);
		
		addSalaryCategories(mapping);
		
		return mapping;
	}
	
	private void addMapping(List<SimpleDataObject> mapping, String key, String value) {
		SimpleDataObject obj = new SimpleDataObject();
		obj.setAttribute("key", key);
		obj.setAttribute("value", value);
		mapping.add(obj);
	}
	
	private void addPositionCustomFields(List<SimpleDataObject> mapping){
		CustomFieldData fieldData = null;
		CustomFieldManager positionCustomFieldManager = new CustomFieldManager();
		List<CustomFieldData> positionCustomData = positionCustomFieldManager.getCustomFieldsFor(CustomFieldConstants.ENTITY_TYPE_POSITION, true);
		if(positionCustomData != null && positionCustomData.size() > 0) {
			for(int i = 0; i < positionCustomData.size(); i++) {
				fieldData = positionCustomData.get(i);
				addMapping(mapping, fieldData.getFieldName(), fieldData.getFieldDisplayName());				
			}
		}
	}
	
	private void addApplicantCustomFields(List<SimpleDataObject> mapping) {
		CustomFieldData fieldData = null;
		CustomFieldManager customFieldManager = new CustomFieldManager();
		List<CustomFieldData> data = customFieldManager.getCustomFieldsFor(CustomFieldConstants.ENTITY_TYPE_APPLICANT, true);
		if(data != null && data.size() > 0) {
			for(int i = 0; i < data.size(); i++) {
				fieldData = data.get(i);
				addMapping(mapping, fieldData.getFieldName(), fieldData.getFieldDisplayName());				
			}
		}
	}
	
	private void addSalaryComponents(List<SimpleDataObject> mapping){
		try {
			SalaryStructureManager salaryStructureManager = new SalaryStructureManager(); 
			List<SalaryComponentsData> salaryComponents = salaryStructureManager.getSalaryComponents();
			Iterator<SalaryComponentsData> itr =salaryComponents.iterator();
			String monthlyDesc = TPLabels.getLabel("master_salary_structure.salaryComponentType_monthly");
			String annualDesc = TPLabels.getLabel("master_salary_structure.salaryComponentType_yearly");
			while (itr.hasNext()) {
				SalaryComponentsData salaryComponentsData = (SalaryComponentsData) itr.next();
				if(SalaryStructureConstants.SALARY_PERIOD_MONTHLY.equals(salaryComponentsData.getSalaryComponentType())){
					addMapping(mapping,OfferSheetConstants.SAL_COMPONENT_PREFIX_MONTHLY+salaryComponentsData.getSalaryComponentId(),salaryComponentsData.getSalaryComponentName()+" "+monthlyDesc);
					addMapping(mapping,OfferSheetConstants.SAL_COMPONENT_PREFIX_ANNUAL+salaryComponentsData.getSalaryComponentId(),salaryComponentsData.getSalaryComponentName()+" "+annualDesc);
				}else {
					addMapping(mapping,OfferSheetConstants.SAL_COMPONENT_PREFIX_ANNUAL+salaryComponentsData.getSalaryComponentId(),salaryComponentsData.getSalaryComponentName()+" "+annualDesc);
				}
			}
			addMapping(mapping,OfferSheetConstants.MONTHLY_TOTAL,TPLabels.getLabel("master_salary_structure.monthly_total"));
			addMapping(mapping,OfferSheetConstants.ANNUAL_TOTAL,TPLabels.getLabel("master_salary_structure.annual_total"));
			addMapping(mapping,OfferSheetConstants.INPUT_SALARY_VARIABLE_MONTHLY,GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_SALARY_VARIABLE_INPUT_SALARY_LABEL)+" "+monthlyDesc);
			addMapping(mapping,OfferSheetConstants.INPUT_SALARY_VARIABLE_ANNUAL,GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_SALARY_VARIABLE_INPUT_SALARY_LABEL)+" "+annualDesc);
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
	}
	
	private void addSalaryCategories(List<SimpleDataObject> mapping){
		try {
			SalaryComponentCategoryDAOImpl salCompCategoryDao = new SalaryComponentCategoryDAOImpl();
			SalCompCategoryService salCmpCatService = new SalCompCategoryService();
			salCmpCatService.setSalCompCategoryDao(salCompCategoryDao);
			Map<String,SalaryComponentCategory> salCompMap = salCmpCatService.getSalaryComponentCategoryMap();
			if(salCompMap!=null){
				String monthlyDesc = TPLabels.getLabel("master_salary_structure.salaryComponentType_monthly");
				String annualDesc = TPLabels.getLabel("master_salary_structure.salaryComponentType_yearly");
				for (Entry<String,SalaryComponentCategory> entry : salCompMap.entrySet()) {
					SalaryComponentCategory salaryComponentCategory = entry.getValue();
					addMapping(mapping,OfferSheetConstants.SAL_CATEGORY_PREFIX_MONTHLY+salaryComponentCategory.getCategoryId(),salaryComponentCategory.getCategoryName()+" "+monthlyDesc);
					addMapping(mapping,OfferSheetConstants.SAL_CATEGORY_PREFIX_ANNUAL+salaryComponentCategory.getCategoryId(),salaryComponentCategory.getCategoryName()+" "+annualDesc);
				}
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
	}
}
