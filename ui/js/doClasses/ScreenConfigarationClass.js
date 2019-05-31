//Class For Field
function Field(){
	this.index=0;
	this.fieldId=0;
	this.fieldTitle='';
	this.fieldType=0;
	this.isProcessField=0;
	this.fieldImportShow=0;
	this.fieldEditShow=0;
	this.fieldImportMandatory=0;
	this.fieldConfidential=0;
	this.fieldVendorShow=0;
	this.fieldVendorMandatory=0;
	this.fieldEmployeeShow=0;
	this.fieldEmployeeMandatory=0;
	this.fieldWebsiteShow=0;
	this.fieldWebsiteMandatory=0;
	
	this.setIndex=setIndex;
	this.setFieldId=setFieldId;
	this.setFieldTitle=setFieldTitle;
	this.setFieldType=setFieldType;
	this.setIsProcessField=setIsProcessField;
	this.setFieldImportShow=setFieldImportShow;
	this.setFieldEditShow=setFieldEditShow;
	this.setFieldImportMandatory=setFieldImportMandatory;
	this.setFieldConfidential=setFieldConfidential;	
	this.setFieldVendorShow=setFieldVendorShow;
	this.setFieldVendorMandatory=setFieldVendorMandatory;
	this.setFieldEmployeeShow=setFieldEmployeeShow;
	this.setFieldEmployeeMandatory=setFieldEmployeeMandatory;
	this.setFieldWebsiteShow=setFieldWebsiteShow;
	this.setFieldWebsiteMandatory=setFieldWebsiteMandatory;
	
	this.toString=toString;
}

function setIndex(index){this.index=index;}
function setFieldId(fieldId){this.fieldId=fieldId;}
function setFieldTitle(fieldTitle){this.fieldTitle=fieldTitle;}
function setFieldType(fieldType){this.fieldType=fieldType}
function setIsProcessField(isProcessField){this.isProcessField=isProcessField}
function setFieldImportShow(fieldImportShow){this.fieldImportShow=fieldImportShow;}
function setFieldEditShow(fieldEditShow){this.fieldEditShow=fieldEditShow;}
function setFieldImportMandatory(fieldImportMandatory){this.fieldImportMandatory=fieldImportMandatory}
function setFieldVendorShow(fieldVendorShow){this.fieldVendorShow=fieldVendorShow;}
function setFieldVendorMandatory(fieldVendorMandatory){this.fieldVendorMandatory=fieldVendorMandatory}
function setFieldEmployeeShow(fieldEmployeeShow){this.fieldEmployeeShow=fieldEmployeeShow;}
function setFieldEmployeeMandatory(fieldEmployeeMandatory){this.fieldEmployeeMandatory=fieldEmployeeMandatory}
function setFieldWebsiteShow(fieldWebsiteShow){this.fieldWebsiteShow=fieldWebsiteShow;}
function setFieldWebsiteMandatory(fieldWebsiteMandatory){this.fieldWebsiteMandatory=fieldWebsiteMandatory}
function setFieldConfidential(fieldConfidential){this.fieldConfidential=fieldConfidential}

function toString(){
	var str = "";
	str += formatString(''+this.fieldId) + "|";
	str += formatString(this.fieldType) + "|";
	str += formatString(''+this.fieldImportShow) + "|";
	str += formatString(this.fieldEditShow) + "|";
	str += formatString(this.fieldImportMandatory) + "|";
	str += formatString(this.fieldVendorShow) + "|";
	str += formatString(this.fieldVendorMandatory) + "|";
	str += formatString(this.fieldEmployeeShow) + "|";
	str += formatString(this.fieldEmployeeMandatory) + "|";
	str += formatString(this.fieldWebsiteShow) + "|";
	str += formatString(this.fieldWebsiteMandatory) + "|";
	str += formatString(this.fieldConfidential) + "|";
	str += (parseInt(this.index) + 1);
	return str;
}