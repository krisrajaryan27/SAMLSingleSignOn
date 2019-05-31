<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<script language="JavaScript">
	function viewDetails(){
		window.location = 'selectionProcess.do?mode=viewOriginalResume&applicantId=<bean:write name="applicantForm" property="applicantId"/>';
	}
	function doOnLoad() {
		<logic:notEmpty name="commentUrl" scope="request">
			var el = document.createElement("iframe");
			el.style.width="0px";
			el.style.height="0px";
			el.setAttribute('frameBorder','0');			
			document.body.appendChild(el);
			var url = '<bean:write name="commentUrl" scope="request"/>'.replace(/&amp;/g, '&');
			el.setAttribute('src', url);
			window.setTimeout(onSave, 300);
		</logic:notEmpty>
		<logic:empty name="commentUrl" scope="request">
			onSave();
		</logic:empty>
	}
	function onSave(){
		<logic:equal property="subMode" name="applicantForm" value="add">
			var openerExist=false;
			if (window.opener != null && !window.opener.closed){
				openerExist=true;
			}
			
			if(openerExist){
				try{
					window.opener.onImportFinish(<bean:write property="emailId" name="applicantForm"/>);
				}catch(e){}
			}
			<logic:equal property="saveNcontinue" name="applicantForm" value="1">
				viewDetails();
			</logic:equal>
			<logic:notEqual property="saveNcontinue" name="applicantForm" value="1">
				if(openerExist){
				window.opener.focus();
				}
				window.close();
			</logic:notEqual>
		</logic:equal>
		<logic:equal property="subMode" name="applicantForm" value="edit">
			viewDetails();
		</logic:equal>
		return false;
	}
	window.onload=doOnLoad;
</script>
