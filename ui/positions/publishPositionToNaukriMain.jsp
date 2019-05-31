<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@page import="com.talentPool.positions.PositionConstants"%>
<%@page import="org.apache.struts.Globals"%>
<%@page import="com.talentPool.user.manager.ModuleSet"%>
<link rel="stylesheet" type="text/css" href="themes/default/autoComplete.css">
<link rel="stylesheet" type="text/css" href="themes/default/CalendarPopup.css"/>
<script language="JavaScript" src="js/scripta/lib/prototype.js" type="text/javascript"></script>								
<script language="JavaScript" src="js/scripta/src/effects.js" type="text/javascript"></script>
<script language="JavaScript" src="js/selectoption.js" type="text/javascript"></script>
<script language="JavaScript" src="js/selectbox/selectbox.js" type="text/javascript"></script>	
<script language="JavaScript" src="js/commonFunctions.js" type="text/javascript"></script>
<script language="JavaScript" src="js/ajaxfunctions.js" type="text/javascript"></script>
<script language="JavaScript" src="js/scripta/lib/prototype.js" type="text/javascript"></script>
<script language="JavaScript" src="js/scripta/src/effects.js" type="text/javascript"></script>
<script language="JavaScript" src="js/scripta/src/controls.js" type="text/javascript"></script>
<script language="JavaScript" src="js/calender/CalendarPopup.js"></script>		
<script language="JavaScript" src="js/calender/dateFormatter.js" type="text/javascript"></script>
<logic:present name="saved" scope="request">
	<script>
		window.top.hidePopWin(true);
	</script>
</logic:present>
<DIV id="calDiv" style="position:absolute;background:#FFFFFF;z-index:1000;" ></DIV>
<script language="JavaScript">
	var popUpCal = new CalendarPopup("calDiv");
	var dtfo = new DateFormatter();
	popUpCal.showNavigationDropdowns();
</script>
<html:form action="/position">
<html:hidden property="mode" value="publishPositionToNaukri"/>
<html:hidden property="t" name="positionForm"/>
<html:hidden property="positionId" name="positionForm"/>
<html:hidden property="ruleType" name="positionForm"/>
<html:hidden property="degreeId" name="positionForm"/>			
<html:hidden property="branchId" name="positionForm"/>
<html:hidden property="isPublishedToSite" name="positionForm"/>
<html:hidden property="expSelected" name="positionForm"/>
<html:hidden property="degreeSelected" name="positionForm"/>
<html:hidden property="branchSelected" name="positionForm"/>
<html:hidden property="instituteSelected" name="positionForm"/>
<html:hidden property="currentLocationSelected" name="positionForm"/>
<input type="hidden" name="isSubmitted" value="1"/>
<div id="autocomplete" class="autocomplete"></div>
<div class="contentDivPop">
	<div id="divError" style="display:block">
		<% 
			if(request.getAttribute(Globals.ERROR_KEY)!=null){
		%>
		<script>
			var isError=1;
		</script>
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
		<br/>
		<% } %>
		<% 
			if(request.getAttribute("errorMsg")!=null){
		%>
		<script>
			var isError=1;
		</script>
		<table  id="m_errortable" > 
			<tr>
			  <td class="header">
			    <b><bean:message key="errors.following_errors"/></b>
			  </td>               
			</tr>
			<tr>
			    <td class="message"><%= (String)request.getAttribute("errorMsg")%></td>               
			</tr>
		</table>
		<br/>
		<% } %>
	</div>
	<div class="outerDiv" style="overflow:auto;width: 500px;">
		<div id="popUpHeader" class="popupTop">
				<table class="tblPop" >
					<tr>
						<td class="header">
							<bean:message key="common.position" />:
						</td>
						<td>
							&nbsp;<bean:write name="positionTitle"  scope="request"/>
						</td>
					</tr>
					<tr>
						<td class="header">
							<bean:message key="position.description.hire_by_date" />:
						</td>
						<td>
							&nbsp;<bean:write name="positionHireByDate"  scope="request"/>
						</td>
					</tr>
					<tr>
						<td class="header">
							<bean:message key="common.vacancies" />:
						</td>
						<td>
							&nbsp;<bean:write name="vacancies"  scope="request"/>
						</td>
					</tr>
				</table>
			</div>	
			<div class="popupBody">
				<table  border="0" cellspacing="0" cellpadding="0" width="100%">		
					<tr>
						<td>
							<div class="navBtn" style="margin-top:5px;float:right;">
								<a href="#" style="width:90px;" class="active" onclick="javascript:onSubmit();"><span class="rightC"></span><span class="leftC"></span>
								<%if("1".equals((String)request.getAttribute("isPublishedToNaukri"))){ %>
									UnPublish
								<%} else{%>
									Publish
								<%} %>
								</a>
								<a href="#" style="width:60px; margin-left:5px;" class="active" onclick="javascript: window.top.hidePopWin(false);return false;"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.cancel"/></a>
							</div>
						</td>
					</tr>			
				</table>
			</div>
	</div>
