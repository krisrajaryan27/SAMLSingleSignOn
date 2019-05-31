<%@page import="com.talentPool.positions.constants.PositionConfigurationConstants"%>
<%@page import="com.talentPool.positions.manager.PositionScreenConfigurationManager"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@page import="com.talentPool.positions.PositionConstants"%>
<%@page import="com.talentPool.common.properties.TPApplicationProperties"%>
<%@page import="com.talentPool.common.properties.GlobalApplicationProperties"%>
<%@page import="com.talentPool.common.properties.GlobalConstants"%>
<%@page import="com.talentPool.user.manager.ModuleSet"%>
<%@page import="com.talentPool.masters.constants.MastersConstants"%><style>
<!--
.filterPanel{padding-left: 15px;padding-top: 5px;}
-->
</style>

<script src="js/dhtmlxGrid/dhtmlXCommon.js"></script> 
<script src="js/dhtmlxGrid/dhtmlXGrid.js"></script> 
<script src="js/dhtmlxGrid/dhtmlXGridCell.js"></script> 
<script  src="js/dhtmlxGrid/dhtmlXGrid_excell_link.js"></script>
<script src="js/dhtmlxGrid/dhtmlXGrid_srnd.js"></script>
<script src="js/ajaxfunctions.js" type="text/javascript"></script>
<script src="js/scripta/lib/prototype.js" type="text/javascript"></script>
<script src="js/scripta/src/effects.js" type="text/javascript"></script>
<script src="js/selectoption.js" type="text/javascript"></script>
<script src="js/checkboxlist/checkboxlist.js" type="text/javascript"></script>
<script src="js/commonGridFunctions.js" type="text/javascript"></script>
<script src="js/submodal/common.js"></script>
<script src="js/submodal/subModal.js"></script>
<script src="js/cookies.js"></script>


<script>
var checkboxListPositions=null;
function onChangeFilter(lst){
   var showCondition = lst.getSelectedIds();
   var selectedText = lst.getSelectedOptions();
   if(showCondition==''){
   		dataGrid.clearAll();
   		alert('<bean:message key="position.home.error.select_filter_criteria"/> <bean:message key="common.positions"/> ');
   		addStatusCriteria('');
   }else{
	   	addStatusCriteria(selectedText);
   		loadGrid();
   }
}
function addStatusCriteria(selectedText){
   	criteriaPane.add(new criteriaOpt('<%=PositionConstants.FILTER_STATUS%>', selectedText));
   	criteriaPane.refreshCriteria();
}
function resetStatusPanel(){
	<logic:notEqual name="positionForm" property="showCondition" value="<%=PositionConstants.POSITION_STATUS_TEMPLATE%>">		
		checkboxListPositions.resetSelected('<%=PositionConstants.POSITION_STATUS_OPENED%>',opts);
		addStatusCriteria(checkboxListPositions.getSelectedOptions());
	</logic:notEqual>
	
}

</script>

<div style="margin-left:5px; ">
	<div style="margin-top:5px;width: 195px;background-color: #fdfdfd;border: 1px solid #99CC01; padding: 5px;">
	<table style="width: 100px;">
		<tr>
		<td class="Grey"><bean:message key="common.filters"/></td>
		<td align="right" class="Grey">[<a href="#" onclick="resetFilters();return false;" class="green"><bean:message key="common.reset"/></a>]</td>
		</tr>
	</table>
	<div style="margin-top:5px; width: 195px; display: none" id="criteriaDiv" ></div>
