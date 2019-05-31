<%@page import="com.talentPool.common.utils.CommonUtils"%>
<%@page import="com.talentPool.common.utils.Utils"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@page import="org.apache.struts.Globals"%>
<%@page import="com.talentPool.common.properties.TPApplicationProperties"%>
<%@page import="com.talentPool.common.utils.Utils"%>
<head>
<script type="text/javascript" src="js/tiny_mce/tiny_mce.js"></script>
<script src="js/scripta/lib/prototype.js" type="text/javascript"></script>
<script src="js/ajaxfunctions.js" type="text/javascript"></script>
<script src="js/commonFunctions.js" type="text/javascript"></script>
<script src="js/selectoption.js" type="text/javascript"></script>
<script src="js/selectbox/selectbox.js" type="text/javascript"></script>
<script src="js/dhtmlxGrid/dhtmlXCommon.js"></script> 
<script src="js/dhtmlxGrid/dhtmlXGrid.js"></script> 
<script src="js/dhtmlxGrid/dhtmlXGridCell.js"></script>
<script src="js/dhtmlxGrid/dhtmlXGrid_excell_link.js"></script>
<script src="js/dhtmlxGrid/dhtmlXGrid_drag.js"></script>
<script src="js/dhtmlxGrid/dhtmlxgrid_filter.js"></script>
<script src="js/tpSelectListFunctions.js"></script>
<script src="js/scripta/src/effects.js" type="text/javascript"></script>
<script type="text/javascript">
	var selectedTemplate = null;
	var optTemplate = new Array();
	tinyMCE.init({
		mode : "textareas",
		elements : "content",
		theme : "advanced",
		theme_advanced_toolbar_location : "top",
		theme_advanced_toolbar_align : "left",
		theme_advanced_buttons1 : "formatselect,fontselect,fontsizeselect,newdocument,bold,italic,underline,forecolor,backcolor,bullist,numlist,separator,undo,redo,cut,copy,paste,justifyleft,justifyright",
		theme_advanced_buttons2 : "",
		theme_advanced_buttons3 : "",
		force_br_newlines: true,
		theme_advanced_disable : "anchor",
		theme_advanced_path : false
	});
	var selectBoxGroupOrCompany = null;
</script>
</head>
<s:hidden id="selectedSocialMediaTypeId" value="%{#request['selectedSocialMediaTypeId']}"/>

