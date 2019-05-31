<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ page import="org.apache.struts.Globals" %>
<%@page import="java.util.ArrayList"%>
<%@page import="com.talentPool.masters.constants.MastersConstants"%>
<%@page import="com.talentPool.documents.DocumentConstants"%>
<script src="js/scripta/lib/prototype.js"></script>
<script src="js/commonFunctions.js"></script>
<script language="JavaScript" src="js/ImagePopUp.js" type="text/javascript"></script>
<script language="JavaScript">
<logic:present name="update" scope="request">
	window.top.hidePopWin(true);
</logic:present>                
</script>
<script language="JavaScript">
var icons = new Array();
var flagIcons = new Array();
<% 
ArrayList images = (ArrayList)request.getAttribute("images");
ArrayList flagImages = (ArrayList)request.getAttribute("flagImages");
if(images!=null){
	for(int i=0;i<images.size();i++){
%>
	icons[icons.length]='<%=(String)images.get(i)%>';
<%		
	}
}

if(flagImages!=null){
	for(int i=0;i<flagImages.size();i++){
%>
	flagIcons[flagIcons.length]='<%=(String)flagImages.get(i)%>';
<%		
	}
}
%>
</script>

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
	<html:form action="/masters" onsubmit="submitForm();return false;">
	<html:hidden property="mode" name="mastersForm"/>  	
  	<html:hidden property="flagId" name="mastersForm"/>  	
  	<html:hidden property="flagImage" name="mastersForm"/>
  	<html:hidden property="flagType" name="mastersForm"/>
  	
	<div class="popupTop">
		<table class="tblPop">
		<tr>
			<td></td>
			<td>
				<logic:equal name="mastersForm" property="flagType" value="<%=MastersConstants.FLAG_TYPE_PUBLIC%>">
			  	<img src="images/radiobutton.gif" name='imgCategory' id='img_<%=MastersConstants.FLAG_TYPE_PRIVATE%>' onclick="javascript:onRadioChange('imgCategory','<%=MastersConstants.FLAG_TYPE_PRIVATE%>');" style="margin-bottom:-1px;"/>Private
				<img src="images/checkedradiobutton.gif" name='imgCategory' id='img_<%=MastersConstants.FLAG_TYPE_PUBLIC%>' onclick="javascript:onRadioChange('imgCategory','<%=MastersConstants.FLAG_TYPE_PUBLIC%>');" style="margin-bottom:-1px;"/>Public
				</logic:equal>
				<logic:notEqual name="mastersForm" property="flagType" value="<%=MastersConstants.FLAG_TYPE_PUBLIC%>">
			  	<img src="images/checkedradiobutton.gif" name='imgCategory' id='img_<%=MastersConstants.FLAG_TYPE_PRIVATE%>' onclick="javascript:onRadioChange('imgCategory','<%=MastersConstants.FLAG_TYPE_PRIVATE%>');" style="margin-bottom:-1px;"/>Private
				<img src="images/radiobutton.gif" name='imgCategory' id='img_<%=MastersConstants.FLAG_TYPE_PUBLIC%>' onclick="javascript:onRadioChange('imgCategory','<%=MastersConstants.FLAG_TYPE_PUBLIC%>');" style="margin-bottom:-1px;"/>Public
				</logic:notEqual>
			</td>
		</tr>
		<tr>
			<td class="header">
				<bean:message key="flag_master.error.update_text"/>
				<span class="star">*</span>
				:
			</td>
			<td>
				<html:text property="flagText" name="mastersForm" size="35" styleId="skillCategory"></html:text>
		</tr>
		<tr>
		  	<td class="header">
		  		<bean:message key="flag_master.label.flag_icon"/>
		  		<span class="star">*</span>
		  		:
		  	</td>
		  	<td>
			  	<table style="background-color: #FFF; border: 1px solid #99CC01;" id="tblselect" cellpadding="2" cellspacing="0" onclick="imagePopUp.show('tblselect'); return false;">
			  		<tr>
			  		<td style="width: 40px; padding-left:5px;">
						<img src="images/blank.gif" name='imgSelected' id='imgSelected' style="margin-bottom:-3px;cursor:hand;" />
					</td>
					<td>
			  			<img src="images/btn_dropdown.gif" style="margin-bottom:-3px;cursor:hand;margin-right:-20px;height: 20px;"  title="Select Icon" onblur="imagePopUp.hide()" onclick="imagePopUp.show('tblselect'); return false;"/>
			  		</td>
			  		</tr>
				</table>
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
	</html:form>	
