<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>

<%@ page import="org.apache.struts.Globals" %>
<%@ page import="com.talentPool.common.properties.TPApplicationProperties"%>
<%@ page import="com.talentPool.reportDesign.utils.ReportDesignUtils"%>
<%@ page import="com.talentPool.reportDesign.constants.ReportDesignConstants"%>
<%@ page import="com.talentPool.admin.dataobject.ReportLevelData" %>

<script language="JavaScript" src="js/scripta/lib/prototype.js" type="text/javascript"></script>								
<script language="JavaScript" src="js/scripta/src/effects.js" type="text/javascript"></script>
<script language="JavaScript" src="js/commonFunction.js"></script>
<script language="JavaScript" src="js/ajaxfunctions.js" type="text/javascript"></script>

<html:form action="/reportDesigner" onsubmit="submitForm();return false;">
<html:hidden property="mode" name="reportDesignForm"/>
<html:hidden property="reportId" name="reportDesignForm"/>
<html:hidden property="reportType" name="reportDesignForm"/>
<html:hidden property="reportCategory" name="reportDesignForm"/>
<html:hidden property="columns" name="reportDesignForm"/>
<html:hidden property="filters" name="reportDesignForm"/>
<html:hidden property="sortBy" name="reportDesignForm"/>
<html:hidden property="sortWith" name="reportDesignForm"/>
<html:hidden property="isSharedReport" name="reportDesignForm"/>
<html:hidden property="levelPermissions" name="reportDesignForm"/>
<html:hidden property="reportFormat" name="reportDesignForm"/>
<html:hidden property="reportFilePath" name="reportDesignForm"/>
<html:hidden property="sheetIndex" name="reportDesignForm"/>
<html:hidden property="rowIndex" name="reportDesignForm"/>

<div class="contentDiv">
	<table width="100%" border="0" cellspacing="0" cellpadding="0"> 
	  <tr> 
	    <td><div style="width:100px;" class="boxTab"><span class="rightC"></span><span class="leftC"></span>
	    <img src="images/blank_small.gif" align="absmiddle" />Description</div></td> 
	  </tr> 
   </table> 
	<table class="reportHeader" cellspacing="0" cellpadding="0" border="0"  width="100%">
		<tr>				  			
			<td class="head">
				<b>Save Report As</b>
			</td>
		</tr>
		<tr>				  			
			<td>
				 <table class="innerReport">
	  			 	<tr>
	  			 		<td>
  			 				<table  cellspacing="0" cellpadding="0" >
								<tr>
									<td class="header" width="110px;"><bean:message key="reportdesigner.label.reportname"/><span class="star">*&nbsp;</span>
									</td>
									<td>
										<html:text name="reportDesignForm" property="reportName" size="49" styleClass="" maxlength="100"/>
									</td>
								</tr>										
							</table>	
						</td>
	  			 	</tr>
	  			 	<tr>
	  			 		<td>
  			 				<table  cellspacing="0" cellpadding="0" >
  			 					<tr>
									<td class="header" width="110px;"><span>Report Description&nbsp;</span>
									</td>
									<td>									
										<html:textarea name="reportDesignForm" property="reportDescription" styleClass=""  cols="70" rows="4"/>
									</td>
								</tr>		
  			 				</table>
	  			 		</td>
	  			 	</tr>
	  			 	<tr>
	  			 		<td>
  			 				<table  cellspacing="0" cellpadding="0" >
  			 					<tr>
									<td class="header" width="110px;"><span>Report Access&nbsp;</span>
									</td>
									<td>				
										<logic:equal name="reportDesignForm" property="isSharedReport" value="<%=ReportDesignConstants.REPORT_ACCESS_SHARED%>">
										  	<img src="images/radiobutton.gif" name='reportAccess' id='img_<%=ReportDesignConstants.REPORT_ACCESS_PRIVATE%>' onclick="javascript:onRadioChange('reportAccess','<%=ReportDesignConstants.REPORT_ACCESS_PRIVATE%>');" style="margin-bottom:-1px;"/><bean:message key="reportdesigner.label.private"/>
											<img src="images/checkedradiobutton.gif" name='reportAccess' id='img_<%=ReportDesignConstants.REPORT_ACCESS_SHARED%>' onclick="javascript:onRadioChange('reportAccess','<%=ReportDesignConstants.REPORT_ACCESS_SHARED%>');" style="margin-bottom:-1px;"/><bean:message key="reportdesigner.label.shared"/>
										</logic:equal>					
										<logic:notEqual name="reportDesignForm" property="isSharedReport" value="<%=ReportDesignConstants.REPORT_ACCESS_SHARED%>">
										  	<img src="images/checkedradiobutton.gif" name='reportAccess' id='img_<%=ReportDesignConstants.REPORT_ACCESS_PRIVATE%>' onclick="javascript:onRadioChange('reportAccess','<%=ReportDesignConstants.REPORT_ACCESS_PRIVATE%>');" style="margin-bottom:-1px;"/><bean:message key="reportdesigner.label.private"/>
											<img src="images/radiobutton.gif" name='reportAccess' id='img_<%=ReportDesignConstants.REPORT_ACCESS_SHARED%>' onclick="javascript:onRadioChange('reportAccess','<%=ReportDesignConstants.REPORT_ACCESS_SHARED%>');" style="margin-bottom:-1px;"/><bean:message key="reportdesigner.label.shared"/>
										</logic:notEqual>		
									</td>
								</tr>		
  			 				</table>
	  			 		</td>
	  			 	</tr>
	  			 	<tr>
	  			 		<td>
	  			 			<div id="reportLevelsDiv" style="display:none;">
	  			 				<table  cellspacing="0" cellpadding="0" >
	  			 					<tr>
	  			 						<td class="header" width="110px;">
										  	<span>Share Report with:</span>
									  	</td>
	  			 						<td style="vertical-align: top;">
	  			 						<div class="checkboxlistdiv"  style="padding:0px 3px 3px;">
											<logic:iterate id="levels" name="reportLevels" scope="request">										
												<table cellspacing=0 cellpadding=0 border=0>
													<tr>
														<td style="padding:2px 2px 0px 4px;">													
																<img src="images/checkboxunchecked.gif" name='imgLevelPermission' id='<bean:write name="levels" property="levelId" />' onclick="javascript: toggleChkBox(this);">														
														</td>
														<td style="padding:5px 0px 0px 0px;" width="250px;">
															All users having&nbsp;<bean:write name="levels" property="levelName" />&nbsp;data access
														</td>
													</tr>
												</table>													
											</logic:iterate>
											</div>	
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
	<br/>
	<table class="tblPop" width="100%">
		<tr>
			<td>
				<div class="navBtn" style="float: right;">
					<a href="#" style="width:50px;" class="active" onclick="javascript: previousPage();"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.back"/></a>
					<a href="#" style="width:50px; margin-left:5px;" class="active" onclick="javascript: nextPage();"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.save"/></a>
					<a href="#" style="width:60px; margin-left:5px;" class="active" onclick="javascript: onCancel();return false;"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.cancel"/></a>
				</div>
			</td>
		</tr>
	</table>
	<br/>
