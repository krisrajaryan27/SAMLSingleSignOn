<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@page import="com.talentPool.positions.PositionConstants"%>
<%@page import="org.apache.struts.Globals"%>
<%@page import="com.talentPool.common.properties.GlobalApplicationProperties"%>
<%@page import="com.talentPool.common.properties.GlobalConstants"%>

<link rel="stylesheet" type="text/css" href="themes/default/CalendarPopup.css"/>
<script language="JavaScript" src="js/scripta/lib/prototype.js" type="text/javascript"></script>
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
<html:hidden property="mode" value="publishPositionToEmployeeOrWalkinPortal"/>
<html:hidden property="t" name="positionForm"/>
<html:hidden property="positionId" name="positionForm" />
<html:hidden property="publishType" name="positionForm"/>
<html:hidden property="employeeApplyRefer" name="positionForm"/>
<html:hidden property="employeeCanEmail" name="positionForm"/>
<input type="hidden" name="isSubmitted" value="1"/>
<input type="hidden" name="publishOrUnPublish" value="<bean:write name="publishOrUnPublish" scope="request" />"/>
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
	<div class="outerDiv" style="width: 100%;">
		<div class="popupTop">
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
			<table border="0" cellspacing="0" cellpadding="0" class="posinput" width="460px">
				<tr>
					<td>
						<img src="images/checkedradiobutton.gif" name='imgPublish' 
						id='img_published_<%=PositionConstants.PUBLISHED%>'
						 onclick="javascript:onRadioChange(this,'<%=PositionConstants.PUBLISHED%>');" 
						 style="margin-bottom:-1px;"/>
						 <bean:message key="position.publish.label.publish"/>&nbsp;
						 <img src="images/radiobutton.gif" name='imgPublish' 
							id='img_published_<%=PositionConstants.UNPUBLISHED%>'
							 onclick="javascript:onRadioChange(this,'<%=PositionConstants.UNPUBLISHED%>');" 
						 	style="margin-bottom:-1px;"/>
						 <bean:message key="position.publish.label.unpublish"/>&nbsp;
					</td>
				</tr>
				<tr>
					<td>
						<div id="publishDate" style="display: block;">
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
						</div>
					</td>
				</tr>
				<tr>
					<td>
						<div id="publishCriteria" style="display: block;">
						<table>
						<tr>
							<td>								
								<img src="images/radiobutton.gif" name='imgEmployeeApplyRefer' 
								id='img_EmployeeApplyRefer_<%=PositionConstants.POSITIONS_EMPLOYEE_APPLY%>'
						 		onclick="javascript:onRadioChange(this,'<%=PositionConstants.POSITIONS_EMPLOYEE_APPLY%>');" 
						 		style="margin-bottom:-1px;"/>&nbsp;
								<bean:message key="publish_position.employee.apply"/>&nbsp;
							</td>
						</tr>
						<tr>		
							<td>								
								<img src="images/radiobutton.gif" name='imgEmployeeApplyRefer' 
								id='img_EmployeeApplyRefer_<%=PositionConstants.POSITIONS_EMPLOYEE_REFER%>'
						 		onclick="javascript:onRadioChange(this,'<%=PositionConstants.POSITIONS_EMPLOYEE_REFER%>');" 
						 		style="margin-bottom:-1px;"/>&nbsp;						 								 		
								<bean:message key="publish_position.employee.refer"/>&nbsp;
							</td>
						</tr>
						<tr>	
							<td>								
								<img src="images/checkedradiobutton.gif" name='imgEmployeeApplyRefer' 
								id='img_EmployeeApplyRefer_<%=PositionConstants.POSITIONS_EMPLOYEE_APPLY_REFER%>'
						 		onclick="javascript:onRadioChange(this,'<%=PositionConstants.POSITIONS_EMPLOYEE_APPLY_REFER%>');" 
						 		style="margin-bottom:-1px;"/>&nbsp;
								<bean:message key="publish_position.employee.apply_refer"/> &nbsp;
							</td>
						</tr>
						
						<tr>
						
							<td>
							<div id="mandatoryFieldsId" style="display:block;margin-left:25px;">
								<img src="images/checkboxunchecked.gif" name='imgEmployeeEmail' 
								id='imgEmployeeEmail' 
								onclick="javascript: changeCheckboxState(this);"/>
								<bean:message key="publish_position.employee.email"/> &nbsp;
								</div>	
							</td>		
									
						</tr>
					
						</table>
						</div>
					</td>
				</tr>
			</table>	
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
window.onload=doOnLoad;
var chkedRadBtn = 'images/checkedradiobutton.gif';
var unChkedRadBtn = 'images/radiobutton.gif';

