<script language="JavaScript" src="js/ajaxfunctions.js" type="text/javascript"></script>
<div id="changeStatusDiv" class="dataPane" style="margin:23px;display:none;width:460px;">
  <table width="100%" border="0" cellspacing="0" cellpadding="0">  	
		<tr>
	  	<td>&nbsp;</td>
	    <td>
	        <script type="text/javascript">
			  	  var opts = new Array();					    
		        var m = [new SelectOption('-1','<bean:message key="applicant_home.label.selectStatusMessage"/>')];
						opts = m.concat(opts);
						selectBoxMessage = new SelectBox(opts,'','images/icon_down_arrow.gif',{namesonly:false, width:'220px', size:12});        				
						document.write(selectBoxMessage.getHtml());
						selectBoxMessage.init();
					</script>
	    </td>
	    <td>
	    	<input name="Button" type="button" class="btn" value="<bean:message key="common.go"/>" onclick="javascript: saveStatusMessage();return false;"/>
	    	<input name="Button" type="button" class="btn" value="<bean:message key="common.cancel"/>" onclick="javascript: hideDiv();return false;"/>
	    </td>
	  </tr>
	</table> 
</div>
<script language="JavaScript">
function hideDiv() {
	var elem = document.getElementById("changeStatusDiv");
	if (elem) {
		elem.style.display = 'none';
	}
	
}
var candidateId;
function showDiv(id) {
	candidateId = id;
	// ajax call to get all the status messages for position step in which applicant is placed.
	var pars = "mode=getStatusMessagesInXml&applicantId=" + id;
	var myAjax = ajaxCall("selectionProcess.do",'get',pars,updateStatusMessages, reportError);
	var elem = document.getElementById("changeStatusDiv");
	if (elem) {
		elem.style.display = '';
	}
}

function updateStatusMessages(request){
	xmlFile = request.responseXML;
	if(!isValidSession(xmlFile,'<%=TPApplicationProperties.getProperty("redirect_on_session_expired")%>')) {
	  return;
	}	
	var selectedId = -1;
	var opts = new Array();  
	var messages = xmlFile.getElementsByTagName("messages")[0];
	var message= messages.getElementsByTagName("message");
	if(message != null) {
		for(var i = 0; i < message.length; i++){  		
	  		var id = message[i].getAttribute("id");
	  		var selected = message[i].getAttribute("selected");
	  		if (selected != null && selected == 'selected') {
	  			selectedId = id;
	  		}
	  		var text = message[i].firstChild.nodeValue;
	  		opts[i] = new SelectOption(id, text);
	  	}
	}
	var m = [new SelectOption('-1', '<bean:message key="applicant_home.label.selectStatusMessage"/>')];
	opts = m.concat(opts);
	selectBoxMessage.reInitialize(opts, selectedId);
}

function saveStatusMessage() {
	var id = selectBoxMessage.getSelectedId();
	if (id == '-1') {
		alert('<bean:message key="applicant_home.error.select_status_message"/>');
	} else {
		// save the status message.		
		var pars = "mode=saveStatusMessage&applicantId=" + candidateId + "&statusMessage=" + selectBoxMessage.getText(selectBoxMessage.getSelectedIndex());
  		var myAjax = ajaxCall("selectionProcess.do",'get',pars,messageSaved, reportError);
	}
}

function messageSaved(request){
	xmlFile = request.responseXML;
	if(!isValidSession(xmlFile,'<%=TPApplicationProperties.getProperty("redirect_on_session_expired")%>')) {
	  return;
	}
	hideDiv();
	statusChanged();
}
</script>