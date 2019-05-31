var CustomReportColumn = Class.create({
  initialize: function(key, displayName, groupBy, sortBy) {
	  this.key  = key;
	  this.displayName = displayName;
	  this.groupBy = groupBy;
	  this.sortBy= sortBy;
  },
  
    /*** Getters ***/
    getKey: function(){
		return this.key;
	},
	getDisplayName: function(){
		return this.displayName;
	},
	getGroupBy: function(){
		return this.groupBy;
	},
	getSortBy: function(){
		return this.sortBy;
	},
		
	/*** Setters ****/
	setKey: function(key){
		this.key=key;
	},
	setDisplayName: function(displayName){
		this.displayName=displayName;
	},
	setGroupBy: function(groupBy){
		this.groupBy=groupBy;
	},
	setSortBy: function(sortBy){
		this.sortBy=sortBy;
	}
});
