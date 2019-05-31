<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ page import="com.talentPool.positions.dataobject.PositionVendorData" %>
<%@ page import="org.apache.struts.Globals"%>
<link rel="stylesheet" type="text/css" href="themes/default/CalendarPopup.css"/>
<link rel="stylesheet" type="text/css" href="themes/default/default.css"/>
<script language="JavaScript" src="js/scripta/lib/prototype.js" type="text/javascript"></script>
<script language="JavaScript" src="js/calender/CalendarPopup.js"></script>		
<script language="JavaScript" src="js/calender/dateFormatter.js" type="text/javascript"></script>
<logic:present name="saved" scope="request">
	<script>
		window.top.hidePopWin(true);
	</script>
</logic:present>
<DIV id="calDiv" style="position:absolute;background:#FFFFFF;z-index:1000;" ></DIV>
<script language="JavaScript">
	var sourceTypeIds = new Array();
	var sourceIds = new Array();
	var popUpCal = new CalendarPopup("calDiv");
	var dtfo = new DateFormatter();
	popUpCal.showNavigationDropdowns();
</script>
<logic:notPresent name="saved" scope="request">
<html:form action="/position">
<html:hidden property="mode" value="publishPosition"/>
<html:hidden property="t" name="positionForm"/>
<html:hidden property="positionId" name="positionForm"/>
<input type="hidden" name="isSubmitted" value="1"/>
<div class="contentDivPop">
	<div id="divError" style="display:block">
	<% 
		if(request.getAttribute(Globals.ERROR_KEY)!=null){
	%>
	<script>
		var isError=1;
	</script>
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
	<br/>
	<% } %>
	</div>
	<logic:empty name="positionVendors" scope="request">
		<bean:message key="publish_position.message.vendor_not_present" /> 
	</logic:empty>	
	<logic:notEmpty name="positionVendors" scope="request">
		<div class="outerDiv" style="overflow:auto;">
			<div class="popupTop" >
				<table class="tblPop" >
					<tr>
						<td class="header">
							<bean:message key="common.position" />:
						</td>
						<td>
							&nbsp;<bean:write name="positionTitle"  scope="request"/>
						</td>
					</tr>
					<tr>
						<td class="header">
							<bean:message key="position.description.hire_by_date" />:
						</td>
						<td>
							&nbsp;<bean:write name="positionHireByDate"  scope="request"/>
						</td>
					</tr>
					<tr>
						<td class="header">
							<bean:message key="common.vacancies" />:
						</td>
						<td>
							&nbsp;<bean:write name="vacancies"  scope="request"/>
						</td>
					</tr>
				</table>
			</div>
			<div class="popupTop">
			<table border="0" cellspacing="0" cellpadding="0" class="posinput" width="460px">
				<tr>
					<td>
						<bean:message key="publish_position.label.publishFrom" />
						 &nbsp;<html:text name="positionForm" styleId="publishFrom" 
							property="publishFrom" maxlength="15" size="13" styleClass="Grey" 
								onblur="getFDate(this,'DD/MM/YYYY');"  />&nbsp;
						<img src="images/ico_cal.gif" style="height:16px;margin-bottom:-3px;cursor:hand;" 
						onclick="popUpCal.select(document.getElementById('publishFrom'),'publishFrom','dd/MM/yyyy'); return false;" />
						&nbsp;&nbsp;
						<bean:message key="publish_position.label.publishTo" />
						&nbsp;&nbsp;
						<html:text name="positionForm" styleId="publishTo" 
							property="publishTo" maxlength="15" size="13" styleClass="Grey" 
								onblur="getFDate(this,'DD/MM/YYYY');"  />&nbsp;
						<img src="images/ico_cal.gif" style="height:16px;margin-bottom:-3px;cursor:hand;" 
						onclick="popUpCal.select(document.getElementById('publishTo'),'publishTo','dd/MM/yyyy'); return false;" />
					</td>
				</tr>
				<tr>
					<td>
						<img src="images/checkboxunchecked.gif" name="0" id="0" onclick="javascript: toggleSelectAll(this);"/>
						Select All
					</td>
				</tr>
				<%
					String sourceCategoryId = "";
				%>
				<logic:iterate id="positionVendor" name="positionVendors" type="PositionVendorData">
					<% if (!sourceCategoryId.equals(positionVendor.getSourceTypeId())) { %>
					<script language="JavaScript">
						sourceTypeIds[sourceTypeIds.length] = '0_' + '<bean:write name="positionVendor" property="sourceTypeId" />';
					</script>
					<tr>
						<td>
							&nbsp;&nbsp;&nbsp;&nbsp;
							<img src="images/checkboxunchecked.gif" name="0_<bean:write name="positionVendor" property="sourceTypeId" />" id="0_<bean:write name="positionVendor" property="sourceTypeId" />" onclick="javascript: toggleSourceCategory(this);"/>
							<bean:write name="positionVendor" property="sourceType" />
						</td>				
					</tr>
					<% 
							sourceCategoryId = positionVendor.getSourceTypeId();						
						} 
					%>
					<script language="JavaScript">
						sourceIds[sourceIds.length] = '0_' + '<bean:write name="positionVendor" property="sourceTypeId" />' + '_' + '<bean:write name="positionVendor" property="sourceId" />';
					</script>
					<tr>
						<td>
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<logic:equal name="positionVendor" property="positionOpenToVendor" value="0">
								<img src="images/checkboxunchecked.gif" name="0_<bean:write name="positionVendor" property="sourceTypeId" />_<bean:write name="positionVendor" property="sourceId" />" id="0_<bean:write name="positionVendor" property="sourceTypeId" />_<bean:write name="positionVendor" property="sourceId" />" onclick="javascript: toggleSource(this);"/>
							</logic:equal>
							<logic:equal name="positionVendor" property="positionOpenToVendor" value="1">
								<img src="images/checkboxchecked.gif" name="0_<bean:write name="positionVendor" property="sourceTypeId" />_<bean:write name="positionVendor" property="sourceId" />" id="0_<bean:write name="positionVendor" property="sourceTypeId" />_<bean:write name="positionVendor" property="sourceId" />" onclick="javascript: toggleSource(this);"/>
							</logic:equal>
							<bean:write name="positionVendor" property="sourceTitle" />
						</td>
					</tr>
					<input type="hidden" name="<%=positionVendor.getSourceId() + "_val"%>" id="<%=positionVendor.getSourceId() + "_val"%>" value="<%=positionVendor.getPositionOpenToVendor()%>"/>
				</logic:iterate>			
			</table>		
			</div>
			<div class="popupBody">
				<table  border="0" cellspacing="0" cellpadding="0" width="100%">		
					<tr>
						<td>
							<div class="navBtn" style="margin-top:5px;float:right;">
								<a href="#" style="width:60px;" class="active" onclick="javascript:publish();"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.submit"/></a>
								<a href="#" style="width:60px; margin-left:5px;" class="active" onclick="javascript: window.top.hidePopWin(false);return false;"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.cancel"/></a>
							</div>
						</td>
					</tr>			
				</table>
			</div>				
		</div>
	</logic:notEmpty>