</div>
	<logic:notEqual name="positionForm" property="showCondition" value="<%=PositionConstants.POSITION_STATUS_TEMPLATE%>">			
		<div style="padding-top:15px;">
			<img src="images/ico_arrow_right.gif" style="margin-bottom: -2px;" id="filterimg_<%=PositionConstants.FILTER_STATUS %>">&nbsp;<a href="#" onclick="activatePanel('<%=PositionConstants.FILTER_STATUS %>')" class="leftTitle">Show</a>
		</div>
		<div id="filterpanel_<%=PositionConstants.FILTER_STATUS %>" class="filterPanel" style="display: none;margin-top: 5px;">
		    <script type="text/javascript">
		         var opts = <bean:write name="JSCriteria" scope="request" filter="false"/>;
		         <logic:notEmpty name="positionForm" property="showCondition">
		         checkboxListPositions = new CheckBoxList(opts,'<bean:write name="positionForm" property="showCondition"/>',{namesonly:false, layerclass:'checkboxlistdiv', width:'150px', size:10, imageclass:'chkboxclass', uncheckedimg:'images/checkboxunchecked.gif', checkedimg:'images/checkboxchecked.gif'});
		         </logic:notEmpty>
		          <logic:empty name="positionForm" property="showCondition">
		         checkboxListPositions = new CheckBoxList(opts,'<%=PositionConstants.POSITION_STATUS_OPENED%>',{namesonly:false, layerclass:'checkboxlistdiv', width:'150px', size:10, imageclass:'chkboxclass', uncheckedimg:'images/checkboxunchecked.gif', checkedimg:'images/checkboxchecked.gif'});
		         </logic:empty>
		         document.write(checkboxListPositions.getHtml());
		         checkboxListPositions.setOnChangeHandler(onChangeFilter);
		         checkboxListPositions.init();
		    </script>
		</div>
	</logic:notEqual>
	<div style="padding-top:15px;">
		<img src="images/ico_arrow_down.gif" style="margin-bottom: -2px;" id="filterimg_<%=PositionConstants.FILTER_POSITION %>">&nbsp;<a href="#" onclick="activatePanel('<%=PositionConstants.FILTER_POSITION %>')" class="leftTitle"><bean:message key="common.position"/></a>
	</div>
	<div id="filterpanel_<%=PositionConstants.FILTER_POSITION %>" class="filterPanel" style="display: block;">
		<table style="padding: 2; border-spacing: 2">
			<tr> 
		        <td><input type="text" name="positionName" id="positionName" style="width: 180px;" ></td> 
		    </tr> 
		</table>	
	</div>
	<%if(PositionScreenConfigurationManager.isDescriptionFieldShow(PositionConfigurationConstants.FIELD_POSITION_OWNER)){%>
	<div style="padding-top:15px;">
		<img src="images/ico_arrow_right.gif" style="margin-bottom: -2px;" id="filterimg_<%=PositionConstants.FILTER_POSITION_OWNER %>">&nbsp;<a href="#" onclick="activatePanel('<%=PositionConstants.FILTER_POSITION_OWNER %>')" class="leftTitle"><bean:message key="global.position_owner"/></a>
	</div>
	<div id="filterpanel_<%=PositionConstants.FILTER_POSITION_OWNER %>" class="filterPanel" style="display: none;">
	</div>
	<%} %>
	
	<%
		if(PositionScreenConfigurationManager.isDescriptionFieldShow(PositionConfigurationConstants.FIELD_POSITION_TYPE_EXT_INT)){
	%>
	
	<div style="padding-top:15px;">
		<img src="images/ico_arrow_right.gif" style="margin-bottom: -2px;" id="filterimg_<%=PositionConstants.FILTER_POSITION_TYPE %>">&nbsp;<a href="#" onclick="activatePanel('<%=PositionConstants.FILTER_POSITION_TYPE %>')" class="leftTitle"><bean:message key="position.description.position_type_ext_int"/></a>
	</div>	
	<div id="filterpanel_<%=PositionConstants.FILTER_POSITION_TYPE %>" class="filterPanel" style="display: none;">
	</div>
	<% } %>	
	<div style="padding-top:15px;">
		<img src="images/ico_arrow_right.gif" style="margin-bottom: -2px;" id="filterimg_<%=PositionConstants.FILTER_DEPARTMENT %>">&nbsp;<a href="#" onclick="activatePanel('<%=PositionConstants.FILTER_DEPARTMENT %>')" class="leftTitle"><%=GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_DEPARTMENT_LEVEL_1)%></a>
	</div>
	<div id="filterpanel_<%=PositionConstants.FILTER_DEPARTMENT %>" class="filterPanel" style="display: none;">	</div>
	
	<div style="padding-top:15px;">
		<img src="images/ico_arrow_right.gif" style="margin-bottom: -2px;" id="filterimg_<%=PositionConstants.FILTER_SUB_DEPARTMENT %>">&nbsp;<a href="#" onclick="activatePanel('<%=PositionConstants.FILTER_SUB_DEPARTMENT %>')" class="leftTitle"><%=GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_DEPARTMENT_LEVEL_2)%></a>
	</div>
	<div id="filterpanel_<%=PositionConstants.FILTER_SUB_DEPARTMENT %>" class="filterPanel" style="display: none;">	</div>
	
	<div style="padding-top:15px;">
		<img src="images/ico_arrow_right.gif" style="margin-bottom: -2px;" id="filterimg_<%=PositionConstants.FILTER_SUB_SUB_DEPARTMENT %>">&nbsp;<a href="#" onclick="activatePanel('<%=PositionConstants.FILTER_SUB_SUB_DEPARTMENT %>')" class="leftTitle"><%=GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_DEPARTMENT_LEVEL_3)%></a>
	</div>
	<div id="filterpanel_<%=PositionConstants.FILTER_SUB_SUB_DEPARTMENT %>" class="filterPanel" style="display: none;">	</div>
	
	<% if(MastersConstants.DEPARTMENT_LEVEL_4.equals(GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_MAX_DEPT_LEVEL)) || MastersConstants.DEPARTMENT_LEVEL_5.equals(GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_MAX_DEPT_LEVEL))){%>
	<div style="padding-top:15px;">
		<img src="images/ico_arrow_right.gif" style="margin-bottom: -2px;" id="filterimg_<%=PositionConstants.FILTER_SUB3_DEPARTMENT %>">&nbsp;<a href="#" onclick="activatePanel('<%=PositionConstants.FILTER_SUB3_DEPARTMENT %>')" class="leftTitle"><%=GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_DEPARTMENT_LEVEL_4)%></a>
	</div>
	<div id="filterpanel_<%=PositionConstants.FILTER_SUB3_DEPARTMENT %>" class="filterPanel" style="display: none;">	</div>
   <% }%>
	
	<% if(MastersConstants.DEPARTMENT_LEVEL_5.equals(GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_MAX_DEPT_LEVEL))){%>
	<div style="padding-top:15px;">
		<img src="images/ico_arrow_right.gif" style="margin-bottom: -2px;" id="filterimg_<%=PositionConstants.FILTER_SUB4_DEPARTMENT %>">&nbsp;<a href="#" onclick="activatePanel('<%=PositionConstants.FILTER_SUB4_DEPARTMENT %>')" class="leftTitle"><%=GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_DEPARTMENT_LEVEL_5)%></a>
	</div>
	<div id="filterpanel_<%=PositionConstants.FILTER_SUB4_DEPARTMENT %>" class="filterPanel" style="display: none;">	</div>
	<%} %>
	<div style="padding-top:15px;">
		<img src="images/ico_arrow_right.gif" style="margin-bottom: -2px;" id="filterimg_<%=PositionConstants.FILTER_LOCATION %>">&nbsp;<a href="#" onclick="activatePanel('<%=PositionConstants.FILTER_LOCATION %>')" class="leftTitle"><bean:message key="common.location"/></a>
	</div>
	<div id="filterpanel_<%=PositionConstants.FILTER_LOCATION %>" class="filterPanel" style="display: none;">
	</div>
	
	<div style="padding-top:15px;">
		<img src="images/ico_arrow_right.gif" style="margin-bottom: -2px;" id="filterimg_<%=PositionConstants.FILTER_RECRUITER %>">&nbsp;<a href="#" onclick="activatePanel('<%=PositionConstants.FILTER_RECRUITER %>')" class="leftTitle"><bean:message key="common.recruiter.user"/></a>
	</div>
	<div id="filterpanel_<%=PositionConstants.FILTER_RECRUITER %>" class="filterPanel" style="display: none;">
	</div>
	<div style="padding-top:15px;">
		<img src="images/ico_arrow_right.gif" style="margin-bottom: -2px;" id="filterimg_<%=PositionConstants.FILTER_PRIMARY_SKILLS %>">&nbsp;<a href="#" onclick="activatePanel('<%=PositionConstants.FILTER_PRIMARY_SKILLS %>')" class="leftTitle"><bean:message key="add_position.label.primarySkills"/></a>
	</div>
	<div id="filterpanel_<%=PositionConstants.FILTER_PRIMARY_SKILLS %>" class="filterPanel" style="display: none;">	</div>
	
	<div style="padding-top:15px;">
		<img src="images/ico_arrow_right.gif" style="margin-bottom: -2px;" id="filterimg_<%=PositionConstants.FILTER_CUSTOM_FIELD %>">&nbsp;<a href="#" onclick="openCustomFieldFilterScreen('<%=PositionConstants.FILTER_STATUS %>')" class="leftTitle"><bean:message key="common.custom_fields"/></a>
	</div>
	<div id="filterpanel_<%=PositionConstants.FILTER_CUSTOM_FIELD %>" class="filterPanel" style="display: none;margin-top: 5px;">
	</div>
