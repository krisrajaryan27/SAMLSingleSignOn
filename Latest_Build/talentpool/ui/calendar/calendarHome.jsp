<%@ taglib uri="/WEB-INF/tld/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic" %>
<logic:present scope="request" parameter="popup">
<tiles:insert page="/templates/popupWindowTemplate.jsp" flush="true">
  <tiles:put name="mainPane" value="/calendar/calendarHomeMain.jsp"/>
</tiles:insert>
</logic:present>                
<logic:notPresent scope="request" parameter="popup">
<tiles:insert page="/templates/memberHomeOneColumnTemplate.jsp" flush="true">
	<tiles:put name="topPane" value="/common/topPane.jsp"/>
  <tiles:put name="mainPane" value="/calendar/calendarHomeMain.jsp"/>
</tiles:insert>
</logic:notPresent>                

