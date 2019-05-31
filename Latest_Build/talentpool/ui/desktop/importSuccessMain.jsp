<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<div class="contentDiv" style="margin: 20px 0px 0px 10px;">
	<table cellpadding="0" cellspacing="0" class="boxETab">
	  <tr>
		  <td class="leftC"></td>
		  <td class="content"><bean:message key="common.message"/></td>
		  <td class="rightC"></td>
	  </tr>
  	</table>  
	<div class="outerDiv" style="padding:20px;">
	<bean:message key="common.candidate"/> processed successfully 
	
	<div class="navBtn" style="float: left;margin-top:10px; ">
		<a	href="#" style="width:60px; margin-right: 5px;" class="active"	onclick="closethis();" id="ignore"><span
		class="rightC"></span><span class="leftC"></span><bean:message key="common.close"/></a> 
	</div>
	<br/><br/><br/>
	</div>
</div>

<script language="JavaScript">
function closethis(){
	var openerExist=false;
	if (window.opener != null && !window.opener.closed){
		openerExist=true;
	}
	if(openerExist){
		try{
			window.opener.refreshGrid();
		}catch(e){
			
		}
	}
	window.close();
}
</script>
