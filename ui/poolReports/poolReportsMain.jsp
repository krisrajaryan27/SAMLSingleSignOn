<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ page import="com.talentPool.reports.form.ReportForm, 
				com.talentPool.common.properties.TPApplicationProperties"%>
<%@page import="com.talentPool.common.properties.GlobalApplicationProperties"%>
<%@page import="com.talentPool.common.properties.GlobalConstants"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.talentPool.common.utils.Utils"%>
<%@page import="com.talentPool.reports.ReportConstants"%>
<%@page import="com.talentPool.common.utils.CommonUtils"%>
<%@page import="com.talentPool.reportDesign.dataobject.ReportData"%>
<%@page import="com.talentPool.reportDesign.constants.ReportDesignConstants"%>
<%@page import="com.talentPool.reports.ReportUtils"%>
<%@page import="com.talentPool.reportDesign.utils.FilterUtils"%>
<%@page import="com.talentPool.common.properties.GlobalApplicationProperties,
								com.talentPool.common.properties.GlobalConstants"%>
<script src="js/tpSelectListFunctions.js"></script> 
<script src="js/dhtmlxGrid/dhtmlXCommon.js"></script> 
<script src="js/dhtmlxGrid/dhtmlXGrid.js"></script> 
<script src="js/dhtmlxGrid/dhtmlXGridCell.js"></script>
<script src="js/dhtmlxGrid/dhtmlXGrid_excell_link.js"></script>
<script src="js/dhtmlxGrid/dhtmlXGrid_drag.js"></script>
<script src="js/dhtmlxGrid/dhtmlxgrid_filter.js"></script>
<link rel="stylesheet" type="text/css" href="themes/default/CalendarPopup.css">
<script language="JavaScript" src="js/ajaxfunctions.js" type="text/javascript"></script>	
<script language="JavaScript" src="js/checkboxlist/checkboxlist.js" type="text/javascript"></script>
<SCRIPT LANGUAGE="JavaScript" SRC="js/calender/CalendarPopup.js"></SCRIPT>
<script language="JavaScript" src="js/calender/dateFormatter.js" type="text/javascript"></script>
<script src="js/cookies.js"></script>
<script type="text/javascript">
var selectFilter=null;
var selectOrderBy =null
var selectPosition=null;
var selectSource=null;
var selectEmployeeSource=null;
var selectBoxDepartment=null;
var selectBoxSubDepartment=null;
var selectBoxSubSubDepartment=null;
var selectDateRange=null;
var selectAppointmentDateRange=null;
var selectDepartmentFilter=null;
var selectPositionStage=null;
var selectImportedBy=null;
var selectBoxUsers=null;
var selectBoxExpenseTypes=null;
var selectBoxActivityTypes=null;
var selectBoxActivityByUsers=null;
var selectEntityFilter=null;
var selectBoxGrade=null;
var selectBoxBand= null;
var selectBudgetStatusFilter = null;

var dataGridUsers2=null;
var dataGridExpenseType2 = null;
var dataGridActivityType2 = null;
var dataGridImportedByType2=null;
var dataGridActivityByUser2=null;
</script>
<%

	ReportForm reportForm = (ReportForm)request.getAttribute("reportForm");
	
%>

