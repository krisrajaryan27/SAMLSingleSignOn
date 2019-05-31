<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ page import="com.talentPool.admin.AdminConstants"%>
<%@ page import="org.apache.struts.Globals"%>
<%@ page import="com.talentPool.admin.form.AdminForm"%>
<%@page import="com.talentPool.inbox.InboxConstants"%>
<script src="js/scripta/lib/prototype.js"></script>
<script language="JavaScript" src="js/selectoption.js" type="text/javascript"></script>
<script language="JavaScript" src="js/selectbox/selectbox.js" type="text/javascript"></script>
<script language="JavaScript" src="js/scripta/src/effects.js" type="text/javascript"></script>
<script language="javascript">
var exchangeVersion=null;
</script>

<html:form action="/adminHome">
<html:hidden property="mode" value="submitInboxSettings"/>
<html:hidden property="t" name="adminForm"/>
<html:hidden property="st" name="adminForm"/>
<html:hidden property="inboxServerType" name="adminForm"/>
<html:hidden property="inboxSmtpAuthRequired" name="adminForm"/>
<html:hidden property="inboxSmtpAuthSame" name="adminForm"/>

<html:hidden property="inboxOutgoingSSLEnabled" name="adminForm"/>
<html:hidden property="inboxOutgoingTLSEnabled" name="adminForm"/>
<html:hidden property="inboxIncomingSSLEnabled" name="adminForm"/>

<html:hidden property="exchangeSmtp" name="adminForm"/>
<html:hidden property="exchangeServerVersion" name="adminForm"/>

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
	<br>
	<% } %>
	<%
		String saved = (String)request.getAttribute("saved");
		if(saved !=null){
	%>
			<table  id="m_errortable" > 
				<tr>
			    <td class="header">
			        <b>
			        <bean:message key="admin_inbox_settings.label.settings_updated_successfully"/>			        
			        </b>
			    </td>               
				</tr>
			</table>
			<br>
	<%
		}
	%>
	</div>
