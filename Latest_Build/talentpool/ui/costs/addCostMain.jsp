<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ page import="org.apache.struts.Globals"%>
<%@ page import="com.talentPool.common.utils.CommonUtils"%>
<script language="JavaScript" src="js/scripta/lib/prototype.js" type="text/javascript"></script>
<script language="JavaScript" src="js/scripta/src/effects.js" type="text/javascript"></script>
<script language="JavaScript" src="js/commonFunctions.js" type="text/javascript"></script>
<script language="JavaScript" src="js/selectoption.js" type="text/javascript"></script>
<script language="JavaScript" src="js/selectbox/selectbox.js" type="text/javascript"></script>
<script language="JavaScript" src="js/calender/CalendarPopup.js" type="text/javascript"></script>
<script language="JavaScript" src="js/calender/dateFormatter.js" type="text/javascript"></script>
<script language="JavaScript" src="js/utils/SelectUtils.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css" href="themes/default/CalendarPopup.css">

<script language="JavaScript">
<logic:present name="update" scope="request">
	window.top.hidePopWin(true);
</logic:present>
var selectCostType=null;    
var selectSource=null;            
</script>

<div class="contentDivPop" style="width:550px;">
<%
if (request.getAttribute(Globals.ERROR_KEY) != null) {
%>
<table id="m_errortable">
	<tr>
		<td class='header'><b><bean:message
			key="errors.following_errors" /></b></td>
	</tr>
	<tr>
		<td class="message"><html:errors /></td>
	</tr>
</table>
<br />
<br />
<%
}
%>
<table width="100%" class="boxHeader" style="margin-top:5px;" cellspacing="0" cellpading="0">
		<tr>
			<td class="header" height="18">
			<logic:empty name="costForm" property="costId" >
					<b><bean:message key="add_cost.label.add_cost" /></b>
			</logic:empty>
			<logic:notEmpty name="costForm" property="costId">
					<b><bean:message key="add_cost.label.edit_cost" /></b>
			</logic:notEmpty>
			</td>
		</tr>
</table>

