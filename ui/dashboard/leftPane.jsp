<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ page import="java.util.List,
                com.talentPool.user.dataobject.LastViewedEntity,
                com.talentPool.common.NavigationConstants,
                com.talentPool.user.UserConstants"%>
<bean:define id="lastViewedEntities" name="lastViewedEntities" scope="request" type="List"/>
<bean:define id="recentSearches" name="recentSearches" scope="request" type="List"/>
<logic:equal value="true" name="permissionSet" scope="session" property="PERMISSION_SCREEN">
<script language="JavaScript" src="js/commonFunctions.js" type="text/javascript"></script>
<div style="margin-left:18px; margin-right:18px;"><br /> 
  <span class="greenBullet">&raquo; </span><a href="#" class="green" onclick="javascript:savedSearches(); return false;"><strong><bean:message key="common.saved_searches"/></strong></a>
</div>
<logic:equal value="true" name="permissionSet" scope="session" property="PERMISSION_SCREEN">	
<div style="margin-left:18px; margin-right:18px;"><br /> 
  <span class="greenBullet">&raquo; </span><a href="#" class="green" onclick="javascript:searchForPosition(); return false;"><strong><bean:message key="leftpane.label.search_by_requirements"/></strong></a>
</div>
</logic:equal> 
<logic:equal value="true" name="permissionSet" scope="session" property="PERMISSION_SCREEN">	
<div style="margin-left:18px; margin-right:18px;"><br /> 
  <span class="greenBullet">&raquo; </span><a href="doSearch.do?mode=newApplicants" class="green"><strong><bean:message key="leftpane.label.new_applicants"/></strong></a>
</div>
</logic:equal> 
<div style="border-bottom:1px solid #CCCCCC">&nbsp;</div>
	
<div style="margin-left:18px; margin-right:18px;"> 
  <logic:notEqual value="<%=NavigationConstants.T_SCREEN%>" scope="request" name="t">
  <table width="100%" border="0" cellspacing="0" cellpadding="0"> 
    <tr> 
      <td colspan="2"><img src="images/spacer.gif" width="1" height="13" /></td> 
    </tr> 
    <tr> 
      <td colspan="2"><strong class="Grey"><bean:message key="leftpane.label.search"/></strong></td> 
    </tr> 
    <tr> 
      <td colspan="2"><img src="images/spacer.gif" width="1" height="8" /></td> 
    </tr>
    <html:form action="/doSearch">
    
	<html:hidden property="mode" value="doSearch"/>
      <tr> 
        <td width="69%" valign="top"><input name="searchText" type="text" style="width:124px;" class="txt1"/>&nbsp;</td> 
        <td width="31%" valign="top"><div class="navBtn"><a href="#" style="width:30px;margin-left: 2px;" class="active" onclick="javascript:document.searchForm.submit();return false;"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.cap_go"/></a></div>
        </td> 
      </tr> 
      <tr> 
        <td height="18" colspan="2" valign="bottom"><span class="greenBullet">&raquo;</span> <a href="doSearch.do?mode=search&advancedSearch=1" class="green"><bean:message key="leftpane.label.adv_search"/></a></td> 
      </tr> 
    </html:form> 
  </table> 
  </logic:notEqual>
  <table width="100%" border="0" cellspacing="0" cellpadding="0"> 
    <tr> 
      <td height="20">&nbsp;</td> 
    </tr> 
    <tr> 
      <td height="20"><strong class="Grey"><bean:message key="leftpane.label.recent"/></strong></td> 
    </tr> 
    <tr> 
      <td style="line-height:18px;">
      	<logic:notEmpty name="recentSearches">
				<logic:iterate id="entity" name="recentSearches" type="LastViewedEntity">
			      	<span class="greenBullet">&raquo;</span> <a href="#" class="green" onclick="javascript: doSearch('<bean:write name="entity" property="entityId"/>');return false;" title="<bean:write name="entity" property="title"/>">
			      	<logic:empty name="entity" property="description">[no keywords]</logic:empty>
			      	<logic:notEmpty name="entity" property="description"><bean:write name="entity" property="descriptionTrimmed"/></logic:notEmpty>
			      	</a><br /> 
				</logic:iterate>
		</logic:notEmpty>
	  </td> 
    </tr> 
  </table> 
