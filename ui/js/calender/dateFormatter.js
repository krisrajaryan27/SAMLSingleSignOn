//display format
// Field        | Full Form          | Short Form
// -------------+--------------------+-----------------------
// Year         | yyyy (4 digits)    | yy (2 digits), y (2 or 4 digits)
// Month        | MMM (name or abbr.)| MM (2 digits), M (1 or 2 digits)
//              | NNN (abbr.)        |
// Day of Month | dd (2 digits)      | d (1 or 2 digits)

function DateFormatter(){
	//this.strDatestyle = "US"; //European date style
	this.strDatestyle = "EU"; //European date style
	this.displaySeparator="/";
	this.strMonthArray = new Array("Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec");
	this.strSeparatorArray = new Array("-"," ","/",".");
	
	this.strDate=null;
	this.strDay=null;
	this.strMonth=null;
	this.strYear=null;
	this.intday=null;
	this.intMonth=null;
	this.intYear=null;
	this.datefield = null;

	this.err = 0;
	this.displayFormat='MMM-YYYY';//can be only MD
	this.checkDate=checkDFDate;
	this.setDisplayFormat=setDFDisplayFormat;
	this.getDateObject =getDFDateObject;
	this.getFormattedDate=getDFFormattedDate;
	this.setDateStyle=setDFDateStyle;
	
}
//this can be either EU=dd/mm/yyyy oe US=mm/dd/yyyy
function setDFDateStyle(dateStyle){
	this.strDatestyle =dateStyle;
}
function setDFDisplayFormat(displayFormat){
	this.displayFormat=displayFormat;
}
/* This is a function to trim the string */
String.prototype.trim = function() {
 // skip leading and trailing whitespace
 // and return everything in between
  var x=this;
  x=x.replace(/^\s*(.*)/, "$1");
  x=x.replace(/(.*?)\s*$/, "$1");
  return x;
}

function checkDFDate(objName){
	this.datefield=objName;
	this.strDate = this.datefield.value.trim();
		
	var formattedDate=this.getFormattedDate(this.strDate);
	if(formattedDate!=false){
		this.datefield.value = formattedDate;
		return true;
	}
	return false;
}

