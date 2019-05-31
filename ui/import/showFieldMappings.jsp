<%@ taglib uri="/WEB-INF/tld/struts-tiles.tld" prefix="tiles"%>
<tiles:insert page="/templates/memberHomeOneColumnTemplate.jsp" flush="true">
	<tiles:put name="topPane" value="/common/topPane.jsp"/>
 	<tiles:put name="mainPane" value="/import/showFieldMappingsMain.jsp"/>
</tiles:insert>