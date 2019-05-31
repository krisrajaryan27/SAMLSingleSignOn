<%@ taglib uri="/WEB-INF/tld/struts-tiles.tld" prefix="tiles"%>
<tiles:insert page="/templates/memberHomeTwoColumnTemplate.jsp" flush="true">
	<tiles:put name="topPane" value="/common/topPane.jsp"/>
	<tiles:put name="leftPane" value="/costs/leftPane.jsp"/>
  <tiles:put name="mainPane" value="/costs/costTypesMain.jsp"/>
</tiles:insert>