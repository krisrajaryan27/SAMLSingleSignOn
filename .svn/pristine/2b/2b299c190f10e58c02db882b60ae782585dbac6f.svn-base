<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<script src="js/scripta/lib/prototype.js" type="text/javascript"></script>								
<script src="js/scripta/src/effects.js" type="text/javascript"></script>
<script src="js/selectoption.js" type="text/javascript"></script>
<script src="js/selectbox/selectbox.js" type="text/javascript"></script>
<script src="stepsMigration/js/GroupedStepMapping.js" type="text/javascript"></script>
<style type="text/css">
tr.stepsTable {
	border-left: 1px solid #ccc;
	border-right: 1px solid #ccc;
	border-top: 1px solid #ccc;
}
tr.stepsTable td {
	font-family: Verdana, Arial, Helvetica, sans-serif;
	font-size: 11px;
	font-weight:normal;
	color:#666666;
	background-Color:#D0E4A3;
	border: 0px solid;
	border-color : white white white white;
	text-align: left;
	margin:0px;
	padding:0px 4px 0px 0px ;
	font-weight:normal;
    -moz-user-select:none;
	-moz-user-select:-moz-none;    
    overflow:hidden;
    height:25px;
    empty-cells:show;
}
</style>
</head>
<body>
<s:form name="stepsMigrationWizard" method="POST">
<s:hidden name="migrationId" />
<s:hidden name="stepsFor" />
<s:hidden name="groupedStepMappingJSON" />
<div class="contentDiv">
	<div class="boxTab" style="width:140px;"><span class="rightC"></span><span class="leftC"></span>Step Master Mapping</div>
	<div class="outerDiv" >
		<table class="posinput" width="100%" style="padding: 4px"> 
		  <tr>
		  	  <td class="label" width="150px;">
				  <s:label key="steps_migration.label.steps_for" />
			  </td>
			  <td>
		  		<script>									
					var opts = <s:property value="#request['stepsForJSArray']"  /> ;
					var stepsFor = new SelectBox(opts,'<s:property value="stepsFor" />','images/btn_dropdown.gif',{namesonly:false, width:'250px', size:10, textboxclass:''});
					document.write(stepsFor.getHtml());
					stepsFor.setOnChangeHandler('groupAllStepsForMigration');
					stepsFor.init();
				</script>						  
			  </td>
		  </tr> 
		</table>
		<s:if test="#request['stepsMigrationView']!=null">
			<s:set var="stepsMigrationView" value="#request['stepsMigrationView']"  />
			
			<table width="100%" cellpadding="0" cellspacing="0" id="stepsMappingTable"> 
			  <tr class="stepsTable" >
			  	 <td width="3%">
					  &nbsp;
				  </td>
			  	  <td width="24%">
			  	  	<s:label key="common.stage" />
				  </td>
				  <td width="35%" >
					  <s:label key="common.step" />
				  </td>
				  <td width="38%">
				  	<s:label key="steps_migration.header.mapping" />
				  </td>
			  </tr> 
			   <tr>
			   		<td colspan="5">
			   			<table width="100%">
			   				<tr>
							  	<td width="100%">
							  		<s:property value="#stepsMigrationView.stepLevelMap[0]" />
								</td>
			   				</tr>
			   			</table>
			   			<s:if test="#stepsMigrationView.shortListScheduled.size>0">
			   			<table width="100%">
			   				<tr>
							  	<td width="100%">
							  		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label key="steps_migration.text.schedulable" />
								</td>
			   				</tr>
			   			</table>
			   			<div>
			   				<table width="100%">
				   				<s:iterator value="#stepsMigrationView.shortListScheduled" >
				   					<tr id="step_<s:property value='groupedStepId' />" class="stepMappingRow" >
			   						  	 <td width="3%">
											  &nbsp;
										  </td>
									  	  <td width="24%">
											  &nbsp;
										  </td>
										  <td width="35%" >
										  	<s:property value="stepName" />
										  	<s:hidden  id="stepIds"  name="stepIds"/>
										  </td>
										  <td width="38%">
								  			<script>									
												var opts = <s:property value="#stepsMigrationView.scheduledMasterStepsJsArray"  /> ;
												var masterSteps_<s:property value='groupedStepId' /> = new SelectBox(opts,'<s:property value="stepMappingId" />','images/btn_dropdown.gif',{namesonly:false, width:'250px', size:10, textboxclass:''});
												document.write(masterSteps_<s:property value='groupedStepId' />.getHtml());
												masterSteps_<s:property value='groupedStepId' />.init();
											</script>							  
										  </td>
				   					</tr>
				   				</s:iterator>
				   			</table>
			   			</div>
			   			</s:if>
			   			<s:if test="#stepsMigrationView.shortListNonScheduled.size>0">
			   			<table width="100%">
			   				<tr>
							  	<td width="100%">
							  		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label key="steps_migration.text.non_schedulable" />
								</td>
			   				</tr>
			   			</table>
			   			<div>
			   				<table width="100%">
				   				<s:iterator value="#stepsMigrationView.shortListNonScheduled" >
					   				<tr id="step_<s:property value='groupedStepId' />" class="stepMappingRow" >
			   						  	 <td width="3%">
											  &nbsp;
										  </td>
									  	  <td width="24%">
											  &nbsp;
										  </td>
										  <td width="35%" >
											 <s:property value="stepName" />
											 <s:hidden  id="stepIds"  name="stepIds"/>
										  </td>
										  <td width="38%">
								  			<script>									
												var opts = <s:property value="#stepsMigrationView.nonScheduledMasterStepsJsArray"  /> ;
												var masterSteps_<s:property value='groupedStepId' /> = new SelectBox(opts,'<s:property value="stepMappingId" />','images/btn_dropdown.gif',{namesonly:false, width:'250px', size:10, textboxclass:''});
												document.write(masterSteps_<s:property value='groupedStepId' />.getHtml());
												masterSteps_<s:property value='groupedStepId' />.init();
											</script>							  
										  </td>
					   				</tr>
					   			</s:iterator>
				   			</table>
			   			</div>
			   			</s:if>
			   		</td>
			  </tr> 
			  <tr>
			   		<td colspan="5">
			   			<table width="100%">
			   				<tr>
							  	<td width="100%">
							  		<s:property value="#stepsMigrationView.stepLevelMap[1]" />
								</td>
			   				</tr>
			   			</table>
			   			<s:if test="#stepsMigrationView.selectScheduled.size>0">
			   			<table width="100%">
			   				<tr>
							  	<td width="100%">
							  		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label key="steps_migration.text.schedulable" />
								</td>
			   				</tr>
			   			</table>
			   			<div>
			   				<table width="100%">
				   				<s:iterator value="#stepsMigrationView.selectScheduled" >
				   					<tr id="step_<s:property value='groupedStepId' />" class="stepMappingRow" >
			   						  	 <td width="3%">
											  &nbsp;
										  </td>
									  	  <td width="24%">
											  &nbsp;
										  </td>
										  <td width="35%" >
										  	<s:property value="stepName" />
										  	<s:hidden  id="stepIds"  name="stepIds"/>
										  </td>
										  <td width="38%">
								  			<script>									
												var opts = <s:property value="#stepsMigrationView.scheduledMasterStepsJsArray"  /> ;
												var masterSteps_<s:property value='groupedStepId' /> = new SelectBox(opts,'<s:property value="stepMappingId" />','images/btn_dropdown.gif',{namesonly:false, width:'250px', size:10, textboxclass:''});
												document.write(masterSteps_<s:property value='groupedStepId' />.getHtml());
												masterSteps_<s:property value='groupedStepId' />.init();
											</script>							  
										  </td>
				   					</tr>
				   				</s:iterator>
				   			</table>
			   			</div>
			   			</s:if>
			   			<s:if test="#stepsMigrationView.selectNonScheduled.size>0">
			   			<table width="100%">
			   				<tr>
							  	<td width="100%">
							  		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label key="steps_migration.text.non_schedulable" />
								</td>
			   				</tr>
			   			</table>
			   			<div>
			   				<table width="100%">
				   				<s:iterator value="#stepsMigrationView.selectNonScheduled" >
					   				<tr id="step_<s:property value='groupedStepId' />" class="stepMappingRow" >
			   						  	 <td width="3%">
											  &nbsp;
										  </td>
									  	  <td width="24%">
											  &nbsp;
										  </td>
										  <td width="35%" >
											 <s:property value="stepName" />
											 <s:hidden  id="stepIds"  name="stepIds"/>
										  </td>
										  <td width="38%">
								  			<script>									
												var opts = <s:property value="#stepsMigrationView.nonScheduledMasterStepsJsArray"  /> ;
												var masterSteps_<s:property value='groupedStepId' /> = new SelectBox(opts,'<s:property value="stepMappingId" />','images/btn_dropdown.gif',{namesonly:false, width:'250px', size:10, textboxclass:''});
												document.write(masterSteps_<s:property value='groupedStepId' />.getHtml());
												masterSteps_<s:property value='groupedStepId' />.init();
											</script>							  
										  </td>
					   				</tr>
					   			</s:iterator>
				   			</table>
			   			</div>
			   			</s:if>
			   		</td>
			  </tr> 
			  <tr>
			   		<td colspan="5">
			   			<table width="100%">
			   				<tr>
							  	<td width="100%">
							  		<s:property value="#stepsMigrationView.stepLevelMap[2]" />
								</td>
			   				</tr>
			   			</table>
			   			<s:if test="#stepsMigrationView.hireScheduled.size>0">
			   			<table width="100%">
			   				<tr>
							  	<td width="100%">
							  		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label key="steps_migration.text.schedulable" />
								</td>
			   				</tr>
			   			</table>
			   			<div>
			   				<table width="100%">
				   				<s:iterator value="#stepsMigrationView.hireScheduled" >
				   					<tr id="step_<s:property value='groupedStepId' />"  class="stepMappingRow" >
			   						  	 <td width="3%">
											  &nbsp;
										  </td>
									  	  <td width="24%">
											  &nbsp;
										  </td>
										  <td width="35%" >
										  	<s:property value="stepName" />
										  	<s:hidden  id="stepIds"  name="stepIds"/>
										  </td>
										  <td width="38%">
								  			<script>									
												var opts = <s:property value="#stepsMigrationView.scheduledMasterStepsJsArray"  /> ;
												var masterSteps_<s:property value='groupedStepId' /> = new SelectBox(opts,'<s:property value="stepMappingId" />','images/btn_dropdown.gif',{namesonly:false, width:'250px', size:10, textboxclass:''});
												document.write(masterSteps_<s:property value='groupedStepId' />.getHtml());
												masterSteps_<s:property value='groupedStepId' />.init();
											</script>							  
										  </td>
				   					</tr>
				   				</s:iterator>
				   			</table>
			   			</div>
			   			</s:if>
			   			<s:if test="#stepsMigrationView.hireNonScheduled.size>0">
			   			<table width="100%">
			   				<tr>
							  	<td width="100%">
							  		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label key="steps_migration.text.non_schedulable" />
								</td>
			   				</tr>
			   			</table>
			   			<div>
			   				<table width="100%">
				   				<s:iterator value="#stepsMigrationView.hireNonScheduled" >
					   				<tr id="step_<s:property value='groupedStepId' />" class="stepMappingRow" >
			   						  	 <td width="3%">
											  &nbsp;
										  </td>
									  	  <td width="24%">
											  &nbsp;
										  </td>
										  <td width="35%" >
											 <s:property value="stepName" />
											 <s:hidden id="stepIds"  name="stepIds"/>
										  </td>
										  <td width="38%">
								  			<script>									
												var opts = <s:property value="#stepsMigrationView.nonScheduledMasterStepsJsArray"  /> ;
												var masterSteps_<s:property value='groupedStepId' /> = new SelectBox(opts,'<s:property value="stepMappingId" />','images/btn_dropdown.gif',{namesonly:false, width:'250px', size:10, textboxclass:''});
												document.write(masterSteps_<s:property value='groupedStepId' />.getHtml());
												masterSteps_<s:property value='groupedStepId' />.init();
											</script>							  
										  </td>
					   				</tr>
					   			</s:iterator>
				   			</table>
			   			</div>
			   			</s:if>
			   		</td>
			  </tr> 
			</table>
		</s:if>
	</div>
	<div class="navBtn" style="margin-top: 5px;">
			<a href="#" style="width:80px; margin-left:5px;float: left;" class="active" onclick="javascript: resetMigration();return false;"><span class="rightC"></span><span class="leftC"></span><s:text name="common.cancel"/></a>
			<s:if test="stepsFor!='' && stepsFor!=-1 && stepsFor!=null">
				<a href="#" style="width:80px; margin-left:5px;float: right;" class="active" onclick="javascript: validateStepMapping();return false;"><span class="rightC"></span><span class="leftC"></span><s:text name="common.next"/></a>
			</s:if>
	</div> 
