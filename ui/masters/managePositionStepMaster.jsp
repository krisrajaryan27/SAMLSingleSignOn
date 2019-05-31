<%@ taglib uri="/WEB-INF/tld/struts-tiles.tld" prefix="tiles"%>
<tiles:insert page="/templates/memberHomeTwoColumnTemplate.jsp" flush="true">
	<tiles:put name="topPane" value="/common/topPane.jsp"/>
	<tiles:put name="leftPane" value="/masters/leftPane.jsp"/>
  	<tiles:put name="mainPane" value="/masters/managePositionStepMasterMain.jsp"/>
</tiles:insert>