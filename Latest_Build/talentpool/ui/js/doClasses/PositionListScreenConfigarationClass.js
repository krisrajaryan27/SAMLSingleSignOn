//Class For Field
function ListConfField(){
	this.index=0;
	this.fieldId=0;
	this.fieldTitle='';
	this.fieldType=0;
	this.fieldOnPositionListShow=0;
	this.fieldIsFilter=0;
	this.fieldIsFilterEditable=0;
	
	this.setIndex=setIndex;
	this.setFieldId=setFieldId;
	this.setFieldTitle=setFieldTitle;
	this.setFieldType=setFieldType;
	this.setFieldOnPositionListShow=setFieldOnPositionListShow;
	this.setIsFilter=setIsFilter;
	this.setIsFilterEditable=setIsFilterEditable;
	//this.toString=toString;
}

function setIndex(index){this.index=index;}
function setFieldId(fieldId){this.fieldId=fieldId;}
function setFieldTitle(fieldTitle){this.fieldTitle=fieldTitle;}
function setFieldType(fieldType){this.fieldType=fieldType;}
function setFieldOnPositionListShow(fieldOnPositionListShow){this.fieldOnPositionListShow=fieldOnPositionListShow;}
function setIsFilter(fieldIsFilter){this.fieldIsFilter=fieldIsFilter;}
function setIsFilterEditable(fieldIsFilterEditable){this.fieldIsFilterEditable=fieldIsFilterEditable;}

/*function toString(){
	var str = "";
	str += formatString(''+this.fieldId) + "|";
	str += formatString(this.fieldType) + "|";
	str += formatString(''+this.fieldOnPositionListShow) + "|";
	str += (parseInt(this.index) + 1);
	return str;
}*/