<body>
	<s:form action="publishPositionToSocialMedia" id="publishPositionToSocialMedia" method="POST">
	<s:hidden property="positionId" name="positionId" value="%{#request['positionId']}"/>
	<s:hidden property="socialMediaId" name="socialMediaId" />
	<s:hidden property="postContent" name="postContent"/>
	<s:hidden property="postTitle" name="postTitle"/>
	<s:hidden id="publishOrUnPublish" name="publishOrUnPublish" value="%{#request['publishOrUnPublish']}"/>
	<s:hidden property="socialId" name="socialId" />
	<s:hidden property="templateCode" name="templateCode" id="templateCode"/>
	<s:hidden property="selectedGroupTitles" name="selectedGroupTitles" />

	
	<div class="contentDivPop" id="contentBlock">
		<div style="color: red; margin-top: 0; float: inherit;" >
								<%if(!Utils.isBlankOrNull((String) request.getAttribute("saved"))) {
								String saved = (String) request.getAttribute("saved");
								if(saved == "0"){%>
								<s:text name="position.home.publish.failure"/> &nbsp;:<s:property value="#request['failedFor']"/>
								<%}
								} %>
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:property value="#request['tokenInvalid']"/>
							</div>
		
		<div class="outerDiv" style="width: 100%;">
			<div class="popupTop">
				<table class="tblPop"  >
					<tr>
						<td class="header">
							<s:text name ="common.position" />:
						</td>
						<td>
							&nbsp;<s:property value="#request['positionTitle']"/>
						</td>
					</tr>
					<tr>
						<td class="header">
							<s:text name="position.description.hire_by_date" />:
						</td>
						<td>
							&nbsp;<s:property value="#request['positionHireByDate']"/>
						</td>
					</tr>
					<tr>
						<td class="header">
							<s:text name="common.vacancies" />:
						</td>
						<td>
							&nbsp;<s:property value="#request['vacancies']"/>
						</td>
					</tr>
				</table>
			</div>
			<div class="popupTop">
				<table class="tblPop" id ="socialMediaPublish" style="border: 0; border-spacing: 0; padding: 0; width: 460px;">
					<tr>
						<td class="header">
							<s:text name="view_position.label.publishTo"/>&nbsp;
						</td>
					</tr>
			
					<s:iterator value="#request['sources']" var="source">
						<tr>
							<td>
							<s:if test="%{#source.tokenFlag == 'false'}">
								<img src="images/checkboxunchecked_grey.gif" name='imgPublish' id='<s:property value="sourceId" />' style="margin-bottom:-1px;"  data="unchecked"/>
							</s:if>
							<s:else>
								<img src="images/checkboxunchecked.gif" name='imgPublish' id='<s:property value="sourceId" />' style="margin-bottom:-1px;" onclick="selectMediaType(this)" data="unchecked"/>
							</s:else>
								<s:property value="sourceTitle" />&nbsp;&nbsp;
								<s:if test="%{#source.tokenFlag == 'false'}">
								<a href="#" onclick="javascript:redirectToAuth('<s:property value="sourceId"/>')"><i><s:text name="position.home.label.authorize_to_connect" /></i></a>
								</s:if>
							</td>
							<td>
								<s:if test="%{#source.lastPostedDate != null && #source.lastPostedDate != ''}">
									<s:text name="position.home.label.last_posted_on"/> : <s:property value="lastPostedDate"/>
								</s:if>
							</td>
						</tr>
					</s:iterator>
				</table>	
			</div>
			<div class="popupTop">
					<table class="tblPop" id="selectGroup_Company"
						style="border: 0; border-spacing: 0; padding: 0; width: 460px;">
						<tr>
							<td class="header" width="100px"><s:text
									name="view_position.label.selectGroup" />&nbsp;</td>
							<td>
								<div id="selectGroupOrCompany" style="display: block;">
									<table cellpadding="0" cellspacing="0" class="innerReport">
										<tr>
											<td>
												<table cellpadding="0" cellspacing="0"
													style="padding-left: 6px;">
													<tr>
														<td class="gridborder">
															<div id="FIELDS_GRID"
																style="width: 225px; height: 150px; overflow: visible;"></div>
														</td>
													</tr>
												</table>
											</td>
											<td width="34px" align="center">
											<a href="#" onclick="javascript: selectItem(fieldsGrid,selectedFieldsGrid);return false;"
												title="Add"> <img src="images/ico_rightarrow.gif"
													border="0" /></a><br /> <a href="#"
												onclick="javascript: deselectItem(selectedFieldsGrid,fieldsGrid);return false;"
												title="Remove"> <img src="images/ico_leftarrow.gif"
													border="0" style="margin-top: 10px;" /></a></td>
											<td>
												<table cellpadding="0" cellspacing="0">
													<tr>
														<td class="gridborder">
															<div id="FIELDS_GRID_SELECTED"
																style="width: 225px; height: 150px; overflow: visible;"></div>
														</td>
													</tr>
												</table>
											</td>
										</tr>
									</table>
								</div>
							</td>
						</tr>

						<tr>
							<td></td>
						</tr>
					</table>
				</div>
			<div class="popupTop">
				<table class="tblPop" id ="templateSelect" style="border: 0; border-spacing: 0; padding: 0; width: 460px;">
					<tr>
						<td class="header"><s:text name="view_position.label.template"/></td>
						<td>	
	    				    <script type="text/javascript">
			                    optTemplate[0] = new SelectOption('-1','<s:text name="common.selectlist.select"/>');
			                    optTemplate= optTemplate.concat(<%=(String) request.getAttribute("jsArrayForTemplate")%>);
			                    selectedTemplate = new SelectBox(optTemplate,'','images/btn_dropdown.gif',{namesonly:false, width:'200px', size:20});
			                    document.write(selectedTemplate.getHtml());
			                    selectedTemplate.setOnChangeHandler('onTemplateSelect');
			                    selectedTemplate.init();
	                  		</script>
						</td>
					</tr>
					<tr>
						<td class="header"><s:text name="view_position.label.templateTitle"/></td>
						<td><s:textfield name="title" id="title" size="50px" ></s:textfield></td>
					</tr>
	
				</table>
			</div>
			
			<div class="popupTop">
				<table class="tblPop" id="templateDisplayContent" style="border: 0; border-spacing: 0; padding: 0; width: 460px;">
					<tr>
						<td class="header"><s:text name="view_position.label.publishContent"/></td>
					</tr>
					<tr>
						<td><s:textarea name="content" id="content" styleClass="inputBox" style="width:100%;height:145px;"/></td>
					</tr>
	
				</table>
			</div>
			
			<div class="popupBody">
				<table style="border: 0; border-spacing: 0; padding: 0; width: 100%;">		
					<tr>
						<td>
			
							</td>
						<td>
							<div class="navBtn" style="margin-top:5px;float:right;">
								<a href="#" style="width:90px;" class="active" onclick="javascript:onSubmit();"><span class="rightC"></span><span class="leftC"></span><s:text name="common.submit"/></a>
								<a href="#" style="width:60px; margin-left:5px;" class="active" onclick="javascript: window.top.hidePopWin(false);return false;"><span class="rightC"></span><span class="leftC"></span><s:text name="common.cancel"/></a>
							</div>
						</td>
					</tr>			
				</table>
			</div>
		</div>
	</div>
	<div id="afterPost" style="display: none; padding-left: 20px; padding-top: 150 px; float: inherit;">
		<br/><br/>
		<table>
			<tr>
				<td>
					<s:text name="position.home.publish.success"/>
				</td>
			</tr>
			<tr>
				<td>
					<div class="navBtn">
								<a href="#" style="width:90px;" class="active" onclick="javascript:closePopUp();"><span class="rightC"></span><span class="leftC"></span><s:text name="common.ok"/></a>
							</div>
				</td>
			</tr>
		</table>
		
		
	</div>
	<br></br>
	</s:form>
