var monthAbbreviations = new Array("Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec");

var MonthYearCalender =Class.create();
MonthYearCalender.prototype = {
	initialize: function(divId){
	this.divId=divId;
	this.divContainer=$(divId);
	this.currentDate=null;
	this.targetElm=null;
	this.dateSeparator='-';
	this.monthAbbreviations = monthAbbreviations;
	
	this.strDatestyle = "EU"; //European date style
	this.displayFormat='DD/MM/YYYY';//can be only MD
	
	this.lostFocus=false;
	Event.observe(this.divContainer, "keypress", this.onKeyPress.bindAsEventListener(this));
	//Event.observe(this.divContainer, "blur", this.onBlur.bindAsEventListener(this));
	//Event.observe(this.divContainer, "focus", this.onFocus.bindAsEventListener(this));
	//Event.observe(this.divContainer, "mousedown", this.onMouseDown.bindAsEventListener(this));
},
//this can be either EU=dd/mm/yyyy oe US=mm/dd/yyyy
setDateStyle: function(dateStyle){
	this.strDatestyle =dateStyle;
},

setDisplayFormat: function(displayFormat){
	this.displayFormat=displayFormat;
},

onBlur: function(event){
	this.lostFocus=true;
	setTimeout(this.reallyLostFocus.bind(this),250);
},
onFocus: function(event){
	this.lostFocus=false;
},
onMouseDown: function(event){
	this.lostFocus=false;
},
reallyLostFocus: function(){
	  if(this.lostFocus){
		hideCalender(this.targetElm,this.divId);
	  }
},
onTargetFocus: function(event){
	this.lostFocus=false;
},
  
onKeyPress: function(event){
		this.lostFocus=false;
    	switch(event.keyCode) {
		case Event.KEY_ESC:
			 hideCalender(this.targetElm,this.divId);
			 Event.stop(event);
			 return;
		}
},
showCalender: function(anchorName,targetElm){
	this.targetElm=targetElm;
	var tmpDate = $(targetElm).value;

	//get Date from box
	var dateFormatter = new DateFormatter();
	dateFormatter.setDateStyle(this.strDatestyle);
	tmpDate = dateFormatter.getDateObject(tmpDate);
	this.currentDate=null;
	if(tmpDate!=''){
		if(tmpDate!=false && !isNaN(tmpDate)){
			this.currentDate = tmpDate;
		}
	}
	this.divContainer.innerHTML=this.getCalender();
	Position.clone($(anchorName),this.divId,{setHeight: false, setWidth: false, offsetTop: $(anchorName).offsetHeight});
	this.divContainer.style.display='block';
	this.divContainer.focus();
},
getCalender: function(){
	var now = new Date();
	if (this.currentDate==null) { this.currentDate = now; }
	var month = this.currentDate.getMonth()+1; 
	var year = this.currentDate.getFullYear();
	var startYear = now.getFullYear()-11;
	
	var strCal = '<TABLE CELLSPACING=0 CELLPADDING=0 CLASS=Cal>\n';
	//Calender Row
	strCal += '<TR>';
	strCal += '<TD>';
		strCal += '<TABLE  CLASS=CalContent id=tblMonths>\n<TBODY>\n';
		for (var i=0; i<6; i++) {
			strCal += '<TR>';
			strCal += '<TD ONCLICK=selectOption(this);';
			if(i*2==month-1){	strCal += ' CLASS=menuon';}
			strCal += '>';
			strCal += this.monthAbbreviations[i*2];
			strCal += '</TD>';
			strCal += '<TD ONCLICK=selectOption(this);';
			if(i*2+1==month-1){strCal += ' CLASS=menuon';}
			strCal += '>';
			strCal += this.monthAbbreviations[(i*2)+1];
			strCal += '</TD>';
			strCal += '</TR>\n';
		}
		strCal += '</TBODY>\n</TABLE>\n';
	strCal += '</TD>';
	strCal += '<TD>';
	//years
		strCal += '<TABLE CLASS=CalContent id=tblYears>\n<TBODY>\n';
		for (var i=0; i<6; i++) {
			strCal += '<TR>';
			strCal += '<TD onclick=selectOption(this);';
			if(startYear==year){strCal += ' CLASS=menuon';}
			strCal += '>';
			strCal += startYear;
			strCal += '</TD>';
			strCal += '<TD onclick=selectOption(this);';
			if(startYear+1==year){strCal += ' CLASS=menuon';}
			strCal += '>';
			strCal += startYear+1;
			strCal += '</TD>';
			strCal += '</TR>\n';
			startYear +=2;
		}
		strCal += '</TBODY>\n</TABLE>\n';
	strCal += '</TD>';
	strCal += '</TR>\n';
	//Save And Cancel Button Row

	strCal += '<TR>';
	strCal += '<TD COLSPAN=2 ALIGN=RIGHT>';
	strCal += '<input type=button name=save value=Save class=btn onclick=showFormattedDate(\''+this.targetElm+'\',\''+this.divId+'\',\''+this.strDatestyle+'\',\''+this.displayFormat+'\');>&nbsp;';
	strCal += '<input type=button name=cancel value=Cancel class=btn onclick=hideCalender(\''+this.targetElm+'\',\''+this.divId+'\')>';
	strCal += '</TD>';
	strCal += '</TR>\n';
	
	strCal += '</TABLE>';
	return strCal;
	
}

}

function hideCalender(targetElm,divId){
	$(divId).style.display='none';
	$(targetElm).focus();
}

function selectOption(col){
	var tbl = col.parentNode.parentNode.parentNode;
	for(var I=0;I<tbl.rows.length;I++){
		var row=tbl.rows[I];
		row.cells[0].className='';
		row.cells[1].className='';
	}
	col.className='menuon';

}

function showFormattedDate(targetElm,divId,dateStyle,displayFormat){
	var elm = $(targetElm);
	var month = getSelectedInTable('tblMonths');
	var year = getSelectedInTable('tblYears');
	for(var I=0;I<monthAbbreviations.length;I++){
		if(month==monthAbbreviations[I]){
			month = I+1;
			break;
		}
	}
	var dateStr='1/'+ month +'/' + year;
	var dtf = new DateFormatter();
	dtf.setDisplayFormat(displayFormat);
	var tmpDate = dtf.getFormattedDate(dateStr);
	elm.value=tmpDate;
	$(divId).style.display='none';
	$(targetElm).focus();
	/*
	if(month<10)month='0'+month;
	elm.value=monthAbbreviations[month-1]+separator+year;
	$(divId).style.display='none';
	$(targetElm).focus();
	*/
}
function getSelectedInTable(tblId){
	var tbl = $(tblId);
	for(var I=0;I<tbl.rows.length;I++){
		var row=tbl.rows[I];
		if(row.cells[0].className=='menuon')return row.cells[0].firstChild.nodeValue;
		if(row.cells[1].className=='menuon')return row.cells[1].firstChild.nodeValue;
	}
}