function doOnLoad() {
	setPopupTitle();
	var publishedOrUnpublished  = '<bean:write name="publishOrUnPublish" scope="request" />';
	var publishType = document.positionForm.publishType.value;	
	var empApplyRefer = document.positionForm.employeeApplyRefer.value;
	var empCanEmail = document.positionForm.employeeCanEmail.value;
	if(publishedOrUnpublished==<%=PositionConstants.PUBLISHED%>){
		document.getElementById("img_published_"+<%=PositionConstants.PUBLISHED%>).src = 'images/checkedradiobutton.gif'; 
		document.getElementById("img_published_"+<%=PositionConstants.UNPUBLISHED%>).src = 'images/radiobutton.gif';
		document.getElementById("publishDate").style.display='block';
		document.getElementById('mandatoryFieldsId').style.display='block';
		if(publishType=='<%=PositionConstants.PUBLISH_EMPLOYEE_PORTAL%>'){
				if(<%=(GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_SEND_MAIL_TO_FRIEND_FOR_OPENING)).equals("1")%>){	
				document.getElementById("publishCriteria").style.display='block';
				if(empApplyRefer==<%=PositionConstants.POSITIONS_EMPLOYEE_APPLY_REFER%>){
					document.getElementById("img_EmployeeApplyRefer_"+<%=PositionConstants.POSITIONS_EMPLOYEE_APPLY_REFER%>).src = 'images/checkedradiobutton.gif'; 
					document.getElementById("img_EmployeeApplyRefer_"+<%=PositionConstants.POSITIONS_EMPLOYEE_APPLY%>).src = 'images/radiobutton.gif';
					document.getElementById("img_EmployeeApplyRefer_"+<%=PositionConstants.POSITIONS_EMPLOYEE_REFER%>).src = 'images/radiobutton.gif';
				}else if(empApplyRefer==<%=PositionConstants.POSITIONS_EMPLOYEE_APPLY%>){
					document.getElementById("img_EmployeeApplyRefer_"+<%=PositionConstants.POSITIONS_EMPLOYEE_APPLY_REFER%>).src = 'images/radiobutton.gif'; 
					document.getElementById("img_EmployeeApplyRefer_"+<%=PositionConstants.POSITIONS_EMPLOYEE_APPLY%>).src = 'images/checkedradiobutton.gif';
					document.getElementById("img_EmployeeApplyRefer_"+<%=PositionConstants.POSITIONS_EMPLOYEE_REFER%>).src = 'images/radiobutton.gif';			
				}else if(empApplyRefer==<%=PositionConstants.POSITIONS_EMPLOYEE_REFER%>){
					document.getElementById("img_EmployeeApplyRefer_"+<%=PositionConstants.POSITIONS_EMPLOYEE_APPLY_REFER%>).src = 'images/radiobutton.gif'; 
					document.getElementById("img_EmployeeApplyRefer_"+<%=PositionConstants.POSITIONS_EMPLOYEE_APPLY%>).src = 'images/radiobutton.gif';
					document.getElementById("img_EmployeeApplyRefer_"+<%=PositionConstants.POSITIONS_EMPLOYEE_REFER%>).src = 'images/checkedradiobutton.gif';
				}
			}else{			
				document.getElementById("publishCriteria").style.display='none';
			}
			if(empCanEmail==<%=GlobalConstants.ENABLED%>){
				document.getElementById("imgEmployeeEmail").src='images/checkboxchecked.gif';
			}
		}else{			
			document.getElementById("publishCriteria").style.display='none';
		}		
	}else if(publishedOrUnpublished==<%=PositionConstants.UNPUBLISHED%>){
		document.getElementById("img_published_"+<%=PositionConstants.PUBLISHED%>).src = 'images/radiobutton.gif'; 
		document.getElementById("img_published_"+<%=PositionConstants.UNPUBLISHED%>).src = 'images/checkedradiobutton.gif';
		document.getElementById("publishDate").style.display='none';
		document.getElementById('mandatoryFieldsId').style.display='none';
		document.getElementById("publishCriteria").style.display='none';
		document.getElementById("img_EmployeeApplyRefer_"+<%=PositionConstants.POSITIONS_EMPLOYEE_APPLY_REFER%>).src = 'images/checkedradiobutton.gif'; 
		document.getElementById("img_EmployeeApplyRefer_"+<%=PositionConstants.POSITIONS_EMPLOYEE_APPLY%>).src = 'images/radiobutton.gif';
		document.getElementById("img_EmployeeApplyRefer_"+<%=PositionConstants.POSITIONS_EMPLOYEE_REFER%>).src = 'images/radiobutton.gif';		
	}
}
function onRadioChange(obj,isPublish){
	var imgs = document.getElementsByName(obj.name);	
	var publishType = document.positionForm.publishType.value;	
	for (i = 0; i < imgs.length; i++) {
		var theImage = imgs[i];
		if (theImage.id.indexOf("img_published") > -1) {
			if( theImage.id == 'img_published_'+isPublish){
				document.positionForm.publishOrUnPublish.value=isPublish;
				theImage.src = "images/checkedradiobutton.gif";
			}else{
				theImage.src = "images/radiobutton.gif";
			}
		}
		
		if (theImage.id.indexOf("img_EmployeeApplyRefer") > -1) {						
			if( theImage.id == 'img_EmployeeApplyRefer_'+isPublish){
				theImage.src = "images/checkedradiobutton.gif";
				document.positionForm.employeeApplyRefer.value=isPublish;
			}else{
				theImage.src = "images/radiobutton.gif";
			}
		}
	}

	
	var source = document.getElementById('img_published_<%=PositionConstants.PUBLISHED%>').src;
	
	var displayStyle = document.getElementById('publishDate').style.display;
	//if((source.indexOf(chkedRadBtn)!= -1) && (displayStyle=="none")){
	if((source.indexOf(chkedRadBtn)!= -1)){
		document.getElementById('publishDate').style.display="block";
		if(publishType=='<%=PositionConstants.PUBLISH_EMPLOYEE_PORTAL%>'){
			if(<%=(GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_SEND_MAIL_TO_FRIEND_FOR_OPENING)).equals("1")%>){
				document.getElementById("publishCriteria").style.display='block';
			}else{
				document.getElementById("publishCriteria").style.display='none';
			}
		}else{
			document.getElementById("publishCriteria").style.display='none';
		}
	}else if(displayStyle=="block"){
		document.getElementById('publishDate').style.display="none";
		document.getElementById("publishCriteria").style.display='none';
	}	
	var employeeApply = document.getElementById('img_EmployeeApplyRefer_<%=PositionConstants.POSITIONS_EMPLOYEE_APPLY%>').src;
	var displayEmail = document.getElementById('mandatoryFieldsId').style.display;
	if((employeeApply.indexOf(chkedRadBtn)!= -1) ){
		document.getElementById('mandatoryFieldsId').style.display="none";
	}else if(displayEmail=="none"){
		document.getElementById('mandatoryFieldsId').style.display="block";
	}
}
	
