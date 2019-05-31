<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@page import="org.apache.struts.Globals"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="com.talentPool.admin.dataobject.DuplicateSettingsData"%>
<%@page import="com.talentPool.common.utils.Utils"%>
<%@page import="com.talentPool.admin.DuplicateSettingsConstants"%>
<%@page import="com.talentPool.user.manager.ModuleSet"%>
<script language="JavaScript" src="js/scripta/lib/prototype.js" type="text/javascript"></script>
<script language="JavaScript" src="js/scripta/src/effects.js" type="text/javascript"></script>
<script language="JavaScript" src="js/selectoption.js" type="text/javascript"></script>
<script language="JavaScript" src="js/selectbox/selectbox.js" type="text/javascript"></script>
<script type="text/javascript">
<!--
var cboInternalName=null;
var cboVendorName=null;
var opts = [new SelectOption('0','<bean:message key="duplicate_detection_settings.label.exact_match"/>'),new SelectOption('1','<bean:message key="duplicate_detection_settings.label.approx_match"/>')]; 
//-->
</script>
<html:form action="/adminHome">
<html:hidden property="mode" value="duplicatePositionDetectionSettings"/>
<input type="hidden" name="isSubmitted" value="1"/>
<div class="contentDiv">
	<div id="divError" style="display:block">
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
	<br/>
	<% } %>
	<%
		String saved = (String)request.getAttribute("saved");
		if(saved !=null){
	%>
			<table  id="m_errortable" > 
				<tr>
			    <td class="header">
			        <b><bean:message key="duplicate_detection_settings.label.updated_successful"/></b>
			    </td>               
				</tr>
			</table>
			<br/>
	<%
		}
	%>
	</div>
</div>
<%
ArrayList internalSettings = (ArrayList)request.getAttribute("internalSettings");
/* ArrayList vendorSettings = (ArrayList)request.getAttribute("vendorSettings");
ArrayList employeeSettings = (ArrayList)request.getAttribute("employeeSettings"); */
List settings = (List)request.getAttribute("settings");
%>
<div class="contentDivPop" style="padding-right:20px;">
   	<table width="100%" border="0" cellspacing="0" cellpadding="0"> 
	  <tr> 
	    <td><div style="width:190px;" class="boxTab"><span class="rightC"></span><span class="leftC"></span><bean:message key="duplicate_detection_settings.label.duplicate_settings"/></div></td> 
	  </tr> 
	</table> 
	<table class="boxHeader" cellspacing="0" cellpading="0">
		<tr>
			<td height="18" style="width:250px;"><strong><bean:message key="duplicate_detection_settings.label.fields"/></strong></td>
			<td height="18" style="width:200px;"><strong><bean:message key="duplicate_detection_settings.label.internal"/></strong></td>
			
		</tr>
	</table>
	<table border="0" cellspacing="0" cellpadding="0" class="posinput" style="border:1px solid #99CC33; border-top: none;">
		<%			
			for(int i=0; i<settings.size(); i++){
			
			String cls = "row1";
			if(i%2==0){
				cls = "row2";
			}
			if(i==settings.size()-1){
				cls += " lastrow";
			}
			
			DuplicateSettingsData data = (DuplicateSettingsData)settings.get(i);
			
		%>
		<tr>	
			<td class="label <%=cls %>" style="width:250px;padding-left:4px;padding-right:4px;height:19px;"><%=data.getFieldLabel() %></td>
			<% 
				DuplicateSettingsData idata =null;
				for(int k=0; k<internalSettings.size(); k++){
					idata = (DuplicateSettingsData)internalSettings.get(k);
					if(idata.getFieldId().equals(data.getFieldId()) && idata.getFieldType().equals(data.getFieldType())){
						break;
					}
					idata=null;
				}
			%>
			<td class="<%=cls %>" style="width:200px;padding-left:4px; padding-right:4px;">
			<%
			if(idata!=null){
					
			String id = idata.getFieldId() + "_" + idata.getFieldType() + "_" + idata.getCheckFor();
			if(Utils.isBlankOrNull(idata.getFieldCheckType())){ %>
				<input type="hidden" name="<%=id%>" id="<%=id%>" value=""/>
		  		<img src="images/checkboxunchecked.gif" id="<%=id%>_img" name="<%=id%>_img" onclick="javascript: changeCheckboxState(this);" />				  		
			<% }else{ %>
				<input type="hidden" name="<%=id%>" id="<%=id%>" value="<%=idata.getFieldCheckType()%>"/>
		  		<img src="images/checkboxchecked.gif" id="<%=id%>_img" name="<%=id%>_img" onclick="javascript: changeCheckboxState(this);" />				  		
			<%}	%>
			<% 
				if(idata.getFieldId().equals(DuplicateSettingsConstants.POSITION_NAME_ID) && idata.getFieldType().equals(DuplicateSettingsConstants.FIELD_TYPE_SYSTEM)){
			%>
			<script type="text/javascript">
				cboInternalName = new SelectBox(opts,'<%=idata.getFieldCheckType()%>','images/btn_dropdown.gif',{namesonly:false, width:'130px', size:2});
				document.write(cboInternalName.getHtml());
				cboInternalName.init();
				<% if(Utils.isBlankOrNull(idata.getFieldCheckType())){ %>
				cboInternalName.hideMe();
				<% }%>
			</script>
			<% }  %>
			<% } else { %>				
			&nbsp;
			<%} %>
			</td>

			
			
			
			
			
		</tr>
		<% } %>
	</table>
	<div class="navBtn" style="margin-top:5px;">
		<a href="#" style="width:60px;" class="active" onclick="javaScript:submitfrm();return false;"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.submit"/></a>
	</div>
</div>	
</html:form>

<script language="javascript">
function changeCheckboxState(chkBox){
	var fldId = chkBox.id;
	var checked = false;
	if(chkBox.src.indexOf('checkboxchecked.gif') == -1){
		chkBox.src='images/checkboxchecked.gif';
		$(fldId.substr(0,fldId.length-4)).value = '0';
		checked=true;
	}else{
		chkBox.src='images/checkboxunchecked.gif';
		$(fldId.substr(0,fldId.length-4)).value = '';
	}
	
	var fields = fldId.split('_');
	if(fields[0]=='<%=DuplicateSettingsConstants.POSITION_NAME_ID%>' && 	fields[1]=='<%=DuplicateSettingsConstants.FIELD_TYPE_SYSTEM%>'){
		if(fields[2]=='<%=DuplicateSettingsConstants.SETTINGS_INTERNAL%>'){		
			if(checked){
				cboInternalName.showMe();
			}else{
				cboInternalName.hideMe();
			}
		}
	}	
}

function submitfrm(){
	var inernalNameId= '<%=DuplicateSettingsConstants.POSITION_NAME_ID%>_<%=DuplicateSettingsConstants.FIELD_TYPE_SYSTEM%>_<%=DuplicateSettingsConstants.SETTINGS_INTERNAL%>' ;
	if($(inernalNameId).value!=''){
		$(inernalNameId).value = cboInternalName.getSelectedId();
	}
	
	
	var frm=document.adminForm;
	frm.submit();
}
</script>