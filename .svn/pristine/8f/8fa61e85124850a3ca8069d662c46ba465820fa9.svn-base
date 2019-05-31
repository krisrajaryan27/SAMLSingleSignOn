package com.talentPool.custom.action;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.mysql.fabric.xmlrpc.base.Array;
import com.talentPool.applicant.manager.ImportConfigurationManager;
import com.talentPool.common.NavigationConstants;
import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.base.TPDispatchAction;
import com.talentPool.common.db.SimpleDataObject;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.utils.Utils;
import com.talentPool.custom.constants.CustomFieldConstants;
import com.talentPool.custom.dataobject.CRColumnCustomFieldMapData;
import com.talentPool.custom.dataobject.CustomFieldData;
import com.talentPool.custom.form.CustomFieldForm;
import com.talentPool.custom.manager.CRColumnCustomFieldMapManager;
import com.talentPool.custom.manager.CustomFieldManager;
import com.talentPool.custom.utils.CustomFieldUtils;
import com.talentPool.custom.utils.CustomFieldXMLGenerator;
import com.talentPool.positions.manager.PositionScreenConfigurationManager;
import com.talentPool.user.constants.ModuleConstants;
import com.talentPool.user.constants.PermissionConstants;
import com.talentPool.user.manager.SessionManager;

public class CustomFieldScreenAction extends TPDispatchAction {
	
	//Start :- Custom Fields
	public ActionForward manageCustomFields(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "manageCustomFields";		
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		
		Integer[] permissions = new Integer[2];
		permissions[0] = PermissionConstants.PERMISSION_ADMIN;
		permissions[1] = PermissionConstants.PERMISSION_CUSTOM_FIELDS;		
		if(!isUserAuthorized(request, ModuleConstants.MODULE_CUSTOM_FIELDS, permissions,null,null,null)) {
			forward = "authorizationFailure";			
			return mapping.findForward(forward);
		}
				
		try {
			request.setAttribute("t", NavigationConstants.T_ADMIN);
			request.setAttribute("mainPane", NavigationConstants.MAINPANE_ADMIN_CUSTOM_FIELDS);						
		} catch (Exception e) {
			TPLogger.getLogger().error("Error in custom fields", e);
		}
		return mapping.findForward(forward);
	}
	
