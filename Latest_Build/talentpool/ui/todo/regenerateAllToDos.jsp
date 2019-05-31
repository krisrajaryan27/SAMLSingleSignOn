<%@ taglib uri="/WEB-INF/tld/struts-tiles.tld" prefix="tiles"%>
<tiles:insert page="/templates/memberHomeOneColumnTemplate.jsp" flush="true">
	<tiles:put name="topPane" value="/common/topPane.jsp"/>
  <tiles:put name="mainPane" value="/todo/regenerateAllToDosMain.jsp"/>
</tiles:insert>