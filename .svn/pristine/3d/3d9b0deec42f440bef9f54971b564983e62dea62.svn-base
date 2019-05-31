<%@page import="com.talentPool.otherApplications.constant.OtherApplicationConstants"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%> 
<%@ page import="com.talentPool.common.NavigationConstants, 
				com.talentPool.search.SearchConstants,
				com.talentPool.common.properties.GlobalApplicationProperties,
                com.talentPool.common.properties.GlobalConstants,
				com.talentPool.repository.RepositoryConstants,
				com.talentPool.user.manager.PermissionSet,
                com.talentPool.applicant.manager.ImportConfigurationManager,
                com.talentPool.applicant.constants.ImportConfigurationConstants,
				java.lang.Boolean,
				com.talentPool.user.manager.ModuleSet,
				com.talentPool.masters.constants.MastersConstants"%>

<!-- Display Search Results -->
<%@page import="com.talentPool.search.dataobjects.SearchResultData"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.talentPool.repository.TPDocument"%>
<%@page import="com.talentPool.common.utils.Utils"%>
<%@page import="com.talentPool.common.utils.CommonUtils"%>
<%@page import="com.talentPool.common.db.SimpleDataObject"%>
<%@page import="com.talentPool.repository.TPRepositorySearcherUtils"%>
<%@page import="com.talentPool.common.properties.TPApplicationProperties"%>

<%@page import="com.talentPool.inbox.InboxConstants"%>

<%@page import="com.talentPool.common.properties.TPLabels"%>
<%@page import="com.talentPool.applicant.ApplicantConstants"%><script language="JavaScript" src="js/commonFunctions.js" type="text/javascript"></script>
<script type="text/javascript">
<!--
var noCellNo = new Array();

var smsEnabled = false;
<% if (ModuleSet.isMODULE_SMS() && GlobalConstants.ENABLED.equalsIgnoreCase(GlobalApplicationProperties.getProperty("sms_enabled"))) { %>
<logic:equal value="true" name="permissionSet" scope="session" property="PERMISSION_SEND_SMS">
smsEnabled = true;
</logic:equal>
<% } %>			

var menu = new TpMenu();
var mnu_viewdetails = new TpMenu({type:'menu', id: 'details', image: 'images/ico_profile.gif', title:'View Details', onclick:'onClickMenu', width:'200px'});
menu.addItem(mnu_viewdetails);
if(smsEnabled){
	var mnu_sms = new TpMenu({type:'menu', id: 'sms', image:'images/ico_sms.gif', title:'Send SMS', onclick:'onClickMenu'});
	menu.addItem(mnu_sms);
}

<logic:equal value="true" name="permissionSet" scope="session" property="PERMISSION_DELETE_APPLICANT">
	var mnu_delete = new TpMenu({type:'menu', id: 'delete', image: 'images/ico_delete.gif', title:'Delete', onclick:'onClickMenu'});
	menu.addItem(mnu_delete);
</logic:equal>

var mnuhandler = new TpMenuHandler(menu,{});
mnuhandler.setOffsetOptions({setHeight: false, setWidth: false, offsetTop:5, offsetLeft:10});

function showMenu(applicantId, elementId){
	<%if(ModuleSet.isMODULE_SMS()){ %>
	if(smsEnabled){
		if(noCellNo.indexOf(applicantId)>=0){
			mnuhandler.disableMenuItem(mnu_sms.getId(), true);
		}else{
			mnuhandler.disableMenuItem(mnu_sms.getId(), false);
		}
	}
	<%}%>	
	mnuhandler.show(applicantId,elementId);
}
function onClickMenu(mnu, opt){
	var id = mnu.getId();
	if(id == "details"){
		viewApplicant(opt);
	}else if(id == "sms"){
		sendSMS(opt);
	
	}else if(id == "delete"){
		deleteApplicant(opt);
	}
		
}

var moreMenu = new TpMenu();

var moreMenu_saveSearch = new TpMenu({type:'menu', id: 'saveSearch',image: 'images/ico_savesearch.gif', title:'<bean:message key="common.saveSearch"/>', onclick:'onClickMoreMenu', width:'130px'});
moreMenu.addItem(moreMenu_saveSearch);