<div class="outerDiv" style="padding-top:5px;padding-bottom: 5px;">
	<html:form action="/costs" onsubmit="submitForm();return false;">
	<html:hidden property="mode"/>
	<html:hidden property="costId"/>
	<html:hidden property="costTypeId"/>
	<html:hidden property="relatedToPosition"/>
	<html:hidden property="relatedToSource"/>
	<html:hidden property="relatedToSubscription"/>
	<html:hidden property="strPositionIds"/>
	<html:hidden property="sourceId"/>
	<html:hidden property="submitted" value="1"/>

	<table class="tabinput" cellpadding="2" cellspacing="2">
	<tr>
		<td class="label"><bean:message key="add_cost.label.date"/></td>
		<td>
		<html:text property="costPaidDate" styleId="costPaidDate" size="12" maxlength="10" onblur="getFormattedDate(this);"/><img src="images/ico_cal.gif" onClick="popUpCal.select(document.getElementById('costPaidDate'),'costPaidDate','dd/MM/yyyy'); return false;" class="CalImg" name="imgCal" id="imgCal" style="margin-bottom:-3px;"/>
		</td>
	</tr>
	<tr>
		<td class="label"><bean:message key="add_cost.label.amount"/></td>
		<td><html:text property="amount" size="12" maxlength="12" styleId="amount"/></td>
	</tr>
	<tr>
		<td class="label"><bean:message key="add_cost.label.purpose"/></td>
		<td>
           <script type="text/javascript">
                var opts = <bean:write name="costForm" property="JSCostTypesArray" filter="false"/>;
                var m = [new SelectOption('0','<bean:message key="common.selectlist.default"/>')];
                opts = m.concat(opts);
                selectCostType = new SelectBox(opts,'<bean:write name="costForm" property="costTypeId"/>','images/btn_dropdown.gif',{namesonly:false, width:'250px', size:15});
                document.write(selectCostType.getHtml());
                selectCostType.init();
           </script>
		</td>
	</tr>
	<tr>
		<td colspan="2" style="height:3px;"></td>
	</tr>
	<tr>
		<td>
		</td>
		<td >
		<img 
		<logic:equal value="1" property="relatedToPosition" name="costForm">
			src="images/checkboxchecked.gif";
		</logic:equal>
		<logic:equal value="0" property="relatedToPosition" name="costForm">
			src="images/checkboxunchecked.gif";
		</logic:equal>
		id="relPos" name="relPos" onclick="changeCheckboxState(this,document.costForm.relatedToPosition,'divPosition');"
		style="margin-bottom:-1px;" />
		<bean:message key="add_cost.label.position_related"/>&nbsp;<bean:message key="common.position"/></td>
	</tr>
	<tr id="divPosition">
		<td></td>
		<td style="height:25px;">
		<table cellspacing="0" cellpadding="0">
			<tr>
				<td>
					<html:select property="listPositions" size="5" style="width:200px;" multiple="true">
					    <html:options property="positionIds" labelProperty="positionNames" />
					</html:select>
				</td>
				<td style="width:42px;">																												
					<div class="navBtn" style="margin:5px;">
						<a href="#" style="width:30px;" class="active" onclick="javascript: moveOptions(document.costForm.listPositions,document.costForm.listSelPositions);return false;" title="Add" ><span class="rightC"></span><span class="leftC"></span><img src="images/ico_rightarrow.gif"  border="0" /></a> 
						<a href="#" style="width:30px;margin-top:5px;" class="active" onclick="javascript: moveOptions(document.costForm.listSelPositions,document.costForm.listPositions);return false;" title="Remove" ><span class="rightC"></span><span class="leftC"></span><img src="images/ico_leftarrow.gif"  border="0" /></a> 
					</div>									
				</td>
				<td>
					<html:select property="listSelPositions" size="5" style="width:200px;" multiple="true">
					    <html:options property="positionIdsSelected" labelProperty="positionNamesSelected" />
					</html:select>
				</td>
			</tr>
		</table>
		</td>
	</tr>

	<tr>
		<td colspan="2" style="height:3px;"></td>
	</tr>
	<tr>
		<td>
		</td><td>
		<img 
		<logic:equal value="1" property="relatedToSource" name="costForm">
			src="images/checkboxchecked.gif";
		</logic:equal>
		<logic:equal value="0" property="relatedToSource" name="costForm">
			src="images/checkboxunchecked.gif";
		</logic:equal>
		id="relSrc" name="relSrc" onclick="changeCheckboxState(this,document.costForm.relatedToSource,'divSource');"
		style="margin-bottom:-1px;" />
		<bean:message key="add_cost.label.source_related"/></td>
	</tr>
	<tr id="divSource">
		<td></td>
		<td style="height:25px; vertical-align: top;">
           <script type="text/javascript">
                var opts = <%=CommonUtils.getListJavaScriptArray(CommonUtils.getSourceIds(), CommonUtils.getSourceNames())%>;
                var m = [new SelectOption('0','<bean:message key="common.selectlist.default"/>')];
                opts = m.concat(opts);
                selectSource = new SelectBox(opts,'<bean:write name="costForm" property="sourceId"/>','images/btn_dropdown.gif',{namesonly:false, width:'250px', size:15});
                document.write(selectSource.getHtml());
                selectSource.init();
           </script>
		</td>
	</tr>

	<tr>
		<td colspan="2" style="height:3px;"></td>
	</tr>
	<tr>
		<td>
		</td><td>
		<img 
		<logic:equal value="1" property="relatedToSubscription" name="costForm">
			src="images/checkboxchecked.gif";
		</logic:equal>
		<logic:equal value="0" property="relatedToSubscription" name="costForm">
			src="images/checkboxunchecked.gif";
		</logic:equal>
		id="relSrc" name="relSrc" onclick="changeCheckboxState(this,document.costForm.relatedToSubscription,'divSub');"
		style="margin-bottom:-1px;" />
		<bean:message key="add_cost.label.period_related"/></td>
	</tr>
	<tr id="divSub">
		<td></td>
		<td style="height:25px; vertical-align: top;">
		<bean:message key="add_cost.label.from_date"/><html:text property="subscriptionDateFrom" styleId="subscriptionDateFrom" size="12" maxlength="10" onblur="getFormattedDate(this);"/><img src="images/ico_cal.gif" onClick="popUpCal.select(document.getElementById('subscriptionDateFrom'),'subscriptionDateFrom','dd/MM/yyyy'); return false;" class="CalImg" name="imgCal" id="imgCal" style="margin-bottom:-3px;"/>
		<bean:message key="add_cost.label.to_date"/><html:text property="subscriptionDateTo" styleId="subscriptionDateTo" size="12" maxlength="10" onblur="getFormattedDate(this);"/><img src="images/ico_cal.gif" onClick="popUpCal.select(document.getElementById('subscriptionDateTo'),'subscriptionDateTo','dd/MM/yyyy'); return false;" class="CalImg" name="imgCal" id="imgCal" style="margin-bottom:-3px;"/>
		</td>
	</tr>
	<tr>
		<td colspan="2" style="height:3px;"></td>
	</tr>
	<tr>
		<td class="label" style="vertical-align: top;"><bean:message key="add_cost.label.remarks"/></td>
		<td>
		<html:textarea property="remarks" rows="3" cols="46"></html:textarea>
		</td>
	</tr>
	</table>
	</html:form>
</div>

			<div class="navBtn" style="float: right; margin-top: 10px;"><a href="#"
				style="width:60px;" class="active"
				onclick="javascript: submitForm();"><span class="rightC"></span><span
				class="leftC"></span><bean:message key="common.submit" /></a> <a
				href="#" style="width:60px; margin-left:5px;" class="active"
				onclick="javascript: window.top.hidePopWin(false);return false;"><span
				class="rightC"></span><span class="leftC"></span><bean:message
				key="common.cancel" /></a></div>

</div>
<DIV id="calDiv" style="position:absolute;background:#FFFFFF;z-index:1000;" ></DIV>

