var RatingData = Class.create();
RatingData.prototype ={
	initialize: function(ratingId, ratingTitle, ratingFields){
		this.ratingId=ratingId;
		this.ratingTitle=ratingTitle;
		this.ratingFields=ratingFields;
	},
	
	setRatingId: function(ratingId){
		this.ratingId=ratingId;
	},
	
	getRatingId: function(){
		return this.ratingId;
	},
	
	setRatingTitle: function(ratingTitle){
		this.ratingTitle=ratingTitle;
	},
	getRatingTitle: function(){
		return this.ratingTitle;
	},
	
	setRatingFields: function(ratingFields){
		this.ratingFields=ratingFields;
	},
	getRatingFields: function(){
		return this.ratingFields;
	}
}