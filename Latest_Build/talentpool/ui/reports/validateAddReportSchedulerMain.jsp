<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ page import="org.apache.struts.Globals,com.talentPool.reports.ReportConstants,com.talentPool.reports.form.ReportForm,com.talentPool.common.utils.CommonUtils,com.talentPool.positions.dataobject.PositionData,com.talentPool.user.dataobject.LoginData,com.talentPool.reports.dataobject.FilterData,com.talentPool.common.properties.TPApplicationProperties"%>
<%@page import="com.talentPool.user.MessageConstants"%>
<%@page import="com.talentPool.reports.ReportVersionConstants,com.talentPool.common.db.SimpleDataObject"%>
<%@page import="java.util.ArrayList"%>

<script language="JavaScript" src="js/scripta/lib/prototype.js"	type="text/javascript"></script>
<script language="JavaScript" src="js/scripta/src/effects.js" type="text/javascript"></script>
<script language="JavaScript" src="js/scripta/src/controls.js" type="text/javascript"></script>
<script language="JavaScript" src="js/selectbox/dropdiv.js" type="text/javascript"></script>
<script language="JavaScript" src="js/ajaxfunctions.js" type="text/javascript"></script>
<script language="JavaScript" src="js/selectoption.js" type="text/javascript"></script>
<script language="JavaScript" src="js/selectbox/selectbox.js" type="text/javascript"></script>
<script language="JavaScript">
var allPos = new Array();
</script>
<logic:present name="update" scope="request">	
	<script>
		window.top.hidePopWin(true);
	</script>
</logic:present>

<%
ReportForm reportForm = (ReportForm) request.getAttribute("reportForm");
String reportLabel=(String)ReportVersionConstants.mapReportIdName.get(reportForm.getReportName());
ArrayList receiverConflicts=(ArrayList)request.getAttribute("receiverConflicts");
String noConflictUser=reportForm.getUserNameTo();

%>
<div class="contentDivPop" style="width: 500px;">
<table class="header">
<tr>
	<td style="text-align: left; vertical-align: top;" class="header">
		<bean:message key="report_scheduler.position.conflict.message"/>
	</td>		
</tr>
<tr>
	<td style="text-align: left; vertical-align: top;" class="header">
		&nbsp;
	</td>		
</tr>
</table>