</div>
</div>
<DIV id="imagePopUpDiv" style="position:absolute;z-index:1000;background-color: #FFF; border: 1px solid #99CC01;display:none;" ></DIV>  

<script type="text/javascript">
var imagePopUp = new ImagePopUp("imagePopUpDiv", icons);

function submitForm(){
	errors = '';
	var Name = document.mastersForm.flagText.value;
	if(Name.trim()==""){
		errors = addError(errors, '<bean:message key="flag_master.label.message_enter_flag_description" />');
	}
	var imageSrc = document.getElementById('imgSelected').src;
	if(imageSrc.trim()=="" || imageSrc.indexOf('blank.gif')>0){
		errors = addError(errors, '<bean:message key="flag_master.label.message_select_flag_icon" />');
	}else{
		imageSrc=getCategoryIconPath(imageSrc);
	}
	if(errors != '') {
		alert(errors);
		return;
	}
	document.mastersForm.flagImage.value=imageSrc;
	document.mastersForm.submit();
}

function getCategoryIconPath(imageSrc){
	var src = imageSrc.split('=');
	var image =	src[2];
	src = image.split('&');
	imageSrc = src[0];
	return imageSrc;
}

function onRadioChange(imgGroupName, attachmentId){
	var imgs = document.getElementsByName(imgGroupName);
	for (i = 0; i < imgs.length; i++) {
		var theImage = imgs[i];
		if (theImage.id.indexOf("img") > -1) {
			if( theImage.id == 'img_'+attachmentId){
				theImage.src = "images/checkedradiobutton.gif";
				document.mastersForm.flagType.value=attachmentId;
			}else{
				theImage.src = "images/radiobutton.gif";
			}
		}
	}
}

function actionOnLoad(){
	var selectedIMG = '<bean:write name="mastersForm" property="flagImage"/>';
	if(selectedIMG.trim()!="" && selectedIMG.trim!=null){
		document.getElementById('imgSelected').src='docs.do?mode=getIcon&fileName='+selectedIMG+'&contentDisposition=<%=DocumentConstants.CONTENT_DISPOSITION_INLINE %>';
	}
	<logic:empty property="flagId" name="mastersForm">
	window.top.setPopTitle('<b><bean:message key="flag_master.label.add_flag" /></b>');
	document.mastersForm.flagType.value='<%=MastersConstants.FLAG_TYPE_PRIVATE%>';
	</logic:empty>
	<logic:notEmpty property="flagId" name="mastersForm">
	window.top.setPopTitle('<b><bean:message key="flag_master.label.edit_flag" /></b>');
	</logic:notEmpty>
}

function selectImage(fileName) {
	var imageSrc = document.getElementById('imgSelected').src;
	if(flagIcons.indexOf(fileName) != -1 && imageSrc.indexOf(fileName) == -1) {
		if(!confirm('<bean:message key="flag_master.label.confirm_same_flag_image" />')) 
			return;
	}
	var url = 'docs.do?mode=getIcon&fileName='+fileName+'&contentDisposition=<%=""+DocumentConstants.CONTENT_DISPOSITION_INLINE %>';
	$('imgSelected').src=url;
	imagePopUp.hide();
	return;
}
window.onload=actionOnLoad;
</script>