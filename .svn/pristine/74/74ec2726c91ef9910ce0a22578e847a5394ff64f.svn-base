var CustomFieldData =Class.create();
CustomFieldData.prototype = {
	initialize: function(name, displayName, type, required, fieldId, fieldValue){
		this.name=name;
		this.displayName=displayName;
		this.type=type;
		this.required=required;
		this.fieldId=fieldId;
		this.fieldValue=fieldValue;
	},
	isRequired: function(){
		if(this.required=='1'){
			return true;
		}else{
			return false;
		}
	},
	getDisplayName: function(){
		return this.displayName;
	},
	getName: function(){
		return this.name;
	},
	getType: function(){
		return this.type;
	},
	getFieldId: function(){
		return this.fieldId;
	},
	getFieldValue: function(){
		return this.fieldValue;
	},
	
	
	/*** Setters ****/
	
	setIsRequired: function(required){
		if(required=='1')
			this.required=required;
		else
			this.required='0';
	},
	setDisplayName: function(displayName){
		this.displayName=displayName;
	},
	setName: function(name){
		this.name=name;
	},
	setType: function(type){
		this.type=type;
	},
	setFieldId: function(fieldId){
		this.fieldId=fieldId;
	},
	setFieldValue: function(fieldValue){
		this.fieldValue=fieldValue;
	}
}