<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<head>
	<s:head />
<script language="JavaScript" src="js/selectoption.js" type="text/javascript"></script>
<script language="JavaScript" src="js/selectbox/selectbox.js" type="text/javascript"></script>
</head>
<div class="contentDiv">
	<s:form method="POST" action="saveReportGrouping">
		<s:hidden name="reportCategory" />
		<s:hidden name="reportSubCategory" />
		<s:hidden name="selectedColumns" />
		<s:hidden name="groupByColumns" />
		<s:hidden name="sortByOrder" />
		<table width="100%" border="0" cellspacing="0" cellpadding="0"> 
		  <tr> 
		    <td><div style="width:120px;" class="boxTab"><span class="rightC"></span><span class="leftC"></span>
		    <img src="images/blank_small.gif" /><s:label key="custom_report.label.select_columns" /></div></td> 
		  </tr> 
   		</table> 
	   <table class="reportHeader" cellspacing="0" cellpadding="0" border="0"  width="100%">    
			<tr>				  			
				<td class="head" colspan="2">
					<b><s:label key="custom_report.label.select_grouping" />:</b>
				</td>
			</tr>
			<tr>
				<td>
					<table class="innerReport">	
						<tr>	
							<td>
								<script type="text/javascript">
									var groupByJsArray = <%=request.getAttribute("groupByJsArray") %>;
									var groupBySelectbox1 = new SelectBox(groupByJsArray,'-1','images/btn_dropdown.gif',{namesonly:false, width:'200px', size:5});
									document.write(groupBySelectbox1.getHtml());
									groupBySelectbox1.init();
								</script>
							</td>
							<td>
								<script type="text/javascript">
									var sortByJsArray = <%=request.getAttribute("sortByJSArray") %>;
									var sortBySelectbox1 = new SelectBox(sortByJsArray,'-1','images/btn_dropdown.gif',{namesonly:false, width:'200px', size:5});
									document.write(sortBySelectbox1.getHtml());
									sortBySelectbox1.init();
								</script>
							</td>
						</tr>
						<tr>
							<td>
								<script type="text/javascript">
									var groupBySelectbox2 = new SelectBox(groupByJsArray,'-1','images/btn_dropdown.gif',{namesonly:false, width:'200px', size:5});
									document.write(groupBySelectbox2.getHtml());
									groupBySelectbox2.init();
								</script>
							</td>
							<td>
								<script type="text/javascript">
									var sortBySelectbox2 = new SelectBox(sortByJsArray,'-1','images/btn_dropdown.gif',{namesonly:false, width:'200px', size:5});
									document.write(sortBySelectbox2.getHtml());
									sortBySelectbox2.init();
								</script>
							</td>
						</tr>
						<tr>
							<td>
								<script type="text/javascript">
									var groupBySelectbox3 = new SelectBox(groupByJsArray,'-1','images/btn_dropdown.gif',{namesonly:false, width:'200px', size:5});
									document.write(groupBySelectbox3.getHtml());
									groupBySelectbox3.init();
								</script>
							</td>
							<td>
								<script type="text/javascript">
									var sortBySelectbox3 = new SelectBox(sortByJsArray,'-1','images/btn_dropdown.gif',{namesonly:false, width:'200px', size:5});
									document.write(sortBySelectbox3.getHtml());
									sortBySelectbox3.init();
								</script>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
     	<table class="tblPop" width="100%">
			<tr>
				<td>
					<div class="navBtn" style="float: right;">
						<a href="#" style="width:50px;" class="active" onclick="javascript: previousPage();"><span class="rightC"></span><span class="leftC"></span><s:label key="common.back"/></a>
						<a href="#" style="width:50px; margin-left:5px;" class="active" onclick="javascript: nextPage();"><span class="rightC"></span><span class="leftC"></span><s:label key="common.next"/></a>
						<a href="#" style="width:60px; margin-left:5px;" class="active" onclick="javascript: onCancel();return false;"><span class="rightC"></span><span class="leftC"></span><s:label key="common.cancel"/></a>
					</div>
				</td>
			</tr>
		</table>
	</s:form>
</div>
<script type="text/javascript"> 
function nextPage(){
	var groupBy = '';
	var sortBy = '';
	if(groupBySelectbox1.getSelectedId()!=-1){
		groupBy+=groupBySelectbox1.getSelectedId()+',';
		sortBy+=sortBySelectbox1.getSelectedId()+',';
		if(groupBySelectbox2.getSelectedId()!=-1){
			groupBy+=groupBySelectbox2.getSelectedId()+',';
			sortBy+=sortBySelectbox2.getSelectedId()+',';
			if(groupBySelectbox3.getSelectedId()!=-1){
				groupBy+=groupBySelectbox3.getSelectedId()+',';
				sortBy+=sortBySelectbox3.getSelectedId()+',';
			}
		}
	}
	if(groupBy!=''){
		groupBy = groupBy.substring(0,groupBy.length-1);
		sortBy 	= sortBy.substring(0,sortBy.length-1);
	}	
	document.saveReportGrouping.groupByColumns.value=groupBy;
	document.saveReportGrouping.sortByOrder.value=sortBy;
	document.saveReportGrouping.submit();
}
</script>