</div>
<div class="contentDivPop" style="padding-right:20px;">
	<table width="100%" class="boxHeader" style="margin-top:5px;" cellspacing="0" cellpading="0">
		<tr>
			<td class="header" height="18"><strong>
			<bean:message key="admin_inbox_settings.label.inbox_settings"/>			
			</strong></td>
		</tr>
	</table>
	<div class="outerDiv" style="border-top:none;padding:10px 0px 10px 0px;">
		<table border="0" cellspacing="0" cellpadding="0" class="posinput">
		<tr>
			<td class="label" style="width: 167px">
			    <bean:message key="admin_settings.label.company_name"/>
			</td>
			<td ></td>
			<td>
			    <html:text property="inboxDisplayName"  name="adminForm" size="50"/>  
			</td>
		</tr>
		<tr>
			<td class="label">
		  		<bean:message key="admin_settings.label.email"/>
			</td>
		  	<td></td>
		  	<td>
		    	<html:text property="inboxEmail"  name="adminForm" size="50"/>  
		  	</td>
		</tr>						   
		<tr>
			<td class="label">
		    	<bean:message key="admin_settings.label.username"/>
		  	</td>						      
		  	<td ></td>
		  	<td>
		    	<html:text property="inboxUserName"  name="adminForm" size="50"/>  
		  	</td>
		</tr>						  
		<tr>
			<td class="label">
		    	<bean:message key="admin_settings.label.password"/>
		  	</td>						      
		  	<td ></td>
		  	<td>
		    	<html:password property="inboxPassword"  name="adminForm" size="20"/>  
		  	</td>
		</tr>						   
		<tr>
			<td class="label">
		   		<bean:message key="admin_inbox_settings.label.server_type"/>
		  	</td>						      
		  	<td ></td>
		  	<td height="20">
		 		<img 
			  		<logic:equal value="<%=InboxConstants.SERVER_TYPE_POP3 %>" property="inboxServerType" name="adminForm">
				  		src="images/checkedradiobutton.gif" 
			  		</logic:equal>
			  		<logic:notEqual value="<%=InboxConstants.SERVER_TYPE_POP3 %>" property="inboxServerType" name="adminForm">
			    		src="images/radiobutton.gif" 
			  		</logic:notEqual>
			    	name="rdo"
					id='img_<%=InboxConstants.SERVER_TYPE_POP3 %>'
					onclick="onChangeServerType('rdo','<%=InboxConstants.SERVER_TYPE_POP3 %>');">&nbsp;POP3&nbsp;&nbsp;
			 	<img 
		  			<logic:equal value="<%=InboxConstants.SERVER_TYPE_IMAP %>" property="inboxServerType" name="adminForm">
			  			src="images/checkedradiobutton.gif" 
		  			</logic:equal>
		  			<logic:notEqual value="<%=InboxConstants.SERVER_TYPE_IMAP %>" property="inboxServerType" name="adminForm">
			    		src="images/radiobutton.gif" 
			  		</logic:notEqual>
			    	name="rdo"
					id='img_<%=InboxConstants.SERVER_TYPE_IMAP %>'
					onclick="onChangeServerType('rdo','<%=InboxConstants.SERVER_TYPE_IMAP %>');">&nbsp;IMAP&nbsp;&nbsp;
		  
		  		<img 
		  			<logic:equal value="<%=InboxConstants.SERVER_TYPE_EXCHANGE %>" property="inboxServerType" name="adminForm">
			  			src="images/checkedradiobutton.gif" 
		  			</logic:equal>
		  			<logic:notEqual value="<%=InboxConstants.SERVER_TYPE_EXCHANGE %>" property="inboxServerType" name="adminForm">
		    			src="images/radiobutton.gif" 
		  			</logic:notEqual>
		    		name="rdo"
					id='img_<%=InboxConstants.SERVER_TYPE_EXCHANGE %>'
					onclick="onChangeServerType('rdo','<%=InboxConstants.SERVER_TYPE_EXCHANGE %>');">&nbsp;Microsoft Exchange Server			  		
		  	</td>
		</tr>
		</table>		
		<div id="mailServers" style="display: block;">
		<table border="0" cellspacing="0" cellpadding="0" class="posinput">
		<tr>
			<td class="label" style="width: 167px">						
    			<bean:message key="admin_settings.label.incoming_mail_server"/>
  			</td>						      
			<td></td>
  			<td>
    			<html:text property="inboxpopHost"  name="adminForm" size="50"/>  
  			</td>
		</tr>
		</table>
		</div>
		<div id="exchangeServer" style="display: block;">
		<table border="0" cellspacing="0" cellpadding="0" class="posinput">
		<tr>
			<td class="label" style="width: 167px">
				<bean:message key="admin_settings.label.exchange_server_name"/>
			</td>						      
			<td ></td>
  			<td>
    			<html:text property="exchangeServerName"  name="adminForm" size="50"/>  
  			</td>
		</tr>
		<tr>
			<td class="label"><bean:message key="admin_settings.label.exchange_server_domain"/></td>
  			<td ></td>
  			<td>
    			<html:text property="domainName"  name="adminForm" size="50"/>  
  			</td>
		</tr>
		<tr>
			<td class="label"><bean:message key="admin_settings.label.exchange_version"/></td>						      
			<td ></td>
  			<td>
    			<script type="text/javascript"> 
					var opts = [new SelectOption('<%=InboxConstants.EXCHANGE_VERSION_2007_SP1%>','2007 SP1'), new SelectOption('<%=InboxConstants.EXCHANGE_VERSION_2010_SP1%>','2010 SP1')];
					exchangeVersion= new SelectBox(opts,'<bean:write name="adminForm" property="exchangeServerVersion" />','images/btn_dropdown.gif',{namesonly:false, width:'80px', size:12});
					document.write(exchangeVersion.getHtml());
					exchangeVersion.init();
				</script>  
  			</td>
		</tr>
		<tr>
			<td class="label"><bean:message key="admin_settings.label.exchange_smtp"/></td>
			<td></td>  
			<td>
				<img						  
			  		<logic:equal value="<%=InboxConstants.EXCHANGE_USE_SMTP %>" property="exchangeSmtp" name="adminForm">
			  			src="images/checkboxchecked.gif" 
			  		</logic:equal>
			  		<logic:notEqual value="<%=InboxConstants.EXCHANGE_USE_SMTP %>" property="exchangeSmtp" name="adminForm">
			  			src="images/checkboxunchecked.gif"
			  		</logic:notEqual>
		    		onclick="checkSmtpExchange(this);" id="chkSmtp" />
		  	</td>			  	
		</tr>
		</table>
		</div>
		<div id="smtpServer" style="display: block;">
			<table border="0" cellspacing="0" cellpadding="0" class="posinput">	
			<tr>
				<td class="label" style="width: 167px">
	    			<bean:message key="admin_settings.label.outgoing_mail_server"/>
	  			</td>						      
	  			<td ></td>
	  			<td>
	    			<html:text property="inboxSmtpHost"  name="adminForm" size="50"/>  
	  			</td>
			</tr>
			<tr>
				<td class="label">
			  	</td>
			  	<td ></td>
			  	<td style="padding:0px;">
					<table cellspacing="0" cellpadding="0" border="0">
					<tr> 
						<td>
							<img						  
						  		<logic:equal value="<%=InboxConstants.SMTP_AUTHENTICATION_REQUIRED %>" property="inboxSmtpAuthRequired" name="adminForm">
						  			src="images/checkboxchecked.gif" 
						  		</logic:equal>
						  		<logic:notEqual value="<%=InboxConstants.SMTP_AUTHENTICATION_REQUIRED %>" property="inboxSmtpAuthRequired" name="adminForm">
						  			src="images/checkboxunchecked.gif"
						  		</logic:notEqual>
					    		onclick="checkRequireAuthentication(this);" id="chkUpdateResume" />
					  	</td>
					  	<td>
					  		<bean:message key="admin_inbox_settings.label.smtp_authentication"/>					  
					  	</td>
					</tr>
				 	</table>
				  	<div id="DIV_UR" style="padding-left: 20px;
				  		<logic:equal value="<%=InboxConstants.SMTP_AUTHENTICATION_REQUIRED %>" property="inboxSmtpAuthRequired" name="adminForm">
				 			display:block; 
				  		</logic:equal>
				  		<logic:notEqual value="<%=InboxConstants.SMTP_AUTHENTICATION_REQUIRED %>" property="inboxSmtpAuthRequired" name="adminForm">
				  			display:none; 
				  		</logic:notEqual>
				  		" >
				  	
					  	<table cellspacing="0" cellpadding="0" border="0" class="posinput">
						<tr>
							<td class="label">
						  		<img
							  		<logic:equal value="<%=InboxConstants.SMTP_USE_INCOMING_AUTH %>" property="inboxSmtpAuthSame" name="adminForm">
										src="images/checkedradiobutton.gif" 
									</logic:equal>
									<logic:notEqual value="<%=InboxConstants.SMTP_USE_INCOMING_AUTH %>" property="inboxSmtpAuthSame" name="adminForm">
							    		src="images/radiobutton.gif" 
							  		</logic:notEqual>
					    			name="aut"
									id='img_<%=InboxConstants.SMTP_USE_INCOMING_AUTH %>'
									onclick="onChangeAuthType('aut','<%=InboxConstants.SMTP_USE_INCOMING_AUTH %>');">&nbsp;Use same settings as my incoming mail server
						  	</td> 
						</tr>
						<tr>
							<td class="label">
						  		<img
							  		<logic:equal value="<%=InboxConstants.SMTP_USE_OTHER_AUTH %>" property="inboxSmtpAuthSame" name="adminForm">
										src="images/checkedradiobutton.gif" 
									</logic:equal>
									<logic:notEqual value="<%=InboxConstants.SMTP_USE_OTHER_AUTH %>" property="inboxSmtpAuthSame" name="adminForm">
								    	src="images/radiobutton.gif" 
								  	</logic:notEqual>
							   		name="aut"
									id='img_<%=InboxConstants.SMTP_USE_OTHER_AUTH %>'
									onclick="onChangeAuthType('aut','<%=InboxConstants.SMTP_USE_OTHER_AUTH %>');">&nbsp;Log on using
						  	</td> 
						</tr>
						<tr>
							<td style="padding-left:10px;">
						  	<table border="0" cellspacing="0" cellpadding="0" class="posinput">
						  	<tr>
								<td class="label">
									<bean:message key="admin_settings.label.username"/>
								</td>						      
								<td ></td>
								<td>
									<html:text property="inboxSmtpUserName"  name="adminForm" size="30"/>  
								</td>
							</tr>						  
							<tr>
								<td class="label">
									<bean:message key="admin_settings.label.password"/>
								</td>						      
								<td ></td>
								<td>
									<html:password property="inboxSmtpPassword"  name="adminForm" size="30"/>  
								</td>
							</tr>						   
							</table>					  
						  	</td> 
						</tr>
						</table>
				  	</div>
			  	</td>
				</tr>
			</table>
			</div>
			<table border="0" cellspacing="0" cellpadding="0" class="posinput">
			<tr>
				<td class="label" style="width: 167px">
			    	<b><bean:message key="admin_inbox_settings.label.server_port_num"/></b>
			  	</td>						      
			  	<td ></td>
			  	<td></td>
			</tr>
			<tr>
			  	<td class="label">
			  		<bean:message key="admin_inbox_settings.label.incoming_server"/>
			  	</td>						      
			  	<td ></td>
			  	<td>
			    	<html:text property="inboxIncomingPort"  name="adminForm" size="10"/>  
			  	</td>
			</tr>			
			<tr>
				<td class="label"></td>						      
			  	<td ></td>
			  	<td style="padding:0px;">
			  		<table cellspacing="0" cellpadding="0" border="0">
					<tr> 
						<td>
							<img						  
						  		<logic:equal value="<%=InboxConstants.SSL_ENABLED %>" property="inboxIncomingSSLEnabled" name="adminForm">
						  			src="images/checkboxchecked.gif" 
						  		</logic:equal>
						  		<logic:notEqual value="<%=InboxConstants.SSL_ENABLED %>" property="inboxIncomingSSLEnabled" name="adminForm">
						  			src="images/checkboxunchecked.gif"
						  		</logic:notEqual>
					    		onclick="checkSSL(this,document.adminForm.inboxIncomingSSLEnabled, true);" id="chkIncomingSSLEnabled" />
					  	</td>
					  	<td>
					  		<bean:message key="admin_inbox_settings.label.encrypted_connection_ssl"/>
					  	</td>
					</tr>
				  	</table>
			  	</td>
			</tr>			
			<tr>
			  	<td class="label">
					<bean:message key="admin_inbox_settings.label.outgoing_server"/>
			  	</td>						      
			  	<td ></td>
			  	<td>
			    	<html:text property="inboxOutgoingPort"  name="adminForm" size="10"/>  
			  	</td>
			</tr>			
			<tr>
				<td class="label"></td>						      
			  	<td ></td>
			  	<td style="padding:0px;">
			  		<table cellspacing="0" cellpadding="0" border="0">
					<tr> 
						<td>
							<img						  
						  		<logic:equal value="<%=InboxConstants.SSL_ENABLED %>" property="inboxOutgoingSSLEnabled" name="adminForm">
						  			src="images/checkboxchecked.gif" 
						  		</logic:equal>
						  		<logic:notEqual value="<%=InboxConstants.SSL_ENABLED %>" property="inboxOutgoingSSLEnabled" name="adminForm">
						  			src="images/checkboxunchecked.gif"
						  		</logic:notEqual>
					    		onclick="checkSSL(this,document.adminForm.inboxOutgoingSSLEnabled, false);" id="chkOutgoingSSLEnabled" />
					  	</td>
					  	<td>
					  		<bean:message key="admin_inbox_settings.label.encrypted_connection_ssl"/>
					  	</td>
					</tr>
				  	</table>
			  	</td>
			</tr>			
			<tr>
				<td class="label"></td>						      
			  	<td ></td>
			  	<td style="padding:0px;">
			  		<table cellspacing="0" cellpadding="0" border="0">
					<tr> 
						<td>
							<img						  
						  		<logic:equal value="<%=InboxConstants.TLS_ENABLED %>" property="inboxOutgoingTLSEnabled" name="adminForm">
						  			src="images/checkboxchecked.gif" 
						  		</logic:equal>
						  		<logic:notEqual value="<%=InboxConstants.TLS_ENABLED %>" property="inboxOutgoingTLSEnabled" name="adminForm">
						  			src="images/checkboxunchecked.gif"
						  		</logic:notEqual>
					    		onclick="checkTLS(this,document.adminForm.inboxOutgoingTLSEnabled, false);" id="chkOutgoingTLSEnabled" />
					  	</td>
					  	<td>
					  		<bean:message key="admin_inbox_settings.label.tls_connection"/>
					  	</td>
					</tr>
				  	</table>
			  	</td>
			</tr>			
			<tr>
			  	<td colspan="3" height="20"></td>						     
			</tr>									   
			<tr>
			  	<td class="label">
			    	<bean:message key="admin_settings.label.polling_duration"/>
			  	</td>						     
			  	<td ></td>
			  	<td>
			    	<html:text property="inboxPollingDuration" name="adminForm" size="5"/>  
			  	</td>
			</tr>
			</table>
