<%@ taglib uri="/WEB-INF/tld/struts-tiles.tld" prefix="tiles"%>
<tiles:insert page="/templates/memberHomeTwoColumnTemplate.jsp" flush="true">
	<tiles:put name="topPane" value="/common/topPane.jsp"/>
	<tiles:put name="leftPane" value="/inbox/leftPane.jsp"/>
    <tiles:put name="mainPane" value="/inbox/inboxMain.jsp"/>
</tiles:insert>