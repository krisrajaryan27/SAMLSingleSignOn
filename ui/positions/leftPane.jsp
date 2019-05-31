<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@page import="com.talentPool.user.manager.ModuleSet"%>
<%@page import="com.talentPool.positions.PositionConstants"%>
<script language="JavaScript" src="js/ajaxfunctions.js" type="text/javascript"></script>
<script language="JavaScript" src="js/scripta/lib/prototype.js" type="text/javascript"></script>
<script language="JavaScript" src="js/scripta/src/effects.js" type="text/javascript"></script>
<script language="JavaScript" src="js/selectoption.js" type="text/javascript"></script>
<script language="JavaScript" src="js/checkboxlist/checkboxlist.js" type="text/javascript"></script>

<script>
var checkboxListPositions=null;
</script>
<logic:equal name="positionForm" property="dir" value="">
<div id="publishOptionsDiv">
<div style="margin-left:18px; margin-right:18px;"  > 
 	<table width="100%" border="0" cellspacing="0" cellpadding="0" id="publishOptionsTable"> 
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
  	<%} %>	
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
<div style="margin-left:18px; margin-right:18px;"  > 
 	<table width="100%" border="0" cellspacing="0" cellpadding="0" id="publishOptionsTable"> 
    <tr> 
      <td colspan="2"><img src="images/spacer.gif" width="1" height="13" /></td> 
    </tr> 
    <tr> 
      <td colspan="2"><strong class="Grey"><bean:message key="position.home.publish_announcements"/></strong></td> 
    </tr>
    <tr> 
      <td colspan="2"><img src="images/spacer.gif" width="1" height="8" /></td> 
    </tr>  
    <% if(ModuleSet.isMODULE_EMPLOYEE()){ %>
	<logic:equal value="true" name="permissionSet" scope="session" property="PERMISSION_PUBLISH_POSITION_FOR_EMPLOYEE_PORTAL">
    <tr> 
    <logic:equal name="publish" scope="request" value="<%=PositionConstants.EMPLOYEE_ANNOUNCEMENTS%>">
      <td height="18" colspan="2" valign="bottom"><span class="greenBullet">&raquo;</span> <bean:message key="position.home.publish_option_employee_portal"/></td> 
  	</logic:equal>
  	<logic:notEqual name="publish" scope="request" value="<%=PositionConstants.EMPLOYEE_ANNOUNCEMENTS%>">
      <td height="18" colspan="2" valign="bottom"><span class="greenBullet">&raquo;</span> <a href="position.do?mode=getEmployeeAnnouncementsToPublish" class="green"><bean:message key="position.home.publish_option_employee_portal"/></a></td> 
  	</logic:notEqual>      
  	</tr>  
  	</logic:equal>		
	<% } %>	
   </table>
</div>
<div style="border-bottom: 1px solid rgb(204, 204, 204);">&nbsp;</div>
</div>
</logic:equal>
<script language="javascript">

<logic:equal name="positionForm" property="dir" value="">
displayPublishOptionsDiv();
</logic:equal>

function displayPublishOptionsDiv(){
	var oRows = document.getElementById('publishOptionsTable').getElementsByTagName('tr');		
	if(oRows.length==3){
		document.getElementById('publishOptionsDiv').style.display = 'none'; 
	}
			
}
	
</script>