</div>
<div class="navBtn" style="margin-top:5px;"><a href="#" style="width:60px;" class="active" onclick="javascript:submitForm();"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.submit"/></a>
</div>
</html:form>

<script language="javascript">
function onRadioChange(imgGroupName, attachmentId, prevId){
	var imgs = document.getElementsByName(imgGroupName);
	var fId = -1;
	for (i = 0; i < imgs.length; i++) {
			var theImage = imgs[i];
			if (theImage.id.indexOf("img") > -1) {
				if( theImage.id == 'img_'+attachmentId){
					theImage.src = "images/checkedradiobutton.gif";
					fId= attachmentId;
				}else{
					theImage.src = "images/radiobutton.gif";
				}
			}
	}
	return fId;
}

function onChangeServerType(imgGroupName, attachmentId){
	var prevId = document.adminForm.inboxServerType.value;
	var nextId = onRadioChange(imgGroupName, attachmentId,prevId); 
	if(nextId!=-1){
		document.adminForm.inboxServerType.value=nextId;
	}
	setPortNumbers();

	var smtpExc = document.adminForm.exchangeSmtp.value;
	if(attachmentId == <%=InboxConstants.SERVER_TYPE_EXCHANGE%>){
		document.getElementById("exchangeServer").style.display="block";		
		document.getElementById("mailServers").style.display="none";
		
		if(smtpExc=='<%=InboxConstants.EXCHANGE_USE_SMTP%>'){
			document.getElementById("smtpServer").style.display="block";
		}else{
			document.getElementById("smtpServer").style.display="none";
		}
	}else{		
		document.getElementById("exchangeServer").style.display="none";
		document.getElementById("mailServers").style.display="block";
		document.getElementById("smtpServer").style.display="block";		
	}
}

