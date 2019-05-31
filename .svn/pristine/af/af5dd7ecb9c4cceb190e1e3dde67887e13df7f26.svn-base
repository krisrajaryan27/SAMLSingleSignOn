<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ page import="org.apache.struts.Globals"%>
<%@ page import="com.talentPool.selectionProcess.SelectionProcessConstants,
                com.talentPool.applicant.dataobject.ApplicantData,
                com.talentPool.common.utils.CommonUtils,
                com.talentPool.selectionProcess.form.SelectionProcessForm,
                com.talentPool.user.manager.PermissionSet,
                com.talentPool.applicant.manager.ImportConfigurationManager,
                com.talentPool.applicant.constants.ImportConfigurationConstants,
                java.util.ArrayList,
                com.talentPool.common.properties.TPApplicationProperties"%>
<logic:present name="update" scope="request">
<script>
window.top.hidePopWin(true);
</script>
</logic:present>                
<script language="JavaScript" src="js/scripta/lib/prototype.js" type="text/javascript"></script>
<script language="JavaScript" src="js/commonFunctions.js" type="text/javascript"></script>
<SCRIPT LANGUAGE="JavaScript" SRC="js/calender/CalendarPopup.js"></SCRIPT>
<script language="JavaScript" src="js/ajaxfunctions.js" type="text/javascript"></script>
<script language="JavaScript" src="js/scripta/src/effects.js" type="text/javascript"></script>
<script language="JavaScript" src="js/selectoption.js" type="text/javascript"></script>
<script language="JavaScript" src="js/selectbox/selectbox.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css" href="themes/default/CalendarPopup.css"> 
<% 
if(request.getAttribute(Globals.ERROR_KEY)!=null){
%>
<table  id="m_errortable" > 
  <tr>
	<td class="header">
	  <b><bean:message key="errors.following_errors"/></b>
	</td>               
  </tr>
  <tr>
    <td class="message"><html:errors/></td>               
  </tr>
</table>
<br>
<% } %>



<div class="contentDivPop" style="width: 500px;">
	<div class="outerDiv">
	<html:form action="/selectionProcess">
  	<html:hidden property="mode" name="selectionProcessForm"/>
  	<html:hidden property="applicantId" name="selectionProcessForm"/>
  	<html:hidden property="phoneNo" name="selectionProcessForm"/>
  	<html:hidden property="communicationType" name="selectionProcessForm"/>
  	<html:hidden property="communicationId" name="selectionProcessForm"/>
	
	<logic:equal value='<%=""+SelectionProcessConstants.INTERACTION_PHONE %>' property="communicationType" name="selectionProcessForm">
	<bean:define id="phoneIds" name="phoneIds" scope="request" type="ArrayList"></bean:define>
	<bean:define id="applicantData" name="applicantData" scope="request" type="ApplicantData"/>
	<div class="popupTop">
		<table class="tblPop">
		<tr>
			<td class="header">
			<bean:message key="add_phone.label.called"/>
			</td>
			<td>
				<script type="text/javascript">
					var def = [new SelectOption('','')];
					var opts = <%=CommonUtils.getListJavaScriptArray(phoneIds, phoneIds)%>;
					opts = def.concat(opts);
					var m = [new SelectOption('<bean:message key="add_phone.label.new_phone_val"/>','<bean:message key="add_phone.label.new_phone"/>')];
					opts = opts.concat(m);
					selectBox = new SelectBox(opts,'<bean:write property="phoneNo" name="selectionProcessForm"/>','images/btn_dropdown.gif',{namesonly:false, width:'180px', size:15});
					selectBox.setOnChangeHandler('showNewNumber');
					document.write(selectBox.getHtml());
					selectBox.init();
				</script>
			
			</td>
		</tr>
		<tr>
			<td class="header">
			<bean:message key="add_phone.label.date"/>
			</td>
			<td>
                <html:text name="selectionProcessForm" property="logDate" size="12" maxlength="10" styleId="logDate"/>
                <img src="images/ico_cal.gif" onClick="cal.select(document.forms['selectionProcessForm'].logDate,'logDate','dd/MM/yyyy'); return false;" class="CalImg" name="imgCal" id="imgCal"/>
			</td>
		</tr>
		</table>
	</div>
	</logic:equal>
	<div class="popupBody">
		<table class="tblPop">
		<tr>
			<td class="header">
			<bean:message key="add_note.label.note"/>
			</td>
		</tr>
		<tr>
			<td>
				<html:textarea property="note"  name="selectionProcessForm" rows="10" cols="90"></html:textarea>
			</td>
		</tr>
		<tr>
			<td>
			<div class="navBtn" style="float: right;"><a href="#" style="width:60px;" class="active" onclick="javascript: submitForm();"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.save"/></a>
			<a href="#" style="width:60px; margin-left: 5px;" class="active" onclick="javascript: window.top.hidePopWin(isDataChanged);return false;"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.cancel"/></a>
			</div>
			</td>
		</tr>
		</table>
	</div>	
	</html:form>
	</div>