function setPopupTitle(){
	var publishType = document.positionForm.publishType.value;
	if (publishType == ''){
		publishType = <%= request.getParameter("publishType")%>
	}
	var title = '<b>' + '<bean:message key="common.publish" /> '+ '<bean:message key="common.position" /> ';
	if(publishType=='<%=PositionConstants.PUBLISH_WALK_IN%>'){
		title+='<bean:message key="publish_position.label.publish_position_to_walkin" /> ';
	}else if(publishType==<%=PositionConstants.PUBLISH_SOCIAL_MEDIA%>){
		title+='<bean:message key="position.home.title.publish_socail_media" /> ';
	}else {
		title+='<bean:message key="publish_position.label.publish_position_to_employee" /> ';
	} 
	title+=' - ' + '<bean:write name="positionTitle" scope="request" />' + '</b>';	
	window.top.setPopTitle(title);
}
function onSubmit(){
	document.positionForm.submit();
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

function changeCheckboxState(chkBox){
	if(chkBox.src.indexOf('checkboxchecked.gif') == -1){
		chkBox.src='images/checkboxchecked.gif';
		document.positionForm.employeeCanEmail.value = <%=GlobalConstants.ENABLED%>;
	}else{
		chkBox.src='images/checkboxunchecked.gif';
		document.positionForm.employeeCanEmail.value = <%=GlobalConstants.DISABLED%>;
	}
}
</script>