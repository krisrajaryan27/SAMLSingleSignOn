<%@page import="com.talentPool.common.properties.TPApplicationProperties"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<script src="js/scripta/lib/prototype.js"></script>
<script src="js/selectoption.js" type="text/javascript"></script>
<script src="js/checkboxlist/checkboxlist.js" type="text/javascript"></script>
<s:form action="configureReportTotals" method="POST">
<%@include file="include/commonReportHiddenFields.jspf" %>
<div class="contentDiv" >
	<table style="padding: 0px;border: 0;" cellspacing="0" > 
		<tr> 
			<td>
				<div style="width:200px;" class="boxTab"><span class="rightC"></span><span class="leftC"></span>
					<s:label key="custom_report.title.configure_totals" />
				</div>
			</td> 
		</tr> 
	</table>
	<div class="outerDiv">
		<table class="posinput" style="padding: 10px;">	
			<tr>
				<td style="vertical-align: top;" width="140px;">
					<s:label key="custom_report.label.show_subtotals_for" />:
				</td>
				<td>
					<script type="text/javascript">	
			 			var opts = <s:property value="#request.rowsJSArray"  />;
			 			var rowTotalsCheckBoxList = new CheckBoxList(opts,'<s:property value="showRowSubTotalsFor"  />',{namesonly:false, layerclass:'checkboxlistdiv',width:'210px', size:19, imageclass:'chkboxclass', uncheckedimg:'images/checkboxunchecked.gif', checkedimg:'images/checkboxchecked.gif'});
						document.write(rowTotalsCheckBoxList.getHtml());
						rowTotalsCheckBoxList.init();
			 		</script>	
				</td>
			</tr>
			<tr>
				<td style="vertical-align: top;" width="140px;">
					<s:text name="custom_report.label.show_row_grand_total" />
				</td>
				<td>
					<img id="showRowGrandTotalImg" src="images/checkboxunchecked.gif" style="cursor: pointer;" >
				</td>
			</tr>
		</table>
	</div>
	<table class="tblPop" style="width: 100%;">
		<tr>
			<td>
				<div class="navBtn" style="float: right;margin-top: 5px;">
					<a href="#" style="width:50px;" class="active" onclick="javascript: previousPage();"><span class="rightC"></span><span class="leftC"></span><s:text name="common.back"/></a>
					<a href="#" style="width:70px; margin-left:5px;" class="active" onclick="javascript: nextPage();"><span class="rightC"></span><span class="leftC"></span><s:text name="common.next"/></a>
					<a href="#" style="width:60px; margin-left:5px;" class="active" onclick="javascript: onCancel();return false;"><span class="rightC"></span><span class="leftC"></span><s:text name="common.cancel"/></a>
				</div>
			</td>
		</tr>
	</table>
</div>	
</s:form>
<script>
var chkedCheckBox="images/checkboxchecked.gif";
var unChkedCheckBox="images/checkboxunchecked.gif";

function previousPage(){
	populateForm();
	if(document.configureReportTotals.showCandidateNames.value=='true'){
		document.configureReportTotals.action="candidateNamesSpecification.action";
	}else{
		document.configureReportTotals.action="customizeReport.action";
	}
	document.configureReportTotals.submit();
}

function nextPage(){
	populateForm()
	document.configureReportTotals.action="selectFilters.action";
	document.configureReportTotals.submit();
}

function populateForm(){
	if($('showRowGrandTotalImg').src.indexOf(chkedCheckBox)!=-1){
		document.configureReportTotals.showRowGrandTotal.value = true;
	}else{
		document.configureReportTotals.showRowGrandTotal.value = false;
	}
	document.configureReportTotals.showRowSubTotalsFor.value = rowTotalsCheckBoxList.getSelectedIds();	
}

function onCancel(){
	window.location.href="reports.do?mode=reportFilter&reportName=";
}

function changeCheckBoxState(event){	
	var imgElem = event.element();	
	if(imgElem.src.indexOf(chkedCheckBox)!=-1){
		imgElem.src=unChkedCheckBox;	
	}else if(imgElem.src.indexOf(unChkedCheckBox)!=-1){
		imgElem.src=chkedCheckBox;
	}
}

Event.observe(window, "load", function() {
	$("showRowGrandTotalImg").observe("click", changeCheckBoxState);
	if(document.configureReportTotals.showRowGrandTotal.value=='true'){
		$("showRowGrandTotalImg").src=chkedCheckBox;
	}	
});
</script>