<% if(ModuleSet.isMODULE_MASS_EMAILS()){ %>
	var moreMenu_massEmail = new TpMenu({type:'menu', id: 'massEmail',image: 'images/ico_multiple_email.gif', title:'<bean:message key="select.label.multi_email"/>', onclick:'onClickMoreMenu', width:'130px'});
	moreMenu.addItem(moreMenu_massEmail);
<% } %>

<logic:equal value="true" name="permissionSet" scope="session" property="PERMISSION_DELETE_APPLICANT">
	var moreMenu_delete = new TpMenu({type:'menu', id: 'deleteApplicants', image: 'images/ico_delete.gif', title:'<bean:message key="common.delete"/>', onclick:'onClickMoreMenu', width:'130px'});
	moreMenu.addItem(moreMenu_delete);
</logic:equal>
var moreMenu_export = new TpMenu({type:'menu', id: 'export',image: 'images/excel_bw.GIF', title:'<bean:message key="common.export"/>', onclick:'onClickMoreMenu', width:'130px'});
moreMenu.addItem(moreMenu_export);

var mnuhandlermore = new TpMenuHandler(moreMenu,{});
mnuhandlermore.setOffsetOptions({setHeight: false, setWidth: false, offsetTop:30, offsetLeft:-56});

function showMenuMore(var1,var2){
	mnuhandlermore.show(var1,var2);
}

function onClickMoreMenu(mnu, opt){
	var id = mnu.getId();
	if(id=="deleteApplicants"){
		deleteApplicants();
	}else if(id=="saveSearch") {
		saveSearch();
	}else if(id=="massEmail"){
		massEmail();
	}
	else if(id=="export"){
		exportApplicant();
	}
} 

function showInPopUp(url,width,height,returnFun, close){
	showPopWin(url, width, height, returnFun,close);
}
//-->
</script>
<logic:notEmpty name="searchForm" property="searchByRequirementsString">
	<b class="Grey"><bean:write name="searchForm" property="searchByRequirementsString"/></b>
	<br><br>
</logic:notEmpty>
<logic:notEmpty name="searchResultData" scope="request">
<logic:empty name="searchResultData" scope="request" property="records">
<br/><br/><br/>
<b class="Grey"><bean:message key="search_applicant_home.error.no_result_found"/></b>
</logic:empty>
<logic:notEmpty name="searchResultData" scope="request" property="records">
<br/>
<table cellpadding="0" cellspacing="0" border="0" width="100%" >
	<tr>
		<td style="vertical-align: middle;">
		<a href="#" onclick="JavaScript:clearViewed();return false;" class="green">Clear viewed</a> 
		</td>
		<% int _width = 220; %>
		<logic:equal value="true" name="permissionSet" scope="session" property="PERMISSION_SET_FLAG">
			<% _width += 90; %>
		</logic:equal>
		<logic:equal value="true" name="permissionSet" scope="session" property="PERMISSION_SHORTLIST">
			<% _width += 75; %>
		</logic:equal>
		<td align="right">
			<div id="emailReceivedBtnBar" class="navBtnTab" style="width:<%=_width%>px;float: right;">
			<img src="images/btn_tabRightC.gif" width="10" height="40" align="right"  style="margin-top:-10px;"/>
			<img src="images/btn_tabLeftC.gif" width="10" height="40" align="left" style="margin-top:-10px;"/>
			<logic:equal value="true" name="permissionSet" scope="session" property="PERMISSION_SHORTLIST">
				<a href="#" style="width:75px;" onclick="javascript:shortlistSelected(); return false;"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.shortlist"/></a> 	
				<img src="images/dot_gray.gif" width="1" height="16" vspace="3" align="left"/>
			</logic:equal>
			<img src="images/dot_gray.gif" width="1" height="16" vspace="3" align="left"/>
			<a href="#" style="width:130px;" onclick="javascript:forwardResumes(); return false;"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.forward"/> <bean:message key="common.resumes"/></a>	
			<logic:equal value="true" name="permissionSet" scope="session" property="PERMISSION_SET_FLAG">
				<img src="images/dot_gray.gif" width="1" height="16" vspace="3" align="left"/>			
				<a href="#" style="width:90px;" onclick="javascript:categorize(); return false;"><span class="rightC"></span><span class="leftC"></span><bean:message key="search_applicant.home.label.set_flag"/></a> 
			</logic:equal>
			<img src="images/dot_gray.gif" width="1" height="16" vspace="3" align="left"/>
  			<a href="#" style="width:65px;" onmouseover="javascript: showMenuMore('search','more');" id="more"><span class="rightC"></span><span class="leftC"></span><img src="images/ico_more.gif" width="14" height="14" border="0" align="absmiddle" style="margin-right: 3px;"/><bean:message key="select.label.more"/></a>
			</div>	     
		</td>
	</tr>
