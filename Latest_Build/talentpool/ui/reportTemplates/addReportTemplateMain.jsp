<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@page import="com.talentPool.reportDesign.constants.ReportDesignConstants"%>
<%@page import="org.apache.struts.Globals"%>
<%@page import="com.talentPool.reports.ReportUtils"%>
<%@page import="com.talentPool.common.properties.TPApplicationProperties"%>
<%@page import="com.talentPool.reports.ReportVersionConstants"%>
<%@page import="com.talentPool.documents.utils.DocumentUtils"%><script language="JavaScript" src="js/scripta/lib/prototype.js" type="text/javascript"></script>								
<script language="JavaScript" src="js/scripta/src/effects.js" type="text/javascript"></script>
<script language="JavaScript" src="js/selectoption.js" type="text/javascript"></script>
<script language="JavaScript" src="js/selectbox/selectbox.js" type="text/javascript"></script>
<script language="JavaScript" src="js/commonFunction.js"></script>
<script language="JavaScript" src="js/ajaxfunctions.js" type="text/javascript"></script>
<script src="js/submodal/common.js"></script>
<script src="js/submodal/subModal.js"></script>
<script type="text/javascript">
var selectTemplateType=null;
</script>
<html:form action="/reportTemplates" onsubmit="submitForm();return false;">
<html:hidden property="mode" name="reportTemplatesForm"/>
<html:hidden property="reportId" name="reportTemplatesForm"/>
<html:hidden property="reportFilePath" name="reportTemplatesForm"/>
<html:hidden property="sheetIndex" name="reportTemplatesForm"/>
<html:hidden property="rowIndex" name="reportTemplatesForm"/>
<html:hidden property="templateColumns" name="reportTemplatesForm"/>
<html:hidden property="originalFileName" name="reportTemplatesForm"/>
<html:hidden property="templateName" name="reportTemplatesForm"/>
<html:hidden property="reportTemplateId" name="reportTemplatesForm"/>
<html:hidden property="sheetsArray" name="reportTemplatesForm"/>
<bean:define id="reportTemplatesForm" name="reportTemplatesForm" type="com.talentPool.dynamicReports.form.ReportTemplatesForm" ></bean:define>
<div class="contentDiv">
<%
	if(request.getAttribute(Globals.ERROR_KEY)!=null){
%>
<table id="m_errortable" >
	<tr>
    <td class="header" colspan="2">
    	<b><bean:message key="errors.following_errors"/></b>
    </td>
	</tr>
   <tr>
   	<td class="message" colspan="2"><html:errors/></td>
   </tr>
</table>
<br/>
<%} %>
<table width="100%" border="0" cellspacing="0" cellpadding="0"> 
	  <tr> 
	    <td><div style="width:180px;" class="boxTab"><span class="rightC"></span><span class="leftC"></span>
	    <img src="images/blank_small.gif" align="absmiddle" />
	    <logic:notEmpty name="reportTemplatesForm" property="reportTemplateId">
			<b><bean:message key="report_templates.label.edit"/></b>
		</logic:notEmpty>
		<logic:empty name="reportTemplatesForm" property="reportTemplateId">
			<b><bean:message key="report_templates.label.add"/></b>
		</logic:empty>
	    </div></td> 
	  </tr> 
