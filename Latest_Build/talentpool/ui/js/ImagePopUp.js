var ImagePopUp =Class.create();

ImagePopUp.prototype = {
	initialize: function(oDivId, oImages){
		this.divId=oDivId;
		this.divContainer=$(oDivId);
		this.oImages=oImages;
		this.createUI();
		Event.observe(this.divContainer, "keypress", this.onKeyPress.bindAsEventListener(this));
		
	},
	onKeyPress: function(event){
    	switch(event.keyCode) {
			case Event.KEY_ESC:
				 this.hide();
				 return;
		}
	},
	createUI: function(){
		strImage = '';
		strImage += '<table class=timePopUpOuter cellspacing=1 cellpadding=1>';
		strImage += '<tr><td>';
		strImage += '<table cellspacing=2 cellpadding=2>';
		for(var i=0, len = this.oImages.length; i < len; i++){
			fileName=this.oImages[i];
			lineChange=i%3;
			if(lineChange=='0'){
				strImage += '<tr>';
			}
			
			var url = 'docs.do?mode=getIcon&fileName=' + fileName + '&contentDisposition=';
			strImage += '<td><img src=\''+url+'\' onclick=\"selectImage(\'' + fileName + '\');\" style="cursor:hand;"></td>';
			
			if(lineChange=='2'){
				strImage += '</tr>';
			}
		}
		strImage += '</table>';
		strImage += '</td></tr>';
		strImage += '</table>';
		this.divContainer.innerHTML= strImage;
	},
	
	hide: function() {
		this.divContainer.hide();
	},
	
	show: function(anchorName){
		Position.clone($(anchorName),this.divContainer,{setHeight: false, offsetTop: $(anchorName).offsetHeight});
		this.divContainer.show();
	}
}