</table>
<div style="border: 1px solid #999999;">
<table width="100%" class="boxHeaderT" style="border-width:0px; border-bottom:2px;" cellspacing="0" cellpading="0">
	<tr>
		<td height="22" class="header" style="padding-left:5px;" width="25">
 		<img src="images/checkboxunchecked.gif" onclick="selectAll(this);" id="chkAll" style="padding-left:1px;"/>
 		</td>
 		<td height="18" class="header">
	   	<bean:write name="searchResultData" property="pager.recordRange" filter="false" scope="request"/>
 		</td>
 		<td align="right" class="header">
	   		<bean:message key="search_applicant_home.label.sort_by"/> :
	   	<logic:equal name="searchForm" property="sortBy" value="<%=SearchConstants.SORT_BY_SCORE%>">
  	   	<b><bean:message key="search_applicant_home.label.relevance"/></b>
	   	</logic:equal>
	   	<logic:notEqual name="searchForm" property="sortBy" value="<%=SearchConstants.SORT_BY_SCORE%>">
  	   	<a href="#" onclick="sortResults(<%=SearchConstants.SORT_BY_SCORE%>);return false;"><bean:message key="search_applicant_home.label.relevance"/></a>
	   	</logic:notEqual> |
	   	<logic:equal name="searchForm" property="sortBy" value="<%=SearchConstants.SORT_BY_EXP%>">
	   		<b><bean:message key="search_applicant_home.label.experience"/></b>
	   	</logic:equal>
	   	<logic:notEqual name="searchForm" property="sortBy" value="<%=SearchConstants.SORT_BY_EXP%>">
  	   	<a href="#" onclick="sortResults(<%=SearchConstants.SORT_BY_EXP%>);return false;"><bean:message key="search_applicant_home.label.experience"/></a>
	   	</logic:notEqual> |
	   	<logic:equal name="searchForm" property="sortBy" value="<%=SearchConstants.SORT_BY_IMPORT_DATE%>">
	   		<b><bean:message key="search_applicant_home.label.import_date"/></b>
	   	</logic:equal>
	   	<logic:notEqual name="searchForm" property="sortBy" value="<%=SearchConstants.SORT_BY_IMPORT_DATE%>">
  	   	<a href="#" onclick="sortResults(<%=SearchConstants.SORT_BY_IMPORT_DATE%>);return false;"><bean:message key="search_applicant_home.label.import_date"/></a>
	   	</logic:notEqual>
 		</td>
 	</tr>
	</table>
	<table  cellpadding="0" cellspacing="0" width="100%" class="searchResult">
	<% 
	
	String userId = (String) request.getSession(false).getAttribute("userId");
	SearchResultData searchResultData = (SearchResultData)request.getAttribute("searchResultData");	
	ArrayList viewed = (ArrayList) request.getSession(false).getAttribute("viewed");
	ArrayList records = searchResultData.getRecords();
	for(int i=0; records!=null && i<records.size();i++){
		TPDocument tpDocument = (TPDocument)records.get(i);
		String aId = tpDocument.getId();
		String state = tpDocument.getState();
		String applicantStatus = tpDocument.getApplicantStatus();
	%>
			<tr>
			<td valign="top" style="padding-top: 12px;padding-left: 5px;padding-right: 5px;" width="25">
			<img src="images/checkboxunchecked.gif" name="indSelect" id="ind_<%=aId%>_<%=state%>_<%=applicantStatus%>" onclick="toggleMe(this);" style="margin-bottom: 8px;"/>
			<% if(tpDocument.isProcessed()){ %>
			<img src="images/ico_rejected.gif"  style="padding-bottom: 2px;" onmouseover="showStatus(<%=aId%>);" id="status_<%=aId%>" onmouseout="hideStatus();"/><br/>
			 		<% } %>
			<% if(state.equals(RepositoryConstants.STATE_INPROCESS)){ %>
			<img src="images/ico_in_process.gif"  style="padding-bottom: 2px;" onmouseover="showStatus(<%=aId%>);" id="status_<%=aId%>" onmouseout="hideStatus();"/><br/>
			 		<% } %>
			</td>
		   <td valign="top">
		   		<table cellspacing="0" cellpadding="0" width="100%" class="searchResult"> 
		   			<tr>
			   			<td>
			   				<table cellspacing="0" cellpadding="0" width="100%" class="searchResult"> 
					   		<tr>
					   		<td>
					   		<table cellspacing="0" cellpadding="0" width="100%" class="searchResult" align="left">
					   		<tr>
					   			<td class="green" style="font-weight: bold;">
					   				<a href="#" onclick="viewApplicantAndMarkViewed(<%=aId%>);return false;" onmouseover="showAjaxTip(event,<%=aId%>)" onmouseout="hideToolTip()" class="greenname">
					   				<%=tpDocument.highlightQueryTerms(Utils.escapeHTML(tpDocument.getName())) %></a>
					   				<% 
										if(viewed!=null && viewed.contains(aId)){
									%>
									<img src="images/icon_viewed.gif" id="viewed_<%=aId %>" style="margin-left: 10px;margin-bottom: -2px;"/>
									<%}else{%>
									<img src="images/icon_viewed.gif" id="viewed_<%=aId %>" style="margin-left: 10px;margin-bottom: -2px;display: none;"/>
									<%} if(TPRepositorySearcherUtils.isNewRecord(tpDocument.getImportDate())){%>&nbsp;&nbsp;
									<img src="images/new_record.JPG" style="margin-bottom: -2px;" alt="New" title="New"/>
									<%} %>&nbsp;<a href="#" class="green" style="font-weight: bold;text-decoration: none;" id="a_<%=aId%>" onmouseover="showMenu(<%=aId%>,'a_<%=aId%>');">&raquo;</a>
					   			</td>
					   		</tr>
					   		<%
					   			if(GlobalConstants.ENABLED.equals(OtherApplicationConstants.THYSSENKRUPP_CUSTOM_FIELD_REGISTRATION_NO)){
					   				String[] vals = tpDocument.getValues("app_registrationNumber");
					   				if(vals!=null && !Utils.isBlankOrNull(vals[0])){
					   				%>									
									<tr>
										<td><%="Registration No: " + vals[0]  %>
										</td>
									</tr>
					   		<% } } %>
					   		
					   		<% if(!Utils.isBlankOrNull(tpDocument.getLastEmployer()) ||  !Utils.isBlankOrNull(tpDocument.getExperience())){ %>
					   		<tr>	
					   			<td style="font-weight: bold;">
					   			<% if(!Utils.isBlankOrNull(tpDocument.getLastEmployer())){ 
					   				out.write(tpDocument.highlightQueryTerms(Utils.escapeHTML(tpDocument.getLastEmployer())));
					   				if(!Utils.isBlankOrNull(tpDocument.getExperience())){
					   					out.write(", ");
					   				}
					   				}
					   				if(!Utils.isBlankOrNull(tpDocument.getExperience())){
					   					out.write(tpDocument.getExperience());
					   				}	
					   			%>
					   			</td>
					   		</tr>
					   		<% }
								String[] educationList = tpDocument.getEducationList();
								for(int k=0;educationList!=null && k<educationList.length;k++){
							%>	
					   		<tr>	
					   			<td><%=Utils.escapeHTML(educationList[k])%></td>
					   		</tr>
					   		<% } 
					   		if(!Utils.isBlankOrNull(tpDocument.getSkillsString())){
					   		%>
					   		<tr>	
					   			<td class="Grey"><%=tpDocument.highlightQueryTerms(Utils.escapeHTML(tpDocument.getSkillsString())) %></td>
					   		</tr>
					   		<% } %>
					   		</table>
							</td>
						   	<td width="170" >
							   	<table cellspacing="0" cellpadding="0" width="100%" class="searchResult" align="left">
							   	<tr>
							   		<td><bean:message key="search_applicant.home.label.imported_on"/> :
							   		<%if(tpDocument.getImportDate()!=null){%>
									<%=tpDocument.getImportDateToDisplay()%>
									<%}%>
							   		</td>
							   	</tr>
							   	<% if(!Utils.isBlankOrNull(tpDocument.getResumeSource())){%>
							   	<tr>
							   		<td>
							   		
							   		<% if (ImportConfigurationManager.isApplicantFieldViewable(ImportConfigurationConstants.FIELD_SOURCE ,((PermissionSet)request.getSession(false).getAttribute("permissionSet")).isSHOW_CONFIDENTIAL_DATA()) ){%>
							   		<%=Utils.escapeHTML(tpDocument.getResumeSource()) %>
							   		
							   		<%} %>
							   		
							   		</td>
							   	</tr>
							   	<% } %>
							   	<% if(!Utils.isBlankOrNull(tpDocument.getCurrLocation())){%>
							   	<tr>
							   		<td><%=tpDocument.highlightQueryTerms(Utils.escapeHTML(tpDocument.getCurrLocation())) %></td>
							   	</tr>
							   	<% } %>
							   	<% if(!Utils.isBlankOrNull(tpDocument.getMobileNo())){%>
							   	<tr>
							   		<td class="Grey"><%=tpDocument.highlightQueryTerms(Utils.escapeHTML(tpDocument.getMobileNo())) %></td>
							   	</tr>
							   	<% } %>
							   	</table>
					   		</td>
					   		</tr>
					   		</table>
					   </td>
		   			</tr>
					<% 
					String fragments= tpDocument.getBestHighlightedFragments();
					if(!Utils.isBlankOrNull(fragments)){
					%>
					<tr>
						<td class="Grey" style="padding-top: 10px;"><%=fragments%></td>
					</tr>
					<%}%>
					<%  String[] flagIds = tpDocument.getFlagIds();
						if(flagIds!=null && flagIds.length>0){
					%>
					<tr>
						<td class="Grey" style="padding-top: 10px;">
						<%
						for(int f=0; f<flagIds.length; f++){
							if(!Utils.isBlankOrNull(flagIds[f]) && (MastersConstants.FLAG_TYPE_PUBLIC.equalsIgnoreCase(CommonUtils.getFlagType(flagIds[f])) || userId.equalsIgnoreCase(CommonUtils.getFlagOwner(flagIds[f])))){
						%>				
						<img src="<%=CommonUtils.getFlagImage(flagIds[f]) %>" title="<%=CommonUtils.getFlagText(flagIds[f]) %>" style="margin-right: 5px;" />
						<% 			
								}
							}
						%>							   			
						</td>
					</tr>
					<%	} %>	
				</table>	
		   </td>
	   </tr>
	   <tr>
	   <td colspan="2" class="seperator">&nbsp;
	   </td>
	   </tr>
	<% 
		if(Utils.isBlankOrNull(tpDocument.getMobileNo())){
	%>
	<script>noCellNo[noCellNo.length]=<%=aId %></script>
	<% 
		}
	%>
	<% 
	}
	%>
	</table>   
	<br/>
	<table width="100%">
	<tr>
	<td align="center">
	<div class="paging">
	<bean:write name="searchResultData" property="pager.showPaging" filter="false"/>
	</div>
	</td>
	</tr>
	</table>
	<br/>