<html:form action="/reports">
<html:hidden property="reportName"/>
<html:hidden property="t"/>
<html:hidden property="st"/>
<html:hidden property="mode" />
<html:hidden property="reportId"/>
<html:hidden property="filterId"/>
<html:hidden property="positionId"/>
<html:hidden property="departmentId"/>
<html:hidden property="subDepartmentId"/>
<html:hidden property="subDepartmentTitle"/>
<html:hidden property="subSubDepartmentId"/>
<html:hidden property="subSubDepartmentTitle"/>
<html:hidden property="positionTitle"/>
<html:hidden property="departmentTitle"/>
<html:hidden property="sourceId"/>
<html:hidden property="sourceName"/>
<html:hidden property="selectedUserIds" name="reportForm"/>
<html:hidden property="selectedUserNames"  name="reportForm"/>
<html:hidden property="reportType"/>
<html:hidden property="groupBy"/>
<html:hidden property="columns"/>
<html:hidden property="dateRange" name="reportForm"/>
<html:hidden property="appointmentDateRange" name="reportForm"/>
<html:hidden property="selectedExpenseTypes" name="reportForm"/>
<html:hidden property="selectedExpenseTypeNames" name="reportForm"/>
<html:hidden property="selectedActivityTypes" name="reportForm"/>
<html:hidden property="selectedActivityTypeNames" name="reportForm"/>
<html:hidden property="selectedImportedByUsers" name="reportForm"/>
<html:hidden property="selectedImportedByUserNames" name="reportForm"/>
<html:hidden property="selectedActivityByUsers" name="reportForm"/>
<html:hidden property="selectedActivityByUserNames" name="reportForm"/>
<html:hidden property="stages" name="reportForm"/>
<html:hidden property="stageNames" name="reportForm"/>
<html:hidden property="entityTypeId" name="reportForm"/>
<html:hidden property="entityTypeTitle" name="reportForm"/>
<html:hidden property="budgetGradeId" name="reportForm"/>
<html:hidden property="budgetGradeName" name="reportForm"/>
<html:hidden property="budgetBandId" name="reportForm"/>
<html:hidden property="budgetBandName" name="reportForm"/>
<html:hidden property="budgetStatus" name="reportForm"/>
<html:hidden property="budgetStatusTitle" name="reportForm"/>
<bean:define id="reportData" name="reportData" type="ReportData" scope="request"/>
<div class="contentDiv">
   <table width="100%" border="0" cellspacing="0" cellpadding="0"> 
	  <tr> 
	    <td>
	    	<div class="boxTab"><span class="rightC"></span><span class="leftC"></span>
	    		<img src="images/blank_small.gif" align="absmiddle" /><bean:write name="reportData" property="reportName"/>&nbsp;&nbsp;
	    	</div>
	    </td> 
	  </tr> 
   </table> 
   <table class="reportHeader" cellspacing="0" cellpadding="0" border="0"  width="100%">
    <tr>				  			
		<td class="head">
			<b><bean:message key="report.label.filter" /></b>
		</td>
	</tr>
	<tr>				  			
		<td>
			 <table class="innerReport">
			 <logic:equal name="reportData" property="dateFilter" value="<%=ReportDesignConstants.AVAILABLE %>">
			 <tr>
	   			<td>
					<table cellpadding="0" cellspacing="0">
						<tr>
							<td style="width: 150px;">
		                  	 <bean:message key="hiring_efficiency_report.label.date_range" />:&nbsp;
		                  	</td>
		                  	<td> 
		                  	 <script type="text/javascript">
				                    var optDate = <%=ReportUtils.getJSArrayForDateRange()%>;
				                    selectDateRange = new SelectBox(optDate,'<%=ReportConstants.TODAY%>','images/btn_dropdown.gif',{namesonly:false, width:'180px', size:20});
				                    document.write(selectDateRange.getHtml());
				                    selectDateRange.setOnChangeHandler('onDateRangeChange');
				                    selectDateRange.init();
		                  	  </script>
				   			</td>
				   			<td>
								<div id="divSelectDateRange" style="display:none;">
							 		 <table class="innerReport" cellspacing="0" cellpadding="0" border="0" >
						      	 		<tr>
						      	 			<td style="width: 20px;"></td>
						      	 			<td>
			  		        		  			<bean:message key="hiring_efficiency_report.label.from" />:
			  		        					<html:text property="fromDate" styleId="fromDate" size="12" maxlength="10" onblur="getFormattedDate(this);"/><img src="images/ico_cal.gif" onClick="popUpCal.select(document.getElementById('fromDate'),'fromDate','dd/MM/yyyy'); return false;" class="CalImg" name="imgCal" id="imgCal" style="margin-bottom:-3px;"/>
											</td>
											<td style="width: 20px;"></td>
											<td>
				  		          				<bean:message key="hiring_efficiency_report.label.to" />:
				  		          				<html:text property="toDate" styleId="toDate" size="12" maxlength="10" onblur="getFormattedDate(this);"/><img src="images/ico_cal.gif" onClick="popUpCal.select(document.getElementById('toDate'),'toDate','dd/MM/yyyy'); return false;" class="CalImg" name="imgCal" id="imgCal" style="margin-bottom:-3px;"/>
			  		      					</td>
			  		      				</tr>
				   						</table>
								</div>
							</td>
						</tr>
					</table>			
  				  </td>
 				</tr>
 			 </logic:equal>			
 			 <%
 			 String deptFilter = reportData.getDepartmentFilter()+"";
 			 String budgetDeptFilter = reportData.getBudgetDepartmentFilter()+"";
 			 if(deptFilter.equals(ReportDesignConstants.AVAILABLE ) || budgetDeptFilter.equals(ReportDesignConstants.AVAILABLE )) {%>			
					<tr>
	  			 		<td>
							<table cellspacing="0" cellpadding="0" class="innerReport">
							<tr>
								<td style="width: 150px;">
									<%=GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_DEPARTMENT_LEVEL_1) %>:&nbsp;
								</td>
								<td>
									<script type="text/javascript">
										var opts = new Array();
										opts[0] = new SelectOption('<%=ReportConstants.FILTER_ALL_DEPARTMENTS%>','<bean:message key="report.label.filter_all_departments"/>');
										opts[1] = new SelectOption('<%=ReportConstants.FILTER_SPECIFIC_DEPARTMENT%>','<bean:message key="report.label.filter_specific_department"/> <%=GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_DEPARTMENT_LEVEL_1) %>');
										selectDepartmentFilter = new SelectBox(opts,'<%=ReportConstants.FILTER_ALL_DEPARTMENTS%>','images/btn_dropdown.gif',{namesonly:false, width:'180px', size:20});
										document.write(selectDepartmentFilter.getHtml());
										selectDepartmentFilter.setOnChangeHandler('onChangeDepartmentFilter');
										selectDepartmentFilter.init();
									</script>
								</td>
							</tr>
							</table>		  			  
						</td>
					</tr>
					<tr>
						<td>
							<div id="divSelectSpecificDepartment"  style="display:none;">
								<table cellspacing="0" cellpadding="0" class="innerReport">
									<tr>
										<td style="width: 150px;">
											<bean:message key="report.label.select" /> <%=GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_DEPARTMENT_LEVEL_1) %>:&nbsp;
										</td>
										<td>
											<script type="text/javascript">
												var opts = <%=CommonUtils.getListJavaScriptArray(reportForm.getDepartmentIds(),reportForm.getDepartmentNames())%>;
												var m = [new SelectOption('-1','<bean:message key="common.selectlist.default"/>')];
												opts = m.concat(opts);
												selectBoxDepartment = new SelectBox(opts,'','images/btn_dropdown.gif',{namesonly:false, width:'180px', size:15});
												selectBoxDepartment.setOnChangeHandler('onChangeDepartment');
												document.write(selectBoxDepartment.getHtml());
												selectBoxDepartment.init();
											</script>
										</td>
									</tr>
								</table>
								<div id="divSelectSpecificSubDepartment" style="display:none;">
									<table cellspacing="0" cellpadding="0" class="innerReport">
										<tr>
											<td style="width: 150px;">
												<bean:message key="report.label.select" /> <%=GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_DEPARTMENT_LEVEL_2) %>:&nbsp;
											</td>
											<td>
												<script type="text/javascript">
													var opts = [new SelectOption('-1','<bean:message key="common.selectlist.default"/>')];
													selectBoxSubDepartment = new SelectBox(opts,'','images/btn_dropdown.gif',{namesonly:false, width:'180px', size:15});
													selectBoxSubDepartment.setOnChangeHandler('onChangeSubDepartment');
													document.write(selectBoxSubDepartment.getHtml());
													selectBoxSubDepartment.init();
												</script>
											</td>
										</tr>
									</table>
								</div>
								<div id="divSelectSpecificSubSubDepartment" style="display:none;">
									<table cellspacing="0" cellpadding="0" >
										<tr>
											<td style="width: 150px;">
												<bean:message key="report.label.select" /> <%=GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_DEPARTMENT_LEVEL_3) %>:&nbsp;
											</td>
											<td>
											<script type="text/javascript">
												var opts = [new SelectOption('-1','<bean:message key="common.selectlist.default"/>')];
												selectBoxSubSubDepartment = new SelectBox(opts,'','images/btn_dropdown.gif',{namesonly:false, width:'180px', size:15});
												document.write(selectBoxSubSubDepartment.getHtml());
												selectBoxSubSubDepartment.init();
											</script>
											</td>
										</tr>
									</table>
								</div>								
							</div>
						</td>
		 			</tr>
				
				<%} %>		 			
				<logic:equal name="reportData" property="positionFilter" value="<%=ReportDesignConstants.AVAILABLE %>">
  			 	<tr>
  			 		<td>
						<table cellspacing="0" cellpadding="0">
						<tr>
							<td style="width: 150px;">
								<bean:message key="common.position" />:&nbsp;
							</td>
							<td>
								<script type="text/javascript">
									var opts = new Array();
									opts[0] = new SelectOption('<%=ReportConstants.FILTER_ALL_POSITIONS%>','<bean:message key="common.positions_all"/>');
									opts[1] = new SelectOption('<%=ReportConstants.FILTER_OPEN_POSITIONS%>','<bean:message key="common.open_positions"/>');
									opts[2] = new SelectOption('<%=ReportConstants.FILTER_OPEN_AND_ONHOLD_POSITIONS%>','<bean:message key="report.label.filter_open_and_onhold_positions"/>');
									opts[3] = new SelectOption('<%=ReportConstants.FILTER_SPECIFIC_POSITION%>','<bean:message key="common.specific_position"/>');
									selectFilter = new SelectBox(opts,'<%=ReportConstants.FILTER_ALL_POSITIONS%>','images/btn_dropdown.gif',{namesonly:false, width:'180px', size:20});
									document.write(selectFilter.getHtml());
									selectFilter.setOnChangeHandler('onChangeFilter');
									selectFilter.init();
								</script>
							</td>
						</tr>
						</table>		  			  
					</td>
				</tr>
  			 	<tr>
  			 		<td>
						<div id="divSelectSpecificPosition" style="display:none;" >
						<table cellspacing="0" cellpadding="0">
						<tr>
							<td style="width: 150px;">
								<bean:message key="common.select_position" />:&nbsp;
							</td>
							<td>
								<script type="text/javascript">
									var opts = <%=CommonUtils.getListJavaScriptArrayWithProperties((ArrayList)request.getAttribute("positions"), "positionId", "positionTitle")%>;
									var m = [new SelectOption('-1','<bean:message key="common.selectlist.default"/>')];
									opts = m.concat(opts);
									selectPosition = new SelectBox(opts,'','images/btn_dropdown.gif',{namesonly:false, width:'180px', size:20});
									document.write(selectPosition.getHtml());
									selectPosition.init();
								</script>
							</td>
						</tr>
						</table>		  			  
						</div>
					</td>
				</tr>
				</logic:equal> 			
		 		<logic:equal name="reportData" property="stageFilter" value="<%=ReportDesignConstants.AVAILABLE %>">
					<tr>
						<td>
							<table class="innerReport" cellspacing="0" cellpadding="0" border="0"  width="100%">
							<tr>
								<td style="width: 150px;">
									<bean:message key="report.label.position_stages.stage" />:
								</td>
								<td>
							        <script type="text/javascript">
						            	var opts = <%=FilterUtils.getJSArrayForPositionStages()%>;
					                   	var m = [new SelectOption('','<bean:message key="common.selectlist.all"/>')];
					                   	opts = m.concat(opts);				                    
					                   	selectPositionStage = new SelectBox(opts,'','images/btn_dropdown.gif',{namesonly:false, width:'180px', size:20});
								        document.write(selectPositionStage.getHtml());
								        selectPositionStage.init();
							        </script>
							    </td>
							</tr>
							</table>
			 			 </td>
		 			</tr>
				</logic:equal>
				<logic:equal name="reportData" property="sourceFilter" value="<%=ReportDesignConstants.AVAILABLE %>">
					<tr>
						<td>
							<table class="innerReport" cellspacing="0" cellpadding="0" border="0"  width="100%">
								<tr>
									<td style="width: 150px;">
										<bean:message key="report.label.candidate_status.source" />:
									</td>
									<td>
								        <script type="text/javascript">
							            	var opts = <%=CommonUtils.getListJavaScriptArray(CommonUtils.getSourceIdsWithoutEmployeeSource(),CommonUtils.getSourceNamesWithoutEmployeeSource())%>;
						                   	var m = [new SelectOption('','<bean:message key="common.selectlist.all"/>')];
						                   	var n = [new SelectOption('-1','<bean:message key="report.label.candidate_status.employee_referrals"/>')];
						                   	opts = m.concat(opts);			
						                   	opts=opts.concat(n);	                    
									        selectSource = new SelectBox(opts,'','images/btn_dropdown.gif',{namesonly:false, width:'180px', size:20});
									        selectSource.setOnChangeHandler('onChangeSelectSource');
									        document.write(selectSource.getHtml());
									        selectSource.init();
								        </script>
								    </td>		
							    </tr>							    
							</table>
						</td>
					</tr>
					<tr>
						<td>
							<div id="employeeReferralSourcesDiv" style="display: none;">
								<table cellspacing="0" cellpadding="0">
									<tr>		
								    	<td style="width: 150px;">
											<bean:message key="report.label.candidate_status.employee" />:
										</td>			    
									    <td>
									    	<script type="text/javascript">
									            	var opts = <%=CommonUtils.getListJavaScriptArray(CommonUtils.getSourceIdsWithOnlyEmployeeSource(),CommonUtils.getSourceNamesWithOnlyEmployeeSource())%>;
								                   	var m = [new SelectOption('<%=ReportConstants.SOURCE_EMP_REFERRALS_ALL%>','<bean:message key="common.selectlist.all"/>')];
								                   	opts = m.concat(opts);			
											        selectEmployeeSource = new SelectBox(opts,'','images/btn_dropdown.gif',{namesonly:false, width:'180px', size:20});
											        document.write(selectEmployeeSource.getHtml());
											        selectEmployeeSource.init();
									        </script>
									    </td>
									</tr>
								</table>
							</div>
			 			 </td>
		 			</tr>
				</logic:equal>	
				<logic:equal name="reportData" property="importedByFilter" value="<%=ReportDesignConstants.AVAILABLE %>">
					<tr>
	  			 		<td>
							<table cellspacing="0" cellpadding="0">
							<tr>
								<td style="width: 150px;">
									<bean:message key="report.label.importedBy" />:&nbsp;
								</td>
								<td>
									<script type="text/javascript">
										var opts = new Array();
										opts[0] = new SelectOption('','<bean:message key="common.selectlist.all"/>');
										opts[1] = new SelectOption('<%=ReportConstants.FILTER_IMPORTED_BY%>','<bean:message key="report.label.filter_specific_imported_by"/>');
										selectImportedBy = new SelectBox(opts,'','images/btn_dropdown.gif',{namesonly:false, width:'180px', size:20});
										document.write(selectImportedBy.getHtml());
										selectImportedBy.setOnChangeHandler('onChangeImportedByFilter');
										selectImportedBy.init();
									</script>
								</td>
							</tr>
							</table>		  			  
						</td>
					</tr>
		 			<tr>
		 				<td> 	
		 					<div id="divImportedBy" style="display:none;">
			 					<table cellspacing="0" cellpadding="0" border="0" style="padding-bottom: 10px; " class="innerReport" >
			 						<tr>
						 			 	<td valign="top" style="width: 150px;">
				 			     	 		<bean:message key="report.label.select" />&nbsp;<bean:message key="report.label.importedBy" />:
				 			     	 	 </td>
										<td>
											<table cellpadding="0" cellspacing="0"  >
												<tr>
													<td class="gridborder" >
														<div id="GRD_IMPORTED_BY_1" style="width:225px;height: 106px;border-bottom: 1px solid #99cc33;"></div>
													</td>
												</tr>
											</table>							
										</td>
										<td width="34px" align="center">
											<a href="#" onclick="javascript: selectItem(dataGridImportedByType1,dataGridImportedByType2);return false;" title="Add" ><img src="images/ico_rightarrow.gif"  border="0" /></a><br/>
											<a href="#" onclick="javascript: deselectItem(dataGridImportedByType2,dataGridImportedByType1);return false;" title="Remove" ><img src="images/ico_leftarrow.gif"  border="0" style="margin-top: 10px;"/></a> 
										</td>						
										<td>
											<table cellpadding="0" cellspacing="0">
												<tr>
													<td class="gridborder">
														<div id="GRD_IMPORTED_BY_2" style="width:225px;height: 106px;border-bottom: 1px solid #99cc33;"></div>
													</td>
												</tr>
											</table>							
										</td>									
						 			</tr>
				 				</table>
			 				</div>
			 			</td>
		 			</tr>
	 			</logic:equal>	
				<logic:equal name="reportData" property="userFilter" value="<%=ReportDesignConstants.AVAILABLE %>">
					<tr>
	  			 		<td>
							<table cellspacing="0" cellpadding="0">
							<tr>
								<td style="width: 150px;">
									<bean:message key="report.label.users" />:&nbsp;
								</td>
								<td>
									<script type="text/javascript">
										var opts = new Array();
										opts[0] = new SelectOption('','<bean:message key="common.selectlist.all"/>');
										opts[1] = new SelectOption('<%=ReportConstants.FILTER_SPECIFIC_USER%>','<bean:message key="report.label.filter_specific_user"/>');
										selectBoxUsers = new SelectBox(opts,'','images/btn_dropdown.gif',{namesonly:false, width:'180px', size:20});
										document.write(selectBoxUsers.getHtml());
										selectBoxUsers.setOnChangeHandler('onChangeUsersFilter');
										selectBoxUsers.init();
									</script>
								</td>
							</tr>
							</table>		  			  
						</td>
					</tr>
		 			<tr>
		 				<td> 	
		 					<div id="divSelectUsers" style="display:none;">
			 					<table cellspacing="0" cellpadding="0" border="0" style="padding-bottom: 10px; " class="innerReport" >
			 						<tr>
						 			 	<td valign="top" style="width: 150px;">
				 			     	 		<bean:message key="report.label.select" />&nbsp;<bean:message key="report.label.users" />:
				 			     	 	 </td>
										<td>
											<table cellpadding="0" cellspacing="0"  >
												<tr>
													<td class="gridborder" >
														<div id="GRD_USERS_1" style="width:225px;height: 106px;border-bottom: 1px solid #99cc33;"></div>
													</td>
												</tr>
											</table>							
										</td>
										<td width="34px" align="center">
											<a href="#" onclick="javascript: selectItem(dataGridUsers1,dataGridUsers2);return false;" title="Add" ><img src="images/ico_rightarrow.gif"  border="0" /></a><br/>
											<a href="#" onclick="javascript: deselectItem(dataGridUsers2,dataGridUsers1);return false;" title="Remove" ><img src="images/ico_leftarrow.gif"  border="0" style="margin-top: 10px;"/></a> 
										</td>						
										<td>
											<table cellpadding="0" cellspacing="0">
												<tr>
													<td class="gridborder">
														<div id="GRD_USERS_2" style="width:225px;height: 106px;border-bottom: 1px solid #99cc33;"></div>
													</td>
												</tr>
											</table>							
										</td>									
						 			</tr>
				 				</table>
			 				</div>
			 			</td>
		 			</tr>
	 			</logic:equal>				
				<logic:equal name="reportData" property="appointmentDateFilter" value="<%=ReportDesignConstants.AVAILABLE %>">
				 	<tr>
		   				<td>
		   					<table cellspacing="0" cellpadding="0" border="0" >	
						   		<tr>
						   			<td style="width: 150px;">
				                   		<bean:message key="common.label.appointment_date_range" />:&nbsp;
				                   	</td>
				                   	<td>	
					                   <script type="text/javascript">
							                    var optDate = <%=ReportUtils.getJSArrayForDateRangeOfFutureDates()%>;
							                    selectAppointmentDateRange = new SelectBox(optDate,'<%=ReportConstants.TODAY%>','images/btn_dropdown.gif',{namesonly:false, width:'180px', size:20});
							                    document.write(selectAppointmentDateRange.getHtml());
							                    selectAppointmentDateRange.setOnChangeHandler('onAppointmentDateRangeChange');
							                    selectAppointmentDateRange.init();
					                    </script>
							   		</td>
							   		<td>
										<div id="divSelectAppointmentNumRange" style="display:none;">
											 <table class="innerReport" cellspacing="0" cellpadding="0" border="0" >
											     <tr>
											     	<td style="width: 20px;"></td>
											       	<td><bean:message key="report.label.number" />=<html:text name="reportForm" property="appointmentNumberRange" styleId="numberRange" size="4" maxlength="2"/>
													</td>
													<td></td>
													<td></td>
								  		      	 </tr>
						 			   		 </table>
										</div>							
										<div id="divSelectAppointmentDateRange" style="display:none;">
										  <table class="innerReport" cellspacing="0" cellpadding="0" border="0" >
									      	 <tr>
									      	 	<td style="width: 20px;"></td>
									      	 	<td>
						  		        		  	<bean:message key="report.label.from" />:
						  		        			<html:text property="fromAppointmentDate" styleId="fromAppointmentDate" size="12" maxlength="10" onblur="getFormattedDate(this);"/><img src="images/ico_cal.gif" onClick="popUpCal.select(document.getElementById('fromAppointmentDate'),'fromAppointmentDate','dd/MM/yyyy'); return false;" class="CalImg" name="imgCal" id="imgCal" style="margin-bottom:-3px;"/>
												</td>
												<td style="width: 20px;"></td>
												<td>
						  		          			<bean:message key="report.label.to" />:
					  		          			  	<html:text property="toAppointmentDate" styleId="toAppointmentDate" value="" size="12" maxlength="10" onblur="getFormattedDate(this);"/><img src="images/ico_cal.gif" onClick="popUpCal.select(document.getElementById('toAppointmentDate'),'toAppointmentDate','dd/MM/yyyy'); return false;" class="CalImg" name="imgCal" id="imgCal" style="margin-bottom:-3px;"/>
						  		      			</td>
						  		      		</tr>
					 			   		 </table>
										</div>
									</td>
								</tr>
							</table>					
	  				  	</td>
	 				</tr>
	 			 </logic:equal>	 			 	 			 
				<logic:equal name="reportData" property="expenseTypeFilter" value="<%=ReportDesignConstants.AVAILABLE %>">
					<tr>
	  			 		<td>
							<table cellspacing="0" cellpadding="0">
							<tr>
								<td style="width: 150px;">
									<bean:message key="report.label.expenseType" />:&nbsp;
								</td>
								<td>
									<script type="text/javascript">
										var opts = new Array();
										opts[0] = new SelectOption('','<bean:message key="common.selectlist.all"/>');
										opts[1] = new SelectOption('<%=ReportConstants.FILTER_SPECIFIC_EXPENSE_TYPE%>','<bean:message key="report.label.filter_specific_expense_type"/>');
										selectBoxExpenseTypes = new SelectBox(opts,'','images/btn_dropdown.gif',{namesonly:false, width:'180px', size:20});
										document.write(selectBoxExpenseTypes.getHtml());
										selectBoxExpenseTypes.setOnChangeHandler('onChangeExpenseTypeFilter');
										selectBoxExpenseTypes.init();
									</script>
								</td>
							</tr>
							</table>		  			  
						</td>
					</tr>
		 			<tr>
		 				<td> 	
		 					<div id="divSelectExpenseTypes" style="display:none;">
			 					<table cellspacing="0" cellpadding="0" border="0" style="padding-bottom: 10px; " class="innerReport" >
			 						<tr>
						 			 	<td valign="top" style="width: 150px;">
				 			     	 		<bean:message key="report.label.select" />&nbsp;<bean:message key="report.label.expenseType" />:
				 			     	 	 </td>
										<td>
											<table cellpadding="0" cellspacing="0"  >
												<tr>
													<td class="gridborder" >
														<div id="GRD1" style="width:225px;height: 106px;border-bottom: 1px solid #99cc33;"></div>
													</td>
												</tr>
											</table>							
										</td>
										<td width="34px" align="center">
											<a href="#" onclick="javascript: selectItem(dataGridExpenseType1,dataGridExpenseType2);return false;" title="Add" ><img src="images/ico_rightarrow.gif"  border="0" /></a><br/>
											<a href="#" onclick="javascript: deselectItem(dataGridExpenseType2,dataGridExpenseType1);return false;" title="Remove" ><img src="images/ico_leftarrow.gif"  border="0" style="margin-top: 10px;"/></a> 
										</td>						
										<td>
											<table cellpadding="0" cellspacing="0">
												<tr>
													<td class="gridborder">
														<div id="GRD2" style="width:225px;height: 106px;border-bottom: 1px solid #99cc33;"></div>
													</td>
												</tr>
											</table>							
										</td>									
						 			</tr>
				 				</table>
				 			</div>
			 			</td>
		 			</tr>
		 		</logic:equal>		
		 		<logic:equal name="reportData" property="activityByFilter" value="<%=ReportDesignConstants.AVAILABLE %>">
					<tr>
	  			 		<td>
							<table cellspacing="0" cellpadding="0">
							<tr>
								<td style="width: 150px;">
									<bean:message key="report.label.activity_by" />:&nbsp;
								</td>
								<td>
									<script type="text/javascript">
										var opts = new Array();
										opts[0] = new SelectOption('','<bean:message key="common.selectlist.all"/>');
										opts[1] = new SelectOption('<%=ReportConstants.FILTER_SPECIFIC_USER%>','<bean:message key="report.label.filter_specific_user"/>');
										selectBoxActivityByUsers = new SelectBox(opts,'','images/btn_dropdown.gif',{namesonly:false, width:'180px', size:20});
										document.write(selectBoxActivityByUsers.getHtml());
										selectBoxActivityByUsers.setOnChangeHandler('onChangeActivityByUsersFilter');
										selectBoxActivityByUsers.init();
									</script>
								</td>
							</tr>
							</table>		  			  
						</td>
					</tr>
		 			<tr>
		 				<td> 	
		 					<div id="divSelectActivityByUsers" style="display:none;">
			 					<table cellspacing="0" cellpadding="0" border="0" style="padding-bottom: 10px; " class="innerReport" >
			 						<tr>
						 			 	<td valign="top" style="width: 150px;">
				 			     	 		<bean:message key="report.label.select" />&nbsp;<bean:message key="report.label.activity_by" />:
				 			     	 	 </td>
										<td>
											<table cellpadding="0" cellspacing="0"  >
												<tr>
													<td class="gridborder" >
														<div id="GRD_ACTIVITY_BY_1" style="width:225px;height: 106px;border-bottom: 1px solid #99cc33;"></div>
													</td>
												</tr>
											</table>							
										</td>
										<td width="34px" align="center">
											<a href="#" onclick="javascript: selectItem(dataGridActivityByUser1,dataGridActivityByUser2);return false;" title="Add" ><img src="images/ico_rightarrow.gif"  border="0" /></a><br/>
											<a href="#" onclick="javascript: deselectItem(dataGridActivityByUser2,dataGridActivityByUser1);return false;" title="Remove" ><img src="images/ico_leftarrow.gif"  border="0" style="margin-top: 10px;"/></a> 
										</td>						
										<td>
											<table cellpadding="0" cellspacing="0">
												<tr>
													<td class="gridborder">
														<div id="GRD_ACTIVITY_BY_2" style="width:225px;height: 106px;border-bottom: 1px solid #99cc33;"></div>
													</td>
												</tr>
											</table>							
										</td>									
						 			</tr>
				 				</table>
			 				</div>
			 			</td>
		 			</tr>
	 			</logic:equal>	 			
		 		<logic:equal name="reportData" property="activityTypeFilter" value="<%=ReportDesignConstants.AVAILABLE %>">
		 			<tr>
	  			 		<td>
							<table cellspacing="0" cellpadding="0">
							<tr>
								<td style="width: 150px;">
									<bean:message key="report.label.user_activity.interaction" />:&nbsp;
								</td>
								<td>
									<script type="text/javascript">
										var opts = new Array();
										opts[0] = new SelectOption('','<bean:message key="common.selectlist.all"/>');
										opts[1] = new SelectOption('<%=ReportConstants.FILTER_SPECIFIC_ACTIVITY_TYPE%>','<bean:message key="report.label.filter_specific_activity_type"/>');
										selectBoxActivityTypes = new SelectBox(opts,'','images/btn_dropdown.gif',{namesonly:false, width:'180px', size:20});
										document.write(selectBoxActivityTypes.getHtml());
										selectBoxActivityTypes.setOnChangeHandler('onChangeActivityTypeFilter');
										selectBoxActivityTypes.init();
									</script>
								</td>
							</tr>
							</table>		  			  
						</td>
					</tr>
		 			<tr>
		 				<td>
		 					<div id="divSelectActivityTypes" style="display:none;">
			 					<table cellspacing="0" cellpadding="0" border="0" style="padding-bottom: 10px; " class="innerReport" >
			 						<tr>
							 			<td valign="top" style="width: 150px;">
							     	 		<bean:message key="report.label.select" />&nbsp;<bean:message key="report.label.user_activity.interaction" />:  
							     	 	</td>
										<td>
											<table cellpadding="0" cellspacing="0"  >
												<tr>
													<td class="gridborder" >
														<div id="GRD_ACTIVITY_1" style="width:225px;height: 106px;border-bottom: 1px solid #99cc33;"></div>
													</td>
												</tr>
											</table>							
										</td>
										<td width="34px" align="center">
											<a href="#" onclick="javascript: selectItem(dataGridActivityType1,dataGridActivityType2);return false;" title="Add" ><img src="images/ico_rightarrow.gif"  border="0" /></a><br/>
											<a href="#" onclick="javascript: deselectItem(dataGridActivityType2,dataGridActivityType1);return false;" title="Remove" ><img src="images/ico_leftarrow.gif"  border="0" style="margin-top: 10px;"/></a> 
										</td>						
										<td>
											<table cellpadding="0" cellspacing="0">
												<tr>
													<td class="gridborder">
														<div id="GRD_ACTIVITY_2" style="width:225px;height: 106px;border-bottom: 1px solid #99cc33;"></div>
													</td>
												</tr>
											</table>							
										</td>									
		 							</tr>
		 						</table>
		 					</div>
		 				</td>
		 			</tr>
		 		</logic:equal>			 		
		 		<logic:equal name="reportData" property="auditEntityFilter" value="<%=ReportDesignConstants.AVAILABLE %>">
					<tr>
	  			 		<td>
							<table cellspacing="0" cellpadding="0">
							<tr>
								<td style="width: 150px;">
									<bean:message key="report.label.entity" />:&nbsp;
								</td>
								<td>
									 <script type="text/javascript">
						                    var opts = <%=ReportUtils.getJSArrayForAuditEntityTypes()%>;
											selectEntityFilter = new CheckBoxList(opts,'',{namesonly:false, layerclass:'checkboxlistdiv', width:'175px', size:10, imageclass:'chkboxclass', uncheckedimg:'images/checkboxunchecked.gif', checkedimg:'images/checkboxchecked.gif'});
											document.write(selectEntityFilter.getHtml());
											selectEntityFilter.init();
											selectEntityFilter.selectAll(true);
				                    </script>
								</td>
							</tr>
							</table>		  			  
						</td>
					</tr>
		 		</logic:equal>			
		 		<logic:equal name="reportData" property="budgetGradeFilter" value="<%=ReportDesignConstants.AVAILABLE %>">
					<tr>
	  			 		<td>
							<table cellspacing="0" cellpadding="0">
								<tr>
									<td style="width: 150px;"><%=GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_BUDGET_ITEM_GRADE_LABEL) %>:</td>
									<td>
										<script language="JavaScript">									
											var opts = <%=ReportUtils.getJSArrayGrades()%>;								
											var opt = [new SelectOption('-1','All <%=GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_BUDGET_ITEM_GRADE_LABEL) %>s')];
											grades = opt.concat(opts);
											selectBoxGrade = new SelectBox(grades,'','images/btn_dropdown.gif',{namesonly:false, width:'180px', size:15});
											document.write(selectBoxGrade.getHtml());
											selectBoxGrade.init();
										</script>								
									</td>
								</tr>							
							</table>		  			  
						</td>
					</tr>
		 		</logic:equal>	
		 		<logic:equal name="reportData" property="budgetBandFilter" value="<%=ReportDesignConstants.AVAILABLE %>">
					<tr>
	  			 		<td>
							<table cellspacing="0" cellpadding="0">
								<tr>
									<td style="width: 150px;"><%=GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_BUDGET_ITEM_BAND_LABEL) %>:</td>
									<td>
										<script language="JavaScript">									
											var opts = <%=ReportUtils.getJSArrayBands()%>;									
											var opt = [new SelectOption('-1','All <%=GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_BUDGET_ITEM_BAND_LABEL) %>s')];
											bands = opt.concat(opts);
											selectBoxBand = new SelectBox(bands,'','images/btn_dropdown.gif',{namesonly:false, width:'180px', size:15});
											document.write(selectBoxBand.getHtml());
											selectBoxBand.init();
										</script>									
								</tr>
							</table>		  			  
						</td>
					</tr>
		 		</logic:equal>	
		 		<logic:equal name="reportData" property="budgetStatusFilter" value="<%=ReportDesignConstants.AVAILABLE %>">	 	
		 			<tr>
			 			<td>
							<table cellspacing="0" cellpadding="0">
								<tr>
				 				<td style="width: 150px;">
										<bean:message key="common.budget_item" /> <bean:message key="common.status" />:&nbsp;
									</td>
									<td>
										<script type="text/javascript">
											var opts = new Array();
											opts[0] = new SelectOption('-1','<bean:message key="common.all"/> <bean:message key="common.budget_item"/>s');
											opts[1] = new SelectOption('<%=ReportConstants.FILTER_ACTIVE_BUDGET_ITEMS%>','<bean:message key="common.all"/> <bean:message key="common.active"/> <bean:message key="common.budget_item"/>s');
											selectBudgetStatusFilter = new SelectBox(opts,'-1','images/btn_dropdown.gif',{namesonly:false, width:'180px', size:20});
											document.write(selectBudgetStatusFilter.getHtml());
											selectBudgetStatusFilter.init();
										</script>
									</td>
								</tr>
							</table>
						</td>
		 			</tr>		
		 		</logic:equal>	 			
			</table>	
		 </td>
	</tr>
	<tr id="stepsDivHeader">				  			
		<td class="head">
			<b><bean:message key="report.label.select_fields" /></b>
		</td>
	</tr>
	<tr id="stepsDiv">
		<td>
			<table cellpadding="0" cellspacing="0" class="innerReport">
				<tr>
					<td>
						<table cellpadding="0" cellspacing="0" style="padding-left: 6px;">
							<tr>
								<td class="gridborder">
									<div id="STEPS_GRID" style="width:225px;height: 150px;overflow: visible;"></div>
								</td>
							</tr>
						</table>
					</td>
					<td width="34px" align="center">
						<a href="#" onclick="javascript: selectItem(stepsGrid,selectedStepsGrid);return false;" title="Add" ><img src="images/ico_rightarrow.gif"  border="0" /></a><br/>
						<a href="#" onclick="javascript: deselectItem(selectedStepsGrid,stepsGrid);return false;" title="Remove" ><img src="images/ico_leftarrow.gif"  border="0" style="margin-top: 10px;"/></a> 
					</td>
					<td>
						<table cellpadding="0" cellspacing="0">
							<tr>
								<td class="gridborder">
									<div id="SELECTED_STEPS_GRID" style="width:225px;height: 150px;overflow: visible;"></div>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</td>
	</tr>
   </table>
   <br>
   <table cellspacing="0" cellpadding="0" border="0"  width="100%">
   	<tr>
   		<td>  			
   		<div class="navBtn" style="float:left;margin-right:5px;margin-top:5px;">
			<a href="#" style="width:120px;" class="active" onclick="javascript:submitForm();">
			<span class="rightC"></span><span class="leftC"></span><bean:message key="report.label.view_report"/></a>
		</div>	
 		</td>
	</tr>
	</table>	

