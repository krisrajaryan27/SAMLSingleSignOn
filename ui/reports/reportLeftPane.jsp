<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-tiles.tld" prefix="tiles"%>
<%@page import="com.talentPool.reports.ReportVersionConstants,
				com.talentPool.user.UserConstants"%>
<%@page import="java.util.BitSet"%>
<%@page import="java.util.List"%>
<%@page import="com.talentPool.common.properties.TPApplicationProperties"%>
<%@page import="com.talentPool.reports.dataobject.CustomizedReportData"%>
<script src="js/dhtmlxGrid/dhtmlXCommon.js"></script> 
<script src="js/dhtmlxGrid/dhtmlXGrid.js"></script> 
<script src="js/dhtmlxGrid/dhtmlXGridCell.js"></script> 
<script src="js/dhtmlxGrid/dhtmlXGrid_srnd.js"></script>
<script src="js/dhtmlxGrid/dhtmlXGrid_excell_link.js"></script>
<script src="js/commonGridFunctions.js" type="text/javascript"></script>
<script src="js/scripta/lib/prototype.js" type="text/javascript"></script>
<script src="js/scripta/src/effects.js" type="text/javascript"></script>
<script src="js/selectbox/dropdiv.js" type="text/javascript"></script>
<script src="js/ajaxfunctions.js" type="text/javascript"></script>
<script src="js/selectoption.js" type="text/javascript"></script>
<script src="js/selectbox/selectbox.js" type="text/javascript"></script>
<script src="js/calender/dateFormatter.js" type="text/javascript"></script>
<script src="js/submodal/common.js"></script>
<script src="js/submodal/subModal.js"></script>
<div style="margin-left:18px; margin-right:18px;">
<% 
BitSet reportBitSet = (BitSet)request.getSession(false).getAttribute("reportBitSet");
List newCustomReports = (List)request.getAttribute("newCustomReports");
%>
<br/>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<logic:equal value="true" name="permissionSet" scope="session" property="PERMISSION_SUMMARY_REPORTS_ADD_MODIFY">		
		<tr>
			<td>
				<div class="navBtn"><a href="#" onclick="createCustomReport();return false;" style="width:80px;margin-right:5px;" class="active"><span class="rightC"></span><span class="leftC"></span><bean:message key="positions_home.label.add_new" /></a></div>		
			</td>
		</tr>
	</logic:equal> 
  	<tr> 
	 	<td height="5"></td> 
	</tr>
	
	<% if(ReportVersionConstants.isReportInCategoryAvailable(reportBitSet, ReportVersionConstants.CATEGORY_REPORT_STATUS)){ %> 
    <tr> 
	  	<td height="20"><strong class="Grey"><bean:message key="report.label.report_type.status" /></strong></td> 
	</tr>
	<%} %>
	<% if(ReportVersionConstants.isReportAvailable(reportBitSet,ReportVersionConstants.REPORT_HIRING_STATUS)){ %> 
	<tr>
		<logic:equal value="<%=ReportVersionConstants.REPORT_HIRING_STATUS%>" property="reportName" name="reportForm">
			<td height="18"><span class="greenBullet">&raquo;</span>&nbsp;<%=ReportVersionConstants.getReportTitle(ReportVersionConstants.REPORT_HIRING_STATUS)%><br/></td>
		</logic:equal>            	    
		<logic:notEqual value="<%=ReportVersionConstants.REPORT_HIRING_STATUS%>" property="reportName" name="reportForm">
			<td height="18"><span class="greenBullet">&raquo;</span>&nbsp;<a href="#" class="green" onclick="gotoReport('<%=ReportVersionConstants.REPORT_HIRING_STATUS%>');" ><%=ReportVersionConstants.getReportTitle(ReportVersionConstants.REPORT_HIRING_STATUS)%></a><br/></td>
		</logic:notEqual>            	    
	</tr>
	<% } %>
	<% if(ReportVersionConstants.isReportAvailable(reportBitSet,ReportVersionConstants.REPORT_HIRING_FUNNEL)){ %> 
  	<tr>
		<logic:equal value="<%=ReportVersionConstants.REPORT_HIRING_FUNNEL%>" property="reportName" name="reportForm">
			<td height="18"><span class="greenBullet">&raquo;</span>&nbsp;<%=ReportVersionConstants.getReportTitle(ReportVersionConstants.REPORT_HIRING_FUNNEL)%><br/></td>
		</logic:equal>
		<logic:notEqual value="<%=ReportVersionConstants.REPORT_HIRING_FUNNEL %>" property="reportName" name="reportForm">
			<td height="18"><span class="greenBullet">&raquo;</span>&nbsp;<a href="#" class="green" onclick="gotoReport('<%=ReportVersionConstants.REPORT_HIRING_FUNNEL %>');"><%=ReportVersionConstants.getReportTitle(ReportVersionConstants.REPORT_HIRING_FUNNEL)%></a><br/></td>
		</logic:notEqual>            	    
	</tr>	
	<% } %>
	<% if(ReportVersionConstants.isReportAvailable(reportBitSet,ReportVersionConstants.REPORT_POSITION_SUMMARY)){ %> 
	<tr>
		<logic:equal value="<%=ReportVersionConstants.REPORT_POSITION_SUMMARY%>" property="reportName" name="reportForm">
			<td height="18"><span class="greenBullet">&raquo;</span>&nbsp;<%=ReportVersionConstants.getReportTitle(ReportVersionConstants.REPORT_POSITION_SUMMARY)%><br/></td>
		</logic:equal>
		<logic:notEqual value="<%=ReportVersionConstants.REPORT_POSITION_SUMMARY %>" property="reportName" name="reportForm">
			<td height="18"><span class="greenBullet">&raquo;</span>&nbsp;<a href="#" class="green" onclick="gotoReport('<%=ReportVersionConstants.REPORT_POSITION_SUMMARY %>');"><%=ReportVersionConstants.getReportTitle(ReportVersionConstants.REPORT_POSITION_SUMMARY)%></a><br/></td>
		</logic:notEqual>            	    
	</tr>
	<% } %>
	<% if(ReportVersionConstants.isReportAvailable(reportBitSet,ReportVersionConstants.REPORT_PENDING_ACTION)){ %> 
	<tr>
		<logic:equal value="<%=ReportVersionConstants.REPORT_PENDING_ACTION%>" property="reportName" name="reportForm">
			<td height="18"><span class="greenBullet">&raquo;</span>&nbsp;<%=ReportVersionConstants.getReportTitle(ReportVersionConstants.REPORT_PENDING_ACTION)%><br/></td>
		</logic:equal>
		<logic:notEqual value="<%=ReportVersionConstants.REPORT_PENDING_ACTION %>" property="reportName" name="reportForm">
			<td height="18"><span class="greenBullet">&raquo;</span>&nbsp;<a href="#" class="green" onclick="gotoReport('<%=ReportVersionConstants.REPORT_PENDING_ACTION %>');"><%=ReportVersionConstants.getReportTitle(ReportVersionConstants.REPORT_PENDING_ACTION)%></a><br/></td>
		</logic:notEqual>            	    
	</tr>
	<% } %>
	<% if(ReportVersionConstants.isReportAvailable(reportBitSet,ReportVersionConstants.REPORT_CANDIDATE_STATUS)){ %> 
	<tr>
		<logic:equal value="<%=ReportVersionConstants.REPORT_CANDIDATE_STATUS%>" property="reportName" name="reportForm">
			<td height="18"><span class="greenBullet">&raquo;</span>&nbsp;<%=ReportVersionConstants.getReportTitle(ReportVersionConstants.REPORT_CANDIDATE_STATUS)%><br/></td>
		</logic:equal>
		<logic:notEqual value="<%=ReportVersionConstants.REPORT_CANDIDATE_STATUS %>" property="reportName" name="reportForm">
			<td height="18"><span class="greenBullet">&raquo;</span>&nbsp;<a href="#" class="green" onclick="gotoReport('<%=ReportVersionConstants.REPORT_CANDIDATE_STATUS %>');"><%=ReportVersionConstants.getReportTitle(ReportVersionConstants.REPORT_CANDIDATE_STATUS)%></a><br/></td>
		</logic:notEqual>            	    
	</tr>
	<% } %>
	<% if(ReportVersionConstants.isReportAvailable(reportBitSet,ReportVersionConstants.REPORT_CANDIDATE_STATUS)){ %> 
	<tr>
		<logic:equal value="<%=ReportVersionConstants.REPORT_AUDIT_LOG%>" property="reportName" name="reportForm">
			<td height="18"><span class="greenBullet">&raquo;</span>&nbsp;<%=ReportVersionConstants.getReportTitle(ReportVersionConstants.REPORT_AUDIT_LOG)%><br/></td>
		</logic:equal>
		<logic:notEqual value="<%=ReportVersionConstants.REPORT_AUDIT_LOG %>" property="reportName" name="reportForm">
			<td height="18"><span class="greenBullet">&raquo;</span>&nbsp;<a href="#" class="green" onclick="gotoReport('<%=ReportVersionConstants.REPORT_AUDIT_LOG %>');"><%=ReportVersionConstants.getReportTitle(ReportVersionConstants.REPORT_AUDIT_LOG)%></a><br/></td>
		</logic:notEqual>            	    
	</tr>
	<% } %>
	<% if(ReportVersionConstants.isReportAvailable(reportBitSet,ReportVersionConstants.REPORT_PENDING_OFFER)){ %> 
	<tr>
		<logic:equal value="<%=ReportVersionConstants.REPORT_PENDING_OFFER%>" property="reportName" name="reportForm">
			<td height="18"><span class="greenBullet">&raquo;</span>&nbsp;<%=ReportVersionConstants.getReportTitle(ReportVersionConstants.REPORT_PENDING_OFFER)%><br/></td>
		</logic:equal>
		<logic:notEqual value="<%=ReportVersionConstants.REPORT_PENDING_OFFER %>" property="reportName" name="reportForm">
			<td height="18"><span class="greenBullet">&raquo;</span>&nbsp;<a href="#" class="green" onclick="gotoReport('<%=ReportVersionConstants.REPORT_PENDING_OFFER %>');"><%=ReportVersionConstants.getReportTitle(ReportVersionConstants.REPORT_PENDING_OFFER)%></a><br/></td>
		</logic:notEqual>            	    
	</tr>
	<% } %>
	<% if(ReportVersionConstants.isReportAvailable(reportBitSet,ReportVersionConstants.REPORT_REJECTED_CANDIDATES)){ %> 
	<tr>
		<logic:equal value="<%=ReportVersionConstants.REPORT_REJECTED_CANDIDATES%>" property="reportName" name="reportForm">
			<td height="18"><span class="greenBullet">&raquo;</span>&nbsp;<%=ReportVersionConstants.getReportTitle(ReportVersionConstants.REPORT_REJECTED_CANDIDATES)%><br/></td>
		</logic:equal>
		<logic:notEqual value="<%=ReportVersionConstants.REPORT_REJECTED_CANDIDATES %>" property="reportName" name="reportForm">
			<td height="18"><span class="greenBullet">&raquo;</span>&nbsp;<a href="#" class="green" onclick="gotoReport('<%=ReportVersionConstants.REPORT_REJECTED_CANDIDATES %>');"><%=ReportVersionConstants.getReportTitle(ReportVersionConstants.REPORT_REJECTED_CANDIDATES)%></a><br/></td>
		</logic:notEqual>            	    
	</tr>
	<% } %>
	
	<% if(ReportVersionConstants.isReportAvailable(reportBitSet,ReportVersionConstants.REPORT_JOINER)){ %> 
	<tr>
		<logic:equal value="<%=ReportVersionConstants.REPORT_JOINER%>" property="reportName" name="reportForm">
			<td height="18"><span class="greenBullet">&raquo;</span>&nbsp;<%=ReportVersionConstants.getReportTitle(ReportVersionConstants.REPORT_JOINER)%><br/></td>
		</logic:equal>
		<logic:notEqual value="<%=ReportVersionConstants.REPORT_JOINER %>" property="reportName" name="reportForm">
			<td height="18"><span class="greenBullet">&raquo;</span>&nbsp;<a href="#" class="green" onclick="gotoReport('<%=ReportVersionConstants.REPORT_JOINER %>');"><%=ReportVersionConstants.getReportTitle(ReportVersionConstants.REPORT_JOINER)%></a><br/></td>
		</logic:notEqual>            	    
	</tr>
	<% } %>
	
	<% if(ReportVersionConstants.isReportAvailable(reportBitSet,ReportVersionConstants.REPORT_JOINED)){ %> 
	<tr>
		<logic:equal value="<%=ReportVersionConstants.REPORT_JOINED%>" property="reportName" name="reportForm">
			<td height="18"><span class="greenBullet">&raquo;</span>&nbsp;<%=ReportVersionConstants.getReportTitle(ReportVersionConstants.REPORT_JOINED)%><br/></td>
		</logic:equal>
		<logic:notEqual value="<%=ReportVersionConstants.REPORT_JOINED %>" property="reportName" name="reportForm">
			<td height="18"><span class="greenBullet">&raquo;</span>&nbsp;<a href="#" class="green" onclick="gotoReport('<%=ReportVersionConstants.REPORT_JOINED %>');"><%=ReportVersionConstants.getReportTitle(ReportVersionConstants.REPORT_JOINED)%></a><br/></td>
		</logic:notEqual>            	    
	</tr>
	<% } %>
	<% if(ReportVersionConstants.isReportAvailable(reportBitSet,ReportVersionConstants.REPORT_BLACKLISTED)){ %> 
	<tr>
		<logic:equal value="<%=ReportVersionConstants.REPORT_BLACKLISTED%>" property="reportName" name="reportForm">
			<td height="18"><span class="greenBullet">&raquo;</span>&nbsp;<%=ReportVersionConstants.getReportTitle(ReportVersionConstants.REPORT_BLACKLISTED)%><br/></td>
		</logic:equal>
		<logic:notEqual value="<%=ReportVersionConstants.REPORT_BLACKLISTED %>" property="reportName" name="reportForm">
			<td height="18"><span class="greenBullet">&raquo;</span>&nbsp;<a href="#" class="green" onclick="gotoReport('<%=ReportVersionConstants.REPORT_BLACKLISTED %>');"><%=ReportVersionConstants.getReportTitle(ReportVersionConstants.REPORT_BLACKLISTED)%></a><br/></td>
		</logic:notEqual>            	    
	</tr>
	<% } %>
	
	<% if(ReportVersionConstants.isReportAvailable(reportBitSet,ReportVersionConstants.REPORT_ALL_CANDIDATES)){ %> 
	<tr>
		<logic:equal value="<%=ReportVersionConstants.REPORT_ALL_CANDIDATES%>" property="reportName" name="reportForm">
			<td height="18"><span class="greenBullet">&raquo;</span>&nbsp;<%=ReportVersionConstants.getReportTitle(ReportVersionConstants.REPORT_ALL_CANDIDATES)%><br/></td>
		</logic:equal>
		<logic:notEqual value="<%=ReportVersionConstants.REPORT_ALL_CANDIDATES %>" property="reportName" name="reportForm">
			<td height="18"><span class="greenBullet">&raquo;</span>&nbsp;<a href="#" class="green" onclick="gotoReport('<%=ReportVersionConstants.REPORT_ALL_CANDIDATES %>');"><%=ReportVersionConstants.getReportTitle(ReportVersionConstants.REPORT_ALL_CANDIDATES)%></a><br/></td>
		</logic:notEqual>            	    
	</tr>
	<% } %>
	
	<% if(ReportVersionConstants.isReportInCategoryAvailable(reportBitSet, ReportVersionConstants.CATEGORY_REPORT_ANALYSIS)){ %> 
	<tr> 
	  	<td height="10"></td> 
	</tr>      
 	<tr> 
    	<td height="20"><strong class="Grey"><bean:message key="report.label.report_type.analysis" /></strong></td> 
  	</tr>
	<%}if(ReportVersionConstants.isReportAvailable(reportBitSet,ReportVersionConstants.MASTER_REPORT)){ %>
	<tr>
		<logic:equal value="<%=ReportVersionConstants.MASTER_REPORT%>" property="reportName" name="reportForm">
			<td height="18"><span class="greenBullet">&raquo;</span>&nbsp;<%=ReportVersionConstants.getReportTitle(ReportVersionConstants.MASTER_REPORT)%><br/></td>
		</logic:equal>
		<logic:notEqual value="<%=ReportVersionConstants.MASTER_REPORT %>" property="reportName" name="reportForm">
			<td height="18"><span class="greenBullet">&raquo;</span>&nbsp;<a href="#" class="green" onclick="gotoReport('<%=ReportVersionConstants.MASTER_REPORT %>');"><%=ReportVersionConstants.getReportTitle(ReportVersionConstants.MASTER_REPORT)%></a><br/></td>
		</logic:notEqual>
	</tr>
	<%}if(ReportVersionConstants.isReportAvailable(reportBitSet,ReportVersionConstants.MASTER_REPORT)){ %>
	<tr>
		<logic:equal value="<%=ReportVersionConstants.MASTER_DATA_REPORT%>" property="reportName" name="reportForm">
			<td height="18"><span class="greenBullet">&raquo;</span>&nbsp;<%=ReportVersionConstants.getReportTitle(ReportVersionConstants.MASTER_DATA_REPORT)%><br/></td>
		</logic:equal>
		<logic:notEqual value="<%=ReportVersionConstants.MASTER_DATA_REPORT %>" property="reportName" name="reportForm">
			<td height="18"><span class="greenBullet">&raquo;</span>&nbsp;<a href="#" class="green" onclick="gotoReport('<%=ReportVersionConstants.MASTER_DATA_REPORT %>');"><%=ReportVersionConstants.getReportTitle(ReportVersionConstants.MASTER_DATA_REPORT)%></a><br/></td>
		</logic:notEqual>
	</tr>
	<%} %>
	<% if(ReportVersionConstants.isReportAvailable(reportBitSet,ReportVersionConstants.REPORT_HIRING_EFICIENCY)){ %> 
  	<tr>
		<logic:equal value="<%=ReportVersionConstants.REPORT_HIRING_EFICIENCY%>" property="reportName" name="reportForm">
			<td height="18"><span class="greenBullet">&raquo;</span>&nbsp;<%=ReportVersionConstants.getReportTitle(ReportVersionConstants.REPORT_HIRING_EFICIENCY)%><br/></td>
		</logic:equal>
		<logic:notEqual value="<%=ReportVersionConstants.REPORT_HIRING_EFICIENCY%>" property="reportName" name="reportForm">
			<td height="18"><span class="greenBullet">&raquo;</span>&nbsp;<a href="#" class="green" onclick="gotoReport('<%=ReportVersionConstants.REPORT_HIRING_EFICIENCY%>');" ><%=ReportVersionConstants.getReportTitle(ReportVersionConstants.REPORT_HIRING_EFICIENCY)%></a><br/></td>
		</logic:notEqual>            	    
  	</tr>
	<% } %>
	<% if(ReportVersionConstants.isReportAvailable(reportBitSet,ReportVersionConstants.REPORT_SOURCE_WISE_HIRING)){ %> 
  	<tr>
		<logic:equal value="<%=ReportVersionConstants.REPORT_SOURCE_WISE_HIRING%>" property="reportName" name="reportForm">
			<td height="18"><span class="greenBullet">&raquo;</span>&nbsp;<%=ReportVersionConstants.getReportTitle(ReportVersionConstants.REPORT_SOURCE_WISE_HIRING)%><br/></td>
		</logic:equal>
		<logic:notEqual value="<%=ReportVersionConstants.REPORT_SOURCE_WISE_HIRING %>" property="reportName" name="reportForm">
			<td height="18"><span class="greenBullet">&raquo;</span>&nbsp;<a href="#" class="green" onclick="gotoReport('<%=ReportVersionConstants.REPORT_SOURCE_WISE_HIRING%>');" ><%=ReportVersionConstants.getReportTitle(ReportVersionConstants.REPORT_SOURCE_WISE_HIRING)%></a><br/></td>
		</logic:notEqual>            	    
 	</tr>
	<% } %>
	<% if(ReportVersionConstants.isReportAvailable(reportBitSet,ReportVersionConstants.REPORT_MONTHLY_JOINING_REPORT)){ %> 
  	<tr>
		<logic:equal value="<%=ReportVersionConstants.REPORT_MONTHLY_JOINING_REPORT%>" property="reportName" name="reportForm">
			<td height="18"><span class="greenBullet">&raquo;</span>&nbsp;<%=ReportVersionConstants.getReportTitle(ReportVersionConstants.REPORT_MONTHLY_JOINING_REPORT)%><br/></td>
		</logic:equal>
		<logic:notEqual value="<%=ReportVersionConstants.REPORT_MONTHLY_JOINING_REPORT %>" property="reportName" name="reportForm">
			<td height="18"><span class="greenBullet">&raquo;</span>&nbsp;<a href="#" class="green" onclick="gotoReport('<%=ReportVersionConstants.REPORT_MONTHLY_JOINING_REPORT %>');"><%=ReportVersionConstants.getReportTitle(ReportVersionConstants.REPORT_MONTHLY_JOINING_REPORT)%></a><br/></td>
		</logic:notEqual>            	    
	</tr>
	<% } %>
	<% if(ReportVersionConstants.isReportAvailable(reportBitSet,ReportVersionConstants.REPORT_SOURCEWISE_IMPORT)){ %> 
	<tr>
		<logic:equal value="<%=ReportVersionConstants.REPORT_SOURCEWISE_IMPORT%>" property="reportName" name="reportForm">
			<td height="18"><span class="greenBullet">&raquo;</span>&nbsp;<%=ReportVersionConstants.getReportTitle(ReportVersionConstants.REPORT_SOURCEWISE_IMPORT)%><br/></td>
		</logic:equal>
		<logic:notEqual value="<%=ReportVersionConstants.REPORT_SOURCEWISE_IMPORT %>" property="reportName" name="reportForm">
			<td height="18"><span class="greenBullet">&raquo;</span>&nbsp;<a href="#" class="green" onclick="gotoReport('<%=ReportVersionConstants.REPORT_SOURCEWISE_IMPORT %>');"><%=ReportVersionConstants.getReportTitle(ReportVersionConstants.REPORT_SOURCEWISE_IMPORT)%></a><br/></td>
		</logic:notEqual>            	    
	</tr>	  
	<% } %>
	<% if(ReportVersionConstants.isReportAvailable(reportBitSet,ReportVersionConstants.REPORT_CANDIDATE_STATUS)){ %> 
	<tr>
		<logic:equal value="<%=ReportVersionConstants.REPORT_AUDIT_LOG%>" property="reportName" name="reportForm">
			<td height="18"><span class="greenBullet">&raquo;</span>&nbsp;<%=ReportVersionConstants.getReportTitle(ReportVersionConstants.REPORT_AUDIT_LOG)%><br/></td>
		</logic:equal>
		<logic:notEqual value="<%=ReportVersionConstants.REPORT_AUDIT_LOG %>" property="reportName" name="reportForm">
			<td height="18"><span class="greenBullet">&raquo;</span>&nbsp;<a href="#" class="green" onclick="gotoReport('<%=ReportVersionConstants.REPORT_AUDIT_LOG %>');"><%=ReportVersionConstants.getReportTitle(ReportVersionConstants.REPORT_AUDIT_LOG)%></a><br/></td>
		</logic:notEqual>            	    
	</tr>
	<% } %>
	<% if(ReportVersionConstants.isReportAvailable(reportBitSet,ReportVersionConstants.REPORT_OFFER_TO_JOINED)){ %> 
	<tr>
		<logic:equal value="<%=ReportVersionConstants.REPORT_OFFER_TO_JOINED%>" property="reportName" name="reportForm">
			<td height="18"><span class="greenBullet">&raquo;</span>&nbsp;<%=ReportVersionConstants.getReportTitle(ReportVersionConstants.REPORT_OFFER_TO_JOINED)%><br/></td>
		</logic:equal>
		<logic:notEqual value="<%=ReportVersionConstants.REPORT_OFFER_TO_JOINED %>" property="reportName" name="reportForm">
			<td height="18"><span class="greenBullet">&raquo;</span>&nbsp;<a href="#" class="green" onclick="gotoReport('<%=ReportVersionConstants.REPORT_OFFER_TO_JOINED %>');"><%=ReportVersionConstants.getReportTitle(ReportVersionConstants.REPORT_OFFER_TO_JOINED)%></a><br/></td>
		</logic:notEqual>            	    
	</tr>
	<% } %>
	<% if(ReportVersionConstants.isReportAvailable(reportBitSet,ReportVersionConstants.REPORT_OFFER_TO_JOINED_DETAILED)){ %> 
	<tr>
		<logic:equal value="<%=ReportVersionConstants.REPORT_OFFER_TO_JOINED_DETAILED%>" property="reportName" name="reportForm">
			<td height="18"><span class="greenBullet">&raquo;</span>&nbsp;<%=ReportVersionConstants.getReportTitle(ReportVersionConstants.REPORT_OFFER_TO_JOINED_DETAILED)%><br/></td>
		</logic:equal>
		<logic:notEqual value="<%=ReportVersionConstants.REPORT_OFFER_TO_JOINED_DETAILED %>" property="reportName" name="reportForm">
			<td height="18"><span class="greenBullet">&raquo;</span>&nbsp;<a href="#" class="green" onclick="gotoReport('<%=ReportVersionConstants.REPORT_OFFER_TO_JOINED_DETAILED %>');"><%=ReportVersionConstants.getReportTitle(ReportVersionConstants.REPORT_OFFER_TO_JOINED_DETAILED)%></a><br/></td>
		</logic:notEqual>            	    
	</tr>
	<% } %>
	<% if(ReportVersionConstants.isReportAvailable(reportBitSet,ReportVersionConstants.REPORT_OFFER_CTC)){ %> 
	<tr>
		<logic:equal value="<%=ReportVersionConstants.REPORT_OFFER_CTC%>" property="reportName" name="reportForm">
			<td height="18"><span class="greenBullet">&raquo;</span>&nbsp;<%=ReportVersionConstants.getReportTitle(ReportVersionConstants.REPORT_OFFER_CTC)%><br/></td>
		</logic:equal>
		<logic:notEqual value="<%=ReportVersionConstants.REPORT_OFFER_CTC %>" property="reportName" name="reportForm">
			<td height="18"><span class="greenBullet">&raquo;</span>&nbsp;<a href="#" class="green" onclick="gotoReport('<%=ReportVersionConstants.REPORT_OFFER_CTC %>');"><%=ReportVersionConstants.getReportTitle(ReportVersionConstants.REPORT_OFFER_CTC)%></a><br/></td>
		</logic:notEqual>            	    
	</tr>
	<% } %>
	<% if(ReportVersionConstants.isReportAvailable(reportBitSet,ReportVersionConstants.ONE_STOP_FILE_REPORT)){ %> 
	<tr>
		<logic:equal value="<%=ReportVersionConstants.ONE_STOP_FILE_REPORT%>" property="reportName" name="reportForm">
			<td height="18"><span class="greenBullet">&raquo;</span>&nbsp;<%=ReportVersionConstants.getReportTitle(ReportVersionConstants.ONE_STOP_FILE_REPORT)%><br/></td>
		</logic:equal>
		<logic:notEqual value="<%=ReportVersionConstants.ONE_STOP_FILE_REPORT %>" property="reportName" name="reportForm">
			<td height="18"><span class="greenBullet">&raquo;</span>&nbsp;<a href="#" class="green" onclick="gotoReport('<%=ReportVersionConstants.ONE_STOP_FILE_REPORT %>');"><%=ReportVersionConstants.getReportTitle(ReportVersionConstants.ONE_STOP_FILE_REPORT)%></a><br/></td>
		</logic:notEqual>            	    
	</tr>
	<% } %>
	<% if(ReportVersionConstants.isReportAvailable(reportBitSet,ReportVersionConstants.REPORT_CANDIDATE_COMPARISON)){ %> 
	<tr>
		<logic:equal value="<%=ReportVersionConstants.REPORT_CANDIDATE_COMPARISON%>" property="reportName" name="reportForm">
			<td height="18"><span class="greenBullet">&raquo;</span>&nbsp;<%=ReportVersionConstants.getReportTitle(ReportVersionConstants.REPORT_CANDIDATE_COMPARISON)%><br/></td>
		</logic:equal>
		<logic:notEqual value="<%=ReportVersionConstants.REPORT_CANDIDATE_COMPARISON %>" property="reportName" name="reportForm">
			<td height="18"><span class="greenBullet">&raquo;</span>&nbsp;<a href="#" class="green" onclick="gotoReport('<%=ReportVersionConstants.REPORT_CANDIDATE_COMPARISON %>');"><%=ReportVersionConstants.getReportTitle(ReportVersionConstants.REPORT_CANDIDATE_COMPARISON)%></a><br/></td>
		</logic:notEqual>            	    
	</tr>  	
	<% } %>
	<% if(ReportVersionConstants.isReportAvailable(reportBitSet,ReportVersionConstants.REPORT_RECRUITMENT_COST)){ %> 
	<tr>
		<logic:equal value="<%=ReportVersionConstants.REPORT_RECRUITMENT_COST%>" property="reportName" name="reportForm">
			<td height="18"><span class="greenBullet">&raquo;</span>&nbsp;<%=ReportVersionConstants.getReportTitle(ReportVersionConstants.REPORT_RECRUITMENT_COST)%><br/></td>
		</logic:equal>
		<logic:notEqual value="<%=ReportVersionConstants.REPORT_RECRUITMENT_COST %>" property="reportName" name="reportForm">
			<td height="18"><span class="greenBullet">&raquo;</span>&nbsp;<a href="#" class="green" onclick="gotoReport('<%=ReportVersionConstants.REPORT_RECRUITMENT_COST %>');"><%=ReportVersionConstants.getReportTitle(ReportVersionConstants.REPORT_RECRUITMENT_COST)%></a><br/></td>
		</logic:notEqual>            	    
	</tr>	
	<% } %>
	<% if(ReportVersionConstants.isReportAvailable(reportBitSet,ReportVersionConstants.REPORT_TIME_TO_HIRE)){ %> 
	<tr>
		<logic:equal value="<%=ReportVersionConstants.REPORT_TIME_TO_HIRE%>" property="reportName" name="reportForm">
			<td height="18"><span class="greenBullet">&raquo;</span>&nbsp;<%=ReportVersionConstants.getReportTitle(ReportVersionConstants.REPORT_TIME_TO_HIRE)%><br/></td>
		</logic:equal>
		<logic:notEqual value="<%=ReportVersionConstants.REPORT_TIME_TO_HIRE %>" property="reportName" name="reportForm">
			<td height="18"><span class="greenBullet">&raquo;</span>&nbsp;<a href="#" class="green" onclick="gotoReport('<%=ReportVersionConstants.REPORT_TIME_TO_HIRE %>');"><%=ReportVersionConstants.getReportTitle(ReportVersionConstants.REPORT_TIME_TO_HIRE)%></a><br/></td>
		</logic:notEqual>            	    
	</tr>
	<% } %>
	<% if(ReportVersionConstants.isReportAvailable(reportBitSet,ReportVersionConstants.REPORT_DATEWISE_HIRING_REPORT)){ %> 
	<tr>
		<logic:equal value="<%=ReportVersionConstants.REPORT_DATEWISE_HIRING_REPORT%>" property="reportName" name="reportForm">
			<td height="18"><span class="greenBullet">&raquo;</span>&nbsp;<%=ReportVersionConstants.getReportTitle(ReportVersionConstants.REPORT_DATEWISE_HIRING_REPORT)%><br/></td>
		</logic:equal>
		<logic:notEqual value="<%=ReportVersionConstants.REPORT_DATEWISE_HIRING_REPORT %>" property="reportName" name="reportForm">
			<td height="18"><span class="greenBullet">&raquo;</span>&nbsp;<a href="#" class="green" onclick="gotoReport('<%=ReportVersionConstants.REPORT_DATEWISE_HIRING_REPORT %>');"><%=ReportVersionConstants.getReportTitle(ReportVersionConstants.REPORT_DATEWISE_HIRING_REPORT)%></a><br/></td>
		</logic:notEqual>            	    
	</tr>
	<% } %>
	<% if(ReportVersionConstants.isReportAvailable(reportBitSet,ReportVersionConstants.REPORT_CLOSED_POSITION_TAT)){ %> 
	<tr>
		<logic:equal value="<%=ReportVersionConstants.REPORT_CLOSED_POSITION_TAT%>" property="reportName" name="reportForm">
			<td height="18"><span class="greenBullet">&raquo;</span>&nbsp;<%=ReportVersionConstants.getReportTitle(ReportVersionConstants.REPORT_CLOSED_POSITION_TAT)%><br/></td>
		</logic:equal>
		<logic:notEqual value="<%=ReportVersionConstants.REPORT_CLOSED_POSITION_TAT %>" property="reportName" name="reportForm">
			<td height="18"><span class="greenBullet">&raquo;</span>&nbsp;<a href="#" class="green" onclick="gotoReport('<%=ReportVersionConstants.REPORT_CLOSED_POSITION_TAT %>');"><%=ReportVersionConstants.getReportTitle(ReportVersionConstants.REPORT_CLOSED_POSITION_TAT)%></a><br/></td>
		</logic:notEqual>            	    
	</tr>
	<% } %>
	<% if(ReportVersionConstants.isReportAvailable(reportBitSet,ReportVersionConstants.COST_SHEET_FINANCE_REPORT)){ %> 
	<tr>
		<logic:equal value="<%=ReportVersionConstants.COST_SHEET_FINANCE_REPORT%>" property="reportName" name="reportForm">
			<td height="18"><span class="greenBullet">&raquo;</span>&nbsp;<%=ReportVersionConstants.getReportTitle(ReportVersionConstants.COST_SHEET_FINANCE_REPORT)%><br/></td>
		</logic:equal>
		<logic:notEqual value="<%=ReportVersionConstants.COST_SHEET_FINANCE_REPORT %>" property="reportName" name="reportForm">
			<td height="18"><span class="greenBullet">&raquo;</span>&nbsp;<a href="#" class="green" onclick="gotoReport('<%=ReportVersionConstants.COST_SHEET_FINANCE_REPORT %>');"><%=ReportVersionConstants.getReportTitle(ReportVersionConstants.COST_SHEET_FINANCE_REPORT)%></a><br/></td>
		</logic:notEqual>            	    
	</tr>
	<% } %>
	<% if(ReportVersionConstants.isReportAvailable(reportBitSet,ReportVersionConstants.JOINER_DATA_FINANCE_REPORT)){ %> 
	<tr>
		<logic:equal value="<%=ReportVersionConstants.JOINER_DATA_FINANCE_REPORT%>" property="reportName" name="reportForm">
			<td height="18"><span class="greenBullet">&raquo;</span>&nbsp;<%=ReportVersionConstants.getReportTitle(ReportVersionConstants.JOINER_DATA_FINANCE_REPORT)%><br/></td>
		</logic:equal>
		<logic:notEqual value="<%=ReportVersionConstants.JOINER_DATA_FINANCE_REPORT %>" property="reportName" name="reportForm">
			<td height="18"><span class="greenBullet">&raquo;</span>&nbsp;<a href="#" class="green" onclick="gotoReport('<%=ReportVersionConstants.JOINER_DATA_FINANCE_REPORT %>');"><%=ReportVersionConstants.getReportTitle(ReportVersionConstants.JOINER_DATA_FINANCE_REPORT)%></a><br/></td>
		</logic:notEqual>            	    
	</tr>
	<% } %>
	<% if(ReportVersionConstants.isReportAvailable(reportBitSet,ReportVersionConstants.INDIA_HIRING_REQ_REPORT)){ %> 
	<tr>
		<logic:equal value="<%=ReportVersionConstants.INDIA_HIRING_REQ_REPORT%>" property="reportName" name="reportForm">
			<td height="18"><span class="greenBullet">&raquo;</span>&nbsp;<%=ReportVersionConstants.getReportTitle(ReportVersionConstants.INDIA_HIRING_REQ_REPORT)%><br/></td>
		</logic:equal>
		<logic:notEqual value="<%=ReportVersionConstants.INDIA_HIRING_REQ_REPORT %>" property="reportName" name="reportForm">
			<td height="18"><span class="greenBullet">&raquo;</span>&nbsp;<a href="#" class="green" onclick="gotoReport('<%=ReportVersionConstants.INDIA_HIRING_REQ_REPORT %>');"><%=ReportVersionConstants.getReportTitle(ReportVersionConstants.INDIA_HIRING_REQ_REPORT)%></a><br/></td>
		</logic:notEqual>            	    
	</tr>
	<% } %>
	<% if(ReportVersionConstants.isReportAvailable(reportBitSet,ReportVersionConstants.INDIA_HIRING_SUMMARY_REPORT)){ %> 
	<tr>
		<logic:equal value="<%=ReportVersionConstants.INDIA_HIRING_SUMMARY_REPORT%>" property="reportName" name="reportForm">
			<td height="18"><span class="greenBullet">&raquo;</span>&nbsp;<%=ReportVersionConstants.getReportTitle(ReportVersionConstants.INDIA_HIRING_SUMMARY_REPORT)%><br/></td>
		</logic:equal>
		<logic:notEqual value="<%=ReportVersionConstants.INDIA_HIRING_SUMMARY_REPORT %>" property="reportName" name="reportForm">
			<td height="18"><span class="greenBullet">&raquo;</span>&nbsp;<a href="#" class="green" onclick="gotoReport('<%=ReportVersionConstants.INDIA_HIRING_SUMMARY_REPORT %>');"><%=ReportVersionConstants.getReportTitle(ReportVersionConstants.INDIA_HIRING_SUMMARY_REPORT)%></a><br/></td>
		</logic:notEqual>            	    
	</tr>
	<% } %>
	<% if(ReportVersionConstants.isReportInCategoryAvailable(reportBitSet, ReportVersionConstants.CATEGORY_REPORT_OPERATIONS)){ %> 
	<tr> 
	  	<td height="10"></td> 
	</tr> 	
	<tr> 
	  	<td height="20"><strong class="Grey"><bean:message key="report.label.report_type.operations" /></strong></td> 
	</tr>
	<%} %>
	<% if(ReportVersionConstants.isReportAvailable(reportBitSet,ReportVersionConstants.REPORT_CALL_LIST)){ %> 
  	<tr>
		<logic:equal value="<%=ReportVersionConstants.REPORT_CALL_LIST%>" property="reportName" name="reportForm">
			<td height="18"><span class="greenBullet">&raquo;</span>&nbsp;<%=ReportVersionConstants.getReportTitle(ReportVersionConstants.REPORT_CALL_LIST)%><br/></td>
		</logic:equal>
		<logic:notEqual value="<%=ReportVersionConstants.REPORT_CALL_LIST%>" property="reportName" name="reportForm">
			<td height="18"><span class="greenBullet">&raquo;</span>&nbsp;<a href="#" class="green" onclick="gotoReport('<%=ReportVersionConstants.REPORT_CALL_LIST%>');" ><%=ReportVersionConstants.getReportTitle(ReportVersionConstants.REPORT_CALL_LIST)%></a><br/></td>
		</logic:notEqual>            	    
  	</tr> 
	<% } %>
	<% if(ReportVersionConstants.isReportAvailable(reportBitSet,ReportVersionConstants.REPORT_INTERVIEW_LIST)){ %> 
  	<tr>
		<logic:equal value="<%=ReportVersionConstants.REPORT_INTERVIEW_LIST%>" property="reportName" name="reportForm">
			<td height="18"><span class="greenBullet">&raquo;</span>&nbsp;<%=ReportVersionConstants.getReportTitle(ReportVersionConstants.REPORT_INTERVIEW_LIST)%><br/></td>
		</logic:equal>
		<logic:notEqual value="<%=ReportVersionConstants.REPORT_INTERVIEW_LIST %>" property="reportName" name="reportForm">
			<td height="18"><span class="greenBullet">&raquo;</span>&nbsp;<a href="#" class="green" onclick="gotoReport('<%=ReportVersionConstants.REPORT_INTERVIEW_LIST %>');"><%=ReportVersionConstants.getReportTitle(ReportVersionConstants.REPORT_INTERVIEW_LIST)%></a><br/></td>
		</logic:notEqual>            	    
	</tr>
	<% } %>
	<% if(ReportVersionConstants.isReportAvailable(reportBitSet,ReportVersionConstants.REPORT_INTERVIEW_STATUS)){ %> 
  	<tr>
		<logic:equal value="<%=ReportVersionConstants.REPORT_INTERVIEW_STATUS%>" property="reportName" name="reportForm">
			<td height="18"><span class="greenBullet">&raquo;</span>&nbsp;<%=ReportVersionConstants.getReportTitle(ReportVersionConstants.REPORT_INTERVIEW_STATUS)%><br/></td>
		</logic:equal>
		<logic:notEqual value="<%=ReportVersionConstants.REPORT_INTERVIEW_STATUS %>" property="reportName" name="reportForm">
			<td height="18"><span class="greenBullet">&raquo;</span>&nbsp;<a href="#" class="green" onclick="gotoReport('<%=ReportVersionConstants.REPORT_INTERVIEW_STATUS %>');"><%=ReportVersionConstants.getReportTitle(ReportVersionConstants.REPORT_INTERVIEW_STATUS)%></a><br/></td>
		</logic:notEqual>            	    
	</tr>
	<% } %>
	<% if(ReportVersionConstants.isReportAvailable(reportBitSet,ReportVersionConstants.REPORT_APPLICANT_DETAILS)){ %> 
	<tr>
		<logic:equal value="<%=ReportVersionConstants.REPORT_APPLICANT_DETAILS%>" property="reportName" name="reportForm">
			<td height="18"><span class="greenBullet">&raquo;</span>&nbsp;<%=ReportVersionConstants.getReportTitle(ReportVersionConstants.REPORT_APPLICANT_DETAILS)%><br/></td>
		</logic:equal>
		<logic:notEqual value="<%=ReportVersionConstants.REPORT_APPLICANT_DETAILS%>" property="reportName" name="reportForm">
			<td height="18"><span class="greenBullet">&raquo;</span>&nbsp;<a href="#" class="green" onclick="gotoReport('<%=ReportVersionConstants.REPORT_APPLICANT_DETAILS%>');" ><%=ReportVersionConstants.getReportTitle(ReportVersionConstants.REPORT_APPLICANT_DETAILS)%></a><br/></td>
		</logic:notEqual>            	    
  	</tr>
	<% } %>
	<% if(ReportVersionConstants.isReportInCategoryAvailable(reportBitSet, ReportVersionConstants.CATEGORY_REPORT_ACTIVITY)){ %> 
	<tr> 
	  	<td height="10"></td> 
	</tr>     
	<tr> 
	  	<td height="20"><strong class="Grey"><bean:message key="report.label.report_type.activity" /></strong></td> 
	</tr>
	<%} %>
	<% if(ReportVersionConstants.isReportAvailable(reportBitSet,ReportVersionConstants.REPORT_IMPORT_REPORT)){ %> 
  	<tr>
		<logic:equal value="<%=ReportVersionConstants.REPORT_IMPORT_REPORT%>" property="reportName" name="reportForm">
			<td height="18"><span class="greenBullet">&raquo;</span>&nbsp;<%=ReportVersionConstants.getReportTitle(ReportVersionConstants.REPORT_IMPORT_REPORT)%><br/></td>
		</logic:equal>
		<logic:notEqual value="<%=ReportVersionConstants.REPORT_IMPORT_REPORT%>" property="reportName" name="reportForm">
			<td height="18"><span class="greenBullet">&raquo;</span>&nbsp;<a href="#" class="green" onclick="gotoReport('<%=ReportVersionConstants.REPORT_IMPORT_REPORT%>');" ><%=ReportVersionConstants.getReportTitle(ReportVersionConstants.REPORT_IMPORT_REPORT)%></a><br/></td>
		</logic:notEqual>            	    
  	</tr>
	<% } %>
	<% if(ReportVersionConstants.isReportAvailable(reportBitSet,ReportVersionConstants.REPORT_USER_ACTIVITY)){ %> 
  	<tr>
		<logic:equal value="<%=ReportVersionConstants.REPORT_USER_ACTIVITY%>" property="reportName" name="reportForm">
			<td height="18"><span class="greenBullet">&raquo;</span>&nbsp;<%=ReportVersionConstants.getReportTitle(ReportVersionConstants.REPORT_USER_ACTIVITY)%><br/></td>
		</logic:equal>
		<logic:notEqual value="<%=ReportVersionConstants.REPORT_USER_ACTIVITY %>" property="reportName" name="reportForm">
			<td height="18"><span class="greenBullet">&raquo;</span>&nbsp;<a href="#" class="green" onclick="gotoReport('<%=ReportVersionConstants.REPORT_USER_ACTIVITY %>');"><%=ReportVersionConstants.getReportTitle(ReportVersionConstants.REPORT_USER_ACTIVITY)%></a><br/></td>
		</logic:notEqual>            	    
	</tr>
	<% } %>
	<% if(ReportVersionConstants.isReportAvailable(reportBitSet,ReportVersionConstants.REPORT_POSITION_ACTIVITY)){ %> 
	<tr>
		<logic:equal value="<%=ReportVersionConstants.REPORT_POSITION_ACTIVITY%>" property="reportName" name="reportForm">
			<td height="18"><span class="greenBullet">&raquo;</span>&nbsp;<%=ReportVersionConstants.getReportTitle(ReportVersionConstants.REPORT_POSITION_ACTIVITY)%><br/></td>
		</logic:equal>
		<logic:notEqual value="<%=ReportVersionConstants.REPORT_POSITION_ACTIVITY %>" property="reportName" name="reportForm">
			<td height="18"><span class="greenBullet">&raquo;</span>&nbsp;<a href="#" class="green" onclick="gotoReport('<%=ReportVersionConstants.REPORT_POSITION_ACTIVITY %>');"><%=ReportVersionConstants.getReportTitle(ReportVersionConstants.REPORT_POSITION_ACTIVITY)%></a><br/></td>
		</logic:notEqual>            	    
	</tr>
	<% } %>
	<% if(ReportVersionConstants.isReportAvailable(reportBitSet,ReportVersionConstants.REPORT_HIRING_ACTIVITY)){ %> 
	<tr>
		<logic:equal value="<%=ReportVersionConstants.REPORT_HIRING_ACTIVITY%>" property="reportName" name="reportForm">
			<td height="18"><span class="greenBullet">&raquo;</span>&nbsp;<%=ReportVersionConstants.getReportTitle(ReportVersionConstants.REPORT_HIRING_ACTIVITY)%><br/></td>
		</logic:equal>
		<logic:notEqual value="<%=ReportVersionConstants.REPORT_HIRING_ACTIVITY %>" property="reportName" name="reportForm">
			<td height="18"><span class="greenBullet">&raquo;</span>&nbsp;<a href="#" class="green" onclick="gotoReport('<%=ReportVersionConstants.REPORT_HIRING_ACTIVITY %>');"><%=ReportVersionConstants.getReportTitle(ReportVersionConstants.REPORT_HIRING_ACTIVITY)%></a><br/></td>
		</logic:notEqual>            	    
	</tr>
	<% } %>
	
	<% if(ReportVersionConstants.isCustomReportAvailable()){ %>
	<tr> 
	  	<td height="10"></td> 
	</tr>     
	<tr> 
	  	<td height="20"><strong class="Grey"><bean:message key="report.label.report_type.custom" /></strong></td> 
	</tr>	 
  	<tr>
		<logic:equal value="<%=ReportVersionConstants.REPORT_CANDIDATE_OFFERS%>" property="reportName" name="reportForm">
			<td height="18"><span class="greenBullet">&raquo;</span>&nbsp;<%=ReportVersionConstants.getReportTitle(ReportVersionConstants.REPORT_CANDIDATE_OFFERS)%><br/></td>
		</logic:equal>
		<logic:notEqual value="<%=ReportVersionConstants.REPORT_CANDIDATE_OFFERS%>" property="reportName" name="reportForm">
			<td height="18"><span class="greenBullet">&raquo;</span>&nbsp;<a href="#" class="green" onclick="gotoReport('<%=ReportVersionConstants.REPORT_CANDIDATE_OFFERS%>');" ><%=ReportVersionConstants.getReportTitle(ReportVersionConstants.REPORT_CANDIDATE_OFFERS)%></a><br/></td>
		</logic:notEqual>  
  	</tr>
	<% } %>
	<tr> 
	  	<td height="10"></td> 
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
				<logic:notEmpty name="customizedReports" scope="request">
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
				 </logic:notEmpty>				
			</table>		
		</logic:notEqual>
		</td>
		<td/>
	</tr>
    
