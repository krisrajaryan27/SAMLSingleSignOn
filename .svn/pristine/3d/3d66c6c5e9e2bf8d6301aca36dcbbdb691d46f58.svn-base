<%@ taglib prefix="s" uri="/struts-tags"%>
<%@page import="com.talentPool.reports.ReportConstants"%>
<%@page import="com.talentPool.reports.ReportUtils"%>
<link rel="stylesheet" type="text/css"
	href="themes/default/CalendarPopup.css" />
<script src="js/calender/CalendarPopup.js"></script>
<script src="js/calender/dateFormatter.js" type="text/javascript"></script>
<s:set name="CUSTOM" value="@com.talentPool.reports.ReportConstants@CUSTOM" id="CUSTOM"/>
<table class="innerReport">
	<tr>
		<td class="label"><s:text name="report.label.as_of_date" />:&nbsp;
		</td>
		<td><script type="text/javascript">
				var optDate = <%=ReportUtils.getJSArrayForAsOfDate()%>;
				selectAsOfDate = new SelectBox(optDate,'<%=ReportConstants.TODAY%>','images/btn_dropdown.gif',{namesonly:false, width:'180px', size:20});
				document.write(selectAsOfDate.getHtml());
				selectAsOfDate.setOnChangeHandler('onAsOfDateChange');
				selectAsOfDate.init();
			</script>
		</td>
		<td>
			<div id="divSelectAsOfDate" style="display:none;">
				<table class="innerReport" cellspacing="0" cellpadding="0" border="0" >
		   	 		<tr>
				    	<td style="width: 20px;"></td>
				      	<td>
	  		        		<s:text name="report.label.select" /> :
	  		        			<s:textfield name="asOfDate" id="asOfDate" size="12" maxlength="10" onblur="getFormattedDate(this);"/><img src="images/ico_cal.gif" onClick="popUpCal.select(document.getElementById('asOfDate'),'asOfDate','dd/MM/yyyy'); return false;" class="CalImg" name="imgCal" id="imgCal" style="margin-bottom:-3px;"/>
						</td>
	  		      	</tr>
		   		</table>
			</div>
		</td>
	</tr>
</table>
<DIV id="calDiv"
	style="position: absolute; background: #FFFFFF; z-index: 1000;"></DIV>
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

function onAsOfDateChange(val){
	customDisplayHiddenAsOfDate(selectAsOfDate.getSelectedId());
}

function customDisplayHiddenAsOfDate(val){
	if(val=='<s:property value="#CUSTOM" />') {
		$("divSelectAsOfDate").style.display = "block";
	} else {
		$("divSelectAsOfDate").style.display = "none";
	}
}

function initAsOfDateFilter(asOfDateRange, customValue){
	if(asOfDateRange && asOfDateRange!=null){
		selectAsOfDate.setSelected(selectAsOfDate.getIndexWithId(asOfDateRange));
		if(asOfDateRange=='<s:property value="#CUSTOM" />') {
			if(customValue && customValue!=null){
				$('asOfDate').value=customValue;
			}
		}
	}	
}

function validateAsOfDateFilter() {
	if (frm.asOfDate) {
		frm.asOfDate.value = selectAsOfDate.getSelectedId();
	}
}

function validateAndGetAsOfDateFilterValue(){
	var value = selectAsOfDate.getSelectedId();
	if(value=='<s:property value="#CUSTOM" />') {
		if($('asOfDate').value=='' || $('asOfDate')==null){
			alert('<s:text name="custom_report.error.select_asofdate" />');
			return false;
		}else {
			return [value,$('asOfDate').value];
		}	
	}else{
		return [value];
	}
}
	
</script>