</div>
</html:form>



<script language="javascript">
var chkedChkBox = 'images/checkboxchecked.gif';
var unChkedChkBox = 'images/checkboxunchecked.gif';

function toggleChkBox(obj) {
	var source = obj.src;
	if (source.indexOf(chkedChkBox) != -1) {
		obj.src = unChkedChkBox;		
	} else {
		obj.src = chkedChkBox;
	}	
}

function onRadioChange(imgGroupName, attachmentId){
	var imgs = document.getElementsByName(imgGroupName);
	for (i = 0; i < imgs.length; i++) {
			var theImage = imgs[i];
			if (theImage.id.indexOf("img") > -1) {
				if( theImage.id == 'img_'+attachmentId){
					theImage.src = "images/checkedradiobutton.gif";
					document.reportDesignForm.isSharedReport.value=<%=ReportDesignConstants.REPORT_ACCESS_SHARED%>;
				}else{
					theImage.src = "images/radiobutton.gif";
					document.reportDesignForm.isSharedReport.value=<%=ReportDesignConstants.REPORT_ACCESS_PRIVATE%>;
				}
			}
	}
	if(document.reportDesignForm.isSharedReport.value==<%=ReportDesignConstants.REPORT_ACCESS_PRIVATE%>){
		$("reportLevelsDiv").style.display="none";
	}else{
		$("reportLevelsDiv").style.display="block";
	}
}

function getStrLevelPermissions(){
	var name=document.getElementsByName("imgLevelPermission");
	var levelPermissions='';
	for (var i = 0; i < name.length; i++) {
		var obj = name[i];
		var objId = obj.id;
		var imageSource = obj.src;
		if(imageSource.indexOf(unChkedChkBox)==-1){
				levelPermissions = levelPermissions+","+objId;
		}		
	}
	if(levelPermissions.length>0){
		levelPermissions = levelPermissions.substr(1);
	}
	return levelPermissions;
}

function submitForm(){
	
	document.reportDesignForm.submit();
}

function onWindowLoad(){										
	<logic:equal name="reportDesignForm" property="isSharedReport" value="<%=ReportDesignConstants.REPORT_ACCESS_SHARED%>">
	
		if(document.reportDesignForm.levelPermissions.value.length>0){
			var permissions = document.reportDesignForm.levelPermissions.value.split(',');
			for (var i = 0; i < permissions.length; i++) {
				document.getElementById(permissions[i]).src=chkedChkBox;
			}
		}
		$("reportLevelsDiv").style.display="block";
	</logic:equal>	
}

function getSelectedIds(){
	return dataGrid.getAllRowIds(",");
}

function nextPage(){
	if(document.reportDesignForm.reportName.value==''){
		alert('Please enter <bean:message key="reportdesigner.label.reportname"/>');
		return false;
	}
	if(document.reportDesignForm.isSharedReport.value==<%=ReportDesignConstants.REPORT_ACCESS_SHARED%>){
		var levelPermissions = getStrLevelPermissions();
		if(levelPermissions==''){
			alert('Please select atleast one Data Access Level');
			return false;
		}
		document.reportDesignForm.levelPermissions.value=levelPermissions;
	}
	document.reportDesignForm.mode.value="saveReportDesign";
	document.reportDesignForm.submit();
}

function previousPage(){
	document.reportDesignForm.mode.value="selectFilters";
	document.reportDesignForm.submit();
}

function onCancel(){
	window.location.href="reports.do?mode=reportFilter&reportName=";
}

window.onload=onWindowLoad;
</script>