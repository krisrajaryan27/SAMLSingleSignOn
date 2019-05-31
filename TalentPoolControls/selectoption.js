var SelectOption = Class.create();
SelectOption.prototype ={
	initialize: function(oId,oText,oLevel,oChilds,oIsSelected,options) {
		this.oId=oId;
		this.oText=oText;
		this.oLevel=(!oLevel)?0:oLevel;
		this.oChilds=oChilds;
		this.oIsSelected=oIsSelected;
		this.options = options==null?{}:options;
		this.oTriState=false;
		this.oDisabled=false;
	},
	getId: function(){
		return this.oId;
	},
	setId: function(oId){
		this.oId=oId;
	},
	getText: function(){
		return this.oText;
	},
	getChilds: function(){
		return this.oChilds;
	},
	setChilds: function(oChilds){
		this.oChilds=oChilds;
	},
	getLevel: function(){
		return this.oLevel;
	},
	setLevel: function(oLevel){
		this.oLevel=oLevel;
	},
	isSelected: function(){
		return (this.oIsSelected=="1");
	},
	setSelected: function(val){
		if(val){
			this.oIsSelected="1";
		}else{
			this.oIsSelected="0";
		}
	},
	isTriState: function(){
		return this.oTriState;
	},
	setIsTriState: function(val){
		this.oTriState=val;
	},
	isDisabled: function(){
		return this.oDisabled;
	},
	setIsDisabled: function(val){
		this.oDisabled=val;
	},
	getOptions: function(){
		return this.options;
	},
	toString: function(){
		var str =  "\n[" + this.oId + ", "+this.oText + ", "+ this.oLevel + "," + this.oChilds + "]";
		return str;
	}
	
	
}
