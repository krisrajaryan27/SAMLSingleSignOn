<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ page import="org.apache.struts.Globals,
				  com.talentPool.common.properties.GlobalApplicationProperties,
				  com.talentPool.common.properties.GlobalConstants,
                  com.talentPool.common.NavigationConstants, 
                  com.talentPool.user.dataobject.RoleData,
                  com.talentPool.user.UserConstants,
                  com.talentPool.masters.constants.MastersConstants,
                  com.talentPool.common.utils.CommonUtils"%>
<%@page import="com.talentPool.inbox.InboxConstants"%>
<%@page import="com.talentPool.common.properties.TPApplicationProperties"%>
<%@page import="com.talentPool.masters.constants.MastersConstants"%>
<%@ page import="java.security.KeyPair"%>
<%@ page import="com.talentPool.encryption.JCryptionUtil"%>
<script src="encryption/js/jquery-2.0.3.min.js" type="text/javascript"></script>
<script src="encryption/js/jquery-ui-1.8.2.custom.min.js" type="text/javascript"></script>
<script src="js/encrypt.js" type="text/javascript"></script>
<script src="encryption/js/jquery.jcryption-1.1.js" type="text/javascript"></script>
<script src="js/dhtmlxGrid/dhtmlXCommon.js"></script> 
<script src="js/dhtmlxGrid/dhtmlXGrid.js"></script> 
<script src="js/dhtmlxGrid/dhtmlxgrid_filter.js"></script>
<script src="js/dhtmlxGrid/dhtmlXGridCell.js"></script>
<script src="js/dhtmlxGrid/dhtmlXGrid_excell_link.js"></script>
<script src="js/dhtmlxGrid/dhtmlXGrid_drag.js"></script>
<script language="JavaScript" src="js/scripta/lib/prototype.js" type="text/javascript"></script>
<script language="JavaScript" src="js/scripta/src/effects.js" type="text/javascript"></script>
<script language="JavaScript" src="js/selectoption.js" type="text/javascript"></script>
<script language="JavaScript" src="js/doClasses/IdValueClass.js" type="text/javascript"></script>
<script language="JavaScript" src="js/selectbox/selectbox.js" type="text/javascript"></script>
<script language="JavaScript" src="js/ajaxfunctions.js" type="text/javascript"></script>
<script language="JavaScript" src="js/checkboxlist/checkboxlist.js" type="text/javascript"></script>
<script language="JavaScript" src="js/checkboxandradiogroup/checkboxradiogroup.js" type="text/javascript"></script>
<script language="JavaScript" src="js/submodal/common.js" type="text/javascript"></script>
<script language="JavaScript" src="js/submodal/subModal.js" type="text/javascript"></script>
<script language="JavaScript" src="js/scripta/src/controls.js" type="text/javascript"></script>
<script language="JavaScript" src="js/commonFunctions.js" type="text/javascript"></script>
<script language="JavaScript" src="js/tpSelectListFunctions.js"></script>
<link rel="stylesheet" type="text/css" href="themes/default/autoComplete.css">

<script language="JavaScript" >
var selectBoxSource = null;
var selectBoxSub3Department = null;
var selectBoxSub4Department = null;
</script>
<logic:present name="update" scope="request">	
	<script>
		window.top.hidePopWin(true);
	</script>