	public ActionForward getCustomFieldsXML(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "xmlFile";
		String xmlFile = new String();
		try {
			if (!SessionManager.isLoginFound(mapping, actionForm, request, response, this)) {
				xmlFile = Utils.getXMLForSessionExpiry();
			} else {				
				CustomFieldForm customFieldForm = (CustomFieldForm) actionForm;				
				String entityType = customFieldForm.getEntityType();
				CustomFieldManager customFieldManager = new CustomFieldManager();
				List<CustomFieldData> fields = customFieldManager.getCustomFieldsForType(entityType);
				CustomFieldXMLGenerator customFieldXMLGenerator= new CustomFieldXMLGenerator();
				xmlFile = customFieldXMLGenerator.getXMLForCustomFields(fields, entityType);					
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while deleting user", e);
			xmlFile = Utils.getXMLForError(null);
		}
		request.setAttribute("xmlFile", xmlFile);
		return mapping.findForward(forward);
	}
	
	public ActionForward addCustomField(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "addCustomField";
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}		
		Integer[] permissions = new Integer[2];
		permissions[0] = PermissionConstants.PERMISSION_ADMIN;
		permissions[1] = PermissionConstants.PERMISSION_CUSTOM_FIELDS;		
		if(!isUserAuthorized(request, ModuleConstants.MODULE_CUSTOM_FIELDS, permissions,null,null,null)) {
			forward = "authorizationFailure";			
			return mapping.findForward(forward);
		}
		try {
			CustomFieldForm customFieldForm = (CustomFieldForm) actionForm;
			String entityType = customFieldForm.getEntityType();
			
			if(!Utils.isBlankOrNull(customFieldForm.getCustomFieldId())) { 
				CustomFieldManager customFieldManager = new CustomFieldManager();
				CustomFieldData customFieldData = null;
				if(entityType!=null && !entityType.contains(",") && CustomFieldConstants.ENTITY_TYPE_APPLICANT_TABLE==Integer.parseInt(entityType)){
					customFieldData=customFieldManager.getCustomFieldTableData(customFieldForm.getCustomFieldId());
					customFieldData.setFieldEntityType(CustomFieldConstants.ENTITY_TYPE_APPLICANT_TABLE);
					customFieldData.setFieldName(customFieldData.getFieldDisplayName());
			}else{
					customFieldData = customFieldManager.getCustomField(customFieldForm.getCustomFieldId());
//					String options = customFieldData.getFieldOptions();
//					if (!Utils.isBlankOrNull(options) && options.contains("{")){
//						String[] opts = options.split(Pattern.quote("}"));
//						Arrays.sort(opts);
//						options = "";
//						for (int i=0;i<opts.length;i++){
//								options = options+opts[i] + "}";
//						}
//						customFieldData.setFieldOptions(options);
// 					}
			}
				populateForm(customFieldForm, customFieldData);
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while getting custom field", e);
		}
		return mapping.findForward(forward);
	}
	
	public ActionForward saveCustomField(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "addCustomField";
		CustomFieldForm customFieldForm = (CustomFieldForm) actionForm;
		ActionErrors errors = new ActionErrors();
		ArrayList<String>error=null;
		try {			
			
			CustomFieldData customFieldData = populateCustomFieldData(customFieldForm);
			entityWiseCustomFieldData(customFieldData);
			CustomFieldManager customFieldManager = new CustomFieldManager();
			if(Utils.isBlankOrNull(customFieldData.getFieldId())) {				
				error=customFieldManager.addNewCustomField(customFieldData);
			} else {
				customFieldManager.updateCustomField(customFieldData);
			}		
			if(error.size()>0){
				
				//	errors.add(error.get(0), new ActionError(error.get(0)));
					errors.add(error.get(0), new ActionError(error.get(0)));
					saveErrors(request, errors);
				}
			else{
							request.setAttribute("update", "1");
							CustomFieldManager.reloadCustomFieldsMaps();
							
							if(customFieldData.getFieldEntityType() == CustomFieldConstants.ENTITY_TYPE_APPLICANT){
								ImportConfigurationManager.reloadImportFieldsMaps();
							}
							
							if(customFieldData.getFieldEntityType() == CustomFieldConstants.ENTITY_TYPE_POSITION){
								PositionScreenConfigurationManager.reloadPositionFieldsMaps();
							}
			}
			
		} catch (Exception e) {
			
			errors.add("admin.custom_fields.error.addOrUpdate", new ActionError("admin.custom_fields.error.addOrUpdate"));
			saveErrors(request, errors);
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return mapping.findForward(forward);
	}
	
	public ActionForward deleteCustomField(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "xmlFile";
		String xmlFile = null;
		try {
			if (!SessionManager.isLoginFound(mapping, actionForm, request, response, this)) {
				xmlFile = Utils.getXMLForSessionExpiry();
			} else {
				CustomFieldForm customFieldForm = (CustomFieldForm) actionForm;
				String customFieldId = customFieldForm.getCustomFieldId();
				String customFieldEntityType = customFieldForm.getEntityType();				
				CustomFieldManager customFieldManager = new CustomFieldManager();				
				if(customFieldEntityType.equalsIgnoreCase(""+CustomFieldConstants.ENTITY_TYPE_APPLICANT_TABLE)){
					customFieldManager.deleteCustomTable(customFieldId);
				}else{
				customFieldManager.deleteCustomField(customFieldId, customFieldEntityType);
					CustomFieldManager.reloadCustomFieldsMaps();
				}
				xmlFile = Utils.getXMLForIds(customFieldId);
				if(customFieldEntityType.equals(""+ CustomFieldConstants.ENTITY_TYPE_APPLICANT)){
					ImportConfigurationManager.reloadImportFieldsMaps();
				}
				
				if(customFieldEntityType.equalsIgnoreCase(""+CustomFieldConstants.ENTITY_TYPE_POSITION)){
					PositionScreenConfigurationManager.reloadPositionFieldsMaps();
				}
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			xmlFile = Utils.getXMLForError();
		}
		request.setAttribute("xmlFile", xmlFile);
		return mapping.findForward(forward);		
	}
	
	private CustomFieldData populateCustomFieldData(CustomFieldForm customFieldForm) {
		CustomFieldData customFieldData = new CustomFieldData();
		customFieldData.setFieldId(customFieldForm.getCustomFieldId());
		customFieldData.setFieldName(customFieldForm.getCustomFieldName());
		customFieldData.setFieldType(customFieldForm.getCustomFieldType());
		customFieldData.setFieldAttributes(customFieldForm.getCustomFieldAttributes());
		customFieldData.setFieldOtherAttributes(customFieldForm.getCustomFieldOtherAttributes());
		customFieldData.setFieldDisplayName(customFieldForm.getCustomFieldDisplayName());
		customFieldData.setFieldDefaultValue(customFieldForm.getCustomFieldDefaultValue());
		customFieldData.setFieldOptions(customFieldForm.getCustomFieldOptions());
		if(!Utils.isBlankOrNull(customFieldForm.getCustomFieldRequired())){
		customFieldData.setFieldRequired(Integer.parseInt(customFieldForm.getCustomFieldRequired()));
		}
		if(!Utils.isBlankOrNull(customFieldForm.getCustomFieldSearchable())){
		customFieldData.setFieldSearchable(Integer.parseInt(customFieldForm.getCustomFieldSearchable()));		
		}
		customFieldData.setFieldEntityType(Integer.parseInt(customFieldForm.getEntityType()));
		customFieldData.setCustomFieldTableColumnIds(customFieldForm.getCustomFieldTableColumnIds());
		return customFieldData;
	}
	
	private void populateForm(CustomFieldForm customFieldForm, CustomFieldData customFieldData) {
		customFieldForm.setCustomFieldId(customFieldData.getFieldId());		
		String newFieldName = CustomFieldUtils.splitFieldName(customFieldData.getFieldName().trim(),customFieldData.getFieldEntityType());
		customFieldForm.setCustomFieldName(newFieldName);		
		customFieldForm.setCustomFieldType(customFieldData.getFieldType());
		customFieldForm.setCustomFieldAttributes(customFieldData.getFieldAttributes());
		customFieldForm.setCustomFieldOtherAttributes(customFieldData.getFieldOtherAttributes());
		customFieldForm.setCustomFieldDisplayName(customFieldData.getFieldDisplayName().trim());
		customFieldForm.setCustomFieldDefaultValue(customFieldData.getFieldDefaultValue());
		customFieldForm.setCustomFieldOptions(customFieldData.getFieldOptions());
		customFieldForm.setCustomFieldRequired(String.valueOf(customFieldData.getFieldRequired()));
		customFieldForm.setCustomFieldSearchable(String.valueOf(customFieldData.getFieldSearchable()));
		customFieldForm.setEntityType(String.valueOf(customFieldData.getFieldEntityType()));		
		customFieldForm.setCustomFieldTableColumnIds(customFieldData.getCustomFieldTableColumnIds());
	}
	
	private void entityWiseCustomFieldData(CustomFieldData customFieldData) {		
		if(customFieldData.getFieldEntityType()==CustomFieldConstants.ENTITY_TYPE_APPLICANT){
			customFieldData.setFieldName("app_"+customFieldData.getFieldName());			
		}else if(customFieldData.getFieldEntityType()==CustomFieldConstants.ENTITY_TYPE_POSITION){
			customFieldData.setFieldName("pos_"+customFieldData.getFieldName());
		}
	}

	public ActionForward customFieldMapping(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "customFieldMapping";
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}		
		Integer[] permissions = new Integer[2];
		permissions[0] = PermissionConstants.PERMISSION_ADMIN;
		permissions[1] = PermissionConstants.PERMISSION_CUSTOM_FIELDS;		
		if(!isUserAuthorized(request, ModuleConstants.MODULE_CUSTOM_FIELDS, permissions,null,null,null)) {
			forward = "authorizationFailure";			
			return mapping.findForward(forward);
		}
		try {
			String strEntityType = request.getParameter("entityType");
			int entityType = CustomFieldConstants.ENTITY_TYPE_POSITION;
			try {
				entityType = Integer.parseInt(strEntityType); 
			} catch (Exception e) {
			}
			CustomFieldManager customFieldManager = new CustomFieldManager();
			CRColumnCustomFieldMapManager cfManager = new CRColumnCustomFieldMapManager();
			ArrayList<CRColumnCustomFieldMapData> list = cfManager.getCustomFieldsFor(entityType);
			request.setAttribute("cfArray", list);
			request.setAttribute("entityType", entityType);
			request.setAttribute("jsArrayCustomFields", customFieldManager.getJSArrayCustomFields(entityType));
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while getting custom field", e);
		}
		return mapping.findForward(forward);
	}	
	
	public ActionForward saveCustomFieldMapping(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "customFieldMapping";
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}		
		Integer[] permissions = new Integer[2];
		permissions[0] = PermissionConstants.PERMISSION_ADMIN;
		permissions[1] = PermissionConstants.PERMISSION_CUSTOM_FIELDS;		
		if(!isUserAuthorized(request, ModuleConstants.MODULE_CUSTOM_FIELDS, permissions,null,null,null)) {
			forward = "authorizationFailure";			
			return mapping.findForward(forward);
		}
		try {
			CustomFieldForm customFieldForm = (CustomFieldForm) actionForm;
			String selectedCustomFieldMapping = customFieldForm.getSelectedCustomFieldMapping();
			CRColumnCustomFieldMapManager cfManager = new CRColumnCustomFieldMapManager();
			cfManager.saveCustomFieldsMapping(selectedCustomFieldMapping);
			request.setAttribute("save", "1");
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while saving custom field mapping", e);
		}
		return mapping.findForward(forward);
	}	
	
	//End :- Custom Fields
	
	public ActionForward fetchCustomFieldsForTableXML(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response){
		String forward = "xmlFile";
		String xmlFile = "";
		//logic to fetch tabular custom fields list
		CustomFieldManager customFieldManager = new CustomFieldManager();
		List<CustomFieldData> fields = null;
		CustomFieldForm customFieldForm = (CustomFieldForm) actionForm;
		String fieldId = customFieldForm.getCustomFieldId();
		fields = customFieldManager.getCustomFieldsForTableType(""+CustomFieldConstants.ENTITY_TYPE_APPLICANT_TABLE_FIELD, fieldId);
		HashMap<String,Object> valMap = new HashMap<>();
		for(CustomFieldData data: fields){
			valMap.put(data.getFieldId(),data.getFieldDisplayName());
		}
		xmlFile = Utils.getXMLForMapForDHTMLGrid(valMap);
		request.setAttribute("xmlFile", xmlFile);
		return mapping.findForward(forward);
	}

	public ActionForward saveTabularCustomField(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "addCustomField";
		CustomFieldForm customFieldForm = (CustomFieldForm) actionForm;
		try {			
			CustomFieldData cData = populateCustomFieldData(customFieldForm);
			CustomFieldManager customFieldManager = new CustomFieldManager();
			customFieldManager.addNewCustomFieldTable(cData);
			request.setAttribute("success", "1");
		} catch (Exception e) {
			ActionErrors errors = new ActionErrors();
			errors.add("admin.custom_fields.error.addOrUpdate", new ActionError("admin.custom_fields.error.addOrUpdate"));
			saveErrors(request, errors);
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return mapping.findForward(forward);
	}
	
	public ActionForward getCustomTabularFieldsXML(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "xmlFile";
		String xmlFile = new String();
		try {
			if (!SessionManager.isLoginFound(mapping, actionForm, request, response, this)) {
				xmlFile = Utils.getXMLForSessionExpiry();
			} else {				
				CustomFieldForm customFieldForm = (CustomFieldForm) actionForm;				
				String entityType = customFieldForm.getEntityType();
				CustomFieldManager manager = new CustomFieldManager();
				List<CustomFieldData> result= manager.getAllCustomFieldTablesForEntityType(entityType);
				HashMap<String,Object> valMap = new HashMap<>();
				CustomFieldXMLGenerator customFieldXMLGenerator= new CustomFieldXMLGenerator();
				xmlFile = customFieldXMLGenerator.getXMLForCustomFields(result,""+ CustomFieldConstants.ENTITY_TYPE_APPLICANT_TABLE);	
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while fetching custom tables user", e);
		}
		request.setAttribute("xmlFile", xmlFile);
		return mapping.findForward(forward);
	}
}
