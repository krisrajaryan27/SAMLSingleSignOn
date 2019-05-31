<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@page import="com.talentPool.common.utils.Utils"%>
<%@page import="com.talentPool.common.db.SimpleDataObject"%>
<%@page import="java.util.ArrayList"%>
<%
ArrayList history = (ArrayList)request.getAttribute("history");
%>
<div class="contentDiv">
	<b>About</b>
	<br/><br/>
	<table width="100%" border="0" cellspacing="0" cellpadding="0"> 
	<tr> 
	<td><div  class="boxTab" style="width:70px;"><span class="rightC"></span><span class="leftC"></span>History</div></td> 
	</tr> 
	</table> 
	<table class="reportHeader" cellspacing="0" cellpadding="0" border="0"  width="100%">
	<tr>
	<td class="head"><b>Product</b></td>
	<td class="head"><b>Version</b></td>
	<td class="head"><b>Build</b></td>
	<td class="head"><b>Date</b></td>
	</tr>
	<%
	if(history !=null){
		for(int i=0;i<history.size();i++){
		SimpleDataObject result = (SimpleDataObject)history.get(i);
	%>
	<tr>
	<td style="padding-top: 5px; padding-bottom: 5px;"><%=result.getString("productCode") %></td>
	<td style="padding-top: 5px; padding-bottom: 5px;"><%=result.getString("versionNumber") %></td>
	<td style="padding-top: 5px; padding-bottom: 5px;"><%=result.getString("buildNumber") %></td>
	<td style="padding-top: 5px; padding-bottom: 5px;"><%=Utils.getDateConvertedToString(result.getDate("installDate"),"dd-MMM-yyyy") %></td>
	</tr>
	<%
		}
	}
	%>
	</table>
	<br>
	<b>Contact</b>
	<br>
	<table >
	<tr>
	<td style="padding-top: 5px;">	
	<bean:message key="applicant_main.label.web" />
	&nbsp;<a href="http://www.nitman.co.in" target="_new">
	<bean:message key="common.label.www_talentpool_in" />	
	</a>
	</td>
	</tr>
	<tr>
	<td style="padding-top: 5px;">
	<bean:message key="applicant_main.label.email" />	
	&nbsp;<a href="mailto:support@nitman.co.in">
	<bean:message key="common.label.support_talentpool_in" />
	</a>
	</td>
	</tr>
	</table>
	<br><br><br>
	<div class="navBtn" style="float: left;">
	<a href="#" style="width:60px;" class="active" onclick="javascript: window.close();"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.close"/></a>
	</div>

</div>