<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>

<%@page import="com.talentPool.reportDesign.constants.ReportDesignConstants"%>
<%@page import="org.apache.struts.Globals"%>

<script language="JavaScript" src="js/scripta/lib/prototype.js" type="text/javascript"></script>								
<script language="JavaScript" src="js/scripta/src/effects.js" type="text/javascript"></script>
<script language="JavaScript" src="js/selectoption.js" type="text/javascript"></script>
<script language="JavaScript" src="js/selectbox/selectbox.js" type="text/javascript"></script>
<script language="JavaScript" src="js/commonFunction.js"></script>
<script language="JavaScript" src="js/ajaxfunctions.js" type="text/javascript"></script>
<script src="js/submodal/common.js"></script>
<script src="js/submodal/subModal.js"></script>

<html:form action="/reportDesigner" onsubmit="submitForm();return false;">
<html:hidden property="mode" name="reportDesignForm"/>
<html:hidden property="reportId" name="reportDesignForm"/>
<html:hidden property="reportType" name="reportDesignForm"/>
<html:hidden property="reportName" name="reportDesignForm"/>
<html:hidden property="columns" name="reportDesignForm"/>
<html:hidden property="reportFormat" name="reportDesignForm"/>
<html:hidden property="sortBy" name="reportDesignForm"/>
<html:hidden property="sortWith" name="reportDesignForm"/>
<html:hidden property="filters" name="reportDesignForm"/>
<html:hidden property="reportDescription" name="reportDesignForm"/>
<html:hidden property="isSharedReport" name="reportDesignForm"/>
<html:hidden property="levelPermissions" name="reportDesignForm"/>
<html:hidden property="reportFilePath" name="reportDesignForm"/>
<html:hidden property="sheetIndex" name="reportDesignForm"/>
<html:hidden property="rowIndex" name="reportDesignForm"/>

<div class="contentDiv">
	<%
		if(request.getAttribute(Globals.ERROR_KEY)!=null){
	%>
	<table  id="m_errortable" >
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
	    <td><div style="width:100px;" class="boxTab"><span class="rightC"></span><span class="leftC"></span>
	    <img src="images/blank_small.gif" align="absmiddle" />Upload File</div></td> 
	  </tr> 
   </table> 
   <table class="reportHeader" cellspacing="0" cellpadding="0" border="0"  width="100%">    
	<tr>				  			
		<td class="head">
			<b>Select Pre formatted Excel Report file</b>
		</td>
	</tr>
	
	<tr>
		<td>
			<div class="navBtn"  id="upload" style="float: none;display: block;">
				<a href="#" style="width:120px;" class="active" onclick="javascript:uploadDocument();"><span class="rightC"></span><span class="leftC"></span><bean:message key="reportdesigner.label.upload_report_file"/></a>
			</div>
			<div class="navBtn" id="success" style="float: left;display: none;">
				<bean:message key="reportdesigner.upload.file_uploaded_success_message"/>.
			</div>
		</td>
	</tr>
	
   </table>
	<br/>
	<table class="tblPop" width="100%">
		<tr>
			<td>
				<div class="navBtn" style="float: right;">
					<a href="#" style="width:50px;" class="active" onclick="javascript: previousPage();"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.back"/></a>
					<a href="#" style="width:50px; margin-left:5px;" class="active" onclick="javascript: nextPage();"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.next"/></a>
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

function validate(filePath){
	if(filePath==null || filePath==''){
		alert("Please upload "+'<bean:message key="reportdesigner.upload.report_file_upload.label" />');
		return false;
	}
	return true;
}

function uploadDocument() {  
	var url="importResume.do?mode=addDocument&option=0";
	showInPopUp(url,500,160,null);
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

function previousPage(){
	document.reportDesignForm.mode.value="selectColumns";
	document.reportDesignForm.submit();
}

function nextPage(){
	var filePath = document.reportDesignForm.reportFilePath.value;
	if(validate(filePath)){
		document.reportDesignForm.mode.value="selectReportSheet";
		document.reportDesignForm.submit();
	}
}

function onCancel(){
	window.location.href="reports.do?mode=reportFilter&reportName=";
}

function onWindowLoad(){
	initPopUp();
	if(document.reportDesignForm.reportFilePath.value!=''){
		$('upload').style.display="none";
		$('success').style.display="block";
	}
}


function fileUploaded(error,attachmentId, originalFileName, attachmentSize, labeledAttachmentSize, filePath, option){	
	if(error==''){
		if(option=='0'){
			document.reportDesignForm.reportFilePath.value = filePath;
			$('upload').style.display="none";
			$('success').style.display="block";
		}
	}else{
		alert(error);
	}
	showWait(false);
}

window.onload=onWindowLoad;
</script>