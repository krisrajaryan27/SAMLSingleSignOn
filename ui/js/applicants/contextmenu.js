var strRightClickMenu = '<?xml version=\'1.0\'?>';
strRightClickMenu += '<menu absolutePosition="auto" mode="popup" maxItems="8"  ';
strRightClickMenu += 'globalCss="contextMenu" globalSecondCss="contextMenu" globalTextCss="contextMenuItem" withoutImages="true" >';
strRightClickMenu += '<MenuItem  name="Name" src=""  id="1"/>';
strRightClickMenu += '<MenuItem  name="Email1" src=""  id="2"/>';
strRightClickMenu += '<MenuItem  name="Mobile" src=""  id="3"/>';
strRightClickMenu += '<MenuItem  name="Location" src=""  id="4"/>';
strRightClickMenu += '<MenuItem  name="Extract skills" src=""  id="5"/>';
strRightClickMenu += '<MenuItem  name="Extract educational qualification" src=""  id="6"/>';
strRightClickMenu += '<MenuItem  name="Working since" src=""  id="7"/>';
strRightClickMenu += '<MenuItem  name="Current employer" src=""  id="8"/>';
strRightClickMenu += '</menu>';

var aMenu = null;
var textSelected = "";

function loadContextMenu(){
	Event.observe(document, "mousedown", onRightMouseDown.bindAsEventListener(this));
	aMenu=new dhtmlXContextMenuObject('120',0,"Demo menu","images/dhtmlxGrid/","empty.html");
	aMenu.menu.loadXMLString(strRightClickMenu);
	aMenu.setContextMenuHandler(onRightMenuClick);
	aMenu.menu.setGfxPath("images/dhtmlxGrid/");
}
function onRightMenuClick(menuitemId,gridItemId){
  if (menuitemId == '1') {
	  window.parent.setName(textSelected);
  }else if (menuitemId == '2') {
	  window.parent.setEmail1(textSelected);
  }else if (menuitemId == '3') {
	  window.parent.setMobile(textSelected);
  }else if (menuitemId == '4') {
	  window.parent.setLocation(textSelected);
  }else if (menuitemId == '5') {
	  window.parent.setSkills(textSelected);
  }else if (menuitemId == '6') {
	  window.parent.setEducation(textSelected);
  }else if (menuitemId == '7') {
	  window.parent.setWorkingSince(textSelected);
  }else if (menuitemId == '8') {
	  window.parent.setCurrentEmployer(textSelected);
  }
}

function onRightMouseDown(ev){
	if(ev.button==2){
		textSelected = getSelText();
		var el;
		if (ev.target) el = ev.target;
		else if (ev.srcElement) el = ev.srcElement;
		el.contextMenu=aMenu;
		el.a=this.aMenu._contextStart;	
		if(navigator.appVersion.indexOf('MSIE')>0)
		ev.srcElement.oncontextmenu = function(){event.cancelBubble=true;return false;};
		el.a(el,ev);
		el.a=null;
		return true;
	}
}
function getSelText()
{
	var txt = '';
	if (window.getSelection)
	{
		txt = window.getSelection();
	}
	else if (document.getSelection)
	{
		txt = document.getSelection();
	}
	else if (document.selection)
	{
		txt = document.selection.createRange().text;
	}
	return txt;
}