</div>
<div style="border-bottom: 1px solid rgb(204, 204, 204);">&nbsp;</div>
<logic:notEqual name="positionForm" property="showCondition" value="<%=PositionConstants.POSITION_STATUS_TEMPLATE%>">		

<div id="publishOptionsDiv">
<div style="margin-left:18px; margin-right:18px;"  > 
 	<table style="width: 100%; border: 0; padding: 0; border-spacing: 0" id="publishOptionsTable"> 
    <tr> 
      <td colspan="2"><img src="images/spacer.gif" width="1" height="13" /></td> 
    </tr> 
    <tr> 
      <td colspan="2"><strong class="Grey"><bean:message key="position.home.publish_options"/></strong></td> 
    </tr> 
    <tr> 
      <td colspan="2"><img src="images/spacer.gif" width="1" height="8" /></td> 
    </tr>  
    <% if(ModuleSet.isMODULE_VENDOR()){ %>
	<logic:equal value="true" name="permissionSet" scope="session" property="PERMISSION_PUBLISH_POSITION">
 	<tr> 
	<logic:equal name="publish" scope="request" value="<%=PositionConstants.VENDOR_PORTAL%>">
      <td height="18" colspan="2" valign="bottom"><span class="greenBullet">&raquo;</span> <bean:message key="position.home.publish_option_vendor_portal"/></td> 
  	</logic:equal>
  	<logic:notEqual name="publish" scope="request" value="<%=PositionConstants.VENDOR_PORTAL%>">
      <td height="18" colspan="2" valign="bottom"><span class="greenBullet">&raquo;</span> <a href="position.do?mode=publishPositionsToVendors" class="green"><bean:message key="position.home.publish_option_vendor_portal"/></a></td> 
  	</logic:notEqual>
  	</tr>
  	</logic:equal>		
	<% } %>		  	
  	<% if(ModuleSet.isMODULE_EMPLOYEE()){ %>
	<logic:equal value="true" name="permissionSet" scope="session" property="PERMISSION_PUBLISH_POSITION_FOR_EMPLOYEE_PORTAL">
    <tr> 
    <logic:equal name="publish" scope="request" value="<%=PositionConstants.EMPLOYEE_PORTAL%>">
      <td height="18" colspan="2" valign="bottom"><span class="greenBullet">&raquo;</span> <bean:message key="position.home.publish_option_employee_portal"/></td> 
  	</logic:equal>
  	<logic:notEqual name="publish" scope="request" value="<%=PositionConstants.EMPLOYEE_PORTAL%>">
      <td height="18" colspan="2" valign="bottom"><span class="greenBullet">&raquo;</span> <a href="position.do?mode=getAllPositionsToPublish&publishType=<%=PositionConstants.PUBLISH_EMPLOYEE_PORTAL%>" class="green"><bean:message key="position.home.publish_option_employee_portal"/></a></td> 
  	</logic:notEqual>      
  	</tr>  
  	</logic:equal>		
	<% } %>		
	<% if(ModuleSet.isMODULE_WEB_INTEGRATION()){ %>
	<logic:equal value="true" name="permissionSet" scope="session" property="PERMISSION_PUBLISH_POSITION_TO_WEB_SITE">
    <tr> 
    <logic:equal name="publish" scope="request" value="<%=PositionConstants.CORPORATE_WEBSITE%>">
      <td height="18" colspan="2" valign="bottom"><span class="greenBullet">&raquo;</span> <bean:message key="position.home.publish_option_corporate_website"/></td> 
  	</logic:equal>
  	<logic:notEqual name="publish" scope="request" value="<%=PositionConstants.CORPORATE_WEBSITE%>">
      <td height="18" colspan="2" valign="bottom"><span class="greenBullet">&raquo;</span> <a href="position.do?mode=getAllPositionsToPublishToWebsite" class="green"><bean:message key="position.home.publish_option_corporate_website"/></a></td> 
  	</logic:notEqual>         
  	</tr>  
  	</logic:equal>		
	<% } %>		
  	<% if(ModuleSet.isMODULE_WALKIN()){ %>
	<logic:equal value="true" name="permissionSet" scope="session" property="PERMISSION_PUBLISH_POSITION_FOR_WALK_IN">
    <tr> 
    <logic:equal name="publish" scope="request" value="<%=PositionConstants.WALK_INS%>">
	  <td height="18" colspan="2" valign="bottom"><span class="greenBullet">&raquo;</span> <bean:message key="position.home.publish_option_walk_ins"/></td> 
  	</logic:equal>
  	<logic:notEqual name="publish" scope="request" value="<%=PositionConstants.WALK_INS%>">
	  <td height="18" colspan="2" valign="bottom"><span class="greenBullet">&raquo;</span> <a href="position.do?mode=getAllPositionsToPublish&publishType=<%=PositionConstants.PUBLISH_WALK_IN%>" class="green"><bean:message key="position.home.publish_option_walk_ins"/></a></td>       
  	</logic:notEqual>         
  	</tr>    
  	</logic:equal>		
	<% } %>
	<% if(ModuleSet.isMODULE_SOCIAL_NETWORK()){ %>		
	<logic:equal value="true" name="permissionSet" scope="session" property="PERMISSION_POST_JOB_ON_SOCIAL_MEDIA">
    <tr> 
    <logic:equal name="publish" scope="request" value="<%=PositionConstants.SOCIAL_MEDIA%>">
	  <td height="18" colspan="2" valign="bottom"><span class="greenBullet">&raquo;</span> <bean:message key="position.home.publish_option_socail_media"/></td> 
  	</logic:equal>
  	<logic:notEqual name="publish" scope="request" value="<%=PositionConstants.SOCIAL_MEDIA%>">
	  <td height="18" colspan="2" valign="bottom"><span class="greenBullet">&raquo;</span> <a href="position.do?mode=getAllPositionsToPublishToSocialMedia&publishType=<%=PositionConstants.PUBLISH_SOCIAL_MEDIA%>" class="green"><bean:message key="position.home.publish_option_socail_media"/></a></td>       
  	</logic:notEqual>         
  	</tr>    
  	</logic:equal>
  	<% } %>	
  	 <tr> 
    <logic:equal name="publish" scope="request" value="<%=PositionConstants.NAUKRI_PORTAL%>">
	  <td height="18" colspan="2" valign="bottom"><span class="greenBullet">&raquo;</span> <bean:message key="position.home.publish_option_naukri"/></td> 
  	</logic:equal>
  	<logic:notEqual name="publish" scope="request" value="<%=PositionConstants.NAUKRI_PORTAL%>">
	  <td height="18" colspan="2" valign="bottom"><span class="greenBullet">&raquo;</span> <a href="position.do?mode=getAllPositionsToPublishToNaukri&publishType=<%=PositionConstants.NAUKRI_PORTAL%>" class="green"><bean:message key="position.home.publish_option_naukri"/></a></td>       
  	</logic:notEqual>         
  	</tr>    
  </table> 