</logic:present> 
<logic:notPresent name="update" scope="request">
<div class="contentDivPop" style="width: 600px;">
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
<logic:present name="companyName" scope="request">	
	<bean:define id="ldapEnabled" value="<%=GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_LDAP_ENABLED)%>"></bean:define>
	<div id="autocomplete" class="autocomplete" ></div>
	<div class="outerDiv">
		<html:form action="/adminHome" onsubmit="return submitForm();">
		<html:hidden property="mode" name="adminForm" value="saveUser"/>
		<html:hidden property="t" name="adminForm"/>
		<html:hidden property="selectedRoleIds"/>
		<html:hidden property="userId" name="adminForm"/>
		<html:hidden property="userSourceId" name="adminForm"/>
		<html:hidden property="isUserLdapSetting" name="adminForm"/>
		<html:hidden property="parentId" name="adminForm"/>
		<html:hidden property="departmentId" name="adminForm"/>
		<html:hidden property="subDepartmentId" name="adminForm"/>
		<html:hidden property="subSubDepartmentId" name="adminForm"/>
		<html:hidden property="sub3DepartmentId" name="adminForm"/>
		<html:hidden property="sub4DepartmentId" name="adminForm"/>
		<html:hidden property="locationId" name="adminForm"/>
		<html:hidden property="gradeId" name="adminForm"/>
		<html:hidden property="bandId" name="adminForm"/>
		<html:hidden property="buId" name="adminForm"/>
		<html:hidden property="costCenterId" name="adminForm"/>		
		<html:hidden property="businessUnitXML" name="adminForm"/>
		<html:hidden property="costCenterXML" name="adminForm"/>
		<html:hidden property="rnd" name="adminForm"/>
		<input type="hidden" name="myVar"/>
				
		
		<div class="popupTop">
		<table class="tblPop">			
		<tr>
			<td>
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td>
					<table width="100%" border="0" cellspacing="0" cellpadding="0" class="posinput">
					<tr>
						<td width="110px;" <html:errormap property="add_user.errors.short_username|add_user.errors.invalid_username|add_user.errors.username_nospace" errorStyleClass="labelError" styleClass="label"/> nowrap="nowrap">
							<bean:message key="add_user.label.username"/>
							<span class="star">*</span>
					    </td>
					    <td>
						     <html:text property="userName" styleId="userName" size="30" maxlength="50" name="adminForm" ></html:text>
						     <%
								if (GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_LDAP_ENABLED).equals(GlobalConstants.ENABLED)) {
							 %>  
						     <script>
								new Ajax.Autocompleter("userName", "autocomplete", "adminHome.do?mode=getLdapUserAutoCompleteList", {frequency: 0.001, tokens: [',',';'], onHide: function(element, update){onCompletetFn(update);}  });
							  </script>  
							  <%} %>
					     </td>
			        </tr>
					<logic:empty name="adminForm" property="userId">
					<tr>
						<td <html:errormap property="add_user.errors.short_password|add_user.errors.retype_password" errorStyleClass="labelError" styleClass="label"/> nowrap="nowrap">
							<bean:message key="add_user.label.password"/>
							<span class="star">*</span>
					    </td>
					    <td>
							<input type="password" autocomplete="off" name="password" size="20" maxlength="50"/>    
					    </td>	            
					</tr>
					<tr>
						<td <html:errormap property="add_user.errors.retype_password" errorStyleClass="labelError" styleClass="label"/> nowrap="nowrap">
							<bean:message key="add_user.label.retype_password"/>
							<span class="star">*</span>
						</td>
						<td>
							<input type="password" autocomplete="off" name="reTypedPassword" size="20" maxlength="50"/>    
						</td>
					</tr>
					</logic:empty>
					<tr>
						<td <html:errormap property="add_user.errors.fname_required|add_user.errors.invalid_fname" errorStyleClass="labelError" styleClass="label"/> nowrap="nowrap">
							<bean:message key="add_user.label.fname"/>
							<span class="star">*</span>
						</td>
					    <td>
					    	<logic:equal value="<%=GlobalConstants.ENABLED%>" name="ldapEnabled" scope="page">
						    	<html:text property="firstName" size="20" maxlength="20" name="adminForm" disabled="true"></html:text>
							</logic:equal>
							<logic:equal value="<%=GlobalConstants.DISABLED%>" name="ldapEnabled"  scope="page">
					    		<html:text property="firstName" size="20" maxlength="20" name="adminForm"></html:text>
							</logic:equal>
						</td>
					</tr>
					<tr>
						<td <html:errormap property="add_user.errors.lname_required|add_user.errors.invalid_lname" errorStyleClass="labelError" styleClass="label"/> nowrap="nowrap">
							<bean:message key="add_user.label.lname"/>
							<span class="star">*</span>
						</td>
					    <td>
					   		<logic:equal value="<%=GlobalConstants.ENABLED%>" name="ldapEnabled" scope="page">
					   			<html:text property="lastName" size="20" maxlength="20" name="adminForm" disabled="true"></html:text>
					    	</logic:equal>
							<logic:equal value="<%=GlobalConstants.DISABLED%>" name="ldapEnabled"  scope="page">
					    		<html:text property="lastName" size="20" maxlength="20" name="adminForm"></html:text>
							</logic:equal>
						</td>
					</tr>
					<tr>
						<td <html:errormap property="add_user.errors.invalid_email" errorStyleClass="labelError" styleClass="label"/> nowrap="nowrap">
							<bean:message key="add_user.label.email"/>
							<span class="star">*</span>
						</td>
					    <td>
					    	<logic:equal value="<%=GlobalConstants.ENABLED%>" name="ldapEnabled" scope="page">
					   			<html:text property="email" size="30" maxlength="50" name="adminForm" disabled="true"></html:text>
					    	</logic:equal>
							<logic:equal value="<%=GlobalConstants.DISABLED%>" name="ldapEnabled"  scope="page">
					    		<html:text property="email" size="30" maxlength="50" name="adminForm"></html:text>
							</logic:equal>
						</td>
					</tr>
				</table>
					
				<div id="empDiv" style="display: block;">
					<table cellspacing="0" cellpadding="0" class="posinput">
						<tr>
							<td class="label" nowrap="nowrap" width="110px;">
								<bean:message key="add_user.label.employee_code" />
								<span class="star">*</span>
							</td>
						    <td>
								<html:text property="employeeCode" size="20" maxlength="20" name="adminForm"></html:text>    
							</td>					            
						</tr>
					</table>
				</div>
				<%
					if (GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_LDAP_ENABLED).equals(GlobalConstants.ENABLED)) {
				 %>
				 <table cellspacing="0" cellpadding="0" class="posinput">
				<tr>
					<td width="110px;" class="label" nowrap="nowrap"><bean:message key="add_user.label.skip_ldap_setting" />
					</td>
				    <td>
						<img id="userLdapSetting" src="" onclick="javascript: skipLdapSetting(this);" />   
					</td>					            
				</tr>
				</table>
				<% } %>
				
				<table width="100%" border="0" cellspacing="0" cellpadding="0" class="posinput">
					<tr>
						<td class="label" nowrap="nowrap" width="110px;">
							<bean:message key="add_user.label.home_phone" />
						</td>
						<td>
							<html:text property="homePhone" size="20" maxlength="20" name="adminForm"></html:text>    
						</td>
					</tr>
					<tr>
						<td class="label" nowrap="nowrap">
							<bean:message key="add_user.label.cell_phone" />
						</td>
					    <td>
							<html:text property="cellPhone" size="20" maxlength="20" name="adminForm"></html:text>    
						</td>					            
					</tr>					
					 
					<tr>
						<td class="label" nowrap="nowrap"><bean:message key="common.location" /></td>
						<td>
							<script language="JavaScript">									
								var opts = <%=request.getAttribute("jsArrayLocations")%>;											
								var opt = [new SelectOption('-1','<bean:message key="common.selectlist.select" />')];
								locations = opt.concat(opts);
								selectBoxLocations = new SelectBox(locations,'<bean:write name="adminForm" property="locationId" />','images/btn_dropdown.gif',{namesonly:false, width:'170px', size:10, textboxclass:''});
								document.write(selectBoxLocations.getHtml());
								selectBoxLocations.init();
							</script>											
						</td>
					</tr>	
					<tr>
						<td class="label" nowrap="nowrap"><%=GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_DEPARTMENT_LEVEL_1) %></td>
						<td>
							<script language="JavaScript">	
								var opts = <%=request.getAttribute("jsArrayDepartments")%>;											
								var opt = [new SelectOption('-1','<bean:message key="common.selectlist.default" />')];
								departments = opt.concat(opts);
								selectBoxDepartment = new SelectBox(departments,'<bean:write name="adminForm" property="departmentId" />','images/btn_dropdown.gif',{namesonly:false, width:'170px', size:10, textboxclass:''});
								selectBoxDepartment.setOnChangeHandler('loadSubdepartments');
								document.write(selectBoxDepartment.getHtml());
								selectBoxDepartment.init();
							</script>
						</td>
					</tr>
					<tr>
						<td class="label" nowrap="nowrap"><%=GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_DEPARTMENT_LEVEL_2) %></td>
						<td>
							<script language="JavaScript">	
								var opts = <%=request.getAttribute("jsArraySubDepartments")%>;											
								var opt = [new SelectOption('-1','<bean:message key="common.selectlist.default" />')];
								departments = opt.concat(opts);
								selectBoxSubDepartment = new SelectBox(departments,'<bean:write name="adminForm" property="subDepartmentId" />','images/btn_dropdown.gif',{namesonly:false, width:'170px', size:10, textboxclass:''});
								selectBoxSubDepartment.setOnChangeHandler('loadSubSubdepartments');
								document.write(selectBoxSubDepartment.getHtml());
								selectBoxSubDepartment.init();
							</script>
						</td>
					</tr>
					<tr>
						<td class="label" nowrap="nowrap"><%=GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_DEPARTMENT_LEVEL_3) %></td>
						<td>
							<script language="JavaScript">	
								var opts = <%=request.getAttribute("jsArraySubSubDepartments")%>;											
								var opt = [new SelectOption('-1','<bean:message key="common.selectlist.default" />')];
								departments = opt.concat(opts);
								selectBoxSubSubDepartment = new SelectBox(departments,'<bean:write name="adminForm" property="subSubDepartmentId" />','images/btn_dropdown.gif',{namesonly:false, width:'170px', size:10, textboxclass:''});
								selectBoxSubSubDepartment.setOnChangeHandler('loadSub3departments');
								document.write(selectBoxSubSubDepartment.getHtml());
								selectBoxSubSubDepartment.init();
							</script>
						</td>
					</tr>	
					<tr id="dept_level_4">
						<td class="label" nowrap="nowrap"><%=GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_DEPARTMENT_LEVEL_4) %></td>
						<td>
							<script language="JavaScript">	
								var opts = <%=request.getAttribute("jsArraySub3Departments")%>;											
								var opt = [new SelectOption('-1','<bean:message key="common.selectlist.default" />')];
								departments = opt.concat(opts);
								selectBoxSub3Department = new SelectBox(departments,'<bean:write name="adminForm" property="sub3DepartmentId" />','images/btn_dropdown.gif',{namesonly:false, width:'170px', size:10, textboxclass:''});
								selectBoxSub3Department.setOnChangeHandler('loadSub4departments');
								document.write(selectBoxSub3Department.getHtml());
								selectBoxSub3Department.init();
							</script>
						</td>
					</tr>	
					<tr id="dept_level_5">
						<td class="label" nowrap="nowrap"><%=GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_DEPARTMENT_LEVEL_5) %></td>
						<td>
							<script language="JavaScript">	
								var opts = <%=request.getAttribute("jsArraySub4Departments")%>;											
								var opt = [new SelectOption('-1','<bean:message key="common.selectlist.default" />')];
								departments = opt.concat(opts);
								selectBoxSub4Department = new SelectBox(departments,'<bean:write name="adminForm" property="sub4DepartmentId" />','images/btn_dropdown.gif',{namesonly:false, width:'170px', size:10, textboxclass:''});
								document.write(selectBoxSub4Department.getHtml());
								selectBoxSub4Department.init();
							</script>
						</td>
					</tr>	
					
					<tr>
						<td class="label" nowrap="nowrap"><bean:message key="common.grade" /></td>
						<td>
							<script language="JavaScript">									
								var opts = <%=request.getAttribute("jsArrayGrades")%>;											
								var opt = [new SelectOption('-1','<bean:message key="common.selectlist.select" />')];
								grades = opt.concat(opts);
								selectBoxGrades = new SelectBox(grades,'<bean:write name="adminForm" property="gradeId" />','images/btn_dropdown.gif',{namesonly:false, width:'170px', size:10, textboxclass:''});
								document.write(selectBoxGrades.getHtml());
								selectBoxGrades.init();
							</script>											
						</td>
					</tr>	
					<tr>
						<td class="label" nowrap="nowrap"><bean:message key="common.band" /></td>
						<td>
							<script language="JavaScript">									
								var opts = <%=request.getAttribute("jsArrayBands")%>;											
								var opt = [new SelectOption('-1','<bean:message key="common.selectlist.select" />')];
								bands = opt.concat(opts);
								selectBoxBands = new SelectBox(bands,'<bean:write name="adminForm" property="bandId" />','images/btn_dropdown.gif',{namesonly:false, width:'170px', size:10, textboxclass:''});
								document.write(selectBoxBands.getHtml());
								selectBoxBands.init();
							</script>											
						</td>
					</tr>	
					</table>	
					
					<div id="assignDiv" style="display: block;">
					<table cellspacing="0" cellpadding="0" class="posinput">
						<logic:empty name="adminForm" property="userId">
							<tr>
								<td class="label" nowrap="nowrap" width="110px;">
									<bean:message key="common.assign_under" />
								</td>
							    <td>							    
									<script language="JavaScript">	
										var opts = <%=request.getAttribute("jsManagedUser")%>;									
										var m = [new SelectOption('0','<bean:write name="companyName" scope="request"/>')];
										managedUser = m.concat(opts);
										selectBoxManagedUser = new SelectBox(managedUser,'<bean:write name="adminForm" property="parentId" />','images/btn_dropdown.gif',{namesonly:false, width:'170px', size:10, textboxclass:''});
										document.write(selectBoxManagedUser.getHtml());
										selectBoxManagedUser.init();
									</script>
								</td>					            
							</tr>	
						</logic:empty>				
					</table>
					</div>
																
				</td>
				<td valign="top">
					<table width="100%" border="0" cellspacing="0" cellpadding="0" class="posinput">
					<tr>
						<td <html:errormap property="add_user.errors.role_required" errorStyleClass="labelError" styleClass="label"/>>
							<bean:message key="add_user.label.roles"/>
							<span class="star">*</span>
						</td>
						<td>
							<table width="100%" border="0" cellspacing="0" cellpadding="0" class="posinput">
							<logic:iterate id="role" name="roles" type="RoleData" indexId="counter">
							<tr>
								<td>
								<span id="img_<%=counter%>" onclick="javascript: doShowAdditionalOptions(<bean:write name='role' property='roleId'/>, '<%=counter%>');">
								<script>
									if(document.adminForm.selectedRoleIds.value == <%=role.getRoleId()%> ){
										document.write("<img src=\"images/checkedradiobutton.gif\"/>");
									}else{
										document.write("<img src=\"images/radiobutton.gif\"/>");
									}
								</script>		
								</span>
								</td>
								<td>
									<bean:write name="role" property="roleTitle" />&nbsp;&nbsp;
								</td>
							</tr>
							<logic:equal name="role" property="roleId" value="<%=String.valueOf(UserConstants.ROLE_VENDOR)%>">
							<tr>
								<td></td>
								<td>
			    					<div id="vendorSource" style="display:none;">
			    						<script>
			    							var opts = <%=CommonUtils.getListJavaScriptArray(CommonUtils.getSourceIdsWithoutEmployeeSource(),CommonUtils.getSourceNamesWithoutEmployeeSource())%>;
									        var m = [new SelectOption('-1','<bean:message key="common.selectlist.default"/>')];
										    opts = m.concat(opts);
			    							selectBoxSource = new SelectBox(opts,'<bean:write property="userSourceId" name="adminForm" />','images/btn_dropdown.gif',{namesonly:false, width:'170px', size:20});
			    							document.write(selectBoxSource.getHtml());
							                selectBoxSource.init();
			    						</script>
			    					</div>
			    				</td>
			    			</tr>
	    					</logic:equal>
							</logic:iterate>
							</table>
						</td>
					</tr>
					</table>
				</td>
			</tr>
			</table>
		</td>
	</tr>			
	</table>
	<%if("1".equals(GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_BUSINESS_UNIT_PROPERTY))){%>
	<table width="100%" border="0" cellspacing="0" cellpadding="5">
		<tr>
			<td class="label" nowrap="nowrap" width="110px;" style="color:#666666;">
				<%=GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_BUSINESS_UNIT_LABEL)%>&nbsp;
			</td>
			<td align="left" >
			<table cellspacing="0" cellpadding="0" border="0" class="filterGrid" >
				<tr>
					<td style="vertical-align: top;" align="left">
						<table cellpadding="0" cellspacing="0">
						<tr>
							<td>
								<input id="buFilter" name="buFilter" type="text" size="37" onfocus="onFilterFocus('buFilter')" value="Filter" style="width:197px;color: graytext; border-bottom: 0px;" onclick="onFilterFocus('buFilter','Filter');" onblur="onFilterUnfocus('buFilter','Filter')"/>
							</td>
						</tr>
						<tr>
							<td class="gridborder" style="padding: 0px;">
								<div id="BU_GRD" class="gridbox" style="width:200px;height:85px;"></div>
							</td>
						</tr>
						</table>			
					</td>		
					<td style="padding: 10px;vertical-align: middle;" >					
						<a href="#" onclick="javascript: selectItem(buGrid,buGridSelected);return false;" title="<bean:message key='common.add' />" >
							<img src="images/ico_rightarrow.gif"  border="0" />
						</a><br/>
						<a href="#" onclick="javascript: deselectItem(buGridSelected,buGrid);return false;" title="<bean:message key='common.remove' />" >
							<img src="images/ico_leftarrow.gif"  border="0" />
						</a> 
					</td>					
					<td style="vertical-align: top;">
						<table cellpadding="0" cellspacing="0">
						<tr>
							<td class="gridborder">
								<div id="BU_GRD_SELECTED"  class="gridbox" style="width:201px;height:105px;"></div>
							</td>
						</tr>
						</table>
					</td>
				</tr>
			</table>			
			</td>
		</tr>
	</table>
	<% } if("1".equals(GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_COST_CENTER_PROPERTY))){%>							
	<table width="100%" border="0" cellspacing="0" cellpadding="5">
	<tr>
		<td class="label" nowrap="nowrap" width="110px;" style="color:#666666;">
			<%=GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_COST_CENTER_LABEL)%>&nbsp;
		</td>
		<td align="left" >
			<table cellspacing="0" cellpadding="0" border="0" class="filterGrid" >
			<tr>
				<td style="vertical-align: top;" align="left">
				<table cellpadding="0" cellspacing="0">
				<tr>
					<td>
						<input id="costCenterFilter" name="costCenterFilter" type="text" size="37" onfocus="onFilterFocus('costCenterFilter')" value="Filter" style="width:197px;color: graytext; border-bottom: 0px;" onclick="onFilterFocus('costCenterFilter','Filter');" onblur="onFilterUnfocus('costCenterFilter','Filter')"/>
					</td>
				</tr>
				<tr>
					<td class="gridborder" style="padding: 0px;">
						<div id="COST_CENTER_GRD" class="gridbox" style="width:200px;height:90px;"></div>
					</td>
				</tr>
				</table>			
				</td>		
				<td style="padding: 10px;vertical-align: middle;" >					
					<a href="#" onclick="javascript: selectItem(costCenterGrid,costCenterGridSelected);return false;" title="<bean:message key='common.add' />" >
						<img src="images/ico_rightarrow.gif"  border="0" />
					</a><br/>
					<a href="#" onclick="javascript: deselectItem(costCenterGridSelected,costCenterGrid);return false;" title="<bean:message key='common.remove' />" >
						<img src="images/ico_leftarrow.gif"  border="0" />
					</a>
				</td>					
				<td style="vertical-align: top;">
				<table cellpadding="0" cellspacing="0">
				<tr>
					<td class="gridborder">
						<div id="COST_CENTER_GRD_SELECTED"  class="gridbox" style="width:200px;height:105px;"></div>
					</td>
				</tr>
				</table>
				</td>
			</tr>
		</table>			
		</td>
	</tr>
	</table>			
	<% } %>
	</div>
	<div class="popupBody">
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td>
				<div class="navBtn" style="float:right;margin-left:5px;margin-top:5px;">
					<a href="#" style="width:50px;" class="active" onclick="javascript:submitForm();"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.save"/></a>
					<a href="#" style="width:60px;margin-left:5px;" class="active" onclick="javascript:window.top.hidePopWin(false);"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.cancel"/></a>
				</div>
			</td>
		</tr>
		</table>
	</div>
