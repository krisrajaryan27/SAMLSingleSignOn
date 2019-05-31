var FeedbackFormField = Class.create();
FeedbackFormField.prototype ={
	initialize: function(fieldId, feedbackFieldId, fieldTitle, fieldDesc, fieldType, commentRequired, ratingId, multipleSelectId,displayType,isMandatory,systemGenerated,feedbackFieldType,applicantFieldId){
		this.fieldId=fieldId;
		this.feedbackFieldId=feedbackFieldId;
		this.fieldTitle=fieldTitle;
		this.fieldDesc=fieldDesc;
		this.fieldType=fieldType;
		this.commentRequired=commentRequired;
		this.ratingId=ratingId;
		this.multipleSelectId=multipleSelectId;
		this.displayType=displayType;
		this.isMandatory=isMandatory;
		this.systemGenerated=systemGenerated;
		this.feedbackFieldType=feedbackFieldType;
		this.applicantFieldId=applicantFieldId;
	},
	
	setFieldId: function(fieldId){
		this.fieldId=fieldId;
	},
	
	getFieldId: function(){
		return this.fieldId;
	},
	
	setFeedbackFieldId: function(feedbackFieldId){
		this.feedbackFieldId=feedbackFieldId;
	},
	
	getFeedbackFieldId: function(){
		return this.feedbackFieldId;
	},
	
	setFieldTitle: function(fieldTitle){
		this.fieldTitle=fieldTitle;
	},
	
	getFieldTitle: function(){
		return this.fieldTitle;
	},
	setFieldDesc: function(fieldDesc){
		this.fieldDesc=fieldDesc;
	},
	
	getFieldDesc: function(){
		return this.fieldDesc;
	},
	setFieldType: function(fieldType){
		this.fieldType=fieldType;
	},
	
	getFieldType: function(){
		return this.fieldType;
	},
	
	setRatingId: function(ratingId){
		this.ratingId=ratingId;
	},
	
	getRatingId: function(){
		return this.ratingId;
	},
	
	setMultipleSelectId: function(multipleSelectId){
		this.multipleSelectId=multipleSelectId;
	},
	
	getMultipleSelectId: function(){
		return this.multipleSelectId;
	},
	
	setCommentRequired: function(commentRequired){
		this.commentRequired=commentRequired;
	},
	
	getCommentRequired: function(){
		return this.commentRequired;
	},
	
	setDisplayType: function(displayType){
		this.displayType=displayType;
	},
	
	getDisplayType: function(){
		return this.displayType;
	},
	
	setIsMandatory: function(isMandatory){
		this.isMandatory=isMandatory;
	},
	
	getIsMandatory: function(){
		return this.isMandatory;
	},
	
	setSystemGenerated: function(systemGenerated){
		this.systemGenerated=systemGenerated;
	},
	
	getSystemGenerated: function(){
		return this.systemGenerated;
	},
	
	setFeedbackFieldType: function(feedbackFieldType){
		this.feedbackFieldType=feedbackFieldType;
	},
	
	getFeedbackFieldType: function(){
		return this.feedbackFieldType;
	},
	
	setApplicantFieldId: function(applicantFieldId){
		this.applicantFieldId=applicantFieldId;
	},
	
	getApplicantFieldId: function(){
		return this.applicantFieldId;
	}
	
}