</div>

<logic:equal value='<%=""+SelectionProcessConstants.INTERACTION_PHONE %>' property="communicationType" name="selectionProcessForm">
<div class="contentDivPop" id="divNewPhone_" style="top:0px;position:absolute;display:none;width: 500px; ">
<div class="outerDiv" >
	<div class="popupTop">
		<table class="tblPop">
		<tr>
			<td class="header">
			<bean:message key="add_phone.label.edit_phone"/>
			</td>
		</tr>
		</table>
	</div>
	<div class="popupBody" style="height: 158px;">
		      <table class="tblPop" >
	            <tr>
	              <td class="header"><bean:message key="add_phone.label.phone1"/></td>
	              <td><html:text name="applicantData" property="applicantHomePhone" maxlength="25" size="25" styleId="applicantHomePhone"/></td>
	              <td>
	              	<img src="" id="isInvalidHomePhone" title="Mark Invalid" onclick="javascript: toggleCheckBox(this);"/>
	              </td>
	            </tr>
	            <tr>
	              <td class="header"><bean:message key="add_phone.label.phone2"/></td>
	              <td><html:text name="applicantData" property="applicantWorkPhone" maxlength="25" size="25" styleId="applicantWorkPhone"/></td>
	              <td>
	              	<img src="" id="isInvalidWorkPhone" title="Mark Invalid" onclick="javascript: toggleCheckBox(this);"/>
	              </td>
	            </tr>
	            <tr>
	            	  <td class="header"><bean:message key="add_phone.label.mobile"/></td>
	            	  <td><html:text name="applicantData" property="applicantCellPhone" maxlength="25" size="25" styleId="applicantCellPhone"/></td>
	            	  <td>
	              	<img src="" id="isInvalidCellPhone" title="Mark Invalid" onclick="javascript: toggleCheckBox(this);"/>
	              </td>
	           	</tr>
	   	      </table>
	</div>    	      
	<div class="popupBody">
		<table class="tblPop" width="100%">
		<tr>
			<td>
			<div class="navBtn" style="float: right;"><a href="#" style="width:60px;" class="active" onclick="javascript: updatePhones();"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.save"/></a>
			<a href="#" style="width:60px; margin-left: 5px;" class="active" onclick="javascript: hideNewPhoneDiv();return false;"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.cancel"/></a>
			</div>
			</td>
		</tr>
		</table>
	</div>    	      
</div>    	      
</div>
<script language="JavaScript">
selectedCheckBox="images/checkboxchecked.gif";
deselectedCheckBox="images/checkboxunchecked.gif";

function toggleCheckBox(obj) {
	var src = obj.src;
	if (src.indexOf(deselectedCheckBox) != -1) {
		obj.src = selectedCheckBox;
	} else {
		obj.src = deselectedCheckBox;
	}
}

<logic:equal name="applicantData" property="applicantHomePhoneIsInvalid" value="<%=SelectionProcessConstants.PHONE_VALID%>">
	$('isInvalidHomePhone').src=deselectedCheckBox;
