//Class For Field
function DetailsConfField(){
	this.index=0;
	this.fieldId=0;
	this.fieldTitle='';
	this.fieldType=0;
	this.fieldOnPositionDetailsShow=0;
	
	this.setIndex=setIndex;
	this.setFieldId=setFieldId;
	this.setFieldTitle=setFieldTitle;
	this.setFieldType=setFieldType;
	this.setFieldOnPositionDetailsShow=setFieldOnPositionDetailsShow;
	
	//this.toString=toString;
}

function setIndex(index){this.index=index;}
function setFieldId(fieldId){this.fieldId=fieldId;}
function setFieldTitle(fieldTitle){this.fieldTitle=fieldTitle;}
function setFieldType(fieldType){this.fieldType=fieldType;}
function setFieldOnPositionDetailsShow(fieldOnPositionDetailsShow){this.fieldOnPositionDetailsShow=fieldOnPositionDetailsShow;}
function isFilter(fieldIsFilter){this.fieldIsFilter=fieldIsFilter;}

/*function toString(){
	var str = "";
	str += formatString(''+this.fieldId) + "|";
	str += formatString(this.fieldType) + "|";
	str += formatString(this.fieldOnPositionDetailsShow) + "|";
	str += (parseInt(this.index) + 1);
	return str;
}*/