<script type="text/javascript">
var popUpCal = new CalendarPopup("calDiv"); 
popUpCal.showNavigationDropdowns();
var dtf = new DateFormatter();
dtf.setDisplayFormat('DD/MM/YYYY');
function getFormattedDate(obj){
	if(obj.value.trim()!=''){
  	  if(!dtf.checkDate(obj)){
  		obj.select();
  		alert('<bean:message key="calendar.error.invalid_date"/>');
  		obj.focus();
  		return false;
  	  }else {
  		return true;
  	  }
	}
	return true;
}

function changeCheckboxState(chkBox, fld, divId){
	var prevId = fld.value;
	if(prevId!='1'){
		fld.value='1';
		chkBox.src='images/checkboxchecked.gif';
		Element.show(divId);
	}else{
		fld.value='0';
		chkBox.src='images/checkboxunchecked.gif';
		Element.hide(divId);
	}
}



function submitForm(){
	if(document.costForm.costPaidDate.value.trim()==""){
		alert('<bean:message key="add_cost.error.invalid_payment_date"/>');
		document.costForm.costPaidDate.focus();
		return false;
	}
	if(document.costForm.amount.value.trim()==""){
		alert('<bean:message key="add_cost.error.invalid_amount"/>');
		document.costForm.amount.focus();
		return false;
	}
	if(selectCostType.getSelectedId()==0){
		alert('<bean:message key="add_cost.error.please_select_purpose"/>');
		selectCostType.setFocus();
		return false;
	}else{
		document.costForm.costTypeId.value=selectCostType.getSelectedId();
	}
	if(document.costForm.relatedToPosition.value==1){
		if(document.costForm.listSelPositions.length==0){
			alert('<bean:message key="common.please_select"/> <bean:message key="common.position"/>');
			return false;
		}else{
			var pos = '';
			for (i = 0; i < document.costForm.listSelPositions.length; i++) {
				if (pos.length > 0) {
					pos += ',';
				}
				pos += document.costForm.listSelPositions[i].value;
			}
			document.costForm.strPositionIds.value=pos;
		}
	}else{
		document.costForm.strPositionIds.value="";
	}
	
	if(document.costForm.relatedToSource.value==1){
		if(selectSource.getSelectedId()==0){
			alert('<bean:message key="add_cost.error.please_select_source"/>');
			selectSource.setFocus();
			return false;
		}else{
			document.costForm.sourceId.value=selectSource.getSelectedId();
		}
	}else{
		document.costForm.sourceId.value="";
	}
	
	if(document.costForm.relatedToSubscription.value==1){
		if(document.costForm.subscriptionDateFrom.value.trim()==""){
			alert('<bean:message key="add_cost.error.invalid_from_date"/>');
			document.costForm.subscriptionDateFrom.focus();
			return false;
		}
		if(document.costForm.subscriptionDateTo.value.trim()==""){
			alert('<bean:message key="add_cost.error.invalid_to_date"/>');
			document.costForm.subscriptionDateTo.focus();
			return false;
		}
	}	
	document.costForm.submit();
}

function showHideDivs(){
	var  divId = 'divPosition';
	<logic:equal value="0" property="relatedToPosition" name="costForm">
	Element.hide(divId);
	</logic:equal>
	<logic:equal value="1" property="relatedToPosition" name="costForm">
	Element.show(divId);
	</logic:equal>

	divId = 'divSource';
	<logic:equal value="0" property="relatedToSource" name="costForm">
	Element.hide(divId);
	</logic:equal>
	<logic:equal value="1" property="relatedToSource" name="costForm">
	Element.show(divId);
	</logic:equal>

	divId = 'divSub';	
	<logic:equal value="0" property="relatedToSubscription" name="costForm">
	Element.hide(divId);
	</logic:equal>
	<logic:equal value="1" property="relatedToSubscription" name="costForm">
	Element.show(divId);
	</logic:equal>
}
function actionOnLoad(){
	window.top.setPopTitle('<b><bean:message key="costs.label.title" /></b>');
	showHideDivs();
	Event.observe('amount', "keydown", this.onKeyDown.bindAsEventListener(this));
}
 function onKeyDown(event) {
//48-57,96-105(0-9) 110, 190(.) 36 - home, 35 - end, 37 - left, 39 - right, 46 - del, 8 - bksp, 9 -tab, 13-ENTER, 16- SHIFT
	  var cd = event.keyCode;
	  var validKey=false;
	  
	  if( (cd>47 && cd<58) || (cd>95 && cd<106) ||cd==110||cd==190||cd==36||cd==35||cd==37||cd==39||cd==46||cd==8||cd==9||cd==13||cd==16){
	  	validKey=true;
	  }
	  var val = Event.element(event).value;
	  if(cd==110||cd==190){
		  if(val.indexOf(".")>=0){
				validKey=false;			  
		  }
	  }
	if(event.shiftKey && cd!=9){
		validKey=false;		
	}
	  if(!validKey){
	  	Event.stop(event);
	  }
	  
	  
  }

window.onload=actionOnLoad;
</script>