</div> 
<div style="border-bottom:1px solid #CCCCCC">&nbsp;</div> 
</logic:equal>
<div style="margin-left:18px; margin-right:18px;"> 
  <table width="100%" border="0" cellspacing="0" cellpadding="0"> 
    <tr> 
      <td><img src="images/spacer.gif" width="1" height="13" /></td> 
    </tr> 
    <tr> 
      <td height="20"><strong class="Grey"><bean:message key="leftpane.label.viewed"/></strong></td> 
    </tr> 
    <tr> 
      <td style="line-height:18px;"> 
		<logic:notEmpty name="lastViewedEntities">
		<logic:iterate id="entity" name="lastViewedEntities" type="LastViewedEntity">
			<logic:equal name="entity" property="entityType" value="<%=UserConstants.ENTITY_CANDIDATE%>">
		      	<img src="images/ico_profile.gif" vspace="3" align="absmiddle" /> <a href="#" onclick="javascript: viewApplicant('<bean:write name="entity" property="entityId"/>');return false;" class="green"><bean:write name="entity" property="descriptionTrimmed"/></a><br /> 
			</logic:equal>
			<logic:equal name="entity" property="entityType" value="<%=UserConstants.ENTITY_POSITION%>">
		        <img src="images/ico_star.gif" width="13" height="13" vspace="3" align="absmiddle" /> <a href="#" onclick="javascript: viewPositionSummary('<bean:write name="entity" property="entityId"/>');return false;" class="green"><bean:write name="entity" property="descriptionTrimmed"/></a><br /> 
			</logic:equal>
		</logic:iterate>
		</logic:notEmpty>
    </tr> 
  </table> 
</div> 
<div style="border-bottom:1px solid #CCCCCC">&nbsp;</div> 
<div style="margin-left:18px; margin-right:18px;"> 
<table width="100%" border="0" cellspacing="0" cellpadding="0"> 
    <tr> 
      <td colspan="2"><img src="images/spacer.gif" width="1" height="13" /></td> 
    </tr> 
    <tr> 
      <td colspan="2"><strong class="Grey"><bean:message key="common.last_login" />&nbsp;<bean:message key="common.colon" /></strong></td> 
    </tr> 
    <tr> 
      <td colspan="2"><img src="images/spacer.gif" width="1" height="13" /></td> 
    </tr>
    <tr> 
      <td colspan="2"><bean:write name="lastLogin" scope="session" /></td> 
    </tr>
</table>
</div>
<br/>
<script language="JavaScript">
function viewApplicant(aId){
	//var app = 
	var hilite = '';
	if($("searchText")) {		
		var val = $("searchText").value;
		if(val != '') {			
			parts = val.split(',');
			for(i = 0; i < parts.length; i++) {
				parts2 = parts[i].split(/[\s]+/);	
				for(j = 0; j < parts2.length; j++) {
					if(hilite != '') {
						hilite += ',';
					}
					hilite += formatString(parts2[j].trim()) + "|hl1";
				}
			}
		}		
	}
	url = "selectionProcess.do?mode=viewOriginalResume&applicantId=" + aId;
	if(hilite != '') {
		url += '&hilite='+hilite;
	}
	window.open(url,aId,'width=1024,height=768,left=0,top=0,scrollbars=yes,resizable=yes,status=no');
	//app.focus();
	return false;
}
function viewPosition(id) {
	window.location="position.do?mode=description&positionId=" + id;
}
function viewPositionSummary(positionId){
	window.location.href=uncache("position.do?mode=positionSummary&positionId="+positionId);
}
function doSearch(id) {
	window.location="doSearch.do?mode=displaySearchResult&save=0&searchId=" + id ;
}

function savedSearches(){
	var url = 'doSearch.do?mode=showSavedSearches';
	showInPopUp(url, 620, 380,doSaveSearch);	
}

function doSaveSearch(returnVal){
	if(returnVal !='' && returnVal !=null){
		var url = 'doSearch.do?mode=doSavedSearch&searchId='+returnVal;
		window.location=url;
	}
	return true;
}
</script>