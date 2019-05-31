
Object.extend(Event, {
  KEY_PAGEUP:	33,	  
  KEY_PAGEDOWN:	34,
  KEY_HOME:		36,
  KEY_END:		35,
  KEY_ALT:		18
			  })
  

/*Class to implement select box*/
var SELECTBOX_NO=0;
var SelectBox =Class.create();
SelectBox.prototype = {
	initialize: function(oSelectOptions, oSelectedId, imageSource, options){
		//names of the textbox, image and layer is auto generated
		SELECTBOX_NO++;
		this.textBoxId= 'ctl_selectbox_text_'+SELECTBOX_NO;
		this.imageId= 'ctl_selectbox_image_'+SELECTBOX_NO;
		this.layerId= 'ctl_selectbox_layer_'+SELECTBOX_NO;
		this.containerId=null;	//optional
		this.containerHeader=null;
		this.containerFooter=null;

		//variables which hold the references to textbox, image and layer is auto generated
		this.oTextBox=null;
		this.oLayer=null; //update
		this.oImgcbo=null;
		this.oContainer =null;
		this.oOptions=oSelectOptions;
		this.oSelectOptions=this.constructUA(oSelectOptions);
		this.oSelectedId=oSelectedId;
		this.totalOptions = this.oSelectOptions!=null? this.oSelectOptions.length : 0;
		this.index=this.totalOptions>0? this.getIndexWithId(this.oSelectedId):null; //initial selected index
		this.tmpIndex=this.index; //this is required for mouse over effect
		this.lastTmpIndex=this.tmpIndex;
		this.imageSource=imageSource;

		this.altKeyPressed=false;
		this.active= false; //is true whenever the dropdown is displayed and false whenever dropdown is hidden
		this.lostFocus=false;
		this.outerBox=null;
		
		this.onChangeHandler=null;
	   	
		this.options = options || {};
		this.namesOnly =  this.options.namesonly==true?true:false;
		this.width = this.options.width!=null?this.options.width:'120px';
		this.size=this.options.size!=null?this.options.size:10;
		this.textboxclass = (this.options.textboxclass!=null)?this.options.textboxclass:'dropdowntextbox';
		this.textboxfocussedclass=(this.options.textboxfocussedclass!=null)?this.options.textboxfocussedclass:'dropdowntextboxfocused';
		this.imageclass=(this.options.imageclass!=null)?this.options.imageclass:'dropdownimage';
		this.containerclass=(this.options.containerclass!=null)?this.options.containerclass:'containerdiv';
		this.layerclass=(this.options.layerclass!=null)?this.options.layerclass:'dropdowndiv';
		this.oStyles=(this.options.oStyles!=null)?this.options.oStyles:null;
		this.oControlname=this.options.controlname;
		//new variable
		this.typed=null;
	},
	getControlName: function(){
		return this.oControlname;
	},
	constructUA: function(opts){
		var finalarr = new Array();
		if(opts){
			var I=0;
			for(I=0; I<opts.length; I++){
				finalarr[finalarr.length]=opts[I];
				if(opts[I].getChilds()){
					var childs= this.constructUA(opts[I].getChilds());
					for(K=0; K<childs.length; K++){
						finalarr[finalarr.length] = childs[K];
					}
				}			
			}
		}
		return finalarr;
	},

	disable: function(){
		this.oTextBox.disabled='disabled';
		this.oImgcbo.disabled='disabled';
  	},
	enable: function(){
		this.oTextBox.disabled='';
		this.oImgcbo.disabled='';		
	},
		
	getHtml: function(){
		var html = '<input type=\"text\" id=\"'+this.textBoxId+'\" value=\"\" name=\"'+this.textBoxId+'\"  class=\"'+this.textboxclass+'\" >';
		html += '<img src=\"'+this.imageSource+'\"   id=\"'+this.imageId+'\" class=\"'+this.imageclass+'\"><br/>';
		if(this.containerId!=null){
			html += '<div id=\"'+this.containerId+'\" class=\"'+this.containerclass+'\">';
			html += (this.containerHeader==null)?'':this.containerHeader;
		}
		html += '<div id=\"'+this.layerId+'\" class=\"'+this.layerclass+'\">';
		html += '</div>';
		if(this.containerId!=null){
			html += (this.containerFooter==null)?'':this.containerFooter;
			html += '</div>';
		}
		return html;
	},
	
	init: function() {
			this.oTextBox=$(this.textBoxId); //element
			this.oLayer=$(this.layerId); //update
			this.oImgcbo=$(this.imageId);
			this.oContainer = this.containerId==null? null:$(this.containerId);
			//outer container is either oContainer or oLayer
			this.outerBox=this.oContainer==null?this.oLayer:this.oContainer;
			Element.hide(this.outerBox);
			
			//the options list only contains names then replace ids
			if(this.namesOnly ){
				if(this.totalOptions>0){
					for(i=0;i<this.oSelectOptions.length;i++){
						this.oSelectOptions[i].oId= this.oSelectOptions[i].getText();
					}
				}
			}
			
			//set the width of combo
			
			if(this.oContainer!=null){
				Element.setStyle(this.oContainer,{width:this.width});
			}else{
				Element.setStyle(this.oLayer,{width:this.width});
			}
			var mwidth = Element.getStyle(this.oImgcbo, 'width');
			if(mwidth==null){mwidth='15px';}
			mwidth = mwidth.substr(0,mwidth.length-2);
			this.width = this.width.substr(0,this.width.length-2)- mwidth +'px';
			Element.setStyle(this.oTextBox,{width:this.width});
			
			//construct the combo box render the classes and select the entry
			//this should be called after getHtml only
			this.constructDropDown();
			this.render();
			this.selectEntry();
			//add event handlers	
			this.addObservers(this.oLayer);
		
			//customize this function
			this.options.onShow  = this.options.onShow || 
			function(oTextBox, oLayer){ 
			  if(!oLayer.style.position || oLayer.style.position=='absolute') {
				oLayer.style.position = 'absolute';
				//var ofsetTop=oTextBox.offsetHeight + Element.cumulativeScrollOffset(oTextBox)[1]-document.viewport.getScrollOffsets()[1];
				//Position.clone(oTextBox, oLayer, {setHeight: false, setWidth: false, offsetTop: ofsetTop});
				//Position.clone(oTextBox, oLayer, {setHeight: false, setWidth: false, setTop:false});
			  }
			  Effect.Appear(oLayer,{duration:0});
			};
			
			//you can customize this function
			this.options.onHide = this.options.onHide || 
    			function(oTextBox, oLayer){ new Effect.Fade(oLayer,{duration:0.05}) };
			
			
	},
	
	setContainerTemplates: function(containerHeader, containerFooter){
		this.containerId='ctl_selectbox_container_'+SELECTBOX_NO;
		this.containerHeader=containerHeader;
		this.containerFooter=containerFooter;		
	},
	
	addObservers: function(element) {
   		//add event handlers to textbox
   		//keypress event is commented as it does not fire when up down arrow keys pressed hence internally called from keydown event
		//Event.observe(this.oTextBox, "keypress", this.onKeyPress.bindAsEventListener(this));
		Event.observe(this.oTextBox, "keydown", this.onKeyDown.bindAsEventListener(this));
		Event.observe(this.oTextBox, "keyup", this.onKeyUp.bindAsEventListener(this));
		Event.observe(this.oTextBox, "focus", this.onFocus.bindAsEventListener(this));
		Event.observe(this.oTextBox, "blur", this.onBlur.bindAsEventListener(this));
		Event.observe(this.oTextBox, "mousedown", this.onMouseDown.bindAsEventListener(this));
		//add event handlers to layer
		Event.observe(this.oLayer, "mouseover", this.onHover.bindAsEventListener(this));
		Event.observe(this.oLayer, "click", this.onClick.bindAsEventListener(this));
		Event.observe(this.oLayer, "focus", this.onDropDownFocus.bindAsEventListener(this));
		Event.observe(this.oLayer, "keypress", this.onDropDownKeyPress.bindAsEventListener(this));
		Event.observe(this.oLayer, "blur", this.onDropDownBlur.bindAsEventListener(this));
		//add event handlers to image
		Event.observe(this.oImgcbo, "click", this.onButtonClick.bindAsEventListener(this));
	},  


  resizeBox: function(){
	  	var height = (this.totalOptions>0)?Element.getStyle(this.getEntry(0), 'height'):'15px';
		height=height.substr(0,height.length-2);
		height=(this.totalOptions>this.size)?height*this.size:height*this.totalOptions;
		height=(height==0)?15:height+2; //+2 is added for border
		Element.setStyle(this.oLayer,{height:height+'px'});
  },
  
  setOnChangeHandler: function(onChangeHandler){
		this.onChangeHandler =  onChangeHandler;
  },
  //return the selected index of the item
  getSelectedIndex: function(){
	return (this.totalOptions>0)?this.index:null;
  },

  getSelectedId: function(){	  
	  if(this.totalOptions>this.index){
	  	return this.oSelectOptions[this.index].getId();
	  }else{
		return null;  
	  }
  },
  
  getAllSelectOptionIds: function(){
	  if(this.totalOptions>this.index){		
		var allIds='';  
		for(var i=0;i<this.totalOptions;i++){
			if(i==this.totalOptions-1){
				allIds+=this.oSelectOptions[i].getId();
			}else{
				allIds+=this.oSelectOptions[i].getId()+',';
				
			}
			
		}  
	  	return allIds;
	  }else{
		return null;  
	  }
  },
  
  getText: function(selectedIndex){
	if(selectedIndex>0 && selectedIndex<this.totalOptions){
		return this.oSelectOptions[selectedIndex].getText();
	}else{
		return null;
	}
  },
  //set the option with gien index selected
  setSelected: function(selectedIndex){
	if(selectedIndex>=0 && selectedIndex<this.totalOptions){
		var prevIndex=this.index;
		this.lastTmpIndex=this.tmpIndex;
		this.tmpIndex=this.index;
		this.index =selectedIndex;
		this.selectEntry();
		this.render();
		this.raiseOnChangeEvent(prevIndex);
	}
  },
  
  clearDropDown: function(){
	this.oSelectOptions=null;
	this.totalOptions=0;
	this.constructDropDown();
	this.oTextBox.value="";
	this.index=0;
	this.lastTmpIndex=this.tmpIndex=this.index=0;
  },


  reInitialize: function(oSelectOptions, oSelectedId){
	  this.oTextBox.value="";
	  this.oOptions=oSelectOptions;
	  this.oSelectOptions=this.constructUA(oSelectOptions);
	  this.oSelectedId=oSelectedId;
	  this.totalOptions = this.oSelectOptions!=null? this.oSelectOptions.length : 0;
	  this.index=this.totalOptions>0? this.getIndexWithId(this.oSelectedId):null; //initial selected index
	  this.tmpIndex=this.index;
	  this.lastTmpIndex=this.tmpIndex;
	  this.constructDropDown();

	  //construct the combo box render the classes and select the entry
	  this.render();
	  this.selectEntry();
  },
  
  getIndexWithId: function(id){
	for(i=0;i< this.totalOptions;i++){
		if(this.oSelectOptions[i].getId()==id)
		return i;
	}
	return 0;
  },
  
  getIndexWithText: function(text){
	for(i=0;i< this.totalOptions;i++){
		if(this.oSelectOptions[i].getText()==text)
		return i;
	}
	return 0;
  },
  //display the div
  show: function() {
	if(Element.getStyle(this.outerBox, 'display')=='none') { 
		if (window.atob) { // check for IE10
				this.outerBox.show();				
			} else { 
				this.options.onShow(this.oTextBox, this.outerBox);
	    		//if(Element.getStyle(this.oLayer, 'display')=='none') this.options.onShow(this.oTextBox, this.oLayer);
			}		
	}
	this.active = true;     //added by me
  },
  
  //hide the div	
  hide: function() {
	if(Element.getStyle(this.outerBox, 'display')!='none')  {	
		if (window.atob) { // check for IE10
			this.outerBox.hide();			
		} else { 
			this.options.onHide(this.oTextBox, this.outerBox);
		    //if(Element.getStyle(this.oLayer, 'display')!='none') this.options.onHide(this.oTextBox, this.oLayer);
		}		
	}	
	this.active = false; 
	this.lastTmpIndex=this.tmpIndex;
	this.tmpIndex=this.index;
	this.render();
  },
  hideMe: function(){
	this.oTextBox.hide();
	this.oImgcbo.hide();
	this.oLayer.hide();
	if(this.oContainer!=null){
		this.oContainer.hide();
	}
  },
  showMe: function(){
		this.oTextBox.show();
		this.oImgcbo.show();
  },
  
  dropDown: function(){
	this.oTextBox.focus();  
	this.toggle();	
 	this.highlightTextBox();	 
  },
  //toggle is cobination of hide and show	
  toggle: function(){
		if(this.active) 
			this.hide();
		else
		 	this.show();
  },
  //keyup and keydown events as used to determine the alt key kept pressed 	
  onKeyUp:function(event) {
	switch(event.keyCode) {
		case Event.KEY_ALT:
			this.altKeyPressed=false;
			return;
	}
  },
  
  onFocus: function(event){
	this.lostFocus=false;
	this.highlightTextBox();
	//if(!this.active){
	//	Element.addClassName(this.oTextBox,this.textboxfocussedclass);
	//}

	if (this.oTextBox.createTextRange) {
		var oRange = this.oTextBox.createTextRange(); 
        oRange.moveStart("character", 0); 
        oRange.moveEnd("character", 0-this.oTextBox.value.length);      
        oRange.select();
    //use setSelectionRange() for Mozilla
    } else if (this.oTextBox.setSelectionRange) {
        this.oTextBox.setSelectionRange(this.oTextBox.value.length, this.oTextBox.value.length);
    }     
  },

  raiseOnChangeEvent: function(prevIndex){
	if(this.onChangeHandler!=null){
	  if(prevIndex!=this.index){
			eval(this.onChangeHandler+"(" + this.index +",this )");
		}
	  }
  },
 
  onKeyDown:function(event) {
	  switch(event.keyCode) {
		case Event.KEY_ALT:
			//this.debug('onKeyDown even fired for' +  event.keyCode); 
			this.altKeyPressed=true;
			return;
	  }
	  return this.onKeyPress(event);
  },
 onKeyPress: function(event) {
	  this.lostFocus=false;
	  var prevIndex = this.index;
	  var iKeyCode = event.keyCode;
      
	  switch(iKeyCode) {
       case Event.KEY_TAB:
		if(!this.active)
			return;
       case Event.KEY_RETURN:
         this.selectEntry();
	     this.raiseOnChangeEvent(prevIndex);
         Event.stop(event);
       case Event.KEY_ESC:
         this.hide();
	 	 this.highlightTextBox();
         Event.stop(event);
         return;
       case Event.KEY_LEFT:
       case Event.KEY_RIGHT:
	   Event.stop(event);
         return;
       case Event.KEY_UP:
       case Event.KEY_DOWN:
	   case Event.KEY_HOME:
	   case Event.KEY_END:
	   case Event.KEY_PAGEUP:
	   case Event.KEY_PAGEDOWN:
	   	 if(!this.altKeyPressed){
			 if(iKeyCode==Event.KEY_UP){
				 this.modifyIndexByOffset(-1);
			 }else if(iKeyCode==Event.KEY_DOWN){
				 this.modifyIndexByOffset(1);
			 }else if(iKeyCode==Event.KEY_HOME){
				 this.modifyIndexByOffset(0-(this.totalOptions+1));
			 }else if(iKeyCode==Event.KEY_END){
				 this.modifyIndexByOffset(this.totalOptions);
			 }else if(iKeyCode==Event.KEY_PAGEUP){
				 this.modifyIndexByOffset(0-this.size);
			 }else if(iKeyCode==Event.KEY_PAGEDOWN){
				 this.modifyIndexByOffset(this.size);
			 }
			 this.render();
			 this.selectEntry();
		 }else{
			this.toggle();
			this.altKeyPressed=false;
		 }
		 this.highlightTextBox();	 
		 this.moveToVisible();
		 this.raiseOnChangeEvent(prevIndex);		 
         Event.stop(event);
		 //if(navigator.appVersion.indexOf('AppleWebKit')>0) Event.stop(event);
         return;
		
		default:
		if(String.fromCharCode(iKeyCode)){
			var keyStr = String.fromCharCode(iKeyCode);
			if((iKeyCode >= 96 && iKeyCode < 106)){
				keyStr=String.fromCharCode(iKeyCode-48)
			}
			if(this.typed==null){
				this.typed = keyStr;	
			}else{
				this.typed += keyStr;
			}
			//this.findNext(String.fromCharCode(iKeyCode));
			setTimeout(this.findNext.bind(this), 400);
		}
		/*		
		if(!(iKeyCode < 32 || (iKeyCode >= 33 && iKeyCode < 46) || (iKeyCode >= 112 && iKeyCode <= 123)))
        	this.findNext(String.fromCharCode(iKeyCode));
        */	
		Event.stop(event);
	  }
	  
	
  },

 highlightTextBox: function(){
		 if(this.active){
		 	Element.removeClassName(this.oTextBox,this.textboxfocussedclass);
			//Element.addClassName(this.oTextBox,this.textboxclass);
		 }else{
			//Element.removeClassName(this.oTextBox,this.textboxclass);
			Element.addClassName(this.oTextBox,this.textboxfocussedclass);
		 }
 },
 
 onMouseDown: function(event){
	 this.lostFocus=false;
	 this.toggle();
	 this.highlightTextBox();
},
 
 findNext :function(scar){
	if(this.typed!=null){
		scar=this.typed;
		if(this.totalOptions >0){
			var checkIndex = this.index<this.totalOptions-1?this.index+1:0;
			while(checkIndex != this.index){
				if(this.oSelectOptions[checkIndex].getText().substr(0,scar.length).toLowerCase() == scar.toLowerCase())
				break;
				checkIndex = (checkIndex==this.totalOptions-1)?0:checkIndex+1;
			}
			if(checkIndex!=this.index){
				 var prevIndex = this.index;
				 this.index=checkIndex;
				 this.lastTmpIndex=this.tmpIndex;
				 this.tmpIndex=this.index;
				 this.raiseOnChangeEvent(prevIndex);
				 this.render();
				 this.selectEntry();
				 this.moveToVisible();
			}
		}
	this.typed=null;
	}
 },

 moveToVisible: function(){
	 var cell_obj = this.getCurrentEntry();
	 try{
		var distance = cell_obj.offsetTop+cell_obj.offsetHeight;
		if(distance>(this.oLayer.offsetHeight+this.oLayer.scrollTop)){
		 	var scrollTop = distance - this.oLayer.offsetHeight;
		}else if(cell_obj.offsetTop<this.oLayer.scrollTop){
		 	var scrollTop = cell_obj.offsetTop-5
		}
		if(scrollTop)
		this.oLayer.scrollTop = scrollTop;
	}catch(er){
	 
	}
},

 onBlur: function(event) {
    // needed to make click events working
	this.lostFocus=true;
	setTimeout(this.reallyLostFocus.bind(this),250);
  }, 
  
  reallyLostFocus: function(){
	  if(this.lostFocus){
		if(this.active)this.hide();
		this.lastTmpIndex=this.tmpIndex;
		this.tmpIndex=this.index;
	 	Element.removeClassName(this.oTextBox,this.textboxfocussedclass);
		//Element.addClassName(this.oTextBox,this.textboxclass);
	  }
  },
  
  //creates the drop down list	and resize the height
  constructDropDown: function(){
	var oDiv = null;
	this.oLayer.innerHTML = "";  //clear contents of the layer
	
	for(i=0;i<this.totalOptions;i++){
		var opt = this.oSelectOptions[i];
		oDiv = document.createElement("div");
		oDiv.id='opt_div_'+i;
		oDiv.title=opt.getText();
		oDiv.appendChild(document.createTextNode(opt.getText()));
		oDiv.index=i;
		if(this.oStyles){
			if(this.oStyles.length>opt.getLevel()){
			oDiv.className=this.oStyles[opt.getLevel()];
			}
		}
		if(opt.getOptions().cs){
			oDiv.className=opt.getOptions().cs;
		}
		this.oLayer.appendChild(oDiv);
	}
	this.resizeBox();
  },

setFocus:function(){
	this.oTextBox.focus();
},

onButtonClick:function(event) {
	this.dropDown();
},


onHover: function(event) {
	this.lostFocus=false;
	var element;
	try{
		element=Event.findElement(event, 'div'); // this doesn't work in IE8, it will use the catch block
	}
	catch(e){
		element =event.target;	
	}
	
	if(element.index>=0){
		this.lastTmpIndex=this.tmpIndex;
		this.tmpIndex = element.index;
		this.render();
	}
		Event.stop(event);
  },
  
  onClick: function(event) {
	  this.lostFocus=false;	
	  var element;
		try{
			element=Event.findElement(event, 'div'); // this doesn't work in IE8, it will use the catch block
		}
		catch(e){
			element =event.target;	
		}
		if(element.index>=0){
			var prevIndex=this.index;
			this.index = element.index;
			this.lastTmpIndex=this.tmpIndex;
			this.tmpIndex=this.index;
			this.selectEntry();
			this.hide();
			this.oTextBox.focus();
			this.raiseOnChangeEvent(prevIndex);
		}
  },
  
  onDropDownFocus: function(event){
	  this.lostFocus=false;
  },
  
  onDropDownKeyPress: function(event){
	this.onKeyPress(event);  
  },
  
  onDropDownBlur: function(event){
  	this.onBlur(event);  
  },
    
  modifyIndexByOffset: function(offset) {
	this.index = this.index==this.tmpIndex? this.index : this.tmpIndex;
	var nextSelected = this.index + offset;
	if(nextSelected >=0 && nextSelected<this.totalOptions){
		this.index = nextSelected;
	}else if(nextSelected<0){
		this.index=0;
	}else if(nextSelected>=this.totalOptions){
		this.index=this.totalOptions-1;
	}
	this.lastTmpIndex=this.tmpIndex;
	this.tmpIndex=this.index;
  },
  
  getCurrentEntry: function() {
	if(this.totalOptions>0){  
    	return this.getEntry(this.index);
	}
  },
  
  selectEntry: function() {
	if(this.totalOptions>0){  
	    this.updateElement(this.getCurrentEntry());
	}
	this.lastTmpIndex=this.tmpIndex;
	this.tmpIndex = this.index;
  },
  
  getEntry: function(index) {
	return this.oLayer.childNodes[index];
  }, 
  
  /*iterate through all the options and set the selected and not selected class*/
  render: function() {
	var ind = this.index==this.tmpIndex?this.index : this.tmpIndex;
	if(this.totalOptions > 0) {
	 /*
      //This code is functional but as it rnders the complete div it is commented
	  for (var i = 0; i < this.totalOptions; i++){
		ind==i ? 
          Element.addClassName(this.getEntry(i),"selected"): 
          Element.removeClassName(this.getEntry(i),"selected");
	  }*/
	   
	   Element.removeClassName(this.getEntry(this.lastTmpIndex),"selected");
	   Element.removeClassName(this.getEntry(this.index),"selected");
	   Element.addClassName(this.getEntry(ind),"selected");
    } 
  },

  updateElement: function(selectedElement) {
	if(this.totalOptions>selectedElement.index){  
    	this.oTextBox.value = this.oSelectOptions[selectedElement.index].getText();
	}else{
		this.oTextBox.value ='';
	}
  },

  //used for debugging purpose	
  setDebugMode: function(divName){
	this.divDebug = $(divName);
  },
  
  debug: function(txt){
	if(this.divDebug!=null){  
		this.divDebug.innerHTML +="<br>" + txt;
	}
  }

}