</div>
<br></br>
</html:form>
<script language="JavaScript">
window.onload=doOnLoad;
function doOnLoad() {
	setPopupTitle();
	if(sourceIds!=undefined && sourceIds.lengt>=0){
		for (var i = 0; i < sourceIds.length; i++) {
			determineStateOfSourceCategory(sourceIds[i]);
		}
		determineStateOfSelectAll();
	}
	
}

function setPopupTitle(){
	var title = '<b>' + '<bean:message key="common.publish" /> <bean:message key="common.position" />' + ' - ' + '<bean:write name="positionTitle" scope="request" />' + '</b>';	
	window.top.setPopTitle(title);
}

var chkedChkBox = 'images/checkboxchecked.gif';
var unChkedChkBox = 'images/checkboxunchecked.gif';
var triStateChkBox = 'images/trueTri.gif';

function toggleSelectAll(obj) {
	var source = obj.src;
	if (source.indexOf(chkedChkBox) != -1) {
		obj.src = unChkedChkBox;		
		toggleTreeState(unChkedChkBox);
	} else {
		obj.src = chkedChkBox;
		toggleTreeState(chkedChkBox);
	}
}

function toggleTreeState(image) {
	for (var i = 0; i < sourceTypeIds.length; i++) {
		document.getElementById(sourceTypeIds[i]).src = image;
	}	
	for (var i = 0; i < sourceIds.length; i++) {
		document.getElementById(sourceIds[i]).src = image;
		var parts = sourceIds[i].split('_');
		if (image == chkedChkBox) {
			document.getElementById(parts[2] + '_val').value = 1;
		} else {
			document.getElementById(parts[2] + '_val').value = 0;
		}
	}
}

