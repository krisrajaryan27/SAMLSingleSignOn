// The property Class
function propertyObject(){
	this.propertyId;
	this.propertyName;
	this.checked;
	this.setPropertyId = setPropertyId;
	this.setPropertyName = setPropertyName;
	this.setChecked = setChecked;
}

function propertyObject(propertyId, propertyName, checked, imageUrl){
	this.propertyId = propertyId;
	this.propertyName = propertyName;
	this.checked = checked;
	this.imageUrl = imageUrl;
}

function setPropertyId(propertyId){
	this.propertyId = propertyId;
}

function  setPropertyName(propertyName){
	this.propertyName = propertyName;
}

function setChecked(checked){
	this.checked = checked;
}

// The multiCheckBoxControl class
function multiCheckBoxControl(propertyIdArray, propertyNameArray, checkedImage, uncheckedImage, controlName){
	this.controlName = controlName;
	this.propertyArray;
	this.checkedDisplayImage = checkedImage;
	this.uncheckedDisplayImage = uncheckedImage;
	this.instantiate = instantiate;
	this.setPropertyArray = setPropertyArray;
	this.setAllCheckedIds = setAllCheckedIds;
	this.getAllCheckedIds = getAllCheckedIds;
	this.togglevalues = togglevalues;
	this.initChecklist = initChecklist;
	this.getAllCheckedIdsForStringArray = getAllCheckedIdsForStringArray;
	this.propertyArray = new Array();

	for( var i=0; i< propertyIdArray.length; i++){
		this.propertyArray[i] = new propertyObject();
		this.propertyArray[i].propertyId = propertyIdArray[i];
		this.propertyArray[i].propertyName = propertyNameArray[i];
		this.propertyArray[i].checked = 0;
		this.propertyArray[i].imageUrl = this.uncheckedDisplayImage;
	}
}

function setPropertyArray(propertyArray){
	this.propertyArray  = propertyArray
}

function instantiate(){
	var html = '';
	html = html + '<div class="listboxdiv">';
	html = html + '<ul class="checklist cl1">';

	for(var i=0; i< this.propertyArray.length; i++){
		if( i%2 == 0 ){
		html = html + '<li class = "alt">';
		}else{
		html = html + '<li>';
		}
		if(this.propertyArray[i].checked == 1){
			html = html + '<image class="listcontrolimage" id="img_'+this.propertyArray[i].propertyId+'"  src="'+this.checkedDisplayImage+'" onclick="'+this.controlName+'.togglevalues(\''+this.propertyArray[i].propertyId+'\');">';
		}else{
			html = html + '<image class="listcontrolimage" id="img_'+this.propertyArray[i].propertyId+'"  src="'+this.uncheckedDisplayImage+'" onclick="'+this.controlName+'.togglevalues(\''+this.propertyArray[i].propertyId+'\');">';
		}
		html = html + '&nbsp;';
		//html = html + this.propertyArray[i].propertyId;
		html = html + this.propertyArray[i].propertyName;
		html= html + '</li>';
	} 

	html = html + '</ul>';
	html = html + '</div>';
	document.write(html);
	this.initChecklist();
}

function setAllCheckedIds(checkedIdsArray){
	for(var i=0; i< this.propertyArray.length; i++){
		for(var j=0; j< checkedIdsArray.length; j++){
			if( this.propertyArray[i].propertyId == checkedIdsArray[j]){
				this.propertyArray[i].checked = 1;
			}
		}
	}
}

function getAllCheckedIds(){
	var tempArray = new Array();
	for(var i=0; i< this.propertyArray.length; i++){
		if( this.propertyArray[i].checked == 1){
			tempArray[tempArray.length] = this.propertyArray[i].propertyId;
		}
	}
	return tempArray;
}

function togglevalues(propId){
	var img = document.getElementById('img_'+propId);
	for(var i=0; i < this.propertyArray.length; i++){
		if( this.propertyArray[i].propertyId == propId){
			if(this.propertyArray[i].checked == 1){
				this.propertyArray[i].checked =0;
				img.src = this.uncheckedDisplayImage;
			}else{
				this.propertyArray[i].checked =1;
				img.src = this.checkedDisplayImage;
			}
		}
	}
}

function initChecklist() {
	if (document.all && document.getElementById) {
		
		var lists = document.getElementsByTagName("ul");

		for (i = 0; i < lists.length; i++) {
			var theList = lists[i];

			// Only work with those having the class "checklist"
			if (theList.className.indexOf("checklist") > -1) {
				var labels = theList.getElementsByTagName("li");
				// Assign event handlers to labels within
				for (var j = 0; j < labels.length; j++) {
					var theLabel = labels[j];
					theLabel.onmouseover = function() { this.className += " hover"; };
					theLabel.onmouseout = function() { this.className = this.className.replace(" hover", ""); };
				}
			}
		}
	}
}

function getAllCheckedIdsForStringArray(){
	var tempString = '';
	for(var i=0; i< this.propertyArray.length; i++){
		if( this.propertyArray[i].checked == 1){
			tempString = tempString + this.propertyArray[i].propertyId + ',';
		}
	}
	if( tempString.length >0){
		tempString = tempString.substring(0,(tempString.length-1));
	}
	return tempString;

}