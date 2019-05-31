// JavaScript Document
/* following class is used for each option in the select box,
This becomes quiete handy when you use this instead of two separate rows of ids and names
*/
 

/*Class to implement select box*/
var CHECKBOXLIST_NO=0;
var CheckBoxList =Class.create();
CheckBoxList.prototype = {
	initialize: function(oSelectOptions, oSelIds, options){
		
		//names of the textbox, image and layer is auto generated
		CHECKBOXLIST_NO++;
		this.imageId= 'ctl_checkboxlist_image_'+CHECKBOXLIST_NO;
		this.layerId= 'ctl_checkboxlist_layer_'+CHECKBOXLIST_NO;
		this.containerId=null;	//optional
		//variables which hold the references to textbox, image and layer is auto generated
		this.oLayer=null; //update
		this.oOptions=oSelectOptions;
		this.oSelectOptions=oSelectOptions;
		this.totalOptions = this.oSelectOptions!=null? this.oSelectOptions.length : 0;
		this.oSelIds=oSelIds;
		this.onChangeHandler=null;
	   	
		this.options = options || {};
		this.width = this.options.width!=null?this.options.width:'120px';
		this.size=this.options.size!=null?this.options.size:10;
		this.imageclass=(this.options.imageclass!=null)?this.options.imageclass:'chkboxclass';
		this.contentImgClass = (this.options.contentImgClass!=null)?this.options.contentImgClass:'contentImgClass';
		this.checkedimg=(this.options.checkedimg!=null)?this.options.checkedimg:'images/checkboxchecked.gif';
		this.uncheckedimg=(this.options.uncheckedimg!=null)?this.options.uncheckedimg:'images/checkboxunchecked.gif';
		this.tristatecheckedimg=(this.options.tristatecheckedimg!=null)?this.options.tristatecheckedimg:'images/checkboxtristate.gif';
		
		this.disabledcheckedimg=(this.options.disabledcheckedimg!=null)?this.options.disabledcheckedimg:'images/checkboxdisabledchecked.gif';
		this.disableduncheckedimg=(this.options.disableduncheckedimg!=null)?this.options.disableduncheckedimg:'images/checkboxdisabledunchecked.gif';
		
		this.layerclass=(this.options.layerclass!=null)?this.options.layerclass:'checkboxlistdiv';
		this.oStyles=(this.options.oStyles!=null)?this.options.oStyles:null;
		this.oTristateIds=this.options.tristateids!=null?this.options.tristateids:'';
		this.oDisabledIds=this.options.disabledids!=null?this.options.disabledids:'';
		
		this.resetOptionsSelected();
		this.oControlname=this.options.controlname;
	},
	getControlName: function(){
		return this.oControlname;
	},
	resetOptionsSelected: function(){
		var ids = new Array();
		var tids = new Array();
		var dids = new Array();
		if(this.oSelIds !=''){
			ids = this.oSelIds.split(",");
		}
		if(this.oTristateIds !=''){
			tids = this.oTristateIds.split(",");
		}
		if(this.oDisabledIds !=''){
			dids = this.oDisabledIds.split(",");
		}
		
		this.oSelectOptions.each(function(item) {
			item.setSelected(ids.indexOf(item.getId())>=0);
			item.setIsTriState(tids.indexOf(item.getId())>=0);
			item.setIsDisabled(dids.indexOf(item.getId())>=0);
		});
	},
	isTristateCheckbox: function(oid){
		var tids = new Array();
		var isTristate = false;
		if(this.oTristateIds !=''){
			tids = this.oTristateIds.split(",");
		}
		return (tids.indexOf(oid)>=0);
	},
	resetSelected: function(oSelIds, options){
		this.oSelIds = (oSelIds==null)?'':oSelIds;
		this.options = options || {};
		this.oTristateIds=this.options.tristateids!=null?this.options.tristateids:'';
		this.oDisabledIds=this.options.disabledids!=null?this.options.disabledids:'';
		this.resetOptionsSelected();
		this.constructList();
	},
		
	getHtml: function(){
		var html = '<div id=\"'+this.layerId+'\" class=\"'+this.layerclass+'\">';
		html += '</div>';
		return html;
	},
	
	init: function() {
			this.oLayer=$(this.layerId); //update
			//set the width of combo
			Element.setStyle(this.oLayer,{width:this.width});
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
	var htm = "";
	for(i=0;i<this.totalOptions;i++){
		var opt = this.oSelectOptions[i];
		var divClassName="";
		if(this.oStyles){
			if(this.oStyles.length>opt.getLevel()){
				divClassName=this.oStyles[opt.getLevel()];
			}
		}
		
		htm += '<div id=\"opt_div_' + i + '\" title=\"' + opt.getText() + '\" class=\"'+ divClassName + '\">';
		var imgSrc = (opt.isDisabled())?this.disableduncheckedimg : this.uncheckedimg;
		if(opt.isSelected()){
			imgSrc = (opt.isDisabled())?this.disabledcheckedimg : this.checkedimg;
		}
		if(opt.isTriState()){
			imgSrc = this.tristatecheckedimg;
		}
		htm += '<table cellspacing=0 cellpadding=0>';
		htm += '<tr><td>';
		htm += '<img src=\"'+ imgSrc + '\" class=\"' + this.imageclass + '\" id=\"' + this.imageId + "_" +i +'\">';
		htm += '</td>';
		if(opt.options.optImage !=null){
			htm += '<td>';
			htm += '<img src=\"'+ opt.getOptions().optImage + '\" class=\"' + this.contentImgClass + '\" >';
			htm += '</td>';
		}
		htm += '<td>';
		htm += opt.getText().escapeHTML();
		htm += '</td></tr></table>';
		htm +='</div>';
	}
	this.oLayer.innerHTML =htm;
	this.resizeBox();
  },
	resizeBox: function(){
		var height = (this.totalOptions>0)?Element.getStyle(this.getEntry(0), 'height'):'15px';
		height=height.substr(0,height.length-2);
		height=(this.totalOptions>this.size)?height*this.size:height*this.totalOptions;
		
		height=(height==0)?18:height+5; //+2 is added for border
		Element.setStyle(this.oLayer,{height:height+'px'});
		if(this.totalOptions>this.size){
			Element.setStyle(this.oLayer,{overflowY:'scroll'});
			
		}else{
			//Element.setStyle(this.oLayer,{overflow-y:''});
		}
		//overflow-y: scroll;
	},
	getEntry: function(index) {
		return this.oLayer.childNodes[index];
	}, 
	getSelectedIds: function(){
		return this.getSelectedItems(true,true,',');
	},
	getSelectedText: function(separator){
		return this.getSelectedItems(true,true,',');
	},
	getSelectedOptions: function(){
		return this.getSelectedItems(false,true,',');
	},
	getTristateIds: function(){
		return this.getSelectedItems(true,false,',');
	},
	getTristateText: function(separator){
		return this.getSelectedItems(false,false,',');
	},
	getSelectedItems: function(ids, forSelect, separator){
		var selItem="";
		this.oSelectOptions.each(function(item) {
			var selected = forSelect? item.isSelected():item.isTriState();
			
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
		var element = Event.findElement(event, 'div');
		var index = element.id.split('_')[2];
		if(index>=0){
			if(this.togleSelected(index)){
				if(this.onChangeHandler!=null){
					eval(this.onChangeHandler)(this);
				}
			}
		}
  },
  
  togleSelected: function(idx){
			var chk = document.getElementById(this.imageId + "_" +idx);
			var opt = this.oSelectOptions[idx];
			if(opt.isDisabled()){
				return false;
			}
			if(opt.isSelected()){
				chk.src=this.uncheckedimg;
				opt.setSelected(false);
				opt.setIsTriState(false);
			}else if(opt.isTriState()) {
				chk.src=this.checkedimg;
				opt.setSelected(true);
				opt.setIsTriState(false);
			}else{
				if(this.isTristateCheckbox(opt.getId())){
					chk.src=this.tristatecheckedimg;
					opt.setSelected(false);
					opt.setIsTriState(true);
				}else{
					chk.src=this.checkedimg;
					opt.setSelected(true);
					opt.setIsTriState(false);
				}
			}
			return true;	
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