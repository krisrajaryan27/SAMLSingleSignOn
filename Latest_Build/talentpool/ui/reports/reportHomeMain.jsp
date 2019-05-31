<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@page import="com.talentPool.reports.ReportVersionConstants,
				com.talentPool.user.UserConstants"%>
<%@page import="java.util.BitSet"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.talentPool.reportDesign.dataobject.ReportData"%>
<%@page import="com.talentPool.customReports.dataobject.CustomReportDetails"%>
<%@page import="com.talentPool.reports.dataobject.CustomizedReportData"%>

<% 
BitSet reportBitSet = (BitSet)request.getSession(false).getAttribute("reportBitSet");
ArrayList customReports = (ArrayList)request.getAttribute("customReports");
%>
<div class="contentDiv">
<br>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td valign="top">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<% if(ReportVersionConstants.isReportInCategoryAvailable(reportBitSet, ReportVersionConstants.CATEGORY_REPORT_STATUS)){ %> 
				<tr> 
				    <td height="20"><strong class="Grey"><bean:message key="report.label.report_type.status" /></strong></td> 
				</tr> 
				<% } %>
				<% if(ReportVersionConstants.isReportAvailable(reportBitSet,ReportVersionConstants.REPORT_HIRING_STATUS)){ %> 
			  	<tr>
			  		<td height="18"><span class="greenBullet">&raquo;</span>&nbsp;<a href="#" class="green" onclick="gotoReport('<%=ReportVersionConstants.REPORT_HIRING_STATUS%>');" ><%=ReportVersionConstants.getReportTitle(ReportVersionConstants.REPORT_HIRING_STATUS)%></a><br/></td>
			  	</tr>
				<% } %>
				<% if(ReportVersionConstants.isReportAvailable(reportBitSet,ReportVersionConstants.REPORT_HIRING_FUNNEL)){ %> 
			  	<tr>
					<td height="18"><span class="greenBullet">&raquo;</span>&nbsp;<a href="#" class="green" onclick="gotoReport('<%=ReportVersionConstants.REPORT_HIRING_FUNNEL %>');"><%=ReportVersionConstants.getReportTitle(ReportVersionConstants.REPORT_HIRING_FUNNEL)%></a><br/></td>
				</tr>	
				<% } %>
				<% if(ReportVersionConstants.isReportAvailable(reportBitSet,ReportVersionConstants.REPORT_POSITION_SUMMARY)){ %> 
				<tr>
					<td height="18"><span class="greenBullet">&raquo;</span>&nbsp;<a href="#" class="green" onclick="gotoReport('<%=ReportVersionConstants.REPORT_POSITION_SUMMARY %>');"><%=ReportVersionConstants.getReportTitle(ReportVersionConstants.REPORT_POSITION_SUMMARY)%></a><br/></td>
				</tr>
				<% } %>
				<% if(ReportVersionConstants.isReportAvailable(reportBitSet,ReportVersionConstants.REPORT_PENDING_ACTION)){ %> 
				<tr>					
					<td height="18"><span class="greenBullet">&raquo;</span>&nbsp;<a href="#" class="green" onclick="gotoReport('<%=ReportVersionConstants.REPORT_PENDING_ACTION %>');"><%=ReportVersionConstants.getReportTitle(ReportVersionConstants.REPORT_PENDING_ACTION)%></a><br/></td>
				</tr>
				<% } %>
				<%-- Start: Candidate Report Summary --%>
				<% if(ReportVersionConstants.isReportAvailable(reportBitSet,ReportVersionConstants.REPORT_CANDIDATE_STATUS)){ %> 
				<tr>					
					<td height="18"><span class="greenBullet">&raquo;</span>&nbsp;<a href="#" class="green" onclick="gotoReport('<%=ReportVersionConstants.REPORT_CANDIDATE_STATUS%>');"><%=ReportVersionConstants.getReportTitle(ReportVersionConstants.REPORT_CANDIDATE_STATUS)%></a><br/></td>
				</tr>				
				<% } %>
				<%-- End: Candidate Report Summary --%>
				<% if(ReportVersionConstants.isReportAvailable(reportBitSet,ReportVersionConstants.REPORT_PENDING_OFFER)){ %> 
				<tr>					
					<td height="18"><span class="greenBullet">&raquo;</span>&nbsp;<a href="#" class="green" onclick="gotoReport('<%=ReportVersionConstants.REPORT_PENDING_OFFER%>');"><%=ReportVersionConstants.getReportTitle(ReportVersionConstants.REPORT_PENDING_OFFER)%></a><br/></td>
				</tr>				
				<% } %>
				<% if(ReportVersionConstants.isReportAvailable(reportBitSet,ReportVersionConstants.REPORT_REJECTED_CANDIDATES)){ %> 
				<tr>					
					<td height="18"><span class="greenBullet">&raquo;</span>&nbsp;<a href="#" class="green" onclick="gotoReport('<%=ReportVersionConstants.REPORT_REJECTED_CANDIDATES%>');"><%=ReportVersionConstants.getReportTitle(ReportVersionConstants.REPORT_REJECTED_CANDIDATES)%></a><br/></td>
				</tr>				
				<% } %>
				<% if(ReportVersionConstants.isReportAvailable(reportBitSet,ReportVersionConstants.REPORT_JOINER)){ %> 
				<tr>					
					<td height="18"><span class="greenBullet">&raquo;</span>&nbsp;<a href="#" class="green" onclick="gotoReport('<%=ReportVersionConstants.REPORT_JOINER%>');"><%=ReportVersionConstants.getReportTitle(ReportVersionConstants.REPORT_JOINER)%></a><br/></td>
				</tr>				
				<% } %>
				<% if(ReportVersionConstants.isReportAvailable(reportBitSet,ReportVersionConstants.REPORT_JOINED)){ %> 
				<tr>					
					<td height="18"><span class="greenBullet">&raquo;</span>&nbsp;<a href="#" class="green" onclick="gotoReport('<%=ReportVersionConstants.REPORT_JOINED%>');"><%=ReportVersionConstants.getReportTitle(ReportVersionConstants.REPORT_JOINED)%></a><br/></td>
				</tr>				
				<% } %>
				<% if(ReportVersionConstants.isReportAvailable(reportBitSet,ReportVersionConstants.REPORT_BLACKLISTED)){ %> 
				<tr>					
					<td height="18"><span class="greenBullet">&raquo;</span>&nbsp;<a href="#" class="green" onclick="gotoReport('<%=ReportVersionConstants.REPORT_BLACKLISTED%>');"><%=ReportVersionConstants.getReportTitle(ReportVersionConstants.REPORT_BLACKLISTED)%></a><br/></td>
				</tr>				
				<% } %>
				<% if(ReportVersionConstants.isReportAvailable(reportBitSet,ReportVersionConstants.REPORT_ALL_CANDIDATES)){ %> 
				<tr>					
					<td height="18"><span class="greenBullet">&raquo;</span>&nbsp;<a href="#" class="green" onclick="gotoReport('<%=ReportVersionConstants.REPORT_ALL_CANDIDATES%>');"><%=ReportVersionConstants.getReportTitle(ReportVersionConstants.REPORT_ALL_CANDIDATES)%></a><br/></td>
				</tr>				
				<% } %>
			</table>
		</td>
		<td valign="top">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<% if(ReportVersionConstants.isReportInCategoryAvailable(reportBitSet, ReportVersionConstants.CATEGORY_REPORT_ANALYSIS)){ %> 
				<tr> 
			    	<td height="20"><strong class="Grey"><bean:message key="report.label.report_type.analysis" /></strong></td> 
			  	</tr>
				<% }%>
				<%if(ReportVersionConstants.isReportAvailable(reportBitSet,ReportVersionConstants.MASTER_REPORT)){ %>
				<tr>
					<td height="18"><span class="greenBullet">&raquo;</span>&nbsp;<a href="#" class="green" onclick="gotoReport('<%=ReportVersionConstants.MASTER_REPORT%>');" ><%=ReportVersionConstants.getReportTitle(ReportVersionConstants.MASTER_REPORT)%></a><br/></td>
				</tr>
				<%}%>
				<%if(ReportVersionConstants.isReportAvailable(reportBitSet,ReportVersionConstants.MASTER_REPORT)){ %>
				<tr>
					<td height="18"><span class="greenBullet">&raquo;</span>&nbsp;<a href="#" class="green" onclick="gotoReport('<%=ReportVersionConstants.MASTER_DATA_REPORT%>');" ><%=ReportVersionConstants.getReportTitle(ReportVersionConstants.MASTER_DATA_REPORT)%></a><br/></td>
				</tr>
				<%}%>
				<% if(ReportVersionConstants.isReportAvailable(reportBitSet,ReportVersionConstants.REPORT_HIRING_EFICIENCY)){ %> 
			  	<tr>
			  		<td height="18"><span class="greenBullet">&raquo;</span>&nbsp;<a href="#" class="green" onclick="gotoReport('<%=ReportVersionConstants.REPORT_HIRING_EFICIENCY%>');" ><%=ReportVersionConstants.getReportTitle(ReportVersionConstants.REPORT_HIRING_EFICIENCY)%></a><br/></td>
			  	</tr>
				<% } %>
				<% if(ReportVersionConstants.isReportAvailable(reportBitSet,ReportVersionConstants.REPORT_SOURCE_WISE_HIRING)){ %> 
			  	<tr>
					<td height="18"><span class="greenBullet">&raquo;</span>&nbsp;<a href="#" class="green" onclick="gotoReport('<%=ReportVersionConstants.REPORT_SOURCE_WISE_HIRING%>');" ><%=ReportVersionConstants.getReportTitle(ReportVersionConstants.REPORT_SOURCE_WISE_HIRING)%></a><br/></td>
			  	</tr>
				<% } %>
				<% if(ReportVersionConstants.isReportAvailable(reportBitSet,ReportVersionConstants.REPORT_MONTHLY_JOINING_REPORT)){ %> 
			  	<tr>
					<td height="18"><span class="greenBullet">&raquo;</span>&nbsp;<a href="#" class="green" onclick="gotoReport('<%=ReportVersionConstants.REPORT_MONTHLY_JOINING_REPORT %>');"><%=ReportVersionConstants.getReportTitle(ReportVersionConstants.REPORT_MONTHLY_JOINING_REPORT)%></a><br/></td>
				</tr>
				<% } %>
				<% if(ReportVersionConstants.isReportAvailable(reportBitSet,ReportVersionConstants.REPORT_SOURCEWISE_IMPORT)){ %> 
				<tr>		
					<td height="18"><span class="greenBullet">&raquo;</span>&nbsp;<a href="#" class="green" onclick="gotoReport('<%=ReportVersionConstants.REPORT_SOURCEWISE_IMPORT %>');"><%=ReportVersionConstants.getReportTitle(ReportVersionConstants.REPORT_SOURCEWISE_IMPORT)%></a><br/></td>
				</tr>
				<% } %>
				<% if(ReportVersionConstants.isReportAvailable(reportBitSet,ReportVersionConstants.REPORT_OFFER_TO_JOINED)){ %> 
				<tr>		
					<td height="18"><span class="greenBullet">&raquo;</span>&nbsp;<a href="#" class="green" onclick="gotoReport('<%=ReportVersionConstants.REPORT_OFFER_TO_JOINED %>');"><%=ReportVersionConstants.getReportTitle(ReportVersionConstants.REPORT_OFFER_TO_JOINED)%></a><br/></td>
				</tr>
				<% } %>
				<% if(ReportVersionConstants.isReportAvailable(reportBitSet,ReportVersionConstants.REPORT_OFFER_CTC)){ %> 
				<tr>		
					<td height="18"><span class="greenBullet">&raquo;</span>&nbsp;<a href="#" class="green" onclick="gotoReport('<%=ReportVersionConstants.REPORT_OFFER_CTC %>');"><%=ReportVersionConstants.getReportTitle(ReportVersionConstants.REPORT_OFFER_CTC)%></a><br/></td>
				</tr>
				<% } %>
				<% if(ReportVersionConstants.isReportAvailable(reportBitSet,ReportVersionConstants.ONE_STOP_FILE_REPORT)){ %> 
				<tr>		
					<td height="18"><span class="greenBullet">&raquo;</span>&nbsp;<a href="#" class="green" onclick="gotoReport('<%=ReportVersionConstants.ONE_STOP_FILE_REPORT %>');"><%=ReportVersionConstants.getReportTitle(ReportVersionConstants.ONE_STOP_FILE_REPORT)%></a><br/></td>
				</tr>
				<% } %>
				<% if(ReportVersionConstants.isReportAvailable(reportBitSet,ReportVersionConstants.REPORT_OFFER_TO_JOINED_DETAILED)){ %> 
				<tr>		
					<td height="18"><span class="greenBullet">&raquo;</span>&nbsp;<a href="#" class="green" onclick="gotoReport('<%=ReportVersionConstants.REPORT_OFFER_TO_JOINED_DETAILED %>');"><%=ReportVersionConstants.getReportTitle(ReportVersionConstants.REPORT_OFFER_TO_JOINED_DETAILED)%></a><br/></td>
				</tr>
				<% } %>
				<% if(ReportVersionConstants.isReportAvailable(reportBitSet,ReportVersionConstants.REPORT_CANDIDATE_COMPARISON)){ %> 
				<tr>		
					<td height="18"><span class="greenBullet">&raquo;</span>&nbsp;<a href="#" class="green" onclick="gotoReport('<%=ReportVersionConstants.REPORT_CANDIDATE_COMPARISON %>');"><%=ReportVersionConstants.getReportTitle(ReportVersionConstants.REPORT_CANDIDATE_COMPARISON)%></a><br/></td>
				</tr>
				<% } %>
				<% if(ReportVersionConstants.isReportAvailable(reportBitSet,ReportVersionConstants.REPORT_RECRUITMENT_COST)){ %> 
				<tr>
					<td height="18"><span class="greenBullet">&raquo;</span>&nbsp;<a href="#" class="green" onclick="gotoReport('<%=ReportVersionConstants.REPORT_RECRUITMENT_COST %>');"><%=ReportVersionConstants.getReportTitle(ReportVersionConstants.REPORT_RECRUITMENT_COST)%></a><br/></td>
				</tr>
				<% } %>
				<% if(ReportVersionConstants.isReportAvailable(reportBitSet,ReportVersionConstants.REPORT_TIME_TO_HIRE)){ %> 
				<tr>					
					<td height="18"><span class="greenBullet">&raquo;</span>&nbsp;<a href="#" class="green" onclick="gotoReport('<%=ReportVersionConstants.REPORT_TIME_TO_HIRE %>');"><%=ReportVersionConstants.getReportTitle(ReportVersionConstants.REPORT_TIME_TO_HIRE)%></a><br/></td>
				</tr>
				<% } %>
				<% if(ReportVersionConstants.isReportAvailable(reportBitSet,ReportVersionConstants.REPORT_DATEWISE_HIRING_REPORT)){ %> 
				<tr>					
					<td height="18"><span class="greenBullet">&raquo;</span>&nbsp;<a href="#" class="green" onclick="gotoReport('<%=ReportVersionConstants.REPORT_DATEWISE_HIRING_REPORT %>');"><%=ReportVersionConstants.getReportTitle(ReportVersionConstants.REPORT_DATEWISE_HIRING_REPORT)%></a><br/></td>
				</tr>
				<% } %>
				<% if(ReportVersionConstants.isReportAvailable(reportBitSet,ReportVersionConstants.REPORT_CLOSED_POSITION_TAT)){ %> 
				<tr>					
					<td height="18"><span class="greenBullet">&raquo;</span>&nbsp;<a href="#" class="green" onclick="gotoReport('<%=ReportVersionConstants.REPORT_CLOSED_POSITION_TAT %>');"><%=ReportVersionConstants.getReportTitle(ReportVersionConstants.REPORT_CLOSED_POSITION_TAT)%></a><br/></td>
				</tr>
				<% } %>
				<% if(ReportVersionConstants.isReportAvailable(reportBitSet,ReportVersionConstants.COST_SHEET_FINANCE_REPORT)){ %> 
				<tr>		
					<td height="18"><span class="greenBullet">&raquo;</span>&nbsp;<a href="#" class="green" onclick="gotoReport('<%=ReportVersionConstants.COST_SHEET_FINANCE_REPORT %>');"><%=ReportVersionConstants.getReportTitle(ReportVersionConstants.COST_SHEET_FINANCE_REPORT)%></a><br/></td>
				</tr>
				<% } %>
				<% if(ReportVersionConstants.isReportAvailable(reportBitSet,ReportVersionConstants.JOINER_DATA_FINANCE_REPORT)){ %> 
				<tr>		
					<td height="18"><span class="greenBullet">&raquo;</span>&nbsp;<a href="#" class="green" onclick="gotoReport('<%=ReportVersionConstants.JOINER_DATA_FINANCE_REPORT %>');"><%=ReportVersionConstants.getReportTitle(ReportVersionConstants.JOINER_DATA_FINANCE_REPORT)%></a><br/></td>
				</tr>
				<% } %>
				<% if(ReportVersionConstants.isReportAvailable(reportBitSet,ReportVersionConstants.INDIA_HIRING_REQ_REPORT)){ %> 
				<tr>		
					<td height="18"><span class="greenBullet">&raquo;</span>&nbsp;<a href="#" class="green" onclick="gotoReport('<%=ReportVersionConstants.INDIA_HIRING_REQ_REPORT %>');"><%=ReportVersionConstants.getReportTitle(ReportVersionConstants.INDIA_HIRING_REQ_REPORT)%></a><br/></td>
				</tr>
				<% } %>
				<% if(ReportVersionConstants.isReportAvailable(reportBitSet,ReportVersionConstants.INDIA_HIRING_SUMMARY_REPORT)){ %> 
				<tr>		
					<td height="18"><span class="greenBullet">&raquo;</span>&nbsp;<a href="#" class="green" onclick="gotoReport('<%=ReportVersionConstants.INDIA_HIRING_SUMMARY_REPORT %>');"><%=ReportVersionConstants.getReportTitle(ReportVersionConstants.INDIA_HIRING_SUMMARY_REPORT)%></a><br/></td>
				</tr>
				<% } %>
				
			</table>		
		</td>
	</tr>
	<tr>
		<td colspan="2" height="20">&nbsp;</td>
	</tr>
	<tr>
		<td valign="top">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<% if(ReportVersionConstants.isReportInCategoryAvailable(reportBitSet, ReportVersionConstants.CATEGORY_REPORT_OPERATIONS)){ %> 
				<tr> 
				  <td height="20"><strong class="Grey"><bean:message key="report.label.report_type.operations" /></strong></td> 
				</tr>
				<% } %>
				<% if(ReportVersionConstants.isReportAvailable(reportBitSet,ReportVersionConstants.REPORT_CALL_LIST)){ %> 
			  	<tr>
			  		<td height="18"><span class="greenBullet">&raquo;</span>&nbsp;<a href="#" class="green" onclick="gotoReport('<%=ReportVersionConstants.REPORT_CALL_LIST%>');" ><%=ReportVersionConstants.getReportTitle(ReportVersionConstants.REPORT_CALL_LIST)%></a><br/></td>
			 	</tr> 
				<% } %>
				<% if(ReportVersionConstants.isReportAvailable(reportBitSet,ReportVersionConstants.REPORT_INTERVIEW_LIST)){ %> 
			  	<tr>
					<td height="18"><span class="greenBullet">&raquo;</span>&nbsp;<a href="#" class="green" onclick="gotoReport('<%=ReportVersionConstants.REPORT_INTERVIEW_LIST %>');"><%=ReportVersionConstants.getReportTitle(ReportVersionConstants.REPORT_INTERVIEW_LIST)%></a><br/></td>
				</tr>
				<% } %>
				<% if(ReportVersionConstants.isReportAvailable(reportBitSet,ReportVersionConstants.REPORT_INTERVIEW_STATUS)){ %> 
			  	<tr>
					<td height="18"><span class="greenBullet">&raquo;</span>&nbsp;<a href="#" class="green" onclick="gotoReport('<%=ReportVersionConstants.REPORT_INTERVIEW_STATUS%>');"><%=ReportVersionConstants.getReportTitle(ReportVersionConstants.REPORT_INTERVIEW_STATUS)%></a><br/></td>
				</tr>
				<% } %>
				<% if(ReportVersionConstants.isReportAvailable(reportBitSet,ReportVersionConstants.REPORT_APPLICANT_DETAILS)){ %> 
				<tr>
					<td height="18"><span class="greenBullet">&raquo;</span>&nbsp;<a href="#" class="green" onclick="gotoReport('<%=ReportVersionConstants.REPORT_APPLICANT_DETAILS%>');" ><%=ReportVersionConstants.getReportTitle(ReportVersionConstants.REPORT_APPLICANT_DETAILS)%></a><br/></td>
  				</tr>
				<% } %>
			</table>		
		</td>
		<td valign="top">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<% if(ReportVersionConstants.isReportInCategoryAvailable(reportBitSet, ReportVersionConstants.CATEGORY_REPORT_ACTIVITY)){ %> 
				<tr> 
				  	<td height="20"><strong class="Grey"><bean:message key="report.label.report_type.activity" /></strong></td> 
				</tr>
				<% } %>
				<% if(ReportVersionConstants.isReportAvailable(reportBitSet,ReportVersionConstants.REPORT_IMPORT_REPORT)){ %> 
				<tr>
					<td height="18"><span class="greenBullet">&raquo;</span>&nbsp;<a href="#" class="green" onclick="gotoReport('<%=ReportVersionConstants.REPORT_IMPORT_REPORT%>');" ><%=ReportVersionConstants.getReportTitle(ReportVersionConstants.REPORT_IMPORT_REPORT)%></a><br/></td>
			  	</tr>
				<% } %>
				<% if(ReportVersionConstants.isReportAvailable(reportBitSet,ReportVersionConstants.REPORT_USER_ACTIVITY)){ %> 
			  	<tr>
					<td height="18"><span class="greenBullet">&raquo;</span>&nbsp;<a href="#" class="green" onclick="gotoReport('<%=ReportVersionConstants.REPORT_USER_ACTIVITY %>');"><%=ReportVersionConstants.getReportTitle(ReportVersionConstants.REPORT_USER_ACTIVITY)%></a><br/></td>
				</tr>
				<% } %>
				<% if(ReportVersionConstants.isReportAvailable(reportBitSet,ReportVersionConstants.REPORT_POSITION_ACTIVITY)){ %> 
				<tr>
					<td height="18"><span class="greenBullet">&raquo;</span>&nbsp;<a href="#" class="green" onclick="gotoReport('<%=ReportVersionConstants.REPORT_POSITION_ACTIVITY %>');"><%=ReportVersionConstants.getReportTitle(ReportVersionConstants.REPORT_POSITION_ACTIVITY)%></a><br/></td>
				</tr>	
				<% } %>
				<% if(ReportVersionConstants.isReportAvailable(reportBitSet,ReportVersionConstants.REPORT_HIRING_ACTIVITY)){ %> 
				<tr>
					<td height="18"><span class="greenBullet">&raquo;</span>&nbsp;<a href="#" class="green" onclick="gotoReport('<%=ReportVersionConstants.REPORT_HIRING_ACTIVITY %>');"><%=ReportVersionConstants.getReportTitle(ReportVersionConstants.REPORT_HIRING_ACTIVITY)%></a><br/></td>
				</tr>	
				<% } %>
			</table>		
		</td>
	</tr>
	
	<% if(ReportVersionConstants.isCustomReportAvailable()){ %>
	<tr>
		<td colspan="2" height="20">&nbsp;</td>
	</tr>
	<tr>
		<td valign="top">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr> 
				  	<td height="10"></td> 
				</tr>     
				<tr> 
				  	<td height="20"><strong class="Grey"><bean:message key="report.label.report_type.custom" /></strong></td> 
				</tr>
				<tr>
					<td height="18"><span class="greenBullet">&raquo;</span>&nbsp;<a href="#" class="green" onclick="gotoReport('<%=ReportVersionConstants.REPORT_CANDIDATE_OFFERS %>');"><%=ReportVersionConstants.getReportTitle(ReportVersionConstants.REPORT_CANDIDATE_OFFERS)%></a><br/></td>
				</tr>	 
			</table>		
		</td>
	</tr>
		<% } %>
	<tr>
		<td colspan="2" height="20">&nbsp;</td>
	</tr>
	<tr>
	<td valign="top">
		<logic:notEqual name="customReportsSize" scope="request" value="0">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr> 
				  	<td height="20"><strong class="Grey"><bean:message key="report.label.report_type.custom" /></strong></td> 
				</tr>
				<logic:iterate id="customReport" name="customReports" scope="request" type="ReportData">
				  	<tr>
				  		<td height="18"><span class="greenBullet">&raquo;</span>&nbsp;<a href="#" class="green" onclick="gotoCustomReport('<bean:write property="reportId" name="customReport" />');" ><bean:write property="reportName" name="customReport" /></a>
			  				<logic:equal value="1" name="customReport" property="canDeleteReport">
			  					<a href="#" class="green" onclick="editCustomReport('<bean:write property="reportId" name="customReport" />');" >[Edit]</a>			
			  					<a href="#" class="green" onclick="deleteCustomReport('<bean:write property="reportId" name="customReport" />','<bean:write property="reportName" name="customReport" />');" >[Del]</a>			  					
				  			</logic:equal>					  		
				  		<br/></td>
				 	</tr>				 	
				 </logic:iterate>
			</table>		
		</logic:notEqual>
	</td>
	<td valign="top">
		<logic:notEqual name="newCustomReportsSize" scope="request" value="0">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr> 
				  	<td height="20">
				  		<strong class="Grey"><bean:message key="common.summary" /></strong>
				  		[<bean:message key="report.label.last_scheduler_run_time" />
	  					<bean:write name="schedulerLastRunTime" scope="request" />]
				  	</td> 
				</tr>
				<logic:iterate id="newCustomReport" name="newCustomReports" scope="request" type="CustomReportDetails">
				  	<tr>
				  		<td height="18"><span class="greenBullet">&raquo;</span>&nbsp;<a href="#" class="green" onclick="gotoNewCustomReport('<bean:write property="reportId" name="newCustomReport" />');" ><bean:write property="reportName" name="newCustomReport" /></a>
			  				<logic:equal value="true" name="permissionSet" scope="session" property="PERMISSION_SUMMARY_REPORTS_ADD_MODIFY">
			  					<a href="#" class="green" onclick="editNewCustomReport('<bean:write property="reportId" name="newCustomReport" />');" >[Edit]</a>			
			  					<a href="#" class="green" onclick="deleteNewCustomReport('<bean:write property="reportId" name="newCustomReport" />','<bean:write property="reportName" name="newCustomReport" />');" >[Del]</a>		
				  			</logic:equal>					  		
				  		<br/></td>
				 	</tr> 
				 </logic:iterate>				
			</table>		
		</logic:notEqual>
	</td>
	</tr>
	<tr>
		<td valign="top">
		<logic:notEqual name="customizedReportsSize" scope="request" value="0">
			<table style="width: 100%; padding: 0; border-spacing: 0; border: 0;" >
				<tr> 
				  	<td height="20">
				  		<strong class="Grey">Customized Reports<!-- <bean:message key="common.summary" /> --></strong>
				  	</td> 
				</tr>
				<logic:iterate id="customizedReport" name="customizedReports" scope="request" type="CustomizedReportData">
				  	<tr>
				  		<td height="18">
				  			<span class="greenBullet">&raquo;</span>&nbsp;
				  			<a href="#" class="green" onclick="gotoReport('<bean:write property="reportName" name="customizedReport" />');" >
				  				<bean:write property="reportLabel" name="customizedReport" />
				  			</a>
				  			<br/>
				  		</td>
				 	</tr> 
				 </logic:iterate>				
			</table>		
		</logic:notEqual>
		</td>
		<td/>
	</tr>
</table>
<br/>
<br/>
</div>
<script>
function editNewCustomReport(reportId){
	var param = "reportId="+reportId;
	window.location.href="editCustomReport.action?"+param;
}
</script>