</div>
<br></br>
</html:form>
<script language="JavaScript">
var chkedRadBtn = 'images/checkedradiobutton.gif';
var unChkedRadBtn = 'images/radiobutton.gif';
var chkedChkBox = 'images/checkboxchecked.gif';
var unChkedChkBox = 'images/checkboxunchecked.gif';

function toggleSource(obj) {
	var source = obj.src;	
	var val = obj.id;
	if (source.indexOf(chkedChkBox) != -1) {
		obj.src = unChkedChkBox;
	} else {
		obj.src = chkedChkBox;
	}
	setChekBoxValues();
}

function setChekBoxValues(){
	var objSrc = document.getElementById('img_chk_<%=PositionConstants.MANDATORY_EXPRIENCE%>').src;
	if(objSrc.indexOf(chkedChkBox)!=-1){
		document.positionForm.expSelected.value="<%=PositionConstants.RULE_MANDATORY_CHK_BOX_SELECTED%>";
	}else{
		document.positionForm.expSelected.value="<%=PositionConstants.RULE_MANDATORY_CHK_BOX_NOT_SELECTED%>";
	}
	objSrc = document.getElementById('img_chk_<%=PositionConstants.MANDATORY_EDUCATION%>').src;
	if(objSrc.indexOf(chkedChkBox)!=-1){
		document.positionForm.degreeSelected.value="<%=PositionConstants.RULE_MANDATORY_CHK_BOX_SELECTED%>";
	}else{
		document.positionForm.degreeSelected.value="<%=PositionConstants.RULE_MANDATORY_CHK_BOX_NOT_SELECTED%>";
	}
	objSrc = document.getElementById('img_chk_<%=PositionConstants.MANDATORY_BRANCH%>').src;
	if(objSrc.indexOf(chkedChkBox)!=-1){
		document.positionForm.branchSelected.value="<%=PositionConstants.RULE_MANDATORY_CHK_BOX_SELECTED%>";
	}else{
		document.positionForm.branchSelected.value="<%=PositionConstants.RULE_MANDATORY_CHK_BOX_NOT_SELECTED%>";
	}
	objSrc = document.getElementById('img_chk_<%=PositionConstants.MANDATORY_INSTITUTE%>').src;
	if(objSrc.indexOf(chkedChkBox)!=-1){
		document.positionForm.instituteSelected.value="<%=PositionConstants.RULE_MANDATORY_CHK_BOX_SELECTED%>";
	}else{
		document.positionForm.instituteSelected.value="<%=PositionConstants.RULE_MANDATORY_CHK_BOX_NOT_SELECTED%>";
	}
	objSrc = document.getElementById('img_chk_<%=PositionConstants.MANDATORY_INSTITUTE%>').src;
	if(objSrc.indexOf(chkedChkBox)!=-1){
		document.positionForm.instituteSelected.value="<%=PositionConstants.RULE_MANDATORY_CHK_BOX_SELECTED%>";
	}else{
		document.positionForm.instituteSelected.value="<%=PositionConstants.RULE_MANDATORY_CHK_BOX_NOT_SELECTED%>";
	}
	objSrc = document.getElementById('img_chk_<%=PositionConstants.MANDATORY_CURRENT_LOCATION%>').src;
	if(objSrc.indexOf(chkedChkBox)!=-1){
		document.positionForm.currentLocationSelected.value="<%=PositionConstants.RULE_MANDATORY_CHK_BOX_SELECTED%>";
	}else{
		document.positionForm.currentLocationSelected.value="<%=PositionConstants.RULE_MANDATORY_CHK_BOX_NOT_SELECTED%>";
	}
}

