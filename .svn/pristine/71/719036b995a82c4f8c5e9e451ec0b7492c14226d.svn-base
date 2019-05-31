<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ page import="org.apache.struts.Globals, com.talentPool.common.NavigationConstants"%>

<%@page import="java.util.ArrayList"%>
<%@ page import="com.talentPool.common.utils.CommonUtils"%>
<%@ page import="com.talentPool.common.utils.Utils"%>
<%@ page import="java.security.KeyPair"%>
<%@ page import="com.talentPool.encryption.JCryptionUtil"%>
<script src="encryption/js/jquery-2.0.3.min.js" type="text/javascript"></script>
<script src="encryption/js/jquery-ui-1.8.2.custom.min.js" type="text/javascript"></script>
<script src="js/scripta/lib/prototype.js" type="text/javascript"></script>
<script src="js/scripta/src/effects.js" type="text/javascript"></script>
<script src="js/encrypt.js" type="text/javascript"></script>
<script src="encryption/js/jquery.jcryption-1.1.js" type="text/javascript"></script>
<script src="js/selectoption.js" type="text/javascript"></script>
<script src="js/selectbox/selectbox.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css" href="themes/default/selectbox.css">

<%
ArrayList<String> securityQuestionIds = CommonUtils.getSecurityQuestionIds();
ArrayList<String> securityQuestionNames = CommonUtils.getSecurityQuestionNames();

%>
<script type="text/javascript">
var selectBoxSecurityQuestion=null;
var securityQuestionOptions = <%=CommonUtils.getListJavaScriptArray(securityQuestionIds,securityQuestionNames)%>;
</script>

<logic:present name="update" scope="request">
	<script>
		window.top.hidePopWin(true);
	</script>
</logic:present>                
<logic:notPresent name="update" scope="request">
<div class="contentDivPop" style="width: 450px;">

<logic:present name="passwordChanged" scope="request">
			<table id="m_errortable" > 
		    <tr>
	        <td class="message" style="padding: 20px;">Password changed successfully</td>               
		    </tr>
			</table><br/><br/>
			<div class="navBtn" style="margin-top:5px;margin-left: 0px;">
			<a href="login.do?loginmode=login" style="width:60px; margin-left: 5px;" class="active" >
								<span class="rightC"></span><span class="leftC"></span>
								<bean:message key="common.ok"/>
			</a>
			</div>
	
</logic:present>

