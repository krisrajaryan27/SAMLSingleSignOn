/*
* 	New Ajax Functions 
*/
function ajaxCall(action,method,pars,oncomplete, onfailure){
	pars = uncache(pars);
	return new Ajax.Request(
				action, 
				{
					method: method, 
					parameters: pars, 
					onComplete: oncomplete,
					onFailure: onfailure
				});
}
function uncache(url){
	var d = new Date();
	var time = d.getTime();
	return url + '&tajax='+time;
} 

function reportError(request){
	alert("HTTP error "+request.status+": "+request.statusText);
}

function isValidSession(xmlFile,urlToRedirect){
	if(xmlFile && xmlFile.getElementsByTagName("session-expired")[0]!=null){
		if(xmlFile.getElementsByTagName("session-expired")[0].firstChild.nodeValue =="1"){
			if(urlToRedirect!=''){
				window.location=urlToRedirect;	
			}
			return false;
		}
	}
	return true;
}

function isErrorXml(xmlFile){
	if(xmlFile && xmlFile.getElementsByTagName("errors")[0] !=null){
		return true;
	}
	return false;
	
}

function getErrors(xmlFile){
	var errors = new Array();
	if(	xmlFile.getElementsByTagName("errors")[0] !=null){
		var err = xmlFile.getElementsByTagName("errors")[0];
		var errs = err.getElementsByTagName("error");
		for(var i=0;i<errs.length; i++){
			errors[errors.length]=errs[i].firstChild.nodeValue;
		}		
	}
	return errors;
}

function getIds(xmlFile){
	var arrIds = new Array();
	if(	xmlFile.getElementsByTagName("ids")[0] !=null){
		var idElm = xmlFile.getElementsByTagName("ids")[0];
		var ids = idElm.getElementsByTagName("id");
		for(var i=0;i<ids.length; i++){
			arrIds[arrIds.length]=ids[i].firstChild.nodeValue;
		}		
	}
	return arrIds;
}

function getSingleElement(parent,tagName,defVal){
	try {
		//return parent.getElementsByTagName(tagName)[0].firstChild.nodeValue;
		var xmlNode = parent.getElementsByTagName(tagName)[0];
		if(!xmlNode) return defVal;
	    if(typeof(xmlNode.textContent) != "undefined")
	    	return xmlNode.textContent;
	    
	    return xmlNode.firstChild.nodeValue;
	} catch( myError ) {}
	return defVal;
}

function getStringToSubmit(str){
	return encodeURIComponent(str);
}