window.onload=doOnLoad;
function doOnLoad() {
	setPopupTitle();
}

function showDiv(divId,source){
	if(source.indexOf(chkedRadBtn)!=-1){
		document.getElementById(divId).style.display="block";
	}
}


function setPopupTitle(){
	var title = '<b>' + '<bean:message key="common.publish" /> <bean:message key="common.position" /> to Naukri' + ' - ' + '<bean:write name="positionTitle" scope="request" />' + '</b>';	
	window.top.setPopTitle(title);
}

function onRadioChange(imgGroupName, attachmentId){
	var imgs = document.getElementsByName(imgGroupName);
	for (i = 0; i < imgs.length; i++) {
		var theImage = imgs[i];
		if (theImage.id.indexOf("img") > -1) {
			if( theImage.id == 'img_'+attachmentId){
				theImage.src = "images/checkedradiobutton.gif";
				document.positionForm.ruleType.value=attachmentId;
			}else{
				theImage.src = "images/radiobutton.gif";
			}
		}
	}

	var source = document.getElementById('img_<%=PositionConstants.RULE_MANDATORY%>').src;
	var displayStyle = document.getElementById('mandatoryFieldsId').style.display;
	if((source.indexOf(chkedRadBtn)!= -1) && (displayStyle=="none")){
		document.getElementById('mandatoryFieldsId').style.display="block";
	}else if(displayStyle=="block"){
		document.getElementById('mandatoryFieldsId').style.display="none";
	}
}

function onDisplayHideRadioChange(imgGroupName, isPublishToSite){
	var imgs = document.getElementsByName(imgGroupName);
	for (i = 0; i < imgs.length; i++) {
		var theImage = imgs[i];
		if (theImage.id.indexOf("img_published") > -1) {
			if( theImage.id == 'img_published_'+isPublishToSite){
				theImage.src = "images/checkedradiobutton.gif";
				document.positionForm.isPublishedToSite.value=isPublishToSite;
			}else{
				theImage.src = "images/radiobutton.gif";
			}
		}
	}

	var source = document.getElementById('img_published_<%=PositionConstants.POSITION_PUBLISHED_TO_WEBSITE%>').src;
	var displayStyle = document.getElementById('ruleDiv').style.display;
	if((source.indexOf(chkedRadBtn)!= -1) && (displayStyle=="none")){
		document.getElementById('ruleDiv').style.display="block";
	}else if(displayStyle=="block"){
		document.getElementById('ruleDiv').style.display="none";
	}
}

function getDateObject(str){
	var dt=null;
	if(str){
		if(str.length ==0){
			throw "cannot be blank";			
		}
		var parts = str.split("/");
		dt = new Date(parseInt(parts[2], 10),
		                  parseInt(parts[1], 10) - 1,
		                  parseInt(parts[0], 10));
	}
	return dt;
}

function validateDateRange(fromDate,toDate){
	try{
		var frmDt = getDateObject(fromDate);
		var toDt=getDateObject(toDate);
		if(toDt.getTime()>=frmDt.getTime()){
			return 1;
		}	
	}catch(err){
		return -1;
	}
	return 0;
}

function onSubmit(){
	//Validate data only if display is clicked
	document.positionForm.submit();
}


</script>