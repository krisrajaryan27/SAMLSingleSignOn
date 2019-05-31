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
<html:hidden property="mode" value="publishPositionToSite"/>
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
			<div class="popupTop">
				<logic:equal name="positionForm" property="isPublishedToSite" value="<%=PositionConstants.POSITION_PUBLISHED_TO_WEBSITE%>">
					<img src="images/checkedradiobutton.gif" name='imgPublishCategory' id='img_published_<%=PositionConstants.POSITION_PUBLISHED_TO_WEBSITE%>' onclick="javascript:onDisplayHideRadioChange('imgPublishCategory','<%=PositionConstants.POSITION_PUBLISHED_TO_WEBSITE%>');" style="margin-bottom:-1px;"/>
					<bean:message key="rule.label.publish_to_website"/>&nbsp;
					<img src="images/radiobutton.gif" name='imgPublishCategory' id='img_published_<%=PositionConstants.POSITION_NOT_PUBLISHED_TO_WEBSITE%>' onclick="javascript:onDisplayHideRadioChange('imgPublishCategory','<%=PositionConstants.POSITION_NOT_PUBLISHED_TO_WEBSITE%>');" style="margin-bottom:-1px;"/>
					<bean:message key="rule.label.remove_from_website"/>	
				</logic:equal>
				<logic:equal name="positionForm" property="isPublishedToSite" value="<%=PositionConstants.POSITION_NOT_PUBLISHED_TO_WEBSITE%>">
					<img src="images/radiobutton.gif" name='imgPublishCategory' id='img_published_<%=PositionConstants.POSITION_PUBLISHED_TO_WEBSITE%>' onclick="javascript:onDisplayHideRadioChange('imgPublishCategory','<%=PositionConstants.POSITION_PUBLISHED_TO_WEBSITE%>');" style="margin-bottom:-1px;"/>
					<bean:message key="rule.label.publish_to_website"/>
					<img src="images/checkedradiobutton.gif" name='imgPublishCategory' id='img_published_<%=PositionConstants.POSITION_NOT_PUBLISHED_TO_WEBSITE%>' onclick="javascript:onDisplayHideRadioChange('imgPublishCategory','<%=PositionConstants.POSITION_NOT_PUBLISHED_TO_WEBSITE%>');" style="margin-bottom:-1px;"/>
					<bean:message key="rule.label.remove_from_website"/>	
				</logic:equal>
				<br></br>
				<div id="ruleDiv" style="display:none;">
						<table>
							<tr>
								<td >
									<bean:message key="publish_position.label.publishFrom" />
									&nbsp;<html:text name="positionForm" styleId="publishFrom" 
										property="publishFrom" maxlength="15" size="13" styleClass="Grey" 
											onblur="getFDate(this,'DD/MM/YYYY');"  />&nbsp;
									<img src="images/ico_cal.gif" style="height:16px;margin-bottom:-3px;cursor:hand;" 
									onclick="popUpCal.select(document.getElementById('publishFrom'),'publishFrom','dd/MM/yyyy'); return false;" />
									&nbsp;&nbsp;
									<bean:message key="publish_position.label.publishTo" />
									&nbsp;&nbsp;
									<html:text name="positionForm" styleId="publishTo" 
										property="publishTo" maxlength="15"  size="13" styleClass="Grey" 
											onblur="getFDate(this,'DD/MM/YYYY');"  />&nbsp;
									<img src="images/ico_cal.gif" style="height:16px;margin-bottom:-3px;cursor:hand;" 
									onclick="popUpCal.select(document.getElementById('publishTo'),'publishTo','dd/MM/yyyy'); return false;" />
								</td>
							</tr>
						</table>
						<br></br>
					    <logic:equal name="positionForm" property="ruleType" value="<%=PositionConstants.RULE_GO_TO_DATABASE%>">
							<img src="images/checkedradiobutton.gif" name='imgCategory' id='img_<%=PositionConstants.RULE_GO_TO_DATABASE%>' onclick="javascript:onRadioChange('imgCategory','<%=PositionConstants.RULE_GO_TO_DATABASE%>');" style="margin-bottom:-1px;"/>
							<bean:message key="rule.go_to_database"/></br></br>
							<%-- <% if (!ModuleSet.isMODULE_RCHILLI_INTEGRATION()) { %>
							<img src="images/radiobutton.gif" name='imgCategory' id='img_<%=PositionConstants.RULE_GO_TO_INBOX%>' onclick="javascript:onRadioChange('imgCategory','<%=PositionConstants.RULE_GO_TO_INBOX%>');" style="margin-bottom:-1px;"/>
							<bean:message key="rule.go_to_inbox"/></br></br>
							<%
								}
							%> --%>
							<img src="images/radiobutton.gif" name='imgCategory' id='img_<%=PositionConstants.RULE_MANDATORY%>' onclick="javascript:onRadioChange('imgCategory','<%=PositionConstants.RULE_MANDATORY%>');" style="margin-bottom:-1px;"/>
							<bean:message key="rule.mandatory"/></br></br>
						</logic:equal>
						<% if (!ModuleSet.isMODULE_RCHILLI_INTEGRATION()) { %>
						<logic:equal name="positionForm" property="ruleType" value="<%=PositionConstants.RULE_GO_TO_INBOX%>">
							<img src="images/radiobutton.gif" name='imgCategory' id='img_<%=PositionConstants.RULE_GO_TO_DATABASE%>' onclick="javascript:onRadioChange('imgCategory','<%=PositionConstants.RULE_GO_TO_DATABASE%>');" style="margin-bottom:-1px;"/>
							<bean:message key="rule.go_to_database"/></br></br>
							<%-- <img src="images/checkedradiobutton.gif" name='imgCategory' id='img_<%=PositionConstants.RULE_GO_TO_INBOX%>' onclick="javascript:onRadioChange('imgCategory','<%=PositionConstants.RULE_GO_TO_INBOX%>');" style="margin-bottom:-1px;"/>
							<bean:message key="rule.go_to_inbox"/></br></br> --%>
							<img src="images/radiobutton.gif" name='imgCategory' id='img_<%=PositionConstants.RULE_MANDATORY%>' onclick="javascript:onRadioChange('imgCategory','<%=PositionConstants.RULE_MANDATORY%>');" style="margin-bottom:-1px;"/>
							<bean:message key="rule.mandatory"/></br></br>
						</logic:equal>
						<%
								}
							%>
						<logic:equal name="positionForm" property="ruleType" value="<%=PositionConstants.RULE_MANDATORY%>">
							<img src="images/radiobutton.gif" name='imgCategory' id='img_<%=PositionConstants.RULE_GO_TO_DATABASE%>' onclick="javascript:onRadioChange('imgCategory','<%=PositionConstants.RULE_GO_TO_DATABASE%>');" style="margin-bottom:-1px;"/>
							<bean:message key="rule.go_to_database"/></br></br>
							<%-- <% if (!ModuleSet.isMODULE_RCHILLI_INTEGRATION()) { %>
							<img src="images/radiobutton.gif" name='imgCategory' id='img_<%=PositionConstants.RULE_GO_TO_INBOX%>' onclick="javascript:onRadioChange('imgCategory','<%=PositionConstants.RULE_GO_TO_INBOX%>');" style="margin-bottom:-1px;"/>
							<bean:message key="rule.go_to_inbox"/></br></br>
							<%
								}
							%> --%>
							<img src="images/checkedradiobutton.gif" name='imgCategory' id='img_<%=PositionConstants.RULE_MANDATORY%>' onclick="javascript:onRadioChange('imgCategory','<%=PositionConstants.RULE_MANDATORY%>');" style="margin-bottom:-1px;"/>
							<bean:message key="rule.mandatory"/></br></br>
						</logic:equal>
						<div id="mandatoryFieldsId" style="display:none;margin-left:25px;">
								<table>
								  <tr>
								  	<td>
								  		<logic:equal name="positionForm" property="expSelected" value="<%=PositionConstants.RULE_MANDATORY_CHK_BOX_NOT_SELECTED%>">
								  			<img src="images/checkboxunchecked.gif" name="expChk" id='img_chk_<%=PositionConstants.MANDATORY_EXPRIENCE%>' onclick="javascript:toggleSource(this);" style="margin-bottom:-1px;"/>
								  		</logic:equal>
								  		<logic:equal name="positionForm" property="expSelected" value="<%=PositionConstants.RULE_MANDATORY_CHK_BOX_SELECTED%>">
								  			<img src="images/checkboxchecked.gif" name="expChk" id='img_chk_<%=PositionConstants.MANDATORY_EXPRIENCE%>' onclick="javascript:toggleSource(this);" style="margin-bottom:-1px;"/>
								  		</logic:equal>
										<bean:message key="rule.label.experience"/>
								  	</td>
								  	<td>
								  		<html:text name="positionForm" property="minimumExperience" styleId="minimumExperience" size="5" />&nbsp;<bean:message key="position.requirements.experience.to" />&nbsp;<html:text name="positionForm" property="maximumExperience" styleId="maximumExperience" size="5" />&nbsp;
								  		<bean:message key="position.requirements.experience.years" />
								  	</td>
								  </tr>
								  <tr>
								  	<td>
									  	<logic:equal name="positionForm" property="degreeSelected" value="<%=PositionConstants.RULE_MANDATORY_CHK_BOX_NOT_SELECTED%>">
									  		<img src="images/checkboxunchecked.gif" name='eduChk' id='img_chk_<%=PositionConstants.MANDATORY_EDUCATION%>' onclick="javascript:toggleSource(this);" style="margin-bottom:-1px;"/>
									  	</logic:equal>
									  	<logic:equal name="positionForm" property="degreeSelected" value="<%=PositionConstants.RULE_MANDATORY_CHK_BOX_SELECTED%>">
									  		<img src="images/checkboxchecked.gif" name='eduChk' id='img_chk_<%=PositionConstants.MANDATORY_EDUCATION%>' onclick="javascript:toggleSource(this);" style="margin-bottom:-1px;"/>
									  	</logic:equal>
								  		<bean:message key="common.education"/>
								  	</td>
								  	<td>
								  		<script language="JavaScript">									
											var opts = <bean:write name="positionForm" property="jsArrayDegrees" filter="false"/>;											
											var opt = [new SelectOption('-1','<bean:message key="common.selectlist.default" />')];
											degrees = opt.concat(opts);
											selectBoxDegree = new SelectBox(degrees,'<bean:write name="positionForm" property="degreeId" />','images/btn_dropdown.gif',{namesonly:false, width:'180px', size:15});
											document.write(selectBoxDegree.getHtml());
											selectBoxDegree.init();
										</script>
								  	</td>
								  </tr>
								  <tr>
								  	<td>
								  		<logic:equal name="positionForm" property="branchSelected" value="<%=PositionConstants.RULE_MANDATORY_CHK_BOX_NOT_SELECTED%>">
								  			<img src="images/checkboxunchecked.gif" name='branchChk' id='img_chk_<%=PositionConstants.MANDATORY_BRANCH%>' onclick="javascript:toggleSource(this);" style="margin-bottom:-1px;"/>
								  		</logic:equal>
								  		<logic:equal name="positionForm" property="branchSelected" value="<%=PositionConstants.RULE_MANDATORY_CHK_BOX_SELECTED%>">
								  			<img src="images/checkboxchecked.gif" name='branchChk' id='img_chk_<%=PositionConstants.MANDATORY_BRANCH%>' onclick="javascript:toggleSource(this);" style="margin-bottom:-1px;"/>
								  		</logic:equal>
								  		<bean:message key="common.branch"/>
								  	</td>
								  	<td>
								  		<script language="JavaScript">									
											var opts = <bean:write name="positionForm" property="jsArrayBranches" filter="false"/>;											
											var opt = [new SelectOption('-1','<bean:message key="common.selectlist.any" />')];
											branches = opt.concat(opts);
											selectBoxBranch = new SelectBox(branches,'<bean:write name="positionForm" property="branchId" />','images/btn_dropdown.gif',{namesonly:false, width:'180px', size:15});
											document.write(selectBoxBranch.getHtml());
											selectBoxBranch.init();
										</script>
								  	</td>
								  </tr>
								  <tr>
								  	<td>
								  		<logic:equal name="positionForm" property="instituteSelected" value="<%=PositionConstants.RULE_MANDATORY_CHK_BOX_NOT_SELECTED%>">
								  			<img src="images/checkboxunchecked.gif" name='instituteChk' id='img_chk_<%=PositionConstants.MANDATORY_INSTITUTE%>' onclick="javascript:toggleSource(this);" style="margin-bottom:-1px;"/>
								  		</logic:equal>
								  		<logic:equal name="positionForm" property="instituteSelected" value="<%=PositionConstants.RULE_MANDATORY_CHK_BOX_SELECTED%>">
								  			<img src="images/checkboxchecked.gif" name='instituteChk' id='img_chk_<%=PositionConstants.MANDATORY_INSTITUTE%>' onclick="javascript:toggleSource(this);" style="margin-bottom:-1px;"/>
								  		</logic:equal>
								  		<bean:message key="common.institute"/>
								  	</td>
								  	<td>
								  		<html:text name="positionForm" property="institute" styleId="institute" size="50"/>
								  		<script	language="JavaScript">
											new Ajax.Autocompleter("institute", "autocomplete", "position.do?mode=getAutoCompleteList&autocompleteFieldType=<%=PositionConstants.AUTOCOMPLETE_INSTITUTE%>", {frequency: 0.001, tokens: [';']});
										</script>
								  	</td>
								  </tr>
								  <tr>
								  	<td>
								  		<logic:equal name="positionForm" property="currentLocationSelected" value="<%=PositionConstants.RULE_MANDATORY_CHK_BOX_NOT_SELECTED%>">
								  			<img src="images/checkboxunchecked.gif" name='locationChk' id='img_chk_<%=PositionConstants.MANDATORY_CURRENT_LOCATION%>' onclick="javascript:toggleSource(this);" style="margin-bottom:-1px;"/>
								  		</logic:equal>
								  		<logic:equal name="positionForm" property="currentLocationSelected" value="<%=PositionConstants.RULE_MANDATORY_CHK_BOX_SELECTED%>">
								  			<img src="images/checkboxchecked.gif" name='locationChk' id='img_chk_<%=PositionConstants.MANDATORY_CURRENT_LOCATION%>' onclick="javascript:toggleSource(this);" style="margin-bottom:-1px;"/>
								  		</logic:equal>
								  		<bean:message key="common.current_location"/>
								  	</td>
								  	<td>
								  		<html:text name="positionForm" property="currentLocation" styleId="currentLocation" size="50"/>
								  	</td>
								  </tr>
								</table>
							</div>
					</div>
				</div>
			<div class="popupBody">
				<table  border="0" cellspacing="0" cellpadding="0" width="100%">		
					<tr>
						<td>
							<div class="navBtn" style="margin-top:5px;float:right;">
								<a href="#" style="width:90px;" class="active" onclick="javascript:onSubmit();"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.submit"/></a>
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
	var source = "";

	source = document.getElementById('img_published_<%=PositionConstants.POSITION_PUBLISHED_TO_WEBSITE%>').src; 
	showDiv('ruleDiv',source);

	source = document.getElementById('img_<%=PositionConstants.RULE_MANDATORY%>').src;
	showDiv('mandatoryFieldsId',source); 
}