</div>
<div style="border-bottom: 1px solid rgb(204, 204, 204);">&nbsp;</div>
</div>
</logic:notEqual>
<script>
var rightArrow='images/ico_arrow_right.gif';
var downArrow='images/ico_arrow_down.gif';
function activatePanel(panelId){
	if($('filterimg_'+panelId).src.endsWith(rightArrow)){
		$('filterimg_'+panelId).src = downArrow;
		Effect.BlindDown('filterpanel_'+panelId,{duration:0.2});
		loadFilter(panelId);
		hideOtherPanels(panelId);
	}else{
		hidePanel(panelId)
	}
}

function setwait(panelId){
	if(panelId!='<%=PositionConstants.FILTER_POSITION %>'){
		$('filterpanel_'+ panelId).innerHTML="<table style=\"border:0px;\"><tr><td style=\"border:0px;\"><img src=images/wait.gif /></td><td style=\"border:0px;\">&nbsp;<bean:message key="common.please_wait"/></td></tr></table>";
	}
}
function hidePanel(panelId){
	if($('filterimg_'+panelId)!= null){
	$('filterimg_'+panelId).src = rightArrow;
	Effect.BlindUp('filterpanel_'+panelId,{duration:0.2});
	}
}
function hideOtherPanels(panelId){
	if(panelId!='<%=PositionConstants.FILTER_POSITION %>'){
		//hidePanel('<%=PositionConstants.FILTER_POSITION %>');
	}	
	if(panelId!='<%=PositionConstants.FILTER_POSITION_OWNER %>'){
		hidePanel('<%=PositionConstants.FILTER_POSITION_OWNER %>');
	}
	if(panelId!='<%=PositionConstants.FILTER_DEPARTMENT %>'){
		hidePanel('<%=PositionConstants.FILTER_DEPARTMENT %>');
	}
	if(panelId!='<%=PositionConstants.FILTER_SUB_DEPARTMENT %>'){
		hidePanel('<%=PositionConstants.FILTER_SUB_DEPARTMENT %>');
	}
	if(panelId!='<%=PositionConstants.FILTER_SUB_SUB_DEPARTMENT %>'){
		hidePanel('<%=PositionConstants.FILTER_SUB_SUB_DEPARTMENT %>');
	}
	if(panelId!='<%=PositionConstants.FILTER_SUB3_DEPARTMENT %>'){
		hidePanel('<%=PositionConstants.FILTER_SUB3_DEPARTMENT %>');
	}
	if(panelId!='<%=PositionConstants.FILTER_SUB4_DEPARTMENT %>'){
		hidePanel('<%=PositionConstants.FILTER_SUB4_DEPARTMENT %>');
	}
	if(panelId!='<%=PositionConstants.FILTER_RECRUITER %>'){
		hidePanel('<%=PositionConstants.FILTER_RECRUITER %>');
	}
	if(panelId!='<%=PositionConstants.FILTER_LOCATION %>'){
		hidePanel('<%=PositionConstants.FILTER_LOCATION %>');
	}
	if(panelId!='<%=PositionConstants.FILTER_POSITION_TYPE %>'){
		hidePanel('<%=PositionConstants.FILTER_POSITION_TYPE %>');
	}
	if(panelId!='<%=PositionConstants.FILTER_PRIMARY_SKILLS %>'){
		hidePanel('<%=PositionConstants.FILTER_PRIMARY_SKILLS %>');
	}
	<logic:notEqual name="positionForm" property="showCondition" value="<%=PositionConstants.POSITION_STATUS_TEMPLATE%>">		
	if(panelId!='<%=PositionConstants.FILTER_STATUS %>'){
		hidePanel('<%=PositionConstants.FILTER_STATUS %>');
	}
	</logic:notEqual>
	
}
function loadFilter(panelId){
	if(panelId!='<%=PositionConstants.FILTER_POSITION %>' && panelId!='<%=PositionConstants.FILTER_STATUS %>' && panelId!='<%=PositionConstants.FILTER_CUSTOM_FIELD %>'){
		setwait(panelId);		
		var pars = 'mode=getHTMLForFilter&filterFor='+panelId+getCriteriaQryString();
		var myAjax = ajaxCall("position.do","get",pars,renderFilter,reportError);
	}
}