</body>
<script type="text/javascript">
window.onload=doOnLoad;
var socialMediaIds=[];
var templateCode = '';
var chkedBtn = 'images/checkboxchecked.gif';
var unChkedBtn = 'images/checkboxunchecked.gif';
var fieldsGrid = null;
var selectedFieldsGrid = null;
var selectedFieldsGridValue = [];
var returnVal;
function doOnLoad() {
	initFieldsGrid();
	initSelectedFieldsGrid();
	var saved = '<%=request.getAttribute("saved")%>';
	if(saved == '1'){
		document.getElementById("contentBlock").style.display = 'none';
		document.getElementById("afterPost").style.display ='block';
	}
	var title_post = '<%=request.getAttribute("postTitle")%>';
	<%
		String contentPost = (String) request.getAttribute("postContent");
		if(contentPost!=null){
			contentPost = contentPost.replaceAll("[\r?\n]+","");			
		}			
	%>
	var content_post = "<%=contentPost%>";
    var selectedSocialMediaTypeIds = document.getElementById("selectedSocialMediaTypeId").value;
    var selectedSocialMediaTypeId = selectedSocialMediaTypeIds.split(",");
    var selectedFieldsGridValues = '<s:property value="socialId"/>';
    selectedFieldsGridValue = selectedFieldsGridValues.split(",");
    var template_Code = '<%=request.getAttribute("templateCode")%>';
    var tmpStr = '';
    var tmpStr1 = '';
    if(template_Code.length > 0 && template_Code != 'null'){
    	selectedTemplate.reInitialize(optTemplate,template_Code);
    	templateCode = template_Code;
    }
    if(title_post.length>0 && title_post != 'null'){
    	document.getElementById("title").value = title_post;
    }
    if(content_post.length>0 && content_post != 'null'){
    	tinyMCE.activeEditor.setContent(content_post);
    }
    if(selectedSocialMediaTypeId.length > 0 && selectedSocialMediaTypeId != 'null'){
    	for(var i=0;i<selectedSocialMediaTypeId.length ; i++){
    		var tmp = selectedSocialMediaTypeId[i];
    		selectMediaType(document.getElementById(tmp));
    		if(tmpStr != ''){
    			tmp = "," + tmp;
    		}
    		tmpStr = tmpStr + tmp;
    	}
    	var pars = "socialMediaId="+tmpStr;
    	fieldsGrid.clearAll();
    	fieldsGrid.loadXML("getGroupsAndCompanies.action?"+pars,fillSelectedGridBox);
    }
}

