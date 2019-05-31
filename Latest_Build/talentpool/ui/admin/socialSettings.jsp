<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="com.talentPool.common.properties.TPApplicationProperties"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<script language="JavaScript" src="js/scripta/lib/prototype.js" type="text/javascript"></script>
<script language="JavaScript" src="js/scripta/src/effects.js" type="text/javascript"></script>
<script language="JavaScript" src="js/ajaxfunctions.js" type="text/javascript"></script>
<script language="JavaScript" src="js/utils/updater.js" type="text/javascript"></script>
<script src="js/dhtmlxGrid/dhtmlXCommon.js"></script>
<script src="js/dhtmlxGrid/dhtmlXGrid.js"></script>
<script src="js/dhtmlxGrid/dhtmlXGridCell.js"></script>
<script src="js/dhtmlxGrid/dhtmlXGrid_excell_link.js"></script>
<script src="js/dhtmlxGrid/dhtmlXGrid_srnd.js"></script>
<body>
<s:hidden name="userId" id="userId" value="%{#request['userId']}"/>
<div class="contentDiv">
<s:form name="linkedInSettings" id="linkedInSettings" method="POST" action="saveLinkedInSettings">
	<div class="boxTab" style="width:200px;">
		<span class="rightC"></span><span class="leftC"></span><s:text name="admin.social_network.LinkedIn_settings" />
	</div>
	<div class="outerDiv" style="width: 600px;" >
		<table class="posinput" width="100%" style="padding: 15px 0px 15px 30px;">
			<tr>
				<td class="label" width="150px">
			 		<s:text name="admin.social_network.client_Id"/>  
		 		 </td>
				  <td>
			 		: &nbsp;&nbsp;
			 		<s:textfield name="linkedInClientId" size="20"  id="linkedInClientId" value=""/>	
				  </td>
			</tr>
			<tr>
				<td class="label" width="150px">
			 		<s:text name="admin.social_network.client_secret"/>  
				</td>
			  	<td>
			 		: &nbsp;&nbsp;
			 		<s:textfield name="linkedInClientSecret" size="20" id="linkedInClientSecret" value=""/>	
			 	</td>
			</tr>
			<tr>
				 <td class="label" width="150px">
					<s:text name="admin.social_network.LinkedIn.companyIds"></s:text>
				</td> 
				<td>
			 		: &nbsp;&nbsp;
			 		<s:textfield name="linkedInCompanyIds" size="20" id="linkedInCompanyIds" value=""/>	
			 		<div style="font-size: 10px;"><i>{<s:text name="admin.social_network.LinkedIn.companyTip"/>}</i></div>
				</td>
			</tr>
		</table>
		<div class="navBtn" style="float: right;margin-top: 5px;">
			<a href="#" style="width:100px; margin-left:5px;" class="active" onclick="document.linkedInSettings.reset();" id="reset">
				<span class="rightC"></span><span class="leftC"></span><s:text name="common.reset"/>
			</a>
		</div>
		<div class="navBtn" style="float: right;margin-top: 5px;">
			<a href="#" style="width:100px; margin-left:5px;" class="active" onclick="javascript: save('1');">
				<span class="rightC"></span><span class="leftC"></span><s:text name="common.save"/>
			</a>
		</div>
	</div>
	
	</s:form>