function onChangeAuthType(imgGroupName, attachmentId){
	var prevId = document.adminForm.inboxSmtpAuthSame.value;
	var nextId = onRadioChange(imgGroupName, attachmentId,prevId); 
	if(nextId!=-1){
		document.adminForm.inboxSmtpAuthSame.value=nextId;
	}
}

function checkRequireAuthentication(chkBox){
	var prevId = document.adminForm.inboxSmtpAuthRequired.value;
	if(prevId!='<%=InboxConstants.SMTP_AUTHENTICATION_REQUIRED%>'){
		document.adminForm.inboxSmtpAuthRequired.value='<%=InboxConstants.SMTP_AUTHENTICATION_REQUIRED%>';
		chkBox.src='images/checkboxchecked.gif';
		$("DIV_UR").style.display="block";
	}else{
		document.adminForm.inboxSmtpAuthRequired.value='0';
		chkBox.src='images/checkboxunchecked.gif';
		$("DIV_UR").style.display="none";
	}
}

function checkSmtpExchange(chkBox){	
	var chkSmtpId = document.adminForm.exchangeSmtp.value;	
	if(chkSmtpId!='<%=InboxConstants.EXCHANGE_USE_SMTP%>'){
		document.adminForm.exchangeSmtp.value = '<%=InboxConstants.EXCHANGE_USE_SMTP%>';
		chkBox.src = 'images/checkboxchecked.gif';
		document.getElementById("smtpServer").style.display="block";
	}else{
		document.adminForm.exchangeSmtp.value = '<%=InboxConstants.EXCHANGE_NO_SMTP%>';
		chkBox.src = 'images/checkboxunchecked.gif';
		document.getElementById("smtpServer").style.display="none";
	}
}

