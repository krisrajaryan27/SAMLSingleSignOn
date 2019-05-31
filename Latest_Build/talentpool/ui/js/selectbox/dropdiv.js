/*Class to implement select box*/
var DropDiv =Class.create();
DropDiv.prototype = {
	initialize: function(oLayerId, oAnchorElement, options){
		//names of the textbox, image and layer is auto generated
		this.oLayer= $(oLayerId);
		this.oAnchorElement = $(oAnchorElement);
		this.lostFocus=false;
		this.active=false;
		this.options = options || {};
		this.addObservers(this.oLayer);
		//customize this function
		this.options.onShow  = this.options.onShow || 
		function(oAnchorElement, oLayer){ 
			  if(!oLayer.style.position || oLayer.style.position=='absolute') {
				oLayer.style.position = 'absolute';
				Position.clone(oAnchorElement, oLayer, {setHeight: false, setWidth: false, offsetTop: oAnchorElement.offsetHeight});
			  }
			  Effect.Appear(oLayer,{duration:0.05});
		};
		//you can customize this function
		this.options.onHide = this.options.onHide || 
		function(oAnchorElement, oLayer){ new Effect.Fade(oLayer,{duration:0.05}) };
		
	},
	
	addObservers: function(element) {
		Event.observe(this.oLayer, "focus", this.onDropFocus.bindAsEventListener(this));
		Event.observe(this.oLayer, "blur", this.onDropBlur.bindAsEventListener(this));
	},  
	
	onDropFocus: function(event){
	  this.lostFocus=false;
  	},
  
 	onDropBlur: function(event){
  		this.onBlur(event);  
  	},
	
	onBlur: function(event) {
		this.lostFocus=true;
		setTimeout(this.reallyLostFocus.bind(this),250);
	}, 

	reallyLostFocus: function(){
	  if(this.lostFocus){
		this.hide();
	  }
  	},
 	//display the div
  	show: function() {
		if(Element.getStyle(this.oLayer, 'display')=='none') this.options.onShow(this.oAnchorElement, this.oLayer);
		this.active=true;
		setTimeout(this.setFocus.bind(this),100);
		 
  	},
	setFocus: function(element){
		this.oLayer.focus();
	},
	hide: function() {
		if(Element.getStyle(this.oLayer, 'display')!='none') this.options.onHide(this.oAnchorElement, this.oLayer);
		this.active=false;
  	},
	
	toggle: function(){
		if(this.active) 
			this.hide();
		else
		 	this.show();
  	}

}