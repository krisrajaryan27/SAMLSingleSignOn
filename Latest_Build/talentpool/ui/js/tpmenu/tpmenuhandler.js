// JavaScript Document
var TpMenuHandler =  Class.create();
TpMenuHandler.MENU_NO=0;
TpMenuHandler.postfixImgTD = "_imgtd";
TpMenuHandler.postfixImg = "_img";
TpMenuHandler.postfixTextTD = "_txttd";
TpMenuHandler.postfixArrowTD = "_arrowtd";
TpMenuHandler.postfixArrowImg = "_arrowimg";

TpMenuHandler.prototype ={
	/*oMenus is array of TpMenus*/
	initialize: function(oTpMenu, options) {
		this.oTpMenu = oTpMenu;
		this.options=options;
		TpMenuHandler.MENU_NO++;
		this.menuId = 'MENU'+ TpMenuHandler.MENU_NO;
		this.menus = new Array(); /*this holds the references to menu divs */
		this.menuItems = new Array(); /*this holds the references to individual menu items*/
		
		this.menuList = oTpMenu.getMenuList(); /* this holds the menus as passed by users, the code will set id for each*/
		
		this.type = this.options.type!=null?this.options.type:'';
		this.image=this.options.image!=null?this.options.image:'';
		this.title=this.options.title!=null?this.options.title:'';
		this.onclick=this.options.onclick!=null?this.options.onclick:'';
		
		this.menuClass=this.options.menuClass!=null?this.options.menuClass:'divTpMnu';
		this.tableClass=this.options.tableClass!=null?this.options.tableClass:'tblTpMnu';
		this.selectedClass=this.options.selectedClass!=null?this.options.selectedClass:'selectedTpMenu';
		
		this.subMenuImg = this.options.subMenuImg!=null?this.options.subMenuImg:'images/ico_sub_arrow.gif';
		this.createUI(this.menuList, this.menuId );
		this.storeReferences();
		this.addObservers();
		this.initSettings();
		this.lostFocus=false;
		this.params=null;
		this.offsetoptions=null;
	},
	initSettings: function(){
		//disable menus, hide menus, set toggle state for each menu is done here
		for(x=0;x<this.menuItems.length;x++){
			var menuItem = this.menuItems[x] ;
			var oItem = this.getOriginalMenuObject(this.menuList, menuItem.id);
			this.ChangeItemUI(oItem);
		}
	},
	setMenuItemToggleState: function(_menuItemId, _toggleState){
		/* if toggle state is null then auto toggle it*/
		var menuItem =this.getOriginalMenuObjectFromUserGivenId(this.menuList,_menuItemId);
		if(_toggleState==null){
			menuItem.setToggleState(null);
		}else{
			menuItem.setToggleState(_toggleState);
		}
		//change individual item UI
		this.ChangeItemUI(menuItem);
		return menuItem.getToggleState();
		
	},
	getMenuItemToggleState:function(_menuItemId){
		var menuItem =this.getOriginalMenuObjectFromUserGivenId(this.menuList,_menuItemId);
		return menuItem.getToggleState();
	},
	disableMenuItem: function(_menuItemId, _State){
		var menuItem =this.getOriginalMenuObjectFromUserGivenId(this.menuList,_menuItemId);
		menuItem.setDisabled(_State);
		this.ChangeItemUI(menuItem);
	},
	isDisabled:function(_menuItemId){
		var menuItem =this.getOriginalMenuObjectFromUserGivenId(this.menuList,_menuItemId);
		return menuItem.isDisabled();
	},
	hideMenuItem: function(_menuItemId, _State){
		var menuItem =this.getOriginalMenuObjectFromUserGivenId(this.menuList,_menuItemId);
		menuItem.setHidden(_State);
		this.ChangeItemUI(menuItem);
	},
	
	isHidden:function(_menuItemId){
		var menuItem =this.getOriginalMenuObjectFromUserGivenId(this.menuList,_menuItemId);
		return menuItem.isHidden();
	},
	
	ChangeItemUI: function(_menuItemOriginal){
		var menuItemReference = this.getMenuItemReference(_menuItemOriginal.getMenuId());
		var childs = menuItemReference.getElementsBySelector('#' + menuItemReference.id + TpMenuHandler.postfixImgTD);
		if(_menuItemOriginal.getToggleState()){
			Element.addClassName(childs[0],'imgtdToggle');			
		}else{
			Element.removeClassName(childs[0],'imgtdToggle');
		}
		
		if(_menuItemOriginal.isHidden()){
			menuItemReference.hide();
		}else{
			menuItemReference.show();
		}
		if(_menuItemOriginal.isDisabled()){
			menuItemReference.disabled='disabled';
			/*
			var childs = menuItemReference.getElementsBySelector('#' + menuItemReference.id + TpMenuHandler.postfixImg);
			childs[0].disabled='disabled';
			var childs = menuItemReference.getElementsBySelector('#' + menuItemReference.id + TpMenuHandler.postfixArrowImg);
			childs[0].disabled='disabled';
			*/
		}else{
			menuItemReference.disabled='';
		}
	},
	
	storeReferences:  function() {
		for(i=0;i<this.menus.length;i++){
			//convert menu ids to menu references first
			this.menus[i] = $(this.menus[i]);
		}
		for(i=0;i<this.menuItems.length;i++){
			//convert menu ids to menu references first
			this.menuItems[i] = $(this.menuItems[i]);
		}
	},
	addObservers: function() {
   		//add event handlers to textbox
		for(i=0;i<this.menuItems.length;i++){
			Event.observe(this.menuItems[i], "mouseover", this.onItemHover.bindAsEventListener(this));
			Event.observe(this.menuItems[i], "mousedown", this.onItemClick.bindAsEventListener(this));
		}
		for(i=0;i<this.menus.length;i++){
			Event.observe(this.menus[i], "mouseout", this.onMenuMouseOut.bindAsEventListener(this));
			Event.observe(this.menus[i], "mouseover", this.onMenuMouseOver.bindAsEventListener(this));
		}
		Event.observe(this.menus[this.menus.length-1], "focus", this.onMenuFocus.bindAsEventListener(this));
		Event.observe(this.menus[this.menus.length-1], "blur", this.onMenuMouseOut.bindAsEventListener(this));
	},
	onItemHover: function(event) {
		this.lostFocus=false;
		//$('debug').innerHTML=event.srcElement.nodeName + '<br/>'+ $('debug').innerHTML;
		var srcElement = Event.element(event);
		if(srcElement.nodeName=='TD'){
			var element = Event.findElement(event, 'TABLE');
			var parentDivElement =  Event.findElement(event, 'DIV');
			this.highlightSelected(element.id);
			this.hideAllSubmenus(element.id);
			this.showSubMenu(element,parentDivElement,element.id);
		}
		//Event.stop(event);
	},
	onMenuMouseOver: function(event) {
		this.lostFocus=false;
	},	
	onMenuMouseOut: function(event) {
		this.lostFocus=true;
		setTimeout(this.reallyLostFocus.bind(this),500);
	},	
	onMenuFocus: function(event) {
		this.lostFocus=false;
	},	
	reallyLostFocus: function(event) {
		if(this.lostFocus){
			this.hide();			
		}
	},
	onItemClick: function(event) {
		var element = Event.findElement(event, 'TABLE');
		var mnuItem = this.getOriginalMenuObject(this.menuList,element.id);
		if(mnuItem.options.onclick !=null){
			//alert(mnuItem.options.onclick+"('"+mnuItem.options.id+ "'," + this.params + ")");
			eval(mnuItem.options.onclick)(mnuItem, this.params);
		}
		//Event.stop(event);
	},
	showSubMenu: function(element,parentDivElement,_menuItemId){
		for(i=0;i<this.menus.length;i++){
			var tmpId = this.menus[i].id;
			if((tmpId.length-2 == _menuItemId.length) && (tmpId.substr(0,_menuItemId.length) == _menuItemId)){
				var pWidth = Element.getStyle(parentDivElement,'width');
				pWidth = Math.round(pWidth.substr(0,pWidth.length-2))-3;
				Position.clone(element, this.menus[i], {setHeight: false, setWidth: false, offsetTop: 0, offsetLeft: pWidth});
				var zindex = Element.getStyle(parentDivElement,'zIndex');
				zindex=zindex+1;
				Element.setStyle(this.menus[i],{zIndex:zindex});
				this.menus[i].show();
				break;
			}
		}
	},
	hideAllSubmenus: function(_menuItemId){
		for(i=0;i<this.menus.length;i++){
			var tmpId = this.menus[i].id;
			if((tmpId.length > _menuItemId.length) && (tmpId.substr(0,_menuItemId.length) != _menuItemId)){
				this.menus[i].hide();
			}
		}
	},
	highlightSelected: function(_menuItemId){
		for(i=0;i<this.menuItems.length;i++){
			if(_menuItemId==null){
				Element.removeClassName(this.menuItems[i],this.selectedClass);
			}else{
				var tmpId = this.menuItems[i].id;
				if((tmpId.substr(0,tmpId.length-1) == _menuItemId.substr(0,_menuItemId.length-1)) && (tmpId.length==_menuItemId.length)){
					if(_menuItemId==tmpId){
					var oItem = this.getOriginalMenuObject(this.menuList, tmpId);
					if(!oItem.getIsCategory()){
						Element.addClassName(this.menuItems[i],this.selectedClass);
					}
					}else{
						Element.removeClassName(this.menuItems[i],this.selectedClass);
					}
				}
				if(tmpId.length>_menuItemId.length){
					Element.removeClassName(this.menuItems[i],this.selectedClass);
				}
			}
		}
	},
	
	createUI: function(_menuList, _divId){
		var html = "";
		_divId = _divId + '_0';
		
		html += '<div id=\"'+_divId+'\" class=\"'+this.menuClass+'\">';
		var itemCount=0;
		var divWidth='150px';
		for(var i=0;i<_menuList.length;i++){
			var mnuItem = _menuList[i];
			var tableId =  _divId + itemCount;
			mnuItem.setMenuId(tableId);
			if(mnuItem.options.type=="menu"){
				var ImageTDId = tableId + TpMenuHandler.postfixImgTD;
				var ImageId = tableId + TpMenuHandler.postfixImg;
				var TextTDId = tableId + TpMenuHandler.postfixTextTD;
				var ArrowTDId = tableId + TpMenuHandler.postfixArrowTD;
				var ArrowImageId = tableId + TpMenuHandler.postfixArrowImg;
				var tblclass = mnuItem.getMenuCss()==false?this.tableClass:mnuItem.getMenuCss();
				
				html += '<TABLE class=\"'+ tblclass + '\" id=\"' + tableId + '\" width=\"100%\" cellspacing=\"1\" >';
				html += '<TR>';
				html += '<TD class=\"imgtd\" id=\"'+ ImageTDId +'\">';
				if(mnuItem.options.image !=null){
				html += '<img src=\"' + mnuItem.options.image + '\" class=\"icondimensions\" id=\"'+ ImageId +'\">';
				}
				html +='</TD>';
				html += '<TD id=\"'+ TextTDId +'\">'+ mnuItem.options.title +'</TD>';
				html += '<TD class="imgarrow" id=\"'+ ArrowTDId +'\">';
				if(mnuItem.getMenuList().length>0){
				html += '<img src=\"' + this.subMenuImg + '\" title=\"Send SMS\" class=\"arrowdimensions\" id=\"'+ ArrowImageId +'\">';
				}
				html += '</TD>';
				html += '</TR>';
				html += '</TABLE>';
				this.menuItems[this.menuItems.length]=tableId;
				
			}else{
				//separator
			}
			itemCount++;
			if(mnuItem.getMenuList().length>0){
				this.createUI(mnuItem.getMenuList(),tableId);
			}
			if(mnuItem.options.width!=null){
				divWidth = mnuItem.options.width;
			}
			
		}
		html += '</div>';
		document.write(html);
		//add this div to menus references array
		this.menus[this.menus.length]=_divId;
		
		//hide divmenu and set the width for each
		$(_divId).hide();
		Element.setStyle($(_divId),{width:divWidth});
	},
	getMenuItemReference: function(_menuId){
		for(i=0;i<this.menuItems.length;i++){
			var menuItem = this.menuItems[i];
			if(menuItem.id==_menuId){
				return menuItem;
			}
		}
	},
	
	getOriginalMenuObject: function(_menuList,_menuId){
		var mit = null;
		for(var i=0;i<_menuList.length;i++){
			
			var mnuItem = _menuList[i];
			if(mnuItem.getMenuId()==_menuId){
				mit = mnuItem;
				break;
			}
			if(mnuItem.getMenuList().length > 0){
				mit = this.getOriginalMenuObject(mnuItem.getMenuList(),_menuId);
				if(mit!=null){
					break;
				}
			}
		}
		return mit;
	},
	
	getOriginalMenuObjectFromUserGivenId: function(_menuList,_menuId){
		var mit = null;
		for(i=0;i<_menuList.length;i++){
			var mnuItem = _menuList[i];
			if(mnuItem.getId()==_menuId){
				mit = mnuItem;
				break;
			}
			if(mnuItem.getMenuList().length > 0){
				mit = this.getOriginalMenuObjectFromUserGivenId(mnuItem.getMenuList(),_menuId);
				if(mit!=null){
					break;
				}
			}
		}
		return mit;
	},
	
	setOffsetOptions: function(_offsetOptions){
		this.offsetoptions=_offsetOptions;
	},

	show: function(_params,_elementId){
		//alert();
		this.params=_params; /*these params will be passed as it is in onclick event of menu*/
		if(this.offsetoptions!=null){
			Position.clone(_elementId, this.menus[this.menus.length-1], this.offsetoptions);
		}else{
			Position.clone(_elementId, this.menus[this.menus.length-1], {setHeight: false, setWidth: false, offsetTop:20});
		}
		this.highlightSelected(null); /*this will remove all selection*/
		this.menus[this.menus.length-1].show();
		this.menus[this.menus.length-1].focus();
	},
	
	hide: function(){
		for(i=0;i<this.menus.length;i++){
			this.menus[i].hide();
		}
	}
	
	
}