/** Author : Shivprasad */

/* This is a function to trim the string */
String.prototype.trim = function() {
 // skip leading and trailing whitespace
 // and return everything in between
  var x=this;
  x=x.replace(/^\s*(.*)/, "$1");
  x=x.replace(/(.*?)\s*$/, "$1");
  return x;
}

/*****
 *  Inserted by PraveenK
 *  To Support IE9 following fragment is added. 
 *  The following method is used in Prototype.js (After auto-complete) and  tinymce
 *  Following code fragment can be removed when above two libraries fix this issue. 
 *****/
if ((typeof Range !== 'undefined') && !Range.prototype.createContextualFragment) {
    Range.prototype.createContextualFragment = function(html) {
        var frag = document.createDocumentFragment(); 
        var div = document.createElement('div');
        frag.appendChild(div);
        div.outerHTML = html;
        return frag;
    };
}


/* This function is used to toggle the divs
	To make div visible pass it as Div1
	and to hide pass as div2
*/
function toggleDivs(div1,div2){
		document.getElementById(div1).style.display="block";
		document.getElementById(div2).style.display="none";
}


/*
Gets table id and remove all rows from the table
*/
function emptyTable(tableId){
	var tbl = document.getElementById(tableId);
	var totRows = tbl.rows.length;
	for(i=totRows-1;i>=0;i--){
		tbl.deleteRow(i);
	}
}

/* 
Selects the option from the list
cboControl is the control for which the option is to be selected
selId is the id of the option to be selected
*/
function setSelected(cboControl,selId){
	if(cboControl.options.length >0){
	    for(i=0; i<cboControl.options.length; i++){
	    	if(cboControl.options[i].value==selId){
	    		cboControl.options[i].selected=true;
	    	}
	    }
	}
}

/*
Creates a new form element
ctl - is ctl type
elementType - type of element
elementName - name of element
elementValue - value of element
elementClass - style of element
*/
function createFormElement(ctl,elementType,elementName,elementValue,elementClass, elementId){
	var elm = document.createElement(ctl);
	elm.type=elementType;
	elm.name=elementName;
	elm.value=elementValue;
	if(elementClass.length>0){
		elm.className=elementClass;
	}
	if(elementId) {
		elm.id=elementId;
	}
	return elm;
}
/*
Formats str by replacing ',' with '&#44;'
and '|' with '&#124;'
*/
function formatString(str){
	if(str) {
		str = str.replace(/,/g ,"&#44;"); 
		str = str.replace(/[|]/g ,"&#124;");
		str = str.replace(/:/g ,"&#58;");
		str = str.replace(/_/g ,"&#95;");
		return str;
	} else {
		return str;
	}
}

/*
Reconstruct str by replacing '&#44;' with ','
and '&#124;' with '|'
*/
function reformatString(str){
	str = str.replace(/(&#44;)/g,","); 
	return str.replace(/(&#124;)/g,"|"); 
}

/*
Display popup DIV with id as divId.
position it to center.
If height and width not given assume it to be 300px
*/
function showPopUpDiv(divId){
	var divTag = document.getElementById(divId);
	var popWidth = divTag.style.width;
	var popHeight= divTag.style.height;
	
	if(popWidth=="")popWidth=300;
	if(popHeight=="")popHeight=300;
		
	x = document.body.scrollLeft + (screen.width/2) - (parseInt(popWidth)/2);
    y = document.body.scrollTop + (screen.height/2) - (parseInt(popHeight)/2);
    
    divTag.style.display="block";
    divTag.style.left = x;
    divTag.style.top = y;
}
/*
Hide DIV with id divId
*/
function hidePopUpDiv(divId){
	var divTag = document.getElementById(divId);
	divTag.style.display="none";
}

/*returns true if enter is pressed on the calling control
onkeypress="if(checkEnter(event)){return false;}
*/

function checkEnter(e){
	var characterCode //literal character code will be stored in this variable
	var ie=false;
	var ns=false;
	
	if(e && e.which){ //if which property of event object is supported (NN4)
		e = e;
		characterCode = e.which; //character code is contained in NN4's which property
		ns=true;
	}else{
		e = event;
		characterCode = e.keyCode; //character code is contained in IE's keyCode property
		ie=true;
	}
	
	
	if(characterCode == 13){ //if generated character code is equal to ascii 13 (if enter key)
		return true; 
	}else{
		return false; 
	}
}

/*
	Function to parse the xmlFile containing errors.
*/
function parseErrors(xmlFile) {
	errors = xmlFile.getElementsByTagName("errors")[0];
	var errorElems = errors.getElementsByTagName("error");
	errorElem = errorElems[0].firstChild.nodeValue;
	return errorElem;
}

/*
	Function to parse the xmlFile containing Ids.
*/
function parseIds(xmlFile) {
	ids = xmlFile.getElementsByTagName("ids")[0];
	var idElems = ids.getElementsByTagName("id");
	idElem = idElems[0].firstChild.nodeValue;
	return idElem;
}

function addError(errors, error) {
	if(errors.length > 0) {
		errors += '\n';
	}
	errors += error;
	return errors;
}

function unescapeHTML(html) {
	var htmlNode = document.createElement("DIV");
	htmlNode.innerHTML = html;
	if(htmlNode.innerText !== undefined)
	return htmlNode.innerText; // IE
	return htmlNode.textContent; // FF
} 

/*
   Function to alert for special character 
 */
function validateCharacters(string){
	var myRegExp1 = /<|>|"|'|%|;|\)|\(|\&/;
	var matchPos1 = string.search(myRegExp1);
	if(matchPos1 != -1)
		return false;
	else
		return true; 
}