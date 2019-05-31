<%@page import="com.talentPool.user.UserConstants"%>
<%@page import="com.talentPool.common.utils.Utils"%>
<%@page import="com.talentPool.common.properties.TPApplicationProperties"%>
<%@page import="com.talentPool.recaptcha.properties.ReCaptchaProperties"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ page import="org.apache.struts.Globals"%>
<script src="js/utils/updater.js" type="text/javascript"></script>
<script src="js/scripta/lib/prototype.js" type="text/javascript"></script>
<script src="js/scripta/src/effects.js" type="text/javascript"></script>
<script>
var RecaptchaOptions = {
   theme : 'white'
};
</script>
<div class="contentDiv">
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
	<%
		String saved = (String)request.getAttribute("saved");
		if(saved !=null){
	%>
			<table  id="m_errortable" > 
				<tr>
			    <td class="header">
			        <b><bean:message key="forgot_password.label.success"/></b>
			    </td>               
				</tr>
			</table>
			<br/>
			<div class="navBtn" style="margin-top:5px;margin-left: 0px;">
			<a href="login.do?loginmode=login" style="width:60px; margin-left: 5px;" class="active" >
								<span class="rightC"></span><span class="leftC"></span>
								<bean:message key="common.ok"/>
			</a>
			</div>
	<%
		}
	%>
	</div>
<%
if(saved ==null){
%>

<html:form action="/user" onsubmit="return submitForm();">
<html:hidden property="mode" value="forgotPassword"/>
<html:hidden property="securityQuestion" name="userForm"/>
<html:hidden property="userName" name="userForm"/>
<html:hidden property="userId" name="userForm"/>
<html:hidden property="email" name="userForm"/>
<html:hidden property="resetOption" name="userForm"/>
<html:hidden property="forgotPassword" name="userForm" value="1"/>
<input type="hidden" name="submitted" value="1"/>

<b><bean:message key="forgot_password.label.title"/></b>
<br/><br/>

<table class="tabinput">
	<tr>
		<td class="label"><bean:message key="forgot_password.label.desc"/></td>
	</tr>
	<tr>
		<td class="label" >&nbsp;</td>
	</tr>
	<tr>
		<td height="20">
			<img src="images/radiobutton.gif" name="rdo" id='img_question' 
				onclick="javascript:onRadioChange(this);">&nbsp;
			<bean:message key="forgot_password.option.question"/>&nbsp;&nbsp;
		</td>	
	</tr>
	<tr id="divQuestion" style="padding: 10px 0px 10px 15px; display: none">
		<td>
			<table style="padding: 10px 0px 10px 10px">
	 			<tr>
	  				<td class="label">
						<bean:message key="forgot_password.label.question"/> : 
						<bean:write property="securityQuestion" name="userForm"/> 
						<br/><br/>
						<bean:message key="forgot_password.label.answer"/>&nbsp;&nbsp; :
						<!-- html:text property="answer" styleId="answer" name="userForm" size="50" maxlength="100" autocomplete="false"/--> 
						<input type="text" id="answer" name="answer" size="50" maxlength="100" autocomplete="off">
	  				</td>		
	 			</tr>		
			</table>		
		</td>
	</tr>
	<tr>
		<td height="20">
			<img src="images/radiobutton.gif" name="rdo" id='img_link'
				onclick="javascript:onRadioChange(this);">&nbsp;
			<bean:message key="forgot_password.option.link"/>&nbsp;&nbsp;
		</td>
	</tr>
	<tr id="divLink" style="padding: 10px 0px 10px 15px; display: none">
		<td>
			<table style="padding: 10px 0px 10px 10px">
	 			<tr>
	  				<td class="label">
						<bean:message key="forgot_password.label.link"/>
	  				</td>		
	 			</tr>		
			</table>	
		</td>
	</tr>
     <% 
			if("1".equals(TPApplicationProperties.getProperty("forgotPassword_captcha_enabled"))){
		%>
	       	<tr>
	       		<td colspan="2">
	       		<table>
					<tr>
						<td>
							<script type="text/javascript"
	 									src="http://api.recaptcha.net/challenge?k=<%=ReCaptchaProperties.getProperty("public.key")%>">
							</script>						
							<noscript>
	 									<iframe src="http://api.recaptcha.net/noscript?k=<%=ReCaptchaProperties.getProperty("public.key")%>"
	     									height="100" width="200"></iframe>
	     								<br>
	 									<textarea name="recaptcha_challenge_field" rows="1" cols="40"></textarea>
	 									<input type="hidden" name="recaptcha_response_field" value="manual_challenge"> 
							</noscript>
						</td>
					</tr> 			   		
				</table>
	       		</td>
	       	</tr>
	<% } %>
</table>
<table>
	<tr>
	<td style="width: 100px;">
		<div class="navBtn" style="margin-top:5px;margin-left: 50px; ">
		<a href="#" style="width:60px;" class="active" onclick="javascript:submitForm();return false;" id="submit">
			<span class="rightC"></span><span class="leftC"></span>
			<bean:message key="common.submit"/>
		</a>
		</div>
	</td>
	<td>
		<div class="navBtn" style="margin-top:5px; ">
		<a href="login.do?loginmode=login" style="width:60px; " class="active" >
			<span class="rightC"></span><span class="leftC"></span>
			<bean:message key="common.cancel"/>
		</a>
		</div>
	</td>
	</tr>
</table>
</html:form>

<script type="text/javascript">
function submitForm(){
	if($('img_question') && $('img_question').src.indexOf(chkedRadioButton) != -1) {
		if($('answer') && $('answer').value == '') {
			alert('Please enter answer to the security question');
			$('answer').focus();
			return;
		}
		document.userForm.resetOption.value = <%=UserConstants.RESET_OPTION_QUESTION%>;
		document.userForm.answer.value = $('answer').value;
	} else if($('img_link').src.indexOf(chkedRadioButton) != -1) {
		document.userForm.resetOption.value = <%=UserConstants.RESET_OPTION_LINK%>;
	} else {
		alert('Choose one of the option');
		return;
	}
	
	showUpdater('submit',{setHeight: false, setWidth: false, offsetLeft: -50});
	document.userForm.submit();
}

var unchkedRadioButton='images/radiobutton.gif';
var chkedRadioButton='images/checkedradiobutton.gif';

function onRadioChange(obj){
	var source = obj.src;
	var objectId = obj.id;	
	if (source.indexOf(unchkedRadioButton) != -1) {
		if(objectId == 'img_question'){
			<logic:empty property="securityQuestion" name="userForm">
				alert('<bean:message key="forgot_password.error.no_security_question"/>');
				return;
			</logic:empty>
			document.getElementById('img_question').src =chkedRadioButton;
			document.getElementById('img_link').src =unchkedRadioButton;
			$('divQuestion').show();
			$('divLink').hide();			
		} else {
			document.getElementById('img_question').src = unchkedRadioButton;
			document.getElementById('img_link').src =chkedRadioButton;
			$('divQuestion').hide();
			$('divLink').show();						
		}
	}
}

</script>
<%} %>
</div>										