</div>
</html:form>
<DIV id="calDiv" style="position:absolute;background:#FFFFFF;z-index:1000;" ></DIV>
<script language="JavaScript">

function getAllItemAttributes(gridObject,cellIndex){
	var itemIdsString =  gridObject.getAllRowIds(',');
	if(itemIdsString!= null && itemIdsString.length>0){
		var itemIds = gridObject.getAllRowIds(',').split(',');		
		var itemNames = "";
		for(var i=0;i<itemIds.length;i++)
			itemNames= itemNames +", "+ gridObject.cells(itemIds[i],cellIndex).cell.innerHTML;
		return itemNames.substring(2);
	}
	else 
		return 'All';
}

function getSelectedItemNameForSelectBox(selectBox){

	var index=selectBox.getSelectedIndex();
	if(index == 0){
		return "All";
	}else{
		return selectBox.getText(index);
	}	
}
</script>
<script language="JavaScript">

var popUpCal = new CalendarPopup("calDiv"); 
popUpCal.showNavigationDropdowns();

//DATE FORMATTER CODE AND FUNCTIONS
var dtf = new DateFormatter();
dtf.setDisplayFormat('DD/MM/YYYY');
function getFormattedDate(obj){
	if(obj.value != ''){
		if(!dtf.checkDate(obj)){
			obj.select();
			alert("<bean:message key="report.error.invalid_date"/>");
			obj.focus();
			return false;
		}else {
			return true;
		}
	}
}

