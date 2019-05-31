// JavaScript Document
var TpMenu =  Class.create();
TpMenu.prototype ={
	/*oMenus is array of TpMenus*/
	initialize: function(options) {
		this.oMenuList = new Array();
		this.options=options!=null?options: {};
		this.isToggle = this.options.isToggle!=null?this.options.isToggle:false;
		this.disabled = this.options.disabled!=null?this.options.disabled:false;
		this.hidden = this.options.hidden!=null?this.options.hidden:false;
		this.toggleState =  this.options.toggleState!=null?this.options.toggleState:false;
		this.id = this.options.id;
		this.menuCss= this.options.menuCss!=null?this.options.menuCss:false;
		this.isCategory=this.options.isCategory!=null?this.options.isCategory:false;
	},
	getIsCategory: function(){
		return this.isCategory;
	},
	getMenuCss: function(){
		return this.menuCss;
	},
	getId: function(){
		return this.options.id;
	},
	getOptions: function(){
		return this.options;
	},
	
	addItem: function(tpMenu){
		this.oMenuList[this.oMenuList.length]=tpMenu;
	},
	
	getMenuList: function(){
		return this.oMenuList;
	},
	
	setMenuId: function(oMenuId){
		this.menuId=oMenuId;
	},
	
	getMenuId: function(){
		return this.menuId;
	},
	getIsToggle: function(){
		return this.isToggle;
	},
	getToggleState: function(){
		return this.toggleState;
	},
	setToggleState: function(_state){
		/*if _state passed is null then automatically toggle*/
		if(_state == null){
			this.toggleState = !this.toggleState;
		}else{
			this.toggleState =_state;
		}
	},
	setHidden: function(_state){
		this.hidden = _state;
	},
	
	isHidden: function(){
		return this.hidden;
	},
	
	setDisabled: function(_state){
		this.disabled=_state;
	},
	
	isDisabled: function(){
		return this.disabled;
	},
	
	toString: function(){
		var str ="";
		for(i=0;i<this.oMenuList.length;i++){
			str = str + ' | ' + (this.oMenuList[i].options.type);
		}
		return str;
	}
	
}