</html:form>
</div>
<br/><br/>
</logic:present>
</div>
<script>
var keys;
jQuery.ajaxSetup({ cache: false });

jQuery(document).ready(function() {
	jQuery.jCryption.getKeys("EncryptionServlet?generateKeypair=true",jsonCallback );
});		

function jsonCallback(receivedKeys) {
	keys = receivedKeys;
}

selectedCheckBox="images/checkboxchecked.gif";
deselectedCheckBox="images/checkboxunchecked.gif";

var chkedCheckBox = "images/checkboxchecked.gif";
var unChkedCheckBox = "images/checkboxunchecked.gif";

function onCompletetFn(update){
	new Effect.Fade(update,{duration:0.15});
	var uName = document.adminForm.userName.value;
	document.adminForm.myVar.value = uName;
	if(uName.indexOf('(')>-1){
		document.adminForm.userName.value = uName.substring(0,uName.indexOf('('));
		document.adminForm.firstName.value = uName.substring(uName.indexOf('(')+1,uName.indexOf(' '));
		document.adminForm.lastName.value = uName.substring(uName.indexOf(' ')+1,uName.indexOf(','));
		document.adminForm.email.value = uName.substring(uName.indexOf(', ')+2,uName.indexOf(')'));
	}
}

function doShowAdditionalOptions(role_id, imgId) {
  var spanImg = document.getElementById('img_'+imgId);
 
	var sourceElem = $("vendorSource");
	var empDiv = $("empDiv");
	var assignDiv = $("assignDiv");
	if(selectBoxSource){
		//selectBoxSource.setSelected(selectBoxSource.getIndexWithId('-1'));
		//selectBoxSource.setSelected(selectBoxSource.getIndexWithId('<bean:write property="userSourceId" name="adminForm" />'));
	}
	if (<%=GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_LDAP_ENABLED).equals(GlobalConstants.ENABLED)%>) {
		if (role_id == <%=String.valueOf(UserConstants.ROLE_VENDOR)%>) {
			enableLDAPCriticalFields();
		} else {
			enableOrDisableLdapCriticalFields(); // based on skip ldap option ldap critical fields are enabled or disabled
			//disableLDAPCriticalFields();
		}
	}
	if (role_id == <%=String.valueOf(UserConstants.ROLE_VENDOR)%>) {		  	
  		if(sourceElem){
		    Element.show('vendorSource');		    
	    }
  		if(assignDiv){
  			Element.hide('assignDiv');
	    }
	    if(empDiv){
		    Element.hide('empDiv');
	    }
	}else { 
  		if(sourceElem){
  		   Element.hide('vendorSource');
	    }
	    if(empDiv){
		   Element.show('empDiv');
	    }
	    if(assignDiv){
	    	Element.show('assignDiv');
	    }
	}

	var images = document.getElementsByTagName("span");
	//alert(images.length);
	for (i = 0; i < images.length; i++) {
		var theImage = images[i];
		if (theImage.id.indexOf("img") > -1) {
			//alert(theImage.id);
			if( theImage.id == 'img_'+imgId ){
				//alert('changing');
				theImage.innerHTML = '<img src="images/checkedradiobutton.gif" />';
				document.adminForm.selectedRoleIds.value = role_id;
			}else{
				theImage.innerHTML = '<img src="images/radiobutton.gif" />';
			}
		}
	}
  return false;
}