function onChangeSelectSource(){
	var srcId = selectSource.getSelectedId();
	if(srcId=='-1'){		
	    	$("employeeReferralSourcesDiv").style.display = 'block';  
	
	}else {    
		$("employeeReferralSourcesDiv").style.display = 'none';
	}
}

function onChangeFilter(val){
	val = selectFilter.getSelectedId();
	
	if(val=="<%=ReportConstants.FILTER_SPECIFIC_POSITION%>"){
		displayHidden("1");		
	}else{
		displayHidden("0");
	}
}

function onChangeDepartmentFilter(val){
	val = selectDepartmentFilter.getSelectedId();
	
	if(val=="<%=ReportConstants.FILTER_SPECIFIC_DEPARTMENT%>"){
		$("divSelectSpecificDepartment").style.display="block";
	}else{
		$("divSelectSpecificDepartment").style.display="none";
	}
}

function onChangeImportedByFilter(){
	val = selectImportedBy.getSelectedId();
	if(val=="<%=ReportConstants.FILTER_IMPORTED_BY%>"){
		$("divImportedBy").style.display="block";
		if(dataGridImportedByType2==null)
			initImportedByGrids();
	}else{
		$("divImportedBy").style.display="none";
	}
}

function onChangeExpenseTypeFilter(){
	val = selectBoxExpenseTypes.getSelectedId();
	if(val=="<%=ReportConstants.FILTER_SPECIFIC_EXPENSE_TYPE%>"){
		$("divSelectExpenseTypes").style.display="block";
		if(dataGridExpenseType2==null)
			initExpenseTypeGrids();
	}else{
		$("divSelectExpenseTypes").style.display="none";
	}
}