<br/><br/>
<s:form name="facebookSettings" id="facebookSettings" method="POST" action="saveFacebookSettings">
	<div class="boxTab" style="width:200px;">
		<span class="rightC"></span><span class="leftC"></span><s:text name="admin.social_network.facebook_settings" />
	</div>
	<div class="outerDiv" style="width: 600px;" >
		<table class="posinput" width="100%" style="padding: 15px 0px 15px 30px;">
			<tr>
				<td class="label" width="150px">
			 		<s:text name="admin.social_network.client_Id"/>  
			 </td>
			  <td>
			 	: &nbsp;&nbsp;
			 	<s:textfield name="facebookClientId" size="20"  id="facebookClientId" value=""/>	
			 </td>
			</tr>
			<tr>
				<td class="label" width="150px">
			 		<s:text name="admin.social_network.client_secret"/>  
			 </td>
			  <td>
			 	: &nbsp;&nbsp;
			 	<s:textfield name="facebookClientSecret" size="20" id="facebookClientSecret" value=""/>	
			 </td>
			</tr>
		</table>
	<div class="navBtn" style="float: right;margin-top: 5px;">
		<a href="#" style="width:100px; margin-left:5px;" class="active" onclick="document.facebookSettings.reset();" id="reset">
			<span class="rightC"></span><span class="leftC"></span><s:text name="common.reset"/>
		</a>
	</div>
	<div class="navBtn" style="float: right;margin-top: 5px;">
		<a href="#" style="width:100px; margin-left:5px;" class="active" onclick="javascript: save('2');">
			<span class="rightC"></span><span class="leftC"></span><s:text name="common.save"/>
		</a>
	</div>
	</div>
	</s:form>
	<br/><br/>
	<s:form name="socialSettings" id="socialSettings" method="POST" action="saveSocialSettings" >
	<div class="boxTab" style="width:200px;">
		<span class="rightC"></span><span class="leftC"></span><s:text name="admin.social_network.socialGroupCompany_settings" />
	</div>
	<div class="outerDiv" style="width: 600px;" >
		<table class="posinput" width="100%" style="padding: 15px 0px 15px 30px;"> 
		  <tr>
		  	 <td class="label" width="150px">
			 	<s:text name="admin.social_network.group_id"/>  
			 </td>
			 <td>
			 	: &nbsp;&nbsp;
			 	<s:textfield name="socialId" size="20" maxlength="20"  id="groupId" value=""/>	
			 </td>
		  </tr>
		  <tr>
		  	 <td class="label" width="150px">
				<s:text name="admin.social_network.group_name" /> 
			 </td>
			 <td>
			 	: &nbsp;&nbsp;
			 	<s:textfield name="socialName" size="50" maxlength="100" id="groupName" value=""/>	
			 </td>
		  </tr>
		  <tr>
		  	 <td class="label" width="150px">
			 	<s:text name="admin.social_network.group_url" /> 
			 </td>
			 <td>
			 	: &nbsp;&nbsp;
			 	<s:textfield name="url" size="50" maxlength="150" id="groupURL" value="" />	
			 </td>
		  </tr>	
		    <tr>
		  	 <td class="label" width="150px">
			 	<s:text name="admin.social_network.social_mediaType" /> 
			 </td>
			 <td>
			 	: &nbsp;&nbsp;
				<s:select name= "selectedSources" id="mediaType"  list="sources" listValue="apiType" listKey="implementationId" headerKey="-1" headerValue="-- Select --" value="-1"/>
			 </td>
		  </tr>	  
		</table>
		<div class="navBtn" style="float: right;margin-top: 5px;">
			<a href="#" style="width:100px; margin-left:5px;" class="active" onclick="javascript: reset();" id="reset">
				<span class="rightC"></span><span class="leftC"></span><s:text name="common.reset"/>
			</a>
		</div>
		<div class="navBtn" style="float: right;margin-top: 5px;">
			<a href="#" style="width:100px; margin-left:5px;" class="active" onclick="javascript: save('3');" id="save_update_button">
				<span class="rightC"></span><span class="leftC"></span><s:text name="common.save"/>
			</a>
		</div>
		
	</div>
	<div id="saveStatus" class="contentDiv" style="color: red"></div>
	<br/><br/>	
	<div class="boxTab" style="width:200px;" >
		<span class="rightC"></span><span class="leftC"></span><s:text name="admin.social_network.GroupList" />
	</div>
	<table cellpadding="0" cellspacing="0">
		<tr>
			<td class="gridborder">
				<div id="groupGridBox"  style="width: 600px; height: 20px;"></div>
				</td>
		</tr>
	</table>
	</s:form>		
	<br/><br/>		
</div>
<br/><br/><br/><br/>