</logic:equal>
<logic:notEqual name="applicantData" property="applicantHomePhoneIsInvalid" value="<%=SelectionProcessConstants.PHONE_VALID%>">
	$('isInvalidHomePhone').src=selectedCheckBox;
</logic:notEqual>

<logic:equal name="applicantData" property="applicantWorkPhoneIsInvalid" value="<%=SelectionProcessConstants.PHONE_VALID%>">
	$('isInvalidWorkPhone').src=deselectedCheckBox;
</logic:equal>
<logic:notEqual name="applicantData" property="applicantWorkPhoneIsInvalid" value="<%=SelectionProcessConstants.PHONE_VALID%>">
	$('isInvalidWorkPhone').src=selectedCheckBox;
</logic:notEqual>

<logic:equal name="applicantData" property="applicantCellPhoneIsInvalid" value="<%=SelectionProcessConstants.PHONE_VALID%>">
	$('isInvalidCellPhone').src=deselectedCheckBox;
</logic:equal>
<logic:notEqual name="applicantData" property="applicantCellPhoneIsInvalid" value="<%=SelectionProcessConstants.PHONE_VALID%>">
	$('isInvalidCellPhone').src=selectedCheckBox;
</logic:notEqual>
</script>                   
<DIV ID="calDiv" STYLE="position:absolute;visibility:hidden;background-color:#FFF;"></DIV>	
</logic:equal>


<script LANGUAGE="JavaScript">
var isDataChanged=false;
  //submit for if notes is submitted	
  function submitForm() {
	 <logic:equal value='<%=""+SelectionProcessConstants.INTERACTION_PHONE %>' property="communicationType" name="selectionProcessForm">
	      document.selectionProcessForm.phoneNo.value = selectBox.getSelectedId();
	      if(selectBox.getSelectedId()=='' || selectBox.getSelectedId()=='<bean:message key="add_phone.label.new_phone_val"/>'){
	      	alert('<bean:message key="add_phone.error.select_phone"/>');
	      	selectBox.setFocus();
	      	return false;
	      }
      </logic:equal>
		if(document.selectionProcessForm.note.value.trim()==""){
			alert('<bean:message key="add_phone.error.enter_note"/>');
			document.selectionProcessForm.note.focus();
			return;	
		}		      
		document.selectionProcessForm.submit(); 
      	return true;
    }
  