function onChangeActivityTypeFilter(){
	val = selectBoxActivityTypes.getSelectedId();
	if(val=="<%=ReportConstants.FILTER_SPECIFIC_ACTIVITY_TYPE%>"){
		$("divSelectActivityTypes").style.display="block";
		if(dataGridActivityType2==null)
			initActivityTypeGrids();
	}else{
		$("divSelectActivityTypes").style.display="none";
	}
}

function onChangeUsersFilter(){
	val = selectBoxUsers.getSelectedId();
	if(val=="<%=ReportConstants.FILTER_SPECIFIC_USER%>"){
		$("divSelectUsers").style.display="block";
		if(dataGridUsers2==null)
			initUserGrids();
	}else{
		$("divSelectUsers").style.display="none";
	}
}

function onChangeActivityByUsersFilter(){
	val = selectBoxActivityByUsers.getSelectedId();
	if(val=="<%=ReportConstants.FILTER_SPECIFIC_USER%>"){
		$("divSelectActivityByUsers").style.display="block";
		if(dataGridActivityByUser2==null)
			initActivityByUserGrids();
	}else{
		$("divSelectActivityByUsers").style.display="none";
	}
}

function displayHidden(val){
	if(val=="0"){
		$("divSelectSpecificPosition").style.display="none";
	}else if(val=="1"){
		$("divSelectSpecificPosition").style.display="block";
	}else if(val=="2"){
		$("divSelectSpecificPosition").style.display="none";
	} 
}

function onDateRangeChange(val){
	customDisplayHidden(selectDateRange.getSelectedId());
}

function customDisplayHidden(val){
	if(val=='<%=ReportConstants.CUSTOM%>'){
		$("divSelectDateRange").style.display="block";
	} else {
		$("divSelectDateRange").style.display="none";
	}  
}

function onAppointmentDateRangeChange(val){
	customAppointmentDateDisplayHidden(selectAppointmentDateRange.getSelectedId());
}

function customAppointmentDateDisplayHidden(val){
	
	if(val=='<%=ReportConstants.CUSTOM%>'){
		$("divSelectAppointmentDateRange").style.display="block";
		$("divSelectAppointmentNumRange").style.display="none";		
	}else if(val=='<%=ReportConstants.NEXT_N_DAYS%>'  || val=='<%=ReportConstants.NEXT_N_WEEKS%>'){
		$("divSelectAppointmentNumRange").style.display="block";
		$("divSelectAppointmentDateRange").style.display="none";	
	}else {
		$("divSelectAppointmentNumRange").style.display="none";
		$("divSelectAppointmentDateRange").style.display="none";
	}  
}

