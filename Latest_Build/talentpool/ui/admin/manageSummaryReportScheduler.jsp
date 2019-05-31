<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="com.talentPool.common.properties.TPApplicationProperties"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<script language="JavaScript" src="js/scripta/lib/prototype.js" type="text/javascript"></script>
<script language="JavaScript" src="js/selectoption.js" type="text/javascript"></script>
<script language="JavaScript" src="js/selectbox/selectbox.js" type="text/javascript"></script>
<script language="JavaScript" src="js/scripta/src/effects.js" type="text/javascript"></script>
<script language="JavaScript" src="js/ajaxfunctions.js" type="text/javascript"></script>
<script language="JavaScript" src="js/utils/updater.js" type="text/javascript"></script>
<body>
<s:form name="summaryReportScheduler" method="POST" action="saveSchedulerProperties" >
<s:hidden name="startHour" id="startHour"/>
<s:hidden name="frequency" id="frequency"/>
<s:set name="curStatus" value="#request['currentStatus']" />
<div class="contentDiv">
	<div class="boxTab" style="width:200px;"><span class="rightC"></span><span class="leftC"></span><s:text name="admin.summary_report_scheduler.properties" /></div>
	<div class="outerDiv" style="width: 350px;" >
		<table class="posinput" width="100%" style="padding: 15px 0px 15px 30px;"> 
		  <tr>
		  	 <td class="label" width="100px">
			 	<s:text name="admin.summary_report_scheduler.start_hour" /> 
			 </td>
			 <td>
			 	: &nbsp;&nbsp;
			 		<script type="text/javascript">
						var opts = [new SelectOption('0','12 AM'), new SelectOption('1','1 AM'), new SelectOption('2','2 AM'), 
						            new SelectOption('3','3 AM'), new SelectOption('4','4 AM'), new SelectOption('5','5 AM'), 
						            new SelectOption('6','6 AM'), new SelectOption('7','7 AM'), new SelectOption('8','8 AM'), 
						            new SelectOption('9','9 AM'), new SelectOption('10','10 AM'), new SelectOption('11','11 AM'),
						            new SelectOption('12','12 PM'), new SelectOption('13','1 PM'), new SelectOption('14','2 PM'), 
						            new SelectOption('15','3 PM'), new SelectOption('16','4 PM'), new SelectOption('17','5 PM'), 
						            new SelectOption('18','6 PM'), new SelectOption('19','7 PM'), new SelectOption('20','8 PM'), 
						            new SelectOption('21','9 PM'), new SelectOption('22','10 PM'), new SelectOption('23','11 PM')];
						var selectBoxStartHour = new SelectBox(opts,'<s:property value="#request['startHour']"/>','images/btn_dropdown.gif',{namesonly:false, width:'120px', size:15});
   						document.write(selectBoxStartHour.getHtml());
   						selectBoxStartHour.init();
			 		</script>
			 </td>
		  </tr>
		  <tr>
		  	 <td class="label" width="100px">
				<s:text name="admin.summary_report_scheduler.frequency" /> 
			 </td>
			 <td>
			 	: &nbsp;&nbsp;
			 		<script>
						var opts = [new SelectOption('4','Every 4 hrs'),
						            new SelectOption('6','Every 6 hrs'),
						            new SelectOption('8','Every 8 hrs'), 
						            new SelectOption('12','Every 12 hrs'), 
						            new SelectOption('24','Every 24 hrs')];
						var selectBoxFrequency = new SelectBox(opts,<s:property value="#request['frequency']"/>,'images/btn_dropdown.gif',{namesonly:false, width:'120px', size:15});
   						document.write(selectBoxFrequency.getHtml());
   						selectBoxFrequency.init();
			 		</script>
			 </td>
		  </tr>
		</table>
		<div class="navBtn" style="float: right;margin-top: 5px;">
			<a href="#" style="width:100px; margin-left:5px;" class="active" onclick="javascript: save();"><span class="rightC"></span><span class="leftC"></span><s:text name="common.save"/></a>
		</div>
	</div>
	<br/><br/><br/><br/>
	<div class="boxTab" style="width:200px;"><span class="rightC"></span><span class="leftC"></span><s:text name="admin.summary_report_scheduler.last_run_details" /></div>
	<div class="outerDiv" style="width: 350px;" >
		<table class="posinput" width="100%" style="padding: 15px 0px 15px 30px;"> 
		  <tr>
		  	 <td class="label" width="100px">
			 	<s:text name="admin.summary_report_scheduler.last_status" /> 
			 </td>
			 <td>
			 	: &nbsp;&nbsp;<s:property value="#request['lastStatus']"/>
			 </td>
		  </tr>
		  <tr>
		  	 <td class="label" width="100px">
				<s:text name="admin.summary_report_scheduler.last_start_time" /> 
			 </td>
			 <td>
			 	: &nbsp;&nbsp;<s:property value="#request['lastStartTime']"/>
			 </td>
		  </tr>
		  <tr>
		  	 <td class="label" width="100px"> 
			 	<s:text name="admin.summary_report_scheduler.last_end_time" />
			 </td>
			 <td>
			 	: &nbsp;&nbsp;<s:property value="#request['lastEndTime']"/>
			 </td>
		  </tr> 
		</table>		
	</div> 
	<br/><br/>
	<table width="350px" style="padding: 15px 0px 15px 30px;">
		<tr>
			<td class="label" width="100px">
			 	<s:text name="admin.summary_report_scheduler.current_status" /> 
			</td>
			<td id="curStat">
			 	: &nbsp;&nbsp;<s:property value="#curStatus"/>
			</td>
		</tr>
		<tr>
			<td colspan=2>
				<div id="wait">
					<s:if test="#curStatus == 'IDLE'">
				 		<div id="runNowBtn" class="navBtn" style="float: right;margin-top: 5px;">
							<a href="#" style="width:100px; margin-left:5px;" class="active" onclick="javascript: runNow();"><span class="rightC"></span><span class="leftC"></span><s:text name="admin.summary_report_scheduler.run_now"/></a>
						</div>	
					</s:if>
				</div>
			</td>	
		</tr>
	</table>	
</div>
</s:form>
</body>
<script type="text/javascript">

function save(){
	document.summaryReportScheduler.startHour.value = selectBoxStartHour.getSelectedId() + ":00";
	document.summaryReportScheduler.frequency.value = selectBoxFrequency.getSelectedId();
	document.summaryReportScheduler.submit();
}

function runNow(){
	showUpdater('wait',{setHeight: false, setWidth: false, offsetLeft: 0});
	$('curStat').replace(': &nbsp;&nbsp;RUNNING');
	document.summaryReportScheduler.action='runScheduler.action';
	document.summaryReportScheduler.submit();
}

window.onload = doOnLoad;
function doOnLoad(){
	var flag = <%=request.getAttribute("currentStatus").equals("IDLE")%>;
	if(flag) {
		hideUpdater('wait');
	} else {
		showUpdater('wait',{setHeight: false, setWidth: false, offsetLeft: 0});
	}
}

</script>
</html>
