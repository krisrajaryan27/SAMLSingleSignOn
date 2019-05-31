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
</head>
<body>
<div class="contentDivPop" style="width:580px;">
		<s:if test="hasActionErrors()">
			<table id="m_errortable">
				<tr>
					<td class="header" style="padding: 4px">
						Error
					</td>
				</tr>  
				<tr >
				   <td style="padding: 2px">
				    	<s:actionerror />
				    </td>
				</tr>  
			</table>
		</s:if>	
	<div class="outerDiv">
	<s:form name="positionMigrationScreen" method="POST">
	<s:hidden name="groupedStepMappingJSON" />
	<s:hidden name="positionId" />
	<s:hidden name="stepsFor" />
	<s:set var="stepsMigrationView" value="#request['stepsMigrationView']"  />
	<div class="popupTop">
		<table class="tblPop">
			<tr>
				<td class="header">
					Migrate for Position:
				</td>
				<td>
					<s:property value="#request['positionName']" />
				</td>
			</tr>
		</table>	
	</div>	 
	<div class="popupTop">
	<table width="100%" cellpadding="0" cellspacing="0" id="stepsMappingTable" class="tblPop">
	   <tr>
	   		<td colspan="5">
	   			<table width="100%">
	   				<tr>
					  	<td width="100%">
					  		<s:property value="#stepsMigrationView.stepLevelMap[0]" />
						</td>
	   				</tr>
	   			</table>
	   			<div>
	   				<table width="100%">
		   				<s:iterator value="#stepsMigrationView.shortListLst" >
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
								  <s:if test="isScheduled==1">
									<script>									
										var opts = <s:property value="#stepsMigrationView.scheduledMasterStepsJsArray"  />;
										var masterSteps_<s:property value='groupedStepId' /> = new SelectBox(opts,'<s:property value="stepMappingId" />','images/btn_dropdown.gif',{namesonly:false, width:'200px', size:10, textboxclass:''});
										document.write(masterSteps_<s:property value='groupedStepId' />.getHtml());
										masterSteps_<s:property value='groupedStepId' />.init();
									</script>		  	
								  </s:if>
								  <s:elseif test="isScheduled==0">
									  <script>									
										var opts = <s:property value="#stepsMigrationView.nonScheduledMasterStepsJsArray"  />;
										var masterSteps_<s:property value='groupedStepId' /> = new SelectBox(opts,'<s:property value="stepMappingId" />','images/btn_dropdown.gif',{namesonly:false, width:'200px', size:10, textboxclass:''});
										document.write(masterSteps_<s:property value='groupedStepId' />.getHtml());
										masterSteps_<s:property value='groupedStepId' />.init();
									</script>
								  </s:elseif>
								  </td>
		   					</tr>
		   				</s:iterator>
		   			</table>
	   			</div>
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
	   			<div>
	   				<table width="100%">
		   				<s:iterator value="#stepsMigrationView.selectLst" >
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
						  			 <s:if test="isScheduled==1">
										<script>									
											var opts = <s:property value="#stepsMigrationView.scheduledMasterStepsJsArray"  />;
											var masterSteps_<s:property value='groupedStepId' /> = new SelectBox(opts,'<s:property value="stepMappingId" />','images/btn_dropdown.gif',{namesonly:false, width:'200px', size:10, textboxclass:''});
											document.write(masterSteps_<s:property value='groupedStepId' />.getHtml());
											masterSteps_<s:property value='groupedStepId' />.init();
										</script>		  	
									  </s:if>
									  <s:elseif test="isScheduled==0">
										  <script>									
											var opts = <s:property value="#stepsMigrationView.nonScheduledMasterStepsJsArray"  />;
											var masterSteps_<s:property value='groupedStepId' /> = new SelectBox(opts,'<s:property value="stepMappingId" />','images/btn_dropdown.gif',{namesonly:false, width:'200px', size:10, textboxclass:''});
											document.write(masterSteps_<s:property value='groupedStepId' />.getHtml());
											masterSteps_<s:property value='groupedStepId' />.init();
										</script>
									  </s:elseif>						  
								  </td>
		   					</tr>
		   				</s:iterator>
		   			</table>
	   			</div>
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
	   			<div>
	   				<table width="100%">
		   				<s:iterator value="#stepsMigrationView.hireLst" >
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
						  			<s:if test="isScheduled==1">
										<script>									
											var opts = <s:property value="#stepsMigrationView.scheduledMasterStepsJsArray"  />;
											var masterSteps_<s:property value='groupedStepId' /> = new SelectBox(opts,'<s:property value="stepMappingId" />','images/btn_dropdown.gif',{namesonly:false, width:'200px', size:10, textboxclass:''});
											document.write(masterSteps_<s:property value='groupedStepId' />.getHtml());
											masterSteps_<s:property value='groupedStepId' />.init();
										</script>		  	
									  </s:if>
									  <s:elseif test="isScheduled==0">
										  <script>									
											var opts = <s:property value="#stepsMigrationView.nonScheduledMasterStepsJsArray"  />;
											var masterSteps_<s:property value='groupedStepId' /> = new SelectBox(opts,'<s:property value="stepMappingId" />','images/btn_dropdown.gif',{namesonly:false, width:'200px', size:10, textboxclass:''});
											document.write(masterSteps_<s:property value='groupedStepId' />.getHtml());
											masterSteps_<s:property value='groupedStepId' />.init();
										</script>
									  </s:elseif>								  
								  </td>
		   					</tr>
		   				</s:iterator>
		   			</table>
	   			</div>
	   		</td>
	  </tr> 
	</table>
	</div>
	<div class="popupBody">
		<table class="tblPop" width="100%">
			<tr>
				<td>
					<div class="navBtn" style="float: right;">
						<a href="#" style="width:60px;" class="active" onclick="javascript: submitForm();">
							<span class="rightC"></span><span class="leftC"></span>
							<s:text name="common.submit"></s:text>
						</a>
						<a href="#" style="width:60px; margin-left:5px;" class="active" onclick="javascript: window.top.hidePopWin(false);return false;">
							<span class="rightC"></span><span class="leftC"></span>
							<s:text name="common.cancel"></s:text>
						</a>
					</div>
				</td>
			</tr>
		</table>
	</div>
	</s:form>	
