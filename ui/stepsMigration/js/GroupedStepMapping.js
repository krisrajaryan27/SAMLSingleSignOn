/**
 * @author PraveenK
 * @since  Jan 4, 2012
 */
var GroupedStepMapping = Class.create({
  initialize: function(groupedStepId, stepIds, stepName, stepMappingId) {
	  this.groupedStepId  = groupedStepId;
	  this.stepIds = stepIds;
	  this.stepName = stepName;
	  this.stepMappingId = stepMappingId;
  },
  
  
	getGroupedStepId: function(){
		return this.groupedStepId;
	},
	getStepIds: function(){
		return this.stepIds;
	},
	getStepName: function(){
		return this.stepName;
	},
	getStepMappingId: function(){
		return this.stepMappingId;
	},
	
	
	/*** Setters ****/
	setGroupedStepId: function(groupedStepId){
		this.groupedStepId=groupedStepId;
	},
	setStepIds: function(stepIds){
		this.stepIds=stepIds;
	},
	setStepName: function(stepName){
		this.stepName=stepName;
	},
	setStepMappingId: function(stepMappingId){
		this.stepMappingId=stepMappingId;
	}
});