</table> 
<table class="reportHeader" cellspacing="0" cellpadding="0" border="0"  width="100%">    
	<tr>				  			
		<td class="head" colspan="2" >
			<logic:notEmpty name="reportTemplatesForm" property="reportTemplateId">
				<b><bean:message key="report_templates.label.details"/></b>
			</logic:notEmpty>
			<logic:empty name="reportTemplatesForm" property="reportTemplateId">
				<b><bean:message key="report_templates.label.select_details"/></b>
			</logic:empty>
		</td>
	</tr>
	<tr>
		<td>
			<bean:message key="report_templates.label.template_type"/>					
		</td>
		<logic:notEmpty name="reportTemplatesForm" property="reportTemplateId">
			<td>
				<%=ReportVersionConstants.getReportTitle(reportTemplatesForm.getReportId())%>
			</td>
		</logic:notEmpty>
		<logic:empty name="reportTemplatesForm" property="reportTemplateId">
			<td>
				<script type="text/javascript">
					var opts = <%=ReportUtils.getJSArrayReportTemplateType()%>;
					var opt = [new SelectOption('-1','<bean:message key="common.selectlist.select" />')];
					opts = opt.concat(opts);
					selectTemplateType = new SelectBox(opts,'<bean:write name="reportTemplatesForm" property="reportId" />','images/btn_dropdown.gif',{namesonly:false, width:'175px', size:20});
					document.write(selectTemplateType.getHtml());
					selectTemplateType.init();
				</script>
			</td>
		</logic:empty>
	</tr>
	<tr>
		<td>
			<bean:message key="report_templates.label.template_file"/>
		</td>
		<logic:notEmpty name="reportTemplatesForm" property="reportTemplateId">
			<td>
				<a href="#" onclick="javascript: viewDocument();" >
					<bean:write name="reportTemplatesForm" property="originalFileName" />
				</a>
			</td>
		</logic:notEmpty>
		<logic:empty name="reportTemplatesForm" property="reportTemplateId">
			<td>
				<div class="navBtn"  id="uploadDiv" style="float: none;" >
					<a href="#" style="width:120px;" class="active" onclick="javascript:uploadDocument();"><span class="rightC"></span><span class="leftC"></span><bean:message key="reportdesigner.label.upload_report_file"/></a>
				</div>
				<div id="uploadSucess" style="float: none;display: none;" >
					<a href="#" onclick="javascript: viewDocument();" id="uploadSucessLink" ></a>
				</div>
			</td>
		</logic:empty>
	</tr>
</table>
<div style="display: none;" id="sheetDetails">
	<table class="reportHeader" cellspacing="0" cellpadding="0" border="0"  width="100%">
		<tr>				  			
			<td class="head" colspan="2" style="border-top: 0px;" width="150px;">
				<b><bean:message key="report_templates.label.sheet_details"/></b>
			</td>
		</tr>
		<tr>
			<td>
				<bean:message key="report_templates.label.select_sheet"/>&nbsp;
			</td>
			<td>
				<script type="text/javascript">
                    var columns = <bean:write name='sheetDetails' scope='request' filter='false' />;
                    selectSheet = new SelectBox(columns,'<bean:write property="sheetIndex" name="reportTemplatesForm"/>','images/btn_dropdown.gif',{namesonly:false, width:'150px', size:20});
                    document.write(selectSheet.getHtml());
                    selectSheet.init();
		        </script>
			</td>
		</tr>
		<tr>
			<td>
			  	<bean:message key="reportdesigner.label.select_row"/>&nbsp;
		  	</td>
			<td style="vertical-align: top;">
				<script type="text/javascript">
					var opts = new Array();
					<%for(int i=0; i<50;i++){%>
					opts[<%=i%>] = new SelectOption('<%=i+1%>','<%=i+1%>');
					<%}%>
					selectRow = new SelectBox(opts,'<bean:write property="rowIndex" name="reportTemplatesForm"/>','images/btn_dropdown.gif',{namesonly:false, width:'80px', size:20});
					document.write(selectRow.getHtml());
					selectRow.init();
				</script>
			</td>			
			</tr>
	</table>
</div>
	<br/>
	<table class="tblPop" width="100%">
		<tr>
			<td>
				<div class="navBtn" style="float: right;">
					<a href="#" style="width:50px; margin-left:5px;display: none;" id="nextPage" class="active" onclick="javascript: nextPage();"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.next"/></a>
					<a href="#" style="width:60px; margin-left:5px;" class="active" onclick="javascript: onCancel();return false;"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.cancel"/></a>
				</div>
			</td>
		</tr>
	</table>	
</div>
</html:form>	
<div id="updater" style="display: none; position: absolute; top: 55px; left: 820px;width:120px;">
	<table>
	<tr>
		<td><img src="images/wait.gif"/></td>
		<td>Please wait....</td>
	</tr>
	</table>
</div>
<script type="text/javascript">
var editPage = false;

