// JavaScript Document
/* following class is used for each option in the select box,
This becomes quiete handy when you use this instead of two separate rows of ids and names
*/
 

/*Class to implement select box*/
var CHECKBOXRADIOGROUP_NO=0;
var CheckBoxRadioGroup =Class.create();
CheckBoxRadioGroup.prototype = {
	initialize: function(oSelectOptions, oSelIds, options){
		
		//names of the textbox, image and layer is auto generated
		CHECKBOXRADIOGROUP_NO++;
		this.imageId= 'ctl_chrg_image_'+CHECKBOXRADIOGROUP_NO;
		this.layerId= 'ctl_chrg_layer_'+CHECKBOXRADIOGROUP_NO;
		this.containerId=null;	//optional
		//variables which hold the references to textbox, image and layer is auto generated
		this.oLayer=null; //update
		this.oOptions=oSelectOptions;
		this.oSelectOptions=oSelectOptions;
		this.totalOptions = this.oSelectOptions!=null? this.oSelectOptions.length : 0;
		this.oSelIds=oSelIds;
		this.onChangeHandler=null;
	   	
		this.options = options || {};
		//this.width = this.options.width!=null?this.options.width:'120px';
		//this.size=this.options.size!=null?this.options.size:10;
		this.imageclass=(this.options.imageclass!=null)?this.options.imageclass:'chkboxgroupclass';
		this.contentImgClass = (this.options.contentImgClass!=null)?this.options.contentImgClass:'contentImgClass';
		this.singleselect=(this.options.singleselect!=null)?this.options.singleselect=='true':false;
		if(this.options.checkedimg!=null){
			this.checkedimg=this.options.checkedimg;
		}else{
			if(this.singleselect){
				this.checkedimg='images/checkedradiobutton.gif';
			}else{
				this.checkedimg='images/checkboxchecked.gif';
			}
		}
		if(this.options.uncheckedimg!=null){
			this.uncheckedimg=this.options.uncheckedimg;
		}else{
			if(this.singleselect){
				this.uncheckedimg='images/radiobutton.gif';
			}else{
				this.uncheckedimg='images/checkboxunchecked.gif';
			}
		}
		this.layerclass=(this.options.layerclass!=null)?this.options.layerclass:'checkboxradiogroupdiv';
		this.oStyles=(this.options.oStyles!=null)?this.options.oStyles:null;
		this.controlsperline=(this.options.controlsperline!=null)?this.options.controlsperline:0;
		this.oControlname=this.options.controlname;
		
		this.resetOptionsSelected();
	},
	getControlName: function(){
		return this.oControlname;
	},
	resetOptionsSelected: function(){
		var ids = new Array();
		var tids = new Array();
		if(this.oSelIds !=''){
			ids = this.oSelIds.split(",");
		}
		this.oSelectOptions.each(function(item) {
			item.setSelected(ids.indexOf(item.getId())>=0);
		});
	},
	resetSelected: function(oSelIds, options){
		this.oSelIds = (oSelIds==null)?'':oSelIds;
		this.options = options || {};
		this.resetOptionsSelected();
		this.constructList();
	},
		
	getHtml: function(){
		/*var html = '<table id=\"'+this.layerId+'\" class=\"'+this.layerclass+'\">';
		html += '</table>';*/
		var html = '<div id=\"'+this.layerId+'\" class=\"'+this.layerclass+'\">';
		html += '</div>';
		
		return html;
	},
	
	init: function() {
			this.oLayer=$(this.layerId); //update
			//set the width of combo
			//Element.setStyle(this.oLayer,{width:this.width});
			//construct the combo box render the classes and select the entry
			//this should be called after getHtml only
			this.constructList();
			//add event handlers	
			this.addObservers(this.oLayer);
		
			
	},
  
  reInitialize: function(oSelectOptions, oSelIds){
	this.oOptions=oSelectOptions;
	this.oSelectOptions=oSelectOptions;
	this.totalOptions = this.oSelectOptions!=null? this.oSelectOptions.length : 0;
	this.oSelIds=oSelIds;
	this.resetOptionsSelected();
 	this.constructList();
  },
  setOnChangeHandler: function(onChangeHandler){
		this.onChangeHandler =  onChangeHandler;
  },
	
  //creates the drop down list	and resize the height
  constructList: function(){
	var oDiv = null;
	var oChk=null;
	this.oLayer.innerHTML = "";  //clear contents of the layer
	var htm = "<table>";
	for(i=0;i<this.totalOptions;i++){
		var opt = this.oSelectOptions[i];
		var divClassName="";
		if(this.oStyles){
			if(this.oStyles.length>opt.getLevel()){
				divClassName=this.oStyles[opt.getLevel()];
			}
		}
		if(this.controlsperline==0 && i==0){
			htm += '<tr>';
		}else if(this.controlsperline !=0 && i%this.controlsperline==0){
			htm += '<tr>';
		}
		htm += '<td id=\"opt_div_' + i + '\" title=\"' + opt.getText() + '\" >';
		var imgSrc = this.uncheckedimg;
		if(opt.isSelected()){
			imgSrc = this.checkedimg;
		}
		htm += '<img src=\"'+ imgSrc + '\" class=\"' + this.imageclass + '\" id=\"' + this.imageId + "_" +i +'\">';
		if(opt.options.optImage !=null){
			htm += '<img src=\"'+ opt.getOptions().optImage + '\" class=\"' + this.contentImgClass + '\" >';
		}
		htm += opt.getText();
		htm +='</td>';
		
		if(this.controlsperline !=0 && i%this.controlsperline==this.controlsperline-1){
			htm += '</tr>';
		}else if(i==this.totalOptions-1){
			htm += '</tr>';
		}
	}
	htm +='</table>';
	
	this.oLayer.innerHTML =htm;
	/*this.resizeBox();*/
  },
  /*
	resizeBox: function(){
		var height = (this.totalOptions>0)?Element.getStyle(this.getEntry(0), 'height'):'15px';
		height=height.substr(0,height.length-2);
		height=(this.totalOptions>this.size)?height*this.size:height*this.totalOptions;
		
		height=(height==0)?18:height+2; //+2 is added for border
		Element.setStyle(this.oLayer,{height:height+'px'});
		if(this.totalOptions>this.size){
			Element.setStyle(this.oLayer,{overflowY:'scroll'});
			
		}else{
			//Element.setStyle(this.oLayer,{overflow-y:''});
		}
		//overflow-y: scroll;
	},
	*/
	getEntry: function(index) {
		return this.oLayer.childNodes[index];
	}, 
	getSelectedIds: function(){
		return this.getSelectedItems(true,',');
	},
	getSelectedText: function(separator){
		return this.getSelectedItems(true,',');
	},
	getSelectedItems: function(ids, separator){
		var selItem="";
		this.oSelectOptions.each(function(item) {
			var selected = item.isSelected();
			
			if(selected){
				if(selItem!=""){
					selItem +=separator;
				}
				selItem += ids? item.getId():item.getText();
			}
		});
		return selItem;
	},
	
	addObservers: function(element) {
   		//add event handlers to textbox
		Event.observe(this.oLayer, "click", this.onClick.bindAsEventListener(this));
  },  
  onClick: function(event) {
		var element = Event.findElement(event, 'td');
		if(element.id){
			var index = element.id.split('_')[2];
			if(index>=0){
				if(this.singleselect){
					this.selectAll(false);
				}
				this.togleSelected(index);
			}
			if(this.onChangeHandler!=null){
				eval(this.onChangeHandler)(this);
			}
		}
  },
  
  togleSelected: function(idx){
		var chk = $(this.imageId + "_" +idx);
		var opt = this.oSelectOptions[idx];
		if(opt.isSelected()){
			chk.src=this.uncheckedimg;
			opt.setSelected(false);
		}else{
			chk.src=this.checkedimg;
			opt.setSelected(true);
		}
			
			
  },
  
  selectAll: function(state){
	for(j=0;j<this.totalOptions;j++){
		var opt = this.oSelectOptions[j];
		if(opt.isSelected()){
			if(!state){
				this.togleSelected(j);
			}
		}else{
			if(state){
				this.togleSelected(j);
			}
		}
	}
  		
  },
  
  hideMe: function(){
	this.oLayer.hide();
  },
  showMe: function(){
	this.oLayer.show();
  },
  toggleMe: function(){
  	if(this.oLayer.style.display=="none"){
  		this.oLayer.show();
  	}else{
  		this.oLayer.hide();
  	}
  },
  getItems: function(){
  	return this.oSelectOptions;
  }
  
  


}