function validateSetFormFields(){
	//first do the validations

	document.reportForm.mode.value = 'runReport';
	document.reportForm.positionId.value='';
	document.reportForm.departmentId.value='';
	document.reportForm.subDepartmentId.value='';
	document.reportForm.subSubDepartmentId.value='';

	if(selectBudgetStatusFilter!=null){
		document.reportForm.budgetStatus.value=selectBudgetStatusFilter.getSelectedId();
		document.reportForm.budgetStatusTitle.value= getSelectedItemNameForSelectBox(selectBudgetStatusFilter);
	}
	//if specific department or position option selected
	if(selectFilter!=null){
		var val = selectFilter.getSelectedId();
		if(val=="<%=ReportConstants.FILTER_SPECIFIC_POSITION%>"){
			if(selectPosition.getSelectedId()=="-1"){
				alert('<bean:message key="common.please_select" /> <bean:message key="common.position" />');
				return false;
			}else{
				document.reportForm.positionTitle.value=selectPosition.getText(selectPosition.getSelectedIndex());
				document.reportForm.positionId.value=selectPosition.getSelectedId();
			}	
		}
		else{
			document.reportForm.positionTitle.value= getSelectedItemNameForSelectBox(selectFilter);
		}
		document.reportForm.filterId.value=val;
	}	

	if(selectDepartmentFilter!=null){
		var val2 = selectDepartmentFilter.getSelectedId();
		if(val2=="<%=ReportConstants.FILTER_SPECIFIC_DEPARTMENT%>"){
			if(selectBoxDepartment.getSelectedId()=="-1"){
				alert("Please select "+'<%=GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_DEPARTMENT_LEVEL_1) %>');
				return false;
			}else{
				document.reportForm.departmentTitle.value=getSelectedItemNameForSelectBox(selectBoxDepartment);
				document.reportForm.departmentId.value=selectBoxDepartment.getSelectedId();
				if(selectBoxSubDepartment.getSelectedId()!="-1"){
					document.reportForm.subDepartmentId.value=selectBoxSubDepartment.getSelectedId();
					document.reportForm.subDepartmentTitle.value=getSelectedItemNameForSelectBox(selectBoxSubDepartment);
				}
				if(selectBoxSubSubDepartment.getSelectedId()!="-1"){
					document.reportForm.subSubDepartmentId.value=selectBoxSubSubDepartment.getSelectedId();
					document.reportForm.subSubDepartmentTitle.value=getSelectedItemNameForSelectBox(selectBoxSubSubDepartment);
				}
			}	
		}
		else{
			document.reportForm.departmentTitle.value=getSelectedItemNameForSelectBox(selectDepartmentFilter);
		}
	}
	
	if(selectSource!=null){
		if(selectSource.getSelectedId()=='-1'){
			document.reportForm.sourceId.value=selectEmployeeSource.getSelectedId();
			document.reportForm.sourceName.value=getSelectedItemNameForSelectBox(selectEmployeeSource);
		}else{
			document.reportForm.sourceId.value=selectSource.getSelectedId();
			document.reportForm.sourceName.value=getSelectedItemNameForSelectBox(selectSource);
		}
	}
	if(selectDateRange!=null){
		document.reportForm.dateRange.value=selectDateRange.getSelectedId();
	}
	if(selectAppointmentDateRange!=null){
		document.reportForm.appointmentDateRange.value=selectAppointmentDateRange.getSelectedId();
	}
	if(selectBoxUsers!=null){
		var val2 = selectBoxUsers.getSelectedId();
		document.reportForm.selectedUserNames.value=getSelectedItemNameForSelectBox(selectBoxUsers);
		document.reportForm.selectedUserIds.value='';
		if(val2=="<%=ReportConstants.FILTER_SPECIFIC_USER%>"){
			if(dataGridUsers2.getAllItemIds(',')==""){
				alert("Please select "+'<bean:message key="report.label.users" />');
				return false;
			}
			else{	
				document.reportForm.selectedUserIds.value=dataGridUsers2.getAllItemIds(',');	
				document.reportForm.selectedUserNames.value=getAllItemAttributes(dataGridUsers2,0);	
			}	
		}
	}
	if(selectBoxExpenseTypes!=null){
		var val2 = selectBoxExpenseTypes.getSelectedId();
		document.reportForm.selectedExpenseTypeNames.value=getSelectedItemNameForSelectBox(selectBoxExpenseTypes);
		document.reportForm.selectedExpenseTypes.value='';
		if(val2=="<%=ReportConstants.FILTER_SPECIFIC_EXPENSE_TYPE%>"){
			if(dataGridExpenseType2.getAllItemIds(',')==""){
				alert("Please select "+'<bean:message key="report.label.expenseType" />');
				return false;
			}
			else{		
				document.reportForm.selectedExpenseTypes.value=dataGridExpenseType2.getAllItemIds(',');		
				document.reportForm.selectedExpenseTypeNames.value=getAllItemAttributes(dataGridExpenseType2,0);
			}	
		}
	}	
	if(selectBoxActivityTypes!=null){
		var val2 = selectBoxActivityTypes.getSelectedId();
		document.reportForm.selectedActivityTypeNames.value=getSelectedItemNameForSelectBox(selectBoxActivityTypes);
		document.reportForm.selectedActivityTypes.value='';
		if(val2=="<%=ReportConstants.FILTER_SPECIFIC_ACTIVITY_TYPE%>"){
			if(dataGridActivityType2.getAllItemIds(',')==""){
				alert("Please select "+'<bean:message key="report.label.user_activity.interaction" />');
				return false;
			}
			else{	
				document.reportForm.selectedActivityTypes.value=dataGridActivityType2.getAllItemIds(',');	
				document.reportForm.selectedActivityTypeNames.value=getAllItemAttributes(dataGridActivityType2,0);
			}	
		}
	}		
	if(selectImportedBy!=null){
		var val2 = selectImportedBy.getSelectedId();
		document.reportForm.selectedImportedByUserNames.value=getSelectedItemNameForSelectBox(selectImportedBy);
		document.reportForm.selectedImportedByUsers.value='';
		if(val2=="<%=ReportConstants.FILTER_IMPORTED_BY%>"){
			if(dataGridImportedByType2.getAllItemIds(',')==""){
				alert("Please select "+'<bean:message key="report.label.importedBy" /> user');
				return false;
			}
			else{	
				document.reportForm.selectedImportedByUsers.value=dataGridImportedByType2.getAllItemIds(',');	
				document.reportForm.selectedImportedByUserNames.value=getAllItemAttributes(dataGridImportedByType2,0);
			}	
		}
	}
	if(selectBoxActivityByUsers!=null){
		var val2 = selectBoxActivityByUsers.getSelectedId();
		document.reportForm.selectedActivityByUserNames.value=getSelectedItemNameForSelectBox(selectBoxActivityByUsers);
		document.reportForm.selectedActivityByUsers.value='';
		if(val2=="<%=ReportConstants.FILTER_SPECIFIC_USER%>"){
			if(dataGridActivityByUser2.getAllItemIds(',')==""){
				alert("Please select "+'<bean:message key="report.label.activity_by" />');
				return false;
			}
			else{		
				document.reportForm.selectedActivityByUsers.value=dataGridActivityByUser2.getAllItemIds(',');	
				document.reportForm.selectedActivityByUserNames.value=getAllItemAttributes(dataGridActivityByUser2,0);	
			}	
		}
	}
	if(selectPositionStage!=null){
		document.reportForm.stages.value=selectPositionStage.getSelectedId();
		document.reportForm.stageNames.value=getSelectedItemNameForSelectBox(selectPositionStage);
	}	
	if(selectEntityFilter!=null){
		var entities = selectEntityFilter.getSelectedIds();		
		if(entities==''){
			alert('<bean:message key="report.message.please_select_entity"/>');
			return false;
		}	
		document.reportForm.entityTypeTitle.value=selectEntityFilter.getSelectedItems(false,true,',');
		document.reportForm.entityTypeId.value=entities;
	}	
	if(selectBoxGrade!=null){
		var entities = selectBoxGrade.getSelectedId();		
		if(entities==''){
			alert('<bean:message key="report.message.please_select_entity"/>');
			return false;
		}	
		document.reportForm.budgetGradeId.value=selectBoxGrade.getSelectedId();
		document.reportForm.budgetGradeName.value=getSelectedItemNameForSelectBox(selectBoxGrade);
	}	
	if(selectBoxBand!=null){
		var entities = selectBoxBand.getSelectedId();		
		if(entities==''){
			alert('<bean:message key="report.message.please_select_entity"/>');
			return false;
		}	
		document.reportForm.budgetBandId.value=selectBoxBand.getSelectedId();
		document.reportForm.budgetBandName.value=getSelectedItemNameForSelectBox(selectBoxBand);
	}		
	return true;
}
function onChangeDepartment(){
  var val = selectBoxDepartment.getSelectedId();
  var pars = "mode=getSubDepartmentJS&departmentId=" + val;
  var myAjax = ajaxCall("department.do",'get',pars,updateSubdepartments, reportError);
}

function updateSubdepartments(request){
 	xmlFile = request.responseXML;
	if(!isValidSession(xmlFile,'<%=TPApplicationProperties.getProperty("redirect_on_session_expired")%>'))return;
	if(!isErrorXml(xmlFile)){
		var op = request.responseText;
		var opts = eval(op);
		var m = [new SelectOption('-1', '<bean:message key='common.selectlist.default' />')];
		opts = m.concat(opts);
		selectBoxSubDepartment.reInitialize(opts, '');
		m = [new SelectOption('-1', '<bean:message key='common.selectlist.default' />')];
	    selectBoxSubSubDepartment.reInitialize(m, '');
		if(opts.length>1){
			Element.show('divSelectSpecificSubDepartment');
		}else{
			Element.hide('divSelectSpecificSubDepartment');
			Element.hide('divSelectSpecificSubSubDepartment');
		}
		
	}	
}
function onChangeSubDepartment(){
  var val = selectBoxSubDepartment.getSelectedId();
  var pars = "mode=getSubDepartmentJS&departmentId=" + val;
  var myAjax = ajaxCall("department.do",'get',pars,updateSubSubdepartments, reportError);
}
function updateSubSubdepartments(request){
 	xmlFile = request.responseXML;
	if(!isValidSession(xmlFile,'<%=TPApplicationProperties.getProperty("redirect_on_session_expired")%>'))return;
	if(!isErrorXml(xmlFile)){
		var op = request.responseText;
		var opts = eval(op);
		var m = [new SelectOption('-1', '<bean:message key='common.selectlist.default' />')];
		opts = m.concat(opts);
		selectBoxSubSubDepartment.reInitialize(opts, '');
		if(opts.length>1){
			Element.show('divSelectSpecificSubSubDepartment');
		}else{
			Element.hide('divSelectSpecificSubSubDepartment');
		}
		
	}	
}

function submitForm(){
	if( validateSetFormFields()){			
		var d = new Date();
		document.reportForm.target=d;
		document.reportForm.submit();
	}
}


function initExpenseTypeGrids() {	
	dataGridExpenseType1 = new dhtmlXGridObject('GRD1'); 
	dataGridExpenseType1.imgURL = "images/"; 
	dataGridExpenseType1.setHeader("<bean:message key="common.expenseTypes"/>"); 
	dataGridExpenseType1.setInitWidths("200");
	dataGridExpenseType1.setColAlign("left");
	dataGridExpenseType1.setColTypes("ro"); 
	dataGridExpenseType1.setColSorting("str");
	dataGridExpenseType1.enableMultiselect(true);	
	dataGridExpenseType1.init();     
	
	dataGridExpenseType1.setSortImgState(true,0,"ASC");
	dataGridExpenseType1.attachEvent("onKeyPress",onGrid1KeyPressed);
	dataGridExpenseType1.attachEvent("onRowDblClicked",doOnGrid1RowDblClicked);
	dataGridExpenseType1.attachEvent("onRowSelect",doOnDataGrid1RowSelectHandler);
	dataGridExpenseType1.attachEvent("onXLE",doOnLoadingEnd);
	
	dataGridExpenseType1.gridToGrid = function(rowId,sgrid,tgrid){
	    var z=new Array();
		for(var i=0;i<sgrid.hdr.rows[0].cells.length;i++)
		z[i]=sgrid.cells(rowId,i).getValue();
		return z;
	}
		
	dataGridExpenseType2 = new dhtmlXGridObject('GRD2'); 
	dataGridExpenseType2.imgURL = "images/"; 
	dataGridExpenseType2.setHeader("<bean:message key="common.selected"/> <bean:message key="common.expenseTypes"/>"); 
	dataGridExpenseType2.setInitWidths("200");
	dataGridExpenseType2.setColAlign("left");
	dataGridExpenseType2.setColTypes("ro"); 
	dataGridExpenseType2.setColSorting("str");	
	dataGridExpenseType2.enableMultiselect(true);	
	dataGridExpenseType2.init();     
	
	dataGridExpenseType2.setSortImgState(true,0,"ASC");
	dataGridExpenseType2.attachEvent("onKeyPress",onGrid2KeyPressed);
	dataGridExpenseType2.attachEvent("onRowDblClicked",doOnGrid2RowDblClicked);
	dataGridExpenseType2.attachEvent("onRowSelect",doOnDataGrid2RowSelectHandler);
	dataGridExpenseType2.gridToGrid = function(rowId,sgrid,tgrid){
	    var z=new Array();
		for(var i=0;i<sgrid.hdr.rows[0].cells.length;i++)
		z[i]=sgrid.cells(rowId,i).getValue();
		return z;
	}

	dataGridExpenseType1.loadXML("reportDesigner.do?mode=getExpenseTypesInXML");	
}

function doOnDataGrid2RowSelectHandler() {
	dataGridExpenseType1.clearSelection();
}

