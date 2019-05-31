//Class For Field
function PosScreenConfFields(){
	this.index=0;
	this.fieldId=0;
	this.fieldTitle='';
	this.fieldType=0;
	this.fieldOnPositionPrintShow=0;
	this.fieldPositionShow=0;
	this.fieldPositionMandatory=0;
	this.fieldVendorShow=0;
	this.fieldEmployeeShow=0;
	
	this.setIndex=setIndex;
	this.setFieldId=setFieldId;
	this.setFieldTitle=setFieldTitle;
	this.setFieldType=setFieldType;
	this.setFieldOnPositionPrintShow=setFieldOnPositionPrintShow;
	this.setFieldPositionShow=setFieldPositionShow;
	this.setFieldPositionMandatory=setFieldPositionMandatory;
	this.setFieldVendorShow=setFieldVendorShow;
	this.setFieldEmployeeShow=setFieldEmployeeShow;
	//this.toString=toString;
}

function setIndex(index){this.index=index;}
function setFieldId(fieldId){this.fieldId=fieldId;}
function setFieldTitle(fieldTitle){this.fieldTitle=fieldTitle;}
function setFieldType(fieldType){this.fieldType=fieldType;}
function setFieldOnPositionPrintShow(fieldOnPositionPrintShow){this.fieldOnPositionPrintShow=fieldOnPositionPrintShow;}
function setFieldPositionShow(fieldPositionShow){this.fieldPositionShow=fieldPositionShow;}
function setFieldPositionMandatory(fieldPositionMandatory){this.fieldPositionMandatory=fieldPositionMandatory;}
function setFieldVendorShow(fieldVendorShow){this.fieldVendorShow=fieldVendorShow;}
function setFieldEmployeeShow(fieldEmployeeShow){this.fieldEmployeeShow=fieldEmployeeShow;}

/*function toString(){
	var str = "";
	str += formatString(''+this.fieldId) + "|";
	str += formatString(this.fieldType) + "|";
	str += formatString(''+this.fieldOnPositionPrintShow) + "|";
	str += (parseInt(this.index) + 1);
	return str;
}*/