function getDFFormattedDate(strDT){
	this.strDate = strDT;
	if(this.getDateObject(this.strDate)!=false){
		
		var strYYYY=this.intYear;
		var strYY=this.strYear.substr(2, 2);
		var strMMM=this.strMonthArray[this.intMonth-1];
		var strM=this.intMonth;
		var strMM=this.intMonth;
		if(this.intMonth<10){
			strMM='0'+this.intMonth;
		}
		var strD=this.strDay;
		var strDD = this.strDay;
		if(this.strDay<10){
			if(this.strDay.length<2){
				strDD = '0'+this.strDay;
			}
		}
		var dtFinal = '';
		var strFormatArray=new Array();
		var strSeparator='';
		
		for (var I = 0; I< this.strSeparatorArray.length; I++) {
			if (this.displayFormat.indexOf(this.strSeparatorArray[I]) != -1) {
					strFormatArray =  this.displayFormat.split(this.strSeparatorArray[I]);
					strSeparator=this.strSeparatorArray[I];
					break;
			}
		}

		for (var I = 0; I< strFormatArray.length; I++) {
			if(strFormatArray[I].toLowerCase()=='yyyy'){
				strFormatArray[I]=strYYYY;
			}else if(strFormatArray[I].toLowerCase()=='yy'){
				strFormatArray[I]=strYY;
			}else if(strFormatArray[I]=='MMM'){
				strFormatArray[I]=strMMM;
			}else if(strFormatArray[I]=='MM'){
				strFormatArray[I]=strMM;
			}else if(strFormatArray[I]=='M'){
				strFormatArray[I]=strM;
			}else if(strFormatArray[I].toLowerCase()=='dd'){
				strFormatArray[I]=strDD;
			}else if(strFormatArray[I].toLowerCase()=='d'){
				strFormatArray[I]=strD;
			}
			dtFinal+=strFormatArray[I];	
			if(I< strFormatArray.length-1){
				dtFinal+=strSeparator;
			}
		}
		return dtFinal;
	
	}else{
		return false;
	}
	
}
function getDFDateObject(strDT){
	var strDateArray;
	var booFound = false;
	this.strDate = strDT;
	for (var I = 0; I< this.strSeparatorArray.length; I++) {
		if (this.strDate.indexOf(this.strSeparatorArray[I]) != -1) {
				strDateArray = this.strDate.split(this.strSeparatorArray[I]);
				if (strDateArray.length < 2) {
					this.err = 1;
					return false;
				}else{
					var ind=0;
					if (strDateArray.length == 3){
						this.strDay = strDateArray[ind];
						ind +=1;
					}else{
						this.strDay = '1';
					}
					this.strMonth = strDateArray[ind];
					ind +=1;
					this.strYear = strDateArray[ind];
					
					//alert(this.strDay+'/'+this.strMonth+'/'+this.strYear);
				}
			booFound = true;
		}
	}
	
	if (booFound == false) {
		if (this.strDate.length>5) {
			this.strDay = this.strDate.substr(0, 2);
			this.strMonth = this.strDate.substr(2, 2);
			this.strYear = this.strDate.substr(4);
		}else{
			return false;
		}
	}
	if (this.strYear.length == 2) {
		this.intYear = parseInt(this.strYear, 10);
		if (isNaN(this.intYear)) {
			err = 4;
			return false;
		}
		if(this.intYear>50){
			this.strYear = '19' + this.strYear;
		}else{
			this.strYear = '20' + this.strYear;
		}
	}else if(this.strYear.length != 2 && this.strYear.length != 4){
		return false;
	}
	// US style
	if (this.strDatestyle == "US") {
		strTemp = this.strDay;
		this.strDay = this.strMonth;
		this.strMonth = strTemp;
	}
	this.strDay=this.strDay.replace('st','').replace('nd','').replace('rd','').replace('th','');
	this.intday = parseInt(this.strDay, 10);
	if (isNaN(this.intday)) {
		err = 2;
		return false;
	}
	this.intMonth = parseInt(this.strMonth, 10);
	if (isNaN(this.intMonth)) {
		for (i = 0;i<12;i++) {
			if (this.strMonth.toUpperCase() == this.strMonthArray[i].toUpperCase()) {
				this.intMonth = i+1;
				this.strMonth = this.strMonthArray[i];
				i = 12;
			}else{
				if(this.strMonth.length>3){
					if(this.strMonth.substr(0,3).toUpperCase() == this.strMonthArray[i].toUpperCase()) {
						this.intMonth = i+1;
						this.strMonth = this.strMonthArray[i];
						i = 12;
					}				
				}
			}
		}
		if (isNaN(this.intMonth)) {
			err = 3;
			return false;
		}
	}
	this.intYear = parseInt(this.strYear, 10);
	if (isNaN(this.intYear)) {
		err = 4;
		return false;
	}
	if (this.intMonth>12 || this.intMonth<1) {
			err = 5;
			return false;
	}
	if(this.displayFormat=='D'){
		if ((this.intMonth == 1 || this.intMonth == 3 || this.intMonth == 5 || this.intMonth == 7 || this.intMonth == 8 || this.intMonth == 10 || this.intMonth == 12) && (this.intday > 31 || this.intday < 1)) {
			err = 6;
			return false;
		}
		if ((this.intMonth == 4 || this.intMonth == 6 || this.intMonth == 9 || this.intMonth == 11) && (this.intday > 30 || this.intday < 1)) {
			err = 7;
			return false;
		}
		if (this.intMonth == 2) {
			if (this.intday < 1) {
				err = 8;
				return false;
			}
			if (LeapYear(this.intYear) == true) {
				if (this.intday > 29) {
					err = 9;
					return false;
				}
			}else {
				if (this.intday > 28) {
					err = 10;
					return false;
				}
			}
		}
	}

	return new Date(this.intYear,this.intMonth-1,this.intday,0,0,0);
}