<html:form action="/reportScheduler">
	<html:hidden property="reportName" />
	<html:hidden property="mode" value="saveReportScheduleCriteria" />
	<html:hidden property="frequencyOfScheduler" />
	<html:hidden property="dayOfWeek" />
	<html:hidden property="dayOfMonth" />
	<html:hidden property="everyDay" />
	<html:hidden property="weekDay" />
	<html:hidden property="scheduleId" />
	<html:hidden property="reportId" />
	<html:hidden property="users" />
	<html:hidden property="scheduleTime" />
	<html:hidden property="startDate" />
	<html:hidden property="mailSubject" />
	<html:hidden property="mailBody" />		
	<html:hidden property="userNameTo" />		
	
	<html:hidden property="filterData" />
	
	<html:hidden property="fromDate" />
	<html:hidden property="toDate" />
	<html:hidden property="reportName" />
	<html:hidden property="filterId" />
	<html:hidden property="positionId" />
	<html:hidden property="departmentId" />
	<html:hidden property="orderBy" />
	<html:hidden property="reportFormat" />
	<html:hidden property="fromMonth" />
	<html:hidden property="fromYear" />
	<html:hidden property="toMonth" />
	<html:hidden property="toYear" />
	<html:hidden property="userId" />
	<html:hidden property="sourceId" />
	<html:hidden property="sourceCategoryId" />
	<html:hidden property="reportType" />
	<html:hidden property="interviewers" />
	<html:hidden property="stages" />
	<html:hidden property="maxExp" />
	<html:hidden property="minExp" />
	<html:hidden property="degreeIds" />
	<html:hidden property="applicants" />
	<html:hidden property="stepIds" />
	<html:hidden property="users" />
	<html:hidden property="recruitmentCostReportType" />
	<html:hidden property="actionId" />
	<html:hidden property="dateRange" />
	<html:hidden property="numberRange" />
	<html:hidden property="activities" />	
	
	<table class="boxHeader">
	<tr>
		<td style="width: 25px;" class="head">&nbsp;</td>
		<td class="head" style="width: 170px;height: 16px;"> <bean:message key="report_scheduler.label.receiver_name"/> 
		</td>
		<td class="head" style="width: 272px;"><bean:message key="common.position"/>&nbsp;<bean:message key="report_scheduler.label.position_without_rights"/>
		</td>
	</tr>
	</table>
	<div class="outerDiv" style="height:225px;overflow: auto; border-top: none;">
	<table class="boxContent" style="border:0px;">		
			<%		
			for(int i=0;i<receiverConflicts.size();i++){
			String positionTitle="";
			SimpleDataObject sDo= (SimpleDataObject)receiverConflicts.get(i);
			String userName = sDo.getString("name");
			String receiverUserId = sDo.getString("userId");
			ArrayList positions = (ArrayList)sDo.getAttribute("positions");									
		%>
		<tr>
			<td style="width: 25px;vertical-align: top;padding-top: 2px; padding-bottom: 4px;">
				<img src="images/checkboxunchecked.gif" id="<%=receiverUserId%>" name="imgMailTo" onclick="javascript:toggleChkBox(this)">	
			</td>
			<td style="width: 170px;vertical-align: top;padding-top: 2px; padding-bottom: 4px;">
			<%= userName %>	
			</td>
			<td style="width: 250px; padding-top: 2px; padding-bottom: 4px;">
			<%	for(int k=0;k<positions.size();k++){
					SimpleDataObject sDo1= (SimpleDataObject)positions.get(k);
					if(k==(positions.size()-1)){
						positionTitle += sDo1.getString("positionTitle");					
					}else{
						positionTitle += sDo1.getString("positionTitle")+"; ";
					}				
				}			
				String partialPositons = positionTitle;
				String allPosition ="";
				if(positionTitle.length() > 45){
					partialPositons = positionTitle.substring(0, 45) + " ... ";
					partialPositons += " [ <a href=\"#\" onclick=\"showAllPositions(" + i + ");\" class=green>See complete list</a> ]";
					allPosition = positionTitle.replace("'","\\'");
				} 		
		 %>
		<%= partialPositons %>
		<script language="JavaScript">
			allPos[allPos.length] = '<%=allPosition%>';
		</script>
				
		</td>				
	</tr>
	<% } %>
	</table>   	
	</div>	
	<div>	
	<table class="tblPop" cellspacing="0" cellpadding="0" border="0" width="100%">
	<tr>
		<td>
			<div class="navBtn"	style="float:right;margin-left:5px;margin-top:5px;">
			<a href="#" style="width:50px;" class="active" onclick=" goBack();">
			<span class="rightC"></span><span class="leftC"></span><bean:message key="common.back" /></a>					
			<a href="#" style="width:50px;margin-left:5px;" class="active" onclick="submitForm();">
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
<div id="divAllPos" style="width: 485px; height: 380px; position: absolute; top: 20px; left: 20px;display: none; background-color: #eee; padding: 10px;" class="outerDiv">
	<div id="innerDivAllPos" style="height: 280px; overflow: auto; background-color: #fff;padding: 10px;" class="outerDiv"></div>
	<div class="navBtn"	style="float:right;margin-left:5px;margin-top:5px;">
		<a href="#" style="width:60px;margin-left:5px;" class="active" onclick="javascript:hideAllPosition();">
		<span class="rightC"></span><span class="leftC"></span><bean:message key="common.close"/></a>
	</div>
</div>

<script language="JavaScript">
var chkedChkBox = 'images/checkboxchecked.gif';
var unChkedChkBox = 'images/checkboxunchecked.gif';
function showAllPositions(idx){
	$('innerDivAllPos').innerHTML = allPos[idx];
	$('divAllPos').style.display='block';
}
function hideAllPosition(){
	$('divAllPos').style.display='none';
}
function selectUserToSend(){
	var exc = '';
	chk = $A(document.getElementsByName("imgMailTo"));
	if(chk){
		chk.each(function(item) {
			if(item.src.endsWith(chkedChkBox)){
				if(exc == ''){
					exc = item.id;
				}else{
					exc += ','+item.id;
				}	
			}
		});
	}
	return exc;
}

function toggleChkBox(obj) {
	if(obj){
		var src = obj.src;
		if (src.indexOf(unChkedChkBox) != -1) {
			obj.src = chkedChkBox;
		} else {
			obj.src = unChkedChkBox;
		}
	}
}

var nonConflictUser='';
function submitForm(){
	if(<%=noConflictUser.length()%>==0){
		nonConflictUser += selectUserToSend();
	}else{
		nonConflictUser=',';
		nonConflictUser += selectUserToSend();
	}	
	document.reportForm.userNameTo.value ="<%=noConflictUser%>"+nonConflictUser;	
	document.reportForm.submit();
}
function goBack(){
	document.reportForm.userNameTo.value="";
	document.reportForm.mode.value='addReportScheduler';
	document.reportForm.submit();
}
</script>