</div>
</logic:notEmpty>
</logic:notEmpty>
<div id="statusDiv" style="background-color: #fffdcd; position: absolute; border: 1px solid #aaa; display: none;padding: 5px;">
<table >
  <tr>
    <td id="statusText"></td>
  </tr>
</table>
</div>
<script language="JavaScript">

var currentId='';
function showStatus(aId){
	$('statusDiv').style.display='none';
	currentId = aId;
	$('statusText').innerHTML='';
	var pars = "mode=getHTMLForApplicantStatus&applicantId="+aId;
	var myAjax = ajaxCall("doSearch.do","get",pars,loadStatusMessage,reportError);
	
}


function saveSearch(){
	var url = 'doSearch.do?mode=saveSearch&applicantId=';
	showInPopUp(url, 400, 170,doNothing);
}	

function doNothing(){
	return true;
}

function loadStatusMessage(request){
	xmlFile = request.responseXML;
	if(!isValidSession(xmlFile,'<%=TPApplicationProperties.getProperty("redirect_on_session_expired")%>'))return;
	if(!isErrorXml(xmlFile)){
		var op = request.responseText;
		var reqId = op.substring(0,op.indexOf("|"));
		op = op.substring(op.indexOf("|")+1);
		if(currentId==reqId){
			$('statusText').innerHTML=getReplaced(op).escapeHTML();
			Position.clone('status_'+reqId, 'statusDiv', {setHeight: false, setWidth: false, offsetTop:15, offsetLeft:15});
			//Effect.Grow('statusDiv',{duration:0.3, direction:'top-left'});
			Effect.Appear('statusDiv',{duration:0.1});
		}
	}
}
function hideStatus(){
	$('statusDiv').style.display='none';
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

function categorize(){
	var applicantIds = getSelectedCheckboxes();
	if(applicantIds!=''){
		var url = "selectionProcess.do?mode=setFlagToApplicants&applicantIds="+applicantIds;
		showPopWin(url, 420, 380,submitMe,true);
	}else{
		alert('<bean:message key="search_applicant_home.error.select_applicant" />');
	}
}
function deleteApplicants(){
	var applicantIds = getSelectedCheckboxes();
	if(applicantIds!=''){
		deleteApplicant(applicantIds);
	}else{
		alert('<bean:message key="search_applicant_home.error.select_applicant" />');
	}
}

function exportApplicant(){
	var applicantIds = getSelectedCheckboxes();
	if(applicantIds!=''){
		var url = "export.do?mode=exportSelectApplicants&ids="+applicantIds;
		window.setTimeout("showInPopUp('"+url+"',550,320,null,true);", 10);
	}else{
		alert('<bean:message key="search_applicant_home.error.select_applicant" />');
	}
}

function showInPopUp(url,width,height,returnFun, close){
	showPopWin(url, width, height, returnFun,close);
}
var imgChecked="images/checkboxchecked.gif";
var imgUnChecked="images/checkboxunchecked.gif";

function toggleMe(imgElement){
	var destImg = imgUnChecked;
	if(imgElement.src.endsWith(imgUnChecked)){
		destImg = imgChecked;
	}
	imgElement.src=destImg;
}
function selectAll(imgElement){
	var refs = $A(document.getElementsByName('indSelect'));
	if(refs){
		var destImg = imgUnChecked;
		if(imgElement.src.endsWith(imgUnChecked)){
			destImg = imgChecked;
		}
		imgElement.src=destImg;
		refs.each(function(item) {
			item.src=destImg;
		});
	}
}
function getSelectedCheckboxes(){
	var selIds = '';
	var refs = $A(document.getElementsByName('indSelect'));
	if(refs){
		refs.each(function(item) {
			if(item.src.endsWith(imgChecked)){
				var aId = item.id.split('_')[1];
				selIds += aId + ',';
			}
		});
	}
	if(selIds.length>0){
		selIds = selIds.substring(0,selIds.length-1);
	}
	return selIds;
}

function getInprocessSelectedCheckboxes(){
	var selIds = '';
	var refs = $A(document.getElementsByName('indSelect'));
	if(refs){
		refs.each(function(item) {
			if(item.src.endsWith(imgChecked)){
				var idSplit = item.id.split('_');
				var state = idSplit[2];
				if(state=='<%=RepositoryConstants.STATE_INPROCESS%>'){
					var aId = idSplit[1];
					selIds += aId + ',';
				} 
			}
		});
	}
	if(selIds.length>0){
		selIds = selIds.substring(0,selIds.length-1);
	}
	return selIds;
}

function getBlackListedSelectedCheckboxes(){
	var selIds = '';
	var refs = $A(document.getElementsByName('indSelect'));
	if(refs){
		refs.each(function(item) {
			if(item.src.endsWith(imgChecked)){
				var idSplit = item.id.split('_');
				var applicantStatus = idSplit[3];
				if(applicantStatus=='<%=ApplicantConstants.APPLICANT_STATUS_BLACKLISTED%>'){
					var aId = idSplit[1];
					selIds += aId + ',';
				} 
			}
		});
	}
	if(selIds.length>0){
		selIds = selIds.substring(0,selIds.length-1);
	}
	return selIds;
}

function getJoinedCandidateSelectedCheckboxes(){
	<%ArrayList joinedApplicants=(ArrayList)request.getAttribute("jndAppIds");%>
	var joinArray = <%=joinedApplicants%>;	
	var selIds = '';
	var refs = $A(document.getElementsByName('indSelect'));
	if(refs){		
		refs.each(function(item) {			
			if(item.src.endsWith(imgChecked)){
				var idSplit = item.id.split('_');				
				var state = idSplit[2];				
				if(state=='<%=RepositoryConstants.STATE_JOINED%>'){
					var aId = idSplit[1];
					for(var i=0;i<joinArray.length;i++){
						if(joinArray[i]==aId){
							selIds += aId + ',';		
						}
					}
										
				} 
			}
		});
	}
	if(selIds.length>0){
		selIds = selIds.substring(0,selIds.length-1);
	}
	return selIds;
}

function forwardResumes(){
	var selId = getSelectedCheckboxes();
	if (selId) {
		var url = 'inbox.do?mode=forwardResumes&newEmailType=<%=InboxConstants.EMAIL_TYPE_FORWARD_RESUME%>&emailLocation=<%=InboxConstants.EMAIL_LOCATION_COMMUNICATIONS%>&applicantId='+selId;
		window.setTimeout("showInPopUp('"+url+"',810, 513,submitMe,true);", 10);
	}else {
		alert('<bean:message key="common.please_select"/> <bean:message key="common.candidate"/>'); 
	} 
}

function viewApplicantAndMarkViewed(aId){
	viewApplicant(aId);
	markViewed(aId);
	return false;
}
function markViewed(aId){
	Element.show("viewed_"+aId);
}

function shortlistSelected(){
	var applicantIds = getSelectedCheckboxes();
	if(applicantIds!=''){
		if(validateApplicantsForShortlist(applicantIds)){
			var url = "selectionProcess.do?mode=shortlist&applicantId="+applicantIds;
			showInPopUp(url, 650, 350,submitMe);
		}
	}else{
		alert("<bean:message key="common.please_select"/> <bean:message key="common.candidate"/>");
	}
}

function validateApplicantsForShortlist(applicantIds){
	var inProcessSelIds = getInprocessSelectedCheckboxes();
	var blackListedIds = getBlackListedSelectedCheckboxes();
	var totalSelectedApplicantIds = applicantIds.split(',').length;
	var joinedCandidateSelectedIds = getJoinedCandidateSelectedCheckboxes();
	var error = '';
	if(inProcessSelIds.length>0){
		error += inProcessSelIds.split(',').length+' of '+totalSelectedApplicantIds+' <bean:message key="search_applicant_home.error.shortlist_inprocess"/>';
	}
	if(blackListedIds.length>0){
		error += blackListedIds.split(',').length+' of '+totalSelectedApplicantIds+' <bean:message key="search_applicant_home.error.shortlist_blacklist"/>';
	}
	if(joinedCandidateSelectedIds.length>0){
		error += joinedCandidateSelectedIds.split(',').length+' of '+totalSelectedApplicantIds+' canidate is already joined';
	}
	if(error!=''){
		error += '<bean:message key="search_applicant_home.error.shortlist_unselect" />';
		alert(error);
		return false;
	}	
	return true;	
}

</script>