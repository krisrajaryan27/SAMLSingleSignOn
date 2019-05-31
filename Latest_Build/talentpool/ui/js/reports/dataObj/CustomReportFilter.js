var CustomReportFilter = Class.create({
  initialize: function(filterId, visibility, value, value1, value2) {
	  this.filterId  = filterId;
	  this.visibility = visibility;
	  this.value = value;
	  this.value1 = value1;
	  this.value2 = value2;
  },
  
    /*** Getters ***/
    getFilterId: function(){
		return this.filterId;
	},
	getVisibility: function(){
		return this.visibility;
	},
	getValue: function(){
		return this.value;
	},
	getValue1: function(){
		return this.value1;
	},
	getValue2: function(){
		return this.value2;
	},
		
	/*** Setters ****/
	setFilterId: function(filterId){
		this.filterId=filterId;
	},
	setVisibility: function(visibility){
		this.visibility=visibility;
	},
	setValue: function(value){
		this.value=value;
	},
	setValue1: function(value1){
		this.value1=value1;
	},
	setValue2: function(value2){
		this.value2=value2;
	}
});