function doOnDataGrid1RowSelectHandler() {
	dataGridExpenseType2.clearSelection();
}

function doOnGrid1RowDblClicked() {
	selectItem(dataGridExpenseType1,dataGridExpenseType2);
}
function doOnGrid2RowDblClicked() {
	deselectItem(dataGridExpenseType2,dataGridExpenseType1);
}

function onGrid1KeyPressed(keyCode,ctrl,shift) {
	dataGridExpenseType2.clearSelection();
	onGridObjKeyPressed(dataGridExpenseType1,dataGridExpenseType2,4,keyCode,ctrl,shift);
}

function onGrid2KeyPressed(keyCode,ctrl,shift) {
	dataGridExpenseType1.clearSelection();
	onGridObjKeyPressed(dataGridExpenseType2,dataGridExpenseType1,4,keyCode,ctrl,shift);
}

function doOnLoadingEnd() {
	//setSelectedFromCookie();
}


function initActivityTypeGrids() {	
	dataGridActivityType1 = new dhtmlXGridObject('GRD_ACTIVITY_1'); 
	dataGridActivityType1.imgURL = "images/"; 
	dataGridActivityType1.setHeader("<bean:message key="common.activityTypes"/>"); 
	dataGridActivityType1.setInitWidths("200");
	dataGridActivityType1.setColAlign("left");
	dataGridActivityType1.setColTypes("ro"); 
	dataGridActivityType1.setColSorting("str");
	dataGridActivityType1.enableMultiselect(true);	
	dataGridActivityType1.init();     
	
	dataGridActivityType1.setSortImgState(true,0,"ASC");
	dataGridActivityType1.attachEvent("onKeyPress",onGridActivityType1KeyPressed);
	dataGridActivityType1.attachEvent("onRowDblClicked",doOnGridActivityType1RowDblClicked);
	dataGridActivityType1.attachEvent("onRowSelect",doOnDataGridActivityType1RowSelectHandler);
	dataGridActivityType1.attachEvent("onXLE",doOnLoadingEnd);
	
	dataGridActivityType1.gridToGrid = function(rowId,sgrid,tgrid){
	    var z=new Array();
		for(var i=0;i<sgrid.hdr.rows[0].cells.length;i++)
		z[i]=sgrid.cells(rowId,i).getValue();
		return z;
	}
		
	dataGridActivityType2 = new dhtmlXGridObject('GRD_ACTIVITY_2'); 
	dataGridActivityType2.imgURL = "images/"; 
	dataGridActivityType2.setHeader("<bean:message key="common.selected"/> <bean:message key="common.activityTypes"/>"); 
	dataGridActivityType2.setInitWidths("200");
	dataGridActivityType2.setColAlign("left");
	dataGridActivityType2.setColTypes("ro"); 
	dataGridActivityType2.setColSorting("str");	
	dataGridActivityType2.enableMultiselect(true);	
	dataGridActivityType2.init();     
	
	dataGridActivityType2.setSortImgState(true,0,"ASC");
	dataGridActivityType2.attachEvent("onKeyPress",onGridActivityType2KeyPressed);
	dataGridActivityType2.attachEvent("onRowDblClicked",doOnGridActivityType2RowDblClicked);
	dataGridActivityType2.attachEvent("onRowSelect",doOnDataGridActivityType2RowSelectHandler);
	dataGridActivityType2.gridToGrid = function(rowId,sgrid,tgrid){
	    var z=new Array();
		for(var i=0;i<sgrid.hdr.rows[0].cells.length;i++)
		z[i]=sgrid.cells(rowId,i).getValue();
		return z;
	}

	dataGridActivityType1.loadXML("reportDesigner.do?mode=getActivityTypesInXML");	


	
}

function doOnDataGridActivityType2RowSelectHandler() {
	dataGridActivityType1.clearSelection();
}

function doOnDataGridActivityType1RowSelectHandler() {
	dataGridActivityType2.clearSelection();
}

function doOnGridActivityType1RowDblClicked() {
	selectItem(dataGridActivityType1,dataGridActivityType2);
}
function doOnGridActivityType2RowDblClicked() {
	deselectItem(dataGridActivityType2,dataGridActivityType1);
}

function onGridActivityType1KeyPressed(keyCode,ctrl,shift) {
	dataGridActivityType2.clearSelection();
	onGridObjKeyPressed(dataGridActivityType1,dataGridActivityType2,4,keyCode,ctrl,shift);
}

function onGridActivityType2KeyPressed(keyCode,ctrl,shift) {
	dataGridActivityType1.clearSelection();
	onGridObjKeyPressed(dataGridActivityType2,dataGridActivityType1,4,keyCode,ctrl,shift);
}

function initUserGrids() {	
	dataGridUsers1 = new dhtmlXGridObject('GRD_USERS_1'); 
	dataGridUsers1.imgURL = "images/"; 
	dataGridUsers1.setHeader("<bean:message key="common.users"/>"); 
	dataGridUsers1.setInitWidths("200");
	dataGridUsers1.setColAlign("left");
	dataGridUsers1.setColTypes("ro"); 
	dataGridUsers1.setColSorting("str");
	dataGridUsers1.enableMultiselect(true);	
	dataGridUsers1.init();     
	
	dataGridUsers1.setSortImgState(true,0,"ASC");
	dataGridUsers1.attachEvent("onKeyPress",onGridUsers1KeyPressed);
	dataGridUsers1.attachEvent("onRowDblClicked",doOnGridUsers1RowDblClicked);
	dataGridUsers1.attachEvent("onRowSelect",doOndataGridUsers1RowSelectHandler);
	dataGridUsers1.attachEvent("onXLE",doOnLoadingEnd);
	
	dataGridUsers1.gridToGrid = function(rowId,sgrid,tgrid){
	    var z=new Array();
		for(var i=0;i<sgrid.hdr.rows[0].cells.length;i++)
		z[i]=sgrid.cells(rowId,i).getValue();
		return z;
	}
		
	dataGridUsers2 = new dhtmlXGridObject('GRD_USERS_2'); 
	dataGridUsers2.imgURL = "images/"; 
	dataGridUsers2.setHeader("<bean:message key="common.selected"/> <bean:message key="common.users"/>"); 
	dataGridUsers2.setInitWidths("200");
	dataGridUsers2.setColAlign("left");
	dataGridUsers2.setColTypes("ro"); 
	dataGridUsers2.setColSorting("str");	
	dataGridUsers2.enableMultiselect(true);	
	dataGridUsers2.init();     
	
	dataGridUsers2.setSortImgState(true,0,"ASC");
	dataGridUsers2.attachEvent("onKeyPress",onGridUsers2KeyPressed);
	dataGridUsers2.attachEvent("onRowDblClicked",doOnGridUsers2RowDblClicked);
	dataGridUsers2.attachEvent("onRowSelect",doOndataGridUsers2RowSelectHandler);
	dataGridUsers2.gridToGrid = function(rowId,sgrid,tgrid){
	    var z=new Array();
		for(var i=0;i<sgrid.hdr.rows[0].cells.length;i++)
		z[i]=sgrid.cells(rowId,i).getValue();
		return z;
	}

	dataGridUsers1.loadXML("reportDesigner.do?mode=getUsersInXML");	
	
}

function doOndataGridUsers2RowSelectHandler() {
	dataGridUsers1.clearSelection();
}

function doOndataGridUsers1RowSelectHandler() {
	dataGridUsers2.clearSelection();
}

function doOnGridUsers1RowDblClicked() {
	selectItem(dataGridUsers1,dataGridUsers2);
}
function doOnGridUsers2RowDblClicked() {
	deselectItem(dataGridUsers2,dataGridUsers1);
}

function onGridUsers1KeyPressed(keyCode,ctrl,shift) {
	dataGridUsers2.clearSelection();
	onGridObjKeyPressed(dataGridUsers1,dataGridUsers2,4,keyCode,ctrl,shift);
}

function onGridUsers2KeyPressed(keyCode,ctrl,shift) {
	dataGridUsers1.clearSelection();
	onGridObjKeyPressed(dataGridUsers2,dataGridUsers1,4,keyCode,ctrl,shift);
}

function initImportedByGrids() {	
	dataGridImportedByType1 = new dhtmlXGridObject('GRD_IMPORTED_BY_1'); 
	dataGridImportedByType1.imgURL = "images/"; 
	dataGridImportedByType1.setHeader("<bean:message key="common.importedByUsers"/>"); 
	dataGridImportedByType1.setInitWidths("200");
	dataGridImportedByType1.setColAlign("left");
	dataGridImportedByType1.setColTypes("ro"); 
	dataGridImportedByType1.setColSorting("str");
	dataGridImportedByType1.enableMultiselect(true);	
	dataGridImportedByType1.init();     
	
	dataGridImportedByType1.setSortImgState(true,0,"ASC");
	dataGridImportedByType1.attachEvent("onKeyPress",onGridImportedBy1KeyPressed);
	dataGridImportedByType1.attachEvent("onRowDblClicked",doOnGridImportedBy1RowDblClicked);
	dataGridImportedByType1.attachEvent("onRowSelect",doOndataGridImportedByType1RowSelectHandler);
	dataGridImportedByType1.attachEvent("onXLE",doOnLoadingEnd);
	
	dataGridImportedByType1.gridToGrid = function(rowId,sgrid,tgrid){
	    var z=new Array();
		for(var i=0;i<sgrid.hdr.rows[0].cells.length;i++)
		z[i]=sgrid.cells(rowId,i).getValue();
		return z;
	}
		
	dataGridImportedByType2 = new dhtmlXGridObject('GRD_IMPORTED_BY_2'); 
	dataGridImportedByType2.imgURL = "images/"; 
	dataGridImportedByType2.setHeader("<bean:message key="common.selected"/> <bean:message key="common.importedByUsers"/>"); 
	dataGridImportedByType2.setInitWidths("200");
	dataGridImportedByType2.setColAlign("left");
	dataGridImportedByType2.setColTypes("ro"); 
	dataGridImportedByType2.setColSorting("str");	
	dataGridImportedByType2.enableMultiselect(true);	
	dataGridImportedByType2.init();     
	
	dataGridImportedByType2.setSortImgState(true,0,"ASC");
	dataGridImportedByType2.attachEvent("onKeyPress",onGridImportedBy2KeyPressed);
	dataGridImportedByType2.attachEvent("onRowDblClicked",doOnGridImportedBy2RowDblClicked);
	dataGridImportedByType2.attachEvent("onRowSelect",doOndataGridImportedByType2RowSelectHandler);
	dataGridImportedByType2.gridToGrid = function(rowId,sgrid,tgrid){
	    var z=new Array();
		for(var i=0;i<sgrid.hdr.rows[0].cells.length;i++)
		z[i]=sgrid.cells(rowId,i).getValue();
		return z;
	}

	dataGridImportedByType1.loadXML("reportDesigner.do?mode=getUsersInXML");	
	
}