function fillSelectedGridBox(){
	for(var i=0;i<selectedFieldsGridValue.length ; i++){
		var tmp = selectedFieldsGridValue[i];
		fieldsGrid.setSelectedRow(tmp,true,false,false);
	}
	selectItem(fieldsGrid,selectedFieldsGrid);
}

function redirectToAuth(socialId){
	returnVal = socialId;
	window.top.hidePopWin(true);
}

function onSubmit(){
	if (socialMediaIds.length=='0'){
		alert('<s:text name="alert.socialMedia.publish.selectMediaType"/>');
		return false;
	}
	var selectedRowIds = selectedFieldsGrid.getAllRowIds();
	var selectedGroupIds = selectedRowIds.split(",");
	var selectedGroupTitles = '';
	for(var i =0 ;i<selectedGroupIds.length ;i++){
		var tmp =selectedFieldsGrid.cells(selectedGroupIds[i],'1').getValue() ;
		if(selectedGroupTitles.length > 0){
			tmp = ","+tmp;
		}
		selectedGroupTitles += tmp;
	}
	document.forms[0].selectedGroupTitles.value = selectedGroupTitles;
	if (selectedRowIds.length == 0){
		alert("Please select a group or a company.");
		return false;
	}
	document.forms[0].socialMediaId.value=socialMediaIds;
	document.forms[0].socialId.value=selectedRowIds;
	if(templateCode == "-1" || templateCode.length == 0){
		alert('<s:text name="alert.socialMedia.pubblish.selectTemplate"/>');
		return false;
	}
	document.getElementById("templateCode").value = selectedTemplate.getSelectedId();
	document.forms[0].postContent.value=tinyMCE.activeEditor.getContent();
	document.forms[0].postTitle.value=document.getElementById("title").value;
	document.forms[0].submit();
}


function selectMediaType(mediaType){
	var e = mediaType;
	if(e.attributes["data"].value=="unchecked"){
		e.src=chkedBtn;
		e.attributes["data"].value="checked"
		socialMediaIds.push(e.id);
	}
	else{
		e.src=unChkedBtn;
		e.attributes["data"].value="unchecked"
		var temp=[];
		for(var i=0;i<socialMediaIds.length;i++){
			if(socialMediaIds[i]!=e.id){
				temp.push(socialMediaIds[i]);
			}
		}
		socialMediaIds=temp;
		
	}	
	//socialMediaIds= mediaType.value;
	var grpOrCompany = document.getElementById("selectGroupOrCompany");
	var pars = "socialMediaId="+socialMediaIds.toString();
	fieldsGrid.clearAll();
	fieldsGrid.loadXML("getGroupsAndCompanies.action?"+pars,onLoadFunction);
}

function onLoadFunction(){
	var fieldids = fieldsGrid.getAllRowIds();
	var deleteids = selectedFieldsGrid.getAllRowIds();
	var deleterows = deleteids.split(",");
	
	for(var i=0; i<deleterows.length;i++){
		fieldsGrid.deleteRow(deleterows[i]);
		if(fieldids.indexOf(deleterows[i]) == -1){
			selectedFieldsGrid.deleteRow(deleterows[i]);
		}
	}
}
function onTemplateSelect(){
	templateCode =selectedTemplate.getSelectedId() ;
	if(templateCode.length > 0 && templateCode != "-1"){
		var myAjax = ajaxCall("templateAction.action?positionId="+document.forms[0].positionId.value+"&templateCode="+templateCode,"get","",appendTemplateContent,reportError);
	}else{
		document.getElementById("title").value="";
		tinyMCE.activeEditor.setContent("");
		templateCode ="";
	}
}

function appendTemplateContent(request){
    xmlFile = request.responseXML;
    if(!isValidSession(xmlFile,'<s:text name="redirect_on_session_expired"/>')) return;
	var template = xmlFile.getElementsByTagName("template")[0];
	var subject = unescapeHTML(getSingleElement(template,"subject","")); /*.escapeHTML();*/
	var content = getSingleElement(template,"content","");//.escapeHTML();
	content = content.unescapeHTML();
	document.getElementById("title").value=subject;
	tinyMCE.activeEditor.setContent(content);
}

