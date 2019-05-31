<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<script language="JavaScript" src="js/scripta/lib/prototype.js" type="text/javascript"></script>	
<script language="JavaScript" src="js/scripta/src/effects.js" type="text/javascript"></script>
<script language="JavaScript" src="js/scripta/src/controls.js" type="text/javascript"></script>
<script language="JavaScript" src="js/selectoption.js" type="text/javascript"></script>
<script language="JavaScript" src="js/selectbox/selectbox.js" type="text/javascript"></script>
<script language="JavaScript" src="js/ajaxfunctions.js" type="text/javascript"></script>
<div class="contentDiv">
	<table class="tabinput">
				<tr>	
					<td>&nbsp;</td>
					<td>	
						<bean:message key="applicant_registration.label.register_with_position" />&nbsp;<bean:message key="common.position" />			
					</td>
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td>
						
						<script language="JavaScript">
					var opts = <bean:write name="strPositions" scope="request" filter="false"/>;
					var m = [new SelectOption('-1','<bean:message key="common.select_position_option"/>')];
					opts = m.concat(opts);
					var selectBoxPosition = new SelectBox(opts,'-1','images/btn_dropdown.gif',{namesonly:false, width:'150px'});
					document.write(selectBoxPosition.getHtml());
					selectBoxPosition.init();
				</script>
				
					</td>
				</tr>
				</table>
				</div>
			</td>			
		</tr>
		<table>
		<tr>
			<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<div class="navBtn" style="margin-top:5px;float:right;">
					<a href="#" style="width:60px;" class="active" onclick="javascript:submitForm();"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.submit"/></a>
				</div>
			</td>
			
		</tr>
		</table>
	</table>
</div>
<script language="JavaScript">
function submitForm() {
	var errors = '';
	if( selectBoxPosition.getSelectedId() == '-1') {
		if(errors.length > 0) { 
			errors += '\n';
		}
		errors += '<bean:message key="common.please_select"/> <bean:message key="common.position"/> <bean:message key="applicant_registration.error.select_position" />';
	}
	if(errors.length > 0) {
		alert(errors);
	} else {
		var  url = "applicantRegistration.do?mode=defaultStep1&positionId=" + selectBoxPosition.getSelectedId();
		window.location =  url;
	}
}
</script>