<logic:notPresent name="passwordChanged" scope="request">
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
	<logic:equal property="agePasswordChange" name="userForm" value="1">
		      	<table style="border: 0; border-spacing: 0; padding: 0;" class="positionDetails"> 
					<tr> 
						<td style='padding-left:5px; padding-top:5px;line-height:15px;vertical-align:top;color:#666666;font-weight: bold;font-size: 14px;'>
							Change Password
						</td>
					</tr>
				</table><br/>							
			 </logic:equal>
	<div class="outerDiv">
		<html:form action="/user">
		<html:hidden property="mode" value="savePassword"/>
		<html:hidden property="t"/>
		<html:hidden property="st"/>
		<html:hidden property="userId"/>
		<html:hidden property="cancelled"/>
		<html:hidden property="forgotPassword" name="userForm"/>
		<html:hidden property="rnd" name="userForm"/>
		<html:hidden property="forcePasswordChange" name="userForm"/>
		<html:hidden property="isPasswordExpired" name="userForm"/>
		<html:hidden property="agePasswordChange" name="userForm"/>
		<div class="popupBody">
			<logic:equal property="isPasswordExpired" name="userForm" value="true">
		    		<table id="m_errortable" > 
		    			<tr>
	        				<td class="message" style="padding: 20px;"><bean:message key="change_password.password_expired_message"/></td>               
		    			</tr>
					</table><br/><br/>
				</logic:equal>
			<table class="tblPop">
				<tr>
					<td>
						<table width="100%" border="0" cellspacing="0" cellpadding="0" class="posinput"> 
						<%if (request.getSession().getAttribute("userId") == null) {%>
						  <logic:notEqual property="forgotPassword" name="userForm" value="1">
						    <tr>
						      <td class="label" nowrap="nowrap"><bean:message key="change_password.label.old_password"/></td>
						      <td><input type="password" autocomplete="off" name="oldPassword" size="25" maxlength="50"/></td>
						    </tr>
						    </logic:notEqual> 
						    <%}else { %>
						    	<logic:notEqual property="forgotPassword" name="userForm" value="1">
						    	 <logic:equal name="userForm" property="userId" value='<%=(String)request.getSession(false).getAttribute("userId")%>'>
						    		<tr>
						      			<td class="label" nowrap="nowrap"><bean:message key="change_password.label.old_password"/></td>
						      			<td><input type="password" autocomplete="off" name="oldPassword" size="25" maxlength="50"/></td>
						   			</tr>
						  		</logic:equal> 
						  		</logic:notEqual> 
						    <%} %>
						  <tr>
						    <td class="label" nowrap="nowrap"><bean:message key="change_password.label.new_password"/></td>
						    <td><input type="password" autocomplete="off" name="newPassword" size="25" maxlength="50"/>
						    <%if (!Utils.isBlankOrNull((String)request.getSession().getAttribute("userId"))){ %>
						    <logic:equal name="userForm" property="userId" value='<%=(String)request.getSession().getAttribute("userId") %>'>
						    <img style="vertical-align: text-bottom" src="images/password_hint.JPG" title='<bean:message key="change_password.label.hint_new_password"/>'>
						    </logic:equal> 	
						    <% }else {%>
						    <img style="vertical-align: text-bottom" src="images/password_hint.JPG" title='<bean:message key="change_password.label.hint_new_password"/>'>
						    <%} %>	
						    </td>
						  </tr>
						  <tr>
						    <td class="label" nowrap="nowrap"><bean:message key="change_password.label.confirm_new_password"/></td>
						    <td><input type="password"  autocomplete="off"name="confirmNewPassword" size="25" maxlength="50"/></td>
						  </tr>	
						  <logic:equal name="userForm" property="forcePasswordChange" value="1">
	  						<tr>
      							<td class="label" nowrap="nowrap">Security Question</td>
      							<td>
									<input type="hidden" name="securityQuestionId" /> 
									<script	type="text/javascript">
										selectBoxSecurityQuestion = new SelectBox(securityQuestionOptions,'<bean:write property="securityQuestionId" name="userForm"/>','images/btn_dropdown.gif',{namesonly:false, width:'250px', size:12});
		    							document.write(selectBoxSecurityQuestion.getHtml());
										selectBoxSecurityQuestion.init();
									</script>
	 							</td>
      						</tr>
      						<tr>
								<td class="label">Answer</td>
								<td><html:textarea name="userForm" property="answer" cols="30" rows="5" /></td>
	  						</tr>
	  					</logic:equal> 			  
						</table>
					</td>
				</tr>
				<tr>
					<td>
						<table width="100%" border="0" cellspacing="0" cellpadding="0" class="posinput">
							<tr>
								<td>
									<div class="navBtn" style="float:right;"><a href="#" style="width:50px;" class="active" onclick="submitForm();"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.save"/></a>
									
									 <logic:notEqual name="userForm" property="forcePasswordChange" value="1">
		    							<logic:notEqual property="forgotPassword" name="userForm" value="1">
		    								<logic:notEqual property="isPasswordExpired" name="userForm" value="true">
		    									<logic:notEqual property="agePasswordChange" name="userForm" value="1">
		      										<a href="#" style="width:60px;margin-left:5px;" class="active" onclick="javascript:window.top.hidePopWin(false);">
		      										<span class="rightC"></span><span class="leftC"></span>
		      										<bean:message key="common.cancel"/></a>
		      									</logic:notEqual>
			  								</logic:notEqual>
			 							</logic:notEqual>
									</logic:notEqual>
		    						<logic:equal property="forgotPassword" name="userForm" value="1">
		      							<a href="login.do?loginmode=login" style="width:60px;margin-left:5px;" class="active">
			  							<span class="rightC"></span><span class="leftC"></span>
			  							<bean:message key="common.cancel"/></a>
			 						</logic:equal>
			 						<logic:equal property="forcePasswordChange" name="userForm" value="1">
		      							<a href="login.do?loginmode=login" style="width:60px;margin-left:5px;" class="active">
			  							<span class="rightC"></span><span class="leftC"></span>
			  							<bean:message key="common.cancel"/></a>
			 						</logic:equal>
			 						<logic:equal property="isPasswordExpired" name="userForm" value="true">
		      							<a href="login.do?loginmode=login" style="width:60px;margin-left:5px;" class="active">
			  							<span class="rightC"></span><span class="leftC"></span>
			  							<bean:message key="common.cancel"/></a>
			 						</logic:equal>
			 						<logic:equal property="agePasswordChange" name="userForm" value="1">
		      							<a href="login.do?loginmode=postLogin" style="width:60px;margin-left:5px;" class="active">
			  							<span class="rightC"></span><span class="leftC"></span>
			  							<bean:message key="common.cancel"/></a>
			 						</logic:equal>
									</div>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</div>
	</html:form>  
	</div>
	</logic:notPresent>  
	</div>
	<script type="text/javascript">
	var keys;
	jQuery.ajaxSetup({ cache: false });
	
	jQuery(document).ready(function() {
		jQuery.jCryption.getKeys("EncryptionServlet?generateKeypair=true",jsonCallback );
	});		

	function jsonCallback(receivedKeys) {
		keys = receivedKeys;
	}
	
	function submitForm(){
		if(document.userForm.oldPassword != null && document.userForm.oldPassword.value==""){
			alert('<bean:message key="change_password.error.old_password.nullOrBlank"/>');
			document.userForm.oldPassword.focus();
			return false;
		}else if (document.userForm.newPassword.value==""){
			alert('<bean:message key="change_password.error.new_password.nullOrBlank"/>');
			document.userForm.newPassword.focus();
			return false;
		}else if (document.userForm.confirmNewPassword.value==""){
			alert('<bean:message key="change_password.error.new_password.nullOrBlank"/>');
			document.userForm.confirmNewPassword.focus();
			return false;
		}else if (document.userForm.newPassword.value!=document.userForm.confirmNewPassword.value){
			alert('<bean:message key="change_password.error.new_password.mismatch"/>');
			document.userForm.confirmNewPassword.focus();
			return false;
		}
		<logic:equal name="userForm" property="forcePasswordChange" value="1">
		document.userForm.securityQuestionId.value=selectBoxSecurityQuestion.getSelectedId();
		</logic:equal> 		
		rnd = Math.round(Math.random())+2;
		document.userForm.rnd.value=rnd;
		var newPass = Encrypt(document.userForm.newPassword.value,rnd);	
		if(document.userForm.oldPassword != null) {
			var oldPass = Encrypt(document.userForm.oldPassword.value,rnd);
		
			jQuery.jCryption.encrypt(oldPass, keys, function(encryptedOldPasswd) {
				document.userForm.oldPassword.value=encryptedOldPasswd;
				jQuery.jCryption.encrypt(newPass, keys, function(encryptedNewPasswd) {
					document.userForm.newPassword.value=encryptedNewPasswd;
					document.userForm.confirmNewPassword.value=encryptedNewPasswd;
					document.userForm.submit();
				});
			});
		} else {
			jQuery.jCryption.encrypt(newPass, keys, function(encryptedNewPasswd) {
				document.userForm.newPassword.value=encryptedNewPasswd;
				document.userForm.confirmNewPassword.value=encryptedNewPasswd;
				document.userForm.submit();
			});
		}
	}
	
	<logic:notEqual property="forcePasswordChange" name="userForm" value="1">
	<logic:notEqual property="forgotPassword" name="userForm" value="1">
	<logic:notEqual property="agePasswordChange" name="userForm" value="1">
	<logic:equal property="isPasswordExpired" name="userForm" value="true">
		function setPopupTitle(){
			var title = '<b>' + 'Change Password' + '</b>';
			
			window.top.setPopTitle(title);
		}
		
		window.onload = setPopupTitle;
		</logic:equal>
		</logic:notEqual>
		</logic:notEqual>
		</logic:notEqual>
	</script>
</logic:notPresent>                

