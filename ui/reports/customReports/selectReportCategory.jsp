<%@page import="com.talentPool.common.properties.TPApplicationProperties"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@page import="com.talentPool.customReports.utils.CustomReportUtils"%>
<script src="js/scripta/lib/prototype.js" type="text/javascript"></script>								
<script src="js/scripta/src/effects.js" type="text/javascript"></script>
<script src="js/selectoption.js" type="text/javascript"></script>
<script src="js/selectbox/selectbox.js" type="text/javascript"></script>
<script type="text/javascript">
var selectReportType=null;
var selectReportCategory=null;
var selectReportSubCategory=null;
</script>
<s:form action="saveReportCategory" method="POST">
<s:hidden name="reportCategory" />
<s:hidden name="reportSubCategory" />
<div class="contentDiv">
   <table width="100%" border="0" cellspacing="0" cellpadding="0"> 
	  <tr> 
	    <td><div style="width:120px;" class="boxTab"><span class="rightC"></span><span class="leftC"></span>
	    <img src="images/blank_small.gif" align="absmiddle" />Report Category</div></td> 
	  </tr> 
   </table> 
   <table class="reportHeader" cellspacing="0" cellpadding="0" border="0"  width="100%">    
	<tr>				  			
		<td class="head">
			<b>Select a category for which a new report has to be created:</b>
		</td>
	</tr>
	<tr>				  			
		<td>
			 <table class="innerReport">  			 	
				<tr>
  			 		<td>
						<table cellspacing="0" cellpadding="0" >
							<tr>
							  <td class="header" width="150px;">
								  <s:label key="custom.report.type.label" /><span class="star">*&nbsp;</span>
							  </td>
							  <td>
						  		<script>									
									var opts = <%=CustomReportUtils.getJSArrayForReportType()%>;
									selectReportType = new SelectBox(opts,'','images/btn_dropdown.gif',{namesonly:false, width:'170px', size:10, textboxclass:''});
									document.write(selectReportType.getHtml());
									selectReportType.init();
								</script>						  
							  </td>
						  </tr>
						  <tr><td>&nbsp;</td></tr>
							<tr>
							  <td class="header" width="150px;">
								  <s:label key="custom.report.category.label" /><span class="star">*&nbsp;</span>
							  </td>
							  <td>
						  		<script>									
									var opts = <%=CustomReportUtils.getJSArrayForReportCategory()%>;
									selectReportCategory = new SelectBox(opts,'','images/btn_dropdown.gif',{namesonly:false, width:'170px', size:10, textboxclass:''});
									document.write(selectReportCategory.getHtml());
									selectReportCategory.setOnChangeHandler('onSelectCategory');
									selectReportCategory.init();
								</script>						  
							  </td>
						  </tr>
						  <tr><td>&nbsp;</td></tr>
						  <tr>
						  	<td style="vertical-align: top;">Related Categories
						  	</td>
						  	<td>
						  		<script>									
									var opts = [new SelectOption('-1','none')];
									selectReportSubCategory = new SelectBox(opts,'','images/btn_dropdown.gif',{namesonly:false, width:'170px', size:10, textboxclass:''});
									document.write(selectReportSubCategory.getHtml());
									selectReportSubCategory.init();
								</script>
						  	</td>
						  </tr>
					  </table>		  			  
					</td>
				</tr>
			</table>	
		 </td>
	</tr>
   </table>
	<br/>
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
</div>
</s:form>
<script>
function onSelectCategory(){
	var type = selectReportType.getSelectedId();
	var id = selectReportCategory.getSelectedId();
	
	if(id!='-1'){
		var pars = "reportType="+type+"&reportCategory="+id;
		var myAjax = ajaxCall("getReportRelatedCategory.action","get",pars,onSelectCategoryComplete,reportError);
	}
}

function onSelectCategoryComplete(request){
	xmlFile = request.responseXML;
  	if(!isValidSession(xmlFile,'<%=TPApplicationProperties.getProperty("redirect_on_session_expired")%>'))return;
	if(!isErrorXml(xmlFile)){
		var op = request.responseText;
		var opts = eval(op);
		var m = [new SelectOption('-1', 'none')];
		opts = m.concat(opts);
		selectReportSubCategory.reInitialize(opts, '');
	}else{
		alert('<s:label key="admin_master_dept.error.failed_delete"/>');
		return;
	}
}

function nextPage(){
	document.saveReportCategory.reportCategory.value=selectReportCategory.getSelectedId();
	document.saveReportCategory.reportSubCategory.value=selectReportSubCategory.getSelectedId();
	document.saveReportCategory.submit();
}

function onCancel(){
	window.location.href="reports.do?mode=reportFilter&reportName=";
}
</script>