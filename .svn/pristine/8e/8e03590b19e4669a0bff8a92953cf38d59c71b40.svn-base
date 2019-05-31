<%@ taglib prefix="s" uri="/struts-tags" %>
<%@page import="com.talentPool.reports.ReportConstants"%>
<%@page import="com.talentPool.reports.ReportUtils"%>
<%@page import="com.talentPool.common.utils.Utils"%>
<link rel="stylesheet" type="text/css" href="themes/default/CalendarPopup.css"/>
<script src="js/calender/CalendarPopup.js"></script>
<script src="js/calender/dateFormatter.js" type="text/javascript"></script>
<s:set name="CUSTOM" value="@com.talentPool.reports.ReportConstants@CUSTOM" id="CUSTOM"/>
<table class="innerReport">
	 <tr>
		<td class="label">
			<%=Utils.getBlankIfNull(request.getParameter("type"))%>
			<s:text name="report.label.date_range" />:&nbsp;
		</td>
		<td> 
			<script type="text/javascript">
				var optDate = <%=ReportUtils.getJSArrayForDateRange()%>;
				selectDateRange = new SelectBox(optDate,'<%=ReportConstants.TODAY%>','images/btn_dropdown.gif',{namesonly:false, width:'180px', size:20});
				document.write(selectDateRange.getHtml());
				selectDateRange.setOnChangeHandler('onDateRangeChange');
				selectDateRange.init();
			</script>
		</td>
		<td>
			<div id="divSelectDateRange" style="display:none;">
		 		 <table class="innerReport" cellspacing="0" cellpadding="0" border="0" >
	      	 		<tr>
	      	 			<td style="width: 20px;"></td>
	      	 			<td>
        		  			<s:text name="report.label.from" /> :
        					<s:textfield name="fromDate" id="fromDate"  size="12" maxlength="10" maonblur="getFormattedDate(this);"/><img src="images/ico_cal.gif" onClick="popUpCal.select(document.getElementById('fromDate'),'fromDate','dd/MM/yyyy'); return false;" class="CalImg" name="imgCal" id="imgCal" style="margin-bottom:-3px;"/>
						</td>
						<td style="width: 20px;"></td>
						<td>
	          				<s:text name="report.label.to" /> :
	          				<s:textfield name="toDate" id="toDate" size="12" maxlength="10" onblur="getFormattedDate(this);"/><img src="images/ico_cal.gif" onClick="popUpCal.select(document.getElementById('toDate'),'toDate','dd/MM/yyyy'); return false;" class="CalImg" name="imgCal" id="imgCal" style="margin-bottom:-3px;"/>
      					</td>
      				</tr>
				</table>
			</div>
		</td>
	</tr>
</table>	
<DIV id="calDiv" style="position:absolute;background:#FFFFFF;z-index:1000;" ></DIV>
<script>
var popUpCal = new CalendarPopup("calDiv"); 

popUpCal.showNavigationDropdowns();

//DATE FORMATTER CODE AND FUNCTIONS
var dtf = new DateFormatter();
dtf.setDisplayFormat('DD/MM/YYYY');
function getFormattedDate(obj){
	if(obj.value.trim()!=''){
	  if(!dtf.checkDate(obj)){
		obj.select();
		alert('<s:text name="calendar.error.invalid_date"/>');
		obj.focus();
		return false;
	  }else {
		return true;
	  }
	}
	return true;
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

function initDateFilter(dateRange, fromDate, toDate){
	if(dateRange && dateRange!=null){
		selectDateRange.setSelected(selectDateRange.getIndexWithId(dateRange));
		if(dateRange=='<s:property value="#CUSTOM" />') {
			if(fromDate && fromDate!=null){
				$('fromDate').value=fromDate;
			}
			if(fromDate && fromDate!=null){
				$('toDate').value=toDate;
			}
		}
	}	
}


function validateAndGetDateFilterValue(){
	var value = selectDateRange.getSelectedId();
	if(value=='<s:property value="#CUSTOM" />') {
		if($('toDate').value=='' || $('toDate')==null){
			alert('<s:text name="custom_report.error.select_dateRange" />');
			return false;
		}else {
			return [value,$('fromDate').value,$('toDate').value];
		}	
	}else{
		return [value];
	}
}
</script>