function showDiv(divId,source){
	if(source.indexOf(chkedRadBtn)!=-1){
		document.getElementById(divId).style.display="block";
	}
}


function setPopupTitle(){
	var title = '<b>' + '<bean:message key="common.publish" /> <bean:message key="common.position" /> to Site' + ' - ' + '<bean:write name="positionTitle" scope="request" />' + '</b>';	
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
	var publishFrom = document.getElementsByName('publishFrom')[0].value;
	var publishTo = document.getElementsByName('publishTo')[0].value;
	var flagDt = validateDateRange(publishFrom,publishTo);
	if(flagDt==0){
		alert("From date cannot be greater than to date");
		return false;
	}else if(flagDt == -1){
		alert("invalid dates entered!");
		return false;
	}
	var source = document.getElementById('img_published_<%=PositionConstants.POSITION_PUBLISHED_TO_WEBSITE%>').src;
	if(source.indexOf(chkedRadBtn)!= -1) {
		errors = validateData();
		if (errors.length > 0) {
			alert(errors);
			return false;
		}
		document.positionForm.degreeId.value=selectBoxDegree.getSelectedId();
		document.positionForm.branchId.value=selectBoxBranch.getSelectedId();
	}
	document.positionForm.submit();
}

function validateData(){
	errors='';
	var isExpSelected = $('img_chk_0').src;
	var isEduSelected = $('img_chk_1').src;
	var isBranchSelected = $('img_chk_2').src;
	var isInstSelected = $('img_chk_3').src;
	var isCurrentLocationSelected = $('img_chk_4').src;
	
	var degreeId = selectBoxDegree.getSelectedId();
	var branchId = selectBoxBranch.getSelectedId();
	var minimumExperience = $('minimumExperience').value;
	var maximumExperience = $('maximumExperience').value;	

	var source = document.getElementById('img_<%=PositionConstants.RULE_MANDATORY%>').src;
	
	if(source.indexOf(chkedRadBtn)!=-1 && isExpSelected.indexOf(chkedChkBox)==-1 && isEduSelected.indexOf(chkedChkBox)==-1 && isBranchSelected.indexOf(chkedChkBox)==-1
			&& isInstSelected.indexOf(chkedChkBox)==-1 && isCurrentLocationSelected.indexOf(chkedChkBox)==-1 ){
		errors = addError(errors, 'Please select at least one mandatory condition.');
	}else if(source.indexOf(chkedRadBtn)!=-1){
		if (isExpSelected.indexOf(chkedChkBox)!=-1 && minimumExperience=='') {
			errors = addError(errors, '- <bean:message key="position.requirements.minimum_experience" />');
		}
		if (isExpSelected.indexOf(chkedChkBox)!=-1 && maximumExperience=='') {
			errors = addError(errors, '- <bean:message key="position.requirements.maximum_experience" />');
		}
		if(isExpSelected.indexOf(chkedChkBox)!=-1 && isNaN(minimumExperience)){									
			errors = addError(errors, '- <bean:message key="common.please_enter_valid_number" /> in <bean:message key="position.requirements.minimum_experience" />');
		}
		if(isExpSelected.indexOf(chkedChkBox)!=-1 && isNaN(maximumExperience)){									
			errors = addError(errors, '- <bean:message key="common.please_enter_valid_number" /> in <bean:message key="position.requirements.maximum_experience" />');
		}
		
		if (isEduSelected.indexOf(chkedChkBox)!=-1 && degreeId==-1) {
			errors = addError(errors, '- <bean:message key="common.education" />');
		}
		if(isBranchSelected.indexOf(chkedChkBox)!=-1 && branchId==-1) {
			errors = addError(errors, '- <bean:message key="common.branch" />');
		}

		if(isInstSelected.indexOf(chkedChkBox)!=-1 && $('institute').value==''){
			errors = addError(errors, '- <bean:message key="common.institute" />');
		}
		if(isCurrentLocationSelected.indexOf(chkedChkBox)!=-1 && $('currentLocation').value==''){
			errors = addError(errors, '- <bean:message key="common.current_location" />');
		}
		if (errors.length > 0) {
			errors = addError('<bean:message key="common.data_required" />', errors);
		}
	}
	
	return errors;
	
}

function getFDate(obj,format){
	dtfo.setDisplayFormat(format);
	if(obj.value.trim()!=''){
	if(!dtfo.checkDate(obj)){
		obj.select();
		alert("Please enter date in " + format + " format");
		obj.focus();
		return false;
	}else {
		return true;
	}
	}
	return true;
}

</script>