function skipLdapSetting(obj){
	toggleCheckBox(obj);
	enableOrDisableLdapCriticalFields();
}

function enableOrDisableLdapCriticalFields(){
	if($('userLdapSetting')){
		var src = $('userLdapSetting').src;
		if (src.indexOf(selectedCheckBox) != -1) {
			enableLDAPCriticalFields();
		} else {		
			disableLDAPCriticalFields();
		}	
	}
}

function toggleCheckBox(obj) {
	if(obj){
		var src = obj.src;
		if (src.indexOf(deselectedCheckBox) != -1) {
			obj.src = selectedCheckBox;
		} else {
			obj.src = deselectedCheckBox;
		}
	}
}


function populateIsUserLdapSetting() {
	elem = $('userLdapSetting');	
	var userLdapSetting='<bean:write name="adminForm" property="isUserLdapSetting" />';
	if ( userLdapSetting== '<%=UserConstants.IS_USER_LDAP_SETTING_DISABLED%>') {
		elem.src = selectedCheckBox;
		enableLDAPCriticalFields();
	} else {
		elem.src = deselectedCheckBox;
		disableLDAPCriticalFields();
	}
}

function saveIsUserLdapSetting() {
	var obj = $('userLdapSetting');
	var src = obj.src;
	if (src.indexOf(selectedCheckBox) != -1) {
		document.adminForm.isUserLdapSetting.value='<%=UserConstants.IS_USER_LDAP_SETTING_DISABLED%>';		
	} else {		
		document.adminForm.isUserLdapSetting.value='<%=UserConstants.IS_USER_LDAP_SETTING_ENABLED%>';		
	}
}
    
