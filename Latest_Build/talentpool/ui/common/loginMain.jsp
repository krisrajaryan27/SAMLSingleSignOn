<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ page import="org.apache.struts.Globals,
				com.talentPool.user.LoginConstants"%>
<%@page import="com.talentPool.common.utils.Utils"%>
<%@ page import="java.security.KeyPair"%>
<%@ page import="com.talentPool.encryption.JCryptionUtil"%>
<%@page import="com.talentPool.recaptcha.properties.ReCaptchaProperties"%>
<%@page import="com.talentPool.common.properties.TPApplicationProperties"%>
<script src="encryption/js/jquery-2.0.3.min.js" type="text/javascript"></script>
<script src="encryption/js/jquery-ui-1.8.2.custom.min.js" type="text/javascript"></script>
<script src="js/scripta/lib/prototype.js" type="text/javascript"></script>
<script src="js/cookies.js" type="text/javascript"></script>
<script src="js/encrypt.js" type="text/javascript"></script>
<script src="encryption/js/jquery.jcryption-1.1.js" type="text/javascript"></script>
<script>
var RecaptchaOptions = {
   theme : 'white'
};
</script>
<%
String singlesignonerror = (String)request.getAttribute("singlesignonerror");
%>
<html:form action="/login" focus="userName"> 
	<input type="hidden" name="loginmode" value="login"/>
	<html:hidden property="rnd"/>
	<html:hidden property="ignoreSignedOn" value=""/>
	<html:hidden property="singlesignonerror"/>
	<html:hidden property="forgotPassword" name="loginForm"/>
	<table cellspacing="0" cellpadding="0" width="100%">
		<tr>
			<td width="50"></td>
			<td height="140">
				<br><br>
				<img src="images/logo.jpg" />
			</td>
			<td width="37"></td>
			<td></td>
		</tr>
		<tr>
			<td width="90" style="background-color:#F2F2F2;height:235px;"></td>
			<td style="background-color:#F2F2F2;" valign="bottom"><img src="images/image_people.jpg" /></td>
			<td style="background-color:#F2F2F2;"></td>
			<td style="background-color:#F2F2F2;" >	
				<table cellspacing="0" cellpadding="0" width="100%">
					<tr>						
						<td>
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
									</table>
									<br>
							<%
								}
								if((request.getAttribute("isExpired")!=null && request.getAttribute("isExpired").equals("1")) || 
									((request.getParameter("isExpired") !=null) && request.getParameter("isExpired").equals("1"))){
							%>
									<table id="m_errortable" > 
									<tr>
								    <td class='header'>
								    	<b><bean:message key="login.text.session_expired" /></b>
								    </td>               
									</tr>
								    <tr>
								       <td class="message"><ul><li>
								       <bean:message key="login.text.session_expired_relogin" />
								       </li></ul></td>               
								    </tr>
									</table>
									<br>
							<%	
								}
								if(request.getAttribute("disableForgotPassword")!=null && request.getAttribute("disableForgotPassword").equals("1")) {
							%>  
								<script>alert('<bean:message key="common.error.cannot_use_forgotPassword"/>');</script>
							<%	
								}
							%>  
							<table cellspacing="0" cellpadding="0" width="350">
								<tr>
									<td>
										<div class="navBtnTab2" >
											<a class="active" href="#" style="display:block;width:120px;"><span class="rightC"></span><span class="leftC"></span>
												<bean:message key="login.label.title" />
											</a>
										</div>
									</td>
								</tr>
								<tr>
									<td>
										<div class="outerDiv" style="background-color:#FFFFFF;">
											<table><tr><td>
												<div class="contentDiv" style="margin:23px;margin-left:60px;">
													<table cellspacing="0" cellpadding="5" class="tabinput">
													<tr>
							            <td <html:errormap property="userNameAndPassword" errorStyleClass="labelError" styleClass="label"/>>
							                <bean:message key="login.label.username"/>
							            </td>
							            <td>
							            	<html:text property="userName" size="25" maxlength="50" styleClass="Yellow"></html:text>
							            </td>
								        </tr>
								        <tr>
							            <td <html:errormap property="userNameAndPassword" errorStyleClass="labelError" styleClass="label"/>>
							                <bean:message key="login.label.password"/>
							            </td>
							            <td>
							            	<html:password property="userPassword" size="25" maxlength="50" styleClass="Yellow" value="" ></html:password>
							            </td>
								        </tr>
								        <tr>
								        	<td></td>
							            <td class="label">
							                <input type="checkbox" name="automaticLogon" value="on" class="checkbox">
							                <bean:message key="login.label.remember_me"/>
							            </td>
								        </tr>
								        <% 
											if("1".equals(TPApplicationProperties.getProperty("captcha_enabled")) 
													&& request.getAttribute(Globals.ERROR_KEY)!=null){
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
							   									<input type="hidden" name="recaptcha_response_field" value="manual_challenge" style="height: 17px;"> 
															</noscript>
														</td>
													</tr> 			   		
												</table>
								        		</td>
								        	</tr>
								        <% } %>
												<tr>
													<td></td>
													<td>
														<div class="navBtn"><a href="#" style="width:60px;" class="active" onclick="javascript:submitForm();"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.submit"/></a></div>
													</td>
												</tr>
												<tr>
													<td></td>
													<td align="left">
														<a href="javascript:forgotPassword();" class="green">Forgot password?</a>
													</td>
												</tr>
													</table>
												</div></td></tr>
											</table>
										</div>
										<div align="right" style="padding-right:2px;">
										<a href="#" class="Grey" onclick="showAbout();"><%=LoginConstants.getVERSION()%></a>
										</div>
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</td>
			<td></td>
		</tr>
		<tr>
			<td width="50"></td>
			<td></td>
			<td colspan="2" align="left"><img src="images/image_circles.gif" /></td>			
		</tr>
	</table>		
