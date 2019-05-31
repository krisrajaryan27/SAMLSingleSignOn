
function TimePopUp(divId){
	this.divId=divId;
	this.divContainer=$(divId);
	this.targetElm=null;

	this.showTime=showMYTime;
	this.getTime=getMYTime;	
	this.hidePopup=hideTimePopupWindow;
	this.visible=visible;
	this.onKeyPress=onKeyPressEventForTimePopUp;
	
	Event.observe(this.divContainer, "keypress", this.onKeyPress.bindAsEventListener(this));
}

function visible() {
	if (this.divContainer.style.display=='block') {
		return true;
	}
	return false;
}

function hideTimePopupWindow() {
	$(this.divId).style.display='none';
	return;
}
function onKeyPressEventForTimePopUp(event){
    	switch(event.keyCode) {
		case Event.KEY_ESC:
			 hideTimePopUp(this.targetElm,this.divId);
			 Event.stop(event);
			 return;
		}
}
function showMYTime(anchorName,targetElm){
	this.targetElm=targetElm;
	var tmpDate = $(targetElm).value;
	
	this.divContainer.innerHTML=this.getTime(this.targetElm, this.divId);
	Position.clone($(anchorName),this.divId,{setHeight: false, offsetTop: $(anchorName).offsetHeight});
	this.divContainer.style.display='block';
	this.divContainer.focus();
}
function getMYTime(elem1, elem2){
	amStrVal = '\''+ elem1 + '\',\'' + elem2 + '\',this.innerHTML,\'AM\'';
	pmStrVal ='\''+ elem1 + '\',\'' + elem2 + '\',this.innerHTML,\'PM\'';
	pmStr = 'PM';
	strTime = '';
	strTime += '<table class=timePopUpOuter cellspacing=1 cellpadding=1>';
	strTime += '<tr>';
	strTime += '<td>';
	strTime += '<table class=timePopUp cellspacing=0>';
	strTime += '<tr><td class=header>AM</td></tr>';
	strTime += '<tr><td class=AMCell onclick=selectMYTime(' + amStrVal + ');>08:00</td></tr>';
	strTime += '<tr><td class=AMCell onclick=selectMYTime(' + amStrVal + ');>08:30</td></tr>';
	strTime += '<tr><td class=AMCell onclick=selectMYTime(' + amStrVal + ');>09:00</td></tr>';
	strTime += '<tr><td class=AMCell onclick=selectMYTime(' + amStrVal + ');>09:30</td></tr>';
	strTime += '<tr><td class=AMCell onclick=selectMYTime(' + amStrVal + ');>10:00</td></tr>';
	strTime += '<tr><td class=AMCell onclick=selectMYTime(' + amStrVal + ');>10:30</td></tr>';
	strTime += '<tr><td class=AMCell onclick=selectMYTime(' + amStrVal + ');>11:00</td></tr>';
	strTime += '<tr><td class=AMCell onclick=selectMYTime(' + amStrVal + ');>11:30</td></tr>';	
	strTime += '</table>';
	strTime += '</td>';
	strTime += '<td>';
	strTime += '<table class=timePopUp cellspacing=0>';
	strTime += '<tr><td class=header>PM</td><td class=header>&nbsp;</td><td class=header>&nbsp;</td></tr>';
	strTime += '<tr><td class=PMCell onclick=selectMYTime(' + pmStrVal + ');>12:00</td><td class=PMCell onclick=selectMYTime(' + pmStrVal + ');>04:00</td></tr>';
	strTime += '<tr><td class=PMCell onclick=selectMYTime(' + pmStrVal + ');>12:30</td><td class=PMCell onclick=selectMYTime(' + pmStrVal + ');>04:30</td></tr>';
	strTime += '<tr><td class=PMCell onclick=selectMYTime(' + pmStrVal + ');>01:00</td><td class=PMCell onclick=selectMYTime(' + pmStrVal + ');>05:00</td></tr>';
	strTime += '<tr><td class=PMCell onclick=selectMYTime(' + pmStrVal + ');>01:30</td><td class=PMCell onclick=selectMYTime(' + pmStrVal + ');>05:30</td></tr>';
	strTime += '<tr><td class=PMCell onclick=selectMYTime(' + pmStrVal + ');>02:00</td><td class=PMCell onclick=selectMYTime(' + pmStrVal + ');>06:00</td></tr>';
	strTime += '<tr><td class=PMCell onclick=selectMYTime(' + pmStrVal + ');>02:30</td><td class=PMCell onclick=selectMYTime(' + pmStrVal + ');>06:30</td></tr>';
	strTime += '<tr><td class=PMCell onclick=selectMYTime(' + pmStrVal + ');>03:00</td><td class=PMCell onclick=selectMYTime(' + pmStrVal + ');>07:00</td></tr>';
	strTime += '<tr><td class=PMCell onclick=selectMYTime(' + pmStrVal + ');>03:30</td><td class=PMCell onclick=selectMYTime(' + pmStrVal + ');>07:30</td></tr>';
	strTime += '</table>';
	strTime += '</td>';
	strTime += '</tr>';
	strTime += '</table>';
	return strTime;
}


function hideTimePopUp(targetElm,divId){
	$(divId).style.display='none';
	$(targetElm).focus();
}

function selectMYTime(targetElem, timePopUpDiv, val1, val2) {
	if ($(targetElem)) {
		$(targetElem).value = val1 + ' ' + val2;		
	}
	hideTimePopUp(targetElem, timePopUpDiv);	
	return;
}