function submitForm() {
	onLoadJS();
}    
    
function onLoadJS(){
	//document.adminForm.userSourceId.value='';
	if(selectBoxSource){
		var val = selectBoxSource.getSelectedId();
		if (val != '-1') {
			document.adminForm.userSourceId.value=val;	
		}
	}
	<% 	if (GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_LDAP_ENABLED).equals(GlobalConstants.ENABLED)) { %>
		saveIsUserLdapSetting();
	<% } %>	
	<logic:empty name="adminForm" property="userId">	
	var parentId = selectBoxManagedUser.getSelectedId();
	document.adminForm.parentId.value=parentId;
	</logic:empty>
	if(selectBoxDepartment.getSelectedId()!='-1'){
		document.adminForm.departmentId.value=selectBoxDepartment.getSelectedId();
	}else{
		document.adminForm.departmentId.value='';
	}
	if(selectBoxSubDepartment.getSelectedId()!='-1'){
		document.adminForm.subDepartmentId.value=selectBoxSubDepartment.getSelectedId();
	}else{
		document.adminForm.subDepartmentId.value='';
	}
	if(selectBoxSubSubDepartment.getSelectedId()!='-1'){
		document.adminForm.subSubDepartmentId.value=selectBoxSubSubDepartment.getSelectedId();
	}else{
		document.adminForm.subSubDepartmentId.value='';
	}
	if(selectBoxSub3Department){
		if(selectBoxSub3Department.getSelectedId()!='-1'){
			document.adminForm.sub3DepartmentId.value=selectBoxSub3Department.getSelectedId();
		}else{
			document.adminForm.sub3DepartmentId.value='';
		}
	}
	if(selectBoxSub4Department){
		if(selectBoxSub4Department.getSelectedId()!='-1'){
			document.adminForm.sub4DepartmentId.value=selectBoxSub4Department.getSelectedId();
		}else{
			document.adminForm.sub4DepartmentId.value='';
		}
	}
	if(selectBoxLocations.getSelectedId()!='-1'){
		document.adminForm.locationId.value=selectBoxLocations.getSelectedId();
	}else{
		document.adminForm.locationId.value='';
	}
	if(selectBoxGrades.getSelectedId()!='-1'){
		document.adminForm.gradeId.value=selectBoxGrades.getSelectedId();
	}else{
		document.adminForm.gradeId.value='';
	}
	if(selectBoxBands.getSelectedId()!='-1'){
		document.adminForm.bandId.value=selectBoxBands.getSelectedId();
	}else{
		document.adminForm.bandId.value='';
	}
	<%if("1".equals(GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_BUSINESS_UNIT_PROPERTY))){%>
		var buIds = buGridSelected.getAllItemIds();
		document.adminForm.buId.value=buIds;
	<%}%>	
	
	<%if("1".equals(GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_COST_CENTER_PROPERTY))){%>
		var costCenterIds = costCenterGridSelected.getAllItemIds();
		document.adminForm.costCenterId.value=costCenterIds;
	<%}%>
		
	enableLDAPCriticalFields();
	
	if(document.adminForm.password != null && document.adminForm.password.value != ""
			&& document.adminForm.reTypedPassword != null && document.adminForm.reTypedPassword.value != "") {
		rnd = Math.round(Math.random())+2;
		document.adminForm.rnd.value=rnd;
		var newPass = Encrypt(document.adminForm.password.value,rnd);		
		var reTypePass = Encrypt(document.adminForm.reTypedPassword.value,rnd);
		jQuery.jCryption.encrypt(newPass, keys, function(encryptedPasswd) {
			document.adminForm.password.value=encryptedPasswd;
			jQuery.jCryption.encrypt(reTypePass, keys, function(encryptedNewPasswd) {
				document.adminForm.reTypedPassword.value=encryptedNewPasswd;
				document.adminForm.submit();
			});
		});
	} else {
		document.adminForm.submit();
	}
}

