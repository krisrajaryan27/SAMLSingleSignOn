<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ page import="org.apache.struts.Globals"%>
<%@ page import="com.talentPool.positions.PositionConstants,com.talentPool.positions.dataobject.PositionData,
                  com.talentPool.common.NavigationConstants,com.talentPool.positions.form.PositionForm,
                  com.talentPool.common.properties.TPApplicationProperties"%>
<%@page import="com.talentPool.user.manager.ModuleSet"%>
<script language="JavaScript" src="js/scripta/lib/prototype.js"	type="text/javascript"></script>
<script language="JavaScript" src="js/scripta/src/effects.js" type="text/javascript"></script>
<script language="JavaScript" src="js/ajaxfunctions.js" type="text/javascript"></script>

<logic:present name="update" scope="request">	
	<script>
		window.top.hidePopWin(true);
	</script>
</logic:present>
<%
PositionData positionData = (PositionData) request.getAttribute("positionData");
%>

<div class="contentDivPop" style="width: 300px;">
<div class="outerDiv">
<html:form action="/position">
	<html:hidden property="mode" value="updatePositionPriority" />		
	<html:hidden property="positionId" />
	<html:hidden property="positionPriority"/>
	
	<div class="popupTop">
	<table class="tblPop">		
	<tr>
		<td style="text-align: left; vertical-align: top;" class="header"><bean:message key="common.position" /> <bean:message key="common.priority" /> 
		</td>
	</tr>		
		<tr>
		<td style="text-align: left; vertical-align: top;"></td>
	</tr>
	<tr>
		<td class="header" style="text-align: left;">
			 <img 
			  <logic:equal value="<%=PositionConstants.POSITION_LEVEL_HIGH %>" property="positionPriority" name="positionForm">
				  src="images/checkedradiobutton.gif" 
			  </logic:equal>
			  <logic:notEqual value="<%=PositionConstants.POSITION_LEVEL_HIGH %>" property="positionPriority" name="positionForm">
			    src="images/radiobutton.gif" 
			  </logic:notEqual>
			    		name="rdoPriority"
						id='img_<%=PositionConstants.POSITION_LEVEL_HIGH %>'
						onclick="onChangePositionPriority('rdoPriority','<%=PositionConstants.POSITION_LEVEL_HIGH %>');"/>&nbsp;<bean:message key="position.priority.high" />&nbsp;
			  <img 
			  <logic:equal value="<%=PositionConstants.POSITION_LEVEL_MEDIUM%>" property="positionPriority" name="positionForm">
				  src="images/checkedradiobutton.gif" 
			  </logic:equal>
			  <logic:notEqual value="<%=PositionConstants.POSITION_LEVEL_MEDIUM%>" property="positionPriority" name="positionForm">
			    src="images/radiobutton.gif" 
			  </logic:notEqual>
			    		name="rdoPriority"
						id='img_<%=PositionConstants.POSITION_LEVEL_MEDIUM %>'
						onclick="onChangePositionPriority('rdoPriority','<%=PositionConstants.POSITION_LEVEL_MEDIUM %>');"/>&nbsp;<bean:message key="position.priority.medium" />
			  <img 
			  <logic:equal value="<%=PositionConstants.POSITION_LEVEL_LOW%>" property="positionPriority" name="positionForm">
				  src="images/checkedradiobutton.gif" 
			  </logic:equal>
			  <logic:notEqual value="<%=PositionConstants.POSITION_LEVEL_LOW%>" property="positionPriority" name="positionForm">
			    src="images/radiobutton.gif" 
			  </logic:notEqual>
			    		name="rdoPriority"
						id='img_<%=PositionConstants.POSITION_LEVEL_LOW %>'
						onclick="onChangePositionPriority('rdoPriority','<%=PositionConstants.POSITION_LEVEL_LOW %>');"/>&nbsp;<bean:message key="position.priority.low" />			
		</td>			   	    
   	</tr> 
   	</table>
	</div>
	<div class="popupBody">	
	<table class="tblPop" cellspacing="0" cellpadding="0" border="0" width="100%">
	<tr>
		<td>
			<div class="navBtn"	style="float:right;margin-left:5px;margin-top:5px;">
			<a href="#" style="width:50px;" class="active" onclick="submitForm();">
			<span class="rightC"></span><span class="leftC"></span><bean:message key="common.save" /></a>					
			<a href="#" style="width:60px;margin-left:5px;" class="active" onclick="javascript:window.top.hidePopWin(false);">
			<span class="rightC"></span><span class="leftC"></span><bean:message key="common.cancel"/></a>
			</div>
		</td>
	</tr>
	</table>
	</div>
</html:form>
</div>
</div>
<script language="JavaScript">

selectedRadioButton="images/checkedradiobutton.gif";
deselectedRadioButton="images/radiobutton.gif";

function submitForm(){		
	document.positionForm.submit();
} 
window.onload = onOnPopUpLoad;

function onOnPopUpLoad() {
	setPopupTitle();
}
function setPopupTitle(){
	var popupTitle = '<b><%=positionData.getPositionTitle().replace("'","\\'")%></b>';
	window.top.setPopTitle(popupTitle);
}

function onChangePositionPriority(imgGroupName, attachmentId){
	var selectedId = onRadioChange(imgGroupName, attachmentId); 
	document.positionForm.positionPriority.value=selectedId;
}
function onRadioChange(imgGroupName, attachmentId){
	var imgs = document.getElementsByName(imgGroupName);
	var fId = -1;
	for (i = 0; i < imgs.length; i++) {
		var theImage = imgs[i];
		if (theImage.id.indexOf("img") > -1) {
			if( theImage.id == 'img_'+attachmentId){
				theImage.src = selectedRadioButton;
				fId= attachmentId;
			}else{
				theImage.src = deselectedRadioButton;
			}
		}
	}
	return fId;
}

</script>
