function setArrows(leftD, rightD){
	document.getElementById("btn_left").style.display=leftD;
	document.getElementById("btn_right").style.display=rightD;
}
function menuExist(menuId){
	if(document.getElementById(menuId))return true;
	return false;
}
function TOGGLE_MENU(dir){
	var toDisplay=6;
	var displayed=0;
	if(dir=="1"){
		setArrows("block","none" );
		for(var i=top_menus.length; i>0; i--){
			if(displayed<toDisplay){
				top_menus[i-1].style.display="block";
			}else{
				top_menus[i-1].style.display="none";
			}
			displayed++;
		}
	}else{
		setArrows("none", "block");
		for(var i=0; i<top_menus.length; i++){
			if(displayed<toDisplay){
				top_menus[i].style.display="block";
			}else{
				top_menus[i].style.display="none";
			}
			displayed++;
		}
	}
}
//this code runs when page loads
var top_menus = Array();
var top_cnt=0;
var top_total_tabs=12;
for(var i=1; i<12; i++){
	if(menuExist('m_'+ i)){
		top_menus[top_cnt++] = document.getElementById('m_'+ i);
	}
}
