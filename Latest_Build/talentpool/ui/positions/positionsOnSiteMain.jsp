<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@page import="com.talentPool.positions.PositionConstants"%>
<%@ page import="com.talentPool.positions.dataobject.PositionData" %>
<% StringBuffer pos_ids = new StringBuffer();
   int i=1;
%>
<div>
	<logic:notEmpty name="positions" scope="request">
	<div style="overflow:auto; margin-left: 25px; margin-top: 25px;">
		<table cellspacing="0" cellpadding="2">
			<tr>
				<th class="colBorder"><bean:message key="position.publish_to_web_site_sr_no"/></td>
				<th class="colBorder"><B><bean:message key="common.position_title" /></B></th>
			   	<th class="colBorder"><B><bean:message key="position.publish_for_walk_in.label.position_location" /></B></th>
			</tr>			  
			<logic:iterate name="positions" id="position" type="PositionData">
			<tr>
				<td class="colBorder"><%=i++%></td>
				<td class="colBorder" width="350px"><a href="viewPositions.do?mode=positionDescription&positionId=<%=position.getPositionId()%>"> <bean:write name="position" property="positionTitle"/></a></td>
				<td class="colBorder" width="180px">
				<logic:notEmpty name="position" property="locationName">
					<bean:write name="position" property="locationName"/>
				</logic:notEmpty>
				<logic:empty name="position" property="locationName">
					&nbsp;
				</logic:empty>
				</td>
			</tr>
			</logic:iterate>
		</table>
	</div>
	</logic:notEmpty>
</div>