function doOndataGridImportedByType2RowSelectHandler() {
	dataGridImportedByType1.clearSelection();
}

function doOndataGridImportedByType1RowSelectHandler() {
	dataGridImportedByType2.clearSelection();
}

function doOnGridImportedBy1RowDblClicked() {
	selectItem(dataGridImportedByType1,dataGridImportedByType2);
}
function doOnGridImportedBy2RowDblClicked() {
	deselectItem(dataGridImportedByType2,dataGridImportedByType1);
}

function onGridImportedBy1KeyPressed(keyCode,ctrl,shift) {
	dataGridImportedByType2.clearSelection();
	onGridObjKeyPressed(dataGridImportedByType1,dataGridImportedByType2,4,keyCode,ctrl,shift);
}

function onGridImportedBy2KeyPressed(keyCode,ctrl,shift) {
	dataGridImportedByType1.clearSelection();
	onGridObjKeyPressed(dataGridImportedByType2,dataGridImportedByType1,4,keyCode,ctrl,shift);
}

function initActivityByUserGrids() {	
	dataGridActivityByUser1 = new dhtmlXGridObject('GRD_ACTIVITY_BY_1'); 
	dataGridActivityByUser1.imgURL = "images/"; 
	dataGridActivityByUser1.setHeader("<bean:message key="report.label.activity_by"/>"); 
	dataGridActivityByUser1.setInitWidths("200");
	dataGridActivityByUser1.setColAlign("left");
	dataGridActivityByUser1.setColTypes("ro"); 
	dataGridActivityByUser1.setColSorting("str");
	dataGridActivityByUser1.enableMultiselect(true);	
	dataGridActivityByUser1.init();     
	
	dataGridActivityByUser1.setSortImgState(true,0,"ASC");
	dataGridActivityByUser1.attachEvent("onKeyPress",onGridActivityBy1KeyPressed);
	dataGridActivityByUser1.attachEvent("onRowDblClicked",doOnGridActivityBy1RowDblClicked);
	dataGridActivityByUser1.attachEvent("onRowSelect",doOnDataGridActivityBy1RowSelectHandler);
	dataGridActivityByUser1.attachEvent("onXLE",doOnLoadingEnd);
	
	dataGridActivityByUser1.gridToGrid = function(rowId,sgrid,tgrid){
	    var z=new Array();
		for(var i=0;i<sgrid.hdr.rows[0].cells.length;i++)
		z[i]=sgrid.cells(rowId,i).getValue();
		return z;
	}
		
	dataGridActivityByUser2 = new dhtmlXGridObject('GRD_ACTIVITY_BY_2'); 
	dataGridActivityByUser2.imgURL = "images/"; 
	dataGridActivityByUser2.setHeader("<bean:message key="common.selected"/> <bean:message key="report.label.activity_by"/>"); 
	dataGridActivityByUser2.setInitWidths("200");
	dataGridActivityByUser2.setColAlign("left");
	dataGridActivityByUser2.setColTypes("ro"); 
	dataGridActivityByUser2.setColSorting("str");	
	dataGridActivityByUser2.enableMultiselect(true);	
	dataGridActivityByUser2.init();     
	
	dataGridActivityByUser2.setSortImgState(true,0,"ASC");
	dataGridActivityByUser2.attachEvent("onKeyPress",onGridActivityBy2KeyPressed);
	dataGridActivityByUser2.attachEvent("onRowDblClicked",doOnGridActivityBy2RowDblClicked);
	dataGridActivityByUser2.attachEvent("onRowSelect",doOnDataGridActivityBy2RowSelectHandler);
	dataGridActivityByUser2.gridToGrid = function(rowId,sgrid,tgrid){
	    var z=new Array();
		for(var i=0;i<sgrid.hdr.rows[0].cells.length;i++)
		z[i]=sgrid.cells(rowId,i).getValue();
		return z;
	}

	dataGridActivityByUser1.loadXML("reportDesigner.do?mode=getUsersInXML");	
	
}

function doOnDataGridActivityBy2RowSelectHandler() {
	dataGridActivityByUser1.clearSelection();
}

function doOnDataGridActivityBy1RowSelectHandler() {
	dataGridActivityByUser2.clearSelection();
}

function doOnGridActivityBy1RowDblClicked() {
	selectItem(dataGridActivityByUser1,dataGridActivityByUser2);
}
function doOnGridActivityBy2RowDblClicked() {
	deselectItem(dataGridActivityByUser2,dataGridActivityByUser1);
}

function onGridActivityBy1KeyPressed(keyCode,ctrl,shift) {
	dataGridActivityByUser2.clearSelection();
	onGridObjKeyPressed(dataGridActivityByUser1,dataGridActivityByUser2,4,keyCode,ctrl,shift);
}

function onGridActivityBy2KeyPressed(keyCode,ctrl,shift) {
	dataGridActivityByUser1.clearSelection();
	onGridObjKeyPressed(dataGridActivityByUser2,dataGridActivityByUser1,4,keyCode,ctrl,shift);
}

function getCustomTitle(item){
	return item.cell.value;
}


/**
 * Select Steps Grid Related Functions 
 */
var stepsGrid = null;
var selectedStepsGrid = null;

function initStepsGrid() {	
	stepsGrid = new dhtmlXGridObject('STEPS_GRID'); 
	stepsGrid.imgURL = "images/"; 
	stepsGrid.setHeader("<bean:message key="common.fields"/>");
	stepsGrid.setInitWidths("200");
	stepsGrid.setColAlign("left");
	stepsGrid.setColTypes("ro"); 
	stepsGrid.enableMultiselect(true);	
	stepsGrid.attachEvent("onXLE",doOnStepsGridLoadingEnd);	     
	stepsGrid.attachEvent("onKeyPress",onStepsGridKeyPressed);
	stepsGrid.attachEvent("onRowDblClicked",doOnStepsGridRowDblClicked);
	stepsGrid.attachEvent("onRowSelect",doOnStepsGridRowSelectHandler);
	stepsGrid.init();
	loadStepsGrid();
	//stepsGrid.setSortImgState(true,0,"ASC");
	
	stepsGrid.gridToGrid = function(rowId,sgrid,tgrid){
	    var z=new Array();
		for(var i=0;i<sgrid.hdr.rows[0].cells.length;i++)
		z[i]=sgrid.cells(rowId,i).getValue();
		return z;
	}
}

function initSelectedStepsGrid(){
	selectedStepsGrid = new dhtmlXGridObject('SELECTED_STEPS_GRID'); 
	selectedStepsGrid.imgURL = "images/"; 
	selectedStepsGrid.setHeader("<bean:message key="common.selected"/> <bean:message key="common.fields"/>");
	selectedStepsGrid.setInitWidths("200");
	selectedStepsGrid.setColAlign("left");
	selectedStepsGrid.setColTypes("ro"); 
	selectedStepsGrid.enableMultiselect(true);	
	selectedStepsGrid.init();     
	
	selectedStepsGrid.attachEvent("onKeyPress",onSelectedStepsGridPressed);
	selectedStepsGrid.attachEvent("onRowDblClicked",doOnSelectedStepsGridRowDblClicked);
	selectedStepsGrid.attachEvent("onRowSelect",doOnSelectedFieldsRowSelectHandler);
	selectedStepsGrid.gridToGrid = function(rowId,sgrid,tgrid){
	    var z=new Array();
		for(var i=0;i<sgrid.hdr.rows[0].cells.length;i++)
		z[i]=sgrid.cells(rowId,i).getValue();
		return z;
	}
}

function onStepsGridKeyPressed(keyCode,ctrl,shift) {	
	selectedStepsGrid.clearSelection();
	onGridObjKeyPressed(stepsGrid,selectedStepsGrid,4,keyCode,ctrl,shift);
}



function onSelectedStepsGridPressed(keyCode,ctrl,shift) {
	stepsGrid.clearSelection();
	onGridObjKeyPressed(selectedStepsGrid,stepsGrid,4,keyCode,ctrl,shift);
}

function doOnStepsGridRowDblClicked() {	
	selectItem(stepsGrid,selectedStepsGrid);
}

function doOnSelectedStepsGridRowDblClicked() {	
	selectItem(selectedStepsGrid,stepsGrid);
}

function doOnStepsGridRowSelectHandler() {
	selectedStepsGrid.clearSelection();
}

function doOnSelectedFieldsRowSelectHandler() {
	stepsGrid.clearSelection();
}

function loadStepsGrid(){
	stepsGrid.parse('<%=Utils.escapeJavaScript((String) request.getAttribute("fieldsXml"))%>');
}

function doOnStepsGridLoadingEnd(){
	setSelectedFromCookie();
}

function setSelectedFromCookie(){
	var vals = readCookie('<bean:message key="pool.report.label.title"/>');
	if(vals!='' && vals!=null){
		var arrVals = vals.split(',');
		for(var x=0;x<arrVals.length;x++){
			stepsGrid.setSelectedRow(arrVals[x],true,false,false);
		}
		selectItem(stepsGrid,selectedStepsGrid);
	}
}

function doOnload(){
	initSelectedStepsGrid();
	initStepsGrid();
}

window.onLoad = doOnload();
</script>