<logic:notEmpty name="reportTemplatesForm" property="reportTemplateId">
	editPage= true; 
</logic:notEmpty>
<logic:empty name="reportTemplatesForm" property="reportTemplateId">
	editPage= false;
</logic:empty>

function uploadDocument() {  
	var url="reportTemplates.do?mode=uploadTemplateFile&option=0";
	showInPopUp(url,500,160,null);
}

function viewDocument(){
	var filePath = document.reportTemplatesForm.reportFilePath.value;		
	var url = "<%=DocumentUtils.getDocumentURL("@fileName@","" )%>";
	url = url.replace(/(@fileName@)/g,filePath);
	window.open(url);
}

function showWait(state){
	showDiv('updater',state);
}

function showDiv(divId,show){
	if(show==0){
		$(divId).style.display="none";
	}else{
		$(divId).style.display="block";
	}
}

function showInPopUp(url,width,height,returnFun, close){
	showPopWin(url, width, height, returnFun,close);
}

function nextPage(){
	if(validate()){
		document.reportTemplatesForm.mode.value="reportTemplateColumnMapping";
		if(!editPage){
			document.reportTemplatesForm.reportId.value=selectTemplateType.getSelectedId();
		}
		document.reportTemplatesForm.sheetIndex.value=selectSheet.getSelectedId();
		document.reportTemplatesForm.rowIndex.value=selectRow.getSelectedId();
		document.reportTemplatesForm.submit();
	}
}

function validate(){
	if(!editPage){
		var reportType = selectTemplateType.getSelectedId();
		var filePath = document.reportTemplatesForm.reportFilePath.value;
		if(reportType=='' || reportType=="-1"){
			alert('<bean:message key="report_templates.error.template_type" />');
			return false;
		}else if(filePath==null || filePath==''){
			alert('<bean:message key="report_templates.error.template_file" />');
			return false;
		}	
	}
	return true;
}

function onCancel(){
	window.location.href="reportTemplates.do?mode=manageReportTemplates";
}

function onWindowLoad(){
	initPopUp();
	if(editPage){
		$('sheetDetails').style.display="";
		$('nextPage').style.display="";
	}else {
		if(document.reportTemplatesForm.originalFileName.value!=''){
			$('uploadDiv').style.display="none";
			$('uploadSucess').style.display="";
			$('uploadSucessLink').innerHTML=document.reportTemplatesForm.originalFileName.value;
		}

		if(document.reportTemplatesForm.reportFilePath.value!=''){
			var op = document.reportTemplatesForm.sheetsArray.value;
			var opts = eval(op);
			selectSheet.reInitialize(opts, '');
			$('sheetDetails').style.display="";
			$('nextPage').style.display="";
		}
	}
}

function fileUploaded(error,attachmentId, originalFileName, attachmentSize, labeledAttachmentSize, filePath, option){	
	if(error==''){
		if(option=='0'){
			document.reportTemplatesForm.reportFilePath.value = filePath;
			document.reportTemplatesForm.originalFileName.value = originalFileName;
			getExcelSheetDetails(filePath);
			$('uploadDiv').style.display="none";
			$('uploadSucess').style.display="";
			$('uploadSucessLink').innerHTML=originalFileName;
		}
	}else{
		alert(error);
	}
	showWait(false);
}

function getExcelSheetDetails(filePath){
  var pars = "mode=getExcelSheetDetails&reportFilePath=" + filePath;
  var myAjax = ajaxCall("reportTemplates.do",'get',pars,updateSheetDetailsDiv, reportError);
}

function updateSheetDetailsDiv(request){
	xmlFile = request.responseXML;
	if(!isValidSession(xmlFile,'<%=TPApplicationProperties.getProperty("redirect_on_session_expired")%>'))return;
	if(!isErrorXml(xmlFile)){
		var op = request.responseText;
		var opts = eval(op);
		document.reportTemplatesForm.sheetsArray.value=op;
		selectSheet.reInitialize(opts, '');
		$('sheetDetails').style.display="";
		$('nextPage').style.display="";
	}	
}

window.onload=onWindowLoad;
</script>

