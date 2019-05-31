<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ page import="org.apache.struts.Globals,
                  com.talentPool.common.NavigationConstants, 
                  com.talentPool.user.dataobject.RoleData,
                  com.talentPool.user.UserConstants, com.talentPool.reports.dataobject.ReportIdName,
                  com.talentPool.common.utils.CommonUtils"%>
<%@ page import="java.util.ArrayList" %>
<link rel="stylesheet" type="text/css"	href="themes/default/popupiframe.css">
<script language="JavaScript" src="js/scripta/lib/prototype.js" type="text/javascript"></script>
<script language="JavaScript" src="js/selectoption.js" type="text/javascript"></script>
<script language="JavaScript" src="js/selectbox/selectbox.js" type="text/javascript"></script> 
<script language="JavaScript" src="js/scripta/src/effects.js" type="text/javascript"></script>
<script language="JavaScript" >
var selectBoxSource = null;
</script>

<logic:present name="update" scope="request">	
	<script>
		window.top.hidePopWin(true);
	</script>
</logic:present> 

<logic:notPresent name="update" scope="request">
<div class="contentDivPop" style="width: 560px;">
	<% 
		if(request.getAttribute(Globals.ERROR_KEY)!=null){
	%>
		<table  id="m_errortable" > 
			<tr>
			    <td class="header">
			        <b><bean:message key="errors.following_errors"/></b>
			    </td>               
			</tr>
		    <tr>
		        <td class="message"><html:errors/></td>               
		    </tr>
		</table>
		<br>
	<% } 

	%>
	
	<div class="outerDiv">
		<html:form action="/adminHome" onsubmit="return submitForm();">
		<html:hidden property="mode" value="saveLevel"/>
		<html:hidden property="levelId" name="adminForm"/>
		<html:hidden property="levelsReportId" name="adminForm"/>
		
		<div class="popupTop">
			<table width="100%" border="0" cellspacing="0" cellpadding="0" class="tblPop">			
				<tr>
        		<td class="header" nowrap="nowrap">
            		<bean:message key="add_level.label.levelName"/>
            		&nbsp;&nbsp;<html:text property="levelName" size="30" maxlength="50" name="adminForm"></html:text>
            	</td>   
	           	
	           </tr>
	           </table>
		</div>	
		<div class="popupBody">	
			<table width="100%" border="0" cellspacing="0" cellpadding="0" class="tblPop">	           		    
				<%	
				ArrayList reportIdNameList=(ArrayList)request.getAttribute("reportIdNameList");
				String categoryName="";
				ReportIdName reportIdName=null;
				for(int t=0; t<reportIdNameList.size();t++){
					reportIdName=(ReportIdName)reportIdNameList.get(t);						
				%>
				<tr>
					<% if(!categoryName.equals(reportIdName.getReportCategory())){
					%>
					<td style="padding:10px 0px 5px 0px;"><b>
					<%= reportIdName.getReportCategory() %>
					</b>
					</td>
					<% } %>
				</tr>		
				<tr>					
					<td>
					<%	if(reportIdName.getIsReportSelected()){
					%>
						&nbsp;&nbsp;&nbsp;&nbsp;<img src="images/checkboxchecked.gif" name='imgReportId' id='<%=reportIdName.getReportId() +"_"+reportIdName.getReportType()%>' onclick="javascript:toggleChkBox(this)">
					<% } else{%>				
						&nbsp;&nbsp;&nbsp;&nbsp;<img src="images/checkboxunchecked.gif" name='imgReportId' id='<%=reportIdName.getReportId() +"_"+reportIdName.getReportType()%>' onclick="javascript:toggleChkBox(this)">
					<%
					}%>
					<%= reportIdName.getReportName() %>	
					<% categoryName=reportIdName.getReportCategory();
					}	%>
					</td>
				</tr>					
			
				<tr>
					<td>
					</td>
				</tr>
			</table>
		</div>
</html:form>
</div>
<div class="navBtn" style="float:right;margin-left:5px;margin-top:5px;"><a href="#" style="width:50px;" class="active" onclick="javascript:if(submitForm()){document.adminForm.submit();return true;}"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.save"/></a>
<a href="#" style="width:60px;margin-left:5px;" class="active" onclick="javascript:window.top.hidePopWin(false);"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.cancel"/></a></div>
<br/><br/><br/><br/><br/><br/>						

</logic:notPresent>
<script>

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
function setLevelsReportId(){
	var reportIds=document.getElementsByName("imgReportId");
	document.adminForm.levelsReportId.value="";
	var levelsReport='';
	for (var i = 0; i < reportIds.length; i++) {
		var obj = reportIds[i];		
		var objId = obj.id;
		var imageSource = obj.src;						
		if(imageSource.indexOf(unChkedChkBox)==-1){					
			if(levelsReport.length>0){
				levelsReport += ",";
			}
			levelsReport += objId;
		}
	}
	document.adminForm.levelsReportId.value=levelsReport;	
}

function submitForm(){
	setLevelsReportId();
	return true;
}

function setPopupTitle(){
	var title = '<b>' + '<bean:write property="levelName" name="adminForm" />' + '</b>';
	<logic:empty name="adminForm" property="levelId">
	  title = '<b><bean:message key="add_level.label.add_new_level"/></b>';
	</logic:empty>
	window.top.setPopTitle(title);
}

function doOnLoad() {  	
  	setPopupTitle();
}

window.onload = doOnLoad;

</script>