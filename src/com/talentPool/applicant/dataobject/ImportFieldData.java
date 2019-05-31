/**
 * 
 */
package com.talentPool.applicant.dataobject;

import com.talentPool.common.db.SimpleDataObject;

/**
 * @author Ajeet
 *
 */
public class ImportFieldData extends SimpleDataObject{

	public String getFieldId() {
		return getString("fieldId");
	}

	public void setFieldId(String fieldId) {
		setAttribute("fieldId", fieldId);
	}
	
	public String getFieldImportShow() {
		return getString("fieldImportShow");
	}

	public void setFieldImportShow(String fieldImportShow) {
		setAttribute("fieldImportShow", fieldImportShow);
	}
	
	public String getFieldImportMandatory() {
		return getString("fieldImportMandatory");
	}

	public void setFieldImportMandatory(String fieldImportMandatory) {
		setAttribute("fieldImportMandatory", fieldImportMandatory);
	}
	
	public String getFieldEditShow() {
		return getString("fieldEditShow");
	}

	public void setFieldEditShow(String fieldEditShow) {
		setAttribute("fieldEditShow", fieldEditShow);
	}
	
	public String getFieldRank() {
		return getString("fieldRank");
	}

	public void setFieldRank(String fieldRank) {
		setAttribute("fieldRank", fieldRank);
	}
	
	public String getFieldType() {
		return getString("fieldType");
	}

	public void setFieldType(String fieldType) {
		setAttribute("fieldType", fieldType);
	}
	
	public String getFieldTitle() {
		return getString("fieldTitle");
	}

	public void setFieldTitle(String fieldTitle) {
		setAttribute("fieldTitle", fieldTitle);
	}
	
	public String getFieldVendorMandatory() {
		return getString("fieldVendorMandatory");
	}

	public void setFieldVendorMandatory(String fieldVendorMandatory) {
		setAttribute("fieldVendorMandatory", fieldVendorMandatory);
	}
	
	public String getFieldVendorShow() {
		return getString("fieldVendorShow");
	}

	public void setFieldVendorShow(String fieldVendorShow) {
		setAttribute("fieldVendorShow", fieldVendorShow);
	}
		
	public String getFieldEmployeeMandatory() {
		return getString("fieldEmployeeMandatory");
	}

	public void setFieldEmployeeMandatory(String fieldEmployeeMandatory) {
		setAttribute("fieldEmployeeMandatory", fieldEmployeeMandatory);
	}
	
	public String getFieldEmployeeShow() {
		return getString("fieldEmployeeShow");
	}

	public void setFieldEmployeeShow(String fieldEmployeeShow) {
		setAttribute("fieldEmployeeShow", fieldEmployeeShow);
	}
	
	public String getFieldWebsiteShow(){
		return getString("fieldWebsiteShow");
	}
	
	public void setFieldWebsiteShow(String fieldWebsiteShow) {
		setAttribute("fieldWebsiteShow", fieldWebsiteShow);
	}
	
	public String getFieldWebsiteMandatory() {
		return getString("fieldWebsiteMandatory");
	}

	public void setFieldWebsiteMandatory(String fieldWebsiteMandatory) {
		setAttribute("fieldWebsiteMandatory", fieldWebsiteMandatory);
	}
	
	public String getFieldConfidential() {
		return getString("fieldConfidential");
	}

	public void setFieldConfidential(String fieldConfidential) {
		setAttribute("fieldConfidential", fieldConfidential);
	}
	
	public String isProcessField() {
		return getString("isProcessField");
	}

	public void setIsProcessField(String isProcessField) {
		setAttribute("isProcessField", isProcessField);
	}
	
	public void setType(String type){
		setAttribute("type", type);
	}
	
	public String getType(){
		return getString("type");
	}
	
	public void setRegex(String regex){
		setAttribute("regex", regex);
	}
	
	public String getRegex(){
		return getString("regex");
	}
	
	public String getRegexMessage(){
		return getString("regexMessage");
	}
	
	public void setRegexMessage(String regexMessage){
		setAttribute("regexMessage",	regexMessage);
	}
	
	public void setOptionList(String optionList){
		setAttribute("optionList", optionList);
	}
	
	public String getOptionList(){
		return getString("optionList");
	}
	
	public void setDefaultValue(String defaultValue){
		setAttribute("defaultValue", defaultValue);
	}	
	
	public String getDefaultValue(){
		return getString("defaultValue");
	}
	
	public void setWaterMarkPlaceHolder(String waterMarkPlaceHolder){
		setAttribute("waterMarkPlaceHolder",waterMarkPlaceHolder);
	}
	
	public String getWaterMarkPlaceHolder(){
		return getString("waterMarkPlaceHolder");
	}
	
	public void setApiUrl(String apiUrl){
		setAttribute("apiUrl", apiUrl);
	}
	
	public String getApiUrl(){
		return getString("apiUrl");
	}
	
	public void setCSSClassName(String cssClassName){
		setAttribute("cssClassName", cssClassName);
	}
	
	public String getCSSClassName(){
		return getString("cssClassName");
	}

	public void setPropertyName(String propertyName){
		setAttribute("propertyName", propertyName);
	}
	
	public String getPropertyName(){
		return getString("propertyName");
	}
	
	public void setParentId(String parentId){
		setAttribute("parentId",parentId);
	}
	
	public String getParentId(){
		return getString("parentId");
	}
	
	public void setId(String id){
		setAttribute("id", id);
	}
	
	public String getId(){
		return getString("id");
	}
	
	public void setDisplayName(String displayName){
		setAttribute("displayName", displayName);
	}
	
	public String getDisplayName(){
		return getString("displayName");
	}
	
}


