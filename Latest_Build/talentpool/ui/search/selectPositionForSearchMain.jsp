<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ page import="org.apache.struts.Globals" %>
<%@ page import="com.talentPool.positions.PositionConstants"%>
<%@ page import="com.talentPool.positions.utils.PositionDraftUtils"%>
<%@ page import="com.talentPool.common.properties.TPApplicationProperties"%>
<%@ page import="com.talentPool.common.utils.Utils"%>
<script language="JavaScript" src="js/scripta/lib/prototype.js" type="text/javascript"></script>								
<script language="JavaScript" src="js/scripta/src/effects.js" type="text/javascript"></script>
<script language="JavaScript" src="js/commonFunctions.js" type="text/javascript"></script>	
<script language="JavaScript" src="js/selectoption.js" type="text/javascript"></script>
<script language="JavaScript" src="js/selectbox/selectbox.js" type="text/javascript"></script>
<script language="JavaScript" src="js/ajaxfunctions.js" type="text/javascript"></script>	
<script language="JavaScript" src="js/commonFunctions.js" type="text/javascript"></script>

<div class="contentDivPop" style="width:450px;">
	<% 
		if(request.getAttribute(Globals.ERROR_KEY)!=null){
	%>
			<table id="m_errortable" > 
				<tr>
			    <td class='header'>
		        <b><bean:message key="errors.following_errors"/></b>
			    </td>               
				</tr>
		    <tr>
	        <td class="message"><html:errors/></td>               
		    </tr>
			</table><br/><br/>
	<%
		}
	%>
	<div class="outerDiv">
		<div class="popupTop">
			<table class="tblPop">
				<tr>
					  <td class="header">
						  <bean:message key="search.by.requirements.department"/>:
					  </td>
					  <td>
						<script language="JavaScript">	
							var opts = <bean:write name="searchForm" property="jsDepartmentArray" filter="false"/>;
							var m = [new SelectOption('-1','<bean:message key="common.selectlist.select"/>')];
							opts = m.concat(opts);
							selectBoxDepartment = new SelectBox(opts,'-1','images/btn_dropdown.gif',{namesonly:false, width:'300px', size:5});
							document.write(selectBoxDepartment.getHtml());
							selectBoxDepartment.setOnChangeHandler('onChangeSelect');
							selectBoxDepartment.init();
						</script>
					</td>
				  </tr>
				  <tr>
					  <td class="header">
						  <bean:message key="search.by.requirements.location"/>:
					  </td>
					  <td>
						<script language="JavaScript">	
							var opts = <bean:write name="searchForm" property="jsLocationArray" filter="false"/>;
							var m = [new SelectOption('-1','<bean:message key="common.selectlist.select"/>')];
							opts = m.concat(opts);
							selectBoxLocation = new SelectBox(opts,'-1','images/btn_dropdown.gif',{namesonly:false, width:'300px', size:5});
							document.write(selectBoxLocation.getHtml());
							selectBoxLocation.setOnChangeHandler('onChangeSelect');
							selectBoxLocation.init();
						</script>
					</td>
				  </tr>
				   <tr>
					  <td class="header">
						  <bean:message key="common.position"/>:
					  </td>
					  <td>
						<script language="JavaScript">	
							var opts = <bean:write name="searchForm" property="jsPositionsArray" filter="false"/>;
							var m = [new SelectOption('-1','<bean:message key="common.selectlist.select"/>')];
							opts = m.concat(opts);
							selectBoxPosition = new SelectBox(opts,'-1','images/btn_dropdown.gif',{namesonly:false, width:'300px', size:5});
							document.write(selectBoxPosition.getHtml());
							selectBoxPosition.init();
						</script>
					</td>
				  </tr>
			 </table>
		 </div>
		 <div class="popupBody">
			<table class="tblPop" width="100%">
			<tr>
				<td>
				<div class="navBtn" style="float: right;">
				<a href="#" style="width:60px;" class="active" onclick="javascript: submitForm();"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.submit"/></a>
				<a href="#" style="width:60px; margin-left:5px;" class="active" onclick="javascript: window.top.hidePopWin(false);return false;"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.cancel"/></a>
				</div>
				</td>
			</tr>
			</table>
		</div>
  	</div>
	 
</div>

<script language="javascript">
var departmentId = "";
var locationId = "";
function onChangeSelect(){
	if(selectBoxDepartment.getSelectedId()!="-1"){
		departmentId = selectBoxDepartment.getSelectedId(); 
	}else{
		departmentId ="";
	}
	if(selectBoxLocation.getSelectedId()!="-1"){
		locationId = selectBoxLocation.getSelectedId();
	}else{
		locationId ="";
	}
	var pars = "mode=getXMLPositionsForDeparmentNLocation&departmentId=" +departmentId+"&locationId="+locationId;
    var myAjax = ajaxCall("doSearch.do",'get',pars,loadPositions, reportError);
}

function loadPositions(request){
	xmlFile = request.responseXML;
	if(!isValidSession(xmlFile,'<%=TPApplicationProperties.getProperty("redirect_on_session_expired")%>')){
		return;
	}
	if(!isErrorXml(xmlFile)){
		var positions = xmlFile.getElementsByTagName("positions")[0];
		var position= positions.getElementsByTagName("position");
		var opts = new Array();
		if(position != null) {
		  	for(var i = 0; i < position.length; i++){
		  		var id = position[i].getAttribute("id");
		  		var name = position[i].firstChild.nodeValue;
		  		opts[i] = new SelectOption(id, name);
		  	}
		}
        var m = [new SelectOption('-1','<bean:message key="common.selectlist.select"/>')];
        opts = m.concat(opts);
        selectBoxPosition.reInitialize(opts, '-1');
	}
}

var returnVal = null;
function submitForm(){
	returnVal= "";
	var positionId = selectBoxPosition.getSelectedId();
	returnVal +=positionId;

	departmentId = selectBoxDepartment.getSelectedId();
	returnVal +="_"+departmentId;

	locationId = selectBoxLocation.getSelectedId()
	returnVal +="_"+locationId;

	window.top.hidePopWin(true);
}

function actionOnLoad(){
		var title = '<b><bean:message key="search.by.requirements.window_title"/></b>';
		window.top.setPopTitle(title);
}
window.onload=actionOnLoad;
</script>