</table>
<br/>
</div>
<script>
function gotoReport(reportName){
	param = "mode=reportFilter&reportName="+reportName;
	if (reportName == '<%=ReportVersionConstants.REPORT_CALL_LIST%>') {
		param += '&isPopup=0';
	}
	window.location.href="reports.do?"+param;
}

function gotoCustomReport(reportId){
	param = "mode=customReport&reportId="+reportId;
	window.location.href="reports.do?"+param;
}

function deleteCustomReport(reportId, reportName){
    retVal = confirm('<bean:message key="reportdesigner.label.confirm_delete_report"/>' + ' ' + reportName + '. ' + '<bean:message key="common.continue?"/>');
    if (retVal == true) {
		var pars = "mode=deleteCustomReport&reportId="+reportId;
		var myAjax = ajaxCall("reports.do","get",pars,onDeleteComplete,reportError);
    }
}

function editCustomReport(reportId){
	param = "mode=editReport&reportId="+reportId;
	window.location.href="reportDesigner.do?"+param;
}

function onDeleteComplete(request){
	xmlFile = request.responseXML;
  	if(!isValidSession(xmlFile,'<%=TPApplicationProperties.getProperty("redirect_on_session_expired")%>'))return;
	if(isErrorXml(xmlFile)){
		alert('<bean:message key="reportdesigner.error.failed_delete"/>');
		return;
	}
	window.location.href="reports.do?mode=reportFilter&reportName=";
}