function renderFilter(request){
	xmlFile = request.responseXML;	
	if(!isValidSession(xmlFile,'<%=TPApplicationProperties.getProperty("redirect_on_session_expired")%>'))return;
	if(!isErrorXml(xmlFile)){
		var op = request.responseText;
		var panelId = op.substring(0,op.indexOf("|"));
		op = op.substring(op.indexOf("|")+1);
		var selectedLink =  op.substring(0,op.indexOf("|"));
		op = op.substring(op.indexOf("|")+1);
		$('filterpanel_'+ panelId).innerHTML=getReplaced(op);
		criteriaPane.add(new criteriaOpt(panelId, selectedLink));
		criteriaPane.refreshCriteria();
	}else{
		alert('<bean:message key="common.error.unable_to_process_request"/>');
	}
	return false;
}
function getReplaced(txt){
	txt = txt.replace(/(&amp;nbsp;)/g,'&nbsp;');
	txt = txt.replace(/(&gt;)/g,'>');
	txt = txt.replace(/(&lt;)/g,'<');
	txt = txt.replace(/(&amp;gt;)/g,'&gt;');
	txt = txt.replace(/(&amp;lt;)/g,'&lt;');
	txt = txt.replace(/(&amp;quot;)/g,'&quot;');
	return txt;
}
function applyFilter(panelId, filterId){
	if(panelId=='<%=PositionConstants.FILTER_DEPARTMENT %>'){
		document.positionForm.departmentId.value=filterId;
	}else if(panelId=='<%=PositionConstants.FILTER_POSITION_OWNER %>'){
		document.positionForm.positionOwnerId.value=filterId;
	}else if(panelId=='<%=PositionConstants.FILTER_SUB_DEPARTMENT %>'){
		document.positionForm.subDepartmentId.value=filterId;
	}else if(panelId=='<%=PositionConstants.FILTER_SUB_SUB_DEPARTMENT %>'){
		document.positionForm.subSubDepartmentId.value=filterId;
	}else if(panelId=='<%=PositionConstants.FILTER_SUB3_DEPARTMENT %>'){
		document.positionForm.sub3DepartmentId.value=filterId;
	}else if(panelId=='<%=PositionConstants.FILTER_SUB4_DEPARTMENT %>'){
		document.positionForm.sub4DepartmentId.value=filterId;
	}else if(panelId=='<%=PositionConstants.FILTER_RECRUITER %>'){
		document.positionForm.recruiterId.value=filterId;
	}else if(panelId=='<%=PositionConstants.FILTER_LOCATION %>'){
		document.positionForm.locationId.value=filterId;
	} else if(panelId=='<%=PositionConstants.FILTER_PRIMARY_SKILLS %>'){
		document.positionForm.skillId.value=filterId;
	} else if(panelId=='<%=PositionConstants.FILTER_POSITION_TYPE %>'){
		document.positionForm.positionTypeExtInt.value=filterId;
	}else if(panelId=='<%=PositionConstants.FILTER_STATUS%>'){
		addStatusCriteria(filterId);
		resetStatusPanel();
	}else if(panelId=='<%=PositionConstants.FILTER_CUSTOM_FIELD%>'){
		resetCustomFieldPanel();
	}
	loadGrid();
	loadFilter(panelId);
	if(panelId=='<%=PositionConstants.FILTER_POSITION %>'){
		if(filterId!=''){
			filterId = filterId+'...';
		}
		criteriaPane.add(new criteriaOpt('<%=PositionConstants.FILTER_POSITION %>', filterId));
		criteriaPane.refreshCriteria();
	}
}
function resetCustomFieldPanel(){
	criteriaPane.add(new criteriaOpt('<%=PositionConstants.FILTER_CUSTOM_FIELD %>', ''));
	criteriaPane.refreshCriteria();
}
function resetFilters(){
	document.positionForm.positionOwnerId.value='';
	document.positionForm.departmentId.value='';
	document.positionForm.subDepartmentId.value='';
	document.positionForm.subSubDepartmentId.value='';
	document.positionForm.sub3DepartmentId.value='';
	document.positionForm.sub4DepartmentId.value='';
	document.positionForm.positionId.value='';
	document.positionForm.recruiterId.value='';
	document.positionForm.locationId.value='';
	document.positionForm.positionTypeExtInt.value='';	
	document.positionForm.skillId.value='';
	if(document.positionForm.customFieldFilterId){
		document.positionForm.customFieldFilterId.value='';
		document.positionForm.customFieldFilterType.value='';
		document.positionForm.customFieldFilterValue.value='';
	}
	$('positionName').value='';
	resetStatusPanel();
	loadGrid();
	loadActivePanel();
	criteriaPane.clearAll();
	criteriaPane.refreshCriteria();
	resetStatusPanel();
}
function loadActivePanel(){
	if($('filterimg_'+<%=PositionConstants.FILTER_POSITION_OWNER %>) && $('filterimg_'+<%=PositionConstants.FILTER_POSITION_OWNER %>).src.endsWith(downArrow)){
		loadFilter(<%=PositionConstants.FILTER_POSITION_OWNER %>);
	}else if($('filterimg_'+<%=PositionConstants.FILTER_DEPARTMENT %>).src.endsWith(downArrow)){
		loadFilter(<%=PositionConstants.FILTER_DEPARTMENT %>);
	}else if($('filterimg_'+<%=PositionConstants.FILTER_SUB_DEPARTMENT %>).src.endsWith(downArrow)){
		loadFilter(<%=PositionConstants.FILTER_SUB_DEPARTMENT %>);
	}else if($('filterimg_'+<%=PositionConstants.FILTER_SUB_SUB_DEPARTMENT %>).src.endsWith(downArrow)){
		loadFilter(<%=PositionConstants.FILTER_SUB_SUB_DEPARTMENT %>);
	}else if($('filterimg_'+<%=PositionConstants.FILTER_SUB3_DEPARTMENT %>) && $('filterimg_'+<%=PositionConstants.FILTER_SUB3_DEPARTMENT %>).src.endsWith(downArrow)){
		loadFilter(<%=PositionConstants.FILTER_SUB3_DEPARTMENT %>);
	}else if($('filterimg_'+<%=PositionConstants.FILTER_SUB4_DEPARTMENT %>) && $('filterimg_'+<%=PositionConstants.FILTER_SUB4_DEPARTMENT %>).src.endsWith(downArrow)){
		loadFilter(<%=PositionConstants.FILTER_SUB4_DEPARTMENT %>);
	}else if($('filterimg_'+<%=PositionConstants.FILTER_RECRUITER %>).src.endsWith(downArrow)){
		loadFilter(<%=PositionConstants.FILTER_RECRUITER %>);
	}else if($('filterimg_'+<%=PositionConstants.FILTER_LOCATION %>).src.endsWith(downArrow)){
		loadFilter(<%=PositionConstants.FILTER_LOCATION %>);
	} else if($('filterimg_'+<%=PositionConstants.FILTER_PRIMARY_SKILLS %>).src.endsWith(downArrow)){
		loadFilter(<%=PositionConstants.FILTER_PRIMARY_SKILLS %>);
	}else if($('filterimg_'+<%=PositionConstants.FILTER_POSITION %>).src.endsWith(downArrow)){
		loadFilter(<%=PositionConstants.FILTER_POSITION %>);
	}	
	else if($('filterimg_'+<%=PositionConstants.FILTER_POSITION_TYPE %>).src.endsWith(downArrow)){
		loadFilter(<%=PositionConstants.FILTER_POSITION_TYPE %>);
	} 
}

