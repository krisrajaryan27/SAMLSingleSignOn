<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ page import="com.talentPool.reports.ReportConstants, com.talentPool.reports.form.ReportForm, com.talentPool.common.utils.CommonUtils"%>
<%@ page import="com.talentPool.user.manager.ModuleSet,com.talentPool.common.properties.TPApplicationProperties,com.talentPool.reports.ReportUtils"%>
<%@page import="com.talentPool.common.properties.GlobalApplicationProperties"%>
<%@page import="com.talentPool.common.properties.GlobalConstants"%>
<script language="JavaScript" src="js/ajaxfunctions.js" type="text/javascript"></script>
<SCRIPT LANGUAGE="JavaScript" SRC="js/calender/CalendarPopup.js"></SCRIPT>
<link rel="stylesheet" type="text/css" href="themes/default/CalendarPopup.css">
<script language="JavaScript" src="js/calender/dateFormatter.js" type="text/javascript"></script>
<script language="JavaScript" src="js/checkboxlist/checkboxlist.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css" href="themes/default/checkboxlist.css">
<script type="text/javascript">
var selectDateRange = null;
var selectBoxDepartment = null;
</script>
<%
	ReportForm reportForm = (ReportForm)request.getAttribute("reportForm");
%>

<div class="contentDiv">
	<html:form action="/masterReport">
	<html:hidden property="mode" value="createMasterDataReport"/>
	<html:hidden property="filterId"/>
	<html:hidden property="dateRange" name="reportForm"/>
	<html:hidden property="departmentId"/>
	
   	<table width="100%" border="0" cellspacing="0" cellpadding="0"> 
	  <tr> 
	    <td><div style="width:150px;" class="boxTab"><span class="rightC"></span><span class="leftC"></span><bean:message key="report.label.master_data_report"/></div></td> 
	  </tr> 
	</table> 
	<table class="reportHeader" cellspacing="0" cellpadding="0" border="0"  width="100%">
		<tr>				  			
			<td class="head">
				<b><bean:message key="report.label.filter" /></b>
			</td>
		</tr>
		<tr>				  			
			<td>
				<table class="innerReport">
				<tr>
			   		<td width="80">
	               		<bean:message key="report.label.date_range" /> :
	               	</td>
					<td>
	                  	 <script type="text/javascript">
			                    var optDate = <%=ReportUtils.getJSArrayForDateRange()%>;
			                    selectDateRange = new SelectBox(optDate,'<%=ReportConstants.CUSTOM%>','images/btn_dropdown.gif',{namesonly:false, width:'160px', size:20});
			                    document.write(selectDateRange.getHtml());
			                    selectDateRange.setOnChangeHandler('onDateRangeChange');
			                    selectDateRange.init();
	                  	  </script>
			   		</td>
			   		<td>
						<div id="divSelectDateRange" >
						<table class="innerReport" cellspacing="0" cellpadding="0" border="0" >
					    <tr>
					    	<td style="width: 20px;"></td>
					      	<td>
		  		        		<bean:message key="report.label.from" /> :</td>
		  		        	<td>	
		  		        		<html:text property="fromDate" styleId="fromDate" size="12" maxlength="10" onblur="getFormattedDate(this);"/><img src="images/ico_cal.gif" onClick="popUpCal.select(document.getElementById('fromDate'),'fromDate','dd/MM/yyyy'); return false;" class="CalImg" name="imgCal" id="imgCal" style="margin-bottom:-3px;"/>
							</td>
							<td style="width: 20px;"></td>
							<td>
			  		        	<bean:message key="report.label.to" /> :</td>
			  		        <td>
			  		        	<html:text property="toDate" styleId="toDate" size="12" maxlength="10" onblur="getFormattedDate(this);"/><img src="images/ico_cal.gif" onClick="popUpCal.select(document.getElementById('toDate'),'toDate','dd/MM/yyyy'); return false;" class="CalImg" name="imgCal" id="imgCal" style="margin-bottom:-3px;"/>
		  		      		</td>
		  		      	</tr>
			   			</table>
						</div>
	   				</td>
	  			</tr>
	  			<tr>
	  				<td colspan="3">
	  					<div id="divSelectSpecificDepartment">
						<table cellspacing="0" cellpadding="0">
						<tr>
							<td width="80">
								<bean:message key="report.label.select" /> <%=GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_DEPARTMENT_LEVEL_1) %>:&nbsp;
							</td>
							<td>
								<script type="text/javascript">
									var opts = <%=CommonUtils.getListJavaScriptArray(reportForm.getDepartmentIds(),reportForm.getDepartmentNames())%>;
									var m = [new SelectOption('0','<bean:message key="common.selectlist.all"/>')];
									opts = m.concat(opts);
									selectBoxDepartment = new SelectBox(opts,'','images/btn_dropdown.gif',{namesonly:false, width:'180px', size:15});
									document.write(selectBoxDepartment.getHtml());
									selectBoxDepartment.init();
								</script>
							</td>
						</tr>
						</table>
						</div>
	  				</td>	  				
	  			</tr>
					
				</table>
			</td>
		</tr>
	</table>
	<br>
	<table cellspacing="0" cellpadding="0" border="0"  width="100%">
   	<tr>
   		<td>
	   	<div class="navBtn" style="float:left;margin-right:5px;margin-top:5px;">
			<a href="#" style="width:120px;" class="active" onclick="javascript:submitForm();">
			<span class="rightC"></span><span class="leftC"></span><bean:message key="report.label.view_report"/></a>
		</div>	
		</td>
	</tr>
	</table>
	<br>
	</html:form>
</div>
<DIV id="calDiv" style="position:absolute;background:#FFFFFF;z-index:1000;" ></DIV>
<script language="JavaScript">

var popUpCal = new CalendarPopup("calDiv"); 
popUpCal.showNavigationDropdowns();

window.onload=doOnLoad;

function doOnLoad() {

}

function onDateRangeChange(val){
	customDisplayHidden(selectDateRange.getSelectedId());
}

function customDisplayHidden(val){
	if(val=='<%=ReportConstants.CUSTOM%>'){
		$("divSelectDateRange").style.display="block";
	} else {
		$("divSelectDateRange").style.display="none";
	}  
}

function validateSetFormFields(){

	document.reportForm.departmentId.value=selectBoxDepartment.getSelectedId();
	document.reportForm.dateRange.value=selectDateRange.getSelectedId();
	return true;
}


function submitForm(){
	if( validateSetFormFields()){
		var d = new Date();
		document.reportForm.target=d;
		document.reportForm.submit();
	}
}

//DATE FORMATTER CODE AND FUNCTIONS
var dtf = new DateFormatter();
dtf.setDisplayFormat('DD/MM/YYYY');
function getFormattedDate(obj){
	if(obj.value != ''){
		if(!dtf.checkDate(obj)){
			obj.select();
			alert("<bean:message key="report.error.invalid_date"/>");
			obj.focus();
			return false;
		}else {
			return true;
		}
	}
}
</script>