</div>
</div>
</body>
<script type="text/javascript">
function submitForm(){
	if(validateMapping()){
		var stepMapppingArray = buildStepMapping();
		if(stepMapppingArray!=null){
			var groupedStepMappingJSON =Object.toJSON(stepMapppingArray);
			document.positionMigrationScreen.groupedStepMappingJSON.value=groupedStepMappingJSON;
			document.positionMigrationScreen.action="migratePosition.action";
			document.positionMigrationScreen.submit();			
		}
	}
}

function buildStepMapping(){
	var stepMapppingArray = new Array();
	$$('#stepsMappingTable tr.stepMappingRow').each(function(mappingRow) {
		var groupedId = mappingRow.id;
		var groupedStepMapping = new GroupedStepMapping();
		groupedId = groupedId.substring(5,groupedId.length);
		groupedStepMapping.setGroupedStepId(groupedId);
		groupedStepMapping.setStepIds(mappingRow.select('input#stepIds')[0].value);
		groupedStepMapping.setStepMappingId(eval('masterSteps_'+groupedId).getSelectedId());
		stepMapppingArray[stepMapppingArray.size()]=groupedStepMapping;
	});
	return stepMapppingArray;
}

function validateMapping(){
	var error = false;
	$$('#stepsMappingTable tr.stepMappingRow').each(function(mappingRow) {
		if(!error){
			var groupedId = mappingRow.id;
			groupedId = groupedId.substring(5,groupedId.length);
			if(eval('masterSteps_'+groupedId).getSelectedId()==-1){
				error = true;
			}
		}
	});
	if(error){
		alert('<s:text name="steps_migration.error_message.mandatory_maping" />');
		return false;
	}else{
		return true;
	}
}
</script>
</html>