function createCustomReport(){
	var url="customizeReport.action";
	window.location.href=url;
	//showInPopUp(url,700,500,doNothing,true);
}


function gotoNewCustomReport(reportId){
	var showMigrationMessage = '0';
	<logic:present name="showMigrationMessage" scope="request">
		showMigrationMessage = '<bean:write name="showMigrationMessage" scope="request"/>';
	</logic:present>
	var param = "reportId="+reportId;
	param+='&showMigrationMessage='+showMigrationMessage;
	window.location.href="reportMain.action?"+param;
}

function deleteNewCustomReport(reportId, reportName){
    retVal = confirm('<bean:message key="custom.report.label.confirm_delete_report"/>' + ' ' + reportName + '. ' + '<bean:message key="common.continue?"/>');
    if (retVal == true) {
    	var pars = "reportId="+reportId;
		var myAjax = ajaxCall("deleteCustomReport.action","get",pars,onReportDeleteComplete,reportError);
    }
}

function onReportDeleteComplete(request){
	xmlFile = request.responseXML;
  	if(!isValidSession(xmlFile,'<%=TPApplicationProperties.getProperty("redirect_on_session_expired")%>'))return;
	if(isErrorXml(xmlFile)){
		alert('<bean:message key="custom.report.error.failed_delete"/>');
		return;
	}
	window.location.href="reports.do?mode=reportFilter&reportName=";
}

function showInPopUp(url,width,height,returnFun, close){
	showPopWin(url, width, height, returnFun,close);
}

function doNothing(val){
	
}

function onWindowLoad(){
	initPopUp();
}
window.onload=onWindowLoad;
</script>