function enableLDAPCriticalFields(){
	document.adminForm.firstName.disabled = false;
	document.adminForm.lastName.disabled = false;
	document.adminForm.email.disabled = false;
}

function disableLDAPCriticalFields(){
	//resetvalues
	var uName = document.adminForm.myVar.value;
	if(uName.indexOf('(')>-1){
		document.adminForm.userName.value = uName.substring(0,uName.indexOf('('));
		document.adminForm.firstName.value = uName.substring(uName.indexOf('(')+1,uName.indexOf(' '));
		document.adminForm.lastName.value = uName.substring(uName.indexOf(' ')+1,uName.indexOf(','));
		document.adminForm.email.value = uName.substring(uName.indexOf(', ')+2,uName.indexOf(')'));
	}
	document.adminForm.firstName.disabled = true;
	document.adminForm.lastName.disabled = true;
	document.adminForm.email.disabled = true;
}

function loadSubdepartments() {
  var val = selectBoxDepartment.getSelectedId();
  var pars = "mode=getSubDepartmentJS&departmentId=" + val;
  var myAjax = ajaxCall("department.do",'get',pars,updateSubdepartments, reportError);
}
function updateSubdepartments(request){
 	xmlFile = request.responseXML;
	if(!isValidSession(xmlFile,'<%=TPApplicationProperties.getProperty("redirect_on_session_expired")%>'))return;
	if(!isErrorXml(xmlFile)){
		var op = request.responseText;
		var opts = eval(op);
		var m = [new SelectOption('-1', '<bean:message key='common.selectlist.default' />')];
		opts = m.concat(opts);
		selectBoxSubDepartment.reInitialize(opts, '');
		m = [new SelectOption('-1', '<bean:message key='common.selectlist.default' />')];
	    selectBoxSubSubDepartment.reInitialize(m, '');
	    selectBoxSub3Department.reInitialize(m, '');
	    selectBoxSub4Department.reInitialize(m, '');
	}	
 
}
function loadSubSubdepartments() {
  var val = selectBoxSubDepartment.getSelectedId();
  var pars = "mode=getSubDepartmentJS&departmentId=" + val;
  var myAjax = ajaxCall("department.do",'get',pars,updateSubSubdepartments, reportError);
}
function updateSubSubdepartments(request){
 	xmlFile = request.responseXML;
	if(!isValidSession(xmlFile,'<%=TPApplicationProperties.getProperty("redirect_on_session_expired")%>'))return;
	if(!isErrorXml(xmlFile)){
		var op = request.responseText;
		var opts = eval(op);
		var m = [new SelectOption('-1', '<bean:message key='common.selectlist.default' />')];
		opts = m.concat(opts);
		selectBoxSubSubDepartment.reInitialize(opts, '');
		m = [new SelectOption('-1', '<bean:message key='common.selectlist.default' />')];
	    selectBoxSub3Department.reInitialize(m, '');
	    selectBoxSub4Department.reInitialize(m, '');
	}	
 
}

function loadSub3departments() {
  var val = selectBoxSubSubDepartment.getSelectedId();
  var pars = "mode=getSubDepartmentJS&departmentId=" + val;
  var myAjax = ajaxCall("department.do",'get',pars,updateSub3departments, reportError);
}
function updateSub3departments(request){
 	xmlFile = request.responseXML;
	if(!isValidSession(xmlFile,'<%=TPApplicationProperties.getProperty("redirect_on_session_expired")%>'))return;
	if(!isErrorXml(xmlFile)){
		var op = request.responseText;
		var opts = eval(op);
		var m = [new SelectOption('-1', '<bean:message key='common.selectlist.default' />')];
		opts = m.concat(opts);
		selectBoxSub3Department.reInitialize(opts, '');
		m = [new SelectOption('-1', '<bean:message key='common.selectlist.default' />')];
	    selectBoxSub4Department.reInitialize(m, '');
	}	 
}

function loadSub4departments() {
  var val = selectBoxSub3Department.getSelectedId();
  var pars = "mode=getSubDepartmentJS&departmentId=" + val;
  var myAjax = ajaxCall("department.do",'get',pars,updateSub4departments, reportError);
}
function updateSub4departments(request){
 	xmlFile = request.responseXML;
	if(!isValidSession(xmlFile,'<%=TPApplicationProperties.getProperty("redirect_on_session_expired")%>'))return;
	if(!isErrorXml(xmlFile)){
		var op = request.responseText;
		var opts = eval(op);
		var m = [new SelectOption('-1', '<bean:message key='common.selectlist.default' />')];
		opts = m.concat(opts);
		selectBoxSub4Department.reInitialize(opts, '');
	}	 
}

function hideDeptBlocks(){
	var val = '<%= GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_MAX_DEPT_LEVEL)%>';	
	if(val=='<%=MastersConstants.DEPARTMENT_LEVEL_4%>'){
		$("dept_level_4").style.display = '';
		$("dept_level_5").style.display = 'none';
	}else if(val=='<%=MastersConstants.DEPARTMENT_LEVEL_5%>'){
		$("dept_level_4").style.display = '';
		$("dept_level_5").style.display = '';
	}else{
		$("dept_level_4").style.display = 'none';
		$("dept_level_5").style.display = 'none';
	}
}