</html:form>
<script>
	var keys;
	var text;
	jQuery.ajaxSetup({ cache: false });
	
	function jsonCallback(receivedKeys) {
		keys = receivedKeys;
		jQuery.jCryption.encrypt(text, keys, function(encryptedPasswd) {
			document.loginForm.userPassword.value=encryptedPasswd;
			document.loginForm.submit();
		});
	}
	
	function registerEvents(){
		//Below code added to disable the Autocomplete of username and password in browser.
		for (i=0; i<document.forms.length; i++) {
		    document.forms[i].setAttribute("AutoComplete","off");
		}
		
		try{
			Event.observe(document, "keydown", onKeyDown.bindAsEventListener(this));
		}catch (error) {
			//alert(error);
		}
		setRememberMe();
		<logic:notEmpty name="loginForm" property="singlesignonerror" >
			var confirmed = confirm('<bean:message key="login.errors.single_signon_error"/>');
			if(confirmed){
				document.loginForm.ignoreSignedOn.value="1";
				submitForm();
			}
		</logic:notEmpty>
	}
	function onKeyDown(event){
		if(event.keyCode==Event.KEY_RETURN){
			submitForm();
		}
	}
	function submitForm(){
		if(document.loginForm.userName.value==""){
			alert('<bean:message key="login.errors.enter_username"/>');
			document.loginForm.userName.focus();
			return false;
		}else if (document.loginForm.userPassword.value==""){
			alert('<bean:message key="login.errors.enter_password"/>');
			document.loginForm.userPassword.focus();
			return false;
		}
		rnd = Math.round(Math.random())+2;
		eraseCookie("tprem");
		eraseCookie("tprem2");
		eraseCookie("rnd");
		if(document.loginForm.automaticLogon.checked){
			createCookie("tprem",document.loginForm.userName.value,360);
			createCookie("tprem2",Encrypt(document.loginForm.userPassword.value,rnd),360);
			createCookie("rnd",rnd,360);
		}

		document.loginForm.rnd.value=rnd;
		text = Encrypt(document.loginForm.userPassword.value,rnd);
		jQuery.jCryption.getKeys("EncryptionServlet?generateKeypair=true",jsonCallback );
	}
	
	function setRememberMe(){
		var userName = readCookie("tprem");
		var userPassword = readCookie("tprem2");
		rnd = readCookie("rnd");
		if(userPassword != null){
			userPassword = unEncrypt(userPassword, rnd);
		}
		if(userName != null){
			document.loginForm.userName.value = userName;
			document.loginForm.userPassword.value = userPassword;
			document.loginForm.automaticLogon.checked=true;
			document.loginForm.userPassword.focus();
		}else{
			document.loginForm.userName.focus();
		}
	}
	function showAbout(){
		window.open("application.do?mode=viewAbout",'About','width=500,height=300,scrollbars=yes,resizable=yes,status=no');
	}
		
	function forgotPassword() {
		if(document.loginForm.userName.value==""){
			alert('<bean:message key="login.errors.enter_username"/>');
			document.loginForm.userName.focus();
			window.location.href = "#";
		} else {
			document.loginForm.forgotPassword.value = "1";
			//document.loginForm.submit();
			window.location="user.do?mode=password&userName="+document.loginForm.userName.value;
		}
	}

window.onload=registerEvents;
</script>