function applyPositionFilter(){
	applyFilter('<%=PositionConstants.FILTER_POSITION%>',$('positionName').value);
}

var observer=false;
function onPositionFilterChange(event){
var iKeyCode = event.keyCode;
	switch(iKeyCode) {
		default:
		if((iKeyCode>=48 && iKeyCode<=90) || (iKeyCode>=96 && iKeyCode<=105) || iKeyCode==Event.KEY_BACKSPACE || iKeyCode==Event.KEY_DELETE){
			if(observer) clearTimeout(observer);
			observer = setTimeout(applyPositionFilter.bind(this), 300);
		}
		
	}
}
//criteria class code
function deleteCriteria(panelId){
	if(panelId=='<%=PositionConstants.FILTER_POSITION %>'){
		$('positionName').value='';
	}else if(panelId=='<%=PositionConstants.FILTER_CUSTOM_FIELD %>'){
		if(document.positionForm.customFieldFilterId){
			document.positionForm.customFieldFilterId.value='';
			document.positionForm.customFieldFilterType.value='';
			document.positionForm.customFieldFilterValue.value='';
		}
	}
	applyFilter(panelId,'');
	loadActivePanel();
}

//called onload
function applyPreFilters(){
	var openPanels = 0;
	if(document.positionForm.positionOwnerId.value!=''){
		activatePanel('<%=PositionConstants.FILTER_POSITION_OWNER %>');
		openPanels++;
	}
	if(document.positionForm.departmentId.value!=''){
		activatePanel('<%=PositionConstants.FILTER_DEPARTMENT %>');
		openPanels++;
	}
	if(document.positionForm.subDepartmentId.value!=''){
		activatePanel('<%=PositionConstants.FILTER_SUB_DEPARTMENT %>');
		openPanels++;
	}
	if(document.positionForm.subSubDepartmentId.value!=''){
		activatePanel('<%=PositionConstants.FILTER_SUB_SUB_DEPARTMENT %>');
		openPanels++;
	}
	if(document.positionForm.sub3DepartmentId.value!=''){
		activatePanel('<%=PositionConstants.FILTER_SUB3_DEPARTMENT %>');
		openPanels++;
	}
	if(document.positionForm.sub4DepartmentId.value!=''){
		activatePanel('<%=PositionConstants.FILTER_SUB4_DEPARTMENT %>');
		openPanels++;
	}
	if(document.positionForm.locationId.value!=''){
		activatePanel('<%=PositionConstants.FILTER_LOCATION %>');
		openPanels++;
	}
	if(document.positionForm.positionTypeExtInt.value!=''){
		activatePanel('<%=PositionConstants.FILTER_POSITION_TYPE %>');
		openPanels++;
	}
	if(document.positionForm.positionId.value!=''){
		activatePanel('<%=PositionConstants.FILTER_POSITION %>');
		openPanels++;
	}
	if(document.positionForm.recruiterId.value!=''){
		activatePanel('<%=PositionConstants.FILTER_RECRUITER %>');
		openPanels++;
	}
	if(document.positionForm.skillId.value!=''){
		activatePanel('<%=PositionConstants.FILTER_PRIMARY_SKILLS %>');
		openPanels++;
	}
	<logic:notEqual name="positionForm" property="showCondition" value="<%=PositionConstants.POSITION_STATUS_TEMPLATE%>">		
	if(checkboxListPositions.getSelectedOptions()!=''){
		addStatusCriteria(checkboxListPositions.getSelectedOptions());
	}
	</logic:notEqual>

	if(openPanels>1){
		hideOtherPanels('');
	}
}

var criteriaPane=null; //initializehtth this variable on window load

function openCustomFieldFilterScreen(){
	var url = 'customFieldFilters.do?mode=openCustomFieldFilterScreen';
	window.setTimeout("showInPopUp('"+url+"',560, 300,onCustomFieldFilterSelected,true);", 10);
}
</script>