<logic:equal value='<%=""+SelectionProcessConstants.INTERACTION_PHONE %>' property="communicationType" name="selectionProcessForm">

	//Set today date in date text box
	if(document.getElementById('logDate').value==''){
		var dt = new Date();
		var day = dt.getDate()<10?'0'+dt.getDate():dt.getDate();
		var mt=(dt.getMonth()+1)<10?'0'+(dt.getMonth()+1):dt.getMonth()+1;
		var dte=day +'/'+mt+'/'+dt.getFullYear();
		$('logDate').value=dte;
	}
	//Create calender object
	var cal = new CalendarPopup("calDiv"); 
	cal.showNavigationDropdowns();
	
	//Show new number screen when option is selected
	function showNewNumber(){
	    val = selectBox.getSelectedId();
		if(val=="<bean:message key="add_phone.label.new_phone_val"/>"){
			$('divNewPhone_').style.display='block';
			$('applicantHomePhone').focus();
		}else{
			$('divNewPhone_').style.display='none';
		}
	}

	function updatePhones(){
		var homePhone=encodeURIComponent($('applicantHomePhone').value);
		var workPhone=encodeURI($('applicantWorkPhone').value).replace(/\+/g, '%2B');
		var cellPhone=encodeURI($('applicantCellPhone').value).replace(/\+/g, '%2B');
		
		homePhoneIsInvalid = <%=SelectionProcessConstants.PHONE_VALID%>;
		workPhoneIsInvalid = <%=SelectionProcessConstants.PHONE_VALID%>;
		cellPhoneIsInvalid = <%=SelectionProcessConstants.PHONE_VALID%>;
		
		if ($('isInvalidHomePhone').src.indexOf(selectedCheckBox) != -1) {
			homePhoneIsInvalid = <%=SelectionProcessConstants.PHONE_INVALID%>;
		}
		if ($('isInvalidWorkPhone').src.indexOf(selectedCheckBox) != -1) {
			workPhoneIsInvalid = <%=SelectionProcessConstants.PHONE_INVALID%>;
		}
		if ($('isInvalidCellPhone').src.indexOf(selectedCheckBox) != -1) {
			cellPhoneIsInvalid = <%=SelectionProcessConstants.PHONE_INVALID%>;
		}
		//send HTTP request to update DB
		var pars = "mode=updatePhones&applicantId=<bean:write property="applicantId" name="selectionProcessForm"/>&applicantHomePhone="+ homePhone +"&applicantHomePhoneIsInvalid=" + homePhoneIsInvalid+ "&applicantWorkPhone="+ workPhone +"&applicantWorkPhoneIsInvalid=" + workPhoneIsInvalid  + "&applicantCellPhone="+cellPhone +"&applicantCellPhoneIsInvalid=" + cellPhoneIsInvalid;
		var myAjax = ajaxCall("selectionProcess.do","post",pars,updatePhonesList,reportError);			
	}
	
	function hideNewPhoneDiv(){
		$('divNewPhone_').style.display='none';
		selectBox.setSelected(selectBox.getIndexWithId(''));
	}
	
	function updatePhonesList(request){
        xmlFile = request.responseXML;
        if(!isValidSession(xmlFile,'<%=TPApplicationProperties.getProperty("redirect_on_session_expired")%>')) {
          return;
        }
	    var homePhone=document.getElementById('applicantHomePhone').value;
		var workPhone=document.getElementById('applicantWorkPhone').value;
		var cellPhone=document.getElementById('applicantCellPhone').value;
	    var opts = new Array();
		opts[opts.length] = new SelectOption('','');
		var ph=homePhone.trim();
		if(ph!="" && $('isInvalidHomePhone').src.indexOf(deselectedCheckBox) != -1){
			opts[opts.length] = new SelectOption(ph, ph);	
		}
		var ph=workPhone.trim();
		if(ph!="" && $('isInvalidWorkPhone').src.indexOf(deselectedCheckBox) != -1){
			opts[opts.length] = new SelectOption(ph, ph);	
		}
		var ph=cellPhone.trim();
		if(ph!="" && $('isInvalidCellPhone').src.indexOf(deselectedCheckBox) != -1){
			opts[opts.length] = new SelectOption(ph, ph);	
		}
	    opts[opts.length] = new SelectOption('<bean:message key="add_phone.label.new_phone_val"/>', '<bean:message key="add_phone.label.new_phone_val"/>');
	    selectBox.reInitialize(opts, '<bean:message key="add_phone.label.new_phone_val"/>');	
		$('divNewPhone_').style.display='none';
		selectBox.setSelected(selectBox.getIndexWithId(''));
		isDataChanged=true;
	}
	
</logic:equal>

function setPopupTitle(){
	var title = '<b><bean:message key="add_phone.label.phone"/> - </b>';
    <logic:equal value='<%=""+SelectionProcessConstants.INTERACTION_NOTE %>' property="communicationType" name="selectionProcessForm">
      title = '<b><bean:message key="add_note.label.note"/></b>';
    </logic:equal>
    <logic:equal value='<%=""+SelectionProcessConstants.INTERACTION_PHONE %>' property="communicationType" name="selectionProcessForm">
	title += '<bean:write name="applicantData" property="applicantName"/> &nbsp';
	<% if (ImportConfigurationManager.isApplicantFieldViewable(ImportConfigurationConstants.FIELD_SOURCE ,((PermissionSet)request.getSession(false).getAttribute("permissionSet")).isSHOW_CONFIDENTIAL_DATA()) ){%>
		title += '<bean:message key="common.openingRoundBracket"/><bean:message key="common.source"/>:&nbsp;<bean:write name="applicantData" property="applicantSourceTitle"/><bean:message key="common.closingRoundBracket"/>';
	<%} %>
	</logic:equal>
	window.top.setPopTitle(title);
}

window.onload = setPopupTitle;
</script>