</div>
</s:form>
</body>
<script type="text/javascript">

function groupAllStepsForMigration(){
	if(stepsFor.getSelectedId()!=-1){
		document.stepsMigrationWizard.action="groupAllStepsForMigration.action";
		document.stepsMigrationWizard.stepsFor.value=stepsFor.getSelectedId();
		document.stepsMigrationWizard.submit();
	}
}

function validateStepMapping(){
	if(stepsFor.getSelectedId()!=-1){
		var stepMapppingArray = buildStepMapping();
		var groupedStepMappingJSON =Object.toJSON(stepMapppingArray);
		document.stepsMigrationWizard.groupedStepMappingJSON.value=groupedStepMappingJSON;
		document.stepsMigrationWizard.action="validateMigration.action";
		document.stepsMigrationWizard.submit();	
	}
}

function buildStepMapping(){
	var stepMapppingArray = new Array();
	$$('#stepsMappingTable tr.stepMappingRow').each(function(mappingRow) {
		var groupedStepMapping = new GroupedStepMapping();
		var groupedId = mappingRow.id;
		groupedId = groupedId.substring(5,groupedId.length);
		groupedStepMapping.setGroupedStepId(groupedId);
		groupedStepMapping.setStepIds(mappingRow.select('input#stepIds')[0].value);
		groupedStepMapping.setStepMappingId(eval('masterSteps_'+groupedId).getSelectedId());
		stepMapppingArray[stepMapppingArray.size()]=groupedStepMapping; 
	});
	return stepMapppingArray;
}

function goBack(){
	document.stepsMigrationWizard.action="stepsMigrationStatus.action";
	document.stepsMigrationWizard.submit();
}

function resetMigration(){
	document.stepsMigrationWizard.action="cancelOrResetMigration.action";
	document.stepsMigrationWizard.submit();
}

</script>
</html>