</body>
<script type="text/javascript">
var groupGridBox= null;
var row='';
function save(formcount){
	if(formcount=='1'){
		var clientId=$("linkedInClientId").value;
		var clientSecret=$("linkedInClientSecret").value;
		if(clientId.length == 0){
			alert('<s:text name="alert.socialSettings.clientId"/>');
			$("linkedInClientId").focus();
			return false;	
		}
		if(clientSecret.length == 0){
			alert('<s:text name="alert.socialSettings.clientSecret"/>');
			$("linkedInClientSecret").focus();
			return false;	
		}
		document.linkedInSettings.submit();
	}
	
	if(formcount=='2'){
		var clientId=$("facebookClientId").value;
		var clientSecret=$("facebookClientSecret").value;
		if(clientId.length == 0){
			alert('<s:text name="alert.socialSettings.clientId"/>');
			$("facebookClientId").focus();
			return false;	
		}
		if(clientSecret.length == 0){
			alert('<s:text name="alert.socialSettings.clientSecret"/>');
			$("facebookClientSecret").focus();
			return false;	
		}
		document.facebookSettings.submit();
	}
	
	if(formcount=='3'){
		var groupId = $("groupId").value;
		var groupName = $("groupName").value;
		var mediaType = $("mediaType").value;
		if(groupId.length == 0){
			alert('<s:text name="alert.socialSettings.groupId"/>');
			return false;
		}
		if(groupName.length == 0){
			alert('<s:text name="alert.socialSettings.groupName"/>');
			return false;
		}
		if(mediaType == '-1'){
			alert('<s:text name="alert.socialSettings.groupMediaType"/>');
			return false;
		}
		document.socialSettings.submit();
	}
}

function initGroupGridBox(){
	
	groupGridBox = new dhtmlXGridObject('groupGridBox'); 
	groupGridBox.imgURL = "images/dhtmlxGrid/"; 
	groupGridBox.setHeader("&nbsp,<s:text name="admin.social_network.group_name" /> ,<s:text name="admin.social_network.social_mediaType" /> ,'','',''"); 
	groupGridBox.setInitWidths("18,280,280,1,1,1");
	groupGridBox.setColAlign("left,left,left,left,left,left");
	groupGridBox.setColTypes("ro,ro,ro,ro,ro,ro"); 
	groupGridBox.setColSorting("na,na,na,na,na,na");
	groupGridBox.checkSessionExpiry(true,'<%=TPApplicationProperties.getProperty("redirect_on_session_expired")%>');
	groupGridBox.enableAutoHeigth(true,"300");
	groupGridBox.enableMultiselect(false);
	groupGridBox.attachEvent("onRowSelect",onGroupSelect);
	groupGridBox.init();
	groupGridBox.setColumnHidden(3,true);
	groupGridBox.setColumnHidden(4,true);
	groupGridBox.setColumnHidden(5,true);
	groupGridBox.loadXML("loadGroups.action");
	
}

function deleteField(id){
	row=id;
	var gId =  groupGridBox.cells(id,5).getValue();
	var impId= groupGridBox.cells(id,4).getValue();
	var pars = "rowId="+id+"&groupId="+gId+"&impId="+impId;
	var myAjax = ajaxCall("deleteSocialGroup.action",'post',pars,deleteFromGrp, reportError);
}

function deleteFromGrp(request){
	groupGridBox.deleteRow(row);
	groupGridBox.clearSelection();
	groupGridBox.loadXML("loadGroups.action");
	reset();
}

function onGroupSelect(){
	var id = groupGridBox.getSelectedId();  
	
    var name = groupGridBox.cells(id,1).getValue();
    $("groupName").value=name;
    
    var type = groupGridBox.cells(id,4).getValue();
    $("mediaType").options[type].selected = true;
    
    var url =  groupGridBox.cells(id,3).getValue();
    $("groupURL").value= url;
	
    var gid =  groupGridBox.cells(id,5).getValue();
    $("groupId").value= gid;
    
    $("save_update_button").innerHTML='<s:text name="common.update"/>';
}

function reset(){
	document.socialSettings.reset();
	$("save_update_button").innerHTML='<s:text name="common.save"/>';
	groupGridBox.clearSelection();
}

window.onload=doOnLoad;

function doOnLoad(){
	initGroupGridBox();
	document.forms[0].reset();
	var userId = $("userId").value;
	$("linkedInClientId").value='<%=request.getAttribute("linkedInClientId")%>';
	$("linkedInClientSecret").value='<%=request.getAttribute("linkedInClientSecret")%>';
	$("linkedInCompanyIds").value='<%=request.getAttribute("linkedInCompanyIds")%>';
	$("facebookClientId").value='<%=request.getAttribute("facebookClientId")%>';
	$("facebookClientSecret").value='<%=request.getAttribute("facebookClientSecret")%>';
}
</script>
</html>