function setPopupTitle(){
	var title = '<b>' + '<bean:write property="firstName" name="adminForm" />' + ' ' + '<bean:write property="lastName" name="adminForm" />' + '</b>';
	<logic:empty name="adminForm" property="userId">
	  //title = '<b>Add New User</b>';
	  title = '<b><bean:message key="add_user.label.add_new_user"/></b>';
	  
	</logic:empty>
	window.top.setPopTitle(title);
}



/******************* Business Unit Grid Related Code ***************/
var buGrid = null;
var buGridSelected = null;

function initBUGrid(){
	buGrid = new dhtmlXGridObject('BU_GRD'); 
	buGrid.imgURL = "images/"; 
	buGrid.setHeader('<bean:message key="position.requirements.primary_skills" />'); 
	buGrid.setInitWidths("175");
	buGrid.setColAlign("left");
	buGrid.setColTypes("ro");
	buGrid.setNoHeader(true);
	buGrid.setColSorting("bu_name_sort");
	buGrid.enableMultiselect('false');	
	buGrid.init();	
	buGrid.sortRows(0,'str',"asc");
	buGrid.attachEvent("onXLE",doOnLoadingEndBU);
	buGrid.attachEvent("onKeyPress",onBUGridKeyPressed);
	buGrid.attachEvent("onRowSelect",doOnBUGridRowSelectHandler);
	buGrid.attachEvent("onRowDblClicked",doOnBUGridRowDblClicked);
	buGrid.gridToGrid = function(rowId,sgrid,tgrid){
	    var z=new Array();
		for(var i=0;i<sgrid.hdr.rows[0].cells.length;i++)
		z[i]=sgrid.cells(rowId,i).getValue();
		return z;
	}
	loadBUGrid();	
}
function loadBUGrid(){
	buGrid.clearAll();
	buGrid.parse('<bean:write name="adminForm" property="businessUnitXML" scope="request" filter="false" />');	
}

function doOnLoadingEndBU() {
	buGrid.sortRows(0,'str',"asc");
	buGrid.setSortImgState(true,0,"ASC");
}


function bu_name_sort(a,b,order,aId,bId) {
	a0 = dataGridRequisitioner.getUserData(aId,"buName");
	b0 = dataGridRequisitioner.getUserData(bId,"buName");	
	return sort_data(a0,b0,order);
}

function initBUGridSelected(){
	buGridSelected = new dhtmlXGridObject('BU_GRD_SELECTED'); 
	buGridSelected.imgURL = "images/"; 
	buGridSelected.setHeader(""); 
	buGridSelected.setInitWidths("175");
	buGridSelected.setColAlign("left");
	buGridSelected.setColTypes("ro"); 	
	buGridSelected.enableMultiselect('true');
	buGridSelected.setNoHeader(true);
	buGridSelected.setColSorting("bu_name_sort");
	buGridSelected.init();
	buGridSelected.sortRows(0,'str',"asc");
	buGridSelected.setSortImgState(true,0,"ASC");	
	buGridSelected.gridToGrid = function(rowId,sgrid,tgrid){
	    var z=new Array();
		for(var i=0;i<sgrid.hdr.rows[0].cells.length;i++)
		z[i]=sgrid.cells(rowId,i).getValue();
		return z;
	}
	buGridSelected.attachEvent("onKeyPress",onBUGridSelectedGridKeyPressed);
	buGridSelected.attachEvent("onRowSelect",doOnBUGridSelectedRowSelectHandler);
	buGridSelected.attachEvent("onRowDblClicked",doOnBUGridSelectedRowDblClicked);
	loadBUGridSelected();
}
function loadBUGridSelected(){
	var pbu = '<bean:write name="adminForm" property="buId" />';			
	selectItems(pbu,buGrid,buGridSelected);	
}
function onBUGridKeyPressed(keyCode,ctrl,shift) {
	var text = (buGrid.cells(buGrid.getSelectedId(),0)).getValue();
	buGridSelected.clearSelection();
	onGridObjKeyPressed(buGrid,buGridSelected,5,keyCode,ctrl,shift);
	if(keyCode=='13'){
		removeIdFromBackUp(buGrid, text);
	}
}
function onBUGridSelectedGridKeyPressed(keyCode,ctrl,shift) {
	buGrid.clearSelection();
	onGridObjKeyPressed(buGridSelected,buGrid,5,keyCode,ctrl,shift);
	if(keyCode=='13'){
		resetFilterBackUp(buGrid);
	}
}
function doOnBUGridSelectedRowSelectHandler() {
	buGrid.clearSelection();
}
function doOnBUGridRowSelectHandler() {
	buGridSelected.clearSelection();
}
function doOnBUGridRowDblClicked() {
	var text = (buGrid.cells(buGrid.getSelectedId(),0)).getValue();
	selectItem(buGrid,buGridSelected);
	removeIdFromBackUp(buGrid, text);
}
function doOnBUGridSelectedRowDblClicked() {	
	selectItem(buGridSelected,buGrid);
	resetFilterBackUp(buGrid);
}

function onBUCriteriaChange(event){
	var iKeyCode = event.keyCode;
	switch(iKeyCode) {
		case Event.KEY_UP:
		case Event.KEY_DOWN: 
		break;
		case Event.KEY_PAGEUP:
		case Event.KEY_PAGEDOWN:
		break;
		case Event.KEY_RETURN:
		break;
		default:
		if((iKeyCode>=48 && iKeyCode<=90) || (iKeyCode>=96 && iKeyCode<=105) || iKeyCode==Event.KEY_BACKSPACE || iKeyCode==Event.KEY_DELETE){
			buGrid.filterBy(0, $('buFilter').value, false);
		}
	}
}


/******************* Cost Center Grid Related Code ***************/
var costCenterGrid = null;
var costCenterGridSelected = null;

function initCostCenterGrid(){
	costCenterGrid = new dhtmlXGridObject('COST_CENTER_GRD'); 
	costCenterGrid.imgURL = "images/"; 
	costCenterGrid.setHeader('<bean:message key="position.requirements.primary_skills" />'); 
	costCenterGrid.setInitWidths("175");
	costCenterGrid.setColAlign("left");
	costCenterGrid.setColTypes("ro");
	costCenterGrid.setNoHeader(true);
	costCenterGrid.setColSorting("cost_center_name_sort");
	costCenterGrid.enableMultiselect('false');	
	costCenterGrid.init();	
	costCenterGrid.sortRows(0,'str',"asc");
	costCenterGrid.attachEvent("onXLE",doOnLoadingEndCostCenter);
	costCenterGrid.attachEvent("onKeyPress",onCostCenterGridKeyPressed);
	costCenterGrid.attachEvent("onRowSelect",doOnCostCenterGridRowSelectHandler);
	costCenterGrid.attachEvent("onRowDblClicked",doOnCostCenterGridRowDblClicked);
	costCenterGrid.gridToGrid = function(rowId,sgrid,tgrid){
	    var z=new Array();
		for(var i=0;i<sgrid.hdr.rows[0].cells.length;i++)
		z[i]=sgrid.cells(rowId,i).getValue();
		return z;
	}
	loadCostCenterGrid();	
}
function loadCostCenterGrid(){
	costCenterGrid.clearAll();
	costCenterGrid.parse('<bean:write name="adminForm" property="costCenterXML" scope="request" filter="false" />');	
}