//fields grid
function initFieldsGrid() {	
	fieldsGrid = new dhtmlXGridObject('FIELDS_GRID'); 
	fieldsGrid.imgURL = "images/"; 
	fieldsGrid.setHeader("&nbsp,<s:text name="admin.social_network.group_name" /> ,<s:text name="admin.social_network.social_mediaType" /> ,'','',''"	); 
	fieldsGrid.setInitWidths("0,200,0,0,0,0");
	fieldsGrid.setColAlign("left,left,left,left,left,left");
	fieldsGrid.setColTypes("ro,ro,ro,ro,ro,ro"); 
	fieldsGrid.setColSorting("na,str,na,na,na,na");
	fieldsGrid.enableMultiselect(true);	
	fieldsGrid.attachEvent("onKeyPress",onFieldsGridKeyPressed);
	fieldsGrid.attachEvent("onRowDblClicked",doOnFieldsGridRowDblClicked);
	fieldsGrid.attachEvent("onRowSelect",doOnFieldsGridRowSelectHandler);
	fieldsGrid.init();
	loadFieldsGrid();

	fieldsGrid.setSortImgState(true,0,"ASC");
	
	fieldsGrid.gridToGrid = function(rowId,sgrid,tgrid){
	    var z=new Array();
		for(var i=0;i<sgrid.hdr.rows[0].cells.length;i++)
		z[i]=sgrid.cells(rowId,i).getValue();
		return z;
	}
}

function initSelectedFieldsGrid(){
	selectedFieldsGrid = new dhtmlXGridObject('FIELDS_GRID_SELECTED'); 
	selectedFieldsGrid.imgURL = "images/"; 
	selectedFieldsGrid.setHeader("&nbsp,Selected Groups/Companies,<s:text name="admin.social_network.social_mediaType" /> ,'','',''"	); 
	selectedFieldsGrid.setInitWidths("0,200,0,0,0,0");
	selectedFieldsGrid.setColAlign("left,left,left,left,left,left");
	selectedFieldsGrid.setColTypes("ro,ro,ro,ro,ro,ro"); 
	selectedFieldsGrid.enableMultiselect(true);	
	selectedFieldsGrid.init();     
	selectedFieldsGrid.attachEvent("onKeyPress",onSelectedFieldsGridPressed);
	selectedFieldsGrid.attachEvent("onRowDblClicked",doOnSelectedFieldsGridRowDblClicked);
	selectedFieldsGrid.attachEvent("onRowSelect",doOnSelectedFieldsRowSelectHandler);
	selectedFieldsGrid.gridToGrid = function(rowId,sgrid,tgrid){
	    var z=new Array();
		for(var i=0;i<sgrid.hdr.rows[0].cells.length;i++)
		z[i]=sgrid.cells(rowId,i).getValue();
		return z;
	}
}

function onFieldsGridKeyPressed(keyCode,ctrl,shift) {
	selectedFieldsGrid.clearSelection();
	onGridObjKeyPressed(fieldsGrid,selectedFieldsGrid,4,keyCode,ctrl,shift);
}

function onSelectedFieldsGridPressed(keyCode,ctrl,shift) {
	fieldsGrid.clearSelection();
	onGridObjKeyPressed(selectedFieldsGrid,fieldsGrid,4,keyCode,ctrl,shift);
}

function doOnFieldsGridRowDblClicked() {
	selectItem(fieldsGrid,selectedFieldsGrid);
}
function doOnSelectedFieldsGridRowDblClicked() {
	deselectItem(selectedFieldsGrid,fieldsGrid);
}

function doOnFieldsGridRowSelectHandler() {
	selectedFieldsGrid.clearSelection();
}

function doOnSelectedFieldsRowSelectHandler() {
	fieldsGrid.clearSelection();
}

function loadFieldsGrid(){
	fieldsGrid.clearAll();
	fieldsGrid.loadXML("getGroupsAndCompanies.action");
}

/**
 * END Select Fields Grid Related Functions 
 */
 
 function closePopUp(){
	 window.top.hidePopWin(true);
}
</script>