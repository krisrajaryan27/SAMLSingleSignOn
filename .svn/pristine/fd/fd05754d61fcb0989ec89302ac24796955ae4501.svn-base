<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.talentPool.desktop.form.DesktopSearchForm"%>
<%@page import="com.talentPool.common.utils.CommonUtils"%>
<%@page import="com.talentPool.desktop.utils.DesktopUtils"%>
<%@page import="com.talentPool.applicant.ApplicantConstants"%>
<%@page import="com.talentPool.desktop.constants.DesktopConstants"%>
<%@page import="com.talentPool.custom.constants.CustomFieldConstants"%>
<%@page import="com.talentPool.custom.dataobject.CustomFieldData"%>
<%@page import="com.talentPool.applicant.manager.ImportConfigurationManager"%>
<%@page import="com.talentPool.applicant.constants.ImportConfigurationConstants"%>
<%@page import="com.talentPool.applicant.dataobject.ImportFieldData"%>
<%@page import="com.talentPool.custom.utils.CustomFieldUtils"%>
<style>
.educationT {border:1px solid #99CC33; border-collapse: collapse;}
.educationT TD{padding-left:3px; padding-right:4px;background:#FFFFFF; padding-top: 3px; padding-bottom: 2px;}
.educationT TD.header{border-bottom:1px solid #F9FCF3;color: #666666;background:#D0E4A3;}
.educationT TD.headerBottom{background:#99CC33;height:2px;}
</style>
<script src="js/dhtmlxGrid/dhtmlXCommon.js"></script> 
<script src="js/dhtmlxGrid/dhtmlXGrid.js"></script> 
<script src="js/dhtmlxGrid/dhtmlXGridCell.js"></script>
<script src="js/dhtmlxGrid/dhtmlXGrid_excell_link.js"></script>
<script src="js/dhtmlxGrid/dhtmlXGrid_drag.js"></script>
<script src="js/tpSelectListFunctions.js"></script>
<link rel="stylesheet" type="text/css" href="themes/default/calendar.css"/>
<link rel="stylesheet" type="text/css" href="themes/default/CalendarPopup.css"/>
<script language="JavaScript" src="js/scripta/lib/prototype.js" type="text/javascript"></script>
<script language="JavaScript" src="js/scripta/src/effects.js" type="text/javascript"></script>
<script language="JavaScript" src="js/commonFunctions.js" type="text/javascript"></script>
<script language="JavaScript" src="js/ajaxfunctions.js"	type="text/javascript"></script>
<script language="JavaScript" src="js/selectoption.js" type="text/javascript"></script>
<script language="JavaScript" src="js/selectbox/selectbox.js" type="text/javascript"></script>
<script language="JavaScript" src="js/commonGridFunctions.js" type="text/javascript"></script>
<script language="JavaScript" src="js/utils/updater.js" type="text/javascript"></script>
<script language="JavaScript" src="js/checkboxandradiogroup/checkboxradiogroup.js" type="text/javascript"></script>
<script language="JavaScript" src="js/checkboxlist/checkboxlist.js" type="text/javascript"></script>
<script language="JavaScript" src="js/calender/dateFormatter.js" type="text/javascript"></script>
<script language="JavaScript" src="js/calender/monthYearCalender.js" type="text/javascript"></script>
<script language="JavaScript" src="js/calender/CalendarPopup.js"></script>	
<script language="JavaScript" src="js/customfields/customfield.js"></script>
<script language="JavaScript" src="js/customfields/customfieldvalidator.js"></script>
<%
ArrayList importFields = ImportConfigurationManager.getImportFields();
ArrayList customFields = (ArrayList)request.getAttribute("customFields");
DesktopSearchForm desktopSearchForm = (DesktopSearchForm) request.getAttribute("desktopSearchForm");

ArrayList degreeIds = CommonUtils.getDegreeIds();
ArrayList degreeNames = CommonUtils.getDegreeNames();
ArrayList branchIds = CommonUtils.getBranchIds();
ArrayList branchNames = CommonUtils.getBranchNames();
%>
<script language="JavaScript">

<%=CustomFieldUtils.getArrayForCustomFields(customFields)%>

var selectBoxDegree=null;
var selectBoxBranch=null;
var selectBoxSector=null;
var selectBoxIndustry=null;
var selectBoxSource=null;
var selectBoxResumeType=null;
var selectBoxYears=null;
var selectBoxMonths=null;
var checkboxListCategories=null;

var degreeControls = new Array();
var degreeOptions = <%=CommonUtils.getListJavaScriptArray(degreeIds,degreeNames)%>;
var m = [new SelectOption('-1','')];
degreeOptions = m.concat(degreeOptions);

var branchControls = new Array();
var branchOptions = <%=CommonUtils.getListJavaScriptArray(branchIds,branchNames)%>;
m = [new SelectOption('-1','')];
branchOptions = m.concat(branchOptions);

function getCustomTitle(obj){
	var grdId = obj.grid.entBox.id; 
	if(grdId == 'GRD_SKILLS'){
		switch(obj.cell._cellIndex){			
			case 0:				
				return obj.grid.getUserData(obj.cell.parentNode.idd,"skillName");				
				break;
		}	
	}if(grdId == 'GRD_SKILLS_TO'){
		switch(obj.cell._cellIndex){			
			case 0:				
				return obj.grid.getUserData(obj.cell.parentNode.idd,"skillName");				
				break;
		}	
	}
	
	return obj.cell.innerHTML;
}
</script>

<div class="contentDivPop" >
<html:form action="/desktop" >
	
	<html:hidden property="mode" name="desktopSearchForm" value="saveBulkImportParameters"/>
	<html:hidden property="sessionId" name="desktopSearchForm"/>
	<html:hidden property="userId" name="desktopSearchForm"/>	
	<html:hidden property="sourceId" name="desktopSearchForm"/>
	<html:hidden property="skillIds" name="desktopSearchForm"/>
	<html:hidden property="experience" name="desktopSearchForm"/>
	<html:hidden property="sessionType" name="desktopSearchForm"/>
	<html:hidden property="emailId" name="desktopSearchForm"/>
	
	<table class="boxHeaderG" cellspacing="0" cellpading="0" style="<logic:equal property="sessionType" name="desktopSearchForm" value="<%=DesktopConstants.SESSION_TYPE_INBOX_BULK_IMPORT%>">width: 892px</logic:equal><logic:notEqual property="sessionType" name="desktopSearchForm" value="<%=DesktopConstants.SESSION_TYPE_INBOX_BULK_IMPORT%>">width: 632px</logic:notEqual>">
		<tr>
			<td class="header" height="18" style="padding-left: 10px;"><strong><bean:message key="bulk_import_criteria.lable.note"/></strong></td>
		</tr>
	</table>
	<div class="outerDiv" style="padding:10px 0px 10px 0px;<logic:equal property="sessionType" name="desktopSearchForm" value="<%=DesktopConstants.SESSION_TYPE_INBOX_BULK_IMPORT%>">width: 890px;height: 500px;</logic:equal><logic:notEqual property="sessionType" name="desktopSearchForm" value="<%=DesktopConstants.SESSION_TYPE_INBOX_BULK_IMPORT%>">width: 630px;height: 290px;</logic:notEqual>overflow: auto; position: relative;">
	
	<%
			for(int j=0; j<importFields.size();j++){
				ImportFieldData fieldData = (ImportFieldData)importFields.get(j);
				String fieldId = fieldData.getFieldId();
				String fieldType = fieldData.getFieldType();
				String showValue = fieldData.getFieldImportShow();

				if(showValue.equals(ImportConfigurationConstants.FIELD_SHOW)){						
				if(fieldType.equals(ImportConfigurationConstants.FIELD_TYPE_NORMAL)){
					if(fieldId.equals(ImportConfigurationConstants.FIELD_SOURCE)){
						%>
						<table class="tabinput">
							<tr>
								<td	style="width: 130px;" <html:errormap property="source_required" errorStyleClass="labelError" styleClass="label"/>>
									<bean:message key="common.source" />:</td>
								<td>
									<script type="text/javascript">
										var opts = <%=CommonUtils.getListJavaScriptArray(desktopSearchForm.getSourceIds(),desktopSearchForm.getSourceNames())%>;
										var m = [new SelectOption('-1','<bean:message key="common.selectlist.select"/>')];
										opts = m.concat(opts);
										selectBoxSource = new SelectBox(opts,'<bean:write property="sourceId" name="desktopSearchForm"/>','images/btn_dropdown.gif',{namesonly:false, width:'165px', size:15, textboxclass:'Grey'});
										document.write(selectBoxSource.getHtml());
										selectBoxSource.init();
									</script>
								</td>
							</tr>
						</table>
						<%
					}
					if(fieldId.equals(ImportConfigurationConstants.FIELD_CURRENT_LOCATION)){
						%>
						<table class="tabinput">
							<tr>
								<td class="label" style="width: 130px;"><bean:message key="common.current_location" />:</td>					
								<td class="label">
									<html:text styleId="currentLocation" property="currentLocation" name="desktopSearchForm" size="30"	maxlength="50" /> 
								</td>
							</tr>
						</table>
						<%
					}
					if(fieldId.equals(ImportConfigurationConstants.FIELD_SKILLS)){
						%>
						<span class="heading"><bean:message key="common.skills" /></span>
										
								<table cellspacing="0" cellpadding="0" style="padding: 8px;">
									<tr>
										<td style="vertical-align: top;">
											<table cellpadding="0" cellspacing="0">
												<tr>
													<td class="gridborder">
														<div id="GRD_SKILLS" style="width:170px;height:105px;"></div>
													</td>
												</tr>
											</table>
										</td>		
										<td style="padding: 10px;">					
											<a href="#" onclick="javascript: selectItem(dataGridSkills,dataGridSkillsTo);return false;" title="Add" >
												<img src="images/ico_rightarrow.gif"  border="0" />
											</a>
											<br/>
											<a href="#" onclick="javascript: deselectItem(dataGridSkillsTo,dataGridSkills);return false;" title="Remove" >
												<img src="images/ico_leftarrow.gif"  border="0" />
											</a> 
										</td>					
										<td style="vertical-align: top;">
											<table cellpadding="0" cellspacing="0">
												<tr>
													<td class="gridborder">
														<div id="GRD_SKILLS_TO" style="width:170px;height:105px;"></div>
													</td>
												</tr>
											</table>
										</td>
									</tr>	
								</table>	

						<%
					}
					if(fieldId.equals(ImportConfigurationConstants.FIELD_EDUCATION)){
						%>
						<span class="heading"><bean:message key="common.education" /></span>
						<table class="tabinput">
						<tr>
							<td>
								<table class="educationT" id="tblEdu" width="583">
										<tbody>
											<tr>
												<td class="header"></td>
												<td class="header"><bean:message key="common.year" /></td>
												<td class="header"><bean:message key="common.institute" /></td>
												<td class="header"><bean:message key="common.degree" /></td>
												<td class="header"><bean:message key="common.branch" /></td>
												<td class="header"><bean:message key="common.class" /></td>
											</tr>
											<tr>
												<td colspan="6" class="headerBottom"></td>
											</tr>
											<% int i =0;%>
											<tr>
												<td><img src="images/radiobutton.gif" name="edu" id='img_<%=i %>' onclick="onOptionChange('edu','<%=i %>');" /></td>
												<td><input type="text" name="educationYearOfPassing" size="4" value="" onblur="getFormattedYear(this);" id="educationYearOfPassing[<%=i %>]" class="Grey" /></td>
												<td><input type="text" name="educationInstitute" size="27"	maxlength="150" value=""id="educationInstitute[<%=i%>]" class="Grey" />
													<script language="JavaScript">
												//	new Ajax.Autocompleter("educationInstitute[<%=i%>]", "autocomplete", "importResume.do?mode=getAutoCompleteList&fld=<%=ApplicantConstants.AUTOCOMPLETE_INSTITUTE%>", {frequency: 0.001});
													</script>
												</td>
												<td><input type="hidden" name="educationDegreeId" /> <script
													type="text/javascript">
													selectBoxDegree = new SelectBox(degreeOptions,'','images/btn_dropdown.gif',{namesonly:false, width:'75px', size:10, textboxclass:'Grey'});
													document.write(selectBoxDegree.getHtml());
													selectBoxDegree.init();
													degreeControls[degreeControls.length]=[<%=i%>,selectBoxDegree];															
												</script></td>
												<td><input type="hidden" name="educationMajorId" /> <script
													language="JavaScript">
													selectBoxBranch = new SelectBox(branchOptions,'','images/btn_dropdown.gif',{namesonly:false, width:'120px', size:10, textboxclass:'Grey'});
													document.write(selectBoxBranch.getHtml());
													selectBoxBranch.init();
													branchControls[branchControls.length]=[<%=i%>,selectBoxBranch];															
												</script></td>
												<td><input type="text" name="educationalGrade" size="6"
													maxlength="100"	value="" id="educationalGrade[<%=i%>]" class="Grey" /></td>
											</tr>
					
										</tbody>
									</table>
									<table class="tabinput" width="480">
										<tr>
											<td style="text-align: right;" class="Grey"><a href="#"
												onclick="deleteEducation();" class="green">Delete Selected</a>&nbsp;|&nbsp;
												<a href="#" onclick="addEducation();" class="green"><b>more>></b></a>
											</td>
										</tr>
									</table>
							</td>
						</tr>
						</table>
					<% 
					}
					if(fieldId.equals(ImportConfigurationConstants.FIELD_EXPERIENCE)){
						%>
						<table class="tabinput">
							<tr>
								<td	class="label" style="width: 130px;"><bean:message key="common.working_since" />:</td>
								<td><script type="text/javascript">
									var opts = <%=DesktopUtils.getJSArrayForYears()%>;
									var m = [new SelectOption('-1',' --')];
					                opts = m.concat(opts);
									selectBoxYears = new SelectBox(opts,'','images/btn_dropdown.gif',{namesonly:false, width:'40px', size:6, textboxclass:'Grey'});
									document.write(selectBoxYears.getHtml());
									selectBoxYears.init();
									</script>
								</td>
								<td class="label">years&nbsp;</td>
								<td><script type="text/javascript">
									var opts = <%=DesktopUtils.getJSArrayForMonths()%>;
									var m = [new SelectOption('-1',' -- ')];
					                opts = m.concat(opts);
									selectBoxMonths = new SelectBox(opts,'','images/btn_dropdown.gif',{namesonly:false, width:'40px', size:6, textboxclass:'Grey'});
									document.write(selectBoxMonths.getHtml());
									selectBoxMonths.init();
									</script>
								</td>
								<td class="label">months</td>										
							</tr>
						</table>
						<%
					}
					if(fieldId.equals(ImportConfigurationConstants.FIELD_CURRENT_EMPLOYER)){
						%>
						<table class="tabinput">
							<tr>
								<td class="label" style="width: 130px;"><bean:message key="common.current_employer" />:</td>
								<td>
									<html:text styleId="currentEmployer" property="currentEmployer" name="desktopSearchForm" size="30"	maxlength="50" />
								</td>
							</tr>
						</table>
						<%
					}
					if(fieldId.equals(ImportConfigurationConstants.FIELD_CURRENT_CTC)){
						%>
						<table class="tabinput">
							<tr>
								<td class="label" style="width: 130px;"><bean:message key="common.current_ctc" />:</td>
								<td><html:text styleId="currentCTC" property="currentCTC" name="desktopSearchForm" size="30"	maxlength="10" />
								</td>
							</tr>
						</table>
						<%
					}
					if(fieldId.equals(ImportConfigurationConstants.FIELD_EXPECTED_CTC)){
						%>
						<table class="tabinput">
							<tr>
								<td class="label" style="width: 130px;"><bean:message key="common.expected_ctc" />:</td>
								<td><html:text styleId="expectedCTC" property="expectedCTC" name="desktopSearchForm" size="30"	maxlength="10" />
								</td>
							</tr>
						</table>
						<%
					}
					if(fieldId.equals(ImportConfigurationConstants.FIELD_NOTICE_PERIOD)){
						%>
						<table class="tabinput">
							<tr>
								<td class="label" style="width: 130px;"><bean:message	key="common.time_to_join" />:</td>
								<td><html:text styleId="noticePeriod" property="noticePeriod" name="desktopSearchForm" size="30"	maxlength="10" />
								</td>
							</tr>
						</table>
						<%
					}
					if(fieldId.equals(ImportConfigurationConstants.FIELD_NOTE)){
						%>
							<span class="heading">
								<bean:message key="common.note" /></span>
							<table class="tabinput">
							<tr>
								<td>
									<html:textarea property="note"	name="desktopSearchForm" rows="3" cols="65" styleClass="Grey"></html:textarea>
								</td>
							</tr>
							</table>
						<%
					}	
				}else{
					if(customFields!=null && customFields.size()>0){ 
						for(int i=0; i<customFields.size();i++){
						CustomFieldData data = (CustomFieldData)customFields.get(i);
						String customFieldId = data.getFieldName();
							if(fieldId.equals(customFieldId)){
							%>
								<table class="tabinput">
								<tr>
									<td class="label" style="width: 130px;"><%=data.getFieldDisplayName() %>:</td>
									<td><%=data.getUI() %></td>
								</tr>
								</table>
							<%
							}
						} 
				 	} 
				}
				}
			} 
		%>
		
		
						
	<br/>
	</div>
	</html:form>
	
	<div class="navBtn" style="float: right;margin-top: 10px;">
		<a href="#" style="width:65px;" class="active" onclick="javascript:submitForm();" id="submit"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.submit"/></a>
	</div>
	<br/>
</div>

<DIV id="calDiv" style="position:absolute;background:#FFFFFF;z-index:1000;" ></DIV>
<script language="javascript">
var popUpCal = new CalendarPopup("calDiv"); 
popUpCal.showNavigationDropdowns();

//DATE FORMATTER CODE AND FUNCTIONS
var dtfo = new DateFormatter();
function getFDate(obj,format){
	dtfo.setDisplayFormat(format);
	if(obj.value.trim()!=''){
	if(!dtfo.checkDate(obj)){
		obj.select();
		alert("<bean:message key="common.please_enter" /> <bean:message key="common.date" /> <bean:message key="common.in" /> " + format + " <bean:message key="common.format" />");
		obj.focus();
		return false;
	}else {
		return true;
	}
	}
	return true;
}

function getFNumber(obj){
	if(obj.value.trim()!=''){
		if(isNaN(obj.value)){
			alert('<bean:message key="common.please_enter_valid_number" />');
			obj.focus();
			return false;
		}
	}
}

var dataGridSkills;
var dataGridSkillsTo;

function submitForm(){

<%if(ImportConfigurationManager.isImportFieldShow(ImportConfigurationConstants.FIELD_EDUCATION)){%>
	if(degreeControls.length>1){
		for(var i=0; i<degreeControls.length; i++){
			frm.educationDegreeId[i].value=degreeControls[i][1].getSelectedId();
			frm.educationMajorId[i].value=branchControls[i][1].getSelectedId();
		}
	}else{
		if(degreeControls[0]!=null){
		frm.educationDegreeId.value=degreeControls[0][1].getSelectedId();
		frm.educationMajorId.value=branchControls[0][1].getSelectedId();
		}
	}	
<%}%>
<%if(ImportConfigurationManager.isImportFieldShow(ImportConfigurationConstants.FIELD_SOURCE)){%>
	var srcId = selectBoxSource.getSelectedId();
	if(srcId=='' || srcId=="-1"){
		alert('<bean:message key="create_applicant.error.please_select_source" />');
		return false;
	}else{
		document.desktopSearchForm.sourceId.value = srcId;
	}
<%}%>
<%if(ImportConfigurationManager.isImportFieldShow(ImportConfigurationConstants.FIELD_EXPERIENCE)){%>
	/*if(selectBoxYears.getSelectedId()=="-1" && selectBoxMonths.getSelectedId()=="-1"){
		alert('<bean:message key="create_applicant.error.enter_working_since_date" />');
		return false;
	}else{
		document.desktopSearchForm.experience.value = selectBoxYears.getSelectedId()+ ","+ selectBoxMonths.getSelectedId();
	}*/
	if(selectBoxYears.getSelectedId()!="-1" || selectBoxMonths.getSelectedId()!="-1"){
		document.desktopSearchForm.experience.value = selectBoxYears.getSelectedId()+ ","+ selectBoxMonths.getSelectedId();
	}
<%}%>
<%if(ImportConfigurationManager.isImportFieldShow(ImportConfigurationConstants.FIELD_SKILLS)){%>
	if(dataGridSkillsTo){
		var val = dataGridSkillsTo.getAllItemIds();
		document.desktopSearchForm.skillIds.value = val;
	}
<%}%>
<%if(ImportConfigurationManager.isImportFieldShow(ImportConfigurationConstants.FIELD_CURRENT_CTC)){%>
	if(!validNumber(document.desktopSearchForm.currentCTC)){
		return false;
	}
<%}%>		
<%if(ImportConfigurationManager.isImportFieldShow(ImportConfigurationConstants.FIELD_EXPECTED_CTC)){%>
	if(!validNumber(document.desktopSearchForm.expectedCTC)){
		return false;
	}
<%}%>
	
	validCustomfields('<%=CustomFieldConstants.DEFAULT_SELECT_OPTION%>', '<%=CustomFieldConstants.TYPE_DROPDOWN%>', '<%=CustomFieldConstants.TYPE_LISTBOX%>', '<%=CustomFieldConstants.TYPE_CHECKBOX%>', '<%=CustomFieldConstants.TYPE_RADIO%>', false);

	showUpdater('submit',{setHeight: false, setWidth: false, offsetLeft: -50});
	document.desktopSearchForm.submit();

}
	
//education functions

var frm = document.desktopSearchForm;
var eduCount=1;
var eduTable = $('tblEdu');
var eduSelected = -1;

function addEducation(){
	
	var tBody = eduTable.getElementsByTagName('tbody')[0];
	var myRow = document.createElement("tr");
	myRow.id=eduCount;
	addListener(myRow);
	var myCell = document.createElement("td");
	var elm = document.createElement("img");
	elm.src='images/radiobutton.gif';
	elm.name="edu";
	elm.id='img_'+eduCount;
	Event.observe(elm, "click", onEduOptClick.bindAsEventListener(this));
	myCell.appendChild(elm);
	myRow.appendChild(myCell);
	
	myCell = document.createElement("td");
	var el = createFormElement("input","text","educationYearOfPassing","","Grey");
	el.size="4";
	el.id="educationYearOfPassing["+eduCount+"]";
	Event.observe(el, "blur", onBlurYop.bindAsEventListener(this));
	myCell.appendChild(el);	
	myRow.appendChild(myCell);	

	myCell = document.createElement("td");
	var el = createFormElement("input","text","educationInstitute","","Grey");
	el.size="27";
	el.maxlength="150";
	el.id="educationInstitute["+eduCount+"]";
	myCell.appendChild(el);	
	myRow.appendChild(myCell);	

	myCell = document.createElement("td");
	selectBoxDegree = new SelectBox(degreeOptions,'0','images/btn_dropdown.gif',{namesonly:false, width:'75px', size:10, textboxclass:'Grey'});
	myCell.innerHTML = selectBoxDegree.getHtml();
	myCell.innerHTML = myCell.innerHTML + '<input type="hidden" name="educationDegreeId"/>';
	myRow.appendChild(myCell);
	 
	myCell = document.createElement("td");
	selectBoxBranch = new SelectBox(branchOptions,'0','images/btn_dropdown.gif',{namesonly:false, width:'120px', size:10, textboxclass:'Grey'});
	myCell.innerHTML = selectBoxBranch.getHtml();
	myCell.innerHTML = myCell.innerHTML + '<input type="hidden" name="educationMajorId"/>';
	myRow.appendChild(myCell);
	
	myCell = document.createElement("td");
	var el = createFormElement("input","text","educationalGrade","","Grey");
	el.size="6";
	el.maxlength="100";
	el.id="educationalGrade["+eduCount+"]";
	myCell.appendChild(el);	
	myRow.appendChild(myCell);	
	
	tBody.appendChild(myRow);

	//new Ajax.Autocompleter('educationInstitute['+eduCount+']', "autocomplete", "importResume.do?mode=getAutoCompleteList&fld=<%=ApplicantConstants.AUTOCOMPLETE_INSTITUTE%>", {frequency: 0.001});
	selectBoxDegree.init();
	selectBoxBranch.init();
	degreeControls[degreeControls.length]=[eduCount,selectBoxDegree];	
	branchControls[branchControls.length]=[eduCount,selectBoxBranch];	
	onOptionChange("edu",eduCount);
	eduCount = eduCount+1;
}

function onOptionChange(imgGroupName, attachmentId){
	var imgs = getElementsByName_iefix("img",imgGroupName);
	for (i = 0; i < imgs.length; i++) {
			var theImage = imgs[i];
			if (theImage.id.indexOf("img") > -1) {
				if( theImage.id == 'img_'+attachmentId){
					eduSelected = attachmentId;
					theImage.src = "images/checkedradiobutton.gif";
				}else{
					theImage.src = "images/radiobutton.gif";
				}
			}
	}
}
function getElementsByName_iefix(tag, name) {
     var elem = document.getElementsByTagName(tag);
     var arr = new Array();
     for(i = 0,iarr = 0; i < elem.length; i++) {
          att = elem[i].getAttribute("name");
          if(att == name) {
               arr[iarr] = elem[i];
               iarr++;
          }
     }
     return arr;
}

function deleteEducation(){
	deleteRow(eduTable,eduSelected);
	var newDegreeControls = new Array();
	for(var i=0; i<degreeControls.length; i++){
		if(degreeControls[i][0]!= eduSelected){
			newDegreeControls[newDegreeControls.length]=degreeControls[i];
		}
	}
	degreeControls = newDegreeControls;
	
	var newBranchControls = new Array();
	for(var i=0; i<branchControls.length; i++){
		if(branchControls[i][0]!= eduSelected){
			newBranchControls[newBranchControls.length]=branchControls[i];
		}
	}
	branchControls = newBranchControls;
	eduSelected = -1;
	
}

function deleteRow(table,rowId){
	for(var i=0;i<table.rows.length;i++){
		if(table.rows[i].id !=""){
			if(table.rows[i].id == rowId){
				Element.remove(table.rows[i]);
			}
		}
	}
}

function addListener(row){
	Event.observe(row, "click", onRowClick.bindAsEventListener(this));
	Event.observe(row, "keydown", onRowKeyDown.bindAsEventListener(this));
	Event.observe(row, "keyup", onRowKeyUp.bindAsEventListener(this));
}

function onRowClick(event){
	var row = Event.findElement(event, 'tr');
	onOptionChange("edu",row.id);
}
function onRowKeyDown(event){
	onRowClick(event);
}
function onRowKeyUp(event){
	onRowClick(event);
}

function onBlurYop(event){
	var el =Event.element(event);
	getFormattedYear(el);
}
function onEduOptClick(event){
	var el =Event.element(event); 
	var level = el.id.substring(4, el.id.length);
	onOptionChange("edu",level);
}

var yrf= new DateFormatter();
yrf.setDisplayFormat('MMM-YYYY');

function getFormattedYear(obj){
	if(obj.value.trim()!=''){
		if(yrf.getDateObject(obj.value)){
			obj.value=yrf.getDateObject(obj.value).getFullYear();
		}else if(yrf.getDateObject('01-'+obj.value)){
			obj.value=yrf.getDateObject('01-'+obj.value).getFullYear();
		}else{
			alert('<bean:message key="common.please_enter"/> <bean:message key="common.year_of_passing"/> <bean:message key="common.in"/> <bean:message key="add_applicant.errors.yearofpassing_format"/> <bean:message key="common.format"/>');
			obj.focus();
			return false;
		}
	}
	return true;
}

//GRID For Skills
function initGridSkills() {	
	dataGridSkills = new dhtmlXGridObject('GRD_SKILLS'); 
	dataGridSkills.imgURL = "images/"; 
	dataGridSkills.setHeader("<bean:message key="common.skills"/>"); 
	dataGridSkills.setInitWidths("150");
	dataGridSkills.setColAlign("left");
	dataGridSkills.setColTypes("ro"); 
	dataGridSkills.setColSorting("Skills_Name_Sort");	
	dataGridSkills.attachEvent("onKeyPress",onGridSkillsKeyPressed);
	dataGridSkills.attachEvent("onRowDblClicked",doOnDataGridSkillsRowDblClicked);
	dataGridSkills.attachEvent("onRowSelect",doOnDataGridSkillsRowSelectHandler);
	dataGridSkills.attachEvent("onXLE",doOnLoadingEndSkills);
	dataGridSkills.init();
	loadGridSkills();	
	
	dataGridSkills.gridToGrid = function(rowId,sgrid,tgrid){
	    var z=new Array();
		for(var i=0;i<sgrid.hdr.rows[0].cells.length;i++)
		z[i]=sgrid.cells(rowId,i).getValue();
		return z;
	}	
	
	dataGridSkillsTo = new dhtmlXGridObject('GRD_SKILLS_TO'); 
	dataGridSkillsTo.imgURL = "images/"; 
	dataGridSkillsTo.setHeader("<bean:message key="common.selected"/> <bean:message key="common.skills"/>"); 
	dataGridSkillsTo.setInitWidths("150");
	dataGridSkillsTo.setColAlign("left");
	dataGridSkillsTo.setColTypes("ro"); 
	dataGridSkillsTo.setColSorting("Skills_Name_Sort_To");	
	dataGridSkillsTo.attachEvent("onKeyPress",onGridSkillsToKeyPressed);
	dataGridSkillsTo.attachEvent("onRowSelect",doOnDataGridSkillsToRowSelectHandler);
	dataGridSkillsTo.attachEvent("onRowDblClicked",doOnDataGridSkillsToRowDblClicked);
	dataGridSkillsTo.init();
	dataGridSkillsTo.sortRows(0,'str',"asc");
	dataGridSkillsTo.setSortImgState(true,0,"ASC");	
	dataGridSkillsTo.gridToGrid = function(rowId,sgrid,tgrid){
	    var z=new Array();
		for(var i=0;i<sgrid.hdr.rows[0].cells.length;i++)
		z[i]=sgrid.cells(rowId,i).getValue();
		return z;
	}	
}
function doOnDataGridSkillsRowDblClicked() {
	selectItem(dataGridSkills,dataGridSkillsTo);
}

function doOnDataGridSkillsToRowDblClicked() {
	selectItem(dataGridSkillsTo,dataGridSkills);
}

function doOnDataGridSkillsRowSelectHandler() {
	dataGridSkillsTo.clearSelection();
}
function doOnDataGridSkillsToRowSelectHandler() {
	dataGridSkills.clearSelection();
}
function Skills_Name_Sort(a,b,order,aId,bId) {
	a0 = dataGridSkills.getUserData(aId,"skillName");
	b0 = dataGridSkills.getUserData(bId,"skillName");	
	return sort_data(a0,b0,order);
}
function Skills_Name_Sort_To(a,b,order,aId,bId) {
	a0 = dataGridSkillsTo.getUserData(aId,"skillName");
	b0 = dataGridSkillsTo.getUserData(bId,"skillName");	
	return sort_data(a0,b0,order);
}
function loadGridSkills(){
	dataGridSkills.clearAll();
	dataGridSkills.loadXML("desktop.do?mode=XMLSkills");
}

function onGridSkillsKeyPressed(keyCode,ctrl,shift) {
	dataGridSkillsTo.clearSelection();
	onGridObjKeyPressed(dataGridSkills,dataGridSkillsTo,4,keyCode,ctrl,shift);
}

function onGridSkillsToKeyPressed(keyCode,ctrl,shift) {
	dataGridSkills.clearSelection();
	onGridObjKeyPressed(dataGridSkillsTo,dataGridSkills,4,keyCode,ctrl,shift);
}

function doOnLoadingEndSkills() {
	dataGridSkills.sortRows(0,'str',"asc");
	dataGridSkills.setSortImgState(true,0,"ASC");
	selectItems("",dataGridSkills,dataGridSkillsTo);
}
//Common Function
function sort_data(a,b,order) {
	if(order=="asc")
		return a.toLowerCase()>b.toLowerCase()?1:-1;
	else
		return a.toLowerCase()<b.toLowerCase()?1:-1;
}

function validNumber(fieldObj){
	//var regExpNumber = /^\d*[0-9]*([,]*\d*[0-9])*\d*[0-9]$/;
	var regExpNumber = '^\d*[0-9]*[.]*\d*[0-9]*\d*[0-9]$';
	var val;		
	if(fieldObj.value.trim()==""){				
		val= true;
	}else if(!fieldObj.value.match(regExpNumber)){		
		alert("<bean:message key="common.please_enter_valid_number" />");
		fieldObj.focus();
		val= false;
	}else{
		val= true;
	}	
	return val;
}

function onWindowLoad(){
	<%if(ImportConfigurationManager.isImportFieldShow(ImportConfigurationConstants.FIELD_SKILLS)){%>
	initGridSkills();
	<%}%>
}

window.onload=onWindowLoad;
</script>