function doOnLoadingEndCostCenter() {
	costCenterGrid.sortRows(0,'str',"asc");
	costCenterGrid.setSortImgState(true,0,"ASC");
}

function cost_center_name_sort(a,b,order,aId,bId) {
	a0 = dataGridRequisitioner.getUserData(aId,"costCenterName");
	b0 = dataGridRequisitioner.getUserData(bId,"costCenterName");	
	return sort_data(a0,b0,order);
}

function initCostCenterGridSelected(){	
	costCenterGridSelected = new dhtmlXGridObject('COST_CENTER_GRD_SELECTED'); 
	costCenterGridSelected.imgURL = "images/"; 
	costCenterGridSelected.setHeader(""); 
	costCenterGridSelected.setInitWidths("175");
	costCenterGridSelected.setColAlign("left");
	costCenterGridSelected.setColTypes("ro"); 	
	costCenterGridSelected.enableMultiselect('true');
	costCenterGridSelected.setNoHeader(true);
	costCenterGridSelected.setColSorting("cost_center_name_sort");
	costCenterGridSelected.init();
	costCenterGridSelected.sortRows(0,'str',"asc");
	costCenterGridSelected.setSortImgState(true,0,"ASC");	
	costCenterGridSelected.gridToGrid = function(rowId,sgrid,tgrid){
	    var z=new Array();
		for(var i=0;i<sgrid.hdr.rows[0].cells.length;i++)
		z[i]=sgrid.cells(rowId,i).getValue();
		return z;
	}
	costCenterGridSelected.attachEvent("onKeyPress",onCostCenterGridSelectedGridKeyPressed);
	costCenterGridSelected.attachEvent("onRowSelect",doOnCostCenterGridSelectedRowSelectHandler);
	costCenterGridSelected.attachEvent("onRowDblClicked",doOnCostCenterGridSelectedRowDblClicked);
	loadCostCenterGridSelected();
}
function loadCostCenterGridSelected(){
	var pCostCenter = '<bean:write name="adminForm" property="costCenterId" />';	
	selectItems(pCostCenter,costCenterGrid,costCenterGridSelected);
}
function onCostCenterGridKeyPressed(keyCode,ctrl,shift) {
	var text = (costCenterGrid.cells(costCenterGrid.getSelectedId(),0)).getValue();
	costCenterGridSelected.clearSelection();
	onGridObjKeyPressed(costCenterGrid,costCenterGridSelected,5,keyCode,ctrl,shift);
	if(keyCode=='13'){
		removeIdFromBackUp(costCenterGrid, text);
	}
}
function onCostCenterGridSelectedGridKeyPressed(keyCode,ctrl,shift) {
	costCenterGrid.clearSelection();
	onGridObjKeyPressed(costCenterGridSelected,costCenterGrid,5,keyCode,ctrl,shift);
	if(keyCode=='13'){
		resetFilterBackUp(costCenterGrid);
	}
}
function doOnCostCenterGridSelectedRowSelectHandler() {
	costCenterGrid.clearSelection();
}
function doOnCostCenterGridRowSelectHandler() {
	costCenterGridSelected.clearSelection();
}
function doOnCostCenterGridRowDblClicked() {
	var text = (costCenterGrid.cells(costCenterGrid.getSelectedId(),0)).getValue();
	selectItem(costCenterGrid,costCenterGridSelected);
	removeIdFromBackUp(costCenterGrid, text);
}
function doOnCostCenterGridSelectedRowDblClicked() {	
	selectItem(costCenterGridSelected,costCenterGrid);
	resetFilterBackUp(costCenterGrid);
}

function onCostCenterCriteriaChange(event){
	var iKeyCode = event.keyCode;
	switch(iKeyCode) {
		case Event.KEY_UP:
		case Event.KEY_DOWN: 
		break;
		case Event.KEY_PAGEUP:
		case Event.KEY_PAGEDOWN:
		break;
		case Event.KEY_RETURN:
		break;
		default:
		if((iKeyCode>=48 && iKeyCode<=90) || (iKeyCode>=96 && iKeyCode<=105) || iKeyCode==Event.KEY_BACKSPACE || iKeyCode==Event.KEY_DELETE){
			costCenterGrid.filterBy(0, $('costCenterFilter').value, false);		
		}
	}
}


//Common Function
function sort_data(a,b,order) {
	if(order=="asc")
		return a.toLowerCase()>b.toLowerCase()?1:-1;
	else
		return a.toLowerCase()<b.toLowerCase()?1:-1;
}


function doOnLoad() {
	<logic:present name="companyName" scope="request">
  	setPopupTitle();
  	if(document.adminForm.selectedRoleIds.value == '<%=UserConstants.ROLE_VENDOR%>'){
  		Element.show('vendorSource');
  		Element.hide('empDiv');
  		Element.hide('assignDiv');
  		<% 	if (GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_LDAP_ENABLED).equals(GlobalConstants.ENABLED)) { %>
  			enableLDAPCriticalFields();
  		<% } %>
  	}
  	<% 	if (GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_LDAP_ENABLED).equals(GlobalConstants.ENABLED)) { %>
	  	populateIsUserLdapSetting();
	<% } %> 	

	<%if("1".equals(GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_BUSINESS_UNIT_PROPERTY))){%>
		initBUGrid();	
		initBUGridSelected();
		Event.observe($('buFilter'), "keyup", onBUCriteriaChange.bindAsEventListener(this));
	<% } %>

	
	<%if("1".equals(GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_COST_CENTER_PROPERTY))){%>
		initCostCenterGrid();
		initCostCenterGridSelected();	
		Event.observe($('costCenterFilter'), "keyup", onCostCenterCriteriaChange.bindAsEventListener(this));
	<% } %>
	
	</logic:present>
		
	hideDeptBlocks();
}

window.onload = doOnLoad;



</script>
</logic:notPresent>													