function toggleSourceCategory(obj) {
	var source = obj.src;
	if (source.indexOf(chkedChkBox) != -1) {
		obj.src = unChkedChkBox;		
		toggleSources(unChkedChkBox, obj.id);
	} else {
		obj.src = chkedChkBox;
		toggleSources(chkedChkBox, obj.id);
	}	
	determineStateOfSelectAll();
}

function toggleSources(image, objectId) {
	for (var i = 0; i < sourceIds.length; i++) {
		var parts = sourceIds[i].split('_');
		if (objectId == (parts[0] + '_' + parts[1])) {
			document.getElementById(sourceIds[i]).src = image;				
			if (image == chkedChkBox) {
				document.getElementById(parts[2] + '_val').value = 1;
			} else {
				document.getElementById(parts[2] + '_val').value = 0;
			}			
		}
	}
}

function determineStateOfSelectAll() {
	var cnt1 = 0;
	var cnt2 = 0;
	for (var i = 0; i < sourceTypeIds.length; i++) {
		var source = document.getElementById(sourceTypeIds[i]).src;
		if (source.indexOf(chkedChkBox) != -1) {
			cnt1 += 1;
		} else if (source.indexOf(unChkedChkBox) != -1) {
			cnt2 += 1;
		}
	}
	if (cnt1 == sourceTypeIds.length) {
		document.getElementById("0").src = chkedChkBox;
	} else if (cnt2 == sourceTypeIds.length) {
		document.getElementById("0").src = unChkedChkBox;
	} else {
		document.getElementById("0").src = triStateChkBox;
	}
}

function toggleSource(obj) {
	var source = obj.src;	
	var val = obj.id;
	parts = val.split('_');
	if (source.indexOf(chkedChkBox) != -1) {
		obj.src = unChkedChkBox;		
		determineStateOfSourceCategory(obj.id);		
		document.getElementById(parts[2] + '_val').value = 0;
	} else {
		obj.src = chkedChkBox;
		determineStateOfSourceCategory(obj.id);
		document.getElementById(parts[2] + '_val').value = 1;
	}	
	determineStateOfSelectAll();
}

function determineStateOfSourceCategory(objectId) {	
	var cnt1 = 0;
	var cnt2 = 0;
	var cnt3 = 0;
	var parts = objectId.split('_');
	var prefix = parts[0] + '_' + parts[1];
	for (var i = 0; i < sourceIds.length; i++) {
		var source = document.getElementById(sourceIds[i]).src;
		var parts = sourceIds[i].split('_');		
		if (prefix == (parts[0] + '_' + parts[1])) {			
			cnt3 += 1;
			if (source.indexOf(chkedChkBox) != -1) {
				cnt1 += 1;
			} else if (source.indexOf(unChkedChkBox) != -1) {
				cnt2 += 1;
			}			
		}		
	}	
	if (cnt1 == cnt3) {
		document.getElementById(prefix).src = chkedChkBox;
	} else if (cnt2 == cnt3) {
		document.getElementById(prefix).src = unChkedChkBox;
	} else {
		document.getElementById(prefix).src = triStateChkBox;
	}
}

function publish(){
		document.positionForm.submit();
}

function getFDate(obj,format){
	dtfo.setDisplayFormat(format);
	if(obj.value.trim()!=''){
	if(!dtfo.checkDate(obj)){
		obj.select();
		alert("Please enter date in " + format + " format");
		obj.focus();
		return false;
	}else {
		return true;
	}
	}
	return true;
}
</script>
</logic:notPresent>