function checkSSL(chkBox, hElm, reSetPort){
	var prevId = hElm.value;
	if(prevId!='<%=InboxConstants.SSL_ENABLED%>'){
		hElm.value='<%=InboxConstants.SSL_ENABLED%>';
		chkBox.src='images/checkboxchecked.gif';
	}else{
		hElm.value='<%=InboxConstants.SSL_NOT_ENABLED%>';
		chkBox.src='images/checkboxunchecked.gif';
	}
	if(reSetPort){
		setPortNumbers();
	}
}

function checkTLS(chkBox, hElm, reSetPort){
	var tlsId = hElm.value;
	if(tlsId!='<%=InboxConstants.TLS_ENABLED%>'){
		hElm.value='<%=InboxConstants.TLS_ENABLED%>';
		chkBox.src='images/checkboxchecked.gif';
	}else{
		hElm.value='<%=InboxConstants.TLS_NOT_ENABLED%>';
		chkBox.src='images/checkboxunchecked.gif';
	}
	if(reSetPort){
		setPortNumbers();
	}
}

function setPortNumbers(){
	var portNo = "110";
	if(document.adminForm.inboxServerType.value == <%=InboxConstants.SERVER_TYPE_POP3%>){
		if(document.adminForm.inboxIncomingSSLEnabled.value == <%=InboxConstants.SSL_ENABLED%>){
			portNo = "995";
		}else{
			portNo = "110";
		}
	}else{
		//it is imap
		if(document.adminForm.inboxIncomingSSLEnabled.value == <%=InboxConstants.SSL_ENABLED%>){
			portNo = "993";
		}else{
			portNo = "143";
		}
	}
	
	document.adminForm.inboxIncomingPort.value=portNo;
}

function submitForm(){	
	var frm=document.adminForm;
	frm.exchangeServerVersion.value=exchangeVersion.getSelectedId();	
	frm.submit();
}

window.onload=doOnLoad;
function doOnLoad(){
	var ser = document.adminForm.inboxServerType.value;
	var smtpExc = document.adminForm.exchangeSmtp.value;
	if(ser == <%=InboxConstants.SERVER_TYPE_EXCHANGE%>){
		document.getElementById("exchangeServer").style.display="block";		
		document.getElementById("mailServers").style.display="none";
		
		if(smtpExc=='<%=InboxConstants.EXCHANGE_USE_SMTP%>'){
			document.getElementById("smtpServer").style.display="block";
		}else{
			document.getElementById("smtpServer").style.display="none";
		}
	}else{		
		document.getElementById("exchangeServer").style.display="none";
		document.getElementById("mailServers").style.display="block";
		document.getElementById("smtpServer").style.display="block";		
	}
	
}
</script>